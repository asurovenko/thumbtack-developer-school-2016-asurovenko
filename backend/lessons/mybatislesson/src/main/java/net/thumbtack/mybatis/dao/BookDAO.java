package net.thumbtack.mybatis.dao;

import net.thumbtack.mybatis.model.Author;
import net.thumbtack.mybatis.model.Book;

import java.util.List;

public interface BookDAO {

	public Book insert(Book book);

	public void deleteAll();

	public Book getById(int id);

	public List<Book> getByAuthor(Author author);

	public Book insertWithAuthors(Book book);

	public Book failingInsertWrongAuthor(Book book);

	public void deleteAuthor(Author author, Book book);

	public void changeTitleAndYear(Book book, String title, int year);

	public void addAuthor(Book book, Author author);

	public void deleteAuthor(Book book, Author author);
}
