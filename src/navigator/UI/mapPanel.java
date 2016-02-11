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


//用于显示地图的JPanel子类
public class mapPanel extends JPanel
{	
	private Vector<Point> temNearPoint;//邻近的100个点的vector
	private boolean focusNearPoint;//是否把点上色
	private boolean showNear; //是否把邻近点和边上色
	private boolean drawable[];
	private boolean focusOnPoint;//是否突出显示一个点	
	private boolean toImport;
	private boolean showShortestPath;//是否显示最短路
	private boolean isMoving; //鼠标可以拖拽地图的条件下是否进行模拟移动
	private boolean Traffic;//是否启动车流量的动态变化
	private int asis[];
	private int zoomTime;//放大倍数
	private int focusX,focusY;//突出显示的点在图上的坐标
	private int focusNearX,focusNearY;//突出显示的附近邻近点
	private int leftX,upY;//与MainFrame的leftX和upY含义一样
	private Vector<PathNode>pathPoints;//最短路上的点的编号
	private String fileName[];
	private int mapIndex[];//鼠标拖动显示的地图索引
	private Point startPoint,endPoint;//最短路的起点和终点
	
	private Point movingPoint;//临时变量，移动的点
	
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
		setCursor(cu);//设置光标为移动光标
		
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
					
					
					//显示其他边模式					
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
					//动态展示邻近点
					if(isMoving&&movingPoint!=null){
						g.setColor(Color.blue);
						if(100==zoomTime) g.fillOval(movingPoint.getX()-4-leftX, movingPoint.getY()-4-upY, 8, 8);
						else if(200==zoomTime) g.fillOval(movingPoint.getX()*2-4-leftX, movingPoint.getY()*2-4-upY, 8, 8);
						else if(300==zoomTime) g.fillOval(movingPoint.getX()/2-4-leftX, movingPoint.getY()/2-4-upY, 8, 8);
					}
					//突出显示临近点
					if(focusNearPoint){
						g.setColor(Color.ORANGE);
						if(100==zoomTime) g.fillOval(focusNearX-4-leftX, focusNearY-4-upY, 8, 8);
						else if(200==zoomTime) g.fillOval(focusNearX*2-4-leftX, focusNearY*2-4-upY, 8, 8);
						else if(300==zoomTime) g.fillOval(focusNearX/2-4-leftX, focusNearY/2-4-upY, 8, 8);
					}
					//如果突出显示某个点
					if(focusOnPoint) 
					{
						g.setColor(Color.red);
						if(100==zoomTime) g.fillOval(focusX-4-leftX, focusY-4-upY, 8, 8);
						else if(200==zoomTime) g.fillOval(focusX*2-4-leftX, focusY*2-4-upY, 8, 8);
						else if(300==zoomTime) g.fillOval(focusX/2-4-leftX, focusY/2-4-upY, 8, 8);
					}
					
					//车流量模式
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
					//最短路模式
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
						//突出显示路径上的端点
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
						
						//突出显示路径的边
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
	
	//显示图片并突出显示某一个点
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
			if(ansPath.TrafficDijkstra())//如果能够找到最短路
			{
				pathPnts=ansPath.getPathVec();//获取最短路的点
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
	
	//在聚焦到点的时候就设置好isMoving
	public void setIsMoving(){
		this.isMoving=true;
	}
	
	//设置模拟车辆动态点移动
	public void setImitateMoving(Point temp){
		isMoving=true;
		movingPoint=temp;
		repaint();
	}
	
	//取消设置模拟车辆动态点移动
	public void cancelImitateMoving(){
		isMoving=false;
		movingPoint=null;
		repaint();
	}
	
	//显示邻近点和边
	public void setNear(){
		this.showNear=true;
		repaint();
	}
	
	//取消显示邻近点和边
	public void cancelNear(){
		this.showNear=false;
		repaint();
	}
	
	//聚焦在邻近点
	public void setFocusNearPoint(){
		this.focusNearPoint=true;
		repaint();
	}
	
	//取消聚焦在邻近点
	public void cancelFocusNearPoint(){
		this.focusNearPoint=false;
		repaint();
	}
	
	//聚焦在选中点
	public void setFocusPoint(){
		this.focusOnPoint=true;
		repaint();
	}
	
	//取消聚焦在选中点
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






class PathNode {//用于显示最短路路径的时候短暂保存路径的类
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
