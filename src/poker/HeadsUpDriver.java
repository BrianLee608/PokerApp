package poker;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class HeadsUpDriver extends Thread {

    private Socket socket1;
    private Socket socket2;
    private HeadsUpPlayer player1;
    private HeadsUpPlayer player2;

    public HeadsUpDriver(Socket socket1, Socket socket2, HeadsUpPlayer player1, HeadsUpPlayer player2){
        this.socket1 = socket1;
        this.socket2 = socket2;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void run(){
        try {
            HeadsUpPokerGame pokerGame = new HeadsUpPokerGame(2, player1, player2);
            player1.setGame(pokerGame);
            player1.setGame(pokerGame);

        }finally {
            try {
                socket1.close();
                socket2.close();
            }catch (IOException e){
                System.out.println("Couldn't close a socket");
            }
            System.out.println("Connection closed");
        }
    }

}
