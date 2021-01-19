package com.maingame;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import com.config.GameState;
import com.controller.GameController;

/**
 * This class implements the phase view of the game.
 *
 * @author Mehul
 */
public class Main implements Observer {

	static GameState gamePhase;
	GameController gameController;
	
	/**
	 * Get game phase
	 * @return gameState
	 */
	public static GameState getGamePhase() {
		return gamePhase;
	}

	/**
	 * Setter method for the game state.
	 * @param gameState current phase
	 */
	public static void setGamePhase(GameState gameState) {
		gamePhase = gameState;
	}
	
	/**
	 * This method controls all phases of the game.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		
		Main mainView = new Main();
		WorldDominationView worldDominationView = new WorldDominationView();
		
		setGamePhase(GameState.RISK_STATE_INIT);
		Scanner sc = new Scanner(System.in);
		String command;

		while (true) {

			switch (getGamePhase()) {

			case RISK_STATE_INIT:
				System.out.println("---------- Welcome to Risk game ----------");
				mainView.gameController = new GameController(mainView);
				mainView.gameController.addObserver(worldDominationView);
				setGamePhase(GameState.RISK_STATE_MAP_EDITING);
				break;
				
			case RISK_STATE_MAP_EDITING:
				
				System.out.println("Current game phase: Map editor (editcontinent, "
						+ "editcountry, editneighbor, showmap, savemap, editmap, validatemap, loadmap)");
				System.out.println("When map editing gets finised, Use \"loadmap filename\" command to start the game");
				System.out.println("Please enter any commands to continue ...");
				
				command = sc.nextLine();
				mainView.gameController.processMapEditCommands(command);
				break;

			case RISK_STATE_GAMEPLAY_CREATE_PLAYERS:
				
				System.out.println("Current game phase: Gameplay create players (gameplayer -add playername strategy -remove playername, populatecountries, showmap)");
				System.out.println("Use \"populatecountries\" command to allocate initial armies to players");
				System.out.println("Please enter any commands to continue ...");
				
				command = sc.nextLine();
				mainView.gameController.processGamePlayCreatePlayerCommands(command);
				break;
				
			case RISK_STATE_GAMEPLAY_STARTUP_PHASE:
				mainView.gameController.processGamePlayStartupCommands(sc);
				break;
				
			case RISK_STATE_GAMEPLAY_REINFORCEMENT_PHASE:
				mainView.gameController.processGamePlayReinforcementCommands();
				break;
				
			case RISK_STATE_GAMEPLAY_ATTACK_PHASE:
				mainView.gameController.processGamePlayAttackCommands();
				break;
				
			case RISK_STATE_GAMEPLAY_FORTIFICATION_PHASE:
				mainView.gameController.processGamePlayFortifyCommands();
				break;

			default:
				break;
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {		
		String methodValue = (String) arg;

		if (methodValue.equals("loadmap")) {
			setGamePhase(GameState.RISK_STATE_GAMEPLAY_CREATE_PLAYERS);
			System.out.println("----------------------------------");
		} 
		else if (methodValue.equals("populatecountries")) {
			setGamePhase(GameState.RISK_STATE_GAMEPLAY_STARTUP_PHASE);
			System.out.println("----------------------------------");
		}
		else if (methodValue.equals("placeall")) {
			setGamePhase(GameState.RISK_STATE_GAMEPLAY_REINFORCEMENT_PHASE);
			System.out.println("----------------------------------");
		}
		else if (methodValue.equals("reinforcedone")) {
			setGamePhase(GameState.RISK_STATE_GAMEPLAY_ATTACK_PHASE);
			System.out.println("----------------------------------");
		} 
		else if (methodValue.equals("attackdone")) {
			setGamePhase(GameState.RISK_STATE_GAMEPLAY_FORTIFICATION_PHASE);
			System.out.println("----------------------------------");
		} 
		else if (methodValue.equals("fortifydone")) {
			setGamePhase(GameState.RISK_STATE_GAMEPLAY_REINFORCEMENT_PHASE);
			System.out.println("----------------------------------");
		}
		else if (methodValue.equals("gameover")) {
			setGamePhase(GameState.RISK_STATE_INIT);
			System.out.println("----------------------------------");
		}
	}
}