package navigator.background;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import navigator.UI.MainFrame;

 /*
  * ��ȡ�ļ�Secret.txt����
  */
public class ReadTxtFile {
	//�ж������˺������Ƿ���ȷ
   public Boolean Read() throws IOException{
	   File file = new File("D:\\map\\Secret.txt");//�ļ�·��
       BufferedReader br = new BufferedReader(new FileReader(file));//����������
       String line1,line2;
       //ÿ�ζ����У���һ�����û������ڶ���������
       while ((line1 = br.readLine()) != null) {
    	   line2 = br.readLine();
    	   if(line1.equals(MainFrame.log.f1.getText())&&line2.equals(MainFrame.log.f2.getText())){
    		   br.close();
    		   return true;
    	   }
       }
       br.close();
       return false;
   }
   //�ж��û����Ƿ��Ѿ�����
   public Boolean ReadUser() throws IOException{
	   File file = new File("D:\\map\\Secret.txt");
       BufferedReader br = new BufferedReader(new FileReader(file));
       String line1,line2;
       while ((line1 = br.readLine()) != null) {
    	   line2 = br.readLine();
    	   if(line1.equals(MainFrame.reg.f1.getText())){
    		   br.close();
    		   return true;
    	   }
       }
       br.close();
       return false;
   }
   //�޸�����
public Boolean ReturnPassword() throws Exception{
	File file = new File("D:\\map\\Secret.txt");

    BufferedReader br = new BufferedReader(new FileReader(file));
    String line1,line2;
    FileWriter Writer = new FileWriter("D:\\map\\Secret.txt",true);//����д����
    //ÿ�ζ����У���һ���û������ڶ�������
    while ((line1 = br.readLine()) != null) {
 	   line2 = br.readLine();
 	   if(line2.equals(MainFrame.CPF.f1.getText())){//���û����뵱ǰ�û�����ͬʱ����ԭ����ɾ��������������
 		  MainFrame.read.delete(line2,MainFrame.CPF.f2.getText());
 		  br.close();
 		  Writer.close();
 		   return true;
 	   }
    }
    br.close();
    Writer.close();
    return false;
}
//ɾ��ԭ���룬����Ϊ������ĺ���
public  void  delete(String PastWord,String ChangeWord) throws Exception {
		 File file = new File("D:\\map\\Secret.txt");
	     BufferedReader br = new BufferedReader(new FileReader(file));//������
	    String s1 = null,s2=null;
	    String file1 = "";//������Ÿ��������txt���������
	    s1 = br.readLine();//��һ��
	    while (s1 != null) {
	    	 s2 = br.readLine();//�ڶ���
	        if (s1.equals(MainFrame.log.f1.getText())&&s2.equals(PastWord)) {//���û����뵱ǰ�û�����ͬʱ��д���û����͸���������
	                file1 +=(s1+"\r\n");
	                file1 +=(ChangeWord+"\r\n");
	        }
	        else {//���û����뵱ǰ�û�������ͬʱ��д���û�����ԭ����
	        	file1 +=(s1+"\r\n");
                file1 +=(ChangeWord+"\r\n");
                }
            s1 = br.readLine();
	    }
	    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	    bw.write(file1);//��file������д��ԭtxt������ԭ�������ݣ�������������
	    bw.close();
	    br.close();
	}
}

 