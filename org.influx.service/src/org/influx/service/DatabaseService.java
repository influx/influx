package org.influx.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DatabaseService extends InfluxService{

	/**
	 * add a list of tuples into the database
	 * @param <T>
	 * @param tuples
	 * @return
	 */
	public abstract <T> boolean addTuples(List<T> tuples);

	/**
	 * add a tuple to the database
	 * @param <T>
	 * @param tuple
	 * @return
	 */
	public abstract <T> boolean addTuple(T tuple);

	/**
	 * @param <T> delete a tuple from database
	 * @param tuple
	 * @return
	 */
	public abstract <T> boolean deleteTuple(T tuple);

	/**
	 * delete a list of tuples from database
	 * @param <T>
	 * @param tuples
	 * @return
	 */
	public abstract <T> boolean deleteTuples(List<T> tuples);

	/**
	 * execute a hql update
	 * @param hql
	 * @param params: parameters to be specified in hql
	 * @return
	 */
	public abstract int executeUpdate(String hql, Map<String, Object> params);

	
	public abstract List<?> doHQL(String hql, Map<String, Object> params,
			Map<String, Collection> collectionParams, boolean lockForUpdate,
			int start, int limit);

	

}