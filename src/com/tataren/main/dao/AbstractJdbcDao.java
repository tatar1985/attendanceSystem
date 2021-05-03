package com.tataren.main.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

import org.apache.log4j.Logger;
import org.springframework.core.CollectionFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import com.tataren.main.bean.BeanUtils;
import com.tataren.main.db.QuerySqlBuilder;
import com.tataren.main.db.ValueTypeBuilder;
import com.tataren.main.util.StringUtils;
import com.tataren.main.util.TimeUtils;
import com.tataren.main.web.PageBreaker;
import com.tataren.main.web.SortInformation;
import com.tataren.main.domain.Entity;
import com.tataren.main.domain.TimedEntity;

public abstract class AbstractJdbcDao extends JdbcDaoSupport {

	protected Logger logger = Logger.getLogger(AbstractJdbcDao.class);

	private boolean useFormalQuery = false;

	private static Map Tables = new HashMap();
	


	private static boolean WriteSystemLog = false;

	public class BasicRowMapper implements RowMapper {
		private Class claz;

		public BasicRowMapper(Class claz) {
			this.claz = claz;
		}

		public Object mapRow_(ResultSet rs, int rowNum) throws SQLException {

			//			BasicRowProcessor brp = BasicRowProcessor.instance();
			//			Object bean = brp.toBean(rs, claz);
			//			return bean;
			return null;
		}

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ResultSetMetaData rsm = rs.getMetaData();

