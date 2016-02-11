package navigator.background;

import navigator.dataStruct.*;
import navigator.algorithm.*;
import navigator.background.*;
import navigator.UI.*;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import javax.imageio.ImageIO;

/*
 * �Զ����ɵ�ͼ��
 * ���ɵ�ͼ�ĵ㡢�ߣ����н��е�ͼͼƬ�ļ��Ļ���
 */
public class AutoGraph {
	private Set<Point> pntSet;//���set
	private Set<String> nameSet;//����������ֵ��ظ�
	private Vector<Point> pntVec;//���Vector
	private Vector<Point>xSortVec;//��ySort����������Vector
	private Vector<Point>ySortVec;//��xSort����������Vector
	private Vector<Vector<Point>> pntSmallGridVec;//��С������ͼ�ϵĸ���
	private Vector<Vector<Point>> pntNormalGridVec;//������ͼ�ϵĸ���
	private Vector<Vector<Point>> pntBigGridVec;//�������ͼ�ϵĸ���
	private Vector<Vector<Point>> nearPointGridVec;//���ڽ�ͼ�ֳɸ��ӣ����ڵ��ҵ��ڽ��ĵ�
	private Vector<Vector<Point>> nearPointFindVec;//������������ҵĵص������ҵ��ڽ���100����
	private HashMap<Point , Boolean> pntScreen;//���ڼ�¼һ�����Ƿ��������С�ߴ��ͼ��
	private Vector<Vector<VirtualEdge> > normalVirtualVec;//��ͨͼ��virtualPoint����
	private Vector<Vector<VirtualEdge> > bigVirtualVec;//��ͼ��virtualPoint����
	private boolean grid[][];//���ڷ�ֹһ�������ڵ������
	private final int cacheNumForSort=100;
	private int cnt=0;///////////////////////////////////
	public AutoGraph()
	{
		generatePoints();//���ɵ�
		generateEdge();//���ɱ�
		checkTheEdge();//�����Ƿ�ȱʧ
		writeImage();//���е�ͼͼƬ�ļ��Ļ���
		addEdge();//��ÿ����������Edge����Ϊ
	}
	
	private void addEdge()
	{
		for(Point e:pntVec)
		{
			e.addEdge(pntVec);
		}
		System.out.println("success add Edge");
	}
	
	
	//���ɵ�
	private void generatePoints()
	{
		System.out.println("generate Points");
		pntVec=new Vector<Point>();
		Random rand=new Random();
		pntSet=new HashSet<Point>();
		nameSet=new HashSet<String>();
		int randa,randb;//
		int length=10000,numberOfGrid=length/100+1;
		grid=new boolean[numberOfGrid][numberOfGrid];
		
		//initialize the nearPointGridVec
		nearPointGridVec=new Vector<Vector<Point>>();
		for(int i=0;i<630;i++)
			nearPointGridVec.add(new Vector<Point>());
		
		//initialize the nearPointFindVec
		nearPointFindVec=new Vector<Vector<Point>>();
		for(int i=0;i<401;i++)
			nearPointFindVec.add(new Vector<Point>());
		
		for(int i=0;i<numberOfGrid;i++)
			for(int j=0;j<numberOfGrid;j++)
				grid[i][j]=false;
		
		int numX,numY;
		for(int i=0;i<10000;i++)
		{
			//System.out.println(i);
			randa=rand.nextInt(10000);
			randb=rand.nextInt(10000);
			numX=randa/100;numY=randb/100;
			if(grid[numX][numY]==true){ i--; continue;}
			Point temp=new Point((int)randa,(int)randb);
			if(!pntSet.contains(temp)&&!nameSet.contains(temp.getName()))
			{
				pntSet.add(temp);
				nameSet.add(temp.getName());
			}
			else { i--;continue;}
			grid[numX][numY]=true;
			int size=pntVec.size();
			temp.setNum(size);
			pntVec.add(temp);
			
			//���㵱ǰ�����ڵĿ�
			int nearPointGridX,nearPointGridY;
			nearPointGridX=temp.getX()/400;
			nearPointGridY=temp.getY()/400;
			nearPointGridVec.elementAt(nearPointGridY*25+nearPointGridX).add(temp);
			
			
			//���㵱ǰ�����ڵĿ�
			int nearPointFindX,nearPointFindY;
			nearPointFindX=temp.getX()/1000;
			nearPointFindY=temp.getY()/1000;
			nearPointFindVec.elementAt(nearPointFindY*10+nearPointFindX).add(temp);
			
		}
	}
	
	
	//����һ���㸽����ĵ㼯
	public Vector<Point> generateFindVec(Point test){
		int indexX=test.getX()/1000,indexY=test.getY()/1000;
		int index=indexX+indexY*10;
		Vector<Point> temVec=new Vector<Point>(nearPointFindVec.elementAt(index));
		
		//�����˷ֿ�
		Algorithm.Sudoku(nearPointFindVec, temVec, indexX, indexY, 10);
		
		Collections.sort(temVec,new distSort(test));
		return temVec;
	}

	
	private void writeImage()//�ѵ�ͼ������Ӳ����
	{
		String mapFileName=new String("d://map//");
		File mapFile=new File(mapFileName);
		if(!mapFile.isDirectory()) mapFile.mkdir();
		WriteBigImageThread bigImageThread=new WriteBigImageThread();
		bigImageThread.start();
		WriteNormalImageThread normalImageThread=new WriteNormalImageThread();
		normalImageThread.start();
		WriteSmallImageThread smallImageThread=new WriteSmallImageThread();
		smallImageThread.start();
		
	}
	
