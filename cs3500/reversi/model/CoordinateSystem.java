package cs3500.reversi.model;

/**
 * The coordinate system used in this game of reversi, used to create a board and locate a
 * hexagon.
 */
public interface CoordinateSystem {

  /**
   * Returns the row (q) of the Hex.
   *
   * @return the row coordinate of the Hex
   */
  int getQ();

  /**
   * Returns the column (r) of the Hex.
   *
   * @return the column coordinate of the Hex
   */
  int getR();
}
