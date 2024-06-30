import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FunctionsManager extends JScrollPane{
	private static final long serialVersionUID = 1L;
	
	private static final Font DEFAULT_FONT = new Font("Courier new", 0, 13);
	
	public static final int MAX_FUNCTIONS = 10;
	private static final double Z_MAXIMUM = 100;
	private static final double Z_MINIMUM = -100;
	private static final int PANEL_WIDTH = 300;
	private static final int PANEL_HEIGHT = Graph.PANEL_SIZE;
	private static final int TEXT_AREA_HEIGHT = Graph.PANEL_SIZE/20;
	
	private static final int TEXT_AREA_MARGIN = 10;
	
	private JTextArea[] textAreas = new JTextArea[MAX_FUNCTIONS];
	
	private JPanel panel = new JPanel();
	private Graph graph;
	
	FunctionsManager(){
		//scroll bar setup
		this.setPreferredSize(new Dimension(PANEL_WIDTH+1, PANEL_HEIGHT));
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        MatteBorder border = new MatteBorder(0, 1, 0, 0, Color.BLACK);
        this.setBorder(border);

		//panel setup
		panel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		panel.setBorder(null);
		panel.setBackground(Color.WHITE);
        this.setViewportView(panel);
        
        //
        JPanel separator = new JPanel();
        separator.setBackground(Color.WHITE);
        separator.setPreferredSize(new Dimension(PANEL_WIDTH/2, 25));
        panel.add(separator);
        
        //slider setup
        addSliders();
        
        //
        JPanel separator1 = new JPanel();
        separator1.setBackground(Color.WHITE);
        separator1.setPreferredSize(new Dimension(1, 40));
        panel.add(separator1);
        
        //function area setup
        for(int i = 0; i < MAX_FUNCTIONS; i++) {   
        	textAreas[i] = new JTextArea();
        	textAreas[i].setFont(DEFAULT_FONT);
            textAreas[i].setPreferredSize(new Dimension(PANEL_WIDTH - TEXT_AREA_MARGIN * 2, TEXT_AREA_HEIGHT - TEXT_AREA_MARGIN * 2));
                        
            textAreas[i].setBackground(Color.getHSBColor(0, 0, 0.9f));
            textAreas[i].setBorder(new EmptyBorder(TEXT_AREA_MARGIN, TEXT_AREA_MARGIN, TEXT_AREA_MARGIN, TEXT_AREA_MARGIN));
            
            textAreas[i].setRows(1);
            textAreas[i].setLineWrap(false);

            
        	final int index = i;

        	textAreas[i].addKeyListener(new KeyListener() {
            	
				@Override
				public void keyTyped(KeyEvent e) {}

	            @Override
	            public void keyPressed(KeyEvent e) {
	                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	                    String text = textAreas[index].getText().trim(); // Get text from text area
	                    if (!text.isEmpty()) {
	                    	graph.setFunction(text, index);
	                    }
	                    
		                e.consume();
		                
		                graph.repaint();

	                    
	                }
	                

	            }

				@Override
				public void keyReleased(KeyEvent e) {}});
            
        	panel.add(textAreas[i]);
        }
        
        
	}

	private void addSliders() {
        //slider setup
        JLabel boundsSliderZMaximumSliderLabel = new JLabel("domain maximum (0 - " + Z_MAXIMUM + ")");
        boundsSliderZMaximumSliderLabel.setOpaque(false);
        boundsSliderZMaximumSliderLabel.setFont(DEFAULT_FONT);
        panel.add(boundsSliderZMaximumSliderLabel);
        
        JSlider boundsSliderZMaximum = new JSlider();
        boundsSliderZMaximum.setFont(DEFAULT_FONT);

        boundsSliderZMaximum.setMajorTickSpacing(10);
        boundsSliderZMaximum.setMinorTickSpacing(1);
        boundsSliderZMaximum.setValue(Graph.DEFAULT_Z_MAXIMUM_VALUE);
        boundsSliderZMaximum.setMaximum((int)Z_MAXIMUM);
        boundsSliderZMaximum.setPaintTicks(true);
        boundsSliderZMaximum.setPaintLabels(true);
        boundsSliderZMaximum.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();

				graph.setZMaximum(source.getValue());
				graph.repaint();
			}
        });
        boundsSliderZMaximum.setPreferredSize(new Dimension(PANEL_WIDTH, TEXT_AREA_HEIGHT));
        panel.add(boundsSliderZMaximum);

        
        
        
        
        JLabel boundsSliderZMinimumSliderLabel = new JLabel("domain minimum (" + Z_MINIMUM + " - 0)");
        boundsSliderZMinimumSliderLabel.setFont(DEFAULT_FONT);
        boundsSliderZMinimumSliderLabel.setOpaque(false);
        panel.add(boundsSliderZMinimumSliderLabel);
        
        JSlider boundsSliderZMinimum = new JSlider();
        boundsSliderZMinimum.setFont(DEFAULT_FONT);

        boundsSliderZMinimum.setMajorTickSpacing(10);
        boundsSliderZMinimum.setMinorTickSpacing(1);
        boundsSliderZMinimum.setMaximum(0);
        boundsSliderZMinimum.setMinimum((int)Z_MINIMUM);
        boundsSliderZMinimum.setPaintTicks(true);
        boundsSliderZMinimum.setPaintLabels(true);
        boundsSliderZMinimum.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();

				graph.setZMinimum(source.getValue());
				graph.repaint();
			}
        });
        boundsSliderZMinimum.setPreferredSize(new Dimension(PANEL_WIDTH, TEXT_AREA_HEIGHT));
        panel.add(boundsSliderZMinimum);

        JLabel calcuationIntervalSliderLabel = new JLabel("Calculation Interval (0.0 - 1.0)");
        calcuationIntervalSliderLabel.setFont(DEFAULT_FONT);
        panel.add(calcuationIntervalSliderLabel);
        
        JSlider calcuationIntervalSlider = new JSlider();
        calcuationIntervalSlider.setMajorTickSpacing(100);
        calcuationIntervalSlider.setMinorTickSpacing(10);
        calcuationIntervalSlider.setMaximum(1000);
        calcuationIntervalSlider.setMinimum(1);
        calcuationIntervalSlider.setPaintTicks(true);
        calcuationIntervalSlider.setPaintLabels(false);
        calcuationIntervalSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();

				graph.setCalculationInterval(source.getValue() / 1000.0);
				graph.repaint();
			}
        });
        calcuationIntervalSlider.setPreferredSize(new Dimension(PANEL_WIDTH, TEXT_AREA_HEIGHT));
        panel.add(calcuationIntervalSlider);
	}
	
	public void link(Graph graph) {
		this.graph = graph;
	}
}
