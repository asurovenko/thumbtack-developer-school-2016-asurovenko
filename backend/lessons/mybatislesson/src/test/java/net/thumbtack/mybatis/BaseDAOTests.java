package net.thumbtack.mybatis;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;

import net.thumbtack.mybatis.dao.AddressDAO;
import net.thumbtack.mybatis.dao.AuthorBookDAO;
import net.thumbtack.mybatis.dao.AuthorDAO;
import net.thumbtack.mybatis.dao.BookDAO;
import net.thumbtack.mybatis.dao.CommonDAO;
import net.thumbtack.mybatis.daoimpl.AddressDAOImpl;
import net.thumbtack.mybatis.daoimpl.AuthorBookDAOImpl;
import net.thumbtack.mybatis.daoimpl.AuthorDAOImpl;
import net.thumbtack.mybatis.daoimpl.BookDAOImpl;
import net.thumbtack.mybatis.daoimpl.CommonDAOImpl;
import net.thumbtack.mybatis.model.Address;
import net.thumbtack.mybatis.model.Author;
import net.thumbtack.mybatis.model.Book;
import net.thumbtack.mybatis.utils.MyBatisUtils;

public class BaseDAOTests {
	protected CommonDAO commonDAO = new CommonDAOImpl();
	protected AuthorDAO authorDAO = new AuthorDAOImpl();
	protected AddressDAO addressDAO = new AddressDAOImpl();
	protected AuthorBookDAO authorBookDAO = new AuthorBookDAOImpl();
	protected BookDAO bookDAO = new BookDAOImpl();


	@BeforeClass()
	public static void init() {
		if (!MyBatisUtils.initSqlSessionFactory())
			fail();

	}

	@Before()
	public void clearDatabase() {
		authorDAO.deleteAll();
		bookDAO.deleteAll();
	}


	protected void checkAuthorFields(Author author1, Author author2) {
		assertEquals(author1.getFirstName(), author2.getFirstName());
		assertEquals(author1.getLastName(), author2.getLastName());
		assertEquals(author1.getPatronymicName(), author2.getPatronymicName());
		assertEquals(author1.getBirthDate(), author2.getBirthDate());
	}

	protected void checkBookFields(Book book1, Book book2) {
		assertEquals(book1.getId(), book2.getId());
		assertEquals(book1.getTitle(), book2.getTitle());
		assertEquals(book1.getPages(), book2.getPages());
		assertEquals(book1.getYear(), book2.getYear());

	}

	protected void checkAuthorAddresses(Set<Address> addressesSet1, Set<Address> addressesSet2) {
		assertEquals(addressesSet1, addressesSet2);

	}


}
