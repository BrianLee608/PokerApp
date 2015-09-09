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
	
		System.out.println(deckA);

		
		deckA.shuffle();
		System.out.println(deckA);
		
		
		
		
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
		
		Card sixClubs = new Card(Rank.SIX, Suit.Clubs);
		Card threeDiamond = new Card(Rank.THREE, Suit.Diamonds);
		Card threeHeart = new Card(Rank.THREE, Suit.Hearts);
		Card threeSpade = new Card(Rank.THREE, Suit.Spades);
		Card threeClub = new Card(Rank.THREE, Suit.Clubs);
		
		ArrayList <Card> temp = new ArrayList <Card>(Arrays.asList(aceHeart, 
				aceDiamond, aceClub, aceSpade, deuceSpade, sevenHeart, tenDiamond));
			
			
		
	
		Deck.sort(temp);
		System.out.println(temp);

		System.out.println(Arrays.toString(HandEvaluator.hasFourOfAKind(temp)));
		
		ArrayList <Card> temp2 = new ArrayList <Card>(Arrays.asList(aceHeart, 
				aceDiamond, threeDiamond, threeSpade, tenDiamond, sixClubs, threeHeart));
		Deck.sort(temp2);
		
		System.out.println(Arrays.toString(HandEvaluator.hasFourOfAKind(temp2)));

	}
}
