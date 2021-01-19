package com.mapparser;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.entity.Continent;
import com.entity.Country;
import com.entity.Hmap;
import com.exception.InvalidMap;
import com.mapparser.MapVerifier;

/**
 * This is the test class for MapVerifier. {@link MapVerifier}
 * 
 * @author Mahmoudreza
 * @author Maryam
 * @author Komal
 * @version 0.0.1
 */
public class MapVerifierTest {
	
	static Continent continent1;
	static Continent continent2;
	static Country country1;
	static Country country2;
	static Country country3;	
	static Hmap map;
	
	MapVerifier mapverifier;
	ClassLoader loader;

	String mapAuthor = "Maryam";
	String mapImage = "world.map";
	String mapWrap = "no";
	String mapScroll = "horizontal";
	String mapWarn = "yes";

	String conti1 = "North-America";
	int controlValue1 = 10;
	String conti2 = "Asia";
	int controlValue2 = 11;
	String count1 = "Quebec";
	String count2 = "Ontario";
	String count3 = "India";
	int x1 = 1;
	int y1 = 1;
	int x2 = 2;
	int y2 = 2;
	int x3 = 1;
	int y3 = 2;
	
	static HashMap<String, String> mapData = new HashMap<>();
	List<Continent> continentList;

	/**
	 * This method executed before all the methods of the class.
	 */
	@BeforeClass
	public static void beforeClass() {
		System.out.println("This is for testing MapVerifier Class");
	}

	/**
	 * This method is executed before every method of the class.
	 */
	@Before
	public void beforeTest() {
		continent1 = new Continent();
		country1 = new Country();
		
		map = new Hmap();
		map.setMapData(mapData);
		continent1.setName(conti1);
		continent1.setValue(controlValue1);
		country1.setName(count1);
		country1.setxCoordinate(x1);
		country1.setyCoordinate(y1);
		continentList = new ArrayList<>();
		continentList.add(continent1);
	}

	/**
	 * This method runs After All Testing
	 */
	@AfterClass
	public static void afterClassTests() {
		System.out.println("The map verify test is done");
	}

	/**
	 * This method tests that map is null or not.
	 * @throws InvalidMap Invalid Map
	 */
	@Test(expected = InvalidMap.class)
	public void verifyNullMapTest() throws InvalidMap {
		mapverifier.verifyMap(null);
		System.out.println("This is a test for verifyNullMap");
	}

	/**
	 * This method verifies that map has at least one continent.
	 * @throws InvalidMap Invalid Map
	 */
	@Test(expected = InvalidMap.class)
	public void verifyMap() throws InvalidMap {
		mapverifier.verifyMap(new Hmap());
		System.out.println("The Unit Test for verifying Map is performed");
	}

	/**
	 * This method is used to verify that continent is null or not.
	 * @throws InvalidMap Invalid Map
	 */
	@Test(expected = InvalidMap.class)
	public void verifyContinentsTest() throws InvalidMap {
		map.setContinents(continentList);
		mapverifier.verifyContinents(map);
		System.out.println("The Unit Test for verifying Continents is performed");
	}

	/**
	 * This method is used to test if a continent is a sub-graph or not.
	 * @throws InvalidMap Invalid Map
	 */
	@Test
	public void isMapNotConnectedGraphTest() throws InvalidMap {
		assertFalse(mapverifier.isMapConnectedGraph(map));
		System.out.println("This Unit Test for Map_Connected_Graph is performed");
	}

	/**
	 * This method is used to test the continent is connected to graph or not.
	 */
	@Test
	public void isContinentConnectedGraphTest() {

		System.out.println("This is a test for Continent Connected Graph");
		List<Country> countryList = new ArrayList<>();
		
		countryList.add(country1);
		
		country2 = new Country();
		country2.setName(count2);
		countryList.add(country2);
		
		country2.getAdjacentCountries().add(country1);
		country1.getAdjacentCountries().add(country2);
		
		continent1.setCountries(countryList);
		assertEquals(true, MapVerifier.isContinentConnectedGraph(continent1, map));
		System.out.println("The Unit Test for Continent_Connected_Graph is performed");
	}
	
	/**
	 * This method is used to test the continent is connected to graph or not.
	 */
	@Test
	public void isContinentNotConnectedGraphTest() {

		System.out.println("This is a test for Continent not Connected Graph");
		List<Country> countryList = new ArrayList<>();
		countryList.add(country1);
		country2 = new Country();
		country2.setName(count2);
		country2.setxCoordinate(x2);
		country2.setyCoordinate(y2);
		countryList.add(country2);
		country3 = new Country();
		country3.setName(count3);
		country3.setxCoordinate(x3);
		country3.setyCoordinate(y3);
		countryList.add(country3);
		country2.getAdjacentCountries().add(country1);
		country1.getAdjacentCountries().add(country2);
		continent1.setCountries(countryList);
		assertNotEquals(true, MapVerifier.isContinentConnectedGraph(continent1, map));
		System.out.println("The Unit Test for Continent_Not_Connected_Graph is performed");
	}
	
	/**
	 * This method is used to test the map is connected to graph or not.
	 */
	@Test
	public void isMapConnectedGraphTest() {

		System.out.println("This is a test for Continent not Connected Graph");
		List<Country> countryList = new ArrayList<>();
		countryList.add(country1);
		country2 = new Country();
		country2.setName(count2);
		country2.setxCoordinate(x2);
		country2.setyCoordinate(y2);
		countryList.add(country2);
		country2.getAdjacentCountries().add(country1);
		country1.getAdjacentCountries().add(country2);
		continent1.setCountries(countryList);
		country3 = new Country();
		country3.setName(count3);
		country3.setxCoordinate(4);
		country3.setyCoordinate(6);
		List<Country> countryList1 = new ArrayList<>();
		countryList1.add(country3);
		continent2 = new Continent();
		continent2.setName(conti2);
		continent2.setValue(11);
		continentList.add(continent2);
		continent2.setCountries(countryList1);
		country3.getAdjacentCountries().add(country1);
		country1.getAdjacentCountries().add(country3);
		country3.getAdjacentCountries().add(country2);
		country2.getAdjacentCountries().add(country3);
		map.setContinents(continentList);
		assertEquals(MapVerifier.isMapConnectedGraph(map), true);
		System.out.println("The Unit Test for Map_Connected_Graph is performed");
	}
}
