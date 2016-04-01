// TrainTimer.java
// Author: Brian McDonald

package org.redpanda.traincontrolsystem.timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/*******************************************************
 *  Class name: TrainTimer
 *  Inheritance: TimerTask
 *  Attributes: None
 *  Methods:
 * 		public static synchronized TrainTimer getInstance()
 * 		public synchronized void addListener(TrainTimerListener)
 *  Functionality: A singleton that provides a common timer to all
 *  	objects that implement TrainTimerListener. Objects that add themselves
 *  	to the list of listeners will have their update methods called on
 *  	regular time intervals.
 *  Visibility: public
 *  From requirement number: 3.2.1.2 Running Speed
  *******************************************************/
public class TrainTimer extends TimerTask {
	private static TrainTimer instance;			// single instance of TrainTimer
	private List<TrainTimerListener> listeners;	// listeners to call update on time intervals
	private Timer timer;						// master timer
	private long lastTime;						// last time in milliseconds that the timer ran
	
	/*******************************************************
	 *  Method name: TrainTimer
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Constructor for TrainTimer. Not intended for direct instantiation.
	 *  	Use the getInstance method to obtain the instance of the TrainTimer. 
	 *  Visibility: private
	 *  @param:
	 *  @return: Single instance of TrainTimer
	 *  From requirement number 3.2.1.2 Running Speed
	 *******************************************************/
	private TrainTimer() {
		listeners = new ArrayList<TrainTimerListener>();
		timer = new Timer();
	}
	
	/*******************************************************
	 *  Method name: getInstance
	 *  Inheritance: None
	 *  Attributes: static, synchronized
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide access to single instance of TrainTimer
	 *  Visibility: public
	 *  @param:
	 *  @return: Single instance of TrainTimer
	 *  From requirement number 3.2.1.2 Running Speed
	 *******************************************************/
	public static synchronized TrainTimer getInstance() {
		if(instance == null) {
			instance = new TrainTimer();
		}
		
		return instance;
	}
	
	/*******************************************************
	 *  Method name: addListener
	 *  Inheritance: None
	 *  Attributes: synchronized
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Add a TrainTimerListener to the list of listeners
	 *  Visibility: public
	 *  @param: listener TrainTimerListener to be added to list of listeners
	 *  @return: void
	 *  From requirement number 3.2.1.2 Running Speed
	 *******************************************************/
	public void addListener(TrainTimerListener listener) {
		// start the timer if this is the first listener
		if(listeners.isEmpty()) {
			startTimer();
		}
		
		listeners.add(listener);
	}
	
	/*******************************************************
	 *  Method name: startTimer
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Start the master timer 
	 *  Visibility: private
	 *  @param:
	 *  @return: void
	 *  From requirement number 3.2.1.2 Running Speed
	 *******************************************************/
	private void startTimer() {
		lastTime = System.currentTimeMillis();
		timer.scheduleAtFixedRate(this, 100, 100);
	}

	/*******************************************************
	 *  Method name: run
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Notify all listeners of timer event
	 *  Visibility: public
	 *  @param:
	 *  @return: void
	 *  From requirement number 3.2.1.2 Running Speed
	 *******************************************************/
	@Override
	public void run() {
		// calculate elapsed time
		long currentTime = System.currentTimeMillis();
		long elapsedTime = currentTime - lastTime;

		// update all listeners
		for(TrainTimerListener listener : listeners) {
			listener.update((int) elapsedTime);
		}
		
		// update last time to current time
		lastTime = currentTime;
	}
}
