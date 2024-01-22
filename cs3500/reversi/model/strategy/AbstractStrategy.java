package cs3500.reversi.model.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.CoordinateSystem;
import cs3500.reversi.model.Disc;
import cs3500.reversi.model.IBoard;

/**
 * Abstract base class for Reversi game strategies.
 * This class provides common utility methods that can be used by various game strategies
 * to determine valid moves, capture discs, and compute scores.
 */
public abstract class AbstractStrategy {

  /**
   * Determines which discs can be captured from a given position and direction.
   *
   * @param board       The game board.
   * @param q           The q-coordinate of the starting position.
   * @param r           The r-coordinate of the starting position.
   * @param playerColor The color of the current player.
   * @param i           The index of the direction to check for capture.
   * @return A list of Hex objects representing the discs that can be captured.
   */
  protected List<CoordinateSystem> canCapture(IBoard board, int q, int r,
                                              CellColor playerColor, int i) {
    int currentQ = q;
    int currentR = r;
    List<CoordinateSystem> captured = new ArrayList<>();

    CellColor opponentColor = (playerColor == CellColor.BLACK) ? CellColor.WHITE : CellColor.BLACK;

    List<CoordinateSystem> potentialCapture = new ArrayList<>();

    if (!board.isValidCoordinate(currentQ, currentR)) {
      return potentialCapture;
    }

    if (board.getCellColor(currentQ, currentR) == playerColor) {
      return potentialCapture;
    }

    while (board.isValidCoordinate(currentQ, currentR)
            && board.getCellColor(currentQ, currentR) == opponentColor) {
      potentialCapture.add(new Disc(q, r));
      CoordinateSystem nextDirection = getDirectionFor(board, i, currentQ, currentR);
      // Adjust direction based Q
      currentQ += nextDirection.getQ();
      currentR += nextDirection.getR();
    }

    if (board.isValidCoordinate(currentQ, currentR) && board.getCellColor(currentQ, currentR) ==
            playerColor && !potentialCapture.isEmpty()) {
      captured.addAll(potentialCapture);
    }
    return captured;
  }

  /**
   * Identifies valid neighbor directions for a disc at a given position.
   *
   * @param board The game board.
   * @param q     The q-coordinate of the disc.
   * @param r     The r-coordinate of the disc.
   * @return A list of Hex objects representing valid directions.
   */
  protected List<CoordinateSystem> validDirections(IBoard board, int q, int r) {
    List<CoordinateSystem> directions = board.getNeighbors(q, r).stream()
            .map(hex -> {
              int neighborQ = q + hex.getQ();
              int neighborR = r + hex.getR();
              if (board.isValidCoordinate(neighborQ, neighborR)) {
                return hex;
              } else {
                return new Disc(-1, -1); // use invalid placeholder
              }
            })
            .collect(Collectors.toList());
    return directions;
  }


  /**
   * Gets the direction for capturing discs based on the index.
   *
   * @param board The game board.
   * @param i     The index specifying which direction to retrieve.
   * @param dq    The q-coordinate of the current disc.
   * @param dr    The r-coordinate of the current disc.
   * @return The Hex object representing the direction.
   */
  private CoordinateSystem getDirectionFor(IBoard board, int i, int dq, int dr) {
    List<CoordinateSystem> directions = board.getNeighbors(dq, dr);
    return directions.get(i);
  }

}
