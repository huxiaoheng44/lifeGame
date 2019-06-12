package lifegame;
public class Map {
	private int row;//棋盘的行
	private int col;//列 
	private int[][] gameMap;
	
	
	public Map(int row,int col) {
		this.row=row;
		this.col =col;
	}
	
	public int[][] getGameMap(){
		return gameMap;
	}
	public void setGameMap(int[][] map) {
		gameMap=map;
	}
	public void init() {
		this.row=20;
		this.col=40;
		//Gamemap[row][col];
	}
	
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
}
