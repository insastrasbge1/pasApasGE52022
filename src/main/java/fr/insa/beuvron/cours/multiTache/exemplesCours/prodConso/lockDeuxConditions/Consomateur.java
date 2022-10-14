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
package fr.insa.beuvron.cours.multiTache.exemplesCours.prodConso.lockDeuxConditions;

import fr.insa.beuvron.cours.multiTache.exemplesCours.Utils;

/**
 *
 * @author francois
 */
public class Consomateur extends Thread {
    
    private int id;
    private Ressources commun;
    private long tempsConso;

    public Consomateur(int id,Ressources commun,long tempsConso) {
        this.id = id;
        this.commun = commun;
        this.tempsConso = tempsConso;
    }
    
    public String toString() {
        return "Conso "+this.id;
    }
    
    @Override
    public void run() {
        while(true) {
            Utils.sleepNoInterrupt(
                    (long)(tempsConso*(Math.random()+0.5)));
            Utils.debug(2, "demande conso");
            this.commun.consomme();
        }
        
    }
    
}
