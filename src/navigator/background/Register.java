package navigator.background;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import navigator.UI.MainFrame;
/*
 * ע�����û���class
 */
public class Register extends JFrame{
CInstead c1=new CInstead(); //����JPanel�����Ͷ����������ر���ͼƬ
 TextField f1;
 TextField f2;
 TextField f3;
 JButton b1;
 JButton b2;
 JLabel l1=new JLabel("�˺�");
  JLabel l2=new JLabel("����");
  JLabel sp=new JLabel("    ");
  JPanel p1=new JPanel();
  JPanel p2=new JPanel();
  JPanel p4=new JPanel(); 
  JPanel p=new JPanel();
 public Register(){
	 setContentPane(c1);//���ñ���ͼƬ
  Container cp=getContentPane();
  //��panel������Ϊ͸����ʹ�ܿ�������ͼƬ
  p.setOpaque(false);
  p1.setOpaque(false);
  p2.setOpaque(false);
  p4.setOpaque(false); 
  p.setLayout(new GridLayout(4,1));//����ΪGridLayout����
   f1=new TextField(10);
      f2=new TextField(10);
      f2.setEchoChar('*');
   f3=new TextField(10);
   b1=new JButton("ע��");
   b2=new JButton("����");
   cp.setLayout(null);
   p.setLocation(80,60);
   p.setSize(300,200);
   b1.setContentAreaFilled(false);  //���������
   b1.setFocusPainted(false);  //�����޽���
   b1.setRolloverEnabled(true);  //�������ѡ��ʱ���ֱ߿�
   b2.setContentAreaFilled(false);  
   b2.setFocusPainted(false);  
   b2.setRolloverEnabled(true);  
   setcolor();//������ɫ
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
  b2.addActionListener(new ReWrite());//��Ӽ�����
  b1.addActionListener(new register());
  this.setTitle("ע�����");
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
			if(!MainFrame.read.ReadUser()){//�ж��û����Ƿ����
			MainFrame.dog.JL.setText("ע��ɹ�");
			MainFrame.dog.setVisible(true);
			new TextOutputTest();  //���˺���Ϣд�뵽�ļ���
			MainFrame.reg.setVisible(false);
			}
			else {MainFrame.dog.JL.setText("�˺��Ѿ�����");
			MainFrame.dog.setVisible(true);
			}

		}
		   else{
			   MainFrame.dog.JL.setText("�˺Ż����벻��Ϊ��");
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
   //�������ر���ͼƬJPanel������
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
