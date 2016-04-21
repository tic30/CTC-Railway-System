package cn.ctc;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


import cn.ctc.bean.Schedule;
import cn.ctc.bean.Trace;
import cn.ctc.trackpaint.trackimage;
import cn.ctc.util.ExcelUtil;
import cn.ctc.util.ScheduleUtil;
import java.awt.Font;
//import org.redpanda.traincontrolsystem.trackmodel;

public class MainFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	
	private JButton btnDispatch;
	
	private JButton btnUpdateAuthority;
	
	private JButton btnCloseopenTrack;
	
	private JButton btnUpdateSpeed;
	
	private JButton btnReadInSchedule;
	
	List<Schedule> resultList = new ArrayList<Schedule>();
	public JLabel timelabel;
	
	List<Trace> redTraces = new ArrayList<Trace>();
	List<Trace> greenTraces = new ArrayList<Trace>();
	private boolean importBl=false;
	
	public JTextArea textArea;
	public JTextArea textArea1;
	public JTextArea textArea2;
	private BufferedImage image;
	
	private trackimage trackPanel;
	/**
	 * @wbp.nonvisual location=261,224
	 */

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					deleteFile();
//					TrackModel trackModel1 = new TrackModel();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void deleteFile(){
		File scheduleFile = new File("schedule.txt");
		scheduleFile.delete();
		
		File eventFile = new File("newEvent.txt");
		eventFile.delete();
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 0, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnDispatch = new JButton("Dispatch");
		btnDispatch.setBounds(787, 551, 207, 31);
		contentPane.add(btnDispatch);
		
		btnUpdateAuthority = new JButton("Update Authority");
		btnUpdateAuthority.setBounds(787, 581, 207, 31);
		contentPane.add(btnUpdateAuthority);
		
		btnCloseopenTrack = new JButton("Close/Open Track");
		btnCloseopenTrack.setBounds(787, 611, 207, 31);
		contentPane.add(btnCloseopenTrack);
		
		btnUpdateSpeed = new JButton("Update Speed");
		btnUpdateSpeed.setBounds(787, 640, 207, 31);
		contentPane.add(btnUpdateSpeed);
		
		btnReadInSchedule = new JButton("Read in Schedule");
		btnReadInSchedule.setBounds(787, 520, 207, 31);
		contentPane.add(btnReadInSchedule);
		
		btnDispatch.addActionListener(this);
		btnUpdateAuthority.addActionListener(this);
		btnCloseopenTrack.addActionListener(this);
		btnUpdateSpeed.addActionListener(this);
		btnReadInSchedule.addActionListener(this);
		
		//paint track
		/*
		char[] segNames="ABCDEFGHIJKLMN".toCharArray();
		int[] lengths=new int[] {200,100,100,100,100,100,200,100,100,100,200,240,200};
		int[] speedLimits=new int[] {50,50,50,50,30,30,30,30,20,40,40,40,40};
		int[] grades=new int[] {0,0,1,2,2,2,1,0,0,0,0,0,0};
		int[] x1s=new int[] {220,420,520,590,520,420,220,150,150,150,520,720,520};
		int[] x2s=new int[] {420,520,590,590,590,520,420,220,150,220,720,720,720};
		int[] y1s=new int[] {320,320,320,250,80,80,80,150,150,250,320,320,80};
		int[] y2s=new int[] {320,320,250,150,150,80,80,80,250,320,320,80,80};
		trackPanel = new cn.ctc.trackpaint.PrototypeTrack(segNames,lengths,x1s,x2s,y1s,y2s,speedLimits,grades);
		trackPanel.setOpaque( false ) ;
		trackPanel.setSize(966, 401);
		trackPanel.setLocation(14, 81);
		trackPanel.setBorder(null);
		contentPane.add(trackPanel);
		*/
		
		textArea = new JTextArea();
		//contentPane.add(textArea);
		//contentPane.add(textArea);
		textArea.setBounds(0, 523, 263, 148);
		trackPanel = new cn.ctc.trackpaint.trackimage();
		trackPanel.setOpaque( false ) ;
		trackPanel.setSize(966, 401);
		trackPanel.setLocation(14, 81);
		trackPanel.setBorder(null);
		contentPane.add(trackPanel);
		
		JScrollPane scroll = new JScrollPane(textArea); 
		scroll.setSize(263, 148);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		scroll.setLocation(0, 523);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		contentPane.add(scroll);
		
		
		textArea1 = new JTextArea();
		//contentPane.add(textArea);
		//contentPane.add(textArea);
		textArea1.setBounds(263, 523, 263, 148);//263
		
		JScrollPane scroll1 = new JScrollPane(textArea1); 
		scroll1.setSize(263, 148);
		scroll1.setOpaque(false);
		scroll1.getViewport().setOpaque(false);
		scroll1.setLocation(263, 523);
		scroll1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		contentPane.add(scroll1);
		
		
		textArea2 = new JTextArea();
		//contentPane.add(textArea);
		//contentPane.add(textArea);
		textArea2.setBounds(525, 523, 263, 148);//263*2=525
		
		JScrollPane scroll2 = new JScrollPane(textArea2); 
		scroll2.setSize(263, 148);
		scroll2.setOpaque(false);
		scroll2.getViewport().setOpaque(false);
		scroll2.setLocation(525, 523);
		scroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		contentPane.add(scroll2);
		
		timelabel = new JLabel("00:00:00");
		timelabel.setForeground(Color.WHITE);
		timelabel.setFont(new Font("Arial", Font.PLAIN, 18));
		timelabel.setBorder(null);
		timelabel.setBounds(67, 45, 93, 23);
		timelabel.setVerticalAlignment(JLabel.CENTER);
		timelabel.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(timelabel);
		
		JLabel headname = new JLabel("CTC Monitoring System");
		headname.setFont(new Font("Arial", Font.PLAIN, 20));
		headname.setForeground(Color.WHITE);
		headname.setBounds(14, 13, 224, 31);
		contentPane.add(headname);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBackground(Color.DARK_GRAY);
		textArea_1.setBounds(0, 2, 994, 516);
		contentPane.add(textArea_1);
		Timer timer = new Timer(timelabel);//compute Timer
		timer.start();
		
		Departure departure = new Departure(this);
		departure.start();
		
		this.redTraces = ExcelUtil.readTrace("Track_Layout_Vehicle_Data_vF1.xlsx", "red");//Red Trace
		this.greenTraces = ExcelUtil.readTrace("Track_Layout_Vehicle_Data_vF1.xlsx", "green");//Green Trace
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(resultList!=null && resultList.size()>0){
			for(Schedule s:resultList){
				System.out.println(s.toString());
			}
		}
		
		if(e.getSource()==this.btnReadInSchedule){
			int m = JOptionPane.showConfirmDialog(null, "Loading Schedule from file: schedule.xlsx","Confirm",JOptionPane.OK_CANCEL_OPTION);
			//System.out.println(m);
			if(m==0){
				if(importBl){
					JOptionPane.showMessageDialog(null, "schedule.xlsx Was Imported Successfully, no need to import again!", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				    return;
				}
				
				List<Schedule> list = ExcelUtil.readSchedule("schedule.xlsx");
				for(Schedule s :list){
					String content = s.getLine()+" "+s.getAuthority()+" "+s.getAuthsequence().replaceAll(",", " ")
							+" "+ s.getSpeedsequence().replaceAll(",", " ");
					ScheduleUtil.saveTxt(content);
					System.out.println(s.toString());
					this.resultList.add(s);					
				}
				importBl = true;
				JOptionPane.showMessageDialog(null, "Import schedule.xlsx Successfully", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
			}
		}else if(e.getSource()==this.btnDispatch){
			DispatchFrame frame = new  DispatchFrame(resultList);
			frame.setVisible(true);			
			
		}else if(e.getSource()==this.btnUpdateAuthority){
			//this.dispose();
			UpdateAuthorityFrame frame = new  UpdateAuthorityFrame(resultList);
			frame.setVisible(true);
		}else if(e.getSource()==this.btnCloseopenTrack){
			CloseopenTrackFrame  frame = new CloseopenTrackFrame(this.redTraces,this.greenTraces);
			frame.setVisible(true);
		}else if(e.getSource()==this.btnUpdateSpeed){
			UpdateSpeedFrame  frame = new UpdateSpeedFrame(this.redTraces,this.greenTraces);
			frame.setVisible(true);
		}
		
	}
}
