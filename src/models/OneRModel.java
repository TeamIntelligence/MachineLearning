package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import services.MapMaker;

public class OneRModel extends AbstractViewModel {
	private AbstractViewModel 	 							baseData;
	private Map<String, Double>  							probs;
	private double 				 							highestProb;
	private List<String> 		 							highestProbs;
	private Map<String, Map<String, Map<String, Integer>>>  oneRMap;
	
	public OneRModel(List<Map<String, String>> data, Map<String, List<String>> columns) {
		super(data, columns);
	}
	
	public OneRModel(AbstractViewModel baseData) {
		super(baseData.getData(), baseData.getColumns(null));
		this.baseData = baseData;
	}
	
	public void createOneRMap() {
		this.setOneRMap(MapMaker.createTotalCountMap(this.getData(), this.getColumns(null), this.getTargetColumn()));
	}
	
	public Map<String, Map<String, Map<String, Integer>>> getOneRMap() {
		return oneRMap;
	}

	public void setOneRMap(Map<String, Map<String, Map<String, Integer>>> oneRMap) {
		this.oneRMap = oneRMap;
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
	}
}
