CompSci 308: Cell Society Design Document
===================

Introduction
=======

### Problem
We want to create a Cellular Automata simulator that can run different simulations, which include cells of different states and a set of rules that determine how a cell’s state changes based on the states of its neighboring cells.


### Design Goals
Our goals for this design are to present a simple interface for users to run built-in cell automata simulations, and to make the program flexible enough so that new simulations can be easily added by other programmers


### Design Architecture
_Closed_ - the driver of the program, the element that handles UI, and abstract superclasses for the cells and simulation


_Open_ - the addition of cell types and simulations (subclasses of the abstract superclasses Cell and CellManager, respectively)


Overview
=======
![design diagram](designOverview.png)

Our design is driven by a class Driver that delegates the visual component responsibilities to a UI class, which contains a GridPane and other visual components, and the back-end responsibilities to a subclass of CellManager, which uses a subclass of Cell for the simulations computations. Driver runs a game-loop-esque procedure and a step method that continuously updates the simulation. In a given call, the step method uses a subclass of CellManager that corresponds with the type of simulation. This subclass updates the statuses of each Cell and returns the list of updated Cells to Driver. The step method then passes the list of updated Cells to UI, which uses the list to update the colors of the Rectangles in the GridPane.


User Interface
========
![UI](userInterface.png)

We will have a menu that will display on startup and have buttons for the various simulations. When any of these buttons are pressed the scene will change to show that simulation’s user interface.  There will also be a help button that displays all the information about each simulation and how the XML files are formatted.
On each simulation’s interface, we will include:


* The type of the simulation displayed in the title of the stage.
We will insert a button for starting button “START” that switches to “PAUSE” when running and switches to “RESUME” if paused and already started.

* Another button for moving the animation step by step (one animation at a time).

* Another button for switching back to the menu may need to be included if a new stage isn’t made for each simulation. This can be located in the top right corner (not included in picture).

* There will also be a slider to choose the speed at which the animation is updated.

* There will also be a brief introduction about what the simulation is of (maybe bottom left corner, also is not included in picture).

* At the bottom of the scene, we will report any errors in red.


We also have considered on the user interface side, using various windows (stages) instead of just transitioning between scenes to show the various simulations. The menu stage would remain open as long as the program is running. Each button clicked on the menu would then create a new window (stage) for that simulation. This would allow users to compare simulations side by side. NOTE: our methods in Design Details does not reflect this feature.

It would also be nice to be able to dynamically scale the simulation stage/scenes based on the size of the cellular automata specified in the XML file.


Design Details
=======

### Classes
* Driver.java (extends Application)
* This class is the main class for the program. It reads in and parses XML files, starts the program, and handles the simulation loop. 

The driver communicates with the UI and Cell Manager classes by directly passing information about the simulation and cells.


INSTANCE VARIABLES:
```java
Stage myStage
Scene myScene
Group root
CellManager simType
UI display
int fps
int gridSize
ArrayList<Color> cellStatuses
```


METHODS:
```java
/**
* Reads xmlFile and sets simType, gridSize, and cellStatuses * instance variables
* Creates instances of CellManager and UI
*/
private void readAndSet(File xmlFile)

/**
* Starts the application
* Calls UI to create the Menu
*/
public void start(Stage stage)

/**
* Runs the “gameLoop” and calls step
*/
public void simLoop()

/**
* Continuously updates the simulation 
* Interacts with CellManageer to update the statuses of
* cells and UI to update the color displays
*/
private void step(double elapsedTime, ArrayList<Color> cellStatuses)
```

* UI.java
* The UI class manages all of the visual components of the program, displaying the grid and its cells as they change states, the parameters available to be toggled by the user, and other stylistic and textual components.

INSTANCE VARIABLES:
```java
double animationSpeed
boolean start
boolean running
ArrayList<Button> buttons
GridPane grid
```

METHODS:
```java
/**
* Sets up the Menu and waits for users to interact with it
* through handleButtonClick to move to setSim
*/
public void setMenu(Stage stage, ArrayList<Button> buttons)

/**
* Sets up the simulation and waits for user interaction
* button press) to move to the simLoop
*/
private void setSim(Stage stage)

/**
* Sets the Color of each child of grid as the corresponding * Color in newColors
*/
public void updateCellColors(ArrayList<Color> newColors)

/**
* Handles mouse event when button is clicked
* Causes the transition between setMenu and setSim
*/
private void handleButtonClick(Button button)
```


* CellManager.java
* This class holds all of the Cells in the simulation and manages all of the operations necessary to update their statuses.

INSTANCE VARIABLES:
```java
	ArrayList<Cell> currentCells
	ArrayList<String> nextCellStatuses
```
	
METHODS:
```java
/**
* Returns an int array of the locations of neighbor cells
*/
public int[] getNeighborLocationNums(Cell c)

/**
* Sets nextCellStatuses by getting the next status of each 
* cell in currentCells and adding it to nextCellStatuses
*/
public void setNextCellStatuses()

/**
* Sets the status of each cell in currentCells to each 
* corresponding status in nextCellStatuses
*/
public void updateCurrentCells()

/**
* Returns arraylist of all neighboring cells
*/
private ArrayList<Cell> getNeighbors(int[] locationNums)
```

* Cell.java


INSTANCE VARIABLES:
```java
	String status
	Color color
```


METHODS:
```java
	/**
* Gets the current cell status
*/
	public String getStatus()

/**
* Sets the current cell status; also changes cell’s color
* status <--> color
*/
	public void setStatus()
```
	
	
SUBCLASSES:
FireCell.java, WatorCell.java, LifeCell.java, PredatorPrey.java

### Use Cases
