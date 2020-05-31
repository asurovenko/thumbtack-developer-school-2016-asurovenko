package net.thumbtack.mybatis;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import net.thumbtack.mybatis.model.Address;
import net.thumbtack.mybatis.model.Author;

public class XmlDAOTests extends BaseDAOTests {

	@Test
	public void testInsertTwoAuthorWithAddressesGetAllJoin() {
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
			addressDAO.insert(author2, address);
		}
		List<Author> authorsFromDB = authorDAO.getAllUsingJoin();
		assertEquals(2, authorsFromDB.size());
		Collections.sort(authorsFromDB, (p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
		checkAuthorFields(author1, authorsFromDB.get(0));
		checkAuthorFields(author2, authorsFromDB.get(1));
		checkAuthorAddresses(author1Addreses, authorsFromDB.get(0).getAddresses());
		checkAuthorAddresses(author2Addreses, authorsFromDB.get(1).getAddresses());

	}

}
