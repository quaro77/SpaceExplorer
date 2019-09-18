package poly;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Hex {

	private Polygon poly;

	public int x;
	public int y;

	public int j;
	public int k;

	public int item; // 0: vuoto, 1: player, 2: nemico, 3: risorsa, 4: mina, 5: hole in, 6: hole out
	public int size;
	public boolean discovered;
	public boolean hovering;
	public Color color = Color.LIGHT_GRAY;

	public Hex(int x, int y, int j, int k, int size) {
		this.x = x;
		this.y = y;
		this.j = j;
		this.k = k;
		this.size = size;
		this.item = 0;

		int deltax = (int) Math.round(Math.cos(Math.toRadians(60)) * size);
		int deltay = (int) Math.round(Math.sin(Math.toRadians(60)) * size);

		int x1 = x + size;
		int y1 = y;

		int x2 = x + deltax;
		int y2 = y - deltay;

		int x3 = x - deltax;
		int y3 = y - deltay;

		int x4 = x - size;
		int y4 = y;

		int x5 = x - deltax;
		int y5 = y + deltay;

		int x6 = x + deltax;
		int y6 = y + deltay;

		int[] xArray = { x1, x2, x3, x4, x5, x6 };
		int[] yArray = { y1, y2, y3, y4, y5, y6 };

		poly = new Polygon(xArray, yArray, 6);
	}

	public boolean check(int mx, int my) {

		if (poly.contains(mx, my)) {
			return true;
		} else {
			return false;
		}
	}

	public void setDiscovered() {
		this.discovered = true;
		this.color = Color.WHITE;
	}

	public void setUndiscovered() {
		this.discovered = false;
		this.color = Color.LIGHT_GRAY;
	}

	public void draw(Graphics g) {

		if (!hovering) {
			g.setColor(this.color);
		} else {
			g.setColor(Color.GREEN);
		}
		g.fillPolygon(poly);

		if (discovered) {
			g.setColor(Color.BLACK);
			g.drawPolygon(poly);
		}
	}

}
