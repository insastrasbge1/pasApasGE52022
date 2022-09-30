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
package fr.insa.beuvron.cours.multiTache.exemplesCours.pbAcces;

import fr.insa.beuvron.cours.multiTache.exemplesCours.Utils;

/**
 *
 * @author francois
 */
public class Incrementeur {

    public static long val = 0;

    public static final long COMBIEN = 100000000;

    public static class Incremente extends Thread {

        @Override
        public void run() {
            for (long i = 0; i < COMBIEN; i++) {
                synchronized (Incrementeur.class) {
                    val = val + 1;
                }
            }
        }
    }

    public static void test() {
        Incremente i1 = new Incremente();
        Incremente i2 = new Incremente();
        i1.start();
        i2.start();
        Utils.joinNoInterrupt(i1);
        Utils.joinNoInterrupt(i2);
        System.out.println("val = " + val);
    }

    public static void main(String[] args) {
        test();
    }

}
