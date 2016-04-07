// TrainModelUI.java
// Author: Brian McDonald

package org.redpanda.traincontrolsystem.trainmodel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.JButton;
import javax.swing.JTextField;

/*******************************************************
 *  Class name: TrainModelUI
 *  Inheritance: None
 *  Attributes: None
 *  Methods:
 *  	public void addTrain(Train)
 *  	private void updateDisplay()
 *  	private void initializeUI()
 *  Functionality: Display information about all active trains.
 *  	If necessary, simulate inputs from track model and train controller
 *  Visibility: public
 *  From requirement number: 3.2.2 Train Model
  *******************************************************/
public class TrainModelUI {
	private static TrainModelUI instance;		// single instance of class
	
	private JFrame frmTrainModel;						// main window
	private JTable statusTable;							// display status attributes
	private DefaultListModel<Integer> trainListModel;	// model list of active trains
	private JList<Integer> trainList;					// display list of trains trains
	
	// train model action tab
	private JRadioButton radEngineFailureUnset;			// turn off engine failure
	private JRadioButton radEngineFailureSet;			// turn on engine failure
	private JRadioButton radSignalFailureUnset;			// turn off signal failure
	private JRadioButton radSignalFailureSet;			// turn on signal failure
	private JRadioButton radBrakeFailureUnset;			// turn off brake failure
	private JRadioButton radBrakeFailureSet;			// turn on brake failure
	private JRadioButton radEBrakeUnset;				// disengage emergency brake
	private JRadioButton radEBrakeSet;					// engage emergency brake
	
	// track model action tab
	private JTextField txtAuthority;	// text box to enter authority
	private JTextField txtSpeed;		// text box to enter speed
	private JTextField txtGrade;		// text box to enter track grade
	private JButton btnNewTrain;		// button to create new train
	private JButton btnSetAuthority;	// button to set authority from text box
	private JButton btnSetSpeed;		// button to set speed from text box
	private JButton btnSetGrade;		// button to set track grade from text box
	
	// train controller action tab
	private JTextField txtPower;		// text box to enter power command
	private JButton btnSetPower;		// button to set power from text box
	private JButton btnEngageBrake;		// button to engage service brakes
	private JButton btnDisengageBrake;	// button to disengage service brakes
	
	private List<Train> trains;		// keep track of all trains
	private Train currentTrain;		// train currently being displayed

	private TrainModelUI() {
		initializeUI();
		
		trains = new ArrayList<Train>();
	}
	
	/*******************************************************
	 *  Method name: getInstance
	 *  Inheritance: None
	 *  Attributes: static, synchronized
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Provide access to single instance of TrainModelUI
	 *  Visibility: public
	 *  @param:
	 *  @return: Single instance of TrainModelUI
	 *  From requirement number 3.2.2 Train Model
	 *******************************************************/
	public static synchronized TrainModelUI getInstance() {
		if(instance == null) {
			instance = new TrainModelUI();
		}
		
		return instance;
	}
	
	/*******************************************************
	 *  Method name: addTrain
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Add a Train to the list of active Trains
	 *  Visibility: public
	 *  @param: train New Train to add to UI
	 *  @return:
	 *  From requirement number 3.2.2 Train Model
	*******************************************************/
	public void addTrain(Train train) {
		int id = train.getID();
		trains.add(train);
		trainListModel.addElement(id);
		if(id == 1) {
			trainList.setSelectedIndex(0);
			currentTrain = trains.get(0);
			updateDisplay();
		}
	}
	
