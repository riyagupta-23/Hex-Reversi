package cs3500.reversi.view;

import java.io.IOException;

import cs3500.reversi.controller.DummyController;
import cs3500.reversi.controller.ModelStatus;
import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.IBoard;
import cs3500.reversi.model.ReversiHexGame;

/**
 * A textual representation of the Reversi game.
 * This class provides a way to visualize the Reversi game board in text format.
 */
public class ReversiHexTextualView implements View {

  /**
   * The Reversi game instance that this view will display.
   */
  private final ReversiHexGame game;
  private final Appendable ap;


  /**
   * Constructs a textual view of the provided Reversi game.
   *
   * @param game the Reversi game to be displayed
   */
  public ReversiHexTextualView(ReversiHexGame game) {
    this.game = game;
    this.ap = new StringBuilder();
    // Add a dummy controller
    ModelStatus dummyController = new DummyController();
    // add players
    game.addModelStatusListener(dummyController);
    game.addModelStatusListener(dummyController);
  }

  @Override
  public void render() throws IOException {
    String gameState = this.toString();
    ap.append(gameState);
  }

  /**
   * Converts the game board to its textual representation.
   *
   * @return a string representing the game board in text format
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    IBoard board = game.getGrid();
    int size = board.getLength();

    // For the top half
    for (int i = 0; i < size; i++) {
      appendIndent(result, size - 1 - i);
      appendRow(result, size + i, i);
      result.append("\n");
    }

    // For the lower half
    for (int i = size; i < 2 * size - 1; i++) {
      int j = 2 * size - 2 - i;
      appendIndent(result, size - 1 - j);
      appendRow(result, size + j, i);
      result.append("\n");
    }
    return result.toString();
  }

  /**
   * Appends the given number of spaces to the provided StringBuilder.
   *
   * @param result the StringBuilder to append spaces to
   * @param spaces the number of spaces to append
   */
  private void appendIndent(StringBuilder result, int spaces) {
    for (int i = 0; i < spaces; i++) {
      result.append(" ");
    }
  }

  /**
   * Appends a row of cells to the provided StringBuilder based on the game board.
   * The row will contain the textual representation of each cell's color.
   *
   * @param result     the StringBuilder to append the row to
   * @param numOfCells the number of cells in the row
   * @param i          the row index
   */
  private void appendRow(StringBuilder result, int numOfCells, int i) {
    IBoard board = game.getGrid();
    for (int j = 0; j < numOfCells; j++) {
      CellColor color = board.getCellColor(i, j);
      switch (color) {
        case EMPTY:
          result.append("-");
          break;
        case BLACK:
          result.append("X");
          break;
        case WHITE:
          result.append("O");
          break;
        default:
          throw new IllegalArgumentException(); // it will never reach here because the cell
          // can not be any other color.
          // If it is invalid, it will be caught earlier in the code.
      }
      if (j != numOfCells - 1) {
        result.append(" ");
      }
    }
  }
}
