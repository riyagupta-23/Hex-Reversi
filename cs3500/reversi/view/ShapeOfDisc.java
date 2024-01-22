package cs3500.reversi.view;

import java.awt.geom.Point2D;

/**
 * Interface representing the geometric shape of a disc in the Reversi game.
 * This interface provides a method to calculate and retrieve the center point of the shape.
 * It can be implemented by various geometric shapes used in the game's graphical representation,
 * such as circles, squares, or custom shapes.
 */
public interface ShapeOfDisc {

  /**
   * Calculates and returns the center point of the shape.
   * This method is useful for determining the exact central position of a shape,
   * which can be used for accurate placement or calculations in the game's graphical view.
   *
   * @return The center point of the shape, represented as a Point2D object.
   */
  Point2D getCenter();
}
