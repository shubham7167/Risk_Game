package com.mapparser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import com.entity.Continent;
import com.entity.Country;
import com.entity.Hmap;
import com.exception.InvalidMap;


/**
 * This class is responsible to write the map file when user creates the map.
 * 
 * @author Komal
 * @author Mehul 
 */
public class MapWriter {

	/**
	 * This method writes the map details to the map file.
	 * 
	 * @param map object of the map which is being processed
	 * @param file file path
	 */
	public void writeDominationMapFile(Hmap map, File file) {

		FileWriter fileWriter;

		try {
			if (map == null) {
				System.out.println("Map Object is NULL!");
				return;
			}

			String content = parseDominationHmapAndGetString(map);
			fileWriter = new FileWriter(file, false);
			fileWriter.write(content);
			fileWriter.close();

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * This method processes the map by calling three different methods and makes a
	 * string to be written in the map file.
	 * 
	 * @param map object of the map which is being processed
	 * @return String to be written in the map file
	 */
	private String parseDominationHmapAndGetString(Hmap map) {
		StringBuilder mapAttrContent = new StringBuilder();
		mapAttrContent = parseDominationMapAttribute(map);
		mapAttrContent.append(parseDominationContinent(map));
		mapAttrContent.append(parseDominationCountries(map));
		mapAttrContent.append(parseDominationBorders(map));
		return mapAttrContent.toString();
	}

	/**
	 * This method process the map attributes.
	 * 
	 * @param map object of the map which is being processed
	 * @return a String that contains the map properties.
	 */
	private StringBuilder parseDominationMapAttribute(Hmap map) {
		StringBuilder mapAttribute = new StringBuilder();
		mapAttribute.append("[files]");
		mapAttribute.append("\n");

		for (Entry<String, String> keymap : map.getMapData().entrySet()) {
			mapAttribute.append(keymap.getKey() + " " + keymap.getValue());
			mapAttribute.append("\n");
		}

		return mapAttribute;
	}

	/**
	 * This method processes the continents.
	 * 
	 * @param map object of the map which is being processed
	 * @return a string that contains details of the continents that will eventually
	 *         be written in the map file.
	 */
	private StringBuilder parseDominationContinent(Hmap map) {
		StringBuilder continentData = new StringBuilder();
		continentData.append("\n");
		continentData.append("[continents]");
		continentData.append("\n");

		for (Continent continent : map.getContinents()) {

			String color = continent.getColor();

			if (null != color)
				continentData.append(continent.getName() + " " + continent.getValue() + " " + continent.getColor());
			else
				continentData.append(continent.getName() + " " + continent.getValue());

			continentData.append("\n");
		}

		return continentData;
	}

	/**
	 * This method processes the countries.
	 * 
	 * @param map object of the map that is being processed
	 * @return a string that contains details of the countries that will eventually
	 *         be written in the map file.
	 */
	private StringBuilder parseDominationCountries(Hmap map) {
		StringBuilder countryData = new StringBuilder();
		countryData.append("\n");
		countryData.append("[countries]");
		countryData.append("\n");

		for (Continent continent : map.getContinents()) {
			List<Country> countriesList = continent.getCountries();
			if (countriesList != null) {
				for (Country country : countriesList) {
					int continentIdx = map.getContinents().indexOf(country.getBelongToContinent()) + 1;

					countryData.append(map.getCountriesIdxMap().get(country.getName()) + " " + country.getName() + " "
							+ continentIdx + " " + country.getxCoordinate() + " " + country.getyCoordinate());
					countryData.append("\n");
				}
			}
		}

		return countryData;
	}

	/**
	 * This method processes the borders.
	 * 
	 * @param map object of the map that is being processed
	 * @return a string that contains details of the countries that will eventually
	 *         be written in the map file.
	 */
	private StringBuilder parseDominationBorders(Hmap map) {
		StringBuilder borderData = new StringBuilder();
		borderData.append("\n");
		borderData.append("[borders]");
		borderData.append("\n");

		for (Continent continent : map.getContinents()) {

			List<Country> countriesList = continent.getCountries();
			if (countriesList != null) {
				for (Country country : countriesList) {
					borderData.append(map.getCountriesIdxMap().get(country.getName()));

					for (Country adjacentCountries : country.getAdjacentCountries()) {
						borderData.append(" ");
						borderData.append(map.getCountriesIdxMap().get(adjacentCountries.getName()));
					}
					borderData.append("\n");
				}
			}
		}

		return borderData;
	}

	/**
	 * This method writes the map details to the map file.
	 * 
	 * @param map object of the map which is being processed
	 * @param file file path
	 */
	public void writeConquestMapFile(Hmap map, File file) {

		FileWriter fileWriter;

		try {
			if (map == null) {
				System.out.println("Map Object is NULL!");
				return;
			}

			String content = parseConquestMapAndReturnString(map);
			fileWriter = new FileWriter(file, false);
			fileWriter.write(content);
			fileWriter.close();

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * This method processes the map by calling three different methods and makes a
	 * string to be written in the map file.
	 * 
	 * @param map object of the map which is being processed
	 * @return String to be written in the map file
	 */
	private String parseConquestMapAndReturnString(Hmap map) {
		StringBuilder content = new StringBuilder();
		content = parseConquestMapAttribute(map);
		content.append(parseConquestContinent(map));
		content.append(parseConquestCountries(map));
		return content.toString();
	}

	/**
	 * This method process the map attributes.
	 * 
	 * @param map object of the map which is being processed
	 * @return a String that contains the map properties.
	 */
	private StringBuilder parseConquestMapAttribute(Hmap map) {
		StringBuilder mapAttribute = new StringBuilder();
		mapAttribute.append("[Map]");
		mapAttribute.append("\n");

		for (Entry<String, String> keymap : map.getMapData().entrySet()) {
			mapAttribute.append(keymap.getKey() + " " + keymap.getValue());
			mapAttribute.append("\n");
		}

		return mapAttribute;
	}

	/**
	 * This method processes the continents.
	 * 
	 * @param map object of the map which is being processed
	 * @return a string that contains details of the continents that will eventually
	 *         be written in the map file.
	 */
	private StringBuilder parseConquestContinent(Hmap map) {
		StringBuilder continentData = new StringBuilder();
		continentData.append("\n");
		continentData.append("[Continents]");
		continentData.append("\n");

		for (Continent continent : map.getContinents()) {

			continentData.append(continent.getName() + "=" + continent.getValue());
			continentData.append("\n");
		}

		return continentData;
	}

	/**
	 * This method processes the countries.
	 * 
	 * @param map object of the map that is being processed
	 * @return a string that contains details of the countries that will eventually
	 *         be written in the map file.
	 */
	private StringBuilder parseConquestCountries(Hmap map) {
		StringBuilder countryData = new StringBuilder();

		countryData.append("\n");
		countryData.append("[Territories]");
		countryData.append("\n");

		for (Continent continent : map.getContinents()) {
			List<Country> countriesList = continent.getCountries();
			if (countriesList != null) {
				for (Country country : countriesList) {

					countryData.append(country.getName() + "," + country.getxCoordinate() + ","
							+ country.getyCoordinate() + "," + country.getBelongToContinent().getName());
					for (Country adjacentCountries : country.getAdjacentCountries()) {
						countryData.append(",");
						countryData.append(adjacentCountries.getName());
					}
					countryData.append("\n");
				}
				countryData.append("\n");
			}
		}
		return countryData;
	}
}
