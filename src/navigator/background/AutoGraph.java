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
 * 自动生成地图类
 * 生成地图的点、边，还有进行地图图片文件的绘制
 */
public class AutoGraph {
	private Set<Point> pntSet;//点的set
	private Set<String> nameSet;//避免出现名字的重复
	private Vector<Point> pntVec;//点的Vector
	private Vector<Point>xSortVec;//经ySort规则排序后的Vector
	private Vector<Point>ySortVec;//经xSort规则排序后的Vector
	private Vector<Vector<Point>> pntSmallGridVec;//最小倍数视图上的格子
	private Vector<Vector<Point>> pntNormalGridVec;//正常视图上的格子
	private Vector<Vector<Point>> pntBigGridVec;//最大倍数视图上的格子
	private Vector<Vector<Point>> nearPointGridVec;//用于将图分成格子，便于点找到邻近的点
	private Vector<Vector<Point>> nearPointFindVec;//用来将坐标查找的地点用于找到邻近的100个点
	private HashMap<Point , Boolean> pntScreen;//用于记录一个点是否出现在最小尺寸的图上
	private Vector<Vector<VirtualEdge> > normalVirtualVec;//普通图的virtualPoint集合
	private Vector<Vector<VirtualEdge> > bigVirtualVec;//大图的virtualPoint集合
	private boolean grid[][];//用于防止一定区域内点的扎堆
	private final int cacheNumForSort=100;
	private int cnt=0;///////////////////////////////////
	public AutoGraph()
	{
		generatePoints();//生成点
		generateEdge();//生成边
		checkTheEdge();//检查边是否缺失
		writeImage();//进行地图图片文件的绘制
		addEdge();//给每个点进行添加Edge的行为
	}
	
	private void addEdge()
	{
		for(Point e:pntVec)
		{
			e.addEdge(pntVec);
		}
		System.out.println("success add Edge");
	}
	
	
	//生成点
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
			
