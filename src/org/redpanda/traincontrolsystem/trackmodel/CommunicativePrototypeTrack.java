package org.redpanda.traincontrolsystem.trainmodel;

import java.awt.*;
import java.util.*;
import javax.swing.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

public class CommunicativePrototypeTrack extends JPanel implements ActionListener
{
	// DISPLAY VARIABLES
	 JButton startSim, stopSim, resetSim;  
	 ButtonListener theListener;  
	 Timer T;  
         int delay=100;//milliseconds - CHANGE LATER
        
         JPanel fullPanel,buttonPanel, stationPanel;
         int totalTime=0;
        boolean  endReached=false, moving=false, hasBeenReset=true, trackEnd=false;
         int s=0;//second timer
        
        //TRACK VARIABLES
         int[] lens;
         int initAuthority=4, secAuthority=6; //arbitrary array of seg lengths
         PrototypeTrackSegment[] segments; //segment array
         char[] segNames;
         int[] segTimes,segTimeStarts, x1s,x2s, y1s, y2s,limits, grades;

	public CommunicativePrototypeTrack(char[] sn, int[] lengths, int[] t1, int[] t2, int[] h1s, int[]h2s, int[] speedLimits, int[] g) //numSegs=number segments, lengths is array
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
		
                //BUILD PANEL
                fullPanel=new JPanel();
                fullPanel.setLayout(new GridLayout(2,1));
                
                
                
                //SIMULATION BUTTONS
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,3));
		theListener = new ButtonListener();
                
                //START SIMULATION
		startSim = new JButton("Start Simulation");
		startSim.addActionListener(theListener);
		buttonPanel.add(startSim, BorderLayout.CENTER);
                
                //STOP SIMULATION
		stopSim = new JButton("Stop Simulation");
		stopSim.addActionListener(theListener);
		stopSim.setEnabled(false);
		buttonPanel.add(stopSim, BorderLayout.CENTER);
                
                //RESET SIMULATION
                resetSim = new JButton("Reset Simulation");
		resetSim.addActionListener(theListener);
		resetSim.setEnabled(false);
                buttonPanel.add(resetSim, BorderLayout.CENTER);
                
                //STATION PANEL FOR DISPLAY TRACK
                stationPanel=new JPanel();
                
                //Create JPanel
                fullPanel.add(buttonPanel);
                fullPanel.add(stationPanel);
                this.add(fullPanel);
                
                //CREATE TIMER
		T = new Timer(delay, this);
	}
        
	private class ButtonListener implements ActionListener
	{
                public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == startSim) //start simulation
			{
                                new Train();
                                setSpeed(20);
                                setAuthority(initAuthority);
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
                                initAuthority=4; secAuthority=6;
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
                            setTrackGrade(grades[i+2]);
                            repaint();
                            if (i<4)
                            {
                                initAuthority--;
                                setAuthority(initAuthority);
                            }
                            if (i>4)
                            {
                                secAuthority--;
                                setAuthority(secAuthority);
                            }
                        }
                        if (s==segTimeStarts[4])
                        {//station protocol
                            System.out.println("STOPPING AT STATION");
                            int currPass=getPassengerCount();
                            System.out.println("Current Passengers: " + currPass+ "\n");
                            int maxPass=getPassengerCapacity();
                            int exiting=ThreadLocalRandom.current().nextInt(5, maxPass);
                            System.out.println("People exiting: " + exiting +"\n");
                            int nowOn=currPass-exiting;
                            int waiting=ThreadLocalRandom.current().nextInt(5, 100+1);
                            int gettingOn=maxPass-nowOn;
                            System.out.println("Passengers waiting: " + waiting +"\n");
                            System.out.println("Passengers getting on: "+gettingOn+"\n");
                            System.out.println("Passengers still at station: "+ (waiting-gettingOn)+"\n");
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
                    setAuthority(0);
                    segments[9].hasTrain=true;
                    repaint();
                }
        }
}
