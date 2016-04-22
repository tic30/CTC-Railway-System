package org.redpanda.traincontrolsystem.trackmodelstandalone;


class TrackSegment 
{
         int blockNum;
         double length, grade, elevation, speedLim;
         boolean hasStation=false, hasSwitch=false, hasCrossing=false, isUnderground=false;;
         boolean hasTrackFailure, hasCircuitFailure;
         String stationName;
         boolean hasTrain;
         
         public TrackSegment(){
             this(0,0,0,0,0);
         }
         
         public TrackSegment(int bn, double sl, double l, double g, double e)
	{
            this.blockNum=bn; this.speedLim=sl; this.length=l; this.grade=g; this.elevation=e;
	}
        
        public void giveFailure(){
            this.hasTrackFailure=true;
        }
        
        public void giveStation(String s){
            this.stationName=s;
            this.hasStation=true;
        }
        
	/*public void draw(Graphics2D g2)
	{
            g2.setStroke(new BasicStroke(4));
            if (!hasTrain)
            {
                g2.setColor(Color.red);
                g2.draw(new Line2D.Float(x1/10, y1/10, x2/10, y2/10));
            }
            else{
                g2.setColor(Color.blue);
                g2.draw(new Line2D.Float(x1/10, y1/10, x2/10, y2/10));
            }
            g2.setStroke(new BasicStroke());
	}*/
}
