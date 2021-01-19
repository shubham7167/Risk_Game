package com.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This is a Test suite Class for testing PlayerCommands
 * @author Mehul
 * @author Maryam
 * @author Mahmoud Reza
 */
@RunWith(Suite.class)
@SuiteClasses({PlayerModelTest.class,
				PlayerModelAttackTest.class})

public class PlayerModelTestSuite {
}

