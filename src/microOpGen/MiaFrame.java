package microOpGen;
import java.awt.*;
public class MiaFrame extends Frame{
	Font fo;
	Panel lp = new Panel();
	Label l = new Label();
	
	public MiaFrame (String linea){
		super(linea);
		lp.add(l,BorderLayout.CENTER);
		this.add(l);
		this.setSize(400,100);
		this.setBackground(new Color(111,111,255));
		this.setForeground(Color.white);
		this.setLocation(100,200);
		fo = new Font("Times New Roman",fo.ITALIC | fo.BOLD,14);
		this.setFont(fo);
		l.setText("Caricamento animazione in corso....attendere prego");
	}
	public boolean handleEvent(Event e){
		if (e.id == Event.WINDOW_DESTROY)System.exit(0);
		return super.handleEvent(e);
	}
}