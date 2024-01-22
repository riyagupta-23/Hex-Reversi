package cs3500.reversi.view;



/**
 * Interface for the views for the game that takes in actions. Even though there
 * are no methods in this class there are two interfaces extended in
 * this class that make up the views, if any views were to be made in the future
 * calling this interface would be simpler than calling either of the other two.
 */
public interface ReversiView extends ViewStatus, EnhancedView {

}
