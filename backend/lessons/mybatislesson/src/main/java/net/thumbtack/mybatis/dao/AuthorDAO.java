package net.thumbtack.mybatis.dao;

import net.thumbtack.mybatis.model.Address;
import net.thumbtack.mybatis.model.Author;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface AuthorDAO {

	public Author insert(Author author);

	public Author getById(int id);

	public List<Author> getAllLazy();

	public List<Author> getAllUsingJoin();

	public void deleteAll();

	public void delete(Author author);

	public void changeFirstName(Author author, String firstName);

	public void batchInsert(List<Author> authorList);

	public List<Author> getAllWithParams(Date date, String prefix);

	public List<Author> getAllUsingSQLBuilder(Map<String, String> map);

	public void changeFIO(Author author, String firstName, String lastName, String patronymic);

	public void changeBirthDate(Author author, Date date);

	public void addAddress(Author author, Address address);

	public void deleteAllAddress(Author author);

	public void changeAddress(Author author, Address address, String email);

	public List<Author> getAuthorsByFirstName(String name);

}