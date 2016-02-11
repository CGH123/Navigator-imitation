package navigator.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 * 实现启动及退出时的等待界面
 * 
 */
public class Splash {
	private Window windowSplash = null;
	private JProgressBar progressBar;
	private String picPath = "";

	public Splash(String pic) {
		picPath = pic;
		prepareSplash();
	}

	private void prepareSplash() {
		progressBar = new JProgressBar(0, 450);
		progressBar.setStringPainted(true);
		// 设置进度条边框不显示
		progressBar.setBorderPainted(true);
		// 设置进度条的前景色
		progressBar.setForeground(new Color(0, 210, 40));
		// 设置进度条的背景色
		progressBar.setBackground(new Color(188, 190, 194));

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		windowSplash = new Window(null);
		Image image = toolkit.getImage(getClass().getResource(picPath));
		JLabel label = new JLabel(new ImageIcon(image));

		windowSplash.add("Center", label);
		windowSplash.add("South", progressBar);
		Dimension scmSize = toolkit.getScreenSize();
		int imgWidth = image.getWidth(null);
		int imgHeight = image.getHeight(null);
		windowSplash.setLocation(scmSize.width / 2 - (imgWidth / 2),
				scmSize.height / 2 - (imgHeight / 2));
		windowSplash.setSize(imgWidth, imgHeight);
	}

	/**
	 * 设置进度百分比，最大值 560,一共560张图,到550张的时候可以跳出画面
	 * 
	 * @param percent
	 */
	public void setValue(int percent) {
		if (percent < 0 || percent > 450) {
			throw new RuntimeException("percent is invalid.");
		}
		progressBar.setValue(progressBar.getValue()+percent);
	}

	/**
	 * 调用入口
	 */

	public void startSplash() {
		//prepareSplash();
		windowSplash.setVisible(true);
		windowSplash.toFront();
	}	

	/**
	 * 结束
	 */
	public void stopSplash() {
		Toolkit.getDefaultToolkit().beep();//beep一声提醒
		progressBar.setString("加载完成!! "+"     "+"程序正在启动");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		windowSplash.dispose();
	}

	/**
	 * 执行进度条启动函数
	 */
	public void loadingProgram(){
		this.startSplash();
		while(true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(progressBar.getValue()>=450) break;
		}
		this.stopSplash();
	}
	/*
	// 测试
	public static void main(String d[]) {
		Splash s = new Splash("/res/background2.jpg");
		s.startSplash();
		for (int i = 1; i <= 100; i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			s.setValue(i);
		}
		s.stopSplash();
	}
	
	*/
}
