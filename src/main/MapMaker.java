package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.Restaurant;

public class MapMaker {
	public static Map<String, Map<String, Map<String, Integer>>> makeCountMap(List<Restaurant> input) {
		
		Map<String, Map<String, Map<String, Integer>>> map = new HashMap<String, Map<String, Map<String, Integer>>>();
		
		for (Restaurant r : input) {
			Map<String, String> resMap = r.toMap();
			
			for (Entry<String, String> entry : resMap.entrySet()) {
				Map<String, Map<String, Integer>> waitMap = new HashMap<String, Map<String, Integer>>();
				
				if (map.containsKey(entry.getKey())) {
					waitMap = map.get(entry.getKey());
				}
				
				if (resMap.get("wait").equals("true")) {
					Map<String, Integer> valMap = new HashMap<String, Integer>();

					if (waitMap.containsKey("will wait")) {
						valMap = waitMap.get("will wait");
					}
					
					if (valMap.containsKey(entry.getValue())) {
						int count = valMap.get(entry.getValue());
						valMap.put(entry.getValue(), count + 1);
					} else {
						valMap.put(entry.getValue(), 1);
					}
					
					waitMap.put("will wait", valMap);
					map.put(entry.getKey(), waitMap);
				
				} else {
					Map<String, Integer> valMap = new HashMap<String, Integer>();

					if (waitMap.containsKey("will not wait")) {
						valMap = waitMap.get("will not wait");
					}
					
					if (valMap.containsKey(entry.getValue())) {
						int count = valMap.get(entry.getValue());
						valMap.put(entry.getValue(), count + 1);
					} else {
						valMap.put(entry.getValue(), 1);
					}
					
					waitMap.put("will not wait", valMap);
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