	/*******************************************************
	 *  Method name: updateDisplay
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Refresh display with most up-to-date information
	 *  	regarding active trains
	 *  Visibility: public
	 *  @param:
	 *  @return:
	 *  From requirement number 3.2.2 Train Model
	*******************************************************/
	public void updateDisplay() {
		statusTable.setValueAt(String.format("%.2f", currentTrain.getLastPowerCommand()), 0, 1);
		statusTable.setValueAt(String.format("%.2f", currentTrain.getAcceleration()), 1, 1);
		statusTable.setValueAt(String.format("%.2f", kphToMph(currentTrain.getSpeed())), 2, 1);
		statusTable.setValueAt(String.format("%.2f", kphToMph(currentTrain.getSetpointSpeed())), 3, 1);
		statusTable.setValueAt(String.valueOf(currentTrain.getAuthority()), 4, 1);
		statusTable.setValueAt(String.format("%.2f", currentTrain.getTrackGrade()), 5, 1);
		statusTable.setValueAt(String.valueOf(currentTrain.getCarCount()), 6, 1);
		statusTable.setValueAt(String.valueOf(currentTrain.getCrewCount()), 7, 1);
		statusTable.setValueAt(String.valueOf(currentTrain.getPassengerCount()), 8, 1);
		statusTable.setValueAt(String.format("%.2f", currentTrain.getMass()), 9, 1);
		
		if(currentTrain.doorsOpen()) {
			statusTable.setValueAt("Open", 10, 1);
		} else {
			statusTable.setValueAt("Closed", 10, 1);
		}
		
		if(currentTrain.lightsOn()) {
			statusTable.setValueAt("On", 11, 1);
		} else {
			statusTable.setValueAt("Off", 11, 1);
		}
		
		if(currentTrain.getEngineFailure()) {
			radEngineFailureSet.setSelected(true);
			statusTable.setValueAt("Failure", 12, 1);
		} else {
			radEngineFailureUnset.setSelected(true);
			statusTable.setValueAt("Normal", 12, 1);
		}

		if(currentTrain.getSignalFailure()) {
			radSignalFailureSet.setSelected(true);
			statusTable.setValueAt("Failure", 13, 1);
		} else {
			radSignalFailureUnset.setSelected(true);
			statusTable.setValueAt("Normal", 13, 1);
		}
		
		if(currentTrain.getBrakeFailure()) {
			radBrakeFailureSet.setSelected(true);
			statusTable.setValueAt("Failure", 14, 1);
		} else {
			radBrakeFailureUnset.setSelected(true);
			statusTable.setValueAt("Normal", 14, 1);
		}
		
		if(currentTrain.getEBrakeEngaged()) {
			radEBrakeSet.setSelected(true); 
			statusTable.setValueAt("Engaged", 15, 1);
		} else {
			radEBrakeUnset.setSelected(true);
			statusTable.setValueAt("Not Engaged", 15, 1);
		}
	}
	
