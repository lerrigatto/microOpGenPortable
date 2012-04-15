package microOpGen.gui;

import microOpGen.makeanimation.generateAnimationMem;
import jLeoPlayer.*;
//import jLeoPlayer.JLeoWebApplication;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.util.*;
import java.util.TreeSet;
import microOpGen.*;
import microOpGen.makeanimation.*;
import microOpGen.DupFile;



/** La finestra principale del programma. 
 * @author Roberto Palmieri, Giulio Vennari
 */
public class FramePrincipale extends javax.swing.JFrame {
 
    
    private String istruzione;
    private Font f;
    //Lista che precarica le microOperazioni mappate sul file Xml
    private static LinkedList[] array_mop_prefetch;
    private static LinkedList[] array_point;
    public static String dfl_temp = System.getProperty("java.io.tmpdir");

    private File pd32Fld = new File("./microOpGen/Leoweb");
    private File leofolder = new File(dfl_temp);
    private File pd32File = new File(pd32Fld,"pd32.png");
    private File pd32FileOut = new File(leofolder,"pd32.png");
    private File aniFile = new File(leofolder,"animation.lwb");
    
    /** Creates new form PrincipaleFrame */
    public FramePrincipale() throws Exception 
    {
    	DupFile.copia(pd32File,pd32FileOut);
    	first_time = true;
    	last_click = null;
    	last_pos = -1;
        initComponents();
        //Riga aggiunta per l'ottimizzazione
        array_mop_prefetch = prefetchAnimation.loadAnimation();
        array_point = prefetchPoint.loadPoint();
        for(int pos=0;pos<array_mop_prefetch.length;pos++){
        	System.out.println("Posizione: "+pos+"; Lung. lista: "+array_mop_prefetch[pos].size());
        }
        System.out.println("POINTTTTTTTTTTTTTTTT!!!!!!!!!!!!!!!!!!!!");
        for(int pos=0;pos<array_point.length;pos++){
        	System.out.println("Posizione: "+pos+"; Lung. lista: "+array_point[pos].size());
        }
        comportamento();
    }
    
