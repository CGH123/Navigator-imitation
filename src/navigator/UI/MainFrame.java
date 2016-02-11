package navigator.UI;
import navigator.dataStruct.*;
import navigator.algorithm.*;
import navigator.background.*;
import navigator.UI.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


//主界面类
public class MainFrame extends JFrame{
	public static dialog dog;
	public static ChangePasswordFrame CPF;
	public static login  log;
	public static Register reg;
	public static ReadTxtFile read;
	public static ImageBufferFrame IBF=null;
	
	private TrafficFlowSimulation traffic;
	private static MainFrame mainframe;
	
	private int zoomTimes;//缩放倍数
	
	private int leftX,upY;//窗口左上角的坐标
	private int initX,initY;//记录拖动鼠标时鼠标的原始坐标
	private int centerX,centerY;//当前画面的中心点在normal画面中的坐标
	private int wholeLength;
	
	private boolean isLock;//记录驾驶模式画面是否锁定
	
	public AutoGraphThread autoThread;
	public static JMenu UserName;
	public JMenuItem UserLogo;
	private mapPanel map;
	private String index;//图片存放目录
	private final String normalIndex=new String("d://map//normal//");
	private final String bigIndex=new String("d://map//big//");
	private final String smallIndex=new String("d://map//small//");
	
	boolean drawable[];
	int asis[];
	String fileName[];
	int mapIndex[];
	
