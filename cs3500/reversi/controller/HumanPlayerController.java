package cs3500.reversi.controller;

import java.io.IOException;
import java.util.HashMap;

import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.CoordinateSystem;
import cs3500.reversi.model.IBoard;
import cs3500.reversi.model.IGame;
import cs3500.reversi.model.MoveAction;
import cs3500.reversi.model.StartGameListener;
import cs3500.reversi.players.IPlayer;
import cs3500.reversi.view.ReversiView;

/**
 * This is the controller for the human player that will take in commands from the user and
 * communicate that with the model and view.
 */
public class HumanPlayerController implements GameController {
  private final IGame model;
  private final IPlayer player;
  private final ReversiView view;


  /**
   * Controller for the human player that will take in commands from the user and
   * communicate that with the model and view.
   *
   * @param model                Current model for the game.
   * @param player               Current player.
   * @param view                 Player view.
   * @param gameStartCoordinator The listener to make sure the game has been started.
   */
  public HumanPlayerController(IGame model, IPlayer player, ReversiView view,
                               StartGameListener gameStartCoordinator) {
    this.model = model;
    this.player = player;
    this.view = view;

    // Register the controller as a listener for player actions and model status changes
    view.addPlayerActionListener(this);
    model.addModelStatusListener(this);

    view.setStartGameListener(() -> gameStartCoordinator.onPlayerReady());
  }


  @Override
  public void attemptPlay(MoveAction m) {
    // Make sure it's the human player's turn
    if (model.getCurrentPlayer().equals(player)) {
      try {
        CoordinateSystem move = m.getMove();
        player.play(model, move.getQ(), move.getR());
        IBoard board = model.getGrid();
        HashMap<CoordinateSystem, CellColor> currentState = board.getBoard();
        onBoardStateChanged(currentState);
      } catch (IllegalArgumentException e) {
        view.showErrorMessage("Invalid move!");
      }
    } else {
      view.showErrorMessage("It's not your turn!");
    }
  }

  @Override
  public void attemptPassTurn() {
    model.implementPass(player.getColor());
  }

  @Override
  public void onBoardStateChanged(HashMap<CoordinateSystem, CellColor> currentState) {
    view.updateState(currentState);
    view.refresh();
  }

  @Override
  public void onTurnChanged(IPlayer currentPlayer) {
    // Update the view to notify the change of turn
    view.updateTurnIndicator(currentPlayer);
  }

  @Override
  public void onTurnChangeInfo(IPlayer currentPlayer) {
    // Update the view to reflect the change of turn
    view.updateTurnInfo(currentPlayer);

  }

  @Override
  public void onNoValidMove() {
    view.showAutoPass();
    attemptPassTurn();

  }

  @Override
  public void onGameEnded(IPlayer winner) {
    view.displayEndGame(winner);
  }

  @Override
  public void onScoreUpdatedP1(int newScore) {
    view.displayScoreP1(newScore);
  }

  @Override
  public void onScoreUpdatedP2(int newScore) {
    view.displayScoreP2(newScore);
  }

  @Override
  public void onGameStarted() {
    try {
      view.updateStartScreen();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