    /** inizializzazione delle variabili d'istanza
    e posizionamento degli oggetti nel frame */
    private void initComponents() 
    {

		
		this.f = new Font("verdana", 1, 12);

	
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        memoriaF = new javax.swing.JButton(); 
        jPanel13 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        classeF = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        tipoF = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        kF = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        ioF = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        sF = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        modoSF = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        sorgF = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        modoDF = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        destF = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        msgalert = new javax.swing.JTextField("Parametro non valido!");
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        operazioniF = new javax.swing.JTextArea(); //scrittura micro op
        commenti = new javax.swing.JTextArea(); //scrittura commenti
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
 
        jLabel2 = new javax.swing.JLabel();
       
        generaB = new javax.swing.JButton();
        jMenuBaRY = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        chiudiMI = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        suOpGenMI = new javax.swing.JMenuItem();
		
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("MicroOpGen v. 2");
        addWindowListener(new java.awt.event.WindowAdapter() 
        {
            public void windowClosing(java.awt.event.WindowEvent evt) 
            {
            	delFile();
               	System.exit(0);
            }
        });

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 10, 10, 10)));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel5.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(0, 10, 0, 0)));
        jScrollPane1.setBorder(new javax.swing.border.TitledBorder("Leo Web"));
 		
 		memoriaF.setText("Avvia animazione");
 		memoriaF.setFont(f);
 		
        jScrollPane1.setViewportView(memoriaF);
        jPanel5.add(jScrollPane1, java.awt.BorderLayout.NORTH);
        jPanel13.setLayout(new java.awt.BorderLayout());
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel6.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(40, 1, 1, 1)));
        jPanel7.setBorder(new javax.swing.border.TitledBorder("Classe"));
        classeF.setColumns(3);
        classeF.setEditable(false);
        jPanel7.add(classeF);

        jPanel11.add(jPanel7);

        jPanel8.setBorder(new javax.swing.border.TitledBorder("Tipo"));
        tipoF.setColumns(5);
        tipoF.setEditable(false);
        jPanel8.add(tipoF);

        jPanel11.add(jPanel8);

        jPanel9.setBorder(new javax.swing.border.TitledBorder("K"));
        kF.setColumns(8);
        kF.setEditable(false);
        jPanel9.add(kF);

        jPanel11.add(jPanel9);

        jPanel10.setBorder(new javax.swing.border.TitledBorder("I/O"));
        ioF.setColumns(2);
        ioF.setEditable(false);
        jPanel10.add(ioF);

        jPanel11.add(jPanel10);

        jPanel6.add(jPanel11, java.awt.BorderLayout.NORTH);

        jPanel14.setBorder(new javax.swing.border.TitledBorder("S"));
        sF.setColumns(2);
        sF.setEditable(false);
        jPanel14.add(sF);

        jPanel12.add(jPanel14);

        jPanel15.setBorder(new javax.swing.border.TitledBorder("Modo S"));
        modoSF.setColumns(3);
        modoSF.setEditable(false);
        jPanel15.add(modoSF);

        jPanel12.add(jPanel15);

        jPanel16.setBorder(new javax.swing.border.TitledBorder("Sorg"));
        sorgF.setColumns(3);
        sorgF.setEditable(false);
        jPanel16.add(sorgF);

        jPanel12.add(jPanel16);

        jPanel17.setBorder(new javax.swing.border.TitledBorder("Modo D"));
        modoDF.setColumns(3);
        modoDF.setEditable(false);
        jPanel17.add(modoDF);

        jPanel12.add(jPanel17);

        jPanel18.setBorder(new javax.swing.border.TitledBorder("Dest"));
        destF.setColumns(3);
        destF.setEditable(false);
        jPanel18.add(destF);

        jPanel12.add(jPanel18);

        jPanel6.add(jPanel12, java.awt.BorderLayout.SOUTH);

        jPanel13.add(jPanel6, java.awt.BorderLayout.NORTH);

        jPanel5.add(jPanel13, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel5, java.awt.BorderLayout.EAST);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jPanel2.add(jPanel4, java.awt.BorderLayout.NORTH);

        jScrollPane2.setBorder(new javax.swing.border.TitledBorder("micro operazioni"));
        operazioniF.setColumns(20);
        operazioniF.setEditable(false);
        operazioniF.setTabSize(4);
        operazioniF.setBorder(new javax.swing.border.EtchedBorder());
        operazioniF.setText("");
        jScrollPane2.setViewportView(operazioniF);
        jScrollPane3.setBorder(new javax.swing.border.TitledBorder("Descrizione istruzione"));
        //jScrollPane3.setSize(300,50);
        commenti.setColumns(18);
        commenti.setEditable(false);
        commenti.setTabSize(4);
        commenti.setLineWrap(true);
        commenti.setRows(2);
        commenti.setBorder(new javax.swing.border.EtchedBorder());
        commenti.setText("");
        jScrollPane3.setViewportView(commenti);
        jScrollPane3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jPanel5.add(jScrollPane3, java.awt.BorderLayout.AFTER_LAST_LINE);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel3.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 10, 1)));
        jPanel1.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(10, 5, 5, 5)));
        		
        
        jcb = new JComboBox();
		TreeSet  istruzioni = new TreeSet ();
		istruzioni.add("HALT");
		istruzioni.add("NOP");
		istruzioni.add("RESET");
		istruzioni.add("MOV");
		istruzioni.add("MOVFRSR");
		istruzioni.add("MOVTOSR");
		istruzioni.add("MVL");
		istruzioni.add("ADD");
		istruzioni.add("ADC");
		istruzioni.add("CMP");
		istruzioni.add("NEG");
		istruzioni.add("SUB");
		istruzioni.add("SBB");
		istruzioni.add("AND");
		istruzioni.add("OR");
		istruzioni.add("XOR");
		istruzioni.add("NOT");
		istruzioni.add("ASL");
		istruzioni.add("ASR");
		istruzioni.add("LSL");
		istruzioni.add("LSR");
		istruzioni.add("RCL");
		istruzioni.add("RCR");
		istruzioni.add("ROL");
		istruzioni.add("ROR");
		istruzioni.add("CLR");
		istruzioni.add("SET");
		istruzioni.add("JMP");
		istruzioni.add("JSR");
		istruzioni.add("RET");
		istruzioni.add("RTI");
		istruzioni.add("J");
		istruzioni.add("JN");
		istruzioni.add("IN");
		istruzioni.add("OUT");
		istruzioni.add("START");
		istruzioni.add("CLEAR");
		istruzioni.add("JR");
		istruzioni.add("JNR");
		istruzioni.add("SETIM");
		istruzioni.add("CLRIM");
		istruzioni.add("JIM");
		istruzioni.add("JNIM");
		istruzioni.add("PUSH");
		istruzioni.add("POP");
		istruzioni.add("PUSHSR");
		istruzioni.add("POPSR");
		istruzioni.add("INTERRUPT");
		jcb.setFont(f);
		Iterator it = istruzioni.iterator();
		while(it.hasNext())
			jcb.addItem(it.next());
		jPanel1.add(jcb); // menu a tendina
		
		primoM = new JComboBox();
		primoM.setFont(f);
		secondoM = new JComboBox();
		secondoM.setFont(f);
		jPanel1.add(primoM);
        jLabel2.setText(" , ");
        jPanel1.add(jLabel2);
        jPanel1.add(secondoM);

        generaB.setText("Genera");
        jPanel1.add(generaB);

        jPanel3.add(jPanel1, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.NORTH);

        jMenu1.setText("File");
        chiudiMI.setText("Chiudi");
        jMenu1.add(chiudiMI);

        jMenuBaRY.add(jMenu1);

        jMenu2.setText("Info");
        suOpGenMI.setText("su microGen");
        jMenu2.add(suOpGenMI);

        jMenuBaRY.add(jMenu2);

        setJMenuBar(jMenuBaRY);
        memoriaF.setEnabled(false);
        

        pack();
    }
	
	/** metodo richiamato dall'action performed in caso Istr senza parametri */
	private void rimuoviTutti()
	{
		jPanel1.remove(primoM);
		jPanel1.remove(secondoM);
		jPanel1.remove(jLabel2);//elimina l'etichetta con la virgola
		Main.show();
			
	}
        /** metodo richiamato per ripristinare tutte le caselle di testo nella finestra */
	private void ripristinaTutti() //ripristino a 2 parametri
	{
		jPanel1.remove(generaB);
		jPanel1.remove(primoM);
		jPanel1.add(primoM);
		jPanel1.add(jLabel2);
		jPanel1.add(secondoM);
		jPanel1.add(generaB);
		Main.show();
	}
	/** metodo richiamato dall'action performed in caso di jump */
	private void rimuoviSecondo()
	{
		jPanel1.remove(secondoM);
		jPanel1.remove(jLabel2);
		Main.show();	
	}
	/** metodo richiamato dall'action performed in caso di jump */
	private void rimuoviPrimo(String istr)
	{
		jPanel1.remove(primoM);
		jPanel1.remove(jLabel2);
		Main.show();	
	}

	
	/** questo metodo pulisce le form e va chiamato prima di ogni scrittura */
	protected static void pulisci() {
    //textArea
    operazioniF.setText("");
    //Flag
    classeF.setText("");
    tipoF.setText("");
    kF.setText("");
    ioF.setText("");
    sF.setText("");
    modoSF.setText("");
    sorgF.setText("");
    modoDF.setText("");
   
	}
 
 /** metodo che definisce il comportamento dei pulsanti   */
