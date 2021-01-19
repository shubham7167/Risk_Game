package com.entity;

import java.util.ArrayList;
import java.util.List;

import com.entity.Card;
import com.entity.Country;
import com.entity.Player;
import com.strategy.Strategy;

/**
 * @author Mehul
 * This class defines Player with 
 * its properties like id, name, armies 
 * and number of countries won by player.
 */
public class Player {

	private int id;
	private String name;

	private int armies;
	private int numeberOfCardsExchanged;

	private List <Country> assignedCountry;
	private List <Card> cardList;
	
	private int numOfAttacks;
	private int numOfCountriesWon;
	
	private Strategy strategy;
	private String playerStrategy;

	
	/**
	 * Player parameterized constructor.
	 * @param id of Player
	 * @param name of Player
	 */
	public Player(int id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.assignedCountry = new ArrayList <Country>();
		this.cardList = new ArrayList<>();
		this.numeberOfCardsExchanged = 0;
		this.numOfAttacks = 0;
		this.numOfCountriesWon = 0;
	}
	
	/**
	 * Getter method for the player strategy name.
	 * @return the strategy name
	 */
	public String getPlayerStrategyName() {
		return playerStrategy;
	}

	/**
	 * Setter method for the player strategy name.
	 * @param strategy set strategy
	 */
	public void setPlayerStrategyName(String strategy) {
		this.playerStrategy = strategy;
	}	
	
	/**
	 * Getter method for the player strategy object.
	 * @return the strategy
	 */
	public Strategy getStrategy() {
		return strategy;
	}

	/**
	 * Setter method for the player strategy object.
	 * @param strategy strategy object
	 */
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * get cards of player
	 * @return the cardList
	 */
	public List <Card> getCardList() {
		return cardList;
	}

	/**
	 * set cards of the player
	 * @param card card list
	 */
	public void setCardList(Card card) {
		cardList.add(card);
	}
	
	/**
	 * Get the number of Countries Won.
	 * 
	 * @return number of Countries Won.
	 */
	public int getNumOfCountriesWon() {
		return numOfCountriesWon;
	}
	
	/**
	 * Set number of Conquered Countries.
	 * 
	 * @param numConqueredCountries number of Conquered Countries.
	 */
	public void setNumOfCountriesWon(int numConqueredCountries) {
		this.numOfCountriesWon = numConqueredCountries;
	}
	
	/**
	 * Get the number of attacks.
	 * 
	 * @return number of attacks
	 */
	public int getNumOfAttacks() {
		return numOfAttacks;
	}
	
	/**
	 * Set the number of attacks
	 * 
	 * @param numAttack number of attacks.
	 */
	public void setNumOfAttacks(int numAttack) {
		this.numOfAttacks = numAttack;
	}
	
	/**
	 * Getter method for the player ID.
	 * @return id of Player
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Setter method for the player ID.
	 * @param id of Player
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Getter method for the player name.
	 * @return name of Player
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter method for the player name.
	 * @param name of Player
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter method for the player armies.
	 * @return armies of Player
	 */
	public int getArmies() {
		return armies;
	}
	
	/**
	 * Setter method for the player armies.
	 * @param armies of Player
	 */
	public void setArmies(int armies) {
		this.armies = armies;
	}
	
	/**
	 * Getter method for the assigned country of the player.
	 * @return assignedCountry
	 */
	public List<Country> getAssignedCountry() {
		return assignedCountry;
	}
	
	/**
	 * Setter method to assign country to the player.
	 * @param assignedCountry assign country
	 */
	public void setAssignedCountry(Country assignedCountry) {
		this.assignedCountry.add(assignedCountry);
	}
	
	/**
	 * Getter method for the number of times the cards is exchanged.
	 * @return numeberOfCardsExchanged
	 */
	public int getNumeberOfTimeCardsExchanged() {
		return numeberOfCardsExchanged;
	}

	/**
	 * Setter method for the cards exchanged.
	 * @param numeberOfCardsExchanged number Of Cards Exchanged
	 */
	public void setNumeberOfTimesCardsExchanged(int numeberOfCardsExchanged) {
		this.numeberOfCardsExchanged = numeberOfCardsExchanged;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object input_obj) {

		if (input_obj == this)
			return true;

		if (!(input_obj instanceof Player))
			return false;

		Player player = (Player) input_obj;
		
		return player.getName().equalsIgnoreCase(name);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Player [name = " + name + "(" + playerStrategy + ")" +"]";
	}
}
