import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Main {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Graph graph = new Graph();
		FunctionsManager functionsManager = new FunctionsManager();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		
		graph.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				graph.handleMousePressed(e);
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}			
		});
		graph.addMouseMotionListener(new MouseMotionListener() {			
			@Override
			public void mouseDragged(MouseEvent e) {
				graph.handleMouseDragged(e);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		graph.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				graph.handleMouseWheelMoved(e);
				
			}
		});
		
		frame.add(graph);
        frame.add(functionsManager);
        
        functionsManager.link(graph);
        
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		graph.repaint();
	}

}
