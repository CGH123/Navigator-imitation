package navigator.background;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import navigator.UI.MainFrame;
/*login�ǵ�½�����࣬���ǵ�½�����������
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
 JLabel l1=new JLabel("�˺�");
 JLabel l2=new JLabel("����");
 JPanel p1=new JPanel();
 JPanel p2=new JPanel();
 JPanel p3=new JPanel();
 JPanel p=new JPanel(); 
 //���캯��
 public login(){
	 MainFrame.dog=new dialog();
	 setResizable(false);
	 setContentPane(c1);//Ϊ�ײ㲼�����panel����ʾ����ͼƬ
   Container cp=getContentPane();
   p.setLayout(new GridLayout(4,1));
   f1=new TextField(10);
      f2=new TextField(10);
      f2.setEchoChar('*');
   b1=new JButton("��¼");
   b2=new JButton("����");
   b3=new JButton("ע�����û�");
   cp.setLayout(null);//�����޲���
   p1.add(l1);
   p1.add(f1);
   p2.add(l2);
   p2.add(f2);
   //����������Ϊ͸����ʹ�ܿ�������ͼƬ
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
  //Ϊ������ť��Ӽ�����
  b1.addActionListener(new Enter());
  b2.addActionListener(new ReWrite()); 
  b3.addActionListener(new register()); 
  b1.setOpaque(false);//��������ť����Ϊ͸��
  b2.setOpaque(false);
  b3.setOpaque(false);
  b1.setContentAreaFilled(false);  //���������ʹ��͸��
  b1.setFocusPainted(false);  //�����޽���
  b1.setRolloverEnabled(true);  //���ѡ������ʾ�߿�
  b2.setContentAreaFilled(false);  
  b2.setFocusPainted(false);  
  b2.setRolloverEnabled(true);  
  b3.setContentAreaFilled(false);  
  b3.setFocusPainted(false);  
  b3.setRolloverEnabled(true);  
  setcolor();//�ı�ؼ�������ɫ����
  this.setTitle("ϵͳ��¼");
  this.setSize(600,200);
  this.setVisible(true);
  setSize(new Dimension(400,300)); 
  Toolkit toolkit = Toolkit.getDefaultToolkit();
  Dimension scmSize = toolkit.getScreenSize();
this.setLocation(scmSize.width / 2 -200 ,//ʹ�����������Ļ���м�
			scmSize.height / 2 -150);
	
 }
 //�ı�ؼ�������ɫ����
 public void setcolor(){
	 l1.setForeground(Color.blue);
	 l2.setForeground(Color.blue);
	 b1.setForeground(Color.blue);
	 b2.setForeground(Color.blue);
	 b3.setForeground(Color.blue);
 }
 //��½��ť����������
 class Enter implements ActionListener{

  @SuppressWarnings("static-access")
public void actionPerformed(ActionEvent e)
  {  
              try {
				if(MainFrame.read.Read()){
					MainFrame.dog.JL.setText("��½�ɹ�������");
					MainFrame.dog.setVisible(true);
					//��ӹ���
					MainFrame.log.setVisible(false);
					MainFrame.getMainFrame().UserLogo.setVisible(true);
					MainFrame.UserName.setVisible(true);
					MainFrame.UserName.setText(MainFrame.log.f1.getText());
					 //objectContainer.frame.JB.setVisible(true);
				  }
				else{
					MainFrame.dog.JL.setText("�˺Ż����벻��ȷ������");
					MainFrame.dog.setVisible(true);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
  }
 }
 //���ð�ť����������
 class ReWrite implements ActionListener{
  public void actionPerformed(ActionEvent e)
  {
   f1.setText("");
   f2.setText("");
   f1.requestFocus();
  }
 }
 //ע�����û�����
 class register implements ActionListener{
	  public void actionPerformed(ActionEvent e)
	  {
		MainFrame.reg.setVisible(true);
	   
	  }
	 }
 }
//������ʾͼƬpanel����
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
