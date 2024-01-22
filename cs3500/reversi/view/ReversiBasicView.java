package cs3500.reversi.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.Action;
import javax.swing.JOptionPane;

import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.CoordinateSystem;
import cs3500.reversi.model.Disc;
import cs3500.reversi.model.MoveAction;
import cs3500.reversi.model.ReadOnlyIGame;
import cs3500.reversi.model.StartGameListener;
import cs3500.reversi.players.IPlayer;
import cs3500.reversi.controller.PlayerAction;

import static java.lang.Math.sqrt;

/**
 * Represents the basic view of the reversi game.
 */
public class ReversiBasicView extends JFrame implements ReversiView {
  private final JPanel buttonPanel;
  private final HexagonGridPanel hexagonGrid;
  private final List<PlayerAction> playerActionListeners = new ArrayList<>();
  Consumer<String> commandCallback;
  private JButton start;
  private int lastHighlightedRow = -1;
  private int lastHighlightedCol = -1;
  private JLabel turnLabel; // Turn indicator label
  private JLabel player1Score;
  private JLabel player2Score; // Players score
  private StartGameListener startGameListener;
  private JButton enableHintsButton;
  private JButton disableHintsButton;

  /**
   * The basic view used to represent a game of reversi.
   *
   * @param model The current game and the model.
   */
  public ReversiBasicView(ReadOnlyIGame model) {
    super();

    JScrollPane scrollPane;

    int boardSideLength = model.cloneGame().getGrid().getLength();
    int widthInSideLengths = (int) ((boardSideLength * 2 - 1) * sqrt(3));
    int heightInSideLengths = (boardSideLength * 3 - 1) / 2;
    int height = 12;
    int width = 12;
    int buffer = 0;

    this.setSize(buffer + ((widthInSideLengths) * width),
            buffer + ((heightInSideLengths) * height));

    this.setTitle("REVERSI GAME");
    this.setSize(buffer + ((widthInSideLengths) * width),
            buffer + ((heightInSideLengths) * height));


    //use a borderlayout with drawing panel in center and button panel in south
    this.setLayout(new BorderLayout());

    hexagonGrid = new HexagonGridPanel();
    hexagonGrid.setDiscs(model.cloneGame().getGrid().getBoard());
    hexagonGrid.setWidth(width);
    hexagonGrid.setHeight(height);
    hexagonGrid.setBoardSideLength(model.cloneGame().getGrid().getLength());
    hexagonGrid.setPreferredSize(new Dimension((int) ((boardSideLength * 2 - 1)
            * sqrt(3)) * width,
            (boardSideLength * 3 - 1) * height));
    scrollPane = new JScrollPane(hexagonGrid);
    this.add(scrollPane, BorderLayout.CENTER);
    hexagonGrid.setOpaque(true);
    hexagonGrid.setBackground(Color.DARK_GRAY);

    // adding hint decorator
    HintDecorator hintDecorator = new HintDecorator();
    hexagonGrid.addDecorator(hintDecorator);

    //button panel
    buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.setBackground(Color.cyan);

    startGame();
    // Initialize the turn label
    turnLabel();
    // Initialize the player scores label
    player1Score();
    // Initialize the player scores label
    player2Score();
    // adding a play and pass button
    info();


    // key binding for playMove
    addKeyBinding(this.getRootPane(), KeyStroke.getKeyStroke("R"),
            "playerMoveAction", new AbstractAction() {
              @Override
              public void actionPerformed(ActionEvent evt) {
                if (lastHighlightedRow != -1 && lastHighlightedCol != -1) {
                  onPlayerMove(lastHighlightedRow, lastHighlightedCol);
                  refresh();
                }
              }
            });

    // key binding for passMove
    addKeyBinding(this.getRootPane(), KeyStroke.getKeyStroke("N"),
            "playerPassAction", new AbstractAction() {
              @Override
              public void actionPerformed(ActionEvent evt) {
                onPassTurn();
                refresh();
              }
            });


    //listener
    mouseListener(model);
    this.pack();
  }

  private void initHintButtons() {
    enableHintsButton = new JButton("Enable Hints");
    disableHintsButton = new JButton("Disable Hints");
    disableHintsButton.setEnabled(false); // Initially, the disable button is not enabled

    enableHintsButton.addActionListener(e -> {
      hexagonGrid.setShowHints(true); // Enable showing hints
      this.refresh();
      enableHintsButton.setEnabled(false);
      disableHintsButton.setEnabled(true);
    });

    disableHintsButton.addActionListener(e -> {
      hexagonGrid.setShowHints(false); // Disable showing hints
      this.refresh();
      disableHintsButton.setEnabled(false);
      enableHintsButton.setEnabled(true);
    });

    buttonPanel.add(enableHintsButton);
    buttonPanel.add(disableHintsButton);
  }


  /**
   * Adds a listener to listen for actions from the players.
   *
   * @param listener The player action.
   */
  public void addPlayerActionListener(PlayerAction listener) {
    playerActionListeners.add(listener);
  }

