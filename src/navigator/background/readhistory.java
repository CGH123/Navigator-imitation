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
  * ��ȡ��ʷ��¼�ļ�history.txt����
  */
public class readhistory {
     private Vector<String> list1;//�洢�������û���������
	private Vector<String> list;//�洢�����û���������
   public readhistory() throws IOException{
	   list=new Vector<String>();//��ʼ��
	   list1=new Vector<String>();
	   File file = new File("D:\\map\\history.txt");
       BufferedReader br = new BufferedReader(new FileReader(file));//������
       String line1,line2,line3,line4;
       //ÿ�ζ����У���һ�����û������ڶ�������ʷ��¼���ͣ��������ǵ�һ���������±꣬�������ǵڶ����������±꣨û������¶���0��
       while ((line1 = br.readLine()) != null) {//����ļ�Ϊ�գ���ֹ
    	   line2 = br.readLine();
    	   line3=br.readLine();
    	   line4=br.readLine();
    	   if(line1.equals(MainFrame.getMainFrame().UserName.getText())){//��������û����뵱ǰ�û������ʱ
    	    list.add(line2);
    		list.add(line3);
    		list.add(line4);
    	   }
       }
       int k=list.size();
       //��list�г����û�����ĵ�����д��list1
       for(int i=k;i>=3;i-=3){
    	   list1.add(list.elementAt(i-3));
    	   list1.add(list.elementAt(i-2));
    	   list1.add(list.elementAt(i-1));
       }
       new historylistframe(list1); //��historylistframe���������ʾ����������ʷ��¼
       br.close();
   }
}

 