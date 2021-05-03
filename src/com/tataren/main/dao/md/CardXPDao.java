
package com.tataren.main.dao.md;

import java.util.List;
import java.util.Map;

import com.tataren.main.web.PageBreaker;
import com.tataren.main.web.SortInformation;
import com.tataren.main.dao.AbstractJdbcDao;
import com.tataren.main.domain.user.WebUser;


public class CardXPDao extends AbstractJdbcDao {

	public List findCardXP(String sfzh) {
		String sql = "select * from CardXP where sfzh="+sfzh ;
		return this.query(sql, WebUser.class);
	}

}
