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

public class generateAnimation {
    private File leofolder = new File("./microOpGen/Leoweb/");
    private File animationFile = new File(leofolder,"animation.lwb");
    public XML_Data_Import in = new XML_Data_Import ("./microOpGen/points.xml");// Tutte le microOperazioni
    public XML_Data_Import in_f = new XML_Data_Import ("./microOpGen/points_f.xml");// Le microOperazioni del fetch
    private LinkedList  codaRemove = new LinkedList (); // coda memorizza il coodice Leoweb scritto da cancellare
    //private LinkedList codaChangeColor = new LinkedList();
    private LinkedList  textCoda = new LinkedList ();
    private int i=0,j=0,k=0,z=0,y=416;// contatore line e rect text e sfasamento di y nel text
    private Animation animation;
    private Animation animationOr;
    int sfasamento=10;
    
    
    /** Creates a new instance of generateAnimation */
    //Scorre la lista in input, la scarta se   da scartare altrimenti crea l'animazione associata ad ogni elemento e lo scrive in animation.lwb
    public generateAnimation(LinkedList animationType) throws AnimationException,DOMException,IOException,SAXException,ParserConfigurationException {
        DataOutputStream fileLeoweb;
            try {
          
            fileLeoweb = new DataOutputStream(new BufferedOutputStream (new FileOutputStream(animationFile)));
            /* Inizializzazione file per LeoWeb ... standard per tutte le animazioni */
            fileLeoweb.writeBytes("setSize(800,560)\nsetDelay(40)\n");
            fileLeoweb.writeBytes("begin()\nimage1 image(1,0,800,560,pd32.png)\nend()\n");// prima frames standard
            int i = 1;
            while (!(animationType.isEmpty())){
                String str = animationType.getFirst().toString();
                System.out.print(str);
                i++;
                if(!str.equalsIgnoreCase("") && !str.startsWith("//")&& !str.startsWith("- -") && !str.startsWith("IF"))
                {
	               	javax.swing.JOptionPane.showMessageDialog(null,animation.toString(),"prova",0,null);
                	if (animation.getSignal().getFirst().toString().equalsIgnoreCase("Error")){
                		throw new AnimationException("Animazione <"+str+"> non presente nel file XML)");
                	}
                	else {
                		
                		 fileLeoweb.writeBytes(toLeowebCode(animation.getSignal(),str));
                	 	animationType.removeFirst();
                	}
                }
                else
                {
                		System.out.print("   --- not mapped ---");
                		animationType.removeFirst();
                }
                System.out.println("");
            }
            //System.out.println(i);  
            fileLeoweb.flush();
            fileLeoweb.close();
        }
        catch (IOException e){
          	System.err.println("Errore nella scrittura sul file" +e);
          	e.printStackTrace(System.err);     
        }
        catch (AnimationException er){
        	//er.printStackTrace(System.err);
        }
         
    }
    
       
    private String writeAnimationName(String animationName){
        String code="";
        /* if (!textCoda.isEmpty()){
        	code=code+ textCoda.getLast()+".smoothColor(-128,-128,127,8)\n";
        	//code=code+ textCoda.getLast()+".smoothColor(-255,0,0,8)\n";
        	if (textCoda.size()>=2) {
        		code=code+ textCoda.get(textCoda.size()-2)+".smoothColor(-127,0,-127,8)\n";	
        	}
        }*/
        if (!textCoda.isEmpty()) {
        	int sfas=sfasamento+430;
        	code = code + "text"+Integer.toString(k)+" text("+sfas+","+y+",12,255,0,0,\""+animationName+"\")\n";
        }
        else {
        	sfasamento=10;
        	y=416;
        	code = code + "text"+Integer.toString(k)+" text(430,"+y+",12,0,0,160,\""+animationName+"\")\n";
        }
        textCoda.add("text"+Integer.toString(k));
        //sfasamento=sfasamento+10;
        y=y+14;
        k++;
        return code;
    }
    
    private String Remove(){
        String code="";
        while (!codaRemove.isEmpty()){ //line4.setActive(false)
            code = code + codaRemove.getLast()+".setActive(false)\n";
            codaRemove.removeLast();
        }
        return code;
    }
    
    /*private String ChangeColor() {
        String code="";
        while (!codaChangeColor.isEmpty()){ //line4.setActive(false)
            code = code + codaChangeColor.getLast()+".smoothColor(-128,-128,127,8)\n";
            codaRemove.add(codaChangeColor.getLast());
            codaChangeColor.removeLast();
        }
        return code;
    }*/
    
    private String toLeowebCode(LinkedList list,String animationName) throws DOMException,IOException,SAXException,ParserConfigurationException{
        String code="begin()\n";
        String temp="";
        int step=0;
        int k=1;
        Position pos;
        if (!(codaRemove.isEmpty())){
            code= code+ Remove();
        }
        code = code + writeAnimationName(animationName);
        code = code + writeAnimationName(animation.getLabel(step));
        step++;
        while (!(list.isEmpty())){
            String text = list.getFirst().toString();
            if (text.equalsIgnoreCase("pausa")){
                code = code + "end()\nbegin()\n";
                code= code+ Remove();//N:B:
                code = code + writeAnimationName(animation.getLabel(step));
                step++;
                k++;
               }
            else {

	                pos = in.getPosition(text);
	                if (pos.getType().equalsIgnoreCase("line")){
	                    code = code + "line"+Integer.toString(i)+" endpointLine"+pos.getCode()+"\n";
	                    codaRemove.add("line"+Integer.toString(i));
	                    //codaChangeColor.add("line"+Integer.toString(i));
	                    i++;
	                    }
	                else if (pos.getType().equalsIgnoreCase("register")){
	                    code = code + "rect"+Integer.toString(j)+" rectangle"+pos.getCode()+"\n";
	                    codaRemove.add("rect"+Integer.toString(j));
	                    //codaChangeColor.add("rect"+Integer.toString(j));
	                    j++;
	                    }
	                else if (pos.getType().equalsIgnoreCase("bus")){
	                    code = code + "rect"+Integer.toString(j)+" rectangle"+pos.getCode()+"\n";
	                    codaRemove.add("rect"+Integer.toString(j));
	                    //codaChangeColor.add("rect"+Integer.toString(j));
	                    j++;
	                }
                
            }
            list.removeFirst();
        }
        code = code + "end()\n";
        while (!(textCoda.isEmpty())){
        		codaRemove.add(textCoda.getLast());
        		textCoda.removeLast();
        }
        textCoda.clear();
        return code;
    }
}

class AnimationException extends Exception  {
	public AnimationException(String msg){
		super(msg);
	}
}
