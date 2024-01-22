package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cs3500.reversi.controller.ModelStatus;
import cs3500.reversi.players.IPlayer;

/**
 * Abstract class representing a basic structure of a Reversi game.
 * This class implements the IGame interface and provides foundational elements
 * and functionalities common to different versions of the Reversi game.
 */
public abstract class AbstractReversiGame implements IGame {

  protected final IPlayer[] players = new IPlayer[2];
  protected final List<ModelStatus> modelStatusListeners = new ArrayList<>();
  protected IBoard board;
  protected int currentPlayerIndex = 0;
  protected CellColor lastPlayedColor;
  protected int numOfPasses;
  protected boolean isGameStarted = false;

  /**
   * Constructs an AbstractReversiGame with two players and a specified board size.
   * Initializes the players, sets the number of passes to 0, and sets the last played color
   * to white (as the first player will be black).
   *
   * @param player1   The first player of the game.
   * @param player2   The second player of the game.
   * @param boardSize The size of the game board.
   */
  public AbstractReversiGame(IPlayer player1, IPlayer player2, int boardSize) {
    this.players[0] = player1;
    this.players[1] = player2;
    this.numOfPasses = 0;  // It's initialized to 0

    // initialising to white because the first player needs to be Color.BLACK
    this.lastPlayedColor = CellColor.WHITE;
  }

  @Override
  public void addModelStatusListener(ModelStatus listener) {
    if (!isGameStarted) {
      modelStatusListeners.add(listener);
    } else {
      throw new IllegalStateException("Cannot add new players after the game has started");
    }
  }

  /**
   * Starts the game of reversi.
   * Checks if the listeners have been added to the players and checks if a game has already been
   * started, then notifies the player that the game has started.
   */
  public void startGame() {
    if (modelStatusListeners.isEmpty()) {
      throw new IllegalStateException("Cannot start game without players");
    }
    // Flag the game as started
    isGameStarted = true;
    notifyFirstPlayer();
    notifyBoardStateChanged();
  }

