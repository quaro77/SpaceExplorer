package poly;

import java.awt.Color;
import java.awt.Graphics;

public class Map {

	public boolean locked;
	private boolean finish;

	public Hex[][] tiles;
	public int jdim;
	public int kdim;

	public int xStart = 100;
	public int yStart = 100;

	public static final int tileSize = 10;

	// crea una mappa vuota, pronta per essere riempita con i dati caricati da file.
	public Map(int jdim, int kdim, boolean loaded) {
		this.jdim = jdim;
		this.kdim = kdim;
		tiles = new Hex[jdim][kdim];
	}

	// crea una mappa completa.
	public Map(int jdim, int kdim) {
		this.jdim = jdim;
		this.kdim = kdim;

		tiles = new Hex[jdim][kdim];

		for (int i = 0; i < jdim; i++) {
			for (int a = 0; a < kdim; a++) {

				if (a % 2 == 0) {
					tiles[i][a] = new Hex(xStart + (i * (tileSize * 2 + 14)), yStart + (a * tileSize), i, a, tileSize);
				} else {
					tiles[i][a] = new Hex(xStart + (i * (tileSize * 2 + 14)) + 17, yStart + (a * tileSize), i, a, tileSize);
				}
			}
		}
	}

	public int[] getTileIndexes(int j, int k) {

		for (int i = 0; i < jdim; i++) {
			for (int a = 0; a < kdim; a++) {

				if (tiles[i][a] != null && tiles[i][a].check(j, k)) {
					int[] ret = new int[2];
					ret[0] = i;
					ret[1] = a;
					return ret;
				}
			}
		}

		return null;

	}

	public void lock() {
		locked = true;
	}

	public void release() {
		locked = false;
	}

	public void discoverTiles(int x, int y, int r) {

		if (tiles[x][y] != null) {

			tiles[x][y].setDiscovered();

			for (int i = 0; i < jdim; i++) {
				for (int a = 0; a < kdim; a++) {
					if (tiles[i][a] != null && Math.sqrt((tiles[i][a].x - tiles[x][y].x) * (tiles[i][a].x - tiles[x][y].x) + (tiles[i][a].y - tiles[x][y].y) * (tiles[i][a].y - tiles[x][y].y)) < (r * tileSize * 2) + 2) {
						tiles[i][a].setDiscovered();
					}
				}
			}
		}
	}

	public int countUndiscovered() {
		int count = 0;
		for (int i = 0; i < jdim; i++) {
			for (int a = 0; a < kdim; a++) {
				if (tiles[i][a] != null && !tiles[i][a].discovered) {
					count++;
				}
			}
		}
		return count;
	}
	
	public boolean checkIfSide(int x, int y) {
		
		int neib = 0;
		
		for (int i = 0; i < jdim; i++) {
			for (int a = 0; a < kdim; a++) {
				if (tiles[i][a] != null && Math.sqrt((tiles[i][a].x - tiles[x][y].x) * (tiles[i][a].x - tiles[x][y].x) + (tiles[i][a].y - tiles[x][y].y) * (tiles[i][a].y - tiles[x][y].y)) < (tileSize * 2) + 2) {
					neib++;
				}
			}
		}
		
		if (neib >= 6 || neib < 1) return false;
		else return true;
		
	}

	public void reset() {
		for (int i = 0; i < jdim; i++) {
			for (int a = 0; a < kdim; a++) {
				if (tiles[i][a] != null) {
					tiles[i][a].setUndiscovered();
				}
			}
		}
	}

	public void checkHover(int mouseX, int mouseY) {
		for (int i = 0; i < jdim; i++) {
			for (int a = 0; a < kdim; a++) {
				if (tiles[i][a] != null) {
					tiles[i][a].hovering = false;
					if (tiles[i][a].check(mouseX, mouseY)) {
						tiles[i][a].hovering = true;
					}
				}

			}
		}
	}

	public Hex findTile(int x, int y) {
		for (int i = 0; i < jdim; i++) {
			for (int a = 0; a < kdim; a++) {
				if (tiles[i][a] != null && tiles[i][a].check(x, y)) {
					return tiles[i][a];
				}
			}
		}
		return null;
	}

	public int distance(Hex h1, Hex h2) {

		int d;
		double df;

		if (h1 == h2) {
			return 0;
		} else {
			df = Math.sqrt((h2.x - h1.x) * (h2.x - h1.x) + (h2.y - h1.y) * (h2.y - h1.y));

			d = (int) Math.round(df / (tileSize * 2));

			return d;
		}

	}

	/*
	 * 
	 * METODI GRAFICI
	 * 
	 */

	public void draw(Graphics g) {

		for (int i = 0; i < jdim; i++) {
			for (int a = 0; a < kdim; a++) {
				if (tiles[i][a] != null) {
					tiles[i][a].draw(g);
				}
			}
		}

	}

}
