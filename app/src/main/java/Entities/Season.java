package Entities;
import java.io.Serializable;
import java.util.ArrayList;

public class Season implements Serializable {
	private Integer id;
	private String name;
	private ArrayList<Seria> serias;
	
	public Season(String name, ArrayList<Seria> serias) {
		super();
		this.name = name;
		this.serias = serias;
	}
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}

	public ArrayList<Seria> getSerias() {
		return serias;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setSerias(ArrayList<Seria> id_sesons) {
		this.serias = id_sesons;
	}
	@Override
	public String toString() {
		return "  Seasons id=" + id + ", name=" + name + 
				", serias=" + serias + System.lineSeparator();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((serias == null) ? 0 : serias.hashCode());
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
		Season other = (Season) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (serias == null) {
			if (other.serias != null)
				return false;
		} else if (!serias.equals(other.serias))
			return false;
		return true;
	}
	
}