  /**
   * Checks to see if the game has been started.
   */
  private void checkIfGameStarted() {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not been started yet!");
    }
  }

  @Override
  public IGame cloneGame() {
    ReversiHexGame clonedGame =
            new ReversiHexGame(this.players[0], this.players[1],
                    this.board.getLength());
    clonedGame.board = this.board.cloneBoard(); // Assuming IBoard has a cloneBoard() method
    clonedGame.currentPlayerIndex = this.currentPlayerIndex;
    clonedGame.lastPlayedColor = this.lastPlayedColor;
    clonedGame.numOfPasses = this.numOfPasses;
    return clonedGame;
  }

  @Override
  public IBoard getGrid() {
    return board;
  }

  @Override
  public IPlayer getCurrentPlayer() {
    return players[currentPlayerIndex];
  }

  /**
   * Attempts to place a disc on the board and capture opponent discs.
   *
   * @param q     Row of the cell.
   * @param r     Column of the cell.
   * @param color Color of the current player's disc.
   */
  @Override
  public void makeMove(int q, int r, CellColor color) {
    checkIfGameStarted();
    notifyBoardStateChanged();
    ifGameOver();
    validateMove(q, r, color);

    // Check if the move can capture any opponent discs
    List<CoordinateSystem> captured = canCapture(q, r, color);
    if (captured.isEmpty()) {
      throw new IllegalArgumentException("Move cannot capture any discs.");
    }

    // Apply the move and flip the captured discs
    board.setCell(q, r, color);
    notifyBoardStateChanged();
    numOfPasses = 0;  // Reset on a successful move
    lastPlayedColor = color;
    flipDiscs(board, captured, color);
    notifyBoardStateChanged();
    swapPlayers();
    ifGameOver();
    notifyScoreUpdated();

  }

  /**
   * Validates that the provided move coordinates are within board boundaries.
   *
   * @param q Row of the move.
   * @param r Column of the move.
   */
  protected void validateMove(int q, int r, CellColor color) {
    if (color == null) {
      throw new IllegalArgumentException("Invalid color");
    }

    if (q < 0 || q >= 2 * board.getLength() - 1) {
      throw new IllegalArgumentException("Invalid coordinate");  // q is out of range
    }

    if (q < board.getLength()) {
      if (r < 0 && r >= q + board.getLength()) {
        throw new IllegalArgumentException("Invalid coordinate");
      }
    } else {
      if (r < 0 && r >= 3 * board.getLength() - 1 - q) {
        throw new IllegalArgumentException("Invalid coordinate");
      }
    }
  }

  @Override
  public void implementPass(CellColor color) {
    if (isGameOver()) {
      notifyGameEnded();
    } else {
      checkIfGameStarted();
      ifGameOver();
      numOfPasses++;
      swapPlayers();
      lastPlayedColor = color;
    }
  }


  /**
   * Passes if there is no valid move.
   *
   * @param color color of the player who may be autopassed.
   */
  private void autoPassIfNoValidMove(CellColor color) {
    ifGameOver();
    if (!hasValidMoveForPlayer(color)) {
      notifyPlayerNoValidMove();
    }
  }

  /**
   * Swaps to the next player.
   */
  private void swapPlayers() {
    ifGameOver();
    currentPlayerIndex = 1 - currentPlayerIndex;
    changeTurnInfo();
    notifyBoardStateChanged();
    notifyTurnChanged();
    autoPassIfNoValidMove(getCurrentPlayer().getColor());
  }

  @Override
  public boolean isGameOver() {
    return numOfPasses >= 2;
  }

  private void ifGameOver() {
    if (isGameOver()) {
      notifyGameEnded();
    }
  }

  @Override
  public IPlayer winner() {
    checkIfGameStarted();
    if (board.countBlackHexes() > board.countWhiteHexes()) {
      if (getCurrentPlayer().getColor() == CellColor.BLACK) {
        return getCurrentPlayer();
      } else {
        return players[currentPlayerIndex - 1];
      }
    } else if (board.countBlackHexes() < board.countWhiteHexes()) {
      if (getCurrentPlayer().getColor() == CellColor.WHITE) {
        return getCurrentPlayer();
      } else {
        return players[currentPlayerIndex - 1];
      }
    } else {
      return players[currentPlayerIndex];
    }
  }


  @Override
  public boolean hasValidMoveForPlayer(CellColor playerColor) {
    int numRows = 2 * board.getLength() - 1;
    for (int q = 0; q < numRows; q++) {
      int numPieces = q < board.getLength() ? board.getLength() + q : numRows - q;
      for (int r = 0; r < numPieces; r++) {
        if (isValidMove(q, r, playerColor)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean isValidMove(int q, int r, CellColor color) {
    return board.getCellColor(q, r) == CellColor.EMPTY && !canCapture(q, r, color).isEmpty();
  }

  /**
   * Checks if the current player can pass their turn.
   *
   * @param color Current player's color.
   * @return True if they can pass, false otherwise.
   */
  public boolean isValidPass(CellColor color) {
    if (color == null) {
      throw new IllegalArgumentException("Invalid color");
    }
    return lastPlayedColor != color;
  }


  /**
   * Determines the opponent discs that would be captured if the proposed move is played.
   *
   * @param q           Row of the cell.
   * @param r           Column of the cell.
   * @param playerColor Player's color.
   * @return List of Hex coordinates of the captured discs.
   */
  protected List<CoordinateSystem> canCapture(int q, int r, CellColor playerColor) {
    List<CoordinateSystem> captured = new ArrayList<>();
    if (board.getCellColor(q, r) != CellColor.EMPTY) {
      return captured;  // cell is not empty, so it's not a valid move
    }

    CellColor opponentColor = (playerColor == CellColor.BLACK) ? CellColor.WHITE : CellColor.BLACK;

    List<CoordinateSystem> directions = board.getNeighbors(q, r).stream()
            .map(hex -> {
              int neighborQ = q + hex.getQ();
              int neighborR = r + hex.getR();
              if (board.isValidCoordinate(neighborQ, neighborR)) {
                return hex;
              } else {
                return new Disc(-1, -1); // use invalid placeholder
              }
            })
            .collect(Collectors.toList());


    for (CoordinateSystem dir : directions) {
      int index = directions.indexOf(dir);
      int currentQ = q + dir.getQ();
      int currentR = r + dir.getR();
      List<Disc> potentialCapture = new ArrayList<>();

      if (!board.isValidCoordinate(currentQ, currentR)) {
        continue;
      }

      if (board.getCellColor(currentQ, currentR) == playerColor) {
        continue;
      }

      while (board.isValidCoordinate(currentQ, currentR)
              && board.getCellColor(currentQ, currentR) == opponentColor) {
        potentialCapture.add(new Disc(currentQ, currentR));
        CoordinateSystem nextDirection = getDirectionFor(index, currentQ, currentR);
        // Adjust direction based Q
        currentQ += nextDirection.getQ();
        currentR += nextDirection.getR();
      }

      if (board.isValidCoordinate(currentQ, currentR) && board.getCellColor(currentQ, currentR) ==
              playerColor && !potentialCapture.isEmpty()) {
        captured.addAll(potentialCapture);
      }
    }
    return captured;
  }

  @Override
  public int potentialFlips(int q, int r, CellColor playerColor) {
    return canCapture(q, r, playerColor).size();
  }

  /**
   * Retrieves the direction at the given index for a specific board position.
   * This method fetches the neighboring directions of a hexagonal cell on the board and
   * returns the direction corresponding to the provided index.
   *
   * @param i  Index of the desired direction.
   * @param dq Row of the cell in question.
   * @param dr Column of the cell in question.
   * @return The direction Hex at the specified index.
   */
  protected CoordinateSystem getDirectionFor(int i, int dq, int dr) {
    List<CoordinateSystem> directions = board.getNeighbors(dq, dr);
    return directions.get(i);
  }

  /**
   * Flips the discs at the specified hexagonal cells on the board to the provided color.
   * The method iterates over the list of Hex positions, updating each position to the
   * specified player color.
   *
   * @param board       The game board on which the discs are flipped.
   * @param list        List of Hex positions where the discs need to be flipped.
   * @param playerColor The color to which the discs should be changed.
   */
  private void flipDiscs(IBoard board, List<CoordinateSystem> list, CellColor playerColor) {
    for (CoordinateSystem cell : list) {
      board.setCell(cell.getQ(), cell.getR(), playerColor);
    }
    notifyBoardStateChanged();
  }


  @Override
  public int getScore(CellColor playerColor) {
    if (playerColor == CellColor.BLACK) {
      return board.countBlackHexes();
    } else {
      return board.countWhiteHexes();
    }
  }

  /**
   * Notifies all registered listeners about a change in the board state.
   */
  private void notifyBoardStateChanged() {
    for (ModelStatus listener : modelStatusListeners) {
      listener.onBoardStateChanged(board.getBoard());
    }
  }


  /**
   * Notifies all registered listeners about a score update.
   */
  private void notifyScoreUpdated() {
    for (ModelStatus listener : modelStatusListeners) {
      int newScoreP1 = getScore(CellColor.BLACK);
      listener.onScoreUpdatedP1(newScoreP1);
      int newScoreP2 = getScore(CellColor.WHITE);
      listener.onScoreUpdatedP2(newScoreP2);
    }
  }

  /**
   * Notifies all registered listeners about a change in the current player's turn.
   */
  private void notifyTurnChanged() {
    int currentIndex = currentPlayerIndex;
    ModelStatus current = modelStatusListeners.get(currentIndex);
    current.onTurnChanged(getCurrentPlayer());
  }


  /**
   * Notifies all registered listeners about a change in the current player's turn.
   */
  private void changeTurnInfo() {
    for (ModelStatus listener : modelStatusListeners) {
      listener.onTurnChangeInfo(getCurrentPlayer());
    }
  }

  /**
   * Notifies all registered listeners when the game ends.
   */
  private void notifyGameEnded() {
    for (ModelStatus listener : modelStatusListeners) {
      listener.onGameEnded(winner());
    }
  }

  /**
   * This will notify the first player that it is their turn after the game is initialized, thus
   * starting the game.
   */
  private void notifyFirstPlayer() {
    if (!modelStatusListeners.isEmpty()) {
      ModelStatus firstPlayer = modelStatusListeners.get(0);
      firstPlayer.onGameStarted();
    }
  }

  /**
   * This will notify the player when there is no valid moves left for them to make.
   */
  private void notifyPlayerNoValidMove() {
    int currentIndex = currentPlayerIndex;
    ModelStatus current = modelStatusListeners.get(currentIndex);
    current.onNoValidMove();
  }
}
