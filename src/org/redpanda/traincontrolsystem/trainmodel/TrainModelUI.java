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
	
	private class TrainSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting()) {
				if(trainList.getSelectedIndex() != -1) {
					currentTrain = trains.get(trainList.getSelectedValue() - 1);
					updateDisplay();
				}
			}
		}
	}
	
	private class TrainActionListener implements ActionListener {
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
	
	private class AttributeTable extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private String[] columnNames = {"Item", "Value"};
		private Object[][] values = {
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
		
		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return values.length;
		}

		public Object getValueAt(int row, int col) {
			return values[row][col];
		}
		
		public void setValueAt(Object value, int row, int col) {
			values[row][col] = (String) value;
			fireTableCellUpdated(row, col);
		}
		
		public boolean isCellEditable(int row, int col) {
			return false;
		}
	}
}