	private Splash splash;
	////////////////////////构造函数///////////////////////////////////////
	public static MainFrame getMainFrame()
	{
		if(mainframe==null) mainframe=new MainFrame();
		return mainframe;
	}
	private MainFrame()
	{
		//JL.setVisible(false);
		splash = new Splash("/res/background.jpg");
		isLock=false;
		
		autoThread=new AutoGraphThread();//用一个线程来创建一个AutoGraph对象
		autoThread.start();
		
		setZoomTimes(100);//初始化缩放倍数
		wholeLength=10000;
		upY=0;leftX=0;
		centerX=500;centerY=500;
		
		JMenuBar menubar;
		JPanel Panel=new JPanel();
		JMenu function,navigate,zoom,search,flow;
		JMenuItem includeFlow,notIncludeFlow,history,user,ChangeUser,ChangePassword,Exit,pathOnly,small,normal,big,searchName,searchAsis;
		
		//测试使用
	
		
		//初始化组件
		menubar=new JMenuBar();
		JMenuBar menubarEast=new JMenuBar();
		function=new JMenu("功能");navigate=new JMenu("导航");
		flow=new JMenu("车流量");
		includeFlow=new JMenuItem("开启车流量");
		notIncludeFlow=new JMenuItem("关闭车流量");
		user=new JMenuItem("用户登录"); 
		UserName=new JMenu("");
		UserLogo=new JMenuItem("");
		ChangeUser=new JMenuItem("切换用户");
		history=new JMenuItem("历史记录");
		ChangePassword=new JMenuItem("更改密码");
		Exit=new JMenuItem("退出当前账号");
		ImageIcon ig=new ImageIcon("D:\\map\\Aisi.png");
		ig.setImage(ig.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
		UserLogo.setIcon(ig);
		UserLogo.setVisible(false);
		UserName.setVisible(false);
		//用户功能
		zoom=new JMenu("缩放");
		search=new JMenu("查找");
		pathOnly=new JMenuItem("最短路");
		searchName=new JMenuItem("查找地名");
		searchAsis=new JMenuItem("查找坐标");
		small=new JMenuItem("50%");normal=new JMenuItem("100%");big=new JMenuItem("200%");
		add(Panel,BorderLayout.NORTH);
		Panel.setLayout(new BorderLayout());
		Panel.add(menubar,BorderLayout.WEST);
		Panel.add(menubarEast,BorderLayout.EAST);
		index=new String("d://map//normal//");
		map=new mapPanel();
		//添加组件
		menubar.add(function);
		menubar.add(zoom);
		menubar.add(flow);
		flow.add(includeFlow);
		flow.add(notIncludeFlow);
		menubarEast.add(UserLogo);
		menubarEast.add(UserName);
		UserName.add(ChangeUser);
		UserName.add(ChangePassword);
		UserName.add(history);
		UserName.add(Exit);
		function.add(user);
		function.add(navigate);
		function.add(search);
		navigate.add(pathOnly);
		zoom.add(small);
		zoom.add(normal);
		zoom.add(big);
		search.add(searchName);
		search.add(searchAsis);
		add(map,BorderLayout.CENTER);
		
		history.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//车流量关闭
				
					try {
						new readhistory();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
			
		  });
		notIncludeFlow.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//车流量关闭
				
					 TrafficFlowSimulation.isWork=false;
					 map.cancelTraffic();
			}
			
		  });

		includeFlow.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//车流量显示窗口
				traffic=new TrafficFlowSimulation(MainFrame.getMainFrame().autoThread.auto.getPntVec());
				TrafficFlowSimulation.isWork=true;
				map.setTraffic();
				new Thread(traffic).start();
			}
		  });
		
		//切换用户监视器
		ChangeUser.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				log=new login();
				reg=new Register();
				read=new ReadTxtFile();
			}
		  });
		//更改密码监视器
		ChangePassword.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.getMainFrame().CPF.setVisible(true);
				MainFrame.getMainFrame().CPF.f1.setText("");
				MainFrame.getMainFrame().CPF.f2.setText("");
				// TODO Auto-generated method stub
				
			}
		  });
		Exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UserLogo.setVisible(false);
				UserName.setVisible(false);
			}
		  });
		//上传头像监视器
		 UserLogo.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					//j1.setIcon(new ImageIcon("D:/1.png"));
					UserLogo.setText("");
					 try {
						 MainFrame.getMainFrame().IBF=new ImageBufferFrame();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// objectContainer.frame.IBF.setVisible(true);
				}
				  
			  });
		//菜单项添加监视器
		normal.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				setZoomTimes(100);
				wholeLength=10000;
				index=normalIndex;
				
				//显示图片
				leftX=centerX-500;
				upY=centerY-339;
				if(leftX>wholeLength-1001) leftX=wholeLength-1001;
				if(leftX<0) leftX=0;
				if(upY>wholeLength-679) upY=wholeLength-679;
				if(upY<0) upY=0;
				
				CalculateImage();
				map.putImage(fileName, drawable, asis, zoomTimes, leftX, upY);
			}
		});
		
		//add an action listener for button "small"
		small.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				setZoomTimes(50);
				wholeLength=5000;
				index=smallIndex;
				
				//显示图片
				leftX=centerX/2-500;
				upY=centerY/2-339;
				if(leftX>wholeLength-1001) leftX=wholeLength-1001;
				if(leftX<0) leftX=0;
				if(upY>wholeLength-679) upY=wholeLength-679;
				if(upY<0) upY=0;
				
				CalculateImage(); 
				map.putImage(fileName, drawable, asis, zoomTimes, leftX, upY);
			}
		});
		
		user.addActionListener(new ActionListener(){    //添加用户功能
			@Override
			public void actionPerformed(ActionEvent e){
				log=new login();
				reg=new Register();
				read=new ReadTxtFile();
				CPF=new ChangePasswordFrame();
				CPF.setVisible(false);
			}
		});
		
		//add an action listener for the button "big"
		big.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				setZoomTimes(200);
				wholeLength=20000;
				index=bigIndex;
				
				//显示图片
				leftX=centerX*2-500;
				upY=centerY*2-339;
				if(leftX>wholeLength-1001) leftX=wholeLength-1001;
				if(leftX<0) leftX=0;
				if(upY>wholeLength-679) upY=wholeLength-679;
				if(upY<0) upY=0;
				
				CalculateImage();
				map.putImage(fileName, drawable, asis, zoomTimes, leftX, upY);
			}
		});
		
		searchName.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				ComboBoxFrame searchNameFrame=new ComboBoxFrame(MainFrame.getMainFrame().
						autoThread.auto.getPntVec(),true);
				searchNameFrame.frameInitial();
			}
		});
		
		searchAsis.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				CoordinateSearchFrame searchCoordinateFrame=new CoordinateSearchFrame();
				searchCoordinateFrame.frameInitial();
			}

		});
				
		pathOnly.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				ShortestPathInputFrame pathFrame=new ShortestPathInputFrame(MainFrame.getMainFrame()
						.autoThread.auto.getPntVec(),TrafficFlowSimulation.isWork);
				pathFrame.frameInitial();
			}
		});
		
		//用于点击“载入图片”之后的响应
		/*impMap.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				map.setImported();
				boolean drawable[];
				int asis[];
				String fileName[];
				drawable=new boolean[4];
				asis=new int[32];
				fileName=new String[4];
				drawable[0]=true;
				drawable[1]=drawable[2]=drawable[3]=false;
				leftX=0;upY=0;//initialize the axis
				asis[0]=asis[1]=0;asis[2]=asis[3]=1000;
				asis[4]=asis[5]=0;asis[6]=asis[7]=1000;
				fileName[0]=new String("d://map//normal//graph0.png");
				map.putImage(fileName, drawable, asis, zoomTimes, leftX, upY);
				
			}
		});
		*/
		
		//图片框添加监视器，用于拖动页面时的反应
		map.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				initX=e.getX();
				initY=e.getY();
			}
		});
		map.addMouseMotionListener(new MouseMotionAdapter(){
			@Override
			public void mouseDragged(MouseEvent e){
				int x=e.getX();
				int y=e.getY();
				//System.out.println("滚动条: "+leftX+" "+upY);
				int xDiff=(initX-x)/17;//x坐标移动值
				int yDiff=(initY-y)/17;//y坐标移动值
				int curX=leftX+xDiff;//整个图位于左上角x坐标
				int curY=upY+yDiff;//整个图位于左上角的y坐标
				
				if(curX<0) curX=0;
				else if(curX>wholeLength-1000) curX=wholeLength-1000;
				else ;
				
				if(curY<0) curY=0;
				else if(curY>wholeLength-678) curY=wholeLength-678;
				else ;
				
				leftX=curX;
				upY=curY;
				
				//计算centerX和centerY的值
				if(zoomTimes==50){
					centerX=leftX*2+1000;
					centerY=upY*2+678;
				}
				else if(zoomTimes==100){
					centerX=leftX+500;
					centerY=upY+339;
				}
				else 
				{
					centerX=leftX/2+250;
					centerY=upY/2+182;
				}
				//System.out.println("center: "+centerX+" "+centerY);
				CalculateImage();
				map.putImage(fileName, drawable, asis, zoomTimes, leftX, upY);
				
			}
		});
	}
	static void de(){
		File f = new File("D://map//history.txt"); 
	
	   f.delete();
	 	 
	}
	static void ne(){
		File f1 = new File("D:\\map\\history.txt");
		 try {
			f1.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	////////////////////////////////////////构造函数结束/////////////////////////////////////////
	
	//主函数
	public static void main(String[] args) throws IOException{
		de();
		MainFrame frame=MainFrame.getMainFrame();
		Image icon;
		/*File f1 = new File("D:\\map","Secret.txt");
		 f1.createNewFile();*/
		ne();
		try {
			icon = ImageIO.read(frame.getClass().getResource("/res/icon1.jpg"));
			frame.setIconImage(icon);
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
		frame.setTitle("Brio Navigator");
		Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(1000,730);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
			
		frame.splash.loadingProgram();//加载程序的画面
		//画面加载完后才开始显示导航
		MainFrame.getMainFrame().setVisible(true);
		
		frame.setImage();
	}
	
	//设置重画
		public void getPaint(){
			map.getPaint();
		}
	
	//设置跑点的锁屏
	public void setIsLock(){
		isLock=true;
	}
	
	//取消跑点的锁屏
	public void cancelIsLock(){
		isLock=false;
	}
	
	//模拟车流动态移动
	public void setImitateMoving(Point temp){
		if(!isLock) map.setImitateMoving(temp);
		else {
			map.setIsMoving();
			focusOnPoint(temp,true);
		}
	}
	
	//取消模拟车辆动态移动
	public void cancelImitateMoving(){
		map.cancelImitateMoving();
	}
	
	public void setValue(int percent){
		splash.setValue(percent);
	}
	
	public void setImage(){
		map.setImported();
		boolean drawable[];
		int asis[];
		String fileName[];
		drawable=new boolean[4];
		mapIndex=new int[4];
		asis=new int[32];
		fileName=new String[4];
		drawable[0]=true;
		drawable[1]=drawable[2]=drawable[3]=false;
		mapIndex[0]=0;
		leftX=0;upY=0;//initialize the axis
		asis[0]=asis[1]=0;asis[2]=asis[3]=1000;
		asis[4]=asis[5]=0;asis[6]=asis[7]=1000;
		fileName[0]=new String("d://map//normal//graph0.png");
		map.putImage(fileName, drawable, asis, zoomTimes, leftX, upY);
	}
	
	
	//获取放大倍数和设置放大倍数
	public int getZoomTimes() {
		return zoomTimes;
	}
	public void setZoomTimes(int zoomTimes) {
		this.zoomTimes = zoomTimes;
	}
	
	//设置显示邻近
	public void setShowNear(){
		map.setNear();
	}
	
	//取消显示邻近
	public void cancelShowNear(){
		map.cancelNear();
	}
	
	//设置显示邻近的100点
	public void setShowFocusNearPoint(){
		map.setFocusNearPoint();
	}
	
	//取消显示邻近的100个点
	public void cancelShowFocusNearPoint(){
		map.cancelFocusNearPoint();
	}
	
	//取消显示选中点
	public void cancelShowFocusPoint(){
		map.cancelFocusPoint();
	}
	
	//给mapPanel设置最短路的点
	public void showShortestPath(Vector<Integer>pathPoints,Point startPnt){
		focusOnPoint(startPnt,true);
		map.setPathPoints(pathPoints);
	}
		
	//取消显示最短路
	public void cancelShowShortestPath(){
		map.cancelShortestPath();
	}
	
	public void setNearPointVec(Vector<Point> temVec){
		map.setTemNearPoint(temVec);
	}
	
	//使mapPanel聚焦在一个点上，用于搜索框点击之后将画面转移到一个点上
	//flag为true,则突出的是选择点，为False突出的是临近点
	public void focusOnPoint(Point toFocus,boolean mark){
		int zTime=getZoomTimes();//获取当前放大倍数
		int x=toFocus.getX(),y=toFocus.getY();
		if(zTime==50){
			leftX=x/2-500;
			upY=y/2-339;
			
			if(leftX>wholeLength-1001) leftX=wholeLength-1001;
			if(leftX<0) leftX=0;
			if(upY>wholeLength-679) upY=wholeLength-679;
			if(upY<0) upY=0;
			
			CalculateImage();
			if(mark)
				map.pointFocusedImage(fileName, drawable, asis, leftX, upY,  x, y,true);
			else map.pointFocusedImage(fileName, drawable, asis, leftX, upY,  x, y,false);
		}
		
		else if(zTime==100){
			leftX=x-500;
			upY=y-339;
			
			if(leftX>wholeLength-1001) leftX=wholeLength-1001;
			if(leftX<0) leftX=0;
			if(upY>wholeLength-679) upY=wholeLength-679;
			if(upY<0) upY=0;
			
			CalculateImage();
			if(mark)
				map.pointFocusedImage(fileName, drawable, asis, leftX, upY,  x, y,true);
			else map.pointFocusedImage(fileName, drawable, asis, leftX, upY,  x, y,false);
		}
		
		else if(zTime==200){
			leftX=x*2-500;
			upY=y*2-339;
			
			if(leftX>wholeLength-1001) leftX=wholeLength-1001;
			if(leftX<0) leftX=0;
			if(upY>wholeLength-679) upY=wholeLength-679;
			if(upY<0) upY=0;
			
			CalculateImage();
			if(mark)
				map.pointFocusedImage(fileName, drawable, asis, leftX, upY,  x, y,true);
			else map.pointFocusedImage(fileName, drawable, asis, leftX, upY,  x, y,false);
		}
	}
	
	public void focusOnCoordinate(int x,int y){
		Point temp=new Point(x,y);
		focusOnPoint(temp,true);
	}
	
	//计算图片放置的位置
	private void CalculateImage(){
		int crossX,crossY;//四张图片的交叉点
		int sx1,sx2,sy1,sy2;//图片的两个角落坐标
		int dx1,dx2,dy1,dy2;//窗口显示的两个角落的坐标
		
		
		int base=wholeLength/1000;//图片展示的基数
		
		int firNum,secNum,trdNum,fthNum;//窗口最多同时有四张图片，这代表它们的编号
		
		//获取四个编号
		firNum=((int)(upY/1000))*base+leftX/1000;
		secNum=firNum+base;
		trdNum=firNum+1;
		fthNum=secNum+1;
		
		crossX=((leftX/1000)+1)*1000;
		crossY=((upY/1000)+1)*1000;
		
		drawable=new boolean[4];
		asis=new int[32];
		mapIndex=new int[4];
		fileName=new String[4];
		
		mapIndex[0]=firNum; mapIndex[1]=secNum;
		mapIndex[2]=trdNum; mapIndex[3]=fthNum;
		map.setMapIndex(mapIndex);
		//draw the first image
		sx1=leftX%1000;sx2=1000;
		sy1=upY%1000;sy2=1000;
		dx1=0;dy1=0;
		dx2=crossX-leftX; dy2=crossY-upY;
		asis[0]=dx1;asis[1]=dy1;asis[2]=dx2;asis[3]=dy2;
		asis[4]=sx1;asis[5]=sy1;asis[6]=sx2;asis[7]=sy2;
		fileName[0]=index
		+"graph"+firNum+".png";
		drawable[0]=true;
		
		//draw the third image
		if(crossX-leftX<1000){
			dx1=crossX-leftX;dy1=0;
			dx2=1000;dy2=crossY-upY;
			sx1=0;sy1=upY%1000;
			sx2=leftX+1000-crossX;sy2=1000;
			asis[16]=dx1;asis[17]=dy1;asis[18]=dx2;asis[19]=dy2;
			asis[20]=sx1;asis[21]=sy1;asis[22]=sx2;asis[23]=sy2;
			fileName[2]=index+"graph"+trdNum+".png";
			drawable[2]=true;
		}
		else drawable[2]=false;
		
		//draw the second image
		if(crossY-upY<1000){
			sx1=leftX%1000;sy1=0;
			sx2=1000;sy2=upY-crossY+1000;
			dx1=0;dy1=crossY-upY;
			dx2=crossX-leftX;dy2=1000;
			asis[8]=dx1;asis[9]=dy1;asis[10]=dx2;asis[11]=dy2;
			asis[12]=sx1;asis[13]=sy1;asis[14]=sx2;asis[15]=sy2;
			fileName[1]=index
			+"graph"+secNum+".png";
			drawable[1]=true;
		}
		else drawable[1]=false;
		
		//draw the fourth image
		if(crossX-leftX<1000&&crossY-upY<1000){
			dx1=crossX-leftX;
			dy1=crossY-upY;
			dx2=1000;dy2=1000;
			sx1=0;sy1=0;
			sx2=leftX-crossX+1000;
			sy2=upY-crossY+1000;
			asis[24]=dx1;asis[25]=dy1;asis[26]=dx2;asis[27]=dy2;
			asis[28]=sx1;asis[29]=sy1;asis[30]=sx2;asis[31]=sy2;
			fileName[3]=index
			+"graph"+fthNum+".png";
			drawable[3]=true;
		}
		else drawable[3]=false;
		
	}
}

