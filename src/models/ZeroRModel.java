package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import services.MapMaker;

public class ZeroRModel extends AbstractViewModel {
	
	private AbstractViewModel 	 baseData;
	private Map<String, Integer> counts;
	private Map<String, Double>  probs;
	private double 				 highestProb;
	private List<String> 		 highestProbs;
	
	public ZeroRModel(AbstractViewModel baseData) {
		super(baseData.getData(), baseData.getColumns(null));
		
		this.baseData = baseData;
	}
	
	public ZeroRModel(List<Map<String, String>> data, Map<String, List<String>> columns) {
		super(data, columns);
	}
	
	public void createCountMap() {
		this.counts = MapMaker.createTargetColCountMap(this.getData(), this.getColumns(null), this.getTargetColumn());
	}
	
	/**
	 * Does the probability calculations
	 */
	public void createProbsMap() {
		if(this.counts == null) {
			createCountMap();
		}
		
		Map<String, Double> result = new HashMap<String, Double>();
		
		// Make the counts and get the highest probability found 
		double highestProb = 0.00;
		double size = (double) this.getData().size();
		for(Entry<String, Integer> entry : counts.entrySet()) {
			double value = (double) entry.getValue();
			result.put(entry.getKey(), value / size);
			
			if(value/size > highestProb) {
				highestProb = value/size;
			}
		}
		
		// Fill the highest probilities 
		List<String> highestProbs = new ArrayList<String>();
		for(Entry<String, Integer> entry : counts.entrySet()) {
			double value = (double) entry.getValue();
			
			if(value/size == highestProb) {
				highestProbs.add(entry.getKey());
			}
		}
		
		this.highestProbs = highestProbs;
		this.highestProb  = highestProb;
		this.probs 		  = result;
	}

	public AbstractViewModel getBaseData() {
		return baseData;
	}
	
	public Map<String, Integer> getCounts() {
		return counts;
	}

	public Map<String, Double> getProbs() {
		return probs;
	}

	public double getHighestProb() {
		return highestProb;
	}

	public List<String> getHighestProbs() {
		return highestProbs;
	}

	public void setCounts(Map<String, Integer> counts) {
		this.counts = counts;
	}

	public void setProbs(Map<String, Double> probs) {
		this.probs = probs;
	}

	public void setHighestProb(double highestProb) {
		this.highestProb = highestProb;
	}

	public void setHighestProbs(List<String> highestProbs) {
		this.highestProbs = highestProbs;
	}

	public void setBaseData(AbstractViewModel baseData) {
		this.baseData = baseData;
		
		this.setColumns(baseData.getColumns(null));
		this.setData(baseData.getData());
		this.setTargetColumn(baseData.getTargetColumn());
	}
}
