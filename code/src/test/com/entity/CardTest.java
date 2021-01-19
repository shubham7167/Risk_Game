package com.entity;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.*;
import com.entity.Card;
import com.config.CardType;

/**
 * This is a Test Class for testing card
 * 
 * @author Maryam
 * @author Mahmoudreza
 * @version 0.0.1
 */
public class CardTest {
	Card cr = null;

	/**
	 * This method runs before all test methods only one times
	 */
	@BeforeClass
	public static void beforeAllTesting() {
		System.out.println("This is before testing");
	}

	/**
	 * This Method runs before test methods
	 */
	@Before
	public void beforeTest() {
		cr = new Card(CardType.CAVALRY);
		cr = new Card(CardType.ARTILLERY);
		cr = new Card(CardType.INFANTRY);
	}

	/**
	 * This method run after all test methods only one time.
	 */
	@AfterClass
	public static void afterPerformingTests() {
		System.out.println("The test is done");
	}

	/**
	 * This method test get card.
	 */
	@Test
	public void testGetCardKind() {
		assertNotNull(cr.getCardKind());
		System.out.println("'assertNotNull' test is passed");
	}

}
