//package view;
//
//import java.awt.BorderLayout;
//import java.awt.Insets;
//import java.io.File;
//
//import javax.swing.JButton;
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
//
//import main.Application;
//
//public class View extends JPanel{
//	   
//    private JFrame frame;
//    private JLabel label;
//    private JButton button;
//    JButton openButton;
//    JTextArea log;
//    JFileChooser fc;
//    
//    public View() {
//        
//    	super(new BorderLayout());
//    	
////    	frame = new JFrame("View");                                    
////        frame.getContentPane().setLayout(new BorderLayout());                                          
////        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           
////        frame.setSize(200,200);        
////        frame.setVisible(true);
////        
////        label = new JLabel(text);
////        frame.getContentPane().add(label, BorderLayout.CENTER);
////        
////        button = new JButton("Button");        
////        frame.getContentPane().add(button, BorderLayout.SOUTH); 
//
//        //Create the log first, because the action listeners
//        //need to refer to it.
//        log = new JTextArea(20,100);
//        log.setMargin(new Insets(5,5,5,5));
//        log.setEditable(false);
//        JScrollPane logScrollPane = new JScrollPane(log);
//
//        //Create a file chooser
//        fc = new JFileChooser();
//        File workingDirectory = new File(System.getProperty("user.dir") + "/resources");
//        fc.setCurrentDirectory(workingDirectory);
//        openButton = new JButton("Open a File...");
////        openButton.addActionListener(this);
//        
//        //For layout purposes, put the buttons in a separate panel
//        JPanel buttonPanel = new JPanel(); //use FlowLayout
//        buttonPanel.add(openButton); 
//        //Add the buttons and the log to this panel.
//        add(buttonPanel, BorderLayout.PAGE_START);
//        add(logScrollPane, BorderLayout.CENTER);
//        
//    }
//        
//    public JButton getButton(){
//        return openButton;
//    }
//    public JFileChooser getFc(){
//    	return fc;
//    }
//    /**
//     * Create the GUI and show it.  For thread safety,
//     * this method should be invoked from the
//     * event dispatch thread.
//     */
//    public static void createAndShowGUI() {
//        //Create and set up the window.
//        JFrame frame = new JFrame("Application Machine Learning");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// 
//        //Add content to the window.
//        frame.add(new View());
// 
//        //Display the window.
//        frame.pack();
//        frame.setVisible(true);
//    }
//}
