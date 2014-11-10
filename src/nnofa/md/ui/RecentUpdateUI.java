package nnofa.md.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import nnofa.md.Chapter;
import nnofa.md.Manga;
import nnofa.md.tenmanga.TenmangaMangaLister;

public class RecentUpdateUI extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2898637288444906696L;
	JScrollPane chScrollPane;
	JList<String> chJList;
	JButton downloadButton;
	JButton backToPrevPageButton;
	
	Chapter[] chArray;
	private TenmangaMangaLister tml;
	private WelcomePageUI prevPage;
	public RecentUpdateUI(WelcomePageUI prevPage){
		this.prevPage = prevPage;
		tml = new TenmangaMangaLister();
		initUI();      
	}
	
	private void initUI(){
		Container pane = getContentPane();
		FlowLayout layout = new FlowLayout();
		pane.setLayout(layout);
		
		List<Chapter> chList = tml.getRecentUpdates();
		
		String[] chStringArray;
		if(chList != null){
			chArray = chList.toArray(new Chapter[chList.size()]);
			chStringArray = new String[chArray.length];
			int i = 0;
			for(Chapter ch : chArray){
				chStringArray[i] = ch.getTitle();
				i++;
			}
		}
		else{
			chStringArray = new String[1];
			chStringArray[0] = "unable to load";
 		}
		
		chJList = new JList<String>(chStringArray);
		chScrollPane = new JScrollPane(chJList);
		
		Dimension d = new Dimension();
		d.width = 200;
		d.height= 300;
		
		chScrollPane.setPreferredSize(d);
		downloadButton = new JButton("Download");
		downloadButton.addActionListener(this);
		
		backToPrevPageButton = new JButton("Back to prev page");
		backToPrevPageButton.addActionListener(this);
		
		pane.add(chScrollPane);
		pane.add(downloadButton);
		pane.add(backToPrevPageButton);
		
		setTitle("Simple example");
		setSize(800, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		if(prevPage!= null)
			prevPage.setVisible(false);
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				RecentUpdateUI ex = new RecentUpdateUI(null);
				ex.setVisible(true);
			}
		});
	}

	public void actionPerformed(ActionEvent src) {
		// TODO Auto-generated method stub
		if(src.getSource().equals(downloadButton)){
			if(chArray == null){
				JOptionPane.showMessageDialog(null, "Unable to download, click refresh");
				return;
			}
			int index1 = chJList.getSelectedIndex();
			if(index1 == -1){
				JOptionPane.showMessageDialog(null, "You haven't selected which chapter you want to download");
				return;
			}
			String title = "Recent Updates";
			tml.downloadChapter(title, chArray[index1]);
			JOptionPane.showMessageDialog(null, "Your download has finished!");
		}
		else if(src.getSource().equals(backToPrevPageButton)){
			if(prevPage!=null){
				this.dispose();
				prevPage.setVisible(true);
			}
		}
	}

}

