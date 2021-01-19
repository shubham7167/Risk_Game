package com.mapparser;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This is a Test suite Class for testing MapParser
 * 
 * @author Maryam
 * @author Mahmoudreza
 * @version 0.0.1
 */
@RunWith(Suite.class)
@SuiteClasses({ MapReaderTest.class, MapCommandsTest.class, MapVerifierTest.class })

public class MapParserTestSuite {
}
