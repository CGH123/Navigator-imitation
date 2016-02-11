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
 * һ����ͨ��һ��·����ʱTime=cLF(n/v),c��Ԥ�����,L�ǵ�·���ȣ�f�Ǻ�����n��������v�ǵ�·����
 */
public class TrafficFlowSimulation implements Runnable{
	private Vector <Point> pntVec;//����AutoGraph�����pntVec���������
	private Long timeCounter;//ʱ���¼��
	private PriorityQueue<Car> queueForTime;
	private Vector<Car> testTraffic;//��������һ���㷨
	private Random rand;
	static private final double flowConstant=1;//����Ԥ�����
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
	
	//��������ͨ����·��ʱ�ĺ�����n�ǵ�·������v�ǵ�·����
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
				//ÿ���ߵĳ�ʼ����
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
					elementAt(topCar.getEdgeIndex()).minusFlow();//�����뿪����·��������һ
			
			nxtEdge=pntVec.elementAt(curPnt).nextEdge();
			nxtDestination=pntVec.elementAt(curPnt).
					getEdgeVec().elementAt(nxtEdge).getTo();
			nxtEdgeFlow=pntVec.elementAt(curPnt).
					getEdgeVec().elementAt(nxtEdge).getTraflow()+1;//��һ����Ϊ�������ļ���
			nxtEdgeLen=pntVec.elementAt(curPnt).
					getEdgeVec().elementAt(nxtEdge).getLength();
			nxtEdgeVolumn=pntVec.elementAt(curPnt).
					getEdgeVec().elementAt(nxtEdge).getVolumn();
			
			pntVec.elementAt(curPnt).
					getEdgeVec().elementAt(nxtEdge).addFlow();//��������õ�·��������һ
			
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

