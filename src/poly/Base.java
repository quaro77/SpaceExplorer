package poly;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Base implements Drawable {

	private int progressivo;

	public int j;
	public int k;

	public int xDest;
	public int yDest;
	public int xTravel;
	public int yTravel;

	public BaseType tipo;

	public int id;

	int[] xArray = { -5, 5, 5, -5 };
	int[] yArray = { -4, -4, 4, 4 };

	public Map map;
	public Color color = Color.ORANGE;

	public Base(BaseType tipo, int j, int k, Map m) {
		this.id = progressivo++;
		this.tipo = tipo;
		this.map = m;
		this.id = id;
		this.j = j;
		this.k = k;
		map.tiles[j][k].item = 5;

	}

	public void destroy() {
		if (map.tiles[j][k].item == 5) {
			map.tiles[j][k].item = 0;
		}
	}

	/*
	 * 
	 * METODI GRAFICI
	 * 
	 */

	public void draw(Graphics g) {

		if (map.tiles[this.j][this.k].discovered) {

			int actX;
			int actY;

			actX = map.tiles[this.j][this.k].x;
			actY = map.tiles[this.j][this.k].y;

			int[] newxArray = new int[xArray.length];
			int[] newyArray = new int[yArray.length];

			for (int i = 0; i < xArray.length; i++) {
				newxArray[i] = actX + xArray[i];
				newyArray[i] = actY + yArray[i];
			}

			// Polygon p = new Polygon(newxArray, newyArray, xArray.length);
			//
			// g.setColor(this.color);
			// g.fillPolygon(p);
			//
			// g.setColor(Color.BLACK);
			// g.drawPolygon(p);

			if (tipo == BaseType.repair) {
				g.drawLine(actX, actY - 2, actX, actY + 2);
				g.drawLine(actX - 2, actY, actX + 2, actY);
			} else if (tipo == BaseType.mine) {

				g.setColor(this.color);
				g.fillOval(actX - 3, actY - 3, 6, 6);

				g.setColor(Color.BLACK);
				g.drawOval(actX - 3, actY - 3, 6, 6);

			}

		}
	}

}
