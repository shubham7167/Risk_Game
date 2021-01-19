package com.strategy;

import java.util.Stack;

import com.entity.Card;
import com.entity.Hmap;
import com.entity.Player;


/**
 * This is the interface for all strategies.
 * 
 * @author Mehul
 */
public interface Strategy {

	/**
	 * This method is responsible for reinforcement phase.
	 * 
	 * @param Hmap map object
	 * @param player current player
	 * @param cardsStack stack of cards
	 * 
	 * @returns true if reinforcement is successful, false otherwise
	 */
	boolean reinforcementPhase(Hmap map, Player player, Stack<Card> cardsStack);
	
	
	/**
	 * This method implements the attack phase of the strategy.
	 * 
	 * @param Hmap map object
	 * @param player current player
	 * @param cardsStack stack of cards
	 * 
	 * @returns true if attack is successful, false otherwise
	 */
	boolean attackPhase(Hmap map, Player player, Stack<Card> cardsStack);

	
	/**
	 * This method implements the fortification phase of the strategy.
	 * 
	 * @param Hmap map object
	 * @param player current player
	 * 
	 * @return true if attack is successful, false otherwise
	 */
	boolean fortificationPhase(Hmap map, Player player);
}