	//������������ͼ
		private void writeNormalImage(){
			//�����ļ���
			File normalDir=new File("d://map//normal//");
			if(!normalDir.isDirectory()) normalDir.mkdir();
			
			pntNormalGridVec=new Vector<Vector<Point>>();
			for(int i=0;i<100;i++)//��ʼ��pntGridVec
			{
				Vector<Point> temp=new Vector<Point>();
				pntNormalGridVec.add(temp);
			}
			
			
			//�ѵ�װ����Ӧ�ĸ����У��൱�ڷ��ڲ�ͬ��ͼƬ��
			int sizeOfPntVec=pntVec.size();
			for(int i=0;i<sizeOfPntVec;i++){
				Point cur=pntVec.elementAt(i);
				int x=pntVec.elementAt(i).getX();
				int y=pntVec.elementAt(i).getY();
				int indexOfGrid=(y/1000)*10+x/1000;
				pntNormalGridVec.elementAt(indexOfGrid).add(new Point(x,y,cur.getNum(),cur.getName()));
			}
			
			int num=0;
			for(int i=0;i<10000;i+=1000)
			{
				for(int j=0;j<10000;j+=1000)
				{
					testMix(i,j,10,num++,2,"normal");
					//ÿ���һ��ͼ���ڽ�������������ɶ�,����ͬ��ԭ��
					MainFrame.getMainFrame().setValue(1);
				}
			}
			for(int i=100;i<110;i++) {
				emptyImage(num++,"normal");
				MainFrame.getMainFrame().setValue(1);
			}
		}
		
