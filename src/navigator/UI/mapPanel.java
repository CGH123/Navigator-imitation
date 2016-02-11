package navigator.UI;

import navigator.dataStruct.*;
import navigator.algorithm.*;
import navigator.background.*;
import navigator.UI.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


//������ʾ��ͼ��JPanel����
public class mapPanel extends JPanel
{	
	private Vector<Point> temNearPoint;//�ڽ���100�����vector
	private boolean focusNearPoint;//�Ƿ�ѵ���ɫ
	private boolean showNear; //�Ƿ���ڽ���ͱ���ɫ
	private boolean drawable[];
	private boolean focusOnPoint;//�Ƿ�ͻ����ʾһ����	
	private boolean toImport;
	private boolean showShortestPath;//�Ƿ���ʾ���·
	private boolean isMoving; //��������ק��ͼ���������Ƿ����ģ���ƶ�
	private boolean Traffic;//�Ƿ������������Ķ�̬�仯
	private int asis[];
	private int zoomTime;//�Ŵ���
	private int focusX,focusY;//ͻ����ʾ�ĵ���ͼ�ϵ�����
	private int focusNearX,focusNearY;//ͻ����ʾ�ĸ����ڽ���
	private int leftX,upY;//��MainFrame��leftX��upY����һ��
	private Vector<PathNode>pathPoints;//���·�ϵĵ�ı��
	private String fileName[];
	private int mapIndex[];//����϶���ʾ�ĵ�ͼ����
	private Point startPoint,endPoint;//���·�������յ�
	
	private Point movingPoint;//��ʱ�������ƶ��ĵ�
	
