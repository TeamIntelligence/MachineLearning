package services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		List<String> columns = new ArrayList<String>();
		
		
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
						columns.add(colName);
					}
					
					firstRow = false;
					continue;
				} 
				
				int j = 0;
				for(String colName : columns) {
					row.put(colName, args[j]);
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
		
		return new AbstractViewModel(data, columns);
	}
}
