package com.thales.services.cvf.javafirst;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class firstdrawer extends Applet {
	 public firstdrawer() { 
	        main(null); 
	    } 
	   
	 
	    public static void main(String[] argv) { 
	        GraphicsEnvironment ge =  
	           GraphicsEnvironment.getLocalGraphicsEnvironment(); 
	        GraphicsDevice[] gs = ge.getScreenDevices(); 
	        for (int j = 0; j < gs.length; j++) { 
	          GraphicsDevice gd = gs[j]; 
	          GraphicsConfiguration[] gc =  
	             gd.getConfigurations(); 
	             for (int i=0; i < gc.length; i++) { 
	               JFrame f =  
//	                  new JFrame(gd.getDefaultConfiguration()); 
	                  new JFrame(gc[i]); 
	               GCCanvas c = new GCCanvas(gc[i]); 
	               Rectangle gcBounds = gc[i].getBounds(); 
	               int xoffs = gcBounds.x; 
	               int yoffs = gcBounds.y; 
	               f.getContentPane().add(c); 
	               f.setTitle("Screen# "+Integer.toString(j)+", GC# "+Integer.toString(i)); 
	               f.setSize(300+100, 150+100); 
	               f.setLocation((i*50)+xoffs+100, (i*60)+yoffs); 
	               f.show(); 
	             } 
	        } 
	    } 
	/**
	 * @param args
	 */
}
