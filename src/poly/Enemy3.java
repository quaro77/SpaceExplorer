package poly;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

// tipologia di nemico che copre le caselle su cui passa

public class Enemy3 extends Enemy {

	int oldJ;
	int oldK;

	public Enemy3(int j, int k, Map m, Player p) {
		super(j, k, m, p);
		oldJ = j;
		oldK = k;
	}

	@Override
	public void setDestination(int x, int y) {
		super.setDestination(x, y);
		oldJ = j;
		oldK = k;
	}

	@Override
	public void setPosition(int j, int k) {
		this.j = j;
		this.k = k;
		this.xTravel = map.tiles[j][k].x;
		this.yTravel = map.tiles[j][k].y;
		this.xDest = xTravel;
		this.yDest = yTravel;
		// map.discoverTiles(j, k, 1);
		map.tiles[j][k].item = 2;

		if (oldJ != j || oldK != k) {
			if (map.tiles[oldJ][oldK] != null) {
				map.tiles[oldJ][oldK].setUndiscovered();
			}
		}
	}

	/*
	 * 
	 * METODI GRAFICI
	 * 
	 */

	@Override
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

			g.setColor(Color.MAGENTA);
			g.fillPolygon(p);

			g.setColor(Color.BLACK);
			g.drawPolygon(p);

		}

	}

}