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


//�������������JFrame����
public class CoordinateSearchFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3336235314019404890L;
	CInstead c1=new CInstead(); 
	private JPanel xPanel,yPanel,operPanel,p;//����x,y���������panel
	private JLabel xLabel,yLabel;//x,y�����label
	private JTextField xTextField,yTextField;//x,y�����text field
	private JButton confirmButton,cancelButton;//ȷ����ȡ����ť
	private FindHelpList temList;//�����������ҽ������
	//���캯��
	CoordinateSearchFrame(){
		  setContentPane(c1);//���ñ���ͼƬ
		  Container cp=getContentPane();
		xPanel=new JPanel();
		yPanel=new JPanel();
		operPanel=new JPanel();
		p=new JPanel();
		xPanel.setOpaque(false);
		yPanel.setOpaque(false);
		operPanel.setOpaque(false);
		p.setOpaque(false);
		xLabel=new JLabel("������x���꣨������");
		yLabel=new JLabel("������y���꣨������");
		
		xTextField=new JTextField(5);
		yTextField=new JTextField(5);
		
		confirmButton=new JButton("ȷ��");
		cancelButton=new JButton("ȡ��");
		confirmButton.setContentAreaFilled(false);  //���ò����
		confirmButton.setFocusPainted(false);  //�����޽���
		confirmButton.setRolloverEnabled(true);  //�������ѡ��ʱ��ʾ�߿�
		cancelButton.setContentAreaFilled(false);  
		cancelButton.setFocusPainted(false);  
		cancelButton.setRolloverEnabled(true);  
		xPanel.add(xLabel,BorderLayout.WEST);
		xPanel.add(xTextField,BorderLayout.EAST);
		yPanel.add(yLabel,BorderLayout.WEST);
		yPanel.add(yTextField,BorderLayout.EAST);
		operPanel.add(confirmButton,BorderLayout.EAST);
		operPanel.add(cancelButton,BorderLayout.WEST);
		xTextField.setToolTipText("��Χ��0-10000");//��������x��Χ
		yTextField.setToolTipText("��Χ��0-10000");//��������y��Χ
		cp.setLayout(null);
		setcolor();
		p.setLocation(40,50);
		p.setSize(300,200);
		p.add(xPanel,BorderLayout.NORTH);
		p.add(yPanel,BorderLayout.CENTER);
		p.add(operPanel,BorderLayout.SOUTH);
		cp.add(p);
		//ȷ����ť��Ӽ�����
		confirmButton.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				String xCoordinate,yCoordinate;
				xCoordinate = xTextField.getText();
				yCoordinate = yTextField.getText();
				int x,y;
				
				/*
				 * �����������ĺϷ���
				 */
				if(xCoordinate==null||xCoordinate.length()==0){
					JOptionPane.showMessageDialog(CoordinateSearchFrame.this, 
							"��������","x����Ϊ�գ�������x����",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if(!isInteger(xCoordinate)){
					JOptionPane.showMessageDialog(CoordinateSearchFrame.this,
							"x���겻��һ������","��������",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				
				if(yCoordinate==null||yCoordinate.length()==0){
					JOptionPane.showMessageDialog(CoordinateSearchFrame.this, 
							"y����Ϊ�գ�������y����","��������",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if(!isInteger(yCoordinate)){
					JOptionPane.showMessageDialog(CoordinateSearchFrame.this,
							"y���겻��һ������", "��������", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				x=Integer.parseInt(xCoordinate);
				y=Integer.parseInt(yCoordinate);
				
				if(x<0||x>10000){
					JOptionPane.showMessageDialog(CoordinateSearchFrame.this,
							"x���귶Χ��0-10000������������", "x���곬���Ϸ���Χ", JOptionPane.WARNING_MESSAGE);
					return ;
				}
				
				if(y<0||y>10000){
					JOptionPane.showMessageDialog(CoordinateSearchFrame.this,
							"y���귶Χ��0-10000������������", "y���곬���Ϸ���Χ", JOptionPane.WARNING_MESSAGE);
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
		//ȡ����ť��Ӽ�����
		cancelButton.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				CoordinateSearchFrame.this.dispose();
			}
		});
	}
	//������ɫ�ĺ���
	public void setcolor(){
		 confirmButton.setForeground(Color.white);
		 cancelButton.setForeground(Color.white);
		 xLabel.setForeground(Color.white);
		 yLabel.setForeground(Color.white);
	 }
	//��ʼ��
	public void frameInitial(){
		Image icon;
		try {
			icon = ImageIO.read(this.getClass().getResource("/res/icon1.jpg"));//����ͼ��
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
	
	//��������Ƿ�Ϊ����
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
	//�������ر���ͼƬ��JPanel��
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

