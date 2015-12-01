package main;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import views.BodyView;
import views.TopBarView;

public class Application {
    
	public static void main(String[] args){
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
        
        //Add content to the window.
        mainLayout.add(new TopBarView(), BorderLayout.PAGE_START);
        mainLayout.add(new BodyView(), BorderLayout.CENTER);
        
        frame.add(mainLayout);
        
        //Display the window.
        frame.setVisible(true);
    }
}
