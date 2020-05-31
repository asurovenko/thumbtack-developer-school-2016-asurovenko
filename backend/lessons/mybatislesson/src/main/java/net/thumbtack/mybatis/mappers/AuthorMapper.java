package net.thumbtack.mybatis.mappers;

import net.thumbtack.mybatis.model.Address;
import net.thumbtack.mybatis.model.Author;
import net.thumbtack.mybatis.model.Book;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.sql.Date;
import java.util.List;
import java.util.Set;

public interface AuthorMapper {

	@Insert("INSERT INTO authors ( firstname, lastname, patronymic, birthdate) VALUES "
			+ "( #{firstName}, #{lastName}, #{patronymicName}, #{birthDate} )")
	@Options(useGeneratedKeys = true)
	public Integer insert(Author author);

	@Select("SELECT id, firstname, lastname, patronymic as patronymicName, birthdate FROM authors WHERE id = #{id}")
	@Results({ 
			@Result(property = "id", column = "id"),
			@Result(property = "addresses", column = "id", javaType = Set.class, many = @Many(select = "net.thumbtack.mybatis.mappers.AddressMapper.getByAuthor", fetchType = FetchType.LAZY) ),
			@Result(property = "books", column = "id", javaType = List.class, many = @Many(select = "net.thumbtack.mybatis.mappers.BookMapper.getByAuthor", fetchType = FetchType.LAZY) ) })
	public Author getById(int id);

	@Select("SELECT id, firstname, lastname, patronymic as patronymicName, birthdate FROM authors")
	@Results({ 
			@Result(property = "id", column = "id"),
			@Result(property = "addresses", column = "id", javaType = Set.class, many = @Many(select = "net.thumbtack.mybatis.mappers.AddressMapper.getByAuthor", fetchType = FetchType.LAZY) ),
			@Result(property = "books", column = "id", javaType = List.class, many = @Many(select = "net.thumbtack.mybatis.mappers.BookMapper.getByAuthor", fetchType = FetchType.LAZY) ) })
	public List<Author> getAllLazy();

	@Select("SELECT id, firstname, lastname, patronymic as patronymicName, birthdate FROM authors WHERE id IN (SELECT authorid FROM author_book WHERE bookid = #{book.id})")
	@Results({ 
			@Result(property = "id", column = "id"),
			@Result(property = "addresses", column = "id", javaType = Set.class, many = @Many(select = "net.thumbtack.mybatis.mappers.AddressMapper.getByAuthor", fetchType = FetchType.LAZY) ),
			@Result(property = "books", column = "id", javaType = List.class, many = @Many(select = "net.thumbtack.mybatis.mappers.BookMapper.getByAuthor", fetchType = FetchType.LAZY) ) })
	public List<Author> getByBook(Book book);

	@Update("UPDATE authors SET firstName = #{firstName} WHERE id = #{author.id} ")
	public void changeFirstName(@Param("author") Author author, @Param("firstName") String firstName);

	@Delete("DELETE FROM authors WHERE id = #{author.id}")
	public int delete(@Param("author") Author author);

	@Delete("DELETE FROM authors")
	public void deleteAll();

	@Select({ "<script>", 
					"SELECT id, firstname, lastname, patronymic as patronymicName, birthdate FROM authors",
					"<where>" + 
							"<if test='date != null'> birthdate > #{date}", 
							"</if>",
							"<if test='prefix != null'> AND patronymic like #{prefix}", 
							"</if>", 
					"</where>" + 
			"</script>" })
	public List<Author> getAllWithParams(@Param("date") Date date, @Param("prefix") String prefix);

	@Insert({ "<script>", 
			"INSERT INTO authors (firstname, lastname, patronymic, birthdate) VALUES",
				"<foreach item='item' collection='authorList' separator=','>",
					"( #{item.firstName}, #{item.lastName}, #{item.patronymicName}, #{item.birthDate} )", 
				"</foreach>",
			"</script>" })
	// @Options(useGeneratedKeys = true) doesn't work
	public void batchInsert(@Param("authorList") List<Author> authorList);

	@SelectProvider(method = "selectAuthorLike", type = net.thumbtack.mybatis.dao.providers.AuthorDAOProvider.class)
	public List<Author> getUsingSQLBuilder(@Param("firstNameCondition") String firstNameCondition,
			@Param("patronymicCondition") String patronymicCondition, @Param("orderCondition") String orderCondition);

	@Update("UPDATE authors SET firstName = #{firstName}, lastName= #{lastName}, patronymic= #{patronymic} WHERE id = #{author.id} ")
	public void changeFIO(@Param("author") Author author, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("patronymic") String patronymic);

	@Update("UPDATE authors SET birthdate=#{date} WHERE id = #{author.id} ")
	public void changeBirthDate(@Param("author") Author author, @Param("date") Date date);

	@Update("UPDATE author_address set email=#{email} where authorid=#{author.id} and email=#{address.email}")
	public void changeAddress(@Param("author") Author author, @Param("address")Address address, @Param("email") String email);

	@Select("SELECT id, firstname, lastname, patronymic as patronymicName, birthdate FROM authors WHERE firstname=#{name}")
	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "addresses", column = "id", javaType = Set.class, many = @Many(select = "net.thumbtack.mybatis.mappers.AddressMapper.getByAuthor", fetchType = FetchType.LAZY) ),
			@Result(property = "books", column = "id", javaType = List.class, many = @Many(select = "net.thumbtack.mybatis.mappers.BookMapper.getByAuthor", fetchType = FetchType.LAZY) ) })
	public List<Author> getAuthorsByFirstName(@Param("name") String name);

}
