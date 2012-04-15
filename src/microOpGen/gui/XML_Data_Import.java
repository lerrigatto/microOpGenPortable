package microOpGen.gui;
/*
 * Classe per l'import dei dati per le animazioni da file .xml
 * @author Roberto Palmieri, Giulio Vennari
 */

import java.io.*;
import java.util.*;
import java.util.regex.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.apache.xml.serialize.*;
import org.xml.sax.*;
import java.util.LinkedList;
import java.util.ArrayList;
import microOpGen.AnimationException;
import microOpGen.makeanimation.Animation;
import microOpGen.makeanimation.Position;

public class XML_Data_Import {
	private static final String XML_ENCODING = "ISO-8859-1";
	private static final String XML_VERSION  = "1.0";
	String _xmlfile;
	
	public XML_Data_Import(String XMLFile){
            _xmlfile=XMLFile;
  	}
 	
 	public Position getPosition(String lineName) throws DOMException,IOException,SAXException,ParserConfigurationException {
            Position pos;
            Node child;
            DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
            FileReader is = new FileReader(_xmlfile);
            Document doc =docBuild.parse(new InputSource(is));
            Element root = doc.getDocumentElement();
            NodeList nl = root.getChildNodes();
            for (int i=0;i<nl.getLength(); i++) {
                    Node n = nl.item(i);
                    if ((n.getNodeType() == Node.ELEMENT_NODE) && (n.getNodeName().equalsIgnoreCase("Points"))) {
                         if (n.hasChildNodes()) {
                            child = n.getChildNodes().item(0);
                            if ( (child.getNodeType() == Node.TEXT_NODE)) {
                              String nodeName = ((Text) child).getData();
                              if (lineName.equalsIgnoreCase(nodeName)){
                                 if (n.hasAttributes()){
                                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                                        int X,Y,W,H=0;
                                        X = Integer.parseInt(((Element) n).getAttribute("X"));
                                        Y = Integer.parseInt(((Element) n).getAttribute("Y"));
                                        W = Integer.parseInt(((Element) n).getAttribute("W"));
                                        H = Integer.parseInt(((Element) n).getAttribute("H"));
                                        String color = ((Element) n).getAttribute("color").toString();
                                        String type = ((Element) n).getAttribute("type").toString();
                                        pos = new Position (lineName,W,H,X,Y,color,type);
                                        return pos;
                                    } 
                                 }
                              }
                            }
                          } 
                        }
                    }
                 return null; 
           }
        
 //Metodo che ricerca nel file xml(sezione animation) la stringa passata in input, una volta trovata scandisce i suoi sotto tag; tutto in maniera sequenziale
        public Animation getAnimation(String animationType) throws DOMException,IOException,SAXException,ParserConfigurationException,AnimationException {
            int step =0;
            ArrayList  label= new ArrayList ();
            String anime="";
            Position pos;
            String animetext="";
            DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
            FileReader is = new FileReader(_xmlfile);
            Document doc =docBuild.parse(new InputSource(is));
            Element root = doc.getDocumentElement();
            NodeList nl = root.getChildNodes();
            for (int i=0;i<nl.getLength(); i++) {
                Node n = nl.item(i);
                 if ((n.getNodeType() == Node.ELEMENT_NODE) && (((Element)n).getTagName().equalsIgnoreCase("Animation"))) {
                    anime = ((Element)n).getAttribute("name");
                    if (anime.equalsIgnoreCase(animationType)){
                        NodeList sublist = n.getChildNodes();
                        for (int j=0;j<sublist.getLength(); j++) {
                            Node ns = sublist.item(j);
                            if ((ns.getNodeType() == Node.ELEMENT_NODE) && (((Element)ns).getTagName().equalsIgnoreCase("step"))) {
                                NodeList sublist1 = ns.getChildNodes();
                                for (int k=0;k<sublist1.getLength(); k++) {
                                	Node ns1 = sublist1.item(k);	
                                	if ((ns1.getNodeType()==Node.ELEMENT_NODE) && (((Element)ns1).getTagName().equalsIgnoreCase("signal"))) {             
                                     	Node child = ns1.getChildNodes().item(0);
                                    	animetext= animetext+((Text) child).getData()+",pausa,";
                                    	step++;
                                    	}
                                    if ((ns1.getNodeType()==Node.ELEMENT_NODE) && (((Element)ns1).getTagName().equalsIgnoreCase("label"))) {             
                                    	Node child = ns1.getChildNodes().item(0);
                                    	label.add(((Text) child).getData().toString());
                               		 	}
                                	}
                                
                            	}
                        	}  
                   		String toReturn = animetext.substring(0,animetext.length()-7);
                   		Animation an = new Animation(animationType,step,label,toList(toReturn));
                    	return an;
                    }
                }
            }
            //LinkedList err = new LinkedList();/*******************************************************/
            //err.add("Error");				/**************************************************************/
            //return err; 				/******************************************************/
            return null;
        }
                               
          
        
        private LinkedList toList (String points){
            int j=0;
            String punto="";
            LinkedList  lista = new LinkedList ();
            while ((j=points.indexOf(","))!=-1){
                punto=points.substring(0,j);
                lista.add(punto);
                points=points.substring(j+1);
                }
            lista.add(points);
            return lista;
            }
            
            
            //Metodo che restituisce tutte le microOperazioni presenti nel file XML e le carica in memoria in un array di LinkedList di oggetti Animation
        public LinkedList[] getAllAnimation() throws DOMException,IOException,SAXException,ParserConfigurationException,AnimationException {
            
            System.out.println("XML_Data_Import inizio");
            int cont = 0;
            //LinkedList ins = new LinkedList();
            LinkedList[] array_mop_prefetch = new LinkedList[150];
            int step =0;
            ArrayList  label= new ArrayList ();
            String anime="";
            Position pos;
            String animetext="";
            DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
            FileReader is = new FileReader(_xmlfile);
            Document doc =docBuild.parse(new InputSource(is));
            Element root = doc.getDocumentElement();
            NodeList nl = root.getChildNodes();
            String animationType = "";
            System.out.println("Prima della for");
            System.out.println(nl.getLength());
            
            for(int i=0;i<array_mop_prefetch.length;i++)
        		array_mop_prefetch[i] = new LinkedList();
            
            for (int i=0;i<nl.getLength(); i++) {
                Node n = nl.item(i);
                 if ((n.getNodeType() == Node.ELEMENT_NODE) && (((Element)n).getTagName().equalsIgnoreCase("Animation"))) {
                    anime = ((Element)n).getAttribute("name");
                    System.out.println(anime);
                    
                    //if (anime.equalsIgnoreCase(animationType)){
                        NodeList sublist = n.getChildNodes();
                        for (int j=0;j<sublist.getLength(); j++) {
                            Node ns = sublist.item(j);
                            if ((ns.getNodeType() == Node.ELEMENT_NODE) && (((Element)ns).getTagName().equalsIgnoreCase("step"))) {
                                NodeList sublist1 = ns.getChildNodes();
                                for (int k=0;k<sublist1.getLength(); k++) {
                                	Node ns1 = sublist1.item(k);	
                                	if ((ns1.getNodeType()==Node.ELEMENT_NODE) && (((Element)ns1).getTagName().equalsIgnoreCase("signal"))) {             
                                     	Node child = ns1.getChildNodes().item(0);
                                    	animetext= animetext+((Text) child).getData()+",pausa,";
                                    	step++;
                                    	}
                                    if ((ns1.getNodeType()==Node.ELEMENT_NODE) && (((Element)ns1).getTagName().equalsIgnoreCase("label"))) {             
                                    	Node child = ns1.getChildNodes().item(0);
                                    	label.add(((Text) child).getData().toString());
                               		 	}
                                	}
                                
                            	}
                        	}  
                   		String toReturn = animetext.substring(0,animetext.length()-7);
                   		Animation an = new Animation(anime,step,label,toList(toReturn));
                   		step = 0;
                   		animetext = "";
                   		label = new ArrayList();
                   		//System.out.println(an);
                //    	return an;
                    //}
                    //da modificare l'inserimento
                    
                	array_mop_prefetch[an.hashCode()].add(an);
                	cont++;
                	//ins.add(an);//inserimento con linkedList
                }
            }
            //LinkedList err = new LinkedList();/*******************************************************/
            //err.add("Error");				/**************************************************************/
            //return err; 				/******************************************************/
            System.out.println(cont);
            return array_mop_prefetch;
        }
        
 	public LinkedList[] getAllPosition() throws DOMException,IOException,SAXException,ParserConfigurationException {
            LinkedList[] ins = new LinkedList[100];
            for(int xd=0;xd<ins.length;xd++)
            	ins[xd] = new LinkedList();
            Position pos;
            Node child;
            DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
            FileReader is = new FileReader(_xmlfile);
            Document doc =docBuild.parse(new InputSource(is));
            Element root = doc.getDocumentElement();
            NodeList nl = root.getChildNodes();
            for (int i=0;i<nl.getLength(); i++) {
                    Node n = nl.item(i);
                    if ((n.getNodeType() == Node.ELEMENT_NODE) && (n.getNodeName().equalsIgnoreCase("Points"))) {
                         if (n.hasChildNodes()) {
                            child = n.getChildNodes().item(0);
                            if ( (child.getNodeType() == Node.TEXT_NODE)) {
                              String nodeName = ((Text) child).getData();
                              //if (lineName.equalsIgnoreCase(nodeName)){
                                 if (n.hasAttributes()){
                                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                                        int X,Y,W,H=0;
                                        X = Integer.parseInt(((Element) n).getAttribute("X"));
                                        Y = Integer.parseInt(((Element) n).getAttribute("Y"));
                                        W = Integer.parseInt(((Element) n).getAttribute("W"));
                                        H = Integer.parseInt(((Element) n).getAttribute("H"));
                                        String color = ((Element) n).getAttribute("color").toString();
                                        String type = ((Element) n).getAttribute("type").toString();
                                        pos = new Position (nodeName,W,H,X,Y,color,type);
                                        //Aggiungo l'oggetto pos alla mia struttura dati
                                        ins[pos.hashCode()].add(pos);
                                    } 
                                 }
                              //}
                            }
                          } 
                        }
                    }
                 return ins; 
           }
           
    public int hashCode(String name){
		int ris = 0;
		for(int i=0;i<name.length();i++){
			ris += name.charAt(i);
		}
		return ris % 100;
	}
  }