		//����ͼ
		private void writeBigImage(){
			//�����ļ���
			File bigDir=new File("d://map//big//");
			if(!bigDir.isDirectory()) bigDir.mkdir();
			
			//�ѵ�װ����Ӧ�ĸ����У��൱�ڷ��ڲ�ͬ��ͼƬ��
			pntBigGridVec=new Vector<Vector<Point>>();
			for(int i=0;i<400;i++)
			{
				Vector<Point> temp=new Vector<Point>();
				pntBigGridVec.add(temp);
			}
			
			int sizeOfPntVec=pntVec.size();
			for(int i=0;i<sizeOfPntVec;i++){
				Point cur=pntVec.elementAt(i);
				int x=pntVec.elementAt(i).getX();
				int y=pntVec.elementAt(i).getY();
				x<<=1;y<<=1;
				int indexOfGrid=(y/1000)*20+x/1000;
				pntBigGridVec.elementAt(indexOfGrid).add(new Point(x,y,cur.getNum(),cur.getName()));
			}
			
			
			/*
			 * �������߳̽��л�ͼ
			 */
			new Thread(){
				@Override
				public void run(){
					for(int i=0;i<10000;i+=1000)
						for(int j=0;j<10000;j+=1000)
						{
							testMix(i,j,20,(i/1000)*20+j/1000,3,"big");
							MainFrame.getMainFrame().setValue(1);
						}
				}
				
			}.start();
			
			new Thread(){
				@Override
				public void run(){
					for(int i=10000;i<20000;i+=1000)
						for(int j=0;j<10000;j+=1000){
							testMix(i,j,20,(i/1000)*20+j/1000,3,"big");
							MainFrame.getMainFrame().setValue(1);
						}
				}
			}.start();
			
			new Thread(){
				@Override
				public void run(){
					for(int i=0;i<10000;i+=1000)
						for(int j=10000;j<20000;j+=1000){
							testMix(i,j,20,(i/1000)*20+j/1000,3,"big");
							MainFrame.getMainFrame().setValue(1);
						}
				}
			}.start();
			
			new Thread(){
				@Override
				public void run(){
					for(int i=10000;i<20000;i+=1000)
						for(int j=10000;j<20000;j+=1000){
							testMix(i,j,20,(i/1000)*20+j/1000,3,"big");
							MainFrame.getMainFrame().setValue(1);
						}
				}
			}.start();
			for(int i=400;i<420;i++) {
				emptyImage(i,"big");//���ɿհ׵�ͼ
				MainFrame.getMainFrame().setValue(1);
			}
		}
		
		//��Сͼ
		private void writeSmallImage(){
			pntScreen=new HashMap<Point,Boolean>();
			
			//�����ļ���
			File smallDir=new File("d://map//small//");
			if(!smallDir.isDirectory()) smallDir.mkdir();
			
			pntSmallGridVec=new Vector<Vector<Point>>();
			for(int i=0;i<25;i++) pntSmallGridVec.add(new Vector<Point>());
			
			Vector<Vector<Point>> screeningVec=new Vector<Vector<Point>>();//����ѡ�����ĵ�
			for(int i=0;i<1600;i++) screeningVec.add(new Vector<Point>());//��ʼ��screeningVec
			
			//�ѵ���ӽ�screeningVec
			int size=pntVec.size();
			for(int i=0;i<size;i++){
				Point cur=pntVec.elementAt(i);
				pntScreen.put(cur, false);
				int x=cur.getX();
				int y=cur.getY();
				int index=(y/250)*40+x/250;
				screeningVec.elementAt(index).add(new Point(x,y,cur.getNum(),cur.getName()));
			}
			
			//����ɸѡ
			Random rand=new Random();
			for(int i=0;i<1600;i++){
				int curSize=screeningVec.elementAt(i).size();
				if(curSize==0) continue;
				int toChoose=rand.nextInt(curSize);//�������ȡѡ��ĵ�
				Point curPnt=screeningVec.elementAt(i).elementAt(toChoose);
				int x=curPnt.getX();
				int y=curPnt.getY();
				x>>=1;y>>=1;//������Сһ��
			    int index=(y/1000)*5+x/1000;
			    pntSmallGridVec.elementAt(index).add(new Point(x,y,curPnt.getNum(),curPnt.getName()));
			    pntScreen.put(curPnt, true);
			}
			
			int num=0;
			for(int i=0;i<5000;i+=1000)
			{
				for(int j=0;j<5000;j+=1000)
				{
					testMix(i,j,5,num++,1,"small");
					MainFrame.getMainFrame().setValue(1);
				}
			}
			for(int i=25;i<30;i++) {
				emptyImage(i,"small");
				MainFrame.getMainFrame().setValue(1);
			}
		}
		
