package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

import main.MapMaker;
import models.ZeroRModel;
import services.CsvReader;
import views.ZeroRView;

public class ZeroRController implements ActionListener {
	
	private ZeroRModel model;
	private ZeroRView view;
	
	public ZeroRController(ZeroRView view) {
		this.view = view;
		
		view.getOpenFileBtn().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton openFileBtn = view.getOpenFileBtn();
		JTextArea logTextArea = view.getLogTextArea();
	    JFileChooser fileChooser = view.getFileChooser();
		
		//Handle open button action.
		if (e.getSource() == openFileBtn) {
			int returnVal = fileChooser.showOpenDialog(view);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				CsvReader csvReader = new CsvReader(file.getPath());
				this.model = new ZeroRModel(csvReader.readCsv());
				
				Map<String, Map<String, Map<String, Integer>>> counts = MapMaker.makeCountMap(model.getData(), "wait");

				for (Entry<String, Map<String, Map<String, Integer>>> entry : counts.entrySet()) {
					logTextArea.append(entry.getKey() +  "\n");
					for (Entry<String, Map<String, Integer>> wait : entry.getValue().entrySet()) {
						logTextArea.append("\t" + wait + "\n");
					}
				}
			} else {
				logTextArea.append("Open command cancelled by user.\n");
			}
			logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
		}
	}
}
