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
 * ���������б���࣬��ѡ�����Ҫ���ҵĵ���ʱ������ʾ���丽��100����İ��������б�
 */
public class FindHelpList extends JFrame{
	CInstead c1=new CInstead();//������ʾͼƬpanel�Ķ��� 
	private Vector<Point> temtest;//�������Ҫ���ҵص㸽����100�����Vector
	private JList<String> list;//�б��
	private Vector<String> word;//�������Ҫ���ҵص㸽����100����ĵ�����Vector
	//���캯��
	public FindHelpList(final Vector<Point> test){
		 setContentPane(c1);
		 setLayout(null);
		int pointsize;//������ĸ���
		if(test.size()<=100) pointsize=100;
		else pointsize=101;
		temtest=new Vector<Point>();
		for(int i=0;i<pointsize;i++)
			temtest.add(test.elementAt(i));//�Ѹ�����100�������temtests������
		word=new Vector<String>();
		word.add("�����ʾ�ڽ���100����ͱ�");
		for(int i=1;i<pointsize;i++)
			word.add(test.elementAt(i).getName()+"    NO."+i);//�Ѹ���100����ĵ�������word������
		list =new JList<String>();
		list.setSelectionBackground(null);
		list.setForeground(Color.blue);
		JScrollPane scrollPane = new JScrollPane(list);//��������������������ʾ����Ϊlist
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		//list.setForeground(Color.WHITE);
		list.setBackground(new Color(0,0,0,0));
		scrollPane.setLocation(0,0);
		scrollPane.setSize(285,470);
		add(scrollPane);
		list.setListData(word);//��word����list��
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//Ϊlist��Ӽ�����
		list.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				 if (!e.getValueIsAdjusting()){
					// TODO �Զ����ɵķ������
						int selectedIndex=list.getSelectedIndex();//��ȡѡ��ص���±�
						if(selectedIndex<0) return ;
						Point selectedPoint=test.elementAt(selectedIndex);//�����±���ҵص������
						try {
							new historyfile("searchname",selectedPoint.getNum(),0);//�Ѹõص���뵽��ʷ��¼
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//int x=selectedPoint.getX();
						//int y=selectedPoint.getY();
						MainFrame.getMainFrame().setNearPointVec(temtest);//���ø���һ�ٸ�����temtest�����һ�ٸ���
						MainFrame.getMainFrame().focusOnPoint(selectedPoint,false);//��λ��ѡ��ĵ�
						MainFrame.getMainFrame().setShowNear();//��ʾ�����ĵ�
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
	//���ô�����ʾģʽ
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