		//minX,minY���Ѿ������Ŵ��������
		private void drawVirtualEdge(int minX,int minY,VirtualEdge edge,Graphics gra)
		{
			VirtualPoint a=edge.a;
			VirtualPoint b=edge.b;
			
			int firX=a.x,secX=b.x;
			int firY=a.y,secY=b.y;
			
			gra.drawLine(firX-minX, firY-minY, secX-minX, secY-minY);
		}
		
	
		//��ͼ����
		//�����ֱ������ͼ�����Ͻ�x���꣬y���꣬һ�ж�����ͼƬ��ͼƬ�ı�ţ���ͼ�ߴ磬ͼƬ���ڵ�Ŀ¼����
	private void testMix(int minY,int minX,int base,int num,int graphSize,String content){
		Vector<Vector<Point>> pntGridVec;
		if(graphSize==1) pntGridVec=pntSmallGridVec;
		else if(graphSize==2) pntGridVec=pntNormalGridVec;
		else pntGridVec=pntBigGridVec;
		Vector<Vector<VirtualEdge>>virtualEdgeVector;
		if(graphSize==2) virtualEdgeVector=normalVirtualVec;
		else if(3==graphSize) virtualEdgeVector=bigVirtualVec;
		else virtualEdgeVector=null;
		try{
			BufferedImage image=new BufferedImage(1000,1000,BufferedImage.TYPE_INT_RGB);
			Graphics gra=image.createGraphics();
			gra.fillRect(0, 0, 1000, 1000);
			gra.setColor(Color.black);
			int indexOfVector=((int)(minY/1000))*base+minX/1000;
			int size=pntGridVec.elementAt(indexOfVector).size();

			for(int i=0;i<size;i++)
			{
				//draw the point 
				Point temp=pntGridVec.elementAt(indexOfVector).elementAt(i);
				int x=temp.getX();
				int y=temp.getY();
				x-=minX;
				y-=minY;
				int radius=2;
				gra.fillOval(x-radius,y-radius, radius*2, radius*2);
				speDrawString(temp.getName(),x,y,gra);
				
				//���ƺ͵�ǰ�������ı�
				if(graphSize!=1)
				{
					int curIndex=temp.getNum();
					HashSet<Integer>nearPoints=pntVec.elementAt(temp.getNum()).getNearPoints();
					Iterator<Integer> it=nearPoints.iterator();//��ȡ������
					
					while(it.hasNext()){//���������е���
						int index=(int)it.next();
					    drawEdge(curIndex,index,minX,minY,graphSize,gra);//���û��ߺ���
					    
					}
					
					if(virtualEdgeVector!=null){
						//System.out.println("test:"+virtualEdgeVector.elementAt(indexOfVector).size()+"(@line 360)");
						for(int edgeNum=0;edgeNum < virtualEdgeVector.elementAt(indexOfVector).size();edgeNum++){
							VirtualEdge curEdge=virtualEdgeVector.elementAt(indexOfVector).elementAt(edgeNum);
							drawVirtualEdge(minX,minY,curEdge,gra);
						}
					}
				}
				
				
				//special process when graphSize is 1,because some points are ignored here
				//���Ŵ�������С��ʱ��������⴦����Ϊ��Щ�㱻������
				else{
					int curIndex=temp.getNum();
					if(pntScreen.get(pntVec.elementAt(curIndex))==false) continue;//when the current point is ignored,skip this process.
					HashSet<Integer>nearPoints=pntVec.elementAt(temp.getNum()).getNearPoints();
					Iterator<Integer> it=nearPoints.iterator();
					
					while(it.hasNext()){
						int index=(int)it.next();
						if(pntScreen.get(pntVec.elementAt(index))==false) continue;//if the other point is ignored,skip this process.
						else  drawEdge(curIndex,index,minX,minY,graphSize,gra);
					}
				}
				
			}
			/*
			 * ��ͼƬ��һ����
			 */
			/*gra.setColor(Color.blue);
			gra.drawLine(1, 1, 999, 1);
			gra.drawLine(1, 1, 1, 999);
			gra.drawLine(1, 999, 999, 999);
			gra.drawLine(999, 1, 999, 999);*/
			
			String fileName="graph"+num+".png";
			gra.dispose();
			ImageIO.write(image, "png", new File("d://map//"+content+"//"+fileName));
		}catch(Exception e){
			
		}
	}
	
	
	/*
	 * ͨ�����������ͼƬ�ĳߴ��ȡ���ӱ��
	 * ������Ҫ���ݱ��ʳ�����Ӧ�ı���
	 */
	private int getIndexOfPnt(int x,int y,int graphSize)
	{
		int index;
		int indexY=y/1000;
		int indexX=x/1000;
		if(graphSize==2) index=indexY*10+indexX;
		else if(graphSize==3) index=indexY*20+indexX;
		else index=indexY*5+indexX;
		
		return index;
	}
	
	
	
