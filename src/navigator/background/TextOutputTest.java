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
 * ���༭������д��Secret.txt��class
 */
public class TextOutputTest {
	private static final String fileName = "D://map//Secret.txt";//�ļ�·��
	 public TextOutputTest () throws IOException{
		  RandomAccessFile randomFile = new RandomAccessFile("D://map//Secret.txt", "rw");
          long fileLength = randomFile.length();
          randomFile.seek(fileLength);
          String str1 = MainFrame.reg.f1.getText();//��ȡ�༭����û���
		  String str2 = MainFrame.reg.f2.getText();//��ȡ�༭�������
		  //���û��������벻Ϊ��ʱ��д��Secret.txt
		  if (str1 != null && str1.length() != 0) {
			  randomFile.writeBytes(str1+"\r\n");
			  }
			  if (str2 != null && str2.length() != 0) {
				  randomFile.writeBytes(str2+"\r\n");
				  }
          randomFile.close();
	 }
	
}