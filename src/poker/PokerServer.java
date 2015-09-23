package poker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PokerServer {

    public static void main(String[] args) throws Exception {
        //Run server before running client class
        ServerSocket serverSocket = new ServerSocket(9898);
        System.out.println("Running");

        try {
            while (true) {
                //Each client window opens up a new socket
                Socket socket1 = serverSocket.accept();
                System.out.println("Player 1 has joined");
                HeadsUpPlayer player1 = new HeadsUpPlayer(null, 200, 0, socket1, 1);

                Socket socket2 = serverSocket.accept();
                System.out.println("Player 2 has joined");
                HeadsUpPlayer player2 = new HeadsUpPlayer(null, 200, 1, socket2, 0);

                //Once both players have joined add them to driver and start game
                //HeadsUpGameDriver class acts as our server
                HeadsUpDriver newGame = new HeadsUpDriver(socket1, socket2, player1, player2);
                newGame.start();
                //Player threads must be started after game has started
                //Player threads are essentially only to input names (may change in the future depending on features)
                player1.start();
                player2.start();
            }
        } finally {
            serverSocket.close();
        }
    }

}
