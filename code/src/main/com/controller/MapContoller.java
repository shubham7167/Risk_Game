package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.entity.Continent;
import com.entity.Country;
import com.entity.Hmap;
import com.exception.InvalidMap;


/**
 * This Class for checking Map Commands
 * @author Mehul
 * @author Komal
 */
public class MapContoller {
	
	private static int countryIdx = 1;
	
	/**
	 * Removes continent from the map.
	 * @param map Current map object.
	 * @param continentName Name of the continent.
	 * @return true if continent got removed successfully, otherwise false
	 */
	public static boolean removeContinent(Hmap map, String continentName) {
		
		Continent continent = new Continent();
		continent.setName(continentName);
	
		for (Continent c: map.getContinents()) {
			if (c.getName().equalsIgnoreCase(continentName)) {
				map.getContinents().remove(c);
				map.getContinentMap().remove(continentName);
				
				System.out.println("Successfully removed continent: " + continentName + " from map");

				return true;
			}
		}
		
		System.out.println("Continent: " + continentName + " does not exist in map");
		
		return false;
	}
	
	/**
	 * Adds continent to the map with details like control value etc.
	 * @param map Current map object.
	 * @param name Name of the continent.
	 * @param ctrlValue Control value of the continent.
	 * @param color Color of the continent.
	 * @return true if continent gets added to map, false otherwise.
	 */
	public static boolean addContinent(Hmap map, String name, String ctrlValue, String color) {
		Continent continent = new Continent();
		
		continent.setName(name);
		
		try {
			continent.setValue(Integer.parseInt(ctrlValue));
		} catch (Exception e) {
			System.out.println("Exception: " + e.toString());
			return false;
		}
		
		continent.setColor(color);
		
		if (map.getContinents().contains(continent)) {
			System.out.println("The Continent with name " + name + " already exist.");
			return false;
		}
		
		System.out.println("The continent: " + name + " added successfully");
		map.getContinents().add(continent);
		map.getContinentMap().put(name, continent);
		
		return true;
	}
	
	/**
	 * This method checks whether the continent name is present or not.
	 * @param listContinents list of all continents
	 * @param name name of the continents to be updated
	 * @return true if list does not contain other continents with same name
	 */
	public static boolean containsContinentName(final List<Continent> listContinents, final String name){
	    return listContinents.stream().filter(x -> x.getName().equals(name)).findFirst().isPresent();
	}
	
	/**
	 * This method updates the continent details if the user selects the continent.
	 * @param continent The continent whose details must be updated.
	 * @param map map object {@link Hmap}
	 * @param name name of the continent to be updated
	 * @param ctrlValue The control value of the continent.
	 * @return The current continent.
	 * @throws InvalidMap InvalidMapException if any error occurs
	 */
	public static Continent updateContinent(Continent continent, Hmap map, String name, String ctrlValue) throws InvalidMap {
		
		if (!continent.getName().equals(name)) {
			
			if (containsContinentName(map.getContinents(), name)) {
				throw new InvalidMap("The Continent with name " + name + " already exist.");
			}
			continent.setName(name);
		}
		
		continent.setValue(Integer.parseInt(ctrlValue));
		return continent;
	}
	
	/**
	 * This checks whether the Country name is there or not.
	 * @param list list of all Countries
	 * @param name name of the Country to be checked
	 * @return true if list does not contain other Country with same name
	 */
	public static boolean containsCountryName(final ArrayList<Country> list, final String name){
	    return list.stream().filter(z -> z.getName().equals(name)).findFirst().isPresent();
	}
	
	/**
	 * Removes country from the map.
	 * @param map Current map object.
	 * @param name Name of the Country.
	 * @return true if Country gets removed from map, false otherwise.
	 */
	public static boolean removeCountry(Hmap map, String name) {
	
		for (Continent continent: map.getContinents()) {
			for (Country country: continent.getCountries()) {
				if (country.getName().equalsIgnoreCase(name)) {
					
					continent.getCountries().remove(country);
					continent.getCountryMap().remove(name);
					map.getCountriesIdxMap().remove(name);
					
					for (Country adjCountry: country.getAdjacentCountries())
						adjCountry.getAdjacentCountries().remove(country);
					
					System.out.println("Successfully removed country: " + name + " from map");

					return true;
				}
			}
		}
		
		System.out.println("Country: " + name + " does not belong to any continent");
		
		return false;
	}
	
