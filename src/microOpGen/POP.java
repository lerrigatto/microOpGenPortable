package microOpGen;
import java.util.*;

/**
 * Classe dell'istruzione POP con tutti i possibili indirizzamenti
 * @author Roberto Palmieri, Giulio Vennari
 */
public class POP extends Istruzione {
    /**
     * Costruttore della classe
     * @param c Array di caratteri rappresentanti l'intera istruzione
     * @param l LinkedList contenente le stringhe dell'istruzione divisa come "Operatore -> Sorg -> Dest"
     */
    private String modoDst;

    protected POP(LinkedList l, char[] c) throws ParseException {
        super(l.get(0), null, ((l.size() == 3) ? l.get(2) : l.get(1)));
        assegnaByte(l, c);
        modoDst = riconosciModoSrc(c);
        comment.add("Instruction POP: MOVL (R7)+,DEST");
    }

	protected LinkedList getComment(){
		return comment;
	}

	private void assegnaByte(LinkedList l, char[] c) throws ParseException {
		BYTE[0] = " - - -";				// 31-29 Classe
		BYTE[1] = " - - - - -";
		BYTE[2] = " -  -  -  -  -";					// 23-16 K
		BYTE[3] = " - -";						// 15-14 I/O
		BYTE[4] = " - -";						// 13-12 s
		BYTE[5] = " -  -  -";					// 11-9  Modo S
		BYTE[6] = " -  -  -";					// 8-6  Srg
		BYTE[7] = " -  -  -";					// 5-3  Modo D
		BYTE[8] = " -  -  -";					// 2-0  Dst
	}
	
	protected LinkedList getMO(){
		if(modoDst.equals("000"))
		{
			mop.add("R7 -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("MDR -> RX");
		}
		else if(modoDst.equals("010"))
		{
			mop.add("PC -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("MDR -> TEMP2");
			mop.add("PC+4 -> PC");
			mop.add("R7 -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("SHIFTER_OUT[SHIFT_0] -> MAR");
			mop.add("MDR -> (MAR)");
		}
		else if(modoDst.equals("011"))
		{
			mop.add("R7 -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("RX -> MAR");
			mop.add("MDR -> (MAR)");
		}
		else if(modoDst.equals("100"))
		{
			mop.add("PC -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("MDR -> TEMP1");
			mop.add("PC+4 -> PC");
			mop.add("RX -> TEMP2");
			mop.add("R7 -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("ALU_OUT[ADD] -> MAR");
			mop.add("MDR -> (MAR)");
		}
		else if(modoDst.equals("101"))
		{
			mop.add("PC -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("MDR -> TEMP1");
			mop.add("PC -> TEMP2");
			mop.add("PC+4 -> PC");
			mop.add("R7 -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("ALU_OUT[ADD] -> MAR");
			mop.add("MDR -> (MAR)");
		}
		else if(modoDst.equals("110"))
		{
			mop.add("RX -> TEMP1");
			mop.add("S -> ALU ; ALU_OUT[SUB] -> RX");
			mop.add("R7 -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("RX -> MAR");
			mop.add("MDR -> (MAR)");
		}
		else if(modoDst.equals("111"))
		{
			mop.add("R7 -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("RX -> MAR");
			mop.add("MDR -> (MAR)");
			mop.add("RX -> TEMP1");
			mop.add("S -> ALU ; ALU_OUT[ADD] -> RX");
		}
		mop.add("R7 -> TEMP1");
		mop.add("4 -> ALU ; ALU_OUT[ADD] -> R7");
		return mop;
	}
}