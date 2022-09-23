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
package fr.insa.beuvron.cours.multiTache.exemplesCours.exo1;

/**
 *
 * @author francois
 */
public class PairImpairSynchroSleep {

    public static int DEBUGLEVEL = -1;

    public static void debug(int level, String mess) {
        if (level <= DEBUGLEVEL) {
            System.out.println("DEBUG (" + level + ") : " + mess);
        }
    }

    private Pair lePair;
    private Impair leImpair;

    public PairImpairSynchroSleep() {
        this.lePair = this.new Pair();
        this.leImpair = this.new Impair();
    }

    public class Pair extends Thread {

        @Override
        public void run() {
            debug(0, "pair démarre");
            for (int i = 0; i <= 10; i = i + 2) {
                System.out.println(i);
                debug(1, "pair réveille impair");
                leImpair.interrupt();

                try {
                    if (i != 10) {
                        debug(1, "pair s'endord");
                        Thread.sleep(Long.MAX_VALUE);
                    }
                } catch (InterruptedException ex) {
                    debug(1, "pair se réveille");
                }
            }

        }
    }

    public class Impair extends Thread {

        @Override
        public void run() {
            for (int i = 1; i <= 10; i = i + 2) {
                try {
                    debug(1, "impair s'endord");
                    Thread.sleep(Long.MAX_VALUE);
                } catch (InterruptedException ex) {
                    debug(1, "impair se réveille");
                }
                System.out.println(i);
                debug(1, "impair réveille pair");
                lePair.interrupt();

            }

        }
    }

    public void gogogo() {
        lePair.start();
        leImpair.start();
        try {
            lePair.join();
            lePair.join();
        } catch (InterruptedException ex) {
            throw new Error("ne devrait jamais arriver");
        }
        debug(0,"c'est fini");
    }

    public static void main(String[] args) {
        DEBUGLEVEL = 10;
        PairImpairSynchroSleep pi = new PairImpairSynchroSleep();
        pi.gogogo();
    }

}
