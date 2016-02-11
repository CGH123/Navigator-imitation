package navigator.UI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import navigator.background.historyfile;
import navigator.dataStruct.Point;
import navigator.dataStruct.TrieTree;

//���ڲ��ҵ�����JFrame����
public class ComboBoxFrame extends JFrame{
	Container c;//�½��ײ�����
	CInstead c1=new CInstead();//������ʾͼƬpanel�Ķ��� 
	private Vector<String>words;
	private TrieTree tree;//�����ֵ�������
	private JList<String> list;//�����б��
	private JLabel inputLabel;//��ʾ��label����
	private JTextField inputTextField;//��������
	private Vector<Point>res;//���ڽ��ո�����ʾ��ֵ������صص�ļ���
	private FindHelpList temList;//������������frame����ѡ��һ���ص�ʱ�������һ����ʾ100���������frame
	private boolean mark;
    private JPanel InputPanel;//��������label���б༭���JPanel
    //���캯��
	public ComboBoxFrame(Vector<Point> words,final boolean mark)
	{
	   setContentPane(c1);//Ϊ�ײ��������panel����ʾ����ͼƬ
		this.mark=mark;
		this.words=new Vector<String>();//��ʼ��words������������ŵ���
		tree=new TrieTree();//��ʼ���ֵ���
		for(int i=0;i<words.size();i++) tree.insertPoint(words.elementAt(i));//�����е�����ֵ���
		 InputPanel=new JPanel();
		inputLabel=new JLabel("���������");
		inputLabel.setForeground(Color.red);
		inputTextField=new JTextField(10);
		list =new JList<String>();//��ʼ��list
		JScrollPane scrollPane = new JScrollPane(list);//��������������������ҳ�ʼ������ʾ������list
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		list.setForeground(Color.blue);
		list.setBackground(new Color(0,0,0,0));
		setLayout(null);//�����޲���
		InputPanel.setLocation(0,10);
		InputPanel.setSize(350,30);
		InputPanel.add(inputLabel);
		InputPanel.add(inputTextField);
		add(InputPanel);
		setLayout(null);
		scrollPane.setLocation(0,40);
		scrollPane.setSize(335,480);
		add(scrollPane);
		InputPanel.setOpaque(false); 
		//�Ա༭����Ӽ�����
		inputTextField.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				update();
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update();
				
			}
			
			private void update(){
				SwingUtilities.invokeLater(new Runnable(){
					@Override
					//��дrun����
					public void run(){
						ComboBoxFrame.this.words.clear();//���
						String input=inputTextField.getText();//��ȡ�༭������
						inputTextField.setEditable(false);//���ò��ɱ༭
						list.removeAll();//���б����������
						if(input!=null)
						{
							res=tree.result(input);//�������룬�����ֵ���������������Ϊǰ׺�ĵ�����Vector
							int size=res.size();//��ȡres�ĳ���
							for(int i=0;i<size;i++){
								ComboBoxFrame.this.words.add(res.elementAt(i).getName());//�������ӵ�words����
							}
							list.setListData(ComboBoxFrame.this.words);//list����Ϊwords	
						}
						inputTextField.setEditable(true);//���ÿɱ༭
					}
				});
			}
		});
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//Ϊ�б�����Ӽ�����
		list.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				 if (!e.getValueIsAdjusting()) {
					// TODO �Զ����ɵķ������
						int selectedIndex=list.getSelectedIndex();//��ȡ��ѡ��ĵص���±�
						if(selectedIndex<0) return ;
						Point selectedPoint=res.elementAt(selectedIndex);//ͨ���±������ѡ��ĵĵص�
						try {
						new historyfile("searchname",selectedPoint.getNum(),0);//��ѡ��ĵص���ӵ���ʷ��¼
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						MainFrame.getMainFrame().focusOnPoint(selectedPoint,true);//����궨λ��ѡ��ĵ�
						if(mark){
							if(temList!=null) temList.dispose();//������������б�Ϊ�գ������
							//����ѡ��ĵ�ó�����һ�ٸ���ļ��ϣ�������ʾ���´��ڵ����п�
							temList=new FindHelpList(MainFrame.getMainFrame().autoThread.auto.generateFindVec(selectedPoint));
						}					
				 }			
			}
			
		});
		
		
		
	}
	//���ڳ�ʼ��
	public void frameInitial(){
		Image icon;
		try {
			icon = ImageIO.read(this.getClass().getResource("/res/icon1.jpg"));//����ͼ��
			this.setIconImage(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setSize(350,500);
		this.setLocationRelativeTo(null);
		this.setTitle("Name Searcher");
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
                 if(temList!=null) temList.dispose();
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
	//���ñ���ͼƬ
	class CInstead extends JPanel  
	{   
	ImageIcon icon;  
	Image img;  
	public CInstead()  
	{   
	icon=new ImageIcon(MainFrame.getMainFrame().getClass().getResource("/res/ComboBoxFrame.png" ));  
	img=icon.getImage();  
	}   
	public void paintComponent(Graphics g)  
	{   
	super.paintComponent(g);  
	g.drawImage(img,0,0,null );  
	}   
	}
}



