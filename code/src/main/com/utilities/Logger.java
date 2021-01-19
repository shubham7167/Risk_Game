package com.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


/**
 * This class contains the method to save the message into a log file.
 * @author Mehul 
 */
public class Logger {

	/**
	 * Prints and Logs the message with the time-stamp in a log file.
	 * 
	 * @param fileName name of the file
	 * @param logInfo log message
	 */
	public static void printAndLogMessage(String fileName, String logInfo) {
		System.out.println(logInfo);
		LogMessage(fileName, logInfo);
	}
	
	/**
	 * Logs the message with the time-stamp in a log file.
	 * 
	 * @param fileName name of the file
	 * @param logInfo log message
	 */
	public static void LogMessage(String fileName, String logInfo) {

		String directoryName = "logs";
		File logFile = new File(directoryName + "/RiskGame" + ".log");
		String toWrite = null;
		File directory = new File(directoryName);
		
	    if (!directory.exists()) {
	        directory.mkdir();
	    }
	    
		try {
			logFile.createNewFile();
			BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(logFile, true));

			String timeStamp = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
			toWrite = "[" + timeStamp + "] " + fileName + ": " + logInfo;
			bufferWriter.write(toWrite);
			bufferWriter.newLine();
			bufferWriter.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}