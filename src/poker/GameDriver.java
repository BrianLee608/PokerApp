package poker;

import java.io.*;
import java.util.Scanner;

public class GameDriver {

	//Driver will automatically start a pokerGame. After that game has ended,
	//user can opt to play again or exit. 
	public static void main(String[] args) {

		PokerGame pokerGame;
		StartWindow myStartWindow = new StartWindow();
		Scanner in = new Scanner(System.in);
		int players;
		String loadAnswer;

		System.out.println("\t\t\tTexas Holdem Game\n\t\t\t   created by\n"
				+ "\t\t\tBrian Lee & Dennis Deng 2015(c)\n");

		do {
		System.out.print("Load previous game: ");
		loadAnswer = in.nextLine().toLowerCase();
		if(loadAnswer.startsWith("y")){
			pokerGame = null;
			try
			{
				System.out.print("Input file name: ");
				String fileName = in.nextLine();
				FileInputStream fileIn = new FileInputStream("/tmp/" + fileName + ".ser");
				ObjectInputStream input = new ObjectInputStream(fileIn);
				pokerGame = (PokerGame) input.readObject();
				input.close();
				fileIn.close();
				pokerGame.startNewHand();
			}catch(IOException i)
			{
				System.out.println("File not found.\n");
				continue;
			}catch(ClassNotFoundException c){
				return;
			}

		}
		else{
			while(true){
				try{
					System.out.println("New game");
					System.out.print("How many players: ");
					players = in.nextInt();
					//Required since nextInt() doesn't actually read a next line
					in.nextLine();
					break;
				}
				catch(java.util.InputMismatchException e) {
					System.out.print("Not a number\n");
					in.next();
					continue;
				}

			}
			pokerGame = new PokerGame(players);

		}

		System.out.print("Would you like to play another poker game?: ");
		String askUserIfWantToPlay;
		askUserIfWantToPlay = in.nextLine().toLowerCase();
		if (askUserIfWantToPlay.startsWith("n")) {  //no more play
			break;
		} // else if ("y") { play again(go back to top of loop }
		
		} while (true);
		
		in.close();
		
	}

}
