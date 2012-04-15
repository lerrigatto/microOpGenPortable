package microOpGen;
import java.util.*;

/**
 * Classe dell'istruzione SET -flag-
 * @author Roberto Palmieri, Giulio Vennari
 */
public class SET extends Istruzione {
    /**
     * Costruttore della classe
     * @param c Array di caratteri rappresentanti l'intera istruzione
     * @param l LinkedList contenente le stringhe dell'istruzione divisa come "Operatore -> Sorg -> Dest"
     */
    private char flag;
    protected SET(LinkedList l, char[] c) throws ParseException {
        super(l.get(0), null, null);
        assegnaByte(l, c);
        flag = c[4];
        comment.add(selFlag());
    }
    private String selFlag() throws ParseException {
    	String s = "Instruction SET"+flag+":";
		switch(flag){
			case 'C': s +=" 0 -> Carry";break;
			case 'N': s +=" 0 -> negative";break;
			case 'Z': s +=" 0 -> zero";break;
			case 'V': s +=" 0 -> overflow";break;
			case 'P': s +=" 0 -> positive";break;
			case 'I': s +=" 0 -> interrupt";break;
			default: throw new ParseException("Flag non ammesso");
		}
		s = s + " flag";
		return s;
    }
    protected LinkedList getComment(){
		return comment;
	}
	private void assegnaByte(LinkedList l, char[] c) throws ParseException {
		BYTE[0] = "101";				// 31-29 Classe
		switch(c[4]){
			case 'C': BYTE[1] = "01010";break;
			case 'N': BYTE[1] = "01011";break;
			case 'Z': BYTE[1] = "01100";break;
			case 'V': BYTE[1] = "01101";break;
			case 'P': BYTE[1] = "01110";break;
			case 'I': BYTE[1] = "01111";break;
			default: throw new ParseException("Flag non ammesso");
		}
		BYTE[2] = " -  -  -  -  -";					// 23-16 K
		BYTE[3] = " - -";						// 15-14 I/O
		BYTE[4] = " - -";						// 13-12 s
		BYTE[5] = " -  -  -";					// 11-9  Modo S
		BYTE[6] = " -  -  -";					// 8-6  Srg
		BYTE[7] = " -  -  -";					// 5-3  Modo D
		BYTE[8] = " -  -  -";					// 2-0  Dst
	}
	
	protected LinkedList getMO(){
		String s = "SR[";
		switch(flag)
		{
			case 'C': s += "Carry"; break;
			case 'N': s += "NEGATIVE"; break;
			case 'Z': s += "ZERO";break;
			case 'V': s += "OVERFLOW"; break;
			case 'P': s += "PARITY";break;
			case 'I': s += "INTERRUPT ENABLE";break;
		}
		s += "] = 1";
		mop.add(s);
		return mop;
	}
}