package net.thumbtack.mybatis.mappers;

import net.thumbtack.mybatis.model.Address;
import net.thumbtack.mybatis.model.Author;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

public interface AddressMapper {

	@Insert("INSERT INTO author_address ( authorid, email) VALUES " +
			"( #{author.id}, #{address.email} )")
	public Integer insert(@Param("author") Author author, @Param("address") Address address);

	@Select("SELECT * from author_address WHERE authorid= #{author.id}")
	public Set<Address> getByAuthor(Author author);

	@Delete("DELETE FROM author_address")
	public void deleteAll();

}
