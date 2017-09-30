package cellsociety_team04;

import java.util.ArrayList;
import java.util.List;

import cellsociety_Cells.Cell;
import cellsociety_UIUX.SimulationWindow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class GridDisplay {
	private static final double half = 0.5;
	private static final double third = 0.33;
	private static final double twothirds = 0.66;
	private static final double hexshift = 0.5/Math.pow(3,0.5);
	private static final double one = 1.0;
	private static final double zero = 0.0;
	private static final String NULL = "Null";
	
	private static final int gridXStart = 250;
	
	private int numCells;
	private int cellSize;
	private int currRow;
	private int currCol;
	private String cellShape;
	private ArrayList<Color> cellColors = new ArrayList<Color>();
	private double width;
	private double offset;
	
	public GridDisplay(int nCells, int size, String shape) {
		numCells = nCells;
		cellSize = size;
		cellShape = shape;

		width = SimulationWindow.getWidth();
		offset = SimulationWindow.getOffset();
	}
	
	public void updateGridDisplay(List<Cell> currentCells, GridPane grid) { //https://stackoverflow.com/questions/35367060/gridpane-of-squares-in-javafx
		getCellColors(currentCells);
		grid.getChildren().clear();
		for (int row = 0; row < numCells; row++) {
			for (int col = 0; col < numCells; col++) {
				currRow = row;
				currCol = col;
				Polygon polygon = new Polygon();
				createCellShape(polygon);
				setCellColorAndLocation(polygon, currentCells);
				grid.getChildren().addAll(polygon);
			}
		}
		grid.setLayoutX(gridXStart);
		grid.setLayoutY(offset/2);
	}
	
	// pass in currentCells array list and get array list of colors to fill grid
	private void getCellColors(List<Cell> cellStatuses) {
		cellColors.clear();
		for (int i = 0; i < cellStatuses.size(); i++) {
			cellColors.add(cellStatuses.get(i).getColor());
		}
	}
	
	private void createCellShape(Polygon polygon) {
		if (cellShape == "square") {
			squareCell(polygon);
		} else if (cellShape == "triangle") {
			triangleCell(polygon);
		} else if (cellShape == "hexagon") {
			hexagonCell(polygon);
		}
	}
	
	private void squareCell(Polygon polygon) {
		polygon.getPoints().addAll(new Double[]{
				zero, zero,
				one*cellSize, zero,
				one*cellSize, one*cellSize,
				zero, one*cellSize
		});
	}
	
	private void triangleCell(Polygon polygon) {
		if ((currRow + currCol) % 2 == 0) {
			polygon.getPoints().addAll(new Double[]{
					half*cellSize, zero,
					zero, one*cellSize,
					one*cellSize, one*cellSize
			});
		}
		else {
			polygon.getPoints().addAll(new Double[]{
					zero, zero,
					half*cellSize, one*cellSize,
					one*cellSize, zero
			});
		}
	}
	
	private void hexagonCell(Polygon polygon) {
		polygon.getPoints().addAll(new Double[] {
				zero, half*cellSize,
				hexshift*cellSize, zero,
				(1-hexshift)*cellSize, zero,
				one*cellSize, half*cellSize,
				(1-hexshift)*cellSize, one*cellSize,
				hexshift*cellSize, one*cellSize
		});
	}
	
	
	private void setCellColorAndLocation(Polygon polygon, List<Cell> currentCells) {
		int cellNum = currRow*numCells + currCol;
		polygon.setFill(cellColors.get(cellNum));
		if (!currentCells.get(cellNum).getStatus().equals(NULL)) {
			polygon.setStroke(Color.BLACK);
		}
		GridPane.setRowIndex(polygon, currRow);
		GridPane.setColumnIndex(polygon, currCol);
	}
}
