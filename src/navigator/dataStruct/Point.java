package navigator.dataStruct;


import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import navigator.dataStruct.*;
import navigator.algorithm.*;
import navigator.background.*;
import navigator.UI.*;
/*
 * Point���Ǳ���ص����
 * �������һ��static�鹹����һ��ר��ϵͳ���ڹ������������ɹ���
 */
public class Point{
	private Vector<Edge> edgeVec; //ÿ����ı߼���
	private int x,y;//����ֵ
	private int num;//���
	private String name;//�������
	static private Vector<Vector<keyValue>> expert;//���ڹ������
	static private Vector<keyValue> firChar=new Vector<keyValue>();
	static private Random random;//���������������Random�����
	//private Vector<pair>edgePoints;//�ڽӱ�
	private HashSet<Integer>nearPoints;//���ڱ���һ���㸽���ĵ�
	
	
	//�ڲ��࣬���ڼ�¼�ڽӱ�
	class pair
	{
		private int x,y;
		public pair(int x,int y){
			this.x=x;
			this.y=y;
		}
		public int getX(){
			return x;			
		}
		public int getY(){
			return y;
		}
	}
	
	
	static
	{
		random=new Random();
	}
	
	
	//����ר�ҿ�
	//ר�ҿ��ǻ���һЩͳ�������������
	static{
		expert=new Vector<Vector<keyValue>>();
		
		//a��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> aChar=new Vector<keyValue>();
		aChar.add(new keyValue("b",2));
		aChar.add(new keyValue("c",5));
		aChar.add(new keyValue("d",11));
		aChar.add(new keyValue("f",12));
		aChar.add(new keyValue("g",15));
		aChar.add(new keyValue("i",21));
		aChar.add(new keyValue("k",23));
		aChar.add(new keyValue("l",32));
		aChar.add(new keyValue("m",35));
		aChar.add(new keyValue("n",59));
		aChar.add(new keyValue("p",60));
		aChar.add(new keyValue("r",74));
		aChar.add(new keyValue("s",78));
		aChar.add(new keyValue("t",91));
		aChar.add(new keyValue("u",92));
		aChar.add(new keyValue("v",95));
		aChar.add(new keyValue("w",96));
		aChar.add(new keyValue("x",97));
		aChar.add(new keyValue("y",100));
		expert.add(aChar);
		
		//b��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> bChar=new Vector<keyValue>();
		bChar.add(new keyValue("a",9));
		bChar.add(new keyValue("b",13));
		bChar.add(new keyValue("e",42));
		bChar.add(new keyValue("i",55));
		bChar.add(new keyValue("l",63));
		bChar.add(new keyValue("o",78));
		bChar.add(new keyValue("r",83));
		bChar.add(new keyValue("u",95));
		bChar.add(new keyValue("y",100));
		expert.add(bChar);
		
		//c��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> cChar=new Vector<keyValue>();
		cChar.add(new keyValue("a",15));
		cChar.add(new keyValue("e",32));
		cChar.add(new keyValue("h",47));
		cChar.add(new keyValue("i",50));
		cChar.add(new keyValue("k",60));
		cChar.add(new keyValue("l",69));
		cChar.add(new keyValue("o",87));
		cChar.add(new keyValue("r",93));
		cChar.add(new keyValue("t",95));
		cChar.add(new keyValue("u",100));
		expert.add(cChar);
		
		//d��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> dChar=new Vector<keyValue>();
		dChar.add(new keyValue("a",15));
		dChar.add(new keyValue("d",17));
		dChar.add(new keyValue("e",42));
		dChar.add(new keyValue("i",55));
		dChar.add(new keyValue("l",57));
		dChar.add(new keyValue("n",58));
		dChar.add(new keyValue("o",74));
		dChar.add(new keyValue("r",80));
		dChar.add(new keyValue("s",85));
		dChar.add(new keyValue("u",90));
		dChar.add(new keyValue("v",91));
		dChar.add(new keyValue("w",97));
		dChar.add(new keyValue("y",100));
		expert.add(dChar);
		
		//e��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> eChar=new Vector<keyValue>();
		eChar.add(new keyValue("a",9));
		eChar.add(new keyValue("c",11));
		eChar.add(new keyValue("d",23));
		eChar.add(new keyValue("e",28));
		eChar.add(new keyValue("f",30));
		eChar.add(new keyValue("g",31));
		eChar.add(new keyValue("i",33));
		eChar.add(new keyValue("l",38));
		eChar.add(new keyValue("m",42));
		eChar.add(new keyValue("n",53));
		eChar.add(new keyValue("o",54));
		eChar.add(new keyValue("p",55));
		eChar.add(new keyValue("r",75));
		eChar.add(new keyValue("s",85));
		eChar.add(new keyValue("t",89));
		eChar.add(new keyValue("v",91));
		eChar.add(new keyValue("w",92));
		eChar.add(new keyValue("x",93));
		eChar.add(new keyValue("y",100));
		expert.add(eChar);
		
		//f��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> fChar=new Vector<keyValue>();
		fChar.add(new keyValue("a",13));
		fChar.add(new keyValue("e",26));
		fChar.add(new keyValue("i",39));
		fChar.add(new keyValue("l",44));
		fChar.add(new keyValue("o",70));
		fChar.add(new keyValue("r",80));
		fChar.add(new keyValue("t",86));
		fChar.add(new keyValue("u",100));
		expert.add(fChar);
		
		//g��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> gChar=new Vector<keyValue>();
		gChar.add(new keyValue("a",14));
		gChar.add(new keyValue("e",27));
		gChar.add(new keyValue("g",28));
		gChar.add(new keyValue("h",50));
		gChar.add(new keyValue("i",56));
		gChar.add(new keyValue("l",59));
		gChar.add(new keyValue("n",60));
		gChar.add(new keyValue("o",81));
		gChar.add(new keyValue("r",92));
		gChar.add(new keyValue("u",100));
		expert.add(gChar);
		
		//h��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> hChar=new Vector<keyValue>();
		hChar.add(new keyValue("a",18));
		hChar.add(new keyValue("e",73));
		hChar.add(new keyValue("i",85));
		hChar.add(new keyValue("o",94));
		hChar.add(new keyValue("r",95));
		hChar.add(new keyValue("t",97));
		hChar.add(new keyValue("u",100));
		expert.add(hChar);
		
		//i��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> iChar=new Vector<keyValue>();
		iChar.add(new keyValue("a",1));
		iChar.add(new keyValue("c",4));
		iChar.add(new keyValue("d",11));
		iChar.add(new keyValue("e",14));
		iChar.add(new keyValue("f",16));
		iChar.add(new keyValue("g",20));
		iChar.add(new keyValue("k",21));
		iChar.add(new keyValue("l",30));
		iChar.add(new keyValue("m",34));
		iChar.add(new keyValue("n",66));
		iChar.add(new keyValue("o",68));
		iChar.add(new keyValue("r",73));
		iChar.add(new keyValue("s",81));
		iChar.add(new keyValue("t",98));
		iChar.add(new keyValue("v",100));
		expert.add(iChar);
		
		//j��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> jChar=new Vector<keyValue>();
		jChar.add(new keyValue("a",4));
		jChar.add(new keyValue("e",14));
		jChar.add(new keyValue("o",43));
		jChar.add(new keyValue("u",99));
		jChar.add(new keyValue("y",100));
		expert.add(jChar);
		
		//k��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> kChar=new Vector<keyValue>();
		kChar.add(new keyValue("a",9));
		kChar.add(new keyValue("e",53));
		kChar.add(new keyValue("f",54));
		kChar.add(new keyValue("i",76));
		kChar.add(new keyValue("l",78));
		kChar.add(new keyValue("n",81));
		kChar.add(new keyValue("s",86));
		kChar.add(new keyValue("w",89));
		kChar.add(new keyValue("u",95));
		kChar.add(new keyValue("y",100));
		expert.add(kChar);
		
		//l��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> lChar=new Vector<keyValue>();
		lChar.add(new keyValue("a",10));
		lChar.add(new keyValue("b",11));
		lChar.add(new keyValue("d",17));
		lChar.add(new keyValue("e",36));
		lChar.add(new keyValue("f",39));
		lChar.add(new keyValue("i",52));
		lChar.add(new keyValue("k",53));
		lChar.add(new keyValue("l",56));
		lChar.add(new keyValue("o",80));
		lChar.add(new keyValue("r",81));
		lChar.add(new keyValue("s",83));
		lChar.add(new keyValue("t",84));
		lChar.add(new keyValue("u",91));
		lChar.add(new keyValue("v",92));
		lChar.add(new keyValue("y",100));
		expert.add(lChar);
		
		//m��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> mChar=new Vector<keyValue>();
		mChar.add(new keyValue("a",16));
		mChar.add(new keyValue("b",19));
		mChar.add(new keyValue("e",53));
		mChar.add(new keyValue("f",54));
		mChar.add(new keyValue("i",64));
		mChar.add(new keyValue("m",65));
		mChar.add(new keyValue("o",85));
		mChar.add(new keyValue("p",88));
		mChar.add(new keyValue("r",89));
		mChar.add(new keyValue("s",91));
		mChar.add(new keyValue("u",96));
		mChar.add(new keyValue("y",99));
		expert.add(mChar);
		
		//n��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> nChar=new Vector<keyValue>();
		nChar.add(new keyValue("a",8));
		nChar.add(new keyValue("c",9));
		nChar.add(new keyValue("d",23));
		nChar.add(new keyValue("e",46));
		nChar.add(new keyValue("g",65));
		nChar.add(new keyValue("i",69));
		nChar.add(new keyValue("k",71));
		nChar.add(new keyValue("l",72));
		nChar.add(new keyValue("n",73));
		nChar.add(new keyValue("o",83));
		nChar.add(new keyValue("s",87));
		nChar.add(new keyValue("t",95));
		nChar.add(new keyValue("y",100));
		expert.add(nChar);
		
		//o��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> oChar=new Vector<keyValue>();
		oChar.add(new keyValue("a",1));
		oChar.add(new keyValue("b",3));
		oChar.add(new keyValue("c",4));
		oChar.add(new keyValue("d",7));
		oChar.add(new keyValue("f",17));
		oChar.add(new keyValue("i",18));
		oChar.add(new keyValue("k",20));
		oChar.add(new keyValue("l",24));
		oChar.add(new keyValue("m",29));
		oChar.add(new keyValue("n",43));
		oChar.add(new keyValue("o",46));
		oChar.add(new keyValue("p",52));
		oChar.add(new keyValue("r",66));
		oChar.add(new keyValue("s",68));
		oChar.add(new keyValue("t",74));
		oChar.add(new keyValue("u",92));
		oChar.add(new keyValue("v",93));
		oChar.add(new keyValue("w",99));
		oChar.add(new keyValue("y",100));
		expert.add(oChar);
		
		//p��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> pChar=new Vector<keyValue>();
		pChar.add(new keyValue("a",14));
		pChar.add(new keyValue("e",40));
		pChar.add(new keyValue("h",42));
		pChar.add(new keyValue("i",48));
		pChar.add(new keyValue("l",60));
		pChar.add(new keyValue("o",73));
		pChar.add(new keyValue("r",88));
		pChar.add(new keyValue("s",90));
		pChar.add(new keyValue("t",92));
		pChar.add(new keyValue("u",98));
		pChar.add(new keyValue("y",100));
		expert.add(pChar);
		
		//q��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> qChar=new Vector<keyValue>();
		qChar.add(new keyValue("u",100));
		expert.add(qChar);
		
		//r��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> rChar=new Vector<keyValue>();
		rChar.add(new keyValue("a",6));
		rChar.add(new keyValue("c",7));
		rChar.add(new keyValue("d",11));
		rChar.add(new keyValue("e",45));
		rChar.add(new keyValue("f",46));
		rChar.add(new keyValue("g",47));
		rChar.add(new keyValue("i",58));
		rChar.add(new keyValue("k",60));
		rChar.add(new keyValue("l",61));
		rChar.add(new keyValue("m",62));
		rChar.add(new keyValue("n",66));
		rChar.add(new keyValue("o",78));
		rChar.add(new keyValue("s",84));
		rChar.add(new keyValue("t",85));
		rChar.add(new keyValue("u",92));
		rChar.add(new keyValue("v",94));
		rChar.add(new keyValue("y",100));
		expert.add(rChar);
		
		//s��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> sChar=new Vector<keyValue>();
		sChar.add(new keyValue("a",12));
		sChar.add(new keyValue("c",14));
		sChar.add(new keyValue("e",30));
		sChar.add(new keyValue("h",39));
		sChar.add(new keyValue("i",47));
		sChar.add(new keyValue("k",49));
		sChar.add(new keyValue("l",51));
		sChar.add(new keyValue("m",53));
		sChar.add(new keyValue("o",63));
		sChar.add(new keyValue("p",66));
		sChar.add(new keyValue("s",69));
		sChar.add(new keyValue("t",94));
		sChar.add(new keyValue("u",98));
		sChar.add(new keyValue("w",99));
		sChar.add(new keyValue("y",100));
		expert.add(sChar);
		
		//t��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> tChar=new Vector<keyValue>();
		tChar.add(new keyValue("a",10));
		tChar.add(new keyValue("e",30));
		tChar.add(new keyValue("h",52));
		tChar.add(new keyValue("i",73));
		tChar.add(new keyValue("l",74));
		tChar.add(new keyValue("o",89));
		tChar.add(new keyValue("r",92));
		tChar.add(new keyValue("u",96));
		tChar.add(new keyValue("w",97));
		tChar.add(new keyValue("y",100));
		expert.add(tChar);
		
		//u��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> uChar=new Vector<keyValue>();
		uChar.add(new keyValue("a",1));
		uChar.add(new keyValue("b",2));
		uChar.add(new keyValue("c",5));
		uChar.add(new keyValue("d",7));
		uChar.add(new keyValue("g",16));
		uChar.add(new keyValue("i",19));
		uChar.add(new keyValue("l",28));
		uChar.add(new keyValue("m",30));
		uChar.add(new keyValue("n",46));
		uChar.add(new keyValue("p",51));
		uChar.add(new keyValue("r",67));
		uChar.add(new keyValue("s",75));
		uChar.add(new keyValue("e",88));
		uChar.add(new keyValue("t",100));
		expert.add(uChar);
		
		//v��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue>vChar=new Vector<keyValue>();
		vChar.add(new keyValue("a",3));
		vChar.add(new keyValue("e",90));
		vChar.add(new keyValue("i",96));
		vChar.add(new keyValue("o",98));
		vChar.add(new keyValue("y",100));
		expert.add(vChar);
		
		//w��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue>wChar = new Vector<keyValue>();
		wChar.add(new keyValue("a",27));
		wChar.add(new keyValue("e",47));
		wChar.add(new keyValue("h",64));
		wChar.add(new keyValue("i",82));
		wChar.add(new keyValue("l",83));
		wChar.add(new keyValue("n",87));
		wChar.add(new keyValue("o",97));
		wChar.add(new keyValue("r",98));
		wChar.add(new keyValue("s",100));
		expert.add(wChar);
		
		//x��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue>xChar=new Vector<keyValue>();
		xChar.add(new keyValue("a",10));
		xChar.add(new keyValue("c",18));
		xChar.add(new keyValue("e",42));
		xChar.add(new keyValue("i",56));
		xChar.add(new keyValue("p",74));
		xChar.add(new keyValue("t",81));
		xChar.add(new keyValue("u",90));
		xChar.add(new keyValue("y",100));
		expert.add(xChar);
		
		//y��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> yChar=new Vector<keyValue>();
		yChar.add(new keyValue("a",1));
		yChar.add(new keyValue("b",2));
		yChar.add(new keyValue("e",17));
		yChar.add(new keyValue("i",22));
		yChar.add(new keyValue("o",90));
		yChar.add(new keyValue("s",97));
		yChar.add(new keyValue("t",100));
		expert.add(yChar);
		
		//z��ĸ����ӵ���ĸ�ĸ���
		Vector<keyValue> zChar=new Vector<keyValue>();
		zChar.add(new keyValue("a",40));
		zChar.add(new keyValue("e",81));
		zChar.add(new keyValue("i",87));
		zChar.add(new keyValue("l",90));
		zChar.add(new keyValue("o",92));
		zChar.add(new keyValue("y",97));
		zChar.add(new keyValue("z",100));
		expert.add(zChar);
		
		//����ĸ�ĸ��ʹ��򹹽�
		firChar.add(new keyValue("A",5));
		firChar.add(new keyValue("B",12));
		firChar.add(new keyValue("C",19));
		firChar.add(new keyValue("D",23));
		firChar.add(new keyValue("E",26));
		firChar.add(new keyValue("F",31));
		firChar.add(new keyValue("G",35));
		firChar.add(new keyValue("H",40));
		firChar.add(new keyValue("I",42));
		firChar.add(new keyValue("J",45));
		firChar.add(new keyValue("K",47));
		firChar.add(new keyValue("L",52));
		firChar.add(new keyValue("M",59));
		firChar.add(new keyValue("N",65));
		firChar.add(new keyValue("O",69));
		firChar.add(new keyValue("P",73));
		firChar.add(new keyValue("Q",75));
		firChar.add(new keyValue("R",78));
		firChar.add(new keyValue("S",84));
		firChar.add(new keyValue("T",89));
		firChar.add(new keyValue("U",91));
		firChar.add(new keyValue("V",92));
		firChar.add(new keyValue("W",96));
		firChar.add(new keyValue("X",98));
		firChar.add(new keyValue("Y",99));
		firChar.add(new keyValue("Z",100));
		
	}
	
	
	
