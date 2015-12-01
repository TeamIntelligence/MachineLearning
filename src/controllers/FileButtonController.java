package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import models.ZeroRModel;
import services.CsvReader;
import views.ZeroRView;

public class FileButtonController implements ActionListener {

	private ZeroRController controller;
	
	public FileButtonController(ZeroRController controller) {
		this.controller = controller;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ZeroRView view = controller.getView();
		ZeroRModel model = controller.getModel();
		
		JButton openFileBtn = view.getOpenFileBtn();
		JTextArea logTextArea = view.getLogTextArea();
	    JFileChooser fileChooser = view.getFileChooser();
		
		//Handle open button action.
		if (e.getSource() == openFileBtn) {
			int returnVal = fileChooser.showOpenDialog(view);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				CsvReader csvReader = new CsvReader(file.getPath());
				model = new ZeroRModel(csvReader.readCsv());

		        JPanel columnPanel = view.getColumnPanel();
		        
				for(String colName : model.getColumns()) {
			        JButton colNameButton = new JButton(colName);
			        colNameButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							
						}
			        });
			        columnPanel.add(colNameButton); 
				}

				columnPanel.setVisible(true);
				view.getRootWindow().pack();
				
				/*
				Map<String, Map<String, Map<String, Integer>>> counts = MapMaker.makeCountMap(model.getData(), "wait");
				for (Entry<String, Map<String, Map<String, Integer>>> entry : counts.entrySet()) {
					logTextArea.append(entry.getKey() +  "\n");
					for (Entry<String, Map<String, Integer>> wait : entry.getValue().entrySet()) {
						logTextArea.append("\t" + wait + "\n");
					}
				}
				*/
			} else {
				logTextArea.append("Open command cancelled by user.\n");
			}
			logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
		}
	}
}
