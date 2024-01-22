package cs3500.reversi.model;

import cs3500.reversi.controller.ModelStatus;
import cs3500.reversi.players.IPlayer;

/**
 * Readonly interface for a game of Reversi.
 * This interface provides methods to query the game state without modifying it,
 * such as getting the current board, checking if the game is over, and finding out the
 * current player.
 */
public interface ReadOnlyIGame {

  /**
   * Creates a clone of the current game.
   *
   * @return A new ReversiGame instance that is a copy of this game.
   */
  IGame cloneGame();

  /**
   * get the player whose turn it is.
   *
   * @return the current player.
   */
  IPlayer getCurrentPlayer();

  /**
   * determines if the game is over.
   *
   * @return whether the game is over.
   */
  boolean isGameOver();

  /**
   * determines if a player has a valid move.
   *
   * @param playerColor the Color of the player.
   * @return whether this player has a valid move.
   */
  boolean hasValidMoveForPlayer(CellColor playerColor);

  /**
   * THIS IS AN ASSUMPTION MADE. This method will be used for the controller.
   * Determines the winner of the game based on the count of BLACK and WHITE hexes on the board.
   *
   * <p>
   * The logic proceeds as follows:
   * 1. If the number of BLACK hexes is greater than the number of WHITE hexes, BLACK wins.
   * 2. If the number of WHITE hexes is greater than the number of BLACK hexes, WHITE wins.
   * 3. If the number of BLACK hexes is equal to the number of WHITE hexes, then the winner is the
   * second person who passed, thus the last player.
   * </p>
   *
   * @return The winning player
   */
  IPlayer winner();

  /**
   * Grabs the score of the current player.
   *
   * @param playerColor The current player's color.
   * @return The score of the game.
   */
  int getScore(CellColor playerColor);

  /**
   * Adds a status listener to the model.
   *
   * @param listener The item that will have the listener.
   */
  void addModelStatusListener(ModelStatus listener);

  int potentialFlips(int q, int r, CellColor playerColor);

  /**
   * Checks if the proposed move is valid based on current game state.
   *
   * @param q     Row of the cell.
   * @param r     Column of the cell.
   * @param color Player's color.
   * @return True if the move is valid, false otherwise.
   */
  boolean isValidMove(int q, int r, CellColor color);
}