	/*******************************************************
	 *  Method name: initializeUI
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Perform initial setup of GUI
	 *  Visibility: private
	 *  @param:
	 *  @return:
	 *  From requirement number 3.2.2 Train Model
	*******************************************************/
	private void initializeUI() {
		// setup main window
		frmTrainModel = new JFrame();
		frmTrainModel.setTitle("Train Model");
		frmTrainModel.setResizable(false);
		frmTrainModel.setBounds(100, 100, 687, 400);
		frmTrainModel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTrainModel.getContentPane().setLayout(null);
		
		// add labels for each section
		JLabel trainSelectLabel = new JLabel("Train Select");
		trainSelectLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		trainSelectLabel.setBounds(10, 22, 92, 14);
		frmTrainModel.getContentPane().add(trainSelectLabel);
		
		JLabel trainActionLabel = new JLabel("Train Actions");
		trainActionLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		trainActionLabel.setBounds(367, 22, 304, 14);
		frmTrainModel.getContentPane().add(trainActionLabel);
		
		JLabel trainStatusLabel = new JLabel("Train Status");
		trainStatusLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		trainStatusLabel.setBounds(112, 22, 245, 14);
		frmTrainModel.getContentPane().add(trainStatusLabel);
		
		// add train select section
		trainListModel = new DefaultListModel<Integer>();
		JPanel trainSelectPanel = new JPanel();
		trainSelectPanel.setBackground(Color.WHITE);
		trainSelectPanel.setBounds(10, 47, 92, 303);
		frmTrainModel.getContentPane().add(trainSelectPanel);
		trainSelectPanel.setLayout(new BorderLayout(0, 0));
		trainList = new JList<Integer>(trainListModel);
		trainList.addListSelectionListener(new TrainSelectionListener());
		trainSelectPanel.add(trainList);
		
		// add train status section
		statusTable = new JTable(new AttributeTable());
		statusTable.setShowGrid(false);
		statusTable.setRowSelectionAllowed(false);
		
		JPanel trainStatusPanel = new JPanel();
		trainStatusPanel.setLayout(new BorderLayout(0, 0));
		trainStatusPanel.setBackground(Color.WHITE);
		trainStatusPanel.setBounds(112, 47, 245, 303);
		frmTrainModel.getContentPane().add(trainStatusPanel);
		trainStatusPanel.add(statusTable, BorderLayout.CENTER);
		
		// add tabbed panel to hold train model, track model, and train controller actions
		JTabbedPane tabbedActions = new JTabbedPane(JTabbedPane.TOP);
		tabbedActions.setBounds(367, 47, 304, 303);
		frmTrainModel.getContentPane().add(tabbedActions);

		// add train model actions section
		JPanel trainModelActionPanel = new JPanel();
		tabbedActions.addTab("Train Model", null, trainModelActionPanel, null);
		trainModelActionPanel.setBackground(Color.WHITE);
		trainModelActionPanel.setLayout(null);
		
		TrainModelActionListener trainModelListener = new TrainModelActionListener();
		
		JLabel lblUnset = new JLabel("Unset");
		lblUnset.setBounds(148, 11, 46, 14);
		trainModelActionPanel.add(lblUnset);
		
		JLabel lblSet = new JLabel("Set");
		lblSet.setBounds(217, 11, 46, 14);
		trainModelActionPanel.add(lblSet);
		
		JLabel lblEngineFailure = new JLabel("Engine Failure");
		lblEngineFailure.setBounds(10, 41, 124, 14);
		trainModelActionPanel.add(lblEngineFailure);
		
		ButtonGroup radGrpEngineFailure = new ButtonGroup();		
		ButtonGroup radGrpSignalFailure = new ButtonGroup();		
		ButtonGroup radGrpBrakeFailure = new ButtonGroup();		
		ButtonGroup radGrpEBrake = new ButtonGroup();
		
		radEngineFailureUnset = new JRadioButton("");
		radEngineFailureUnset.setSelected(true);
		radEngineFailureUnset.setBackground(Color.WHITE);
		radEngineFailureUnset.setBounds(148, 32, 21, 23);
		radEngineFailureUnset.addActionListener(trainModelListener);
		trainModelActionPanel.add(radEngineFailureUnset);
		
		radEngineFailureSet = new JRadioButton("");
		radEngineFailureSet.setBackground(Color.WHITE);
		radEngineFailureSet.setBounds(217, 32, 21, 23);
		radEngineFailureSet.addActionListener(trainModelListener);
		trainModelActionPanel.add(radEngineFailureSet);
		radGrpEngineFailure.add(radEngineFailureUnset);
		radGrpEngineFailure.add(radEngineFailureSet);
		
		JLabel lblSignalFailure = new JLabel("Signal Pickup Failure");
		lblSignalFailure.setBounds(10, 66, 124, 14);
		trainModelActionPanel.add(lblSignalFailure);
		
		radSignalFailureUnset = new JRadioButton("");
		radSignalFailureUnset.setSelected(true);
		radSignalFailureUnset.setBackground(Color.WHITE);
		radSignalFailureUnset.setBounds(148, 57, 21, 23);
		radSignalFailureUnset.addActionListener(trainModelListener);
		trainModelActionPanel.add(radSignalFailureUnset);
		
		radSignalFailureSet = new JRadioButton("");
		radSignalFailureSet.setBackground(Color.WHITE);
		radSignalFailureSet.setBounds(217, 57, 21, 23);
		radSignalFailureSet.addActionListener(trainModelListener);
		trainModelActionPanel.add(radSignalFailureSet);
		radGrpSignalFailure.add(radSignalFailureUnset);
		radGrpSignalFailure.add(radSignalFailureSet);
		
		JLabel lblBrakeFailure = new JLabel("Brake Failure");
		lblBrakeFailure.setBounds(10, 91, 124, 14);
		trainModelActionPanel.add(lblBrakeFailure);
		
		radBrakeFailureUnset = new JRadioButton("");
		radBrakeFailureUnset.setSelected(true);
		radBrakeFailureUnset.setBackground(Color.WHITE);
		radBrakeFailureUnset.setBounds(148, 82, 21, 23);
		radBrakeFailureUnset.addActionListener(trainModelListener);
		trainModelActionPanel.add(radBrakeFailureUnset);
		
		radBrakeFailureSet = new JRadioButton("");
		radBrakeFailureSet.setBackground(Color.WHITE);
		radBrakeFailureSet.setBounds(217, 82, 21, 23);
		radBrakeFailureSet.addActionListener(trainModelListener);
		trainModelActionPanel.add(radBrakeFailureSet);
		radGrpBrakeFailure.add(radBrakeFailureUnset);
		radGrpBrakeFailure.add(radBrakeFailureSet);
		
		JLabel lblEmergencyBrake = new JLabel("Emergency Brake");
		lblEmergencyBrake.setBounds(10, 116, 124, 14);
		trainModelActionPanel.add(lblEmergencyBrake);
		
		radEBrakeUnset = new JRadioButton("");
		radEBrakeUnset.setSelected(true);
		radEBrakeUnset.setBackground(Color.WHITE);
		radEBrakeUnset.setBounds(148, 107, 21, 23);
		radEBrakeUnset.addActionListener(trainModelListener);
		trainModelActionPanel.add(radEBrakeUnset);
		
		radEBrakeSet = new JRadioButton("");
		radEBrakeSet.setBackground(Color.WHITE);
		radEBrakeSet.setBounds(217, 107, 21, 23);
		radEBrakeSet.addActionListener(trainModelListener);
		trainModelActionPanel.add(radEBrakeSet);
		radGrpEBrake.add(radEBrakeUnset);
		radGrpEBrake.add(radEBrakeSet);
		
		// add track model actions section
		JPanel trackModelActionPanel = new JPanel();
		trackModelActionPanel.setBackground(Color.WHITE);
		tabbedActions.addTab("Track Model", null, trackModelActionPanel, null);
		trackModelActionPanel.setLayout(null);
		
		TrackModelActionListener trackModelListener = new TrackModelActionListener();
		
		btnNewTrain = new JButton("New Train");
		btnNewTrain.setBounds(88, 11, 120, 23);
		btnNewTrain.addActionListener(trackModelListener);
		trackModelActionPanel.add(btnNewTrain);
		
		JLabel lblAuthority = new JLabel("Authority");
		lblAuthority.setBounds(10, 53, 80, 14);
		trackModelActionPanel.add(lblAuthority);
		
		JLabel lblSpeed = new JLabel("Speed");
		lblSpeed.setBounds(10, 78, 80, 14);
		trackModelActionPanel.add(lblSpeed);
		
		JLabel lblTrackGrade = new JLabel("Track Grade");
		lblTrackGrade.setBounds(10, 103, 80, 14);
		trackModelActionPanel.add(lblTrackGrade);
		
		txtAuthority = new JTextField();
		txtAuthority.setBounds(88, 50, 60, 20);
		trackModelActionPanel.add(txtAuthority);
		txtAuthority.setColumns(10);
		
		txtSpeed = new JTextField();
		txtSpeed.setBounds(88, 75, 60, 20);
		trackModelActionPanel.add(txtSpeed);
		txtSpeed.setColumns(10);
		
		txtGrade = new JTextField();
		txtGrade.setBounds(88, 100, 60, 20);
		trackModelActionPanel.add(txtGrade);
		txtGrade.setColumns(10);
		
		btnSetAuthority = new JButton("Set Authority");
		btnSetAuthority.setBounds(158, 49, 131, 23);
		btnSetAuthority.addActionListener(trackModelListener);
		trackModelActionPanel.add(btnSetAuthority);
		
		btnSetSpeed = new JButton("Set Speed");
		btnSetSpeed.setBounds(158, 74, 131, 23);
		btnSetSpeed.addActionListener(trackModelListener);
		trackModelActionPanel.add(btnSetSpeed);
		
		btnSetGrade = new JButton("Set Grade");
		btnSetGrade.setBounds(158, 99, 131, 23);
		btnSetGrade.addActionListener(trackModelListener);
		trackModelActionPanel.add(btnSetGrade);
		
		// train controller actions section
		JPanel trainControllerActionPanel = new JPanel();
		trainControllerActionPanel.setBackground(Color.WHITE);
		tabbedActions.addTab("Train Controller", null, trainControllerActionPanel, null);
		trainControllerActionPanel.setLayout(null);
		
		TrainControllerActionListener trainControllerListener = new TrainControllerActionListener();
		
		JLabel lblPower = new JLabel("Power");
		lblPower.setBounds(10, 24, 46, 14);
		trainControllerActionPanel.add(lblPower);
		
		txtPower = new JTextField();
		txtPower.setBounds(62, 21, 86, 20);
		trainControllerActionPanel.add(txtPower);
		txtPower.setColumns(10);
		
		btnSetPower = new JButton("Set Power");
		btnSetPower.setBounds(158, 20, 131, 23);
		btnSetPower.addActionListener(trainControllerListener);
		trainControllerActionPanel.add(btnSetPower);
		
		btnEngageBrake = new JButton("Engage Service Brake");
		btnEngageBrake.setBounds(51, 69, 193, 23);
		btnEngageBrake.addActionListener(trainControllerListener);
		trainControllerActionPanel.add(btnEngageBrake);
		
		btnDisengageBrake = new JButton("Disengage Service Brake");
		btnDisengageBrake.setBounds(51, 103, 193, 23);
		btnDisengageBrake.addActionListener(trainControllerListener);
		trainControllerActionPanel.add(btnDisengageBrake);
		
		// set main window visible
		frmTrainModel.setVisible(true);
	}
	
