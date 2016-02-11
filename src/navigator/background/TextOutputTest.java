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
 * 将编辑框内容写入Secret.txt的class
 */
public class TextOutputTest {
	private static final String fileName = "D://map//Secret.txt";//文件路径
	 public TextOutputTest () throws IOException{
		  RandomAccessFile randomFile = new RandomAccessFile("D://map//Secret.txt", "rw");
          long fileLength = randomFile.length();
          randomFile.seek(fileLength);
          String str1 = MainFrame.reg.f1.getText();//获取编辑框的用户名
		  String str2 = MainFrame.reg.f2.getText();//获取编辑款的密码
		  //当用户名和密码不为空时，写入Secret.txt
		  if (str1 != null && str1.length() != 0) {
			  randomFile.writeBytes(str1+"\r\n");
			  }
			  if (str2 != null && str2.length() != 0) {
				  randomFile.writeBytes(str2+"\r\n");
				  }
          randomFile.close();
	 }
	
}