package navigator.dataStruct;

public class Car
{
	private int startPnt;//起点编号
	private int destination;//目的地编号
	private int edgeIndex;//边位于起点中的边集的编号
	private Long arrTime;//到达目的地的时间
	
	//构造函数
	public Car(int startPnt,int destination,int edgeIndex,Long arrTime)
	{
		this.destination=destination;
		this.arrTime=arrTime;
		this.setStartPnt(startPnt);
		this.setEdgeIndex(edgeIndex);
	}
	
	//destination的getter和setter
	public int getDestination() {
		return destination;
	}
	public void setDestination(int destination) {
		this.destination = destination;
	}
	
	//arrTime的getter和setter
	public Long getArrTime() {
		return arrTime;
	}
	public void setArrTime(Long arrTime) {
		this.arrTime = arrTime;
	}

	//startPnt的Setter和getter
	public int getStartPnt() {
		return startPnt;
	}

	public void setStartPnt(int startPnt) {
		this.startPnt = startPnt;
	}

	//edgeIndex的getter和setter
	public int getEdgeIndex() {
		return edgeIndex;
	}

	public void setEdgeIndex(int edgeIndex) {
		this.edgeIndex = edgeIndex;
	}
}