	/*******************************************************
	 *  Method name: kphToMph
	 *  Inheritance: None
	 *  Attributes: None
	 *  Precondition: None
	 *  Postcondition: None
	 *  Functionality: Convert a speed in km/hr to mi/hr
	 *  Visibility: public
	 *  @param: kphSpeed Speed in km/hr
	 *  @return:
	 *  From requirement number 3.2.2 Train Model
	*******************************************************/
	private double kphToMph(double kphSpeed) {
		return kphSpeed * 0.621371;
	}
	
	/*******************************************************
	 *  Class name: TrainSelectionListener
	 *  Inheritance: ListSelectionListener
	 *  Attributes: None
	 *  Methods:
	 *  	public void valueChanged(ListSelectionEvent)
	 *  Functionality: Listen for a different train being selected from the list
	 *  Visibility: private
	 *  From requirement number: 3.2.2 Train Model
	  *******************************************************/
	private class TrainSelectionListener implements ListSelectionListener {
		/*******************************************************
		 *  Method name: valueChanged
		 *  Inheritance: None
		 *  Attributes: None
		 *  Precondition: None
		 *  Postcondition: None
		 *  Functionality: Display the information for the train clicked
		 *  	in the active train list
		 *  Visibility: public
		 *  @param: e ListSelectionEvent specifying which train was selected
		 *  @return:
		 *  From requirement number 3.2.2 Train Model
		*******************************************************/
		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting()) {
				if(trainList.getSelectedIndex() != -1) {
					currentTrain = trains.get(trainList.getSelectedValue() - 1);
					updateDisplay();
				}
			}
		}
	}
	
	/*******************************************************
	 *  Class name: TrainModelActionListener
	 *  Inheritance: ActionListener
	 *  Attributes: None
	 *  Methods:
	 *  	public void actionPerformed(ActionEvent)
	 *  Functionality: Listen for interactions such as failures
	 *  	and emergency braking
	 *  Visibility: private
	 *  From requirement number: 3.2.2 Train Model
	  *******************************************************/
	private class TrainModelActionListener implements ActionListener {
		/*******************************************************
		 *  Method name: actionPerformed
		 *  Inheritance: None
		 *  Attributes: None
		 *  Precondition: None
		 *  Postcondition: None
		 *  Functionality: Set the train status appropriately for the action
		 *  	performed and update the UI
		 *  Visibility: private
		 *  @param: e ActionEvent identifying which action was performed by the user
		 *  @return:
		 *  From requirement number 3.2.2 Train Model
		*******************************************************/
		public void actionPerformed(ActionEvent e) {
			boolean engineFailure = radEngineFailureSet.isSelected();
			boolean signalFailure = radSignalFailureSet.isSelected();
			boolean brakeFailure = radBrakeFailureSet.isSelected();
			boolean eBrakeEngaged = radEBrakeSet.isSelected();
			
			currentTrain.setEngineFailure(engineFailure);
			currentTrain.setSignalFailure(signalFailure);
			currentTrain.setBrakeFailure(brakeFailure);
			currentTrain.setEBrakeEngaged(eBrakeEngaged);
			
			updateDisplay();
		}
	}
	
	/*******************************************************
	 *  Class name: TrainModelActionListener
	 *  Inheritance: ActionListener
	 *  Attributes: None
	 *  Methods:
	 *  	public void actionPerformed(ActionEvent)
	 *  Functionality: Listen for input from the Track Model tab
	 *  Visibility: private
	 *  From requirement number: 3.2.2 Train Model
	  *******************************************************/
	private class TrackModelActionListener implements ActionListener {
		/*******************************************************
		 *  Method name: actionPerformed
		 *  Inheritance: None
		 *  Attributes: None
		 *  Precondition: None
		 *  Postcondition: None
		 *  Functionality: Update the active train's status according
		 *  	to the action performed
		 *  Visibility: private
		 *  @param: e ActionEvent identifying which action was performed by the user
		 *  @return:
		 *  From requirement number 3.2.2 Train Model
		*******************************************************/
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnNewTrain) {
				// create a new train
				new Train();
			} else if(e.getSource() == btnSetAuthority) {
				// set authority according to the txtAuthority field
				try {
					int authority = Integer.parseInt(txtAuthority.getText());
					if(authority >= 0) {
						currentTrain.setAuthority(authority);
					}
				} catch (NumberFormatException ex) {
					return;
				}
			} else if(e.getSource() == btnSetSpeed) {
				// set speed according to the txtSpeed field
				try {
					int speed = Integer.parseInt(txtSpeed.getText());
					if(speed >= 0) {
						currentTrain.setSpeed(speed);
					}
				} catch (NumberFormatException ex) {
					return;
				}
			} else if(e.getSource() == btnSetGrade) {
				// set track grade according to the txtGrade field
				try {
					double grade = Double.parseDouble(txtGrade.getText());
					if(grade < 90 && grade > -90) {
						currentTrain.setTrackGrade(grade);
					}
				} catch (NumberFormatException ex) {
					return;
				}
			}
			
			updateDisplay();
		}
	}
	
	/*******************************************************
	 *  Class name: TrainControllerActionListener
	 *  Inheritance: ActionListener
	 *  Attributes: None
	 *  Methods:
	 *  	public void actionPerformed(ActionEvent)
	 *  Functionality: Listen for input from the Train Controller tab
	 *  Visibility: private
	 *  From requirement number: 3.2.2 Train Model
	  *******************************************************/
	private class TrainControllerActionListener implements ActionListener {
		/*******************************************************
		 *  Method name: actionPerformed
		 *  Inheritance: None
		 *  Attributes: None
		 *  Precondition: None
		 *  Postcondition: None
		 *  Functionality: Set the train status appropriately for the action
		 *  	performed and update the UI
		 *  Visibility: private
		 *  @param: e ActionEvent identifying which action was performed by the user
		 *  @return:
		 *  From requirement number 3.2.2 Train Model
		*******************************************************/
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnSetPower) {
				// set power command according to the txtPower field
				try {
					double power = Double.parseDouble(txtPower.getText());
					currentTrain.setPower(power);
				} catch (NumberFormatException ex) {
					return;
				}
			} else if(e.getSource() == btnEngageBrake) {
				 currentTrain.setBrakeEngaged(true);
			} else if(e.getSource() == btnDisengageBrake) {
				currentTrain.setBrakeEngaged(false);
			}
			
			updateDisplay();
		}
	}
	
	/*******************************************************
	 *  Class name: AttributeTable
	 *  Inheritance: AbstractTableModel
	 *  Attributes: None
	 *  Methods:
	 *  	public int getColumnCount()
	 *  	public int getRowCount()
	 *  	public Object getValueAt(int, int)
	 *  	public void setValueAt(Object, int, int)
	 *  		public boolean isCellEditable(int, int)
	 *  Functionality: Display information about all active trains.
	 *  	If necessary, simulate inputs from track model and train controller
	 *  Visibility: public
	 *  From requirement number: 3.2.2 Train Model
	  *******************************************************/
	private class AttributeTable extends AbstractTableModel {
		private static final long serialVersionUID = 1L;	// this should not matter because the class will not be serialized
		private String[] columnNames = {"Item", "Value"};	// column names
		private Object[][] values = {						// row names
				{"Power Command", ""},
				{"Acceleration", ""},
				{"Speed", ""},
				{"Setpoint Speed", ""},
				{"Authority", ""},
				{"Track Grade", ""},
				{"Cars", ""},
				{"Crew", ""},
				{"Passengers", ""},
				{"Mass", ""},
				{"Doors", ""},
				{"Lights", ""},
				{"Engine", ""},
				{"Signal Pickup", ""},
				{"Brakes", ""},
				{"Emergency Brake", ""}
		};
		
		/*******************************************************
		 *  Method name: getColumnCount
		 *  Inheritance: None
		 *  Attributes: None
		 *  Precondition: None
		 *  Postcondition: None
		 *  Functionality: Provide number of columns in table
		 *  Visibility: public
		 *  @param:
		 *  @return: int Nubmer of columns in table
		 *  From requirement number 3.2.2 Train Model
		*******************************************************/
		public int getColumnCount() {
			return columnNames.length;
		}

		/*******************************************************
		 *  Method name: getRowCount
		 *  Inheritance: None
		 *  Attributes: None
		 *  Precondition: None
		 *  Postcondition: None
		 *  Functionality: Provide number of rows in table
		 *  Visibility: public
		 *  @param:
		 *  @return: int Nubmer of rows in table
		 *  From requirement number 3.2.2 Train Model
		*******************************************************/
		public int getRowCount() {
			return values.length;
		}

		/*******************************************************
		 *  Method name: getValueAt
		 *  Inheritance: None
		 *  Attributes: None
		 *  Precondition: None
		 *  Postcondition: None
		 *  Functionality: Provide the value at a row and column location
		 *  	in the table
		 *  Visibility: public
		 *  @param: row Row index
		 *  		col Column index
		 *  @return: Object stored at indicies row, col
		 *  From requirement number 3.2.2 Train Model
		*******************************************************/
		public Object getValueAt(int row, int col) {
			return values[row][col];
		}
		
		/*******************************************************
		 *  Method name: setValueAt
		 *  Inheritance: None
		 *  Attributes: None
		 *  Precondition: None
		 *  Postcondition: None
		 *  Functionality: Set the value of the cell at a specific 
		 *  	row and column in the table
		 *  Visibility: public
		 *  @param: value Object to set as value
		 *  		row Row index
		 *  		col Column index
		 *  @return:
		 *  From requirement number 3.2.2 Train Model
		*******************************************************/
		public void setValueAt(Object value, int row, int col) {
			values[row][col] = (String) value;
			fireTableCellUpdated(row, col);
		}
		
		/*******************************************************
		 *  Method name: isCellEditable
		 *  Inheritance: None
		 *  Attributes: None
		 *  Precondition: None
		 *  Postcondition: None
		 *  Functionality: Allow no cells to be edited
		 *  Visibility: public
		 *  @param: row Row index in table
		 *  		col Column index in table
		 *  @return:
		 *  From requirement number 3.2.2 Train Model
		*******************************************************/
		public boolean isCellEditable(int row, int col) {
			return false;
		}
	}
}
