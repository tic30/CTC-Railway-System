package cn.ctc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.ctc.bean.Schedule;

public class Departure extends Thread{
	
	private MainFrame mainFrame;

	public Departure(MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
	}

	@Override
	public void run() {
		//mainFrame.textArea.setText("GREEN\t\t\tRED\t\t\tSwitches");
		while(true){
			//System.out.println("abc");
			if(mainFrame.resultList!=null && mainFrame.resultList.size()>0){
				String stemp="";
				for(Schedule s:mainFrame.resultList){
					//System.out.println(s.toString());
					stemp+=s.toString()+"\n";
				}
				mainFrame.textArea.setText(stemp);
			}
			
			// TODO Auto-generated method stub
			//super.run();
			//mainFrame.textArea.setText("1111");
			
			
		
			if(mainFrame.resultList!=null && mainFrame.resultList.size()>0){
				for(Schedule s: mainFrame.resultList){
					//System.out.println(s.isIsrun());
					if(s.isIsrun()){
						continue;
					}
					Object lock=new Object();   
				    synchronized (lock){//ÁÙ½çÇø
				    	String time = mainFrame.label.getText();
						//System.out.println(time);
						//System.out.println(s.getDeparturetime().equals(time));
						long timeD = parseTime(s.getDeparturetime());
						long timeC = parseTime(time);
						//System.out.println(Math.abs(timeD-timeC));
						
						if(Math.abs(timeD-timeC)<=500){
							//System.out.println(s.getDeparturetime().equals(time));
							//System.out.println(readFileByLines(s.getLine()));
							//mainFrame.textArea.setText(mainFrame.textArea.getText()+"\n"+readFileByLines(s.getLine()));
							//System.out.println("aaa:"+(mainFrame.textArea.getText()==null ||"".equals(mainFrame.textArea.getText().trim())));
							if(mainFrame.textArea.getText()==null ||"".equals(mainFrame.textArea.getText().trim())){
								mainFrame.textArea.setText(readFileByLines(s.getLine()));
							}else{
								mainFrame.textArea.setText(mainFrame.textArea.getText()+"\n"+readFileByLines(s.getLine()));
							}
							s.setIsrun(true);
						}
				    }
					
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	 public String readFileByLines(String traino) {
	        File file = new File("trackMap.txt");
	        BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            while ((tempString = reader.readLine()) != null) {
	                if(tempString.indexOf(traino)>=0){
	                	return tempString;
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	        
	        return traino+" -";
	    }
	 
	 //00:10:00 -28200000
	 public static long  parseTime(String time ) {
		try {
			java.text.SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			Date date = format.parse(time);
			return date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0l;
	}
	
}
