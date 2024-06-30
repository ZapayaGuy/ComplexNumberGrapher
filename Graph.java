import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JPanel;

public class Graph extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private static final double TICK_SIZE_RELATIVE = 0.1;
	public static final int DEFAULT_Z_MAXIMUM_VALUE = 50;
	
	private double zMaximum = DEFAULT_Z_MAXIMUM_VALUE;
	private double zMinimum = 0;
	
	private double calculationInterval = 0.1;
	
	private static final double SCALE_AMOUNT = 1;
	private static final double MIN_SCALE = 0.01;
	private static final double MAX_SCALE = 1000;
	public static final int PANEL_SIZE = 720;
	private static final double DEFAULT_GRAPH_BOUNDS = 10;
		
	private double scale = PANEL_SIZE/DEFAULT_GRAPH_BOUNDS;
	
	private static double xTranslation = 0;
	private static double yTranslation = 0;

	private ComplexFunction[] functions = new ComplexFunction[FunctionsManager.MAX_FUNCTIONS];
	
	public Graph() {		
        setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
	}
	
	@Override
	public void paintComponent(Graphics g){
	    super.paintComponent(g);
	    
	    //background
	    g.setColor(Color.WHITE);
	    g.fillRect(0,  0, PANEL_SIZE, PANEL_SIZE);
	    
	    //axises
	    g.setColor(Color.BLACK);
	    g.drawLine(ctsx(0), 0, ctsx(0), PANEL_SIZE);
	    g.drawLine(0, ctsy(0), PANEL_SIZE, ctsy(0));
	    	    	    
	    //draw the grid lines
    	double maxBound = Math.max(Math.max(getXLowerBound(), getXUpperBound()), Math.max(getYLowerBound(), getYUpperBound()));
	    for(int i = 1; i < maxBound; i++) {
	    	
	    	double x = i;
	    		    		    	
	    	g.drawLine(
	    		ctsx(x),
	    		ctsy(TICK_SIZE_RELATIVE),
	    		ctsx(x),
	    		ctsy(-TICK_SIZE_RELATIVE)
	    	);	    	
	    	g.drawLine(
	    		ctsx(-x),
	    		ctsy(TICK_SIZE_RELATIVE),
	    		ctsx(-x),
	    		ctsy(-TICK_SIZE_RELATIVE)
	    	);
	    	
	    	double y = i;
	    	
	    	g.drawLine(
	    		ctsx(TICK_SIZE_RELATIVE),
	    		ctsy(y),
	    		ctsx(-TICK_SIZE_RELATIVE),
	    		ctsy(y)
	    	);	    	
	    	g.drawLine(
	    		ctsx(TICK_SIZE_RELATIVE),
	    		ctsy(-y),
	    		ctsx(-TICK_SIZE_RELATIVE),
	    		ctsy(-y)
	    	);	
	    	
	    	g.setFont(new Font("courier new", 0, (int) (scale/3)));
	    	g.drawString(String.format("%1.0f", x), ctsx(x), ctsy(TICK_SIZE_RELATIVE * 5));
	    	g.drawString(String.format("-%1.0f", x), ctsx(-x), ctsy(TICK_SIZE_RELATIVE * 5));
	    	g.drawString(String.format("-%1.0fi", y), ctsx(TICK_SIZE_RELATIVE * 5), ctsy(y));
	    	g.drawString(String.format("%1.0fi", y), ctsx(TICK_SIZE_RELATIVE * 5), ctsy(-y));
	    }
	    
	    //draw the graph
	    for(int i = 0; i < functions.length; i++) {
	    	ComplexFunction f = functions[i];
	    	
	    	if(f == null) {
	    		continue;
	    	}
	    
	    	g.setColor(Color.getHSBColor((float)i/functions.length, 1, 1));
	    	
		    for(double j = zMinimum; j < zMaximum; j += calculationInterval) {
		    	if(f.evaluate(0) == null) {
		    		continue;
		    	}
		    	
		    	double pj = j-calculationInterval;
		    	
		    	g.drawLine(
		    		ctsx(f.evaluate(j).re),
		    		ctsy(f.evaluate(j).im),
		    		ctsx(f.evaluate(pj).re),
		    		ctsy(f.evaluate(pj).im)
		    	);
		    }
	    }
	}
	
	private int ctsx(double x){
		return (int) (x * scale + xTranslation + PANEL_SIZE/2);
	}
	private int ctsy(double y){
		return (int)(y * scale + yTranslation + PANEL_SIZE/2);
	}
	
	private double getXUpperBound(){
		return (-xTranslation + PANEL_SIZE/2)/scale;
	}
	private double getXLowerBound(){
		return (xTranslation + PANEL_SIZE/2)/scale;
	}
	private double getYUpperBound(){
		return (-yTranslation + PANEL_SIZE/2)/scale;
	}
	private double getYLowerBound(){
		return (yTranslation + PANEL_SIZE/2)/scale;
	}
	
	public void setZMaximum(double zMaximum) {
		this.zMaximum = zMaximum;
	}
	
	public void handleMouseWheelMoved(MouseWheelEvent e) {
		scale += e.getWheelRotation() * SCALE_AMOUNT;
				
		if(scale <= MIN_SCALE) {
			scale = MIN_SCALE;
		}else if(scale >= MAX_SCALE) {
			scale = MAX_SCALE;
		}
		
		this.repaint();

	}
	
	private int px, py;
	public void handleMousePressed(MouseEvent e) {
		px = e.getX();
		py = e.getY();
		
	}
	public void handleMouseDragged(MouseEvent e) {
		xTranslation += e.getX()-px;
		yTranslation += e.getY()-py;
		
		px = e.getX();
		py = e.getY();
				
		this.repaint();
	}

	public void setZMinimum(double zMinimum) {
		this.zMinimum = zMinimum;
	}	
	public void setCalculationInterval(double calculationInterval) {
		this.calculationInterval = calculationInterval;
	}
	
	public void setFunction(String expression, int index) {	
		expression = expression.replaceAll("x", "z");
		
    	functions[index] = new ComplexFunction(expression);
	}
}
