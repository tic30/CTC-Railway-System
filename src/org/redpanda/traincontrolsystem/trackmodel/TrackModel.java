import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.util.Arrays;

	
public class TrackModel 
{	
	private JPanel bottomHalf=new JPanel();
        private JPanel infoPanel1=new JPanel();
        private JPanel infoPanel2=new JPanel();
        private JPanel inputPanel=new JPanel();
        private JPanel warningPanel=new JPanel();
        private JButton toggleOrientation;
        boolean toggle=false;
        private JTextField inputFile, inputTemp,inputMaxPass;
        private String fileName, newTemp, newPassLim;
        private Track trackPanel;  
	private JFrame window; //frame for whole window
        private int[] lengths, speedLimits, grades, x1s,x2s,y1s, y2s; 
        private int numSegs,trainNum;
        private char[] segNames;
        private int totLen=0, maxPassNum, temp;
        private Train train;
        
        private String[] crossings; //for pull down menu
        private String[] switches; // for pull down menu
        private int brokenItems=0;

	public static void main(String [] args) //no args now
	{
                new TrackModel();  
	}
        
        public TrackModel()
	{
                temp=85; //initial temp
                maxPassNum=ThreadLocalRandom.current().nextInt(100, 150 + 1);
                
                window = new JFrame("System Prototype - Track Model");
                window.setLayout(new GridLayout(2,1)); //3 rows, one column
                
                //TRACK PANEL FOR IMAGE
                sysPrototypeTrack();
                
                //set train speed to min of speeds.
                trackPanel = new PrototypeTrack(segNames,lengths,x1s,x2s,y1s,y2s,speedLimits,grades, maxPassNum);
                
                //INFO PANEL 1 - stations and crossings
                infoPanel1.setLayout(new GridLayout(1,2));
                toggleOrientation=new JButton("Toggle Switch Orientation");
                infoPanel1.add(toggleOrientation);
                infoPanel1.add(new JLabel("Crossings: Crossing 1 not activated"));
                
                //INFO PANEL 2 - temp, curr pass number, warnings
                infoPanel2.setLayout(new GridLayout(1,4));
                
                //INPUT PANEL 2 - inputfile temp, max pass number
                inputPanel.setLayout(new GridLayout(1,3));
                
                inputFile=new JTextField("Track Layout File", 100);
                inputFile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {     
                        fileName = inputFile.getText();
                        System.out.println("New input file is " + fileName);}});
                
                inputTemp=new JTextField("Modify Temperature",3);
                inputTemp.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent e) {     
                        newTemp = inputTemp.getText();
                        System.out.println("New temp is " + newTemp);
                        if (Integer.parseInt(newTemp) < 35){
                            System.out.println("Tempeature below 35 degrees - heating tracks");}
                        if (Integer.parseInt(newTemp) > 35){
                            System.out.println("Tempeature above 35 degrees - allow tracks to cool");}}});
                
                infoPanel2.add(new JLabel("Temperature: " + temp));
                if (temp<=35)
                {
                    infoPanel2.add(new JLabel("Rails Heated"));
                }
                else
                {
                    infoPanel2.add(new JLabel("Rails Not Heated"));
                }
                
                infoPanel2.add(new JLabel("Number Passengers on Train: 0"));
                infoPanel2.add(new JLabel("Max Number of Passengers is " + maxPassNum));
                
                inputMaxPass=new JTextField("Modify Passenger Limit",4);
                inputMaxPass.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {     
                        newPassLim = inputMaxPass.getText();
                        System.out.println("New max passenger limit is " + newPassLim);}});
                
                    //add everything
                inputPanel.add(inputFile);
                inputPanel.add(inputTemp);
                inputPanel.add(inputMaxPass);
                
                getBrokenComponents();
                
                
                bottomHalf.setLayout(new GridLayout(4,1));
                bottomHalf.add(infoPanel1);
                bottomHalf.add(infoPanel2);
                bottomHalf.add(inputPanel);
                bottomHalf.add(warningPanel);
                
                Container c = window.getContentPane();
                c.add(trackPanel);
                c.add(bottomHalf);
                
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(totLen,1000);
		window.setVisible(true);
	}
  
        
        public void getBrokenComponents(){
            if (brokenItems==0)
                {
                    warningPanel.add(new JLabel("WARNINGS: There are currently no problems with the system.\n"));
                }
                else
                {
                    StringBuilder w=new StringBuilder("Warnings: \n");
                    for (int i=0;i<brokenItems;i++)
                    {
                        w.append("problem");
                    }
                    warningPanel.add(new JLabel(w.toString()));
                }
        }
}

