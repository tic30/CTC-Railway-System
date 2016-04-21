package cn.ctc.util;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FileChooserUtil extends JPanel implements ActionListener {
	static private final String newline = "\n";
	JButton openButton;
	JTextArea log;
	JFileChooser fc;
	String filename1 = "";

	public FileChooserUtil() {
		super(new BorderLayout());

		//Create the log first, because the action listeners
		//need to refer to it.
		log = new JTextArea(5,20);
		log.setMargin(new Insets(5,5,5,5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);

		//Create a file chooser
		fc = new JFileChooser();
		openButton = new JButton("Open a File...");
		openButton.addActionListener(this);

		//For layout purposes, put the buttons in a separate panel
		JPanel buttonPanel = new JPanel(); //use FlowLayout
		buttonPanel.add(openButton);

		//Add the buttons and the log to this panel.
		add(buttonPanel, BorderLayout.PAGE_START);
		add(logScrollPane, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {

		//Handle open button action.
		if (e.getSource() == openButton) {
			int returnVal = fc.showOpenDialog(FileChooserUtil.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				//This is where a real application would open the file.
				filename1 = file.getName();
				log.append("Opening: " + filename1 + "." + newline);
			} else {
				log.append("Open command cancelled by user." + newline);
			}
			log.setCaretPosition(log.getDocument().getLength());
		}
	}
	
	public String getFileName(){
		return filename1;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("TestJFilePicker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Add content to the window.
		frame.add(new FileChooserUtil());

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}