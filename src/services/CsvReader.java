package services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import models.AbstractViewModel;

public class CsvReader {
	
	private String filePath;
	private String csvSplitBy = ",";
	
	public CsvReader(String filePath) {
		this.filePath = filePath;
	}
	
	public CsvReader(String filePath, String csvSplitBy) {
		this.filePath = filePath;
		this.csvSplitBy = csvSplitBy;
	}

	public AbstractViewModel readCsv() {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Map<String, List<String>> columns = new HashMap<String, List<String>>();
		
		
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(filePath));
			boolean firstRow = true;
			
			while ((line = br.readLine()) != null) {
				Map<String, String> row = new HashMap<String, String>();
				
				String[] args = line.split(csvSplitBy);
				for(int i = 0; i < args.length; i++){
					args[i] = args[i].trim();
				}
				
				if(firstRow) {
					for(String colName : args) {
						columns.put(colName, new ArrayList<String>());
					}
					
					firstRow = false;
					continue;
				} 
				
				int j = 0;
				for(Entry<String, List<String>> entry : columns.entrySet()) {
					String colName = entry.getKey();
					ArrayList<String> values = (ArrayList<String>) entry.getValue();
					
					if(!values.contains(args[j])) {
						values.add(args[j]);
					}
					
					row.put(colName, args[j]);
					j++;
				}
				
				data.add(row);
			}
			
			for(Entry<String, List<String>> cols : columns.entrySet()) {
				Collections.sort(cols.getValue());
			}
			
			System.out.println(columns);

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
		
		return new AbstractViewModel(data, columns);
	}
}
