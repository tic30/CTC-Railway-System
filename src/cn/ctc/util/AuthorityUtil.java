package cn.ctc.util;

import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cn.ctc.DispatchFrame;

public class AuthorityUtil {
	private static String content1 = "";
	
	public static void saveTxt(String content) {
		try {
			File file = new File("newEvent.txt");
			FileWriter fileWritter = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(content+"\r\n");
			bufferWritter.close();
			System.out.println(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		content1 = content;
	}
	//to be called by track model
	public static String getNewAuthority(){
		return content1;
	}
	
	public static void main(String[] args) {
		AuthorityUtil.saveTxt("123");saveTxt("456");saveTxt("87 abc");
		System.out.println(getNewAuthority());
	}

}
