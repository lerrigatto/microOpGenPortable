package microOpGen;

import java.util.*;
/** Istruzione che fa il parse di ogni istruzione e ne determina la classe di istruzioni di appartenenza 
 * @author Roberto Palmieri, Giulio Vennari
 */
public class IstruzioneFactory {

    /** prende in ingresso la stringa input e ne individua la classe di cui ? istanza */
    protected static Istruzione assegna(String s) throws ParseException {
        s = s.toUpperCase();
        LinkedList l = toParole(s);
        char[] caratteri = toCaratteri(s);
        String oper = (String)(l.get(0));
        
        if(l.size() <= 5 && l.size() >= 1) {  //se nn si rispetta questa condizione l'operazione nn  valida
            if(caratteri[0] == 'A'){
            	if(oper.equals("ADC"))
	            		return new ADC(l,caratteri);
	            else if(oper.equals("ADD"))
	            		return new ADD(l,caratteri);
	            else if(oper.equals("AND"))
	            		return new AND(l,caratteri);
	            else if(oper.equals("ASL"))
	            		return new ASL(l,caratteri);
	            else if(oper.equals("ASR"))
	            		return new ASR(l,caratteri);
	        }
	        else if(caratteri[0] == 'C'){
	        	if(oper.equals("CLEAR"))
	            		return new CLEAR(l,caratteri);
	            else if(oper.equals("CLR"))
	            		return new CLR(l,caratteri);
	            else if(oper.equals("CLRIM"))
	            		return new CLRIM(l,caratteri);
	            else if(oper.equals("CMP"))
	        			return new CMP(l,caratteri);
	        }
	        else if(caratteri[0] == 'H')
	        	return new HALT(l,caratteri);
	        else if(caratteri[0] == 'I'){
	        	if(oper.equals("IN"))
	        		return new IN(l,caratteri);
	        	else if(oper.equals("INTERRUPT"))
	        		return new INTERRUPT(l,caratteri);
	        }
	        else if(caratteri[0] == 'J'){
	        	if(oper.equals("J"))
	            		return new J(l,caratteri);
	            else if(oper.equals("JIM"))
	            		return new JIM(l,caratteri);
	            else if(oper.equals("JMP"))
	            	return new JMP(l,caratteri);
	            else if(oper.equals("JN"))
	        			return new JN(l,caratteri);
	        	if(oper.equals("JNIM"))
	            		return new JNIM(l,caratteri);
	            else if(oper.equals("JNR"))
	            		return new JNR(l,caratteri);
	            else if(oper.equals("JR"))
	            		return new JR(l,caratteri);
	            else if(oper.equals("JSR"))
	        			return new JSR(l,caratteri);
	        }
	        else if(caratteri[0] == 'L'){
	        	if(oper.equals("LSL"))
	            		return new LSL(l,caratteri);
	            else if(oper.equals("LSR"))
	            		return new LSR(l,caratteri);
	        }
	        else if(caratteri[0] == 'M'){
	        	if(oper.equals("MOV"))
	            		return new MOV(l,caratteri);
	            else if(oper.equals("MOVFRSR"))
	            		return new MOVFRSR(l,caratteri);
	            else if(oper.equals("MOVTOSR"))
	            		return new MOVTOSR(l,caratteri);
	            else if(oper.equals("MVL"))
	        			return new MVL(l,caratteri);
	        }
	        else if(caratteri[0] == 'N'){
	        	if(oper.equals("NEG"))
	            		return new NEG(l,caratteri);
	            else if(oper.equals("NOP"))
	            		return new NOP(l,caratteri);
	            else if(oper.equals("NOT"))
	            		return new NOT(l,caratteri);
	        }
            else if(caratteri[0] == 'O'){
	        	if(oper.equals("OR"))
	            		return new OR(l,caratteri);
	            else if(oper.equals("OUT"))
	            		return new OUT(l,caratteri);
	        }
	        else if(caratteri[0] == 'P'){
	        	if(oper.equals("POP"))
	            		return new POP(l,caratteri);
	            else if(oper.equals("POPSR"))
	            		return new POPSR(l,caratteri);
	        	else if(oper.equals("PUSH"))
	            		return new PUSH(l,caratteri);
	            else if(oper.equals("PUSHSR"))
	            		return new PUSHSR(l,caratteri);
	        }
	        else if(caratteri[0] == 'R'){
	        	if(oper.equals("RCL"))
	            		return new RCL(l,caratteri);
	            else if(oper.equals("RCR"))
	            		return new RCR(l,caratteri);
	        	else if(oper.equals("RESET"))
	            		return new RESET(l,caratteri);
	            else if(oper.equals("RET"))
	            		return new RET(l,caratteri);
	            else if(oper.equals("ROL"))
	            		return new ROL(l,caratteri);
	            else if(oper.equals("ROR"))
	            		return new ROR(l,caratteri);
	            else if(oper.equals("RTI"))
	            		return new RTI(l,caratteri);
	        }
	        else if(caratteri[0] == 'S'){
	        	if(oper.equals("SBB"))
	            		return new SBB(l,caratteri);
	            else if(oper.equals("SET"))
	            		return new SET(l,caratteri);
	        	else if(oper.equals("SETIM"))
	            		return new SETIM(l,caratteri);
	            else if(oper.equals("START"))
	            		return new START(l,caratteri);
	            else if(oper.equals("SUB"))
	            		return new SUB(l,caratteri);
	        }
	        else if(caratteri[0] == 'X')
	            	return new XOR(l,caratteri);
	        }
	        else
	        		throw new ParseException("Istruzione non esistente BY IstruzioneFactory!");
	        	
        return null;
    }
          
    /** spacca la stringa in input in una lista contenente tutte le sue parole eliminando i caratteri [,] [ ] [(] [)] [#] [+] [-] */
    private static LinkedList toParole(String s) 
    {
        StringTokenizer st = new StringTokenizer(s, ",+ -()[]#");
        LinkedList  vettore = new LinkedList (); 
        while(st.hasMoreTokens()) 
            vettore.add(st.nextToken());
        return vettore;
    }
    
    /** spacca la stringa in input in un vettore di caratteri contenente ogni suo carattere */
    private static char[] toCaratteri(String s) 
    {        
        char[] caratteri = new char[s.length()];
        for(int j=0; j<caratteri.length; j++)
            caratteri[j] = s.charAt(j);
        return caratteri;
    }
}