public void comportamento() throws Exception 
{
    suOpGenMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	String message = "Powered by:Una marea di incapaci";
                JOptionPane.showMessageDialog(null,message,"Info su microOpGen v2 ",1);                    
            }
        });
         
          
 generaB.addActionListener(new ActionListener()
 	{
     public void actionPerformed(ActionEvent e)
     	{
           	commenti.setText("");
           	//creo l'istruzione in una stringa intera
      		try
      		{
      			switch(jPanel1.getComponentCount())
         		{
         			case 2:	istruzione = (String)jcb.getSelectedItem(); break;
         			case 3: istruzione = jcb.getSelectedItem()+" "+primoM.getSelectedItem();break;
         			case 5: istruzione = jcb.getSelectedItem()+" "+primoM.getSelectedItem()+","+secondoM.getSelectedItem(); break;
         			default: throw new Exception();
         		}
         		istruzione = istruzione.toLowerCase();
         		//object tipo linkedlist da dare in pasto a leoweb
         		pulisci();
         		link = crea.creaListaMO(istruzione);
         		memoriaF.setEnabled(true);
         	}catch(Exception ex)
         	{
         		System.out.println("CATTURATA ECCEZIONE!!!\n"+ex.toString());
         		new Exception("Istruzione fallita");
         	}
  		}
 	});
 	
 	
 chiudiMI.addActionListener(new ActionListener () {
     
     public void actionPerformed(ActionEvent e) {
      
         FramePrincipale.this.dispose();
         System.exit(0);
         
     }
     
 });
    //action listener per modificare il numero di parametri in funzione dell'istruzione
   jcb.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ripristinaTutti();
				//recupero istruzione
				Object obj = jcb.getSelectedItem();
				String op = obj.toString().toLowerCase();
				String isj = op.substring(0,1);
				char c = isj.charAt(0);
				
				//primo argomento
					//devo resettare il primo men e rimontarlo da capo
					primoM.removeAllItems();
					String[] p = new String[10];
					p[0] = "RX";//reg
					p[1] = "#LABEL";//immediato
					p[2] = "ADDRESS";//assoluto
					p[3] = "LABEL";//assoluto
					p[4] = "(RX)";//indiretto con reg
					p[5] = "LABEL(RX)";//spiazzamento
					p[6] = "LABEL(PC)";//relativo
					p[7] = "-(RX)";//pre decremento
					p[8] = "(RX)+";//post incremento
					p[9] = "DEVICE";//I/O
					String[] q = new String[10];
					q[0] = "RY";//reg
					q[1] = "#LABEL";//immediato
					q[2] = "ADDRESS";//assoluto
					q[3] = "LABEL";//assoluto
					q[4] = "(RY)";//indiretto con reg
					q[5] = "LABEL(RY)";//spiazzamento
					q[6] = "LABEL(PC)";//relativo
					q[7] = "-(RY)";//pre decremento
					q[8] = "(RY)+";//post incremento
					q[9] = "DEVICE";//I/O
					
					
					//Istr con zero paramentri
					if((c == 'h') || op.equals("nop") || op.equals("reset") || op.equals("ret") || op.equals("rti") || op.equals("pushsr") || op.equals("popsr") || op.equals("interrupt"))
						rimuoviTutti();
					//Istr con 1 parametro
					else if(c == 'j' && !op.equals("jnr") && !op.equals("jr") && !op.equals("jim") && !op.equals("jnim"))
						if(op.equals("jmp") || op.equals("jsr")){
							rimuoviSecondo();
							primoM.addItem(p[2]);
							primoM.addItem(p[3]);
							primoM.addItem(p[4]);
							primoM.addItem(p[5]);
							primoM.addItem(p[6]);
							primoM.addItem(p[7]);
							primoM.addItem(p[8]);
						}
						else{//jc oppure jnc con c = flag
							secondoM.removeAllItems();
							primoM.addItem("C");
							primoM.addItem("N");
							primoM.addItem("Z");
							primoM.addItem("V");
							primoM.addItem("P");
							primoM.addItem("I");
							secondoM.addItem(q[2]);
							secondoM.addItem(q[3]);
							secondoM.addItem(q[4]);
							secondoM.addItem(q[5]);
							secondoM.addItem(q[6]);
							secondoM.addItem(q[7]);
							secondoM.addItem(q[8]);
						}
					else if(op.equals("clr") || op.equals("set")){
							rimuoviSecondo();
							primoM.addItem("C");
							primoM.addItem("N");
							primoM.addItem("Z");
							primoM.addItem("V");
							primoM.addItem("P");
							primoM.addItem("I");
						}
					else if(op.equals("start") || op.equals("clear") || op.equals("setim") || op.equals("clrim")){
							rimuoviSecondo();
							primoM.addItem(p[0]);
							primoM.addItem(p[9]);
						}
					else if(op.equals("movfrsr") || op.equals("pop")){
							rimuoviSecondo();
							primoM.addItem(p[0]);
							primoM.addItem(p[2]);
							primoM.addItem(p[3]);
							primoM.addItem(p[4]);
							primoM.addItem(p[5]);
							primoM.addItem(p[6]);
							primoM.addItem(p[7]);
							primoM.addItem(p[8]);
						}
					else if(op.equals("movtosr") || op.equals("push")){
							rimuoviSecondo();
							primoM.addItem(p[0]);
							primoM.addItem(p[1]);
							primoM.addItem(p[2]);
							primoM.addItem(p[3]);
							primoM.addItem(p[4]);
							primoM.addItem(p[5]);
							primoM.addItem(p[6]);
							primoM.addItem(p[7]);
							primoM.addItem(p[8]);
						}
					else if(c == 'm'){//Istr 2 parametri
							//mov , mvl
							secondoM.removeAllItems();
							primoM.addItem(p[0]);
							primoM.addItem(p[1]);
							primoM.addItem(p[2]);
							primoM.addItem(p[3]);
							primoM.addItem(p[4]);
							primoM.addItem(p[5]);
							primoM.addItem(p[6]);
							primoM.addItem(p[7]);
							primoM.addItem(p[8]);
							secondoM.addItem(q[0]);
							secondoM.addItem(q[2]);
							secondoM.addItem(q[3]);
							secondoM.addItem(q[4]);
							secondoM.addItem(q[5]);
							secondoM.addItem(q[6]);
							secondoM.addItem(q[7]);
							secondoM.addItem(q[8]);
						}
					else if(op.equals("in")){
							//in
							secondoM.removeAllItems();
							primoM.addItem(p[0]);
							primoM.addItem(p[9]);
							secondoM.addItem(q[0]);
							secondoM.addItem(q[2]);
							secondoM.addItem(q[3]);
							secondoM.addItem(q[4]);
							secondoM.addItem(q[5]);
							secondoM.addItem(q[6]);
							secondoM.addItem(q[7]);
							secondoM.addItem(q[8]);
						}
					else if(op.equals("out")){
							//out
							secondoM.removeAllItems();
							primoM.addItem(p[0]);
							primoM.addItem(p[1]);
							primoM.addItem(p[2]);
							primoM.addItem(p[3]);
							primoM.addItem(p[4]);
							primoM.addItem(p[5]);
							primoM.addItem(p[6]);
							primoM.addItem(p[7]);
							primoM.addItem(p[8]);
							secondoM.addItem(q[0]);
							secondoM.addItem(q[9]);
						}
					else if(c == 'j'){
                                            //a questo punto pu essere solo jr, jnr, jim, jnim
							secondoM.removeAllItems();
							primoM.addItem(p[0]);
							primoM.addItem(p[9]);
							secondoM.addItem(q[2]);
							secondoM.addItem(q[3]);
							secondoM.addItem(q[4]);
							secondoM.addItem(q[5]);
							secondoM.addItem(q[6]);
							secondoM.addItem(q[7]);
							secondoM.addItem(q[8]);
						}
					else if((c == 'l') || (c == 'r') || (op.indexOf("as") != -1) ){
							//shift, rotate
							secondoM.removeAllItems();
							primoM.addItem("K");
							secondoM.addItem(q[0]);
						}
					else{//tutto il resto delle istruzioni
							secondoM.removeAllItems();
							primoM.addItem(p[0]);
							primoM.addItem(p[1]);
							primoM.addItem(p[2]);
							primoM.addItem(p[3]);
							primoM.addItem(p[4]);
							primoM.addItem(p[5]);
							primoM.addItem(p[6]);
							primoM.addItem(p[7]);
							primoM.addItem(p[8]);
							secondoM.addItem(q[0]);
						}
				Main.show();
			}
		}); 

	//metodo per l'avvio dell'animazione
	memoriaF.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e) 
		{
			try
				{
					//Runtime.getRuntime().freeMemory();
					pulisci();
					//XML_Data_Import in = new XML_Data_Import ("microOpGen\\points.xml");
					System.out.println("Script Generating...");
					if(!link.isEmpty())
					{
						//fram.repaint();
						fram.setVisible(true);
						LinkedList  link2 = cvtLink();
						generateAnimationMem anime = new generateAnimationMem(link2,array_mop_prefetch,array_point);
						//generateAnimation anime = new generateAnimation(link2);
					
						System.out.println("Script Generated...");
	      				System.out.println("Running Leoweb...");
	        		 	
	        		 	//JLeoWebApplication.main("microOpGen/LeoWeb/animation.lwb");
	        		 	//Process p  = Runtime.getRuntime().exec("java -jar microOpGen/LeoWeb/LeoPlayer.jar microOpGen/LeoWeb/animation.lwb");
						String x = dfl_temp + "/animation.lwb";
						File script = new File(x);
						JLeoFrame aux = new JLeoFrame(script);
						
						System.out.println(" aux.isActive() " + aux.isActive());

						aux.setVisible(true);
						System.out.println(" aux.isActive()[after setVisible] " + aux.isActive());
	        		 	memoriaF.setEnabled(false);
	        		 	fram.setVisible(false);
					}
					else
					{
						AnimationException an = new AnimationException("Istruzione non precedentemente generata");
						memoriaF.setEnabled(false);
						fram.setVisible(false);
					}
				}
				catch(Exception ev)
				{
					String aux = "Eccezione catturata\n";
					for(int z=0;z<ev.getStackTrace().length;z++)
						aux += ev.getStackTrace()[z].toString()+"\n";
					JOptionPane.showMessageDialog(null,aux,"MicroOpGen--LeoWeb",0);
					memoriaF.setEnabled(false);
					fram.setVisible(false);
				}
			
		}	
	
	});
}


