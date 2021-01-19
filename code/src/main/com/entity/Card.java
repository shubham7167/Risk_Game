package com.entity;

import com.config.CardType;
import com.entity.Country;

/**
 * This is the main class for the card.
 * @author Mehul
 */
public class Card {

	CardType cardType;
	
	private Country countryToWhichCardBelong;

	/**
	 * This method gets Card Kind.
	 * @return the type of the card
	 */
	public CardType getCardKind() {
		return cardType;
	}

	/**
	 * Parameterized Constructor for Card
	 * @param cardType reference to get cardType enum
	 */
	public Card(CardType cardType){
		this.cardType = cardType;
	}
	
	/**
	 * This method sets Cad Type.
	 * @param cardType sets the kind of card
	 */
	public void setCardKind(CardType cardType) {
		this.cardType = cardType;
	}

	/**
	 * This method gets Country names which card belongs.
	 * @return countryToWhichCardBelong
	 */
	public Country getCountryToWhichCardBelong() {
		return countryToWhichCardBelong;
	}

	/**
	 * This method sets Country names which card belongs.
	 * @param countryToWhichCardBelong country name which card belongs
	 */
	public void setCountryToWhichCardBelong(Country countryToWhichCardBelong) {
		this.countryToWhichCardBelong = countryToWhichCardBelong;
	}

	/*(non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "cardType = " + cardType + ", CountryofCard = " + countryToWhichCardBelong;
	}
}
