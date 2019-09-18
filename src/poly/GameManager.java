package poly;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GameManager implements Runnable {

	private int level;
	private Level currLevel;
	private boolean finish;
	protected boolean gameOut;
	private boolean fineTurno;
	protected Map map;
	private Player player;
	private List<Enemy> enemyList;
	public List<Resource> resourceList;
	private List<Base> baseList;
	protected List<Button> buttonList;
	private List<Explosion> explosionList;
	private int turn;
	private int nRisorse;
	protected Popup popup;

	protected int mouseX;
	protected int mouseY;
	protected boolean leftMousePressed;
	protected boolean rightMousePressed;

	public GameManager() {
		buttonList = new ArrayList<>();
		buttonList.add(new Button("Repair", 450, 100, 80, 20, false));
		buttonList.add(new Button("Mine", 450, 130, 80, 20, false));

		buttonList.get(0).setEnabled(false);
		buttonList.get(1).setEnabled(false);

		level = 4;
		resetLevel(level);
	}

	public void run() {

		while (!gameOut) {

			finish = false;

			while (!finish) {

				// turno player

				fineTurno = false;

				while (!fineTurno) {

					if (!map.locked && popup == null) {

						map.checkHover(mouseX, mouseY);

						if (leftMousePressed) {
							for (Button b : buttonList) {
								b.clicked(mouseX, mouseY);
							}
							for (Button b : buttonList) {
								if (b.state == ButtonState.clicked) {
									b.state = ButtonState.alreadyClicked;
									switch (b.id) {
									case 0: // repair

										// baseList.add(new Base(BaseType.repair, player.j, player.k, map));
										if (player.health < player.healthMax) {
											player.addHealth(1);
											addResources(-2);
										}

										break;
									case 1: // mina

										if (!checkMines(player.j, player.k)) {
											baseList.add(new Base(BaseType.mine, player.j, player.k, map));
											map.tiles[player.j][player.k].item = 4;
											addResources(-1);
										}
										break;
									}
								}
							}

							Hex h = map.findTile(mouseX, mouseY);
							if ((h != null) && (h != map.tiles[player.j][player.k])) {
								if (player.canReachTile(h)) {
									player.setDestination(h.x, h.y);
									checkTileEffect(player, h.j, h.k);
									map.lock();
									fineTurno = true;
								}
							}

						} else {
							for (Button b : buttonList) {
								b.hover(mouseX, mouseY);
							}
						}

					} // fine if (!locked)

					sleep(10);
				} // fine turno player

				// turno PC

				fineTurno = false;
				while (!fineTurno) {
					if (!map.locked) {

						synchronized (enemyList) {
							for (Iterator<Enemy> iterator = enemyList.iterator(); iterator.hasNext();) {
								Enemy e = iterator.next();
								e.logic();
								Hex destHex = map.findTile(e.xDest, e.yDest);
								checkTileEffect(e, destHex.j, destHex.k);
								if (e.destroy) {
									if (e.killed) {
										explosionList.add(new Explosion(destHex.j, destHex.k, map));
									}
									iterator.remove();
								}
							}
						}

						// spawn risorse
						if ((currLevel.resourcesSpawnRate != 0) && (turn % currLevel.resourcesSpawnRate == 0) && (resourceList.size() < currLevel.maxResources)) {

							boolean done = false;

							while (!done) {
								int rj = (int) Math.floor(Math.random() * map.jdim);
								int rk = (int) Math.floor(Math.random() * map.kdim);
								if (map.tiles[rj][rk] != null && map.tiles[rj][rk].item == 0) {
									map.tiles[rj][rk].item = 3;

									double rand = Math.random();
									if (rand < currLevel.resourceNormalRate) {
										resourceList.add(new Resource(rj, rk, ResourceType.normal, map));
									} else {
										resourceList.add(new Resource(rj, rk, ResourceType.invulnerability, map));
									}
									done = true;
								}
							}
						}

						// spawn nemici
						if ((currLevel.enemySpawnRate != 0) && (turn % currLevel.enemySpawnRate == 0) && (enemyList.size() < currLevel.maxEnemies)) {

							spawnEnemyAtSide();

						}

						fineTurno = true;
					} // fine if (!locked)
					sleep(10);
				} // fine turno PC

				turn++;

				// operazioni da effettuare ogni fine turno

				if (map.countUndiscovered() == 0 || player.health == 0) {
					finish = true;
				}

				// System.out.println(turn + " " + turn % currLevel.resourcesSpawnRate);

			} // fine while (!finished)

			if (player.health > 0) {
				popup = new Popup("LEVEL COMPLETE!", 200, 200, 200, 100, "end");
				level++;
			} else {
				popup = new Popup("GAME OVER.", 200, 200, 200, 100, "end");
			}

		}

	}

	public void gameLogic() {
		Thread t = new Thread(this);
		t.start();
	}

	public void resetLevel(int level) {

		currLevel = new Level(level);

		map = currLevel.map[currLevel.settore];

		player = new Player(currLevel.playerSpawnX, currLevel.playerSpawnY, map);

		enemyList = Collections.synchronizedList(new ArrayList<>());
		resourceList = Collections.synchronizedList(new ArrayList<>());
		explosionList = Collections.synchronizedList(new ArrayList<>());

		if (currLevel.startResourcesJ != null & currLevel.startResourcesJ.length > 0) {
			for (int i = 0; i < currLevel.startResourcesJ.length; i++) {
				Resource r = new Resource(currLevel.startResourcesJ[i], currLevel.startResourcesK[i], ResourceType.normal, map);
				resourceList.add(r);
			}
		}

		baseList = Collections.synchronizedList(new ArrayList<>());

		buttonList.get(0).setEnabled(false);
		buttonList.get(1).setEnabled(false);

		turn = 0;
		nRisorse = 0;
		player.health = 3;

	}

	public boolean checkMines(int j, int k) {

		synchronized (baseList) {
			for (Base b : baseList) {
				if (b.j == j && b.k == k) {
					return true;
				}
			}
		}
		return false;
	}

	public void checkTileEffect(Drawable p, int j, int k) {

		synchronized (baseList) {
			for (Iterator<Base> iterator = baseList.iterator(); iterator.hasNext();) {
				Base b = iterator.next();
				if (b.j == j && b.k == k) {
					if (b.tipo == BaseType.repair) {
						if (p instanceof Player) {
							player.addHealth(1);
						}
					} else if (b.tipo == BaseType.mine) {
						b.destroy();
						iterator.remove();
						if (p instanceof Player) {
							explosionList.add(new Explosion(j, k, map));
							player.addHealth(-1);
						} else {
							explosionList.add(new Explosion(j, k, map));
							p.destroy();
						}
					}

				}
			}
		}

		synchronized (resourceList) {
			for (Iterator<Resource> iterator = resourceList.iterator(); iterator.hasNext();) {
				Resource r = iterator.next();
				if (r.j == j && r.k == k) {
					r.destroy();
					iterator.remove();
					if (p instanceof Player)
						if (r.type == ResourceType.normal) {
							addResources(1);
						} else if (r.type == ResourceType.invulnerability) {
							player.setInvulnerable();
						}
					break;
				}
			}
		}

		if (p instanceof Player) {
			synchronized (enemyList) {
				for (Iterator<Enemy> iterator = enemyList.iterator(); iterator.hasNext();) {
					Enemy e = iterator.next();
					if (e.j == j && e.k == k) {
						e.destroy();
						iterator.remove();
						explosionList.add(new Explosion(j, k, map));
						player.addHealth(-1);

					}
				}
			}
		}

	}

	private void spawnEnemyAtSide() {

		try {

			// int rand = (int) Math.floor(Math.random() * 4);

			double rand2 = Math.random();

			int rj = 0;
			int rk = 0;
			boolean done = false;
			int recurs = 0;

			while (!done) {

				int randJ = (int) Math.floor(Math.random() * map.jdim);
				int randK = (int) Math.floor(Math.random() * map.kdim);

				if (map.tiles[randJ][randK] != null && map.checkIfSide(randJ, randK)) {
					rj = randJ;
					rk = randK;
					done = true;
				}

				if (recurs > 1000) {
					System.out.println("Metodo spawnEnemyAtSide - troppe ricorsioni");
				}
			}

			System.out.println("spawn at " + rj + ", " + rk);

			if (rand2 < currLevel.enemy1Rate) {
				enemyList.add(new Enemy(rj, rk, map, player));
			} else if (rand2 < currLevel.enemy2Rate) {
				enemyList.add(new Enemy2(rj, rk, map, player));
			} else if (rand2 < currLevel.enemy3Rate) {
				enemyList.add(new Enemy3(rj, rk, map, player));
			} else {
				enemyList.add(new Enemy4(rj, rk, map, player));
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Metodo spawnEnemyAtSide - indice array fuori dai limiti.");
		}

	}

	public void addResources(int n) {
		nRisorse += n;
		if (nRisorse < 0) {
			nRisorse = 0;
		}
		buttonList.get(1).setEnabled(nRisorse > 0);
		buttonList.get(0).setEnabled(nRisorse > 1);
	}

	public void update() {

		if (popup != null) {
			if (popup.update(leftMousePressed, mouseX, mouseY) == 1) {
				System.out.println("Close popup.");
				if (popup.type.equals("end")) {
					resetLevel(level);
				}
				popup = null;
			}
		}
		player.updatePosition();
		for (Enemy e : enemyList) {
			e.updatePosition();
		}

		synchronized (explosionList) {
			for (Explosion e : explosionList) {
				e.update();
			}

			for (Iterator<Explosion> iterator = explosionList.iterator(); iterator.hasNext();) {
				Explosion e = iterator.next();
				if (e.destroy) {
					iterator.remove();
					break;
				}
			}
		}
	}

	/*
	 * 
	 * METODI GRAFICI
	 * 
	 */

	private void drawUI(Graphics g) {

		g.setColor(Color.BLACK);
		g.drawString("Level " + level, 10, 20);
		g.drawString("Health:", 100, 350);
		g.drawString("Resources:", 75, 380);
		// g.drawString("Build:", 450, 90);

		g.setColor(Color.ORANGE);
		for (int i = 0; i < player.health; i++) {
			g.fillRect(150 + (i * 15), 335, 10, 20);
		}

		g.setColor(Color.GREEN);
		g.fillRect(535, 105, 5, 10);
		g.fillRect(542, 105, 5, 10);
		g.fillRect(535, 135, 5, 10);

		for (int i = 0; i < nRisorse; i++) {
			g.fillRect(150 + (i * 15), 365, 10, 20);
		}
	}

	public void draw(Graphics g) {

		map.draw(g);

		drawUI(g);

		for (Button b : buttonList) {
			b.draw(g);
		}

		synchronized (resourceList) {
			for (Resource r : resourceList) {
				if (Resource.foundFirstResource == 1) {
					popup = new Popup("FIRST RESOURCE FOUND!", 200, 200, 200, 100, "info");
					Resource.foundFirstResource = 2;
				}
				r.draw(g);
			}
		}

		synchronized (baseList) {
			for (Base b : baseList) {
				b.draw(g);
			}
		}

		synchronized (enemyList) {
			for (Enemy e : enemyList) {
				if (Enemy.foundFirstEnemy == 1) {
					popup = new Popup("FIRST ENEMY FOUND!", 200, 200, 200, 100, "info");
					Enemy.foundFirstEnemy = 2;
				}
				e.draw(g);
			}
		}

		player.draw(g);

		synchronized (explosionList) {
			for (Explosion e : explosionList) {
				e.draw(g);
			}
		}

		if (popup != null) {
			popup.draw(g);
		}

	}

	/*
	 * 
	 * UTILITY
	 * 
	 */

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
		}
	}

}
