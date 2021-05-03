package com.tataren.main.web;

import javax.servlet.ServletRequest;

import org.apache.log4j.Logger;



public class InpageSortTag extends SortTag {

	//prefix
	private static final String PREFIX = "__INPAGE_SORT_TAG_";
	//html sort order contol name
	public static final String NAME_SORT_ORDER = PREFIX + "NAME_SORT_ORDER";
	//html sort column contol name	
	public static final String NAME_SORT_COLUMN = PREFIX + "NAME_SORT_COLUMN";

	//mark whether the script is rendered
	private static final String HAS_SCRIPT_RENDERED = PREFIX + "HAS_SCRIPT_RENDERED";

	private static Logger logger = Logger.getLogger(InpageSortTag.class);

	protected String getNameOfSortOrder() {
		return NAME_SORT_ORDER;
	}
	protected String getNameOfSortColumn() {
		return NAME_SORT_COLUMN;
	}

	protected String getNameOfMark() {
		return HAS_SCRIPT_RENDERED;
	}

	/**
	 * @return
	 */
	protected Object getScriptName() {
		return "__InpageSort";
	}
	protected SortInformation getSortInfo() {
		ServletRequest request = pageContext.getRequest();
		SortInformation sortInformation = (SortInformation) request.getAttribute(SortInformation.INPAGE_BEAN_NAME);
		if (sortInformation == null)
			sortInformation = new SortInformation();
		return sortInformation;
	}
	//
	//	//the message to be shown
	//	private String message;
	//
	//	//the sort column
	//	private String column;
	//
	//	//html £¨submit)button name 
	//	private String button;
	//
	//	//html form name
	//	private String form;
	//	/**
	//	 * The default Locale for our server.
	//	 */
	//	private static final Locale defaultLocale = Locale.getDefault();
	//
	//	// --------------------------------------------------------- Public Methods
	//	protected String name = org.apache.struts.taglib.html.Constants.BEAN_KEY;
	//
	//	/**
	//	 * Process the start tag.
	//	 *
	//	 * @exception JspException if a JSP exception has occurred
	//	 */
	//	public int doStartTag() throws JspException {
	//
	//		ServletRequest request = pageContext.getRequest();
	//		FormTag tag = (FormTag) request.getAttribute(Constants.FORM_KEY);
	//
	//		form = tag.getBeanName();
	//		StringBuffer results = new StringBuffer();
	//		results.append(renderMessage());
	//		results.append(renderJavascript());
	//
	//		ResponseUtils.write(pageContext, results.toString());
	//
	//		return (SKIP_BODY);
	//	}
	//
	//	/**
	//	* Release any acquired resources.
	//	*/
	//	public void release() {
	//
	//	}
	//	/**
	//	 * @return
	//	 */
	//	public static Locale getDefaultLocale() {
	//		return defaultLocale;
	//	}
	//
	//	public String getButton() {
	//		return button;
	//	}
	//
	//	public String getColumn() {
	//		return column;
	//	}
	//
	//	public String getForm() {
	//		return form;
	//	}
	//
	//	public String getName() {
	//		return name;
	//	}
	//
	//	public void setButton(String string) {
	//		button = string;
	//	}
	//
	//	public void setColumn(String string) {
	//		BASE64Encoder encoder = new BASE64Encoder();
	//		string = encoder.encodeBuffer(string.getBytes());
	//		column = SortInformation.maskString(string);
	//	}
	//
	//	public void setForm(String string) {
	//		form = string;
	//	}
	//
	//	public void setName(String string) {
	//		name = string;
	//	}
	//
	//	public String getMessage() {
	//		return message;
	//	}
	//
	//	public void setMessage(String string) {
	//		message = string;
	//	}
	//
	//	public String renderMessage() throws JspException {
	//		// Retrieve the message string 
	//		String i18nMessage = RequestUtils.message(pageContext, null, null, message);
	//		if (i18nMessage == null || i18nMessage.equalsIgnoreCase("null"))
	//			i18nMessage = message;
	//		i18nMessage = "<A HREF=\"javascript:" + PREFIX + "SORT('" + column + "')\">" + i18nMessage + "</A>";
	//		return i18nMessage;
	//	}
	//
	//	/*
	//	 * Render javascrip and form hidden element
	//	 * 
	//	 * @return  
	//	 */
	//	private String renderJavascript___() {
	//		ServletRequest request = pageContext.getRequest();
	//		if (request.getAttribute(HAS_SCRIPT_RENDERED) != null)
	//			return "";
	//
	//		SortInformation sortInformation = (SortInformation) request.getAttribute(SortInformation.INPAGE_BEAN_NAME);
	//		if (sortInformation == null)
	//			sortInformation = new SortInformation();
	//
	//		StringBuffer results = new StringBuffer();
	//		//sort order
	//		results.append("<input type=\"HIDDEN\" name=\"");
	//		results.append(NAME_SORT_ORDER);
	//		results.append("\" value=\"");
	//		results.append(sortInformation.getOrder());
	//		results.append("\"");
	//		results.append(">");
	//
	//		//sort column
	//		results.append("<input type=\"HIDDEN\" name=\"");
	//		results.append(NAME_SORT_COLUMN);
	//		results.append("\" value=\"");
	//		results.append(sortInformation.getEncryptedColumn());
	//		results.append("\"");
	//		results.append(">");
	//
	//		//html contol ref
	//		String sortColumnRef = getSortColumnRef();
	//		String sortOrderRef = getSortOrderRef();
	//
	//		/*
	//		 * java script
	//		 */
	//		results.append("<script type=\"text/javascript\" language=\"JavaScript\">");
	//		results.append("function " + PREFIX + "SORT(column){");
	//
	//		/*
	//		 * set sort order
	//		 */
	//
	//		//not column yet
	//		//		results.append("	alert('in: '+ column);");
	//		//		results.append("	alert('old: '+"+sortColumnRef+".value);");
	//		//		results.append("var vvv="+sortColumnRef+".value;");
	//		//		results.append("alert(vvv==column);");
	//		results.append("if(" + sortColumnRef + ".value==\"__null__\"){");
	//		results.append("	" + sortOrderRef + ".value=\"asc\";");
	//		//same column		
	//		results.append("}else if(" + sortColumnRef + ".value==column){");
	//		//results.append("	alert(\"\");");
	//		results.append("	if(" + sortOrderRef + ".value==\"asc\")");
	//		results.append("		" + sortOrderRef + ".value=\"desc\";");
	//		results.append("	else ");
	//		results.append("		" + sortOrderRef + ".value=\"asc\";");
	//		results.append("}else{"); //other column
	//		//		results.append("	alert(\"err\");");
	//		//		results.append("	alert(\"err\");");
	//		results.append("	" + sortOrderRef + ".value=\"asc\";");
	//		results.append("}");
	//		results.append(sortColumnRef + ".value=column;");
	//		//		results.append("	alert(" + sortOrderRef + ".value);");
	//		//set new column
	//
	//		/*
	//		 * 
	//		 * debug
	//		 */
	//		//				results.append("alert('col: '+" + sortColumnRef + ".value);");
	//		//				results.append("alert('order: '+" + sortOrderRef + ".value);");
	//
	//		/*
	//		 * button click
	//		 */
	//		results.append(getButtonRef());
	//		results.append(".click();");
	//		results.append("}");
	//
	//		results.append("</script>");
	//
	//		//store an already rendered mark
	//		request.setAttribute(HAS_SCRIPT_RENDERED, "");
	//		return results.toString();
	//	}
	//
	//	private String getSortColumnRef() {
	//		StringBuffer results = new StringBuffer();
	//		results.append(getFormRef());
	//		results.append(".");
	//		;
	//		results.append(NAME_SORT_COLUMN);
	//		return results.toString();
	//	}
	//	private String getSortOrderRef() {
	//		StringBuffer results = new StringBuffer();
	//		results.append(getFormRef());
	//		results.append(".");
	//		results.append(NAME_SORT_ORDER);
	//		return results.toString();
	//	}
	//	private String getButtonRef() {
	//		StringBuffer results = new StringBuffer();
	//		results.append(getFormRef());
	//		results.append(".");
	//		results.append(button);
	//		return results.toString();
	//	}
	//	private String getFormRef() {
	//		StringBuffer results = new StringBuffer();
	//		results.append("document.forms['");
	//		results.append(form);
	//		results.append("']");
	//		return results.toString();
	//	}
	//
}
