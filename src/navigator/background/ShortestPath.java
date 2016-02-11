package navigator.background;

import navigator.dataStruct.*;
import navigator.algorithm.*;
import navigator.background.*;
import navigator.UI.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Vector;

//在最短路中的节点类，来代替Point以节省空间
class Node{
	int num;//节点的编号
	double dist;//离起点的距离
	double passtime;//离起点的时间
	Node(int num,double dist){
		this.num=num;
		this.dist=dist;
	}
	Node(){}
}

//在最短路中的车点类，来代替Point以节省空间
class TraCar{
	int num;//节点的编号
	double passtime;//离起点的时间
	TraCar(int num,double time){
		this.num=num;
		this.passtime=time;
	}
	TraCar(){}
}

//基于Node类的以时间作为排序规则的Comparator
class TimeComparator implements Comparator<TraCar>{

	@Override
	public int compare(TraCar o1, TraCar o2) {
		if(o1.passtime<o2.passtime) return -1;
		else if(o1.passtime>o2.passtime) return 1;
		else return 0;
	}
	
}

//基于Node类的以距离作为排序规则的Comparator
class NodeComparator implements Comparator<Node>{

	@Override
	public int compare(Node o1, Node o2) {
		if(o1.dist<o2.dist) return -1;
		else if(o1.dist>o2.dist) return 1;
		else return 0;
	}
	
}


//最短路类
public class ShortestPath {
	private boolean vis[];//记录一个节点是否被访问过
	private Vector<Point> pntVec;//点的数组
	private int prePnt[];//记录一个点在最短路上的上一个点的数组
	private double minDist[];//记录一个点距离起点最短距离的数组
	private PriorityQueue<Node> que;//优先队列，用于求最短路的过程
	private PriorityQueue<TraCar> traQue;//优先队列，用于求车流量下最短路径的距离
	private Point staPnt,endPnt;//起点和终点
	private Vector<Integer>answer;//最短路的答案
	private boolean staToEnd;//表示图是否连通
	private double flowConstant=0.1;//车流预设参数
	
	
	//以起点、终点还有点的集合作为参数的构造函数
	public ShortestPath(Point staPnt,Point endPnt,Vector<Point>pntVec){
		this.staPnt=staPnt;
		this.endPnt=endPnt;
		this.pntVec=pntVec;
		answer=new Vector<Integer>();
		vis=new boolean[pntVec.size()+5];
		prePnt=new int[pntVec.size()+5];
		minDist=new double[pntVec.size()+5];
		que=new PriorityQueue<Node>(new NodeComparator());
		traQue=new PriorityQueue<TraCar>(new TimeComparator());
		staToEnd=false;
	}
	//passTime=(int)(flowConstant*nxtEdgeLen*F(nxtEdgeFlow,nxtEdgeVolumn));
	
	//返回两个坐标之间的距离
	private double distBetween(int firX,int firY,int secX,int secY)
	{
		int xDiff=firX-secX;
		int yDiff=firY-secY;
		return Math.sqrt((double)xDiff*xDiff+yDiff*yDiff);
	}
	
	public double F(int n,int v)
	{
		double ratio=(double)n/(double)v;
		if(ratio<0.7) return 1.0;
		else return 1.0+Math.pow(Math.E, ratio);
	}
	
	//通过回溯获取最短路的路线
	private void DFS(int cur)
	{
		if(prePnt[cur]==-1)
		{
			answer.add(cur);
			return;
		}
		DFS(prePnt[cur]);
		answer.add(cur);
	}
	
	//最短路算法（迪杰斯特拉算法），返回的bool值代表图是否连通
		public  boolean TrafficDijkstra()
		{
			
			//初始化数据
			for(int i=0;i<pntVec.size();i++)
			{
				vis[i]=false;//把每个点标记为没有访问过
				prePnt[i]=-1;//-1表示在最短路上没有上一个节点
				minDist[i]=1000000000;//初始化最短距离为一个很大的数字
			}
			
			int staNum=staPnt.getNum();
			int endNum=endPnt.getNum();
			minDist[staNum]=0;//令起点的距离值为0
			
			traQue.add(new TraCar(staNum,0.0));//把起点放进优先队列
			while(!traQue.isEmpty())
			{
				TraCar top=traQue.poll();
				int topNum=top.num;
				double topDist=top.passtime;
				if(vis[topNum]) continue;//如果当前点已经被访问，就不管
				vis[topNum]=true;//标志已经访问过，以后不能在被访问
				if(topNum==endNum) { staToEnd=true;return true;}//如果当前节点是终点，停止过程
				
				Vector<Edge> nearEdge=pntVec.elementAt(topNum).getEdgeVec();
				for(int i=0;i<nearEdge.size();i++)
				{
					Edge temp=nearEdge.elementAt(i);
					int to=temp.getTo();
					if(vis[to]) continue;//如果已经被访问过了，就不用管了
					double len=temp.getLength();
					int flow=temp.getTraflow();
					int volumn=temp.getVolumn();
					double passtime=flowConstant*len*F(flow,volumn);
					//满足条件时，更新下一个点的最小距离
					//利用三角形不等式
					if(minDist[to]>topDist+passtime)
					{
						minDist[to]=topDist+passtime;
						prePnt[to]=topNum;
						traQue.add(new TraCar(to,minDist[to]));
					}
				}
							
			}
			return false;
		}
	
	//最短路算法（迪杰斯特拉算法），返回的bool值代表图是否连通
	public  boolean Dijkstra()
	{
		
		//初始化数据
		for(int i=0;i<pntVec.size();i++)
		{
			vis[i]=false;//把每个点标记为没有访问过
			prePnt[i]=-1;//-1表示在最短路上没有上一个节点
			minDist[i]=1000000000;//初始化最短距离为一个很大的数字
		}
		
		int staNum=staPnt.getNum();
		int endNum=endPnt.getNum();
		minDist[staNum]=0;//令起点的距离值为0
		
		que.add(new Node(staNum,0.0));//把起点放进优先队列
		while(!que.isEmpty())
		{
			Node top=que.poll();
			int topNum=top.num;
			double topDist=top.dist;
			int x=pntVec.elementAt(topNum).getX();
			int y=pntVec.elementAt(topNum).getY();
			
			if(vis[topNum]) continue;//如果当前点已经被访问，就不管
			vis[topNum]=true;//标志已经访问过，以后不能在被访问
			if(topNum==endNum) { staToEnd=true;return true;}//如果当前节点是终点，停止过程
			
			HashSet<Integer> set=pntVec.elementAt(topNum).getNearPoints();
			Iterator<Integer> it= set.iterator();
			while(it.hasNext())
			{
				int nextNearPnt=it.next();
				if(vis[nextNearPnt]) continue;//如果已经被访问过，就不用管了
				int nextX=pntVec.elementAt(nextNearPnt).getX();
				int nextY=pntVec.elementAt(nextNearPnt).getY();
				double distDiff=distBetween(x,y,nextX,nextY);
				
				//满足条件时，更新下一个点的最小距离
				//利用三角形不等式
				if(minDist[nextNearPnt]>topDist+distDiff)
				{
					minDist[nextNearPnt]=topDist+distDiff;
					prePnt[nextNearPnt]=topNum;
					que.add(new Node(nextNearPnt,minDist[nextNearPnt]));
				}
			}		
		}
		return false;
	}
	
	
	
	//返回最短路路径
	public Vector<Integer> getPathVec(){
		if(!staToEnd) return null;
		DFS(endPnt.getNum());
		return answer;
	}
	
}

