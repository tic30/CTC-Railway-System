package org.redpanda.traincontrolsystem.trackmodel;
// TrackModel.java
// Author: Tatiana Sunseri

import java.io.File;
import javax.swing.Timer;
import javax.swing.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.ctc.bean.Schedule;
import cn.ctc.bean.Trace;
import cn.ctc.util.ExcelUtil;
import cn.ctc.util.ScheduleUtil;

public class TrackModel 
{	
        private static File inputFile;
        private static TrackModelUI trackModelUI;
        private Timer T; 
        static int clockSpeed;
        
        Train train=new Train();
        String[] switches={ "switch 1", "switch 2", "switch 3"};
        String[] crossings={ "crossing 1", "crossing 2", "crossing 3"};
        private int[] speeds;
        private int numTrains=0;
        String filename;
        
        TrackSegment[] redSegments;
        
        private static final String[] clockSpeedOptions = { "Normal Clockspeed", "10 Times Clockspeed"};
        
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
        
        public TrackModel()
	{
            JFrame frame2 = new JFrame("Input File"); //get excel file
            String inFile = JOptionPane.showInputDialog(frame2, "Choose a file to load for the Track Model");
            if (inFile.equals("")){
                inFile=("Track Layout & Vehicle Data vF1.xlsx"); //automatic mode
            }
            new TrackModel(inFile);
        }
        
        public TrackModel(String filename) //New track model, specified settings
        {
            //READ FILE - red traces
            File excelFile=new File(filename); //excel file
            //BLOCK NUM - INT
            //BLOCK LENGTH - M
            //BLOCK GRADE - %
            //SPEED LIM - KM/HR
            //ELEVATION - M
            trackModelUI.addTrack();
	}
        
        
        public void getSchedule()
        {
            List<Schedule> scheduleFile = ExcelUtil.readSchedule("schedule.xlsx");
            int i = 1;
            for(Schedule x :scheduleFile){
		String data = x.getLine()+" "+x.getAuthority()+" "+x.getAuthsequence().replaceAll(",", " ")
			+" "+ x.getSpeedsequence().replaceAll(",", " ");
			ScheduleUtil.saveTxt(content);
			System.out.println(s.toString());
			i++;
			this.resultList.add(s);	
            }
        }
}