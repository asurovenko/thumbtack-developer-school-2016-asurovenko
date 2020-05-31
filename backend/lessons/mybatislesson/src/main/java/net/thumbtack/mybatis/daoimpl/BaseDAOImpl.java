package net.thumbtack.mybatis.daoimpl;

import org.apache.ibatis.session.SqlSession;

import net.thumbtack.mybatis.mappers.AddressMapper;
import net.thumbtack.mybatis.mappers.AuthorBookMapper;
import net.thumbtack.mybatis.mappers.AuthorMapper;
import net.thumbtack.mybatis.mappers.BookMapper;
import net.thumbtack.mybatis.utils.MyBatisUtils;

public class BaseDAOImpl {

	protected SqlSession getSession() {
		return MyBatisUtils.getSqlSessionFactory().openSession();
	}

	protected AuthorMapper getAuthorMapper(SqlSession sqlSession) {
		return sqlSession.getMapper(AuthorMapper.class);
	}

	protected BookMapper getBookMapper(SqlSession sqlSession) {
		return sqlSession.getMapper(BookMapper.class);
	}

	protected AuthorBookMapper getAuthorBookMapper(SqlSession sqlSession) {
		return sqlSession.getMapper(AuthorBookMapper.class);
	}

	protected AddressMapper getAddressMapper(SqlSession sqlSession) {
		return sqlSession.getMapper(AddressMapper.class);
	}


}