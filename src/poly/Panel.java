package poly;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements MouseListener, MouseMotionListener {

	public GameManager gm;


	public Panel() {
		addMouseListener(this);
		addMouseMotionListener(this);		
	}
	
	
	public void setGlobalVars(GameManager gm) {
		this.gm = gm;
	}

	public void animate() {
		new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		}).start();
	}


	public void paint(Graphics g) {
		super.paintComponent(g);
		gm.update();
		gm.draw(g);
		//System.out.println(gm.resourceList.size());

	}
		
	public void mouseMoved(MouseEvent e) {
		gm.mouseX = e.getX();
		gm.mouseY = e.getY();
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			gm.leftMousePressed = true;	
		} 
		else if (e.getButton() == MouseEvent.BUTTON3) {
			gm.rightMousePressed = true;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			gm.leftMousePressed = false;
		} 
		else if (e.getButton() == MouseEvent.BUTTON3) {
			gm.rightMousePressed = false;
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		gm.mouseX = e.getX();
		gm.mouseY = e.getY();
	}

}
