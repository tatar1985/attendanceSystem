package com.tataren.main.web;

import javax.servlet.http.HttpServletRequest;


public class SortInformation {

	public static final String BEAN_NAME = SortInformation.class
			.getName()
			+ ".SortInformation";

	public static final String INPAGE_BEAN_NAME = SortInformation.class
			.getName()
			+ ".InpageSortInformation";

	public static final String ASC = "ASC";

	public static final String DESC = "DESC";

	// public static Criteria addOrder(Criteria criteria,
	// SortInformation sortInformation) {
	//
	// if (sortInformation != null && sortInformation.hasOrderInfo())
	// return criteria.addOrder(sortInformation.getHiberOrder());
	// else
	// return criteria;
	// }

	// public static DetachedCriteria addOrder(DetachedCriteria criteria,
	// SortInformation sortInformation) {
	// if (sortInformation != null && sortInformation.hasOrderInfo())
	// return criteria.addOrder(sortInformation.getHiberOrder());
	// else
	// return criteria;
	// }

	/**
	 * Add order information for the specified DetachedCriteria. if
	 * sortInformation contains order-command, use sortInformation's command,
	 * else use column as the order command,and store order info in
	 * sortInformation,if sortInformation is not null.
	 * 
	 * @param dc
	 * @param column
	 * @param sortInformation
	 * @return
	 */
	// public static DetachedCriteria addAscOrder(DetachedCriteria dc,
	// String column, SortInformation sortInformation) {
	// return addOrder(dc, column, sortInformation, true);
	// }
	/**
	 * Add order information for the specified DetachedCriteria. if
	 * sortInformation contains order-command, use sortInformation's command,
	 * else use column as the order command,and store order info in
	 * sortInformation,if sortInformation is not null.
	 * 
	 * @param dc
	 * @param column
	 * @param sortInformation
	 * @return
	 */
	// public static DetachedCriteria addDescOrder(DetachedCriteria dc,
	// String column, SortInformation sortInformation) {
	// return addOrder(dc, column, sortInformation, false);
	// }
	/**
	 * 
	 * @param dc
	 * @param column
	 * @param sortInformation
	 * @param isAsc
	 * @return
	 */
	// private static DetachedCriteria addOrder(DetachedCriteria dc,
	// String column, SortInformation sortInformation, boolean isAsc) {
	// // no order info!
	// if (sortInformation == null) {
	// return dc.addOrder(Order.asc(column));
	// } else if (sortInformation.hasOrderInfo())
	// return sortInformation.addOrder(dc);
	// else {
	// sortInformation.setColumn(column);
	// if (isAsc)
	// sortInformation.setOrder("asc");
	// else
	// sortInformation.setOrder("desc");
	// return dc.addOrder(Order.asc(column));
	// }
	// }
	// private static final Logger logger =
	// Logger.getLogger(SortInformation.class);
	private String column;

	private String order;

	/**
	 * Build instance according to the request
	 * 
	 * @param request
	 * @return SortInformation
	 */

	public static SortInformation getInstance(HttpServletRequest request) {

		SortInformation sortInformation = new SortInformation();
		sortInformation.setEncrptedColumn(request
				.getParameter(SortTag.NAME_SORT_COLUMN));
		sortInformation.setOrder(request.getParameter(SortTag.NAME_SORT_ORDER));
		// order is not encrypted
		request.setAttribute(SortInformation.BEAN_NAME, sortInformation);
		return sortInformation;

	}

	/**
	 * Build instance according to the request
	 * 
	 * @param request
	 * @return SortInformation
	 */

	public static SortInformation getInpageSortInstance(
			HttpServletRequest request) {

		SortInformation sortInformation = new SortInformation();
		sortInformation.setEncrptedColumn(request
				.getParameter(InpageSortTag.NAME_SORT_COLUMN));
		sortInformation.setOrder(request
				.getParameter(InpageSortTag.NAME_SORT_ORDER));
		// order is not encrypted
		request.setAttribute(SortInformation.INPAGE_BEAN_NAME, sortInformation);
		return sortInformation;

	}

	/**
	 * Returns the column name to be sorted by
	 * 
	 * @return column
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * Retruns the encrypted column
	 * 
	 * @return the encrypted column
	 */
	public String getEncryptedColumn() {
		return maskString(encrypt(column));
	}

	public static String maskString(String str) {
		if (str == null)
			return "__null__";
		else
			return "__" + str.trim() + "__";
	}

	public static String unmaskString(String str) {
		if (str == null)
			return null;

		if (str.equals("null"))
			return null;

		if (str.equals("__null__"))
			return null;

		return str.substring(2, str.length() - 2);
	}

