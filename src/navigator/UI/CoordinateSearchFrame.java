package navigator.UI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import navigator.dataStruct.Point;


//用于输入坐标的JFrame子类
public class CoordinateSearchFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3336235314019404890L;
	CInstead c1=new CInstead(); 
	private JPanel xPanel,yPanel,operPanel,p;//输入x,y坐标的两个panel
	private JLabel xLabel,yLabel;//x,y坐标的label
	private JTextField xTextField,yTextField;//x,y坐标的text field
	private JButton confirmButton,cancelButton;//确定和取消按钮
	private FindHelpList temList;//创建帮助查找界面对象
	//构造函数
	CoordinateSearchFrame(){
		  setContentPane(c1);//设置背景图片
		  Container cp=getContentPane();
		xPanel=new JPanel();
		yPanel=new JPanel();
		operPanel=new JPanel();
		p=new JPanel();
		xPanel.setOpaque(false);
		yPanel.setOpaque(false);
		operPanel.setOpaque(false);
		p.setOpaque(false);
		xLabel=new JLabel("请输入x坐标（整数）");
		yLabel=new JLabel("请输入y坐标（整数）");
		
		xTextField=new JTextField(5);
		yTextField=new JTextField(5);
		
		confirmButton=new JButton("确定");
		cancelButton=new JButton("取消");
		confirmButton.setContentAreaFilled(false);  //设置不填充
		confirmButton.setFocusPainted(false);  //设置无焦点
		confirmButton.setRolloverEnabled(true);  //设置鼠标选中时显示边框
		cancelButton.setContentAreaFilled(false);  
		cancelButton.setFocusPainted(false);  
		cancelButton.setRolloverEnabled(true);  
		xPanel.add(xLabel,BorderLayout.WEST);
		xPanel.add(xTextField,BorderLayout.EAST);
		yPanel.add(yLabel,BorderLayout.WEST);
		yPanel.add(yTextField,BorderLayout.EAST);
		operPanel.add(confirmButton,BorderLayout.EAST);
		operPanel.add(cancelButton,BorderLayout.WEST);
		xTextField.setToolTipText("范围是0-10000");//设置输入x范围
		yTextField.setToolTipText("范围是0-10000");//设置输入y范围
		cp.setLayout(null);
		setcolor();
		p.setLocation(40,50);
		p.setSize(300,200);
		p.add(xPanel,BorderLayout.NORTH);
		p.add(yPanel,BorderLayout.CENTER);
		p.add(operPanel,BorderLayout.SOUTH);
		cp.add(p);
		//确定按钮添加监视器
		confirmButton.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				String xCoordinate,yCoordinate;
				xCoordinate = xTextField.getText();
				yCoordinate = yTextField.getText();
				int x,y;
				
				/*
				 * 检查两个输入的合法性
				 */
				if(xCoordinate==null||xCoordinate.length()==0){
					JOptionPane.showMessageDialog(CoordinateSearchFrame.this, 
							"错误输入","x坐标为空，请输入x坐标",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if(!isInteger(xCoordinate)){
					JOptionPane.showMessageDialog(CoordinateSearchFrame.this,
							"x坐标不是一个整数","错误输入",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				
				if(yCoordinate==null||yCoordinate.length()==0){
					JOptionPane.showMessageDialog(CoordinateSearchFrame.this, 
							"y坐标为空，请输入y坐标","错误输入",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if(!isInteger(yCoordinate)){
					JOptionPane.showMessageDialog(CoordinateSearchFrame.this,
							"y坐标不是一个整数", "错误输入", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				x=Integer.parseInt(xCoordinate);
				y=Integer.parseInt(yCoordinate);
				
				if(x<0||x>10000){
					JOptionPane.showMessageDialog(CoordinateSearchFrame.this,
							"x坐标范围是0-10000，请重新输入", "x坐标超出合法范围", JOptionPane.WARNING_MESSAGE);
					return ;
				}
				
				if(y<0||y>10000){
					JOptionPane.showMessageDialog(CoordinateSearchFrame.this,
							"y坐标范围是0-10000，请重新输入", "y坐标超出合法范围", JOptionPane.WARNING_MESSAGE);
					return ;
				}
				try {
					new historyfile("searchasis",x,y);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				MainFrame.getMainFrame().focusOnCoordinate(x, y);
				Point temp=new Point(x,y);
				if(temList!=null) temList.dispose();
				temList=new FindHelpList(MainFrame.getMainFrame().autoThread.auto.generateFindVec(temp));
				//CoordinateSearchFrame.this.dispose();
			}
			
		});
		//取消按钮添加监视器
		cancelButton.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				CoordinateSearchFrame.this.dispose();
			}
		});
	}
	//更改颜色的函数
	public void setcolor(){
		 confirmButton.setForeground(Color.white);
		 cancelButton.setForeground(Color.white);
		 xLabel.setForeground(Color.white);
		 yLabel.setForeground(Color.white);
	 }
	//初始化
	public void frameInitial(){
		Image icon;
		try {
			icon = ImageIO.read(this.getClass().getResource("/res/icon1.jpg"));//设置图标
			this.setIconImage(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.pack();
		this.setTitle("Search");
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setSize(400,350);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	//检查输入是否为整数
	private boolean isInteger(String str){
		int len=str.length();
		for(int index=0;index<len;index++){
			char a=str.charAt(index);
			if(a<'0'||a>'9') return false;
		}
		return true;
	}
	
	@Override
	protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            switch(getDefaultCloseOperation()) {
              case HIDE_ON_CLOSE:
                 setVisible(false);
                 if(temList!=null)  temList.dispose();
                 MainFrame.getMainFrame().cancelShowNear();
                 MainFrame.getMainFrame().cancelShowFocusPoint();
                 MainFrame.getMainFrame().cancelShowFocusNearPoint();
                 break;
              case DISPOSE_ON_CLOSE:
                 dispose();
                 break;
              case DO_NOTHING_ON_CLOSE:
                 default: 
                 break;
          case EXIT_ON_CLOSE:
                  // This needs to match the checkExit call in
                  // setDefaultCloseOperation
        System.exit(0);
        break;
            }
        }
    }
	//用来承载背景图片的JPanel类
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

