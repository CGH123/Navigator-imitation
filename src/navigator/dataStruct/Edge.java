package navigator.dataStruct;

/*
 * 边类，主要用于模拟车流
 */
public class Edge {
	public static final double volumnLengthRatio = 0.5;//道路容量和长度的比率
	private int from;//起点
	private int to;//终点
	private double length;//长度
	private int traflow;//车流量
	private int volumn;//容量
	static public final double flowConstant=0.1;//车流预设参数
	
	//构造函数，to是边的目的地，length是长度，volum是容量
	public Edge(int from,int to,double length,int volumn){
		this.from=from;
		this.to=to;
		this.length=length;
		this.setVolumn(volumn);
	}
	
	//构造函数，to是边的目的地，length是长度，volum是容量，traflow是车流量
	public Edge(int from,int to,double length,int traflow,int volumn){
		this.from=from;
		this.to=to;
		this.length=length;
		this.traflow=traflow;
		this.setVolumn(volumn);
	}
	public Edge(){}
	
	//计算汽车通过道路用时的函数，n是道路流量，v是道路容量
	static public double F(int n,int v)
	{
		double ratio=(double)n/(double)v;
		if(ratio<0.8) return 1.0;
		else return 1.0+Math.pow(Math.E, ratio);
	}
	
	//获取边的起点
	public int getFrom(){
		return from;
	}
	
	//获取边的终点
	public int getTo(){
		return to;
	}
	
	
	//设置车流量
	public void setTraflow(int t){
		traflow=t;
	}
	
	
	//获取车流
	public int getTraflow(){
		return traflow;
	}
	
	
	//设置边的长度
	public void setLength(double length){
		this.length=length;
	}
	
	
	//获取边的长度
	public double getLength(){
		return this.length;
	}
	
	
	//获取容量
	public int getVolumn() {
		return volumn;
	}
	
	//设置容量
	public void setVolumn(int volumn) {
		this.volumn = volumn;
	}
	
	//车流量减一
	public void minusFlow()
	{
		 traflow-=1;
		 if(traflow<0) traflow=0;
	}
	
	//车流量加一
	public void addFlow()
	{
		traflow+=1;
	}
	
	
	//获取流量、容量的比率
	public double flowVolumnRatio()
	{
		return (double)traflow/(double)volumn;
	}
	
}
