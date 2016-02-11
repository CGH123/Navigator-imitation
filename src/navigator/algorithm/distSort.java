package navigator.algorithm;

import java.util.Comparator;

import navigator.dataStruct.Point;

//�Ծ�����Ϊ��������Comparator
//���캯����һ��Point�����������������������Խ����Point�����Խ��ǰ
public class distSort implements Comparator<Point>
{
	private Point init;
	@Override
	public int compare(Point fir,Point sec){
		int distFir=((fir).getX()-init.getX())*((fir).getX()-init.getX())
				+((fir).getY()-init.getY())*((fir).getY()-init.getY());
		int distSec=((sec).getX()-init.getX())*((sec).getX()-init.getX())
				+((sec).getY()-init.getY())*((sec).getY()-init.getY());
		if(distFir<distSec)return -1;
		else if(distFir>distSec)return 1;
		else return 0;
	}
	
	public distSort(Point init)
	{
		this.init=init;
	}
}
