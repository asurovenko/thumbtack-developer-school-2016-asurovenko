package net.thumbtack.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class Book {
	private int id;
	private String title;
	private int year;
	private int pages;

	private List<Author> authors;

	public Book(int id, String title, int year, int pages, List<Author> authors) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.pages = pages;
		this.authors = authors;
	}

	public Book(String title, int year, int pages, List<Author> authors) {
		this(0, title, year, pages, authors);
	}

	public Book(String title, int year, int pages) {
		this(0, title, year, pages, new ArrayList<>());
	}

	public Book() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthorBooks(List<Author> authors) {
		this.authors = authors;
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", year=" + year + ", pages=" + pages + ", authorBooks="
				+ authors + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authors == null) ? 0 : authors.hashCode());
		result = prime * result + id;
		result = prime * result + pages;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + year;
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
		Book other = (Book) obj;
		if (authors == null) {
			if (other.authors != null)
				return false;
		} else if (!authors.equals(other.authors))
			return false;
		if (id != other.id)
			return false;
		if (pages != other.pages)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (year != other.year)
			return false;
		return true;
	}
}
