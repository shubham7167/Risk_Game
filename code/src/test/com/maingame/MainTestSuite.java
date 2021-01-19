package com.maingame;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.entity.EntityTestSuite;
import com.mapparser.MapParserTestSuite;
import com.model.PlayerModelTestSuite;

/**
 * @author Komal
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ EntityTestSuite.class, MapParserTestSuite.class, PlayerModelTestSuite.class})
public class MainTestSuite {
}

