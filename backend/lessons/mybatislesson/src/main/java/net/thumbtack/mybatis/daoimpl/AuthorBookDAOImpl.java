package net.thumbtack.mybatis.daoimpl;


import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thumbtack.mybatis.dao.AuthorBookDAO;
import net.thumbtack.mybatis.model.Author;
import net.thumbtack.mybatis.model.Book;

public class AuthorBookDAOImpl extends BaseDAOImpl implements AuthorBookDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorBookDAOImpl.class);

	@Override
	public void insert(Author author, Book book) {
		LOGGER.debug("DAO Insert author {} book {}", author, book);
        try (SqlSession sqlSession = getSession()) {
			try {
                getAuthorBookMapper(sqlSession).insert(author, book);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't insert author {} book {} {}", author, book, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
		}
	}

	@Override
	public void deleteAll() {
		LOGGER.debug("DAO Delete All author books");
        try (SqlSession sqlSession = getSession()) {
			try {
                getAuthorBookMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't delete all authoor books {}", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
		}
	}
}
