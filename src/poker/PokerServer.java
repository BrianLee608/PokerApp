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
        ServerSocket listener = new ServerSocket(9898);
        try {
            while (true) {
                System.out.println("Running");
                //GameDriver class acts as our server
                GameDriver newGame = new GameDriver(listener.accept());
                newGame.start();
            }
        } finally {
            listener.close();
        }
    }

}
