package cn.ctc;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cn.ctc.bean.Trace;
import cn.ctc.util.TraceUtil;

import javax.swing.JButton;
import javax.swing.JLabel;

public class CloseopenTrackFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	
	private JComboBox comboBox;
	
	private JComboBox comboBox_1;
	private JComboBox comboBox_2=new JComboBox();;
	private JButton btnCommit;
	private JLabel lblNewLabel;
	private JLabel lblLine;
	private JLabel lblBlock;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CloseopenTrackFrame frame = new CloseopenTrackFrame();
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
	public CloseopenTrackFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox=new JComboBox();
	    comboBox.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				System.out.println(comboBox.getSelectedIndex());
			}
	    	
	    });
	    
	    comboBox.addItem("Close");
	    comboBox.addItem("Open");
	    
	    comboBox.setBounds(82, 29, 241, 30);
	    getContentPane().add(comboBox);
	    
	    comboBox_1 = new JComboBox();
	    
	    comboBox_1.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				System.out.println(comboBox_1.getSelectedIndex());
				System.out.println(comboBox_1.getSelectedItem());
			}
	    	
	    });
	    
	    comboBox_1.setBounds(82, 80, 241, 30);
	    
	    comboBox_1.addItem("Red");
	    comboBox_1.addItem("Green");
	    
	    contentPane.add(comboBox_1);
	    comboBox_2 = new JComboBox();
	    comboBox_2 = new JComboBox();
	    comboBox_2.setBounds(82, 137, 241, 30);
	    
	    contentPane.add(comboBox_2);
	    
	    JButton btnCommit = new JButton("Commit");
	    btnCommit.setBounds(95, 204, 212, 23);
	    contentPane.add(btnCommit);
	    
	    lblNewLabel = new JLabel("Open/Close:");
	    lblNewLabel.setBounds(10, 29, 74, 30);
	    contentPane.add(lblNewLabel);
	    
	    lblLine = new JLabel("Line:");
	    lblLine.setBounds(10, 80, 74, 30);
	    contentPane.add(lblLine);
	    
	    lblBlock = new JLabel("Block:");
	    lblBlock.setBounds(10, 137, 74, 30);
	    contentPane.add(lblBlock);
	}
	
	
	public CloseopenTrackFrame(final List<Trace> redTraces,final List<Trace> greenTraces) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		/**comboBox start**/
		comboBox=new JComboBox();
	    comboBox.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				System.out.println(comboBox.getSelectedIndex());
				
			}
	    	
	    });
	    
	    comboBox.addItem("Close");
	    comboBox.addItem("Open");
	    comboBox.setBounds(82, 29, 241, 30);
	    getContentPane().add(comboBox);
	    /**comboBox end**/
	    
	    
	    /**comboBox_1 start**/
	    comboBox_1 = new JComboBox();
	    comboBox_1.setBounds(82, 80, 241, 30);
	    comboBox_1.addItem("Red");
	    comboBox_1.addItem("Green");
	    contentPane.add(comboBox_1);
	    comboBox_1.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				System.out.println(comboBox_1.getSelectedIndex());
				String line = (String)comboBox_1.getSelectedItem();
				System.out.println(line);
				//comboBox_2.removeAll();
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
	    
	   
	    /**comboBox_1 end**/
	    
	    /**comboBox_2 start**/
	    
	    comboBox_2.setBounds(82, 137, 241, 30);
	    if(redTraces!=null &&redTraces.size()>0){
	    	for(Trace trace:redTraces){
	    		comboBox_2.addItem(trace.getBlocknumber());
	    	}
	    }
	    contentPane.add(comboBox_2);
	    /**comboBox_2 end**/
	    
	    lblNewLabel = new JLabel("Open/Close:");
	    lblNewLabel.setBounds(10, 29, 74, 30);
	    contentPane.add(lblNewLabel);
	    
	    lblLine = new JLabel("Line:");
	    lblLine.setBounds(10, 80, 74, 30);
	    contentPane.add(lblLine);
	    
	    lblBlock = new JLabel("Block:");
	    lblBlock.setBounds(10, 137, 74, 30);
	    contentPane.add(lblBlock);
	    
	    btnCommit = new JButton("Commit");
	    btnCommit.addActionListener(this);
	    btnCommit.setBounds(95, 204, 212, 23);
	    contentPane.add(btnCommit);
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
			TraceUtil.saveTxt(this.comboBox.getSelectedItem()+" "+("Red".equals(line)?"r":"g")+" "+comboBox_2.getSelectedItem());
			JOptionPane.showMessageDialog(null, "Update Trace Successfully", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
}
