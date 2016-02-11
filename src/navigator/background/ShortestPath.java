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

//�����·�еĽڵ��࣬������Point�Խ�ʡ�ռ�
class Node{
	int num;//�ڵ�ı��
	double dist;//�����ľ���
	double passtime;//������ʱ��
	Node(int num,double dist){
		this.num=num;
		this.dist=dist;
	}
	Node(){}
}

//�����·�еĳ����࣬������Point�Խ�ʡ�ռ�
class TraCar{
	int num;//�ڵ�ı��
	double passtime;//������ʱ��
	TraCar(int num,double time){
		this.num=num;
		this.passtime=time;
	}
	TraCar(){}
}

//����Node�����ʱ����Ϊ��������Comparator
class TimeComparator implements Comparator<TraCar>{

	@Override
	public int compare(TraCar o1, TraCar o2) {
		if(o1.passtime<o2.passtime) return -1;
		else if(o1.passtime>o2.passtime) return 1;
		else return 0;
	}
	
}

//����Node����Ծ�����Ϊ��������Comparator
class NodeComparator implements Comparator<Node>{

	@Override
	public int compare(Node o1, Node o2) {
		if(o1.dist<o2.dist) return -1;
		else if(o1.dist>o2.dist) return 1;
		else return 0;
	}
	
}


//���·��
public class ShortestPath {
	private boolean vis[];//��¼һ���ڵ��Ƿ񱻷��ʹ�
	private Vector<Point> pntVec;//�������
	private int prePnt[];//��¼һ���������·�ϵ���һ���������
	private double minDist[];//��¼һ������������̾��������
	private PriorityQueue<Node> que;//���ȶ��У����������·�Ĺ���
	private PriorityQueue<TraCar> traQue;//���ȶ��У����������������·���ľ���
	private Point staPnt,endPnt;//�����յ�
	private Vector<Integer>answer;//���·�Ĵ�
	private boolean staToEnd;//��ʾͼ�Ƿ���ͨ
	private double flowConstant=0.1;//����Ԥ�����
	
	
	//����㡢�յ㻹�е�ļ�����Ϊ�����Ĺ��캯��
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
	
	//������������֮��ľ���
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
	
	//ͨ�����ݻ�ȡ���·��·��
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
	
	//���·�㷨���Ͻ�˹�����㷨�������ص�boolֵ����ͼ�Ƿ���ͨ
		public  boolean TrafficDijkstra()
		{
			
			//��ʼ������
			for(int i=0;i<pntVec.size();i++)
			{
				vis[i]=false;//��ÿ������Ϊû�з��ʹ�
				prePnt[i]=-1;//-1��ʾ�����·��û����һ���ڵ�
				minDist[i]=1000000000;//��ʼ����̾���Ϊһ���ܴ������
			}
			
			int staNum=staPnt.getNum();
			int endNum=endPnt.getNum();
			minDist[staNum]=0;//�����ľ���ֵΪ0
			
			traQue.add(new TraCar(staNum,0.0));//�����Ž����ȶ���
			while(!traQue.isEmpty())
			{
				TraCar top=traQue.poll();
				int topNum=top.num;
				double topDist=top.passtime;
				if(vis[topNum]) continue;//�����ǰ���Ѿ������ʣ��Ͳ���
				vis[topNum]=true;//��־�Ѿ����ʹ����Ժ����ڱ�����
				if(topNum==endNum) { staToEnd=true;return true;}//�����ǰ�ڵ����յ㣬ֹͣ����
				
				Vector<Edge> nearEdge=pntVec.elementAt(topNum).getEdgeVec();
				for(int i=0;i<nearEdge.size();i++)
				{
					Edge temp=nearEdge.elementAt(i);
					int to=temp.getTo();
					if(vis[to]) continue;//����Ѿ������ʹ��ˣ��Ͳ��ù���
					double len=temp.getLength();
					int flow=temp.getTraflow();
					int volumn=temp.getVolumn();
					double passtime=flowConstant*len*F(flow,volumn);
					//��������ʱ��������һ�������С����
					//���������β���ʽ
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
	
	//���·�㷨���Ͻ�˹�����㷨�������ص�boolֵ����ͼ�Ƿ���ͨ
	public  boolean Dijkstra()
	{
		
		//��ʼ������
		for(int i=0;i<pntVec.size();i++)
		{
			vis[i]=false;//��ÿ������Ϊû�з��ʹ�
			prePnt[i]=-1;//-1��ʾ�����·��û����һ���ڵ�
			minDist[i]=1000000000;//��ʼ����̾���Ϊһ���ܴ������
		}
		
		int staNum=staPnt.getNum();
		int endNum=endPnt.getNum();
		minDist[staNum]=0;//�����ľ���ֵΪ0
		
		que.add(new Node(staNum,0.0));//�����Ž����ȶ���
		while(!que.isEmpty())
		{
			Node top=que.poll();
			int topNum=top.num;
			double topDist=top.dist;
			int x=pntVec.elementAt(topNum).getX();
			int y=pntVec.elementAt(topNum).getY();
			
			if(vis[topNum]) continue;//�����ǰ���Ѿ������ʣ��Ͳ���
			vis[topNum]=true;//��־�Ѿ����ʹ����Ժ����ڱ�����
			if(topNum==endNum) { staToEnd=true;return true;}//�����ǰ�ڵ����յ㣬ֹͣ����
			
			HashSet<Integer> set=pntVec.elementAt(topNum).getNearPoints();
			Iterator<Integer> it= set.iterator();
			while(it.hasNext())
			{
				int nextNearPnt=it.next();
				if(vis[nextNearPnt]) continue;//����Ѿ������ʹ����Ͳ��ù���
				int nextX=pntVec.elementAt(nextNearPnt).getX();
				int nextY=pntVec.elementAt(nextNearPnt).getY();
				double distDiff=distBetween(x,y,nextX,nextY);
				
				//��������ʱ��������һ�������С����
				//���������β���ʽ
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
	
	
	
	//�������··��
	public Vector<Integer> getPathVec(){
		if(!staToEnd) return null;
		DFS(endPnt.getNum());
		return answer;
	}
	
}

