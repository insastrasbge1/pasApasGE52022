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
public class Main {

    public static void main(String[] args) {
        Utils.DEBUGLEVEL = 1;
        int nbrRessourcesInitial = 5;
        int nbrProd = 30;
        int nbrConso = 30;
        long tempsMoyenProd = 1000;  // 0.5 * tempsMoyenProd <= tempsProd < 1.5 * tempsMoyenProd
        long tempsMoyenConso = 1000;
        Ressources com = new Ressources(5);
        for (int i = 0; i < nbrProd; i++) {
            Producteur p = new Producteur(i, com,tempsMoyenProd);
            p.start();
        }
        for (int i = 0; i < nbrConso; i++) {
            Consomateur c = new Consomateur(i, com,tempsMoyenConso);
            c.start();
        }
    }

}
