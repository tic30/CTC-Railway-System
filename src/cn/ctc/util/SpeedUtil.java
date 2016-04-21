package cn.ctc.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SpeedUtil {
	private static String content1 = "";
	public static void saveTxt(String content) {
		//int again=1;
		//while (again==1)
		try {
			File file = new File("newEvent.txt");
			FileWriter fileWritter = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(content+"\r\n");
			bufferWritter.close();
			//again=0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			/*
			again=1;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}*/
		}
		content1 += content + "\n";
	}
	//to be called by track model
	public static String getNewSpeed(){
		return content1;
	}
	
	public static void main(String[] args) {
		SpeedUtil.saveTxt("123");saveTxt("456");saveTxt("87 abc");
		System.out.println(getNewSpeed());
	}
	

}
