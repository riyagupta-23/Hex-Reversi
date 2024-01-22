package cs3500.reversi.controller;

import java.util.HashMap;

import cs3500.reversi.model.CellColor;
import cs3500.reversi.model.CoordinateSystem;
import cs3500.reversi.model.IGame;
import cs3500.reversi.model.MoveAction;
import cs3500.reversi.model.StartGameListener;
import cs3500.reversi.players.IPlayer;
import cs3500.reversi.view.ReversiView;

/**
 * Controller for the machine controller that will take in a strategy to use that and communicate
 * with the model and the view.
 */
public class MachinePlayerController implements GameController {
  private final IGame model;
  private final IPlayer player;
  private final ReversiView view;

  /**
   * This constructor will act as the machine player's
   * controller and will take a strategy in and use
   * that to play the game.
   *
   * @param model                Current game.
   * @param player               Current player.
   * @param view                 Machine view.
   * @param gameStartCoordinator The listener seeing if the game has started.
   */
  public MachinePlayerController(IGame model, IPlayer player, ReversiView view,
                                 StartGameListener gameStartCoordinator) {
    this.model = model;
    this.player = player;
    this.view = view;

    // Register the controller as a listener for player actions and model status changes
    view.addPlayerActionListener(this);
    model.addModelStatusListener(this);

    gameStartCoordinator.onPlayerReady();
  }

  @Override
  public void attemptPlay(MoveAction action) {
    if (action.isPass()) {
      attemptPassTurn();
    } else {
      CoordinateSystem move = action.getMove();
      if (model.getCurrentPlayer().equals(player)) {
        player.play(model, move.getQ(), move.getR());
      }
    }
  }

  @Override
  public void attemptPassTurn() {
    player.passMove(model);
  }

  @Override
  public void onBoardStateChanged(HashMap<CoordinateSystem, CellColor> currentState) {
    view.updateState(currentState);
    view.refresh();
  }

  @Override
  public void onTurnChanged(IPlayer currentPlayer) {
    MoveAction action = player.getStrategy().chooseMove(model, currentPlayer.getColor());
    attemptPlay(action);
  }

  @Override
  public void onGameEnded(IPlayer winner) {
    // Handle the end of the game in the view
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
    MoveAction action = player.getStrategy().chooseMove(model, CellColor.BLACK);
    attemptPlay(action);
  }

  @Override
  public void onTurnChangeInfo(IPlayer currentPlayer) {
    view.updateTurnInfo(currentPlayer);
  }

  @Override
  public void onNoValidMove() {
    attemptPassTurn();
  }
}
