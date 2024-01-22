package cs3500.reversi.model.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.CoordinateSystem;
import cs3500.reversi.model.Disc;
import cs3500.reversi.model.IBoard;
import cs3500.reversi.model.IGame;
import cs3500.reversi.model.MoveAction;
import cs3500.reversi.model.ReadOnlyIGame;

/**
 * This class implements a strategy for capturing corners in the game of Reversi.
 * It extends the abstract strategy and provides a specific implementation for choosing moves
 * based on the ability to capture corner positions on the board.
 */
public class CaptureCorner extends AbstractStrategy implements ReversiStrategies {

  /**
   * Chooses the best move for the given player with the intention of capturing a corner.
   * If multiple corner moves are available, it chooses the one that maximizes the player's score.
   * If no corner moves are available, it falls back to an alternate strategy.
   *
   * @param game        The current game state as a ReadonlyIGame.
   * @param playerColor The color of the player making the move.
   * @return The chosen move as a Hex object.
   */
  public MoveAction chooseMove(ReadOnlyIGame game, CellColor playerColor) {
    IBoard board =  game.cloneGame().getGrid();

    List<CoordinateSystem> validCornerMoves = findValidCornerMoves(board, game, playerColor);

    if (validCornerMoves.size() > 1) {

      // if multiple corners then choose that maximizes capturing
      MoveAction bestMove = null;
      int highestScore = Integer.MIN_VALUE;

      for (CoordinateSystem moveCorner : validCornerMoves) {
        IGame clonedGame = game.cloneGame();
        clonedGame.makeMove(moveCorner.getQ(), moveCorner.getR(), playerColor);
        int score = clonedGame.getScore(playerColor);

        if (score > highestScore) {
          highestScore = score;
          bestMove = new MoveAction(moveCorner);
        }
      }
      return bestMove; // This will return the hex with the highest score

    } else if (validCornerMoves.size() == 1) {
      return new MoveAction(validCornerMoves.get(0));
    } else {
      ReversiStrategies avoidNearCorner = new AvoidNextToCorners();
      return avoidNearCorner.chooseMove(game, playerColor);
    }
  }

  /**
   * Finds all valid corner moves for the given player.
   *
   * @param board       The current game board as a HexBoard.
   * @param game        The current game state as a ReadonlyIGame.
   * @param playerColor The color of the player.
   * @return A list of CoordinateSystem objects representing valid corner moves.
   */
  private List<CoordinateSystem> findValidCornerMoves(IBoard board, ReadOnlyIGame game,
                                         CellColor playerColor) {
    List<CoordinateSystem> validMoves = new ArrayList<>();

    int numRows = board.getRows();

    for (int q = 0; q < numRows; q++) {
      int numPieces = board.getNumberOfColumnsInRow(q);
      for (int r = 0; r < numPieces; r++) {
        if (game.isValidMove(q, r, playerColor) && isCorner(board, q, r)) {
          validMoves.add(new Disc(q, r));
        }
      }
    }
    return validMoves;
  }

  /**
   * Determines if the given coordinates correspond to a corner position on the board.
   *
   * @param board The HexBoard to check for corners.
   * @param q     The q-coordinate of the Hex to check.
   * @param r     The r-coordinate of the Hex to check.
   * @return True if the coordinates correspond to a corner, false otherwise.
   */
  private boolean isCorner(IBoard board, int q, int r) {
    List<CoordinateSystem> corners = board.getCorners();
    Disc potentialCorner = new Disc(q, r);

    for (CoordinateSystem corner : corners) {
      if (corner.equals(potentialCorner)) {
        return true;
      }
    }
    return false;
  }
}
