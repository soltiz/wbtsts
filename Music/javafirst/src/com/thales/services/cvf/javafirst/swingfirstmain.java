package com.thales.services.cvf.javafirst;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class swingfirstmain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    JFrame frame = new JFrame("HelloWorldSwing");
	    final JLabel label = new JLabel("Hello World");
	    frame.getContentPane().add(label);

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	    label.setText("bingo - 0");
	    Timer t = new Timer();
	    TimerTask task = new TimerTask () {
	    	private int counter=0;
			@Override
			public void run() {
					counter=counter+1;
					label.setText("timer - "+ counter);
				// TODO Auto-generated method stub
				
			}};
	    
	    t.schedule(task, 50, 50);
	    
	    
	    JFrame ac=new AudioCapture();
	    ac.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    ac.pack();
	    ac.setVisible(true);
	    
	}

}
