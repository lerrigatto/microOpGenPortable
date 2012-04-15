package microOpGen;
import java.util.*;

/**
 * Classe dell'istruzione MOVTOSR con tutti i possibili indirizzamenti
 * @author Roberto Palmieri, Giulio Vennari
 */
public class MOVTOSR extends Istruzione {
    /**
     * Costruttore della classe
     * @param c Array di caratteri rappresentanti l'intera istruzione
     * @param l LinkedList contenente le stringhe dell'istruzione divisa come "Operatore -> Sorg -> Dest"
     */
    protected MOVTOSR(LinkedList l, char[] c) throws ParseException {
        super(l.get(0), null, ((l.size() == 3) ? l.get(2) : l.get(1)));
        assegnaByte(l, c);
        comment.add("Instruction MOVTOSR: SORG -> Status Register");
    }
    protected LinkedList getComment(){
		return comment;
	}

	private void assegnaByte(LinkedList l, char[] c) throws ParseException {
		BYTE[0] = "001";				// 31-29 Classe
		BYTE[1] = "00010";				// 28-24 Tipo
		BYTE[2] = " -  -  -  -  -";				// 23-16 K
		BYTE[3] = " - -";						// 15-14 I/O
		BYTE[4] = " 1 0";						// 13-12 s
		BYTE[5] = riconosciModoSrc(c);					// 11-9  Modo S
		BYTE[6] = riconosciSrc();					// 8-6  Srg
		BYTE[7] = " -  -  -";			// 5-3  Modo D
		BYTE[8] = " -  -  -";				// 2-0  Dst
	}
	
	protected LinkedList getMO(){
		if(BYTE[5].equals("000"))
			mop.add("RX -> SR");
		else if(BYTE[5].equals("001"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> SR");
			} 
		else
		{
			if(BYTE[5].equals("010"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> MAR");
			}
			else if(BYTE[5].equals("011"))
				mop.add("RX -> MAR");
			else if(BYTE[5].equals("100"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("RX -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
			}
			else if(BYTE[5].equals("101"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> TEMP1");
				mop.add("PC -> TEMP2");
				mop.add("PC+4 -> PC");
				mop.add("ALU_OUT[ADD] -> MAR");
			}
			else if(BYTE[5].equals("110"))
			{
				mop.add("RX -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[SUB] -> RX");
				mop.add("RX -> MAR");
			}
			else if(BYTE[5].equals("111"))
			{
				mop.add("RX -> MAR");
				mop.add("RX -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[ADD] -> RX");
			}
			mop.add("(MAR) -> MDR");
			mop.add("MDR -> SR");
		}
		return mop;
	}
}