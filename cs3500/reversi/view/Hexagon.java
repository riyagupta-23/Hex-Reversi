package cs3500.reversi.view;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * Represents a hexagon shape.
 * This class extends Path2D.Double to create a hexagon based on specified dimensions
 * and position.
 */
public class Hexagon extends Path2D.Double implements ShapeOfDisc {
  private double width;
  private double height;
  private double topLeftX;
  private double topLeftY;

  /**
   * Constructs a Hexagon object.
   *
   * @param topLeftX The x-coordinate of the top-left corner of the bounding box of the hexagon.
   * @param topLeftY The y-coordinate of the top-left corner of the bounding box of the hexagon.
   * @param width    The width of the hexagon.
   * @param height   The height of the hexagon.
   */
  public Hexagon(double topLeftX, double topLeftY, double width, double height) {
    this.height = height;
    this.width = width;
    this.topLeftX = topLeftX;
    this.topLeftY = topLeftY;

    double centerX = getCenter().getX();
    double centerY = getCenter().getY();

    double bufferAngle = Math.PI / 6.0;
    double angle = Math.PI / 3.0;

    moveTo(centerX + width * Math.cos(bufferAngle + angle * 0),
            centerY + height * Math.sin(bufferAngle + angle * 0));

    for (int i = 1; i < 6; i++) {
      double x = centerX + width * Math.cos(bufferAngle + angle * i);
      double y = centerY + height * Math.sin(bufferAngle + angle * i);
      lineTo(x, y);
    }

    closePath();
  }

  /**
   * Calculates and returns the x-coordinate of the center of the hexagon.
   *
   * @return The center x-coordinate.
   */
  double getCenterX() {
    return topLeftX + width * Math.sqrt(3) / 2;
  }

  /**
   * Calculates and returns the y-coordinate of the center of the hexagon.
   *
   * @return The center y-coordinate.
   */
  double getCenterY() {
    return topLeftY + height;
  }

  /**
   * Calculates and returns the center point of the hexagon.
   *
   * @return The center point of the hexagon.
   */
  public Point2D getCenter() {
    double centerX = getCenterX() - 15;
    double centerY = getCenterY() + 5;
    return new Point2D.Double(centerX, centerY);
  }

}
