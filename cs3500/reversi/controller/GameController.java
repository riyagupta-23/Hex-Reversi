package cs3500.reversi.controller;

/**
 * Interface for the controllers for the game that takes in actions and communicates them to the
 * model. Even though there are no methods in this class there are two interfaces extended in
 * this class that make up the controllers, if any controllers were to be made in the future
 * calling this interface would be simpler than calling either of the other two.
 */
public interface GameController extends PlayerAction, ModelStatus {
}
