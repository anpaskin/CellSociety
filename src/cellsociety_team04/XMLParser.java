package cellsociety_team04;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import cellsociety_Simulations.CellManager;
import cellsociety_Simulations.Fire;
import cellsociety_Simulations.GameOfLife;
import cellsociety_Simulations.RPS;
import cellsociety_Simulations.Segregation;
import cellsociety_Simulations.WaTor;
import cellsociety_UIUX.SimulationWindow;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
	private Driver driver;
	private FileChooser fileChooser;
	private static Document doc;
	
	private boolean isGameOfLife = false;
	private boolean isFire = false;
	private boolean isWaTor = false;
	private boolean isSegregation = false;
	private boolean isRockPaperScissors = false;
	
	private static final List<String> GOLCells = Arrays.asList("Alive", "Dead");
	private static final List<String> FireCells = Arrays.asList("Tree", "Fire", "Empty");
	private static final List<String> WaTorCells = Arrays.asList("Fish", "Shark", "Empty");
	private static final List<String> SegCells = Arrays.asList("Red", "Blue", "Empty");
	private static final List<String> RPSCells = Arrays.asList("Rock", "Paper", "Scissors", "Empty");
	
	private ArrayList<String> simCells = new ArrayList<String>();

	public XMLParser() {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("XML Files", "*.xml"));
		fileChooser.setInitialDirectory(new File("./data"));
	}
	
	
	public CellManager getSimulation() {
		if (doc.getDocumentElement().getAttribute("simulation").equals("GameOfLife")) { 
			isGameOfLife = true; 
			return createGameOfLifeSim();
		}
		else if (doc.getDocumentElement().getAttribute("simulation").equals("Fire")) {
			isFire = true;
			return createFireSim();
		}
		else if (doc.getDocumentElement().getAttribute("simulation").equals("PredatorPrey")) {
			isWaTor = true;
			return createPredatorPreySim();
		}
		else if (doc.getDocumentElement().getAttribute("simulation").equals("Segregation")) {
			isSegregation = true;
			return createSegregationSim();
		}
		else if (doc.getDocumentElement().getAttribute("simulation").equals("RockPaperScissors")) {
			isRockPaperScissors = true;
			return createRockPaperScissorsSim();
		}
		
		
		return null;
	}

	private CellManager createSegregationSim() {
//		NodeList nList = doc.getElementsByTagName("title");
//		String simName = nList.item(0).getTextContent();
//		
		NodeList nList = doc.getElementsByTagName("minSimilar");
		double threshold = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("redRatio");
		double redRatio = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("emptyRatio");
		double emptyRatio = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("size");
		double size = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("shape");
		String shape = nList.item(0).getTextContent();
		
		nList = doc.getElementsByTagName("initialCellStatuses");
		extractCellStatuses(nList);
		
		nList = doc.getElementsByTagName("toroidalEdges");
		boolean isToroidal = stringToBoolean(nList);

		SimulationWindow.setCellShape(shape);
		return new Segregation(threshold, redRatio, emptyRatio, size, shape, isToroidal);
	}

	private CellManager createPredatorPreySim()
	{
		NodeList nList = doc.getElementsByTagName("fishPercent");
		double fishPercent = extractNodeValue(nList);

		nList = doc.getElementsByTagName("sharkPercent");
		double sharkPercent = extractNodeValue(nList);

		nList = doc.getElementsByTagName("fishBreed");
		int fishBreed = Integer.parseInt(nList.item(0).getTextContent());

		nList = doc.getElementsByTagName("sharkBreed");
		int sharkBreed = Integer.parseInt(nList.item(0).getTextContent());
		
		nList = doc.getElementsByTagName("fishEnergy");
		int fishEnergy = Integer.parseInt(nList.item(0).getTextContent());
		
		nList = doc.getElementsByTagName("initialEnergy");
		int initialEnergy = Integer.parseInt(nList.item(0).getTextContent());
	
		nList = doc.getElementsByTagName("size");
		double size = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("shape");
		String shape = nList.item(0).getTextContent();
		
		nList = doc.getElementsByTagName("initialCellStatuses");
		extractCellStatuses(nList);

		nList = doc.getElementsByTagName("toroidalEdges");
		boolean isToroidal = stringToBoolean(nList);
		
		SimulationWindow.setCellShape(shape);
		return new WaTor(sharkPercent, fishPercent, size, initialEnergy, sharkBreed, fishBreed, fishEnergy, shape, isToroidal);
	}

	private CellManager createFireSim() {
		NodeList nList = doc.getElementsByTagName("probCatch");
		double probCatch = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("size");
		double size = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("shape");
		String shape = nList.item(0).getTextContent();
		
		nList = doc.getElementsByTagName("initialCellStatuses");
		extractCellStatuses(nList);
		
		nList = doc.getElementsByTagName("toroidalEdges");
		boolean isToroidal = stringToBoolean(nList);
		
		SimulationWindow.setCellShape(shape);
		return new Fire(probCatch, size, shape, isToroidal);
	}

	private CellManager createGameOfLifeSim() {	
		NodeList nList = doc.getElementsByTagName("aliveRatio");
		double aliveRatio = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("size");
		double size = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("shape");
		String shape = nList.item(0).getTextContent();
		
		nList = doc.getElementsByTagName("initialCellStatuses");
		extractCellStatuses(nList);
		
		nList = doc.getElementsByTagName("toroidalEdges");
		boolean isToroidal = stringToBoolean(nList);
		
		SimulationWindow.setCellShape(shape);
		return new GameOfLife(aliveRatio, size, shape, isToroidal);

	}
	
	private CellManager createRockPaperScissorsSim() {
		NodeList nList = doc.getElementsByTagName("rockRatio");
		double rockRatio = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("paperRatio");
		double paperRatio = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("ScissorsRatio");
		double scissorsRatio = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("size");
		double size = extractNodeValue(nList);
		
		nList = doc.getElementsByTagName("shape");
		String shape = nList.item(0).getTextContent();
		
		nList = doc.getElementsByTagName("initialCellStatuses");
		extractCellStatuses(nList);
		
		nList = doc.getElementsByTagName("toroidalEdges");
		boolean isToroidal = stringToBoolean(nList);

		SimulationWindow.setCellShape(shape);
		return new RPS(rockRatio, paperRatio, scissorsRatio, size, shape, isToroidal);
	}
	
	private boolean stringToBoolean(NodeList nList) {
		String boolVal = nList.item(0).getTextContent();
		if (boolVal.equals("true")) {
			return true;
		}
		return false;
	}
	
	
	private void extractCellStatuses(NodeList nList) {
		String nums = (nList.item(0).getTextContent());
		String[] items = nums.replaceAll("\\s", "").split(",");
		int[] intCellStats = new int[items.length];
		for (int i = 0; i < items.length; i++) {
			try {
				intCellStats[i] = Integer.parseInt(items[i]);
		    } catch (NumberFormatException nfe) {
		        //NOTE: write something here if you need to recover from formatting errors
		    };
		}
		getSimCells();
		//System.out.println(simCells);
		ArrayList<String> cellStats = new ArrayList<String>();
		for (int k : intCellStats) {
			cellStats.add(simCells.get(intCellStats[k]));
			//System.out.println(simCells.get(intCellStats[k]));
		}
		System.out.println(cellStats);
		//Driver.setInitialCellStatuses(cellStats);
	}
	
	private void getSimCells() {
		simCells.clear();
		if (isGameOfLife) {
			simCells.addAll(GOLCells);
		} else if (isFire) {
			simCells.addAll(FireCells);
		} else if (isWaTor) {
			simCells.addAll(WaTorCells);
		} else if (isSegregation) {
			simCells.addAll(SegCells);
		} else if (isRockPaperScissors) {
			simCells.addAll(RPSCells);
		}
	}
	
	private int extractIntValue(NodeList nList) {
		String element = nList.item(0).getTextContent();
		return Integer.parseInt(element);
	}
	
	private Double extractNodeValue(NodeList nList) {
		String element = nList.item(0).getTextContent();
		return stringToDouble(element);
	}
	
	private Double stringToDouble(String element) {
		return Double.parseDouble(element);
	}

//	public void chooseFile(Stage s) {
//		File selectedFile = fileChooser.showOpenDialog(s);
//		createDocForFile(selectedFile);
//	}
	
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
