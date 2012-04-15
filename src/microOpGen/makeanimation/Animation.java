package microOpGen.makeanimation;
/*
 * Animation.java
 * @author Roberto Palmieri, Giulio Vennari
 * Created on 25 Novembre 2004, 17.25
 */
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Classe che tiene traccia dell'animazione
 */
public class Animation {
	private String _name;
	private int _step;
	private ArrayList _label;
	private LinkedList _signal;
	
	public Animation (String name,int step,ArrayList label,LinkedList signal){
		_name=name;
		_step=step;
		_label = new ArrayList(step);
		_signal=signal;
		_label=label;
			
	}
	
	public void setStep(int step){
		_step=step;
	}
	
	public int getStep(){
		return _step;
	}
	
	public String getName(){
		return _name;
	}
	
	public LinkedList getSignal(){
		return _signal;
	}
	
	public String getLabel(int step){
		return _label.get(step).toString();
	}
	
	public String toString(){
		String x = "MicroOperazione: "+_name+"\nNumero di step: "+_step+"\n";
		for(int i=0;i<_step;i++){
			x += "Signal:\n";
			for(int j=0;j<_signal.size();j++)
				x += (String)_signal.get(j)+",";
			x += "\nLabel: ";
			for(int j=0;j<_label.size();j++)
				x += (String)_label.get(j)+",";
			x += "\n";
		}
		return x;
	}
	public Object clone(){
		ArrayList label_aux = new ArrayList();
		LinkedList signal_aux = new LinkedList();
		for(int i=0;i<_label.size();i++){
			label_aux.add(new String((String)_label.get(i)));
		}
		for(int i=0;i<_signal.size();i++){
			signal_aux.add(new String((String)_signal.get(i)));
		}
		
		return new Animation(_name,_step,label_aux,signal_aux);
	}
	public int compareTo(Animation n){
		return _name.compareTo(n.getName());
	}
	public boolean equals(Animation a){
		return a.getName().equals(_name);
	}
	public int hashCode(){
		int h = 0, a = 31415, b = 27183;
		for(int i=0;i<_name.length();i++){
			h = ( a*h + _name.charAt(i)) % 150;
			a = a*b % (149);
		}
		return h;
	}
}