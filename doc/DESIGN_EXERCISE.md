# OOGA Lab Discussion
## Names and NetIDs
Lina Leyhausen (lsl14), Delaney Demark (dad48), Anna Diemel (ad356), Cameron Jarnot (cmj36), Grace Llewelyn (gal16)


## Fluxx

### High Level Design Ideas
The game flux can have an overall Card interface with extended classes for different types of cards with different rules. A ooga.controller class of the program can be used to determine what the rules of the game are right now (based on the cards in play). A player can play against virtual computer players or against other players. The front end of the game displays which cards the player has and allows the player to use the available cards (or draw more). Also, there will be an abstract player class so that the project can keep track of a list of the players in the project and the players can have different implementations like an interactive player, computer player and AI player. There will be an abstract card class so that a list of cards can be kept (players cards, pile of cards) and the different implementations of the cards will likely have interfaces that allow them to do things such as changing the score of the player, etc.

### CRC Card Classes

This class's purpose or value is to manage the game:
```java
public class FluxxGame {
    public FluxxGame()
    public FluxxGame(int numberOfPlayers)
    public int getTotal (Collection<Integer> data)
    public void resetScores()

}
```

This class’s purpose is to represent a player of the game: 
```java
public abstract class Player {
    public List<Card> getCards();
    public void addCardToHand(Card card);
     public void playCard(Card card);
    Public boolean hasWon(); //varies based on what the current goal is
}
```

This class’s purpose is to represent a user controlled player: 
```java
public class InteractivePlayer extends Player {
    private void getUserInput();
     private Card getPlayCard();
}
```

This class's purpose or value is to represent a high level Card:
```java
public abstract class Card {
    public void update (int data)
    public abstract playCard();
    public abstract overrideCard();
    public Card compareCards(Card oldCard, Card newCard);
}
```

This class's purpose or value is to represent and give behavior to a Basic Rules card:
```java
public class BasicRules extends Card {
    InterfaceToControls;
    public void update (int data)
    Implements everything from abstract Card class
}
```

This class's purpose or value is to represent and give behavior to a Keeper card:
```java
public class Keeper extends Card {
    public void update (int data)
Implements everything from abstract Card class
}
```

This class's purpose or value is to represent and give behavior to a Goal card:
```java
public class Goal extends Card {
    public void update (int data)
    Implements everything from abstract Card class
}
```

This class's purpose or value is to represent and give behavior to a New Rule card:
```java
public class NewRule extends Card {
    public void update (int data)
Implements everything from abstract Card class
}
```

This class's purpose is to represent the draw pile:
```java
public class DrawPile {
    public Card getNextCard()
    Public void putCardBack(Card c)
    Public void shuffle()
}
```

### Use Cases

### Use Cases

* A new game is started with five players, their scores are reset to 0.
```java
FluxxGame game = new FluxxGame(5);
game.resetScores();
```

* A player chooses a card to play.
```java
Card goalCard = new Goal();
playerOne.playCard(goalCard);
```

* Given the card in play, the rules are altered.
```java
List<Card> cardsInPlay = cardController.getCards();
newGame.updateRules(cardsInPlay);
```

* A new choice is added to an existing game and its relationship to all the other choices is updated.
```java
Card newCard = new NewCard();
cardController.addCard(newCard);
```

* A new game is added to the system, with its own relationships for all its cards.
```java
FluxxGame newGame = new FluxxGame(); 
// Create new cards that overwrite compareCard 
```



