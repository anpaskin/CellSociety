package cellsociety_team04;

import java.util.ArrayList;
import java.util.List;

import cellsociety_Cells.Cell;
import cellsociety_UIUX.SimulationWindow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * 
 * @author Dara Buggay
 *
 */

public class GridDisplay {
	private static final double half = 0.5;
	private static final double third = 0.33;
	private static final double twothirds = 0.66;
	private static final double one = 1.0;
	private static final double zero = 0.0;
	private static final int gridXStart = 250;
	
	private static final String NULL = "Null";
	private static final String SQUARE = "square";
	private static final String TRI = "triangle";
	private static final String HEX = "hexagon";
	
	private boolean isSquare = false;
	private boolean isTri = false;
	private boolean isHex = false;
	
	private int numCells;
	private int cellSize;
	private int currRow;
	private int currCol;
	private int everyOther;
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
		determineCellShape();
		for (int row = 0; row < numCells; row++) {
			for (int col = 0; col < numCells; col++) {
				currRow = row;
				currCol = col;
				everyOther = currRow + currCol;
				Polygon polygon = new Polygon();
				createCell(polygon);
				setCellColorAndLocation(polygon, currentCells);
				grid.getChildren().addAll(polygon);
			}
		}
		grid.setLayoutX(gridXStart);
		grid.setLayoutY(offset/2);
		setHVGaps(grid);
	}
	
	private void setHVGaps(GridPane grid) {
		if (isTri) {
			grid.setHgap(-0.5*cellSize);
		} else if (isHex) {
			grid.setVgap(-0.5*cellSize);
			grid.setHgap(-0.5/(Math.pow(3, 0.5))*cellSize);
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
	
	private void determineCellShape() {
		if (cellShape.equals(SQUARE)) {
			isSquare = true;
		} else if (cellShape.equals(TRI)) {
			isTri = true;
		} else {
			isHex = true;
		}
	}
	private void createCell(Polygon polygon) {
		if (isSquare) {
			squareCell(polygon);
		} else if (isTri) {
			triangleCell(polygon);
		} else {
			if (everyOther % 2 == 0) {
				hexagonCell(polygon);
			}
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
		if (everyOther % 2 == 0) {
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
