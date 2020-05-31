package net.thumbtack.mybatis.dao.providers;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class AuthorDAOProvider {
	public String selectAuthorLike(Map<String, String> map) {
		return new SQL() {
			{
				SELECT("id, firstname, lastname, patronymic as patronymicName, birthDate");
				FROM("authors a");

				String firstNameCondition = map.get("firstNameCondition");
				if (firstNameCondition != null)
					WHERE("a.firstname like #{firstNameCondition}");

				String patronymicCondition = map.get("patronymicCondition");
				if (patronymicCondition != null)
					WHERE("a.patronymic like #{patronymicCondition}");
				String orderCondition = map.get("orderCondition");
				if (orderCondition != null)
					if (orderCondition.equalsIgnoreCase("asc"))
						ORDER_BY("a.firstname ASC");
					else if (orderCondition.equalsIgnoreCase("desc"))
						ORDER_BY("a.firstname DESC");
			}
		}.toString();
	}

}
