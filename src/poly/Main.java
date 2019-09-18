package poly;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main extends JFrame {

	public static Panel p = new Panel();
	public static GameManager gm = new GameManager();

	public static void main(String[] args) {

				Main frame = new Main();
				frame.setUI();
				gm.gameLogic();

	}

	private void setUI() {
		p.animate();
		p.setGlobalVars(gm);
		add(p, BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 500);
		setLocation(200, 200);
		setVisible(true);
	}

}
