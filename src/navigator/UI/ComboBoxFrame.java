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

//用于查找地名的JFrame子类
public class ComboBoxFrame extends JFrame{
	Container c;//新建底层容器
	CInstead c1=new CInstead();//建立显示图片panel的对象 
	private Vector<String>words;
	private TrieTree tree;//建立字典树对象
	private JList<String> list;//创建列表框
	private JLabel inputLabel;//提示语label对象
	private JTextField inputTextField;//输入框对象
	private Vector<Point>res;//用于接收根据提示语，字典树返回地点的集合
	private FindHelpList temList;//创建帮助查找frame对象，选定一个地点时，会出现一个显示100个附近点的frame
	private boolean mark;
    private JPanel InputPanel;//用来承载label还有编辑框的JPanel
    //构造函数
	public ComboBoxFrame(Vector<Point> words,final boolean mark)
	{
	   setContentPane(c1);//为底层容器添加panel，显示背景图片
		this.mark=mark;
		this.words=new Vector<String>();//初始化words容器，用来存放地名
		tree=new TrieTree();//初始化字典树
		for(int i=0;i<words.size();i++) tree.insertPoint(words.elementAt(i));//把所有点插入字典树
		 InputPanel=new JPanel();
		inputLabel=new JLabel("请输入地名");
		inputLabel.setForeground(Color.red);
		inputTextField=new JTextField(10);
		list =new JList<String>();//初始化list
		JScrollPane scrollPane = new JScrollPane(list);//创建滚动面板容器，并且初始化其显示内容是list
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		list.setForeground(Color.blue);
		list.setBackground(new Color(0,0,0,0));
		setLayout(null);//设置无布局
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
		//对编辑框添加监视器
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
					//重写run函数
					public void run(){
						ComboBoxFrame.this.words.clear();//清空
						String input=inputTextField.getText();//获取编辑框内容
						inputTextField.setEditable(false);//设置不可编辑
						list.removeAll();//把列表框的内容清空
						if(input!=null)
						{
							res=tree.result(input);//根据输入，查找字典树，返回以输入为前缀的地名的Vector
							int size=res.size();//获取res的长度
							for(int i=0;i<size;i++){
								ComboBoxFrame.this.words.add(res.elementAt(i).getName());//将地名加到words容器
							}
							list.setListData(ComboBoxFrame.this.words);//list内容为words	
						}
						inputTextField.setEditable(true);//设置可编辑
					}
				});
			}
		});
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//为列表框框添加监视器
		list.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				 if (!e.getValueIsAdjusting()) {
					// TODO 自动生成的方法存根
						int selectedIndex=list.getSelectedIndex();//获取所选择的地点的下标
						if(selectedIndex<0) return ;
						Point selectedPoint=res.elementAt(selectedIndex);//通过下标查找所选择的的地点
						try {
						new historyfile("searchname",selectedPoint.getNum(),0);//将选择的地点添加到历史记录
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						MainFrame.getMainFrame().focusOnPoint(selectedPoint,true);//将光标定位到选择的点
						if(mark){
							if(temList!=null) temList.dispose();//如果帮助查找列表不为空，则清空
							//根据选择的点得出附近一百个点的集合，并且显示在新窗口的下列框
							temList=new FindHelpList(MainFrame.getMainFrame().autoThread.auto.generateFindVec(selectedPoint));
						}					
				 }			
			}
			
		});
		
		
		
	}
	//窗口初始化
	public void frameInitial(){
		Image icon;
		try {
			icon = ImageIO.read(this.getClass().getResource("/res/icon1.jpg"));//设置图标
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
	//设置背景图片
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



