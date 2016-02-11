package navigator.UI;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
/*
 * ѡ��ͷ���class���û�����ͨ������Ӳ��ѡ��ͷ��
 */
public class ImageBufferFrame extends JFrame {
	private JFileChooser jfc=new JFileChooser("."); 
	public ImageBufferFrame() throws IOException{
		   FileNameExtensionFilter filter1 = new FileNameExtensionFilter("*.png", "png");//�����ļ���׺��������ѡ��png��ʽ��ͼƬ
	        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("*.jpg", "jpg");//�����ļ���׺��������ѡ��png��ʽ��ͼƬ
	        FileNameExtensionFilter filter3 = new FileNameExtensionFilter("*.jpeg", "jpeg");//�����ļ���׺��������ѡ��png��ʽ��ͼƬ
	      //���ļ���׺������ӵ��ļ�ѡ����
	        jfc.addChoosableFileFilter(filter1);
	        jfc.addChoosableFileFilter(filter2);
	        jfc.addChoosableFileFilter(filter3);
	        jfc.setAcceptAllFileFilterUsed(false);
	        jfc.setFileFilter(filter2);
	            File file = jfc.getSelectedFile();//��ȡѡ���ļ��Ķ���
	            int returnVal = jfc.showOpenDialog(ImageBufferFrame.this);
	            if(returnVal == JFileChooser.APPROVE_OPTION)
	            {
	            String path = jfc.getSelectedFile().getAbsolutePath();//��ȡѡ���ļ��ľ���·��
	            ImageIcon im=new ImageIcon(path);//������·���µ�ͼƬ����
	            im.setImage(im.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));//����ͼƬ�Ĵ�СΪ20*20
	            MainFrame.getMainFrame().UserLogo.setIcon(im);//��UserLogo��������ͼƬ
	        }
	}
}
