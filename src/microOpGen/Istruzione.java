package microOpGen;
import java.util.*;
/** Classe astratta genitore di tutte istruzioni 
 * @author Roberto Palmieri, Giulio Vennari
 */
public abstract class Istruzione {
    private Object oper;
    private Object cDest;
    private Object cSorg;
    protected LinkedList  mop;
    protected String[] BYTE = new String[10];
    protected LinkedList  comment;
    
    
    protected Istruzione(Object oper, Object dst, Object src ) {
        this.oper = oper;
        cDest = dst;
        cSorg = src;
        mop = new LinkedList ();
        comment = new LinkedList ();
        mop.add("- - - Fase di FETCH - - -");
		mop.add("PC -> MAR");
		mop.add("(MAR) -> MDR ; PC+4 -> PC");
		mop.add("MDR -> IR");
		//mop.add("PC+4 -> PC");
		mop.add("- - - Fine FETCH - - -");
    }
    
    protected Object getOper() {
        return oper;
    }
    
    protected Object getSrc() {
        return cSorg;
    }
    
    protected Object getDst() {
        return cDest;
    }
    
    public String[] getByte() {
        return BYTE;
    }
    
/** Riconosce il registro sorgente restituendone la configurazione in bit */
    protected String riconosciSrc() throws ParseException {
        if(cSorg.equals("R0")) return "000";
        else if(cSorg.equals("R1")) return "001";
        else if(cSorg.equals("R2")) return "010";
        else if(cSorg.equals("R3")) return "011";
        else if(cSorg.equals("R4")) return "100";
        else if(cSorg.equals("R5")) return "101";
        else if(cSorg.equals("R6")) return "110";
        else if(cSorg.equals("R7")) return "111";
        else if(cSorg.equals("RX")) return "RX";
        else if(cSorg.equals("RY")) return "RY";
        else if(cSorg.equals("PC")) return "PC";
        else if(cSorg.equals("LABEL") || cSorg.equals("ADDRESS")) return "---";
        else throw new ParseException("Sorgente errato");
    }

/** Riconosce il registro destinazione restituendone la configurazione in bit */
    protected String riconosciDst() throws ParseException {
        if(cDest.equals("R0")) return "000";
        else if(cDest.equals("R1")) return "001";
        else if(cDest.equals("R2")) return "010";
        else if(cDest.equals("R3")) return "011";
        else if(cDest.equals("R4")) return "100";
        else if(cDest.equals("R5")) return "101";
        else if(cDest.equals("R6")) return "110";
        else if(cDest.equals("R7")) return "111";
        else if(cDest.equals("RX")) return "RX";
        else if(cDest.equals("RY")) return "RY";
        else if(cDest.equals("PC")) return "PC";
        else if(cDest.equals("LABEL") || cDest.equals("ADDRESS")) return "---";
        else throw new ParseException("Destinazione errato");
    }
    
/** Riconosce il modo sorgente restituendone la configurazione in bit */
    protected String riconosciModoSrc(char[] x) throws ParseException {
        char c = x[((String)oper).length()+1];
        String s = new String(x);
        if(s.indexOf(",") == -1)
	        switch(c){
	        	case 'R': return "000";
	        	case '#': return "001";
	        	case 'A': return "010";
	        	case '(': if(s.indexOf("+") != -1)	return "111";
	        				else return "011";
	        	case 'L':	if(s.indexOf("(") != -1)
	        					if(x[((String)oper).length()+7] == 'R')
	        						return "100";
	        					else
	        						return "101";
	        				else
	        					return "010";
	        	case '-': return "110";
	        }
        else
	        switch(c){
	        	case 'R': return "000";
	        	case '#': return "001";
	        	case 'A': return "010";
	        	case '(': if(x[((String)oper).length()+5] == '+')	return "111";
	        				else return "011";
	        	case 'L': switch(x[((String)oper).length()+6]){
	        					case ',': return "010";
	        					case '(': if(x[((String)oper).length()+7] == 'R')
	        								return "100";
	        							  else
	        							  	return "101";
	        					default: throw new ParseException("Parametro sorgente errato!");
	        				}
	        	case '-': return "110";
	        }
        throw new ParseException("Modo sorgente errato!");
    }
/** Riconosce il modo destinazione restituendone la configurazione in bit */
    protected String riconosciModoDst(char[] x) throws ParseException {
        String temp = new String(x);
        String s = temp.substring(temp.indexOf(",")+1,temp.length());//divido la stringa dalla ',' alla fine
        /*int i = s.indexOf(",");
       	for(i=0;i<x.length && x[i] != ',';i++);//posizionamento sul carattere ','
        if(i == x.length)
        {
        	for(i=0;i<x.length && x[i] != ' ';i++);//posizionamento sul carattere ' '
        }*/
        switch(s.charAt(0)){
        	case 'R': return "000";
        	case '#': return "001";
        	case 'A': return "010";
        	case '(': if(s.indexOf("+") != -1)
        				return "111";
        			  else
        			  	return "011";
        	case 'L': if(s.indexOf("(") != -1)
        				if(s.indexOf("R") != -1)
        					return "100";
        				else
        					return "101";
        			  else
        				return "010";
        	case '-': return "110";
        }
        throw new ParseException("Modo destinazione errato!");
    }
    protected abstract LinkedList getMO();
    //protected abstract LinkedList getComment();
    protected LinkedList getComment(){return null;}
}