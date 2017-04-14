package scraper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import org.apache.commons.io.*;

public class PlantsScraper {

	public static void main(String[] args) throws IOException {
		CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(new URL("https://plants.usda.gov/java/downloadData?fileName=plantlst.txt&static=true").openStream())));
		List<String[]> symbols = reader.readAll();
		symbols.parallelStream().forEach((String[] symbol)->{
			System.out.println("Getting data for symbol " + symbol[0]);
			
			String plantID = symbol[0].replaceAll("\"", ""); //plant unique ID
			String pageString = "https://plants.usda.gov/core/profile?symbol=" + plantID; //URL string for main info page
			String referenceString = "https://plants.usda.gov/java/reference?symbol=" + plantID; //URL string for references page
			String charString = "https://plants.usda.gov/java/charProfile?symbol=" + plantID; //URL string for characteristics page
			
			/*create URL objects*/
			
			URL pageURL = null;
			try {
				pageURL = new URL(pageString);
			} catch (MalformedURLException e) {
				System.err.println("Error getting data for symbol " + symbol[0]);
			}
			
			URL referenceURL = null;
			try {
				referenceURL = new URL(referenceString);
			} catch (MalformedURLException e) {
				System.err.println("Error getting data for symbol " + symbol[0]);
			}
			
			URL charURL = null;
			try {
				charURL = new URL(charString);
			} catch (MalformedURLException e) {
				System.err.println("Error getting data for symbol " + symbol[0]);
			}
			
			new File(plantID).mkdir(); //create new directory for files to go
			
			/*download HTML files into folder*/
			
			try {
				FileUtils.copyURLToFile(pageURL, new File(String.format("%s\\%s.page.html", plantID, plantID)), 1000, 1000);
			} catch (IOException e) {
				System.err.println("Error getting data for symbol " + symbol[0]);
			}
			
			try {
				FileUtils.copyURLToFile(referenceURL, new File(String.format("%s\\%s.reference.html", plantID, plantID)), 1000, 1000);
			} catch (IOException e) {
				System.err.println("Error getting data for symbol " + symbol[0]);
			}
			
			try {
				FileUtils.copyURLToFile(charURL, new File(String.format("%s\\%s.char.html", plantID, plantID)), 1000, 1000);
			} catch (IOException e) {
				System.err.println("Error getting data for symbol " + symbol[0]);
			}
			
		});
		
		
		System.out.println("Done writing data");
		reader.close();
	}

}
