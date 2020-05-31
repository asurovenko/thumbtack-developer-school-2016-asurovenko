package net.thumbtack.mybatis.dao;

import net.thumbtack.mybatis.model.Author;
import net.thumbtack.mybatis.model.Book;

public interface AuthorBookDAO {

	public void insert(Author author, Book book);

	public void deleteAll();

}