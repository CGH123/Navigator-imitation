package navigator.background;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import navigator.UI.MainFrame;

public class dialog extends JFrame{
	JLabel JL=new JLabel("登录成功!!!");
	private JButton JB=new JButton("确定");
	private JPanel P=new JPanel();
	private JPanel JP1=new JPanel();
	private JPanel JP2=new JPanel();
	public dialog(){
		setSize(300,140);
		setTitle("消息");
		CInstead c1=new CInstead();
		setContentPane(c1);
		P.setLayout(new GridLayout(2,1));
		add(P);
		P.add(JP1);
		P.add(JP2);
		JP1.add(JL);
		JP2.add(JB);
		 P.setOpaque(false);
		   JP1.setOpaque(false);
		   JP2.setOpaque(false);
		  JB.setContentAreaFilled(false);  //设置无填充使其透明
		  JB.setFocusPainted(false);  //设置无焦点
		  JB.setRolloverEnabled(true);  //鼠标选定会显示边框
		  JB.setForeground(Color.red);
		  JL.setForeground(Color.red);
		 this.setResizable(false);
		 this.setVisible(false);
		 Toolkit toolkit = Toolkit.getDefaultToolkit();
		  Dimension scmSize = toolkit.getScreenSize();
		this.setLocation(scmSize.width / 2 -150 ,//使界面出现在屏幕正中间
					scmSize.height / 2 -70);
		JB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MainFrame.getMainFrame().dog.setVisible(false);
			}
			  
		  });
		
	}
}
