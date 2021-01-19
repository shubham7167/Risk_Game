package com.entity;

import com.entity.Player;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This is a Test Class for testing player
 * 
 * @author Maryam
 * @author Mahmoudreza
 * @author Komal
 * @version 0.0.1
 */
public class PlayerTest {
	Player player;
	int id1 = 10;
	int id2 = 12;

	/**
	 * This method runs before all test methods only one times
	 */
	@BeforeClass
	public static void beforeAllTesting() {
		System.out.println("This is for testing Player Class");
	}

	/**
	 * This Method runs before test methods
	 */
	@Before
	public void beforeTest() {
		player = new Player(id1, "Player");
	}

	/**
	 * This method run after all test methods only one time.
	 */
	@AfterClass
	public static void afterPerformingTests() {
		System.out.println("The tests are done");
	}

	/**
	 * This method test id of player.
	 */
	@Test
	public void testGetId() {
		assertEquals(id1, player.getId());
		System.out.println("'assertEquals' test for getId method is passed");

		assertTrue(player.getId() == id1);
		System.out.println("'assertTrue' test for getId method is passed");

		assertNotEquals(id2, player.getId());
		System.out.println("'assertNotEquals' test for getId method is passed");
	}

}