private static void removeNumber(JComboBox j){	
	for(int i=0; i<8; i++)
		j.removeItemAt(0);
}

/** metodo usato dalla classe crea per scrivere nella text area le operazioni */
public static void scriviOperazioni(String s)
{
	operazioniF.append(s);
	operazioniF.append("\n");
	
}

/** metodo usato dalla classe crea per scrivere nella text area i commenti */
public static void scriviCommenti(String s)
{
	commenti.append(s);
	//commenti.append("\n");
	
}
/** metodo usato dalla classe crea per scrivere nelle label i bit di micro codice */
public static void scriviBit(String[] bit)
{
	
	classeF.setText(bit[0]);
	tipoF.setText(bit[1]);
	kF.setText(bit[2]);
	ioF.setText(bit[3]);
	sF.setText(bit[4]);
	modoSF.setText(bit[5]);
	sorgF.setText(bit[6]);
	modoDF.setText(bit[7]);
	destF.setText(bit[8]);
	
	Main.show();
}


/** visualizza il messaggio di parametro errato */
public static void alertOn()
{
	operazioniF.append("Parametro errato!");
	Main.show();
}
/** calcella il messaggio di parametro errato */
public static void alertOff()
{
	jPanel4.remove(msgalert);
}
public void setVisible(boolean b)
{
	super.setVisible(b);
	if(first_time){
		jcb.setSelectedItem("RTI");
		first_time = false;
	}
}

