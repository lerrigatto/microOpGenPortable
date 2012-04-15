package microOpGen;

import javax.swing.JOptionPane;
/**
 * Classe che gestisce le eccezioni delle animazioni
 * @author Roberto Palmieri, Giulio Vennari
 * 
 */
public class AnimationException extends Exception {
	public AnimationException() {}
        
        /**
         *  Mostra a schermo un dialog contente il messaggio d'errore
         * @param message Stringa contente il testo del messaggio
         */
	public AnimationException(String message){
	
	super(message);
    specialConstructor = false;
    
    JOptionPane.showMessageDialog(null,message);
	}
	protected boolean specialConstructor;
}