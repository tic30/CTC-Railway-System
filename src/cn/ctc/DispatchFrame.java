package cn.ctc;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import cn.ctc.bean.Schedule;
import cn.ctc.util.ScheduleUtil;

public class DispatchFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	
	private JComboBox comboBox_1;
	private JTextField textField;
	private JLabel lblAuthority;
	private JTextField textField_1;
	private JLabel lblAuthoritySequence;
	private JLabel lblSpeedSequence;
	private JTextField textField_2;
	private JButton btnNewButton;
	
	private List<Schedule> scheduleltList = new ArrayList<Schedule>();
	private JTextField textField_3;
	private JLabel lblDepartureTime;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DispatchFrame frame = new DispatchFrame();
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
	public DispatchFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 492, 427);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		 /**comboBox_1 start**/
	    comboBox_1 = new JComboBox();
	    comboBox_1.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				//System.out.println(comboBox_1.getSelectedIndex());
				String line = (String)comboBox_1.getSelectedItem();
				//System.out.println(line);
			}
	    });
	    contentPane.setLayout(null);
	    
	    comboBox_1.setBounds(151, 20, 241, 30);
	    comboBox_1.addItem("Red");
	    comboBox_1.addItem("Green");
	    contentPane.add(comboBox_1);
	    
	    JLabel lblNewLabel = new JLabel("Line:");
	    lblNewLabel.setBounds(10, 20, 142, 30);
	    contentPane.add(lblNewLabel);
	    
	    textField = new JTextField();
	    textField.setBounds(151, 79, 241, 30);
	    contentPane.add(textField);
	    
	    lblAuthority = new JLabel("Authority:");
	    lblAuthority.setBounds(10, 78, 142, 30);
	    contentPane.add(lblAuthority);
	    
	    textField_1 = new JTextField();
	    textField_1.setBounds(151, 149, 241, 30);
	    contentPane.add(textField_1);
	    
	    lblAuthoritySequence = new JLabel("Authority Sequence:");
	    lblAuthoritySequence.setBounds(10, 148, 142, 30);
	    contentPane.add(lblAuthoritySequence);
	    
	    lblSpeedSequence = new JLabel("Speed Sequence:");
	    lblSpeedSequence.setBounds(10, 206, 142, 30);
	    contentPane.add(lblSpeedSequence);
	    
	    textField_2 = new JTextField();
	    textField_2.setBounds(151, 211, 241, 30);
	    contentPane.add(textField_2);
	    
	    btnNewButton = new JButton("Commit");
	    btnNewButton.addActionListener(this);
	    btnNewButton.setBounds(173, 318, 208, 37);
	    contentPane.add(btnNewButton);
	    
	    textField_3 = new JTextField();
	    textField_3.setBounds(151, 263, 241, 35);
	    contentPane.add(textField_3);
	    textField_3.setColumns(10);
	    
	    lblDepartureTime = new JLabel("Departure Time:");
	    lblDepartureTime.setBounds(10, 263, 142, 30);
	    contentPane.add(lblDepartureTime);
	  
	}
	
	
	public DispatchFrame(List<Schedule> tempScheduleltList) {
		this.scheduleltList = tempScheduleltList;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 492, 427);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		 /**comboBox_1 start**/
	    comboBox_1 = new JComboBox();
	    comboBox_1.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				//System.out.println(comboBox_1.getSelectedIndex());
				String line = (String)comboBox_1.getSelectedItem();
				//System.out.println(line);
			}
	    });
	    contentPane.setLayout(null);
	    
	    comboBox_1.setBounds(151, 20, 241, 30);
	    comboBox_1.addItem("Red");
	    comboBox_1.addItem("Green");
	    contentPane.add(comboBox_1);
	    
	    JLabel lblNewLabel = new JLabel("Line:");
	    lblNewLabel.setBounds(10, 20, 142, 30);
	    contentPane.add(lblNewLabel);
	    
	    textField = new JTextField();
	    textField.setBounds(151, 79, 241, 30);
	    contentPane.add(textField);
	    
	    lblAuthority = new JLabel("Authority:");
	    lblAuthority.setBounds(10, 78, 142, 30);
	    contentPane.add(lblAuthority);
	    
	    textField_1 = new JTextField();
	    textField_1.setBounds(151, 149, 241, 30);
	    contentPane.add(textField_1);
	    
	    lblAuthoritySequence = new JLabel("Authority Sequence:");
	    lblAuthoritySequence.setBounds(10, 148, 142, 30);
	    contentPane.add(lblAuthoritySequence);
	    
	    lblSpeedSequence = new JLabel("Speed Sequence:");
	    lblSpeedSequence.setBounds(10, 206, 142, 30);
	    contentPane.add(lblSpeedSequence);
	    
	    textField_2 = new JTextField();
	    textField_2.setBounds(151, 211, 241, 30);
	    contentPane.add(textField_2);
	    
	    btnNewButton = new JButton("Commit");
	    btnNewButton.addActionListener(this);
	    btnNewButton.setBounds(173, 318, 208, 37);
	    contentPane.add(btnNewButton);
	    
	    textField_3 = new JTextField();
	    textField_3.setBounds(151, 263, 241, 35);
	    contentPane.add(textField_3);
	    textField_3.setColumns(10);
	    
	    lblDepartureTime = new JLabel("Departure Time:");
	    lblDepartureTime.setBounds(10, 263, 142, 30);
	    contentPane.add(lblDepartureTime);
	  
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnNewButton){
			String authority = this.textField.getText();
			if(authority==null ||"".equals(authority.trim())){
				JOptionPane.showMessageDialog(null, "Please Enter Authority ", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				return ;
			}
			
			String authoritysequence =this.textField_1.getText();
			if(authoritysequence==null ||"".equals(authoritysequence.trim())){
				JOptionPane.showMessageDialog(null, "Please Enter Authority Sequence", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				return ;
			}
			
			String speedsequence =this.textField_2.getText();
			
			if(speedsequence==null ||"".equals(speedsequence.trim())){
				JOptionPane.showMessageDialog(null, "Please Enter Speed Sequence", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				return ;
			}
			
			String departuretime = this.textField_3.getText();
			if(departuretime==null || "".equals(departuretime.trim())){
				JOptionPane.showMessageDialog(null, "Please Enter Departure Time", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				return ;
			}
			
			
			
			try {
				Integer.parseInt(authority);
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Authority Is Error", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			if(Integer.parseInt(authority)<0){
				JOptionPane.showMessageDialog(null, "Authority Is Not Less than Zero", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				return ;
			}
			
			String content1 [] = authoritysequence.trim().split(" ");
			
			String content2 [] = speedsequence.trim().split(" ");
			
			if(content1!=null&& content1.length>0){
				try {
					if(Integer.parseInt(content1[0])<0){
						JOptionPane.showMessageDialog(null, "Authority Sequence Is Not Less than Zero", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Authority Sequence Is Error", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
					return ;
				}
			}
			
			
			if(content2!=null&& content2.length>0){
				try {
					if(Integer.parseInt(content2[0])<0){
						JOptionPane.showMessageDialog(null, "Speed Sequence Is Not Less than Zero", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Speed Sequence Is Error", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
					return ;
				}
			}
			
			
			
			if(content1.length!=Integer.parseInt(authority)){
				JOptionPane.showMessageDialog(null, "Authority Sequence Length Is Error", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			if(content2.length!=Integer.parseInt(authority)){
				JOptionPane.showMessageDialog(null, "Speed Sequence Length Is Error", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			
			String times[] =departuretime.trim().split(":");
			if(times.length!=3){
				JOptionPane.showMessageDialog(null, "departure Time  Is Error", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				return ;
			}
			
			for(String time:times){
				if(time==null ||time.length()!=2){
					JOptionPane.showMessageDialog(null, "departure Time  Is Error", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
					return ;
				}
			}
			
			Schedule s = new Schedule();
			String line = (String)comboBox_1.getSelectedItem();
			int i=0;
			if(this.scheduleltList!=null && this.scheduleltList.size()>0){
				for(Schedule temp:scheduleltList){
					if(temp.getLine()!=null){//line.equalsIgnoreCase()
						 i++;
					}
				}
			}
			s.setLine(("Red".equals(line)?"r":"g")+(i+1));
			s.setAuthority(Integer.parseInt(authority));
		    //s.setLine((String)comboBox_1.getSelectedItem());
			s.setDeparturetime(departuretime);
			s.setSpeedsequence(speedsequence.replace(" ", ","));
			s.setAuthsequence(authoritysequence.replaceAll(" ", ","));
			//System.out.println(s.toString());
			this.scheduleltList.add(s);
			String content = ("Red".equals(line)?"r":"g")+(i+1)+" "+s.getAuthority()+" "+authoritysequence.trim()+" "+speedsequence.trim();
			ScheduleUtil.saveTxt(content);
			
			JOptionPane.showMessageDialog(null, "Add Schedule Successfully", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