	/*
	 * �ж�������ŵĸ����Ƿ�����
	 */
	boolean isAdjacent(int indexA,int indexB,int graphSize)
	{
		if(indexA==indexB) return true;
		int minn=indexA<indexB?indexA:indexB;
		int maxed=indexA>indexB?indexA:indexB;
		
		if(graphSize==3)
		{
			if(minn+1==maxed||minn+20==maxed) return true;
			else return false;
		}
		else if(graphSize==2)
		{
			if(minn+1==maxed||minn+10==maxed) return true;
			else return false;
		}
		else
		{
			if(minn+1==maxed||minn+5==maxed) return true;
			else return false;
		}
	}
	
	
	//��������
	//x,y����Ҫ���ݲ�ͬ�ı����Ի��������������Ӧ�ı���
	private void addVirtualEdge(int fromX,int fromY,int toX,int toY,
			int graphSize,Vector<Vector<VirtualEdge>>vec)
	{
		int temX,temY,nxtX,nxtY;
		int minX,minY,maxedX,maxedY;
		
		//ѡ��x����С���Ǹ�
		if(fromX<toX)
		{
			minX=fromX;
			minY=fromY;
			maxedX=toX;
			maxedY=toY;
		}
		else if(fromX>toX)
		{
			minX=toX;
			minY=toY;
			maxedX=fromX;
			maxedY=fromY;
		}
		else 
		{
			minX=toX;
			minY=toY;
			maxedX=fromX;
			maxedY=toY;
		}
		
		if(maxedX==minX) return ;
		
		double rate=((double)(maxedY-minY)/(double)(maxedX-minX));//б��
		
		temX=minX;
		temY=minY;
		while(temX<=toX)
		{
			VirtualPoint nxtPnt=nxtVirtualPoint(rate,temX,temY);
			nxtX=nxtPnt.x;
			nxtY=nxtPnt.y;
			if(nxtX>toX) break;
			if(rate<0&&temY%1000==0&&nxtX%1000==0){//�����������Ϊ�ᷢ�����ܰ��������ӵ���ȷ��ͼƬ��
				int toIndex=getIndexOfPnt(temX,temY-2,graphSize);
				vec.elementAt(toIndex).add(new VirtualEdge(new VirtualPoint(temX,temY),
						new VirtualPoint(nxtX,nxtY)));
			}
			else{
				int toAddIndex=getIndexOfPnt(temX,temY,graphSize);
				vec.elementAt(toAddIndex).add(new VirtualEdge(new VirtualPoint(temX,temY),
						new VirtualPoint(nxtX,nxtY)));
			}
			
			
			temX=nxtX;
			temY=nxtY;
		}
	}
	
	
	//////����������ߵ���һ����//////////////////////////////
	private static VirtualPoint nxtVirtualPoint(double rate,int curX,int curY)
	{
		int temX=0,temY=0;
		
		if(rate<0)
		{
			int tryY,tryX;
			
			if(curY%1000==0)
				tryY=(curY/1000-1)*1000;
			else tryY=(curY/1000)*1000;
			
			tryX=(curX/1000+1)*1000;
			
			if(curX+(double)(tryY-curY)/rate>tryX)
			{
				temX=tryX;
				temY=curY+(int)((tryX-curX)*rate);
			}
			else
			{
				temY=tryY;
				temX=curX+(int)((tryY-curY)/rate);
			}
			
		}
		
		else if(rate>0)
		{
			int tryY=(curY/1000+1)*1000;
			int tryX=(curX/1000+1)*1000;
			if(curX+(double)(tryY-curY)/rate>tryX)
			{
				temX=tryX;
				temY=curY+(int)((tryX-curX)*rate);
			}
			else
			{
				temY=tryY;
				temX=curX+(int)((tryY-curY)/rate);
			}
			
		}
		else
		{
			if(rate==0)
			{
				temY=curY;
				temX=(curX%1000==0)?curX:(curX/1000+1)*1000;
			}
		}
		return new VirtualPoint(temX,temY);
	}

	
	//���м�����б�����ʾ��ʱ���Ƿ����ȱʧ
	private void checkTheEdge()
	{
		//////��ʼ��VirtualVec
		normalVirtualVec=new Vector<Vector<VirtualEdge>>();
		for(int i=0;i<100;i++) normalVirtualVec.add(new Vector<VirtualEdge>());
		bigVirtualVec=new Vector<Vector<VirtualEdge>>();
		for(int i=0;i<400;i++) bigVirtualVec.add(new Vector<VirtualEdge>());
		
		
		int size=pntVec.size();
		for(int i=0;i<size;i++)
		{
			//��ǰ�������
			int thisX=pntVec.elementAt(i).getX();
			int thisY=pntVec.elementAt(i).getY();
			int indexOfBig=getIndexOfPnt(thisX*2,thisY*2,3);//��ǰ���ڴ��������ڵ�ͼ�ı��
			int indexOfNormal=getIndexOfPnt(thisX,thisY,2);//��ǰ������ͨ���������ڵ�ͼ�ı��
			HashSet<Integer>nxtPntSet=pntVec.elementAt(i).getNearPoints();
			Iterator<Integer> it=nxtPntSet.iterator();
			while(it.hasNext())
			{
				int nxtPnt=it.next();
				//�ڽӵ������
				int nxtX=pntVec.elementAt(nxtPnt).getX();
				int nxtY=pntVec.elementAt(nxtPnt).getY();
				
				//normal�����¼��
				int nxtIndexOfNormal=getIndexOfPnt(nxtX,nxtY,2);
				if(!isAdjacent(indexOfNormal,nxtIndexOfNormal,2))
				{
					addVirtualEdge(thisX,thisY,nxtX,nxtY,2,normalVirtualVec);
				}
				
				//big�����¼��
				int nxtIndexOfBig=getIndexOfPnt(nxtX*2,nxtY*2,3);
				if(!isAdjacent(indexOfBig,nxtIndexOfBig,3))
				{
					addVirtualEdge(thisX<<1,thisY<<1,nxtX<<1,nxtY<<1,3,bigVirtualVec);
				}
			}
		}
		
	}
	
