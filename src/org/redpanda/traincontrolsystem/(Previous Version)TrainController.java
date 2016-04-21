/*
Simranjot Pabla
TRAIN CONTROLLER FROM REDPANDACODING
Goals:
	1.Allow driver to enter desired speed
	2.Read in current speed
	3.Calculate power command to be sent to Train Model
	4.Decode signal from track circuit to display given speed and authority
	5.Display train information to driver
	7.Ensure driver entered speed doesn't go over speed limit as well as given authority
	6.Control Non-Vital operations
		a.Doors open at stations automatically and also upon manual button press
		b.Lights turn on at stations as well as in tunnels, but also on manual button press
		c.Stations are announced at correct time
*/

package org.redpanda.traincontrolsystem;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.IOException;
import javax.swing.*;

import org.redpanda.traincontrolsystem.trainmodel.Train;

public class TrainController
{
	//The following variables will be used throughout the lifespan of a train after it has been launched
	private Train Tmodel;
	private double maxPower, Tsample, Kp, Ki, uvar, prevuvar, prevpower, preverror;
	private int ID;
	private String[] stations;
	//The following variables handle the UI that the driver of the train can interact with
	private JButton MoveTrain, BrakeTrain, TrainInfo, NonVitals, EBrakeTrain, Disengage; //This entire section creates the various components that need to be displayed
	private JButton OpenDoors, TurnLights;
	private JFrame theWindow;
	private JPanel mainpanel, buttonpanel, infopanel, nonvitpanel;
	private JTextArea trainstatus, traininfo, currentstatus, ctccommand, alertarea, randarea;
	private JTextArea infosec1, infosec2;
	private JButton doorcontrol, lightcontrol;
	private ControlListener theListener;
	private Container thePane;
	//The following variables are used to hold important information like current speed and whether or not the train has any faults
	private double speed, authority, givenspeed, currspeed, power;
	private String authority1;
	private boolean brake, ebrake;
	private boolean brakebroken, sigbroken, engbroken;
	private boolean doorstatus, lightstatus;
	//These variables will be the timer that allow for automatic updates
	private javax.swing.Timer TCTimer;
	private ActionListener timerlistener;
	
