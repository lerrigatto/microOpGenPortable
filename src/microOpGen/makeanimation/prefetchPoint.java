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

public class prefetchPoint {
	
	
	//Metodo che carica tutte le microOperazioni e le mette in ins
	public static LinkedList[] loadPoint() throws Exception{
		XML_Data_Import in = new XML_Data_Import ("./microOpGen/points.xml");// Tutte le microOperazioni
		LinkedList[] ins;
		ins = in.getAllPosition();
		System.out.println(ins.length);
		return ins;
	}
}