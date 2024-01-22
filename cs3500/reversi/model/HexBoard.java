package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents the Hexagonal board for a game of Reversi. The board is initialized with
 * empty hex cells and four hex cells in the center with alternating colors.
 * <p>
 * Invariant: The class maintains an invariant that every hex cell on the board, if it exists,
 * will have a color from the Color enum.
 * This invariant guarantees that each hex cell is always in a valid state. There are no null
 * or undefined states for any cell on the board.
 * The initial state of each cell is set to Color.EMPTY, and during gameplay,
 * cells can only transition between defined colors (black, white, empty) as per game rules.
 * </p>
 */
public class HexBoard implements IBoard {
  private final int size;
  private HashMap<CoordinateSystem, CellColor> board;


  /**
   * Initializes a hexagonal board of given edge length.
   *
   * @param size the edge length of the hexagonal board. Must be 2 or greater.
   * @throws IllegalArgumentException if size is less than 2.
   */
  public HexBoard(int size) {
    if (size < 2) {
      throw new IllegalArgumentException("Invalid board size.");
    }
    this.size = size;
    initializeBoard();
  }

  /**
   * Initializes a hexagonal board same as another.
   *
   * @param other the other hexagonal board. Must be size 2 or greater.
   * @throws IllegalArgumentException if size of board is less than 2.
   */
  public HexBoard(HexBoard other) {
    if (other.size < 2) {
      throw new IllegalArgumentException("Invalid board size.");
    }
    this.size = other.size;
    initializeBoard();
  }

  @Override
  public void initializeBoard() {
    this.board = new HashMap<>();
    int numRows = 2 * size - 1;

    int numPieces = size;
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < numPieces; j++) {
        board.put(new Disc(i, j), CellColor.EMPTY);
      }
      numPieces++;
    }

    int numPieces2 = numRows - 1;
    for (int i = size; i < numRows; i++) {
      for (int j = 0; j < numPieces2; j++) {
        board.put(new Disc(i, j), CellColor.EMPTY);
      }
      numPieces2--;
    }

    // initialising the start state of the board
    board.put(new Disc(size - 1, size - 2), CellColor.WHITE);
    board.put(new Disc(size - 1, size), CellColor.BLACK);
    board.put(new Disc(size - 2, size - 2), CellColor.BLACK);
    board.put(new Disc(size - 2, size - 1), CellColor.WHITE);
    board.put(new Disc(size, size - 2), CellColor.BLACK);
    board.put(new Disc(size, size - 1), CellColor.WHITE);

  }


  @Override
  public List<CoordinateSystem> getNeighbors(int q, int r) {
    List<CoordinateSystem> neighbors = new ArrayList<>();

    if (q < size - 1) {
      neighbors.add(new Disc(-1, -1));  // up-left
      neighbors.add(new Disc(-1, 0));   // left
      neighbors.add(new Disc(0, 1));   // down-left
      neighbors.add(new Disc(1, 1));    // down-right
      neighbors.add(new Disc(1, 0));    // right
      neighbors.add(new Disc(0, -1));   // up-right
    } else if (q == size - 1) {
      neighbors.add(new Disc(-1, -1));   // up-left
      neighbors.add(new Disc(-1, 0));   // left
      neighbors.add(new Disc(0, 1));    // down-left
      neighbors.add(new Disc(1, 0));    // down-right
      neighbors.add(new Disc(1, -1));    // right
      neighbors.add(new Disc(0, -1));   // up-right

    } else if (q >= size) {
      neighbors.add(new Disc(-1, 0));   // up-left
      neighbors.add(new Disc(-1, 1));   // left
      neighbors.add(new Disc(0, 1));    // down-left
      neighbors.add(new Disc(1, 0));    // down-right
      neighbors.add(new Disc(1, -1));    // right
      neighbors.add(new Disc(0, -1));   // up-right
    }
    return neighbors;
  }

  @Override
  public int getLength() {
    return size;
  }

  @Override
  public List<CoordinateSystem> getCorners() {
    List<CoordinateSystem> corners = new ArrayList<>();

    int numRows = 2 * this.size - 2;

    corners.add(new Disc(0, 0));
    corners.add(new Disc(0, this.size - 1));
    corners.add(new Disc(this.size - 1, 0));
    corners.add(new Disc(this.size - 1, numRows - 2));
    corners.add(new Disc(numRows - 2, 0));
    corners.add(new Disc(numRows - 2, this.size - 1));

    return corners;
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
  public CellColor getCellColor(int q, int r) {
    if (!isValidCoordinate(q, r)) {
      throw new IllegalArgumentException("Invalid Coordinate");
    }
    CoordinateSystem hex = new Disc(q, r);
    return board.get(hex);
  }

  @Override
  public boolean isValidCoordinate(int q, int r) {
    if (q < 0 || q >= 2 * this.size - 1) {
      return false;  // q is out of range
    }

    if (q < this.size) {
      return r >= 0 && r < q + this.size;
    } else {
      return r >= 0 && r < 3 * this.size - 1 - q;
    }
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
    IBoard clonedBoard = new HexBoard(this);
    return clonedBoard;
  }

  @Override
  public HashMap<CoordinateSystem, CellColor> getBoard() {
    return new HashMap<>(board);
  }

  @Override
  public int getNumberOfColumnsInRow(int row) {
    return row < getLength() ? getLength() + row : 3 * getLength() - 2 - row;
  }

  @Override
  public int getRows() {
    return 2 * getLength() - 1;
  }
}
