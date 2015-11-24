package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.Restaurant;

public class Application {
	public static void main(String[] args){
		String csvPath = "resources/restaurant.csv";
		
		Application app = new Application();
		List<Restaurant> restaurants = app.readCsv(csvPath);
				
		 Map<String, Map<String, Map<String, Integer>>> counts = MapMaker.makeCountMap(restaurants);
		 for(Entry<String, Map<String, Map<String, Integer>>> entry : counts.entrySet()){
			 System.out.println(entry.getKey());
			 for(Entry<String, Map<String, Integer>> wait : entry.getValue().entrySet()){
				 System.out.println("\t" + wait);
			 }
		 }
	}
	
	private List<Restaurant> readCsv(String csvPath){
		BufferedReader br = null;
		String line = "";
		String csvSplitBy = ",";
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		try {

			br = new BufferedReader(new FileReader(csvPath));
			while ((line = br.readLine()) != null) {
				String[] args = line.split(csvSplitBy);
				for(int i = 0; i < args.length; i++){
					args[i] = args[i].trim();
				}
				
				boolean alternative = stringToBool(args[0]);
				boolean bar = stringToBool(args[1]);
				boolean fridaySat = stringToBool(args[2]);
				boolean hunger = stringToBool(args[3]);
				String pat = args[4];
				String price= args[5];
				boolean rain = stringToBool(args[6]);
				boolean res = stringToBool(args[7]);
				String est = args[8];
				String type = args[9];
				boolean wait = stringToBool(args[10]);
				
				Restaurant restaurant = new Restaurant(alternative, bar, fridaySat, hunger, pat, price, rain, res, est, type, wait);
				restaurants.add(restaurant);
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
		
		return restaurants;
	}
	
	private boolean stringToBool(String in){
		if(in.trim().toUpperCase().equals("YES")){
			return true;
		}
		return false;
	}
	
}
