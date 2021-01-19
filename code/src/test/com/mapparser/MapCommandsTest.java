package com.mapparser;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.Ignore;

import com.controller.MapContoller;
import com.entity.Continent;
import com.entity.Country;
import com.entity.Hmap;
import com.exception.InvalidMap;

/**
 * This is a Test Class for testing MapCommand
 * 
 * @author Maryam
 * @author Mahmoudreza
 * @version 0.0.1
 */
public class MapCommandsTest {
	MapContoller mpcom;
	static Continent continent;
	static Hmap mp;
	String nameContinent = "North-America";
	String nameCountry = "Quebec";
	String color;
	int controlVal;
	static Country country;
	static Country adjacentCountry;
	int xCoordinate = 1;
	static HashMap<String, String> mpData;

	/**
	 * This method prepare all needs before All Testing Methods and it just runs one
	 */
	@BeforeClass
	public static void initializingForTest() {
		System.out.println("Preparing for testing MapCommand_Class");
		mp = new Hmap();
		mpData = new HashMap<>();
		continent = new Continent();
		country = new Country();
		adjacentCountry = new Country();
	}

	/**
	 * This method runs before All test case and run before each test cases
	 */
	@Before
	public void beforeTesting() {
		mpData = new HashMap<>();
		mp.setMapData(mpData);
	}

	/**
	 * This method runs After All Testing
	 */

	@AfterClass
	public static void afterPerformingTests() {
		System.out.println("All tests are done");
	}

	/**
	 * This method Test the remove Continent
	 */
	@Test
	public void removeContinentTest() {
		assertEquals(true, mpcom.removeContinent(mp, nameContinent));
		System.out.println("This is a test for Remove Continent");
	}

	/**
	 * This method Test the add Continent
	 */
	@Test
	public void addContinentTest() {
		assertEquals(true, mpcom.addContinent(mp, nameContinent, String.valueOf(controlVal), color));
		boolean output = mpcom.addContinent(mp, nameContinent, String.valueOf(controlVal), color);
		assertNotNull(output);
		System.out.println("This is a test for Add Continent it was pass");
	}

	/**
	 * This method Test the update Continent
	 */
	@Test
	public void updateContinentTest() {
		assertNotEquals(continent.getName(), nameContinent);
		System.out.println("This is a test for Update Continent");
	}

	/**
	 * This method Test the add country
	 */
	@Test
	public void addCountryTest() {
		boolean output = MapContoller.addCountry(mp, nameCountry, nameContinent);
		assertNotNull(output);
		System.out.println("This is a test for AddCountry Continent");
	}

	/**
	 * This method Test the update country
	 * @throws InvalidMap Invalid Map
	 */
	@Test
	public void updateCountryTest() throws InvalidMap {
		assertNotEquals(country.getxCoordinate(), xCoordinate);
		System.out.println("This is a test for UpdateCountry Continent");
	}

	/**
	 * This method Test map country to continent
	 */
	@Test
	public void mapCountryToContinentTest() {
		assertNotNull(mpcom.mapCountryToContinent(continent, country));
		System.out.println("This is a test for mapCountry to Continent");
	}
}