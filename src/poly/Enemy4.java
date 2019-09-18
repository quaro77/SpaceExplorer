package poly;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

// tipologia di nemico che cambia direzione quando arriva ai bordi della mappa

public class Enemy4 extends Enemy {

	public Enemy4(int j, int k, Map m, Player p) {
		super(j, k, m, p);
	}

	@Override
	public void logic() {

		Hex destTile = null;

		Boolean done = false;
		int iter = 0;

		isPlayerNear();

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

					super.changeDirection();
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
				changeDirection();
			}

			done = true;
		}
	}

	private void isPlayerNear() {

		if (map.distance(map.tiles[this.j][this.k], map.tiles[player.j][player.k]) <= 2) {

			int plX = map.tiles[player.j][player.k].x;
			int plY = map.tiles[player.j][player.k].y;
			int thX = map.tiles[this.j][this.k].x;
			int thY = map.tiles[this.j][this.k].y;

			if (plX == thX) {
				if (plY < thY) {
					direction = 0;
				} else {
					direction = 3;
				}
			}
			else if (plX < thX && plY < thY) {
				direction = 5;
			}
			else if (plX < thX && plY >= thY) {
				direction = 4;
			}
			else if (plX > thX && plY < thY) {
				direction = 1;
			}
			else if (plX > thX && plY >= thY) {
				direction = 2;
			}
		}

	}

	@Override
	public void changeDirection() {

		int rand = (int) Math.floor(Math.random() * 3);

		switch (direction) {
		case 0:
			if (rand == 0) {
				direction = 2;
			} else if (rand == 1) {
				direction = 3;
			} else if (rand == 2) {
				direction = 4;
			}
			break;
		case 1:
			if (rand == 0) {
				direction = 3;
			} else if (rand == 1) {
				direction = 4;
			} else if (rand == 2) {
				direction = 5;
			}
			break;
		case 2:
			if (rand == 0) {
				direction = 4;
			} else if (rand == 5) {
				direction = 3;
			} else if (rand == 0) {
				direction = 4;
			}
			break;
		case 3:
			if (rand == 0) {
				direction = 5;
			} else if (rand == 1) {
				direction = 0;
			} else if (rand == 2) {
				direction = 1;
			}
			break;
		case 4:
			if (rand == 0) {
				direction = 0;
			} else if (rand == 1) {
				direction = 1;
			} else if (rand == 2) {
				direction = 2;
			}
			break;
		case 5:
			if (rand == 0) {
				direction = 1;
			} else if (rand == 1) {
				direction = 2;
			} else if (rand == 2) {
				direction = 3;
			}
			break;
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

			g.setColor(Color.PINK);
			g.fillPolygon(p);

			g.setColor(Color.BLACK);
			g.drawPolygon(p);

		}

	}

}
