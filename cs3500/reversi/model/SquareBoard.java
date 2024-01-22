package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a square board for a game, implementing the IBoard interface.
 * This class manages the game board's state including the size of the board
 * and the current color of each cell on the board.
 */
public class SquareBoard implements IBoard {
  private final int size;
  private HashMap<CoordinateSystem, CellColor> board;

  /**
   * Constructs a new SquareBoard with a specified size.
   * The size must be an even number greater than or equal to 2, as per Reversi rules.
   *
   * @param size The size of one side of the square board.
   * @throws IllegalArgumentException if the size is less than 2 or not an even number.
   */
  public SquareBoard(int size) {
    if (size < 2 || (size % 2 != 0)) {
      throw new IllegalArgumentException("Invalid board size.");
    }
    this.size = size;
    initializeBoard(); // Assumes this method sets up the board with initial configuration.
  }

  /**
   * Constructs a new SquareBoard by copying the state of another SquareBoard.
   * This can be used to create a deep copy of a board for operations like simulations.
   *
   * @param other The SquareBoard to copy from.
   * @throws IllegalArgumentException if the size of the other board is less than 2 or odd.
   */
  public SquareBoard(SquareBoard other) {
    if (other.size < 2 || (other.size % 2 != 0)) {
      throw new IllegalArgumentException("Invalid board size.");
    }
    this.size = other.size;
    initializeBoard(); // Assumes this method copies the board state from 'other'.
  }


  @Override
  public void initializeBoard() {
    this.board = new HashMap<>();

    int numRows = size;

    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numRows; j++) {
        board.put(new Disc(i, j), CellColor.EMPTY);
      }
    }

    // initialising the start state of the board
    board.put(new Disc(size / 2 - 1, size / 2 - 1), CellColor.BLACK);
    board.put(new Disc(size / 2 - 1, size / 2), CellColor.WHITE);
    board.put(new Disc(size / 2, size / 2 - 1), CellColor.WHITE);
    board.put(new Disc(size / 2, size / 2), CellColor.BLACK);
  }

  @Override
  public List<CoordinateSystem> getNeighbors(int q, int r) {
    List<CoordinateSystem> neighbors = new ArrayList<>();
    neighbors.add(new Disc(-1, -1));  // up-left
    neighbors.add(new Disc(-1, 0));  // top
    neighbors.add(new Disc(-1, 1));   // up-right
    neighbors.add(new Disc(0, 1));   // right
    neighbors.add(new Disc(1, 1));    // down-right
    neighbors.add(new Disc(1, 0));    // bottom
    neighbors.add(new Disc(1, -1));   // down-left
    neighbors.add(new Disc(0, -1));   // left

    return neighbors;
  }

  @Override
  public int getLength() {
    return this.size;
  }

  @Override
  public List<CoordinateSystem> getCorners() {
    List<CoordinateSystem> corners = new ArrayList<>();

    corners.add(new Disc(0, 0));
    corners.add(new Disc(0, this.size - 1));
    corners.add(new Disc(this.size - 1, 0));
    corners.add(new Disc(this.size - 1, this.size - 1));

    return corners;
  }

  @Override
  public CellColor getCellColor(int q, int r) {
    //System.out.println(q + "++" + r);
    if (!isValidCoordinate(q, r)) {
      throw new IllegalArgumentException("Invalid Coordinate");
    }
    CoordinateSystem hex = new Disc(q, r);
    return board.get(hex);
  }

  @Override
  public void setCell(int q, int r, CellColor color) {
    if (!isValidCoordinate(q, r)) {
      throw new IllegalArgumentException("Invalid Coordinate");
    }
    CoordinateSystem hex = new Disc(q, r);
    board.put(hex, color);
  }

  @Override
  public boolean isValidCoordinate(int q, int r) {
    if (q < 0 || q > size - 1) {
      return false;
    } else if (r < 0 || r > size - 1) {
      return false;
    }
    return true;
  }

  @Override
  public int countBlackHexes() {
    int count = 0;
    for (CellColor color : board.values()) {
      if (color == CellColor.BLACK) {
        count++;
      }
    }
    return count;
  }

  @Override
  public int countWhiteHexes() {
    int count = 0;
    for (CellColor color : board.values()) {
      if (color == CellColor.WHITE) {
        count++;
      }
    }
    return count;
  }

  @Override
  public IBoard cloneBoard() {
    SquareBoard clonedBoard = new SquareBoard(this);
    return clonedBoard;
  }

  @Override
  public HashMap<CoordinateSystem, CellColor> getBoard() {
    return new HashMap<>(board);
  }

  @Override
  public int getNumberOfColumnsInRow(int row) {
    return getLength();
  }

  @Override
  public int getRows() {
    return getLength();
  }
}
