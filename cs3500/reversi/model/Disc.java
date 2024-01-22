package cs3500.reversi.model;

/**
 * Represents a singular tile in  Reversi.
 * Each disc is represented by its row (q) and column (r) in the grid.
 */
public class Disc implements CoordinateSystem {
  /**
   * The row of the tile.
   */
  private final int q;
  /**
   * The column of the tile.
   */
  private final int r;

  /**
   * creates a new Disc with the given parameters.
   *
   * @param q the row of the new Disc.
   * @param r the column of the new Disc.
   */
  public Disc(int q, int r) {
    this.q = q; // row
    this.r = r; // column
  }

  /**
   * Determines if the given object is equivalent to this Disc.
   *
   * @param obj the object to compare
   * @return true if the objects are the same or represent the same Disc; false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Disc cell = (Disc) obj;
    return q == cell.q && r == cell.r;
  }

  /**
   * Returns a hash code value for this Disc.
   *
   * @return a hash code value for this object
   */
  @Override
  public int hashCode() {
    return 31 * q + r;
  }

  @Override
  public int getQ() {
    return q;
  }

  @Override
  public int getR() {
    return r;
  }

}