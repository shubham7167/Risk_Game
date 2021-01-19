package com.strategy;

import com.entity.Card;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;
import com.models.CardModel;
import com.models.PlayerModel;
import com.utilities.GameUtilities;
import com.utilities.Logger;

import java.util.*;


/**
 * This class implements Aggressive Strategy methods.
 * 
 * @author Parth
 * @author Mehul
 */
public class Aggressive extends Observable implements Strategy {

    PlayerModel playerModel;
	CardModel cardModel;

	/**
	 * This is the parameterized constructor for Aggressive class
	 */
    public Aggressive() {
        this.playerModel = new PlayerModel();
        this.cardModel = new CardModel();
    }

    @Override
    public boolean reinforcementPhase(Hmap map, Player player, Stack<Card> cardsStack) {

    	int armies = player.getArmies();
		Country countryToReinforce = GameUtilities.getStrongestCountry(player); 	
        		
		if (countryToReinforce != null) {
	        countryToReinforce.setArmy(armies + countryToReinforce.getArmy());
	        player.setArmies(0);   
	        Logger.printAndLogMessage("Aggressive", "Assigned "+ armies + " armies " + "to " + countryToReinforce.getName());
		}
		
		GameUtilities.gamePlayShowmap(map);
		
        return true;
    }

    @Override
    public boolean attackPhase(Hmap map, Player player, Stack<Card> cardsStack) {

		Country attackingCountry = GameUtilities.getStrongestCountry(player);
		List<Country> defendCountries = GameUtilities.getDefendingCountries(attackingCountry);

		for (Country defendCountry: defendCountries) {

			if (playerModel.isAttackPossible(player)) {
			
				Logger.printAndLogMessage("Aggressive", attackingCountry.getName() + "(" + attackingCountry.getPlayer().getName() + ""
						+ ") attacking on " + defendCountry + "(" + defendCountry.getPlayer().getName() + ")");
				
				playerModel.allOutAttackCountry(map, player, attackingCountry.getName(), defendCountry.getName(), cardsStack);
				cardModel.tradeCardsIfPossible(player, cardsStack);
			}
		}

		GameUtilities.gamePlayShowmap(map);
		
        return true;
    }

    @Override
	public boolean fortificationPhase(Hmap map, Player player) {

		Country fortifyCountry = GameUtilities.getStrongestCountry(player);
		
		// Fortify strongest country which is vulnerable to get 
		// attacked by neighbors
		for (Country c: player.getAssignedCountry()) {
			if (fortifyCountry != c) {
				
				if (GameUtilities.isCountryConnected(map, fortifyCountry, c)) {
	
					int moveArmies = c.getArmy() - 1;
					
					Logger.printAndLogMessage("Aggressive", "Fortified country: " + fortifyCountry.getName() + " from "
							+ c.getName() + " with armies: " + moveArmies);
					fortifyCountry.setArmy(fortifyCountry.getArmy() + moveArmies);
					c.setArmy(1);
					
					GameUtilities.gamePlayShowmap(map);
	
					return true;
				}
			}
		}
		
		return true;
    }
}