  /**
   * Adds a listener to see when the game starts.
   *
   * @param listener The listener waiting until the game starts.
   */
  public void setStartGameListener(StartGameListener listener) {
    this.startGameListener = listener;
  }

  /**
   * Starts the game of reversi and populates the board and assigns players.
   */
  public void startGame() {
    start = new JButton("Press to Start the Game");
    buttonPanel.add(start);
    start.addActionListener(e -> {
      if (startGameListener != null) {
        startGameListener.onPlayerReady();
      }
      start.setEnabled(false); // Disable the button to prevent re-pressing
    });
    // Initialize and add hint buttons
    initHintButtons();
    this.add(buttonPanel, BorderLayout.SOUTH);
  }

  private void turnLabel() {
    turnLabel = new JLabel("Turn: " + "BLACK" + " / ");
    turnLabel.setFont(new Font("Arial", Font.BOLD, 14));
    buttonPanel.add(turnLabel);
  }

  private void addKeyBinding(JComponent component, KeyStroke keyStroke,
                             String actionMapKey, Action action) {
    component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, actionMapKey);
    component.getActionMap().put(actionMapKey, action);
  }

  private void player1Score() {
    player1Score = new JLabel("Score: " + 3 + " / ");
    player1Score.setFont(new Font("Arial", Font.BOLD, 14));
    buttonPanel.add(player1Score);
  }


  private void player2Score() {
    player2Score = new JLabel("Score: " + 3);
    player2Score.setFont(new Font("Arial", Font.BOLD, 14));
    player2Score.setForeground(Color.WHITE);
    player2Score.setBackground(Color.BLACK);
    buttonPanel.add(player2Score);
  }


  private void info() {
    JLabel info;
    info = new JLabel("Play : Press R (OR) Pass : Press N   ");
    info.setFont(new Font("Arial", Font.ITALIC, 14));
    info.setForeground(Color.RED);
    buttonPanel.add(info);
  }


  private void mouseListener(ReadOnlyIGame model) {
    //listener
    hexagonGrid.addMouseListener(new MouseListener() {
      // Mouse listener to handle player moves
      @Override
      public void mouseClicked(MouseEvent e) {
        //Mouse clicked listener.
      }

      @Override
      public void mousePressed(MouseEvent e) {
        lastHighlightedRow = hexagonGrid.getX(e.getX(), e.getY());
        lastHighlightedCol = hexagonGrid.getY(e.getX(), e.getY());
        hexagonGrid.highlight(e.getX(), e.getY());
        int hint = model.potentialFlips(hexagonGrid.getX(e.getX(), e.getY()),
                hexagonGrid.getY(e.getX(), e.getY()), model.getCurrentPlayer().getColor());
        hexagonGrid.showInfoOrHint(e.getX(), e.getY(), hint);
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        //Mouse released listener.
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        //Mouse entered listener.
      }

      @Override
      public void mouseExited(MouseEvent e) {
        //Mouse exited listener.
      }
    });
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  // Method to trigger an action when the player plays a move in the GUI
  private void onPlayerMove(int row, int col) {
    for (PlayerAction listener : playerActionListeners) {
      MoveAction move = new MoveAction(new Disc(row, col));
      listener.attemptPlay(move);
    }
    refresh();
  }

  // Method to trigger an action when the player passes turn in the GUI

  private void onPassTurn() {
    for (PlayerAction listener : playerActionListeners) {
      listener.attemptPassTurn();
    }
    refresh();
  }

  @Override
  public void setCommandCallback(Consumer<String> callback) {
    commandCallback = callback;
  }

  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void refresh() {
    hexagonGrid.repaint();
  }

  @Override
  public void updateStartScreen() {
    JOptionPane.showMessageDialog(this, "It is your turn: PLAYER BLACK");
  }

  @Override
  public void updateTurnIndicator(IPlayer currentPlayer) {
    JOptionPane.showMessageDialog(this, "It is your turn: PLAYER "
            + (currentPlayer.getColor() != CellColor.BLACK ? "WHITE" : "BLACK"));
  }

  @Override
  public void updateTurnInfo(IPlayer currentPlayer) {
    turnLabel.setText("Turn: " + (currentPlayer != null ? currentPlayer.getColor()
            + " / " : "None"));
  }

  @Override
  public void displayEndGame(IPlayer winner) {
    String message = (winner == null) ? "Game is a draw!" : "Winner is: Player  "
            + winner.getColor();
    JOptionPane.showMessageDialog(this, message,
            "Game Over", JOptionPane.INFORMATION_MESSAGE);
    System.exit(1);
  }

  @Override
  public void displayScoreP1(int newScore) {
    player1Score.setText("Score: " + newScore + " / ");
  }

  @Override
  public void displayScoreP2(int newScore) {
    player2Score.setText("Score: " + newScore);
  }

  @Override
  public void updateState(HashMap<CoordinateSystem, CellColor> updatedState) {
    hexagonGrid.updateHexagons(updatedState);
  }

  @Override
  public void showAutoPass() {
    String message = "No valid move avaliable. Your turn has been passed.";
    JOptionPane.showMessageDialog(this, message,
            "Auto-Pass", JOptionPane.INFORMATION_MESSAGE);
  }


}

