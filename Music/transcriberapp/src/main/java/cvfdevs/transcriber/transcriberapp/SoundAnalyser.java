package cvfdevs.transcriber.transcriberapp;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Hello world!
 *
 */
public class SoundAnalyser 
{
	
	 private static void createAndShowGUI() {
	        //Create and set up the window.
	        JFrame frame = new AudioCapture();
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        //Add the ubiquitous "Hello World" label.
	        JLabel label = new JLabel("Hello World");
	        frame.getContentPane().add(label);
	        
	        
	        JTextField frequency = new JTextField("440");
	        frame.getContentPane().add(frequency);
	        

	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	        
	        
	        
		  
	
	    }
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        createAndShowGUI();
    }
}
