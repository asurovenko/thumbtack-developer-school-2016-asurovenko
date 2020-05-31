package net.thumbtack.mybatis;

import net.thumbtack.mybatis.model.Address;
import net.thumbtack.mybatis.model.Author;
import net.thumbtack.mybatis.model.Book;
import org.junit.Test;

import java.sql.Date;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AnnotationsDAOTests extends BaseDAOTests{

	@Test
	public void testInsertAuthor() {
		Author author = new Author("Иван", "Иванов", "Иванович", Date.valueOf("1960-7-1"));
		authorDAO.insert(author);
		Author authFromDB = authorDAO.getById(author.getId());
		checkAuthorFields(author, authFromDB);
	}

	@Test(expected = RuntimeException.class)
	public void testInsertAuthorWithNullName() {
		Author author = new Author(null, "Иванов", "Иванович", Date.valueOf("1960-7-1"));
		authorDAO.insert(author);
	}

	@Test
	public void testChangeAuthorFirstName() {
		Author author = new Author("Иван", "Иванов", "Иванович", Date.valueOf("1960-7-1"));
		authorDAO.insert(author);
		Author auth = authorDAO.getById(author.getId());
		checkAuthorFields(author, auth);
		authorDAO.changeFirstName(author, "Василий");
		auth = authorDAO.getById(author.getId());
		assertEquals("Василий", auth.getFirstName());
	}

	@Test(expected = RuntimeException.class)
	public void testChangeAuthorFirstNameToNull() {
		Author author = new Author("Иван", "Иванов", "Иванович", Date.valueOf("1960-7-1"));
		authorDAO.insert(author);
		Author auth = authorDAO.getById(author.getId());
		checkAuthorFields(author, auth);
		authorDAO.changeFirstName(author, null);
	}

	@Test
	public void testInsertAuthorWithAddresses() {
		Author author = new Author("Иван", "Иванов", "Иванович", Date.valueOf("1960-7-1"));
		authorDAO.insert(author);
		Set<Address> addresses = new HashSet<>();
		addresses.add(new Address("ivanov@mail.ru"));
		addresses.add(new Address("ivanov@gmail.com"));
		for (Address address : addresses) {
			addressDAO.insert(author, address);
		}
		Author authFromDB = authorDAO.getById(author.getId());
		checkAuthorFields(author, authFromDB);
		Set<Address> addressesFromDB = authFromDB.getAddresses();
		checkAuthorAddresses(addresses, addressesFromDB);

	}

	@Test(expected = RuntimeException.class)
	public void testInsertAddressBeforeInsertingAuthor() {
		Author author = new Author("Иван", "Иванов", "Иванович", Date.valueOf("1960-7-1"));
		Set<Address> addresses = new HashSet<>();
		addresses.add(new Address("ivanov@mail.ru"));
		addresses.add(new Address("ivanov@gmail.com"));
		for (Address address : addresses) {
			addressDAO.insert(author, address);
		}
	}

	@Test
	public void testInsertTwoAuthors() {
		Author author1 = new Author("Иван", "Иванов", "Иванович", Date.valueOf("1960-7-1"));
		Author author2 = new Author("Петр", "Петров", "Петрович", Date.valueOf("1980-7-1"));
		authorDAO.insert(author1);
		authorDAO.insert(author2);
		List<Author> authorsFromDB = authorDAO.getAllLazy();
		assertEquals(2, authorsFromDB.size());
		Collections.sort(authorsFromDB, (p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
		checkAuthorFields(author1, authorsFromDB.get(0));
		checkAuthorFields(author2, authorsFromDB.get(1));
	}

	
	
	@Test
	public void testInsertTwoAuthorsWithAddressesGetAllLazy() {
		Author author1 = new Author("Иван", "Иванов", "Иванович", Date.valueOf("1960-7-1"));
		Set<Address> author1Addreses = new HashSet<>();
		author1Addreses.add(new Address("ivanov@mail.ru"));
		author1Addreses.add(new Address("ivanov@gmail.com"));

		Author author2 = new Author("Петр", "Петров", "Петрович", Date.valueOf("1980-7-1"));
		Set<Address> author2Addreses = new HashSet<>();
		author2Addreses.add(new Address("petrov@mail.ru"));
		author2Addreses.add(new Address("petrov@gmail.com"));

		authorDAO.insert(author1);
		for (Address address : author1Addreses) {
			addressDAO.insert(author1, address);
		}
		authorDAO.insert(author2);
		for (Address address : author2Addreses) {
			addressDAO.insert(author2,address);
		}
		List<Author> authorsFromDB = authorDAO.getAllLazy();
		assertEquals(2, authorsFromDB.size());
		Collections.sort(authorsFromDB, (p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
		checkAuthorFields(author1, authorsFromDB.get(0));
		checkAuthorFields(author2, authorsFromDB.get(1));
		checkAuthorAddresses(author1Addreses, authorsFromDB.get(0).getAddresses());
		checkAuthorAddresses(author2Addreses, authorsFromDB.get(1).getAddresses());

	}
	@Test
	public void testInsertAuthorWithBooks() {
		Author author = new Author("Иван", "Иванов", "Иванович", Date.valueOf("1960-7-1"));
		authorDAO.insert(author);
		Book book1 = new Book("C++", 2000, 300, null);
		Book book2 = new Book("Java", 2005, 400, null);
		bookDAO.insert(book1);
		bookDAO.insert(book2);
		authorBookDAO.insert(author, book1);
		authorBookDAO.insert(author, book2);
		Author authorFromDB = authorDAO.getById(author.getId());
		checkAuthorFields(author, authorFromDB);
		Collections.sort(authorFromDB.getBooks(), (p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
		checkBookFields(book1, authorFromDB.getBooks().get(0));
		checkBookFields(book2, authorFromDB.getBooks().get(1));

	}

	@Test
	public void testInsertAuthorWithBooksAndAddresses() {
		Author author = new Author("Иван", "Иванов", "Иванович", Date.valueOf("1960-7-1"));
		authorDAO.insert(author);
		Book book1 = new Book("C++", 2000, 300, null);
		Book book2 = new Book("Java", 2005, 400, null);
		bookDAO.insert(book1);
		bookDAO.insert(book2);
		authorBookDAO.insert(author, book1);
		authorBookDAO.insert(author, book2);
		Set<Address> addresses = new HashSet<>();
		addresses.add(new Address("ivanov@mail.ru"));
		addresses.add(new Address("ivanov@gmail.com"));
		for (Address address : addresses) {
			addressDAO.insert(author, address);
		}
		Author authorFromDB = authorDAO.getById(author.getId());
		checkAuthorFields(author, authorFromDB);
		checkAuthorAddresses( addresses, authorFromDB.getAddresses());
		Collections.sort(authorFromDB.getBooks(), (p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
		checkBookFields(book1, authorFromDB.getBooks().get(0));
		checkBookFields(book2, authorFromDB.getBooks().get(1));
	}
	
	@Test
	public void testRemoveBookFromAuthor() {
		Author author = new Author("Иван", "Иванов", "Иванович", Date.valueOf("1960-7-1"));
		authorDAO.insert(author);
		Book book1 = new Book("C++", 2000, 300, null);
		Book book2 = new Book("Java", 2005, 400, null);
		bookDAO.insert(book1);
		bookDAO.insert(book2);
		authorBookDAO.insert(author, book1);
		authorBookDAO.insert(author, book2);
		Author authorFromDB = authorDAO.getById(author.getId());
		checkAuthorFields(author, authorFromDB);
		Collections.sort(authorFromDB.getBooks(), (p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
		checkBookFields(book1, authorFromDB.getBooks().get(0));
		checkBookFields(book2, authorFromDB.getBooks().get(1));
		bookDAO.deleteAuthor(author, book1);
		Author authorFromDB1 = authorDAO.getById(author.getId());
		checkBookFields(book2, authorFromDB1.getBooks().get(0));
	}

	@Test
	public void testInsertBookTransactionally() {

		Set<Address> author1Addreses = new HashSet<>();
		author1Addreses.add(new Address("ivanov@mail.ru"));
		author1Addreses.add(new Address("ivanov@gmail.com"));
		Author author1 = new Author("Иван", "Иванов", "Иванович", Date.valueOf("1960-7-1"), author1Addreses, null);

		Set<Address> author2Addreses = new HashSet<>();
		author2Addreses.add(new Address("petrov@mail.ru"));
		author2Addreses.add(new Address("petrov@gmail.com"));
		Author author2 = new Author("Петр", "Петров", "Петрович", Date.valueOf("1980-7-1"), author2Addreses, null);

		List<Author> authorList = new ArrayList<>();
		authorList.add(author1);
		authorList.add(author2);
		Book book = new Book("C++", 2000, 300, authorList);

		bookDAO.insertWithAuthors(book);

		Book bookFromDB = bookDAO.getById(book.getId());

		checkBookFields(book, bookFromDB);
		List<Author> authorsFromDB = bookFromDB.getAuthors();

		Collections.sort(authorsFromDB, (p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
		checkAuthorFields(author1, authorsFromDB.get(0));
		checkAuthorFields(author2, authorsFromDB.get(1));

		checkAuthorAddresses(author1Addreses, authorsFromDB.get(0).getAddresses());
		checkAuthorAddresses(author2Addreses, authorsFromDB.get(1).getAddresses());

	}

	@Test
	public void testInsertBookTransactionFails() {

		Set<Address> author1Addreses = new HashSet<>();
		author1Addreses.add(new Address("ivanov@mail.ru"));
		author1Addreses.add(new Address("ivanov@gmail.com"));
		Author author1 = new Author("Иван", "Иванов", "Иванович", Date.valueOf("1960-7-1"), author1Addreses, null);

		Set<Address> author2Addreses = new HashSet<>();
		author2Addreses.add(new Address("petrov@mail.ru"));
		author2Addreses.add(new Address("petrov@gmail.com"));
		Author author2 = new Author("Петр", "Петров", "Петрович", Date.valueOf("1980-7-1"), author2Addreses, null);

		List<Author> authorList = new ArrayList<>();
		authorList.add(author1);
		authorList.add(author2);
		Book book = new Book("C++", 2000, 300, authorList);


		try {
			bookDAO.failingInsertWrongAuthor(book);
		} catch (RuntimeException e) {
		}

		Book bookFromDB = bookDAO.getById(book.getId());
		assertNull(bookFromDB);

	}

	@Test
	public void testDeleteAuthor() {
		Author author = new Author("Иван", "Иванов", "Иванович", Date.valueOf("1960-7-1"));
		authorDAO.insert(author);
		Author authorFromDB = authorDAO.getById(author.getId());
		checkAuthorFields(author, authorFromDB);
		authorDAO.delete(author);
		authorFromDB = authorDAO.getById(author.getId());
		assertNull(authorFromDB);
	}

	@Test
	public void testDeleteAuthorAndAddresses() {
		Author author = new Author("Иван", "Иванов", "Иванович", Date.valueOf("1960-7-1"));
		authorDAO.insert(author);
		Set<Address> addresses = new HashSet<>();
		addresses.add(new Address("ivanov@mail.ru"));
		addresses.add(new Address("ivanov@gmail.com"));
		for (Address address : addresses) {
			addressDAO.insert(author, address);
		}
		Author authorFromDB = authorDAO.getById(author.getId());
		checkAuthorFields(author, authorFromDB);
		authorDAO.delete(author);
		authorFromDB = authorDAO.getById(author.getId());
		assertNull(authorFromDB);
	}


	@Test
	public void testClearDatabaseTransactionally() {
		testInsertBookTransactionally();
		commonDAO.clear();
	}

	@Test
	public void testChangeAuthorFIO() {
		Author author = new Author("Алексей", "Пупкин", "Юрьевич", Date.valueOf("1971-1-10"));
		authorDAO.insert(author);
		Author auth = authorDAO.getById(author.getId());
		checkAuthorFields(author, auth);
		authorDAO.changeFIO(author, "Рома", "Русин", "Олегович");
		auth = authorDAO.getById(author.getId());
		assertEquals("Рома", auth.getFirstName());
		assertEquals("Русин", auth.getLastName());
		assertEquals("Олегович", auth.getPatronymicName());
	}


	@Test
	public void testChangeAuthorBirthDate() {
		Author author = new Author("Алексей", "Пупкин", "Юрьевич", Date.valueOf("1971-1-10"));
		authorDAO.insert(author);
		Author auth = authorDAO.getById(author.getId());
		checkAuthorFields(author, auth);
		authorDAO.changeBirthDate(author, Date.valueOf("2000-3-4"));
		auth = authorDAO.getById(author.getId());
		assertEquals(Date.valueOf("2000-3-4"), auth.getBirthDate());
	}

	@Test
	public void testAddAuthorAddress() {
		Author author = new Author("Алексей", "Пупкин", "Юрьевич", Date.valueOf("1971-1-10"));
		authorDAO.insert(author);
		Author auth = authorDAO.getById(author.getId());
		checkAuthorFields(author, auth);
		Address address = new Address("apupkin@gmail.com");
		authorDAO.addAddress(author, address);
		auth = authorDAO.getById(author.getId());
		assertTrue(auth.getAddresses().contains(address));
	}

	@Test
	public void testDeleteAllAddress() {
		Author author = new Author("Алексей", "Пупкин", "Юрьевич", Date.valueOf("1971-1-10"));
		authorDAO.insert(author);
		Author auth = authorDAO.getById(author.getId());
		checkAuthorFields(author, auth);
		Address address = new Address("apupkin@gmail.com");
		authorDAO.addAddress(author, address);
		auth = authorDAO.getById(author.getId());
		assertTrue(auth.getAddresses().contains(address));
		authorDAO.deleteAllAddress(author);
		auth = authorDAO.getById(author.getId());
		assertTrue(auth.getAddresses().isEmpty());
	}

	@Test
	public void testChangeAddress() {
		Author author = new Author("Алексей", "Пупкин", "Юрьевич", Date.valueOf("1971-1-10"));
		authorDAO.insert(author);
		Author auth = authorDAO.getById(author.getId());
		checkAuthorFields(author, auth);
		Address address = new Address("apupkin@gmail.com");
		authorDAO.addAddress(author, address);
		auth = authorDAO.getById(author.getId());
		assertTrue(auth.getAddresses().contains(address));
		authorDAO.changeAddress(author, address, "pupkin@yandex.ru");
		auth = authorDAO.getById(author.getId());
		assertTrue(auth.getAddresses().contains(new Address("pupkin@yandex.ru")));
	}

	@Test
	public void testChangeTitleAndYearFromBook() {
		Book book = new Book("Java", 1950, 100);
		bookDAO.insert(book);
		Book book1 = bookDAO.getById(book.getId());
		checkBookFields(book, book1);
		bookDAO.changeTitleAndYear(book, "C#", 2011);
		book1 = bookDAO.getById(book.getId());
		assertEquals("C#", book1.getTitle());
		assertEquals(2011, book1.getYear());
	}

	@Test
	public void testAddAuthorFromBook() {
		Book book = new Book("Java", 1950, 100);
		bookDAO.insert(book);
		Book book1 = bookDAO.getById(book.getId());
		checkBookFields(book, book1);
		Author author = new Author("Алексей", "Пупкин", "Юрьевич", Date.valueOf("1971-1-10"));
		authorDAO.insert(author);
		Author author1 = authorDAO.getById(author.getId());
		checkAuthorFields(author, author1);
		bookDAO.addAuthor(book, author);
		book1 = bookDAO.getById(book.getId());
		checkAuthorFields(book1.getAuthors().get(0), author);
	}

	@Test
	public  void testDeleteAuthorFromBook() {
		Book book = new Book("Java", 1950, 100);
		bookDAO.insert(book);
		Book book1 = bookDAO.getById(book.getId());
		checkBookFields(book, book1);
		Author author = new Author("Алексей", "Пупкин", "Юрьевич", Date.valueOf("1971-1-10"));
		authorDAO.insert(author);
		Author author1 = authorDAO.getById(author.getId());
		checkAuthorFields(author, author1);
		bookDAO.addAuthor(book, author);
		book1 = bookDAO.getById(book.getId());
		checkAuthorFields(book1.getAuthors().get(0), author);
		bookDAO.deleteAuthor(book, author);
		book1 = bookDAO.getById(book.getId());
		assertTrue(book1.getAuthors().isEmpty());
	}

	@Test
	public void testGetAuthorsByFirstName() {
		Author author1 = new Author("Алексей", "Пупкин", "Юрьевич", Date.valueOf("1971-1-10"));
		authorDAO.insert(author1);
		Author auth = authorDAO.getById(author1.getId());
		checkAuthorFields(author1, auth);
		Author author2 = new Author("И", "Кошкин", "Олегович", Date.valueOf("1988-10-2"));
		authorDAO.insert(author2);
		auth = authorDAO.getById(author2.getId());
		checkAuthorFields(author2, auth);
		List<Author> authors = authorDAO.getAuthorsByFirstName("И");
		checkAuthorFields(authors.get(0), author2);
	}
}
