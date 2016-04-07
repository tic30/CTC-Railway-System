package org.redpanda.traincontrolsystem.trackmodel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.util.Arrays;

	
public class tmp4 
{	
	private JPanel bottomHalf=new JPanel();
        private JPanel infoPanel1=new JPanel();
        private JPanel infoPanel2=new JPanel();
        private JPanel inputPanel=new JPanel();
        private JPanel warningPanel=new JPanel();
        private JTextField inputFile, inputTemp,inputMaxPass;
        private String fileName, newTemp, newPassLim;
        private PrototypeTrack trackPanel;  
	private JFrame window; //frame for whole window
        private ActionListener theListener;
        private int[] lengths; 
        private int numSegs,trainNum;
        private char[] segNames;
        private int totLen=0, maxPassNum, temp;
        private Timer master;//will be used for interaction purposes  
        private Train[] trains;
        
        private String[] crossings; //for pull down menu
        private String[] switches; // for pull down menu
        private int brokenItems=0;

	public static void main(String [] args) //no args now
	{
                new tmp4();  
	}
        
        public tmp4()
	{
                //CREATE RANDOMIZED VARIABLES WHERE NECESSARY
                    //inital temp
                temp=ThreadLocalRandom.current().nextInt(20, 85 + 1);
                maxPassNum=ThreadLocalRandom.current().nextInt(100, 150 + 1);
                    //max pass num
            
                //read in excel file OR
                //randPrototypeTrack(); 
                sysPrototypeTrack();
                trains=new Train[10];//assume 10 or less trains at first 
                    //getSchedule();
                    //send info to Brian
                
                window = new JFrame("System Prototype - Track Model");
                window.setLayout(new GridLayout(2,1)); //3 rows, one column
                
                //TRACK PANEL FOR IMAGE
                trackPanel = new PrototypeTrack(segNames,lengths); //passes all segment lengths
                
                //INFO PANEL 1 - stations and crossings
                infoPanel1.setLayout(new GridLayout(1,2));
                infoPanel1.add(new JLabel("Stations: S1\t\t S2\n\n\n"));
                infoPanel1.add(new JLabel("Crossings: C1\t\t C2\t\t C3\n\n\n"));
                
                //INFO PANEL 2 - temp, curr pass number, warnings
                infoPanel2.setLayout(new GridLayout(1,4));
                infoPanel2.add(new JLabel("Temperature:" + temp));
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
                
                //INPUT PANEL 2 - inputfile temp, max pass number
                inputPanel.setLayout(new GridLayout(1,3));
                
                inputFile=new JTextField("Track Layout File", 100);
                inputFile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {     
                        fileName = inputFile.getText();}});
                
                inputTemp=new JTextField("Modify Temperature",3);
                inputTemp.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {     
                        newTemp = inputTemp.getText();
                        System.out.println("New temp is " + newTemp);}});
                
                inputMaxPass=new JTextField("Modify Passenger Limit",4);
                inputMaxPass.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {     
                        newPassLim = inputMaxPass.getText();}});
                
                    //add everything
                inputPanel.add(inputFile);
                inputPanel.add(inputTemp);
                inputPanel.add(inputMaxPass);
                
                getBrokenComponents();
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

	public void randPrototypeTrack(){ //random - for trial and error
            numSegs=ThreadLocalRandom.current().nextInt(5, 10 + 1); //between 5 and 10 segs created
            lengths=new int[numSegs];
            for(int i=0;i<numSegs;i++){
                lengths[i]=ThreadLocalRandom.current().nextInt(10, 100 + 1);
                totLen=totLen+lengths[i]+75;
            }  
        } 
        public void sysPrototypeTrack(){ //predetermined for systemp prototype
            numSegs=15;
            segNames="ABCDEFGHIJKLMN".toCharArray();
            lengths=new int[] {100,200,100,200,100,300,100,100,200,300,100,200,200,100,300};
        }
        
        public void getSchedule() //from CHU
        {
            String line=null; 
            trainNum=0;
            //schedule.txt has format trainNo + ‘ ‘ +authority+ ‘ ‘ +authSequence+ ' '+speed sequence
            try {
                FileReader myReader = new FileReader("schedule.txt");
                BufferedReader buff = new BufferedReader(myReader);

                while((line = buff.readLine()) != null) //while more to read
                {
                    String[] info=line.split(" "); //info array will contain everything, read until null
                    trainNum=trainNum+1;
                    if (trainNum>=trains.length)
                    {
                        Train[] tempArray=new Train[trains.length*2];
                        tempArray=Arrays.copyOf(trains,trains.length);
                        trainNum=trains.length;
                        trains=new Train[tempArray.length];
                        trains=Arrays.copyOf(tempArray,trainNum);
                    }
                    //trains[trainNum].name=new Train(info[0],maxPassNum);
                    //trains[trainNum].giveAuth(info[1]); //to whatever
                    //trains[trainNum].giveSpeed(info[2]);//change to whatever
                }   
                buff.close();        
            }
            catch(FileNotFoundException e) 
            {
                System.out.println(
                    "Unable to open file '" + 
                    fileName + "'");                
            }
            catch(IOException e){
                System.out.println("Input/Output exception - cannot read.\n");
            }
        }
        
        public void getBrokenComponents(){
            
        }
}



