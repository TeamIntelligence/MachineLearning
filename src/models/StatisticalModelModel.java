package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.Application;
import views.OneRView;

public class StatisticalModelModel extends OneRModel {
	
	private Map<String, String> 		labelStrings;
	private Map<String, Double> 		results;
	
	public StatisticalModelModel(AbstractViewModel baseData) {
		super(baseData.getData(), baseData.getColumns());
	}
	
	public StatisticalModelModel(List<Map<String, String>> data, Map<String, List<String>> columns) {
		super(data, columns);
	}
	
	public void createStatisticalModel(Map<String, String> valMap) {
		String targetCol   										= this.getTargetColumn();
		List<String> targetUniqueValues 						= this.getColumns().get(targetCol);
		Map<String, Map<String, Map<String, Integer>>> oneRMap 	= this.getOneRMap();
		double totalChance 										= 0.00;
		int targetTotal 										= this.getBaseData().getData().size();
		Map<String, String> labelStrings						= new HashMap<String, String>();
		Map<String, Double> results								= new HashMap<String, Double>();
		
		for(String targetVal : targetUniqueValues){
			if(targetVal.equals(OneRView.total)){
				continue;
			}
			
			int targetCount 		= oneRMap.get(targetCol).get(targetVal).get(targetVal);
			StringBuilder sb 		= new StringBuilder();
			List<Integer> counts 	= new ArrayList<Integer>();
			double result 			= 1.00;
			
			sb.append(String.format("%1$s = ", targetVal) );
			for(Entry<String,String> entry : valMap.entrySet()){
				int count = oneRMap.get(entry.getKey()).get(entry.getValue()).get(targetVal);
				double colRes = (double) count/targetCount != 0 ? (double) count/targetCount : 1.000;
				
				counts.add(count);
				sb.append(String.format(" %1$s (%2$d/%3$d) *", Application.df.format(colRes), count, targetCount) );
				result *= colRes;
			}
			
			result *= (double) targetCount/targetTotal;
			totalChance += result;
			
			// Remove the last *
			sb.append(String.format(" %1$s (%2$d/%3$d) = %4$s", Application.df.format((double) targetCount/targetTotal), targetCount, targetTotal, Application.df.format(result)));
			
			results.put(targetVal, result);
			labelStrings.put(targetVal, sb.toString());
		}
		results.put("total", totalChance);
		
		this.results = results;
		this.labelStrings = labelStrings;
	}
	
	public void setOneRModel(OneRModel baseOneRModel){
		this.setOneRMap(baseOneRModel.getOneRMap());
	}
	public Map<String, String> getLabelStrings() {
		return labelStrings;
	}

	public void setLabelStrings(Map<String, String> labelStrings) {
		this.labelStrings = labelStrings;
	}

	public Map<String, Double> getResults() {
		return results;
	}

	public void setResults(Map<String, Double> results) {
		this.results = results;
	}
}