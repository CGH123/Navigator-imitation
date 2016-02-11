package navigator.background;


public class AutoGraphThread extends Thread{
	public AutoGraph auto;
	@Override 
	public void run(){
		auto=new AutoGraph();
	}


}