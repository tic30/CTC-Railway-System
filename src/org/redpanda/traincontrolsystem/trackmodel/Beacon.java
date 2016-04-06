package org.redpanda.traincontrolsystem.trackmodel;

public class Beacon 
{ 
	//may not need all of these
        String name;
        String[] info;
        int distToStation;
        
        
        public Beacon(String n)
        {
		name=n; //train name
                distToStation=100; //meters
        }
        
        public void getInfo()
        {
            //return 64 bits
        }
}




