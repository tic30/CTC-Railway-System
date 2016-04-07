// Train.java
// Author: Brian McDonald

package org.redpanda.traincontrolsystem.trainmodel;

import org.redpanda.traincontrolsystem.timer.TrainTimer;
import org.redpanda.traincontrolsystem.timer.TrainTimerListener;

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
 *  	public double getMass()
 *  	public double getSpeed()
 *  	public void setSpeed(double)
 *  	public double getSetpointSpeed()
 *  	public double getAuthority()
 *  	public void setAuthority(int)
 *  	public double getTrackGrade()
 *  	public void setTrackGrade(double)
 *  	public void setPower(double)
 *  	public double getLastPowerCommand()
 *  	public boolean doorsOpen()
 *  	public void openDoors()
 *  	public void closeDoors()
 *  	public boolean lightsOn()
 *  	public void turnLightsOn()
 *  	public void turnLightsOff()
 *  	public void setBrakeEngaged()
 *  	public boolean getEngineFailure()
 *  	public void setEngineFailure(boolean)
 *  	public boolean getEngineFailure()
 *  	public void setEngineFailure(boolean)
 *  	public boolean getSignalFailure()
 *  	public void setSignalFailure(boolean)
 *  	public boolean getEBrakeEngaged()
 *  	public void setEBrakeEngaged(boolean)
 *  	public void update(int)
 *  	private void calculateSpeed(int)
 *  	public static void main(String[])
 *  Functionality: Model all functionality of a real train according to the
 *  	requirements document
 *  Visibility: public
 *  From requirement number: 3.2.2 Train Model
  *******************************************************/
public class Train implements TrainTimerListener {
	private static final double G_ACCEL = 9.8;					// acceleration due to gravity in m/s^2
	private static final int PASSENGER_MASS = 68;				// passenger mass in kg
	private static final int CAR_CAPACITY = 222;				// maximum number of passengers that can fit in a car
	private static final double CAR_MASS = 40.9;				// empty car mass in kg
	private static final double MAX_SPEED = 70.0;				// maximum speed for train in km/h
	private static final double MAX_ACCEL = 0.5;				// maximum acceleration rate for train in m/s^s
	private static final double MAX_SERVICE_DECEL = 1.2;		// maximum deceleration rate for services brake in m/s^2
	private static final double MAX_EMERGENCY_DECEL = 2.73;		// maximum deceleration rate for emergency brake in m/s^2
	
	private static TrainModelUI ui;		// keep single instance of UI
	private static int trainCount = 0;	// keep track of number of active trains
	
	private int id;									// unique id for train
	private int carCount = 1;						// number of cars in train
	private int crewCount = 1;						// number of crew members on train
	private int passengerCount = 0;					// number of passengers on train
	private int passengerCapacity = 0;				// max number of passengers the train can hold
	private int authority = 0;						// current authority of train
	private double speed = 0;						// current speed of train in km/hr
	private double setpointSpeed = 0;				// current setpoint speed of train
	private double acceleration = 0;				// current acceleration of train
	private double trackGrade = 0;					// grade of the current piece of track
	private double powerCommand = 0;				// last commanded power for train
	private boolean doorsOpen = false;				// doors open/closed status
	private boolean lightsOn = false;				// lights on/off failure
	private boolean serviceBrakeEngaged = false;	// service brakes status
	private boolean engineFailure = false;			// engine status
	private boolean signalFailure = false;			// signal pickup status
	private boolean brakeFailure = false;			// brake status
	private boolean eBrakeEngaged = false;			// emergency brake status
	
	public Train() {
		this(1);
	}
	
