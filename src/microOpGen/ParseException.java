package microOpGen;

import javax.swing.JOptionPane;
/** Classe per la gestione delle eccezioni di parsing 
 * @author Roberto Palmieri, Giulio Vennari
 */
public class ParseException extends Exception {

  public ParseException(String message) {
    super(message);
    specialConstructor = false;
    JOptionPane.showMessageDialog(null,message);
    
  }

    protected boolean specialConstructor;

}