	//�����������캯��
	//x��y������x�����y����
	public Point(int x,int y){
		this.x=x;
		this.y=y;
		nearPoints=new HashSet<Integer>();
		edgeVec=new Vector<Edge>();
		getRandName();
		
	}
	
	
	//���캯��
	//�ĸ������ֱ����x���ꡢy���ꡢ��ı�š���ĵ���
	public Point(int x,int y,int index,String name){
		this.num=index;
		this.x=x;
		this.y=y;
		this.name=name;
		nearPoints=new HashSet<Integer>();
		edgeVec=new Vector<Edge>();
	}
	
	
	//����equals����
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Point)) 
			return false;
		if(obj==this)
			return true;
		return (x==((Point) obj).getX()&&y==((Point) obj).getY());
	}
	
	
	
	//��ȡx����
	public int getX() {
		return x;
	}
	
	
	//��ȡy����
	public int getY() {
		return y;
	}

	
	//����hashcode����
	@Override
	public int hashCode()//��ϣֵ�����ڲ���
	{
		return x*10001+y;
	}
	
	
	//��ȡ����
	public String getName()
	{
		return name;
	}
	
	
	
	//�����������
	public void getRandName(){
		
		/*
		 * ��������������ʳ���
		 */
		double randNum=Math.random();
		randNum*=10.0;
		int number;
		//���������������ȥ������
		if(randNum<2.5) number=4;
		else if(randNum>=2.5&&randNum<5.0) number=5;
		else if(randNum>=5.0&&randNum<7.5) number=6;
		else number=7;
		
		StringBuffer str=new StringBuffer();
		
		//��������ĸ
		randNum=Math.random();
		randNum*=100.0;
		int index=upperBound(firChar,0,firChar.size()-1,randNum);
		str.append(firChar.elementAt(index).getName().charAt(0));
		int last=str.charAt(0)-'A';
		
		//���ɺ������ĸ
		for(int i=1;i<number;i++)
		{
			randNum=Math.random()*100.0;//�����������������һ����ĸ
			index=upperBound(expert.elementAt(last),0,expert.elementAt(last).size()-1,randNum);
			if(index==-1){i--;continue;}
			str.append(expert.elementAt(last).elementAt(index).getName().charAt(0));
			last=str.charAt(i)-'a';
		}
		name=new String(str);
		
	}
	
	//��������������ֵģ�������һ�����������е�upperBound
	private int upperBound(Vector<keyValue> vec,int head,int tail,double search)
	{
		if(head>head) return -1;
		if(tail-1==head)
		{
			if(search<=vec.elementAt(head).getValue())return head;
			else if(search<=vec.elementAt(tail).getValue()) return tail;
			else return -1;
		}
		else if(tail==head){
			if(search<=vec.elementAt(head).getValue())return head;
			else return -1;
		}
		
		
		if(search>vec.elementAt(tail).getValue())return -1;
		int mid;
		
		//���ֲ���
		while(head<tail-1)
		{
			mid=(head+tail)>>1;
			if(vec.elementAt(mid).getValue()>search)tail=mid;
			else head=mid;
		}
		if(search<=vec.elementAt(head).getValue()) return head;
		else if(search<=vec.elementAt(tail).getValue())return tail;
		else return -1;
		
	}
	
	
	//�����������һ���������ıߣ�����ֵ��nearPoint�е��±�
	private int getRandEdgeNum(){
		Random rand=new Random();
		int temp=rand.nextInt(550);
		if(temp<100) return 0;
		else if(temp<190) return 1;
		else if(temp<270) return 2;
		else if(temp<340) return 3;
		else if(temp<400) return 4;
		else if(temp<450) return 5;
		else if(temp<490) return 6;
		else if(temp<520) return 7;
		else if(temp<540) return 8;
		else return 9;
	}
	
	
	
	
	//num��getter��setter
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	
	//��ȡ�ڽ��ĵ�
	public HashSet<Integer> getNearPoints(){
		return nearPoints;
	}
	
	//��ȡ�߼�
	public Vector<Edge> getEdgeVec(){
		return edgeVec;
	}
	
	
	//��nearPoint���뵽EdgeVec��
	public void addEdge(Vector<Point> totalVec){
		//int size=this.nearPoints.size();
		Vector<Point> temtotal=totalVec;
		HashSet<Integer> tm=this.nearPoints;
		Iterator<Integer> it=tm.iterator();
		while(it.hasNext()){
			int dis=it.next();
			Point tempPoint=temtotal.elementAt(dis);
			double val=Algorithm.getdistance(this, tempPoint);
			double temp=Edge.volumnLengthRatio*val;
			Edge tempEdge=new Edge(this.getNum(),dis,val,(int)(temp));
			edgeVec.add(tempEdge);
			
		}
	}
	
	/////��cachePoint����ĵ㰴���뵱ǰ��ľ����������
	public void distSortMethod(Vector<Point>totalVec,Vector<Point>cache){
		Collections.sort(cache,new distSort(this));
		Random rand=new Random();
		int num=rand.nextInt(3);																							
		num+=3;
		int nearSize=nearPoints.size();
		if(nearSize>=num) return;
		for(int i=0;i<num-nearSize;i++){
			int temp=getRandEdgeNum();
			if(cache.elementAt(temp).equals(this)) {num++;continue;}
			if(nearPoints.contains(cache.elementAt(temp).getNum())) {num++; continue;}//���ظ��ĵ㣬��continue
			nearPoints.add(cache.elementAt(temp).getNum());
			totalVec.elementAt(cache.elementAt(temp).getNum()).getNearPoints().add(getNum());//���������ӵ��ߵ���һ���˵��nearPoints��
		}
	}
	
	
	//�����ȡ��һ���ߣ�����ģ�⳵��ʱһ��������һ���ص�ѡ����һ����
	public int nextEdge()
	{
		int edgeSize=edgeVec.size();
		return random.nextInt(edgeSize);

		/*
		int answer=0;
		double temp=100;
		for(int i=0;i<edgeSize;i++)
		{
			Edge tempEdge=edgeVec.elementAt(i);
			double traflow=tempEdge.getTraflow();
			double volumn=tempEdge.getVolumn();
			if(traflow/volumn<temp)
			{
				temp=traflow/volumn;
				answer=i;
			}
		}
		return answer;
		*/
	}
	
	
}

class keyValue//���ڹ�������
{
	private int value;
	private String name;
	
	public keyValue(String name,int value){
		this.value=value;
		this.name=name;
	}
	
	public int getValue()
	{
		return value;
	}
	public String getName()
	{
		return name;
	}
}




