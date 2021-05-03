
package com.tataren.main.web;

import javax.servlet.http.HttpServletRequest;

import com.tataren.main.util.RequestUtils;


public class PageBreaker {
	
	public static final PageBreaker unlimitedBreaker() {
		PageBreaker unlimitedBreaker = new PageBreaker();
		unlimitedBreaker.setCurrentPageNum(1);
		unlimitedBreaker.setPageSize(Integer.MAX_VALUE);
		unlimitedBreaker.setTotalRowNum(Integer.MAX_VALUE);
		return unlimitedBreaker;
	}

	//public static final String BEAN_NAME = PageBreaker.class.getCanonicalName();
	
	public static final String BEAN_NAME = PageBreaker.class.getName();

	// private static final Logger logger = Logger.getLogger(PageBreaker.class);

	// row rumber
	private long totalRowNum;

	// current page number
	private long currentPageNum;

	// current page size
	private int pageSize;

	public static PageBreaker maskNull(PageBreaker pageBreaker) {
		return pageBreaker == null ? unlimitedBreaker() : pageBreaker;
	}

	/**
	 * Build instance according to the request
	 * 
	 * @param request
	 * @return pageBreaker
	 */
	public static PageBreaker getInstance(HttpServletRequest request) {
		return getInstance(request, PageBreakerTag.DEFAULT_PAGE_SIZE);
	}

	/**
	 * Build instance according to the request
	 * 
	 * @param request
	 * @param defaultPageSize
	 * @return pageBreaker
	 */
	public static PageBreaker getInstance(HttpServletRequest request, int defaultPageSize) {
		int pageSize = RequestUtils.getIntParameter(request, PageBreakerTag.CURRENT_PAGE_SIZE, defaultPageSize).intValue();
		pageSize = pageSize == 0 ? defaultPageSize : pageSize;
		// pageSize =
		// pageSize < PageBreakerTag.DEFAULT_MIN_PAGE_SIZE
		// ? PageBreakerTag.DEFAULT_MIN_PAGE_SIZE
		// : pageSize;

		long currentPageNum = RequestUtils.getLongParameter(request, PageBreakerTag.CURRENT_PAGE_NUM, 1);

		PageBreaker pageBreaker = new PageBreaker();
		pageBreaker.setCurrentPageNum(currentPageNum);
		pageBreaker.setPageSize(pageSize);

		request.setAttribute(PageBreakerTag.BEAN_NAME, pageBreaker);
		return pageBreaker;

	}

	public String toString() {
		return "totalRowNum" + totalRowNum + " currentPageNum" + currentPageNum + " pageSize" + pageSize;
	}

	/**
	 * Returns current page number
	 * 
	 * @return int
	 */
	public long getCurrentPageNum() {
		return currentPageNum;
	}

	/**
	 * Return fist row position
	 * 
	 * @return long
	 */
	public long getFistRowPosition() {
		long position = getPageSize() * (currentPageNum - 1);
		return position >= 0 ? position : 0;
	}

	/**
	 * Returns row number per page
	 * 
	 * @return int
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Returns totale page number
	 * 
	 * @return int
	 */
	public long getTotalPageNum() {
		if (pageSize == 0)
			return 0;
		else
			return (long) (Math.ceil((double) totalRowNum / (double) pageSize));
		// ad hoc
	}

	/**
	 * Returns total row number
	 * 
	 * @return int
	 */
	public long getTotalRowNum() {
		return totalRowNum;
	}

	/**
	 * @param i
	 */
	public void setCurrentPageNum(long i) {
		currentPageNum = i;
	}

	/**
	 * Set total row number
	 * 
	 * @param total
	 *            row number
	 */
	public void setTotalRowNum(long size) {

		this.totalRowNum = size;
		// reset currentPageNum
		currentPageNum = currentPageNum > this.getTotalPageNum() ? this.totalRowNum > 0 ? 1 : 0 : currentPageNum;
	}

	/**
	 * Sets row number per page
	 * 
	 * @param i
	 */
	public void setPageSize(int i) {
		pageSize = i;
	}

	/**
	 * Returns whether there is previous page to show.
	 * 
	 * @return boolean
	 */
	public boolean hasPreviousPage() {
		return this.currentPageNum > 1;
	}

	/**
	 * Returns whether there is next page to show.
	 * 
	 * @return boolean
	 */
	public boolean hasNextPage() {
		return this.currentPageNum < this.getTotalPageNum();
	}

}
