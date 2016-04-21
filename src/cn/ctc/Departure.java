package cn.ctc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.ctc.bean.Schedule;

import org.redpanda.traincontrolsystem.trackmodel.TrackModel;

public class Departure extends Thread{
	
	private MainFrame mainFrame;

	public Departure(MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
	}

	@Override
	public void run() {
		mainFrame.textArea.setText("Green Line");
		mainFrame.textArea1.setText("Red Line");
		mainFrame.textArea2.setText("Crossing Switch");
		String[] switchMap = this.readFileBySwitchMap();
		while(true){
			/*
			if(mainFrame.resultList!=null && mainFrame.resultList.size()>0){
				for(Schedule s:mainFrame.resultList){
					System.out.println(s.toString());
				}
			}
			*/
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
				    	String time = mainFrame.timelabel.getText();
						//System.out.println(time);
						//System.out.println(s.getDeparturetime().equals(time));
						//System.out.println(s.getDeparturetime());
						long timeD = parse(s.getDeparturetime());
						long timeC = parse(time);
						//System.out.println(Math.abs(timeD-timeC));
						
						if(Math.abs(timeD-timeC)<=500){
							//System.out.println(s.getDeparturetime().equals(time));
							String readLine = readFileByLines(s.getLine());
							//System.out.println(readLine);
							//mainFrame.textArea.setText(mainFrame.textArea.getText()+"\n"+readFileByLines(s.getLine()));
							if(s.getLine().toLowerCase().indexOf("g")>=0){
								if(mainFrame.textArea.getText()==null ||"".equals(mainFrame.textArea.getText().trim())){
									mainFrame.textArea.setText(readLine);
								}else{
									mainFrame.textArea.setText(mainFrame.textArea.getText()+"\n"+readLine);
								}
							}
							
							if(s.getLine().toLowerCase().indexOf("r")>=0){
								if(mainFrame.textArea1.getText()==null ||"".equals(mainFrame.textArea1.getText().trim())){
									mainFrame.textArea1.setText(readLine);
								}else{
									mainFrame.textArea1.setText(mainFrame.textArea1.getText()+"\n"+readLine);
								}
							}
							//System.out.println(this.readFileByTrackMap(switchMap));
							if(mainFrame.textArea2.getText()==null ||"".equals(mainFrame.textArea2.getText().trim())){
								mainFrame.textArea2.setText(this.readFileByTrackMap(switchMap));
							}else{
								mainFrame.textArea2.setText("Crossing Switch"+"\n"+this.readFileByTrackMap(switchMap));
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
	
	public String[] readFileBySwitchMap(){
		File file = new File("switchMap.txt");
        BufferedReader reader = null;
        String[] switchMap=new String[13];
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int i=0;
            while ((tempString = reader.readLine()) != null) {
            	if(i>=13){
            		break;
            	}
            	switchMap[i]=tempString;
            	i++;
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
        //for(int zz=0;zz<switchMap.length;zz++)
        	//System.out.print(switchMap[zz]+" ");
        return switchMap;        
	}
	
	public String readFileByTrackMap(String[] switchMap){
		File file = new File("trackMap.txt");
        BufferedReader reader = null;
        String trackMap="";
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int i=0;
            while ((tempString = reader.readLine()) != null) {
            	//System.out.println(tempString);
               if(i==1){
            	   trackMap=  tempString; 
            	   break;
               }
               
               i++;
               
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
        
        if(trackMap==null ||"".equals(trackMap)){
        	return "";
        }
        if(trackMap.trim().length()!=12){
        	//System.out.println(trackMap.trim().length());
        	return "";
        }
        
        //System.out.println(trackMap);
        
        String content ="";
        String s= "";
        for(int i=0;i<12;i++){
        	int index= Integer.parseInt(trackMap.substring(i,i+1));
        	 s = switchMap[i];
        	//System.out.println(s);
        	if(s==null ||"".equals(s.trim())){
        		return "";
        	}
        	
        	String ss[] =s.trim().split(" ");
        	if(i==0){
        		content=ss[index];
        	}else{
        		content=content+"\n"+ss[index];
        	}
        	
        	
        }
        
        //System.out.println(content);
        return content;
        
        
        
        
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
	            //read from track model
	            return traino + " " + trackmodel.getOccupancy(traino);
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
	 public static long  parse(String time ) {
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
	
	 
	 public static void main(String[] args) {
		 parse("0:00:01");
	}
}
