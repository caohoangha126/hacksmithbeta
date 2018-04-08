package hsmith2018;

import javax.swing.JComponent;
import java.util.*;
import java.util.Timer;
import java.awt.*;
import javax.swing.*;  

/**
 *  Canvas subclass for ArrayList depiction inspired by Ha Cao's original GraphCanvas class 
 *
 *  @author  Sarah Abowitz
 *  @version April 8th 2018
 */

public class ArrListCanvas extends JComponent{
	private ArrayList<Integer> arrList = new ArrayList<Integer>();
	private ArrayList<String> aLAnnotations = new ArrayList<String>();
	boolean addingToAL = false;
	private int access = 10;
	
	/** Constructor */
	public ArrListCanvas() {
		setMinimumSize(new Dimension(500,700));
		setPreferredSize(new Dimension(500,700));
	}
    
    public void setArrList(ArrayList<Integer> aL){
    	arrList = aL;
    }
    
    public ArrayList<Integer> getArrList(){
    	return arrList;
    }
    
    // TODO go back and fix this Sarah
    public void arrAccess(int index){
    	access = index;
    	ArrListCanvas.this.repaint();
    }
	
    public void arrListAddition(int num){
    	addingToAL = true;
    	Timer timer = new Timer();
    	int bound = arrList.size();
    	long delay = 1000;
    	int j;
    	for (j = 0; j <= arrList.size(); j++){
    		final int i = j;
    		timer.schedule(new TimerTask(){
    			@Override 
    			public void run(){
	    	    	aLAnnotations.add("Remains the same");
    	    		ArrListCanvas.this.repaint();
    			}
    			}, (2500*j));
    		
    	}
    	final int k = j+1;
    	timer.schedule(new TimerTask(){
    		@Override
    		public void run(){
    		try{
    				aLAnnotations.add("arrayList.get("+k+") = "+num);
    		} catch (ArrayIndexOutOfBoundsException a){
    			a.printStackTrace();
    		}
    		ArrListCanvas.this.repaint();
    		
    		arrList.add(num);
        	
        	aLAnnotations.clear();
        	ArrListCanvas.this.repaint();
        	
        	}}, 2500*bound);
    }
    
    public void arrListRemoval(int index){
    	addingToAL=false;
    	Timer timer = new Timer();
    	int bound = arrList.size();
    	long delay = 1000;
    	for (int j = 0; j < arrList.size(); j++){
    		final int i = j;
    		timer.schedule(new TimerTask(){
    			@Override 
    			public void run(){
    				if (i >= index && i < arrList.size()-1){
    	    			aLAnnotations.add("arrList.get("+i+") = "+arrList.get(i+1));
    	    		} else if (i < arrList.size()-1){
    	    			aLAnnotations.add("Remains the same");
    	    		} else {
    	    			aLAnnotations.add("ArrayList shrinks past this");
    	    		}
    				ArrListCanvas.this.repaint();
    			}
    			}, (2500*j));
    		
    	}
    	
    	timer.schedule(new TimerTask(){
    		@Override
    		public void run(){
    		arrList.remove(arrList.get(index));
        	
        	aLAnnotations.clear();
        	ArrListCanvas.this.repaint();
        	
        	}}, 2500*bound);
    }
    
    public void paintComponent(Graphics g) {
		Color c = Color.GREEN;
		for (int i=0; i < arrList.size(); i++){
			if (i == access){
				c = Color.CYAN;
			} else {
				c = Color.GREEN;
			}
			// System.out.println("i: "+i+" access: "+access);
			g.setColor(c);
			g.fillRect(22, 26+(60*i), 400, 50);
			g.setColor(Color.BLACK);
			g.drawString("arrList.get("+i+") = "+arrList.get(i), 30, 50+(60*i));
			if (aLAnnotations.size()>i && aLAnnotations.size()>0){
				g.drawString(aLAnnotations.get(i), 170, 50+(60*i));
				
			}
			if (addingToAL && arrList.size() < aLAnnotations.size()){
				g.drawString(aLAnnotations.get(aLAnnotations.size()-1), 170, 50+(60*arrList.size()));
			}
		}
		
		access = 10;
	
}
}
