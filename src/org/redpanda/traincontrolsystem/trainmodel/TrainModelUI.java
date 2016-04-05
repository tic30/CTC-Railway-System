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
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

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
	private JFrame frmTrainModel;						// main window
	private JTable statusTable;							// display status attributes
	private DefaultListModel<Integer> trainListModel;	// model list of active trains
	private JList<Integer> trainList;					// display list of trains trains
	private JRadioButton radEngineFailureUnset;			// turn off engine failure
	private JRadioButton radEngineFailureSet;			// turn on engine failure
	private JRadioButton radSignalFailureUnset;			// turn off signal failure
	private JRadioButton radSignalFailureSet;			// turn on signal failure
	private JRadioButton radBrakeFailureUnset;			// turn off brake failure
	private JRadioButton radBrakeFailureSet;			// turn on brake failure
	private JRadioButton radEBrakeUnset;				// disengage emergency brake
	private JRadioButton radEBrakeSet;					// engage emergency brake
	
	private List<Train> trains;		// keep track of all trains
	private Train currentTrain;		// train currently being displayed

	public TrainModelUI() {
		initializeUI();
		
		trains = new ArrayList<Train>();
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
	 *  Visibility: private
	 *  @param:
	 *  @return:
	 *  From requirement number 3.2.2 Train Model
	*******************************************************/
	private void updateDisplay() {
		statusTable.setValueAt(String.valueOf(currentTrain.getSpeed()), 0, 1);
		statusTable.setValueAt(String.valueOf(currentTrain.getSetpointSpeed()), 1, 1);
		statusTable.setValueAt(String.valueOf(currentTrain.getAuthority()), 2, 1);
		statusTable.setValueAt(String.valueOf(currentTrain.getCarCount()), 3, 1);
		statusTable.setValueAt(String.valueOf(currentTrain.getCrewCount()), 4, 1);
		statusTable.setValueAt(String.valueOf(currentTrain.getPassengerCount()), 5, 1);
		
		if(currentTrain.doorsOpen()) {
			statusTable.setValueAt("Open", 6, 1);
		} else {
			statusTable.setValueAt("Closed", 6, 1);
		}
		
		if(currentTrain.lightsOn()) {
			statusTable.setValueAt("On", 7, 1);
		} else {
			statusTable.setValueAt("Off", 7, 1);
		}
		
		if(currentTrain.getEngineFailure()) {
			radEngineFailureSet.setSelected(true);
			statusTable.setValueAt("Failure", 8, 1);
		} else {
			radEngineFailureUnset.setSelected(true);
			statusTable.setValueAt("Normal", 8, 1);
		}

		if(currentTrain.getSignalFailure()) {
			radSignalFailureSet.setSelected(true);
			statusTable.setValueAt("Failure", 9, 1);
		} else {
			radSignalFailureUnset.setSelected(true);
			statusTable.setValueAt("Normal", 9, 1);
		}
		
		if(currentTrain.getBrakeFailure()) {
			radBrakeFailureSet.setSelected(true);
			statusTable.setValueAt("Failure", 10, 1);
		} else {
			radBrakeFailureUnset.setSelected(true);
			statusTable.setValueAt("Normal", 10, 1);
		}
		
		if(currentTrain.getEBrakeEngaged()) {
			radEBrakeSet.setSelected(true); 
			statusTable.setValueAt("Engaged", 11, 1);
		} else {
			radEBrakeUnset.setSelected(true);
			statusTable.setValueAt("Not Engaged", 11, 1);
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
		frmTrainModel.setBounds(100, 100, 600, 400);
		frmTrainModel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTrainModel.getContentPane().setLayout(null);
		
		// add labels for each section
		JLabel trainSelectLabel = new JLabel("Train Select");
		trainSelectLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		trainSelectLabel.setBounds(10, 22, 92, 14);
		frmTrainModel.getContentPane().add(trainSelectLabel);
		
		JLabel trainActionLabel = new JLabel("Train Actions");
		trainActionLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		trainActionLabel.setBounds(112, 22, 252, 14);
		frmTrainModel.getContentPane().add(trainActionLabel);
		
		JLabel trainStatusLabel = new JLabel("Train Status");
		trainStatusLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		trainStatusLabel.setBounds(374, 22, 210, 14);
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
		
		JPanel trainActionPanel = new JPanel();
		trainActionPanel.setBackground(Color.WHITE);
		trainActionPanel.setBounds(112, 47, 245, 303);
		frmTrainModel.getContentPane().add(trainActionPanel);
		trainActionPanel.setLayout(null);
		
		// add train actions section
		JLabel lblUnset = new JLabel("Unset");
		lblUnset.setBounds(140, 11, 46, 14);
		trainActionPanel.add(lblUnset);
		
		JLabel lblSet = new JLabel("Set");
		lblSet.setBounds(196, 11, 46, 14);
		trainActionPanel.add(lblSet);
		
		JLabel lblEngineFailure = new JLabel("Engine Failure");
		lblEngineFailure.setBounds(10, 41, 124, 14);
		trainActionPanel.add(lblEngineFailure);
		
		radEngineFailureUnset = new JRadioButton("");
		radEngineFailureUnset.setSelected(true);
		radEngineFailureUnset.setBackground(Color.WHITE);
		radEngineFailureUnset.setBounds(140, 32, 21, 23);
		radEngineFailureUnset.addActionListener(new TrainActionListener());
		trainActionPanel.add(radEngineFailureUnset);
		
		radEngineFailureSet = new JRadioButton("");
		radEngineFailureSet.setBackground(Color.WHITE);
		radEngineFailureSet.setBounds(196, 32, 21, 23);
		radEngineFailureSet.addActionListener(new TrainActionListener());
		trainActionPanel.add(radEngineFailureSet);
		
		ButtonGroup radGrpEngineFailure = new ButtonGroup();
		radGrpEngineFailure.add(radEngineFailureUnset);
		radGrpEngineFailure.add(radEngineFailureSet);
		
		JLabel lblSignalFailure = new JLabel("Signal Pickup Failure");
		lblSignalFailure.setBounds(10, 66, 124, 14);
		trainActionPanel.add(lblSignalFailure);
		
		radSignalFailureUnset = new JRadioButton("");
		radSignalFailureUnset.setSelected(true);
		radSignalFailureUnset.setBackground(Color.WHITE);
		radSignalFailureUnset.setBounds(140, 57, 21, 23);
		radSignalFailureUnset.addActionListener(new TrainActionListener());
		trainActionPanel.add(radSignalFailureUnset);
		
		radSignalFailureSet = new JRadioButton("");
		radSignalFailureSet.setBackground(Color.WHITE);
		radSignalFailureSet.setBounds(196, 57, 21, 23);
		radSignalFailureSet.addActionListener(new TrainActionListener());
		trainActionPanel.add(radSignalFailureSet);
		
		ButtonGroup radGrpSignalFailure = new ButtonGroup();
		radGrpSignalFailure.add(radSignalFailureUnset);
		radGrpSignalFailure.add(radSignalFailureSet);
		
		JLabel lblBrakeFailure = new JLabel("Brake Failure");
		lblBrakeFailure.setBounds(10, 91, 124, 14);
		trainActionPanel.add(lblBrakeFailure);
		
		radBrakeFailureUnset = new JRadioButton("");
		radBrakeFailureUnset.setSelected(true);
		radBrakeFailureUnset.setBackground(Color.WHITE);
		radBrakeFailureUnset.setBounds(140, 82, 21, 23);
		radBrakeFailureUnset.addActionListener(new TrainActionListener());
		trainActionPanel.add(radBrakeFailureUnset);
		
		radBrakeFailureSet = new JRadioButton("");
		radBrakeFailureSet.setBackground(Color.WHITE);
		radBrakeFailureSet.setBounds(196, 82, 21, 23);
		radBrakeFailureSet.addActionListener(new TrainActionListener());
		trainActionPanel.add(radBrakeFailureSet);
		
		ButtonGroup radGrpBrakeFailure = new ButtonGroup();
		radGrpBrakeFailure.add(radBrakeFailureUnset);
		radGrpBrakeFailure.add(radBrakeFailureSet);
		
		JLabel lblEmergencyBrake = new JLabel("Emergency Brake");
		lblEmergencyBrake.setBounds(10, 116, 124, 14);
		trainActionPanel.add(lblEmergencyBrake);
		
		radEBrakeUnset = new JRadioButton("");
		radEBrakeUnset.setSelected(true);
		radEBrakeUnset.setBackground(Color.WHITE);
		radEBrakeUnset.setBounds(140, 107, 21, 23);
		radEBrakeUnset.addActionListener(new TrainActionListener());
		trainActionPanel.add(radEBrakeUnset);
		
		radEBrakeSet = new JRadioButton("");
		radEBrakeSet.setBackground(Color.WHITE);
		radEBrakeSet.setBounds(196, 107, 21, 23);
		radEBrakeSet.addActionListener(new TrainActionListener());
		trainActionPanel.add(radEBrakeSet);
		
		ButtonGroup radGrpEBrake = new ButtonGroup();
		radGrpEBrake.add(radEBrakeUnset);
		radGrpEBrake.add(radEBrakeSet);
		
		// add train status section
		statusTable = new JTable(new AttributeTable());
		statusTable.setShowGrid(false);
		statusTable.setRowSelectionAllowed(false);
		
		JPanel trainStatusPanel = new JPanel();
		trainStatusPanel.setLayout(new BorderLayout(0, 0));
		trainStatusPanel.setBackground(Color.WHITE);
		trainStatusPanel.setBounds(367, 47, 217, 303);
		frmTrainModel.getContentPane().add(trainStatusPanel);
		trainStatusPanel.add(statusTable, BorderLayout.CENTER);
		
		// set main window visible
		frmTrainModel.setVisible(true);
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
	 *  Class name: TrainActionListener
	 *  Inheritance: ActionListener
	 *  Attributes: None
	 *  Methods:
	 *  	public void actionPerformed(ActionEvent)
	 *  Functionality: Listen for interactions such as failures
	 *  	and emergency braking
	 *  Visibility: private
	 *  From requirement number: 3.2.2 Train Model
	  *******************************************************/
	private class TrainActionListener implements ActionListener {
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
				{"Speed", ""},
				{"Speed Limit", ""},
				{"Authority", ""},
				{"Cars", ""},
				{"Crew", ""},
				{"Passengers", ""},
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
