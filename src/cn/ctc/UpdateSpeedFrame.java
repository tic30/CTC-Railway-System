package cn.ctc;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cn.ctc.bean.Trace;
import cn.ctc.util.SpeedUtil;
import cn.ctc.util.TraceUtil;

import javax.swing.JTextField;
import javax.swing.JLabel;

public class UpdateSpeedFrame extends JFrame implements ActionListener{


	private JPanel contentPane;
	
	private JComboBox comboBox_1;
	private JComboBox comboBox_2 = new JComboBox();;
	private JButton btnCommit;
	private JTextField textField;
	private JLabel lblBlock;
	private JLabel lblSpeed;
	private  List<Trace> redTraces;
	private  List<Trace> greenTraces;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateSpeedFrame frame = new UpdateSpeedFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UpdateSpeedFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	    
	    comboBox_1 = new JComboBox();
	    
	    comboBox_1.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				System.out.println(comboBox_1.getSelectedIndex());
				System.out.println(comboBox_1.getSelectedItem());
			}
	    	
	    });
	    
	    comboBox_1.setBounds(82, 26, 241, 30);
	    
	    comboBox_1.addItem("Red");
	    comboBox_1.addItem("Green");
	    
	    contentPane.add(comboBox_1);
	    comboBox_2 = new JComboBox();
	    comboBox_2.setBounds(82, 86, 241, 30);
	    
	    contentPane.add(comboBox_2);
	    
	    JButton btnCommit = new JButton("Commit");
	    btnCommit.setBounds(95, 204, 212, 23);
	    contentPane.add(btnCommit);
	    
	    textField = new JTextField();
	    textField.setBounds(82, 143, 241, 30);
	    contentPane.add(textField);
	    textField.setColumns(10);
	    
	    JLabel lblLine = new JLabel("Line:");
	    lblLine.setBounds(10, 26, 54, 30);
	    contentPane.add(lblLine);
	    
	    lblBlock = new JLabel("Block:");
	    lblBlock.setBounds(10, 86, 54, 30);
	    contentPane.add(lblBlock);
	    
	    lblSpeed = new JLabel("Speed:");
	    lblSpeed.setBounds(10, 143, 54, 30);
	    contentPane.add(lblSpeed);
	}
	
	
	public UpdateSpeedFrame( List<Trace> paramRedTraces, List<Trace> paramGreenTraces) {
		this.redTraces = paramRedTraces;
		this.greenTraces = paramGreenTraces;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	    
	    
	    /**comboBox_1 start**/
	    comboBox_1 = new JComboBox();
	    comboBox_1.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				System.out.println(comboBox_1.getSelectedIndex());
				String line = (String)comboBox_1.getSelectedItem();
				System.out.println(line);
				comboBox_2.removeAll();
				if("Green".equals(line)){
					
					 if(greenTraces!=null &&greenTraces.size()>0){
					    	for(Trace trace:greenTraces){
					    		System.out.println(trace.getBlocknumber());
					    		comboBox_2.addItem(trace.getBlocknumber());
					    	}
					    }
				}else if("Red".equals(line)){
					 if(redTraces!=null &&redTraces.size()>0){
					    	for(Trace trace:redTraces){
					    		System.out.println(trace.getBlocknumber());
					    		comboBox_2.addItem(trace.getBlocknumber());
					    	}
					    }
				}
			}
	    	
	    });
	    
	    comboBox_1.setBounds(82, 26, 241, 30);
	    comboBox_1.addItem("Red");
	    comboBox_1.addItem("Green");
	    contentPane.add(comboBox_1);
	    /**comboBox_1 end**/
	    
	    /**comboBox_2 start**/
	    
	    comboBox_2.setBounds(82, 86, 241, 30);
	    if(redTraces!=null &&redTraces.size()>0){
	    	for(Trace trace:redTraces){
	    		comboBox_2.addItem(trace.getBlocknumber());
	    	}
	    }
	    contentPane.add(comboBox_2);
	    /**comboBox_2 end**/
	    
	    btnCommit = new JButton("Commit");
	    btnCommit.addActionListener(this);
	    btnCommit.setBounds(95, 204, 212, 23);
	    contentPane.add(btnCommit);
	    
	    textField = new JTextField();
	    textField.setBounds(82, 143, 241, 30);
	    contentPane.add(textField);
	    textField.setColumns(10);
	    
	    JLabel lblLine = new JLabel("Line:");
	    lblLine.setBounds(10, 26, 54, 30);
	    contentPane.add(lblLine);
	    
	    lblBlock = new JLabel("Block:");
	    lblBlock.setBounds(10, 86, 54, 30);
	    contentPane.add(lblBlock);
	    
	    lblSpeed = new JLabel("Speed:");
	    lblSpeed.setBounds(10, 143, 54, 30);
	    contentPane.add(lblSpeed);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnCommit){
			int index = comboBox_2.getSelectedIndex();
			if(index<0){
				JOptionPane.showMessageDialog(null, "Please Select A Block", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String line = (String)comboBox_1.getSelectedItem();
			
			String speed = this.textField.getText();
			if(speed==null||"".equals(speed)){
				JOptionPane.showMessageDialog(null, "Please Enter Speed ", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				return ;
			}
			
			try {
				Double.parseDouble(speed);
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Speed Is Error ", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			double newSpeed = 0;
			if("Red".equals(line)){
				Trace trace = this.redTraces.get(index);
				trace.getSpeedlimit();
				
				newSpeed = trace.getSpeedlimit()+Double.parseDouble(speed);
				
				if(newSpeed<=0){
					//JOptionPane.showMessageDialog(null, "Speed Change  Is Not Less than Zero ", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
					//return ;
					newSpeed = 0;
				}
				trace.setSpeedlimit(newSpeed);
				this.redTraces.remove(index);
				this.redTraces.add(trace);
				
			}else if("Green".equals(line)){
				Trace trace = this.greenTraces.get(index);
				newSpeed = trace.getSpeedlimit()+Double.parseDouble(speed);
				if(newSpeed<=0){
					JOptionPane.showMessageDialog(null, "Speed Change  Is Not Less than Zero ", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
					return ;
				}
				
				trace.setSpeedlimit(newSpeed);
				
				this.greenTraces.remove(index);
				this.greenTraces.add(trace);
				
			}
			SpeedUtil.saveTxt("speed"+" "+("Red".equals(line)?"r":"g")+" "+comboBox_2.getSelectedItem()+" "+ newSpeed);
			
			
			JOptionPane.showMessageDialog(null, "Update Speed Successfully", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
}
