package net.thumbtack.mybatis.dao;

import net.thumbtack.mybatis.model.Address;
import net.thumbtack.mybatis.model.Author;

public interface AddressDAO {

	public Address insert(Author author, Address address);

	public void deleteAll();

}