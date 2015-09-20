package poker;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class GameDriver extends Thread {
	private Socket socket;

	public GameDriver(Socket socket) {
		this.socket = socket;
	}
	//Driver will automatically start a pokerGame. After that game has ended,
	//user can opt to play again or exit. 
	public void run() {
		try {
			PokerGame pokerGame;
			//Note that input/output switch based on whether we're in the client or server class
			Scanner in = new Scanner(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			int players;
			String loadAnswer;

			do {
				loadAnswer = in.nextLine().toLowerCase();
				if (loadAnswer.startsWith("y")) {
					pokerGame = null;
					try {
						out.println("Input file name" + "\nnewline");
						String fileName = in.nextLine();
						FileInputStream fileIn = new FileInputStream("/tmp/" + fileName + ".ser");
						ObjectInputStream input = new ObjectInputStream(fileIn);
						pokerGame = (PokerGame) input.readObject();
						input.close();
						fileIn.close();
						pokerGame.startNewHand();
					} catch (IOException i) {
						out.println("File not found" + "\nnewline");
						continue;
					} catch (ClassNotFoundException c) {
						return;
					}

				} else {
					while (true) {
						try {
							out.println("New game");
							out.println("How many players?" + "\nnewline");
							players = in.nextInt();
//							//Required since nextInt() doesn't actually read a next line
							in.nextLine();
							break;
						} catch (java.util.InputMismatchException e) {
							out.println("Not a number" + "\nnewline");
							in.next();
							continue;
						}

					}
					pokerGame = new PokerGame(players, socket);
				}

				out.println("Would you like to play another poker game?" + "\nnewline");
				String askUserIfWantToPlay;
				askUserIfWantToPlay = in.nextLine().toLowerCase();
				if (askUserIfWantToPlay.startsWith("n")) {  //no more play
					break;
				} // else if ("y") { play again(go back to top of loop }

			} while (true);

			in.close();

		}catch (IOException e){
			System.out.println("Client handling error: " + e);
		} finally {
			try {
				socket.close();
			}catch (IOException e){
				System.out.println("Couldn't close a socket");
			}
			System.out.println("Connection closed");
		}
	}

}
