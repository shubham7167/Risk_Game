package com.entity;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.*;
import com.entity.Continent;

/**
 * This is a Test Class for testing continent
 * 
 * @author Maryam
 * @author Mahmoudreza
 * @version 0.0.1
 */
public class ContinentTest {
	Continent cont = null;

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
		cont = new Continent();
	}

	/**
	 * This method run after all test methods only one time.
	 */
	@AfterClass
	public static void afterPerformingTests() {
		System.out.println("The test is done");
	}

	/**
	 * This method test get color continent.
	 */
	@Test
	public void testGetColor() {
		assertNull(cont.getColor());
		System.out.println("'assertNull' test for getColor method is passed");
		assertNotEquals("Red", cont.getColor());
		System.out.println("'assertNotEqual' test for getColor method is passed");
	}

	/**
	 * This method test get name continent.
	 */
	@Test
	public void testGetName() {
		assertNull(cont.getName());
		System.out.println("'assertNull' test for getName method is passed");
		assertNotEquals("Advance Programming", cont.getName());
		System.out.println("'assertNotEqual' test for getName method is passed");
	}
}
