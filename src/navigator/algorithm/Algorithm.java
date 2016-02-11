package navigator.algorithm;


import java.util.Vector;

import navigator.dataStruct.*;
import navigator.algorithm.*;
import navigator.background.*;
import navigator.UI.*;

//算法集合类
public class Algorithm {
	public static boolean stopThread=false;
	
	//九宫格算法
		public static void Sudoku(Vector<Vector<Point>> nearPointVec,Vector<Point> temVec,
				int indexX,int indexY,int scale){
			int index=indexY*scale+indexX;//计算是第几个格子
			int mark=scale-1;//行和列最大格子的下标
			//当要查找的点落到左上角的格子时	
			if(indexX==0&&indexY==0){
				for(int i=0;i<nearPointVec.elementAt(index+1).size();i++)
					temVec.add(nearPointVec.elementAt(index+1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+scale).size();i++)
					temVec.add(nearPointVec.elementAt(index+scale).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+scale+1).size();i++)
					temVec.add(nearPointVec.elementAt(index+scale+1).elementAt(i));
			}
			//当要查找的点落到右上角的格子时
			else if(indexX==mark&&indexY==0){
				for(int i=0;i<nearPointVec.elementAt(index-1).size();i++)
					temVec.add(nearPointVec.elementAt(index-1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+scale-1).size();i++)
					temVec.add(nearPointVec.elementAt(index+scale-1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+scale).size();i++)
					temVec.add(nearPointVec.elementAt(index+scale).elementAt(i));
			}
			//当要查找的点落到左下角的格子时
			else if(indexX==0&&indexY==mark){
				for(int i=0;i<nearPointVec.elementAt(index-scale).size();i++)
					temVec.add(nearPointVec.elementAt(index-scale).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index-scale+1).size();i++)
					temVec.add(nearPointVec.elementAt(index-scale+1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+1).size();i++)
					temVec.add(nearPointVec.elementAt(index+1).elementAt(i));
			}
			//当要查找的点落到右下角的格子时
			else if(indexX==mark&&indexY==mark){
				for(int i=0;i<nearPointVec.elementAt(index-scale-1).size();i++)
					temVec.add(nearPointVec.elementAt(index-scale-1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index-scale).size();i++)
					temVec.add(nearPointVec.elementAt(index-scale).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index-1).size();i++)
					temVec.add(nearPointVec.elementAt(index-1).elementAt(i));
			}
			//当要查找的点落到第一行格子中部的格子时
			else if(indexY==0&&indexX>0&&indexX<mark){
				for(int i=0;i<nearPointVec.elementAt(index-1).size();i++)
					temVec.add(nearPointVec.elementAt(index-1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+1).size();i++)
					temVec.add(nearPointVec.elementAt(index+1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+scale).size();i++)
					temVec.add(nearPointVec.elementAt(index+scale).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+scale-1).size();i++)
					temVec.add(nearPointVec.elementAt(index+scale-1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+scale+1).size();i++)
					temVec.add(nearPointVec.elementAt(index+scale+1).elementAt(i));
			}
			//当要查找的点落到最后一行中部的格子时
			else if(indexY==mark&&indexX>0&&indexX<mark){
				for(int i=0;i<nearPointVec.elementAt(index-1).size();i++)
					temVec.add(nearPointVec.elementAt(index-1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+1).size();i++)
					temVec.add(nearPointVec.elementAt(index+1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index-scale).size();i++)
					temVec.add(nearPointVec.elementAt(index-scale).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index-scale-1).size();i++)
					temVec.add(nearPointVec.elementAt(index-scale-1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index-scale+1).size();i++)
					temVec.add(nearPointVec.elementAt(index-scale+1).elementAt(i));
			}
			//当要查找的点落到第一列中部的格子时
			else if(indexX==0&&indexY>0&&indexY<mark){
				for(int i=0;i<nearPointVec.elementAt(index+scale).size();i++)
					temVec.add(nearPointVec.elementAt(index+scale).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index-scale).size();i++)
					temVec.add(nearPointVec.elementAt(index-scale).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+1).size();i++)
					temVec.add(nearPointVec.elementAt(index+1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+scale+1).size();i++)
					temVec.add(nearPointVec.elementAt(index+scale+1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index-scale+1).size();i++)
					temVec.add(nearPointVec.elementAt(index-scale+1).elementAt(i));
			}
			//当要查找的点落到最后一列中部的格子时
			else if(indexX==mark&&indexY>0&&indexY<mark){
				for(int i=0;i<nearPointVec.elementAt(index+scale).size();i++)
					temVec.add(nearPointVec.elementAt(index+scale).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index-scale).size();i++)
					temVec.add(nearPointVec.elementAt(index-scale).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index-1).size();i++)
					temVec.add(nearPointVec.elementAt(index-1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+scale-1).size();i++)
					temVec.add(nearPointVec.elementAt(index+scale-1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index-scale-1).size();i++)
					temVec.add(nearPointVec.elementAt(index-scale-1).elementAt(i));
			}
			//当要查找的点落到中部的格子时
			else {
				for(int i=0;i<nearPointVec.elementAt(index+1).size();i++)
					temVec.add(nearPointVec.elementAt(index+1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index-1).size();i++)
					temVec.add(nearPointVec.elementAt(index-1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+scale).size();i++)
					temVec.add(nearPointVec.elementAt(index+scale).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index-scale).size();i++)
					temVec.add(nearPointVec.elementAt(index-scale).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+scale-1).size();i++)
					temVec.add(nearPointVec.elementAt(index+scale-1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index-scale+1).size();i++)
					temVec.add(nearPointVec.elementAt(index-scale+1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index-scale-1).size();i++)
					temVec.add(nearPointVec.elementAt(index-scale-1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+scale+1).size();i++)
					temVec.add(nearPointVec.elementAt(index+scale+1).elementAt(i));
			}
		}
	
	//获取两点之间的距离
	public static double getdistance(Point a,Point b){
		double temp=(a.getX()-b.getX())*(a.getX()-b.getX())
				+(a.getY()-b.getY())*(a.getY()-b.getY());
		temp=Math.sqrt(temp);
		return temp;
	}
	
	//自动模拟动态运动
	public static void CalculateMoving(final Vector<Integer> record){
		//使用线程动态展示车辆，不然repaint()函数不能同时进行
		new Thread(){
			@Override
			public void run(){
				stopThread=false;//每次要跑之前终止线程布尔值为false,
				
				Vector<Integer> temRecord=record;
				Vector<Point> pntVec=MainFrame.getMainFrame().autoThread.auto.getPntVec();
				for(int i=0;!stopThread&&i<temRecord.size()-1;i++){
					Point a=pntVec.elementAt(temRecord.elementAt(i));
					Point b=pntVec.elementAt(temRecord.elementAt(i+1));
					double x1=a.getX(),y1=a.getY(),x2=b.getX(),y2=b.getY();
					if(x1==x2&&y1==y2) return;
					int result;//一次函数算每次x变化后的Y
					int tempCase;//分析两点位置的情况问题
					double num;//模拟动态x或y周的临时变化值
					
					if(Math.abs(x2-x1)>Math.abs(y2-y1)) tempCase=1;
					else tempCase=2;
					
					switch(tempCase){
						case 1: 	//考虑x2在x1的方位
							if(x1>x2){
								num=x1;
								while(num!=x2){
									num--;
									result=(int) (num*(y2-y1)/(x2-x1)+(x2*y1-x1*y2)/(x2-x1));
									if(stopThread) break;
									MainFrame.getMainFrame().setImitateMoving(new Point((int) num,result));
									try {
										Thread.sleep(13);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}else if(x1<x2){
								num=x1;
								while(num!=x2){
									num++;
									result=(int) (num*(y2-y1)/(x2-x1)+(x2*y1-x1*y2)/(x2-x1));
									if(stopThread) break;
									MainFrame.getMainFrame().setImitateMoving(new Point((int) num,result));
									try {
										Thread.sleep(13);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							} break;
						case 2: //考虑y2在y1的方位
							if(y1>y2){
								num=y1;
								while(num!=y2){
									num--;
									result=(int) ((num-(x2*y1-x1*y2)/(x2-x1))/((y2-y1)/(x2-x1)));
									if(stopThread) break;
									MainFrame.getMainFrame().setImitateMoving(new Point(result,(int) num));
									try {
										Thread.sleep(13);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							} else if(y1<y2){
								num=y1;
								while(num!=y2){
									num++;
									result=(int) ((num-(x2*y1-x1*y2)/(x2-x1))/((y2-y1)/(x2-x1)));
									if(stopThread) break;
									MainFrame.getMainFrame().setImitateMoving(new Point(result,(int) num));
									try {
										Thread.sleep(13);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							} break;			
					}
				}
				MainFrame.getMainFrame().cancelImitateMoving();
			}
		}.start();
	}
	
	
	
}