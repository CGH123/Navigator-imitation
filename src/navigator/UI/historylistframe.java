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
 * ��ʷ��¼frame��
 */
public class historylistframe extends JFrame{
	CInstead c1=new CInstead();//������ʾͼƬpanel�Ķ��� 
	private Vector<Integer>answerPath; //���·�ϵ�ļ���
	private FindHelpList temList;//�������ҵ����п����
	private JList<String> list;//���п�
	private Vector<String> word;//���п�����Vector
	public historylistframe(final Vector<String> test){
		 setContentPane(c1);
		 setLayout(null);
		int pointsize;//test�Ĵ�С
		 pointsize=test.size();
		word=new Vector<String>();
		word.add("��ʷ��¼��");
		for(int i=1;i<pointsize+1;i+=3){
			if(test.elementAt(i-1).equals("searchname"))//�����¼�ǲ��ҵص�ʱ
			word.add("���ҵص㣺 "+MainFrame.getMainFrame().autoThread.auto.getPntVec().elementAt(Integer.parseInt(test.elementAt(i))).getName());
		    if(test.elementAt(i-1).equals("pathonly"))//�����¼�ǲ������·ʱ
		    	word.add("����·���� "+MainFrame.getMainFrame().autoThread.auto.getPntVec().elementAt(Integer.parseInt(test.elementAt(i))).getName()+"->"+MainFrame.getMainFrame().autoThread.auto.getPntVec().elementAt(Integer.parseInt(test.elementAt(i+1))).getName());
		    if(test.elementAt(i-1).equals("searchasis"))//�����¼�ǲ�������ʱ
		    	word.add("�������꣺ ��"+Integer.parseInt(test.elementAt(i))+","+Integer.parseInt(test.elementAt(i+1))+"��");
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
		list.setListData(word);//�����ݼӵ����п�
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//Ϊlist��Ӽ�����
		list.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				 if (!e.getValueIsAdjusting()){
					// TODO �Զ����ɵķ������
						int selectedIndex=list.getSelectedIndex();//��ȡѡ����±�
						if(selectedIndex<0) return ;
						//�ж���������ʷ��¼
						if(test.elementAt((selectedIndex-1)*3).equals("searchname")){//�жϳ��ǲ��ҵص�ʱ
							cancel();
						Point selectedPoint=MainFrame.getMainFrame().autoThread.auto.getPntVec().elementAt(Integer.parseInt(test.elementAt((selectedIndex-1)*3+1)));//��ȡ�ص�
						MainFrame.getMainFrame().focusOnPoint(selectedPoint,true);//��λѡ��ĵص�
						if(temList!=null) temList.dispose();//��ʾ�ص�ѡ��������粻Ϊ������գ������ѡ��ص㸽����һ�ٸ���
						temList=new FindHelpList(MainFrame.getMainFrame().autoThread.auto.generateFindVec(selectedPoint));
						}
						if(test.elementAt((selectedIndex-1)*3).equals("pathonly")){//�жϳ��ǲ������·ʱ
							cancel();
							Point startPoint=MainFrame.getMainFrame().autoThread.auto.getPntVec().elementAt(Integer.parseInt(test.elementAt((selectedIndex-1)*3+1)));
							Point endPoint=MainFrame.getMainFrame().autoThread.auto.getPntVec().elementAt(Integer.parseInt(test.elementAt((selectedIndex-1)*3+2)));
					
							ShortestPath ansPath=new ShortestPath(startPoint,endPoint,MainFrame.getMainFrame().autoThread.auto.getPntVec());
							if(ansPath.Dijkstra())//����ܹ��ҵ����·
								answerPath=ansPath.getPathVec();//��ȡ���·�ĵ�ļ���
							else answerPath=null;
							
		
							MainFrame.getMainFrame().showShortestPath(answerPath,startPoint);//��MainFrame�������·�ĵ�
							
						}
						if(test.elementAt((selectedIndex-1)*3).equals("searchasis")){//�жϳ��ǲ�������ʱ
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
		MainFrame.getMainFrame().cancelShowNear();//ȡ���������ĵ�ͱ���ɫ
		MainFrame.getMainFrame().cancelShowFocusPoint();//ȡ������λ�ĵ���ɫ
		MainFrame.getMainFrame().cancelShowFocusNearPoint();//ȡ������λ�������ĵ���ɫ
		MainFrame.getMainFrame().cancelShowShortestPath();//ȡ����ʾ���·
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
