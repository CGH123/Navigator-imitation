package navigator.background;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import navigator.UI.MainFrame;
/*login是登陆界面类，这是登陆界面的主窗口
 * 
 * */
public class login extends JFrame{
 Container c;
 CInstead c1=new CInstead();
 public TextField f1;
 public TextField f2;
 JButton b1;
 JButton b2;
 JButton b3;
 JLabel l1=new JLabel("账号");
 JLabel l2=new JLabel("密码");
 JPanel p1=new JPanel();
 JPanel p2=new JPanel();
 JPanel p3=new JPanel();
 JPanel p=new JPanel(); 
 //构造函数
 public login(){
	 MainFrame.dog=new dialog();
	 setResizable(false);
	 setContentPane(c1);//为底层布局添加panel，显示背景图片
   Container cp=getContentPane();
   p.setLayout(new GridLayout(4,1));
   f1=new TextField(10);
      f2=new TextField(10);
      f2.setEchoChar('*');
   b1=new JButton("登录");
   b2=new JButton("重置");
   b3=new JButton("注册新用户");
   cp.setLayout(null);//设置无布局
   p1.add(l1);
   p1.add(f1);
   p2.add(l2);
   p2.add(f2);
   //将布局设置为透明，使能看见背景图片
   p.setOpaque(false);
   p1.setOpaque(false);
   p2.setOpaque(false);
   p3.setOpaque(false);
  p3.add(b1);
  p3.add(b2);
  p3.add(b3);
  p.add(p1);
  p.add(p2);
  p.add(p3);
  p.setLocation(40,40);
  p.setSize(300,170);
  cp.add(p);
  //为三个按钮添加监视器
  b1.addActionListener(new Enter());
  b2.addActionListener(new ReWrite()); 
  b3.addActionListener(new register()); 
  b1.setOpaque(false);//将三个按钮设置为透明
  b2.setOpaque(false);
  b3.setOpaque(false);
  b1.setContentAreaFilled(false);  //设置无填充使其透明
  b1.setFocusPainted(false);  //设置无焦点
  b1.setRolloverEnabled(true);  //鼠标选定会显示边框
  b2.setContentAreaFilled(false);  
  b2.setFocusPainted(false);  
  b2.setRolloverEnabled(true);  
  b3.setContentAreaFilled(false);  
  b3.setFocusPainted(false);  
  b3.setRolloverEnabled(true);  
  setcolor();//改变控件字体颜色函数
  this.setTitle("系统登录");
  this.setSize(600,200);
  this.setVisible(true);
  setSize(new Dimension(400,300)); 
  Toolkit toolkit = Toolkit.getDefaultToolkit();
  Dimension scmSize = toolkit.getScreenSize();
this.setLocation(scmSize.width / 2 -200 ,//使界面出现在屏幕正中间
			scmSize.height / 2 -150);
	
 }
 //改变控件字体颜色函数
 public void setcolor(){
	 l1.setForeground(Color.blue);
	 l2.setForeground(Color.blue);
	 b1.setForeground(Color.blue);
	 b2.setForeground(Color.blue);
	 b3.setForeground(Color.blue);
 }
 //登陆按钮监视器的类
 class Enter implements ActionListener{

  @SuppressWarnings("static-access")
public void actionPerformed(ActionEvent e)
  {  
              try {
				if(MainFrame.read.Read()){
					MainFrame.dog.JL.setText("登陆成功！！！");
					MainFrame.dog.setVisible(true);
					//添加功能
					MainFrame.log.setVisible(false);
					MainFrame.getMainFrame().UserLogo.setVisible(true);
					MainFrame.UserName.setVisible(true);
					MainFrame.UserName.setText(MainFrame.log.f1.getText());
					 //objectContainer.frame.JB.setVisible(true);
				  }
				else{
					MainFrame.dog.JL.setText("账号或密码不正确！！！");
					MainFrame.dog.setVisible(true);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
  }
 }
 //重置按钮监视器的类
 class ReWrite implements ActionListener{
  public void actionPerformed(ActionEvent e)
  {
   f1.setText("");
   f2.setText("");
   f1.requestFocus();
  }
 }
 //注册新用户的类
 class register implements ActionListener{
	  public void actionPerformed(ActionEvent e)
	  {
		MainFrame.reg.setVisible(true);
	   
	  }
	 }
 }
//用于显示图片panel的类
class CInstead extends JPanel  
{   
ImageIcon icon;  
Image img;  
public CInstead()  
{   
icon=new ImageIcon(MainFrame.getMainFrame().getClass().getResource("/res/login.jpg" ));  
img=icon.getImage();  
}   
public void paintComponent(Graphics g)  
{   
super.paintComponent(g);  
g.drawImage(img,0,0,null );  
}   
}  
