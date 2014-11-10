package nnofa.md.tenmanga;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import nnofa.md.Chapter;
import nnofa.md.Manga;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class TenmangaMangaLister {
	
	private String downloadFolder = "";
	private String userAgent="Mozilla";
	private int timeoutLimit=10000;
	private int retry = 10;
	
	public static final String WEBSITE = "http://www.tenmanga.com";
	private String categorySearch = WEBSITE + "/category/*.html";
	// e.g. : http://www.tenmanga.com/category/seinen.html
	private String firstLetterSearch = WEBSITE + "/category/*.html";
	// e.g. : http://www.tenmanga.com/category/A.html
	private String wordSearch = "http://search.tenmanga.com/list/?wd=";
	// e.g. : "http://search.tenmanga.com/list/?wd=one" will return one piece, one outs
	
	public HashMap<String, Manga> getHashMapMangaByFirstLetter(String firstLetter, int page){
		int cnt = 0;
		if(page > 1) firstLetter = firstLetter + "_" + page;
		String url = firstLetterSearch.replace("*", firstLetter);
		HashMap<String, Manga> mangaMap = new HashMap<String, Manga>();
		while(cnt < retry){
			try {
				Document doc = Jsoup.connect(url).timeout(timeoutLimit).get();
				Elements intros = doc.getElementsByClass("intro");
				for (Element intro : intros){
					Elements links = intro.getElementsByTag("a");
					Element primaryLink = links.first();
					Element descLink = links.get(2);
					String title = primaryLink.text();
					String href=primaryLink.attr("href");
					String desc = descLink.text();
					mangaMap.put(title, new Manga(title, href, desc));
				}
				return mangaMap;
			} catch (IOException e) {
				// TODO Auto-generated 
				cnt++;
				System.out.println("cnt = " + cnt);
			}
		}
		return null;
	}
	
	public HashMap<String, Manga> getHashMapMangaByCategory(String category, int page){
		if(page > 1) category = category + "_" + page;
		category = category.toLowerCase();
		int cnt = 0;
		String url = categorySearch.replace("*", category);
		HashMap<String, Manga> mangaMap = new HashMap<String, Manga>();
		while(cnt < retry){
			try {
				Document doc = Jsoup.connect(url).timeout(timeoutLimit).get();
				Elements intros = doc.getElementsByClass("intro");
				for (Element intro : intros){
					Elements links = intro.getElementsByTag("a");
					Element primaryLink = links.first();
					Element descLink = links.get(2);
					String title = primaryLink.text();
					String href=primaryLink.attr("href");
					String desc = descLink.text();
					mangaMap.put(title, new Manga(title, href, desc));
				}
				return mangaMap;
			} catch (IOException e) {
				// TODO Auto-generated 
				cnt++;
				System.out.println("cnt = " + cnt);
			}
		}
		return null;
	}
	
	public HashMap<String, Chapter> getHashMapChapter(Manga manga){
		int cnt = 0;
		HashMap<String, Chapter> ret = new HashMap<String, Chapter>();
		while(cnt < retry){
			try {
				Document doc = Jsoup.connect(manga.getLink()).timeout(timeoutLimit).get();
				Element chapterList = doc.getElementsByClass("chapter_list").first();
				Elements tableRows = chapterList.getElementsByTag("tr");
				for(Element tr : tableRows){
					Element td = tr.getElementsByTag("td").first();
					Element a = td.getElementsByTag("a").first();
					if(a== null) continue;
					String href = a.attr("href");
					if(!href.contains("http")){
						href = WEBSITE + href;
					}
					ret.put(a.text(), new Chapter(a.text(), href));
				}
				if(ret.size() == 0){
					// different link if the manga quite abunai
					doc = Jsoup.connect(manga.getLink() + "?waring=1").timeout(timeoutLimit).get();
					chapterList = doc.getElementsByClass("chapter_list").first();
					tableRows = chapterList.getElementsByTag("tr");
					for(Element tr : tableRows){
						Element td = tr.getElementsByTag("td").first();
						Element a = td.getElementsByTag("a").first();
						if(a== null) continue;
						String href = a.attr("href");
						if(!href.contains("http")){
							href = WEBSITE + href;
						}
						ret.put(a.text(), new Chapter(a.text(), href));
					}
				}
				return ret;
			}
			catch (IOException e) {
				cnt++;
				System.out.println("cnt = " + cnt);
			}
		}
		return null;
	}
	
	public List<Chapter> getListChapter(Manga manga){
		int cnt = 0;
		List<Chapter> ret = new ArrayList<Chapter>();
		while(cnt < retry){
			try {
				Document doc = Jsoup.connect(manga.getLink()).timeout(timeoutLimit).get();
				Element chapterList = doc.getElementsByClass("chapter_list").first();
				Elements tableRows = chapterList.getElementsByTag("tr");
				for(Element tr : tableRows){
					Element td = tr.getElementsByTag("td").first();
					Element a = td.getElementsByTag("a").first();
					if(a== null) continue;
					String href = a.attr("href");
					if(!href.contains("http")){
						href = WEBSITE + href;
					}
					ret.add(new Chapter(a.text(), href));
				}
				if(ret.size() == 0){
					// different link if the manga quite abunai
					doc = Jsoup.connect(manga.getLink() + "?waring=1").timeout(timeoutLimit).get();
					chapterList = doc.getElementsByClass("chapter_list").first();
					tableRows = chapterList.getElementsByTag("tr");
					for(Element tr : tableRows){
						Element td = tr.getElementsByTag("td").first();
						Element a = td.getElementsByTag("a").first();
						if(a== null) continue;
						String href = a.attr("href");
						if(!href.contains("http")){
							href = WEBSITE + href;
						}
						ret.add(new Chapter(a.text(), href));
					}
				}
				return ret;
			}
			catch (IOException e) {
				cnt++;
				System.out.println("cnt = " + cnt);
			}
		}
		return null;
	}
	
	public List<Chapter> getRecentUpdates(){
		int cnt = 0;
		List<Chapter> ret = new ArrayList<Chapter>();
		while(cnt < retry){
			try {
				Document doc = Jsoup.connect(WEBSITE).timeout(timeoutLimit).get();
				Element divUpdate = doc.getElementsByClass("newUpdate").first();
				Elements links = divUpdate.getElementsByTag("a");
				for(Element link : links){
					String href = link.attr("href");
					String title = link.attr("title");
					Chapter ch = new Chapter(title, href);
					ret.add(ch);
				}
				return ret;
			}
			catch (IOException e) {
				cnt++;
				System.out.println("cnt = " + cnt);
			}
		}
		return null;
	}
	public String getImageUrlFromPage(String pageUrl){
		int cnt = 0;
		while(cnt < retry){
			try {
				Document doc = Jsoup.connect(pageUrl).timeout(5000)
						.userAgent("Chrome")
						.get();
				Element imgPic = doc.getElementById("comicpic");
				String src = imgPic.attr("src");
				return src;
			}
			catch(Exception e){
				
			}
			cnt++;
		}
		return null;
	}
	
	public void downloadChapter(String mangaTitle, Chapter ch){
		int pageNumber = getNumPage(ch);
		String outputDir = downloadFolder + mangaTitle + "/" + ch.getTitle() + "/";
		boolean success = new File(outputDir).mkdirs();
		if(!success) return;
		String imgUrl = getImageUrlFromPage(ch.getLink());
		int cnt = 0;
		while(!downloadImage(outputDir, imgUrl, 1) && cnt < retry){
			try {
				Thread.sleep(3000);
				cnt++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cnt++;
		}
		for(int i = 2; i <= pageNumber; i++){
			System.out.println(i);
			String appending = "-" + i + ".html";
			String link = ch.getLink();
			String newurl = link.substring(0, link.length()-1) + appending;
			imgUrl = getImageUrlFromPage(newurl);
			if (imgUrl == null) continue;
			cnt = 0;
			while(!downloadImage(outputDir, imgUrl, i) && cnt < retry){
				try {
					Thread.sleep(3000);
					cnt++;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cnt++;
			}
		}
	}
	
	private int getNumPage(Chapter ch){
		System.out.println(ch.getLink());
		int cnt = 0;
		while(cnt < retry){
			try {
				Document doc = Jsoup.connect(ch.getLink()).timeout(timeoutLimit)
						.userAgent("Mozilla")
						.get();
				Element selectPage = doc.getElementById("page");
				Elements whichPage = 
						selectPage.getElementsByTag("option");
				System.out.println("size = " + whichPage.size());
				return whichPage.size();
			}
			catch(Exception e){
				e.printStackTrace();
				cnt++;
			}
		}
		return -1;
	}
	
	public boolean downloadImage(String outputDir, String url, int pageNum){
		try{
			String[] splits= url.split("\\.");
			String ext = splits[splits.length-1];
			String page;
			if (pageNum < 10){
				page = "0" + pageNum;
			}
			else {
				page= "" + pageNum;
			}
			String outputFile = outputDir + page + "." + ext;
			Response resultImage = Jsoup.connect(url).userAgent(userAgent).
					ignoreContentType(true).maxBodySize(3000000).execute();
			FileOutputStream out = new FileOutputStream(new File(outputFile));
			out.write(resultImage.bodyAsBytes());
			out.close();
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
	
	public String getDownloadFolder(){return downloadFolder;}
	public void setDownloadFolder(String downloadFolder){ this.downloadFolder = downloadFolder; }
	
	public static void main(String[] args){
		TenmangaMangaLister tml = new TenmangaMangaLister();
		List<Chapter> chs = tml.getRecentUpdates();
		for(Chapter ch : chs){
			System.out.println(ch.getTitle() + " " + ch.getLink());
		}
	}

}
