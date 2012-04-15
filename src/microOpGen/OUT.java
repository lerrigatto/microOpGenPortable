package microOpGen;
import java.util.*;

/**
 * Classe dell'istruzione OUT con tutti i possibili indirizzamenti
 * @author Roberto Palmieri, Giulio Vennari
 */
public class OUT extends Istruzione {
    /**
     * Costruttore della classe
     * @param c Array di caratteri rappresentanti l'intera istruzione
     * @param l LinkedList contenente le stringhe dell'istruzione divisa come "Operatore -> Sorg -> Dest"
     */
    protected OUT(LinkedList l, char[] c) throws ParseException {
        super(l.get(0), ((l.size() == 4) ? l.get(3) : l.get(2)), ((l.size() == 4) ? l.get(2) : l.get(1)));
        assegnaByte(l, c);
        comment.add("Instruction OUT: SORG -> Buffer of DEVICE");
    }

	protected LinkedList getComment(){
		return comment;
	}

	private void assegnaByte(LinkedList l, char[] c) throws ParseException {
		int i;
		BYTE[0] = "111";				// 31-29 Classe
		BYTE[1] = "00000";				// 28-24 Type
//		if(c[7] == 'R'){
		for(i=0;i<c.length;i++)
			if(c[i] == ',')
				break;
		if(c[i+1] == 'R'){
			BYTE[2] = " - - - - - "+riconosciDst();// 23-16 K 
			BYTE[3] = "10";					// 15-14 I/O
		}
		else{
			BYTE[2] = "DEVICE";				// 23-16 K
			BYTE[3] = "01";					// 15-14 I/O
		}
		BYTE[4] = " S ";					// 13-12 s
		BYTE[5] = "---";				// 11-9  Modo S
		BYTE[6] = "---";				// 8-6  Srg
		BYTE[7] = riconosciModoSrc(c);	// 5-3  Modo D
		BYTE[8] = riconosciSrc();		// 2-0  Dst
	}
    /**
     * Restituisce una lista di sringhe contenentile Micro-Operazioni relative
     */
	protected LinkedList getMO(){
					
			if(BYTE[7].equals("000"))
			{
				mop.add("RX -> I/ODR");
			}
			else if(BYTE[7].equals("001"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> I/ODR");
			}else if(BYTE[7].equals("010"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> I/ODR");
			}else if(BYTE[7].equals("011"))
			{
				mop.add("RY -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> I/ODR");
			}else if(BYTE[7].equals("100"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("RX -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> I/ODR");
			}else if(BYTE[7].equals("101")){
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> TEMP1");
				mop.add("PC -> TEMP2");
				mop.add("PC+4 -> PC");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> I/ODR");
			}else if(BYTE[7].equals("110"))
			{
				mop.add("RX -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[SUB] -> RX ; ALU_OUT[SUB] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> I/ODR");
			}else if(BYTE[7].equals("111"))
			{
				mop.add("RX -> MAR ; RX -> TEMP1");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> I/ODR");

				mop.add("S -> ALU ; ALU_OUT[ADD] -> RX");
			}
			if(BYTE[3] == "10"){
				mop.add("RY -> I/OAR");
			}
			else{
				mop.add("IR[DEVICE] -> I/OAR");
			}
			mop.add("I/ODR -> REG");
		return mop;
	}
}
