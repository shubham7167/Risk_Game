package com.strategy;

import java.util.Observable;
import java.util.Scanner;
import java.util.Stack;

import com.config.Commands;
import com.entity.Card;
import com.entity.Hmap;
import com.entity.Player;
import com.maingame.CardExchangeView;
import com.models.CardModel;
import com.models.PlayerModel;
import com.utilities.GameUtilities;

/**
 * This class implements Human Strategy methods.
 * 
 * @author Mehul
 */
public class Human extends Observable implements Strategy {

	boolean isShowMapCommand = false;
	PlayerModel playerModel;
	Player currentPlayer;
	CardModel cardModel;
	Stack<Card> cardsStack;

	/**
	 * This is the parameterized constructor for Human class
	 */
	public Human() {
		this.addObserver(new CardExchangeView());
		this.playerModel = new PlayerModel();
		this.cardsStack = new Stack<Card>();
		this.cardModel = new CardModel();
	}
	
	/**
	 * Get the current player.
	 * 
	 * @return currentPlayer playing
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Get the Card model.
	 * 
	 * @return card model object
	 */
	public CardModel getCardModel() {
		return cardModel;
	}
	
	/**
	 * This method is to set the current player.
	 * 
	 * @param player Current player.
	 */
	public void setCurrentPlayer(Player player) {
		this.currentPlayer = player;
	}
	
	/**
	 * Set the stack of cards.
	 * 
	 * @param stackOfCards stack of cards.
	 */
	public void setCardsStack(Stack<Card> stackOfCards) {
		this.cardsStack = stackOfCards;
	}
	
	/**
	 * Get the stack of cards.
	 * 
	 * @return stack of cards
	 */
	public Stack<Card> getCardsStack() {
		return cardsStack;
	}
	
	@Override
	public boolean reinforcementPhase(Hmap map, Player player, Stack<Card> cardsStack) {

		setCurrentPlayer(player);
		setCardsStack(cardsStack);
		
		if (!isShowMapCommand) {
			// Card exchange view
			setChanged();
			notifyObservers("card-exchange");
		} 
		
		System.out.println("Current game phase: Gameplay reinforcement phase (reinforce countryname num, showmap)");
		System.out.println("Current Player: " + player.getName() + 
				" (" + player.getPlayerStrategyName() + ")"  
				+ ", Armies left for reinforcement = " + player.getArmies());
		
		Scanner sc = new Scanner(System.in);
		String command = sc.nextLine();
		String[] words = command.split(" ");
		String commandType = words[0];

		switch (commandType) {

		case Commands.MAP_COMMAND_SKIP:
			// changeCurrentPlayer();
			break;

		case Commands.MAP_COMMAND_SHOWMAP:
			isShowMapCommand = true;
			GameUtilities.gamePlayShowmap(map);
			break;

		case Commands.MAP_COMMAND_REINFORCE:

			isShowMapCommand = false;
			if (words.length < 3) {
				System.out.println("Error: Invalid command, Try again !!!");
				break;
			}

			String countryName = words[1];
			int numberOfArmies = 0;

			try {
				numberOfArmies = Integer.parseInt(words[2]);
			} catch (Exception e) {
				System.out.println("Exception: " + e.toString());
				return false;
			}

			if (numberOfArmies <= 0) {
				System.out.println("Error: You should enter more than 0 armies to reinforce.");
				return false;
			}

			if (!GameUtilities.isCountryBelongToPlayer(map, player, countryName)) {
				System.out.println("Error: Given country " + countryName + " does not belong to " + player);
				return false;
			}

			if (playerModel.reinforceArmiesForCurrentPlayer(player, countryName, numberOfArmies))
				return true;
			break;
			
		default:
			isShowMapCommand = false;
			System.out.println("Error: Invalid command, Try again !!!");
			break;
		}

		return false;
	}

	@Override
	public boolean attackPhase(Hmap map, Player player, Stack<Card> cardsStack) {
		
		Scanner sc = new Scanner(System.in);
		String command = sc.nextLine();
		String words[] = command.split(" ");

		switch (words[0]) {

		case Commands.MAP_COMMMAND_ATTACK:

			// Player may decide to attack or not to attack again. 
			// If attack not possible, attack automatically ends.
			if (words.length < 2) {
				System.out.println("Error: Invalid command, Try again !!!");
				return false;
			}
			
			if (words[1].equalsIgnoreCase(Commands.MAP_COMMAND_ATTACK_OPTION_NOATTACK)) {
				System.out.println(player + " has chosen not to attack");
				return true;
			}
			
			if (words.length < 4) {
				System.out.println("Error: Invalid command, Try again !!!");
				return false;
			}
							
			// Attack with allout mode
			if (words[3].equalsIgnoreCase(Commands.MAP_COMMAND_ATTACK_OPTION_ALLOUT)) {

				String attackingCountry = words[1];
				String defendingCountry = words[2];
								
				playerModel.allOutAttackCountry(map, player, 
						attackingCountry, defendingCountry, cardsStack);
			} else {

				int numOfDice = 0;
				String attackingCountry = words[1];
				String defendingCountry = words[2];
				
				try {
					numOfDice = Integer.parseInt(words[3]);
				} catch (Exception e) {
					System.out.println("Exception: " + e.toString());
					return false;
				}
				
				if (numOfDice <= 0) {
					System.out.println("Error: Invalid number of dice of entered");
					return false;
				}

				playerModel.attackCountry(map, player, attackingCountry, 
						defendingCountry, numOfDice, 0, cardsStack);
			}
			
			for (String w: words) {
				if (w.equalsIgnoreCase(Commands.MAP_COMMAND_ATTACK_OPTION_NOATTACK)) {
					System.out.println(player + " has chosen not to attack");
					return true;
				}
			}			
			break;

		case Commands.MAP_COMMAND_SHOWMAP:
			GameUtilities.gamePlayShowmap(map);
			break;

		default:
			System.out.println("Error: Invalid command, Try again !!!");
			break;
		}

		return false;
	}

	@Override
	public boolean fortificationPhase(Hmap map, Player player) {

		Scanner sc = new Scanner(System.in);
		String command = sc.nextLine();
		String[] words = command.split(" ");
		String commandType = words[0];

		switch (commandType) {

		case Commands.MAP_COMMAND_SHOWMAP:
			GameUtilities.gamePlayShowmap(map);
			break;

		case Commands.MAP_COMMAND_FORTIFY:

			if (words.length < 2) {
				System.out.println("Error: Invalid command length. Try again !!!");
				return false;
			}

			// fortify -none command
			if (words[1].equalsIgnoreCase(Commands.MAP_COMMAND_FORTIFY_OPTION_NONE)) {
				System.out.println(player + " has chosen to skip fortify.");
				return true;		
			} 
			
			if (words.length < 4) {
				System.out.println("Error: Invalid command length. Try again !!!");
				return false;
			}

			int numArmies = 0;

			try {
				numArmies = Integer.parseInt(words[3]);
			} catch (Exception e) {
				System.out.println("Exception: " + e.toString());
				return false;
			}

			if (numArmies <= 0) {
				System.out.println("Exception: Invalid number of armies. "
						+ "Please choose more than 0 army");
				return false;
			}

			if (playerModel.fortifyCurrentPlayer(map, player, words[1], 
					words[2], numArmies)) {
				return true;
			}
			break;

		default:
			System.out.println("Error: Invalid command, Try again !!!");
			break;
		}

		return false;
	}
}
