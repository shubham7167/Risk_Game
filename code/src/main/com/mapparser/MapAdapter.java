package com.mapparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.entity.Hmap;
import com.exception.InvalidMap;


/**
 * This class implements adapter for domination map & conquest map.
 * @author Komal
 */
public class MapAdapter extends DominationMapParser implements MapInterface {

	ConquestMapParser conquestMapParser;
	boolean isDominationMapFile;
	
	/**
	 * This is parameterized constructor for Map Adapter.
	 * @param conquestParser Conquest Map Parser object.
	 */	
	public MapAdapter(ConquestMapParser conquestParser) {
		this.conquestMapParser = conquestParser;
		this.isDominationMapFile = false;
	}
	
	/**
	 * This is the default constructor of Map Adapter.
	 */	
	public MapAdapter() {
		this.conquestMapParser = new ConquestMapParser();
		this.isDominationMapFile = false;
	}
	
	/**
	 * Returns conquest Map Parser object.
	 * @return conquestMapParser
	 */
	public ConquestMapParser getConquestMapParser() {
		return conquestMapParser;
	}
	
	/**
	 * This method identifies the domination map file format.
	 * 
	 * @return true if file is Domination Map File, false otherwise
	 */
	public static boolean isDominationMapFile(File file) {
		
		String fileContent = new String();
		Scanner sc = null;
		
		try {
			sc = new Scanner(new FileInputStream(file));
			fileContent = sc.useDelimiter("\\Z").next();
			
		} catch (FileNotFoundException e1) {
			System.out.println("Exception: " + e1.toString());
		}			
		
		if (fileContent.contains("[borders]"))
			return true;
		
		return false;
	}
	
	/**
	 * Read domination and conquest map file
	 * 
	 * @param file File to read
	 * @return the map
	 * @throws InvalidMap
	 */
	@Override
	public Hmap readMapFile(File file) throws InvalidMap {
		
		if (MapAdapter.isDominationMapFile(file)) {
			isDominationMapFile = true;
			return readDominationFile(file);
		} else {
			isDominationMapFile = false;
			return conquestMapParser.readConquestFile(file);
		}		
	}

	/**
	 * Write domination and conquest map file
	 * 
	 * @param file File to read
	 * @param map Object of the map which is being processed
	 */
	@Override
	public void writeMapFile(File file, Hmap map) {
	
		if (isDominationMapFile) {
			writeDominationFile(map, file);
		} else {
			conquestMapParser.writeConquestFile(map, file);
		}
	}
}

/**
 *  This class is to Parse Domination Map
 */
class DominationMapParser {

	MapReader mapReader;
	MapWriter mapWriter;

	/**
	 * default constructor to initialize members
	 */
	public DominationMapParser() {
		this.mapReader = new MapReader();
		this.mapWriter = new MapWriter();
	}

	/**
	 * This method read Domination map
	 * 
	 * @param file File to be read
	 * @return map the map object after processing the file data
	 * @throws InvalidMap
	 */
	public Hmap readDominationFile(File file) throws InvalidMap {
		return mapReader.readDominationMapFile(file);
	}

	/**
	 * This method write Domination map
	 * 
	 * @param map Hmap object
	 * @param file File to be read
	 */
	public void writeDominationFile(Hmap map, File file) {
		mapWriter.writeDominationMapFile(map, file);
	}
}

/** 
 * This class is to Parse Conquest Map
 */
class ConquestMapParser {

	MapReader mapReader;
	MapWriter mapWriter;
	
	/**
	 * default constructor to initialize members
	 */
	public ConquestMapParser() {
		this.mapReader = new MapReader();
		this.mapWriter = new MapWriter();
	}

	/**
	 * This method read conquest map
	 * 
	 * @param file File to be read
	 * @return map returns the map object after processing the file data
	 * @throws InvalidMap
	 */
	public Hmap readConquestFile(File file) throws InvalidMap {
		return mapReader.readConquestMapFile(file);
	}

	/**
	 * This method write conquest map
	 * 
	 * @param map Hmap object
	 * @param file File to be read
	 */
	public void writeConquestFile(Hmap map, File file) {
		mapWriter.writeConquestMapFile(map, file);
	}
}

/**
 * This is a interface for MapAdapter class which has Map Reader and Map Writer methods
 */
interface MapInterface {
	
	/**
	 * Method description to Read domination and conquest map file 
	 * @throws InvalidMap 
	 */
	public Hmap readMapFile(File file) throws InvalidMap;
	
	/**
	 * Method description to Write domination and conquest map file
	 */
	public void writeMapFile(File file, Hmap map);
}

