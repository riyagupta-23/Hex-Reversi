package cs3500.reversi.model;

import cs3500.reversi.players.IPlayer;

/**
 * Represents the main Reversi game logic.
 * The game is played on a hexagonal board where players take turns placing their color discs
 * on the board to capture opponent discs.
 * The game ends when both players pass their turns consecutively.
 *
 * <p>Usage:
 * ReversiGame game = new ReversiGame(player1, player2, boardSize);
 * game.makeMove(q, r, color);
 */
public class ReversiHexGame extends AbstractReversiGame {
  /**
   * Constructs a new Reversi game.
   *
   * @param player1   First player.
   * @param player2   Second player.
   * @param boardSize The size of the board's side.
   */
  public ReversiHexGame(IPlayer player1, IPlayer player2, int boardSize) {
    super(player1, player2, boardSize);
    board = new HexBoard(boardSize);
  }

}
