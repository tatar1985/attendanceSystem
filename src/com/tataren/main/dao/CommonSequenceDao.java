package com.tataren.main.dao;

import java.util.List;

import com.tataren.main.error.IllegalDataStateExcpetion;
import com.tataren.main.domain.SequenceGenerator;



public class CommonSequenceDao extends AbstractJdbcDao {

	public boolean existCommonSeq(String key, String helpKey) {
		return getCommonSeq(key, helpKey) != null;
	}

	public SequenceGenerator getCommonSeq(String key, String helpKey) {
		List list;
		if (helpKey == null) {
			String sql = "select * from SequenceGenerator where key=?";
			list = this.query(sql, new Object[] { key }, SequenceGenerator.class);

		} else {
			String sql = "select * from SequenceGenerator where key=? and helpKey=? ";
			list = this.query(sql, new Object[] { key, helpKey }, SequenceGenerator.class);
		}

		if (list.size() == 0) {
			return null;
		} else if (list.size() == 1) {
			return (SequenceGenerator) list.get(0);
		} else {
			throw new IllegalDataStateExcpetion(" duplicated key: " + key + " helpKey: " + helpKey);
		}
	}
}
