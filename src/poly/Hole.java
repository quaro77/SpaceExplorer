package poly;

public class Hole {
	
	public int j;
	public int k;
	public int sector;
	public int jExit;
	public int kExit;
	public int sectorExit;
	private Map mapEnter;
	private Map mapExit;
	
	public Hole(int j, int k, int sector, int jExit, int kExit, int sectorExit, Map mapEnter, Map mapExit) {
		this.j = j;
		this.k = k;
		this.sector = sector;
		this.jExit = jExit;
		this.kExit = kExit;
		this.sectorExit = sectorExit;
		this.mapEnter = mapEnter;
		this.mapExit = mapExit;
		mapEnter.tiles[j][k].item = 5;
		mapEnter.tiles[jExit][kExit].item = 6;
	}

}
