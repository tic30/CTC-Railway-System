package org.redpanda.traincontrolsystem.trainmodel;

import org.redpanda.traincontrolsystem.trainmodel.Train;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.util.Arrays;

	
public class TrackModel 
{	
	 JPanel bottomHalf=new JPanel();
         JPanel infoPanel1=new JPanel();
         JPanel infoPanel2=new JPanel();
         JPanel inputPanel=new JPanel();
         JPanel warningPanel=new JPanel();
         JButton toggleOrientation;
         boolean toggle=false;
         JTextField inputFile, inputTemp,inputMaxPass;
         String fileName, newTemp, newPassLim;
         PrototypeTrack trackPanel;  
	 JFrame window; //frame for whole window
         int[] lengths, speedLimits, grades, x1s,x2s,y1s, y2s; 
         int numSegs,trainNum;
         char[] segNames;
         int totLen=0, maxPassNum, temp;
         Train train;
        
         String[] crossings; //for pull down menu
         String[] switches; // for pull down menu
         int brokenItems=0;

	public static void main(String [] args) //no args now
	{
                new TrackModel();  
	}
        
        public TrackModel()
	{
                temp=ThreadLocalRandom.current().nextInt(20, 85 + 1); //initial temp
                maxPassNum=getPassengerCapacity();
                
                window = new JFrame("System Prototype - Track Model");
                window.setLayout(new GridLayout(2,1)); //3 rows, one column
                
                //TRACK PANEL FOR IMAGE
                sysPrototypeTrack();
                
                //set train speed to min of speeds.
                trackPanel = new CommunicativePrototypeTrack(segNames,lengths,x1s,x2s,y1s,y2s,speedLimits,grades);
                
                //INFO PANEL 1 - stations and crossings
                infoPanel1.setLayout(new GridLayout(1,2));
                toggleOrientation=new JButton("Toggle Switch Orientation");
                infoPanel1.add(toggleOrientation);
                infoPanel1.add(new JLabel("Crossings: Crossing 1 not activated"));
                
                //INFO PANEL 2 - temp, curr pass number, warnings
                infoPanel2.setLayout(new GridLayout(1,4));
                infoPanel2.add(new JLabel("Temperature:" + temp));
                if (temp<=35)
                {
                    infoPanel2.add(new JLabel("Rails Heated"));
                }
                else
                {
                    infoPanel2.add(new JLabel("Rails Not Heated"));
                }
                
                infoPanel2.add(new JLabel("Number Passengers on Train: 0"));
                infoPanel2.add(new JLabel("Max Number of Passengers is " + maxPassNum));
                
                //INPUT PANEL 2 - inputfile temp, max pass number
                inputPanel.setLayout(new GridLayout(1,3));
                
                inputFile=new JTextField("Track Layout File", 100);
                inputFile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {     
                        fileName = inputFile.getText();
                        System.out.println("New input file is " + fileName);}});
                
                inputTemp=new JTextField("Modify Temperature",3);
                inputTemp.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent e) {     
                        newTemp = inputTemp.getText();
                        System.out.println("New temp is " + newTemp);}});
                
                inputMaxPass=new JTextField("Modify Passenger Limit",4);
                inputMaxPass.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {     
                        newPassLim = inputMaxPass.getText();
                        System.out.println("New max passenger limit is " + newPassLim);}});
                
                    //add everything
                inputPanel.add(inputFile);
                inputPanel.add(inputTemp);
                inputPanel.add(inputMaxPass);
                
                getBrokenComponents();
                
                
                bottomHalf.setLayout(new GridLayout(4,1));
                bottomHalf.add(infoPanel1);
                bottomHalf.add(infoPanel2);
                bottomHalf.add(inputPanel);
                bottomHalf.add(warningPanel);
                
                Container c = window.getContentPane();
                c.add(trackPanel);
                c.add(bottomHalf);
                
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(totLen,1000);
		window.setVisible(true);
	}
  
        public void sysPrototypeTrack(){ //predetermined for system prototype
            numSegs=15;
            segNames="ABCDEFGHIJKLMN".toCharArray();
            lengths=new int[] {200,100,100,100,100,100,200,100,100,100,200,240,200};
            speedLimits=new int[] {50,50,50,50,30,30,30,30,20,40,40,40,40};
            grades=new int[] {0,0,1,2,2,2,-1,0,0,0,0,0,0};
            x1s=new int[] {220,420,520,590,520,420,220,150,150,150,520,720,520};
            x2s=new int[] {420,520,590,590,590,520,420,220,150,220,720,720,720};
            y1s=new int[] {320,320,320,250,80,80,80,150,150,250,320,320,80};
            y2s=new int[] {320,320,250,150,150,80,80,80,250,320,320,80,80};
        }
        
        public void getBrokenComponents(){
            if (brokenItems==0)
                {
                    warningPanel.add(new JLabel("WARNINGS: There are currently no problems with the system.\n"));
                }
                else
                {
                    StringBuilder w=new StringBuilder("Warnings: \n");
                    for (int i=0;i<brokenItems;i++)
                    {
                        w.append("problem");
                    }
                    warningPanel.add(new JLabel(w.toString()));
                }
        }
}

