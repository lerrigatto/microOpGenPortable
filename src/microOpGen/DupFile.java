package microOpGen;

import java.io.*;                // Include le funzioni I/O standard di base.


public class DupFile {            // Da qua inizia la classe Jlez6a

 public static void copia(File original, File dest)
 {                                       // Inizio del main()

// Gli input/output stream e la variabile LungFile li dichiaro nel main(),
// in modo che siano accessibili ovunque. Se fossero dichiarati all'interno
// di un blocco { } di un try o del for, all'esterno di tale blocco non
// esisterebbero. Fate attenzione a questo fatto quando decidete dove
// dichiarare variabili/oggetti! Dichiarateli localmente solo se sono
// effettivamente usati esclusivamente all'interno del blocco. Per esempio
// la variabile i usata nel for e' dichiarata (e usabile) solo nel blocco
// del for, perche' effettivamente non viene usata mai fuori da esso.

    FileInputStream MioInFilStr = null;
    FileOutputStream MioOutFilStr = null;
    int LungFile = 0;

// Provo ad aprire il file Jlez6a.java in lettura con FileInputStream.

    try {
      MioInFilStr = new FileInputStream(original);
    }
    catch (IOException e) {
      System.out.println("Errore: " + e + " nella lettura di un file");
      System.exit(0);
    }


// Provo ad aprire/creare Jlez6a.bak in scrittura con FileOutputStream.

    try {
      MioOutFilStr = new FileOutputStream(dest);
    }
    catch (IOException e) {
      System.out.println("Errore: " + e + " nella creazione di un file");
      System.exit(0);
    }

// Se sono arrivato qua senza eccezioni, ho i 2 flussi (stream) attivi.
// Per creare il file di copia bastera' leggere dalla sorgente e scrivere
// nella destinazione tutti i bytes, uno alla volta.

// Per sapere quanti byte posso copiare (cioe' la lunghezza del file) uso
// il metodo int available().

    try {
      LungFile = MioInFilStr.available();   // Mi informo sul num. bytes.
    }
    catch (IOException e) {
      System.out.println("Errore: " + e + " determinando la lungh. di un file");
      System.exit(0);
    }

// Per leggere/scrivere uso FileInputStream.read(), FileOutPutStream.write().
// So quanti bytes devo copiare, grazie ad available().

    try {
      for(int i=0; i<LungFile; i++) {
        MioOutFilStr.write(MioInFilStr.read());   // copio un byte.
      }
    }
    catch (IOException e) {
      System.out.println("Errore: " + e + " nella copia di un file");
      System.exit(0);
    }


// File copiato, posso chiudere i due flussi (stream).

    try {
      MioInFilStr.close();         // Chiudo l'InputStream.
      MioOutFilStr.close();        // Chiudo l'OutputStream.
    }
    catch (IOException e) {
      System.out.println("Errore: " + e + " nella chiusura di un file");
      System.exit(0);
    }

// Scriviamo un messaggio di felicitazioni

     System.out.print("File copiato, " + LungFile + " bytes,");
     System.out.println(" suffisso copia: .bak");
     //javax.swing.JOptionPane.showMessageDialog(null,"file copiato","",0,null);

 }               // Fine del metodo principale Main()


}            // Fine della classe Jlez6a