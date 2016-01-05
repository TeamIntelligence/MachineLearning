package views;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import models.AbstractViewModel;
import models.DecisionTreeModel;
import models.TreeNode;

public class DecisionTreeView extends JPanel implements ViewInterface {

	private static final long serialVersionUID = 1L;
	private DecisionTreeModel model;

	public DecisionTreeView() {
		super(new BorderLayout());

		this.createLayout();
		this.model = new DecisionTreeModel(null, null);
	}

	@Override
	public void createLayout() {
		removeAll();
		
		if(this.model != null) {
			TreeNode treeNode = this.model.getTree();
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
			
			printNodeTree(root, treeNode);
			JTree tree = new JTree(root);
			expandAll(tree);
			
			add(tree);
		}
		
		repaint();
		revalidate();
	}

	@Override
	public void refresh() {
		createLayout();
	}

	private void expandAll(JTree tree) {
		int row = 0;
		while (row < tree.getRowCount()) {
			tree.expandRow(row);
			row++;
		}
	}

	private void printNodeTree(DefaultMutableTreeNode root, TreeNode treeNode) {
		if (treeNode.getChildren() == null) {
			root.add(new DefaultMutableTreeNode(treeNode.toString()));
			return;
		}

		DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode(treeNode.toString());
		for (TreeNode t : treeNode.getChildren()) {
			printNodeTree(newRoot, t);
		}

		root.add(newRoot);
	}

	@Override
	public void setModel(AbstractViewModel baseData) {
		model.setBaseData(baseData);
		refresh();
	}
}
