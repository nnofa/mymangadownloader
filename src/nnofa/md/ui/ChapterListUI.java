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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import nnofa.md.Chapter;
import nnofa.md.Manga;
import nnofa.md.tenmanga.TenmangaMangaLister;

public class ChapterListUI extends JFrame implements ActionListener, ListSelectionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2898637288444906696L;
	JScrollPane startChScrollPane;
	JScrollPane stopChScrollPane;
	JList<String> startChList;
	JList<String> stopChList;
	JButton downloadButton;
	JButton backToPrevPageButton;
	
	Chapter[] chArray;
	private Manga manga;
	private TenmangaMangaLister tml;
	private ListMangaUI prevPage;
	public ChapterListUI(Manga manga, ListMangaUI prevPage){
		this.manga = manga;
		this.prevPage = prevPage;
		tml = new TenmangaMangaLister();
		initUI();      
	}
	
	private void initUI(){
		Container pane = getContentPane();
		FlowLayout layout = new FlowLayout();
		pane.setLayout(layout);
		
		if(manga == null){
			manga = new Manga("One Piece", "http://www.tenmanga.com/book/One+Piece.html", null);
		}
		
		List<Chapter> chList = tml.getListChapter(manga);
		
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
		
		startChList = new JList<String>(chStringArray);
		startChList.addListSelectionListener(this);
		startChScrollPane = new JScrollPane(startChList);
		stopChList = new JList<String>(chStringArray);
		stopChScrollPane = new JScrollPane(stopChList);
		
		Dimension d = new Dimension();
		d.width = 200;
		d.height= 300;
		
		startChScrollPane.setPreferredSize(d);
		stopChScrollPane.setPreferredSize(d);
		
		downloadButton = new JButton("Download");
		downloadButton.addActionListener(this);
		
		backToPrevPageButton = new JButton("Back to prev page");
		backToPrevPageButton.addActionListener(this);
		
		pane.add(startChScrollPane);
		pane.add(stopChScrollPane);
		pane.add(downloadButton);
		pane.add(backToPrevPageButton);
		
		setTitle("Simple example");
		setSize(800, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		if(prevPage != null)
			prevPage.setVisible(false);
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				ChapterListUI ex = new ChapterListUI(null, null);
				ex.setVisible(true);
			}
		});
	}

	public void actionPerformed(ActionEvent src) {
		// TODO Auto-generated method stub
		if(src.getSource().equals(downloadButton)){
			if(chArray == null){
				JOptionPane.showMessageDialog(null, "Unable to download, click refresh");
			}
			int index1 = startChList.getSelectedIndex();
			int index2 = stopChList.getSelectedIndex();
			int min, max;
			if(index1 > index2){
				min = index2; max = index1;
			}
			else{
				min = index1; max = index2;
			}
			for(int i = max;i >= min; i--){
				tml.downloadChapter(manga.getTitle(), chArray[i]);
			}
			JOptionPane.showMessageDialog(null, "Your download has finished!");
		}
		else if(src.getSource().equals(backToPrevPageButton)){
			this.dispose();
			prevPage.setVisible(true);
		}
	}

	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		if(stopChList.getSelectedIndex() == -1){
			stopChList.setSelectedIndex(startChList.getSelectedIndex());
		}
	}
}
