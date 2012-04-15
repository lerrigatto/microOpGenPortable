package microOpGen;
import java.util.*;

/**
 * Classe dell'istruzione JN -flag-
 * @author Roberto Palmieri, Giulio Vennari
 */
public class JN extends Istruzione {
    /**
     * Costruttore della classe
     * @param c Array di caratteri rappresentanti l'intera istruzione
     * @param l LinkedList contenente le stringhe dell'istruzione divisa come "Operatore -> Sorg -> Dest"
     */
    private char flag;

    protected JN(LinkedList l, char[] c) throws ParseException {
        super(l.get(0), ((l.size() == 4) ? l.get(3) : l.get(2)), null);
        assegnaByte(l, c);
        flag = c[3];
        comment.add(selFlag());
    }
    private String selFlag() throws ParseException {
    	String s = "Instruction JN"+flag+": IF ";
		switch(flag){
			case 'C': s +="Carry";break;
			case 'N': s +="negative";break;
			case 'Z': s +="zero";break;
			case 'V': s +="overflow";break;
			case 'P': s +="positive";break;
			case 'I': s +="interrupt";break;
			default: throw new ParseException("Flag non ammesso in selFlag");
		}
		s = s + " = 0 THEN DEST -> PC";
		return s;
    }

	protected LinkedList getComment(){
		return comment;
	}

	private void assegnaByte(LinkedList l, char[] c) throws ParseException {
		BYTE[0] = "110";				// 31-29 Classe
		switch(c[3]){
			case 'C': BYTE[1] = "10010";break;
			case 'N': BYTE[1] = "10011";break;
			case 'Z': BYTE[1] = "10100";break;
			case 'V': BYTE[1] = "10101";break;
			case 'P': BYTE[1] = "10110";break;
			case 'I': BYTE[1] = "10111";break;
			default: throw new ParseException("Flag non ammesso in assegna");
		}
		BYTE[2] = " -  -  -  -  -";					// 23-16 K
		BYTE[3] = " - -";						// 15-14 I/O
		BYTE[4] = " 1 0";						// 13-12 s
		BYTE[5] = " -  -  -";					// 11-9  Modo S
		BYTE[6] = " -  -  -";					// 8-6  Srg
		BYTE[7] = riconosciModoDst(c);	// 5-3  Modo D
		BYTE[8] = riconosciDst();		// 2-0  Dst
	}
	
	protected LinkedList getMO(){
		mop.add("IF SR["+flag+"] == 0 THEN");
		if(BYTE[7].equals("010"))
		{
			mop.add("PC -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("MDR -> PC");
		}
		else if(BYTE[7].equals("011"))
		{
			mop.add("RY -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("MDR -> PC");
		}
		else if(BYTE[7].equals("100"))
		{
			mop.add("PC -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("MDR -> TEMP1");
			mop.add("RX -> TEMP2");
			mop.add("ALU_OUT[ADD] -> PC");
		}
		else if(BYTE[7].equals("101"))
		{
			mop.add("PC -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("MDR -> TEMP1");
			mop.add("PC -> TEMP2");
			mop.add("ALU_OUT[ADD] -> PC");
		}
		else if(BYTE[7].equals("110"))
		{
			mop.add("RX -> TEMP1");
			mop.add("S -> ALU ; ALU_OUT[SUB] -> RX ; ALU_OUT[SUB] -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("MDR -> PC");
		}
		else if(BYTE[7].equals("111"))
		{
			mop.add("RX -> MAR");
			mop.add("(MAR) -> MDR");
			mop.add("MDR -> PC");
			mop.add("S -> ALU ; ALU_OUT[ADD] -> RX");
		}
		return mop;
	}
}