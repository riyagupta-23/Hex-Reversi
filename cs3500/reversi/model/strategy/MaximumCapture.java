package cs3500.reversi.model.strategy;

import java.util.List;

import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.CoordinateSystem;
import cs3500.reversi.model.Disc;
import cs3500.reversi.model.IBoard;
import cs3500.reversi.model.MoveAction;
import cs3500.reversi.model.ReadOnlyIGame;

/**
 * A strategy class for Reversi that focuses on capturing the maximum number of opponent discs.
 * This class extends AbstractStrategy and implements the ReversiStrategies interface.
 * It evaluates all valid moves and selects the one that results in the most captures.
 */
public class MaximumCapture extends AbstractStrategy implements ReversiStrategies {

  /**
   * Chooses the best move for a player based on maximizing the number of captured opponent discs.
   *
   * @param game        The current game state as ReadonlyIGame.
   * @param playerColor The color of the player making the move.
   * @return The chosen move as a Hex object.
   */
  public MoveAction chooseMove(ReadOnlyIGame game, CellColor playerColor) {
    IBoard board =  game.cloneGame().getGrid();

    MoveAction bestMove = findBestMove(board, game, playerColor);

    return bestMove;
  }

  /**
   * Finds the best move on the board that captures the maximum number of discs.
   * If multiple moves capture the same number of discs, the uppermost-leftmost move is selected.
   *
   * @param board       The HexBoard representing the current state of the board.
   * @param game        The current game state as ReadonlyIGame.
   * @param playerColor The color of the player.
   * @return The best move as a Hex object.
   */
  private MoveAction findBestMove(IBoard board, ReadOnlyIGame game, CellColor playerColor) {
    MoveAction bestMove = null;
    int maxCaptures = -1;


    int numRows = board.getRows();
    for (int q = 0; q < numRows; q++) {
      int numColumns = board.getNumberOfColumnsInRow(q);
      for (int r = 0; r < numColumns; r++) {
        if (game.isValidMove(q, r, playerColor)) {
          for (CoordinateSystem dir : validDirections(board, q, r)) {
            List<CoordinateSystem> captures = canCapture(board, q + dir.getQ(), r + dir.getR(),
                    playerColor, 0);
            int numCaptures = captures.size();
            if (numCaptures > maxCaptures || (numCaptures == maxCaptures && isUpperLeft(q, r,
                    bestMove.getMove()))) {
              maxCaptures = numCaptures;
              bestMove = new MoveAction(new Disc(q, r));
            }
          }
        }
      }
    }

    return bestMove != null ? bestMove : new MoveAction(); // Fallback for no valid moves
  }


  /**
   * Determines if a given position (q, r) is more upper-left compared to another position.
   *
   * @param q        The q-coordinate of the current position.
   * @param r        The r-coordinate of the current position.
   * @param bestMove The Hex object representing the position to compare against.
   * @return True if the current position is more upper-left, false otherwise.
   */
  private boolean isUpperLeft(int q, int r, CoordinateSystem bestMove) {
    if (bestMove == null) {
      return true; // No best move yet, so the current one is uppermost-leftmost by default
    }
    // Check if (q, r) is upper (smaller q) or if it's the same row, then check for leftmost
    // (smaller r)
    return (q < bestMove.getQ() || (q == bestMove.getQ() && r < bestMove.getR()));
  }
}
