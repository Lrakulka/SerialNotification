package Entities;
import java.io.Serializable;
import java.util.ArrayList;

public class Serial implements Serializable {
	private Integer id;
	private String name;
	private String url;
	private String imagePath;
	private Boolean isWatched;
	private ArrayList<Season> seasons;
	
	public Serial(String name, String url, String imagePath, ArrayList<Season> seasons) {
		super();
		this.name = name;
		this.url = url;
		this.seasons = seasons;
		this.imagePath = imagePath;
		isWatched = false;
	}
	
	public String getUrl() {
		return url;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Boolean getIsWatched() {
		return isWatched;
	}
	public void setIsWatched(Boolean isWatched) {
		this.isWatched = isWatched;
	}
	
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Season> getSeasons() {
		return seasons;
	}
	public void setSeasons(ArrayList<Season> seasons) {
		this.seasons = seasons;
	}
	@Override
	public String toString() {
		return "Serial id=" + id + ", name=" + name + ", url=" + url + ", imagePath=" + imagePath + ", isWatched="
				+ isWatched + ", seasons=" + seasons + System.lineSeparator();
	}
	
}
