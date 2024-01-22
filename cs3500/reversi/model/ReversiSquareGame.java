package cs3500.reversi.model;

import cs3500.reversi.players.IPlayer;

/**
 * This class represents a specific implementation of the Reversi game with a square board.
 * It extends the AbstractReversiGame class, using a SquareBoard as its game board.
 * This class provides the functionality specific to a square board Reversi game.
 */
public class ReversiSquareGame extends AbstractReversiGame {

  /**
   * Constructs a ReversiSquareGame with two players and a specified board size.
   * Initializes the players, sets up the board as a SquareBoard with the given size.
   *
   * @param player1   The first player of the game.
   * @param player2   The second player of the game.
   * @param boardSize The size of the square board.
   */
  public ReversiSquareGame(IPlayer player1, IPlayer player2, int boardSize) {
    super(player1, player2, boardSize);
    board = new SquareBoard(boardSize);
  }

  @Override
  public IGame cloneGame() {
    ReversiSquareGame clonedGame =
            new ReversiSquareGame(this.players[0], this.players[1],
                    this.board.getLength());
    clonedGame.board = this.board.cloneBoard();
    clonedGame.currentPlayerIndex = this.currentPlayerIndex;
    clonedGame.lastPlayedColor = this.lastPlayedColor;
    clonedGame.numOfPasses = this.numOfPasses;
    return clonedGame;
  }


  @Override
  protected void validateMove(int q, int r, CellColor color) {
    if (color == null) {
      throw new IllegalArgumentException("Invalid color");
    }

    if (q < 0 || q > board.getLength() - 1) {
      throw new IllegalArgumentException("Invalid coordinate");  // q is out of range
    }

    if (r < 0 || r > board.getLength() - 1) {
      throw new IllegalArgumentException("Invalid coordinate");  // r is out of range
    }
  }

  @Override
  public boolean hasValidMoveForPlayer(CellColor playerColor) {
    for (int q = 0; q < board.getLength(); q++) {
      for (int r = 0; r < board.getLength(); r++) {
        if (isValidMove(q, r, playerColor)) {
          return true;
        }
      }
    }
    return false;
  }

}