	/**
	 * Returns the order
	 * 
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * Returns the encrypted order clause
	 * 
	 * @return the encrypted order clause
	 */
	public String getEncryptedOrder() {
		return encrypt(order);
	}

	/**
	 * Sets column to sort by
	 * 
	 * @param column
	 *            column to sort by
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * Sets encrypted column
	 * 
	 * @param encrypted
	 *            column
	 */
	void setEncrptedColumn(String column) {
		this.column = decrypt(column);
	}

	/**
	 * Sets order
	 * 
	 * @param order
	 *            order to sort by
	 */
	public void setOrder(String order) {
		if (order == null || order.trim().equalsIgnoreCase("null")
				|| order.trim().equalsIgnoreCase("__null__")
				|| order.trim().length() == 0)
			return;

		if (ASC.equalsIgnoreCase(order.trim())) {
			this.order = ASC;
		} else if (DESC.equalsIgnoreCase(order.trim())) {
			this.order = DESC;
		} else
			throw new IllegalArgumentException("  no such order :" + order);
	}

	/**
	 * Sets order
	 * 
	 * @param order
	 *            order to sort by
	 */
	public void setOrder(boolean increase) {
		if (increase)
			setOrder(ASC);
		else
			setOrder(DESC);
	}
	
	public void setAscOrder(){
		setOrder(ASC);
	}
	public void setDescOrder(){
		setOrder(DESC);
	}
	/**
	 * Sets encrypted order
	 * 
	 * @param order
	 *            order to sort by
	 */
	void setEncryptedOrder(String order) {
		this.order = decrypt(order);
	}

	/**
	 * Returns a sort clause
	 * 
	 * @return sort clause
	 */
	public String getOrderClause() {
		if (hasOrderInfo())
			return " ORDER BY " + column + " " + order + " ";
		else
			return "";
	}

	//  
	// /**
	// * Adds order clause to the criteria if there is order information
	// *
	// * @param dc
	// * dc to add order clause
	// * @return DetachedCriteria with order expressions if there exists order
	// * information.
	// */
	// public DetachedCriteria addOrder(DetachedCriteria dc) {
	// if (hasOrderInfo())
	// return dc.addOrder(getHiberOrder());
	// else
	// return dc;
	// }

	/**
	 * Adds order clause to the dc.If <tt>SortInformation</tt>contains order
	 * information, adds it,other wise,add order clause according to
	 * <tt>column</tt>
	 * 
	 * @param dc
	 *            dc to add order clause
	 * @return DetachedCriteria with order expressions if there exists order
	 *         information
	 */
	// public DetachedCriteria addAscOrder(DetachedCriteria dc, String column) {
	// // no order info!
	// if (hasOrderInfo())
	// return addOrder(dc);
	// else {
	// this.setColumn(column);
	// this.setOrder("asc");
	// return dc.addOrder(Order.asc(column));
	// }
	// }
	/**
	 * Adds order clause to the criteria.If <tt>SortInformation</tt>contains
	 * order information, adds it,other wise,add order clause according to
	 * <tt>column</tt>
	 * 
	 * @param DetachedCriteria
	 *            DetachedCriteria to add order clause
	 * @return DetachedCriteria with order expressions if there exists order
	 *         information
	 */
	// public DetachedCriteria addDescOrder(DetachedCriteria dc, String column)
	// {
	// // no order info!
	// if (hasOrderInfo())
	// return addOrder(dc);
	// else {
	// this.setColumn(column);
	// this.setOrder("desc");
	// return dc.addOrder(Order.asc(column));
	// }
	// }
	/**
	 * Returns true if there is order information
	 * 
	 * @return true if has order information
	 */
	public boolean hasOrderInfo() {
		if (column == null)
			return false;

		column = column.trim();

		if (column.length() == 0 || column.equalsIgnoreCase("null")
				|| column.equalsIgnoreCase("__null__")) {
			return false;
		}

		return true;
	}

	/**
	 * Returns true if asc order
	 * 
	 * @return boolean
	 */
	public boolean isAscOrder() {
		return order != null && order.trim().equalsIgnoreCase("asc");
	}

	private String encrypt(String str) {
		if (str == null)
			return null;
		//BASE64Encoder encoder = new BASE64Encoder();
		//return encoder.encodeBuffer(str.getBytes());
		return null;
	}

	private String decrypt(String str) {
		str = unmaskString(str);
		if (str == null)
			return null;
		//BASE64Decoder encoder = new BASE64Decoder();
		try {
			// return new String(encoder.decodeBuffer(str));
			return null;
		} catch (Exception e) {
			// never happen
			return str;
		}
	}
}