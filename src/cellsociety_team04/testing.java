package cellsociety_team04;

import java.util.ArrayList;

public class testing {

	public static void main(String[] args) {
		
		System.out.println(3%19);
		
		Segregation segSim = new Segregation(.375, .24, .5, 100);
		segSim.initializeCurrentCells();
		ArrayList<Cell> s = segSim.getCurrentCells();
		ArrayList<Cell> p = segSim.setParamCells();
		System.out.println("Current Cells Size: " + s.size());
		System.out.println("Param Size: " + p.size());
		double rc = Math.sqrt(s.size());
		double prc = Math.sqrt(p.size());
		char[][] grid = new char[(int)rc][(int)rc];
		int i = 0;
		for(int x = 0; x < 100; x++) {
			for(int r = 0; r < rc; r++) {
				for(int c = 0; c < rc; c++) {
					if(s.get(i).getStatus().equals("Red")) grid[r][c] = 'R';
					else if(s.get(i).getStatus().equals("Blue")) grid[r][c] = 'B';
					else if(s.get(i).getStatus().equals("Empty")) grid[r][c] = 'E';
					else if(s.get(i).getStatus().equals("Null")) grid[r][c] = 'N';
					i++;
				}
			}
			i = 0;
			
			for(int r = 0; r < rc; r++) {
				for(int c = 0; c < rc; c++) {
					System.out.print(grid[r][c] + "  ");
				}
				System.out.println();
			}
			System.out.println("_________________________________");
			segSim.setNextCellStatuses();
			segSim.updateCurrentCells();
		}
			
		
	}
	
}
