# Refactoring
Below are the classes we refactored during lab. We specify what we changed as well as how it betters the code (removing duplicated code, simplification, clarity)

## MenuWindow.java
* got rid of duplicated code in MenuWindow for adding buttons by creating methods createMenuButton(String imageName, String buttonText) and setMenuButtonLayout(Button button, Double x, Double y)
* simplified method chooseSim() in MenuWindow by pulling out code for xml parsing & simulation creation and putting that into another method getSimFromFile(Button buttonPressed)
* eliminated magic values (the tags for each simulation type and the file names for the button images)
* edited chooseSim() to be void (getting rid of unnecessary boolean pressed)

## CellManager.java and sub classes
* eliminated magic values for cell statuses
* made setNextCellStatuses method private, now called by updateCurrentCells

## Driver.java
* pulled simulation loop methods up to parent Window class
* refactored longest method into determineSim() and into setupSim() (getting rid of duplicated code and also lets you only check the instance of the CellManager once)
* created a general simWindow (SimulationWindow) that is determined by the CellManager type (instead of creating one for every possible SimulationWindow subclass then only showing the wanted one)

## SimulationWindow.java
* removed code for displaying the grid in SimulationWindow and moved it into a new class GridDisplay.java (this greatly shortens the length of SimulationWindow and makes code easier to read with a new class specifically for the grid)