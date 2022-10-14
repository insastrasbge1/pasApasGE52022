/*
Copyright 2000- Francois de Bertrand de Beuvron

This file is part of CoursBeuvron.

CoursBeuvron is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

CoursBeuvron is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with CoursBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insa.beuvron.cours.multiTache.exemplesCours.prodConso.lockUneCondition;

import fr.insa.beuvron.cours.multiTache.exemplesCours.Utils;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * les consomateurs et les producteurs partage un lock (du package concurrent)
 * sur la ressource. Pour garantir que cela fonctionne, il faudrait que la
 * gestion des locks soit équitable (fair) ie : chaque thread en attente fini
 * par obtenir le lock. Sinon, on peut aboutir à une situation de blocage : si
 * dispo = 0, et que c'est systématiquement un consomateur qui obtient le lock
 * (le/les producteurs ne l'obtiennent jamais), alors la production est bloquée.
 * Les locks de package concurrent peuvent garantir l'équité (si demandé)
 *
 * @author francois
 */
public class Ressources {

    public static int MAXR = 10;

    private int dispo;

    private Lock lock;
    private Condition condForProdAndConso;

    public Ressources() {
        this(0);
    }

    public Ressources(int val) {
        this.dispo = val;
        // ici, le lock doit être équitable (fair) car les producteurs et les
        // consomateurs "await" sur la même condition
        this.lock = new ReentrantLock(true);
        this.condForProdAndConso = this.lock.newCondition();
    }

    public void ajoute() {
        boolean ok = false;
        while (!ok) {
            this.lock.lock();
            try {
                if (this.dispo < MAXR) {
                    this.dispo = this.dispo + 1;
                    ok = true;
                    Utils.debug(1, Thread.currentThread() + " : ajout ==> " + this.dispo);
                    condForProdAndConso.signalAll();
                } else {
                    try {
                        condForProdAndConso.await();
                    } catch (InterruptedException ex) {
                    }
                }
            } finally {
                this.lock.unlock();
            }
        }
    }

    public void consomme() {
        boolean ok = false;
        while (!ok) {
            this.lock.lock();
            try {
                if (this.dispo > 0) {
                    this.dispo = this.dispo - 1;
                    ok = true;
                    Utils.debug(1, Thread.currentThread() + " : consomme ==> " + this.dispo);
                    condForProdAndConso.signalAll();
                } else {
                    try {
                        condForProdAndConso.await();
                    } catch (InterruptedException ex) {
                    }
                }
            } finally {
                this.lock.unlock();
            }
        }
    }

    /**
     * @return the dispo
     */
    public int getDispo() {
        return dispo;
    }

    /**
     * @param dispo the dispo to set
     */
    public void setDispo(int dispo) {
        this.dispo = dispo;
    }

}
