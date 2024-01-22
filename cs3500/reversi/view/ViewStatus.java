package cs3500.reversi.view;

import java.io.IOException;
import java.util.HashMap;

import cs3500.reversi.controller.PlayerAction;
import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.CoordinateSystem;
import cs3500.reversi.model.StartGameListener;
import cs3500.reversi.players.IPlayer;

/**
 * Interface detailing what to add when the view needs to notify the user of something happening
 * or changing. These methods communicate with the model to listen for events and tell the user
 * when these events happen.
 */
public interface ViewStatus {

  /**
   * Adds a listener to listen for actions from the players.
   *
   * @param listener The player action.
   */
  void addPlayerActionListener(PlayerAction listener);

  /**
   * Adds a listener to see when the game starts.
   *
   * @param listener The listener waiting until the game starts.
   */
  void setStartGameListener(StartGameListener listener);


  /**
   * Updates the start screen and says whose turn it is.
   */
  void updateStartScreen() throws IOException;

  /**
   * Method to update the turn indicator.
   *
   * @param currentPlayer Current player.
   */
  void updateTurnIndicator(IPlayer currentPlayer);

  /**
   * Updates the turn information on the board.
   *
   * @param currentPlayer Current player.
   */
  void updateTurnInfo(IPlayer currentPlayer);

  /**
   * Method to display end game message.
   *
   * @param winner The player who won.
   */
  void displayEndGame(IPlayer winner);

  /**
   * Displays the score of player 1.
   *
   * @param newScore The new score of player 1.
   */
  void displayScoreP1(int newScore);

  /**
   * Displays the score of player 2.
   *
   * @param newScore The new score of player 2.
   */
  void displayScoreP2(int newScore);

  /**
   * Updates the state of the hexagon in the view and on the board.
   *
   * @param updatedState The model to update.
   */
  void updateState(HashMap<CoordinateSystem, CellColor> updatedState);

  /**
   * Makes sure to tell the player that their turn was automatically passed.
   */
  void showAutoPass();
}
