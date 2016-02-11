package navigator.UI;

import navigator.dataStruct.*;
import navigator.algorithm.*;
import navigator.background.*;
import navigator.UI.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;



//用于输入最短路起点和终点的输入框JFrame子类
public class ShortestPathInputFrame extends JFrame{
	CInstead c1=new CInstead(); 
	private JTextField startPntInput,endPntInput;//输入起点，终点的输入框
	private JLabel startPntLabel,endPntLabel;//提示输入起点终点的标签
	private JPanel upPanel,downPanel,operPanel,p;//几个JPanel
	private TrieTree tree;//字典树
	private JButton confirm,cancel,help,moving,drivingConfirm,drivingCancel;//所用到的JButton
	private Vector<Point>pntVec;//点的集合数组
	private Vector<Integer>answerPath;//最短路的答案数组
	private ComboBoxFrame searchNameFrame;//用于查找地名的输入框
	
	private boolean isMoving;//判断最短路是否合法
	private boolean isStop; //确定车流动态点是否移动或者终止
	
	//构造函数，pntVec是地图里面地名的集合数组，flowConsidered表示是否考录车流量
	ShortestPathInputFrame(Vector<Point>pntVec,final boolean flowConsidered){
		 setContentPane(c1);
		 Container cp=getContentPane();
		isMoving=false;
		isStop=false;
		this.pntVec=pntVec;
		startPntInput=new JTextField(7);
		endPntInput=new JTextField(7);
		startPntLabel=new JLabel("请输入起点");
		endPntLabel=new JLabel("请输入终点");
		upPanel=new JPanel();
		downPanel=new JPanel();
		operPanel=new JPanel();
		p=new JPanel();
		p.setOpaque(false);
		upPanel.setOpaque(false);
		downPanel.setOpaque(false);
		operPanel.setOpaque(false);
		tree=new TrieTree();
		confirm=new JButton("确定");
		cancel=new JButton("取消");
		help=new JButton("地点大全");
		moving=new JButton("模拟车辆移动");
		drivingConfirm=new JButton("锁定屏幕");
		drivingCancel=new JButton("解锁屏幕");
		 setcolor();
		int size=pntVec.size();
		for(int i=0;i<size;i++) tree.insertPoint(pntVec.elementAt(i));
		
		
		//添加相应控件
		upPanel.add(startPntLabel,BorderLayout.WEST);
		upPanel.add(startPntInput,BorderLayout.EAST);
		downPanel.add(endPntLabel,BorderLayout.WEST);
		downPanel.add(endPntInput,BorderLayout.EAST);
		operPanel.setLayout(new GridLayout(3,3));
		operPanel.add(confirm);
		operPanel.add(cancel);
		operPanel.add(moving);
		operPanel.add(help);
		operPanel.add(drivingConfirm);
		operPanel.add(drivingCancel);
		confirm.setContentAreaFilled(false);  
		confirm.setFocusPainted(false);  
		confirm.setRolloverEnabled(true);  
		cancel.setContentAreaFilled(false);  
		cancel.setFocusPainted(false);  
		cancel.setRolloverEnabled(true);  
		moving.setContentAreaFilled(false);  
		moving.setFocusPainted(false);  
		moving.setRolloverEnabled(true);  
		help.setContentAreaFilled(false);  
		help.setFocusPainted(false);  
		help.setRolloverEnabled(true);  
		drivingConfirm.setContentAreaFilled(false);  
		drivingConfirm.setFocusPainted(false);  
		drivingConfirm.setRolloverEnabled(true);  
		drivingCancel.setContentAreaFilled(false);  
		drivingCancel.setFocusPainted(false);  
		drivingCancel.setRolloverEnabled(true);  
		cp.setLayout(null);
		p.add(upPanel,BorderLayout.NORTH);
		p.add(downPanel,BorderLayout.CENTER);
		p.add(operPanel,BorderLayout.SOUTH);
		p.setLocation(130,80);
		p.setSize(250,200);
		cp.add(p);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		  Dimension scmSize = toolkit.getScreenSize();
		  this.setLocation(scmSize.width / 2 -275 ,
		  			scmSize.height / 2 -190);
		
		
		
//////////////////////////////////////给相应控件添加相应监听器//////////////////////////////////////
		
		//”确定“按钮的监听器
		confirm.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String startPointName=startPntInput.getText();
				String endPointName=endPntInput.getText();
				
				//判断字符是否合法
				if(startPointName==null||startPointName.length()==0){
					JOptionPane.showMessageDialog(ShortestPathInputFrame.this, 
							"错误输入","起点名为空，请输入起点！",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//如果终点名输入为空
				if(endPointName==null||endPointName.length()==0){
					JOptionPane.showMessageDialog(ShortestPathInputFrame.this, 
							"错误输入","终点名为空，请输入终点！",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//如果起点名输入有误
				if(!tree.ifHas(startPointName)){
					JOptionPane.showMessageDialog(ShortestPathInputFrame.this, 
							"错误输入","不存在与起点输入相同的地名，请重新输入起点！",JOptionPane.WARNING_MESSAGE);
					startPntInput.setText("");
					return;
				}
				
				//如果终点名输入有误
				if(!tree.ifHas(endPointName)){
					JOptionPane.showMessageDialog(ShortestPathInputFrame.this, 
							"错误输入","不存在与终点输入相同的地名，请重新输入终点！",JOptionPane.WARNING_MESSAGE);
					endPntInput.setText("");
					return;
				}
				
				//如果合法，就求最短路
				isMoving=true;
				Point startPoint,endPoint;
				startPoint=null;
				endPoint=null;
				int size=ShortestPathInputFrame.this.pntVec.size();
				for(int i=0;i<size;i++){
					if(startPointName.equals(ShortestPathInputFrame.this.pntVec.elementAt(i).getName()))
						startPoint=ShortestPathInputFrame.this.pntVec.elementAt(i);
					if(endPointName.equals(ShortestPathInputFrame.this.pntVec.elementAt(i).getName()))
						endPoint=ShortestPathInputFrame.this.pntVec.elementAt(i);
				}
				try {
					new historyfile("pathonly",startPoint.getNum(),endPoint.getNum());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ShortestPath ansPath=new ShortestPath(startPoint,endPoint,ShortestPathInputFrame.this.pntVec);
				if(flowConsidered)
				{
					if(ansPath.TrafficDijkstra())//如果能够找到最短路
						answerPath=ansPath.getPathVec();//获取最短路的点
					else 
						answerPath=null;
				}
				else
				{
					if(ansPath.Dijkstra())//如果能够找到最短路
						answerPath=ansPath.getPathVec();//获取最短路的点
						
					else 
						answerPath=null;
				}
								
				MainFrame.getMainFrame().showShortestPath(answerPath,startPoint);//给MainFrame设置最短路的点
			}
			
		});
		
		
		//“取消“按钮的监听器
		cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.getMainFrame().cancelShowShortestPath();
				ShortestPathInputFrame.this.dispose();
			}
			
		});
		
		
		//"模拟车辆移动"按钮的监听器
		moving.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				if(answerPath!=null){
					if(!isStop)
						{
							Algorithm.CalculateMoving(answerPath);
							isStop=true;
						}
					else 
						{
							Algorithm.stopThread=true;
							isStop=false;
						}
				}
				else
					JOptionPane.showMessageDialog(ShortestPathInputFrame.this, 
							"错误操作","请先确定运动路程的起点和终点！",JOptionPane.WARNING_MESSAGE);
			}
		});
		
		
		
		//"锁定屏幕"按钮的监听器
		drivingConfirm.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(isMoving)  
					MainFrame.getMainFrame().setIsLock();
				else 
					JOptionPane.showMessageDialog(ShortestPathInputFrame.this, 
							"错误操作","请先模拟车辆移动！",JOptionPane.WARNING_MESSAGE);
			}
			
		});
		
		//响应“解除屏幕”按钮的事件的监听器
		drivingCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(isMoving)
					MainFrame.getMainFrame().cancelIsLock();
				else 
					JOptionPane.showMessageDialog(ShortestPathInputFrame.this, 
							"错误操作","请先模拟车辆移动！",JOptionPane.WARNING_MESSAGE);
			}
			
		});
		
		
		//响应“帮助”按钮的事件的监听器
		help.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				searchNameFrame=new ComboBoxFrame(MainFrame.getMainFrame()
						.autoThread.auto.getPntVec(),false);
				searchNameFrame.frameInitial();
			}
		});
		
/////////////////////////////////////////添加监听器完毕/////////////////////////////////////////
		
		
		
	}
	
	
	//框体的初始化函数
	public void frameInitial(){
		Image icon;//设置图标
		try {
			icon = ImageIO.read(this.getClass().getResource("/res/icon1.jpg"));
			this.setIconImage(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setSize(550,380);
		this.setLocationRelativeTo(null);
		this.setTitle("起点和终点");
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	
	@Override
	protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            switch(getDefaultCloseOperation()) {
              case HIDE_ON_CLOSE:
                 setVisible(false);
                 MainFrame.getMainFrame().cancelShowShortestPath();
                 answerPath=null;
                 isMoving=false;
                 Algorithm.stopThread=true;
                 if(searchNameFrame!=null)
                	 searchNameFrame.dispose();
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
	public void setcolor(){
		 confirm.setForeground(Color.white);
		 cancel.setForeground(Color.white);
		 moving.setForeground(Color.white);
		 help.setForeground(Color.white);
		 drivingConfirm.setForeground(Color.white);
		 drivingCancel.setForeground(Color.white);
		 startPntLabel.setForeground(Color.white);
		 endPntLabel.setForeground(Color.white);
	 }
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