	/**
	 * Adds country to the map and the continent with its respective details.
	 * @param map Current map object.
	 * @param name Name of the Country.
	 * @param continentName Continent to which the country belongs to.
	 * @return true if Country gets added to map, false otherwise.
	 */
	public static boolean addCountry(Hmap map, String name, String continentName) {
		
		Country country = new Country();
		Continent belongToContinent = null;
		
		country.setName(name);
		
		// check if country with same name exist or not
		for (Continent c : map.getContinents()) {
			
			if (c.getCountries().contains(country)) {
				System.out.println("Country with same name: " + name + 
						" already exist in continent: " + c.getName());
				return false;
			}
			
			if (c.getName().equalsIgnoreCase(continentName))
				belongToContinent = c;
		}	
		
		if (null == belongToContinent) {
			System.out.println("Belong to continent: " + continentName + " does not exist in map");
			return false;
		}

		country.setBelongToContinent(belongToContinent);

		map.getCountriesIdxMap().put(name, countryIdx++);
		belongToContinent.getCountries().add(country);
		belongToContinent.getCountryMap().put(name, country);
		
		System.out.println("Country: " + name + " added to the continent: " 
		+ continentName + " successfully");
		
		return true;
	}
	
	/**
	 * This method updates the continent details when the user selects the country.
	 * @param country The country whose values must be updated.
	 * @param map Map Object {@link Hmap}
	 * @param name name for the Country to be updated - new name for the Country
	 * @param xCo X-Co-ordinate of the Country.
	 * @param yCo Y-Co-ordinate of the Country.
	 * @param adjCoun The adjacent Countries list. 
	 * @return The object to the newly updated Country.
	 * @throws InvalidMap if any error occurs
	 */
	public static Country updateCountry(Country country, Hmap map, String name,String xCo, String yCo, 
			Country adjCoun) throws InvalidMap {
		
		country.setxCoordinate(Integer.parseInt(xCo));
		country.setyCoordinate(Integer.parseInt(yCo));
		
		if (!country.getName().equals(name)) {
			
			ArrayList<Country> listAllCoun = new ArrayList<Country>();
			
			for (Continent cont : map.getContinents()) {
				listAllCoun.addAll(cont.getCountries());
			}
			
			if (containsCountryName(listAllCoun, name)) {
				throw new InvalidMap("The Country with name "+name+" already exist.");
			}
			country.setName(name);
		}
		
		if (adjCoun != null) {
			
			if (!adjCoun.getAdjacentCountries().contains(country)) {
				adjCoun.getAdjacentCountries().add(country);
			}
			
			if (!country.getAdjacentCountries().contains(adjCoun)) {
				country.getAdjacentCountries().add(adjCoun);
			}
		}
		
		return country;
	}

	/**
	 * This method removes the country to the corresponding continent.
	 * @param map Current map object.
	 * @param countryName Name of the Country.
	 * @param nbrCountryName Name of the neighbor Country.
	 * @return true if neighbor Country gets removed from map, false otherwise.
	 */
	public static boolean removeNeighborCountry(Hmap map, String countryName, String nbrCountryName) {
		
		boolean isCountryDel= false, isNeigborDel = false;
		
		if (!map.getCountriesIdxMap().containsKey(countryName)) {
			System.out.println("Exception: The country: " + countryName + " does not exist in map");
			return false;
		}
		
		if (!map.getCountriesIdxMap().containsKey(nbrCountryName)) {
			System.out.println("Exception: The neighbor country: " + nbrCountryName + " does not exist in map");
			return false;
		}
		
		for (Continent c: map.getContinents()) {
			for (Country country: c.getCountries()) {
				if (country.getName().equalsIgnoreCase(countryName)) {
					
					country.getAdjacentCountries().remove(c.getCountryMap().get(nbrCountryName));
					country.getNeighborCountries().remove(nbrCountryName);
					
					System.out.println("The neighbor country: " + nbrCountryName + 
							" removed from adjacent country: " + countryName);

					isCountryDel = true;
				}
				
				if (country.getName().equalsIgnoreCase(nbrCountryName)) {
					country.getAdjacentCountries().remove(c.getCountryMap().get(countryName));
					country.getNeighborCountries().remove(countryName);
					isNeigborDel  = true;
				}
			}
		}
		
		if (isCountryDel && isNeigborDel)
			return true;
		
		return false;
	}
	
