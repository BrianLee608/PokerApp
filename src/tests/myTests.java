package tests;

import poker.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class myTests {

	@Test
	public void testBasicDeck() {
		
		Deck deckA = new Deck();
		Deck deckB = new Deck();
		
		assertTrue(deckA.getNumCards() == 52);
		assertTrue(deckA.equals(deckB));
		
		deckA.shuffle();
		assertFalse(deckA.equals(deckB));
		
		deckA.deal(5);
		assertTrue(deckA.getNumCards() == 47);
		
	}

	@Test 
	public void testBasicCard() {
		
		
		
	}
}
