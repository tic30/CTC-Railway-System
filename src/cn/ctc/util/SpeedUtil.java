package cn.ctc.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SpeedUtil {
	
	public static void saveTxt(String content) {
		int again=1;
		while (again==1)
		try {
			File file = new File("newEvent.txt");
			FileWriter fileWritter = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(content+"\r\n");
			bufferWritter.close();
			again=0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			again=1;
			e.printStackTrace();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

	}

}
