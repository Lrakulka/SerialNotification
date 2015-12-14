package Entities;

import java.io.Serializable;

public class Seria implements Serializable {
	private Integer id;
	private String name;
	private Boolean isWatched;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Seria(String name, String url) {
		super();
		this.url = url;
		this.name = name;
		this.isWatched = false;
	}
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Boolean getIsWatched() {
		return isWatched;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIsWatched(Boolean isWatched) {
		this.isWatched = isWatched;
	}
	@Override
	public String toString() {
		return "   Seria [id=" + id + ", name=" + name + 
				", isWatched=" + isWatched + "]" + System.lineSeparator();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seria other = (Seria) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
