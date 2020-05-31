package net.thumbtack.mybatis.mappers;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import net.thumbtack.mybatis.model.Author;
import net.thumbtack.mybatis.model.Book;


public interface AuthorBookMapper {

	    @Insert("INSERT INTO author_book (authorid, bookid) VALUES " +
	            "( #{author.id}, #{book.id} )" )
	    public Integer insert(@Param("author")Author author, @Param("book")Book book);

	    @Delete("DELETE FROM author_book WHERE authorid = #{author.id} AND bookId = #{book.id}")
		public void deleteAuthorFromBook(@Param("author")Author author, @Param("book")Book book);

	    @Delete("DELETE FROM author_book")
		public void deleteAll();


}
