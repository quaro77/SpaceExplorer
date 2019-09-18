package poly;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Player implements Drawable {

	public int j;
	public int k;

	public int xDest;
	public int yDest;
	public int xTravel;
	public int yTravel;
	private boolean traveling;
	private int speed = 1; // pixel
	private int range = 1; // hex
	public int health;
	public int healthMax = 3;
	public boolean invulnerable;
	private int invulnSeconds = 5;
	private long invulnTime;

	int[] xArray = { 0, -5, 5 };
	int[] yArray = { -5, 5, 5 };

	private Map map;
	private Color color = Color.BLUE;

	public Player(int j, int k, Map m) {
		this.map = m;
		this.j = j;
		this.k = k;
		setPosition(j, k);
	}

	public boolean canReachTile(Hex h) {

		if (map.distance(map.tiles[j][k], h) <= range) {
			return true;
		}

		return false;

	}

	public void destroy() {

	}

	public void setInvulnerable() {

		invulnTime = System.currentTimeMillis();
		invulnerable = true;

	}

	public void setPosition(int j, int k) {
		this.j = j;
		this.k = k;
		this.xTravel = map.tiles[j][k].x;
		this.yTravel = map.tiles[j][k].y;
		this.xDest = xTravel;
		this.yDest = yTravel;
		map.discoverTiles(j, k, 1);
		map.tiles[j][k].item = 1;
	}

	public void setDestination(int x, int y) {
		this.xDest = x;
		this.yDest = y;
		map.tiles[j][k].item = 0;
	}

	public void updatePosition() {

		if (invulnerable) {
			long time = System.currentTimeMillis();
			if (time - invulnTime >= invulnSeconds * 1000) {
				invulnerable = false;
			}
		}

		if ((xTravel != xDest) || (yTravel != yDest)) {
			map.lock();
			traveling = true;
			// TODO implementare sistema migliore con vettori
			if (xDest > xTravel) {
				xTravel += speed;
			}
			if (xDest < xTravel) {
				xTravel -= speed;
			}
			if (yDest > yTravel) {
				yTravel += speed;
			}
			if (yDest < yTravel) {
				yTravel -= speed;
			}
			if (Math.abs(xDest - xTravel) <= speed && (Math.abs(yDest - yTravel) <= speed)) {

				traveling = false;
				xTravel = xDest;
				yTravel = yDest;
				int[] coords = map.getTileIndexes(xDest, yDest);

				if (coords != null) {
					setPosition(coords[0], coords[1]);
				}
				map.release();
			}
		}
	}

	public void addHealth(int n) {
		if (!invulnerable) {
			health += n;
		}
		if (health >= healthMax) {
			health = healthMax;
		}

		if (health <= 0) {
			health = 0;
		}
	}

	/*
	 * 
	 * METODI GRAFICI
	 * 
	 */

	public void draw(Graphics g) {

		int actX;
		int actY;

		if (!traveling) {
			actX = map.tiles[this.j][this.k].x;
			actY = map.tiles[this.j][this.k].y;
		} else {
			actX = xTravel;
			actY = yTravel;
		}

		int[] newxArray = new int[xArray.length];
		int[] newyArray = new int[yArray.length];

		for (int i = 0; i < xArray.length; i++) {
			newxArray[i] = actX + xArray[i];
			newyArray[i] = actY + yArray[i];
		}

		Polygon p = new Polygon(newxArray, newyArray, xArray.length);

		if (!invulnerable) {
			g.setColor(this.color);
			g.fillPolygon(p);
			g.setColor(Color.BLACK);
			g.drawPolygon(p);
		} else {
			g.setColor(Color.YELLOW);
			g.fillPolygon(p);
			g.setColor(Color.RED);
			g.drawPolygon(p);
		}

	}

}
