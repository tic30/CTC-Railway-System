package cn.ctc;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cn.ctc.bean.Schedule;
import cn.ctc.util.AuthorityUtil;

public class UpdateAuthorityFrame extends JFrame implements ActionListener{
	
	private JComboBox comboBox;
	private JTextField textField;
	private JButton btnNewButton;
	private List<Schedule> resultList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateAuthorityFrame frame = new UpdateAuthorityFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public UpdateAuthorityFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 390);
		getContentPane().setLayout(null);
	}	

	/**
	 * Create the frame.
	 */
	
	public UpdateAuthorityFrame(List<Schedule> paramList) {
		resultList = paramList;
		//.setDefaultClosedOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
		setResizable(false);
		setBounds(100, 100, 450, 390);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("TrainNo:");
		lblNewLabel.setBounds(10, 78, 175, 32);
		getContentPane().add(lblNewLabel);
		
		comboBox=new JComboBox();
	    comboBox.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				//System.out.println(comboBox.getSelectedIndex());
			}
	    	
	    });
	    
	    if(resultList!=null && resultList.size()>0){
	    	for(Schedule s:resultList){
	    		comboBox.addItem(s.getLine());
	    	}
	    }
	    
	    comboBox.setBounds(183, 79, 241, 30);
	    getContentPane().add(comboBox);
	    
	    JLabel lblNewLabel_1 = new JLabel("Authority Change Amount:");
	    lblNewLabel_1.setBounds(10, 164, 175, 32);
	    getContentPane().add(lblNewLabel_1);
	    
	    textField = new JTextField();
	    textField.setBounds(186, 164, 238, 32);
	    getContentPane().add(textField);
	    textField.setColumns(10);
	    
	    btnNewButton = new JButton("Commit");
	    btnNewButton.setBounds(126, 258, 194, 41);
	    btnNewButton.addActionListener(this);
	    getContentPane().add(btnNewButton);
		
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnNewButton){
			if(comboBox.getSelectedIndex()<0){
				JOptionPane.showMessageDialog(null, "Please Select A Train", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				return ;
			}
			
			String authority = textField.getText();
			if(authority==null||"".equals(authority)){
				JOptionPane.showMessageDialog(null, "Please Enter Authority Change Amount ", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				return ;
			}
			
			
			try {
				Integer.parseInt(authority);
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Authority Change Amount Is Error ", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			
			
			int index = comboBox.getSelectedIndex(); 
			Schedule schedule = this.resultList.get(index);
			if(schedule.getAuthority()+Integer.parseInt(authority)<=0){
				JOptionPane.showMessageDialog(null, "Stop the train now", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				//authority = "-"+schedule.getAuthority();
				authority = "0";
				//return ;
			}
			
			
			
			if(Integer.parseInt(authority)>0){
				String content = JOptionPane.showInputDialog("Pease Enter Authority Sequence And Speed Sequence:");
				if(content==null||"".equals(content.trim())){
					JOptionPane.showMessageDialog(null, "Change Authority  Authority fail", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				String contents [] = content.trim().split(" ");
				int len = contents.length;
				//System.out.println(len);
				//System.out.println(authority+schedule.getAuthority());
				if(len!=(Integer.parseInt(authority)+schedule.getAuthority())*2){
					JOptionPane.showMessageDialog(null, "Authority Sequence And Speed Sequence Length Is Wrong", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
					return ;
				}else{
					
					String authsequence="";
					for(int i=0;i<len/2;i++){
						if(i==0){
							authsequence = contents[i];
						}else{
							authsequence = authsequence+","+contents[i];
						}
					}
					schedule.setAuthsequence(authsequence);
					//System.out.println(authsequence);
					
					String speedsequence="";
					for(int i=len/2;i<len;i++){
						if(i==len/2){
							speedsequence = contents[i];
						}else{
							speedsequence = speedsequence+","+contents[i];
						}
					}
					schedule.setSpeedsequence(speedsequence);
				}
			}else{
				String authsequence=schedule.getAuthsequence();
				String speedsequence=schedule.getSpeedsequence();
				
				String contents1 [] = authsequence.trim().split(",");
				authsequence ="";
				for(int i=0;i<Integer.parseInt(authority);i++){
					if(i==0){
						authsequence=contents1[i];
					}else{
						authsequence=authsequence+","+contents1[i];
					}
				}
				
				schedule.setAuthsequence(authsequence);
				
				String contents2 [] = speedsequence.trim().split(",");
				speedsequence="";
				for(int i=0;i<Integer.parseInt(authority);i++){
					if(i==0){
						speedsequence=contents2[i];
					}else{
						speedsequence=speedsequence+","+contents2[i];
					}
				}
				schedule.setSpeedsequence(speedsequence);
			}
			
			schedule.setAuthority(schedule.getAuthority()+Integer.parseInt(authority));
			this.resultList.remove(index);
			this.resultList.add(schedule);
			
			String content ="autho"+" "+ schedule.getLine()+" "+Integer.parseInt(authority)+" "+ schedule.getAuthsequence().replaceAll(",", " ")+" "+ schedule.getSpeedsequence().replaceAll(",", " ");
			AuthorityUtil.saveTxt(content);
			JOptionPane.showMessageDialog(null, "Change Authority  Authority Successfully", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
			
			
			
			
		}
		
		
		
	}
}
