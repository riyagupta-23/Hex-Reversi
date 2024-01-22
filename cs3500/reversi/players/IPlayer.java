package cs3500.reversi.players;

import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.IGame;
//import cs3500.reversi.model.ReversiGame;
import cs3500.reversi.model.strategy.ReversiStrategies;

/**
 * This interface represents a player that could potentially be a user, machine or other. This
 * allows the color of a player to be grabbed as well as actions like play and passMove to be
 * executed.
 */
public interface IPlayer {

  /**
   * gets the color of this player.
   * @return the Color of this player
   */
  CellColor getColor();

  /**
   * checks if this move is valid before doing the move.
   * @param game the reversi game this player is playing.
   * @param q the row of the move.
   * @param r the column of the move.
   */
  void play(IGame game, int q, int r);

  /**
   * passes this turn.
   * @param game the reversi game this player is playing.
   */
  void passMove(IGame game);

  ReversiStrategies getStrategy();
}

