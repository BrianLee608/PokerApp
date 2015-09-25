package tests;

import java.util.ArrayList;
import java.util.Arrays;

import poker.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class myTests {

	@Test
	public void testBasicDeck() {

		Deck deckA = new Deck();
		Deck deckB = new Deck();
		
		assertTrue(deckA.getNumCards() == 52);
		//assertTrue(deckA.equals(deckB));
		
		deckA.shuffle();
		assertFalse(deckA.equals(deckB));
		
		deckA.deal(5);
		assertTrue(deckA.getNumCards() == 47);
		
	}
/*
	@Test 
	public void testBasicCard() {
		
		Card aceHeart = new Card(Rank.ACE, Suit.Hearts);
		Card aceHeartCopy = new Card(aceHeart);
		Card aceClub = new Card(Rank.ACE, Suit.Clubs);
		Card deuceSpade = new Card(Rank.DEUCE, Suit.Spades);
		Card tenDiamond = new Card(Rank.TEN,Suit.Diamonds);
		
		assertTrue(aceHeart.equals(aceHeartCopy));
		assertFalse(aceHeart.equals(aceClub));
		assertTrue(aceHeart.compareTo(aceClub) == 0);
		assertTrue(aceClub.compareTo(deuceSpade) > 0);
		assertTrue(deuceSpade.compareTo(tenDiamond) < 0);
		
		Card[] temp = {aceHeart, aceClub, deuceSpade, tenDiamond };
		System.out.println(Arrays.toString(temp));
	
		Deck.sort(temp);
		System.out.println(Arrays.toString(temp));
		
	}
	*/
	
	@Test
	public void testShuffle(){
		
		Deck deckA = new Deck();
	
//		System.out.println(deckA);
//
//
//		deckA.shuffle();
//		System.out.println(deckA);

	}
	
	@Test 
	public void testQuad() {
		
		Card aceHeart = new Card(Rank.ACE, Suit.Hearts);
		Card aceDiamond = new Card(Rank.ACE, Suit.Diamonds);
		Card aceClub = new Card(Rank.ACE, Suit.Clubs);
		Card aceSpade = new Card(Rank.ACE, Suit.Spades);

		Card sevenHeart = new Card(Rank.SEVEN, Suit.Hearts);
		Card deuceSpade = new Card(Rank.DEUCE, Suit.Spades);
		Card tenDiamond = new Card(Rank.TEN,Suit.Diamonds);
		
		Card sixClub = new Card(Rank.SIX, Suit.Clubs);
		Card threeDiamond = new Card(Rank.THREE, Suit.Diamonds);
		Card threeHeart = new Card(Rank.THREE, Suit.Hearts);
		Card threeSpade = new Card(Rank.THREE, Suit.Spades);
		Card threeClub = new Card(Rank.THREE, Suit.Clubs);
		
		ArrayList <Card> temp = new ArrayList <Card>(Arrays.asList(aceHeart, 
				aceDiamond, aceClub, aceSpade, deuceSpade, sevenHeart, tenDiamond));




		HandEvaluator.sort(temp);
//		System.out.println(temp);
//
//		System.out.println(Arrays.toString(HandEvaluator.hasFourOfAKind(temp)));
//
//		ArrayList <Card> temp2 = new ArrayList <Card>(Arrays.asList(aceHeart,
//				aceDiamond, threeDiamond, threeSpade, tenDiamond, sixClub, threeHeart));
//		HandEvaluator.sort(temp2);
//
//		System.out.println(Arrays.toString(HandEvaluator.hasFourOfAKind(temp2)));

	}

	@Test
	public void testFullHouse() {

		Card aceHeart = new Card(Rank.ACE, Suit.Hearts);
		Card aceDiamond = new Card(Rank.ACE, Suit.Diamonds);
		Card aceClub = new Card(Rank.ACE, Suit.Clubs);
		Card aceSpade = new Card(Rank.ACE, Suit.Spades);

		Card sevenHeart = new Card(Rank.SEVEN, Suit.Hearts);
		Card sevenClub = new Card(Rank.SEVEN, Suit.Clubs);
		Card sevenSpade = new Card(Rank.SEVEN, Suit.Spades);
		Card deuceSpade = new Card(Rank.DEUCE, Suit.Spades);
		Card tenDiamond = new Card(Rank.TEN,Suit.Diamonds);

		Card sixClub = new Card(Rank.SIX, Suit.Clubs);
		Card threeDiamond = new Card(Rank.THREE, Suit.Diamonds);
		Card threeHeart = new Card(Rank.THREE, Suit.Hearts);
		Card threeSpade = new Card(Rank.THREE, Suit.Spades);
		Card threeClub = new Card(Rank.THREE, Suit.Clubs);

		ArrayList <Card> temp = new ArrayList <Card>(Arrays.asList(sevenSpade,
				threeClub, threeDiamond, threeHeart, threeSpade, sevenHeart, sevenClub));

		HandEvaluator.sort(temp);
//		System.out.println(Arrays.toString(HandEvaluator.hasFullHouse(temp)));

	}

	@Test
	public void testFlush() {

		Card aceHeart = new Card(Rank.ACE, Suit.Hearts);
		Card aceDiamond = new Card(Rank.ACE, Suit.Diamonds);
		Card aceClub = new Card(Rank.ACE, Suit.Clubs);
		Card aceSpade = new Card(Rank.ACE, Suit.Spades);

		Card sevenHeart = new Card(Rank.SEVEN, Suit.Hearts);
		Card sevenClub = new Card(Rank.SEVEN, Suit.Clubs);
		Card sevenSpade = new Card(Rank.SEVEN, Suit.Spades);
		Card deuceSpade = new Card(Rank.DEUCE, Suit.Spades);
		Card tenDiamond = new Card(Rank.TEN,Suit.Diamonds);
		Card fourSpade = new Card(Rank.FOUR, Suit.Spades);
		Card fiveSpade = new Card(Rank.FIVE, Suit.Spades);

		Card sixClub = new Card(Rank.SIX, Suit.Clubs);
		Card threeDiamond = new Card(Rank.THREE, Suit.Diamonds);
		Card threeHeart = new Card(Rank.THREE, Suit.Hearts);
		Card threeSpade = new Card(Rank.THREE, Suit.Spades);
		Card threeClub = new Card(Rank.THREE, Suit.Clubs);

		ArrayList <Card> temp = new ArrayList <Card>(Arrays.asList(sevenSpade,
				threeClub, threeDiamond, threeHeart, threeSpade, fourSpade, fiveSpade));

		//Sort by rank, then sort by suit
		HandEvaluator.sort(temp);
		HandEvaluator.sortSuit(temp);
//		System.out.println(Arrays.toString(HandEvaluator.hasFlush(temp)));

	}

	@Test
	public void testStraight() {

		Card aceHeart = new Card(Rank.ACE, Suit.Hearts);
		Card aceDiamond = new Card(Rank.ACE, Suit.Diamonds);
		Card aceClub = new Card(Rank.ACE, Suit.Clubs);
		Card aceSpade = new Card(Rank.ACE, Suit.Spades);

		Card sevenHeart = new Card(Rank.SEVEN, Suit.Hearts);
		Card sevenClub = new Card(Rank.SEVEN, Suit.Clubs);
		Card sevenSpade = new Card(Rank.SEVEN, Suit.Spades);
		Card deuceSpade = new Card(Rank.DEUCE, Suit.Spades);
		Card tenDiamond = new Card(Rank.TEN,Suit.Diamonds);
		Card fourSpade = new Card(Rank.FOUR, Suit.Spades);
		Card fiveSpade = new Card(Rank.FIVE, Suit.Spades);
		Card deuceHeart = new Card(Rank.DEUCE, Suit.Hearts);
		Card fiveHeart = new Card(Rank.FIVE, Suit.Hearts);

		Card sixClub = new Card(Rank.SIX, Suit.Clubs);
		Card threeDiamond = new Card(Rank.THREE, Suit.Diamonds);
		Card threeHeart = new Card(Rank.THREE, Suit.Hearts);
		Card threeSpade = new Card(Rank.THREE, Suit.Spades);
		Card threeClub = new Card(Rank.THREE, Suit.Clubs);
		Card eightClub = new Card(Rank.EIGHT, Suit.Clubs);


		ArrayList <Card> temp = new ArrayList <Card>(Arrays.asList(sevenSpade,
				sixClub, threeDiamond, deuceSpade, sevenClub, fourSpade, fiveSpade));

		//Sort by rank, then sort by suit
		HandEvaluator.sort(temp);
		
		ArrayList <Card> temp2 = new ArrayList <Card>(Arrays.asList(deuceHeart,
				threeClub, fourSpade, fiveHeart, sevenClub, eightClub, sixClub));
		HandEvaluator.sort(temp2);
//		System.out.println("About to test regular flush");
//		System.out.println(temp2);
//		System.out.println(Arrays.toString(HandEvaluator.hasStraight(temp2)));

		
		

	}
	
	
	@Test
	public void testTwoPair() {

		Card aceHeart = new Card(Rank.ACE, Suit.Hearts);
		Card aceDiamond = new Card(Rank.ACE, Suit.Diamonds);
		Card aceClub = new Card(Rank.ACE, Suit.Clubs);
		Card aceSpade = new Card(Rank.ACE, Suit.Spades);

		Card sevenHeart = new Card(Rank.SEVEN, Suit.Hearts);
		Card sevenClub = new Card(Rank.SEVEN, Suit.Clubs);
		Card sevenSpade = new Card(Rank.SEVEN, Suit.Spades);
		Card deuceSpade = new Card(Rank.DEUCE, Suit.Spades);
		Card tenDiamond = new Card(Rank.TEN,Suit.Diamonds);
		Card fourSpade = new Card(Rank.FOUR, Suit.Spades);
		Card fiveSpade = new Card(Rank.FIVE, Suit.Spades);

		Card sixClub = new Card(Rank.SIX, Suit.Clubs);
		Card threeDiamond = new Card(Rank.THREE, Suit.Diamonds);
		Card threeHeart = new Card(Rank.THREE, Suit.Hearts);
		Card threeSpade = new Card(Rank.THREE, Suit.Spades);
		Card threeClub = new Card(Rank.THREE, Suit.Clubs);

		ArrayList <Card> temp = new ArrayList <Card>(Arrays.asList(sevenSpade,
				sixClub, sevenHeart, deuceSpade, threeSpade, threeClub, fiveSpade));

//		HandEvaluator.sort(temp);

		ArrayList <Card> temp2 = new ArrayList <Card>(Arrays.asList(aceClub,
				aceDiamond, sevenHeart, sevenClub, threeSpade, threeClub, fiveSpade));
		
//		HandEvaluator.sort(temp2);
		//make sure it won't count 3 pair
		
	}
	
	
	@Test
	public void testStraightFlush() {

		Card aceSpade = new Card(Rank.ACE, Suit.Spades);
		Card aceHeart = new Card(Rank.ACE, Suit.Hearts);
		Card kingHeart = new Card(Rank.KING, Suit.Hearts);
		Card queenHeart = new Card(Rank.QUEEN, Suit.Hearts);
		Card jackHeart = new Card(Rank.JACK, Suit.Hearts);
		Card tenHeart = new Card(Rank.TEN, Suit.Hearts);


		Card sevenHeart = new Card(Rank.SEVEN, Suit.Hearts);
		Card sevenClub = new Card(Rank.SEVEN, Suit.Clubs);
		Card sevenSpade = new Card(Rank.SEVEN, Suit.Spades);
		Card deuceSpade = new Card(Rank.DEUCE, Suit.Spades);
		Card tenDiamond = new Card(Rank.TEN,Suit.Diamonds);
		Card fourSpade = new Card(Rank.FOUR, Suit.Spades);
		Card fiveSpade = new Card(Rank.FIVE, Suit.Spades);

		Card sixSpade = new Card(Rank.SIX, Suit.Spades);
		Card threeDiamond = new Card(Rank.THREE, Suit.Diamonds);
		Card threeHeart = new Card(Rank.THREE, Suit.Hearts);
		Card threeSpade = new Card(Rank.THREE, Suit.Spades);
		Card threeClub = new Card(Rank.THREE, Suit.Clubs);

		ArrayList <Card> temp = new ArrayList <Card>(Arrays.asList(sevenSpade,
				sixSpade, aceHeart, kingHeart, queenHeart, jackHeart, tenHeart));

		
		HandEvaluator.sortSuitAndNumeral(temp);
		
		ArrayList <Card> temp2 = new ArrayList <Card>(Arrays.asList(sevenSpade,
				sixSpade, fiveSpade, fourSpade, threeSpade, jackHeart, tenHeart));
		
		HandEvaluator.sortSuitAndNumeral(temp2);
//		System.out.println(temp2);
		
//		System.out.println(Arrays.toString(HandEvaluator.hasStraightFlush(temp2)));
		
		
		Card deuceHeart = new Card(Rank.DEUCE, Suit.Hearts);
		Card threeHeart2 = new Card(Rank.THREE, Suit.Hearts);
		Card fourHeart = new Card(Rank.FOUR, Suit.Hearts);
		Card fiveHeart = new Card(Rank.FIVE, Suit.Hearts);
		Card aceHeart2 = new Card(Rank.ACE, Suit.Hearts);
		Card jackSpade = new Card(Rank.JACK, Suit.Spades);
		Card tenSpade = new Card(Rank.TEN, Suit.Spades);

		ArrayList <Card> temp3 = new ArrayList <Card>(Arrays.asList(aceHeart2,
				deuceHeart, fiveHeart, fourHeart, threeHeart2, jackSpade, tenSpade));
		HandEvaluator.sortSuitAndNumeral(temp3);
//		System.out.println(temp3);
//		System.out.println(Arrays.toString(HandEvaluator.hasStraightFlush(temp3)));

		
	}

	@Test
		 public void testhasThreeOfAKind() {

		Card aceHeart = new Card(Rank.ACE, Suit.Hearts);
		Card aceDiamond = new Card(Rank.ACE, Suit.Diamonds);
		Card aceClub = new Card(Rank.ACE, Suit.Clubs);
		Card aceSpade = new Card(Rank.ACE, Suit.Spades);

		Card sevenHeart = new Card(Rank.SEVEN, Suit.Hearts);
		Card sevenClub = new Card(Rank.SEVEN, Suit.Clubs);
		Card sevenSpade = new Card(Rank.SEVEN, Suit.Spades);
		Card deuceSpade = new Card(Rank.DEUCE, Suit.Spades);
		Card tenDiamond = new Card(Rank.TEN,Suit.Diamonds);

		Card sixClub = new Card(Rank.SIX, Suit.Clubs);
		Card threeDiamond = new Card(Rank.THREE, Suit.Diamonds);
		Card threeHeart = new Card(Rank.THREE, Suit.Hearts);
		Card threeSpade = new Card(Rank.THREE, Suit.Spades);
		Card threeClub = new Card(Rank.THREE, Suit.Clubs);
		Card fourClub = new Card(Rank.FOUR, Suit.Clubs);


		ArrayList <Card> temp = new ArrayList <Card>(Arrays.asList(aceClub,
				threeClub, threeDiamond, threeHeart, sixClub, tenDiamond, sevenClub));

		HandEvaluator.sort(temp);
//		System.out.println(Arrays.toString(HandEvaluator.hasThreeOfAKind(temp)));

	}

	@Test
	public void testhasOnePair() {

		Card aceHeart = new Card(Rank.ACE, Suit.Hearts);
		Card aceDiamond = new Card(Rank.ACE, Suit.Diamonds);
		Card aceClub = new Card(Rank.ACE, Suit.Clubs);
		Card aceSpade = new Card(Rank.ACE, Suit.Spades);

		Card sevenHeart = new Card(Rank.SEVEN, Suit.Hearts);
		Card sevenClub = new Card(Rank.SEVEN, Suit.Clubs);
		Card sevenSpade = new Card(Rank.SEVEN, Suit.Spades);
		Card deuceSpade = new Card(Rank.DEUCE, Suit.Spades);
		Card tenDiamond = new Card(Rank.TEN,Suit.Diamonds);

		Card sixClub = new Card(Rank.SIX, Suit.Clubs);
		Card threeDiamond = new Card(Rank.THREE, Suit.Diamonds);
		Card threeHeart = new Card(Rank.THREE, Suit.Hearts);
		Card threeSpade = new Card(Rank.THREE, Suit.Spades);
		Card threeClub = new Card(Rank.THREE, Suit.Clubs);
		Card fourClub = new Card(Rank.FOUR, Suit.Clubs);


		ArrayList <Card> temp = new ArrayList <Card>(Arrays.asList(aceClub,
				threeClub, fourClub, threeHeart, sixClub, tenDiamond, sevenClub));

		HandEvaluator.sort(temp);
//		System.out.println(Arrays.toString(HandEvaluator.hasOnePair(temp)));

	}

	@Test
	public void testHandEvaluator() {

		Card sevenHeart = new Card(Rank.SEVEN, Suit.Hearts);
		Card sevenClub = new Card(Rank.SEVEN, Suit.Clubs);

		Card sevenSpade = new Card(Rank.SEVEN, Suit.Spades);
		Card deuceSpade = new Card(Rank.DEUCE, Suit.Spades);

		Card aceSpade = new Card(Rank.ACE, Suit.Spades);
		Card kingClub = new Card(Rank.KING, Suit.Clubs);

		Card aceClub = new Card(Rank.ACE, Suit.Clubs);
		Card deuceClub = new Card(Rank.DEUCE, Suit.Clubs);

		Card tenDiamond = new Card(Rank.TEN, Suit.Diamonds);
		Card sevenDiamond = new Card(Rank.SEVEN, Suit.Diamonds);
		Card tenSpade = new Card(Rank.TEN, Suit.Spades);
		Card fourSpade = new Card(Rank.FOUR, Suit.Spades);
		Card fiveSpade = new Card(Rank.FIVE, Suit.Hearts);
		Card sixClub = new Card(Rank.SIX, Suit.Clubs);
		Card threeDiamond = new Card(Rank.THREE, Suit.Diamonds);
		Card queenSpade = new Card(Rank.QUEEN, Suit.Spades);
		Card tenHeart = new Card(Rank.TEN, Suit.Hearts);
		Card tenClub = new Card(Rank.TEN, Suit.Clubs);
		Card aceDiamond = new Card(Rank.ACE, Suit.Diamonds);
		Card fourDiamond = new Card(Rank.FOUR, Suit.Diamonds);
		Card fiveClub = new Card(Rank.FIVE, Suit.Clubs);
		Card eightSpade = new Card(Rank.EIGHT, Suit.Spades);
		Card kingSpade = new Card(Rank.KING, Suit.Spades);
		Card nineClub = new Card(Rank.NINE, Suit.Clubs);
		Card jackSpade = new Card(Rank.JACK, Suit.Spades);





		ArrayList <Card> temp = new ArrayList <Card>(Arrays.asList(threeDiamond, eightSpade, fiveClub, fourSpade, deuceClub));
		Card[] tempBoard = temp.toArray(new Card[temp.size()]);
		ArrayList <Card> temp1 = new ArrayList <Card>(Arrays.asList(sevenDiamond, tenSpade, tenDiamond, fourSpade, fiveSpade));
		Card[] tempBoard1 = temp1.toArray(new Card[temp1.size()]);
		ArrayList <Card> temp2 = new ArrayList <Card>(Arrays.asList(queenSpade, tenSpade, tenDiamond, fourSpade, fiveSpade));
		Card[] tempBoard2 = temp2.toArray(new Card[temp2.size()]);
		ArrayList <Card> temp3 = new ArrayList <Card>(Arrays.asList(tenHeart, tenSpade, tenDiamond, tenClub, aceDiamond));
		Card[] tempBoard3 = temp3.toArray(new Card[temp3.size()]);
		ArrayList <Card> temp4 = new ArrayList <Card>(Arrays.asList(aceDiamond, fourDiamond, sevenDiamond, threeDiamond, tenDiamond));
		Card[] tempBoard4 = temp4.toArray(new Card[temp4.size()]);
		ArrayList <Card> temp5 = new ArrayList <Card>(Arrays.asList(deuceClub, eightSpade, sevenDiamond, deuceSpade, tenDiamond));
		Card[] tempBoard5 = temp5.toArray(new Card[temp5.size()]);

		ArrayList <Player> tempPlayers = new ArrayList<Player>(4);
		tempPlayers.add(new Player("a",200,0));
		tempPlayers.add(new Player("b",200,1));
//		tempPlayers.add(new Player("c",200,2));
//		tempPlayers.add(new Player("d",200,3));


		Card [] tempPlayerCards1 = new Card[2];
		tempPlayerCards1[0] = sevenClub;
		tempPlayerCards1[1] = sixClub;

		Card [] tempPlayerCards2 = new Card[2];
		tempPlayerCards2[0] = deuceClub;
		tempPlayerCards2[1] = deuceSpade;

		Card [] tempPlayerCards3 = new Card[2];
		tempPlayerCards3[0] = kingSpade;
		tempPlayerCards3[1] = nineClub;

		Card [] tempPlayerCards4 = new Card[2];
		tempPlayerCards4[0] = kingClub;
		tempPlayerCards4[1] = jackSpade;

		tempPlayers.get(0).receiveHand(tempPlayerCards1);
		tempPlayers.get(1).receiveHand(tempPlayerCards2);
//		tempPlayers.get(2).receiveHand(tempPlayerCards3);
//		tempPlayers.get(3).receiveHand(tempPlayerCards4);

		HandEvaluator.evaluateHands(tempPlayers, tempBoard);
//		HandEvaluator.evaluateHands(tempPlayers, tempBoard1);
//		HandEvaluator.evaluateHands(tempPlayers, tempBoard2);
//		HandEvaluator.evaluateHands(tempPlayers, tempBoard3);
//		HandEvaluator.evaluateHands(tempPlayers, tempBoard4);
//		HandEvaluator.evaluateHands(tempPlayers, tempBoard5);


	}

}