			if (Map.class.isAssignableFrom(claz)) {
				Map bean = CollectionFactory.createLinkedCaseInsensitiveMapIfPossible(rsm.getColumnCount());
				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					Object value = null;
					int type = rsm.getColumnType(i);
					if (type == Types.INTEGER) {
						value = new Integer(rs.getInt(i));
					} else if (type == Types.CHAR || type == Types.VARCHAR) {
						value = rs.getString(i);
					} else if (type == Types.SMALLINT) {
						value = new Short(rs.getShort(i));
					} else if (type == Types.FLOAT) {
						value = new Float(rs.getFloat(i));
					} else if (type == Types.DOUBLE) {
						value = new Double(rs.getDouble(i));
					} else if (type == Types.NUMERIC) {
						// value=new Integer(rs.getInt(i));
						value = rs.getObject(i);
					} else if (type == Types.DATE) {
						value = rs.getDate(i);
					} else if (type == Types.TIME) {
						value = rs.getTime(i);
					} else if (type == Types.TIMESTAMP) {
						value = rs.getTimestamp(i);
					}

					String columnName = rsm.getColumnName(i);
					bean.put(columnName, value);
				}
				return bean;
			} else {
				Object bean;
				try {
					bean = claz.newInstance();
				} catch (Exception e) {
					throw new SQLException("bean " + claz + " can not be initialized");
				}
				HashMap valueMap = new HashMap();

				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					Object value = null;
					int type = rsm.getColumnType(i);
					if (type == Types.INTEGER) {
						value = new Integer(rs.getInt(i));
					} else if (type == Types.CHAR || type == Types.VARCHAR) {
						value = rs.getString(i);
					} else if (type == Types.SMALLINT) {
						value = new Short(rs.getShort(i));
					} else if (type == Types.FLOAT) {
						value = new Float(rs.getFloat(i));
					} else if (type == Types.DOUBLE) {
						value = new Double(rs.getDouble(i));
					} else if (type == Types.NUMERIC) {
						// value=new Integer(rs.getInt(i));
						value = rs.getObject(i);
					} else if (type == Types.DATE) {
						value = rs.getDate(i);
					} else if (type == Types.TIME) {
						value = rs.getTime(i);
					} else if (type == Types.TIMESTAMP) {
						value = rs.getTimestamp(i);
					}

					String columnName = rsm.getColumnName(i);
					if (rs.getObject(columnName) == null)
						value = null;
					valueMap.put(rsm.getColumnName(i), value);
					// valueMap.put(rsm.getColumnName(i), rs.getObject(i));
				}
				try {
					BeanUtils.populate(bean, valueMap);
				} catch (Exception e) {
					throw new SQLException("bean " + claz + " can not be populated");
				}
				return bean;
			}
		}
	}

	public void saveOrUpdate(Entity bean) {

		if (bean.isNew()) {
			if (bean instanceof TimedEntity) {
				TimedEntity te = (TimedEntity) bean;
				te.setSystemTime(new Timestamp(System.currentTimeMillis()));
			}
		}
		AbstractEntityHelper aeh = saveOrUpdateEntityHelper(bean);

		logger.info(formalSql(aeh.getSql(), aeh.getValues(), aeh.getTypes()));
		writeSystemLog(aeh);
		getJdbcTemplate().update(aeh.getSql(), aeh.getValues(), aeh.getTypes());
	}

	public int update(String sql) {
		writeSystemLog(sql);
		return getJdbcTemplate().update(sql);
	}

	public int update(String sql, Object[] values) {
		writeSystemLog(sql);
		return getJdbcTemplate().update(sql, values);
	}

	public int update(String sql, Object[] values, int[] types) {
		writeSystemLog(sql, values);
		return getJdbcTemplate().update(sql, values, types);
	}

	public int deleteBean(Class claz, Integer sid) {
		String tableName = getTableName(claz);
		String sql = "delete from " + tableName + " where sid=?";
		writeSystemLog(sql, String.valueOf(sid));
		return getJdbcTemplate().update(sql, new Object[] { sid });
	}

	public int deleteBean(Entity bean) {
		String tableName = bean.getTableName();
		String sql = "delete from " + tableName + " where sid=?";
		writeSystemLog(sql, String.valueOf(bean.getSid()));
		return getJdbcTemplate().update(sql, new Object[] { bean.getSid() });
	}

	protected void writeSystemLog(AbstractEntityHelper aeh) {
		writeSystemLog(aeh.getSql(), aeh.getValues());
	}

	protected void writeSystemLog(String sql, Object[] values) {
		if (WriteSystemLog) {
			String value = "";
			for (int i = 0; i < values.length; i++) {
				value += "        " + values[i];
			}
			writeSystemLog(sql, value);
		}
	}

	protected void writeSystemLog(String sql) {
		writeSystemLog(sql, "");
	}

	protected void writeSystemLog(String sql, String value) {
		if (WriteSystemLog) {
			int limit = 3888;
			// lost some log
			if (value.length() > limit) {
				value = value.substring(0, limit);
			}
			if (sql.length() > limit) {
				sql = sql.substring(0, limit);
			}

			int sid = getJdbcTemplate().queryForInt("select seq_systemLog.nextVal from dual");
			ValueTypeBuilder stb = new ValueTypeBuilder().b(sid).b(sql).b(value);
			getJdbcTemplate().update("insert into systemlog (sid,sql,val) values(?,?,?)", stb.getValues(), stb.getTypes());
		}
	}

	public List findByIds(Class claz, Integer[] sids) {
		String tableName = getTableName(claz);
		//bugs!
		//String sql = QuerySqlBuilder.buildInClause(sids);
		String sidClause = QuerySqlBuilder.buildOrClause("sid", sids.length);

		List list = getJdbcTemplate().query("select * from " + tableName + " where " + sidClause, sids,
				new BasicRowMapper(claz));
		return toRandomAccessList(list);
	}

	public Entity getBean(Class claz, Integer sid) {
		return this.getBean(claz, sid.intValue());
	}

	public Entity getBean(Class claz, int sid) {
		String tableName = getTableName(claz);

		String sql = " select * from " + tableName + " where sid=?";
		List list = getJdbcTemplate().query(sql, new Object[] { new Integer(sid) }, new BasicRowMapper(claz));

		if (list.size() == 0)
			return null;
		else if (list.size() == 1)
			return (Entity) list.get(0);
		else {
			throw new IncorrectResultSizeDataAccessException(" Incorrect result size: expected 1, actual ", 1, list.size());
		}
	}

	public List findByExample(Entity eg) {
		return this.findByExample(eg, null, null);
	}

	public List findByExample(Entity eg, String clause) {
		return this.findByExample(eg, clause, null, null);
	}

	public List findByExample(Entity eg, PageBreaker breaker, SortInformation sortInfo) {
		List list = findByExample(eg, null, breaker, sortInfo);
		return toRandomAccessList(list);
	}

	public List findByExample(Entity eg, String clause, PageBreaker breaker, SortInformation sortInfo) {
		breaker = PageBreaker.maskNull(breaker);

		AbstractEntityHelper aeh = queryEntityHelper(eg);
		String select = aeh.getSql();
		if (clause != null) {
			select += clause;
		}
		if (sortInfo != null) {
			select += sortInfo.getOrderClause();
		}
		Object[] values = aeh.getValues();
		int[] types = aeh.getTypes();

		String count = " select count(*) from (" + select + ")";
		int size = formalQueryForInt(count, values, types);
		breaker.setTotalRowNum(size);

		select = " select * from (select row_.*, rownum rownum_ from (" + select + " order by -sid ) row_  where rownum <= "
				+ breaker.getCurrentPageNum() * breaker.getPageSize() + ") where rownum_ >" + breaker.getFistRowPosition();

		if (logger.isInfoEnabled()) {
			logger.info(select);
		}

		List list = formalQuery(select, values, types, new BasicRowMapper(eg.getClass()));
		return toRandomAccessList(list);
	}

	public List query(String sql, Class claz, PageBreaker breaker, SortInformation sortInfo) {
		return this.query(sql, (Object[]) null, claz, breaker, sortInfo);
	}

	public List query(String sql, Object[] values, Class claz, PageBreaker breaker, SortInformation sortInfo) {
		return this.query(sql, values, (int[]) null, claz, breaker, sortInfo);
	}

	public List query(String sql, Object[] values, int[] types, Class claz, PageBreaker breaker, SortInformation sortInfo) {
		if (logger.isInfoEnabled()) {
			StringBuffer b = new StringBuffer(sql).append("   values: ");
			for (int i = 0; values != null && i < values.length; i++) {
				b.append(values[i]);
				b.append(",");
			}
			logger.info(sql);
		}

		if (sortInfo != null) {
			sql += sortInfo.getOrderClause();
		}
		breaker = PageBreaker.maskNull(breaker);
		String count = " select count(*) from (" + sql + ")";
		int size = formalQueryForInt(count, values, types);
		breaker.setTotalRowNum(size);

		sql = " select * from (select row_.*, rownum rownum_ from (" + sql + " ) row_  where rownum <= "
				+ breaker.getCurrentPageNum() * breaker.getPageSize() + ") where rownum_ >" + breaker.getFistRowPosition();

		List list = formalQuery(sql, values, types, new BasicRowMapper(claz));
		return toRandomAccessList(list);
	}

	public List query(String sql, ValueTypeBuilder builder, Class claz, PageBreaker breaker, SortInformation sortInfo) {
		return query(sql, builder.getValues(), builder.getTypes(), claz, breaker, sortInfo);
	}

	public List query(String sql, Class claz) {
		List list = formalQuery(sql, null, null, new BasicRowMapper(claz));
		return toRandomAccessList(list);
	}

	public List query(String sql, Object[] values, Class claz) {
		List list = formalQuery(sql, values, null, new BasicRowMapper(claz));
		return toRandomAccessList(list);
	}

	public List query(String sql, Object[] values, int[] types, Class claz) {
		if (logger.isInfoEnabled()) {
			StringBuffer b = new StringBuffer();
			for (int i = 0; i < values.length; i++) {
				b.append(values[i]);
				b.append(",");
			}
			logger.info(sql);
			logger.info(b);
		}
		List list = formalQuery(sql, values, types, new BasicRowMapper(claz));
		return toRandomAccessList(list);
	}

	public List query(String sql, ValueTypeBuilder builder, Class claz) {
		return query(sql, builder.getValues(), builder.getTypes(), claz);
	}

	protected AbstractEntityHelper queryEntityHelper(Entity bean) {
		AbstractEntityHelper aeh = new SimpleQueryEntityHelper(bean);
		return doCreateEntityHelper(bean, aeh);
	}

	protected AbstractEntityHelper saveOrUpdateEntityHelper(Entity bean) {
		AbstractEntityHelper aeh;
		if (bean.isNew()) {
			int sid = this.getJdbcTemplate().queryForInt("select seq_" + bean.getTableName() + ".nextVal from dual ");
			bean.setSid(sid);
			aeh = new CreatationEntityHelper(bean);
		} else {
			aeh = new UpdateEntityHelper(bean);
		}
		return doCreateEntityHelper(bean, aeh);
	}

	protected AbstractEntityHelper UpdateEntityHelperTYG(Entity bean) {
		AbstractEntityHelper aeh;
	
		aeh = new UpdateEntityHelper(bean);
		
		return doCreateEntityHelper(bean, aeh);
	}
	
	private List formalQuery(String sql, Object[] values, int[] types, RowMapper mapper) {
		
	
		if (useFormalQuery) {
			sql = formalSql(sql, values, types);
			logger.info(sql);
			return getJdbcTemplate().query(sql, mapper);
		} else {
			if (types != null) {
				return getJdbcTemplate().query(sql, values, types, mapper);
			} else {
				if ((values != null)) {
					return getJdbcTemplate().query(sql, values, mapper);
				} else {
					return getJdbcTemplate().query(sql, mapper);
				}
			}
		}
		
	}

	private int formalQueryForInt(String sql, Object[] values, int[] types) {
		if (useFormalQuery) {
			sql = formalSql(sql, values, types);
			logger.info(sql);
			return getJdbcTemplate().queryForInt(sql);
		} else {
			if (types != null) {
				return getJdbcTemplate().queryForInt(sql, values, types);
			} else {
				if ((values != null)) {
					return getJdbcTemplate().queryForInt(sql, values);
				} else {
					return getJdbcTemplate().queryForInt(sql);
				}
			}
		}
	}

	private String formalSql(String sql, Object[] values, int[] types) {
		if (!useFormalQuery) {
			return sql;
		}

		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				if (values[i] != null) {
					if (types != null) {
						if (types[i] == Types.BOOLEAN) {
							Boolean v = (Boolean) values[i];
							if (v.booleanValue())
								sql = sql.replaceFirst("\\?", "1");
							else
								sql = sql.replaceFirst("\\?", "0");
						} else if (types[i] == Types.DATE) {
							String date = TimeUtils.date2String((Date) values[i], "yyyy-MM-dd HH:mm");
							sql = sql.replaceFirst("\\?", " to_date('" + date + "', 'YYYY-MM-DD HH24:MI')");
						} else if (types[i] == Types.TIMESTAMP) {
							String date = TimeUtils.date2String((Date) values[i], "yyyy-MM-dd HH:mm");
							sql = sql.replaceFirst("\\?", " to_date('" + date + "', 'YYYY-MM-DD HH24:MI')");
						} else
							sql = sql.replaceFirst("\\?", "'" + StringUtils.quoteReplacement(values[i].toString()) + "'");
					} else {
						if (values[i] instanceof Boolean) {
							Boolean v = (Boolean) values[i];
							if (v.booleanValue())
								sql = sql.replaceFirst("\\?", "1");
							else
								sql = sql.replaceFirst("\\?", "0");

						} else if (values[i] instanceof Date) {
							String date = TimeUtils.date2String((Date) values[i], "yyyy-MM-dd HH:mm");
							sql = sql.replaceFirst("\\?", " to_date('" + date + "', 'YYYY-MM-DD HH24:MI')");
						} else if (values[i] instanceof Timestamp) {
							String date = TimeUtils.date2String((Date) values[i], "yyyy-MM-dd HH:mm");
							sql = sql.replaceFirst("\\?", " to_date('" + date + "', 'YYYY-MM-DD HH24:MI')");
						} else {
							sql = sql.replaceFirst("\\?", "'" + StringUtils.quoteReplacement(values[i].toString()) + "'");
						}
					}

				} else {
					if (sql.toLowerCase().indexOf(" insert ") >= 0) {
						sql = sql.replaceFirst("\\?", " null ");
					} else {
						sql = sql.replaceFirst("\\?", " is null ");
					}
				}
			}
		}
		return sql;
	}

	/**
	 * incase there are some unmached field or method in the concrete
	 * bean,over-ride this method
	 * 
	 */
	protected AbstractEntityHelper doCreateEntityHelper(Entity bean, AbstractEntityHelper aeh) {
		return aeh;
	}

	protected String getTableName(Class claz) {
		String tableName = (String) Tables.get(claz);
		if (tableName == null) {
			Entity bean = null;
			try {
				bean = (Entity) claz.newInstance();
			} catch (InstantiationException e) {
				logger.fatal(e);
			} catch (IllegalAccessException e) {
				logger.fatal(e);
			}
			tableName = bean.getTableName();
			Tables.put(claz, tableName);
		}
		return tableName;
	}

	protected final List toRandomAccessList(List list) {
		if (list instanceof RandomAccess) {
			return list;
		} else {
			List r = new ArrayList(list.size());
			r.addAll(list);
			return r;
		}
	}
}
