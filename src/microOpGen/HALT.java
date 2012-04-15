package microOpGen;
import java.util.*;

/**
 * Classe dell'istruzione HALT
 * @author Roberto Palmieri, Giulio Vennari
 */
public class HALT extends Istruzione {
    /**
     * Costruttore della classe
     * @param c Array di caratteri rappresentanti l'intera istruzione
     * @param l LinkedList contenente le stringhe dell'istruzione divisa come "Operatore -> Sorg -> Dest"
     */
    protected HALT(LinkedList l, char[] c) throws ParseException {
        super(l.get(0), null, null);
        assegnaByte(l, c);
        comment.add("Instruction HALT: CPU wait until interrupt or reset");
    }
    
    protected LinkedList getComment(){
		return comment;
	}
	
	private void assegnaByte(LinkedList l, char[] c) throws ParseException {
		BYTE[0] = "000";				// 31-29 Classe
		BYTE[1] = "00000";				// 28-24 Type
		BYTE[2] = "--------";			// 23-16 K
		BYTE[3] = "--";					// 15-14 I/O
		BYTE[4] = "--";					// 13-12 s
		BYTE[5] = "---";				// 11-9  Modo S
		BYTE[6] = "---";				// 8-6  Srg
		BYTE[7] = "---";				// 5-3  Modo D
		BYTE[8] = "---";				// 2-0  Dst
	}
	
	protected LinkedList getMO(){
		return mop;
	}
}