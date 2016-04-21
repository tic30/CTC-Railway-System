package cn.ctc;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Timer extends Thread {

	private JLabel label;

	int hour = 0;
	int min = 0;
	int sec = 0;
	int xtime = 1;

	public Timer(JLabel label) {
		super();
		this.label = label;
		Object[] options = {"x 1","x 10"};
		int tempxtime = JOptionPane.showOptionDialog(null, "Please choose timer speed","Confirm",0,JOptionPane.INFORMATION_MESSAGE,null,options,null);
		//System.out.println(xtime);
		if(tempxtime == 1)
			this.xtime = 10;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {

			sec += 1;
			if (sec >= 60) {
				sec = 0;
				min += 1;
			}
			if (min >= 60) {
				min = 0;
				hour += 1;
			}
			showTime();

			try {
				Thread.sleep(1000/xtime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void showTime() {
		String strTime = "";
		if (hour < 10)
			strTime = "0" + hour + ":";
		else
			strTime = "" + hour + ":";

		if (min < 10)
			strTime = strTime + "0" + min + ":";
		else
			strTime = strTime + "" + min + ":";

		if (sec < 10)
			strTime = strTime + "0" + sec;
		else
			strTime = strTime + "" + sec;

		// display
		label.setText(strTime);
	}

	public static void main(String[] args){
		JFrame frame = new JFrame("TestJFilePicker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Add content to the window.
		JLabel label1 = new JLabel();
		new Timer(label1);
		frame.add(label1);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}
