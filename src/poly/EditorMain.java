package poly;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class EditorMain extends JFrame {

	public static Panel p = new Panel();
	public static EditorManager em = new EditorManager();

	public static void main(String[] args) {


				EditorMain frame = new EditorMain();
				frame.setUI();
				em.gameLogic();

	}

	private void setUI() {
		p.animate();
		p.setGlobalVars(em);
		add(p, BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 500);
		setLocation(200, 200);
		setVisible(true);
	}
}