	/**
	 * This method adds the country to the corresponding continent.
	 * @param map Current map object.
	 * @param countryName Name of the Country.
	 * @param nbrCountryName Name of the neighbor Country.
	 * @return true if neighbor Country gets added to map, false otherwise.
	 */
	public static boolean addNeighborCountry(Hmap map, String countryName, String nbrCountryName) {
		
		boolean isCountryAdded = false, isNeigborAdded = false;
		
		if (!map.getCountriesIdxMap().containsKey(countryName)) {
			System.out.println("Exception: The country: " + countryName + " does not exist in map");
			return false;
		}
		
		if (!map.getCountriesIdxMap().containsKey(nbrCountryName)) {
			System.out.println("Exception: The neighbor country: " + nbrCountryName + " does not exist in map");
			return false;
		}
		
		for (Continent c: map.getContinents()) {
			for (Country country: c.getCountries()) {
				if (country.getName().equalsIgnoreCase(countryName)) {
		
					if (country.getNeighborCountries().contains(nbrCountryName)) {
						System.out.println("Exception: The neighbor country already exist");
						return false;
					}
					
					for (Continent c1: map.getContinents()) {
						for (Country country1: c1.getCountries()) {
							if (country1.getName().equalsIgnoreCase(nbrCountryName))
								country.getAdjacentCountries().add(country1);
						}
					}
					
					country.getNeighborCountries().add(nbrCountryName);
					
					System.out.println("The neighbor country: " + nbrCountryName + 
							" added as an adjacent country to: " + countryName);

					isCountryAdded = true;
				}
				
				if (country.getName().equalsIgnoreCase(nbrCountryName)) {
					
					if (country.getNeighborCountries().contains(countryName)) {
						System.out.println("Exception: The neighbor country already exist");
						return false;
					}
	
					for (Continent c1: map.getContinents()) {
						for (Country country1: c1.getCountries()) {
							if (country1.getName().equalsIgnoreCase(countryName))
								country.getAdjacentCountries().add(country1);
						}
					}
					country.getNeighborCountries().add(countryName);
					isNeigborAdded = true;
				}
			}
		}

		if (isCountryAdded && isNeigborAdded)
			return true;
		
		System.out.println("Failed to add the neighbor country: " + nbrCountryName);

		return false;
	}
	
	/**
	 * This method adds the country to the corresponding continent.
	 * @param continent continent object which will be assigned Countries
	 * @param country The country which is added to the continent.
	 * @return the Object to the newly updated continent.
	 */
	public static Continent mapCountryToContinent(Continent continent, Country country) {
		
		try {
			continent.getCountries().add(country);
		} catch(Exception e) {
			ArrayList<Country> list = new ArrayList<>();
			list.add(country);
			continent.setCountries(list);
		}
		
		return continent;
	}
	
	/**
	 * This method shows the map.
	 * @param map current map object
	 */
	public static void mapEditorShowmap(Hmap map) {
		
		for (Continent c : map.getContinents()) {
			System.out.println("--------------------------------");
			System.out.println(c);

			for (Country con : c.getCountries()) {
				System.out.println(con.getName() + ": " + con.getNeighborCountries());
			}
		}
		System.out.println("--------------------------------");
	}
}
