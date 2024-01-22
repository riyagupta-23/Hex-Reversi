package cs3500.reversi.view;

import java.io.IOException;

import cs3500.reversi.controller.DummyController;
import cs3500.reversi.controller.ModelStatus;
import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.IBoard;
import cs3500.reversi.model.ReversiSquareGame;

/**
 * A textual representation of the Reversi game with a square board.
 * This class provides a way to visualize the Reversi game board in text format.
 */
public class ReversiSquareTextualView implements View {

  private final ReversiSquareGame game;
  private final Appendable ap;


  /**
   * Constructs a textual view of the provided Reversi game.
   *
   * @param game the Reversi game to be displayed
   */
  public ReversiSquareTextualView(ReversiSquareGame game) {
    this.game = game;
    this.ap = new StringBuilder();
    // Add a dummy controller
    ModelStatus dummyController = new DummyController();
    game.addModelStatusListener(dummyController); // Add the dummy controller
    game.addModelStatusListener(dummyController); // Add the dummy controller

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
    for (int i = 0; i < board.getLength(); i++) {
      for (int j = 0; j < board.getLength(); j++) {
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
            throw new IllegalArgumentException();
        }
        if (j != board.getLength() - 1) {
          result.append(" ");
        }
      }
      result.append("\n");
    }
    return result.toString();

  }
}
