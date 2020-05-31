package net.thumbtack.mybatis.daoimpl;

import net.thumbtack.mybatis.dao.BookDAO;
import net.thumbtack.mybatis.model.Address;
import net.thumbtack.mybatis.model.Author;
import net.thumbtack.mybatis.model.Book;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BookDAOImpl extends BaseDAOImpl implements BookDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorDAOImpl.class);

	@Override
	public Book insert(Book book) {
		LOGGER.debug("DAO Insert Book {}", book);
		try (SqlSession sqlSession = getSession()) {
			try {
				getBookMapper(sqlSession).insert(book);
			} catch (RuntimeException ex) {
				LOGGER.debug("Can't insert Book {} {}", book, ex);
				sqlSession.rollback();
				throw ex;
			}
			sqlSession.commit();
		}
		return book;
	}

	@Override
	public Book getById(int id) {
		try (SqlSession sqlSession = getSession()) {
			return getBookMapper(sqlSession).getById(id);
		}
	}

	@Override
	public List<Book> getByAuthor(Author author) {
		try (SqlSession sqlSession = getSession()) {
			return getBookMapper(sqlSession).getByAuthor(author);
		}

	}

	@Override
	public Book insertWithAuthors(Book book) {
		LOGGER.debug("Transactional DAO Insert {} ", book);
		try (SqlSession sqlSession = getSession()) {
			try {
				getBookMapper(sqlSession).insert(book);
				for (Author author : book.getAuthors()) {
					getAuthorMapper(sqlSession).insert(author);
					getAuthorBookMapper(sqlSession).insert(author, book);
					for (Address address : author.getAddresses()) {
						getAddressMapper(sqlSession).insert(author, address);
					}
				}
			} catch (RuntimeException ex) {
				LOGGER.debug("Can't insert Book {} {} ", book, ex);
				sqlSession.rollback();
				throw ex;
			}
			sqlSession.commit();
		}
		return book;
	}

	@Override
	public Book failingInsertWrongAuthor(Book book) {
		LOGGER.debug("Transactional DAO Insert must fail {} ", book);
		try (SqlSession sqlSession = getSession()) {
			try {
				getBookMapper(sqlSession).insert(book);
				// next line is wrong - author is not saved in DB and has no id not set
				getAuthorBookMapper(sqlSession).insert(new Author(), book);
			} catch (RuntimeException ex) {
				LOGGER.debug("Can't insert Book {} {} ", book, ex);
				sqlSession.rollback();
				throw ex;
			}
			sqlSession.commit();
		}
		return book;
	}

	@Override
	public void deleteAll() {
		LOGGER.debug("DAO Delete All Books {}");
		try (SqlSession sqlSession = getSession()) {
			try {
				getBookMapper(sqlSession).deleteAll();
			} catch (RuntimeException ex) {
				LOGGER.debug("Can't delete all Books {}", ex);
				sqlSession.rollback();
				throw ex;
			}
			sqlSession.commit();
		}
	}

	@Override
	public void deleteAuthor(Author author, Book book) {
		LOGGER.debug("DAO Delete Author {} From Book {}", author, book);
		try (SqlSession sqlSession = getSession()) {
			try {
				getAuthorBookMapper(sqlSession).deleteAuthorFromBook(author, book);
			} catch (RuntimeException ex) {
				LOGGER.debug("Can't delete Author {} From Book {} {} ", author, book, ex);
				sqlSession.rollback();
				throw ex;
			}
			sqlSession.commit();
		}
	}


	@Override
	public void changeTitleAndYear(Book book, String title, int year) {
		try (SqlSession sqlSession = getSession()) {
			try {
				getBookMapper(sqlSession).changeTitleAndYear(book, title, year);
			} catch (RuntimeException ex) {
				LOGGER.debug("Can't change title and year from book {} {} ", book, ex);
				sqlSession.rollback();
				throw ex;
			}
			sqlSession.commit();
		}
	}

	@Override
	public void addAuthor(Book book, Author author) {
		try (SqlSession sqlSession = getSession()) {
			try {
				getAuthorBookMapper(sqlSession).insert(author, book);
			} catch (RuntimeException ex) {
				LOGGER.debug("Can't add author {}", ex);
				sqlSession.rollback();
				throw ex;
			}
			sqlSession.commit();
		}
	}


	@Override
	public void deleteAuthor(Book book, Author author) {
		try (SqlSession sqlSession = getSession()) {
			try {
				getBookMapper(sqlSession).deleteAuthor(book, author);
			} catch (RuntimeException ex) {
				LOGGER.debug("Can't delete author from book {} {}", book, ex);
				sqlSession.rollback();
				throw ex;
			}
			sqlSession.commit();
		}
	}
}
