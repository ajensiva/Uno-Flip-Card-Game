# Uno-Flip-Card
## Development Team - Team 6
- __Zarif Khan__ : _101224172_
- __Arun Karki__ : _101219923_
- __Jason Kia__ : _101233435_
- __Ajen Srisivapalan__ : _101248498_
-------------------------------------------------------------------
### Milestone 1:
- Implemented short text based UI of the classic Uno-Flip Card game
- Includes implementation of Lightside cards
- Allows anywhere from 2-4 players
- Includes Java Unit Testing of each class

### Known issues:
- Darkside is not implemented 
- UI is only available through the terminal
- The user plays as every player (does not include AI compatability)
- In the case that a user does not input an index to play a Card (if they put a String) then there is no catch for this case
--------------------------------------------------------------------
### Milestone 2:
- The code efficiently handles player turns, card plays, and special card effects, ensuring a seamless and engaging gaming experience, with the GUI initializing the game through the startGame() method and providing a "Next Player" button for user-controlled turn progression.
- User interaction is a primary focus, prompting input through various buttons like "UNO!!," "Next Player," and color selection during wild card scenarios, enhancing interactivity through mouse-driven actions for active participation in card plays and game progression.
- The code strictly enforces standard UNO game rules, validating card plays based on color or type, adding cards to a player's hand with the addCard() method, and incorporating a wild card GUI that aligns with the rule allowing players to choose a color in specific gameplay scenarios.
- The code demonstrates modularity and readability with encapsulated functionalities in methods like startMenu(), startGame(), and wildCardGui(), promoting a clear separation of concerns for maintainability, future expansion, and ease of understanding for potential updates and enhancements.

### Known issues:
- Updating player's hand once a card has been removed may cause an error to occur. The game proceeds to function properly but the issue hasn't been addressed yet. The error doesn't inherently cause the game to break.
- In some instances, some cards may not be visibly removed from the Game, but they still play, and will not appear when the turn return's to the player who played said Card.
- Reverse does not work
--------------------------------------------------------------------
### Milestone 3:
- Emphasizes the necessity for all tests to pass, ensuring accurate card placement and scoring functionalities within the game.
- This stage introduces Uno Flip cards, incorporating their specific rules and scoring mechanisms to enhance gameplay dynamics.
- A notable enhancement is the incorporation of an arbitrary number of AI players with flexible behavior strategies, allowing for intelligent gameplay simulation. Options include generating all legal moves and selecting the highest-scoring move or using rules such as "The first valid card will be placed."

### Known issues:
- Potential scaling issues with Card images across platforms  

#### MIT License

_Copyright (c) 2023 [Ajen Srisivapalan, Jason Keah, Zarif Khan, Arun Karki]_

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