private LinkedList  cvtLink()
{
	LinkedList  result = new LinkedList ();
	for(int i = 0; i < link.size(); i++)
	{
		String s = (String)link.get(i);
		s = s.replaceAll("RX", "R0");
		s = s.replaceAll("RY", "R1");
		for(int j=0;j<s.length();j++)
			if(s.charAt(j) == '(')
				s = s.substring(0,j) + "[" + s.substring(j+1,s.length());
		for(int j=0;j<s.length();j++)
			if(s.charAt(j) == ')')
				s = s.substring(0,j) + "]" + s.substring(j+1,s.length());
		//System.out.println(s);
		result.add(s);
	}
	return result;
}

private void delFile(){
	pd32FileOut.delete();
	aniFile.delete();
	//javax.swing.JOptionPane.showMessageDialog(null,"File cancellati","",0,null);
	
}
 /** variabili d'istanza */
 private javax.swing.JMenuItem chiudiMI;
    private static javax.swing.JTextField classeF;
    protected static javax.swing.JTextField msgalert;
    private static javax.swing.JTextField destF;
    private static javax.swing.JButton generaB;
    private static javax.swing.JTextField ioF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBaRY;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private static javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private static javax.swing.JTextField kF;
    private static javax.swing.JButton memoriaF;
    private static javax.swing.JTextField modoDF;
    private static javax.swing.JTextField modoSF;
    private static javax.swing.JTextArea operazioniF;
    private static javax.swing.JTextArea commenti;

    private static javax.swing.JTextField sF;
  
    private static javax.swing.JTextField sorgF;
    private static javax.swing.JMenuItem suOpGenMI;
    private static javax.swing.JTextField tipoF;
    private javax.swing.JComboBox jcb; //selezione
    private javax.swing.JComboBox primoM; //selezione 1 arg
    private javax.swing.JComboBox secondoM; //selezione 2 ARG
    private int caselle = 2;
    private java.util.LinkedList link;
    private boolean first_time;
    private String last_click;
    private int last_pos;
   //fine variabili d'istanza
	MiaFrame fram = new MiaFrame(" --- Leo Web Player ---");
}