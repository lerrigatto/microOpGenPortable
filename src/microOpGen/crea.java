package microOpGen;

import microOpGen.gui.FramePrincipale;
import javax.swing.*;
import java.util.*;
import java.io.*;
import microOpGen.gui.*;

/** Classe per la creazione della lisa di micro operazioni, relative all'istruzione, e la stampa a video nella text area del frame principale 
 *  
 *  @param s Istruzione formato toString
 *  @author Roberto Palmieri, Giulio Vennari
 */
public class crea {

    public static LinkedList creaListaMO(String s) throws ParseException {
        if(s != null && s.length() > 0) {
            Istruzione istru = IstruzioneFactory.assegna(s);
            //assegna microcodice
            LinkedList l = istru.getMO();
            LinkedList l2 = istru.getComment();
            //assegnazione di un array di bit
        	String[] bit = istru.getByte();
			FramePrincipale.scriviBit(bit);
            for(int j=0; j<l2.size(); j++) {
                String riga  = ((String)l2.get(j));
                FramePrincipale.scriviCommenti(riga);
            }
            for(int i=0; i<l.size(); i++) {
                String riga  = ((String)l.get(i)).toUpperCase(); 
                FramePrincipale.scriviOperazioni(riga);
            }
            return l;
        }
        throw new ParseException("Istruzione non riconosciuta");
    }
    
    public static void toString(LinkedList l) {
        ListIterator lit = l.listIterator(0);
        int i = 0;
        while(lit.hasNext()) {
            String s = (String)lit.next();
            FramePrincipale.scriviOperazioni(s);
        	i++;
        }
    }
    
    public void crea(String input) throws ParseException{
        toString(creaListaMO(input));
    }
}