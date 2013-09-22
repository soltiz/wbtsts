package com.thales.services.cvf.javafirst;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;



public class GCCanvas extends Canvas {

    	 
	        /**
	 * 
	 */
	private static final long serialVersionUID = -1999239504241405962L;
			GraphicsConfiguration gc; 
	        Rectangle bounds; 
	     
	        public GCCanvas(GraphicsConfiguration gc) { 
	            super(gc); 
	            this.gc = gc; 
	            bounds = gc.getBounds(); 
	        } 
	     
	        public Dimension getPreferredSize() { 
	            return new Dimension(300, 150); 
	        } 
	     
	        public void paint(Graphics g) { 
	            g.setColor(Color.red); 
	            g.fillRect(0, 0, 100, 150); 
	            g.setColor(Color.green); 
	            g.fillRect(100, 0, 100, 150); 
	            g.setColor(Color.blue); 
	            g.fillRect(200, 0, 100, 150); 
	            g.setColor(Color.black); 
	            g.drawString("ScreenSize="+ 
	               Integer.toString(bounds.width)+ 
	               "X"+ Integer.toString(bounds.height), 10, 15); 
	            g.drawString(gc.toString(), 10, 30); 
	        } 
}
