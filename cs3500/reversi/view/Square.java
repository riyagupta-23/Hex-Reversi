package cs3500.reversi.view;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * Represents a square shape, implementing the ShapeOfDisc interface.
 * This class is used to create and manipulate square shapes, primarily for
 * graphical representations in the Reversi game view. It extends Path2D.Double,
 * allowing it to represent a geometric path constructed from straight lines, and
 * provides functionality to calculate its center.
 */
public class Square extends Path2D.Double implements ShapeOfDisc {
  private double x;
  private double y;
  private double width;
  private double height;

  /**
   * Constructs a Square object with specified coordinates and dimensions.
   *
   * @param x      The x-coordinate of the top-left corner of the square.
   * @param y      The y-coordinate of the top-left corner of the square.
   * @param width  The width of the square.
   * @param height The height of the square.
   */
  public Square(double x, double y, double width, double height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    moveTo(x, y); // Start in the top-left corner
    lineTo(x + width, y); // Line to the top-right corner
    lineTo(x + width, y + height); // Line to the bottom-right corner
    lineTo(x, y + height); // Line to the bottom-left corner
    closePath(); // Close the path to complete the square
  }

  /**
   * Calculates and returns the center point of the square.
   * This method is useful for determining the middle of the square,
   * which can be used for various calculations and renderings.
   *
   * @return The center point of the square, represented as a Point2D object.
   */
  public Point2D getCenter() {
    double centerX = x + width / 2.0;
    double centerY = y + height / 2.0;
    return new Point2D.Double(centerX, centerY);
  }
}
