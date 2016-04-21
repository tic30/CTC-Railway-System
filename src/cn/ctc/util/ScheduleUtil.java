package cn.ctc.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ScheduleUtil {
	public static ArrayList<String> contentOutput = new ArrayList<String>();

	public static void saveTxt(String content) {
		try {
			File file = new File("schedule.txt");
			FileWriter fileWritter = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(content+"\r\n");
			bufferWritter.close();
			contentOutput.add(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//to be called by track model
	public static ArrayList<String> getSchedule(){
		return contentOutput;
	}
	
	public static void main(String[] args) {
		ScheduleUtil.saveTxt("Writing Test Success");
	}

}
