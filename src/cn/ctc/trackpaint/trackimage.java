package cn.ctc.trackpaint;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class trackimage extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private BufferedImage img;

    public trackimage() {
       try {                
          img = ImageIO.read(new File("track_drawing_detail.png"));
       } catch (IOException ex) {
    	   ex.printStackTrace();
       }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this); // see javadoc for more info on the parameters            
    }
    
    public static void main(String[] args)
    {
    	JFrame f = new JFrame("Load Image Sample");
        
        f.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
 
        f.add(new trackimage());
        f.pack();
        f.setVisible(true);
    }
}

