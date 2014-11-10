package nnofa.md;

public class Manga {
	private String title;
	private String link;
	private String description;
	public Manga(){}
	
	public Manga(String title, String link, String description){
		this.title = title;
		this.link = link;
		this.description = description;
	}
	
	public void setTitle(String title){this.title = title;}
	public void setLink(String link){this.link = link;}
	public void setDesc(String description){this.description= description;}
	
	public String getTitle(){return title;}
	public String getLink(){return link;}
	public String getDesc(){return description;}
}
