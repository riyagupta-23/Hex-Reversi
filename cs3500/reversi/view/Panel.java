package cs3500.reversi.view;

//import java.awt.geom.Point2D;
import java.util.HashMap;

import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.CoordinateSystem;
//import cs3500.reversi.model.Hex;

/**
 * Interface defining the methods for a panel in the Reversi game.
 * This interface allows manipulation of the game's visual components,
 * such as setting hexagons or squares, adjusting dimensions, and highlighting cells.
 */
public interface Panel {
  /**
   * Grabs the X coordinate of the current disc.
   *
   * @param x X coordinate of a disc.
   * @param y Y coordinate of a disc.
   * @return X of the disc.
   */
  int getX(int x, int y);

  /**
   * Grabs the Y coordinate of the current disc.
   *
   * @param x X coordinate of a disc.
   * @param y Y coordinate of a disc.
   * @return Y of the disc.
   */
  int getY(int x, int y);

  /**
   * Sets the discs and their corresponding colors.
   *
   * @param discs A map of Hex objects to their CellColor.
   */
  void setDiscs(HashMap<CoordinateSystem, CellColor> discs);

  /**
   * Sets the side length of the board.
   *
   * @param boardSideLength The side length of the hexagon board.
   */
  void setBoardSideLength(int boardSideLength);

  /**
   * Sets the height of the board.
   *
   * @param height The height of the hexagon board.
   */

  void setHeight(int height);

  /**
   * Sets the width of the board.
   *
   * @param width The width of the hexagon board.
   */
  void setWidth(int width);

  /**
   * Highlights the hexagon at the specified coordinates.
   *
   * @param x The x-coordinate of the click.
   * @param y The y-coordinate of the click.
   */
  void highlight(int x, int y);

  void showInfoOrHint(int x, int y, int hint);

}
