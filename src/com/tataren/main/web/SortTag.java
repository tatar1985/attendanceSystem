package com.tataren.main.web;

import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;

import com.tataren.main.security.BASE64Encoder;
import com.tataren.main.constant.CommonGlobals;



public class SortTag extends TagSupport {

	// prefix
	private static final String PREFIX = "__SORT_TAG_";

	// html sort order contol name
	public static final String NAME_SORT_ORDER = PREFIX + "NAME_SORT_ORDER";

	// html sort column contol name
	public static final String NAME_SORT_COLUMN = PREFIX + "NAME_SORT_COLUMN";

	// mark whether the script is rendered
	private static final String HAS_SCRIPT_RENDERED = PREFIX + "HAS_SCRIPT_RENDERED";

	private static Logger logger = Logger.getLogger(SortTag.class);

	// the message to be shown
	private String message;

	// the sort column
	private String column;

	// html £¨submit)button name
	private String button;

	// html form name
	private String form;

	/**
	 * The default Locale for our server.
	 */
	private static final Locale defaultLocale = Locale.getDefault();

	/**
	 * Process the start tag.
	 * 
	 * @exception JspException
	 *                if a JSP exception has occurred
	 */
	public int doStartTag() throws JspException {

		StringBuffer results = new StringBuffer();
		renderLink(results);
		renderJavascript(results);

		TagUtils.getInstance().write(pageContext, results.toString());

		return (SKIP_BODY);
	}

	/**
	 * Release any acquired resources.
	 */
	public void release() {

	}

	/**
	 * @return
	 */
	public static Locale getDefaultLocale() {
		return defaultLocale;
	}

	public String getButton() {
		return button;
	}

	public String getColumn() {
		return column;
	}

	public String getForm() {
		return form;
	}

	public void setButton(String string) {
		button = string;
	}

	public void setColumn(String string) {
		BASE64Encoder encoder = new BASE64Encoder();
		string = encoder.encodeBuffer(string.getBytes());
		column = SortInformation.maskString(string);
	}

	public void setForm(String string) {
		form = string;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String string) {
		message = string;
	}

	private String renderLink(StringBuffer results) throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		SortInformation sortInformation = getSortInfo();

		// Retrieve the message string
		String i18nMessage = message;
		try {
			i18nMessage = TagUtils.getInstance().message(pageContext, null, null, message);
		} catch (Throwable e) {
			// silence
		}
		boolean isUnorder;
		boolean isAsc;
		if (column.equals(sortInformation.getEncryptedColumn())) {
			isUnorder = false;
			if (sortInformation.getOrder().equalsIgnoreCase("asc")) {
				isAsc = false;
			} else {
				isAsc = true;
			}
		} else {
			isUnorder = true;
			isAsc = true;
		}

		results.append("<A HREF=\"javascript:");
		results.append(getScriptName());
		results.append("('");
		results.append(column);
		results.append("',");
		results.append(isAsc);
		results.append(")\">");
		results.append(i18nMessage);

