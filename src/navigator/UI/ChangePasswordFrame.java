package navigator.UI;

import navigator.dataStruct.*;
import navigator.algorithm.*;
import navigator.background.*;
import navigator.UI.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/*
 * ���Ǹ��������frame
 */
public class ChangePasswordFrame extends JFrame {
	 Container c;
	 CInstead c1=new CInstead();
	public TextField f1;
	public TextField f2;
	TextField f3;
	TextField f4;
	JButton b1;
	JButton b2;
	JLabel l1;
	JLabel l2;
	String power;

	public ChangePasswordFrame() {
		 setContentPane(c1);//���ñ���ͼƬ
		Container cp = getContentPane();
		 l1 = new JLabel("ԭ����");
		 l2 = new JLabel("����������");
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p4 = new JPanel();
		 JPanel p=new JPanel();
		 p.setLayout(new GridLayout(3,1));
		f1 = new TextField(10);
		f2 = new TextField(10);
		f1.setEchoChar('*');
		f2.setEchoChar('*');
		f3 = new TextField(10);
		f4 = new TextField(30);
		b1 = new JButton("ȷ��");
		b2 = new JButton("����");
		p1.add(l1);
		p1.add(f1);
		p2.add(l2);
		p2.add(f2);
		p4.add(b1);
		p4.add(b2);
		cp.add(p);
		p.add(p1);
		p.add(p2);
		p.add(p4);
		 //����������Ϊ͸����ʹ�ܿ�������ͼƬ
		   p.setOpaque(false);
		   p1.setOpaque(false);
		   p2.setOpaque(false);
		   p4.setOpaque(false);
		   b1.setOpaque(false);//��������ť����Ϊ͸��
		   b2.setOpaque(false);
		   b1.setContentAreaFilled(false);  //���������ʹ��͸��
		   b1.setFocusPainted(false);  //�����޽���
		   b1.setRolloverEnabled(true);  //���ѡ������ʾ�߿�
		   b2.setContentAreaFilled(false);  
		   b2.setFocusPainted(false);  
		   b2.setRolloverEnabled(true);    
		   setcolor();//�ı�ؼ�������ɫ����
		b1.addActionListener(new Enter());//��ť��Ӽ�����
		b2.addActionListener(new ReWrite());
		this.setTitle("ϵͳ��¼");
		this.setSize(500, 200);
		this.setVisible(true);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension scmSize = toolkit.getScreenSize();
		this.setLocation(scmSize.width / 2 - 300, scmSize.height / 2 - 100);//ʹ�����������Ļ���м�

	}
	 //ȷ����ť������
	class Enter implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				if (MainFrame.getMainFrame().read.ReturnPassword()) {
					JOptionPane.showMessageDialog(null, "�������޸ģ������µ�¼");
					MainFrame.getMainFrame().UserName.setVisible(false);
					MainFrame.getMainFrame().UserLogo.setVisible(false);
					MainFrame.getMainFrame().CPF.setVisible(false);
					MainFrame.getMainFrame().log.setVisible(true);
					MainFrame.getMainFrame().log.f1.setText("");
					MainFrame.getMainFrame().log.f2.setText("");
				} else {
					JOptionPane.showMessageDialog(null, "ԭ���벻��ȷ");
				}
			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	// ���ð�ť������
	class ReWrite implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			f1.setText("");
			f2.setText("");
			f3.setText("");
			f1.requestFocus();
		}
	}
	//������ʾͼƬpanel����
	class CInstead extends JPanel  
	{   
	ImageIcon icon;  
	Image img;  
	public CInstead()  
	{   
	icon=new ImageIcon(MainFrame.getMainFrame().getClass().getResource("/res/ChangePasswordFrame.png" ));  
	img=icon.getImage();  
	}   
	public void paintComponent(Graphics g)  
	{   
	super.paintComponent(g);  
	g.drawImage(img,0,0,null );  
	}   
	} 
	//�ı�ؼ�������ɫ����
	 public void setcolor(){
		 l1.setForeground(Color.blue);
		 l2.setForeground(Color.blue);
		 b1.setForeground(Color.blue);
		 b2.setForeground(Color.blue);
	 }
}
