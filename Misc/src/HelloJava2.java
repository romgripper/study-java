import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;


public class HelloJava2 {

	public static void main(String[] args) {
		JFrame frame = new JFrame("HelloJava2");
		frame.add(new HelloComponent2("Hello, Java!"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
}

class HelloComponent2 extends JComponent implements MouseMotionListener {
	private String theMessage;
	private int messageX = 125, messageY = 95;
	
	public HelloComponent2(String message) {
		theMessage = message;
		addMouseMotionListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawString(theMessage, messageX, messageY);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		messageX = e.getX();
		messageY = e.getY();
		repaint();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
	}
}