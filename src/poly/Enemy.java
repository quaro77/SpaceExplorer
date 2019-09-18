package poly;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Enemy implements Drawable {

	public static int foundFirstEnemy = 0;
	protected static int progressivo;

	public int j;
	public int k;

	public int xDest;
	public int yDest;
	public int xTravel;
	public int yTravel;
	protected boolean traveling;
	protected int speed = 1; // pixel
	protected int range = 2; // hex
	public int id;
	protected int direction;
	public boolean destroy;
	public boolean killed;
	

	int[] xArray = { 0, -5, -2, -5, 0, 5, 2, 5 };
	int[] yArray = { -2, -5, 0, 5, 2, 5, 0, -5 };

	protected Map map;
	protected Player player;

	public Enemy(int j, int k, Map m, Player p) {
		this.player = p;
		this.map = m;
		this.id = progressivo++;
		this.j = j;
		this.k = k;
		setPosition(j, k);
		setInitialDirection();

	}

	private void setInitialDirection() {

		double rand = Math.random();

		if (j == 0) {
			if (rand < 0.5) {
				direction = 1;
			} else {
				direction = 2;
			}
		}

		if (j == map.jdim - 1) {
			if (rand < 0.5) {
				direction = 4;
			} else {
				direction = 5;
			}
		}

		if (k == 0) {
			if (rand < 0.33) {
				direction = 2;
			} else if (rand < 0.66) {
				direction = 3;
			} else {
				direction = 4;
			}
		}

		if (k == map.kdim - 1) {
			if (rand < 0.33) {
				direction = 5;
			} else if (rand < 0.66) {
				direction = 0;
			} else {
				direction = 1;
			}
		}

	}

	public void setPosition(int j, int k) {
		this.j = j;
		this.k = k;
		this.xTravel = map.tiles[j][k].x;
		this.yTravel = map.tiles[j][k].y;
		this.xDest = xTravel;
		this.yDest = yTravel;
		// map.discoverTiles(j, k, 1);
		map.tiles[j][k].item = 2;
	}

	public void setDestination(int x, int y) {
		this.xDest = x;
		this.yDest = y;
		map.tiles[j][k].item = 0;
	}

	public void destroy() {
		destroy = true;
		map.tiles[j][k].item = 0;
	}

	public void logic() {

		Hex destTile = null;

		Boolean done = false;
		int iter = 0;
		
		while (!done && iter < 2) {

			switch (direction) {
			case 0: // alto
				destTile = map.findTile(xTravel, yTravel - 15);
				break;
			case 1: // dx alto
				destTile = map.findTile(xTravel + 14, yTravel - 10);
				break;
			case 2: // dx basso
				destTile = map.findTile(xTravel + 14, yTravel + 10);
				break;
			case 3: // basso
				destTile = map.findTile(xTravel, yTravel + 15);
				break;
			case 4: // sx basso
				destTile = map.findTile(xTravel - 14, yTravel + 10);
				break;
			case 5: // sx alto
				destTile = map.findTile(xTravel - 14, yTravel - 10);
				break;
			}
			

			if (destTile != null) {
				
				if (destTile.item == 2) {
					
					changeDirection();
					iter++;
					continue;
				}
				
				setDestination(destTile.x, destTile.y);
				destTile.item = 2;
				if (player.j == destTile.j && player.k == destTile.k) {
					destroy = true;
					killed = true;
					player.addHealth(-1);
				}
			} else {
				destroy = true;
			}
			
			done = true;
		}
	}
	
	
	public void changeDirection() {
		switch (direction) {
		case 0:
			direction = 3;
			break;
		case 1:
			direction = 4;
			break;
		case 2:
			direction = 5;
			break;
		case 3:
			direction = 0;
			break;
		case 4:
			direction = 1;
			break;
		case 5:
			direction = 2;
			break;
			
		}
		
	}
	
	

	public void updatePosition() {

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

	/*
	 * 
	 * METODI GRAFICI
	 * 
	 */

	public void draw(Graphics g) {

		if (map.tiles[this.j][this.k] != null && map.tiles[this.j][this.k].discovered) {
			
			foundFirstEnemy++;

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

			g.setColor(Color.RED);
			g.fillPolygon(p);

			g.setColor(Color.BLACK);
			g.drawPolygon(p);

		}

	}

}
