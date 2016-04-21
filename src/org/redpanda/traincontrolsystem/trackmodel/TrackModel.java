package org.redpanda.traincontrolsystem.trackmodel;
// TrackModel.java
// Author: Tatiana Sunseri

import java.io.File;
import javax.swing.*;
import java.util.*;

import cn.ctc.bean.Schedule;
import cn.ctc.bean.Trace;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
            //GET TRACK FROM FILE
            List<Trace> t1 = ExcelUtil.readTrace(filename, "red");
            for (Trace t : t1) { numRedSegs++;}
            System.out.println("Reading Red Complete, Number : "+numRedSegs);
            redSegments=new TrackSegment[numRedSegs];
            t1 = ExcelUtil.readTrace(filename, "red");
            int i=0;
            
            for (Trace t : t1) { //new track values
               String block=t1.get(i).getBlocknumber();
               int bn=Integer.parseInt(block);
               double len=t1.get(i).getBlocklength();
               double grade=t1.get(i).getBlockgrade();
               double limit=t1.get(i).getSpeedlimit();
               limit=limit* 0.621371;
               //elevation 
               
                redSegments[i]=new TrackSegment(bn, limit, len, grade, 0);
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
               i++;
               if (i==numRedSegs){break;}
           }
            
            /* while (still info to read)
            {
                  //for each row
                  * block Number = curr row, col 3
                  * block length = curr row, col 4
                  * block grade = curr row, col 5
                  * speed lim = curr row, col 6 * 0.621371
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
                
                
	}
        
        public void getSchedule()
        {
            List<Schedule> scheduleFile = ExcelUtil.readSchedule("schedule.xlsx");
            int i = 1;
            /*for(Schedule x :scheduleFile)
            {
		String data = x.getLine()+" "+x.getAuthority()+" "x.getDepartureTime()+ " " +x.getAuthsequence().replaceAll(",", " ") +
			" "+ x.getSpeedsequence().replaceAll(",", " ");
			i++;
                //create new train for that shcedule
                //new TrainFollower(train[i],cs);
            }*/
        }
        
        public Train[] getTrains(){
            return trains;
        }
}
