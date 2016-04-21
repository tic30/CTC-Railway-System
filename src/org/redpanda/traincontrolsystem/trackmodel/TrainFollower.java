package org.redpanda.traincontrolsystem.trackmodel;

import org.redpanda.traincontrolsystem.trainmodel.Train;

import javax.swing.Timer;
import java.awt.event.*;

public class TrainFollower implements ActionListener {
        Train train;
        String dispatchTime;
        int clockSpeed, currBlock;
        private Timer T;
    
        public  TrainFollower() { //need to add stuff
            //this.train=new Train();
        }
        
        public Train getTrain(){
            return train;
        }
        
        public void getTrainPosition(){}

        public void actionPerformed(ActionEvent e) 
	{}
        
        public void endFollower(){
            this.train=null;
        }
}
