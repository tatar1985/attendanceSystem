package com.tataren.main.db;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuerySqlBuilder {

	private class WherePair {
		public String getWherePair() {
			if (isDateFormat)
				return tableName + "." + columnName + comparator + " to_date('" + value + "','YYYY-MM-DD HH24:MI:SS') ";
			if (isDateValue(value))
				return tableName + "." + columnName + comparator + " " + value + " ";
			if (isNumberValue(value))
				return tableName + "." + columnName + comparator + " '" + value + "' ";
			else
				return "upper(" + tableName + "." + columnName + ") " + comparator + "upper('" + value + "')";
		}

		private String tableName;

		private String columnName;

		private String value;

		private String comparator;

		private boolean isDateFormat;

		public WherePair(String tableName, String columnName, String value) {
			this(tableName, columnName, value, defaultComparator);
		}

		public WherePair(String tableName, String columnName, String value, String comparator) {
			this(tableName, columnName, value, comparator, false);
		}

		public WherePair(String tableName, String columnName, String value, String comparator, boolean isDateFormat) {
			if (!QuerySqlBuilder.allComparators.contains(comparator)) {
				throw new IllegalArgumentException("comaprator " + comparator + "is illegal");
			} else {
				this.tableName = tableName;
				this.columnName = columnName;
				this.value = value;
				this.comparator = comparator;
				this.isDateFormat = isDateFormat;
				return;
			}
		}
	}

	private static String Clause_True = "( 1=1 )";

	private static String Clause_False = "( 0=1 )";

	private static final String EQUAL = " = ";

	private static final String BIGGER = " > ";

	private static final String BIGGER_OR_EQUAL = " >= ";

	private static final String LESS = " < ";

	private static final String LESS_OR_EQUAL = " <= ";

	private static final String NOT_EQUAL = " != ";

	private static final String LIKE = " LIKE ";

	private static final String NOT_LIKE = " NOT LIKE ";

	private static final Set allComparators;

	private static final char SIMILAR_NOTES[] = { '*', '?' };

	private static final char WIDCHAR_NOTES[] = { '%', '_' };

	private String currentTable;

	private String booleanOperate;

	private List whereClauseList;

	private String defaultComparator;

	static {
		allComparators = new HashSet();
		allComparators.add(" = ");
		allComparators.add(" > ");
		allComparators.add(" >= ");
		allComparators.add(" < ");
		allComparators.add(" <= ");
		allComparators.add(" != ");
		allComparators.add(" LIKE ");
		allComparators.add(" NOT LIKE ");
	}

	public QuerySqlBuilder() {
		booleanOperate = " and ";
		whereClauseList = new ArrayList();
		defaultComparator = " = ";
	}
	/**
	 * 
	 * 
	 * @param array
	 * @return
	 *  max array is 1000,use @buildOrClause
	 * @deprecated
	 */
	public static String buildInClause(int[] array) {
		if (array == null || array.length == 0) {
			return String.valueOf(Integer.MIN_VALUE);
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length - 1; i++) {
			sb.append(array[i]);
			sb.append(',');
		}
		sb.append(array[array.length - 1]);
		return sb.substring(0, sb.length());
	}

	/**
	 * 
	 * 
	 * @param array
	 * @return
	 *  max array is 1000,use @buildOrClause
	 * @deprecated
	 */
	public static String buildInClause(Integer[] array) {
		int[] simpleArray = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			simpleArray[i] = array[i].intValue();
		}

		return buildInClause(simpleArray);
	}
	/**
	 * 
	 * 
	 * @param array
	 * @return
	 *  max array is 1000,use @buildOrClause
	 * @deprecated
	 */
	public static String buildInClause(String[] array) {
		if (array == null || array.length == 0) {
			return "'" + String.valueOf(Double.MIN_VALUE) + "'";
		}

		for (int i = 0; i < array.length; i++) {
			array[i] = normalizeParameter(array[i]);
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length - 1; i++) {
			sb.append('\'');
			sb.append(array[i]);
			sb.append('\'');
			sb.append(',');
		}
		sb.append('\'');
		sb.append(array[array.length - 1]);
		sb.append('\'');
		return sb.substring(0, sb.length());
	}

	private static String normalizeParameter(String parameter) {
		parameter = parameter.replaceFirst("'", "''");
		return parameter;
	}

	public static String buildOrClause(String column, int length) {

		if (length == 0)
			return Clause_False;

		StringBuffer sb = new StringBuffer();
		sb.append(" (");
		for (int i = 0; i < length - 1; i++) {
			sb.append(column + "=?");
			sb.append(" or ");
		}
		sb.append(column + "=?");
		sb.append(" ) ");

		return sb.toString();
	}

	public static String buildOrClause(String columnA, String columnB, int length) {
		if (length == 0)
			return Clause_False;

		StringBuffer sb = new StringBuffer();
		sb.append(" (");
		for (int i = 0; i < length - 1; i++) {
			sb.append('(');
			sb.append(columnA + "=?");
			sb.append(" and ");
			sb.append(columnB + "=?");
			sb.append(')');
			sb.append(" or ");
		}
		sb.append('(');
		sb.append(columnA + "=?");
		sb.append(" and ");
		sb.append(columnB + "=?");
		sb.append(')');
		sb.append(" ) ");

		return sb.toString();
	}

	public static String buildOrClause(String columnA, String columnB, String columnC, int length) {

		if (length == 0)
			return Clause_False;

		StringBuffer sb = new StringBuffer();
		sb.append(" (");
		for (int i = 0; i < length - 1; i++) {
			sb.append('(');
			sb.append(columnA + "=?");
			sb.append(" and ");
			sb.append(columnB + "=?");
			sb.append(" and ");
			sb.append(columnC + "=?");
			sb.append(')');
			sb.append(" or ");
		}

		sb.append('(');
		sb.append(columnA + "=?");
		sb.append(" and ");
		sb.append(columnB + "=?");
		sb.append(" and ");
		sb.append(columnC + "=?");
		sb.append(')');
		sb.append(" ) ");

		return sb.toString();
	}

	public String getWhereClause() {
		String clause = " ";
		for (int i = 0; i < whereClauseList.size(); i++) {
			WherePair pair = (WherePair) whereClauseList.get(i);
			if (i == 0)
				clause = clause + pair.getWherePair();
			else
				clause = clause + booleanOperate + pair.getWherePair();
		}

		return clause;
	}

	public void setTableName(String tableName) {
		currentTable = tableName;
	}

	public void add(String columnName, Object objValue) {
		if (objValue == null || objValue.toString().trim().length() == 0)
			return;
		String value = objValue.toString().trim();
		for (int i = 0; i < SIMILAR_NOTES.length; i++)
			if (value.indexOf(SIMILAR_NOTES[i]) != -1) {
				value = value.replace(SIMILAR_NOTES[i], WIDCHAR_NOTES[i]);
				whereClauseList.add(new WherePair(currentTable, columnName, value, " LIKE "));
				return;
			}

		add(columnName, objValue, defaultComparator, false);
	}

	public void add(String columnName, Object objValue, boolean isDateFormat) {
		add(columnName, objValue, defaultComparator, isDateFormat);
	}

	public void add(String columnName, Object objValue, String comparator) {
		add(columnName, objValue, comparator, false);
	}

	public void add(String columnName, Object value, String comparator, boolean isDateFormat) {
		if (currentTable == null)
			throw new IllegalStateException("invoke 'setTableName' firstly");
		if (value == null || value.toString().trim().length() == 0)
			return;
		if (comparator.equals(" LIKE ") || comparator.equals(" NOT LIKE "))
			value = "%" + value + "%";
		whereClauseList.add(new WherePair(currentTable, columnName, value.toString().trim(), comparator, isDateFormat));
	}

	public void setBoolOperate(boolean isAnd) {
		if (isAnd)
			booleanOperate = " and ";
		else
			booleanOperate = " or ";
	}

	public void setDefaultComparator(String defaultComparator) {
		if (!allComparators.contains(defaultComparator)) {
			throw new IllegalArgumentException("comaprator " + defaultComparator + "is illegal");
		} else {
			this.defaultComparator = defaultComparator;
			return;
		}
	}

	public void clear() {
		currentTable = null;
		booleanOperate = " and ";
		whereClauseList.clear();
		defaultComparator = " = ";
	}

	private boolean isDateValue(String value) {
		if (value == null)
			return false;
		int index = value.toLowerCase().indexOf("to_date");
		return index != -1;
	}

	private boolean isNumberValue(String value) {
		if (value == null)
			return false;
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
