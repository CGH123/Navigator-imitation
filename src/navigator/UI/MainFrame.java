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


//��������
public class MainFrame extends JFrame{
	public static dialog dog;
	public static ChangePasswordFrame CPF;
	public static login  log;
	public static Register reg;
	public static ReadTxtFile read;
	public static ImageBufferFrame IBF=null;
	
	private TrafficFlowSimulation traffic;
	private static MainFrame mainframe;
	
	private int zoomTimes;//���ű���
	
	private int leftX,upY;//�������Ͻǵ�����
	private int initX,initY;//��¼�϶����ʱ����ԭʼ����
	private int centerX,centerY;//��ǰ��������ĵ���normal�����е�����
	private int wholeLength;
	
	private boolean isLock;//��¼��ʻģʽ�����Ƿ�����
	
	public AutoGraphThread autoThread;
	public static JMenu UserName;
	public JMenuItem UserLogo;
	private mapPanel map;
	private String index;//ͼƬ���Ŀ¼
	private final String normalIndex=new String("d://map//normal//");
	private final String bigIndex=new String("d://map//big//");
	private final String smallIndex=new String("d://map//small//");
	
	boolean drawable[];
	int asis[];
	String fileName[];
	int mapIndex[];
	
	private Splash splash;
	////////////////////////���캯��///////////////////////////////////////
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
		
		autoThread=new AutoGraphThread();//��һ���߳�������һ��AutoGraph����
		autoThread.start();
		
		setZoomTimes(100);//��ʼ�����ű���
		wholeLength=10000;
		upY=0;leftX=0;
		centerX=500;centerY=500;
		
		JMenuBar menubar;
		JPanel Panel=new JPanel();
		JMenu function,navigate,zoom,search,flow;
		JMenuItem includeFlow,notIncludeFlow,history,user,ChangeUser,ChangePassword,Exit,pathOnly,small,normal,big,searchName,searchAsis;
		
