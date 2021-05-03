package com.tataren.main.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tataren.main.service.SessionInfo;

public class SessionFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpRequest.setCharacterEncoding("GBK");
		httpResponse.setCharacterEncoding("GBK");

		String uri = httpRequest.getRequestURI();
		if (!SessionInfo.isLogined(httpRequest) && !Ignore.ignores(uri)
				&& !uri.equals(httpRequest.getContextPath() + "/")) {
			httpResponse.getWriter().println(
					"<script>top.location.href='"
							+ httpRequest.getContextPath()
							+ "/index.jsp';</script>");
			// request.getRequestDispatcher("/index").forward(request,
			// response);
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	 * @author elgs
	 */
	public static class Ignore {

		private Ignore() {
		}

		private static List ignoredPatterns;
		static {
			Properties ignoredUris;
			try {
				ignoredUris = loadProperties("ignore.properties");
				ignoredPatterns = new ArrayList(ignoredUris.size());
				Enumeration e = ignoredUris.elements();
				while (e.hasMoreElements()) {
					ignoredPatterns.add(Pattern.compile((String) e
							.nextElement()));
				}
			} catch (IOException e) {
				ignoredPatterns = new ArrayList(0);
			}
		}

		private static Properties loadProperties(String s) throws IOException {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream in = loader.getResourceAsStream(s);
			Properties p = new Properties();
			p.load(in);
			return p;
		}

		public static boolean ignores(String uri) {
			for (Iterator iter = ignoredPatterns.iterator(); iter.hasNext();) {
				if (((Pattern) iter.next()).matcher(uri).matches())
					return true;
			}
			return false;
		}
	}
}
