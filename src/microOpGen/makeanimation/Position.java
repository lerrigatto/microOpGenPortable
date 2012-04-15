package microOpGen.makeanimation;
/*
 * Classe per la gestione delle posizioni delle linee dell'animazione
 * @author Roberto Palmieri, Giulio Vennari
 */

public class Position {
	int w;
	int h;
	int x;
	int y;
	String color;
	String name;
    String _type;
	
	public Position (String lineName,int width, int height, int X, int Y, String lineColor,String type){
		w=width;
		h=height;
		x=X;
		y=Y;
		color=lineColor;
		name=lineName;
        _type=type;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getW(){
		return w;
	}
	
	public int getH(){
		return h;
	}
	
	public String getColor(){
		return color;
	}
	
	public String getName(){
		return name;
	}
        
    public String getType(){
		return _type;
	}
        
    public String getCode() throws NullPointerException{
        String code="";
        try {
	        if ((_type.equalsIgnoreCase("register")) || (_type.equalsIgnoreCase("bus"))){
	            code="("+x+","+y+","+w+","+h+","+color+",false)";
	            return code;
	        }
	        else if (_type.equalsIgnoreCase("line")){
	            if (h==1){
	                code ="("+x+","+y+","+(w+x)+","+y+","+color+")";
	                return code;
	            }
	            else {
	                code ="("+x+","+(h+y)+","+x+","+y+","+color+")";
	                return code;
	            }
	        }
        } catch (NullPointerException e) {
        	System.err.println("Punto non definito nel file XML");
          	e.printStackTrace(System.err);  
        	}
        return null;
    }
    
    public int hashCode(){
		String str = name.toLowerCase();
		int ris = 0;
		for(int i=0;i<str.length();i++){
			ris += str.charAt(i);
		}
		return ris % 100;
	}
	
	public Object clone(){
		return new Position (new String(name),w,h,x,y,new String(color),new String(_type));
	}
}