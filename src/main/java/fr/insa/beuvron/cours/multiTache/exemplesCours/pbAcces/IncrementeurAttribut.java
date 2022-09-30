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
public class IncrementeurAttribut {

    public static long COMBIEN = 100000000;

    public static class Data {

        public long val = 0;
    }

    public static class Incremente extends Thread {

        private Data laVal;

        public Incremente(Data laVal) {
            this.laVal = laVal;
        }

        @Override
        public void run() {
            for (long i = 0; i < COMBIEN; i++) {
                synchronized (this.laVal) {
                    this.laVal.val = this.laVal.val + 1;
                }
            }
        }
    }

    public static void test() {
        Data d = new Data();
        Incremente i1 = new Incremente(d);
        Incremente i2 = new Incremente(d);
        i1.start();
        i2.start();
        Utils.joinNoInterrupt(i1);
        Utils.joinNoInterrupt(i2);
        System.out.println("val = " + d.val);
    }

    public static void main(String[] args) {
        test();
    }

}
