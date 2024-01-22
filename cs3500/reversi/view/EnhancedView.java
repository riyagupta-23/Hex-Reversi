package cs3500.reversi.view;

import java.util.function.Consumer;

import cs3500.reversi.controller.PlayerAction;
import cs3500.reversi.model.StartGameListener;

/**
 * Enhanced view enhances the regular view by adding notifications onto the GUI and allowing
 * the program to "talk" to the user when needed. Makes the board visible after construction.
 * It also enhances by refreshing the board every time a move is made, making sure the board
 * is always up-to-date. The Callback command will also inform the player if there was an
 * error when the player tries to make their move.
 */
public interface EnhancedView {
  /**
   * Make the view visible. This is usually called after the view is constructed.
   */
  void makeVisible();

  /**
   * Provide the view with a callback option to process a command.
   *
   * @param callback object
   */
  void setCommandCallback(Consumer<String> callback);

  /**
   * Transmit an error message to the view.
   * This method should be called when a command or operation
   * cannot be processed correctly, allowing the view to inform the user.
   *
   * @param error The error message to be displayed. This should contain
   *              relevant information about the failure or issue encountered.
   */
  void showErrorMessage(String error);

  /**
   * Signal the view to update and redraw itself.
   * This method is typically used to refresh the visual representation of the
   * view, ensuring it reflects the current state of the model or application.
   */
  void refresh();

  /**
   * Adds a listener to listen for actions from the players.
   *
   * @param listener The player action.
   */
  void addPlayerActionListener(PlayerAction listener);

  /**
   * Adds a listener to see when the game starts.
   *
   * @param listener The listener waiting until the game starts.
   */
  void setStartGameListener(StartGameListener listener);
}

