package navigator.background;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Vector;

import javax.swing.JList;

import navigator.UI.MainFrame;
import navigator.UI.historylistframe;

 /*
  * 读取历史记录文件history.txt的类
  */
public class readhistory {
     private Vector<String> list1;//存储不包括用户名的容器
	private Vector<String> list;//存储包含用户名的容器
   public readhistory() throws IOException{
	   list=new Vector<String>();//初始化
	   list1=new Vector<String>();
	   File file = new File("D:\\map\\history.txt");
       BufferedReader br = new BufferedReader(new FileReader(file));//读对象
       String line1,line2,line3,line4;
       //每次读四行，第一行是用户名，第二行是历史记录类型，第三行是第一个地名的下标，第四行是第二个地名的下标（没有情况下读出0）
       while ((line1 = br.readLine()) != null) {//如果文件为空，终止
    	   line2 = br.readLine();
    	   line3=br.readLine();
    	   line4=br.readLine();
    	   if(line1.equals(MainFrame.getMainFrame().UserName.getText())){//如果读到用户名与当前用户名相等时
    	    list.add(line2);
    		list.add(line3);
    		list.add(line4);
    	   }
       }
       int k=list.size();
       //把list中除了用户名外的的内容写入list1
       for(int i=k;i>=3;i-=3){
    	   list1.add(list.elementAt(i-3));
    	   list1.add(list.elementAt(i-2));
    	   list1.add(list.elementAt(i-1));
       }
       new historylistframe(list1); //在historylistframe这个窗口显示读出来的历史记录
       br.close();
   }
}

 