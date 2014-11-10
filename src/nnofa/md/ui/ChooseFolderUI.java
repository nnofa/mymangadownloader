package nnofa.md.ui;

import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class ChooseFolderUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3083288222685494324L;
	JFileChooser folderChooser;
	
	public ChooseFolderUI(){
		Container pane = getContentPane();
		folderChooser = new JFileChooser();
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		pane.add(folderChooser);
		
		setTitle("Simple example");
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				ChooseFolderUI ex = new ChooseFolderUI();
				ex.setVisible(true);
			}
		});
	}
}