	public Train(int carCount) {
		id = ++trainCount;
		this.carCount = carCount;
		passengerCapacity = carCount * CAR_CAPACITY;
		
		if(ui == null) {
			ui = TrainModelUI.getInstance();
		}
		ui.addTrain(this);
		
		TrainTimer.getInstance().addListener(this);
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
	 *  Method name: setPassengerCount
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Set number of passengers on train
	 *  Visibility: public
	 *  @param: num Number of passengers on train
	 *  @return: true if train can hold all passengers, false if not
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public boolean setPassengerCount(int num) {
		if(num < 0) {
			throw new IllegalArgumentException("Cannot have negative number of passengers");
		}
		
		if(num > passengerCapacity) {
			passengerCount = passengerCapacity;
			return false;
		} else {
			passengerCount = num;
			return true;
		}
	}
	
	/*******************************************************
	 *  Method name: getPassengerCapacity
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide max number of passengers train can hold
	 *  Visibility: public
	 *  @param:
	 *  @return: int Number of passengers train can hold
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public int getPassengerCapacity() {
		return passengerCapacity;
	}

	/*******************************************************
	 *  Method name: getMass
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide total mass of train
	 *  Visibility: public
	 *  @param:
	 *  @return: double Total mass of train
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public double getMass() {
		double carMass = CAR_MASS * carCount;
		double peopleMass = PASSENGER_MASS * (passengerCount + crewCount);
		return carMass + peopleMass;
	}
	
	/*******************************************************
	 *  Method name: getAcceleration
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide current acceleration of train
	 *  Visibility: public
	 *  @param:
	 *  @return: double Current acceleration of train
	 *  From requirement number 3.2.2.1 Physical Modeling
	*******************************************************/
	public double getAcceleration() {
		return acceleration;
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
	 *  Method name: openDoors
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Open the train doors
	 *  Visibility: public
	 *  @param:
	 *  @return:
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public void openDoors() {
		doorsOpen = true;
	}
	
	/*******************************************************
	 *  Method name: closeDoors
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Close the train doors
	 *  Visibility: public
	 *  @param:
	 *  @return:
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public void closeDoors() {
		doorsOpen = false;
	}
	
	/*******************************************************
	 *  Method name: lightsOn
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide status of train lights: on or off
	 *  Visibility: public
	 *  @param:
	 *  @return: boolean true of lights are on, false if lights are off
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public boolean lightsOn() {
		return lightsOn;
	}
	
	/*******************************************************
	 *  Method name: turnLightsOn
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Turn train lights on
	 *  Visibility: public
	 *  @param:
	 *  @return:
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public void turnLightsOn() {
		lightsOn = true;
	}
	
	/*******************************************************
	 *  Method name: turnLightsOff
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Turn train lights off
	 *  Visibility: public
	 *  @param:
	 *  @return:
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public void turnLightsOff() {
		lightsOn = false;
	}

	/*******************************************************
	 *  Method name: setBrakeEngaged
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Set the status of the service brake: engaged or disengaged
	 *  Visibility: public
	 *  @param: status True for engaged, false for disengaged
	 *  @return:
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public void setBrakeEngaged(boolean status) {
		serviceBrakeEngaged = status;
	}
	
	/*******************************************************
	 *  Method name: getBrakeEngaged
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide the status of the service brake: engaged or disengaged
	 *  Visibility: public
	 *  @param:
	 *  @return: status True for engaged, false for disengaged
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	public boolean getBrakeEngaged() {
		return serviceBrakeEngaged;
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

	/*******************************************************
	 *  Method name: update
	 *  Inheritance: TrainTimerListener
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Perform time dependent calculations
	 *  Visibility: public
	 *  @param: elapsedTime Time since last tick in milliseconds
	 *  @return:
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	@Override
	public void update(int elapsedTime) {
		calculateSpeed(elapsedTime);
		ui.updateDisplay();
	}
	
	/*******************************************************
	 *  Method name: calculateSpeed
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Calculate the current speed of the train
	 *  Visibility: public
	 *  @param: elapsedTime Time since last tick in milliseconds
	 *  @return:
	 *  From requirement number 3.2.2.3 Physical Attributes
	*******************************************************/
	private void calculateSpeed(int elapsedTime) {
		double elapsedTimeSeconds = (double) elapsedTime / 1000;
		double speedMetersPerSecond = speed / 3.6;
		
		// don't accept negative power input
		if(powerCommand < 0) {
			return;
		}
		
		// check brakes
		if(eBrakeEngaged) {
			acceleration = -1 * MAX_EMERGENCY_DECEL;
		} else if(serviceBrakeEngaged) {
			acceleration = -1 * MAX_SERVICE_DECEL;
		} else {
			if(powerCommand == 0) {
				// no accleration for zero power command
				acceleration = 0;
			} else if(powerCommand > 0 && speed == 0) {
				// if not moving use max acceleration
				acceleration = MAX_ACCEL;
			} else {
				// calculate acceleration
				acceleration = powerCommand / (getMass() * speedMetersPerSecond);
				
				// don't go over max acceleration
				if(acceleration > MAX_ACCEL) {
					acceleration = MAX_ACCEL;
				}
			}
		}

		speedMetersPerSecond += acceleration * elapsedTimeSeconds;
		speed = speedMetersPerSecond * 3.6;
		
		// don't go over max speed
		if(speed > MAX_SPEED) {
			speed = MAX_SPEED;
			acceleration = 0;
		}
		
		// don't go backwards
		if(speed < 0) {
			speed = 0;
			acceleration = 0;
		}
	}
	
	/*******************************************************
	 *  Method name: main
	 *  Inheritance: None
	 *  Attributes: static
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Start the UI for the train model. This is only
	 *  	called when runnign the train model by itself, so all the
	 *  	actual functionality can be demonstrated using the UI
	 *  Visibility: public
	 *  @param: args Unused
	 *  @return:
	 *  From requirement number 3.2.2 Train Model
	*******************************************************/
	public static void main(String[] args) {
		TrainModelUI.getInstance();
	}
}
