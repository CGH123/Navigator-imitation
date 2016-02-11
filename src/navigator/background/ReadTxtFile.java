package navigator.background;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import navigator.UI.MainFrame;

 /*
  * 读取文件Secret.txt的类
  */
public class ReadTxtFile {
	//判断输入账号密码是否正确
   public Boolean Read() throws IOException{
	   File file = new File("D:\\map\\Secret.txt");//文件路径
       BufferedReader br = new BufferedReader(new FileReader(file));//创建读对象
       String line1,line2;
       //每次读两行，第一行是用户名，第二行是密码
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
   //判断用户名是否已经存在
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
   //修改密码
public Boolean ReturnPassword() throws Exception{
	File file = new File("D:\\map\\Secret.txt");

    BufferedReader br = new BufferedReader(new FileReader(file));
    String line1,line2;
    FileWriter Writer = new FileWriter("D:\\map\\Secret.txt",true);//创建写对象
    //每次读两行，第一行用户名，第二行密码
    while ((line1 = br.readLine()) != null) {
 	   line2 = br.readLine();
 	   if(line2.equals(MainFrame.CPF.f1.getText())){//当用户名与当前用户名相同时，把原密码删除，更换新密码
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
//删除原密码，更换为新密码的函数
public  void  delete(String PastWord,String ChangeWord) throws Exception {
		 File file = new File("D:\\map\\Secret.txt");
	     BufferedReader br = new BufferedReader(new FileReader(file));//读对象
	    String s1 = null,s2=null;
	    String file1 = "";//用来存放更换密码后txt里面的内容
	    s1 = br.readLine();//读一行
	    while (s1 != null) {
	    	 s2 = br.readLine();//第二行
	        if (s1.equals(MainFrame.log.f1.getText())&&s2.equals(PastWord)) {//当用户名与当前用户名相同时，写入用户名和更换的密码
	                file1 +=(s1+"\r\n");
	                file1 +=(ChangeWord+"\r\n");
	        }
	        else {//当用户名与当前用户名不相同时，写入用户名和原密码
	        	file1 +=(s1+"\r\n");
                file1 +=(ChangeWord+"\r\n");
                }
            s1 = br.readLine();
	    }
	    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	    bw.write(file1);//把file的内容写入原txt，覆盖原来的内容，做到更换密码
	    bw.close();
	    br.close();
	}
}

 