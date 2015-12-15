package main;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import views.BodyView;
import views.TopBarView;

public class Application {
	
public static final DecimalFormat df = new DecimalFormat("0.000");

	public static void main(String[] args){
		df.setRoundingMode(RoundingMode.CEILING);
	    //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
	}

    /**
     * Create the GUI and show it.  
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Application Machine Learning");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        
        JPanel mainLayout = new JPanel();
        mainLayout.setLayout(new BorderLayout());
        
        BodyView bodyView = new BodyView();
        
        //Add content to the window.
        mainLayout.add(new TopBarView(bodyView), BorderLayout.PAGE_START);
        mainLayout.add(bodyView, BorderLayout.CENTER);
        
        frame.add(mainLayout);
        
        //Display the window.
        frame.setVisible(true);
    }
}
