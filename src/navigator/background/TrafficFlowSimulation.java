package navigator.background;

import navigator.dataStruct.*;
import navigator.algorithm.*;
import navigator.background.*;
import navigator.UI.*;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Vector;


/*
 * date:2015-5-28
 * 一辆车通过一条路的用时Time=cLF(n/v),c是预设参数,L是道路长度，f是函数，n是流量，v是道路容量
 */
public class TrafficFlowSimulation implements Runnable{
	private Vector <Point> pntVec;//就是AutoGraph里面的pntVec，点的数组
	private Long timeCounter;//时间记录器
	private PriorityQueue<Car> queueForTime;
	private Vector<Car> testTraffic;//测试另外一种算法
	private Random rand;
	static private final double flowConstant=1;//车流预设参数
	public static boolean isWork=false;
	public static boolean changeShortPath=false;
	
	public TrafficFlowSimulation(Vector<Point>pntVec)
	{
		testTraffic=new Vector<Car>();
		rand=new Random();
		this.pntVec=pntVec;
		timeCounter=new Long(0);
		queueForTime=new PriorityQueue<Car>(new CarComparator());
		initTrafficFlow();
	}
	
	//计算汽车通过道路用时的函数，n是道路流量，v是道路容量
	private double F(int n,int v)
	{
		double ratio=(double)n/(double)v;
		if(ratio<1) return 1.0;
		else return 1.0+Math.pow(Math.E, ratio);
	}
	
	private void initTrafficFlow()
	{
		Random random=new Random();
		for(int i=0;i<pntVec.size();i++)
		{
			Point curPnt=pntVec.elementAt(i);
			for(int edgeNum=0;edgeNum<curPnt.getEdgeVec().size();edgeNum++)
			{
				Edge curEdge=curPnt.getEdgeVec().elementAt(edgeNum);
				//每条边的初始流量
				int initFlow=(int) (curEdge.getVolumn()*0.4+random.nextInt((int) (curEdge.getVolumn()*0.7+1)));
				curEdge.setTraflow(initFlow);
				double len=curEdge.getLength();
				int passTime=(int)(flowConstant*len*F(initFlow,curEdge.getVolumn()));
				for(int j=0;j<initFlow;j++)
				{
					Car tem=new Car(curPnt.getNum(),curEdge.getTo(),edgeNum,new Long(passTime));
					tem.setStartPnt(i);
					queueForTime.add(new Car(curPnt.getNum(),curEdge.getTo(),edgeNum,new Long(passTime*j/initFlow+1)));
				}
			}
		}
	}
	
	@Override
	public void run()
	{
		int curPnt,fromPnt,nxtEdge,nxtEdgeFlow,nxtEdgeVolumn,nxtDestination,passTime;
		double nxtEdgeLen;
		int num=0;
		while(queueForTime.isEmpty()==false&&isWork)
		{
			Car topCar=queueForTime.poll();
			curPnt=topCar.getDestination();
			fromPnt=topCar.getStartPnt();
			pntVec.elementAt(fromPnt).getEdgeVec().
					elementAt(topCar.getEdgeIndex()).minusFlow();//车辆离开那条路，流量减一
			
			nxtEdge=pntVec.elementAt(curPnt).nextEdge();
			nxtDestination=pntVec.elementAt(curPnt).
					getEdgeVec().elementAt(nxtEdge).getTo();
			nxtEdgeFlow=pntVec.elementAt(curPnt).
					getEdgeVec().elementAt(nxtEdge).getTraflow()+1;//加一是因为这辆车的加入
			nxtEdgeLen=pntVec.elementAt(curPnt).
					getEdgeVec().elementAt(nxtEdge).getLength();
			nxtEdgeVolumn=pntVec.elementAt(curPnt).
					getEdgeVec().elementAt(nxtEdge).getVolumn();
			
			pntVec.elementAt(curPnt).
					getEdgeVec().elementAt(nxtEdge).addFlow();//车辆进入该道路，流量加一
			
			passTime=(int)(flowConstant*nxtEdgeLen*F(nxtEdgeFlow,nxtEdgeVolumn));
			//System.out.println("test passTime="+passTime);
			topCar.setArrTime(topCar.getArrTime()+passTime);
			topCar.setDestination(nxtDestination);
			topCar.setEdgeIndex(nxtEdge);
			topCar.setStartPnt(curPnt);
			queueForTime.add(topCar);
			MainFrame.getMainFrame().getPaint();
			
			num++;
			if(num>200000) 
			{
				changeShortPath=true;
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				num=0;
			}
				
		}
	}
	
}

class CarComparator implements Comparator<Car>
{

	@Override
	public int compare(Car a, Car b) {
		int res=a.getArrTime().compareTo(b.getArrTime());
		return res;
	}
	
}

