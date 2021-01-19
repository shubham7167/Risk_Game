package com.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.config.PlayerStrategy;
import com.entity.Continent;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;


/**
 * This class contains helper functions.
 *
 * @author Mehul
 */
public class GameUtilities {

	  /**
     * This method will check whether country belongs to a player or not.
     *
     * @param map main map
     * @param currentPlayer current player
     * @param country name of to country
     * @return true if country belong to given player
     */
    public static boolean isCountryBelongToPlayer(Hmap map, Player currentPlayer, String country) {

        if (map.getCountryMap().get(country) == null) {
            System.out.println("Error: Given country " + country + " does not exist in map");
            return false;
        }

        if (map.getCountryMap().get(country).getPlayer().getName().equalsIgnoreCase(currentPlayer.getName()))
            return true;

        return false;
    }
    
    /**
     * This method generates random number from 1 to number.
     *
     * @param number number up to which find random numbers to be generated, from 1 to number
     * @return random number from 1 to number, including number
     */
    public static int getRandomNumber(int number) {
        return new Random().nextInt(number) + 1;
    }

    /**
     * This method check adjacent countries
     *
     * @param map         map object
     * @param fromCountry name of from country
     * @param toCountry   name of to country
     * @return true if countries are adjacent, false otherwise
     */
    public static boolean isCountriesAdjacent(Hmap map, String fromCountry, String toCountry) {

        for (String nbrCountry : map.getCountryMap().get(fromCountry).getNeighborCountries()) {
            if (nbrCountry.equalsIgnoreCase(toCountry)) {
                for (String origCountry : map.getCountryMap().get(toCountry).getNeighborCountries()) {
                    if (origCountry.equalsIgnoreCase(fromCountry))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * It shows all countries and continents, armies on each country, ownership, and
     * connectivity
     *
     * @param map main map
     */
    public static void gamePlayShowmap(Hmap map) {

        Logger.printAndLogMessage("GameUtilities", "----------------------------------");

        for (Continent cont : map.getContinents()) {
            for (Country c : cont.getCountries()) {
            	Logger.printAndLogMessage("GameUtilities", c.getBelongToContinent().getName() + ": value: " + cont.getValue() + ": "
                		+ c.getName() + ": Army count: " + c.getArmy() + ", Player: " + c.getPlayer().getName() 
                		+ ", Adjacent Countries: " + c.getAdjacentCountries());
            }
        }

        Logger.printAndLogMessage("GameUtilities", "----------------------------------");
    }
    
    /**
     * Parses the map and gets country list
     *
     * @param map map object
     * @return list for countries from root Map
     */
    public static ArrayList<Country> getCountryListFromMap(Hmap map) {
        ArrayList<Country> countryListfromMap = new ArrayList<Country>();

        for (Continent c : map.getContinents()) {
            for (Country cont : c.getCountries()) {
                countryListfromMap.add(cont);
            }
        }

        return countryListfromMap;
    }
    
    /**
     * This method counts the number of reinforcement armies for the player.
     *
     * @param player current player object
     * @return the number armies player will get in reinforcement
     */
    public static int countReinforcementArmies(Player player) {
        int currentArmies = player.getArmies();
        int countryCount = player.getAssignedCountry().size();

        if (countryCount < 9) {
            currentArmies = currentArmies + 3;
        } else {
            currentArmies += Math.floor(countryCount / 3);
        }

        for (Continent c: getContinentOwnedByPlayer(player)) {
        	
        	if (c.getValue() > 0)
        		currentArmies += c.getValue();
        	else
        		currentArmies += c.getCountries().size();
        }
        
        return currentArmies;
    }

    /**
     * Get list of continents owned by player
     *
     * @param player player object
     * @return continentList continent List owned by player
     */
    public static Set<Continent> getContinentOwnedByPlayer(Player player) {
    	Set<Continent> continentList = new HashSet<Continent>();
        Boolean isAllCountriesOwned;

        for (Country c : player.getAssignedCountry()) {

            isAllCountriesOwned = true;
            Continent continent = c.getBelongToContinent();

            for (Country country : continent.getCountries()) {
                if (!country.getPlayer().getName().equalsIgnoreCase(player.getName()))
                    isAllCountriesOwned = false;
            }

            if (isAllCountriesOwned)
                continentList.add(continent);
        }

        return continentList;
    }
    
    /**
     * This method will modify the ownership of defending country
     *
     * @param defendingCountry name of the defending country
     * @param attackingCountry name of the attacking country
     */
    public static void changeCountryOwnerShip(Country defendingCountry, Country attackingCountry) {

        List<Country> defendersCountries = defendingCountry.getPlayer().getAssignedCountry();

        defendersCountries.remove(defendingCountry);
        defendingCountry.setPlayer(attackingCountry.getPlayer());
        attackingCountry.getPlayer().getAssignedCountry().add(defendingCountry);
    }

    /**
     * This method initialize armies for all the countries
     * @param map main map It will put one army on every country
     */
    public static void intitializeArmiesForAllCountries(Hmap map) {

        for (Continent cont : map.getContinents()) {
            for (Country c : cont.getCountries()) {
                c.setArmy(c.getArmy() + 1);
                c.getPlayer().setArmies(c.getPlayer().getArmies() - 1);
            }
        }
    }
    
    /**
     * This method checks player won the game or not.
     *
     * @param player Player object
     * @param map map object
     * @return true if player won the game, false otherwise
     */
    public static boolean isPlayerWonGame(Player player, Hmap map) {

        if (player.getAssignedCountry().size() == map.getCountries().size())
            return true;

        return false;
    }
    
    /**
     * @param playerStrategy player strategy value
     *
     * @return true when valid strategy entered, false otherwise
     */
    public static boolean isValidStrategy(String playerStrategy) {
    	
    	switch (playerStrategy) {
	    	
			case PlayerStrategy.PLAYER_STRATEGY_HUMAN:
			case PlayerStrategy.PLAYER_STRATEGY_AGGRESSIVE:
			case PlayerStrategy.PLAYER_STRATEGY_BENELOENT:
			case PlayerStrategy.PLAYER_STRATEGY_CHEATER:
			case PlayerStrategy.PLAYER_STRATEGY_RANDOM:
				return true;
				
			default:
				break;
    	}

    	return false;
    }    
     
	/**
	 * This method gives the defending countries for given country.
	 * @param country Selected country
	 * @return List of defending country.
	 */
	 public static List<Country> getDefendingCountries(Country country) {
		List<Country> defCountryList = country.getAdjacentCountries().stream()
				.filter(t -> (country.getPlayer() != t.getPlayer())).collect(Collectors.toList());
		return defCountryList;
	}

	/**
	 * 
	 * @param currentPlayer
	 * @return
	 */
	public static Country getStrongestCountry(Player currentPlayer) {

	  	List<Country> countryList = currentPlayer.getAssignedCountry();
		Collections.sort(countryList);
		Country strongestCountry = countryList.get(0);
			
		for (Country c: countryList) {	
			if (GameUtilities.getDefendingCountries(c).size() > 0) {
				strongestCountry = c;
				break;
			}
		}  

		return strongestCountry;
	}

	/**
	 * 
	 * @param player
	 * @return
	 */
    public static Country getWeakestCountry(Player player) {

	  	List<Country> countryList = player.getAssignedCountry();
		Collections.sort(countryList);
		Collections.reverse(countryList);
		Country weakestCountry = countryList.get(0);
		
		for (Country c: countryList) {	
			List<Country> enemyCountries = GameUtilities.getDefendingCountries(c);		
			
			for (Country ec: enemyCountries) {
				// Enemy country with more than 1 army is likely to attack weaker country
				if (ec.getArmy() > 1) {
					return c;
				}
			}
		}  

		return weakestCountry;
    }

    /**
     *
     * @param map
     * @param sourceCountry
     * @param destCountry
     * @return
     */
    public static boolean isCountryConnected(Hmap map, Country sourceCountry, Country destCountry) {

        // Initialize before BFS
        for (Country con: map.getCountries()) {
            con.setVisited(false);
        }

        LinkedList<Country> queue = new LinkedList<Country>();
        sourceCountry.setVisited(true);
        queue.add(sourceCountry);

        // Run BFS
        while (queue.size() != 0) {
            Country country = queue.poll();
            for (Country nbrCountry: country.getAdjacentCountries()) {
                if (!nbrCountry.isVisited()) {
                    nbrCountry.setVisited(true);
                    queue.add(nbrCountry);
                }
            }
        }

        // Check if destination is connected to source
        if (destCountry.isVisited())
            return true;

        return false;
    }
    
    /**
     * 
     * @param map
     * @param player
     * @param sourceCountry
     * @return
     */
    public static ArrayList<Country> getConnectedCountries(Hmap map, Player player, Country sourceCountry) {
    	ArrayList<Country> connectedCountries = new ArrayList<Country>();
    	
        for (Country nbrCountry: player.getAssignedCountry()) {
        	if (nbrCountry != sourceCountry) {
	     		if (isCountryConnected(map, sourceCountry, nbrCountry))
	        		connectedCountries.add(nbrCountry);
        	}
        }
        	
    	return connectedCountries;
    }
 }
