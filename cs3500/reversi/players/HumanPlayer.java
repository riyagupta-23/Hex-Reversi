package cs3500.reversi.players;

import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.IGame;
//import cs3500.reversi.model.ReversiGame;
import cs3500.reversi.model.strategy.ReversiStrategies;

/**
 * This class is an implementation of IPLayer and represents a HumanPlayer. Its functionality
 * manages all the Human Player functionality.
 */
public class HumanPlayer implements IPlayer {
  private final CellColor color;

  /**
   * Creates a new Human Player.
   *
   * @param color the color of the new Player.
   */
  public HumanPlayer(CellColor color) {
    if (color == null) {
      throw new IllegalArgumentException("Invalid color");
    }
    this.color = color;
  }


  public CellColor getColor() {
    return this.color;
  }

  @Override
  public void play(IGame game, int q, int r) {
    if (game.isValidMove(q, r, this.color)) {
      game.makeMove(q, r, this.color);
    } else {
      throw new IllegalArgumentException("Invalid move");
    }
  }

  @Override
  public void passMove(IGame game) {
    game.implementPass(this.color);
  }

  @Override
  public ReversiStrategies getStrategy() {
    return null;
  }
}
