

package com.tataren.main.web;

import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tataren.main.util.ConvertUtils;



public class PageBreakerTag extends TagSupport {

	// prefix
	private static final String PREFIX = PageBreakerTag.class.getName();

	// key used to store/retrieve
	public static final String BEAN_NAME = PREFIX + "BEAN_NAME";

	public static final String DEFAULT_MAX_PAGE_SIZE_NAME = PREFIX
			+ "DEFAULT_MAX_PAGE_SIZE_NAME";

	public static final String DEFAULT_MIN_PAGE_SIZE_NAME = PREFIX
			+ "DEFAULT_MIN_PAGE_SIZE_NAME";

	public static final String CURRENT_PAGE_SIZE = PREFIX
			+ "CURRENT_PAGE_SIZE_NAME";

	public static final String CURRENT_PAGE_NUM = PREFIX
			+ "CURRENT_PAGE_NUM_NAME";

	public static final int MAX_PAGE_SIZE = Integer.MAX_VALUE;

	public static final int DEFAULT_MAX_PAGE_SIZE = 100;

	public static final int DEFAULT_MIN_PAGE_SIZE = 5;

	public static final int DEFAULT_PAGE_SIZE = 20;

	private static final int PAGE_NUMBER_LIMIT = 100;

	private static Logger logger = Logger.getLogger(PageBreakerTag.class);

	private String maxPageSize;

	private String minPageSize;

	private String defaultPageSize;

	private String form;

	private String button;

	/**
	 * Process the start tag.
	 * 
	 * @exception JspException
	 *                if a JSP exception has occurred
	 */
	public int doStartTag() throws JspException {

		ServletContext sc = pageContext.getServletContext();
		WebApplicationContext wc = WebApplicationContextUtils
				.getWebApplicationContext(sc);
		// String messageMs = wc.getMessage("welcome", null, null);
		ServletRequest request = pageContext.getRequest();
		// Enumeration enumenum = request.getAttributeNames();
		// while (enumenum.hasMoreElements()) {
		// String k = (String) enumenum.nextElement();
		// Object obj = request.getAttribute(k);
		// System.out.println(obj);
		// }

		PageBreaker pageBreaker = (PageBreaker) request.getAttribute(BEAN_NAME);
		if (pageBreaker == null) {
			pageBreaker = new PageBreaker();
			pageBreaker.setPageSize(ConvertUtils.getInt(defaultPageSize,
					DEFAULT_PAGE_SIZE));
		}
		int currentPageSize = pageBreaker.getPageSize();
		long currentPagePageNum = pageBreaker.getCurrentPageNum();

		// String bundleKey = Globals.MESSAGES_KEY;

		// String messageFistPage = safeMessage(bundleKey, null,
		// "pagebreaker.firstpage");
		// String messageLastPage = safeMessage(bundleKey, null,
		// "pagebreaker.lastpage");
		// String messagePreviousepage = safeMessage(bundleKey, null,
		// "pagebreaker.previusepage");
		// String messgeNextpage = safeMessage(bundleKey, null,
		// "pagebreaker.nextpage");
		String messageTotalRowNumber = safeMessage(null,
				"pagebreaker.totalRowNumber");
		String messagePageSize = safeMessage(null,
				"pagebreaker.currentPageSize");
		String messageTotalPageNumber = safeMessage(null,
				"pagebreaker.messageTotalPageNumber");
		String messagePage = safeMessage(null, "pagebreaker.messagePage");
		String messageGotoPage = safeMessage(null,
				"pagebreaker.messageGotoPage");
		String messagePrePage = safeMessage(null, "pagebreaker.previusepage");
		String messageNextPage = safeMessage(null, "pagebreaker.nextpage");
		String messagePageSeq = safeMessage(null, "pagebreaker.pageSeq");

		StringBuffer result = new StringBuffer();

		result.append(messageTotalRowNumber + " "
				+ pageBreaker.getTotalRowNum());
		// result.append(messagePageSize + " " + pageBreaker.getPageSize());
		result.append(messagePageSize + " ");
		appendPageSizeOption(result, pageBreaker);

		result.append(messageTotalPageNumber);
		result.append(pageBreaker.getTotalPageNum());
		result.append(messagePage);

		result.append(messagePageSeq);
		result.append(pageBreaker.getCurrentPageNum());
		result.append(messagePage);

		//
		appendJumpLink(result, pageBreaker, messagePrePage, messageNextPage);

		appendPageNumber(result, pageBreaker, messageGotoPage);

		result.append("<input type=\"hidden\" name=\"");
		result.append(CURRENT_PAGE_SIZE);
		result.append("\" value=\"");
		result.append(pageBreaker.getPageSize());
		result.append("\">");

		TagUtils.getInstance().write(pageContext, result.toString());

		if (logger.isDebugEnabled()) {
			logger.debug(" generate page breaker: " + result);
		}
		return (SKIP_BODY);
	}

