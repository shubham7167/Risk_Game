package com.maingame;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import com.config.Commands;
import com.controller.GameController;
import com.entity.Card;
import com.strategy.Human;

/**
 * This class controls the behavior of the Card Exchange View. It will display
 * all the cards owned by the current player, then allow the player to select
 * some cards to exchange.
 *
 * @author Komal
 */
public class CardExchangeView implements Observer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {

		String methodValue = (String) arg;
		Human humanStrategy = (Human) o;
		Scanner sc = new Scanner(System.in);

		// If the player selects cards, they are given the appropriate number of armies
		// as reinforcement. The player can choose
		// not to exchange cards and exit the card exchange view. If the player own 5
		// cards or more, they must exchange cards. The cards
		// exchange view should cease to exist after the cards exchange.
		if (methodValue.equals("card-exchange")) {

			int cardSize = humanStrategy.getCurrentPlayer().getCardList().size();

			System.out.println("++++++++++ Card Exchange View ++++++++++");
			System.out.println(humanStrategy.getCurrentPlayer() + " has "
					+ humanStrategy.getCurrentPlayer().getCardList() + " cards");

			if (cardSize >= 3) {

				System.out.println("Current game phase: Gameplay reinforcement (exchangecards)");

				int i = 1;
				for (Card card : humanStrategy.getCurrentPlayer().getCardList()) {
					System.out.println(i + "." + card);
					i++;
				}

				while (true) {

					Boolean forcefulExchange = false;
					if (cardSize >= 5) {
						System.out.println("You have 5(max) cards, you need to exchange !!!");
						forcefulExchange = true;
					}

					String command = sc.nextLine();
					String[] words = command.split(" ");
					String commandType = words[0];

					switch (commandType) {

					case Commands.MAP_COMMAND_REINFORCE_OPTION_EXCHANGECARDS:

						if (words[1].equalsIgnoreCase("-none")) {
							if (forcefulExchange) {
								break;
							} else {
								System.out.println(
										humanStrategy.getCurrentPlayer() + " has chosen not to exchange cards");
								return;
							}
						}

						if (words.length < 4) {
							System.out.println("Invalid command, Try again !!!");
							break;
						}

						boolean retVal = true;
						int idx[] = new int[3];

						try {
							idx[0] = Integer.parseInt(words[1]) - 1;
							idx[1] = Integer.parseInt(words[2]) - 1;
							idx[2] = Integer.parseInt(words[3]) - 1;
						} catch (Exception e) {
							System.out.println("Exception: " + e.toString());
							break;
						}

						if (idx[0] == idx[1] || idx[1] == idx[2] || idx[2] == idx[0]) {
							System.out.println("Error: same card numbers entered");
							break;
						}

						List<Card> cardsChoosen = new ArrayList<>();
						List<Card> cardList = humanStrategy.getCurrentPlayer().getCardList();

						for (int index : idx) {

							if (index < 0) {
								System.out.println("Error: cannot accept negative index");
								retVal = false;
								break;
							}

							try {
								cardsChoosen.add(cardList.get(index));
							} catch (Exception e) {
								System.out.println("Exception: " + e.toString());
								retVal = false;
								break;
							}
						}

						if (!retVal)
							break;

						retVal = humanStrategy.getCardModel().isCardsListValidForExchange(cardsChoosen);

						if (retVal) {
							humanStrategy.getCardModel().exchangeCards(humanStrategy.getCurrentPlayer(), cardsChoosen,
									humanStrategy.getCardsStack());
							return;
						} else {
							System.out.println(
									"Error: You can only exchange when \n1.Cards of all same type or \n2.Cards of all different type");
						}
						break;

					default:
						System.out.println("Invalid command, Try again !!!");
						break;
					}
				}
			}
			System.out.println("++++++++++++++++++++++++++");
		}
	}
}
