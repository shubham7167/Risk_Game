package com.entity;

import com.entity.Country;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * This is a Test Class for testing Country Class
 * 
 * @author Maryam
 * @author Mahmoudreza
 * @version 0.0.1
 */
public class CountryTest {
	Country cn = null;

	/**
	 * This method runs before all test methods only one times
	 */
	@BeforeClass
	public static void beforeAllTesting() {
		System.out.println("This is for testing Country Class");
	}

	/**
	 * This Method runs before test methods
	 */
	@Before
	public void beforeTest() {
		cn = new Country();
	}

	/**
	 * This method run after all test methods only one time.
	 */
	@AfterClass
	public static void afterPerformingTests() {
		System.out.println("The test is done");
	}

	/**
	 * This method test get army in country.
	 */
	@Test
	public void testGetArmy() {
		assertEquals(0, cn.getArmy());
		System.out.println("'assertEquals' test for getArmy method is passed");

		assertTrue(cn.getArmy() == 0);
		System.out.println("'assertTrue' test for getArmy method is passed");

		assertNotEquals(6000, cn.getArmy());
		System.out.println("'assertNotEquals' test for getArmy method is passed");
	}

	/**
	 * This method test get player in country.
	 */
	@Test
	public void testGetPlayer() {
		assertNull(cn.getPlayer());
		System.out.println("'assertNull' test for getPlayer method is passed");

		assertNotEquals("Name of the Player", cn.getPlayer());
		System.out.println("'assertNotEqual' test for getPlayer method is passed");
	}

}
