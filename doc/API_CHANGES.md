#API Changes

### API 1: ClassicGamePlay

ClassicGamePlay is the top level class for the model package, and it handles
gameplay initialization and larger hierarchical instance variables.
The API has many public methods, all of which relate to the backend of the
project. Some examples of such methods are initializeGame, moveToNextPlayer,
makeTrade, and startPlayingGame; these give an idea of the purpose and functionality
of this API.

####Notable changes

This API was originally called GamePlay, and the modification to said
name represents a larger theme of robustness and scalability in the code.

The baseline functionality of this class has been consistent throughout the
development process. The description of GamePlay in DESIGN_PLAN.md contains methods
that ClassicGamePlay still uses; that being said, ClassicGamePlay changed considerably
to handle use cases that our team did not account for.

####Key Aspects

* Change throughout sprints

Much of our changes to ClassicGamePlay involved building implementation out for 
extensions during sprints. This is in line with the idea that our API scaled as the 
project did; most of the increased functionality facilitated the transmission of information
throughout the project or feature work for lower level classes.

#### Design Considerations

* Flexibility

To keep the code flexible and extendable, GamePlay was changed to ClassicGamePlay.
This was to better encapsulate the actual functionality of ClassicGameplay, as it was
specific to the classic ruleset. This also allowed for GamePlay to be extended for other
types of GamePlay.


### API 2: Cards

Cards manages the Card decks used by the given Monopoly game. This includes drawing
cards from the deck and shuffling the deck.

#### Key Aspects

Cards was not significantly altered throughout the design process; one reason for this is that it
encapsulated a smaller section of gameplay that had less extensions than a ClassicGamePlay or
ClassicGameView. This suggests that APIs on a more granular level lead to a more stable API, even 
when scaling up the project. 

#### Design Considerations

* Cards vs Card

In terms of encapsulation, there was discussion around maintaining Cards or a list of Card objects.
While we did end up doing both of these internally in the code, we decided that Cards served better as
a top level abstract class. When playing Monopoly, the user is more concerned about Cards than a single 
Card; especially for other developers looking at the code, public access to Cards as a deck and as an
entity made more logical sense than exposing Card to developers.

### API 3: Players

A top level abstract class that manages a game's players.

#### Notable changes

This design changed considerably throughout development, as touched on in the final presentation.

* Moving money transactions from Players to Bank

In its original design, Players managed money for the game's players. On one level, this does make
sense; each individual player has their own money, so Players could encapsulate the Player by managing
said money. However, this was not so in practice. One reason we decided to move money transactions
out of Players was due to logical fitting; having Bank manage money seems reasonable, and there is no
specific reason that Players would need money amount information that it could not be passed.

Furthermore, Players does not even exist in our original DESIGN_PLAN. It was created during the final
sprint as part of our efforts to improve our interfaces and overall project design. This is important
to mention as a way of discussing API additions as a form of change. There were many APIs that we
did not initially plan for, however proved useful; these changes were almost entirely design driven
as opposed to feature driven. They were refactors that did not improve operability, but simplified the
project. This represents the relationship that our APIs have to our codebase's overall health. Maintaining
solid APIs and other top level classes made a significant impact on our code, trickling down to lower
levels.

#### Key Aspects

* Triggers communication and user prompts for gameplay

When the conceptualization of Players transitioned to triggering communication
and player movement, this drastically changed internal code interactions. Prior, there
were design problems with Turns and the TurnableModel; due to the complexity of turns in
Monopoly, these classes were oversaturated and failed to encapsulate the concept in a meaningful way.
Part of the reason for this was the turn model having too many responsibilities, such as 
triggering communication at the beginning or end of a turn. When Players took this
responsibility (although this responsibility is not evident in the API), it simplified the
codebase as a whole.
