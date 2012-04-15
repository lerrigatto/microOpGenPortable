package microOpGen.makeanimation;
/*
 * Classe principale per la generazione dell'animazione.
 * @author Roberto Palmieri, Giulio Vennari
 */

import microOpGen.gui.XML_Data_Import;
import java.io.*;
import java.util.*;
import org.xml.sax.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class prefetchAnimation {
	
	
	//Metodo che carica tutte le microOperazioni e le mette in ins
	public static LinkedList[] loadAnimation() throws Exception{
		XML_Data_Import in = new XML_Data_Import ("./microOpGen/points_p.xml");// Tutte le microOperazioni
		//LinkedList ins = new LinkedList();
		LinkedList[] ins;
		ins = in.getAllAnimation();
		//animation = in.getAnimation(str);
		//Iterator it = ins.iterator();
		//while(it.hasNext()){
		//	Animation a = (Animation)it.next();
		//	System.out.println(a.getName());
		//}
		System.out.println(ins.length);
			
		return ins;
	}
	
	public static void main(String[] x) throws Exception{
		
		loadAnimation();	
	}
}