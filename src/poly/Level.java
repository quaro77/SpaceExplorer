package poly;

import java.io.File;
import java.io.FileReader;

public class Level {

	public Map[] map;
	public int maxTurns;
	public int maxTime;
	public int enemySpawnRate;
	public int maxEnemies;
	public double enemy1Rate;
	public double enemy2Rate;
	public double enemy3Rate;
	public double resourceNormalRate;
	public int resourcesSpawnRate;
	public int maxResources;
	public int[] startResourcesJ;
	public int[] startResourcesK;

	public int playerSpawnX, playerSpawnY;

	public int settore;
	public int numSettori;

	public Level(int num) {
		setupLevel(num);
	}

	private void setupLevel(int num) {

		switch (num) {
		case 1:
			numSettori = 1;
			settore = 0;
			map = new Map[numSettori];
			map[0] = loadMap(1);
			playerSpawnX = 2;
			playerSpawnY = 6;
			maxTurns = 0;
			maxTime = 0;
			enemySpawnRate = 0;
			maxEnemies = 0;
			resourcesSpawnRate = 0;
			maxResources = 0;
			startResourcesJ = new int[0];
			startResourcesK = new int[0];

			break;
		case 2:
			numSettori = 1;
			settore = 0;
			map = new Map[numSettori];
			map[0] = loadMap(2);
			playerSpawnX = 3;
			playerSpawnY = 8;
			maxTurns = 0;
			maxTime = 0;
			enemySpawnRate = 5;
			maxEnemies = 2;
			enemy1Rate = 1;
			enemy2Rate = 0;
			enemy3Rate = 0;
			resourcesSpawnRate = 0;
			maxResources = 0;
			startResourcesJ = new int[0];
			startResourcesK = new int[0];
			break;
		case 3:
			numSettori = 1;
			settore = 0;
			map = new Map[numSettori];
			map[0] = loadMap(2);
			playerSpawnX = 3;
			playerSpawnY = 8;
			maxTurns = 0;
			maxTime = 0;
			enemySpawnRate = 5;
			maxEnemies = 3;
			enemy1Rate = 1;
			enemy2Rate = 0;
			enemy3Rate = 0;
			resourceNormalRate = 1;
			resourcesSpawnRate = 20;
			maxResources = 2;
			startResourcesJ = new int[1];
			startResourcesK = new int[1];
			startResourcesJ[0] = 5;
			startResourcesK[0] = 5;
			break;	
		case 4:
			numSettori = 1;
			settore = 0;
			map = new Map[numSettori];
			map[0] = loadMap(3);
			playerSpawnX = 3;
			playerSpawnY = 8;
			maxTurns = 0;
			maxTime = 0;
			enemySpawnRate = 5;
			maxEnemies = 10;
			enemy1Rate = 0.5;
			enemy2Rate = 0.9;
			enemy3Rate = 1;
			resourceNormalRate = 0.2;
			resourcesSpawnRate = 10;
			maxResources = 3;
			startResourcesJ = new int[2];
			startResourcesK = new int[2];
			startResourcesJ[0] = 1;
			startResourcesK[0] = 6;
			startResourcesJ[1] = 5;
			startResourcesK[1] = 11;
			break;
		case 5:
			numSettori = 1;
			settore = 0;
			map = new Map[numSettori];
			map[0] = loadMap(3);
			playerSpawnX = 3;
			playerSpawnY = 8;
			maxTurns = 0;
			maxTime = 0;
			enemySpawnRate = 4;
			maxEnemies = 10;
			enemy1Rate = 0.2;
			enemy2Rate = 0.6;
			enemy3Rate = 1;
			resourceNormalRate = 1;
			resourcesSpawnRate = 15;
			maxResources = 3;
			startResourcesJ = new int[2];
			startResourcesK = new int[2];
			startResourcesJ[0] = 1;
			startResourcesK[0] = 6;
			startResourcesJ[1] = 5;
			startResourcesK[1] = 11;
			break;
		case 6:
			numSettori = 1;
			settore = 0;
			map = new Map[numSettori];
			map[0] = loadMap(4);
			playerSpawnX = 5;
			playerSpawnY = 10;
			maxTurns = 0;
			maxTime = 0;
			enemySpawnRate = 3;
			maxEnemies = 20;
			enemy1Rate = 0.2;
			enemy2Rate = 0.4;
			enemy3Rate = 0.9;
			resourceNormalRate = 0.9;
			resourcesSpawnRate = 20;
			maxResources = 2;
			startResourcesJ = new int[1];
			startResourcesK = new int[1];
			startResourcesJ[0] = 3;
			startResourcesK[0] = 2;
			break;
		default:
			numSettori = 1;
			settore = 0;
			map = new Map[numSettori];
			map[0] = loadMap(5);
			playerSpawnX = 5;
			playerSpawnY = 10;
			maxTurns = 0;
			maxTime = 0;
			enemySpawnRate = 2;
			maxEnemies = 25;
			enemy1Rate = 0;
			enemy2Rate = 0.3;
			resourceNormalRate = 0.9;
			resourcesSpawnRate = 20;
			maxResources = 2;
			startResourcesJ = new int[1];
			startResourcesK = new int[1];
			startResourcesJ[0] = 3;
			startResourcesK[0] = 2;
			break;
		}
	}

	private Map loadMap(int level) {

		Map map = null;

		File f;

		FileReader fr = null;

		try {
			f = new File("level" + level + ".lvl");
			fr = new FileReader(f);

			int jdim = fr.read();
			int kdim = fr.read();

			map = new Map(jdim, kdim, true);

			for (int i = 0; i < map.jdim; i++) {
				for (int a = 0; a < map.kdim; a++) {
					int val = fr.read();

					if (val == 0) {
						map.tiles[i][a] = null;
					} else {
						if (a % 2 == 0) {
							map.tiles[i][a] = new Hex(map.xStart + (i * (map.tileSize * 2 + 14)), map.yStart + (a * map.tileSize), i, a, map.tileSize);
						} else {
							map.tiles[i][a] = new Hex(map.xStart + (i * (map.tileSize * 2 + 14)) + 17, map.yStart + (a * map.tileSize), i, a, map.tileSize);
						}
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

		return map;
	}

}
