package microOpGen.makeanimation;
/*
 * Classe principale per la generazione dell'animazione.
 * @author Roberto Palmieri, Giulio Vennari
 */

import microOpGen.gui.XML_Data_Import;
import microOpGen.gui.FramePrincipale;
import java.io.*;
import java.util.*;
import org.xml.sax.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class generateAnimationMem {
	
    private File pd32Fld = new File("./microOpGen/Leoweb/");
    private File leofolder = new File(FramePrincipale.dfl_temp);
    private File animationFile = new File(leofolder,"animation.lwb");
    private File pd32File = new File(pd32Fld,"pd32.png");
    private File pd32FileOut = new File(leofolder,"pd32.png");
    public XML_Data_Import in = new XML_Data_Import ("./microOpGen/points.xml");// Tutte le microOperazioni
    private LinkedList  codaRemove = new LinkedList (); // coda memorizza il coodice Leoweb scritto da cancellare
    //private LinkedList codaChangeColor = new LinkedList();
    private LinkedList  textCoda = new LinkedList ();
    private int i=0,j=0,k=0,z=0,y=416;// contatore line e rect text e sfasamento di y nel text
    private Animation animation;
    private Animation animationOr;
    int sfasamento = 10;
    private LinkedList laux = new LinkedList();
    private String cwd = System.getProperty("user.dir");
    
    
    /** Creates a new instance of generateAnimation */
    //Scorre la lista in input, la scarta se  da scartare altrimenti crea l'animazione associata ad ogni elemento e lo scrive in animation.lwb
    
    public generateAnimationMem(LinkedList animationType,LinkedList[] mop_prefetch,LinkedList[] point_pre) throws AnimationException,DOMException,IOException,SAXException,ParserConfigurationException {
		String cwd2 = cwd.substring(2,cwd.length());
        cwd2 += "\\";
        
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
                	//A questo puntoho ma microOperazione da cercare nella linkedList
                	//Cerco la microOperazione Mappata su tutta la lista
                	//Iterator it = mop_prefetch.iterator();
                	//for(int a=0;a<mop_prefetch.size();a++){
                		//while(it.hasNext()){
                			//animationOr = (Animation)mop_prefetch.get(a);
                			//animationOr = (Animation)it.next();
						//Devo duplicare l'oggetto altrimenti non funziona nulla

                		/*
			                	System.out.println("Original----------!");
			                	System.out.println(animationOr.getName());
			                	System.out.println(animationOr.getStep());
			                	System.out.println(animation.getName());
			                	System.out.println(animation.getStep());
			                	animation.setStep(100);
			                	System.out.println("After----------!");
			                	System.out.println(animationOr.getName());
			                	System.out.println(animationOr.getStep());
			                	System.out.println(animation.getName());
			                	System.out.println(animation.getStep());
                		
                		*/
                	for(int r=0;r<mop_prefetch[hashC(str)].size();r++){
                		animationOr = (Animation)mop_prefetch[hashC(str)].get(r);
                		if(animationOr.getName().equalsIgnoreCase(str)){

                			animation = (Animation)animationOr.clone();
                			System.out.println("dopo la equals");
                				System.out.println(animation.getSignal().size());
                				String stringa = (String)(((LinkedList)animation.getSignal()).getFirst());
	                			//if (((String)(animation.getSignal().getFirst())).equalsIgnoreCase("Error")){
	                			if (stringa.equalsIgnoreCase("Error")){
			                		throw new AnimationException("Animazione <"+str+"> non presente nel file XML)");
			                	}
			                	
		                	else {
		                		 System.out.println("adesso scrivo l'istruzione"+animation.getName());
		                		 System.out.println(animation.getLabel(0));
		                		 for(int h=0;h<animation.getSignal().size();h++)
		                		 	System.out.println(animation.getSignal().get(h));
		                		 fileLeoweb.writeBytes(toLeowebCode(animation.getSignal(),str,point_pre));
		                	 	animationType.removeFirst();
		                	 	break;
		                	}
                		}
                			
                	}//Fine while
                	//animation = in.getAnimation(str);
                	//javax.swing.JOptionPane.showMessageDialog(null,animation.toString(),"prova",0,null);
                	
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
    
    private String toLeowebCode(LinkedList list,String animationName, LinkedList[] ins) throws DOMException,IOException,SAXException,ParserConfigurationException{
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

	                //pos = in.getPosition(text);
	                System.out.println(text);
	                pos = getPosition(text,ins);
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
    
    public int hashC(String _name){
		int h = 0, a = 31415, b = 27183;
		for(int i=0;i<_name.length();i++){
			h = ( a*h + _name.charAt(i)) % 150;
			a = a*b % (149);
		}
		return h;
	}
	
	 	public Position getPosition(String lineName,LinkedList[] ins) throws DOMException,IOException,SAXException,ParserConfigurationException {
            Position pos,p;
            
            for(int g=0;g<ins[hashCo(lineName)].size();g++){
            	p = (Position)ins[hashCo(lineName)].get(g);
            	if(p.getName().equalsIgnoreCase(lineName)){
            		return (Position)p.clone();
            	}
            	
            }
            return null;
        }

    public int hashCo(String name1){
		String name = name1.toLowerCase();
		int ris = 0;
		for(int i=0;i<name.length();i++){
			ris += name.charAt(i);
		}
		return ris % 100;
	}

}

class AnimationExceptionMem extends Exception  {
	public AnimationExceptionMem(String msg){
		super(msg);
	}
}
