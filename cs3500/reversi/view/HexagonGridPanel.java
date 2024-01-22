package cs3500.reversi.view;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.JPanel;

import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.CoordinateSystem;

import static java.lang.Math.sqrt;

/**
 * Represents a grid panel of hexagons for the game Reversi.
 * This panel is responsible for rendering the hexagonal cells of the board,
 * handling resizing, and managing cell highlights.
 */
public class HexagonGridPanel extends JPanel implements Panel {
  private HashMap<CoordinateSystem, CellColor> hexagons;
  private final HashMap<Hexagon, CoordinateSystem> hexagonImages;
  private int boardSideLength;
  private int height;
  private int width;
  protected Hexagon highlightedHexagon;
  private int highlightedQ;
  private int highlightedR;
  protected int possibleFlips;
  private boolean showHints = false;
  private List<GridDecorator> decorators = new ArrayList<>();


  /**
   * Constructs a new HexagonGridPanel.
   * Initializes the mappings for hexagons and their corresponding images.
   * Sets up a component listener for resizing events.
   */
  public HexagonGridPanel() {
    hexagons = new HashMap<CoordinateSystem, CellColor>();
    boardSideLength = 3;
    hexagonImages = new HashMap<Hexagon, CoordinateSystem>();
    highlightedQ = -1;
    highlightedR = -1;
    possibleFlips = -1;

    // Add a component listener to handle resize events
    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        updateIndHexSide();
      }
    });
  }

  public void addDecorator(GridDecorator decorator) {
    decorators.add(decorator);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    hexagonImages.clear();

    for (CoordinateSystem hex : hexagons.keySet()) {
      drawHexagon(hex, g2d);
    }

    Font font = new Font("Arial", Font.PLAIN, 18);
    g2d.setFont(font);
    // If a hexagon is selected, draw its coordinates or give hints
    for (GridDecorator decorator : decorators) {
      decorator.decorate(g2d, this, highlightedHexagon, possibleFlips);
    }

    if (highlightedHexagon != null && possibleFlips == -1) {
      Point2D center = highlightedHexagon.getCenter();
      g2d.drawString("(" + highlightedQ + ", " + highlightedR + ")",
              (int) center.getX(), (int) center.getY());
    }
  }

  @Override
  public void setDiscs(HashMap<CoordinateSystem, CellColor> discs) {
    this.hexagons = discs;

  }

  @Override
  public void setBoardSideLength(int boardSideLength) {
    this.boardSideLength = boardSideLength;
  }

  @Override
  public void setHeight(int height) {
    this.height = height;
  }

  @Override
  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * Draws a single hexagon on the board.
   *
   * @param hex The Hex object representing the hexagon's logical position.
   * @param g2d The Graphics2D object for drawing.
   */
  private void drawHexagon(CoordinateSystem hex, Graphics2D g2d) {
    double q = hex.getQ();
    double r = hex.getR();
    double x = Math.abs(boardSideLength - q - 1) * Math.sqrt(3) / 2 * width + r * Math.sqrt(3)
            * width;
    double y = q * 3 / 2 * height;


    // Draw the hexagon
    Hexagon hexagon = new Hexagon(x, y, width, height);
    hexagonImages.put(hexagon, hex);
    g2d.setColor(Color.LIGHT_GRAY);
    if (highlightedQ == q && highlightedR == r) {
      g2d.setColor(Color.CYAN);
    }
    g2d.fill(hexagon);
    g2d.setColor(Color.BLACK);
    g2d.draw(hexagon);
    CellColor hexInfo = hexagons.get(hex);

    updateHexagons(hexagons);


    // Draw the circle with the corresponding color
    double circleRadius = width * 0.5;
    int circleX = (int) (hexagon.getCenterX() - circleRadius);
    int circleY = (int) (hexagon.getCenterY() - circleRadius);

    if (hexInfo.equals(CellColor.BLACK)) {
      g2d.setColor(Color.BLACK);
      g2d.fillOval(circleX, circleY, (int) (circleRadius * 2), (int) (circleRadius * 2));
    } else if (hexInfo.equals(CellColor.WHITE)) {
      g2d.setColor(Color.WHITE);

      g2d.fillOval(circleX, circleY, (int) (circleRadius * 1.7), (int) (circleRadius * 1.7));
    }
    repaint();

  }

  public void setShowHints(boolean showHints) {
    this.showHints = showHints;
  }

  @Override
  public void showInfoOrHint(int x, int y, int hint) {
    for (Hexagon hexagon : hexagonImages.keySet()) {
      if (hexagon.contains(new Point2D.Double(x, y)) && hexagonImages.get(hexagon) != null) {
        highlightedHexagon = hexagon;
        highlightedQ = hexagonImages.get(hexagon).getQ();
        highlightedR = hexagonImages.get(hexagon).getR();
        if (showHints) {
          possibleFlips = hint;
        } else {
          possibleFlips = -1; // or any other default value
        }
        repaint(); // Trigger a repaint to update the view
        return;
      }
    }
    // Deselect if no hexagon is found
    highlightedHexagon = null;
    repaint();
  }


  @Override
  public void highlight(int x, int y) {
    boolean clickedOutsideBoard = true;
    for (Hexagon hexagon : hexagonImages.keySet()) {
      if (hexagon.contains(new Point2D.Double(x, y)) && hexagonImages.get(hexagon) != null) {
        int q = hexagonImages.get(hexagon).getQ();
        int r = hexagonImages.get(hexagon).getR();
        if (highlightedQ != q || highlightedR != r) {
          // New cell clicked, update highlight and print coordinates
          highlightedQ = q;
          highlightedR = r;
        } else {
          // Same cell clicked, remove highlight
          highlightedQ = -1;
          highlightedR = -1;
        }
        clickedOutsideBoard = false;
        break; // Exit the loop once the clicked cell is found
      }
    }

    if (clickedOutsideBoard) {
      highlightedQ = -1;
      highlightedR = -1;
    }
    repaint();
  }

  @Override
  public int getX(int x, int y) {
    int s = 0;
    for (Hexagon hexagon : hexagonImages.keySet()) {
      if (hexagon.contains(new Point2D.Double(x, y)) && hexagonImages.get(hexagon) != null) {
        s = hexagonImages.get(hexagon).getQ();
      }
    }
    return s;
  }

  @Override
  public int getY(int x, int y) {
    int s = 0;
    for (Hexagon hexagon : hexagonImages.keySet()) {
      if (hexagon.contains(new Point2D.Double(x, y)) && hexagonImages.get(hexagon) != null) {
        s = hexagonImages.get(hexagon).getR();
      }
    }
    return s;
  }

  /**
   * Updates the size of individual hexagons based on the panel's size.
   * This method is typically called when the panel is resized.
   */
  private void updateIndHexSide() {
    int newWidth;
    int newHeight;
    newWidth = getWidth() / (int) ((boardSideLength * 2 - 1) * sqrt(3));

    newHeight = getHeight() / (boardSideLength * 3 - 1);

    // Update the indHexSide
    setWidth(newWidth);
    setHeight(newHeight);

    // Repaint the panel to reflect the changes
    repaint();
  }

  public void updateHexagons(HashMap<CoordinateSystem, CellColor> newHexagons) {
    this.hexagons = newHexagons;
    repaint(); // repaint to reflect the new state
  }

}