			//计算当前点所在的块
			int nearPointGridX,nearPointGridY;
			nearPointGridX=temp.getX()/400;
			nearPointGridY=temp.getY()/400;
			nearPointGridVec.elementAt(nearPointGridY*25+nearPointGridX).add(temp);
			
			
			//计算当前点所在的块
			int nearPointFindX,nearPointFindY;
			nearPointFindX=temp.getX()/1000;
			nearPointFindY=temp.getY()/1000;
			nearPointFindVec.elementAt(nearPointFindY*10+nearPointFindX).add(temp);
			
		}
	}
	
	
	//返回一个点附近点的点集
	public Vector<Point> generateFindVec(Point test){
		int indexX=test.getX()/1000,indexY=test.getY()/1000;
		int index=indexX+indexY*10;
		Vector<Point> temVec=new Vector<Point>(nearPointFindVec.elementAt(index));
		
		//利用了分块
		Algorithm.Sudoku(nearPointFindVec, temVec, indexX, indexY, 10);
		
		Collections.sort(temVec,new distSort(test));
		return temVec;
	}

	
	private void writeImage()//把地图保存在硬盘上
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
	
	//画正常倍数的图
		private void writeNormalImage(){
			//建立文件夹
			File normalDir=new File("d://map//normal//");
			if(!normalDir.isDirectory()) normalDir.mkdir();
			
			pntNormalGridVec=new Vector<Vector<Point>>();
			for(int i=0;i<100;i++)//初始化pntGridVec
			{
				Vector<Point> temp=new Vector<Point>();
				pntNormalGridVec.add(temp);
			}
			
			
			//把点装进相应的格子中，相当于放在不同的图片中
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
					//每完成一张图就在进度条更新下完成度,下面同样原理
					MainFrame.getMainFrame().setValue(1);
				}
			}
			for(int i=100;i<110;i++) {
				emptyImage(num++,"normal");
				MainFrame.getMainFrame().setValue(1);
			}
		}
		
		//画大图
		private void writeBigImage(){
			//建立文件夹
			File bigDir=new File("d://map//big//");
			if(!bigDir.isDirectory()) bigDir.mkdir();
			
			//把点装进相应的格子中，相当于放在不同的图片中
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
			 * 开动多线程进行画图
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
				emptyImage(i,"big");//生成空白的图
				MainFrame.getMainFrame().setValue(1);
			}
		}
		
		//画小图
		private void writeSmallImage(){
			pntScreen=new HashMap<Point,Boolean>();
			
			//建立文件夹
			File smallDir=new File("d://map//small//");
			if(!smallDir.isDirectory()) smallDir.mkdir();
			
			pntSmallGridVec=new Vector<Vector<Point>>();
			for(int i=0;i<25;i++) pntSmallGridVec.add(new Vector<Point>());
			
			Vector<Vector<Point>> screeningVec=new Vector<Vector<Point>>();//用于选择代表的点
			for(int i=0;i<1600;i++) screeningVec.add(new Vector<Point>());//初始化screeningVec
			
			//把点添加进screeningVec
			int size=pntVec.size();
			for(int i=0;i<size;i++){
				Point cur=pntVec.elementAt(i);
				pntScreen.put(cur, false);
				int x=cur.getX();
				int y=cur.getY();
				int index=(y/250)*40+x/250;
				screeningVec.elementAt(index).add(new Point(x,y,cur.getNum(),cur.getName()));
			}
			
			//进行筛选
			Random rand=new Random();
			for(int i=0;i<1600;i++){
				int curSize=screeningVec.elementAt(i).size();
				if(curSize==0) continue;
				int toChoose=rand.nextInt(curSize);//用随机数取选择的点
				Point curPnt=screeningVec.elementAt(i).elementAt(toChoose);
				int x=curPnt.getX();
				int y=curPnt.getY();
				x>>=1;y>>=1;//坐标缩小一倍
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
		
		//minX,minY是已经经过放大处理的坐标
		private void drawVirtualEdge(int minX,int minY,VirtualEdge edge,Graphics gra)
		{
			VirtualPoint a=edge.a;
			VirtualPoint b=edge.b;
			
			int firX=a.x,secX=b.x;
			int firY=a.y,secY=b.y;
			
			gra.drawLine(firX-minX, firY-minY, secX-minX, secY-minY);
		}
		
	
		//画图函数
		//参数分别代表画的图的左上角x坐标，y坐标，一行多少张图片，图片的编号，地图尺寸，图片所在的目录名字
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
				
				//绘制和当前点相连的边
				if(graphSize!=1)
				{
					int curIndex=temp.getNum();
					HashSet<Integer>nearPoints=pntVec.elementAt(temp.getNum()).getNearPoints();
					Iterator<Integer> it=nearPoints.iterator();//获取迭代器
					
					while(it.hasNext()){//迭代器进行迭代
						int index=(int)it.next();
					    drawEdge(curIndex,index,minX,minY,graphSize,gra);//调用画边函数
					    
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
				//当放大倍数是最小的时候进行特殊处理，因为有些点被忽略了
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
			 * 给图片加一个框
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
	 * 通过传入坐标和图片的尺寸获取格子编号
	 * 坐标需要根据倍率乘以相应的倍数
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
	 * 判断两个编号的格子是否相邻
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
	
	
	//添加虚拟边
	//x,y坐标要根据不同的倍率以基本的坐标乘以相应的倍数
	private void addVirtualEdge(int fromX,int fromY,int toX,int toY,
			int graphSize,Vector<Vector<VirtualEdge>>vec)
	{
		int temX,temY,nxtX,nxtY;
		int minX,minY,maxedX,maxedY;
		
		//选出x坐标小的那个
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
		
		double rate=((double)(maxedY-minY)/(double)(maxedX-minX));//斜率
		
		temX=minX;
		temY=minY;
		while(temX<=toX)
		{
			VirtualPoint nxtPnt=nxtVirtualPoint(rate,temX,temY);
			nxtX=nxtPnt.x;
			nxtY=nxtPnt.y;
			if(nxtX>toX) break;
			if(rate<0&&temY%1000==0&&nxtX%1000==0){//特判这个是因为会发生不能把虚拟边添加到正确的图片中
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
	
	
	//////求出添加虚拟边的下一个点//////////////////////////////
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

	
	//进行检查所有边在显示的时候是否会有缺失
	private void checkTheEdge()
	{
		//////初始化VirtualVec
		normalVirtualVec=new Vector<Vector<VirtualEdge>>();
		for(int i=0;i<100;i++) normalVirtualVec.add(new Vector<VirtualEdge>());
		bigVirtualVec=new Vector<Vector<VirtualEdge>>();
		for(int i=0;i<400;i++) bigVirtualVec.add(new Vector<VirtualEdge>());
		
		
		int size=pntVec.size();
		for(int i=0;i<size;i++)
		{
			//当前点的坐标
			int thisX=pntVec.elementAt(i).getX();
			int thisY=pntVec.elementAt(i).getY();
			int indexOfBig=getIndexOfPnt(thisX*2,thisY*2,3);//当前点在大倍率下所在的图的编号
			int indexOfNormal=getIndexOfPnt(thisX,thisY,2);//当前点在普通倍率下所在的图的编号
			HashSet<Integer>nxtPntSet=pntVec.elementAt(i).getNearPoints();
			Iterator<Integer> it=nxtPntSet.iterator();
			while(it.hasNext())
			{
				int nxtPnt=it.next();
				//邻接点的坐标
				int nxtX=pntVec.elementAt(nxtPnt).getX();
				int nxtY=pntVec.elementAt(nxtPnt).getY();
				
				//normal倍率下检查
				int nxtIndexOfNormal=getIndexOfPnt(nxtX,nxtY,2);
				if(!isAdjacent(indexOfNormal,nxtIndexOfNormal,2))
				{
					addVirtualEdge(thisX,thisY,nxtX,nxtY,2,normalVirtualVec);
				}
				
				//big倍率下检查
				int nxtIndexOfBig=getIndexOfPnt(nxtX*2,nxtY*2,3);
				if(!isAdjacent(indexOfBig,nxtIndexOfBig,3))
				{
					addVirtualEdge(thisX<<1,thisY<<1,nxtX<<1,nxtY<<1,3,bigVirtualVec);
				}
			}
		}
		
	}
	
	/////画边函数/////
	private void drawEdge(int from,int to,int minX,int minY,int graphSize,Graphics g){
		g.setColor(Color.black);
		Point f=pntVec.elementAt(from);
		Point t=pntVec.elementAt(to);
		
		int fromX,fromY,toX,toY;
		fromX=f.getX();
		fromY=f.getY();
		toX=t.getX();
		toY=t.getY();
		
		//小图片考虑不进行画边
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
	
	
	
	//生成空白的图
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
	
	//在图上写上地名，防止地名在图的边界没有画上
	private void speDrawString(String name,int x,int y,Graphics g){
		FontMetrics fm=g.getFontMetrics();
		
		int stringX,stringY;//string的x,y坐标
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
	
	//生成边
	private void generateEdge(){
		System.out.println("generateEdge");
		for(int index=0;index<625;index++){
			int indexX=index%25,indexY=index/25;
			Vector<Point>temVec=new Vector<Point>(nearPointGridVec.elementAt(index));
			
			//和附近的点进行连边
			Algorithm.Sudoku(nearPointGridVec, temVec, indexX, indexY, 25);				
			
			
			int gridSize=nearPointGridVec.elementAt(index).size();
			for(int i=0;i<gridSize;i++)
			{
				nearPointGridVec.elementAt(index).elementAt(i).distSortMethod(pntVec, temVec);
			}
		}
		
		
	}
	
	
	//////按照x坐标规则排序////////////////////////////
	/*private void xGenerateEdge(){
		xSortVec=new Vector<Point>(pntVec);
		Collections.sort(xSortVec,new xSort());
		int size=xSortVec.size();
		for(int i=0;i<size;i++){
			int curNum=xSortVec.elementAt(i).getNum();
			/////////////把点添加进当前点的cachePoint里面//////////////////
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
		/////以便垃圾回收/////
		xSortVec.clear();
		xSortVec=null;
	}*/
	
	//////按照y坐标规则排序
	/*private void yGenerateEdge(){
		ySortVec=new Vector<Point>(pntVec);
		Collections.sort(ySortVec,new ySort());
		int size=ySortVec.size();
		for(int i=0;i<size;i++){
			int curNum=ySortVec.elementAt(i).getNum();
			/////////////把点添加进当前点的cachePoint里面//////////////////
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
		/////以便垃圾回收/////
		ySortVec.clear();
		ySortVec=null;
	}*/
	
	
	public Vector<Point> getPntVec()//返回方格点集
	{
		return pntVec;
	}
	
	public Vector<Vector<Point>> getPntNormalGridVec()//返回正常视图上的格子
	{
		return pntNormalGridVec;
	}
	
	public Vector<Vector<Point>> getPntBigGridVec()//返回最大视图上的格子
	{
		return pntBigGridVec;
	}
	
	
	////////////////////////////////////////////////画图线程类////////////////////////////////////
	//画小图线程类
	class WriteSmallImageThread extends Thread{
		@Override
		public void run(){
			//System.out.println("small");
			writeSmallImage();
		}
	}
	
	//画普通图线程类
	class WriteNormalImageThread extends Thread{
		@Override
		public void run(){
			//System.out.println("normal");
			writeNormalImage();
		}
	}
	
	//画大图线程类
	class WriteBigImageThread extends Thread{
		@Override
		public void run(){
			//System.out.println("big");
			writeBigImage();
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////
	
}




//按照x坐标进行比较的比较器
class xSort implements Comparator<Point>
{

	@Override
	public int compare(Point arg0, Point arg1) {
		if(arg0.getX()<arg1.getX()) return -1;
		else if(arg0.getX()>arg1.getX()) return 1;
		else return 0;
	}
	
}


//按照y坐标进行比较的比较器
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
* 用于修复一些地图上连边不完整的bug，方式就是添加一些虚拟的点来填补连边
*/
class VirtualPoint {
	public int x,y;
	public VirtualPoint(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
}


//由虚拟点组成的边
class VirtualEdge {
	public VirtualPoint a,b;
	public VirtualEdge(VirtualPoint a,VirtualPoint b)
	{
		this.a=a;
		this.b=b;
	}
}



