package cn.ctc.trackpaint;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrototypeTrack extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// DISPLAY VARIABLES
	private JButton startSim, stopSim, resetSim;  
	private ButtonListener theListener;  
	private Timer T;  
        private int delay=100;//milliseconds - CHANGE LATER
        private int totalTime=0;
        boolean  endReached=false, moving=false, hasBeenReset=true, trackEnd=false;
        private int s=0;//second timer
        
        //TRACK VARIABLES
        private int[] lens; //arbitrary array of seg lengths
        private PrototypeTrackSegment[] segments; //segment array
        private char[] segNames;
        private int[] segTimes,segTimeStarts, x1s,x2s, y1s, y2s,limits, grades;

	public PrototypeTrack(char[] sn, int[] lengths, int[] t1, int[] t2, int[] h1s, int[]h2s, int[] speedLimits, int[] g) //numSegs=number segments, lengths is array
	{
                lens=new int[lengths.length];
                lens=lengths; //segment lengths
                segNames=sn;
                limits=speedLimits;
                grades=g;
                x1s=t1; x2s=t2;
                y1s=h1s; y2s=h2s;
                
                segments=new PrototypeTrackSegment[lens.length];//REWRITE
                segTimes=new int[lens.length];
                segTimeStarts=new int[lens.length];
                int last=100; //beginning x for portraying track 
                
                //CREATE SEGMENTS, AVOID NAMES FOR NOW
                for (int i=0; i<lens.length;i++)
                {
                    segments[i]=new PrototypeTrackSegment(x1s[i],x2s[i],y1s[i],y2s[i],lens[i],limits[i],grades[i]);
                    segTimes[i]=segments[i].getTimeMag();
                    last=last+lens[i]+15;
                }
                segments[0].hasTrain=true;
        
                //REAL TIME INTERVALS IN ORDER
                int t=0;
                for (int i=0;i<lens.length;i++)
                {
                    segTimeStarts[i]=t+segTimes[i];
                    t=segTimeStarts[i];
                }
                
                //CALCULATE SEG START TIMES FOR LIGHTING
                for (int i=0;i<segments.length;i++)
                {
                    segTimeStarts[i]=segTimeStarts[i]*2;
                    totalTime=totalTime+segTimeStarts[i];
                }
		theListener = new ButtonListener();
		setLayout(null);
                
                //CREATE TIMER
		T = new Timer(delay, this);
		
		//RESET SIMULATION
		resetSim = new JButton("Reset Simulation");
		resetSim.setBounds(564, 360, 161, 27);
		add(resetSim);
		resetSim.addActionListener(theListener);
		resetSim.setEnabled(false);
		
		//STOP SIMULATION
		stopSim = new JButton("Stop Simulation");
		stopSim.setBounds(297, 355, 161, 37);
		add(stopSim);
		stopSim.addActionListener(theListener);
		stopSim.setEnabled(false);
		
		//START SIMULATION
		startSim = new JButton("Start Simulation");
		startSim.setBounds(52, 355, 161, 37);
		add(startSim);
		startSim.addActionListener(theListener);
	}
        
	private class ButtonListener implements ActionListener
	{
                public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == startSim) //start simulation
			{
                                moving=true;
                                hasBeenReset=false; //modify
                                
			}
			else if (e.getSource() == stopSim) //stopSim
			{
                                moving=false;
                                hasBeenReset=false;
                                
			}
                        else if (e.getSource() == resetSim) //reset Sim
                        {
                                segments[0].hasTrain=true;
                                for (int i=1;i<segments.length;i++)
                                {
                                    segments[i].hasTrain=false;
                                }
                                s=0;
                                moving=false;
                                hasBeenReset=true;
                                repaint();
                        } 
                        if (moving)
                        {
                            T.start();
                            startSim.setEnabled(false);
                            stopSim.setEnabled(true);
                            resetSim.setEnabled(true);  
                        }
                        else{
                            T.stop();
                            if (hasBeenReset){
                                startSim.setEnabled(true);
                                stopSim.setEnabled(true);
                                resetSim.setEnabled(false);
                            }
                            else{
                                startSim.setEnabled(true);
                                stopSim.setEnabled(false);
                                resetSim.setEnabled(true);
                            }
                        }
		}
	}
	
	public void paintComponent(Graphics g) // done?
	{
		super.paintComponent(g);	
		Graphics2D g2 = (Graphics2D) g;	
		for (int i=0;i<segments.length;i++)
                {
                    segments[i].draw(g2);
                }
	}
             
	public void actionPerformed(ActionEvent e) 
	{
                if (moving)
                {
                    s=s+1;
                    for (int i=0;i<segments.length-1;i++)
                    {
                        if (s==segTimeStarts[i+1])
                        {
                            segments[i].hasTrain=false;
                            segments[i+1].hasTrain=true;
                            repaint();
                        }
                        if (s==segTimeStarts[4])
                        {//station
                            
                        }
                        if (s==segTimeStarts[10])
                        {   
                            endReached=true;
                            startSim.setEnabled(false);
                            stopSim.setEnabled(false);
                            resetSim.setEnabled(true);
                            hasBeenReset=true;
                            moving=false;
                            T.stop();
                            i=100;
                        }
                    }
                }
                if (endReached)
                {
                    System.out.println("Yard Reached");
                    segments[9].hasTrain=true;
                    repaint();
                }
        }
}

