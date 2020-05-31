package net.thumbtack.mybatis.daoimpl;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thumbtack.mybatis.dao.AddressDAO;
import net.thumbtack.mybatis.model.Address;
import net.thumbtack.mybatis.model.Author;

public class AddressDAOImpl extends BaseDAOImpl implements AddressDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddressDAOImpl.class);

	@Override
	public Address insert(Author author, Address address) {
		LOGGER.debug("DAO Insert Address {}", address);
        try (SqlSession sqlSession = getSession()) {
			try {
                getAddressMapper(sqlSession).insert(author, address);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't insert address {} {}", address, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
		}
		return address;
	}

    @Override
    public void deleteAll() {
        try (SqlSession sqlSession = getSession()) {
            try {
                getAddressMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't delete all address {}", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

}
