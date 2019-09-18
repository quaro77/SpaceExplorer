package poly;

import java.awt.Color;
import java.awt.Graphics;

public class Button {

	private static int progressive;

	public int id;
	private int x;
	private int y;
	private int textx;
	private int texty;
	private int dx;
	private int dy;
	public String text = "";
	public ButtonState state;
	public boolean twoState;
	public boolean enabled;

	public Button(String text, int x, int y, int dx, int dy, boolean twoState) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.text = text;
		this.textx = x + Math.round(dx / 2) - text.length() * 3;
		this.texty = y + Math.round(dy / 2) + 4;
		this.twoState = twoState;
		this.id = progressive++;
		this.enabled = true;

	}

	public boolean clicked(int mouseX, int mouseY) {
		if (enabled && (mouseX > x) && (mouseX < x + dx) && (mouseY > y) && (mouseY < y + dy)) {
			if (state != ButtonState.alreadyClicked) {
				state = ButtonState.clicked;
			}
			return true;
		} else {
			state = ButtonState.normal;
			return false;
		}
	}

	public boolean hover(int mouseX, int mouseY) {
		if (enabled && (mouseX > x) && (mouseX < x + dx) && (mouseY > y) && (mouseY < y + dy)) {
			state = ButtonState.hover;
			return true;
		} else {
			state = ButtonState.normal;
			return false;
		}
	}

	public void setEnabled(boolean en) {
		enabled = en;
	}

	public void draw(Graphics g) {

		if (state == ButtonState.hover || state == ButtonState.clicked) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x, y, dx, dy);
		}

		if (state == ButtonState.clicked || state == ButtonState.alreadyClicked) {
			g.setColor(Color.GRAY);
		} else {
			g.setColor(Color.WHITE);
		}

		g.drawLine(x + 1, y + 1, x + dx - 1, y + 1);
		g.drawLine(x + 1, y + 1, x + 1, y + dy - 1);

		if (state == ButtonState.clicked || state == ButtonState.alreadyClicked) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(Color.GRAY);
		}

		g.drawLine(x, y + dy - 1, x + dx - 1, y + dy - 1);
		g.drawLine(x + dx - 1, y, x + dx - 1, y + dy - 1);

		if (enabled) {
			g.setColor(Color.BLACK);
		} else {
			g.setColor(Color.LIGHT_GRAY);
		}

		g.drawRect(x, y, dx, dy);
		g.drawString(text, textx, texty);

	}

}
