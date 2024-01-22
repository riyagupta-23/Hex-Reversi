package cs3500.reversi;

import cs3500.reversi.controller.GameController;
import cs3500.reversi.controller.HumanPlayerController;
import cs3500.reversi.controller.MachinePlayerController;
import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.GameStartCoordinator;
import cs3500.reversi.model.IGame;
import cs3500.reversi.model.ReversiSquareGame;
import cs3500.reversi.model.StartGameListener;
import cs3500.reversi.model.strategy.AvoidNextToCorners;
import cs3500.reversi.model.strategy.CaptureCorner;
import cs3500.reversi.model.strategy.MaximumCapture;
import cs3500.reversi.model.strategy.ReversiStrategies;
import cs3500.reversi.players.HumanPlayer;
import cs3500.reversi.players.IPlayer;
import cs3500.reversi.model.ReversiHexGame;
import cs3500.reversi.players.MachinePlayer;
import cs3500.reversi.view.ReversiBasicView;
import cs3500.reversi.view.ReversiSquareView;
import cs3500.reversi.view.ReversiView;

/**
 * The main class for the Reversi game application.
 * This class sets up and starts a new game of Reversi.
 */
public final class Reversi {

  /**
   * Main method to start the Reversi game.
   * Initializes players, the game model, and the game view.
   * Sets up a Reversi game with a 6x6 board (size can be changed).
   *
   * @param args Command line arguments (not used as of now).
   */
  public static void main(String[] args) {
    String player1Type = "human"; // Default type for player 1
    String player2Type = "machine"; // Default type for player 2
    String boardType = "hex";
    String[] strategies = {"strategy1", "strategy2", "strategy3"};
    int strategyIndex = -1; // Default value indicating no strategy found

    String strategy;
    if (args.length == 3) {
      boardType = args[0];
      player1Type = args[1];
      player2Type = args[2];
    }
    if (args.length > 3) {
      boardType = args[0];
      player1Type = args[1];
      player2Type = args[2];
      strategyIndex = findStrategyIndex(strategies, args[3]);
    }


    // Initialize players
    IPlayer player1 = null;
    IPlayer player2 = null;
    if (player1Type.equals("human") && player2Type.equals("human")) {
      player1 = new HumanPlayer(CellColor.BLACK);
      player2 = new HumanPlayer(CellColor.WHITE);
    } else if (player1Type.equals("machine")) {
      ReversiStrategies s = determineStrategy(strategyIndex);
      player1 = new MachinePlayer(CellColor.BLACK, s);
      player2 = new HumanPlayer(CellColor.WHITE);
    } else if (player2Type.equals("machine")) {
      ReversiStrategies s = determineStrategy(strategyIndex);
      player1 = new HumanPlayer(CellColor.BLACK);
      player2 = new MachinePlayer(CellColor.WHITE, s);
    }

    // setting up model and view
    IGame model = null;
    ReversiView view1 = null;
    ReversiView view2 = null;

    if (boardType.equals("hex")) {
      model = new ReversiHexGame(player1, player2, 4);
      view1 = new ReversiBasicView(model);
      view2 = new ReversiBasicView(model);
    } else if (boardType.equals("square")) {
      model = new ReversiSquareGame(player1, player2, 8);
      view1 = new ReversiSquareView(model);
      view2 = new ReversiSquareView(model);
    }

    StartGameListener coordinator = new GameStartCoordinator(model);

    // setting up controller
    GameController control1;
    GameController control2;
    if (player1Type.equals("human")) {
      control1 = new HumanPlayerController(model, player1, view1, coordinator);
    } else {
      control1 = new MachinePlayerController(model, player1, view1, coordinator);
    }

    if (player2Type.equals("human")) {
      control2 = new HumanPlayerController(model, player2, view2, coordinator);
    } else {
      control2 = new MachinePlayerController(model, player2, view2, coordinator);
    }

    view1.makeVisible();
    view2.makeVisible();

  }


  // Implement the findStrategyIndex method
  private static int findStrategyIndex(String[] strategies, String strategy) {
    for (int i = 0; i < strategies.length; i++) {
      if (strategies[i].equals(strategy)) {
        return i;
      }
    }
    return -1;
  }

  private static ReversiStrategies determineStrategy(int index) {

    switch (index) {
      case 1:
        return new AvoidNextToCorners();
      case 2:
        return new CaptureCorner();
      default:
        return new MaximumCapture();
    }

  }

}