		if (!isUnorder) {
			if (isAsc) {
				if (CommonGlobals.sortIndicatorUseImage) {
					results.append("<IMG src=\"");
					results.append(request.getContextPath());
					results.append(CommonGlobals.sortIndicatorImageAsc);
					results.append("\"/>");

				} else {
					results.append(CommonGlobals.sortIndicatorCharAsc);
				}
			} else {
				if (CommonGlobals.sortIndicatorUseImage) {
					results.append("<IMG src=\"");
					results.append(request.getContextPath());
					results.append(CommonGlobals.sortIndicatorImageDesc);
					results.append("\"/>");
				} else {
					results.append(CommonGlobals.sortIndicatorCharDesc);
				}
			}
		} else {
			if (CommonGlobals.sortIndicatorUseImage) {
				results.append("<IMG src=\"");
				results.append(request.getContextPath());
				results.append(CommonGlobals.sortIndicatorImageNon);
				results.append("\"/>");
			} else {
				results.append(CommonGlobals.sortIndicatorCharNon);
			}
		}
		results.append("</A>");
		// results.append("<IMG src=\"../images/pus/collapse.gif\"");
		// results.append("onclick=\"javascript:__SORT('");
		// results.append(
		// results.append("')\">"
		//				
		// <IMG src="<%=request.getContextPath()%>/images/pus/collapse.gif"
		// onclick="javascript:doExpand('<%=pus.getPusNum()%>')" >
		return i18nMessage;
	}

	protected Object getScriptName() {
		return "__Sort";
	}

	protected SortInformation getSortInfo() {
		ServletRequest request = pageContext.getRequest();
		SortInformation sortInformation = (SortInformation) request.getAttribute(SortInformation.BEAN_NAME);
		if (sortInformation == null)
			sortInformation = new SortInformation();
		return sortInformation;
	}

	/*
	 * Render javascrip and form hidden element
	 * 
	 * @return
	 */
	protected String renderJavascript(StringBuffer results) {
		ServletRequest request = pageContext.getRequest();
		if (request.getAttribute(getNameOfMark()) != null)
			return "";
		SortInformation sortInformation = getSortInfo();

		// sort order
		results.append("<input type=\"HIDDEN\" name=\"");
		results.append(getNameOfSortOrder());
		results.append("\" value=\"");
		results.append(sortInformation.getOrder());
		results.append("\"");
		results.append(">");

		// sort column
		results.append("<input type=\"HIDDEN\" name=\"");
		results.append(getNameOfSortColumn());
		results.append("\" value=\"");
		results.append(sortInformation.getEncryptedColumn());
		results.append("\"");
		results.append(">");
		// html contol ref
		String sortColumnRef = getSortColumnRef();
		String sortOrderRef = getSortOrderRef();

		results.append("<script type=\"text/javascript\" language=\"JavaScript\">");
		results.append("function " + this.getScriptName() + "(column,b){");
		results.append("if(b){ ");
		results.append(sortOrderRef + ".value=\"asc\";");
		results.append("}	else {");
		results.append(sortOrderRef + ".value=\"desc\";");
		results.append("}");
		results.append(sortColumnRef + ".value=column;");
		// results.append("alert("+sortColumnRef + ".value);");
		/*
		 * button click
		 */
		results.append(getButtonRef());
		results.append(".click();");
		results.append("}");
		results.append("</script>");
		// store an already rendered mark
		request.setAttribute(getNameOfMark(), "");
		return results.toString();

		// //rest all is old
		//
		// //sort order
		// results.append("<input type=\"HIDDEN\" name=\"");
		// results.append(getNameOfSortOrder());
		// results.append("\" value=\"");
		// results.append(sortInformation.getOrder());
		// results.append("\"");
		// results.append(">");
		//
		// //sort column
		// results.append("<input type=\"HIDDEN\" name=\"");
		// results.append(getNameOfSortColumn());
		// results.append("\" value=\"");
		// results.append(sortInformation.getEncryptedColumn());
		// results.append("\"");
		// results.append(">");
		// //html contol ref
		// String sortColumnRef = getSortColumnRef();
		// String sortOrderRef = getSortOrderRef();
		//
		// results.append("<script type=\"text/javascript\"
		// language=\"JavaScript\">");
		// results.append("function __SORT(column){");
		// // results.append(" alert('in: '+ column);");
		// // results.append(" alert('old: '+"+sortColumnRef+".value);");
		// // results.append("var vvv="+sortColumnRef+".value;");
		// // results.append("alert(vvv==column);");
		// results.append("if(" + sortColumnRef + ".value==\"__null__\"){");
		// results.append(" " + sortOrderRef + ".value=\"asc\";");
		// //same column
		// results.append("}else if(" + sortColumnRef + ".value==column){");
		// results.append(" alert(\"equal\");");
		// results.append(" if(" + sortOrderRef + ".value==\"asc\")");
		// results.append(" " + sortOrderRef + ".value=\"desc\";");
		// results.append(" else ");
		// results.append(" " + sortOrderRef + ".value=\"asc\";");
		// results.append("}else{");
		// //other column
		// results.append(" alert(\"other\");");
		// // results.append(" alert(\"err\");");
		// results.append(" " + sortOrderRef + ".value=\"asc\";");
		// results.append("}");
		// results.append(sortColumnRef + ".value=column;");
		// // results.append(" alert(" + sortOrderRef + ".value);");
		// //set new column
		//		 
		// results.append("alert('col: '+" + sortColumnRef + ".value);");
		// results.append("alert('order: '+" + sortOrderRef + ".value);");
		//		 
		// results.append(getButtonRef());
		// results.append(".click();");
		// results.append("}");
		// results.append("</script>");
		// //store an already rendered mark
		// request.setAttribute(getNameOfMark(), "");
		// return results.toString();
	}

	private String getSortColumnRef() {
		StringBuffer results = new StringBuffer();
		results.append(getFormRef());
		results.append(".");
		results.append(getNameOfSortColumn());
		return results.toString();
	}

	private String getSortOrderRef() {
		StringBuffer results = new StringBuffer();
		results.append(getFormRef());
		results.append(".");
		results.append(getNameOfSortOrder());
		return results.toString();
	}

	private String getButtonRef() {
		StringBuffer results = new StringBuffer();
		results.append(getFormRef());
		results.append(".");
		results.append(button);
		return results.toString();
	}

	private String getFormRef() {
		StringBuffer results = new StringBuffer();
		results.append("document.forms['");
		results.append(form);
		results.append("']");
		return results.toString();
	}

	protected String getNameOfSortOrder() {
		return NAME_SORT_ORDER;
	}

	protected String getNameOfSortColumn() {
		return NAME_SORT_COLUMN;
	}

	protected String getNameOfMark() {
		return HAS_SCRIPT_RENDERED;
	}
}
