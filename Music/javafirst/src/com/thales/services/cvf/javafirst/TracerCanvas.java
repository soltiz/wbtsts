package com.thales.services.cvf.javafirst;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.complex.Complex;

public class TracerCanvas extends Canvas {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6997710012278944383L;

	GraphicsConfiguration gc; 
    Rectangle bounds; 
 
    public TracerCanvas(GraphicsConfiguration gc) { 
        super(gc); 
        this.gc = gc; 
        bounds = gc.getBounds(); 
    } 
	
	public Dimension getPreferredSize() { 
        return new Dimension(800, 100); 
    } 
    
    List<Integer> data = new ArrayList<Integer>();
    
    public void addSample(final Integer sample) {
    	data.add(new Integer(sample));
    }
    
    public void setData(Complex[] newdata) {
    	data = new ArrayList<Integer>();
    	for (int l=0; l<newdata.length; l++) {
    		addSample(Double.valueOf(newdata[l].getReal()).intValue()/10000);
    	}
    }
 
    public void paint(Graphics g) { 
    	final int maxw=800;
    	g.setColor(Color.red); 
    	for (int l=0; (l<data.size()) && (l<maxw);l++) {
        	g.drawLine(l, 100, l, 100-data.get(l));
    	} 
    } 
}