	/**
	 * Release any acquired resources.
	 */
	public void release() {
	}

	private void appendPageSizeOption(StringBuffer result,
			PageBreaker pageBreaker) {

		result.append("<select name=\"");
		result.append(CURRENT_PAGE_SIZE);
		result.append("\">");
		int minSize = ConvertUtils.getInt(minPageSize, DEFAULT_MIN_PAGE_SIZE);
		int maxSize = ConvertUtils.getInt(maxPageSize, DEFAULT_MAX_PAGE_SIZE);
		// minSize = Math.min(pageBreaker.getTotalRowNum(), minSize);
		maxSize = (int) Math.min(maxSize, pageBreaker.getTotalRowNum());
		maxSize = Math.max(maxSize, minSize);
		if (pageBreaker.getTotalPageNum() == 0) {
			minSize = maxSize = 0;
		}
		if (pageBreaker.getPageSize() < minSize) {
			pageBreaker.setPageSize(minSize);
		}
		if (pageBreaker.getPageSize() > maxSize) {
			pageBreaker.setPageSize(maxSize);
		}

		boolean matched = false;
		boolean isLastProcessed = false;
		for (int i = minSize; i <= maxSize;) {
			result.append("<option value=\"");
			result.append(i);
			result.append("\"");
			if (i == pageBreaker.getPageSize() && !matched) {
				result.append(" selected ");
				matched = true;
				isLastProcessed = true;
			}
			result.append(">");
			result.append(i);
			result.append("</option>");
			int span = (int) Math.pow(10, String.valueOf(i).length() - 1);
			if (i + span > pageBreaker.getPageSize() && !matched) {
				result.append("<option value=\"");
				result.append(pageBreaker.getPageSize());
				result.append("\"");
				result.append(" selected ");
				result.append(">");
				result.append(pageBreaker.getPageSize());
				result.append("</option>");
				matched = true;
			}

			// do not skip the max possible page!
			if (i != maxSize
					&& (i + span > maxSize && pageBreaker.getPageSize() != maxSize)) {
				result.append("<option value=\"");
				result.append(maxSize);
				result.append("\"");
				result.append(">");
				result.append(maxSize);
				result.append("</option>");
				matched = true;
				isLastProcessed = true;
			}

			i += span;
			isLastProcessed = isLastProcessed || i == maxSize;
		}
		if (pageBreaker.getPageSize() == maxSize) {
			isLastProcessed = true;
		}
		// the last tag,if should
		if (!isLastProcessed) {
			if (maxSize % 10 != 0 && maxSize > 10) {
				result.append("<option value=\"");
				result.append(maxSize);
				result.append("\"");
				if (maxSize == pageBreaker.getPageSize()) {
					result.append(" selected ");
				}
				result.append(">");
				result.append(maxSize);
				result.append("</option>");
			}

		}
		result.append("</select>");
	}

	private void appendJumpLink(StringBuffer result, PageBreaker pageBreaker,
			String messagePrePage, String messageNextPage) {
		if (pageBreaker.getTotalRowNum() == 0)
			return;
		if (pageBreaker.hasPreviousPage()) {
			result.append(this.getJumpScript(messagePrePage, pageBreaker
					.getCurrentPageNum() - 1));
		}
		if (pageBreaker.hasNextPage()) {
			result.append(getJumpScript(messageNextPage, pageBreaker
					.getCurrentPageNum() + 1));
		}

		for (int i = (int) (pageBreaker.getCurrentPageNum() - 0.5) / 10 * 10 + 1; i <= (int) (pageBreaker
				.getCurrentPageNum() - 0.5) / 10 * 10 + 10; i++) {
			if (i <= pageBreaker.getTotalPageNum() && i >= 1)
				if (i != pageBreaker.getCurrentPageNum())
					result.append(getJumpScript(String.valueOf(i), i));
				else
					result.append("&nbsp;" + i);
		}
		result.append("&nbsp;");
	}