	public mapPanel(){
		focusNearPoint=false;
		showNear=false;
		Traffic=false;
		isMoving=false;
		toImport=false;
		focusOnPoint=false;
		showShortestPath=false;
		drawable=new boolean[4];
		mapIndex=new int[4];
		asis=new int[32];
		fileName=new String[4];
		drawable[0]=true;
		drawable[1]=drawable[2]=drawable[3]=false;
		asis[0]=asis[1]=0;asis[2]=asis[3]=1000;
		asis[4]=asis[5]=0;asis[6]=asis[7]=1000;
		fileName[0]=new String("d://map//normal//graph0.png");
	}
	@Override
	protected void paintComponent( Graphics g){
		Cursor cu=new Cursor(Cursor.MOVE_CURSOR);
		setCursor(cu);//���ù��Ϊ�ƶ����
		
		super.paintComponent(g);
		if(toImport==false)return ;
		try {
			for(int i=0;i<4;i++)
			{
				boolean toDraw=drawable[i];
				if(toDraw){
					int bound=i*8;
					BufferedImage image=ImageIO.read(new File(fileName[i]));
					g.drawImage(image, asis[bound], asis[bound+1], asis[bound+2], asis[bound+3], 
							asis[bound+4], asis[bound+5], asis[bound+6], asis[bound+7], this);
					
					
					//��ʾ������ģʽ					
					if(showNear){
						double multi=1;
						if(zoomTime==100) multi=1;
						else if(zoomTime==200) multi=2;
						else if(zoomTime==300) multi=0.5;
						Vector<Point> temVec=new Vector<Point>(temNearPoint);
						Vector<Point> temtotal=MainFrame.getMainFrame().autoThread.auto.getPntVec();						
						for(int j=0;j<temVec.size();j++){
							g.setColor(Color.blue);
							g.fillOval(temVec.elementAt(j).getX()-4-leftX, temVec.elementAt(j).getY()-4-upY, 8, 8);
							HashSet<Integer> tm=temVec.elementAt(j).getNearPoints();
							Iterator it=tm.iterator();
							while(it.hasNext()){					
								Point fir=temVec.elementAt(j);
								Point sec=temtotal.elementAt((int)it.next());
								if(!temVec.contains(sec)) continue;
								int firX,firY,secX,secY;
								firX=(int) (fir.getX()*multi-leftX);
								firY=(int) (fir.getY()*multi-upY);
								secX=(int) (sec.getX()*multi-leftX);
								secY=(int) (sec.getY()*multi-upY);
								g.setColor(Color.green);
								g.drawLine(firX, firY, secX, secY);
							}
						}						
					}
					//��̬չʾ�ڽ���
					if(isMoving&&movingPoint!=null){
						g.setColor(Color.blue);
						if(100==zoomTime) g.fillOval(movingPoint.getX()-4-leftX, movingPoint.getY()-4-upY, 8, 8);
						else if(200==zoomTime) g.fillOval(movingPoint.getX()*2-4-leftX, movingPoint.getY()*2-4-upY, 8, 8);
						else if(300==zoomTime) g.fillOval(movingPoint.getX()/2-4-leftX, movingPoint.getY()/2-4-upY, 8, 8);
					}
					//ͻ����ʾ�ٽ���
					if(focusNearPoint){
						g.setColor(Color.ORANGE);
						if(100==zoomTime) g.fillOval(focusNearX-4-leftX, focusNearY-4-upY, 8, 8);
						else if(200==zoomTime) g.fillOval(focusNearX*2-4-leftX, focusNearY*2-4-upY, 8, 8);
						else if(300==zoomTime) g.fillOval(focusNearX/2-4-leftX, focusNearY/2-4-upY, 8, 8);
					}
					//���ͻ����ʾĳ����
					if(focusOnPoint) 
					{
						g.setColor(Color.red);
						if(100==zoomTime) g.fillOval(focusX-4-leftX, focusY-4-upY, 8, 8);
						else if(200==zoomTime) g.fillOval(focusX*2-4-leftX, focusY*2-4-upY, 8, 8);
						else if(300==zoomTime) g.fillOval(focusX/2-4-leftX, focusY/2-4-upY, 8, 8);
					}
					
					//������ģʽ
					if(Traffic&&zoomTime!=300)
					{
						Vector<Vector<Point>> temMapVec = new Vector<Vector<Point>>();
						double multi=1;
						if(zoomTime==100) 
						{
							multi=1;
							temMapVec=MainFrame.getMainFrame().autoThread.auto.getPntNormalGridVec();					
						}
						else if(zoomTime==200) 
						{
							multi=2;
							temMapVec=MainFrame.getMainFrame().autoThread.auto.getPntBigGridVec();
						}
						if(mapIndex[i]<100)
						{
							Vector<Point> temVec=temMapVec.elementAt(mapIndex[i]);
							
							Vector<Point> totalVec=MainFrame.getMainFrame().autoThread.auto.getPntVec();
							for(Point e:temVec)
							{
								//
								Point ePoint=totalVec.elementAt(e.getNum());
								Vector<Edge> temEdge=ePoint.getEdgeVec();
								for(int j=0;j<temEdge.size();j++)
								{
									Point temPoint=totalVec.elementAt(temEdge.elementAt(j).getTo());
									int firX,firY,secX,secY;
									firX=(int) (ePoint.getX()*multi-leftX);
									firY=(int) (ePoint.getY()*multi-upY);
									secX=(int) (temPoint.getX()*multi-leftX);
									secY=(int) (temPoint.getY()*multi-upY);
									
									double traflow=temEdge.elementAt(j).getTraflow();
									double volumn=temEdge.elementAt(j).getVolumn();
									
									if(traflow/volumn>=1) g.setColor(Color.red);
									else if(traflow/volumn>=0.75) g.setColor(Color.yellow);
									else if(traflow/volumn>=0.6) g.setColor(Color.blue);
									else g.setColor(Color.green);
									g.drawLine(firX, firY, secX, secY);
									g.setColor(Color.black);
								}
							}
						}						
					}
					//���·ģʽ
					if(showShortestPath)
					{
						if(TrafficFlowSimulation.changeShortPath)
						{
							TrafficFlowSimulation.changeShortPath=false;
							setChangingPath();
						}
						if(TrafficFlowSimulation.isWork) g.setColor(Color.black);
						else g.setColor(Color.red);
						int size=pathPoints.size();
						//ͻ����ʾ·���ϵĶ˵�
						if(100==zoomTime){
							for(int index=0;index<size;++index){
								PathNode cur=pathPoints.elementAt(index);
								g.fillOval(cur.getX()-4-leftX, cur.getY()-4-upY, 8, 8);
							}
						}
						else if(200==zoomTime){
							for(int index=0;index<size;++index){
								PathNode cur=pathPoints.elementAt(index);
								g.fillOval(cur.getX()*2-4-leftX, cur.getY()*2-4-upY, 8, 8);
							}
						}
						else{
							for(int index=0;index<size;++index){
								PathNode cur=pathPoints.elementAt(index);
								g.fillOval(cur.getX()/2-4-leftX, cur.getY()/2-4-upY, 8, 8);
							}
						}
						
						//ͻ����ʾ·���ı�
						if(100==zoomTime){
							for(int index=0;index<size-1;++index){
								PathNode fir=pathPoints.elementAt(index);
								PathNode sec=pathPoints.elementAt(index+1);
								int firX,firY,secX,secY;
								firX=fir.getX()-leftX;
								firY=fir.getY()-upY;
								secX=sec.getX()-leftX;
								secY=sec.getY()-upY;
								g.drawLine(firX, firY, secX, secY);
							}
						}
						else if(200==zoomTime){
							for(int index=0;index<size-1;++index){
								PathNode fir=pathPoints.elementAt(index);
								PathNode sec=pathPoints.elementAt(index+1);
								int firX,firY,secX,secY;
								firX=fir.getX()*2-leftX;
								firY=fir.getY()*2-upY;
								secX=sec.getX()*2-leftX;
								secY=sec.getY()*2-upY;
								g.drawLine(firX, firY, secX, secY);
							}
						}
						else{
							for(int index=0;index<size-1;++index){
								PathNode fir=pathPoints.elementAt(index);
								PathNode sec=pathPoints.elementAt(index+1);
								int firX,firY,secX,secY;
								firX=fir.getX()/2-leftX;
								firY=fir.getY()/2-upY;
								secX=sec.getX()/2-leftX;
								secY=sec.getY()/2-upY;
								g.drawLine(firX, firY, secX, secY);
							}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setMapIndex(int mapIndex[])
	{
		this .mapIndex=mapIndex;
	}
	
	public void putImage(String fileName[],boolean drawable[], int asis[],int zoomTime,
			int leftX,int upY){
		this.leftX=leftX;
		this.upY=upY;
		this.zoomTime=zoomTime;
		this.fileName=fileName;
		this.drawable=drawable;
		this.asis=asis;
		repaint();
	}
	
	//��ʾͼƬ��ͻ����ʾĳһ����
	public void pointFocusedImage(String fileName[],boolean drawable[],int asis[], int leftX, int upY, int focusX,int focusY,boolean mark){
		this.fileName=fileName;
		this.drawable=drawable;
		this.leftX=leftX;
		this.upY=upY;
		this.asis=asis;
		
		if(isMoving) {
			movingPoint=new Point(focusX,focusY);
			repaint();
			return;
		}
		
		if(mark){
			this.focusX=focusX;
			this.focusY=focusY;
		}
		else{
			this.focusNearX=focusX;
			this.focusNearY=focusY;
		}
		if(mark) this.setFocusPoint();
		else   this.setFocusNearPoint();	
	}
	
	private void setChangingPath()
	{
		Vector<Integer>pathPnts=new Vector<Integer>();
		ShortestPath ansPath=new ShortestPath(startPoint,endPoint,MainFrame.getMainFrame().autoThread.auto.getPntVec());
			if(ansPath.TrafficDijkstra())//����ܹ��ҵ����·
			{
				pathPnts=ansPath.getPathVec();//��ȡ���·�ĵ�
				setPathPoints(pathPnts);	
			}			
	}
	
	public void setPathPoints(Vector<Integer>pathPnts){
		pathPoints=new Vector<PathNode>();
		Vector<Point>pntVec=MainFrame.getMainFrame().autoThread.auto.getPntVec();
		startPoint=pntVec.elementAt(pathPnts.elementAt(0));
		endPoint=pntVec.elementAt(pathPnts.elementAt(pathPnts.size()-1));
		for(int index:pathPnts)
		{
			Point temp=pntVec.elementAt(index);
			pathPoints.add(new PathNode(temp.getX(),temp.getY()));
		}
		showShortestPath=true;
	}
	
	public void setImported(){
		this.toImport=true;
	}
	
	public void cancelImported(){
		this.toImport=false;
	}
	
	public void setTraffic(){
		this.Traffic=true;
		repaint();
	}
	
	public void cancelTraffic(){
		this.Traffic=false;
		repaint();
	}
	
	//�ھ۽������ʱ������ú�isMoving
	public void setIsMoving(){
		this.isMoving=true;
	}
	
	//����ģ�⳵����̬���ƶ�
	public void setImitateMoving(Point temp){
		isMoving=true;
		movingPoint=temp;
		repaint();
	}
	
	//ȡ������ģ�⳵����̬���ƶ�
	public void cancelImitateMoving(){
		isMoving=false;
		movingPoint=null;
		repaint();
	}
	
	//��ʾ�ڽ���ͱ�
	public void setNear(){
		this.showNear=true;
		repaint();
	}
	
	//ȡ����ʾ�ڽ���ͱ�
	public void cancelNear(){
		this.showNear=false;
		repaint();
	}
	
	//�۽����ڽ���
	public void setFocusNearPoint(){
		this.focusNearPoint=true;
		repaint();
	}
	
	//ȡ���۽����ڽ���
	public void cancelFocusNearPoint(){
		this.focusNearPoint=false;
		repaint();
	}
	
	//�۽���ѡ�е�
	public void setFocusPoint(){
		this.focusOnPoint=true;
		repaint();
	}
	
	//ȡ���۽���ѡ�е�
	public void cancelFocusPoint(){
		this.focusOnPoint=false;
		repaint();
	}
	
	public void setTemNearPoint(Vector<Point> temp){
		temNearPoint=new Vector<Point>(temp);
	}
	
	public void cancelShortestPath(){
		showShortestPath=false;
		focusOnPoint=false;
		focusNearPoint=false;
		repaint();
	}
	
	public void getPaint()
	{
		repaint();
	}

}






class PathNode {//������ʾ���··����ʱ����ݱ���·������
	private int x,y;
	PathNode(int x,int y){
		this.x=x;
		this.y=y;
	}
	PathNode(){}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}
