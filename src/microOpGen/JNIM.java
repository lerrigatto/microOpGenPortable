package microOpGen;
import java.util.*;

/**
 * Classe dell'istruzione JNIM con tutti i possibili indirizzamenti
 * @author Roberto Palmieri, Giulio Vennari
 */
public class JNIM extends Istruzione {
    /**
     * Costruttore della classe
     * @param c Array di caratteri rappresentanti l'intera istruzione
     * @param l LinkedList contenente le stringhe dell'istruzione divisa come "Operatore -> Sorg -> Dest"
     */
    protected JNIM(LinkedList l, char[] c) throws ParseException {
		super(l.get(0), ((l.size() == 4) ? l.get(3) : l.get(2)), l.get(1));
        assegnaByte(l, c);
        comment.add("Instruction JNIM: IF F/F IM[device] == 0 then go to DEST");
    }

	protected LinkedList getComment(){
		return comment;
	}

	private void assegnaByte(LinkedList l, char[] c) throws ParseException {
		BYTE[0] = "111";				// 31-29 Classe
		BYTE[1] = "01001";				// 28-24 Type
		if(c[5] == 'R'){
			BYTE[2] = " - - - - - "+riconosciSrc();				// 23-16 K
			BYTE[3] = "10";					// 15-14 I/O
		}
		else{
			BYTE[2] = "DEVICE";				// 23-16 K
			BYTE[3] = "01";					// 15-14 I/O
		}
		BYTE[4] = " -  -";
		BYTE[5] = " -  -  -";					// 11-9  Modo S
		BYTE[6] = " -  -  -";					// 8-6  Srg
		BYTE[7] = riconosciModoDst(c);					// 5-3  Modo D
		BYTE[8] = riconosciDst();					// 2-0  Dst
	}
	
	protected LinkedList getMO(){
		if(BYTE[3] == "10"){
			mop.add("RX -> I/OAR");
		}
		else{
			mop.add("IR[DEVICE] -> I/OAR");
		}
			mop.add("IF F/F IM[device] == 0 then");
			
			if(BYTE[7].equals("010"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> PC");
			}else if(BYTE[7].equals("011"))
			{
				mop.add("RY -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> PC");
			}else if(BYTE[7].equals("100"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> TEMP1");
				mop.add("RY -> TEMP2");
				mop.add("ALU_OUT[ADD] -> PC");
			}else if(BYTE[7].equals("101"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> TEMP1");
				mop.add("PC -> TEMP2");
				mop.add("ALU_OUT[ADD] -> PC");
			}else if(BYTE[7].equals("110"))
			{
				mop.add("RY -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[SUB] -> RY ; ALU_OUT[SUB] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> PC");
			}else if(BYTE[7].equals("111"))
			{
				mop.add("RY -> MAR ; RY -> TEMP1");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> PC");
				mop.add("S -> ALU ; ALU_OUT[ADD] -> RY");
			}
		return mop;
	}
}