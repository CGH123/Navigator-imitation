package navigator.dataStruct;

public class Car
{
	private int startPnt;//�����
	private int destination;//Ŀ�ĵر��
	private int edgeIndex;//��λ������еı߼��ı��
	private Long arrTime;//����Ŀ�ĵص�ʱ��
	
	//���캯��
	public Car(int startPnt,int destination,int edgeIndex,Long arrTime)
	{
		this.destination=destination;
		this.arrTime=arrTime;
		this.setStartPnt(startPnt);
		this.setEdgeIndex(edgeIndex);
	}
	
	//destination��getter��setter
	public int getDestination() {
		return destination;
	}
	public void setDestination(int destination) {
		this.destination = destination;
	}
	
	//arrTime��getter��setter
	public Long getArrTime() {
		return arrTime;
	}
	public void setArrTime(Long arrTime) {
		this.arrTime = arrTime;
	}

	//startPnt��Setter��getter
	public int getStartPnt() {
		return startPnt;
	}

	public void setStartPnt(int startPnt) {
		this.startPnt = startPnt;
	}

	//edgeIndex��getter��setter
	public int getEdgeIndex() {
		return edgeIndex;
	}

	public void setEdgeIndex(int edgeIndex) {
		this.edgeIndex = edgeIndex;
	}
}
