package com.maingame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import com.controller.GameController;
import com.entity.Continent;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;
import com.utilities.GameUtilities;


/**
 * This class controls the behavior of the World Domination View
 * <ul>
 * <li>the percentage of the map controlled by every player </li>
 * <li>the continents controlled by every player</li>
 * <li>the total number of armies owned by every player.</li>
 * </ul>
 *
 * @author Mahmoudreza
 */
public class WorldDominationView implements Observer {

    /**
     * This method Update Observable and Argument
     * @param o Observable object
     * @param arg an object of Object
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {

        String methodValue = (String) arg;
        GameController gameController = (GameController) o;
        Hmap map = gameController.getMap();

        if (methodValue.equals("show-world-domination")) {

            System.out.println("++++++++ World Domination View ++++++++");

            for (Player p: gameController.getPlayerModel().getPlayersList()) {

                System.out.println("+++++++++++++++++++++++++++");

                double mapPercent = ((float)p.getAssignedCountry().size() / (float)map.getCountries().size()) * 100;

                mapPercent = Math.round(mapPercent * 100.0) / 100.0;
                System.out.println("Player: " + p.getName() + " has " + mapPercent + "% of map");
                System.out.println("Player: " + p.getName() + " has continents = " + GameUtilities.getContinentOwnedByPlayer(p));
                System.out.println("Player: " + p.getName() + " has current armies = " + p.getArmies());
              
                int armiesOnMap = 0;
                
                for (Country c: p.getAssignedCountry()) {
                	armiesOnMap += c.getArmy();
                }
                
                System.out.println("Player: " + p.getName() + " has armies on map = " + armiesOnMap);
            }

            System.out.println("+++++++++++++++++++++++++++");
        }
    }
}
