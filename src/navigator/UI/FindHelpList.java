package navigator.UI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import navigator.dataStruct.Point;
/*
 * 帮助查找列表的类，当选择查找要查找的地名时，会显示出其附近100个点的帮助查找列表
 */
public class FindHelpList extends JFrame{
	CInstead c1=new CInstead();//建立显示图片panel的对象 
	private Vector<Point> temtest;//用来存放要查找地点附近的100个点的Vector
	private JList<String> list;//列表框
	private Vector<String> word;//用来存放要查找地点附近的100个点的地名的Vector
	//构造函数
	public FindHelpList(final Vector<Point> test){
		 setContentPane(c1);
		 setLayout(null);
		int pointsize;//附近点的个数
		if(test.size()<=100) pointsize=100;
		else pointsize=101;
		temtest=new Vector<Point>();
		for(int i=0;i<pointsize;i++)
			temtest.add(test.elementAt(i));//把附近的100个点加入temtests容器中
		word=new Vector<String>();
		word.add("点击显示邻近的100个点和边");
		for(int i=1;i<pointsize;i++)
			word.add(test.elementAt(i).getName()+"    NO."+i);//把附近100个点的地名加入word容器中
		list =new JList<String>();
		list.setSelectionBackground(null);
		list.setForeground(Color.blue);
		JScrollPane scrollPane = new JScrollPane(list);//创建滚动面板对象，设置显示内容为list
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		//list.setForeground(Color.WHITE);
		list.setBackground(new Color(0,0,0,0));
		scrollPane.setLocation(0,0);
		scrollPane.setSize(285,470);
		add(scrollPane);
		list.setListData(word);//将word加入list中
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//为list添加监视器
		list.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				 if (!e.getValueIsAdjusting()){
					// TODO 自动生成的方法存根
						int selectedIndex=list.getSelectedIndex();//获取选择地点的下标
						if(selectedIndex<0) return ;
						Point selectedPoint=test.elementAt(selectedIndex);//根据下表查找地点的坐标
						try {
							new historyfile("searchname",selectedPoint.getNum(),0);//把该地点加入到历史记录
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//int x=selectedPoint.getX();
						//int y=selectedPoint.getY();
						MainFrame.getMainFrame().setNearPointVec(temtest);//设置附近一百个点是temtest里面的一百个点
						MainFrame.getMainFrame().focusOnPoint(selectedPoint,false);//定位到选择的点
						MainFrame.getMainFrame().setShowNear();//显示附近的点
				 }				
			}
			
		});
		
		this.setSize(300,500);
		this.setLocationRelativeTo(null);
		this.setTitle("NearPoint Search");
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setVisible(true);
	}
	@Override
	//设置窗口显示模式
	protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            switch(getDefaultCloseOperation()) {
              case HIDE_ON_CLOSE:
                 setVisible(false);
                 MainFrame.getMainFrame().cancelShowNear();
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
	class CInstead extends JPanel  
	{   
	ImageIcon icon;  
	Image img;  
	public CInstead()  
	{   
	icon=new ImageIcon(MainFrame.getMainFrame().getClass().getResource("/res/FindHelpList.jpg" ));  
	img=icon.getImage();  
	}   
	public void paintComponent(Graphics g)  
	{   
	super.paintComponent(g);  
	g.drawImage(img,0,0,null );  
	}   
	}
}
