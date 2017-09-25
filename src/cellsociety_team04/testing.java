package cellsociety_team04;

import java.util.ArrayList;

import cellsociety_Cells.Cell;
import cellsociety_Simulations.Fire;
import cellsociety_Simulations.GameOfLife;
import cellsociety_Simulations.Segregation;
import cellsociety_Simulations.WaTor;

public class testing {

	public static void main(String[] args) {
		//segTesting(.5, .25, .5, 100);
		//fireTesting(.75, 66, 100);
		//lifeTesting(.5, 100);
		waTorTesting(0.2, 0.35, 100);
	}
	
	private static void waTorTesting(double sharkPercent, double fishPercent, double size) {
		WaTor waSim = new WaTor(sharkPercent, fishPercent, size);
		waSim.initializeCurrentCells();
		ArrayList<Cell> s = waSim.getCurrentCells();
		double rc = Math.sqrt(s.size());
		String[][] grid = new String[(int)rc][(int)rc];
		int i = 0;
		for(int x = 0; x < size; x++) {
			for(int r = 0; r < rc; r++) {
				for(int c = 0; c < rc; c++) {
					String sStatus = s.get(i).getStatus();
					if(sStatus.equals("Shark")) grid[r][c] = "S";
					else if(sStatus.equals("Fish")) grid[r][c] = "f";
					else if(sStatus.equals("Empty")) grid[r][c] = "_";
					else if(sStatus.equals("Null")) grid[r][c] = "N";
					/*if(!sStatus.equals("Null")) {
						System.out.print("Neighbors: ");
						for(Cell n : waSim.getNeighbors(s.get(i))) {
							System.out.print(s.indexOf(n) + ", ");
						}
						System.out.println();
					} */
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
			System.out.println("===================================================");
			waSim.setNextCellStatuses();
			waSim.updateCurrentCells();
		}
	}
	
	private static void lifeTesting(double alive, double size) {
		GameOfLife lifeSim = new GameOfLife(alive, size);
		ArrayList<Cell> s = lifeSim.getCurrentCells();
		lifeSim.initializeCurrentCells();
		double rc = Math.sqrt(s.size());
		char[][] grid = new char[(int)rc][(int)rc];
		int i = 0;
		for(int x = 0; x < size; x++) {
			for(int r = 0; r < rc; r++) {
				for(int c = 0; c < rc; c++) {
					String sStatus = s.get(i).getStatus();
					if(sStatus.equals("Alive")) grid[r][c] = 'L';
					else if(sStatus.equals("Dead")) grid[r][c] = '_';
					else if(sStatus.equals("Null")) grid[r][c] = 'N';
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
			System.out.println("===================================================");
			lifeSim.setNextCellStatuses();
			lifeSim.updateCurrentCells();
		}
	}
	
	
	private static void fireTesting(double probCatch, int fireStartLoc, double size) {
		Fire fireSim = new Fire(probCatch, size);
		ArrayList<Cell> s = fireSim.getCurrentCells();
		fireSim.initializeCurrentCells(fireStartLoc);
		double rc = Math.sqrt(s.size());
		char[][] grid = new char[(int)rc][(int)rc];
		int i = 0;
		for(int x = 0; x < size; x++) {
			for(int r = 0; r < rc; r++) {
				for(int c = 0; c < rc; c++) {
					String sStatus = s.get(i).getStatus();
					if(sStatus.equals("Tree")) grid[r][c] = 'A';
					else if(sStatus.equals("Fire")) grid[r][c] = '^';
					else if(sStatus.equals("Empty")) grid[r][c] = '_';
					else if(sStatus.equals("Null")) grid[r][c] = 'N';
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
			System.out.println("===================================================");
			fireSim.setNextCellStatuses();
			fireSim.updateCurrentCells();
		}
		
	}
	
	private static void segTesting(double minSim, double emptyCount, double redCount, double size) {
		Segregation segSim = new Segregation(minSim, emptyCount, redCount, size);
		segSim.initializeCurrentCells();
		ArrayList<Cell> s = segSim.getCurrentCells();
		//ArrayList<Cell> p = segSim.setParamCells();
		System.out.println("Current Cells Size: " + s.size());
		int red = 0;
		int blue = 0;
		int empty = 0;
		for(Cell c : s) {
			if(c.getStatus().equals("Empty")) {
				empty++;
			}
			else if(c.getStatus().equals("Red")) {
				red++;
			}
			else if(c.getStatus().equals("Blue")) {
				blue++;
			}
		}
		System.out.println("Initial Empty Count: " + empty);
		System.out.println("Initial Red Count: " + red);
		System.out.println("Initial Blue Count: " + blue);
		//System.out.println("Param Size: " + p.size());
		double rc = Math.sqrt(s.size());
		//double prc = Math.sqrt(p.size());
		char[][] grid = new char[(int)rc][(int)rc];
		int i = 0;
		for(int x = 0; x < size; x++) {
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
			System.out.println("===================================================");
			segSim.setNextCellStatuses();
			segSim.updateCurrentCells();
		}
		
		red = 0;
		blue = 0;
		empty = 0;
		for(Cell c : segSim.getCurrentCells()) {
			if(c.getStatus().equals("Empty")) {
				empty++;
			}
			else if(c.getStatus().equals("Red")) {
				red++;
			}
			else if(c.getStatus().equals("Blue")) {
				blue++;
			}
		}
		System.out.println("Final Empty Count: " + empty);
		System.out.println("Final Red Count: " + red);
		System.out.println("Final Blue Count: " + blue);
	}
	
	
}
