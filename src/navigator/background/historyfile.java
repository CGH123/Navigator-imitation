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
 * 将历史记录写入txt文件中的类
 */
public class historyfile {
	private static final String fileName = "D://map//history.txt";//文件路径
	 public historyfile (String str,int str1,int str2) throws IOException{
		  RandomAccessFile randomFile = new RandomAccessFile("D://map//history.txt", "rw");
          long fileLength = randomFile.length();
          randomFile.seek(fileLength);//将指针已到最后
          String name =MainFrame.getMainFrame().UserName.getText(); //获取用户名
    			  randomFile.writeBytes(name+"\r\n");//写入用户名
    			  randomFile.writeBytes(str+"\r\n");//写入历史记录类型
    			  randomFile.writeBytes(str1+"\r\n");//写入记录地点的下标
    				  randomFile.writeBytes(str2+"\r\n");//写入第二个地点的下标（没有时写入0）
              randomFile.close();
        	  
          
         
	 }
	 
	 
	
}