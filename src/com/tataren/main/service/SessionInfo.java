package com.tataren.main.service;

import java.io.Serializable;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tataren.main.domain.user.WebUser;
import com.tataren.tatar.domain.TUsers;

public class SessionInfo implements Serializable {

	private static final String PREFIX = SessionInfo.class.toString() + ".";

	private static final String SESSION_KEY = PREFIX + "KEY";

	public static final String appid = "wxa7d55f77a133135f";

	public static final String secret = "957d17414ca629e3bd5cdc90d2726edf";
	
	public static final String appUrl = "www.tataren.com";

	public static boolean isLogined(HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		return session != null && session.getAttribute(SESSION_KEY) != null;
	}

	public static SessionInfo createInstance(HttpServletRequest request,
			TUsers webUser, Locale locale) {
		SessionInfo sessionInfo = new SessionInfo(webUser, locale);
		HttpSession session = request.getSession(true);
		session.setAttribute(SESSION_KEY, sessionInfo);
		return sessionInfo;
	}

	public static SessionInfo getSessionInfo(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		if (session == null) {
			// should never happen,since session-filter works
			// throw new RuntimeException(" no session exists");
		}

		return (SessionInfo) session.getAttribute(SESSION_KEY);
	}

	private Locale locale;

	private TUsers webUser;

	private SessionInfo(TUsers webUser, Locale locale) {
		this.webUser = webUser;
		this.locale = locale;
	}

	public Locale getLocal() {
		return this.locale;
	}

	public boolean hasPrivilege(String privilegeCode) {
		return true;
	}
	public static double Distance(double long1, double lat1, double long2, double lat2) {

	     

        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
        return d;
    }
}
