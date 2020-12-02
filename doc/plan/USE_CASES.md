## USE_CASES (5 each)
### Anna:
Player X is up next in the game. They take their turn.
``` java
	turns.takeATurn();
	TurnModel playerXTurn = turns.getTurnModel();
```
It is Player X’s turn. They must pay rent to Player Y.
```java
	Player playerY = ownableSpace.getPlayer();
	int rentToPay = ownableSpace.getRent();
	playerX.pay(playerY, rentToPay);
```
It is Player X’s turn. They buy the property they are on from the bank.
``` java
	if (playerX.buysCurrentSpace()) {
		playerX.buyFromBank(playerX.getCurrentSpace());
	}
```
Player X buys a property from Player Y.
``` java
	playerX.buyFromPlayer(playerX.getCurrentSpace(), playerY);
```
Player X tries to place a house on a property, but cannot due to the rules.
``` java
	if (playerX.canPlaceHouse()) { // something }
	else {
ooga.controller.informPlayer(ooga.controller.getRules().getHouseErrorMessage());
	}
```

### Delaney:
Player has landed on a community chest space and must draw the appropriate card.
```java
	Cards communityChestDeck = new CommunityChestCards();
	Card communityChest = communityChestDeck.drawCard();
	playerOne.getPlayerCards().add(communityChest);
```
The Player has drawn a card that sends them straight to jail.
```java
	UserPiece player1 = new UserPiece();
	player1.updatePiecePosition(jailX, jailY);
	player1.goToJail();
```
The Player is in jail. It is their turn. They must roll doubles to get out of jail.
```java
	Dice one = new Dice();
	Dice two = new Dice();
	int rollOne = one.rollDice();
	int rollTwo = two.rollDice();
	if(rollOne == rollTwo) player1.getOutOfJail;
```
The Player lands on a chance space and draws a chance card. The card tells them to move to advance to the Boardwalk.
```java
	Cards chanceDeck = new ChanceCards();
	Card chance = chanceDeck.drawCard()
	// use reflection to call the method given the card message 
	// to move the player to Boardwalk
	chance.getCardMessage();
```
The Player lands on a chance space and draws a chance card that tells them to advance to the railroad. If someone owns the railroad, they must pay rent. Otherwise, they must buy it. Check if the space is owned and take the proper action. If the amount paid is too high, the player loses the game.
```java
	Card chance = chanceDeck.drawCard()
	// use reflection to call the method given the card message 
		// to move the player to Railroad
	chance.getCardMessage();
	if(railroad.getOwner()==null){
		railroad.setOwner(player1);
		player1.pay(railroad.getSellPrice());
	}
	else player1.pay(player2, railroad.getRent());
		player1.checkIfGameLost();
```

### Cameron:
Player X lands on “Pay taxes” space. They must choose to either pay $200 or 10% of the money they possess.
```java
	currPlayer 
	//use two button inputs
	if(payTwoHundredButton.isPressed()){
		currPlayer.setBalance(currPlayer.getBalance()-200);
	}
	else if(payTenPercentButton.isPressed()){
		currPlayer.setBalance(currPlayer.getBalance()*0.9);
	}
```
Player X lands on an available property and chooses not to buy it. Player Y can choose to bid on it.
```java
	int bid = 0;
	if(noBidButton.isPressed()){
		TurnModel.endTurn();
	}
	else if(entersAmount){
		bid = typeBidBox.onAction(e -> updateBid(e));
	}
	//update bid sets bid to the amount entered
```
Player X passes go during their turn. $200 must be added to their total.
```java
	Player currPlayer;
	currPlayer.setBalance(currPlayer.getBalance()+200);
```
Player X lands on “Free parking” space. They collect the money from taxes, jail, etc. in the middle.
```java
	Player currPlayer;
	FreeParkingMoney fpMoney;
	currPlayer.setBalance(currPlayer.getBalance()+fpMoney.cashOutFPMoney);
```
Player X begins to accumulate debt. They may choose a property to mortgage.
	the middle.
```java
	Player currPlayer;
	if(currPlayer.getBalance() < 0){
		offerMortgage();
	}
	//offer mortgage is a method that will offer a prompt to the
	//user to give them the option of which property to mortgage
```

