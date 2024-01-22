package cs3500.reversi.model;


/**
 * This is the Rule keeper of the reversi game, making sure that no player can make a move that
 * is not legal, or making sure they cannot move unless it is their turn. It takes in a board
 * and makes sure that the Reversi game is legal.
 */
public interface IGame extends ReadOnlyIGame {

  /**
   * Starts the Reversi game. This method initializes the game state and prepares the game for
   * player moves.
   */
  void startGame();

  /**
   * Makes the move in the game at the given coordinatees. Throws an exception if the move is
   * not valid.
   *
   * @param q     the row of the move.
   * @param r     the column of the move.
   * @param color the color of the move.
   */
  void makeMove(int q, int r, CellColor color);

  /**
   * implements a pass move done by the players.
   *
   * @param color color of the player passing.
   */
  void implementPass(CellColor color);

  /**
   * Returns the board as the current grid.
   *
   * @return The Board.
   */
  IBoard getGrid();
}
