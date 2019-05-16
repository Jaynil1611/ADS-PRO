import java.awt.*;  
import java.awt.event.*;            //Importing the packages for GUI
import javax.swing.*;
public class MyCanvas extends Canvas implements MouseListener { 

	public MyCanvas() {
		addMouseListener(this);  
        setBackground (Color.GRAY);         //Background color is Gray
        setSize(400, 400);
        setBounds(30,50,400,400);     //set the layout of grid
	}

  public static Cell source;        // source variable
  public static Cell destination;   //destination variable

	public void mouseClicked(MouseEvent e) {  
  		
    }  
    public void mouseEntered(MouseEvent e) {      //Mouse events
    }  
    public void mouseExited(MouseEvent e) {  
    }  
    public void mousePressed(MouseEvent e) { 
    int i=(e.getY()/20);
  		int j=(e.getX()/20);
  		if(e.getButton() == MouseEvent.BUTTON1){    //For source input left-click,Color=ORANGE
  			test.Map[i][j].c=Color.ORANGE;
        source=test.Map[i][j];
  		}
  		if(e.getButton() == MouseEvent.BUTTON2){    //For walls input middle-click, Color=GRAY
  			test.Map[i][j].c=Color.GRAY;
  			test.updateAdjList(i,j);
  		}
      if(e.getButton() == MouseEvent.BUTTON3){     //For destination input right-click, Color=CYAN
        test.Map[i][j].c=Color.CYAN;
        destination=test.Map[i][j];
      }
  		repaint();          //Repaint recalls the paint()
   }  
   
    public void mouseReleased(MouseEvent e) {  
    }  
     public void paint(Graphics g){         //Paint method fills the grid with color
    	for (int i=0;i<20;i++) {
  			for (int j=0;j<20 ;j++ ) {
  				Cell C=test.Map[i][j];
  				try{
          g.setColor(C.c);
  				g.fillRect(C.x,C.y,C.w-1,C.h-1);  //Filling each cell & thereby the whole GRID 
        }catch(Exception e){}
  			}
  		}    
	}  
}    