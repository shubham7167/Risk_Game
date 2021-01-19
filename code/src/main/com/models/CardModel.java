package com.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

import com.config.CardType;
import com.config.Commands;
import com.controller.GameController;
import com.entity.Card;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;

/**
 * This class is handles the behavior of the card.
 *
 * @author Mehul
 * @author Parth
 */
public class CardModel {

    private List<Card> listOfCards;
    private static int armiesAwarded = 5;

    /**
     * Increments the armies count which are assigned when cards are exchanged.
     */
	public void incrementArmiesAwardedCount() {
		armiesAwarded += 5;
	}
	
	/**
     * Gets the armies awarded count.
     *
     * @return armiesAwarded armies count
     */
	public int getArmiesAwarded() {
		return armiesAwarded;
	}
    
	/**
     * Gets the cards to be exchanged.
     *
     * @return the cardsToBeExchange
     */
    public List<Card> getCardsToBeExchange() {
        return listOfCards;
    }

    /**
     * Sets the cards to be exchanged.
     *
     * @param cardsToBeExchange the cardsToBeExchange to set
     */
    public void setCardsToBeExchange(List<Card> cardsToBeExchange) {
        this.listOfCards = cardsToBeExchange;
    }

    /**
     * Allocate cards to country 
     * @param map current Hashmap object
     * @param stackOfCards stack of cards
     * 
     */
    public void allocateCardsToCountry(Hmap map, Stack<Card> stackOfCards) {

        ArrayList<Country> countryList = map.getCountries();
        ArrayList<CardType> cardList = new ArrayList<>();
        int eachUniqueCards = countryList.size() / 3;
        
        cardList.addAll(Collections.nCopies(eachUniqueCards, CardType.valueOf("CAVALRY")));
        cardList.addAll(Collections.nCopies(eachUniqueCards, CardType.valueOf("ARTILLERY")));
        cardList.addAll(Collections.nCopies(eachUniqueCards, CardType.valueOf("INFANTRY")));

        int left = countryList.size() - cardList.size();

        if (left > 0) {
            for (int i = 0; i < left; i++) {
                cardList.add(CardType.values()[(int) (Math.random() * CardType.values().length)]);
            }
        }

        int i = 0;

        for (Country country : countryList) {
            Card card = new Card(cardList.get(i++));
            card.setCountryToWhichCardBelong(country);
            stackOfCards.push(card);
        }

        Collections.shuffle(stackOfCards);
    }

    /**
     * exchange of cards between players
     * @param cardList list of cards
     * @param player current Player object 
     * @param cardStack current stack of cards
     * 
     */    
    public void exchangeCards(Player player, List<Card> cardList, Stack<Card> cardStack) {

    	Boolean isCardArmiesAssigned = false;
    	
        for (Country c : player.getAssignedCountry()) {
	    	for (Card cardChosen: cardList) {
                if (c.getName().equalsIgnoreCase(cardChosen.getCountryToWhichCardBelong().getName())) {
            	   player.setArmies(player.getArmies() + 2);
                   isCardArmiesAssigned = true;
                   break;
                }
	    	}
	    	
	        if (isCardArmiesAssigned)
            	break;
        }

        player.setArmies(player.getArmies() + getArmiesAwarded());
        incrementArmiesAwardedCount();

        for (Card card : cardList) {
            // Removing the exchanged cards from players hand
            player.getCardList().remove(card);
        	// Adding cards back to deck
            cardStack.push(card);
        }
    }

    /**
     * This method validates card for exchange.
     * 
     * @param cardlist list of cards
     * @return true if cards are valid for exchange, false otherwise
     */
    public boolean isCardsListValidForExchange(List<Card> cardlist) {

        if (cardlist.size() == 3) {
            int infantry = 0, cavalry = 0, artillery = 0;
            
            for (Card card : cardlist) {
                if (card.getCardKind().toString().equals(CardType.CAVALRY.toString())) {
                    infantry++;
                } else if (card.getCardKind().toString().equals(CardType.INFANTRY.toString())) {
                    cavalry++;
                } else if (card.getCardKind().toString().equals(CardType.ARTILLERY.toString())) {
                    artillery++;
                }
            }
            
            // if all are of different kind or all are of same 
            // kind then only, player can exchange cards for army.
            if ((infantry == 1 && cavalry == 1 && artillery == 1) 
            		|| infantry == 3 || cavalry == 3 || artillery == 3) {
                return true;
            }
        }
        
        return false;
    }
    
	/**
	 * This method checks for a combination of different cards.
	 * 
	 * @param cardsOfPlayer This represents the cards owned by the player.
	 * @return Returns the combination of cards selected by the player for trade.
	 */
	public List<Card> getCardCombinations(Player player, List<Card> cardsOfPlayer) {
		
		System.out.println("---- Cards available with " + player + "  -----");
		for (Card card : cardsOfPlayer) {
			System.out.println(card);
		}
		System.out.println("------------------------------------------");

		List<Integer> diffCardsIdx = new ArrayList<>();
		HashMap<String, Integer> cardMap = new HashMap<>();
		
		// Create a hash-map of cards count
		for (int i = 0; i < cardsOfPlayer.size(); i++) {
			
			Card card = cardsOfPlayer.get(i);
			
			if (cardMap.containsKey(card.getCardKind().toString())) {
				cardMap.put(card.getCardKind().toString(), cardMap.get(card.getCardKind().toString()) + 1);
			} else {
				cardMap.put(card.getCardKind().toString(), 1);
				diffCardsIdx.add(i);
			}
		}

		for (Map.Entry<String, Integer> entry : cardMap.entrySet()) {
			
			// 3 same cards
			if (entry.getValue() >= 3) {
				
				List<Card> selectedCards = cardsOfPlayer.stream().filter
						(t -> t.getCardKind().toString().equals(
							entry.getKey())).limit(3).collect(Collectors.toList());
				System.out.println("Cards being exchanged: " + selectedCards);
				return selectedCards;
			}
		}

		// 3 different cards
		if (diffCardsIdx.size() == 3) {
			
			List<Card> selectedCards = new ArrayList<>();
			
			for (int i = 0; i < 3; i++) {
				selectedCards.add(cardsOfPlayer.get(diffCardsIdx.get(i)));
			}
			System.out.println("Cards being exchanged: " + selectedCards);
			
			return selectedCards;
		}
		
		return null;
	}

	/**
	 * 
	 * @param player
	 * @param cardStack
	 */
	public void tradeCardsIfPossible(Player player, Stack<Card> cardStack) {
	
		List<Card> cardsForTrade = null;
		cardsForTrade = getCardCombinations(player, player.getCardList());
		
		if (null != cardsForTrade) {
			
			if (isCardsListValidForExchange(cardsForTrade))
				exchangeCards(player, cardsForTrade, cardStack);
		}
	}
}