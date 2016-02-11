package navigator.background;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import navigator.UI.MainFrame;
/*
 * 注册新用户的class
 */
public class Register extends JFrame{
CInstead c1=new CInstead(); //创建JPanel子类型对象，用来承载背景图片
 TextField f1;
 TextField f2;
 TextField f3;
 JButton b1;
 JButton b2;
 JLabel l1=new JLabel("账号");
  JLabel l2=new JLabel("密码");
  JLabel sp=new JLabel("    ");
  JPanel p1=new JPanel();
  JPanel p2=new JPanel();
  JPanel p4=new JPanel(); 
  JPanel p=new JPanel();
 public Register(){
	 setContentPane(c1);//设置背景图片
  Container cp=getContentPane();
  //将panel都设置为透明，使能看到背景图片
  p.setOpaque(false);
  p1.setOpaque(false);
  p2.setOpaque(false);
  p4.setOpaque(false); 
  p.setLayout(new GridLayout(4,1));//设置为GridLayout布局
   f1=new TextField(10);
      f2=new TextField(10);
      f2.setEchoChar('*');
   f3=new TextField(10);
   b1=new JButton("注册");
   b2=new JButton("重置");
   cp.setLayout(null);
   p.setLocation(80,60);
   p.setSize(300,200);
   b1.setContentAreaFilled(false);  //设置无填充
   b1.setFocusPainted(false);  //设置无焦点
   b1.setRolloverEnabled(true);  //设置鼠标选定时出现边框
   b2.setContentAreaFilled(false);  
   b2.setFocusPainted(false);  
   b2.setRolloverEnabled(true);  
   setcolor();//更改颜色
  p1.add(l1);
  p1.add(f1);
  p2.add(l2);
  p2.add(f2);
  p4.add(b1);
  p4.add(sp);
  p4.add(b2);
  p.add(p1);
  p.add(p2);
  p.add(p4);
  cp.add(p);
  b2.addActionListener(new ReWrite());//添加监视器
  b1.addActionListener(new register());
  this.setTitle("注册界面");
  this.setSize(500,380);
  Toolkit toolkit = Toolkit.getDefaultToolkit();
  Dimension scmSize = toolkit.getScreenSize();
  this.setLocation(scmSize.width / 2 -250 ,
  			scmSize.height / 2 -100);
 }
 public void setcolor(){
	 l1.setForeground(Color.white);
	 l2.setForeground(Color.white);
	 b1.setForeground(Color.white);
	 b2.setForeground(Color.white);
 }
 class ReWrite implements ActionListener{
  public void actionPerformed(ActionEvent e)
  {
   f1.setText("");
   f2.setText("");
   f3.setText("");
   f1.requestFocus();
  }
 }
   class  register implements ActionListener{
	  public void actionPerformed(ActionEvent e)
	  { 
	   try {
		if(f1.getText().length()!=0&&f2.getText().length()!=0){
			if(!MainFrame.read.ReadUser()){//判断用户名是否存在
			MainFrame.dog.JL.setText("注册成功");
			MainFrame.dog.setVisible(true);
			new TextOutputTest();  //将账号信息写入到文件中
			MainFrame.reg.setVisible(false);
			}
			else {MainFrame.dog.JL.setText("账号已经存在");
			MainFrame.dog.setVisible(true);
			}

		}
		   else{
			   MainFrame.dog.JL.setText("账号或密码不能为空");
				MainFrame.dog.setVisible(true);
		  }
	} catch (HeadlessException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	 }
   }
   //用来承载背景图片JPanel的子类
   class CInstead extends JPanel  
{   
ImageIcon icon;  
Image img;  
public CInstead()  
{   
icon=new ImageIcon(MainFrame.getMainFrame().getClass().getResource("/res/Picture.png" ));  
img=icon.getImage();  
}   
public void paintComponent(Graphics g)  
{   
super.paintComponent(g);  
g.drawImage(img,0,0,null );  
}   
}   
}
