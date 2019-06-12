package lifegame;
public class GameRule {
	private Map cMap;
	private int[][] current;//当代地图
	private int[][] next;//下一代的地图
	
	public int[][] getGameMessage(){
		return cMap.getGameMap();
	}
	
	public GameRule(int row,int col) {
		cMap=new Map(row,col);
		current=new int[row][col];
		//next=new int[row][col];
		
	}
	/*public void init() {
		current = getGameMessage();
		
	}
	*/
	public void generate() {
		current=cMap.getGameMap();
		int col,row;
		col=cMap.getCol();
		row=cMap.getRow();
		next= new int[row][col];
		int i,k;
		for(i=1;i<row-2;i++) {
			System.out.print("第"+i+"行：");
			for(k=1;k<col-2;k++) {
				System.out.print(cellCount(i,k));
				switch(cellCount(i,k)) {
				case 2:
					next[i][k]=current[i][k];
					break;
				case 3:
					next[i][k]=1;
					break;
				default:
					next[i][k]=0;
				
				}
			}
			System.out.println("");
		}

		current=next;
		cMap.setGameMap(next);
		
	}
	
	//确定周围细胞数量
	private int cellCount(int i,int k) {
		int r,c,count=0;
		for(r=i-1;r<=i+1;r++) {
			for(c=k-1;c<=k+1;c++) {
				if(current[r][c]==1)
					count++;
			}
		}
		if(current[i][k]==1)
			count--;
		return count;
		
	}
	public void setCurrent(int[][] current){
		this.current=current;
	}
	public void setNext(int[][] next) {
		this.next=next;
	}
	public int[][] getCurrent(){
		return current;
	}
	
	public int[][] getNext(){
		return next;
	}
	public void setMap(int row,int col) {
		cMap=new Map(row,col);
	}
	public void setMap(int[][] tu) {
		cMap.setGameMap(tu);
	}
}
