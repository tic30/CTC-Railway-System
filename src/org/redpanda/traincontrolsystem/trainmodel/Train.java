// Train.java
// Author: Brian McDonald

package org.redpanda.traincontrolsystem.trainmodel;

/*******************************************************
 *  Class name: Train
 *  Inheritance: None
 *  Attributes: None
 *  Methods:
 *  	public int getID()
 *  	public int getCarCount()
 *  	public int getCrewCount()
 *  	public void setCrewCount(int)
 *  	public int getPassengerCount()
 *  	public double getSpeed()
 *  	public void setSpeed(double)
 *  	public double getSetpointSpeed()
 *  	public double getAuthority()
 *  	public void setAuthority(int)
 *  	public double getTrackGrade()
 *  	public void setTrackGrade(double)
 *  	public void setPower(double)
 *  	public double getLastPowerCommand()
 *  	public double getCurrentPower()
 *  	public boolean doorsOpen()
 *  	public boolean lightsOn()
 *  	public boolean getEngineFailure()
 *  	public void setEngineFailure(boolean)
 *  	public boolean getEngineFailure()
 *  	public void setEngineFailure(boolean)
 *  	public boolean getSignalFailure()
 *  	public void setSignalFailure(boolean)
 *  	public boolean getEBrakeEngaged()
 *  	public void setEBrakeEngaged(boolean)
 *  Functionality: Model all functionality of a real train according to the
 *  	requirements document
 *  Visibility: public
 *  From requirement number: 3.2.2 Train Model
  *******************************************************/
public class Train {
	private static TrainModelUI ui;		// keep single instance of UI
	private static int trainCount = 0;	// keep track of number of active trains
	
	private int id;							// unique id for train
	private int carCount = 1;				// number of cars in train
	private int crewCount = 4;				// number of crew members on train
	private int passengerCount = 45;		// number of passengers on train
	private int authority = 50;				// current authority of train
	private double speed = 0;				// current speed of train
	private double setpointSpeed = 30;		// current setpoint speed of train
	private double trackGrade = 2;			// grade of the current piece of track
	private double power = 0;				// current power output of train
	private double powerCommand = 0;		// last commanded power for train
	private boolean doorsOpen = false;		// doors open/closed status
	private boolean lightsOn = false;		// lights on/off failure
	private boolean engineFailure = false;	// engine status
	private boolean signalFailure = false;	// signal pickup status
	private boolean brakeFailure = false;	// brake status
	private boolean eBrakeEngaged = false;	// emergency brake status
	
	public Train() {
		if(ui == null) {
			ui = TrainModelUI.getInstance();
		}
		
		id = ++trainCount;
		
		ui.addTrain(this);
	}
	
	/*******************************************************
	 *  Method name: getID
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide train's unique id
	 *  Visibility: public
	 *  @param:
	 *  @return: int Unique id for train
	 *  From requirement number 3.2.2 Train Model
	*******************************************************/
	public int getID() {
		return id;
	}
	
	/*******************************************************
	 *  Method name: getCarCount
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide number of cars in train
	 *  Visibility: public
	 *  @param:
	 *  @return: int Number of cars in train
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public int getCarCount() {
		return carCount;
	}
	
	/*******************************************************
	 *  Method name: getCrewCount
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide number of crew members on train
	 *  Visibility: public
	 *  @param:
	 *  @return: int Number of crew members on train
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public int getCrewCount() {
		return crewCount;
	}
	
	/*******************************************************
	 *  Method name: setCrewCount
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Set number of crew members on train
	 *  Visibility: public
	 *  @param: count Number of crew members on train
	 *  @return:
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public void setCrewCount(int count) {
		crewCount = count;
	}
	
	/*******************************************************
	 *  Method name: getPassengerCount
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide number of passengers on train
	 *  Visibility: public
	 *  @param:
	 *  @return: int Number of passengers on train
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public int getPassengerCount() {
		return passengerCount;
	}
	
	/*******************************************************
	 *  Method name: getSpeed
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide current speed of train
	 *  Visibility: public
	 *  @param:
	 *  @return: double Current speed of train
	 *  From requirement number 3.2.2.1 Physical Modeling
	*******************************************************/
	public double getSpeed() {
		return speed;
	}
	
	/*******************************************************
	 *  Method name: setSpeed
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Set the suggested speed for the train
	 *  Visibility: public
	 *  @param: speed Suggested speed for the train
	 *  @return:
	 *  From requirement number 3.2.2.1 Physical Modeling
	*******************************************************/
	public void setSpeed(double speed) {
		setpointSpeed = speed;
	}
	
