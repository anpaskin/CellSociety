Team 4
Aaron Paskin (anp36)
Dara Buggay (deb26)
Kelly Zhang (kz49)

Team 12
Matthew Dickson (mdd36)
Natalie Huffman (nch15)
Owen Smith (os29)

Back End
Part 1
Teams 4 and 12 both encapsulate CellGroup/CellManager and Cell classes, which generically operate on and update Cells throughout a given simulation of any type. Future developers are thus able to create new simulation types that can use CellGroup/CellManager and Cell.
Team 4 implements inheritance at the cell group level, but both teams have a hierarchy of cell classes. In the active cell manager approach taken by group 4, cells are called on by the cell manager to update themselves to a state, while the cell hierarchy approach of group 12 has the cells check for updates and update themselves.
Team 12 has the CellGroup class closed, since it should be able to manage Cells of any implementation with no differences. The Cell class, on the other hand, is open, since all of the cell subclasses inherit methods from the Cell class. For Team 4, the CellManager class is open, since it changes based on the simulation being implemented, and the Cell class, which has subclasses related to implementation, is also open.
There is the potential for NullPointerExceptions when getting the neighbors of a cell, especially if the cell is an edge cell and has less than eight neighbors. It is also possible that a simulation might try to move a Cell to an empty location but can not find one because no empty locations exist in the grid. Additionally, the CellGroup/CellManager classes can throw an exception if the arguments received from the XML parsing class are invalid for the particular simulation.
In the Group 4 approach, new simulations are easy to implement by just extending the CellManager and Cell classes, which could make changing global update rules by updating one location. Conversely, Group 12’s approach allows for different shaped simulations and encapsulated behavior in the Cell’s actions.

Part 2
The back end depends on the UI and front end to accurately and reliably display the data. Additionally, the CellGroup/CellManager classes rely on the Cell classes (and vice versa), as they rely on each others’ methods.
These dependencies are independent of the implementation and behavior in the front end classes, but dependent for the CellGroup/CellManager and Cell classes.
We minimize the dependencies between back end and front end by delegating the responsibilities of each to two separate inheritances, each used by a single driver class.
Team 4 handles the operations on the cells, including checking neighbors and updating statuses, in its CellManager class (and its children), while Team 12 handles these operations in the Cell class (and its children). Team 12’s CellGroup class uses the Cell as a tool for making updates rather than just as a data storage unit.

Part 3


Make sharks/minnows reproduce
User hits reset button
Detect end of simulation
Move minority cell to new empty
Initialize the cells
Team 12 is excited about the recursive search for neighbors; Team 4 is excited about implementing the specific rules of different simulations.
Team 12 is not looking forward to creating an algorithm to create a list of neighbors; Team 4 is not excited to resolve the bugs that come with passing the back end data to the UI.

UI/UX and XML
PART 1
We decided to have a separate class for the UI that controls all of the buttons and sliders the user will be interacting with. Team 4 is planning on having a menu to enable the user to switch games if he/she wants, while we will have that feature in our XML file. In addition, each team has a separate XML class that handles reading in the necessary parameters.
Team 4 is considering having a hierarchy within UI that will consist of different windows/stages. There will be an abstract class that is just a Stage with specific size. Then there will be a MenuWindow and SimulationWindow subclasses. SimulationWindow could also be an abstract class that has a subclass for each different type of simulation (fire, segregation, gameoflife, wator, predprey). The transitions and event handlers will all be within these window subclasses.
On the contrast, team 12 is just having one class for UI that handles everything, but does not handle transitions between scenes/stages.
Both teams 4 and 12 are having the display for the grid and buttons closed. Then any components to change the display or input values can be changed (open) by changing or editing XML files.
Exceptions include an invalid XML file or invalid elements within a file. They will be handled by throwing error messages.

PART 2:
For both teams 4 and 12, the UI class(es) are pretty independent. However, there is a dependency with CellManager class since the UI has to communicate with the CellManager to obtain the information for the next grid display (new cell statuses). The simulation subclasses are dependent on XML files for those simulations.
The UI/UX class dependencies are based on the implementation and behavior of the Simulation subclasses as well as the implementation of the Driver/Game main class.
We can minimize dependencies by having the best encapsulation possible and having each class interact (if necessary) in main. 
In the Window superclass, we will have a stage that is formatted with default parameters such as size/dimensions, window location, background color, etc. This will also have the grid of cells used in each simulation. The subclasses will have the specifics for that simulation/menu, with buttons, sliders, titles, and text.

PART 3:
One: buttons on the UI for changing which simulation is running
Two: toggle bar to change parameters such as redBlue ratio/percent sharks/probcatch, etc 
Three: changing parameters after initialization through the XML file (while running, in between steps/when paused, before starting)
	Four: reading simulation specific XML information and displaying those values
	Five: selecting different files in the program without editing code/reset to initialization
We are excited about making the UI as intuitive and easy for users as possible. In particular, having different windows in a menu screen where the user can select the simulation is of particular interest to team 4. 
We are most worried about tying in the buttons with the grid display, i.e., having a button or toggle change the simulation
 



