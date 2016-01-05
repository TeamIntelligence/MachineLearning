package models;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class TreeNode {

	private String												parentStepValue;
	private ArrayList<TreeNode> 								children;
	private Entry<String, Map<String, Map<String, Integer>>>	nodeValues;
	
	public TreeNode(String parentStepValue, Entry<String, Map<String, Map<String, Integer>>> nodeValues) {
		this.setParentStepValue(parentStepValue);
		this.setNodeValues(nodeValues);
	}
	
	public void addNode(TreeNode node) {
		if(children == null) {
			children = new ArrayList<TreeNode>();
		}
		
		children.add(node);
	}
	
	public ArrayList<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<TreeNode> children) {
		this.children = children;
	}

	public Entry<String, Map<String, Map<String, Integer>>> getNodeValues() {
		return nodeValues;
	}

	public void setNodeValues(Entry<String, Map<String, Map<String, Integer>>> nodeValues) {
		this.nodeValues = nodeValues;
	}
	
	public String getParentStepValue() {
		return parentStepValue;
	}

	public void setParentStepValue(String parentStepValue) {
		this.parentStepValue = parentStepValue;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if(parentStepValue != null && !parentStepValue.equals(""))
			sb.append("(").append(parentStepValue).append(") - ");

		sb.append(nodeValues.getKey());
		
		return sb.toString();
	}
}
