package microOpGen;
import java.util.*;

/**
 * Classe dell'istruzione SETIM con tutti i possibili indirizzamenti
 * @author Roberto Palmieri, Giulio Vennari
 */
public class SETIM extends Istruzione {
    /**
     * Costruttore della classe
     * @param c Array di caratteri rappresentanti l'intera istruzione
     * @param l LinkedList contenente le stringhe dell'istruzione divisa come "Operatore -> Sorg -> Dest"
     */
    protected SETIM(LinkedList l, char[] c) throws ParseException {
		super(l.get(0), null, l.get(1));
        assegnaByte(l, c);
        comment.add("Instruction SETIM: F/F IM(device) = 1");
    }
    protected LinkedList getComment(){
		return comment;
	}

	private void assegnaByte(LinkedList l, char[] c) throws ParseException {
		BYTE[0] = "111";				// 31-29 Classe
		BYTE[1] = "00111";				// 28-24 Type
		if(c[6] == 'R'){
			BYTE[2] = " - - - - - "+riconosciSrc();				// 23-16 K
			BYTE[3] = "10";					// 15-14 I/O
		}
		else{
			BYTE[2] = "DEVICE";				// 23-16 K
			BYTE[3] = "01";					// 15-14 I/O
		}
		BYTE[4] = "S";					// 13-12 s
		BYTE[5] = "---";					// 11-9  Modo S
		BYTE[6] = "---";					// 8-6  Srg
		BYTE[7] = "---";	// 5-3  Modo D
		BYTE[8] = "---";		// 2-0  Dst
	}
	
	protected LinkedList getMO(){
		if(BYTE[3] == "10"){
			mop.add("RX -> I/OAR");
			mop.add("SETIM -> I/OCB");
		}
		else{
			mop.add("IR[DEVICE] -> I/OAR");
			mop.add("SETIM -> I/OCB");
		}
		return mop;
	}
}