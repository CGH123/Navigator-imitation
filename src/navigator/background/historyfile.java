package navigator.background;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Scanner;

import navigator.UI.MainFrame;
/*
 * ����ʷ��¼д��txt�ļ��е���
 */
public class historyfile {
	private static final String fileName = "D://map//history.txt";//�ļ�·��
	 public historyfile (String str,int str1,int str2) throws IOException{
		  RandomAccessFile randomFile = new RandomAccessFile("D://map//history.txt", "rw");
          long fileLength = randomFile.length();
          randomFile.seek(fileLength);//��ָ���ѵ����
          String name =MainFrame.getMainFrame().UserName.getText(); //��ȡ�û���
    			  randomFile.writeBytes(name+"\r\n");//д���û���
    			  randomFile.writeBytes(str+"\r\n");//д����ʷ��¼����
    			  randomFile.writeBytes(str1+"\r\n");//д���¼�ص���±�
    				  randomFile.writeBytes(str2+"\r\n");//д��ڶ����ص���±꣨û��ʱд��0��
              randomFile.close();
        	  
          
         
	 }
	 
	 
	
}