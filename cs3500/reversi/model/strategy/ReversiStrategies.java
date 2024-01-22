package cs3500.reversi.model.strategy;

import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.MoveAction;
import cs3500.reversi.model.ReadOnlyIGame;

/**
 * Interface for strategies used in the Reversi game.
 * This interface defines the essential method that all Reversi strategies must implement,
 * which is to choose a move based on the current game state and player color.
 */
public interface ReversiStrategies {

  /**
   * Chooses the next move or pass action for a player based on the current game state.
   * Implementing classes will define the strategy to select the most appropriate move.
   *
   * @param game        The current game state as a ReadonlyIGame object.
   * @param playerColor The color of the player making the move.
   * @return The chosen move as a Discobject.
   */
  MoveAction chooseMove(ReadOnlyIGame game, CellColor playerColor);

}
