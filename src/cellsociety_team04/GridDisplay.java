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
	private static final double one = 1.0;
	private static final double zero = 0.0;
	private static final String NULL = "Null";
	
	private int numCells;
	private int cellSize = 50;
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
		grid.setLayoutX(width - numCells*cellSize - offset);
		grid.setLayoutY(offset);
		setHVGaps(grid);
	}
	
	private void setHVGaps(GridPane grid) {
		if (cellShape.equals("triangle")) {
			grid.setHgap(-10);
		} else if (cellShape.equals("hexagon")) {
			
		} else {
			// do nothing
		}
	}
	
	// pass in currentCells array list and get array list of colors to fill grid
	private void getCellColors(List<Cell> cellStatuses) {
		cellColors.clear();
		for (int i = 0; i < cellStatuses.size(); i++) {
			cellColors.add(cellStatuses.get(i).getColor());
		}
	}
	
	private void createCellShape(Polygon polygon) {
		if (cellShape.equals("square")) {
			squareCell(polygon);
		} else if (cellShape.equals("triangle")) {
			triangleCell(polygon);
		} else {
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
				third*cellSize, zero,
				twothirds*cellSize, zero,
				one*cellSize, half*cellSize,
				twothirds*cellSize, one*cellSize,
				third*cellSize, one*cellSize
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
