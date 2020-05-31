package net.thumbtack.asurovenko;

public class Book {
    private int id;

    public Book(int id, String title, int year, int pages, String publishing_house, int binding) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.pages = pages;
        this.publishing_house = publishing_house;
        this.binding = binding;
    }

    private String title;
    private int year;
    private int pages;
    private String publishing_house;
    private int binding;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getPublishing_house() {
        return publishing_house;
    }

    public void setPublishing_house(String publishing_house) {
        this.publishing_house = publishing_house;
    }

    public int getBinding() {
        return binding;
    }

    public void setBinding(int binding) {
        this.binding = binding;
    }

    public String toString() {
        return new StringBuilder("Book(title=")
                .append(title)
                .append(", year=")
                .append(year)
                .append(", pages=")
                .append(pages)
                .append(", publishing_house=")
                .append(publishing_house)
                .append(", binding=")
                .append(binding)
                .append(")")
                .toString();
    }
}
