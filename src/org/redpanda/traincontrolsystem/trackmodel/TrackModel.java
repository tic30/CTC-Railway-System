package org.redpanda.traincontrolsystem.trackmodel;
// TrackModel.java
// Author: Tatiana Sunseri
import java.io.File;
import javax.swing.*;
import java.util.*;

import cn.ctc.bean.Schedule;
import cn.ctc.bean.Trace;
import cn.ctc.util.ExcelUtil;
import org.redpanda.traincontrolsystem.trainmodel.Train;

public class TrackModel 
{	
        private static TrackModelUI trackModelUI;
        static int clockSpeed;
        
        Train[] trains;
        String[] switches;
        String[] crossings;
        int[] speeds;
        int numTrains=0;
        String filename;
        
        TrackSegment[] greenSegments=new TrackSegment[10];
        TrackSegment[] redSegments=new TrackSegment[10];
        int numRedSegs=0, numGreenSegs=0;
        
        private static final String[] clockSpeedOptions = { "Normal Clockspeed", "10 Times Clockspeed"};
        
        //MAIN
        public static void main(String [] args) 
	{
            //get clock speed
            JFrame frame1 = new JFrame("Clock Speed");
            String clockSpeedString = (String) JOptionPane.showInputDialog(frame1, "Choose a clockspeed:","Clockspeed",
            JOptionPane.QUESTION_MESSAGE, null, clockSpeedOptions, clockSpeedOptions[0]);
            if (clockSpeedString.equals("Normal Clockspeed")){ clockSpeed=1;}
            else{ clockSpeed=10;}
            //new user interface
            trackModelUI=TrackModelUI.getInstance();  
	}
        
        //NEW TRACK MODEL
        public TrackModel()
	{
            JFrame frame2 = new JFrame("Input File"); //get excel file
            String inFile = JOptionPane.showInputDialog(frame2, "Choose a file to load for the Track Model");
            do{
                if (inFile.equals("")){
                    inFile=("Track Layout & Vehicle Data vF1.xlsx"); //automatic mode
                }
                else{
                    inFile = JOptionPane.showInputDialog(frame2, "Choose a file to load for the Track Model (must be a .xlsx file)");
                }
            }while (!(inFile.toLowerCase().contains(".xlsx")) || (!(new File(inFile)).exists()));
            new TrackModel(inFile);
        }
        
        //NEW TRACK MODEL
        public TrackModel(String filename) //New track model, specified settings
        {
            //GREEN TRACK 
            List<Trace> t1 = ExcelUtil.readTrace(filename, "green");
            for (Trace t : t1) { numGreenSegs++;}
            System.out.println("Reading Green Complete, Number : "+numGreenSegs);
            greenSegments=new TrackSegment[numGreenSegs];
            t1 = ExcelUtil.readTrace(filename, "green");
            int i=0;
            
            for (Trace t : t1) { //new track values
               String block=t1.get(i).getBlocknumber();
               int bn=Integer.parseInt(block);
               double len=t1.get(i).getBlocklength();
               double grade=t1.get(i).getBlockgrade();
               double limit=t1.get(i).getSpeedlimit();
               limit=limit* 0.621371;
               double e=t1.get(i).getElevation();
               
                greenSegments[i]=new TrackSegment(bn, limit, len, grade, e);
                String check=t1.get(i).checkForStation();
                if (!(check==null)){
                    greenSegments[i].giveStation(check);
                    System.out.println(check);
                } //segment has a station
                
                
                /*if (t.checkForCrossing()){
                    redSegments[i].hasCrossing=true;
                }
                if (t.checkForSwitch()){
                    redSegments[i].hasSwitch=true;
                }
                if (t.checkForUnderground()){
                    redSegments[i].isUnderground=true;
                }*/
               i++;
               if (i==numGreenSegs){break;}
           }
            
            //RED TRACK 
            List<Trace> t2 = ExcelUtil.readTrace(filename, "red");
            for (Trace t : t2) { numRedSegs++;}
            System.out.println("Reading Red Complete, Number : "+numRedSegs);
            redSegments=new TrackSegment[numRedSegs];
            t2 = ExcelUtil.readTrace(filename, "red");
            int j=0;
            
            for (Trace t : t2) { //new track values
               String block=t2.get(j).getBlocknumber();
               int bn=Integer.parseInt(block);
               double len=t2.get(j).getBlocklength();
               double grade=t2.get(j).getBlockgrade();
               double limit=t2.get(j).getSpeedlimit();
               limit=limit* 0.621371;
               double e=t2.get(j).getElevation();
               
                redSegments[j]=new TrackSegment(bn, limit, len, grade, e);
                /*if (!(t.checkForStation()==null)){
                    redSegments[i].giveStation(t.checkForStation());
                }
                if (t.checkForCrossing()){
                    redSegments[i].hasCrossing=true;
                }
                if (t.checkForSwitch()){
                    redSegments[i].hasSwitch=true;
                }
                if (t.checkForUnderground()){
                    redSegments[i].isUnderground=true;
                }
                System.out.println("got through round " + i);*/
              j++;
               if (j==numRedSegs){break;}
           }
            
            /* while (still info to read)
            {
                  * infrastructure
                  *     if not null 
                  *         add as specified
                  *         switches++ or crossings++
                  * elevation = curr row, col 9
                  * switch? curr row, col 11
                  * redSegments[int i]=new trackSegment(block num, speed lim, length, grade, elevation );
                  * numSegs++;
            */
            //trackModelUI.addTrack();
            //if second, trackModelUI.addTrack(); again
                
            getSchedule();
                
	}
        
        public void getSchedule()
        {
            List<Schedule> scheduleFile = ExcelUtil.readSchedule("schedule.xlsx");
            int i = 1;
            for(Schedule x :scheduleFile)
            {
		String l = x.getLine();
                String d=x.getDeparturetime();
                String as = x.getAuthsequence().replaceAll(",", " ");
                //String ss=x.getMinSpeed().replaceAll(",", " ");
                
		i++;
                //access timer
                //create new train for that shcedule
                //new TrainFollower(train[i],cs);
            }
        }
        
        public Train[] getTrains(){
            return trains;
        }
}
