
package com.tataren.main.service;

import java.util.List;

import com.tataren.main.db.ValueTypeBuilder;
import com.tataren.main.web.PageBreaker;
import com.tataren.main.web.SortInformation;
import com.tataren.main.dao.AbstractJdbcDao;
import com.tataren.main.dao.CommonSequenceDao;
import com.tataren.main.domain.Entity;
import com.tataren.main.domain.SequenceGenerator;


public abstract class BasicService implements BasicServiceInterface {

	private AbstractJdbcDao dao;

	private CommonSequenceDao commonSequenceDao;

	public static class BasicJdbcDao extends AbstractJdbcDao {
	}

	public void store(Entity bean) {
		dao.saveOrUpdate(bean);
	}

	public int deleteBean(Class claz, Integer sid) {
		return dao.deleteBean(claz, sid);
	}

	public int deleteBean(Entity bean) {
		return dao.deleteBean(bean.getClass(), bean.getSid());
	}

	public List findByIds(Class claz, Integer[] sids) {
		return dao.findByIds(claz, sids);
	}

	public Entity getBean(Class claz, Integer sid) {
		return this.getBean(claz, sid.intValue());
	}

	public Entity getBean(Class claz, int sid) {
		return dao.getBean(claz, sid);
	}

	public List findByExample(Entity eg) {
		return this.findByExample(eg, null, null);
	}

	public List findByExample(Entity eg, PageBreaker breaker, SortInformation sortInfo) {
		return dao.findByExample(eg, breaker, sortInfo);
	}

	//	public List findByExample(Entity eg, String clause) {
	//		return this.findByExample(eg, clause, null, null);
	//	}
	//
	//	public List findByExample(Entity eg, String clause, PageBreaker breaker, SortInformation sortInfo) {
	//		return dao.findByExample(eg, clause, breaker, sortInfo);
	//	}

	protected int update(String sql) {
		return dao.update(sql);
	}

	protected int update(String sql, Object[] values) {
		return dao.update(sql, values);
	}

	protected int update(String sql, Object[] values, int[] types) {
		return dao.update(sql, values, types);
	}

	protected List query(String sql, Class claz) {
		return dao.query(sql, claz);
	}

	protected List query(String sql, Object[] values, Class claz) {
		return dao.query(sql, values, claz);
	}

	protected List query(String sql, Object[] values, int[] types, Class claz) {
		return dao.query(sql, values, types, claz);
	}

	protected List query(String sql, ValueTypeBuilder builder, Class claz) {
		return dao.query(sql, builder.getValues(), builder.getTypes(), claz);
	}

	protected List query(String sql, Class claz, PageBreaker breaker, SortInformation sortInfo) {
		return dao.query(sql, claz, breaker, sortInfo);
	}

	protected List query(String sql, Object[] values, Class claz, PageBreaker breaker, SortInformation sortInfo) {
		return dao.query(sql, values, claz, breaker, sortInfo);
	}

	protected List query(String sql, Object[] values, int[] types, Class claz, PageBreaker breaker, SortInformation sortInfo) {
		return dao.query(sql, values, types, claz, breaker, sortInfo);
	}

	protected List query(String sql, ValueTypeBuilder builder, Class claz, PageBreaker breaker, SortInformation sortInfo) {
		return query(sql, builder.getValues(), builder.getTypes(), claz, breaker, sortInfo);
	}

	public boolean existCommonSeq(String key, String helpKey) {
		return commonSequenceDao.getCommonSeq(key, helpKey) != null;
	}

	public SequenceGenerator getCommonSeq(String key, String helpKey) {
		return commonSequenceDao.getCommonSeq(key, helpKey);
	}

	public AbstractJdbcDao getDao() {
		return dao;
	}

	public void setDao(AbstractJdbcDao dao) {
		this.dao = dao;
	}

	public CommonSequenceDao getCommonSequenceDao() {
		return commonSequenceDao;
	}

	public void setCommonSequenceDao(CommonSequenceDao commonSequenceDao) {
		this.commonSequenceDao = commonSequenceDao;
	}

}