### Grace:
Player X wants to make an offer to Player Y for their property
```java
  	// this is in ooga.view and called when the user presses the Offer property button
  	OwnableProperty propertyOfferingFor = showPropertiesToChooseFrom(userID); //shows all the properties the user can offer for that aren’t the users
	Offer offerForProperty = getPropertyOffer();
	int[] idsInOffer = new int[2];
	idsInOffer[0] = userID;
	idsInOffer[1] = propertyOfferingFor.getUserID();
	int count = 0;
	while (offerForProperty.getEndOffering() || offerForProperty.getAcceptOffer()) {
  	offerForProperty = getPropertyOffer(offerForProperty.getMoney, offerForProperty.getProperties); // continues getting offers for the property with the overloaded method until the users want to quit
	count++;
	if (count == idsInOffer.length)
		count = 0;
	}
	if (offerForProperty.getAcceptOffer()) {
		changeMoneyBalanceControls.transferMoneyFromTo(idsInOffer[0], idsInOffer[1], offerForProperty.getMoneyAmount());
   		changePropertyControls.transferPropertyFromTo(idsInOffer[0], idsInOffer[1], offerForProperty.getProperties());
	}
```
Player X lands on a utility 
```java
// this is in the Utilities class and is called when the user lands on this space
	int idOfOwner // instance variable
  	void actionWhenUserLandsOnSpace(int userID) {
  	int utilitiesOwnedByOwner = utilitiesControls.getUtilityOwnedByOwner()
  	int priceToPay = super.calculateCost()
  	bankControls.transferMoneyFromTo(userID, utilitiesOwnedByOwner, priceToPay);
}
  	
```
Player X rolls 3 doubles in a row
```java
  	
// inside turn class an the loop of the players
	while (!players.isEmpty()) {
  		int numberOfRolls = 0;
  		TurnView turnView;
  		TurnModel turnModel;
  		do{
  	  		// do the logic inside each turn
        	numberOfRolls ++;
  		} while(determineTurnOver(numberOfRolls, currentPlayer, turnView));
  	 
  		if (numberOfRolls == maxNumberOfRolls()) {
        	gameView.sendUserToJail(userID);
		}
  		if (currentPlayer.determinePlayerElimated()) {
        	players.remove(currentPlayer);
		}
	}
 
	boolean determineTurnOver (int rolls, Player currentPlayer, TurnView turnView) {
  	return rolls <= maxNumberOfRolls && !currentPlayer.determinePlayerElimated() && turnView.getRollAgain())
```
Player X has no money and has mortgaged all properties 
```java
// in the player class
	boolean determinePlayerEliminated() {
  		return balance == 0 && checkAllPropertiesMortages();
	}
 
// in the turns class
	while (!players.isEmpty()) {
  	// do the turn loops and at the end of the turn:
  		if (currentPlayer.determinePlayerElimated()) {
        	players.remove(currentPlayer);
		}
	}
```
Player X buys the property they just landed on and completes the color group
```java
	BoardSpace currentSpot; // passed into method
	if (currentSpot.isOwnable() && !currentSpot.isOwned && promptUserWithTrueFalseKey(“buyProperty”)) {
  		propertyManage.buyPropertyFromBank(playerID, currentSpot);
	}
 
// if the property is landed on again then it is enabled that the user can build as told by
	boardSpace.hasMonopoly();
```

### Lina:
The game begins. Each player is allocated a certain amount of money (and possibly properties).
```Java
	GamePlay newGame = new GamePlay();
	Rules ruleSet = new BasicRules();
	for(int i=0;i<NUM_PLAYERS;i++){
		newGame.addPlayer();
		int startingFunds = ruleSet.getStartingFunds();
		int numStartingProperties = ruleSet.getNumStartingProperties();
		newGame.getPlayer(i).addFunds(startingFunds);
		newGame.getPlayer(i).addRandomProperties(numStartingProperties);
	};
```
Player X draws a card that grants them money, and their personal funds are incremented.
```Java
\\ In the TurnModel class:
	Card newCard = chanceDeck.drawCard();
	playerOne.addCard(newCard);
	newCard.implement(playerOne);

\\ In the MoneyCard class:
	player.addFunds(CARD_VALUE);
```
Player X draws a card that charges them money based on the amount of houses and hotels they have. Their personal funds are decreased accordingly.
```Java
\\ In the MoneyCard class:
	int numHouses = player.getNumHouses();
	int numHotels = player.getNumHotels();
	int charge = numHouses * HOUSE_CHARGE + numHotels * HOTEL_CHARGE;
	player.removeFunds(charge);
```
On their turn, Player X places a house on their property
```Java
\\ In the TurnModel class:
	if(playerOne.canPlaceHouse()) playerOne.placeHouse();

\\In the Player class, canPlaceHouse() method:
	\\(color neighborhood we are checking is blue)
	int numBlueProperties = 0;
	for(OwnableSpace property: propertyList){
		if(property.neighborhood().equals(BLUE)) numBlueProperties++;
	}
	if(numBlueProperties == FULL_NEIGHBORHOOD) return true;
```
On their turn, Player X places a hotel on their property.
```Java
\\ In the TurnModel class:
	if(playerOne.canPlaceHotel()) playerOne.placeHotel();

\\In the Player class, canPlaceHotel() method:
	\\(color neighborhood we are checking is blue)
	int numFullBlueProperties = 0;
	for(OwnableSpace property: propertyList){
		if(property.numHouses() == FULL_HOUSES) numFullBlueProperties++;
	}
	if(numFullBlueProperties == FULL_NEIGHBORHOOD) return true;
```
