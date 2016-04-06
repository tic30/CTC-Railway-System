import java.util.concurrent.ThreadLocalRandom;

public class Train 
{ 
	//may not need all of these
        String name, currBlock;
        int[] authSeq, speedSeq;
        int occupancy, currSpeed;
        int[] route; //boolean?
        
        
        public Train(String n, int max)
        {
		name=n; //train name
                occupancy=ThreadLocalRandom.current().nextInt(0, max + 1);
        }
        
        public void giveAuth(int[] a)
        {
            authSeq=a;
        }
        
        public void giveSpeed(int[] s)
        {
            speedSeq=s;
        }
}




