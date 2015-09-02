package poker;

import java.util.Arrays;
import java.util.Scanner;

public class PlayGame {

	public static void main(String[] args) {
		
		PokerGame game = new PokerGame(2);
		Scanner in = new Scanner(System.in);
		int betsize;
		String action = "";
		// Used to determine if input action is correct
		boolean isCorrect;

		while(!action.equalsIgnoreCase("Exit")){
			// Resets action and betsize after each turn
			action = "";
			betsize = 0;
			isCorrect = false;

			while(isCorrect == false){
				// Output board and player stats
				System.out.println(Arrays.toString(game.board));
				System.out.println(game.players[game.actionIndex]);
				System.out.println("Bet/Check/Fold");
				action = in.nextLine();

				// Checks what action user inputs
				if(action.equalsIgnoreCase("Bet")){
					System.out.println("Size");
					betsize = in.nextInt();
					//Required since nextInt() doesn't actually read a next line
					in.nextLine();
					game.players[game.actionIndex].raise(betsize);
					game.pot = game.pot + betsize;
					isCorrect = true;
				}
				else if(action.equalsIgnoreCase("Check")){
					isCorrect = true;
				}
				else if(action.equalsIgnoreCase("Fold")){
					isCorrect = true;
					System.out.println("Nit");

				}
				else{
					System.out.println("Incorrect Action, Please Try Again");
					action = in.nextLine();
				}
			}
			game.nextTurn();
			game.changeStreet();
			game.fillBoard();
		}
	}

}
