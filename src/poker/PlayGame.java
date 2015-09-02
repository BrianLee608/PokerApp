package poker;

import java.util.Scanner;

public class PlayGame {

	public static void main(String[] args) {
		
		PokerGame game = new PokerGame(2);
		Scanner in = new Scanner(System.in);
		int betsize;
		String action = "";

		while(!action.equalsIgnoreCase("Exit")){
			action = "";
			betsize = 0;
			System.out.println(game.players[0]);
			System.out.println("Bet/Check/Fold");
			action = in.nextLine();

			if(action.equalsIgnoreCase("Bet")){
				System.out.println("Size");
				betsize = in.nextInt();
				//Required since nextInt() doesn't actually read a next line
				in.nextLine();
				game.players[0].raise(betsize);
				game.pot = game.pot + betsize;
			}
			else if(action.equalsIgnoreCase("Check")){

			}
			else if(action.equalsIgnoreCase("Fold")){
				System.out.println("Nit");
			}
			else{}
		}
	}

}
