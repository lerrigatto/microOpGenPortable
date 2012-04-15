package microOpGen;
import java.util.*;

/**
 * Classe dell'istruzione PUSH con tutti i possibili indirizzamenti
 * @author Roberto Palmieri, Giulio Vennari
 */
public class PUSH extends Istruzione {
    /**
     * Costruttore della classe
     * @param c Array di caratteri rappresentanti l'intera istruzione
     * @param l LinkedList contenente le stringhe dell'istruzione divisa come "Operatore -> Sorg -> Dest"
     */
    private String modoSrc;

    protected PUSH(LinkedList l, char[] c) throws ParseException {
        super(l.get(0), null, ((l.size() == 3) ? l.get(2) : l.get(1)));
        assegnaByte(l, c);
        modoSrc = riconosciModoSrc(c);
        comment.add("Instruction PUSH: MOVL SORG,-(R7)");
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
		mop.add("R7 -> TEMP1");
		mop.add("4 -> ALU ; ALU_OUT[SUB] -> R7");
		if(modoSrc.equals("000"))
			mop.add("RX -> MDR");
		else if(modoSrc.equals("001"))
		{
			mop.add("PC -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("PC+4 -> PC");
		}
		else if(modoSrc.equals("010"))
		{
			mop.add("PC -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("PC+4 -> PC");
			mop.add("MDR -> MAR");
			mop.add("(MAR) -> MDR");
		}
		else if(modoSrc.equals("011"))
		{
			mop.add("RX -> MAR");
			mop.add("(MAR) -> MDR");
		}
		else if(modoSrc.equals("100"))
		{
			mop.add("PC -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("PC+4 -> PC");
			mop.add("MDR -> TEMP1");
			mop.add("RX -> TEMP2");
			mop.add("ALU_OUT[ADD] -> MDR");
		}
		else if(modoSrc.equals("101"))
		{
			mop.add("PC -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("MDR -> TEMP1");
			mop.add("PC -> TEMP2");
			mop.add("PC+4 -> PC");
			mop.add("ALU_OUT[ADD] -> MDR");
		}
		else if(modoSrc.equals("110"))
		{
			mop.add("RX -> TEMP1");
			mop.add("S -> ALU ; ALU_OUT[SUB] -> RX ; ALU_OUT[SUB] -> MAR");
			mop.add("(MAR) -> MDR");
		}
		else if(modoSrc.equals("111"))
		{
			mop.add("RX -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("RX -> TEMP1");
			mop.add("S -> ALU ; ALU_OUT[ADD] -> RX");
		}
		mop.add("R7 -> MAR");
		mop.add("MDR -> (MAR)");
		return mop;
	}
}