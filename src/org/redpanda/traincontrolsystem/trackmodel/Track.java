import java.awt.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

public class Track extends JPanel implements ActionListener
{
	// Declare instance variables as shown.
	private JButton startSim, stopSim, resetSim;  // Some buttons
	private JPanel buttonPanel, stationPanel;
	private ButtonListener theListener;  
	private Timer T;  
        private int delay=100;//milliseconds
        private int s;
        private int totalTime=0,temperature, maxPass, currPass;
        boolean initialized=false;
        private TrackSegment seg1, seg2, seg3, seg4, seg5, seg6;
        private int[] lens= new int[6]; //6 sections of track
        private int segTime[]=new int[6];
        private int segTimeStarts[]=new int[6];
        boolean  moving=false, hasBeenReset=true, trackEnd=false; 
        private int speed=30;
        
        int waiting, exiting, enterring;
         String nextStop;

	public Track(int[] lengths)
	{
		lens=lengths; //segment lengths
                int last=50;
                
                //create segments
		seg1=new TrackSegment("seg1", last,lens[0],true); //begin,length,speed,train
                segTime[0]=seg1.getTimeMag();
                last=last+lens[0]+50;
                
                seg2=new TrackSegment("seg2",last,lens[1],false);
                segTime[1]=seg2.getTimeMag();
                seg2.giveStation("Station 1"); 
                
                last=last+lens[1]+50;
                seg3=new TrackSegment("seg3",last,lens[2],false);
                segTime[2]=seg3.getTimeMag();
                
                last=last+lens[2]+50;
                seg4=new TrackSegment("seg4",last,lens[3],false);
                segTime[3]=seg4.getTimeMag();
                
                last=last+lens[3]+50;
                seg5=new TrackSegment("seg5",last,lens[4],false);
                segTime[4]=seg5.getTimeMag();
                seg5.giveStation("Station 2");
                
                last=last+lens[4]+50;
                seg6=new TrackSegment("seg6",last,lens[5],false);
                segTime[5]=seg5.getTimeMag();
                
                //next stops
                seg2.giveNextStop("Station 2");
                seg5.giveNextStop("No more stops");
        
                //get real time intervals in order
                int t0=0;
                segTimeStarts[0]=t0+segTime[0];
                t0=segTimeStarts[0];        
                segTimeStarts[1]=t0+segTime[1];
                t0=segTimeStarts[1];
                segTimeStarts[2]=t0+segTime[2];
                t0=segTimeStarts[2];
                segTimeStarts[3]=t0+segTime[3];
                t0=segTimeStarts[3];
                segTimeStarts[4]=t0+segTime[4];
                t0=segTimeStarts[4];
                segTimeStarts[5]=t0+segTime[5];
                System.out.println("SegTimes: ");
                for (int i=0;i<6;i++)
                {
                    segTimeStarts[i]=segTimeStarts[i]*2;
                    totalTime=totalTime+segTimeStarts[i];
                    System.out.println(segTimeStarts[i]);
                }
                
                    
                //station info
                stationPanel=new JPanel();
                stationPanel.setLayout(new GridLayout(4,2));
                
		//add buttons
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,3));
		theListener = new ButtonListener();
                
		startSim = new JButton("Start Simulation");
		startSim.addActionListener(theListener);
		buttonPanel.add(startSim);
                
		stopSim = new JButton("Stop Simulation");
		stopSim.addActionListener(theListener);
		stopSim.setEnabled(false);
		buttonPanel.add(stopSim);
                
                resetSim = new JButton("Reset Simulation");
		resetSim.addActionListener(theListener);
		resetSim.setEnabled(false);
                buttonPanel.add(resetSim);
                
                //Warning panel
                //warningPanel=new JPanel();
                //warningPanel.add(new JButton("WARNINGS"));
                this.add(buttonPanel);//, BorderLayout.NORTH);
                this.add(stationPanel);
                //this.add(warningPanel);
		T = new Timer(delay, this);
	}
        
        public void initialSettings(int t, int l, int p){
                if (!initialized)
                {
                    temperature=t;
                    maxPass=l;
                    currPass=p;//initialize passengers
                    System.out.println("there are initially " + currPass + " passengers");
                    initialized=true;
                }
        }
        
	private class ButtonListener implements ActionListener
	{
                public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == startSim) //start simulation
			{
                                moving=true;
                                hasBeenReset=false;
                                
			}
			else if (e.getSource() == stopSim) //stopSim
			{
                                moving=false;
                                hasBeenReset=false;
                                
			}
                        else if (e.getSource() == resetSim) //reset Sim
                        {
                                seg1.hasTrain=true; 
                                seg2.hasTrain=false;
                                seg3.hasTrain=false;
                                seg4.hasTrain=false;
                                seg5.hasTrain=false;
                                seg6.hasTrain=false;
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
			seg1.draw(g2);
			seg2.draw(g2);
			seg3.draw(g2);
			seg4.draw(g2);
			seg5.draw(g2);
			seg6.draw(g2);
                
	}
        
       public void stationProtocol(TrackSegment currSeg){
            System.out.println("At Station " + currSeg.getName() + " at time " + s);
            initiateStationPause();
            System.out.println("The max number of passengers is" + maxPass);
            System.out.println("The current number of passengers is" + currPass);
            waiting=ThreadLocalRandom.current().nextInt(0, 200 + 1); //0 to 200 pass waiting
            System.out.println("There are " + waiting + " passengers waiting");
            exiting=ThreadLocalRandom.current().nextInt(0, currPass + 1);
            System.out.println("There are " + exiting + " passengers exiting the train");
            int net=currPass-exiting; //num people still on train
            enterring=maxPass-net;
            System.out.println("There are " + enterring + " passengers getting on train");
            int leftWaiting=waiting-enterring;
            System.out.println("The next stop is " + currSeg.nextStop); 
        }
        
        public void initiateStationPause(){
            System.out.println("Pausing for passengers to exit/board");
        }
             
	public void actionPerformed(ActionEvent e) //do something here
	{
                if (moving)
                {
                    s=s+1; 
                }            
                if (s==segTimeStarts[1]) //seg2 lit
                {
                    seg1.hasTrain=false;
                    seg2.hasTrain=true;
                    //stationProtocol(seg2);
                    repaint();
                }
                if (s==segTimeStarts[2]) //seg3 lit
                {
                    seg2.hasTrain=false;
                    seg3.hasTrain=true;
                    repaint();
                }
                if (s==segTimeStarts[3]) //seg4 lit
                {
                    seg3.hasTrain=false;
                    seg4.hasTrain=true;
                    repaint();
                }
                if (s==segTimeStarts[4])//seg 5 lit
                {
                    seg4.hasTrain=false;
                    seg5.hasTrain=true;
                    //stationProtocol(seg5);
                    repaint();
                }
                if (s==segTimeStarts[5]) //seg6 lit
                {
                    seg5.hasTrain=false;
                    seg6.hasTrain=true;
                    startSim.setEnabled(false);
                    stopSim.setEnabled(false);
                    resetSim.setEnabled(true);
                    hasBeenReset=true;
                    repaint();
                }
        }
}