	/////���ߺ���/////
	private void drawEdge(int from,int to,int minX,int minY,int graphSize,Graphics g){
		g.setColor(Color.black);
		Point f=pntVec.elementAt(from);
		Point t=pntVec.elementAt(to);
		
		int fromX,fromY,toX,toY;
		fromX=f.getX();
		fromY=f.getY();
		toX=t.getX();
		toY=t.getY();
		
		//СͼƬ���ǲ����л���
		if(1==graphSize){
			fromX>>=1;
			fromY>>=1;
			toX>>=1;
			toY>>=1;
		}
		
		else if(3==graphSize){
			fromX<<=1;
			fromY<<=1;
			toX<<=1;
			toY<<=1;
		}
		
		else
		{
			
		}
		
		fromX-=minX;
		fromY-=minY;
		toX-=minX;
		toY-=minY;
		
		g.drawLine(fromX, fromY, toX, toY);
	}
	
	
	
	//���ɿհ׵�ͼ
	private void emptyImage(int num,String content){
		try{
			BufferedImage image=new BufferedImage(1000,1000,BufferedImage.TYPE_INT_RGB);
			Graphics gra=image.createGraphics();
			gra.fillRect(0, 0, 1000, 1000);
			String fileName="graph"+num+".png";
			gra.dispose();
			ImageIO.write(image, "png", new File("d://map//"+content+"//"+fileName));
		}catch(Exception e){
			
		}
	}
	