	public TrainController(Train ConnectedTrain) throws IOException
	{
		//Set up variables needed for train operation to begin
		Tmodel = ConnectedTrain;
		maxPower = 120000;
		Kp = 10000;
		Ki = 200;
		Tsample = .1;
		givenspeed = 0;
		speed = 0;
		currspeed = 0;
		authority = 0;
		power = 0;
		prevpower = 0;
		uvar = 0;
		prevuvar = 0;
		preverror = 0;
		//At the beginning, the train doesn't have any faults
		brakebroken = false;
		sigbroken = false;
		engbroken = false;
		brake = false;
		ebrake = false;
		doorstatus = false;
		lightstatus = false;
		//Set up the buttons that will be the source for driver interaction
		MoveTrain = new JButton("Enter Speed"); //This section creates the buttons I will need
		BrakeTrain = new JButton("Brakes");
		TrainInfo = new JButton("Train Info");
		NonVitals = new JButton("Non-vital Contols");
		EBrakeTrain = new JButton("Emergency Brakes");
		OpenDoors = new JButton("Door controls");
		TurnLights = new JButton("Light controls");
		Disengage = new JButton("Disengage brakes");
		
		buttonpanel = new JPanel(); //This creates the other two panels to be added to the frame
		buttonpanel.setLayout(new GridLayout(6,1));
		mainpanel = new JPanel();
		mainpanel.setLayout(new GridLayout(3,2));
		infopanel = new JPanel();
		infopanel.setLayout(new GridLayout(2,1));
		nonvitpanel = new JPanel();
		nonvitpanel.setLayout(new GridLayout(2,1));
		
		//Set up the visual display for the driver in the beginning
		trainstatus = new JTextArea("Train ID:  "); //This creates the various labels that show the user where information is
		traininfo = new JTextArea("CTC Given Info:");
		currentstatus = new JTextArea("Current Speed: Numbers!! \n \nCurrent Authority: Numbers!! \n");
		ctccommand = new JTextArea("Speed: Numbers again!   \n \nAuthority: Numbers again! \n");
		alertarea = new JTextArea("");
		randarea = new JTextArea("");
		
		mainpanel.add(trainstatus); //Here I add all of the areas to the middle panel
		mainpanel.add(currentstatus);
		mainpanel.add(traininfo);
		mainpanel.add(ctccommand);
		mainpanel.add(alertarea);
		mainpanel.add(randarea);
		
		theListener = new ControlListener(); //This creates the control listener and then I add it to the buttons and text field
		MoveTrain.addActionListener(theListener);
		BrakeTrain.addActionListener(theListener);
		TrainInfo.addActionListener(theListener);
		NonVitals.addActionListener(theListener);
		EBrakeTrain.addActionListener(theListener);
		OpenDoors.addActionListener(theListener);
		TurnLights.addActionListener(theListener);
		Disengage.addActionListener(theListener);
		
		buttonpanel.add(MoveTrain); //Here I set up the initial button panel
		buttonpanel.add(BrakeTrain);
		buttonpanel.add(TrainInfo);
		buttonpanel.add(NonVitals);
		buttonpanel.add(EBrakeTrain);
		buttonpanel.add(Disengage);
		nonvitpanel.add(OpenDoors);
		nonvitpanel.add(TurnLights);
		
		theWindow = new JFrame("TrainController"); //This creates the window which will house everything
		theWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		thePane = theWindow.getContentPane();
		thePane.add(mainpanel, BorderLayout.CENTER);
		thePane.add(buttonpanel, BorderLayout.WEST);
		
		alertarea.setBackground(Color.green);
		theWindow.pack();
		theWindow.setVisible(true);
		
		//The following is the timer and listener that will just update information every 100 milliseconds
		timerlistener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				getUpdates();
			}
		};
		
		TCTimer = new javax.swing.Timer(100, timerlistener);
		TCTimer.start();
		getIDTC();
	}
	
	//The following class holds the function that handles all of the driver input and updates the display accordingly
	private class ControlListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Component theEventer = (Component) e.getSource();    //This will allow the train to read button clicks as well as
			//This will handle the event that the driver has pushed the move train button
			if(theEventer == MoveTrain)
			{	
				String speed1 = JOptionPane.showInputDialog("Please enter a speed");
				speed = Double.parseDouble(speed1);
				if(speed > givenspeed)
				{
					JOptionPane.showMessageDialog(null, "Please enter a speed less than " + String.valueOf(givenspeed));
					speed = givenspeed;
				}
				String temp = "Current Speed: " + String.valueOf(currspeed) + "\n\nCurrent Authority: " + String.valueOf(authority);
				currentstatus.setText(temp);
				brake = false;
				ebrake = false;
			}
			else if(theEventer == BrakeTrain)
			{
				ebrake = false;
				brake = true;
				JOptionPane.showMessageDialog(null, "Brakes engaged, hit disengage to disengage brakes.");
			}
			else if(theEventer == TrainInfo)
			{
				thePane.remove(nonvitpanel);
				thePane.add(mainpanel, BorderLayout.CENTER);
				theWindow.pack();
			}
			else if(theEventer == NonVitals)
			{
				thePane.remove(mainpanel);
				thePane.add(nonvitpanel, BorderLayout.CENTER);
				theWindow.pack();
			}
			else if(theEventer == EBrakeTrain)
			{
				brake = false;
				ebrake = true;
				JOptionPane.showMessageDialog(null, "Emergency brakes engaged, hit disengage to disengage emergency brakes.");
			}
			else if(theEventer == Disengage)
			{
				brake = false;
				ebrake = false;
			}
			else if(theEventer == OpenDoors)
			{
				if(doorstatus == true)
					setDoors(false);
				else
					setDoors(true);
			}
			else if(theEventer == TurnLights)
			{
				if(lightstatus == true)
					setLights(false);
				else
					setLights(true);
			}
		}
	}
	
	public void sendPowerTC()
	{
		Tmodel.setPower(power);
	}
	
	public void getIDTC()
	{
		ID = Tmodel.getID();
		trainstatus.setText("Train ID:  " + String.valueOf(ID));
	}
	
	public void getUpdates()
	{
		//Get the updates from the train model
		currspeed = Tmodel.getSpeed();
		givenspeed = Tmodel.getSetpointSpeed();
		authority = Tmodel.getAuthority();
		engbroken = Tmodel.getEngineFailure();
		sigbroken = Tmodel.getSignalFailure();
		brakebroken = Tmodel.getBrakeFailure();
		doorstatus = Tmodel.doorsOpen();
		lightstatus = Tmodel.lightsOn();
		
		//Update traincontroller variables
		UpdateTC();
	}
	
	public void UpdateTC()
	{
		//First check updates to see if train has broken
		boolean broken = checkTrain();
		if(broken == true)
		{
			engageEBrakeTC(true);
			alertarea.setBackground(Color.red);
		}
		else
		{
			engageEBrakeTC(false);
			alertarea.setBackground(Color.green);
		}
		
		if(brake == true)
			engageBrakeTC(true);
		else
			engageBrakeTC(false);
		
		String temp = "Current Speed: " + String.valueOf(currspeed) + "\n\nCurrent Authority: " + String.valueOf(authority) + "\n";
		currentstatus.setText(temp);
		temp = "Speed: " + String.valueOf(givenspeed) + "\n\nAuthority: " + String.valueOf(authority) + "\n";
		ctccommand.setText(temp);
		
		convertSpeed();
		
		if(authority == 0)
		{
			brake = true;
			MoveTrain.setEnabled(false);
			Disengage.setEnabled(false);
			setDoors(true);
		}
		else
		{
			setDoors(false);
			MoveTrain.setEnabled(true);
			Disengage.setEnabled(true);
		}
	}
	
	public void engageBrakeTC(boolean command)
	{
		if(command == true)
			MoveTrain.setEnabled(false);
		else
			MoveTrain.setEnabled(true);
		
		Tmodel.setBrakeEngaged(command);
	}
	
	public void engageEBrakeTC(boolean command)
	{
		if(command == true)
			MoveTrain.setEnabled(false);
		else
			MoveTrain.setEnabled(true);
			
		Tmodel.setEBrakeEngaged(command);
	}
	
	private void convertSpeed()
	{
		double error = speed - currspeed;
		
		if(error >= 0)
		{
			brake = false;
			if(prevpower < maxPower)
			{
				uvar = prevuvar + ((Tsample/2) * (error + preverror));
			}
			else
			{
				uvar = prevuvar;
			}
			
			power = (Kp * error) + (Ki * uvar);
			
			if(power > maxPower)
				power = maxPower;
		}
		else
		{
			power = 0;
			brake = true;
		}
		
		prevuvar = uvar;
		preverror = error;
		prevpower = power;
		
		sendPowerTC();
	}
	
	public void setDoors(boolean command)
	{
		//
		if(command == true)
			Tmodel.openDoors();
		else
			Tmodel.closeDoors();
	}
	
	public void setLights(boolean command)
	{
		//
		if(command == true)
			Tmodel.turnLightsOn();
		else
			Tmodel.turnLightsOff();
	}
	
	public boolean checkTrain()
	{
		boolean temp = false;
		if(engbroken == true || sigbroken == true || brakebroken == true)
				temp = true;
		return temp;
	}
}
