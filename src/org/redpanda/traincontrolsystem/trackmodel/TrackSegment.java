import java.awt.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

class TrackSegment 
{
	boolean hasTrain=false,hasStation=false;
         int beginning,length,end,limit; 
         int timeLit, t, currPass;
         String segmentName, stationName, nextStop;
        

	public TrackSegment(String segname,int begin,int segLength, boolean train)
	{
		beginning=begin; length=segLength;
                end=beginning+length;
                limit=ThreadLocalRandom.current().nextInt(25, 50 + 1); //random speed limit in mi/hr
                hasTrain=train;
                segmentName=segname;
                timeLit= length/limit; //time= # miles /(speed miles/hr)
                    //will be equal to hours, but we will show in seconds
	}
        
        public void giveStation(String s) 
        {
            hasStation=true;
            stationName=s;
        }
        
        public void giveNextStop(String s2) 
        {
            nextStop=s2;
        }
        
        public int getTimeMag() //time train is in any block (magnitude
        {
            return timeLit;
        }
        
        public String getName() 
        {
            return stationName;
        }
	
	public void draw(Graphics2D g2)
	{
            g2.setStroke(new BasicStroke(4));
            if (!hasTrain)
            {
                g2.setColor(Color.red);
                g2.draw(new Line2D.Float(beginning, 100, end, 100));
            }
            else{
                g2.setColor(Color.blue);
                g2.draw(new Line2D.Float(beginning, 100, end, 100));
            }
            g2.drawString(segmentName,end-50, 120);
            g2.setFont(new Font("Times New Roman",Font.PLAIN,15));
            //g2.drawString(new String(limit + "mi/hr"),end-50, 140);
            //g2.drawString(new String(length + "mi"),end-50, 160);
            if (hasStation)
            {
                g2.setFont(new Font("Times New Roman",Font.BOLD,15));
                //g2.drawString(new String(segmentName),end-50, 180);
                g2.setFont(new Font("Times New Roman",Font.PLAIN,15));
            }
            g2.setStroke(new BasicStroke());
	}
}




