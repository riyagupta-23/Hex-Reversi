package cs3500.reversi.view;


import java.awt.Graphics2D;

/**
 * Interface for grid decorators in the Reversi game.
 * Grid decorators are responsible for adding additional visual elements or styles
 * to the game grid. This can include highlighting possible moves, indicating scores,
 * or any other form of decoration that enhances the game's visual presentation.
 */
public interface GridDecorator {

  /**
   * Decorates a part of the game grid.
   * Implementations of this method will add visual enhancements to the game grid,
   * such as highlighting potential moves, showing the number of possible flips, or
   * other visual cues based on the state of the game.
   *
   * @param g2d              The Graphics2D object used for drawing on the panel.
   * @param panel            The panel representing the game grid where decoration will occur.
   * @param highlightedShape The shape on the grid that is to be decorated. This could be a
   *                         disc, a cell, or any other shape part of the grid.
   * @param possibleFlips    The number of possible flips (or captures) that can be achieved
   *                         from a move.
   */
  void decorate(Graphics2D g2d, Panel panel, ShapeOfDisc highlightedShape, int possibleFlips);
}
