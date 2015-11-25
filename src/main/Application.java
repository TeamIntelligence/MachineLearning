package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.Restaurant;

public class Application {
	
	public static void main(String[] args){
		String csvPath = "resources/restaurant.csv";
		
		Application app = new Application();
		List<Map<String, String>> data = app.readCsv(csvPath, ",");
		
		/*
		for(Map<String, String> row : data) {
			System.out.println(row.toString());
		}
		*/
		
		Map<String, Map<String, Map<String, Integer>>> counts = MapMaker.makeCountMap(data, "wait");
		for(Entry<String, Map<String, Map<String, Integer>>> entry : counts.entrySet()){
			System.out.println(entry.getKey());
			for(Entry<String, Map<String, Integer>> wait : entry.getValue().entrySet()){
				System.out.println("\t" + wait);
			}
		} 
	}
	
	
	private List<Map<String, String>> readCsv(String csvPath, String csvSplitBy) {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		List<String> columns = new ArrayList<String>();
		
		BufferedReader br = null;
		String line = "";
		try {

			br = new BufferedReader(new FileReader(csvPath));
			boolean firstRow = true;
			
			while ((line = br.readLine()) != null) {
				Map<String, String> row = new HashMap<String, String>();
				
				String[] args = line.split(csvSplitBy);
				for(int i = 0; i < args.length; i++){
					args[i] = args[i].trim();
				}
				
				if(firstRow) {
					for(String colName : args) {
						columns.add(colName);
					}
					
					firstRow = false;
					continue;
				} 
				
				int j = 0;
				for(String colName : columns) {
					String value = CheckStringType(args[j]);
					row.put(colName, value);
					j++;
				}
				
				data.add(row);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return data;
	}
	
	private String CheckStringType(String value) {
		String val = value.toUpperCase();
		
		if(val.equals("YES") || val.equals("NO")) {
			return YesNoToBool(val); 
		}
		
		return value;
	}
	
	private String YesNoToBool(String in){
		if(in.toUpperCase().equals("YES")){
			return "true";
		}
		return "false";
	}
	
}
