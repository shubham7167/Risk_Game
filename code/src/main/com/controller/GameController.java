package com.controller;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Scanner;
import java.util.Stack;

import com.config.Commands;
import com.config.PlayerStrategy;
import com.entity.Card;
import com.entity.Hmap;
import com.entity.Player;
import com.exception.InvalidMap;
import com.maingame.Main;
import com.mapparser.MapAdapter;
import com.mapparser.MapVerifier;
import com.models.CardModel;
import com.models.PlayerModel;
import com.strategy.Human;
import com.utilities.GameUtilities;


/**
 * This class reads, parses the command line string from user input.
 *
 * @author Parth
 * @author Mehul
 */
public class GameController extends Observable {

	Hmap rootMap;
	MapAdapter mapAdapter;
	String editFilePath = "";
	boolean isReinfoceArmiesAssigned;
	PlayerModel playerModel;
	CardModel cardModel;
	Player currentPlayer;
	Stack<Card> stackOfCards;

	// default constructor to initialize members
	public GameController(Main mainView) {
		this.mapAdapter = new MapAdapter(new MapAdapter().getConquestMapParser());
		this.playerModel = new PlayerModel();
		this.cardModel = new CardModel();
		this.rootMap = new Hmap();
		this.addObserver(mainView);
		this.stackOfCards = new Stack<Card>();
		this.isReinfoceArmiesAssigned = false;
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
	 * Get the player model.
	 * 
	 * @return player model object
	 */
	public PlayerModel getPlayerModel() {
		return playerModel;
	}

	/**
	 * Get the stack of cards.
	 * 
	 * @return stack of cards
	 */
	public Stack<Card> getCardsStack() {
		return stackOfCards;
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
	 * This method is to set the current player.
	 * 
	 * @param player Current player.
	 */
	public void setCurrentPlayer(Player player) {
		this.currentPlayer = player;
	}

	/**
	 * Setter method for the map object.
	 *
	 * @param map object
	 * @return root map
	 */
	private Hmap setMap(Hmap map) {
		return this.rootMap = map;
	}

	/**
	 * Get map object
	 *
	 * @return the map
	 */
	public Hmap getMap() {
		return rootMap;
	}

	/**
	 * Parses the String and calls the related map edit commands.
	 * 
	 * @param command User input Command/String
	 */
	public void processMapEditCommands(String command) {

		String[] words = command.split(" ");
		String commandType = words[0], filePath = "";
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		
		switch (commandType) {

		case Commands.MAP_COMMAND_EDIT_CONTINENT:

			for (int idx = 1; idx < words.length; idx++) {

				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {

					if (words.length < idx + 3) {
						System.out.println("Invalid command, Try again !!!");
						return;
					}

					MapContoller.addContinent(getMap(), words[idx + 1], words[idx + 2], "");
					idx = idx + 2;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {

					if (words.length < idx + 2) {
						System.out.println("Invalid command, Try again !!!");
						return;
					}
					MapContoller.removeContinent(getMap(), words[idx + 1]);
					idx = idx + 1;

				} else {
					System.out.println("Invalid command, Try again !!!");
				}
			}
			break;

		case Commands.MAP_COMMAND_EDIT_COUNTRY:

			for (int idx = 1; idx < words.length; idx++) {

				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {

					if (words.length < idx + 3) {
						System.out.println("Invalid command, Try again !!!");
						return;
					}

					MapContoller.addCountry(getMap(), words[idx + 1], words[idx + 2]);
					idx = idx + 2;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {

					if (words.length < idx + 2) {
						System.out.println("Invalid command, Try again !!!");
						return;
					}

					MapContoller.removeCountry(getMap(), words[idx + 1]);
					idx = idx + 1;

				} else {
					System.out.println("Invalid command, Try again !!!");
				}
			}
			break;

		case Commands.MAP_COMMAND_EDIT_NEIGHBOR:

			for (int idx = 1; idx < words.length; idx++) {

				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {

					if (words.length < idx + 3) {
						System.out.println("Invalid command, Try again !!!");
						return;
					}

					MapContoller.addNeighborCountry(getMap(), words[idx + 1], words[idx + 2]);
					idx = idx + 2;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {

					if (words.length < idx + 3) {
						System.out.println("Invalid command, Try again !!!");
						return;
					}

					MapContoller.removeNeighborCountry(getMap(), words[idx + 1], words[idx + 2]);
					idx = idx + 2;

				} else {
					System.out.println("Invalid command, Try again !!!");
				}
			}
			break;

		case Commands.MAP_COMMAND_SHOWMAP:
			MapContoller.mapEditorShowmap(getMap());
			break;

		case Commands.MAP_COMMAND_SAVEMAP:

			if (words.length < 2) {
				System.out.println("Invalid command, Try again !!!");
				break;
			}

			filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\" + words[1];

			// save map file should be similar to the one which was edited previously
			if (!editFilePath.isEmpty()) {
				if (!editFilePath.equals(filePath)) {
					System.out.println("Please give same filename as you have given in editmap.");
					break;
				}
			}

			try {
				MapVerifier.verifyMap(getMap());
			} catch (InvalidMap e1) {
				System.out.println("Exception: " + e1.toString());
				break;
			}

			System.out.println("Saving File at: " + filePath);
			File outputMapFile = new File(filePath);
			mapAdapter.writeMapFile(outputMapFile, getMap());
			break;

		case Commands.MAP_COMMAND_EDITMAP:

			if (words.length < 2) {
				System.out.println("Invalid command, Try again !!!");
				break;
			}

			editFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\" + words[1];
			File editMapFile = new File(editFilePath);

			if (editMapFile.exists()) {
				try {
					setMap(mapAdapter.readMapFile(editMapFile));
				} catch (InvalidMap e) {
					System.out.println("Exception: " + e.toString());				
				}
			} else {
				try {
					editMapFile.createNewFile();
					System.out.println("Given map file does not exist. New Map file has been created.");
				} catch (IOException e) {
					System.out.println("Exception: " + e.toString());
				}
			}
			break;

		case Commands.MAP_COMMAND_VALIDATEMAP:

			try {
				MapVerifier.verifyMap(getMap());
			} catch (InvalidMap e1) {
				System.out.println("Exception: " + e1.toString());
			}
			break;

		case Commands.MAP_COMMAND_LOADMAP:

			if (words.length < 2) {
				System.out.println("Invalid command, Try again !!!");
				break;
			}

			if (null == classloader.getResource(words[1])) {
				System.out.println("Exception: File does not exist: " + words[1]);
				break;
			}

			File inputMapFile = new File(classloader.getResource(words[1]).getFile().replace("%20", " "));

			try {
				setMap(mapAdapter.readMapFile(inputMapFile));
				// Update View
				setChanged();
				notifyObservers("loadmap");
			} catch (InvalidMap e) {
				System.out.println("Exception: " + e.toString());
			}
			break;

		default:
			System.out.println("Invalid command, Try again !!!");
			break;
		}
	}

	/**
	 * Parses the String and calls the related player commands.
	 * 
	 * @param command User input Command/String
	 */
	public void processGamePlayCreatePlayerCommands(String command) {

		String[] words = command.split(" ");
		String commandType = words[0];

		switch (commandType) {

		case Commands.MAP_COMMAND_SHOWMAP:
			MapContoller.mapEditorShowmap(getMap());
			break;

		case Commands.MAP_COMMAND_GAMEPLAYER:

			for (int idx = 1; idx < words.length; idx++) {
				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {

					if (words.length < idx + 3) {
						System.out.println("Error: Invalid command, Try again !!!");
						return;
					}

					String playerName = words[idx + 1];
					String playerStrategy = words[idx + 2]; 
					
					playerModel.createPlayer(playerName, playerStrategy);
					
					idx = idx + 2;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {

					if (words.length < idx + 2) {
						System.out.println("Invalid command, Try again !!!");
						return;
					}

					String playerName = words[idx + 1];
					playerModel.removePlayer(playerName);
					idx = idx + 1;

				} else {
					System.out.println("Invalid command, Try again !!!");
					break;
				}
			}
			break;

		case Commands.MAP_COMMAND_POPULATE_COUNTRIES:

			// Assign armies according the players count
			if (playerModel.assignArmiesToAllPlayers()) {

				playerModel.populateCountries(getMap());
				GameUtilities.intitializeArmiesForAllCountries(getMap());

				for (Player p : playerModel.getPlayersList()) {
					int countryCount = p.getAssignedCountry().size();
					System.out.println("Number of Countries for Player : " + p.getName() + " = " + countryCount);
				}

				// World domination view
				setChanged();
				notifyObservers("show-world-domination");

				setCurrentPlayer(playerModel.getPlayersList().get(0));

				// Update View
				setChanged();
				notifyObservers("populatecountries");
			}
			break;

		default:
			System.out.println("Invalid command, Try again !!!");
			break;
		}
	}

	/**
	 * Parses the String and calls the related game play startup commands.
	 * 
	 * @param sc scanner object
	 */
	public void processGamePlayStartupCommands(Scanner sc) {


//		if(!(currentPlayer.getStrategy() instanceof Human)){
//
//			playerModel.placeAll();
//
//			// Allocate cards to countries
//			cardModel.allocateCardsToCountry(getMap(), getCardsStack());
//			setCurrentPlayer(playerModel.getPlayersList().get(0));
//
//			// Update View
//			setChanged();
//			notifyObservers("placeall");
//			return;
//		}

		System.out.println("Current game phase: Gameplay startup phase (placearmy, placeall, showmap)");
		System.out.println("Current Player: " + getCurrentPlayer().getName() + " (" 
				+ getCurrentPlayer().getPlayerStrategyName() + ")" + ", number of armies left = "
				+ getCurrentPlayer().getArmies());

		String command = sc.nextLine();
		String[] words = command.split(" ");
		String commandType = words[0];

		switch (commandType) {

		case Commands.MAP_COMMAND_SHOWMAP:
			GameUtilities.gamePlayShowmap(getMap());
			break;

		case Commands.MAP_COMMAND_PLACE_ARMY:

			if (words.length < 2) {
				System.out.println("Invalid command, Try again !!!");
				break;
			}

			if (playerModel.placeArmy(getMap(), getCurrentPlayer(), words[1])) {
				changeCurrentPlayer();
			}

			if (playerModel.isAllPlayersArmiesExhausted()) {
				setCurrentPlayer(playerModel.getPlayersList().get(0));

				// Update View
				setChanged();
				notifyObservers("placeall");
			}
			break;

		case Commands.MAP_COMMAND_PLACE_ALL:

			playerModel.placeAll();
			
			// Allocate cards to countries
			cardModel.allocateCardsToCountry(getMap(), getCardsStack());
			setCurrentPlayer(playerModel.getPlayersList().get(0));
			
			// Update View
			setChanged();
			notifyObservers("placeall");
			break;

		default:
			System.out.println("Invalid command, Try again !!!");
			break;
		}
	}

	/**
	 * Parses the String and calls the related game play reinforcement commands.
	 */
	public void processGamePlayReinforcementCommands() {
				
		if (!isReinfoceArmiesAssigned) {
		
			getCurrentPlayer().setNumOfCountriesWon(0);
			getCurrentPlayer().setNumOfAttacks(0);
			
			playerModel.assignReinforceArmiesToPlayers();
			
			// World domination view
			setChanged();
			notifyObservers("show-world-domination");
			
			isReinfoceArmiesAssigned = true;
		}

		if (!getCurrentPlayer().getPlayerStrategyName().equalsIgnoreCase(PlayerStrategy.PLAYER_STRATEGY_HUMAN)) {
			
			System.out.println("Current game phase: Gameplay reinforcement phase");
			System.out.println("Current Player: " + getCurrentPlayer().getName() + 
					" (" + getCurrentPlayer().getPlayerStrategyName() + ")"  
					+ ", Armies left for reinforcement = " + getCurrentPlayer().getArmies());
		}
		
		/* Call Strategy Pattern reinforce method */
		if (getCurrentPlayer().getStrategy().reinforcementPhase(
				getMap(), getCurrentPlayer(), getCardsStack())) {
			
			// Update View
			setChanged();
			notifyObservers("show-world-domination");
			
			// Going to next phase
			setChanged();
			notifyObservers("reinforcedone");
		}
	}

	/**
	 * Parses the String and calls the related game play attack commands.
	 * 
	 *
	 */
	public void processGamePlayAttackCommands() {

		int previousAttackCount = getCurrentPlayer().getNumOfAttacks();
		System.out.println("Current phase: Gameplay Attack phase (attack, defend, attackmove, showmap)");
		System.out.println("Current Player: " + getCurrentPlayer().getName() + " (" 
							+ getCurrentPlayer().getPlayerStrategyName() + ")");

		if (!playerModel.isAttackPossible(getCurrentPlayer())){
			System.out.println("Attack not possible for " + getCurrentPlayer());
			setChanged();
			notifyObservers("attackdone");
			return;
		}

		/* Call Strategy Pattern attack method */
		if (getCurrentPlayer().getStrategy().attackPhase(getMap(), getCurrentPlayer(), getCardsStack())) {

			setChanged();
			notifyObservers("show-world-domination");

			if (GameUtilities.isPlayerWonGame(getCurrentPlayer(), getMap())) {
				System.out.println("Player: " + getCurrentPlayer().getName()+ " has won the game :)");
				setChanged();
				notifyObservers("gameover");
			} else {
				// Going to next phase
				setChanged();
				notifyObservers("attackdone");
			}
			return;
		}		
		
		// World domination view when attack was successful for human player
		if (getCurrentPlayer().getNumOfAttacks() > previousAttackCount) {
			setChanged();
			notifyObservers("show-world-domination");
		}
		
		// This is the case where by manual attacks human player won the game
		if (GameUtilities.isPlayerWonGame(getCurrentPlayer(), getMap())) {
			System.out.println("Player: " + getCurrentPlayer().getName()+ " has won the game :)");
			setChanged();
			notifyObservers("gameover");
		}
	}

	/**
	 * Parses the String and calls the related game play fortify commands.
	 * 
	 * @param sc scanner object
	 */
	public void processGamePlayFortifyCommands() {
		
		if (getCurrentPlayer().getAssignedCountry().size() == 1) {
			System.out.println(getCurrentPlayer() + " can't do fortify as you have "
					+ "only one country ownership");
			
			// check all players have played
			if (playerModel.isLastPlayer(getCurrentPlayer())) {
				isReinfoceArmiesAssigned = false;
				System.out.println("******* All players have played in their turn **********");
			}
			
			// Update View
			setChanged();
			notifyObservers("fortifydone");
			changeCurrentPlayer();
			
			return;
		}
		
		System.out.println("Current game phase: Gameplay fortify phase (fortify, showmap)");
		System.out.println("Current Player: " + getCurrentPlayer().getName() + " (" 
				+ getCurrentPlayer().getPlayerStrategyName() + ")");

		/* Call Strategy Pattern fortify method */
		if (getCurrentPlayer().getStrategy().fortificationPhase(getMap(), getCurrentPlayer())) {
			
			// check all players have played
			if (playerModel.isLastPlayer(getCurrentPlayer())) {
				isReinfoceArmiesAssigned = false;
				System.out.println("******* All players have played in their turn **********");
			}
			
			// Update View
			setChanged();
			notifyObservers("fortifydone");
			changeCurrentPlayer();
		}
	}

	/**
	 * This will change the current player
	 */
	public void changeCurrentPlayer() {
		int currentPlayerIdx = playerModel.getPlayersList().indexOf(getCurrentPlayer());
		int totalPlayers = playerModel.getPlayersList().size();
		setCurrentPlayer(playerModel.getPlayersList().get((currentPlayerIdx + 1) % totalPlayers));
	}
}