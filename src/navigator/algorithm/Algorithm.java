package navigator.algorithm;


import java.util.Vector;

import navigator.dataStruct.*;
import navigator.algorithm.*;
import navigator.background.*;
import navigator.UI.*;

//�㷨������
public class Algorithm {
	public static boolean stopThread=false;
	
	//�Ź����㷨
		public static void Sudoku(Vector<Vector<Point>> nearPointVec,Vector<Point> temVec,
				int indexX,int indexY,int scale){
			int index=indexY*scale+indexX;//�����ǵڼ�������
			int mark=scale-1;//�к��������ӵ��±�
			//��Ҫ���ҵĵ��䵽���Ͻǵĸ���ʱ	
			if(indexX==0&&indexY==0){
				for(int i=0;i<nearPointVec.elementAt(index+1).size();i++)
					temVec.add(nearPointVec.elementAt(index+1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+scale).size();i++)
					temVec.add(nearPointVec.elementAt(index+scale).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+scale+1).size();i++)
					temVec.add(nearPointVec.elementAt(index+scale+1).elementAt(i));
			}
			//��Ҫ���ҵĵ��䵽���Ͻǵĸ���ʱ
			else if(indexX==mark&&indexY==0){
				for(int i=0;i<nearPointVec.elementAt(index-1).size();i++)
					temVec.add(nearPointVec.elementAt(index-1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+scale-1).size();i++)
					temVec.add(nearPointVec.elementAt(index+scale-1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+scale).size();i++)
					temVec.add(nearPointVec.elementAt(index+scale).elementAt(i));
			}
			//��Ҫ���ҵĵ��䵽���½ǵĸ���ʱ
			else if(indexX==0&&indexY==mark){
				for(int i=0;i<nearPointVec.elementAt(index-scale).size();i++)
					temVec.add(nearPointVec.elementAt(index-scale).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index-scale+1).size();i++)
					temVec.add(nearPointVec.elementAt(index-scale+1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index+1).size();i++)
					temVec.add(nearPointVec.elementAt(index+1).elementAt(i));
			}
			//��Ҫ���ҵĵ��䵽���½ǵĸ���ʱ
			else if(indexX==mark&&indexY==mark){
				for(int i=0;i<nearPointVec.elementAt(index-scale-1).size();i++)
					temVec.add(nearPointVec.elementAt(index-scale-1).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index-scale).size();i++)
					temVec.add(nearPointVec.elementAt(index-scale).elementAt(i));
				for(int i=0;i<nearPointVec.elementAt(index-1).size();i++)
					temVec.add(nearPointVec.elementAt(index-1).elementAt(i));
			}
			//��Ҫ���ҵĵ��䵽��һ�и����в��ĸ���ʱ
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
			//��Ҫ���ҵĵ��䵽���һ���в��ĸ���ʱ
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
			//��Ҫ���ҵĵ��䵽��һ���в��ĸ���ʱ
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
			//��Ҫ���ҵĵ��䵽���һ���в��ĸ���ʱ
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
			//��Ҫ���ҵĵ��䵽�в��ĸ���ʱ
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
	
	//��ȡ����֮��ľ���
	public static double getdistance(Point a,Point b){
		double temp=(a.getX()-b.getX())*(a.getX()-b.getX())
				+(a.getY()-b.getY())*(a.getY()-b.getY());
		temp=Math.sqrt(temp);
		return temp;
	}
	
	//�Զ�ģ�⶯̬�˶�
	public static void CalculateMoving(final Vector<Integer> record){
		//ʹ���̶߳�̬չʾ��������Ȼrepaint()��������ͬʱ����
		new Thread(){
			@Override
			public void run(){
				stopThread=false;//ÿ��Ҫ��֮ǰ��ֹ�̲߳���ֵΪfalse,
				
				Vector<Integer> temRecord=record;
				Vector<Point> pntVec=MainFrame.getMainFrame().autoThread.auto.getPntVec();
				for(int i=0;!stopThread&&i<temRecord.size()-1;i++){
					Point a=pntVec.elementAt(temRecord.elementAt(i));
					Point b=pntVec.elementAt(temRecord.elementAt(i+1));
					double x1=a.getX(),y1=a.getY(),x2=b.getX(),y2=b.getY();
					if(x1==x2&&y1==y2) return;
					int result;//һ�κ�����ÿ��x�仯���Y
					int tempCase;//��������λ�õ��������
					double num;//ģ�⶯̬x��y�ܵ���ʱ�仯ֵ
					
					if(Math.abs(x2-x1)>Math.abs(y2-y1)) tempCase=1;
					else tempCase=2;
					
					switch(tempCase){
						case 1: 	//����x2��x1�ķ�λ
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
						case 2: //����y2��y1�ķ�λ
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