import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.event.WindowAdapter;

	
public class tmp3
{	
	private JPanel infoPanel=new JPanel();
        private Track trackPanel;  // StopWatch is a subclass of JPanel
	private JFrame window; //frame for whole window
        private JLabel temp; JLabel currPass;
        private int[] lengths=new int[6]; 
        private int totLen=0; int randomMaxPass, randomTemp, randomInitPass;
        //private StringBuilder s=new StringBuilder("Segment Lengths:");

	public static void main(String [] args) //no args now
	{
                new tmp3();  
	}
        
        public tmp3()
	{
		window = new JFrame("Track Model Prototype");
                window.setLayout(new GridLayout(3,1));
                randomTrack(); //gives lengths in mi, temp, max pass #, init pass #
                
                infoPanel.add(new JLabel("Outside Temperature is " + randomTemp + "\n\n\n"));
                infoPanel.add(new JLabel("Max number of passengers is " + randomMaxPass + "\n\n\n"));
                infoPanel.add(new JLabel("WARNINGS: \n\n\n"));
                
                trackPanel = new Track(lengths);  
                trackPanel.initialSettings(randomTemp,randomMaxPass,randomInitPass); //temp, pass limi, init pass
 
		Container c = window.getContentPane();
                c.add(trackPanel);//, BorderLayout.CENTER);
                c.add(infoPanel);
                
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(totLen,1000);
		window.setVisible(true);
	}

	public void randomTrack(){ //random lengths, temp, max pass #
            for(int i=0;i<6;i++){
                lengths[i]=ThreadLocalRandom.current().nextInt(50, 200 + 1);
                totLen=totLen+lengths[i]+75;
            }   
            randomTemp=ThreadLocalRandom.current().nextInt(20, 85 + 1);
            randomMaxPass=ThreadLocalRandom.current().nextInt(100, 150 + 1);
            randomInitPass=ThreadLocalRandom.current().nextInt(0, randomMaxPass+1);
        } 
}


