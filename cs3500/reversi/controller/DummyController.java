package cs3500.reversi.controller;

import java.util.HashMap;

import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.CoordinateSystem;
import cs3500.reversi.model.MoveAction;
import cs3500.reversi.players.IPlayer;

/**
 * A dummy implementation of the GameController interface for use in the textual view of Reversi.
 * This class provides empty implementations of all methods in the GameController interface.
 */
public class DummyController implements GameController {


  @Override
  public void onBoardStateChanged(HashMap<CoordinateSystem, CellColor> currentState) {
    // for use in the textual view of Reversi
  }

  @Override
  public void onTurnChanged(IPlayer currentPlayer) {
    // for use in the textual view of Reversi
  }

  @Override
  public void onGameEnded(IPlayer winner) {
    // for use in the textual view of Reversi
  }

  @Override
  public void onScoreUpdatedP1(int newScore) {
    // for use in the textual view of Reversi
  }

  @Override
  public void onScoreUpdatedP2(int newScore) {
    // for use in the textual view of Reversi
  }

  @Override
  public void onGameStarted() {
    // for use in the textual view of Reversi
  }

  @Override
  public void onTurnChangeInfo(IPlayer currentPlayer) {
    // for use in the textual view of Reversi
  }


  @Override
  public void onNoValidMove() {
    // for use in the textual view of Reversi
  }

  @Override
  public void attemptPlay(MoveAction action) {
    // for use in the textual view of Reversi
  }


  @Override
  public void attemptPassTurn() {
    // for use in the textual view of Reversi
  }
}
