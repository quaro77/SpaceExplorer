package poly;

import java.awt.Color;
import java.awt.Graphics;

public class Popup {
	
	private String text;
	private Button button;
	private int x;
	private int y;
	private int dx;
	private int dy;
	private int textx;
	private int texty;
	public String type;
	
	
	public Popup(String text, int x, int y, int dx, int dy, String type) {
		this.type = type;
		this.text = text;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.textx = x + Math.round(dx / 2) - text.length() * 3;
		this.texty = y + Math.round(dy / 2) - 10;
		this.button = new Button("OK", x + Math.round(dx / 2) - 40, y + dy - 40, 80, 20, false);
		
		
	}
	
	public int update(boolean mousePressed, int mouseX, int mouseY) {

		if (mousePressed) {
			button.clicked(mouseX, mouseY);
		} 
		else {
			button.hover(mouseX, mouseY);
		}
		
		if (button.state == ButtonState.clicked) {
			return 1;
		} else {
			return 0;
		}

	}
	
	
	
	
	public void draw(Graphics g) {
		
		g.setColor(Color.WHITE);
		g.fillRect(x, y, dx, dy);
		
		g.setColor(Color.GRAY);
		g.drawRect(x, y, dx, dy);
		
		g.setColor(Color.BLACK);
		g.drawString(text, textx, texty);
		
		button.draw(g);
		
	}

}
