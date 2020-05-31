package net.thumbtack.mybatis.mappers;

import net.thumbtack.mybatis.model.Author;
import net.thumbtack.mybatis.model.Book;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface BookMapper {

	@Insert("INSERT INTO books ( title, year, pages) VALUES ( #{title}, #{year}, #{pages} )")
	@Options(useGeneratedKeys = true)
	public Integer insert(Book book);

	@Select("SELECT id, title, pages, year FROM books WHERE id = #{id}")
	@Results({ 
			@Result(property = "id", column = "id"),
			@Result(property = "authors", column = "id", javaType = List.class, many = @Many(select = "net.thumbtack.mybatis.mappers.AuthorMapper.getByBook", fetchType = FetchType.LAZY) ) })
	public Book getById(int id);

	@Select("SELECT * FROM books WHERE id IN (SELECT bookid FROM author_book WHERE authorid = #{author.id})")
	@Results({ 
			@Result(property = "id", column = "id"),
			@Result(property = "authors", column = "id", javaType = List.class, many = @Many(select = "net.thumbtack.mybatis.mappers.AuthorMapper.getByBook", fetchType = FetchType.LAZY) ) })
	public List<Book> getByAuthor(Author author);

	@Delete("DELETE FROM books")
	public void deleteAll();

	@Update("UPDATE books set title=#{title}, year=#{year} where id=#{book.id}")
	public void changeTitleAndYear(@Param("book") Book book, @Param("title")String title, @Param("year") int year);

	@Delete("DELETE from author_book where authorid=#{author.id} and bookid=#{book.id}")
	public void deleteAuthor(@Param("book") Book book, @Param("author") Author author);

}
