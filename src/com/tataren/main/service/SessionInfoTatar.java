package com.tataren.main.service;

import java.io.Serializable;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tataren.main.domain.user.WebUser;
import com.tataren.tatar.domain.TMember;


public class SessionInfoTatar implements Serializable {

	private static final String PREFIX = SessionInfoTatar.class.toString() + ".";

	private static final String SESSION_KEY = PREFIX + "KEY";
	
	public static final String appid = "wxa7d55f77a133135f";

	public static final String secret = "957d17414ca629e3bd5cdc90d2726edf";
	
	public static   String fileId="1513003595391";
	
	//public static final String  appid = "wxd41954995067e823";
	
	//public static final String  secret = "fa5916e2f311a60db2b9e22e0b3bef31";
	

	
	public static String access_token="1";

	public static boolean isLogined(HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		return session != null && session.getAttribute(SESSION_KEY) != null;
	}

	
	public static SessionInfoTatar createInstance(HttpServletRequest request, WebUser webUser, Locale locale) {
		SessionInfoTatar sessionInfo = new SessionInfoTatar(webUser,locale);
		HttpSession session = request.getSession(true);
		session.setAttribute(SESSION_KEY, sessionInfo);
		return sessionInfo;
	}
	public static SessionInfoTatar createInstanceTMember(HttpServletRequest request, TMember webUser, Locale locale) {
		SessionInfoTatar sessionInfo = new SessionInfoTatar(webUser,locale);
		HttpSession session = request.getSession(true);
		session.setAttribute(SESSION_KEY, sessionInfo);
		return sessionInfo;
	}


	public static SessionInfoTatar getSessionInfo(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		if (session == null) {
			// should never happen,since session-filter works
			// throw new RuntimeException(" no session exists");
		}

		return (SessionInfoTatar) session.getAttribute(SESSION_KEY);
	}



	private Locale locale;
	
	private WebUser webUser;
	
	private TMember tebUser;

	
	private SessionInfoTatar(WebUser webUser, Locale locale) {
		this.webUser = webUser;
		this.locale = locale;
	}
	private SessionInfoTatar(TMember tebUser, Locale locale) {
		this.tebUser = tebUser;
		this.locale = locale;
	}
	public Locale getLocal() {
		return this.locale;
	}

	public boolean hasPrivilege(String privilegeCode) {
		return true;
	}

}
