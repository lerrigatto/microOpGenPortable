package microOpGen;
import java.util.*;

/**
 * Classe dell'istruzione MVL con tutti i possibili indirizzamenti
 * @author Roberto Palmieri, Giulio Vennari
 */
public class MVL extends Istruzione {
    /**
     * Costruttore della classe
     * @param c Array di caratteri rappresentanti l'intera istruzione
     * @param l LinkedList contenente le stringhe dell'istruzione divisa come "Operatore -> Sorg -> Dest"
     */
    protected MVL(LinkedList l, char[] c) throws ParseException {
        super(l.get(0),
        ((l.size()==5) ? l.get(4) : ((l.size()==4) ? l.get(3) : l.get(2) ) ),
        ((l.size()==5) ? l.get(2) : ((l.size()==4) ? ((c[4]=='L') ? l.get(2) : l.get(1) ) : l.get(1))) );
        assegnaByte(l, c);
        comment.add("Instruction MVL: SORG -> DEST without ext sign");
    }
    protected LinkedList getComment(){
		return comment;
	}
	private void assegnaByte(LinkedList l, char[] c) throws ParseException {
		BYTE[0] = "010";				// 31-29 Classe
		BYTE[1] = "00000";				// 28-24 Type
		BYTE[2] = "--------";			// 23-16 K
		BYTE[3] = "--";					// 15-14 I/O
		BYTE[4] = "S";					// 13-12 s
		BYTE[5] = riconosciModoSrc(c);	// 11-9  Modo S
		BYTE[6] = riconosciSrc();		// 8-6  Srg
		BYTE[7] = riconosciModoDst(c);	// 5-3  Modo D
		BYTE[8] = riconosciDst();		// 2-0  Dst
	}
    /**
     * Restituisce una lista di sringhe contenentile Micro-Operazioni relative
     */
	protected LinkedList getMO(){
		if(BYTE[5].equals("000"))	// Sorg.RX
		{
			if(BYTE[7].equals("000"))
			{
				mop.add("RX -> TEMP2");
				mop.add("TEMP2 -> RY");
			}
			else if(BYTE[7].equals("010"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> MAR");
				mop.add("RX -> MDR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("011"))
			{
				mop.add("RY -> MAR");
				mop.add("RX -> MDR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("100"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("RY -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("RX -> MDR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("101"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> TEMP1");
				mop.add("PC -> TEMP2");
				mop.add("PC+4 -> PC");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("RX -> MDR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("110"))
			{
				mop.add("RY -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[SUB] -> RY ; ALU_OUT[SUB] -> MAR");
				mop.add("RX -> MDR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("111"))
			{
				mop.add("RY -> MAR ; RY -> TEMP1");
				mop.add("RX -> MDR");
				mop.add("MDR -> (MAR)");
				mop.add("S -> ALU ; ALU_OUT[ADD] -> RY");
			}
		}
		else if(BYTE[5].equals("001"))	// Sorg.#LABEL
		{
			if(BYTE[7].equals("000"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> RY");
			}
			else if(BYTE[7].equals("010"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP2");
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> MAR");
				mop.add("SHIFTER_OUT[SHIFT_0] -> MDR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("011"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("RY -> MAR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("100"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP2");
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("SHIFTER_OUT[SHIFT_0] -> MDR");
				mop.add("RY -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("101"))
			{
				mop.add("PC -> I/ODR ; PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP2");
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("SHIFTER_OUT[SHIFT_0] -> MDR");
				mop.add("I/ODR -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("110"))
			{
				mop.add("RY -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[SUB] -> RY ; ALU_OUT[SUB] -> MAR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("111"))
			{
				mop.add("RY -> MAR ; RY -> TEMP1");
				mop.add("MDR -> (MAR)");
				mop.add("S -> ALU ; ALU_OUT[ADD] -> RY");
			}
		}
		else if(BYTE[5].equals("010"))	// Sorg.ADDRESS/LABEL
		{
			if(BYTE[7].equals("000"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> RY");
			}
			else if(BYTE[7].equals("010"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> TEMP2");
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> MAR");
				mop.add("SHIFTER_OUT[SHIFT_0] -> MDR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("011"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("RY -> MAR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("100"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> TEMP2");
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("SHIFTER_OUT[SHIFT_0] -> MDR");
				mop.add("RY -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("101"))
			{
				mop.add("PC -> I/ODR ; PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> TEMP2");
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("SHIFTER_OUT[SHIFT_0] -> MDR");
				mop.add("I/ODR -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("110"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("RY -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[SUB] -> RY ; ALU_OUT[SUB] -> MAR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("111"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("RY -> MAR ; RY -> TEMP1");
				mop.add("MDR -> (MAR)");
				mop.add("S -> ALU ; ALU_OUT[ADD] -> RY");
			}
		}
		else if(BYTE[5].equals("011"))	// Sorg.[RX]
		{
			if(BYTE[7].equals("101"))
			{
				mop.add("PC -> MAR ; PC -> TEMP2");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("RX -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("MDR -> (MAR)");
			}
			else
			{
				mop.add("RX -> MAR");
				mop.add("(MAR) -> MDR");
				if(BYTE[7].equals("000"))
					mop.add("MDR -> RY");
				else if(BYTE[7].equals("010"))
				{
					mop.add("MDR -> TEMP2");
					mop.add("PC -> MAR");
					mop.add("(MAR) -> MDR");
					mop.add("MDR -> MAR");
					mop.add("PC+4 -> PC");
					mop.add("SHIFTER_OUT[SHIFT_0] -> MDR");
					mop.add("MDR -> (MAR)");
				}
				else if(BYTE[7].equals("011"))
				{
					mop.add("RY -> MAR");
					mop.add("MDR -> (MAR)");
				}
				else if(BYTE[7].equals("100"))
				{
					mop.add("MDR -> TEMP2");
					mop.add("PC -> MAR");
					mop.add("(MAR) -> MDR");
					mop.add("PC+4 -> PC");
					mop.add("MDR -> TEMP1");
					mop.add("SHIFTER_OUT[SHIFT_0] -> MDR");
					mop.add("RY -> TEMP2");
					mop.add("ALU_OUT[ADD] -> MAR");
					mop.add("MDR -> (MAR)");
				}
				else if(BYTE[7].equals("110"))
				{
					mop.add("RY -> TEMP1");
					mop.add("S -> ALU ; ALU_OUT[SUB] -> RY ; ALU_OUT[SUB] -> MAR");
					mop.add("MDR -> (MAR)");
				}
				else if(BYTE[7].equals("111"))
				{
					mop.add("RY -> MAR ; RY -> TEMP1");
					mop.add("MDR -> (MAR)");
					mop.add("S -> ALU ; ALU_OUT[ADD] -> RY ; ALU_OUT[ADD] -> MAR");
				}
			}
		}
		else if(BYTE[5].equals("100"))	// Sorg.LABEL[RX]
		{
			if(BYTE[7].equals("000"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("RX -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> RY");
			}
			else if(BYTE[7].equals("010"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("RX -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> TEMP2");
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> MAR");
				mop.add("SHIFTER_OUT[SHIFT_0] -> MDR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("011"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("RX -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("RY -> MAR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("100"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("RX -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> TEMP2");
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("SHIFTER_OUT[SHIFT_0] -> MDR");
				mop.add("RY -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("101"))
			{
				mop.add("PC -> I/ODR ; PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("RX -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> TEMP2");
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("SHIFTER_OUT[SHIFT_0] -> MDR");
				mop.add("I/ODR -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("110"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("RX -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("RY -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[SUB] -> RY ; ALU_OUT[SUB] -> MAR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("111"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("RX -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("RY -> MAR ; RY -> TEMP1");
				mop.add("MDR -> (MAR)");
				mop.add("S -> ALU ; ALU_OUT[ADD] -> RY");
			}
		}
		else if(BYTE[5].equals("101")) // Sorg.LABEL[PC]
		{
			if(BYTE[7].equals("000"))
			{
				mop.add("PC -> MAR ; PC -> TEMP2");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> RY");
			}
			else if(BYTE[7].equals("010"))
			{
				mop.add("PC -> MAR ; PC -> TEMP2");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> TEMP2");
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> MAR");
				mop.add("SHIFTER_OUT[SHIFT_0] -> MDR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("011"))
			{
				mop.add("PC -> MAR ; PC -> TEMP2");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("RY -> MAR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("100"))
			{
				mop.add("PC -> MAR ; PC -> TEMP2");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> I/ODR");
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("RY -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("I/ODR -> MDR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("101"))
			{
				mop.add("PC -> MAR ; PC -> TEMP2");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> I/ODR");
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("I/ODR -> MDR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("110"))
			{
				mop.add("PC -> MAR ; PC -> TEMP2");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("RY -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[SUB] -> RY ; ALU_OUT[SUB] -> MAR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("111"))
			{
				mop.add("PC -> MAR ; PC -> TEMP2");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("RY -> MAR ; RY -> TEMP1");
				mop.add("MDR -> (MAR)");
				mop.add("S -> ALU ; ALU_OUT[ADD] -> RY");
			}
		}
		else if(BYTE[5].equals("110"))	// Sorg.-[RX]
		{
			if(BYTE[7].equals("000"))
			{
				mop.add("RX -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[SUB] -> RX ; ALU_OUT[SUB] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> RY");
			}
			else if(BYTE[7].equals("010"))
			{
				mop.add("RX -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[SUB] -> RX ; ALU_OUT[SUB] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> TEMP2");
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> MAR");
				mop.add("SHIFTER_OUT[SHIFT_0] -> MDR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("011"))
			{
				mop.add("RX -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[SUB] -> RX ; ALU_OUT[SUB] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("RY -> MAR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("100"))
			{
				mop.add("RX -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[SUB] -> RX ; ALU_OUT[SUB] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> I/ODR");
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("RY -> TEMP2");
				mop.add("ALU_OUT[ADD] -> MAR");
				mop.add("I/ODR -> MDR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("101"))
			{
				mop.add("PC -> MAR ; PC -> TEMP2");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("ALU_OUT[ADD] -> I/ODR");
				mop.add("RX -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[SUB] -> RX ; ALU_OUT[SUB] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("I/ODR -> MAR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("110"))
			{
				mop.add("RX -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[SUB] -> RX ; ALU_OUT[SUB] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("RY -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[SUB] -> RY ; ALU_OUT[SUB] -> MAR");
				mop.add("MDR -> (MAR)");
			}
			else if(BYTE[7].equals("111"))
			{
				mop.add("RX -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[SUB] -> RX ; ALU_OUT[SUB] -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("RY -> MAR ; RY -> TEMP1");
				mop.add("MDR -> (MAR)");
				mop.add("S -> ALU ; ALU_OUT[ADD] -> RY");
			}
		}
		else if(BYTE[5].equals("111"))	// Sorg.[RX]+
		{
			if(BYTE[7].equals("000"))
			{
				mop.add("RX -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> RY");
				mop.add("S -> ALU ; ALU_OUT[ADD] -> RX");
			}
			else if(BYTE[7].equals("010"))
			{
				mop.add("RX -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("MDR -> TEMP2");
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> MAR");
				mop.add("SHIFTER_OUT[SHIFT_0] -> MDR");
				mop.add("MDR -> (MAR)");
				mop.add("RX -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[ADD] -> RX");
			}
			else if(BYTE[7].equals("011"))
			{
				mop.add("RX -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("RY -> MDR");
				mop.add("MDR -> (MAR)");
				mop.add("RX -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[ADD] -> RX");
			}
			else if(BYTE[7].equals("100"))
			{
				mop.add("PC -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("RY -> TEMP2");
				mop.add("ALU_OUT[ADD] -> TEMP2");
				mop.add("RX -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("SHIFTER_OUT[SHIFT_0] -> MAR");
				mop.add("MDR -> (MAR)");
				mop.add("RX -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[ADD] -> RX");
			}
			else if(BYTE[7].equals("101"))
			{
				mop.add("PC -> MAR ; PC -> TEMP2");
				mop.add("(MAR) -> MDR");
				mop.add("PC+4 -> PC");
				mop.add("MDR -> TEMP1");
				mop.add("ALU_OUT[ADD] -> TEMP2");
				mop.add("RX -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("SHIFTER_OUT[SHIFT_0] -> MAR");
				mop.add("MDR -> (MAR)");
				mop.add("RX -> TEMP1");
				mop.add("S -> ALU ; ALU_OUT[ADD] -> RX");
			}
			else if(BYTE[7].equals("110"))
			{
				mop.add("RX -> MAR");
				mop.add("(MAR) -> MDR");
				mop.add("RY -> TEMP1");	
				mop.add("S -> ALU ; ALU_OUT[SUB] -> RY ; ALU_OUT[SUB] -> MAR");
				mop.add("MDR -> (MAR)");
				mop.add("S -> ALU ; ALU_OUT[ADD] -> RX");
			}
			else if(BYTE[7].equals("111"))
			{
				mop.add("RX -> MAR ; RX -> TEMP1");
				mop.add("(MAR) -> MDR");
				mop.add("S -> ALU ; ALU_OUT[ADD] -> RX");
				mop.add("RY -> MAR ; RY -> TEMP1");
				mop.add("MDR -> (MAR)");
				mop.add("S -> ALU ; ALU_OUT[ADD] -> RY");
			}
		}
		return mop;
	}
}

