---
marp: true
---

# Demo
* Main
* Show player instantiation on the board
* Show language options
* Show different themes
* Show dynamic resizing
---
                   
# Data Files
* Show properties files as they relate to each theme
    * Information for the start of the game (dice, etc.)
    * Locations for data files needed
* Show communitychest.csv
* Show new folders for each theme

---

# Tests
### So far, we have added tests for
* Backend
    * Several of the board space types (landing on each of the spaces)
    * Chance/community cards (initializing the deck, pulling a card, shuffling the deck, etc)
    * Data reading (reading in board spaces, community card messages, etc)
    * Players (adding a jail card, changing balance, etc)
* Frontend
    * Displaying money
    * Adding a player piece to the board (frontend)

---

# Sprint 2 Completion
 1. We finished much of the main game functionality, such as player pieces being on the board, and player money being displayed on the screen.
2. We still have to implement our real estate view - the process of the player buying real estate and houses.
3. We accomplished our goals in terms of adding different game themes and functionality. The game can now switch between the regular and National Parks theme, and can use some different rules if the players choose Millionaire Monopoly.
## What helped/impeded progress
 * Asking Professor Duvall for advice on the tricky parts of our game functionality helped when we were stuck, and we were able to rethink part of how the game works.
 * We are still working on separating out the data for game themes and game rules, so that took some time.
 ---
 # Significant event
 
 * A significant event we had this week was changing how we do turns. 
 * Went to Duvall's office hours
 ---
 
 # Teamwork and Communication
 ---
 ## What worked well
* Frequent communication, most notably about merge requests and before going into different areas of code (e.g. Cameron and Anna both working on RealEstate, RealEstateView)
Also keeping in loop about when we are and aren’t working on the project & busy days
 * Communicated issues we were having and worked through them together- merge conflicts, bugs
* Team takes advantage of time with Duvall and TAs; get help when we’re blocked and has been particularly helpful when weighing different design options or approaches
 ---
 ## What did not work well
* Less frequent meetings

 ## Improvements
 * Last week had a goal to update team members more, that’s been working
* Improved usage and comfortability with Git issues
---
 # Plans for next sprint
1. Implement Rulesets
2. Work on turn taking and TurnModel
    Buying property, adding real estate; make these options available to players
Need to add computer players to TurnModel
3. Add animation to move player pieces around the screen & drawing a card
4. Add more themes/stylesheets
5. Improve styling of current frontend