		//����ʹ��
	
		
		//��ʼ�����
		menubar=new JMenuBar();
		JMenuBar menubarEast=new JMenuBar();
		function=new JMenu("����");navigate=new JMenu("����");
		flow=new JMenu("������");
		includeFlow=new JMenuItem("����������");
		notIncludeFlow=new JMenuItem("�رճ�����");
		user=new JMenuItem("�û���¼"); 
		UserName=new JMenu("");
		UserLogo=new JMenuItem("");
		ChangeUser=new JMenuItem("�л��û�");
		history=new JMenuItem("��ʷ��¼");
		ChangePassword=new JMenuItem("��������");
		Exit=new JMenuItem("�˳���ǰ�˺�");
		ImageIcon ig=new ImageIcon("D:\\map\\Aisi.png");
		ig.setImage(ig.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
		UserLogo.setIcon(ig);
		UserLogo.setVisible(false);
		UserName.setVisible(false);
		//�û�����
		zoom=new JMenu("����");
		search=new JMenu("����");
		pathOnly=new JMenuItem("���·");
		searchName=new JMenuItem("���ҵ���");
		searchAsis=new JMenuItem("��������");
		small=new JMenuItem("50%");normal=new JMenuItem("100%");big=new JMenuItem("200%");
		add(Panel,BorderLayout.NORTH);
		Panel.setLayout(new BorderLayout());
		Panel.add(menubar,BorderLayout.WEST);
		Panel.add(menubarEast,BorderLayout.EAST);
		index=new String("d://map//normal//");
		map=new mapPanel();
		//������
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
				//�������ر�
				
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
				//�������ر�
				
					 TrafficFlowSimulation.isWork=false;
					 map.cancelTraffic();
			}
			
		  });

		includeFlow.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//��������ʾ����
				traffic=new TrafficFlowSimulation(MainFrame.getMainFrame().autoThread.auto.getPntVec());
				TrafficFlowSimulation.isWork=true;
				map.setTraffic();
				new Thread(traffic).start();
			}
		  });
		
		//�л��û�������
		ChangeUser.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				log=new login();
				reg=new Register();
				read=new ReadTxtFile();
			}
		  });
		//�������������
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
		//�ϴ�ͷ�������
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
		//�˵�����Ӽ�����
		normal.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				setZoomTimes(100);
				wholeLength=10000;
				index=normalIndex;
				
				//��ʾͼƬ
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
				
				//��ʾͼƬ
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
		
		user.addActionListener(new ActionListener(){    //����û�����
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
				
				//��ʾͼƬ
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
		
		//���ڵ��������ͼƬ��֮�����Ӧ
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
		
		//ͼƬ����Ӽ������������϶�ҳ��ʱ�ķ�Ӧ
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
				//System.out.println("������: "+leftX+" "+upY);
				int xDiff=(initX-x)/17;//x�����ƶ�ֵ
				int yDiff=(initY-y)/17;//y�����ƶ�ֵ
				int curX=leftX+xDiff;//����ͼλ�����Ͻ�x����
				int curY=upY+yDiff;//����ͼλ�����Ͻǵ�y����
				
				if(curX<0) curX=0;
				else if(curX>wholeLength-1000) curX=wholeLength-1000;
				else ;
				
				if(curY<0) curY=0;
				else if(curY>wholeLength-678) curY=wholeLength-678;
				else ;
				
				leftX=curX;
				upY=curY;
				
				//����centerX��centerY��ֵ
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
	////////////////////////////////////////���캯������/////////////////////////////////////////
	
	//������
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
			
		frame.splash.loadingProgram();//���س���Ļ���
		//����������ſ�ʼ��ʾ����
		MainFrame.getMainFrame().setVisible(true);
		
		frame.setImage();
	}
	
	//�����ػ�
		public void getPaint(){
			map.getPaint();
		}
	
	//�����ܵ������
	public void setIsLock(){
		isLock=true;
	}
	
	//ȡ���ܵ������
	public void cancelIsLock(){
		isLock=false;
	}
	
	//ģ�⳵����̬�ƶ�
	public void setImitateMoving(Point temp){
		if(!isLock) map.setImitateMoving(temp);
		else {
			map.setIsMoving();
			focusOnPoint(temp,true);
		}
	}
	
	//ȡ��ģ�⳵����̬�ƶ�
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
	
	
	//��ȡ�Ŵ��������÷Ŵ���
	public int getZoomTimes() {
		return zoomTimes;
	}
	public void setZoomTimes(int zoomTimes) {
		this.zoomTimes = zoomTimes;
	}
	
	//������ʾ�ڽ�
	public void setShowNear(){
		map.setNear();
	}
	
	//ȡ����ʾ�ڽ�
	public void cancelShowNear(){
		map.cancelNear();
	}
	
	//������ʾ�ڽ���100��
	public void setShowFocusNearPoint(){
		map.setFocusNearPoint();
	}
	
	//ȡ����ʾ�ڽ���100����
	public void cancelShowFocusNearPoint(){
		map.cancelFocusNearPoint();
	}
	
	//ȡ����ʾѡ�е�
	public void cancelShowFocusPoint(){
		map.cancelFocusPoint();
	}
	
	//��mapPanel�������·�ĵ�
	public void showShortestPath(Vector<Integer>pathPoints,Point startPnt){
		focusOnPoint(startPnt,true);
		map.setPathPoints(pathPoints);
	}
		
	//ȡ����ʾ���·
	public void cancelShowShortestPath(){
		map.cancelShortestPath();
	}
	
	public void setNearPointVec(Vector<Point> temVec){
		map.setTemNearPoint(temVec);
	}
	
	//ʹmapPanel�۽���һ�����ϣ�������������֮�󽫻���ת�Ƶ�һ������
	//flagΪtrue,��ͻ������ѡ��㣬ΪFalseͻ�������ٽ���
	public void focusOnPoint(Point toFocus,boolean mark){
		int zTime=getZoomTimes();//��ȡ��ǰ�Ŵ���
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
	
	//����ͼƬ���õ�λ��
	private void CalculateImage(){
		int crossX,crossY;//����ͼƬ�Ľ����
		int sx1,sx2,sy1,sy2;//ͼƬ��������������
		int dx1,dx2,dy1,dy2;//������ʾ���������������
		
		
		int base=wholeLength/1000;//ͼƬչʾ�Ļ���
		
		int firNum,secNum,trdNum,fthNum;//�������ͬʱ������ͼƬ����������ǵı��
		
		//��ȡ�ĸ����
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

