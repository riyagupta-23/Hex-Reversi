package cs3500.reversi.model;

/**
 * Listens for the game to start and notifies the controllers once it does.
 */
public class GameStartCoordinator implements StartGameListener {
  private final int totalPlayers;
  private final IGame model;
  private int readyPlayerCount = 0;

  /**
   * Listens for the game to start and notifies the controllers once it does.
   * @param model Current game.
   */
  public GameStartCoordinator(IGame model) {
    this.model = model;
    this.totalPlayers = 2;
  }

  @Override
  public void onPlayerReady() {
    readyPlayerCount++;
    if (readyPlayerCount == totalPlayers) {
      model.startGame();
      readyPlayerCount = 0; // Reset for next game
    }
  }
}
