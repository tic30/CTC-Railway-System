package cn.ctc.test;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Exam2 extends Thread implements ActionListener{
    //声明图形界面元素
    private JButton  startJb;
    private JButton stopJb;
    private JButton resetJb;
    private JLabel displayJl ;
     
    //定义变量存储时分秒
    int hour =0;
    int min =0;
    int sec =0 ;
    boolean isRun ;
     
    public Exam2(){
        JFrame jf = new JFrame("计时器");
        startJb = new JButton("开始");
        stopJb = new JButton("结束");
        resetJb = new JButton("重置");
        displayJl = new JLabel("00:00:00");
        this.startJb.addActionListener(this);
        this.stopJb.addActionListener(this);
        this.resetJb.addActionListener(this);
        jf.setSize(300,200);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(new FlowLayout());
        jf.getContentPane().add(this.displayJl);
        jf.getContentPane().add(this.startJb);
        jf.getContentPane().add(this.stopJb);
        jf.getContentPane().add(this.resetJb);
        jf.setResizable(false);
        jf.setVisible(true);
        this.start();
    }
     
 
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==this.startJb){
            this.isRun = true;
             
        }else if(e.getSource()==this.resetJb){
            this.hour=0;
            this.min =0;
            this.sec =0 ;
            showTime();
        }else if(e.getSource()== this.stopJb){
            isRun=false;
             
        }
    }
 
 
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(true){
            if(isRun){
                sec +=1 ;
                if(sec >= 60){
                    sec = 0;
                    min +=1 ;
                }
                if(min>=60){
                    min=0;
                    hour+=1;
                }
                showTime();
            }
 
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
             
        }
    }
 
     
    private void showTime(){
        String strTime ="" ;
        if(hour < 10)
            strTime = "0"+hour+":";
        else
            strTime = ""+hour+":";
         
        if(min < 10)
            strTime = strTime+"0"+min+":";
        else
            strTime =strTime+ ""+min+":";
         
        if(sec < 10)
            strTime = strTime+"0"+sec;
        else
            strTime = strTime+""+sec;
         
        //在窗体上设置显示时间
        this.displayJl.setText(strTime);
    }
     
    public static void main(String[] args){
        new Exam2();
    }
}