	/*******************************************************
	 *  Method name: getSetpointSpeed
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide last received setpoint speed of train
	 *  Visibility: public
	 *  @param:
	 *  @return: double Last received setpoint speed of train
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public double getSetpointSpeed() {
		return setpointSpeed;
	}
	
	/*******************************************************
	 *  Method name: getAuthority
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide last received authority of train
	 *  Visibility: public
	 *  @param:
	 *  @return: int Last received authority of train
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public int getAuthority() {
		return authority;
	}
	
	/*******************************************************
	 *  Method name: setAuthority
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Set the authority for the train
	 *  Visibility: public
	 *  @param: authority Authority for train
	 *  @return:
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public void setAuthority(int authority) {
		this.authority = authority;
	}
	
	/*******************************************************
	 *  Method name: getTrackGrade
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide the grade of the current track
	 *  Visibility: public
	 *  @param:
	 *  @return: double Grade of the track
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public double getTrackGrade() {
		return trackGrade;
	}
	
	/*******************************************************
	 *  Method name: setTrackGrade
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Set the grade of the current track
	 *  Visibility: public
	 *  @param: grade Grade of the current track
	 *  @return:
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public void setTrackGrade(double grade) {
		trackGrade = grade;
	}
	
	/*******************************************************
	 *  Method name: setPower
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Set the power command for the train
	 *  Visibility: public
	 *  @param: power Power command for the train
	 *  @return:
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public void setPower(double power) {
		powerCommand = power;
		this.power = power;
	}
	
	/*******************************************************
	 *  Method name: getLastPowerCommand
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide the last commanded power for the train
	 *  Visibility: public
	 *  @param:
	 *  @return: double Last commanded power for the train
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public double getLastPowerCommand() {
		return powerCommand;
	}
	
	/*******************************************************
	 *  Method name: getCurrentPower
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide the current power output of the train
	 *  Visibility: public
	 *  @param:
	 *  @return: double Current power output of the train
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public double getCurrentPower() {
		return power;
	}
	
	/*******************************************************
	 *  Method name: doorsOpen
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide the status of the train doors: open or closed
	 *  Visibility: public
	 *  @param:
	 *  @return: boolean true if doors are open, false if doors are closed
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public boolean doorsOpen() {
		return doorsOpen;
	}
	
	/*******************************************************
	 *  Method name: lightsOn
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provude status of train lights: on or off
	 *  Visibility: public
	 *  @param:
	 *  @return: boolean true of lights are on, false if lights are off
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public boolean lightsOn() {
		return lightsOn;
	}
	
	/*******************************************************
	 *  Method name: getEngineFailure
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide status of engine: normal or failed
	 *  Visibility: public
	 *  @param:
	 *  @return: boolean true if engine has failed, false if engine is normal
	 *  From requirement number 3.2.2.6 Failure Modes
	*******************************************************/
	public boolean getEngineFailure() {
		return engineFailure;
	}
	
	/*******************************************************
	 *  Method name: setEngineFailure
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Set status of the engine: normal or failed
	 *  Visibility: public
	 *  @param: status true for failure, false for normal operations
	 *  @return:
	 *  From requirement number 3.2.2.6 Failure Modes
	*******************************************************/
	public void setEngineFailure(boolean status) {
		engineFailure = status;
	}
	
	/*******************************************************
	 *  Method name: getSignalFailure
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide status of signal pickup: normal or failed
	 *  Visibility: public
	 *  @param:
	 *  @return: true if signal pickup has failed, false if working normally
	 *  From requirement number 3.2.2.6 Failure Modes
	*******************************************************/
	public boolean getSignalFailure() {
		return signalFailure;
	}
	
	/*******************************************************
	 *  Method name: setSignalFailure
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Set status of signal pickup: normal or failed
	 *  Visibility: public
	 *  @param: status true for failure, false for normal operations
	 *  @return:
	 *  From requirement number 3.2.2.6 Failure Modes
	*******************************************************/
	public void setSignalFailure(boolean status) {
		signalFailure = status;
	}
	
	/*******************************************************
	 *  Method name: getBrakeFailure
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide status of service brakes: normal or failed
	 *  Visibility: public
	 *  @param:
	 *  @return: true of service brakes have failed, false if working normally
	 *  From requirement number 3.2.2.6 Failure Modes
	*******************************************************/
	public boolean getBrakeFailure() {
		return brakeFailure;
	}
	
	/*******************************************************
	 *  Method name: setBrakeFailure
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Set status of service brakes: normal or failed
	 *  Visibility: public
	 *  @param: status true for failure, false for normal operations
	 *  @return:
	 *  From requirement number 3.2.2.6 Failure Modes
	*******************************************************/
	public void setBrakeFailure(boolean status) {
		brakeFailure = status;
	}
	
	/*******************************************************
	 *  Method name: getEBrakeEngaged
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide status of emergency brake: engaged or disengaged
	 *  Visibility: public
	 *  @param:
	 *  @return: true if engaged, false if disengaged
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public boolean getEBrakeEngaged() {
		return eBrakeEngaged;
	}
	
	/*******************************************************
	 *  Method name: setEBrakeEngaged
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Set status of emergency brake: engaged or disengaged
	 *  Visibility: public
	 *  @param: status true for engaged, false for disengaged
	 *  @return:
	 *  From requirement number 3.2.2.5 Inputs
	*******************************************************/
	public void setEBrakeEngaged(boolean status) {
		eBrakeEngaged = status;
	}
	
	public static void main(String[] args) {
		TrainModelUI.getInstance();
	}
}
