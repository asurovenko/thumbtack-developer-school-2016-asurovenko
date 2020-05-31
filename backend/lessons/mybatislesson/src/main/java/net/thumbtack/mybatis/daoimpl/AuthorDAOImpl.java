package net.thumbtack.mybatis.daoimpl;

import net.thumbtack.mybatis.dao.AuthorDAO;
import net.thumbtack.mybatis.model.Address;
import net.thumbtack.mybatis.model.Author;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class AuthorDAOImpl extends BaseDAOImpl implements AuthorDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorDAOImpl.class);

	@Override
	public Author insert(Author author) {
		LOGGER.debug("DAO Insert Author {}", author);
		try (SqlSession sqlSession = getSession()) { // sqlSession.close will be closed automatically
			try {
				getAuthorMapper(sqlSession).insert(author);
			} catch (RuntimeException ex) {
				LOGGER.debug("Can't insert Author {}, {}", author, ex);
				sqlSession.rollback();
				throw ex;
			}
			sqlSession.commit();
		}
		return author;
	}

	@Override
	public Author getById(int id) {
		try (SqlSession sqlSession = getSession()) { 
			return getAuthorMapper(sqlSession).getById(id);
		}
	}

	@Override
	public void changeFirstName(Author author, String firstName) {
		try (SqlSession sqlSession = getSession()) {
			try {
				getAuthorMapper(sqlSession).changeFirstName(author, firstName);
			} catch (RuntimeException ex) {
				LOGGER.debug("Can't change Author First Name {} {} ", author, ex);
				sqlSession.rollback();
				throw ex;
			}
			sqlSession.commit();
		}
	}
	
	
	@Override
	public List<Author> getAllLazy() {
		try (SqlSession sqlSession = getSession()) {
			return getAuthorMapper(sqlSession).getAllLazy();
		}
	}

	@Override
	public List<Author> getAllUsingJoin() {
		try (SqlSession sqlSession = getSession()) {
			return sqlSession.selectList("AuthorMapper.getAllUsingJoin");
		}
	}

	@Override
	public void delete(Author author) {
		try (SqlSession sqlSession = getSession()) {
			try {
				getAuthorMapper(sqlSession).delete(author);
			} catch (RuntimeException ex) {
				LOGGER.debug("Can't delete Author {} {}", author, ex);
				sqlSession.rollback();
				throw ex;
			}
			sqlSession.commit();
		}
	}

	@Override
	public void deleteAll() {
		LOGGER.debug("DAO Delete All Authors {}");
		try (SqlSession sqlSession = getSession()) {
			try {
				getAuthorMapper(sqlSession).deleteAll();
			} catch (RuntimeException ex) {
				LOGGER.debug("Can't delete all Authors {}", ex);
				sqlSession.rollback();
				throw ex;
			}
			sqlSession.commit();
		}
	}


	@Override
	public List<Author> getAllWithParams(Date date, String prefix) {
		try (SqlSession sqlSession = getSession()) {
			return getAuthorMapper(sqlSession).getAllWithParams(date, prefix);
		}
	}


	@Override
	public void batchInsert(List<Author> authorList) {
		try (SqlSession sqlSession = getSession()) {
			try {
				getAuthorMapper(sqlSession).batchInsert(authorList);
			} catch (RuntimeException ex) {
				LOGGER.debug("Can't batch insertt Authors First Name {} {} ", authorList, ex);
				sqlSession.rollback();
				throw ex;
			}
			sqlSession.commit();
		}

	}


	@Override
	public List<Author> getAllUsingSQLBuilder(Map<String, String> map) {
		String firstNameCondition = null;
		String patronymicCondition = null;
		String orderCondition = null;
		if(map != null ) {
			firstNameCondition = map.get("firstName");
			patronymicCondition = map.get("patronymic");
			orderCondition = map.get("order");
		}
		try (SqlSession sqlSession = getSession()) {
			return getAuthorMapper(sqlSession).getUsingSQLBuilder(firstNameCondition, patronymicCondition, orderCondition);
		}
	}

	@Override
	public void changeFIO(Author author, String firstName, String lastName, String patronymic) {
		try (SqlSession sqlSession = getSession()) {
			try {
				getAuthorMapper(sqlSession).changeFIO(author, firstName, lastName, patronymic);
			} catch (RuntimeException ex) {
				LOGGER.debug("Can't change Author FIO {} {} ", author, ex);
				sqlSession.rollback();
				throw ex;
			}
			sqlSession.commit();
		}
	}

	@Override
	public void changeBirthDate(Author author, Date date) {
		try (SqlSession sqlSession = getSession()) {
			try {
				getAuthorMapper(sqlSession).changeBirthDate(author, date);
			} catch (RuntimeException ex) {
				LOGGER.debug("Can't change Author birth date {} {} ", author, ex);
				sqlSession.rollback();
				throw ex;
			}
			sqlSession.commit();
		}
	}

	@Override
	public void addAddress(Author author, Address address) {
		try (SqlSession sqlSession = getSession()) {
			try {
				getAddressMapper(sqlSession).insert(author, address);
			} catch (RuntimeException ex) {
				LOGGER.debug("Can't add Author address {} {} ", author, ex);
				sqlSession.rollback();
				throw ex;
			}
			sqlSession.commit();
		}
	}

    @Override
    public void deleteAllAddress(Author author) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getAddressMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't delete all address from author {} {} ", author, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void changeAddress(Author author, Address address, String email) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getAuthorMapper(sqlSession).changeAddress(author, address, email);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't change address from author {} {} ", author, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

	@Override
	public List<Author> getAuthorsByFirstName(String name) {
		try (SqlSession sqlSession = getSession()) {
			return getAuthorMapper(sqlSession).getAuthorsByFirstName(name);
		}
	}

}
