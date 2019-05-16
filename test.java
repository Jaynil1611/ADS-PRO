import java.awt.*;  
import java.awt.event.*;
import javax.swing.*; 		//Importing the packages for GUI
import java.util.*;
import java.io.*;
import java.lang.Math;
public class test
{  
	public static Cell Map [][] = new Cell[20][20];      //GRID of Cell variables
	Frame f;             
  static MyCanvas o; 			//Object of MyCanvas class

	public test()  				//Constructor of test
  	{  
    	f= new Frame("Canvas Example"); 
      	o=new MyCanvas();
    	f.add(o);                  //Adding this object in frame
    	f.setLayout(null);  
    	f.setSize(470, 490);       //Fixed the size of grid
    	f.setVisible(true);  
  	}

/*------------Removing the obstacles from adjancency list in all 8 directions----------------------*/
public static void updateAdjList(int i,int j)
  	{
  				if (i!=0) {
  						Map[i-1][j].ajdacencyList.remove(Map[i][j]);	
  				}
  				if (i!=19) {
   						Map[i+1][j].ajdacencyList.remove(Map[i][j]);
  				}
  				if (j!=0) {
  						Map[i][j-1].ajdacencyList.remove(Map[i][j]);
  				}
  				if (j!=19) {
  						Map[i][j+1].ajdacencyList.remove(Map[i][j]);
  				}
       		   if (i!=0&&j!=0) {
              Map[i-1][j-1].ajdacencyList.remove(Map[i][j]);  
          		}
	          if (i!=19&&j!=19) {
	              Map[i+1][j+1].ajdacencyList.remove(Map[i][j]);
	          	}
	          if (j!=0&&i!=0) {
	              Map[i+1][j-1].ajdacencyList.remove(Map[i][j]);
	          	}
	          if (j!=19&&i!=19) {
	              Map[i-1][j+1].ajdacencyList.remove(Map[i][j]);
	          	}
  	}

public static void saveMap(String s)       //Save Map function to save a Map
    {
	      try {
	      File f = new File(s);
	      FileOutputStream file = new FileOutputStream(f);      //Creating a file
	      ObjectOutputStream out = new ObjectOutputStream(file); 
	      for (int i=0;i<20; i++) {
	        for (int j=0; j<20; j++) {
	          out.writeObject(Map[i][j]);       //Writing to  a file
	        }
	      } 
	      out.close(); 
	      file.close(); 				 //Closing a file
	    }
     catch(IOException ex)            //Checking exceptions
        { 
            System.out.println("IOException is caught"); 
        } 
	}

public static void loadMap(String s)			//Load Map function to load a map
	{              
     	try {    
            FileInputStream file = new FileInputStream(s); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Method for deserialization of object 
       		for (int i=0;i<20; i++) {
       			for (int j=0; j<20; j++) {
          			Map[i][j]=(Cell)in.readObject();    // Reading the object from a file 
        		}
      		}       
            in.close(); 				//Closing a file
            file.close(); 
        } 

        catch(IOException ex)           
        { 
            System.out.println("IOException is caught"); 
        }
        catch(ClassNotFoundException ex) 
        { 
            System.out.println("ClassNotFoundException is caught"); 
        }  
         catch(Exception e1)
        {
        	 System.out.println("Exception is caught");
        }
	}

/*--------------------------------------A-STAR Implementation-----------------------------------*/
public static void A_star()
    {
      //Cell s=Map[1][2];
      //Cell d=Map[5][10];
      Cell s=MyCanvas.source;
      Cell d=MyCanvas.destination;
      int nodecount=0;
      ArrayList<Cell> open = new ArrayList<>();				//Creating two ArrayList 
      ArrayList<Cell>closed = new ArrayList<>();
      open.add(s);					//Adding the source to open list
      s.f=0;
      s.hu=(int)Math.sqrt((s.x-d.x)*(s.x-d.x)+(s.y-d.y)*(s.y-d.y));  //Calculating the heuristic cost by Euclidean distance 
      s.f=s.g+s.hu;            		//Calculating the final cost

      while(!open.isEmpty())
      {
        Cell a=open.get(0);					
        for(Cell p : open)
        {
          if(p.f<a.f)				//Finding the minimum element in open list
            a=p;
        }
        open.remove(a);
        closed.add(a);
        if(a==d)
        {
          while(d!=null)
          {
            d.c=new Color(0,0,0);
            d=d.parent;
          }
          	s.c=new Color(0,0,0);
        }
        else
        {
          for(Cell n : a.ajdacencyList)
          {
              
            if(!closed.contains(n))
            {
                n.g=a.g+1;
                n.hu=Math.abs(n.x-d.x)+Math.abs(n.y-d.y);
                n.f=n.g+n.hu;
                if(!open.contains(n))
                {
                  n.parent=a;
                  n.c=new Color(n.f%255,0,0);
                  
                  open.add(n); 
                }
                else
                {
                  Cell m=n;
                  if(n.g<m.g)
                  {
                    m.g=n.g;
                    m.parent=n.parent;
                  }
                }
                
            }
         }
      }
       
  }
  
}
//BFS implementation
 public static int BFS(Cell s ,Cell d)
    {
      int nodecount=0;
    	Queue<Cell>queue = new LinkedList<>();
    	queue.add(s);
    	s.visited=true;
      float r=1.0f,g=0.0f,b=0.0f;
    	while(!queue.isEmpty())
    	{
    		 Cell k=queue.remove();
         g=(g+0.002f)%1;
    		for(Cell a : k.ajdacencyList)
    		{

	    		if(!a.visited)
	    		{
	    			
	    			queue.add(a);
	    			a.parent=k;
            a.visited=true;
	    			a.c=new Color(r,(g+0.002f)%1,b);
            nodecount++;
	    		}
          if (a==d) return nodecount;
    	}
    }
    return nodecount;
}
//BFS Path
public static void Path()
	{
    Cell s=MyCanvas.source;
    Cell d=MyCanvas.destination;
		int nodecount=BFS(s,d);		
		s.c=new Color(0,0,0);
		
		while(d!=s)
		{
			d.c=new Color(0,0,0);
			d=d.parent;
		}
		System.out.println("The number of nodes visited are "+nodecount);
}

