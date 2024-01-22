package cs3500.reversi.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JPanel;

import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.CoordinateSystem;

/**
 * SquareGrid is a JPanel that represents a grid of squares for the Reversi game.
 * It handles the rendering of the game board, including squares and discs, and
 * manages the highlighting of squares and showing of hints.
 */
public class SquareGrid extends JPanel implements Panel {
  private HashMap<CoordinateSystem, CellColor> squares;
  private final HashMap<Square, CoordinateSystem> squareImages;
  private int boardSideLength;
  protected Square highlightedSquare; // Corrected spelling
  private int highlightedQ;
  private int highlightedR;
  protected int possibleFlips;
  private boolean showHints = false;
  private List<GridDecorator> decorators = new ArrayList<>();
  private int cellHeight;
  private int cellWidth;

  /**
   * Constructs a new SquareGrid with default settings.
   * Initializes the board side length and sets up a component listener to handle resizing.
   */
  public SquareGrid() {
    squares = new HashMap<CoordinateSystem, CellColor>();
    boardSideLength = 6;
    squareImages = new HashMap<Square, CoordinateSystem>();
    highlightedQ = -1;
    highlightedR = -1;
    possibleFlips = -1;

    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        updateCellSize();
      }
    });
  }

  /**
   * Adds a decorator to the grid. Decorators are used to add visual enhancements
   * like hints or highlights to the grid.
   *
   * @param decorator The GridDecorator to add to this grid.
   */
  public void addDecorator(GridDecorator decorator) {
    decorators.add(decorator);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    squareImages.clear();

    for (CoordinateSystem coord : squares.keySet()) {
      drawSquare(coord, g2d);
    }

    for (GridDecorator decorator : decorators) {
      decorator.decorate(g2d, this, highlightedSquare, possibleFlips);
    }
  }

  @Override
  public void setDiscs(HashMap<CoordinateSystem, CellColor> discs) {
    this.squares = discs;
  }

  @Override
  public int getX(int x, int y) {
    int s = 0;
    for (Square square : squareImages.keySet()) {
      if (square.contains(new Point2D.Double(x, y)) && squareImages.get(square) != null) {
        s = squareImages.get(square).getQ();
      }
    }
    return s;
  }

  @Override
  public int getY(int x, int y) {
    int s = 0;
    for (Square square : squareImages.keySet()) {
      if (square.contains(new Point2D.Double(x, y)) && squareImages.get(square) != null) {
        s = squareImages.get(square).getR();
      }
    }
    return s;
  }

  public void setBoardSideLength(int boardSideLength) {
    this.boardSideLength = boardSideLength;
  }

  @Override
  public void setHeight(int height) {
    this.cellHeight = height;
  }

  @Override
  public void setWidth(int width) {
    this.cellWidth = width;
  }

  private void drawSquare(CoordinateSystem coord, Graphics2D g2d) {
    double q = coord.getQ();
    double r = coord.getR();

    // Modify this calculation to correctly map your coordinate system to screen coordinates
    int x = (int) (q * cellWidth);
    int y = (int) (r * cellHeight);

    // Draw the square
    Square square = new Square(x, y, cellWidth, cellHeight);
    squareImages.put(square, coord);

    g2d.setColor(Color.LIGHT_GRAY);
    if (highlightedQ == q && highlightedR == r) {
      g2d.setColor(Color.CYAN);
    }
    g2d.fill(square);
    g2d.setColor(Color.BLACK);
    g2d.draw(square);
    setDiscs(squares);


    // Draw the circle with the corresponding color
    CellColor cellColor = squares.get(coord);
    int circleSize = cellWidth / 5;

    int circleX = (int) (square.getCenter().getX() - circleSize);
    int circleY = (int) (square.getCenter().getY() - circleSize);
    if (cellColor == CellColor.BLACK) {
      g2d.setColor(Color.BLACK);
      g2d.fillOval(circleX, circleY, circleSize * 2, circleSize * 2);

    } else if (cellColor == CellColor.WHITE) {
      g2d.setColor(Color.WHITE);
      g2d.fillOval(circleX, circleY, circleSize * 2, circleSize * 2);
    }
  }

  public void setShowHints(boolean showHints) {
    this.showHints = showHints;
  }

  @Override
  public void highlight(int x, int y) {
    boolean clickedOutsideBoard = true;
    for (Square square : squareImages.keySet()) {
      if (square.contains(new Point2D.Double(x, y)) && squareImages.get(square) != null) {
        int q = squareImages.get(square).getQ();
        int r = squareImages.get(square).getR();
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

  private void updateCellSize() {
    int newWidth = getWidth() / boardSideLength;
    int newHeight = getHeight() / boardSideLength;

    // Update cell dimensions
    setWidth(newWidth);
    setHeight(newHeight);

    // Repaint the panel to reflect the changes
    repaint();
  }

  @Override
  public void showInfoOrHint(int x, int y, int hint) {
    for (Square square : squareImages.keySet()) {
      if (square.contains(new Point2D.Double(x, y)) && squareImages.get(square) != null) {
        highlightedQ = squareImages.get(square).getQ();
        highlightedR = squareImages.get(square).getR();
        highlightedSquare = square;
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
    highlightedSquare = null;
    repaint();
  }

  public void updateSquares(HashMap<CoordinateSystem, CellColor> newSquares) {
    this.squares = newSquares;
    repaint(); // repaint to reflect the new state
  }
}



