import java.util.*;
import java.awt.*;
import java.io.*;
public class Cell implements Serializable 		//Cell is fundamental component of GRID
{
	int x,y;
	public Cell parent = null; 		//Setting the default value of parent as NULL
	static final int w=20;          //Applying the dimensions to GRID
	static final int h=20;
	boolean visited=false;
	public Color c; 				//Creating a color variable for each cell
	int hu=0,f=0,g=0;
	ArrayList <Cell>ajdacencyList= new ArrayList<>();	//Creating adjancecny list
	Cell(int i,int j)
	{
		y=i*h;						//Calculating x & y co-ordinate of each cell
		x=j*w;
		c=Color.BLUE;				//Applying the default color to blue
	}
	
}
