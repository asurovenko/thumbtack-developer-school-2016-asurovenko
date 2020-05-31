package net.thumbtack.mybatis;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import net.thumbtack.mybatis.model.Author;

public class DynamicSQLTests extends BaseDAOTests {
	
	@Test
	public void testIfCondition() {
		Author author1 = new Author("Иван", "Иванов", "Иванович", Date.valueOf("1985-7-1"));
		Author author2 = new Author("Петр", "Петров", "Петрович", Date.valueOf("1980-7-1"));
		Author author3 = new Author("Петр", "Семенов", "Петрович", Date.valueOf("1986-7-1"));

		authorDAO.insert(author1);
		authorDAO.insert(author2);
		authorDAO.insert(author3);

		List<Author> list = authorDAO.getAllLazy();
		assertEquals(3, list.size());
		
		List<Author> authorsFromDB1 = authorDAO.getAllWithParams(Date.valueOf("1985-1-1"), "Пет%");
		assertEquals(1, authorsFromDB1.size());
		checkAuthorFields(author3, authorsFromDB1.get(0));
	
		List<Author> authorsFromDB2 = authorDAO.getAllWithParams(Date.valueOf("1985-1-1"), null);
		assertEquals(2, authorsFromDB2.size());

		List<Author> authorsFromDB3 = authorDAO.getAllWithParams(null, null);
		assertEquals(3, authorsFromDB3.size());
	
	}

	@Test
	public void testBatchInsertAuthors() {
		Author author1 = new Author("Иван", "Иванов", "Иванович", Date.valueOf("1960-7-1"));
		Author author2 = new Author("Петр", "Петров", "Петрович", Date.valueOf("1970-7-1"));
		Author author3 = new Author("Сидор", "Сидоров", "Сидорович", Date.valueOf("1980-7-1"));
		List<Author> authorList = new ArrayList<>();
		authorList.addAll(Arrays.asList(author1, author2, author3));
		authorDAO.batchInsert(authorList);
		List<Author> authorsFromDB = authorDAO.getAllLazy();
		assertEquals(3,  authorsFromDB.size());
		Collections.sort(authorsFromDB, (p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
		checkAuthorFields(author1, authorsFromDB.get(0));
		checkAuthorFields(author2, authorsFromDB.get(1));
		checkAuthorFields(author3, authorsFromDB.get(2));
		
	}
	@Test
	public void testUsingSQLBuilder() {
		Author author1 = new Author("Иван", "Иванов", "Иванович", Date.valueOf("1985-7-1"));
		Author author2 = new Author("Петр", "Петров", "Петрович", Date.valueOf("1980-7-1"));
		Author author3 = new Author("Петр", "Семенов", "Петрович", Date.valueOf("1986-7-1"));
		Author author4 = new Author("Николай", "Петяев", "Петрович", Date.valueOf("1986-7-1"));

		authorDAO.insert(author1);
		authorDAO.insert(author2);
		authorDAO.insert(author3);
		authorDAO.insert(author4);
		
		Map<String, String> map1 = new HashMap<>();
		map1.put("firstName", "Пет%");
		map1.put("patronymic", "Пет%");

		List<Author> authorsFromDB1 = authorDAO.getAllUsingSQLBuilder(map1);
		assertEquals(2, authorsFromDB1.size());
	
		Map<String, String> map2 = new HashMap<>();
		map2.put("patronymic", "Пет%");
		map2.put("order", "asc");

		List<Author> authorsFromDB2 = authorDAO.getAllUsingSQLBuilder(map2);
		assertEquals(3, authorsFromDB2.size());

		List<Author> authorsFromDB3 = authorDAO.getAllUsingSQLBuilder(null);
		assertEquals(4, authorsFromDB3.size());

	}

}