	private String getJumpScript(String message, long page) {
		StringBuffer result = new StringBuffer();
		result.append("&nbsp;");
		result.append("<A href=\"javascript:");
		result.append(this.getElementRef());
		result.append(CURRENT_PAGE_NUM);
		result.append("')");
		result.append(".value=");
		result.append(page);
		result.append(";");
		result.append(this.getButtonRef());
		result.append(".click();\">");
		result.append(message);
		result.append("</A>");
		return result.toString();
	}

	// private void appendPageNumber(StringBuffer result, PageBreaker
	// pageBreaker, String message) {
	//
	// result.append(message);
	// result.append("<select name=\"");
	// result.append(CURRENT_PAGE_NUM);
	// result.append("\">");
	// int step = 1;
	// for (int i = 1; i <= pageBreaker.getTotalPageNum(); i += step) {
	// result.append("<option value=\"");
	// result.append(i);
	// result.append("\"");
	// if (i == pageBreaker.getCurrentPageNum()) {
	// result.append(" selected ");
	// }
	// result.append(">");
	// result.append(i);
	// result.append("</option>");
	// step = (int) Math.pow(10, String.valueOf(i).length() - 1);
	//
	// }
	// result.append("</select>");
	// }
	private void appendPageNumber(StringBuffer result, PageBreaker pageBreaker,
			String message) {

		result.append(message);

		if (pageBreaker.getTotalPageNum() > PAGE_NUMBER_LIMIT) {
			result.append("<input name=\"");
			result.append(CURRENT_PAGE_NUM);
			int length = String.valueOf(pageBreaker.getTotalPageNum()).length();
			result.append("\" type=\"text\" size=\"");
			result.append(length);
			result.append("\" maxlength=\"");
			result.append(length);
			result.append("\" value=\"");
			result.append(pageBreaker.getCurrentPageNum());
			result.append("\"/>");
		} else {
			result.append("<select name=\"");
			result.append(CURRENT_PAGE_NUM);
			result.append("\" ");
			result.append(" id=\"");
			result.append(CURRENT_PAGE_NUM);
			result.append("\">");

			// int step = 1;
			for (long i = 1; i <= pageBreaker.getTotalPageNum(); i++) {
				result.append("<option value=\"");
				result.append(i);
				result.append("\"");
				if (i == pageBreaker.getCurrentPageNum()) {
					result.append(" selected ");
				}
				result.append(">");
				result.append(i);
				result.append("</option>");
				// step = (int) Math.pow(10, String.valueOf(i).length() - 1);
			}
			result.append("</select>");
		}
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

	private String getElementRef() {
		StringBuffer results = new StringBuffer();
		results.append("document.getElementById('");
		return results.toString();
	}
	
	/**
	 * @return
	 */
	public String getDefaultPageSize() {
		return defaultPageSize;
	}

	/**
	 * @return
	 */
	public String getMaxPageSize() {
		return maxPageSize;
	}

	/**
	 * @return
	 */
	public String getMinPageSize() {
		return minPageSize;
	}

	/**
	 * @param string
	 */
	public void setDefaultPageSize(String string) {
		defaultPageSize = string;
	}

	/**
	 * @param string
	 */

	public void setMaxPageSize(String string) {
		maxPageSize = string;
	}

	/**
	 * @param string
	 */
	public void setMinPageSize(String string) {
		minPageSize = string;
	}

	/**
	 * @return
	 */
	public String getButton() {
		return button;
	}

	/**
	 * @param string
	 */
	public void setButton(String string) {
		button = string;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String s) {
		this.form = s;
	}

	private String safeMessage(String locale, String i18nKey) {
		String message = i18nKey;
		// try {
		// message = TagUtils.getInstance().message(pageContext, bundleKey,
		// locale, i18nKey);
		// } catch (Exception e) {
		// // silence
		// }

		// FIXME: i18n,performance,etc
		ResourceBundle rb = ResourceBundle.getBundle("messages");
		message = rb.getString(i18nKey);

		return message;
	}

	public static void main(String[] args) {
		PageBreaker pg = new PageBreaker();
		pg.setCurrentPageNum(100000000000l);
		pg.setTotalRowNum(100000000000l);
		pg.setPageSize(2);
		PageBreakerTag pbt = new PageBreakerTag();
		StringBuffer sb = new StringBuffer();
		pbt.appendPageNumber(sb, pg, "");
		System.out.println(sb);

	}
}
