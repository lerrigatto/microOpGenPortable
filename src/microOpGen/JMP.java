package microOpGen;
import java.util.*;

/**
 * Classe dell'istruzione JMP con tutti i possibili indirizzamenti
 * @author Roberto Palmieri, Giulio Vennari
 */
public class JMP extends Istruzione {
    /**
     * Costruttore della classe
     * @param c Array di caratteri rappresentanti l'intera istruzione
     * @param l LinkedList contenente le stringhe dell'istruzione divisa come "Operatore -> Sorg -> Dest"
     */
    protected JMP(LinkedList l, char[] c) throws ParseException {
        super(l.get(0), null, ((l.size() == 3) ? l.get(2) : l.get(1)));
        assegnaByte(l, c);
        comment.add("Instruction JMP: DEST -> PC");
    }

	protected LinkedList getComment(){
		return comment;
	}

	private void assegnaByte(LinkedList l, char[] c) throws ParseException {
		BYTE[0] = "110";				// 31-29 Classe
		BYTE[1] = "00000";
		BYTE[2] = " -  -  -  -  -";				// 23-16 K
		BYTE[3] = " - -";						// 15-14 I/O
		BYTE[4] = " 1 0";						// 13-12 s
		BYTE[5] = " -  -  -";					// 11-9  Modo S
		BYTE[6] = " -  -  -";					// 8-6  Srg
		BYTE[7] = riconosciModoSrc(c);	// 5-3  Modo D
		BYTE[8] = riconosciSrc();		// 2-0  Dst
	}
	
	protected LinkedList getMO(){
		if(BYTE[7].equals("100") || BYTE[7].equals("101"))
		{
			mop.add("PC -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("MDR -> TEMP1");
			if(BYTE[7].equals("100"))
				mop.add("RX -> TEMP2");
			else
				mop.add("PC -> TEMP2");
			mop.add("ALU_OUT[ADD] -> PC");
		}
		else
		{
			if(BYTE[7].equals("010"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
			}
			else if(BYTE[7].equals("011"))
			{
				mop.add("RX -> MAR");
				mop.add("(MAR) -> MDR");
			}
			else if(BYTE[7].equals("110"))
			{
				mop.add("RX -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[SUB] -> RX");
				mop.add("RX -> MAR");
				mop.add("(MAR) -> MDR");
			}
			else if(BYTE[7].equals("111"))
			{
				mop.add("RX -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> PC");
				mop.add("RX -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[ADD] -> RX");
			}
			mop.add("MDR -> PC");
		}
		return mop;
	}
}