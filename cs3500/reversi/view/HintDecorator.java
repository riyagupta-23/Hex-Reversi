package cs3500.reversi.view;

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.geom.Point2D;

/**
 * A decorator class for the Reversi grid that adds hints to the game board.
 * This class implements the GridDecorator interface and provides functionality
 * to decorate a given shape on the grid with additional visual cues, such as
 * showing the number of possible flips for a move.
 */
public class HintDecorator implements GridDecorator {

  @Override
  public void decorate(Graphics2D g2d, Panel panel, ShapeOfDisc highlightedShape,
                       int possibleFlips) {
    if (highlightedShape != null && possibleFlips != -1) {
      Point2D center = highlightedShape.getCenter();
      Font font = new Font("Arial", Font.PLAIN, 20); // Set the font size
      g2d.setFont(font);
      g2d.drawString("(" + possibleFlips + ")", (int) center.getX(), (int) center.getY());
    }
  }
}
