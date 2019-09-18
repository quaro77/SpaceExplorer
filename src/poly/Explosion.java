package poly;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Explosion {

	private Sphere[] sphereList = new Sphere[5];
	private long time;
	private Map map;
	private int j;
	private int k;
	public boolean destroy;

	public Explosion(int j, int k, Map map) {
		this.j = j;
		this.k = k;
		this.map = map;
		time = 0;
		destroy = false;
		sphereList[0] = new Sphere(1, 2, 8, 0);
		sphereList[1] = new Sphere(-5, -3, 6, 10);
		sphereList[2] = new Sphere(5, 4, 7, 20);
		sphereList[3] = new Sphere(5, -6, 6, 30);
		sphereList[4] = new Sphere(-3, 5, 7, 50);
	}

	public void update() {
		time++;

		int destroyedSpheres = 0;

		for (Sphere s : sphereList) {

			if (s.dim > s.dimMax) {
				s.destroy = true;
				destroyedSpheres++;
			} else if (time >= s.startTime) {
				s.dim += 0.2;
			}
		}

		if (destroyedSpheres >= sphereList.length) {
			destroy = true;
		}
	}

	/*
	 * 
	 * METODI GRAFICI
	 * 
	 */

	public void draw(Graphics g) {

		if (map.tiles[this.j][this.k] != null && map.tiles[this.j][this.k].discovered) {

			int actX = map.tiles[this.j][this.k].x;
			int actY = map.tiles[this.j][this.k].y;

			for (Sphere s : sphereList) {

				if (!s.destroy) {

					int dimH = (int)Math.round(s.dim / 2);

					g.setColor(Color.YELLOW);
					g.fillOval(actX + s.x - dimH, actY + s.y - dimH, dimH * 2, dimH * 2);

					g.setColor(Color.RED);
					g.drawOval(actX + s.x - dimH, actY + s.y - dimH, dimH * 2, dimH * 2);

				}

			}

		}

	}

}

class Sphere {
	public int x;
	public int y;
	public int dimMax;
	public double dim;
	public int startTime;
	public boolean destroy;

	public Sphere(int x, int y, int dimMax, int startTime) {
		this.x = x;
		this.y = y;
		this.dimMax = dimMax;
		this.startTime = startTime;
		dim = 0;
		destroy = false;
	}
}
