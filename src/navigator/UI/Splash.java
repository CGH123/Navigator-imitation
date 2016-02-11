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
 * ʵ���������˳�ʱ�ĵȴ�����
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
		// ���ý������߿���ʾ
		progressBar.setBorderPainted(true);
		// ���ý�������ǰ��ɫ
		progressBar.setForeground(new Color(0, 210, 40));
		// ���ý������ı���ɫ
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
	 * ���ý��Ȱٷֱȣ����ֵ 560,һ��560��ͼ,��550�ŵ�ʱ�������������
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
	 * �������
	 */

	public void startSplash() {
		//prepareSplash();
		windowSplash.setVisible(true);
		windowSplash.toFront();
	}	

	/**
	 * ����
	 */
	public void stopSplash() {
		Toolkit.getDefaultToolkit().beep();//beepһ������
		progressBar.setString("�������!! "+"     "+"������������");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		windowSplash.dispose();
	}

	/**
	 * ִ�н�������������
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
	// ����
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
