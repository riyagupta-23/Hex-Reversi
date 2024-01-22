package cs3500.reversi.model.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.CoordinateSystem;
import cs3500.reversi.model.Disc;
import cs3500.reversi.model.IBoard;
import cs3500.reversi.model.MoveAction;
import cs3500.reversi.model.ReadOnlyIGame;

/**
 * Strategy implementation for avoiding moves next to the corners in a Reversi game.
 * This strategy extends from AbstractStrategy and implements ReversiStrategies interface.
 * It focuses on selecting moves that are not adjacent to any corner of the board.
 */


public class AvoidNextToCorners extends AbstractStrategy implements ReversiStrategies {

  /**
   * Chooses the best move for a player, avoiding positions next to corners.
   *
   * @param game        The current game state as ReadonlyIGame.
   * @param playerColor The color of the player making the move.
   * @return The chosen move as a Hex object, or the best move according to the MaximumCapture
   *     strategy if no suitable move is found.
   */
  public MoveAction chooseMove(ReadOnlyIGame game, CellColor playerColor) {
    IBoard board = game.cloneGame().getGrid();
    MoveAction bestMove;

    bestMove = findBestMove(board, game, playerColor);

    return bestMove;
  }

  /**
   * Finds the best move for the player while avoiding positions adjacent to any corner.
   *
   * @param board       The HexBoard representing the current state of the board.
   * @param game        The current game state as ReadonlyIGame.
   * @param playerColor The color of the player.
   * @return The best move as a Hex object, or null if no move meets the criteria.
   */
  private MoveAction findBestMove(IBoard board, ReadOnlyIGame game, CellColor playerColor) {
    int numRows = board.getRows();
    for (int q = 0; q < numRows; q++) {
      int numPieces = board.getNumberOfColumnsInRow(q);
      for (int r = 0; r < numPieces; r++) {
        if (game.isValidMove(q, r, playerColor)) {
          if (!isNextToCorner(board, q, r)) {
            MoveAction bestMove = new MoveAction(new Disc(q, r));
            return bestMove;
          }
        }
      }
    }
    ReversiStrategies maximumCapture = new MaximumCapture();
    return maximumCapture.chooseMove(game, playerColor);
  }

  /**
   * Determines if a given position is next to a corner of the board.
   *
   * @param board The HexBoard to check against.
   * @param q     The q-coordinate of the position.
   * @param r     The r-coordinate of the position.
   * @return True if the position is next to a corner, false otherwise.
   */
  private boolean isNextToCorner(IBoard board, int q, int r) {
    List<CoordinateSystem> directions = validDirections(board, q, r);

    int currentQ = q;
    int currentR = r;

    List<Disc> neighbors = new ArrayList<>();

    // get all valid neighbors
    for (CoordinateSystem direction : directions) {
      currentQ += direction.getQ();
      currentR += direction.getR();
      if (board.isValidCoordinate(currentQ, currentR)) {
        Disc neighbor = new Disc(currentQ, currentR);
        neighbors.add(neighbor);
      }
    }

    List<CoordinateSystem> corners = board.getCorners();
    // check if next to a corner
    for (CoordinateSystem neighbor : neighbors) {
      for (CoordinateSystem corner : corners) {
        if (corner.equals(neighbor)) {
          return true;
        }
      }
    }

    return false;
  }
}
