package microOpGen;
import java.util.*;

/**
 * Simulazione dell'operazione di interrupt
 * @author Roberto Palmieri, Giulio Vennari
 */
public class INTERRUPT extends Istruzione {
    /**
     * Costruttore della classe
     * @param c Array di caratteri rappresentanti l'intera istruzione
     * @param l LinkedList contenente le stringhe dell'istruzione divisa come "Operatore -> Sorg -> Dest"
     */
    protected INTERRUPT(LinkedList l, char[] c) throws ParseException {
        super(l.get(0), null, null);
        assegnaByte(l, c);
        comment.add("Simulation of interrupt");
    }
    protected LinkedList getComment(){
		return comment;
	}

	private void assegnaByte(LinkedList l, char[] c) throws ParseException {
		BYTE[0] = "- -";				// 31-29 Classe
		BYTE[1] = "- - -";				// 28-24 Type
		BYTE[2] = " - - - - - - - -";			// 23-16 K
		BYTE[3] = " - -";					// 15-14 I/O
		BYTE[4] = " - -";					// 13-12 s
		BYTE[5] = " - - -";				// 11-9  Modo S
		BYTE[6] = " - - -";				// 8-6  Srg
		BYTE[7] = " - - -";				// 5-3  Modo D
		BYTE[8] = " - - -";				// 2-0  Dst
	}
	
	protected LinkedList getMO(){
		LinkedList mop_int = new LinkedList ();
		mop_int.add("COMPLETE OF DEVICE CONTROLLER");
		mop_int.add("SR[INTERRUPT ENABLE] = 0 ; R7 -> TEMP1");
		mop_int.add("4 -> ALU ; ALU_OUT[SUB] -> R7");
		mop_int.add("R7 -> MAR");
		mop_int.add("PC -> MDR");
		mop_int.add("MDR -> [MAR]");
		mop_int.add("R7 -> TEMP1");
		mop_int.add("4 -> ALU ; ALU_OUT[SUB] -> R7");
		mop_int.add("SR -> MDR");
		mop_int.add("R7 -> MAR");
		mop_int.add("MDR -> (MAR)");
		mop_int.add("IACK IN");
		mop_int.add("IACK IN ; IVN -> I/ODR");
		//mop_int.add("IVN -> I/ODR");
		mop_int.add("I/ODR -> TEMP2");
		mop_int.add("SHIFTER_OUT[SX,2] -> MAR");
		mop_int.add("[MAR] -> MDR");
		mop_int.add("MDR -> PC");
		return mop_int;
	}
}