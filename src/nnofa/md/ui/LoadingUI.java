package nnofa.md.ui;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class LoadingUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4825715551278708454L;
	JLabel msgLabel;
	JLabel imgLabel;
	private boolean loading;
	
	public final static boolean LOADING = true;
	public final static boolean DOWNLOADING = false;
	public LoadingUI(boolean loading){
		this.loading = loading;
		initUI();
	}
	private void initUI(){
		Container pane = getContentPane();
		FlowLayout layout = new FlowLayout();
		pane.setLayout(layout);
		if(loading){
			System.out.println("loading...");
			try {
				Icon icon = new ImageIcon("C:\\Users\\nofandy\\workspace\\MangaDownloader\\resources\\load.gif");
		        JLabel imgLabel = new JLabel(icon);
		        pane.add(imgLabel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("finished");
		}
		
		setTitle("Simple example");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				LoadingUI ex = new LoadingUI(LoadingUI.LOADING);
				ex.setVisible(true);
			}
		});
	}
}