	//��ͼ��д�ϵ�������ֹ������ͼ�ı߽�û�л���
	private void speDrawString(String name,int x,int y,Graphics g){
		FontMetrics fm=g.getFontMetrics();
		
		int stringX,stringY;//string��x,y����
		int stringWidth=fm.stringWidth(name);
		int stringHeight=fm.getAscent();
		
		if(y+stringHeight>1000)stringY=y-2;
		else stringY=y+stringHeight+1;
		
		if(x-stringWidth/2<0) stringX=0;
		else if(x+stringWidth/2>1000) stringX=1000-stringWidth;
		else stringX=x-stringWidth/2;
		
		g.setColor(Color.blue);;
		g.drawString(name, stringX, stringY);
	}
	
	//���ɱ�
	private void generateEdge(){
		System.out.println("generateEdge");
		for(int index=0;index<625;index++){
			int indexX=index%25,indexY=index/25;
			Vector<Point>temVec=new Vector<Point>(nearPointGridVec.elementAt(index));
			
			//�͸����ĵ��������
			Algorithm.Sudoku(nearPointGridVec, temVec, indexX, indexY, 25);				
			
			
			int gridSize=nearPointGridVec.elementAt(index).size();
			for(int i=0;i<gridSize;i++)
			{
				nearPointGridVec.elementAt(index).elementAt(i).distSortMethod(pntVec, temVec);
			}
		}
		
		
	}
	
	
	//////����x�����������////////////////////////////
	/*private void xGenerateEdge(){
		xSortVec=new Vector<Point>(pntVec);
		Collections.sort(xSortVec,new xSort());
		int size=xSortVec.size();
		for(int i=0;i<size;i++){
			int curNum=xSortVec.elementAt(i).getNum();
			/////////////�ѵ���ӽ���ǰ���cachePoint����//////////////////
			if(i>=cacheNumForSort&&i<size-cacheNumForSort){
				for(int j=i-cacheNumForSort;j<i;j++){
					pntVec.elementAt(curNum).addCachePnt(xSortVec.elementAt(j).getNum());
				}
				for(int j=i+1;j<=i+cacheNumForSort;j++){
					pntVec.elementAt(curNum).addCachePnt(xSortVec.elementAt(j).getNum());
				}
			}
			else if(i<cacheNumForSort){
				for(int j=0;j<i;j++){
					pntVec.elementAt(curNum).addCachePnt(xSortVec.elementAt(j).getNum());
				}
				for(int j=i+1;j<=cacheNumForSort;j++){
					pntVec.elementAt(curNum).addCachePnt(xSortVec.elementAt(j).getNum());
				}
			}
			else {
				for(int j=size-cacheNumForSort;j<i;j++){
					pntVec.elementAt(curNum).addCachePnt(xSortVec.elementAt(j).getNum());
				}
				for(int j=i+1;j<size;j++){
					pntVec.elementAt(curNum).addCachePnt(xSortVec.elementAt(j).getNum());
				}
			}
		}
		/////�Ա���������/////
		xSortVec.clear();
		xSortVec=null;
	}*/
	
