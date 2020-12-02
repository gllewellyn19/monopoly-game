## MANAGEMENT_PLAN
##### Cameron Jarnot (cmj36), Anna Diemel (ad356), Lina Leyhausen (lsl14), Delaney Demark (dad48), Grace Llewellyn (gal16)

#### Sprint 1 (Test)
First implementation: have a monopoly board with the basic rules, basic layout, and 3 players. Allow the players to pick a game piece, roll a die, have access to their own money. The game should keep track of how many houses/properties each person has.
Grace will be the front end manager for the beginning, but will also work on the back end, just delegate responsibilities for the front end.
Players can move around the board, see properties, and take cards.
* Cameron: Spaces (ownable, go, etc), Players
* Lina: Board, secondary: frontend - finding images for each space
* Delaney: Cards, Players
* Grace: Frontend/GamePlay/GameView, Taking a turn
* Anna: Rules interface, ooga.model.DataReader, secondary: frontend

#### Sprint 2 (Basic)
This style will look more into the design of how the frontend looks for the project. We will add more images to each place on the board, more player images, etc. At this phase we will also add at least one other implementation of the game with a different look and some different rules. We will also have buttons at this point to change the CSS and the language of the game. If we aren’t to have multiple players for the test version then we will implement more players in this version. 
Players will also be able to take actions on their turns (buy properties, build houses, etc). Players will also be able to pay each other rent.
The final version of the project will have several different versions of monopoly with different rules that the player can choose from (Jr Monopoly, Here and Now, etc) and several different themes (Duke Monopoly, Game of Thrones Monopoly, etc).
* Cameron: Duke theme, Ruleset, Cards
* Lina: CSS, A theme, Buying properties frontend/backend
* Delaney: A theme, Ruleset, Paying rent, Cards
* Grace: CSS, GOT theme
* Anna: A theme, Languages, House building

#### Sprint 3 (Complete)
Each theme will have different texts for the different cards. Any combination of a game type and theme will work. Multiple players will be able to play, and AI players can optionally play as well.
* Everyone: Implement the new card set for your theme
* Cameron: Different AI strategies
* Lina: New rulesets
* Delaney: Deal with Design Coach issues
* Grace: Clean up frontend
* Anna: Different AI strategies
