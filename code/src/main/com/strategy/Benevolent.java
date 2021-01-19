package com.strategy;

import com.entity.Card;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;
import com.utilities.GameUtilities;
import com.utilities.Logger;

import java.util.Collections;
import java.util.Stack;


/**
 * This class implements Benevolent Strategy methods.
 * 
 * @author Parth
 * @author Mehul
 */
public class Benevolent implements Strategy {

	@Override
	public boolean reinforcementPhase(Hmap map, Player player, Stack<Card> cardsStack) {

		int armies = player.getArmies();
		Country countryToReinforce = GameUtilities.getWeakestCountry(player);
		
		if (countryToReinforce != null) {
			countryToReinforce.setArmy(armies + countryToReinforce.getArmy());
	        player.setArmies(0);
	        Logger.printAndLogMessage("Benevolent", "Assigned "+ armies + 
	        		" armies " + "to " + countryToReinforce.getName());
		}
		
		return true;
	}

	@Override
	public boolean attackPhase(Hmap map, Player player, Stack<Card> cardsStack) {

		GameUtilities.gamePlayShowmap(map);
		Logger.printAndLogMessage("Benevolent", player + " won't attack");
		return true;
	}

	@Override
	public boolean fortificationPhase(Hmap map, Player player) {

		Country countryToProtect = GameUtilities.getWeakestCountry(player);

		// Find strongest connected country and fortify armies from it
		// to weakest country
		Collections.sort(player.getAssignedCountry());
		
		for (Country c: player.getAssignedCountry()) {
			if (countryToProtect != c) {
				
				if (GameUtilities.isCountryConnected(map, countryToProtect, c)) {
	
					int moveArmies = c.getArmy() - 1;
					
					Logger.printAndLogMessage("Benevolent", "Fortified country: " + countryToProtect.getName() + " from "
							+ c.getName() + " with armies: " + moveArmies);
					countryToProtect.setArmy(countryToProtect.getArmy() + moveArmies);
					c.setArmy(1);
					
					GameUtilities.gamePlayShowmap(map);
	
					return true;
				}
			}
		}
		
		return true;
	}
}
