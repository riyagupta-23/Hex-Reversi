package cs3500.reversi.model;

//import cs3500.reversi.model.CellColor;
//import cs3500.reversi.model.Hex;
//import cs3500.reversi.model.ReadOnlyIGame;

/**
 * This shows the information about potential moves made by players, this is especially helpful
 * with the machine player, who needs to have each move checked and calculated before they are
 * actually able to move. This class can return if a move can be passed, the coordinates of the
 * move, and the move itself. This allows for checks to be made on moves itself.
 */
public class MoveAction {
  private final CoordinateSystem move;
  private final boolean isPass;

  /**
   * Constructor for a move to be made by a machine player.
   * @param move The coordinates of the wanted move.
   */
  public MoveAction(CoordinateSystem move) {
    this.move = move;
    this.isPass = false;
  }

  /**
   * Constructor for a pass to be made by a machine player.
   */
  public MoveAction() {
    this.move = null;
    this.isPass = true;
  }

  /**
   * Checks if it has passed and returns whether it has or not.
   * @return The pass condition.
   */
  public boolean isPass() {
    return isPass;
  }

  /**
   * Grabs the move of the action.
   * @return The current move of the player.
   */
  public CoordinateSystem getMove() {
    return move;
  }
}
