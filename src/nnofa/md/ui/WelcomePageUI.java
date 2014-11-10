package nnofa.md.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.BackingStoreException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class WelcomePageUI extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5271535749061437341L;
	JList<String> websiteList;
	JComboBox<String> firstLetterComboBox;
	JComboBox<String> categoryComboBox;
	JTextField freeQueryField;
	JButton firstLetterSearchButton;
	JButton categorySearchButton;
	JButton freeQuerySearchButton;
	JButton getRecentUpdateButton;
	private String[] mangaWebsites = {"tenmanga", "batoto"};
	private String[] firstLetter = new String[27];
	private String[] categories = {"Shounen", "Seinen", "Shoujo", "Comedy", "School Life"};
	
	public WelcomePageUI(){
		initFirstLetter();
		initUI();      
	}
	private void initFirstLetter(){
		firstLetter[0] = "0-9";
		for(int i = 1; i < 27; i++){
			firstLetter[i] = "" + (char)(i+64);
		}
	}
	
	private void initUI(){
		Container pane = getContentPane();
		FlowLayout layout = new FlowLayout(FlowLayout.LEADING);
		pane.setLayout(layout);
				
		initWebsiteList(pane);
		initFirstLetterSearch(pane);
		initCategorySearch(pane);
		initFreeQuerySearch(pane);
		initRecentUpdate(pane);
		setTitle("Manga downloader");
		setSize(800, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);  
	}
	
	private void initWebsiteList(Container pane){
		websiteList = new JList<String>(mangaWebsites);
		websiteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		Dimension d = websiteList.getPreferredSize();
		d.width = 80;
		d.height= 100;
		
		JScrollPane scrollPane = new JScrollPane(websiteList);
		scrollPane.setPreferredSize(d);
		pane.add(scrollPane);
	}
	
	private void initFirstLetterSearch(Container pane){
		firstLetterComboBox = new JComboBox<String>(firstLetter);
		firstLetterComboBox.setSize(100, 100);
		firstLetterSearchButton = new JButton("Search by first letter");
		firstLetterSearchButton.addActionListener(this);
		JPanel panel = new JPanel();
		BoxLayout bxl = new BoxLayout(panel,BoxLayout.Y_AXIS);
		panel.setLayout(bxl);
		panel.add(firstLetterComboBox);
		panel.add(Box.createRigidArea(new Dimension(5,5)));
		panel.add(firstLetterSearchButton);
		pane.add(panel);
	}
	
	private void initCategorySearch(Container pane){
		categoryComboBox = new JComboBox<String>(categories);
		categoryComboBox.setSize(100, 100);
		categorySearchButton = new JButton("Search by category");
		categorySearchButton .addActionListener(this);
		JPanel panel = new JPanel();
		BoxLayout bxl = new BoxLayout(panel,BoxLayout.Y_AXIS);
		panel.setLayout(bxl);
		panel.add(categoryComboBox);
		panel.add(Box.createRigidArea(new Dimension(5,5)));
		panel.add(categorySearchButton);
		pane.add(panel);
	}
	
	private void initFreeQuerySearch(Container pane){
		freeQueryField = new JTextField();
		freeQueryField.setSize(100, 100);
		freeQuerySearchButton = new JButton("Search by free query");
		freeQuerySearchButton .addActionListener(this);
		JPanel panel = new JPanel();
		BoxLayout bxl = new BoxLayout(panel,BoxLayout.Y_AXIS);
		panel.setLayout(bxl);
		panel.add(freeQueryField);
		panel.add(Box.createRigidArea(new Dimension(5,5)));
		panel.add(freeQuerySearchButton);
		pane.add(panel);
	}

	private void initRecentUpdate(Container pane){
		getRecentUpdateButton = new JButton("Get recent update");
		getRecentUpdateButton.addActionListener(this);
		pane.add(getRecentUpdateButton);
	}
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				WelcomePageUI ex = new WelcomePageUI();
				ex.setVisible(true);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(getRecentUpdateButton)){
			RecentUpdateUI ex = new RecentUpdateUI(this);
			ex.setVisible(true);
		}
		else{
			String q;
			ListMangaUI ex;
			int method;
			if(e.getSource().equals(categorySearchButton)){
				q = categoryComboBox.getSelectedItem().toString();
				method = ListMangaUI.SEARCH_BY_CATEGORY;
			}
			else if(e.getSource().equals(firstLetterSearchButton)){
				q = firstLetterComboBox.getSelectedItem().toString();
				method = ListMangaUI.SEARCH_BY_FIRST_LETTER;
			}
			else {
				q = freeQueryField.getText();
				method = ListMangaUI.SEARCH_BY_FREE_QUERY;
				ex = new ListMangaUI(this);
			}
			LoadingUI load = new LoadingUI(LoadingUI.LOADING);
			load.setVisible(true);
			
			this.setVisible(false);
			ex = new ListMangaUI(method, q, this);
			
			ex.setVisible(true);
			load.setVisible(false);
		}
	}
}
