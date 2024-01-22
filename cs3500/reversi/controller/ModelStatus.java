package cs3500.reversi.controller;

import java.util.HashMap;

import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.CoordinateSystem;
import cs3500.reversi.players.IPlayer;

/**
 * This is called when the board is changed, the controllers extend this interface when they
 * change something about the board, and communicates with the model to respond to the updated
 * board.
 */
public interface ModelStatus {

  /**
   * Called when the state of the game board changes.
   */
  void onBoardStateChanged(HashMap<CoordinateSystem, CellColor> currentState);

  /**
   * Called when the current player changes (i.e., it's now another player's turn).
   *
   * @param currentPlayer The player whose turn it is now.
   */
  void onTurnChanged(IPlayer currentPlayer);

  /**
   * Called when the game reaches a conclusion.
   *
   * @param winner The player who has won the game, or null if it's a draw.
   */
  void onGameEnded(IPlayer winner);

  /**
   * Called when the score is updated on player 1's view.
   *
   * @param newScore The current score of the game.
   */
  void onScoreUpdatedP1(int newScore);

  /**
   * Called when the score is updated on player 2's view.
   *
   * @param newScore The current score of the game.
   */
  void onScoreUpdatedP2(int newScore);

  /**
   * Called when the game is started, and shows the notification to the players that the
   * game has started.
   */
  void onGameStarted();

  /**
   * Called when a move is made, notifies the next player that it is their turn.
   *
   * @param currentPlayer Takes in the current player.
   */
  void onTurnChangeInfo(IPlayer currentPlayer);

  /**
   * Called when there are no valid moves. Shows the automatic pass and switches whose turn it is.
   */
  void onNoValidMove();
}
