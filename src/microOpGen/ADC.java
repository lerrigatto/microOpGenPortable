package microOpGen;
import java.util.*;

/**
 * Classe astratta dell'istruzione Istr[RX,RY]
 * @author Roberto Palmieri, Giulio Vennari
 */
public class ADC extends Istruzione {
    /**
     * Costruttore della classe
     * @param c Array di caratteri rappresentanti l'intera istruzione
     * @param l LinkedList contenente le stringhe dell'istruzione divisa come "Operatore -> Sorg -> Dest"
     */
    protected ADC(LinkedList l, char[] c) throws ParseException {
        super(l.get(0),
        	((l.size() == 4) ? l.get(3) : l.get(2)),
        	((l.size() == 4) ? l.get(2) : l.get(1)));
        assegnaByte(l, c);
        comment.add("Instruction ADC: SORG + Ri + Carry-> Ri");
    }
    protected LinkedList getComment(){
		return comment;
	}

	private void assegnaByte(LinkedList l, char[] c) throws ParseException {
		BYTE[0] = "010";				// 31-29 Classe
		BYTE[1] = "00001";				// 28-24 Type
		BYTE[2] = "--------";			// 23-16 K
		BYTE[3] = "--";					// 15-14 I/O
		BYTE[4] = "S";					// 13-12 s
		BYTE[5] = riconosciModoSrc(c);	// 11-9  Modo S
		BYTE[6] = riconosciSrc();		// 8-6  Srg
		BYTE[7] = riconosciModoDst(c);	// 5-3  Modo D
		BYTE[8] = riconosciDst();		// 2-0  Dst
	}
	
	protected LinkedList getMO(){
		if(BYTE[5].equals("000"))
			mop.add("RX -> TEMP1");
		else if(BYTE[5].equals("110"))
		{
			mop.add("RX -> TEMP1");
			mop.add("S -> ALU ; ALU_OUT[SUB] -> RX");
			mop.add("RX -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("MDR -> TEMP1");
		}
		else if(BYTE[5].equals("011"))
		{
			mop.add("RX -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("MDR -> TEMP1");
		}
		else if(!BYTE[5].equals("111"))
		{
			mop.add("PC -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("PC+4 -> PC");
			if(BYTE[5].equals("001"))
				mop.add("MDR -> TEMP1");
			else if(BYTE[5].equals("010"))
			{
				mop.add("MDR -> MAR");
				mop.add("(MAR) -> MDR");
				//mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
			}
			else if(BYTE[5].equals("100"))
			{
				mop.add("MDR -> TEMP1");
				mop.add("RX -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> TEMP1");
			}
			else if(BYTE[5].equals("101"))
			{
				mop.add("MDR -> TEMP1");
				mop.add("PC -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> TEMP1");
			}
		}
		else
		{
			mop.add("RX -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("MDR -> TEMP1");
		}
		mop.add("RY -> TEMP2");
		mop.add("ALU_OUT[ADD_with_Carry] -> RY");
		if(BYTE[5].equals("111"))
		{
			mop.add("RX -> TEMP1");
			mop.add("S -> ALU ; ALU_OUT[ADD] -> RX");
		}
		return mop;
	}
}