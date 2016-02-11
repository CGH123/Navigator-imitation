package navigator.dataStruct;

/*
 * ���࣬��Ҫ����ģ�⳵��
 */
public class Edge {
	public static final double volumnLengthRatio = 0.5;//��·�����ͳ��ȵı���
	private int from;//���
	private int to;//�յ�
	private double length;//����
	private int traflow;//������
	private int volumn;//����
	static public final double flowConstant=0.1;//����Ԥ�����
	
	//���캯����to�Ǳߵ�Ŀ�ĵأ�length�ǳ��ȣ�volum������
	public Edge(int from,int to,double length,int volumn){
		this.from=from;
		this.to=to;
		this.length=length;
		this.setVolumn(volumn);
	}
	
	//���캯����to�Ǳߵ�Ŀ�ĵأ�length�ǳ��ȣ�volum��������traflow�ǳ�����
	public Edge(int from,int to,double length,int traflow,int volumn){
		this.from=from;
		this.to=to;
		this.length=length;
		this.traflow=traflow;
		this.setVolumn(volumn);
	}
	public Edge(){}
	
	//��������ͨ����·��ʱ�ĺ�����n�ǵ�·������v�ǵ�·����
	static public double F(int n,int v)
	{
		double ratio=(double)n/(double)v;
		if(ratio<0.8) return 1.0;
		else return 1.0+Math.pow(Math.E, ratio);
	}
	
	//��ȡ�ߵ����
	public int getFrom(){
		return from;
	}
	
	//��ȡ�ߵ��յ�
	public int getTo(){
		return to;
	}
	
	
	//���ó�����
	public void setTraflow(int t){
		traflow=t;
	}
	
	
	//��ȡ����
	public int getTraflow(){
		return traflow;
	}
	
	
	//���ñߵĳ���
	public void setLength(double length){
		this.length=length;
	}
	
	
	//��ȡ�ߵĳ���
	public double getLength(){
		return this.length;
	}
	
	
	//��ȡ����
	public int getVolumn() {
		return volumn;
	}
	
	//��������
	public void setVolumn(int volumn) {
		this.volumn = volumn;
	}
	
	//��������һ
	public void minusFlow()
	{
		 traflow-=1;
		 if(traflow<0) traflow=0;
	}
	
	//��������һ
	public void addFlow()
	{
		traflow+=1;
	}
	
	
	//��ȡ�����������ı���
	public double flowVolumnRatio()
	{
		return (double)traflow/(double)volumn;
	}
	
}
