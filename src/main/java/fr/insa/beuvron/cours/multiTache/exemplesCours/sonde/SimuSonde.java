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
package fr.insa.beuvron.cours.multiTache.exemplesCours.sonde;

import fr.insa.beuvron.cours.multiTache.exemplesCours.Utils;
import fr.insa.beuvron.cours.multiTache.exemplesCours.exo1.PairImpairSynchroSleep;
import static fr.insa.beuvron.cours.multiTache.exemplesCours.exo1.PairImpairSynchroSleep.debug;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author francois
 */
public class SimuSonde extends Thread {

    public static final char SEPARATEUR = '\n';

    private OutputStream sortie;
    private long tempoSonde;

    public static enum Statut {
        NOTSTARTED, RUNNING, STOPPED, ERRORIO
    }

    private Statut statut;

    // TODO !! synchro --> en faire un AtomicBoolean
    private AtomicBoolean stopRequested;

    public void demandeArret() {
        this.stopRequested.set(true);
    }

    public boolean arretDemande() {
        return this.stopRequested.get();
    }

    public Statut getStatut() {
        synchronized (this) {
            return this.statut;
        }
    }

    public synchronized void setStatut(Statut statut) {
        this.statut = statut;
    }

    public SimuSonde(OutputStream sortie, long tempoSonde) {
        this.sortie = sortie;
        this.tempoSonde = tempoSonde;
        this.statut = Statut.NOTSTARTED;
    }

    @Override
    public void run() {
        debug(0, "la sonde démarre");
        this.setStatut(Statut.RUNNING);
        long deltaT = (long) (this.tempoSonde * (1 + Math.random()));
        debug(2, "la s'endord une première fois avant d'envoyer sa première donnée " + deltaT + " ms");
        Utils.sleepNoInterrupt(deltaT);
        while (!this.arretDemande()) {
            double data = Math.random();
            String message = "" + data + SEPARATEUR;
            byte[] asBytes = message.getBytes(Charset.forName("UTF8"));
            try {
                this.sortie.write(asBytes);
            } catch (IOException ex) {
                this.setStatut(Statut.ERRORIO);
                Logger.getLogger(SimuSonde.class.getName()).log(Level.SEVERE, null, ex);
            }
            deltaT = (long) (this.tempoSonde * (1 + Math.random()));
            debug(2, "la sonde s'endord " + deltaT + " ms");
            Utils.sleepNoInterrupt(deltaT);
        }
        this.setStatut(Statut.STOPPED);
    }

    public static void testV0() {
        SimuSonde sonde = new SimuSonde(System.out, 1000);
        sonde.start();
        debug(0, "main attends 5s");
        Utils.sleepNoInterrupt(5000);
        debug(0, "main demande l'arret de la sonde");
        sonde.demandeArret();
        Utils.joinNoInterrupt(sonde);
        debug(0, "sonde bien arrêtée, c'est fini");
    }

    public static void main(String[] args) {
        PairImpairSynchroSleep.DEBUGLEVEL = 10;
        testV0();
    }

}
