package com.mapparser;

import static org.junit.Assert.*;

import com.exception.InvalidMap;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import java.io.*;
import com.entity.Hmap;

/**
 * This class tests all functions for validating Map and Continent.
 * 
 * @author Mahmoudreza
 * @author Maryam
 * @version 0.0.1
 */
public class MapReaderTest {

	static File file = null;
	MapReader mpReader = null;
	Hmap hmp = null;
	ClassLoader clLoader = null;
	static String[] invalidFiles = { "world.map", "country_with_two_continents.map", "country_without_border.map",
			"country_without_continent.map", "countries_in_same_continent.map" };

	/**
	 * This method runs only once before running all methods in the class.
	 */
	@BeforeClass
	public static void beforeClass() {
		System.out.println("The tests are started");
	}

	/**
	 * This method runs before running each test methods. 
	 * @throws IOException io Exception
	 */
	@Before
	public void beforeMethods() throws IOException {
		mpReader = new MapReader();
		hmp = new Hmap();
		clLoader = getClass().getClassLoader();
	}

	/**
	 * This method runs only once after all testing methods.
	 */
	@AfterClass
	public static void afterAllTests() {
		System.out.println("All tests are done");
	}

	/**
	 * This method tests map validation 
	 * @throws InvalidMap Invalid Map
	 */
	@Test
	public void testMapValidation() throws InvalidMap {
		file = new File(clLoader.getResource("world.map").getFile().replace("%20", " "));
		hmp = mpReader.readDominationMapFile(file);
		assertEquals(hmp.getContinents().size(), 6);
	}

	/**
	 * This method tests countries which are in the same continent 
	 * @throws InvalidMap (Exception)
	 */
	@Test(expected = InvalidMap.class)
	public void testCountriesInTheSameContinent() throws InvalidMap {
		file = new File(clLoader.getResource(invalidFiles[4]).getFile().replace("%20", " "));
		mpReader.readDominationMapFile(file);
	}

	/**
	 * This method tests Countries which not have border
	 * 
	 * @throws InvalidMap (Exception)
	 */
	@Test(expected = InvalidMap.class)
	public void testCountriesWithoutBorder() throws InvalidMap {
		file = new File(clLoader.getResource(invalidFiles[2]).getFile().replace("%20", " "));
		mpReader.readDominationMapFile(file);
	}

	/**
	 * This method tests countries which are have two continents
	 * 
	 * @throws InvalidMap (Exception)
	 */
	@Test(expected = InvalidMap.class)
	public void testCountriesWithTwoContinents() throws InvalidMap {
		file = new File(clLoader.getResource(invalidFiles[1]).getFile().replace("%20", " "));
		mpReader.readDominationMapFile(file);
	}

	/**
	 * This method tests countries which not have continent.
	 * 
	 * @throws InvalidMap (Exception)
	 */
	@Test(expected = InvalidMap.class)
	public void testCountriesWithoutContinents() throws InvalidMap {
		file = new File(clLoader.getResource(invalidFiles[3]).getFile().replace("%20", " "));
		mpReader.readDominationMapFile(file);
	}

}
