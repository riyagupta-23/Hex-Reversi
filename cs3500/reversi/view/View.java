package cs3500.reversi.view;

import java.io.IOException;

/**
 * Represents a view for the Reversi game.
 * This interface defines the necessary methods for displaying a representation of the game.
 */
interface View {
  void render() throws IOException;


  /**
   * Converts the game board to its representation as determined by the implementing class.
   * For example, a textual view might return the game board in a string format, while a graphical
   * view might
   * simply return a description of the visual representation.
   *
   * @return a string representation of the game board
   */
  String toString();
}
