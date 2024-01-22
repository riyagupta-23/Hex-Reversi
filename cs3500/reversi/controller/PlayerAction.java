package cs3500.reversi.controller;

import cs3500.reversi.model.MoveAction;

/**
 * This shows all the actions the player should need to make within the game of Reversi.
 */
public interface PlayerAction {
  /**s
   * Attempt to place a disk at a specified location on the board.
   *
   * @param action a move or a pass.
   */
  void attemptPlay(MoveAction action);

  /**
   * Attempt to pass the turn to the next player.
   */
  void attemptPassTurn();

}
