package cellsociety_team04;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import cellsociety_Simulations.CellManager;
import cellsociety_Simulations.Fire;
import cellsociety_Simulations.GameOfLife;
import cellsociety_Simulations.Segregation;
import cellsociety_Simulations.WaTor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * This class handles parsing XML files and returning CellManager simulation
 * This class is inspired by XMLParser in example_xml lab (authors- Rhondu Smithwick & Robert C. Duvall)
 * @author Dara Buggay
 * 
 */


public class XMLParser {
	private FileChooser fileChooser;
	private static Document doc;

	public XMLParser() {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("XML Files", "*.xml"));
		fileChooser.setInitialDirectory(new File("./data"));
	}
	
	
	public CellManager getSimulation() {
		if (doc.getDocumentElement().getAttribute("simulation").equals("GameOfLife")) return createGameOfLifeSim();
		else if (doc.getDocumentElement().getAttribute("simulation").equals("Fire")) return createFireSim();
//		else if (doc.getDocumentElement().getAttribute("simulation").equals("PredatorPrey")) return createPredatorPreySim();
		else if (doc.getDocumentElement().getAttribute("simulation").equals("Segregation")) return createSegregationSim();
		
		return null;
	}

	private CellManager createSegregationSim() {
		NodeList nList = doc.getElementsByTagName("title");
		String simName = nList.item(0).getTextContent();
		
		nList = doc.getElementsByTagName("minSimilar");
		double threshold = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("redRatio");
		double redRatio = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("emptyRatio");
		double emptyRatio = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("size");
		double size = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("shape");
		String shape = nList.item(0).getTextContent();
		
		nList = doc.getElementsByTagName("initalCellStatuses");
		//ArrayList<String> initCells = extractCellStatuses(nList, simName);

		return new Segregation(threshold, redRatio, emptyRatio, size, shape, false);
	}
	
	
	private int[] extractCellStatuses(NodeList nList, String sim) {
		String nums = (nList.item(0).getTextContent());
		String[] items = nums.replaceAll("\\s", "").split(",");
		int[] cellStats = new int[items.length];
		for (int i = 0; i < items.length; i++) {
			try {
				cellStats[i] = Integer.parseInt(items[i]);
		    } catch (NumberFormatException nfe) {
		        //NOTE: write something here if you need to recover from formatting errors
		    };
		}
		return null;
	}

//	private CellManager createPredatorPreySim()
//	{
//		NodeList nList = doc.getElementsByTagName("fishPercent");
//		double fishPercent = extractNodeValue(nList);
//
//		nList = doc.getElementsByTagName("sharkPercent");
//		double sharkPercent = extractNodeValue(nList);
//
//		nList = doc.getElementsByTagName("fishBreed");
//		double fishBreed = extractNodeValue(nList);
//
//		nList = doc.getElementsByTagName("sharkBreed");
//		double sharkBreed = extractNodeValue(nList);
//		
//		nList = doc.getElementsByTagName("sharkStarve");
//		double sharkStarve = extractNodeValue(nList);
//
//		nList = doc.getElementsByTagName("size");
//		double size = extractNodeValue(nList);
//		
//		nList = doc.getElementsByTagName("shape");
//		String shape = nList.item(0).getTextContent();
//
//		
//		return new WaTor(sharkPercent, fishPercent, size, sharkInitial, sharkBreed, fishBreed, fishEnergy, shape, false);
//	}

	private CellManager createFireSim() {
		NodeList nList = doc.getElementsByTagName("probCatch");
		double probCatch = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("size");
		double size = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("shape");
		String shape = nList.item(0).getTextContent();
		
		return new Fire(probCatch, size, shape, false);
	}

	private CellManager createGameOfLifeSim() {	
		NodeList nList = doc.getElementsByTagName("aliveRatio");
		double aliveRatio = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("size");
		double size = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("shape");
		String shape = nList.item(0).getTextContent();
		
		return new GameOfLife(aliveRatio, size, shape, false);

	}
	
	private Double extractNodeValue(NodeList nList) {
		String element = nList.item(0).getTextContent();
		return stringToDouble(element);
	}
	
	private Double stringToDouble(String element) {
		return Double.parseDouble(element);
	}
//	
//	private int[][] createGrid()
//	{
//		Scanner scanner;
//		NodeList nList = doc.getElementsByTagName("row");
//
//		int[][] testGrid = new int[getNumRows()][getNumCols()];
//		for (int i = 0; i < getNumRows(); i++)
//		{
//			scanner = new Scanner(nList.item(i).getTextContent());
//			for (int j = 0; j < getNumCols(); j++)
//			{
//				testGrid[i][j] = scanner.nextInt();
//			}
//		}
//		return testGrid;
//	}
//	
//	private int getNumRows()
//	{
//		NodeList nList = doc.getElementsByTagName("GridHeight");
//		return Integer.parseInt(nList.item(0).getTextContent());
//	}
//	private int getNumCols()
//	{
//		NodeList nList = doc.getElementsByTagName("GridWidth");
//		return Integer.parseInt(nList.item(0).getTextContent());
//	}

	public void chooseFile(Stage s) {
		File selectedFile = fileChooser.showOpenDialog(s);
		createDocForFile(selectedFile);
	}
	
	public void buttonChooseFile(File file) {
		File buttonFile = file;
		createDocForFile(buttonFile);
	}
	
	public void createDocForFile(File file) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try 
		{
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


