package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import services.MapEntry;
import services.MapMaker;

public class DecisionTreeModel extends AbstractViewModel {
	
	private AbstractViewModel 	 							baseData;
	private Map<String, Map<String, Map<String, Integer>>>  totalCountMap;
	private TreeNode										tree;
    private Random 											randomGenerator;
	
	public DecisionTreeModel(List<Map<String, String>> data, Map<String, List<String>> columns) {
		super(data, columns);
		
        randomGenerator = new Random();
	}
	
	public DecisionTreeModel(AbstractViewModel baseData) {
		super(baseData.getData(), baseData.getColumns(null));
		this.baseData = baseData;
	}
	
	private double CalculateEntropy(HashMap<String, Integer> values) {
		int total = 0;
		for(Entry<String, Integer> value : values.entrySet()) {
			total += value.getValue();
		}
		
		Map<String, Double> ratios = new HashMap<String, Double>();
		for(Entry<String, Integer> value : values.entrySet()) {
			ratios.put(value.getKey(), (double)value.getValue() / total);
		}
		
		for(Entry<String, Double> ratio : ratios.entrySet()) {
			double ratioValue = ratio.getValue();
			if(Math.abs(ratioValue) > 0.1) {
				ratios.put(ratio.getKey(), -ratioValue * MathLog(ratioValue, 2));
			}
		}
		
		double returnValue = 0.00;
		for(Entry<String, Double> ratio : ratios.entrySet()) {
			returnValue += ratio.getValue();
		}

        return returnValue;
    }
	
	private HashMap<String, Integer> GetEntropyCounts(String colName, String value) {
		Map<String, Integer> counts = new HashMap<String, Integer>();
		Map<String, Integer> valueCounts = getColumnValueCounts(colName, value);
		
		for(Entry<String, Integer> entryCount : valueCounts.entrySet()) {
			if(entryCount.getKey().equals("total"))
				continue;
			
			counts.put(entryCount.getKey(), entryCount.getValue());
		}
		
		
		return (HashMap<String, Integer>) counts;
    }
	
	private double CalculateGain(List<Map<String, String>> data, String colName) {
		List<String> attributes	= this.getUniqueValuesByColumn(colName);
		double total 			= 0.00;
		
		for(String attribute : attributes) {
			HashMap<String, Integer> values = GetEntropyCounts(colName, attribute);
			double entropy 					= CalculateEntropy(values);
			
			int sumAttribute = 0;
			for(Entry<String, Integer> value : values.entrySet()) {
				sumAttribute += value.getValue();
			}
			
			total += -(double)sumAttribute / this.getData().size() * entropy;
		}
		
		double totalEntropy = CalculateEntropy((HashMap<String, Integer>) MapMaker.createTargetColCountMap(data, this.getColumns(data), this.getTargetColumn()));
		return totalEntropy + total;
    }
	
	private Entry<String, Map<String, Map<String, Integer>>> GetBestColumn(List<Map<String, String>> data, String targetColumn) {
        double maxGain 												= 0.00;
        Entry<String, Map<String, Map<String, Integer>>> result 	= null;
		Map<String, Map<String, Map<String, Integer>>> localTotalCountMap = MapMaker.createTotalCountMap(data, this.getColumns(data), this.getTargetColumn());
		
		for(Entry<String, Map<String, Map<String, Integer>>> column : localTotalCountMap.entrySet()) {
			if(column.getKey().equals(targetColumn) || column.getKey().equals(this.getTargetColumn())) {
				continue;
			}
			
			double gain = CalculateGain(data, column.getKey());
			if(gain <= maxGain)
				continue;
			
			maxGain = gain;
            result = column;
        }
		
        return result;
    }
	
	private boolean CheckAllValuesSame(String colName, String value) {
		int count = 0;
		Map<String, Integer> valueCounts = getColumnValueCounts(colName, value);
		
		for(Entry<String, Integer> entryCount : valueCounts.entrySet()) {
			if(entryCount.getKey().equals("total"))
				continue;
			
			if(entryCount.getValue() > 0) {
				count++;
			}
		}
		
		return (count <= 1);
	}
	
