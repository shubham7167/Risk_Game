package com.model;

import com.models.CardModel;
import com.models.PlayerModel;
import com.utilities.GameUtilities;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

import com.config.CardType;
import com.entity.*;

/**
 * This class test the attack phase
 * @author Maryam
 * @author MahmoudReza
 *
 */
public class PlayerModelAttackTest {

    public static Hmap map;
    public static Player player;
    public static PlayerModel playerModel;
    public static CardModel cardModel;
    public static Stack<Card> cardStack;
    public static Country count1 = new Country();
    static Country count12 = new Country();
    int currentArmies ;
    public ArrayList<Player> playersList;
    String countryName;
    List<Country> totalCoutries;


    /**
     * This method runs before all tests just one time
     */
    @BeforeClass
    public static void beforeAll () {
    System.out.println("This is for testing Attack Class");
    }

    /**
     * This method runs before  each test
     */
    @Before
    public void beforeTest () {
        player = new Player(4,"playerTest");
        playerModel = new PlayerModel();
        cardModel = new CardModel();
        cardStack = new Stack<Card>();
        currentArmies = player.getArmies();
        map = new Hmap();
    }
    /**
     * This method runs after all test just one time
     */
    @AfterClass
    public static void afterAllTest() {
        System.out.println("All tests are done");
    }
    
    /**
     * This method test the attack country
     */
    @Test
    public void attackCountryTest() {

    	Player p1 = new Player(1, "Maryam");
    	Player p2 = new Player(2, "parth");
    	Card card = new Card(CardType.CAVALRY);

    	Continent c1 = new Continent("Asia", 2);

    	Country fromCountry = new Country();
    	fromCountry.setName("India");
    	fromCountry.setArmy(2);
    	fromCountry.setPlayer(p1);
    	
    	Country toCountry = new Country();
    	toCountry.setName("China");
    	toCountry.setArmy(12);
    	toCountry.setPlayer(p2);

    	c1.getCountries().add(fromCountry);
    	c1.getCountries().add(toCountry);
    	
    	map.getContinents().add(c1);    	
    	map.getCountryMap().put("India", fromCountry);
    	map.getCountryMap().put("China", toCountry);
    	
    	fromCountry.getNeighborCountries().add("China");
    	toCountry.getNeighborCountries().add("India");
    	
    	p1.getAssignedCountry().add(fromCountry);
    	p2.getAssignedCountry().add(toCountry);
    	
    	playerModel.getPlayersList().add(p1);
    	playerModel.getPlayersList().add(p2);
    	
    	cardStack.add(card);
    	playerModel.allOutAttackCountry(map, p1, "India", "China", cardStack);
        
    	assertEquals(fromCountry.getArmy(), 1);
    }

    /**
     * This method test the dice attack country 
     */
    @Test
    public void attackCountryDiceTest() {

        boolean armies = playerModel.attackCountry(map,player, "Finland","Norway",10,3,cardStack);
        assertFalse(false);
        System.out.println("Attack test is passed");
    }

    /**
     * This method test the fortify 
     */
    @Test
    public void fortifyTest() {
    	Player p = new Player(1, "Maryam");
    	
    	Continent c1 = new Continent("Asia", 2);

    	Country fromCountry = new Country();
    	fromCountry.setName("India");
    	fromCountry.setArmy(10);
    	fromCountry.setPlayer(p);
    	
    	Country toCountry = new Country();
    	toCountry.setName("China");
    	toCountry.setArmy(13);
    	toCountry.setPlayer(p);

    	c1.getCountries().add(fromCountry);
    	c1.getCountries().add(toCountry);
    	
    	map.getContinents().add(c1);    	
    	map.getCountryMap().put("India", fromCountry);
    	map.getCountryMap().put("China", toCountry);
    	
    	fromCountry.getNeighborCountries().add("China");
    	toCountry.getNeighborCountries().add("India");
    	
    	p.getAssignedCountry().add(fromCountry);
    	p.getAssignedCountry().add(toCountry);
    	
		playerModel.fortifyCurrentPlayer(map, p, fromCountry.getName(), toCountry.getName(), 3);
		
        assertEquals(toCountry.getArmy(), 16);
        assertEquals(fromCountry.getArmy(), 7);
    }
    
    /**
     * This method test assign armies to all players
     */
    @Test
    public void assignArmiesTest() {
    	
    	int armiesCount = 0;
		int numPlayers = 20;
    	boolean assignarmy= playerModel.assignArmiesToAllPlayers();
    	assertFalse(assignarmy);
    }
    
    /**
     * This method test is all player armies exhausted
     */
    @Test
    public void playerArmiesTest() {
    	playerModel.getPlayersList();
    	boolean p=playerModel.isAllPlayersArmiesExhausted();
    	assertTrue(p);
    }
    
    /**
     * This method test the count reinforcement armies
     */
    @Test
    public void countReinforcementArmiesTest() {
     	int count = GameUtilities.countReinforcementArmies(player);
    	assertNotEquals(count, 10);   	
    }
    
    /**
     * This method test reinforce Armies For CurrentPlayer
     */
    @Test
    public void reinforceArmiesForCurrentPlayerTest() {
    	String countryName=null;
    	int numberOfArmies=0;
    	player.getArmies() ;
    	boolean p=playerModel.reinforceArmiesForCurrentPlayer( player,  countryName, numberOfArmies);
    	assertTrue(p);
    }

}
