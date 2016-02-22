
public class Train {
	private static TrainModelUI ui;		// keep single instance of ui
	private static int trainCount = 0;	// keep track of number of active trains
	
	private int id;							// unique id for train
	private int carCount = 1;				// number of cars in train
	private int crewCount = 4;				// number of crew members on train
	private int passengerCount = 45;		// number of passengers on train
	private double speed = 30;				// current speed of train
	private double setpointSpeed = 30;		// current setpoint speed of train
	private double authority = 50;			// current authority of train
	private boolean doorsOpen = false;		// doors open/closed status
	private boolean lightsOn = false;		// lights on/off failure
	private boolean engineFailure = false;	// engine status
	private boolean signalFailure = false;	// signal pickup status
	private boolean brakeFailure = false;	// brake status
	private boolean eBrakeEngaged = false;	// emergency brake status
	
	public Train() {
		if(ui == null) {
			ui = new TrainModelUI();
		}
		
		id = ++trainCount;
		
		ui.addTrain(this);
	}
	
	public int getID() {
		return id;
	}
	
	public int getCarCount() {
		return carCount;
	}
	
	public int getCrewCount() {
		return crewCount;
	}
	
	public int getPassengerCount() {
		return passengerCount;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public double getSetpointSpeed() {
		return setpointSpeed;
	}
	
	public double getAuthority() {
		return authority;
	}
	
	public boolean doorsOpen() {
		return doorsOpen;
	}
	
	public boolean lightsOn() {
		return lightsOn;
	}
	
	public boolean getEngineFailure() {
		return engineFailure;
	}
	
	public void setEngineFailure(boolean status) {
		engineFailure = status;
	}
	
	public boolean getSignalFailure() {
		return signalFailure;
	}
	
	public void setSignalFailure(boolean status) {
		signalFailure = status;
	}
	
	public boolean getBrakeFailure() {
		return brakeFailure;
	}
	
	public void setBrakeFailure(boolean status) {
		brakeFailure = status;
	}
	
	public boolean getEBrakeEngaged() {
		return eBrakeEngaged;
	}
	
	public void setEBrakeEngaged(boolean status) {
		eBrakeEngaged = status;
	}
	
	public static void main(String[] args) {
		new Train();
		new Train();
		new Train();
	}
}
