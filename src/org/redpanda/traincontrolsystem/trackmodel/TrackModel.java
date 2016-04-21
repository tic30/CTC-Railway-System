package org.redpanda.traincontrolsystem.trackmodel;
// TrackModel.java
// Author: Tatiana Sunseri
import java.io.File;
import javax.swing.*;
import java.util.*;
import java.util.ArrayList;

import cn.ctc.bean.Schedule;
import cn.ctc.Timer;
import cn.ctc.bean.Trace;
import cn.ctc.util.ExcelUtil;
import cn.ctc.util.ScheduleUtil;
import org.redpanda.traincontrolsystem.trainmodel.Train;

public class TrackModel 
{	
        private static TrackModel instance;
        private static TrackModelUI trackModelUI;
        static int clockSpeed;
        
        Timer t;
        String[] stations;
        String[] switches;
        String[] crossings;
        int[] speeds;
        int numTrains=0;
        String filename;
        
        Train[] trains;
        TrainFollower[] trainfollowers;
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
            trackModelUI.createModel();
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
                if (!(check==null)){ //segment has station
                    greenSegments[i].giveStation(check);
                    System.out.println(check);} 
                
                boolean check2=t1.get(i).checkForCrossing(); 
                if (check2){ //has crossing
                    greenSegments[i].hasCrossing=true;
                    trackModelUI.addcrossingtoUI(bn);
                }
                
                boolean check3=t1.get(i).checkForSwitch(); 
                if (check3){
                   greenSegments[i].hasSwitch=true;
                    trackModelUI.addswitchtoUI(bn);
                }
                
                boolean check4=t1.get(i).checkForUnderground();
                if (check4){
                   greenSegments[i].isUnderground=true;}
                
               i++;
               if (i==numGreenSegs){break;}
           }
            
            //RED TRACK 
            List<Trace> t2 = ExcelUtil.readTrace(filename, "red");
            for (Trace t : t2) { numRedSegs++;}
            System.out.println("Reading Green Complete, Number : "+numRedSegs);
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
                
                String check=t2.get(j).checkForStation();
                if (!(check==null)){ //segment has station
                    redSegments[j].giveStation(check);
                    System.out.println(check);} 
                
                boolean check2=t2.get(j).checkForCrossing(); 
                if (check2){ //has crossing
                    redSegments[j].hasCrossing=true;
                    trackModelUI.addcrossingtoUI(bn);
                }
                
                boolean check3=t2.get(j).checkForSwitch(); 
                if (check3){
                   redSegments[j].hasSwitch=true;
                    trackModelUI.addswitchtoUI(bn);
                }
                
                boolean check4=t2.get(j).checkForUnderground();
                if (check4){
                   redSegments[j].isUnderground=true;}
                
               j++;
               if (j==numRedSegs){break;}
           }
            //trackModelUI.addgreenTrack();
            //trackModelUi.addredTrack();
             
            getSchedule(); 
	}
        
        public void getSchedule()
        {
            List<String> schedule=ScheduleUtil.getSchedule();
            //automate
            numTrains=schedule.size();
            
            trainfollowers=new TrainFollower[numTrains];
            
            for(int i=0;i<numTrains;i++) //while still trains to schedule
            {
		String parser=schedule.get(i);
                //trainNo + ‘ ‘ +authority+ ‘ ‘ +authSequence+ ‘ ‘ +speedSequence
                //g1 5 152 63 64 65 66 30 50 50 50 50
                char l = parser.charAt(0); //line colo
                
                char n=parser.charAt(1);
                String s2=new String(""+n);
                int number=Integer.parseInt(s2);
                
                int numauths=(int)parser.charAt(3);
                String[] data = parser.substring(5, parser.length()).split(" ");
                int[] auths2=new int[numauths];
                for (int j=0;j<numauths;j++){
                    auths2[i]=Integer.parseInt(data[j]);}
                int ss=Integer.parseInt(data[numauths+1]); // train speed for whole
                //get lengths of each segment
                
                
                TrackSegment[] sender=null;
                String color=null;
                if (l=='r'){
                    sender=redSegments;
                    color="r";}
                if (l=='g'){ 
                    sender=greenSegments;
                    color="g";}
                
                double[] lens=getAuthLens(auths2, sender);
                
                trainfollowers[i]=new TrainFollower(number,color,auths2,lens,sender,ss); 
                i++;
            }
        }
        
        public double[] getAuthLens(int[] a, TrackSegment[] ts){
            double[] l=new double[a.length];
            for (int i=0;i<a.length;i++){
                for (int j=0;j<ts.length;j++){
                    if (a[i]==ts[j].blockNum){
                        l[i]=ts[j].length; }}}
            return l;}
        
        public String getOccupancy(int trainNum){
            String s="";
            for (int i=0;i<trainfollowers.length;i++){
                if (trainNum==trainfollowers[i].trainNum)
                {
                    s=trainfollowers[i].getCurrBlock();
                }
            }
            return s;
        }
}
