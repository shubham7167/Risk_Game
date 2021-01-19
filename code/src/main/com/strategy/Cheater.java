package com.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.entity.Card;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;
import com.models.PlayerModel;
import com.utilities.GameUtilities;


/**
 * This class implements Cheater Strategy methods.
 * 
 * @author Mehul
 */
public class Cheater implements Strategy {

	ArrayList<Player> playerList;

	/**
	 * This is the parameterized constructor for Cheater class
	 * 
	 * @param playerModel player model object
	 */
	public Cheater(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}
	
	@Override
	public boolean reinforcementPhase(Hmap map, Player player, Stack<Card> cardsStack) {

		for (Country c: player.getAssignedCountry()) {
			c.setArmy(c.getArmy() * 2);
			System.out.println(c + ": Armies Doubled to " + c.getArmy());
		}
		
		return true;
	}

	@Override
	public boolean attackPhase(Hmap map, Player player, Stack<Card> cardsStack) {

		List<Country> countriesList = player.getAssignedCountry();
		List<Country> playerCountries = new ArrayList<Country>(countriesList);
		
		for (Country attackingCountry: playerCountries) {
			
			List<Country> defendingCountries = GameUtilities.getDefendingCountries(attackingCountry);
			
			for (Country defendCont: defendingCountries) {
				
				defendCont.setArmy((attackingCountry.getArmy() / 2) + 1);
				attackingCountry.setArmy((attackingCountry.getArmy() / 2) + 1);
				
				System.out.println(attackingCountry.getName() + "(" + attackingCountry.getPlayer().getName() + ""
						+ ") attacking on " + defendCont + "(" + defendCont.getPlayer().getName() + ")");
				
				Player defenderPlayer = defendCont.getPlayer();
				GameUtilities.changeCountryOwnerShip(defendCont, attackingCountry);

				System.out.println(defendCont.getName() + " is conquered by " + 
									attackingCountry.getPlayer());
				
		        // Is game over for defender player?
		        if (defenderPlayer.getAssignedCountry().size() == 0) {
		            System.out.println("----------------------------------");
		            System.out.println("Game over for " + defenderPlayer + " :(");
		            System.out.println("----------------------------------");
		            playerList.remove(defenderPlayer);
		        }
				
				player.setNumOfCountriesWon(player.getNumOfCountriesWon() + 1);
				player.setNumOfAttacks(player.getNumOfAttacks() + 1);
			}
		}

		return true;
	}

	@Override
	public boolean fortificationPhase(Hmap map, Player player) {

		for (Country c: player.getAssignedCountry()) {
			
			List<Country> nbrCounList = GameUtilities.getDefendingCountries(c);

			if (nbrCounList != null && nbrCounList.size() > 0) {
				c.setArmy(c.getArmy() * 2);
				System.out.println("Fortify: Doubled the army on country: " + c.getName() + 
						" (Armies count = " + c.getArmy() + ")");
			}
		}

		return true;
	}
}
