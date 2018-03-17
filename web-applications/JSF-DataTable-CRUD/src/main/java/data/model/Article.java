package data.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "article")
@Table
public class Article extends OrderElement
{
	private static final long serialVersionUID = 1L;

	private String name;
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
}
