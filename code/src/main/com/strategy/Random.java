package com.strategy;

import com.entity.Card;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;
import com.models.CardModel;
import com.models.PlayerModel;
import com.utilities.GameUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Stack;

/**
 * This class implements Random Strategy methods.
 * 
 * @author Mehul
 */
public class Random extends Observable implements Strategy {

	PlayerModel playerModel;
	CardModel cardModel;
	
	/**
	 * This is the parameterized constructor for RandomS class
	 */
    public Random() {
        this.playerModel = new PlayerModel();
        this.cardModel = new CardModel();
    }

    @Override
    public boolean reinforcementPhase(Hmap map, Player player, Stack<Card> cardsStack) {
    	
    	if (player.getAssignedCountry().size() == 0) {
    		System.out.println(player + " can't do reinforcement.");
    		return true;
    	}
    	
		int randomArmies = GameUtilities.getRandomNumber(player.getArmies());
		int randomIdx = 0;
		
		do {
			randomIdx = GameUtilities.getRandomNumber(player.getAssignedCountry().size()) - 1;
			
			Country randomCountry = player.getAssignedCountry().get(randomIdx);
			randomCountry.setArmy(randomCountry.getArmy() + randomArmies);
			player.setArmies(player.getArmies() - randomArmies);
			
			System.out.println(player + ", " + randomArmies + " assigned to : " + 
					randomCountry.getName());

			if (player.getArmies() == 0)
				break;
			
			randomArmies = GameUtilities.getRandomNumber(player.getArmies());
			
		} while (player.getArmies() > 0);
		
    	return true;
    }

    @Override
    public boolean attackPhase(Hmap map, Player player, Stack<Card> cardsStack) {
  
		// Random number of attacks
    	int randomAttackCount = player.getAssignedCountry().size() / 3;
    	int randomIdx = 0;
    	
    	if (randomAttackCount < 3)
    		randomAttackCount = 3;
    	
    	randomAttackCount = GameUtilities.getRandomNumber(randomAttackCount);
    	
    	for (int i = 0; i < randomAttackCount; i++) {
    		
    		if (!playerModel.isAttackPossible(player)) {
    			System.out.println(player + " : No more attack possible");
    			return true;
    		}
    		
    		randomIdx = GameUtilities.getRandomNumber(player.getAssignedCountry().size()) - 1;
    	
    		// Pick Random attacking Country
    		Country attackingCountry = player.getAssignedCountry().get(randomIdx);
		
    		if (attackingCountry.getArmy() > 1) {

    			List<Country> defendingCountries = GameUtilities.getDefendingCountries(attackingCountry);
    			
    			if (defendingCountries.size() == 0)
    				continue;
    			
    			randomIdx = GameUtilities.getRandomNumber(defendingCountries.size()) - 1;
	    
	    		// Pick Random defending Country
	    		Country defendingCountry = defendingCountries.get(randomIdx);
	    		
	    		System.out.println(attackingCountry.getName() + "(" + attackingCountry.getPlayer().getName() + ""
						+ ") attacking on " + defendingCountry + "(" + defendingCountry.getPlayer().getName() + ")");
				
				playerModel.allOutAttackCountry(map, player, attackingCountry.getName(), defendingCountry.getName(), cardsStack);
				cardModel.tradeCardsIfPossible(player, cardsStack);
    		}
    	}

        return true;
    }

    @Override
    public boolean fortificationPhase(Hmap map, Player player) {
    	
    	if (player.getAssignedCountry().size() == 0) {
    		System.out.println(player + " can't do fortify.");
    		return true;
    	}
    	
    	int randomIdx = GameUtilities.getRandomNumber(player.getAssignedCountry().size()) - 1;
    	Country randomFromCountry = player.getAssignedCountry().get(randomIdx);
		
    	if (randomFromCountry.getArmy() > 1) { 
    		
    		ArrayList<Country> connectedCountryList = GameUtilities.getConnectedCountries(map, player, randomFromCountry);
    		
    		randomIdx = GameUtilities.getRandomNumber(connectedCountryList.size()) - 1;
    		Country randomToCountry = connectedCountryList.get(randomIdx);
    		
    		int fortifyArmies = GameUtilities.getRandomNumber(randomFromCountry.getArmy() - 1);
    		
    		playerModel.fortifyCurrentPlayer(map, player, randomFromCountry.getName(),
    				randomToCountry.getName(), fortifyArmies);
    		
    		System.out.println("Fortified country: " + randomFromCountry.getName() + " from "
					+ randomToCountry.getName() + " with armies: " + fortifyArmies);
    	} else {
    		System.out.println(player + " has chosen to skip fortify.");
    	}
    	
        return true;
    }
}