	private Map<String, Integer> getColumnValueCounts(String colName, String value) {
		// res={no={no=4, total=7, yes=3}, yes={no=2, total=5, yes=3}}
		Map<String, Map<String, Integer>> entryCounts = this.totalCountMap.get(colName);
		
		// value = no
		// no={no=4, total=7, yes=3}
		return entryCounts.get(value);
	}
	
	private String getColumnAllValue(Map<String, Integer> values) {
		for(Entry<String, Integer> entry : values.entrySet()) {
			if(entry.getKey() != "total" && entry.getValue() == values.get("total")) {
				return entry.getKey();
			}
		}
		
		return null;
	}
	
	private String getMostCommonTargetValue(Entry<String, Map<String, Integer>> values) {
		int count = 0;
		ArrayList<String> commonValues = new ArrayList<String>();
		String commonTargetValue = "";
		
		if(values != null && values.getValue() != null) {
			for(Entry<String, Integer> value : values.getValue().entrySet()) {
				if(value.getKey().equals("total")) {
					continue;
				}
				
				if(value.getValue() > count) {
					count = value.getValue();
				}
			}
			System.out.println(values);
			for(Entry<String, Integer> value : values.getValue().entrySet()) {
				if(value.getKey().equals("total")) {
					continue;
				}
				
				if(value.getValue() == count) {
					commonValues.add(value.getKey());
				}
			}
			
			int index 				 = randomGenerator.nextInt(commonValues.size());
			commonTargetValue = commonValues.get(index);
		}
		
		return commonTargetValue;
	}
	
	private TreeNode BuildTree(List<Map<String, String>> data, String colName, String parentStepValue, Entry<String, Map<String, Integer>> values, String mostCommonValue) {
		if(values != null && CheckAllValuesSame(colName, values.getKey())) {
			return new TreeNode(parentStepValue, new MapEntry<String, Map<String, Map<String, Integer>>>(getColumnAllValue(values.getValue()), null));
		}
		
		if(values != null && data.size() == 0)  {
			return new TreeNode(parentStepValue, new MapEntry<String, Map<String, Map<String, Integer>>>(mostCommonValue, null));
		}
		
		
		Entry<String, Map<String, Map<String, Integer>>> bestColumn = GetBestColumn(data, colName);
		
		if(bestColumn == null) {
			return new TreeNode(parentStepValue, new MapEntry<String, Map<String, Map<String, Integer>>>(getColumnAllValue(values.getValue()), null));
		}
		
		TreeNode rootNode = new TreeNode(parentStepValue, bestColumn);
		String mostCommonTargetValue = getMostCommonTargetValue(values);
		
		for(Entry<String, Map<String, Integer>> nodeEntry : bestColumn.getValue().entrySet()) {
			
			// Create subset
			List<Map<String, String>> subset = new ArrayList<Map<String, String>>();
			
			for(Map<String, String> row : data) {
				if(row.get(bestColumn.getKey()).equals(nodeEntry.getKey())) {
					Map<String, String> newRow = new HashMap<String, String>(row);
					subset.add(newRow);
				}
			}
			
			for(Map<String, String> row : subset) {
				row.remove(bestColumn.getKey());
			}
			
			rootNode.addNode(BuildTree(subset, bestColumn.getKey(), nodeEntry.getKey(), nodeEntry, mostCommonTargetValue));
		}
		
		return rootNode;
	}
	
	public AbstractViewModel getBaseData() {
		return baseData;
	}
	
	public void setBaseData(AbstractViewModel baseData) {
		this.baseData = baseData;
		
		this.setColumns(baseData.getColumns(null));
		this.setData(baseData.getData());
		this.setTargetColumn(baseData.getTargetColumn());
		this.setUniqueValuesCount();

		this.totalCountMap = MapMaker.createTotalCountMap(this.getData(), this.getColumns(null), this.getTargetColumn());
		this.setTree(BuildTree(this.getData(), this.getTargetColumn(), null, null, null));
	}
	
	private double MathLog(double value, int base) {
		return Math.log(value) / Math.log(base);
	}

	public TreeNode getTree() {
		return tree;
	}

	public void setTree(TreeNode tree) {
		this.tree = tree;
	}
}