 public static void main(String args[])  
  		{  
		  //loadMap("data.txt");
  	  	test t= new test();  
  	 	for (int i=0;i<20;i++) {
  			for (int j=0;j<20 ;j++ ) {
  				Map[i][j]=new Cell(i,j);
  			}
  		}
  		for (int i=0 ;i<20;i++) {
			for (int j=0 ;j<20;j++) {
  				Cell C=Map[i][j];
  				if (i!=0) {
  					if(Map[i-1][j].c!=Color.GRAY)
  						C.ajdacencyList.add(Map[i-1][j]);           //For creation of walls / obstacles	
  				}                                               //and updating the adjancency list
  				if (i!=19) {
  					if(Map[i+1][j].c!=Color.GRAY)
  						C.ajdacencyList.add(Map[i+1][j]);
  				}
  				if (j!=0) {
  					if(Map[i][j-1].c!=Color.GRAY)
  						C.ajdacencyList.add(Map[i][j-1]);
  				}
  				if (j!=19) {
  					if(Map[i][j+1].c!=Color.GRAY)
  						C.ajdacencyList.add(Map[i][j+1]);
  				}

          if (i!=0&&j!=0) {
            if(Map[i-1][j-1].c!=Color.GRAY)
              C.ajdacencyList.add(Map[i-1][j-1]); 
          }
          if (i!=19&&j!=19) {
            if(Map[i+1][j+1].c!=Color.GRAY)
              C.ajdacencyList.add(Map[i+1][j+1]);
          }
          if (j!=0&&i!=19) {
            if(Map[i+1][j-1].c!=Color.GRAY)
              C.ajdacencyList.add(Map[i+1][j-1]);
          }
          if (j!=19&&i!=0) {
            if(Map[i-1][j+1].c!=Color.GRAY)
              C.ajdacencyList.add(Map[i-1][j+1]);
          }
  				
  			}  			
  		}
      	int i=10;
        //Menu Driven
  		Scanner sc=new Scanner(System.in);
  		System.out.println("0.Exit");
  		System.out.println("1.Load Map");
  		System.out.println("2.Save Map");
  		System.out.println("3.Find Path");
      System.out.println("4.A-STAR ");
      try
      {
  		while(i!=0)
      {
  		 		i=sc.nextInt();
  		 	if(i==0)
  		 		t.f.dispose();
  		 	if(i==1)loadMap(sc.next());
  		 	if(i==2)saveMap(sc.next());
  		 	if(i==3)
  		 		{
  		 			
  		 			Path();
  		 			System.out.println("Process Completed");
            o.repaint();
  		 		}
  		
      if(i==4)
      {
        A_star();
      }
    }
    }catch(Exception e){}
  //	saveMap("data.txt");
  	}  
}  

