package cs3500.reversi.players;

import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.IGame;
import cs3500.reversi.model.MoveAction;
import cs3500.reversi.model.strategy.ReversiStrategies;

/**
 * Represents the Player that is using strategies on the Reversi board instead of a user.
 */
public class MachinePlayer implements IPlayer {
  private final CellColor color;
  private final ReversiStrategies strategy;

  /**
   * Creates a new Machine Player.
   *
   * @param color the color of the new Player.
   */
  public MachinePlayer(CellColor color, ReversiStrategies strategy) {
    if (color == null) {
      throw new IllegalArgumentException("Invalid color");
    }
    this.color = color;
    this.strategy = strategy;
  }

  @Override
  public void play(IGame game, int q, int r) {
    MoveAction action = strategy.chooseMove(game, this.color);
    if (!action.isPass()) {
      game.makeMove(q, r, this.color);
    } else {
      passMove(game);
    }
  }

  @Override
  public void passMove(IGame game) {
    game.implementPass(this.color);
  }

  @Override
  public CellColor getColor() {
    return this.color;
  }

  public ReversiStrategies getStrategy() {
    return this.strategy;
  }

}