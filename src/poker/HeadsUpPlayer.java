//Players are mutable

package poker;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class HeadsUpPlayer extends Thread implements Serializable{

    public String name;
    private int money;
    public Card [] holeCards;
    private boolean folded;
    private int streetMoney;
    public boolean endAction;
    public boolean isAllIn;
    //ID is essentially their seat number
    public int id;
    private Socket socket;
    private Scanner in;
    private ObjectOutputStream out;
    private HeadsUpPokerGame game;
    private ArrayList<String> messages;
    private String response;
    //should we make it mutable? and allow PlayGame to modify it?
    //add more variables

    public HeadsUpPlayer(String name, int money, int id, Socket socket) {

        this.name = name;
        this.money = money;
        this.id = id;
        holeCards = new Card[2];
        folded = false;
        isAllIn = false;
        this.socket = socket;
        messages = new ArrayList<String>();
        try {
            in = new Scanner(new InputStreamReader(socket.getInputStream()));
            out = new ObjectOutputStream(socket.getOutputStream());
        }catch(IOException e){
        }
        //modify this later

    }

    public void run(){
        addMessage("Game has started");
        addMessage("Enter player name");
        send();
        clearMessages();
        this.name = receive();
        //Wait until both players have entered a name
        pause();
    }

    public int getMoney() {

        return money;

    }

    public void receiveHand(Card[] hand) {

        holeCards[0] = hand[0];
        holeCards[1] = hand[1];
        folded = false;

    }

    public Card getCardAtPosition(int pos) {

        if (pos != 0 || pos != 1) {
            //throw an exception
        }

        return holeCards[pos];

    }

    public void postBB() {

        if (money == 1) {
            money -= 1;
        } else {
            money -= PokerGame.BIG_BLIND;
        }

    }

    public void postSB() {

        money -= PokerGame.SMALL_BLIND;

    }


    private void spendMoney(int amount) {

        money -= amount;

    }

    public boolean playerFolded() {

        return this.folded;

    }

    private void fold() {

        folded = true;

    }



    //This method gets called from a method in the Hand object (startStreet())
    //In that method, each player is looped through to act();
    public int act(int minimumBet, int pot, HeadsUpHand hand, HeadsUpPokerGame game, int streetIn) {
        boolean isCorrect = false;
        String action;
        int betSize = minimumBet;
        if(!this.folded && !this.isAllIn) {
            while(!isCorrect){
                //Output board
                hand.printBoard(game, streetIn, game.handNumber, this);
                // Output hand and player stats
                addMessage(this.toString());
                addMessage("Bet/Check/Call/Fold");
                send();
                clearMessages();
                action = receive();
                // Checks what action user inputs
                if(action.equalsIgnoreCase("Bet")) {
                    while(true){
                        //Output board
                        hand.printBoard(game, streetIn, game.handNumber, this);
                        // Output hand and player stats
                        addMessage(this.toString());
                        try{
                            addMessage("Size");
                            send();
                            clearMessages();
                            //Requires exception?
                            betSize = Integer.parseInt(receive());

                            if(betSize < 2*minimumBet || betSize == 0) {
                                addMessage("Illegal bet size");
                                //Reset betsize to what was previously bet (miniumum bet)
                                betSize = minimumBet;
                            } else if (money <= betSize) {
                                addMessage("All in");
                                //If betsize is greater than money, player is all in
                                betSize = money;
                                this.spendMoney(betSize);
                                hand.addToPot(betSize);
                                streetMoney = betSize;
                                isAllIn = true;
                                hand.allInCounter++;
                                isCorrect = true;
                            } else {
                                //Any additional bet is total (don't have to remember previous bet)
                                this.spendMoney(betSize-streetMoney);
                                hand.addToPot(betSize-streetMoney);
                                //Total streetmoney becomes betsize
                                streetMoney = betSize;
                                isCorrect = true;
                            }
                            break;
                        }
                        catch(InputMismatchException e) {
                            addMessage("Not a number");
                            in.next();
                            continue;
                        }
                    }
                }
                //we need a way for BB to check b/c minbet is still > 0 for him
                else if(action.equalsIgnoreCase("Check")) {
                    if(minimumBet - streetMoney > 0){
                        addMessage("You cannot check when the pot is raised");
                    } else{
                        isCorrect = true;
                        betSize = 0;
                    }
                }
                else if(action.equalsIgnoreCase("Call")) {
                    if(minimumBet == 0 || minimumBet - streetMoney == 0){
                        addMessage("You cannot call when there is no bet");
                    }
                    else if(money <= minimumBet){
                        addMessage("All in");
                        betSize = money;
                        this.spendMoney(betSize);
                        hand.addToPot(betSize);
                        isAllIn = true;
                        hand.allInCounter++;
                        isCorrect = true;
                    }
                    else{
                        this.spendMoney(minimumBet - streetMoney);
                        hand.addToPot(minimumBet-streetMoney);
                        isCorrect = true;
                        betSize = minimumBet;
                    }
                }
                else if(action.equalsIgnoreCase("Fold")) {
                    this.fold();
                    isCorrect = true;
                    betSize = 0;
                }
                else {
                    addMessage("Incorrect action, please try again");
                }
            }
        }
        else{
            betSize = 0;
        }
        return betSize;
    }

    public void winPot(int amount) {

        money += amount;

    }

    public void setStreetMoney(int amount){
        streetMoney += amount;
    }

    public void resetStreetMoney(){
        streetMoney = 0;
    }

    public void setEndAction(boolean bool){
        endAction = bool;
    }

    public void setGame(HeadsUpPokerGame game){
        this.game = game;
    }

    public void pause(){
        synchronized (this){
            try{
                System.out.println("Waiting");
                this.wait();
            }catch(InterruptedException e){
            }
        }
    }

    public void restart(){
        synchronized (this){
            this.notify();
        }
    }

    public void addMessage(String message){
        messages.add(message);
    }

    private void clearMessages(){
        messages.clear();
    }

    private void send(){
        System.out.println(messages);
        try {
            out.reset();
            out.writeObject(messages);
            out.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private String receive(){
        response = null;
        try {
            while(response == null){
                Thread.sleep(1000);
                response = in.nextLine();
            }
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return response;
    }

    public String toString() {

        String retVal = "";
        retVal += name + ": " + "$" + money + "--" +
                holeCards[0] + holeCards[1] + "--" + id;
        return retVal;

    }

}
