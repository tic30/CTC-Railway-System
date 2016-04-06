package org.redpanda.traincontrolsystem.trackmodel;

import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.event.WindowAdapter;

//PURPOSE TO SHOW JUST TRACK PRINTOUT

public class tester
{	
        private PrototypeTrack trackPanel;  // StopWatch is a subclass of JPanel
	private JFrame window; //frame for whole window
        //private JLabel temp; JLabel currPass;
        private int[] lengths; 
        private int numSegs;
        private int totLen=0; int randomMaxPass, randomTemp, randomInitPass;
        //private StringBuilder s=new StringBuilder("Segment Lengths:");

	public static void main(String [] args) //no args now
	{
                new tester();  
	}
        
        public tester()
	{
		window = new JFrame("Track Layout Tester");
                prototypeTrack(); //create random track for now
                
                trackPanel = new PrototypeTrack(lengths); //passes all segment lengths
                
                Container c = window.getContentPane();
                c.add(trackPanel);
                
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(totLen,1000);
		window.setVisible(true);
	}

	public void prototypeTrack(){ //EDIT
            numSegs=ThreadLocalRandom.current().nextInt(5, 12 + 1); //between 5 and 12 segs created
            System.out.println("there are "+ numSegs + " segments\n");
            lengths=new int[numSegs];
            for(int i=0;i<numSegs;i++)
            {
                lengths[i]=ThreadLocalRandom.current().nextInt(10, 100 + 1);
                System.out.println("Adding length " + lengths[i]);
                totLen=totLen+lengths[i]+75;
            }   
        } 
}


