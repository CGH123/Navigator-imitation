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



//�����������·�����յ�������JFrame����
public class ShortestPathInputFrame extends JFrame{
	CInstead c1=new CInstead(); 
	private JTextField startPntInput,endPntInput;//������㣬�յ�������
	private JLabel startPntLabel,endPntLabel;//��ʾ��������յ�ı�ǩ
	private JPanel upPanel,downPanel,operPanel,p;//����JPanel
	private TrieTree tree;//�ֵ���
	private JButton confirm,cancel,help,moving,drivingConfirm,drivingCancel;//���õ���JButton
	private Vector<Point>pntVec;//��ļ�������
	private Vector<Integer>answerPath;//���·�Ĵ�����
	private ComboBoxFrame searchNameFrame;//���ڲ��ҵ����������
	
	private boolean isMoving;//�ж����·�Ƿ�Ϸ�
	private boolean isStop; //ȷ��������̬���Ƿ��ƶ�������ֹ
	
	//���캯����pntVec�ǵ�ͼ��������ļ������飬flowConsidered��ʾ�Ƿ�¼������
	ShortestPathInputFrame(Vector<Point>pntVec,final boolean flowConsidered){
		 setContentPane(c1);
		 Container cp=getContentPane();
		isMoving=false;
		isStop=false;
		this.pntVec=pntVec;
		startPntInput=new JTextField(7);
		endPntInput=new JTextField(7);
		startPntLabel=new JLabel("���������");
		endPntLabel=new JLabel("�������յ�");
		upPanel=new JPanel();
		downPanel=new JPanel();
		operPanel=new JPanel();
		p=new JPanel();
		p.setOpaque(false);
		upPanel.setOpaque(false);
		downPanel.setOpaque(false);
		operPanel.setOpaque(false);
		tree=new TrieTree();
		confirm=new JButton("ȷ��");
		cancel=new JButton("ȡ��");
		help=new JButton("�ص��ȫ");
		moving=new JButton("ģ�⳵���ƶ�");
		drivingConfirm=new JButton("������Ļ");
		drivingCancel=new JButton("������Ļ");
		 setcolor();
		int size=pntVec.size();
		for(int i=0;i<size;i++) tree.insertPoint(pntVec.elementAt(i));
		
		
		//�����Ӧ�ؼ�
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
		
		
		
//////////////////////////////////////����Ӧ�ؼ������Ӧ������//////////////////////////////////////
		
		//��ȷ������ť�ļ�����
		confirm.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String startPointName=startPntInput.getText();
				String endPointName=endPntInput.getText();
				
				//�ж��ַ��Ƿ�Ϸ�
				if(startPointName==null||startPointName.length()==0){
					JOptionPane.showMessageDialog(ShortestPathInputFrame.this, 
							"��������","�����Ϊ�գ���������㣡",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//����յ�������Ϊ��
				if(endPointName==null||endPointName.length()==0){
					JOptionPane.showMessageDialog(ShortestPathInputFrame.this, 
							"��������","�յ���Ϊ�գ��������յ㣡",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//����������������
				if(!tree.ifHas(startPointName)){
					JOptionPane.showMessageDialog(ShortestPathInputFrame.this, 
							"��������","�����������������ͬ�ĵ�����������������㣡",JOptionPane.WARNING_MESSAGE);
					startPntInput.setText("");
					return;
				}
				
				//����յ�����������
				if(!tree.ifHas(endPointName)){
					JOptionPane.showMessageDialog(ShortestPathInputFrame.this, 
							"��������","���������յ�������ͬ�ĵ����������������յ㣡",JOptionPane.WARNING_MESSAGE);
					endPntInput.setText("");
					return;
				}
				
				//����Ϸ����������·
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
					if(ansPath.TrafficDijkstra())//����ܹ��ҵ����·
						answerPath=ansPath.getPathVec();//��ȡ���·�ĵ�
					else 
						answerPath=null;
				}
				else
				{
					if(ansPath.Dijkstra())//����ܹ��ҵ����·
						answerPath=ansPath.getPathVec();//��ȡ���·�ĵ�
						
					else 
						answerPath=null;
				}
								
				MainFrame.getMainFrame().showShortestPath(answerPath,startPoint);//��MainFrame�������·�ĵ�
			}
			
		});
		
		
		//��ȡ������ť�ļ�����
		cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.getMainFrame().cancelShowShortestPath();
				ShortestPathInputFrame.this.dispose();
			}
			
		});
		
		
		//"ģ�⳵���ƶ�"��ť�ļ�����
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
							"�������","����ȷ���˶�·�̵������յ㣡",JOptionPane.WARNING_MESSAGE);
			}
		});
		
		
		
		//"������Ļ"��ť�ļ�����
		drivingConfirm.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(isMoving)  
					MainFrame.getMainFrame().setIsLock();
				else 
					JOptionPane.showMessageDialog(ShortestPathInputFrame.this, 
							"�������","����ģ�⳵���ƶ���",JOptionPane.WARNING_MESSAGE);
			}
			
		});
		
		//��Ӧ�������Ļ����ť���¼��ļ�����
		drivingCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(isMoving)
					MainFrame.getMainFrame().cancelIsLock();
				else 
					JOptionPane.showMessageDialog(ShortestPathInputFrame.this, 
							"�������","����ģ�⳵���ƶ���",JOptionPane.WARNING_MESSAGE);
			}
			
		});
		
		
		//��Ӧ����������ť���¼��ļ�����
		help.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				searchNameFrame=new ComboBoxFrame(MainFrame.getMainFrame()
						.autoThread.auto.getPntVec(),false);
				searchNameFrame.frameInitial();
			}
		});
		
/////////////////////////////////////////��Ӽ��������/////////////////////////////////////////
		
		
		
	}
	
	
	//����ĳ�ʼ������
	public void frameInitial(){
		Image icon;//����ͼ��
		try {
			icon = ImageIO.read(this.getClass().getResource("/res/icon1.jpg"));
			this.setIconImage(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setSize(550,380);
		this.setLocationRelativeTo(null);
		this.setTitle("�����յ�");
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
