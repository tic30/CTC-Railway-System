// TimerListener.java
// Author: Brian McDonald

package org.redpanda.traincontrolsystem.timer;

/*******************************************************
 *  Interface name: TrainTimerListener
 *  Inheritance: None
 *  Attributes: None
 *  Methods:
 * 		public void update();
 *  Functionality: Used in TrainTimer to call the update function
 *  	on regular time intervals
 *  Visibility: public
 *  From requirement number: 3.2.1.2 Running Speed
  *******************************************************/
public interface TrainTimerListener {
	
	/*******************************************************
	 *  Method name: update
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Perform all calculations requiring change in time
	 *  Visibility: public
	 *  @param: elapsedTime Time since this method was last called in milliseconds
	 *  @return: void
	 *  From requirement number 3.2.1.2 Running Speed
	*******************************************************/
	public void update(int elapsedTime);
}
