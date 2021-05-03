
package com.tataren.main.service;

import java.util.List;

import com.tataren.main.web.PageBreaker;
import com.tataren.main.web.SortInformation;
import com.tataren.main.domain.Entity;
import com.tataren.main.domain.SequenceGenerator;


public interface BasicServiceInterface {

	public void store(Entity bean);

//	public int update(String sql, Object[] values);
//
//	public int update(String sql, Object[] values, int[] types);

	public int deleteBean(Class claz, Integer sid);

	public int deleteBean(Entity bean);

	public List findByIds(Class claz, Integer[] sids);

	public Entity getBean(Class claz, Integer sid);

	public Entity getBean(Class claz, int sid);

	public List findByExample(Entity eg);

	//	public List findByExample(Entity eg, String clause);

	public List findByExample(Entity eg, PageBreaker breaker, SortInformation sortInfo);

	//	public List findByExample(Entity eg, String clause, PageBreaker breaker, SortInformation sortInfo);

	//public List query(String sql, Class claz);
	//
	//	public List query(String sql, Object[] values, Class claz);
	//
	//	public List query(String sql, Object[] values, int[] types, Class claz);
	//
	//	public List query(String sql, ValueTypeBuilder builder, Class claz);
	//
	//	public List query(String sql, Class claz, PageBreaker breaker, SortInformation sortInfo);
	//
	//	public List query(String sql, Object[] values, Class claz, PageBreaker breaker, SortInformation sortInfo);
	//
	//	public List query(String sql, Object[] values, int[] types, Class claz, PageBreaker breaker, SortInformation sortInfo);
	//
	//	public List query(String sql, ValueTypeBuilder builder, Class claz, PageBreaker breaker, SortInformation sortInfo);

	public boolean existCommonSeq(String key, String helpKey);

	public SequenceGenerator getCommonSeq(String key, String helpKey);
}
