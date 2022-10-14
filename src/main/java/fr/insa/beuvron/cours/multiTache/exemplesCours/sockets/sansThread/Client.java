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
package fr.insa.beuvron.cours.multiTache.exemplesCours.sockets.sansThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 *
 * @author francois
 */
public class Client {

    public static void demarre() {
        try {
            Socket soc = new Socket("localhost", 5555);
            try ( BufferedReader bin = new BufferedReader(
                    new InputStreamReader(soc.getInputStream(),
                            Charset.forName("UTF8")))) {
                String line;
                while((line = bin.readLine()) != null) {
                    System.out.println(line);
                }
                System.out.println("fin normale");

            }
        } catch (IOException ex) {
            throw new Error(ex);
        }
    }

    public static void main(String[] args) {
        demarre();
    }
}
