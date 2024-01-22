Hexagonal Reversi Game README
Overview:
This codebase introduces a unique rendition of the classic Reversi game, now on a hexagonal board. It serves as a foundation for understanding the game mechanics of Reversi and offering the flexibility for potential extensions or integrations into other board games. Users are assumed to have a foundational knowledge of traditional Reversi rules and basic Java programming concepts.

QUICKSTART:
  @Test // tests discs are flipped correctly
  public void testGamePlay1() {
    game = new ReversiGame(player1, player2, 6);
    ReversiTextualView view = new ReversiTextualView(game);
    player1.implementMove(game, 4, 3);
    player2.implementMove(game, 4, 6);
    player1.passMove(game);
    player2.implementMove(game, 6, 3);
    player1.implementMove(game, 4,7);
    String expected = "     _ _ _ _ _ _\n" +
            "    _ _ _ _ _ _ _\n" +
            "   _ _ _ _ _ _ _ _\n" +
            "  _ _ _ _ _ _ _ _ _\n" +
            " _ _ _ X X X X X _ _\n" +
            "_ _ _ _ X _ O _ _ _ _\n" +
            " _ _ _ O O O _ _ _ _\n" +
            "  _ _ _ _ _ _ _ _ _\n" +
            "   _ _ _ _ _ _ _ _\n" +
            "    _ _ _ _ _ _ _\n" +
            "     _ _ _ _ _ _\n";
Here is an example test, that showcases the end point of these moves.

Key Components:

Model: 
The heart of the game. It encapsulates the data and the game logic. Without the model, there's no game. The model doesn't know about the view or the players; it simply ensures the game rules are followed and provides methods to query the game state.

Driver Components: - IGame: This interface is the main driver of the game's logic. When an action is taken, it's the IGame's responsibility to process it, check its validity, and update the model accordingly.

Driven Components: - HexBoard and Hex: These are passive elements. They represent the game board and its individual hexagonal tiles. Their state changes as the game progresses, but they don't initiate actions on their own.

Key Subcomponents:

Model Subcomponents:
-Enum Color: Differentiates players. It's crucial for identifying which tiles belong to which player and determining the game's outcome.

- Hex: The atomic component of the board. Each Disccan be occupied by a player's color or remain neutral. The game's strategy revolves around converting these hexes to a player's color.

- HexBoard: A collection of Hexes arranged in a hexagonal grid. It provides methods to query the state of individual hexes, the entire board, and to set the state of hexes based on game rules.

- Strategy (interface ReversiStrategies): This package has all strategies for the AI player. 

- ReversiGame (implementation of IGame): The orchestrator. It uses the board to enforce game rules, determine valid moves, and keep track of the game's state.

Player Subcomponents:
- IPlayer: This interface represents any entity that can make a move in the game. It can be a human, an AI, or any other form of player that might be added in the future.

- HumanPlayer: Implements IPlayer for human player interactions. It translates human decisions of play or pass into actions in the game.

View Subcomponents:
- View (Interface): An abstraction for any kind of game representation. It can be a textual view.

- TextualView: A specific implementation of the View interface. It translates the game state into a string-based visual representation, ideal for console-based play or debugging.

- Panel (Interface):  It can be a graphical one or even a networked view for online play.

- ReversiBasicView (Class): It features a hexagonal grid for the game board, input fields for commands, buttons for actions like quitting or executing commands, and interactive elements to enhance user interaction with the game.

- EnhancedView (Interface): It encompasses methods for making the view visible, setting callbacks for processing commands, displaying error messages, and refreshing the view to reflect the current state of the game. This interface enhances user interaction and game visualization by offering dynamic display and response capabilities.

Source Organization:

Class Reversi - contains main method 

Model Package: cs3500.reversi.model
- Enum CellColor
- Interface CoordinateSystem
- Class Hex
- Interface HexBoard
- Interface IBoard
- Class HexBoard
- Interface IGame
- Class ReversiGame
- Interface StratGameListener
- Class GameStartCoordinator
- Class MoveAction

Model/Strategy Package: cs3500.reversi.model.package (EXTRA-CREDIT)
- Abstract Class AbstractStrategy
- Interface ReversiStrategies 
- Class AvoidNextToCorners 
- Class CaptureCorner 
- Class MaximumCapture 
- Class MiniMaxStrategy

Players Package: cs3500.reversi.players
- Interface IPlayer
- Class HumanPlayer
- Class MachinePlayer

View Package:  cs3500.reversi.view
- Interface View
- Class TextualView
- Interface EnhancedView 
- Interface Panel 
- Class Hexagon 
- Class HexagonaGridPanel
- Class ReversiBasicView 
- Interface ViewStatus 


Controller Package:  cs3500.reversi.controller
- Interface GameController
- Interface PlayerAction 
- Class HumanPlayerController 
- Class MachinePlayerController
- Interface ModelStatus 





