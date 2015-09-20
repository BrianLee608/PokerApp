//The PokerGame class represents a session of poker


package poker;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

public class PokerGame implements Serializable {

	//Constants
	public static final int STARTING_CASH = 200;
	public static final int BIG_BLIND = 2;
	public static final int SMALL_BLIND = 1;

	//variables
	public boolean gameIsLive;
	public int handNumber; 
	public int totalPlayers;
	public int sbIndex;
	public int bbIndex;
	public int actionIndex;
	public int dealerIndex;
	public ArrayList<Player> players;
	public ArrayList<Hand> hand;
	public Scanner in;
	public PrintWriter out;
	
	//Instantiate this when a fresh new game starts
	public PokerGame(int numOfPlayers, Socket socket) {
		try {
			//Initialize a blank array of hands
			hand = new ArrayList<Hand>();
			//Total starting players
			totalPlayers = numOfPlayers;

			handNumber = 0;
			sbIndex = 0;
			bbIndex = 1;
			//Action starts on UTG
			actionIndex = 2;
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(), true);

			//the arraylist posAssign will contain numOfPlayer integers
			//which are shuffled and distributed to each player to give
			//each player a unique position
			//position 0 = dealer
			//position 1 = small blind
			//position 2 = big blind
			//position 3,4,5,6,7,8 ....
			ArrayList<Integer> posAssign = new ArrayList<Integer>(numOfPlayers);
			for (int i = 0; i < numOfPlayers; i++) {
				posAssign.add(i);
			}

			java.util.Collections.shuffle(posAssign);
			//Only used initialize random order of players
			Player[] randomPlayers = new Player[totalPlayers];

			for (int i = 0; i < numOfPlayers; i++) {
				out.println("Enter player " + (i + 1) + " name: " + "\nnewline");
				String name = in.nextLine();
				//Assign random position to each player
				randomPlayers[posAssign.get(i)] = new Player(name, STARTING_CASH, posAssign.get(i));
			}

			//Set players = randomPlayers (arrayList allows us to remove players as they are eliminated)
			players = new ArrayList<Player>(Arrays.asList(randomPlayers));

			//initialize where dealer and action is (preflop)
			if (numOfPlayers == 2) {
				//Small blind is first to act headsup
				actionIndex = 0;
				dealerIndex = 1;
			} else { //if numOfPlayers > 2
				//Action index is after BB
				actionIndex = 2;
				dealerIndex = players.size() - 1;
			}

			//Initially, game will always be live....
			//until only 1 player is remaining
			gameIsLive = true;

			startNewHand();

		}catch (IOException e) {
			out.println("Client handling error: " + e);
		}
	}
	
	public void startNewHand() {
		
		while (gameIsLive) {
			hand.add(new Hand(this));
			handNumber++;
			//After every hand change indexes
			this.changeIndex();
			saveGame();
		}
	
	}

	private void changeIndex(){
		//Change action index for next hand
		actionIndex = (actionIndex == players.size()-1)? 0 : actionIndex+1;
		
		//Change sb index for next hand
		sbIndex = (sbIndex == players.size()-1)? 0 : sbIndex+1;

		//Change bb index for next hand
		bbIndex = (bbIndex == players.size()-1)? 0 : bbIndex+1;

		//Change dealer index for next hand
		dealerIndex = (dealerIndex == players.size()-1)? 0 : dealerIndex+1;
		
	}

	private void saveGame() {
		out.println("Save Game?" + "\nnewline");
		String saveAnswer = in.nextLine().toLowerCase();
		if(saveAnswer.startsWith("y")){
			try
			{
				out.println("Input File Name" + "\nnewline");
				String fileName = in.nextLine();
				FileOutputStream fileOut = new FileOutputStream("/tmp/" + fileName + ".ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(this);
				out.close();
				fileOut.close();
			}catch(IOException i) {}
		}
	}

}
