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
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import nnofa.md.Manga;
import nnofa.md.tenmanga.TenmangaMangaLister;

public class ListMangaUI extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6979038150037517214L;
	JScrollPane mangaScrollPane;
	JList<String> mangaListJList;
	JButton submitButton;
	JButton refreshButton;
	JButton backToWelcomePageButton;
	JButton nextPageButton;
	JButton prevPageButton;
	JScrollPane panel;
	
	public static final int SEARCH_BY_FIRST_LETTER = 1;
	public static final int SEARCH_BY_CATEGORY = 2;
	public static final int SEARCH_BY_FREE_QUERY = 3;
	
	private int page;
	private int searchMethod;
	private String query;
	private WelcomePageUI prevPage;
	private HashMap<String, Manga> mangaMap; 
	private TenmangaMangaLister tml;
	
	public ListMangaUI(WelcomePageUI prevPage){
		page = 1;
		this.prevPage = prevPage;
		tml = new TenmangaMangaLister();
		initializeMangaMap();
		initUI();      
	}
	
	public ListMangaUI(int searchMethod, String query, WelcomePageUI prevPage){
		page = 1;
		this.searchMethod = searchMethod;
		this.query = query;
		this.prevPage = prevPage;
		tml = new TenmangaMangaLister();
		initializeMangaMap();
		initUI();
	}
	
	private void initUI(){
		Container pane = getContentPane();
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		pane.setLayout(fl);
		
		System.out.println("init UI");
		String[] mangaStringArray;
		if(mangaMap != null){
			Set<String> mangaSet = mangaMap.keySet();
			mangaStringArray = mangaSet.toArray(new String[mangaSet.size()]);
		}
		else{
			mangaStringArray = new String[1];
			mangaStringArray[0] = "unable to load";
 		}
		Arrays.sort(mangaStringArray);
		mangaListJList = new JList<String>(mangaStringArray);
		panel = new JScrollPane(mangaListJList);
		
		Dimension d = new Dimension();
		d.width = 200;
		d.height= 300;
		panel.setPreferredSize(d);
		
		submitButton = new JButton("Select");
		submitButton.addActionListener(this);
		
		refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(this);
		
		backToWelcomePageButton = new JButton("Back to welcome page");
		backToWelcomePageButton.addActionListener(this);
		
		nextPageButton = new JButton("Next Manga");
		nextPageButton.addActionListener(this);
		
		prevPageButton = new JButton("Previous Manga");
		prevPageButton.setEnabled(false);
		prevPageButton.addActionListener(this);
		
		pane.add(panel);
		pane.add(submitButton);
		pane.add(refreshButton);
		pane.add(backToWelcomePageButton);
		pane.add(nextPageButton);
		pane.add(prevPageButton);
		
		if(prevPage != null){
			prevPage.setVisible(false); // set previous page to be invisible
		}
		
		setTitle("Manga downloader");
		setSize(800, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);  
	}
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				ListMangaUI ex = new ListMangaUI(null);
				ex.setVisible(true);
			}
		});
	}

	public void actionPerformed(ActionEvent arg0) {
		Object src = arg0.getSource();
		if(src.equals(submitButton)){
			if(mangaListJList.getSelectedValue() == null){
				JOptionPane.showMessageDialog(null, "You haven't selected anything!");
				return;
			}
			String selected = mangaListJList.getSelectedValue();
			Manga man = mangaMap.get(selected);
			ChapterListUI ch = new ChapterListUI(man, this);
			ch.setVisible(true);
		}
		else if(src.equals(refreshButton)){
			System.out.println("refresh");
			refresh();
		}
		else if(src.equals(backToWelcomePageButton)){
			this.dispose();
			prevPage.setVisible(true);
		}
		
		else if(src.equals(nextPageButton)){
			page++;
			System.out.println("page = " + page);
			prevPageButton.setEnabled(true);
			refresh();
		}
		else if(src.equals(prevPageButton)){
			page--;
			if(page==1) prevPageButton.setEnabled(false);
			refresh();
		}
		
	}
	
	private void refresh(){
		initializeMangaMap();
		String[] mangaStringArray;
		if(mangaMap != null){
			Set<String> mangaSet = mangaMap.keySet();
			mangaStringArray = mangaSet.toArray(new String[mangaSet.size()]);
		}
		else{
			mangaStringArray = new String[1];
			mangaStringArray[0] = "unable to load";
 		}
		Arrays.sort(mangaStringArray);
		mangaListJList.setListData(mangaStringArray);
		this.setFocusable(true);
	}
	
	private void initializeMangaMap(){
		if(searchMethod == SEARCH_BY_FIRST_LETTER){
			mangaMap = tml.getHashMapMangaByFirstLetter(query, page);
		}
		else if(searchMethod == SEARCH_BY_CATEGORY){
			mangaMap = tml.getHashMapMangaByCategory(query, page);
		}
		else{
			mangaMap = tml.getHashMapMangaByFirstLetter("O", page);
		}
	}
}
