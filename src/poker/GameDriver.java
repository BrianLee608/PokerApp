package poker;

import java.util.Scanner;

public class GameDriver {

	public static void main(String[] args) {

		boolean userWantsToPlay;
		Scanner in = new Scanner(System.in);
		PokerGame pokerGame;
		
		do {
		String askUserIfWantToPlay;
		System.out.println("Texas Holdem Game\nWould you like to play a game?");
		askUserIfWantToPlay = in.nextLine().toLowerCase();
		if (askUserIfWantToPlay.startsWith("y")) {
			userWantsToPlay = true;
		} else {
			userWantsToPlay = false; 
			break;
		}
		
		pokerGame = new PokerGame(2);
		
		System.out.println("working");
		
	
		
		} while (userWantsToPlay); 
		
	}

}
