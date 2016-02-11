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

import navigator.background.ShortestPath;
import navigator.dataStruct.Point;
/*
 * 历史记录frame类
 */
public class historylistframe extends JFrame{
	CInstead c1=new CInstead();//建立显示图片panel的对象 
	private Vector<Integer>answerPath; //最短路上点的集合
	private FindHelpList temList;//帮助查找的下列框对象
	private JList<String> list;//下列框
	private Vector<String> word;//下列款数据Vector
	public historylistframe(final Vector<String> test){
		 setContentPane(c1);
		 setLayout(null);
		int pointsize;//test的大小
		 pointsize=test.size();
		word=new Vector<String>();
		word.add("历史记录：");
		for(int i=1;i<pointsize+1;i+=3){
			if(test.elementAt(i-1).equals("searchname"))//如果记录是查找地点时
			word.add("查找地点： "+MainFrame.getMainFrame().autoThread.auto.getPntVec().elementAt(Integer.parseInt(test.elementAt(i))).getName());
		    if(test.elementAt(i-1).equals("pathonly"))//如果记录是查找最短路时
		    	word.add("查找路径： "+MainFrame.getMainFrame().autoThread.auto.getPntVec().elementAt(Integer.parseInt(test.elementAt(i))).getName()+"->"+MainFrame.getMainFrame().autoThread.auto.getPntVec().elementAt(Integer.parseInt(test.elementAt(i+1))).getName());
		    if(test.elementAt(i-1).equals("searchasis"))//如果记录是查找坐标时
		    	word.add("查找坐标： 【"+Integer.parseInt(test.elementAt(i))+","+Integer.parseInt(test.elementAt(i+1))+"】");
		}
		list =new JList<String>();
		list.setSelectionBackground(null);
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		list.setForeground(Color.blue);
		list.setBackground(new Color(0,0,0,0));
		scrollPane.setLocation(0,0);
		scrollPane.setSize(285,430);
		add(scrollPane);
		list.setListData(word);//把内容加到下列框
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//为list添加监视器
		list.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				 if (!e.getValueIsAdjusting()){
					// TODO 自动生成的方法存根
						int selectedIndex=list.getSelectedIndex();//获取选择的下标
						if(selectedIndex<0) return ;
						//判断是哪种历史记录
						if(test.elementAt((selectedIndex-1)*3).equals("searchname")){//判断出是查找地点时
							cancel();
						Point selectedPoint=MainFrame.getMainFrame().autoThread.auto.getPntVec().elementAt(Integer.parseInt(test.elementAt((selectedIndex-1)*3+1)));//获取地点
						MainFrame.getMainFrame().focusOnPoint(selectedPoint,true);//定位选择的地点
						if(temList!=null) temList.dispose();//显示地点选择帮助框，如不为空先清空，再添加选择地点附近的一百个点
						temList=new FindHelpList(MainFrame.getMainFrame().autoThread.auto.generateFindVec(selectedPoint));
						}
						if(test.elementAt((selectedIndex-1)*3).equals("pathonly")){//判断出是查找最短路时
							cancel();
							Point startPoint=MainFrame.getMainFrame().autoThread.auto.getPntVec().elementAt(Integer.parseInt(test.elementAt((selectedIndex-1)*3+1)));
							Point endPoint=MainFrame.getMainFrame().autoThread.auto.getPntVec().elementAt(Integer.parseInt(test.elementAt((selectedIndex-1)*3+2)));
					
							ShortestPath ansPath=new ShortestPath(startPoint,endPoint,MainFrame.getMainFrame().autoThread.auto.getPntVec());
							if(ansPath.Dijkstra())//如果能够找到最短路
								answerPath=ansPath.getPathVec();//获取最短路的点的集合
							else answerPath=null;
							
		
							MainFrame.getMainFrame().showShortestPath(answerPath,startPoint);//给MainFrame设置最短路的点
							
						}
						if(test.elementAt((selectedIndex-1)*3).equals("searchasis")){//判断出是查找坐标时
							cancel();
							int x=Integer.parseInt(test.elementAt((selectedIndex-1)*3+1));
							int y=Integer.parseInt(test.elementAt((selectedIndex-1)*3+2));
							MainFrame.getMainFrame().focusOnCoordinate(x, y);
							Point temp=new Point(x,y);
					      if(temList!=null) temList.dispose();
					      temList=new FindHelpList(MainFrame.getMainFrame().autoThread.auto.generateFindVec(temp));	
					      }
						
				 }			
			}
			
		});
		
		this.setSize(300,450);
		this.setLocationRelativeTo(null);
		this.setTitle("NearPoint Search");
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setVisible(true);
	}
	void cancel(){
		MainFrame.getMainFrame().cancelShowNear();//取消给附近的点和边上色
		MainFrame.getMainFrame().cancelShowFocusPoint();//取消给定位的点上色
		MainFrame.getMainFrame().cancelShowFocusNearPoint();//取消给定位到附近的点上色
		MainFrame.getMainFrame().cancelShowShortestPath();//取消显示最短路
	}
	@Override
	protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            switch(getDefaultCloseOperation()) {
              case HIDE_ON_CLOSE:
                 setVisible(false);
                 if(temList!=null)  temList.dispose();
             cancel();
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
	icon=new ImageIcon(MainFrame.getMainFrame().getClass().getResource("/res/login.jpg" ));  
	img=icon.getImage();  
	}   
	public void paintComponent(Graphics g)  
	{   
	super.paintComponent(g);  
	g.drawImage(img,0,0,null );  
	}   
	}
}
