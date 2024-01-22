package cs3500.reversi.model;

import java.util.HashMap;
import java.util.List;

/**
 * This interface represents an instance of a board to play a board game with. It represents the
 * functionality of the game board.
 */
public interface IBoard {

  /**
   * Helps create a Hexagonal board. Sets the length of the board to 2 * size -1 and puts new
   * empty hexes on the entire board. Also puts alternating black and white pieces in the middle
   * of the board. This method ensures that all the existing cells are set to EMPTY color initially.
   */
  void initializeBoard();

  /**
   * gets the neighbors of the Hex aat q,r. The pattern for rows in the upper-half of the hexagon,
   * midde-row, and then lower-half is observed and used.
   *
   * @param q the row of the Hex to find neighbors for
   * @param r the columb of the Hex to find neighbors for
   * @return a List of hexes containing all the specified hexes' neighbors.
   */
  List<CoordinateSystem> getNeighbors(int q, int r);

  /**
   * gets the Length of the board.
   *
   * @return the Length of the board
   */
  int getLength();

  /**
   * Grabs the current corners of the board.
   *
   * @return The corner Hexagons of the board.
   */
  List<CoordinateSystem> getCorners();

  /**
   * gets the current Cell color of a cell specified by the parameters.
   *
   * @param q the row of the cell.
   * @param r the column of the cell.
   * @return the color of the cell.
   */
  CellColor getCellColor(int q, int r);

  /**
   * sets the Cell color of a cell.
   *
   * @param q     the row of the cell.
   * @param r     the column of the cell
   * @param color the color to set the cell.
   */
  void setCell(int q, int r, CellColor color);

  /**
   * Checks if the given coordinate is valid.
   *
   * @param q the row of the coordinate.
   * @param r the column of the coordinate.
   * @return if this coordinate is valid.
   */
  boolean isValidCoordinate(int q, int r);

  /**
   * Counts the number of hexes on the board that are colored BLACK.
   *
   * @return the number of BLACK colored hexes.
   */
  int countBlackHexes();

  /**
   * Counts the number of hexes on the board that are colored WHITE.
   *
   * @return the number of WHITE colored hexes.
   */
  int countWhiteHexes();

  /**
   * Clones the current board of the game along with any mutations.
   *
   * @return Returns the newly copied board.
   */
  IBoard cloneBoard();

  /**
   * Gives the board that is being played on.
   *
   * @return The baord of the current player.
   */
  HashMap<CoordinateSystem, CellColor> getBoard();

  /**
   * Gets the number of columns in a given row.
   *
   * @param row the row for which the column count is needed.
   * @return the number of columns in the specified row.
   */
  int getNumberOfColumnsInRow(int row);

  /**
   * Gets the total number of rows in the board.
   *
   * @return the total number of rows.
   */
  int getRows();
}

