package microOpGen;
import java.util.*;

/**
 * Classe dell'istruzione LSL con tutti i possibili indirizzamenti
 * @author Roberto Palmieri, Giulio Vennari
 */
public class LSL extends Istruzione {
    /**
     * Costruttore della classe
     * @param c Array di caratteri rappresentanti l'intera istruzione
     * @param l LinkedList contenente le stringhe dell'istruzione divisa come "Operatore -> Sorg -> Dest"
     */
    protected LSL(LinkedList l, char[] c) throws ParseException {
        super(l.get(0), l.get(2), l.get(1));
        assegnaByte(l, c);
        comment.add("Instruction LSL: logical shift sx of K position Ry -> Ry\nRi[32-K]->Carry; Repeat Ry[32] in most significant K bits");
    }
    protected LinkedList getComment(){
		return comment;
	}
	private void assegnaByte(LinkedList l, char[] c) throws ParseException {
		BYTE[0] = "100";				// 31-29 Classe
		BYTE[1] = "00010";				// 28-24 Type
		BYTE[2] = "   K";				// 23-16 K
		BYTE[3] = "--";					// 15-14 I/O
		BYTE[4] = "S";					// 13-12 s
		BYTE[5] = "---";					// 11-9  Modo S
		BYTE[6] = "---";					// 8-6  Srg
		BYTE[7] = riconosciModoDst(c);	// 5-3  Modo D
		BYTE[8] = riconosciDst();		// 2-0  Dst
	}
	
	protected LinkedList getMO(){
		mop.add("RY -> TEMP2");
		mop.add("SHIFTER_OUT[LOGIC SX] -> RY");
		return mop;
	}
}