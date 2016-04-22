/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redpanda.traincontrolsystem.trackmodelstandalone;

import org.redpanda.traincontrolsystem.trainmodel.Train;

import javax.swing.Timer;
import java.awt.event.*;

public class TrainFollower implements ActionListener {
        Train train;
        int clockspeed;
        int trainNum;
        String railcolor;
        int speed;
        int[] authority;
        double[] lengths;
        int blockstraveled=0;
        TrackSegment[] railsegments;
        String dispatchtime;
        int currBlock;
        int s, min, hr;
        int[] segtimes, segtimestarts;
        boolean moving, endReached;
        
        private Timer T;
    
        // TrainFollower(color,d,auths,lens,sender); //railcolor, dispatch time, authority array, authority lengths, rail segments
        public  TrainFollower(int cs, String rc, String d, int[] a, double[] l, TrackSegment[] ts, int sp) { 
            this.clockspeed=cs;
            this.train=new Train();
            this.railcolor=rc;
            this.dispatchtime=d;
            this.authority=a;
            this.lengths=l;
            this.railsegments=ts;
            this.speed=sp;
            /*int stringlen=d.length();
            dispatchsec=Integer.parseInt(d.substring(stringlen-2, stringlen-1));
            dispatchmin=Integer.parseInt(d.substring(stringlen-5, stringlen-4));
            dispatchhr=Integer.parseInt(d.substring(stringlen-7));*/
            
            segtimes=new int[lengths.length];
            segtimestarts=new int[lengths.length];
            
            //time intervals in order
             int t=0;
             for (int i=0;i<lengths.length;i++)
             {
                 segtimestarts[i]=t+segtimes[i];
                 t=segtimestarts[i];
             }
            
            s=0; min=0; hr=0;
            endReached=false;
            moving=true;
            if (clockspeed==1){
                T=new Timer(1000, this);
            }
            if (clockspeed==10){
                T=new Timer(1000, this);
            }
        }
        
            public void actionPerformed(ActionEvent e) 
	{
                if (moving)
                {
                    s=s+1;
                    this.train.setSpeed(speed);
                    for (int i=0;i<authority.length-1;i++)
                    {
                        if (s==segtimestarts[i])
                        {
                            currBlock=authority[i];
                            this.train.setAuthority(authority.length-i);
                        }
                        if (s==segtimestarts[authority.length-1])
                        {
                            //sendBeaconInfo();
                        }
                        if (s==segtimestarts[authority.length-1])
                        {   
                            this.train.setAuthority(0);
                            this.train.setSpeed(0);
                            endReached=true;
                            moving=false;
                            System.out.println("end reached");
                            T.stop();
                        }
                    }
                }
        }
        
        public Train getTrain(){
            return train;
        }
        
        public String getCurrBlock(){
            StringBuilder s=new StringBuilder(railcolor);
            s.append(currBlock);
            return s.toString();
        }

        
        public void endFollower(){
            this.train=null;
        }
        
        public void STOP(){
            train.setAuthority(0);
            train.setSpeed(0);
        }
}
