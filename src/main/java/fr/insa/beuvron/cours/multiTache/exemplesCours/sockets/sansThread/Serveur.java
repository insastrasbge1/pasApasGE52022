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

import fr.insa.beuvron.cours.multiTache.exemplesCours.Utils;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 *
 * @author francois
 */
public class Serveur {

    public static void demarre() {
        try {
            ServerSocket server = new ServerSocket(5555);
            System.out.println("je passe en attente");
            Socket soc = server.accept();
            try ( PrintWriter wout = new PrintWriter(
                    new OutputStreamWriter(
                            soc.getOutputStream(),
                            Charset.forName("UTF8")))) {
                wout.println("coucou");
                wout.println("toto");
                Utils.sleepNoInterrupt(3000);
                System.out.println("fini");
            }
        } catch (IOException ex) {
            throw new Error(ex);
        }

    }
    
    public static void main(String[] args) {
        demarre();
    }

}