	//////����y�����������
	/*private void yGenerateEdge(){
		ySortVec=new Vector<Point>(pntVec);
		Collections.sort(ySortVec,new ySort());
		int size=ySortVec.size();
		for(int i=0;i<size;i++){
			int curNum=ySortVec.elementAt(i).getNum();
			/////////////�ѵ���ӽ���ǰ���cachePoint����//////////////////
			if(i>=cacheNumForSort&&i<size-cacheNumForSort){
				for(int j=i-cacheNumForSort;j<i;j++){
					pntVec.elementAt(curNum).addCachePnt(ySortVec.elementAt(j).getNum());
				}
				for(int j=i+1;j<=i+cacheNumForSort;j++){
					pntVec.elementAt(curNum).addCachePnt(ySortVec.elementAt(j).getNum());
				}
			}
			else if(i<cacheNumForSort){
				for(int j=0;j<i;j++){
					pntVec.elementAt(curNum).addCachePnt(ySortVec.elementAt(j).getNum());
				}
				for(int j=i+1;j<=cacheNumForSort;j++){
					pntVec.elementAt(curNum).addCachePnt(ySortVec.elementAt(j).getNum());
				}
			}
			else {
				for(int j=size-cacheNumForSort;j<i;j++){
					pntVec.elementAt(curNum).addCachePnt(ySortVec.elementAt(j).getNum());
				}
				for(int j=i+1;j<size;j++){
					pntVec.elementAt(curNum).addCachePnt(ySortVec.elementAt(j).getNum());
				}
			}
		}
		/////�Ա���������/////
		ySortVec.clear();
		ySortVec=null;
	}*/
	
	
	public Vector<Point> getPntVec()//���ط���㼯
	{
		return pntVec;
	}
	
	public Vector<Vector<Point>> getPntNormalGridVec()//����������ͼ�ϵĸ���
	{
		return pntNormalGridVec;
	}
	
	public Vector<Vector<Point>> getPntBigGridVec()//���������ͼ�ϵĸ���
	{
		return pntBigGridVec;
	}
	
	
	////////////////////////////////////////////////��ͼ�߳���////////////////////////////////////
	//��Сͼ�߳���
	class WriteSmallImageThread extends Thread{
		@Override
		public void run(){
			//System.out.println("small");
			writeSmallImage();
		}
	}
	
	//����ͨͼ�߳���
	class WriteNormalImageThread extends Thread{
		@Override
		public void run(){
			//System.out.println("normal");
			writeNormalImage();
		}
	}
	
	//����ͼ�߳���
	class WriteBigImageThread extends Thread{
		@Override
		public void run(){
			//System.out.println("big");
			writeBigImage();
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////
	
}




//����x������бȽϵıȽ���
class xSort implements Comparator<Point>
{

	@Override
	public int compare(Point arg0, Point arg1) {
		if(arg0.getX()<arg1.getX()) return -1;
		else if(arg0.getX()>arg1.getX()) return 1;
		else return 0;
	}
	
}


//����y������бȽϵıȽ���
class ySort implements Comparator<Point>
{

	@Override
	public int compare(Point o1, Point o2) {
		if(o1.getY()<o2.getY()) return -1;
		else if(o1.getY()>o2.getY()) return 1;
		else return 0;
	}
	
}



/*
* �����޸�һЩ��ͼ�����߲�������bug����ʽ�������һЩ����ĵ��������
*/
class VirtualPoint {
	public int x,y;
	public VirtualPoint(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
}


//���������ɵı�
class VirtualEdge {
	public VirtualPoint a,b;
	public VirtualEdge(VirtualPoint a,VirtualPoint b)
	{
		this.a=a;
		this.b=b;
	}
}



