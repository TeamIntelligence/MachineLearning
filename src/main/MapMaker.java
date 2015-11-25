package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MapMaker {
	
	
	public static Map<String, Map<String, Map<String, Integer>>> makeCountMap(List<Map<String, String>> input, String targetName) {
		
		Map<String, Map<String, Map<String, Integer>>> map = new HashMap<String, Map<String, Map<String, Integer>>>();
		
		// For each row
		for (Map<String, String> resMap : input) {
			
			// For each column
			for (Entry<String, String> entry : resMap.entrySet()) {
				Map<String, Map<String, Integer>> waitMap = new HashMap<String, Map<String, Integer>>();
				
				if (map.containsKey(entry.getKey())) {
					waitMap = map.get(entry.getKey());
				}
				
				if (resMap.get(targetName).equals("true")) {
					Map<String, Integer> valMap = new HashMap<String, Integer>();

					if (waitMap.containsKey(targetName)) {
						valMap = waitMap.get(targetName);
					}
					
					if (valMap.containsKey(entry.getValue())) {
						int count = valMap.get(entry.getValue());
						valMap.put(entry.getValue(), count + 1);
					} else {
						valMap.put(entry.getValue(), 1);
					}
					
					waitMap.put(targetName, valMap);
					map.put(entry.getKey(), waitMap);
				
				} else {
					Map<String, Integer> valMap = new HashMap<String, Integer>();

					if (waitMap.containsKey("Not " + targetName)) {
						valMap = waitMap.get("Not " + targetName);
					}
					
					if (valMap.containsKey(entry.getValue())) {
						int count = valMap.get(entry.getValue());
						valMap.put(entry.getValue(), count + 1);
					} else {
						valMap.put(entry.getValue(), 1);
					}
					
					waitMap.put("Not " + targetName, valMap);
					map.put(entry.getKey(), waitMap);
				}
			}
		}
		return map;
	}

//	public static Map<String, Map<String, String>> makeProbabilityMap(Map<String, Map<String, Integer>> input) {
//		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
//
//		for (Entry<String, Map<String, Integer>> entry : input.entrySet()) {
//		}
//		return map;
//	}
}
