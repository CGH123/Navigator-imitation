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
 * 选择头像的class，用户可以通过访问硬盘选择头像
 */
public class ImageBufferFrame extends JFrame {
	private JFileChooser jfc=new JFileChooser("."); 
	public ImageBufferFrame() throws IOException{
		   FileNameExtensionFilter filter1 = new FileNameExtensionFilter("*.png", "png");//创建文件后缀对象，允许选择png格式的图片
	        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("*.jpg", "jpg");//创建文件后缀对象，允许选择png格式的图片
	        FileNameExtensionFilter filter3 = new FileNameExtensionFilter("*.jpeg", "jpeg");//创建文件后缀对象，允许选择png格式的图片
	      //把文件后缀对象添加到文件选择器
	        jfc.addChoosableFileFilter(filter1);
	        jfc.addChoosableFileFilter(filter2);
	        jfc.addChoosableFileFilter(filter3);
	        jfc.setAcceptAllFileFilterUsed(false);
	        jfc.setFileFilter(filter2);
	            File file = jfc.getSelectedFile();//获取选择文件的对象
	            int returnVal = jfc.showOpenDialog(ImageBufferFrame.this);
	            if(returnVal == JFileChooser.APPROVE_OPTION)
	            {
	            String path = jfc.getSelectedFile().getAbsolutePath();//获取选择文件的绝对路径
	            ImageIcon im=new ImageIcon(path);//创建该路径下的图片对象
	            im.setImage(im.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));//设置图片的大小为20*20
	            MainFrame.getMainFrame().UserLogo.setIcon(im);//给UserLogo对象设置图片
	        }
	}
}
