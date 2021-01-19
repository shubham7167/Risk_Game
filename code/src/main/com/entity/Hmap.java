package com.entity;

import java.io.*;
import java.util.*;
import com.entity.Continent;
import com.entity.Country;
import com.entity.Hmap;

/**
 * This class describes Map and its list of continents
 * @author Mehul
 * @see Country
 * @see Continent
 */
public class Hmap {

	private List<Continent> continents;
	
	private HashMap<String, Continent> continentMap;
	private HashMap<String, Integer> countriesIdxMap;

	private HashMap<String, String> filesAttribute;
	private Map<String, Country> countryMap;
	private ArrayList<Country> countries;

	
	/**
	 * This is the default constructor of Hmap.
	 */
	public Hmap() {
		filesAttribute = new HashMap <String, String>();
		continents = new ArrayList <Continent>();
		countries = new ArrayList <Country>();
		continentMap = new HashMap <String,Continent>();
		countriesIdxMap = new HashMap <String,Integer>();
		countryMap = new TreeMap<String, Country>(String.CASE_INSENSITIVE_ORDER);
	}
	
	/**
	 * This is parameterized constructor for map.
	 * @param newMap The new map object.
	 */
	public Hmap(Hmap newMap) {
		filesAttribute = new HashMap <String, String>(newMap.filesAttribute);
		continents = new ArrayList <Continent>(newMap.continents);
		continentMap = new HashMap <String,Continent>(newMap.continentMap);
		countriesIdxMap = new HashMap <String,Integer>(newMap.countriesIdxMap);
	}
	
	/**
	 * Returns the map data.
	 * @return filesAttribute
	 */
	public HashMap <String, String> getMapData() {
		return filesAttribute;
	}
	
	/**
	 * Setter method for the country hash-map.
	 * @param countryMap hash-map of country
	 */
	public void setCountryMap(Map<String, Country> countryMap) {
		this.countryMap = countryMap;
	}

	/**
	 * Get method for country hash-map.
	 * @return map of countries
	 */
	public Map<String, Country> getCountryMap() {
		return countryMap;
	}
	
	/**
	 * This sets the map data.
	 * @param mapData map data
	 */
	public void setMapData(HashMap <String, String> mapData) {
		this.filesAttribute = mapData;
	}
	
	/**
	 * It returns list of continents.
	 * @return continents
	 */
	public List <Continent> getContinents() {
		return continents;
	}
	
	/**
	 * This sets the continents.
	 * @param continents list of continents
	 */
	public void setContinents(List <Continent> continents) {
		this.continents = continents;
	}

	/**
	 * It returns list of countries.
	 * @return countries
	 */
	public ArrayList <Country> getCountries() {
		return countries;
	}
	
	/**
	 * This sets the countries.
	 * @param countries list of countries
	 */
	public void setCountries(ArrayList <Country> countries) {
		this.countries = countries;
	}
	
	/**
	 * Returns the continent index map
	 * @return countriesIdxMap
	 */
	public HashMap <String, Integer> getCountriesIdxMap() {
		return countriesIdxMap;
	}
	
	/**
	 * This sets the continent index map.
	 * @param countriesIdxMap continent index map
	 */
	public void setCountriesIdxMap(HashMap <String, Integer> countriesIdxMap) {
		this.countriesIdxMap = countriesIdxMap;
	}
	
	/**
	 * Returns the continent maps
	 * @return continentMap
	 */
	public HashMap <String, Continent> getContinentMap() {
		return continentMap;
	}
	
	/**
	 * This sets the continent map.
	 * @param continentMap continent map
	 */
	public void setContinentMap(HashMap <String, Continent> continentMap) {
		this.continentMap = continentMap;
	}

	/**
	 * The update change method is used for observers.
	 */
	public void setChangedForMap() {
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Hmap [ mapData = " + filesAttribute + ", continents = " + continents + ", continentMap = " + continentMap + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		ObjectOutputStream output_obj = null;
		ObjectInputStream input_obj = null;
		Hmap clonedMap = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			output_obj = new ObjectOutputStream(bos);
			
			// serialize and pass the object
			output_obj.writeObject(this);
			output_obj.flush();
			
			ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
			input_obj = new ObjectInputStream(bin);
			clonedMap = (Hmap) input_obj.readObject();
		} catch (Exception e) {
			System.out.println("Exception in ObjectCloner = " + e.toString());
		} finally {
			try {
				output_obj.close();
				input_obj.close();
			} catch (IOException e) {
				// Auto-generated catch block
				System.out.println("Exception: " + e.toString());
			}
		}
		
		return clonedMap;
	}	
}
