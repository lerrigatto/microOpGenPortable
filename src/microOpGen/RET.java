package microOpGen;
import java.util.*;

/**
 * Classe dell'istruzione RET con tutti i possibili indirizzamenti
 * @author Roberto Palmieri, Giulio Vennari
 */
public class RET extends Istruzione {
    /**
     * Costruttore della classe
     * @param c Array di caratteri rappresentanti l'intera istruzione
     * @param l LinkedList contenente le stringhe dell'istruzione divisa come "Operatore -> Sorg -> Dest"
     */
    protected RET(LinkedList l, char[] c) throws ParseException {
        super(l.get(0), null, null);
        assegnaByte(l, c);
        comment.add("Instruction RET: POP PC");
    }

	protected LinkedList getComment(){
		return comment;
	}

	private void assegnaByte(LinkedList l, char[] c) throws ParseException {
		BYTE[0] = "110";				// 31-29 Classe
		BYTE[1] = "00010";
		BYTE[2] = " -  -  -  -  -";					// 23-16 K
		BYTE[3] = " - -";						// 15-14 I/O
		BYTE[4] = " 1 0";						// 13-12 s
		BYTE[5] = " -  -  -";					// 11-9  Modo S
		BYTE[6] = " -  -  -";					// 8-6  Srg
		BYTE[7] = " -  -  -";					// 5-3  Modo D
		BYTE[8] = " -  -  -";					// 2-0  Dst
	}
	
	protected LinkedList getMO(){
		mop.add("R7 -> MAR");
		mop.add("(MAR) -> MDR");
		mop.add("MDR -> PC");
		mop.add("R7 -> TEMP1");
		mop.add("4 -> ALU ; ALU_OUT[ADD] -> R7");
		return mop;
	}
}