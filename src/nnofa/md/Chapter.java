package nnofa.md;

public class Chapter {
	private String title;
	private String link;
	public Chapter(){}
	
	public Chapter(String title, String link){
		this.title = title;
		this.link = link;
	}
	
	public void setTitle(String title){this.title = title;}
	public void setLink(String link){this.link = link;}
	
	public String getTitle(){return title;}
	public String getLink(){return link;}
}
