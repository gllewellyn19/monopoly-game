Design
##### Grace Llewellyn (gal16), Anna Diemel (ad356), Cameron Jarnot (cmj36), Lina Leyhausen (lsl14), Delaney Demark (dad48)

## Roles
Grace: Fronteend, Connecting the different parts of the project, GameView and GamePlay, Controller, Main, Buttons, Splash screen, Refactoring, Error catching, Game of Thrones Monopoly
Anna: Data reading, Mortgaging, Trading, Real estate, Players, Money display, Property, Initializing game, Junior Monopoly
Cameron: Board space logic, Connecting frontend and backend when a space is landed on, Turns, Duke theme, Board space factory, Backend money updates, Bank
Lina: Primarily worked in view with PopUp, Prompter, DisplayBoard, and DisplaySpaces. Worked on model of Board
Delaney: Primarily worked in model with the Cards, Player, Ruleset, Board Spaces and related interactions both in model and view. In terms of adding data, created card-related data and added the National Parks themed Monopoly

## Design Goals
Overall, we aimed to have maximum flexibility in all aspects of our game, even
for features that we did not plan to make additions to. Although we knew that
choosing to create the game of Monopoly meant that we were not required to
implement other games of the same nature due to the complexity of the rules,
we aimed to make it very simple to implement other versions of Monopoly, both
with different rules and themes.

## High level Design
   The design of our monopoly game is broken into a view, a model, and a controller
   to go between them. In the model, the program represents the players, the decks
   of cards, the board spaces, and the mechanics of game play. This is where details
   about which board spaces and cards the game is using are read in from data, as 
   well as specific rules that are mutable between the types of monopoly the program 
   supports. The GamePlay class of the model retrieves the information from the data
   readers and instantiates the players, cards, and board based on the user’s chosen
   sets of data. Using the model instances, the GamePlay and TurnModel classes are 
   able to have players roll dice, land on spaces, draw cards, and buy properties. 
   
   All of the different components of the game of Monopoly are created within the 
   various packages within the model. For example, there are packages for creating 
   and handling the board spaces, the board made of board spaces, the bank, the 
   card decks, the players, the real estates, the dice, and the different rules 
   of the games. In order to initialize the start of the game, there are the data 
   readers that parse required data for a game of Monopoly, and a package that 
   determines how the player starts the game. For many of these components, there 
   are factories for objects that depend on data for initialization.	
   The view part of the project contains the aspects of the visualizations that 
   correspond to objects created on the backend, such as the board and the player 
   statistics. There is also a package for handling the way that player turns are 
   reflected in the view. Additionally, there are aspects of the view that are 
   organized into the purposes they serve solely within the view, which includes 
   the buttons and the pop-ups. These classes were separated into their own packages 
   due to the sheer number of uses and variations for the buttons and pop-ups within 
   the view.
   
   Through interfaces and through the controller, the model is also able to communicate
   with the view. When the game is started, the controller is able to start the GamePlay 
   and GameView and set them up with the resources the user specifies. Beyond that, 
   the model is able to communicate with the view that updates to the graphics should
   be made and get user input through interfaces. 

## Assumptions & Decisions Made
For handling interactions between significant components of the project that we wanted to keep entirely separate in order to best abide by course principles, we decided to move away from solely using the turns package in the model and related interfaces. In addition to these classes and interfaces, we decided to use a multitude of interfaces in order to communicate updates on the backend that needed to be reflected within the model, and vice versa. This is because the frequency of communication and the different kinds of information needed grew too large for our initial simpler solution.
Despite our very flexible design, there were a few assumptions made in order to simplify the project’s design. These basic assumptions include that the number of board spaces in the game should be divisible by four, and the types of board spaces are limited but abstractable. The types of actions that the cards could handle in the game is enumerated for the sake of avoiding superfluous rule handling. Similarly, to avoid extra unnecessary rules that were not integral to the functionality, the number of buildings and amount of money within the game is unlimited. Lastly, since our project is data-based, there are assumptions about the structures of the data files.

## Significant Design Changes Since Original Plan
In the original design of the project, we planned to have a limited number of abstract classes as needed for implementing variations of different features. This has changed drastically, as now the highest level of every significant class is an abstract class for maximum flexibility in our design. Also, we did not originally plan to use many interfaces for necessary interactions between classes, particularly between frontend and backend, while in the final version of the project communication between separated components of the code is entirely handled by triggering interfaces.

## How to add new features
In order to add new features to the game, the editor needs to create new classes that extend already existing abstract classes. For example, if one would want to create a different type of card deck, they would simply create a class that extends Cards within the cards package. Then, by updating the corresponding property (ie. diceType, cardsType, boardType, etc) to the class name they would like to use.


For adding new versions of Monopoly, one would have to create a new folder within the data folder that includes all of the data files needed to start the game,
such as the properties files with the game initializing information and locations
of the required data. A good template for this would be to copy an existing monopoly game and fill in the values you wish to change from the properties file, though the majority of the properties are not required. The most paramount data file to include is a CSV with the list of all of the board spaces in the order you wish for them to appear.

Adding a new GamePlay, GameView and DataReader all mean extending the abstract class then adding the name of your implementation to the default values properties file (make sure to put the class in the same package as the other implemented classes). Examples of the keys for this can be found in the existing default properties file. Adding a new version of any of the other abstract levels at the highest level means extending the abstract method and then adding the name of that class to your properties file. Examples of these keys can be found in the existing ClassicMonopoly properties file. 

Adding new buttons means extending an abstract buttons class and adding that button name to the default properties file as shown in the current default properties file.
