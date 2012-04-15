/*
 * Main()
 * @author Roberto Palmieri, Giulio Vennari
 */
package microOpGen;

import microOpGen.gui.FramePrincipale;
import javax.swing.*;
import microOpGen.gui.*;


/** Proprio quello che immaginate, la classe con il main() */
public class Main {
	private static FramePrincipale framep;

    /** Impedisce l'istanziazione */
    private Main() {
    }


    /** C'e' qualcosa da dire su questo metodo? */
    public static void main(String[] args) throws Exception
    {
		
        FramePrincipale pf = new FramePrincipale();
        pf.setSize(pf.getWidth()+140, pf.getHeight()+150);
        framep = pf;
        pf.alertOff();
        pf.setVisible(true);
        //pf.show();
	}
	/** serve per fare il refresh della finestra ogni qualvolta l'istruzione */
	public static void show()	// cambia il num dei parametri
	{
		framep.setVisible(true);
		//framep.show();
	}
	
	
	/** usato per scrivere sulla finestra un avviso per utente, di parametro errato */
	protected static void alert()
	{
		framep.alertOn();
		framep.setVisible(true);
	}

}
