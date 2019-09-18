package poly;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class EditorManager extends GameManager implements Runnable {

	public EditorManager() {
		buttonList = new ArrayList<>();
		buttonList.add(new Button("Select all", 450, 130, 80, 20, false));
		buttonList.add(new Button("Deselect all", 450, 160, 80, 20, false));
		buttonList.add(new Button("Load", 450, 190, 80, 20, false));
		buttonList.add(new Button("Save", 450, 220, 80, 20, false));

		buttonList.get(0).setEnabled(true);
		buttonList.get(1).setEnabled(true);
		map = new Map(10, 20);
	}

	public void run() {
		
		File f = null;

		while (!gameOut) {

			map.checkHover(mouseX, mouseY);
			if (leftMousePressed) {
				for (Button b : buttonList) {
					b.clicked(mouseX, mouseY);
				}
				for (Button b : buttonList) {
					if (b.state == ButtonState.clicked) {
						b.state = ButtonState.alreadyClicked;
						switch (b.id) {
						case 2: // seleziona tutti
							for (int i = 0; i < map.jdim; i++)
								for (int a = 0; a < map.kdim; a++) {
									map.tiles[i][a].setDiscovered();
								}

							break;
						case 3: // deseleziona tutti
							for (int i = 0; i < map.jdim; i++)
								for (int a = 0; a < map.kdim; a++) {
									map.tiles[i][a].setUndiscovered();
								}

							break;
						case 4: // load
							
							FileReader fr = null;

							try {
								f = new File("level.lvl");
								fr = new FileReader(f);
								
								map.jdim = fr.read();
								
								map.kdim = fr.read();
																
								for (int i = 0; i < map.jdim; i++) {
									for (int a = 0; a < map.kdim; a++) {
										int val = fr.read();

										if (val == 0) {
											map.tiles[i][a].setUndiscovered();
										} 
										else {
											map.tiles[i][a].setDiscovered();
										}
									}	
								}
								
								
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								try {
									fr.close();
								} catch (Exception e) {
									e.printStackTrace();
								}

							}
							
							System.out.println("Loaded.");

							break;
						case 5: // save
							
							FileWriter fw = null;

							try {
								f = new File("level.lvl");
								fw = new FileWriter(f);
								
								fw.write(map.jdim);
								fw.write(map.kdim);
								
								for (int i = 0; i < map.jdim; i++) {
									for (int a = 0; a < map.kdim; a++) {
										if (map.tiles[i][a].discovered) {
										fw.write(1);
										} 
										else {
											fw.write(0);	
										}
									}	
								}
								
								
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								try {
									fw.close();
								} catch (Exception e) {
									e.printStackTrace();
								}

								
							}
							
							System.out.println("Saved.");

							break;

						}
					}
				}

				Hex h = map.findTile(mouseX, mouseY);
				if (h != null) {
					h.setDiscovered();
				}

			} else if (rightMousePressed) {
				Hex h = map.findTile(mouseX, mouseY);
				if (h != null) {
					h.setUndiscovered();
				}
			} else {
				for (Button b : buttonList) {
					b.hover(mouseX, mouseY);
				}
			}
			sleep(10);

		}

	}

	public void gameLogic() {
		Thread t = new Thread(this);
		t.start();
	}

	public void update() {

		if (popup != null) {
			if (popup.update(leftMousePressed, mouseX, mouseY) == 1) {
				popup = null;
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
		g.drawString("EDITOR", 10, 20);
		
		int mouseJ = 0;
		int mouseK = 0;
		Hex h = map.findTile(mouseX, mouseY);
		
		if (h != null) {
			mouseJ = h.j;
			mouseK = h.k;
		}
		
		g.drawString(mouseJ + ", " + mouseK, 10, 35);

	}

	public void draw(Graphics g) {

		map.draw(g);

		drawUI(g);

		for (Button b : buttonList) {
			b.draw(g);
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
