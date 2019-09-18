package poly;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Resource implements Drawable {

		
	public static int foundFirstResource = 0;
	private static int progressivo;

	public ResourceType type;
	
	public int j;
	public int k;

	public int xDest;
	public int yDest;
	public int xTravel;
	public int yTravel;

	public int id;

	int[] xArray = { -5, -1, 4 };
	int[] yArray = { -5, 1, -4 };

	public Map map;
	public Color color = Color.CYAN;

	public Resource(int j, int k, ResourceType type, Map m) {
		this.type = type;
		
		if (type == ResourceType.invulnerability) {
			color = Color.RED;
		}
		
		this.map = m;
		this.id = progressivo++;
		this.j = j;
		this.k = k;
		map.tiles[j][k].item = 3;
	}

	public void destroy() {
		if (map.tiles[j][k].item == 3) {
			map.tiles[j][k].item = 0;
		}
	}

	/*
	 * 
	 * METODI GRAFICI
	 * 
	 */

	public void draw(Graphics g) {

		if (map.tiles[this.j][this.k] != null && map.tiles[this.j][this.k].discovered) {
			
			foundFirstResource++;

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

			g.setColor(this.color);
			g.fillOval(newxArray[0], newyArray[0], 3, 3);
			g.fillOval(newxArray[1], newyArray[1], 4, 4);
			g.fillOval(newxArray[2], newyArray[2], 3, 3);

			g.setColor(Color.BLACK);
			g.drawOval(newxArray[0], newyArray[0], 3, 3);
			g.drawOval(newxArray[1], newyArray[1], 4, 4);
			g.drawOval(newxArray[2], newyArray[2], 3, 3);

		}

	}

}
