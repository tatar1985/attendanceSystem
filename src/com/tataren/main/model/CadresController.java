package com.tataren.main.model;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tataren.main.domain.user.WebUser;
import com.tataren.main.service.SessionInfo;
import com.tataren.main.service.SessionInfoTatar;
import com.tataren.main.service.user.WebUserService;
import com.tataren.main.util.RequestUtils;
import com.tataren.main.util.WeChatValue;
import com.tataren.tatar.domain.TArea;
import com.tataren.tatar.domain.TCommy;
import com.tataren.tatar.domain.TDepartment;
import com.tataren.tatar.domain.TInout;
import com.tataren.tatar.domain.TInoutCollect;
import com.tataren.tatar.domain.TLeave;
import com.tataren.tatar.domain.TMember;
import com.tataren.tatar.domain.TSign;
import com.tataren.tatar.domain.TTask;
import com.tataren.tatar.domain.TTemp;
import com.tataren.tatar.domain.TTemp2018;
import com.tataren.tatar.domain.TUnit;
import com.tataren.tatar.domain.TUsers;
import com.tataren.tatar.domain.TView;
import com.tataren.tatar.domain.Texam;
import com.tataren.tatar.send.CommonUtil;
import com.tataren.tatar.send.Template;
import com.tataren.tatar.send.TemplateParam;

public class CadresController extends MultiActionController  {

	private WebUserService WebUserService;

	public WebUserService getWebUserService() {
		return WebUserService;
	}

	public void setWebUserService(WebUserService WebUserService) {
		this.WebUserService = WebUserService;
	}

	public ModelAndView weChatUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String userID = request.getParameter("userId");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("echostr");
		String echostr = request.getParameter("echostr");
		String token = "tataren";

		userID = "admin";
		echostr = "test";

		System.out.println(echostr);
		request.setAttribute("token", token);
		request.setAttribute("echostr", echostr);
		/**
		 * PrintWriter print;
		 * 
		 * print = response.getWriter(); print.write(echostr); print.flush();
		 **/
		TUsers webUser = (TUsers) WebUserService.findWebUsersByLogonID(userID);
		String locale = RequestUtils.getStringParameter(request, "locale");
		String treeName = "";
		SessionInfo
				.createInstance(request, webUser,
						"c".equals(locale) ? Locale.SIMPLIFIED_CHINESE
								: Locale.ENGLISH);

		request.getSession().setAttribute("treeName", treeName);
		request.getSession().setAttribute("locale", locale);
		request.getSession().setAttribute("webUser", webUser);
		request.getSession().setAttribute("userId", webUser.getLoginName());

		System.out.println("tatar");

		return null;
	}

	public ModelAndView getWeChatInformation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String apiurl = "https://api.weixin.qq.com/cgi-bin/token";
		String appUrl = SessionInfo.appUrl;
		String appid = SessionInfo.appid;
		String secret = SessionInfo.secret;
		String turl = String.format(
				"%s?grant_type=client_credential&appid=%s&secret=%s", apiurl,
				appid, secret);
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(turl);
		JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
		String result = null;

		HttpResponse res = client.execute(get);
		String responseContent = null; // 响应内容
		HttpEntity entity = res.getEntity();
		responseContent = EntityUtils.toString(entity, "UTF-8");
		JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();
		// 将json字符串转换为json对象
		System.out.println(turl);
		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			if (json.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
			} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
				result = json.get("access_token").getAsString();
			}
		}

		String redirect_uri = java.net.URLEncoder.encode(
				"https://"+appUrl+"/tatar_/tatar.do?method=test", "UTF-8");

		String basicUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
				+ appid
				+ "&redirect_uri="
				+ redirect_uri
				+ "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

		HttpGet basicGet = new HttpGet(basicUrl);
		HttpResponse bres = client.execute(basicGet);

		String bresponseContent = null; // 响应内容
		HttpEntity bentity = bres.getEntity();
		bresponseContent = EntityUtils.toString(bentity, "UTF-8").trim();
		JsonObject bjson = jsonparer.parse(bresponseContent).getAsJsonObject();

		if (bres.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			if (bjson.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
			} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
				result = bjson.get("code").getAsString();
			}
		}

		return null;
	}

	public ModelAndView jsInter(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String userID = "111";
		TUsers webUser = this.WebUserService.findWebUsersByLogonID(userID);// 数据库获取用户Id的信息
		String locale = RequestUtils.getStringParameter(request, "locale");
		String treeName = "";
		SessionInfo
				.createInstance(request, webUser,
						"c".equals(locale) ? Locale.SIMPLIFIED_CHINESE
								: Locale.ENGLISH);// 提供缓存

		request.getSession().setAttribute("treeName", treeName);
		request.getSession().setAttribute("locale", locale);
		request.getSession().setAttribute("TMember", webUser);
		request.getSession().setAttribute("userId", webUser.getLoginName());

		String signature = WebUserService.getSignature("");
		System.out.println("emsignature=" + signature);
		request.setAttribute("signature", signature);

		ModelAndView mnv = new ModelAndView();// 创建视图
		mnv.setViewName("../leave/leave-add-mobile");

		return mnv;
	}

	public ModelAndView weChatMobileUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String userID = request.getParameter("userId");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("echostr");
		String echostr = request.getParameter("echostr");
		String token = "tataren";

		userID = "111";

		System.out.println(echostr);
		request.setAttribute("token", token);
		request.setAttribute("echostr", echostr);

		PrintWriter print;

		print = response.getWriter();
		print.write(echostr);
		print.flush();

		TUsers webUser = this.WebUserService.findWebUsersByLogonID(userID);// 数据库获取用户Id的信息
		String locale = RequestUtils.getStringParameter(request, "locale");
		String treeName = "";
		SessionInfo
				.createInstance(request, webUser,
						"c".equals(locale) ? Locale.SIMPLIFIED_CHINESE
								: Locale.ENGLISH);// 提供缓存

		request.getSession().setAttribute("treeName", treeName);
		request.getSession().setAttribute("locale", locale);
		request.getSession().setAttribute("TMember", webUser);
		request.getSession().setAttribute("userId", webUser.getLoginName());

		System.out.println("tatar");
		ModelAndView mnv = new ModelAndView();// 创建视图
		mnv.setViewName("../leave/leave-add-moble");
		return mnv;
	}

	public ModelAndView webUserLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String userID = request.getParameter("userName");// 登录页面输入的用户
		userID = userID == null ? "" : userID.trim(); // 登录页面输入用户中删除空格

		String password = RequestUtils.getStringParameter(request, "password",
				"");// 登录页面输入的密码

		HttpSession session = request.getSession();// 获取会话
		String locale = RequestUtils.getStringParameter(request, "locale");// 本地缓存
		ModelAndView mnv = new ModelAndView();// 创建视图
		mnv.setViewName("../main/index");// 设置视图
		String treeName = "";// 提供缓存

		TUsers webUser = this.WebUserService.findWebUsersByLogonID(userID);// 数据库获取用户Id的信息
		// 判断数据库中有没有用户Id的信息，如果没有，直接返回，如果有信息则验证密码
		if (webUser != null) {// 不存在用户信息
			mnv.setViewName("../main/index");// 如果是医保用户的话跳转页面
		}
		if (webUser != null) {
			if (password.equals(webUser.getPwd()))// 测试密码是否正确
			{
				SessionInfo.createInstance(request, webUser,
						"c".equals(locale) ? Locale.SIMPLIFIED_CHINESE
								: Locale.ENGLISH);// 提供缓存

				request.getSession().setAttribute("treeName", treeName);// 放到缓存
				request.getSession().setAttribute("locale", locale);// 放到缓存
				request.getSession().setAttribute("webUser", webUser);// 放到缓存
				request.getSession().setAttribute("userId",
						webUser.getLoginName());// 放到缓存
				// request.setAttribute("userName", webUser.getLoginName());//
				// 放到缓存

				response.sendRedirect("http://"+SessionInfo.appUrl+"/main/index.jsp");
				//response.sendRedirect("http://localhost:8080/tatar/main/index.jsp");
			} else {
				mnv.addObject("login", "pwdError");// 提示密码错误
				mnv.setViewName("../main/login"); // 密码错误返回页面
			}
		} else {
			mnv.addObject("login", "nouser");// 提示无用户
			mnv.setViewName("../main/login");// 用户空返回页面
		}

		return mnv;
	}

	public ModelAndView webUserLogin2(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String userID = request.getParameter("userName");// 登录页面输入的用户

		userID = userID == null ? "" : userID.trim(); // 登录页面输入用户中删除空格

		String password = RequestUtils.getStringParameter(request, "password",
				"");// 登录页面输入的密码
		HttpSession session = request.getSession();// 获取会话
		String locale = RequestUtils.getStringParameter(request, "locale");// 本地缓存
		ModelAndView mnv = new ModelAndView();// 创建视图
		mnv.setViewName("../indexHuman");// 设置视图
		String treeName = "";// 提供缓存

		// TMember webUser =
		// this.WebUserService.findTMembersByLogonID(userID);// 数据库获取用户Id的信息
		TMember webUser = new TMember();
		webUser.setSid(1);
		webUser.setUsername("admin");
		webUser.setLoginName("admin");
		webUser.setPwd("123");
		webUser.setTrueName("admin");
		webUser.setPhoto("sss");
		webUser.setTleval("10");
		// 判断数据库中有没有用户Id的信息，如果没有，直接返回，如果有信息则验证密码
		if (webUser != null) {// 不存在用户信息
			mnv.setViewName("../indexHuman");// 如果是医保用户的话跳转页面
		}
		if (webUser != null) {
			if (password.equals(webUser.getPwd()))// 测试密码是否正确
			{
				SessionInfoTatar.createInstanceTMember(request, webUser, "c"
						.equals(locale) ? Locale.SIMPLIFIED_CHINESE
						: Locale.ENGLISH);// 提供缓存

				request.getSession().setAttribute("treeName", treeName);// 放到缓存
				request.getSession().setAttribute("locale", locale);// 放到缓存
				request.getSession().setAttribute("TMember", webUser);// 放到缓存
				request.getSession().setAttribute("userId",
						webUser.getLoginName());// 放到缓存
				// request.setAttribute("userName", webUser.getLoginName());//
				// 放到缓存
			} else {
				mnv.addObject("login", "pwdError");// 提示密码错误
				mnv.setViewName("../login"); // 密码错误返回页面
			}
		} else {
			mnv.addObject("login", "nouser");// 提示无用户
			mnv.setViewName("../login");// 用户空返回页面
		}
		return mnv;
	}

	public ModelAndView commyUserLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String userID = request.getParameter("userName");// 登录页面输入的用户
		userID = userID == null ? "" : userID.trim(); // 登录页面输入用户中删除空格

		String password = RequestUtils.getStringParameter(request, "password",
				"");// 登录页面输入的密码

		HttpSession session = request.getSession();// 获取会话
		String locale = RequestUtils.getStringParameter(request, "locale");// 本地缓存
		ModelAndView mnv = new ModelAndView();// 创建视图
		mnv.setViewName("../main/commyIndex");// 设置视图
		String treeName = "";// 提供缓存

		TUsers webUser = this.WebUserService.findWebUsersByLogonID(userID);// 数据库获取用户Id的信息
		// 判断数据库中有没有用户Id的信息，如果没有，直接返回，如果有信息则验证密码
		if (webUser != null) {// 不存在用户信息
			mnv.setViewName("../main/commyIndex");// 如果是医保用户的话跳转页面
		}
		if (webUser != null) {
			if (password.equals(webUser.getPwd()))// 测试密码是否正确
			{
				SessionInfo.createInstance(request, webUser,
						"c".equals(locale) ? Locale.SIMPLIFIED_CHINESE
								: Locale.ENGLISH);// 提供缓存

				request.getSession().setAttribute("treeName", treeName);// 放到缓存
				request.getSession().setAttribute("locale", locale);// 放到缓存
				request.getSession().setAttribute("webUser", webUser);// 放到缓存
				request.getSession().setAttribute("userId",
						webUser.getLoginName());// 放到缓存

				response.sendRedirect("http://localhost:8080/chapter/main/commyIndex.jsp");
			} else {
				mnv.addObject("login", "pwdError");// 提示密码错误
				mnv.setViewName("../main/commyLogin"); // 密码错误返回页面
			}
		} else {
			mnv.addObject("login", "nouser");// 提示无用户
			mnv.setViewName("../main/commyLogin");// 用户空返回页面
		}

		return mnv;
	}

	public ModelAndView loginout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SessionInfo sessionInfo = SessionInfo.getSessionInfo(request);

		request.getSession().invalidate();
		response.getWriter().print(
				"<script>top.location.href='" + request.getContextPath()
						+ "/index.jsp'; </script>");

		return null;
	}
	// normal code by tatar.

	public ModelAndView usersQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main/tatarUsersList");
		TUsers bean = new TUsers();

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");

		if (webUser.getRightt().equals("超级管理员")) {

		} else if (webUser.getRightt().equals("区管理员")) {
			bean.setRegionId(webUser.getRegionId());
		} else if (webUser.getRightt().equals("单位管理员")) {
			bean.setUnitId(webUser.getUnitId());
		} else if (webUser.getRightt().equals("科室管理员")) {
			bean.setDepartmentId(webUser.getDepartmentId());
		} else {
			bean.setSid(webUser.getSid());
		}
		List list = WebUserService.findByExample(bean);
		request.setAttribute("list", list);
		return mnv;
	}

	public ModelAndView usersEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sid = request.getParameter("sid");
		String unitName = new String(
				(request.getParameter("unitName")).getBytes("iso-8859-1"),
				"utf-8");
		String userName = new String(
				(request.getParameter("userName")).getBytes("iso-8859-1"),
				"utf-8");
		String remark = new String(
				(request.getParameter("remark")).getBytes("iso-8859-1"),
				"utf-8");
		String mobile = new String(
				(request.getParameter("mobile")).getBytes("iso-8859-1"),
				"utf-8");
		String departmentName = new String(
				(request.getParameter("departmentName")).getBytes("iso-8859-1"),
				"utf-8");
		String scoreOriginal = request.getParameter("scoreOriginal");
		String state = new String(
				(request.getParameter("state")).getBytes("iso-8859-1"), "utf-8");

		TUsers bean = new TUsers();
		bean.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(bean);
		bean = (TUsers) list.get(0);

		bean.setUnitName(unitName);

		bean.setRemark(remark);
		bean.setUserName(userName);
		bean.setMobile(mobile);
		bean.setDepartmentName(departmentName);
		bean.setRemark(remark);
		bean.setState(state);
		bean.setAccount(scoreOriginal);
		WebUserService.store(bean);

		return usersQuery(request, response);
	}

	public ModelAndView commyMemberQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main/commyUsersList");
		TCommy bean = new TCommy();

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");

		List list = WebUserService.findByExample(bean);
		request.setAttribute("list", list);
		return mnv;
	}

	public ModelAndView leaveQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main/tatarLeaveList");
		String yearId = request.getParameter("yearId");
		if(yearId==null){
			yearId="2019";
		}
		System.out.println("yeaaaaaaaaaaaaaaaar"+yearId);

		TLeave bean = new TLeave();
		/**
		if (webUser.getRightt().equals("超级管理员")) {

		} else if (webUser.getRightt().equals("区管理员")) {

		} else if (webUser.getRightt().equals("单位管理员")) {

		} else if (webUser.getRightt().equals("科室管理员")) {

		} else {
			bean.setUSERSID((webUser.getOpenId()));
		}
		**/
		String kindId=webUser.getRightt();
		String openId=webUser.getOpenId();
		String unitId = webUser.getUnitId();
		
		
		//bean.setLeaveState("正常");
		//List list = WebUserService.findByExample(bean);
	
		
		List list = WebUserService.getTLeave(yearId, openId, unitId, kindId);
		request.setAttribute("list", list);
		return mnv;
	}

	public ModelAndView leaveCollectQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main/tatarLeaveCollectList");
		String yearId = request.getParameter("yearId");
		System.out.println("yeaaaaaaaaaaaaaaaar"+yearId);
		TTemp bean = new TTemp();
		bean.setUnitName(webUser.getUnitName());
		if (webUser.getRightt().equals("超级管理员")) {

		} else if (webUser.getRightt().equals("区管理员")) {

		} else if (webUser.getRightt().equals("单位管理员")) {

		} else if (webUser.getRightt().equals("科室管理员")) {

		} else {
			bean.setUserSid(webUser.getOpenId());
		}
		List list = WebUserService.findByExample(bean);
		

		request.setAttribute("list", list);
		return mnv;
	}
	public ModelAndView leaveCollectQuery2018(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main/tatarLeaveCollectList2018");
		String yearId = request.getParameter("yearId");
		System.out.println("yeaaaaaaaaaaaaaaaar"+yearId);
		TTemp2018 bean = new TTemp2018();
		bean.setUnitName(webUser.getUnitName());
		if (webUser.getRightt().equals("超级管理员")) {

		} else if (webUser.getRightt().equals("区管理员")) {

		} else if (webUser.getRightt().equals("单位管理员")) {

		} else if (webUser.getRightt().equals("科室管理员")) {

		} else {
			bean.setUserSid(webUser.getOpenId());
		}
		List list = WebUserService.findByExample(bean);
		

		request.setAttribute("list", list);
		return mnv;
	}

	public ModelAndView affairQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../affairs/affair-list");
		TLeave bean = new TLeave();
		bean.setFLAGSTATE("1");
		bean.setTLEAVETYPE("公派");
		List list = WebUserService.findByExample(bean);
		request.setAttribute("list", list);
		return mnv;
	}

	public ModelAndView affairCreate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String userName = request.getParameter("userName");
		String userId = request.getParameter("userId");
		String unitName = request.getParameter("unitName");
		String mobile = request.getParameter("mobile");
		String leaveType = request.getParameter("leaveType");
		String image = request.getParameter("image");
		String startTime = request.getParameter("datemin");
		String endTime = request.getParameter("datemax");
		String remark = request.getParameter("remark");
		String photo = request.getParameter("photo");
		String tunitid = request.getParameter("tunitid");
		String flagState = "1";

		Date a1 = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);
		Date b1 = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);
		// 获取相减后天数
		long day = (a1.getTime() - b1.getTime()) / (24 * 60 * 60 * 1000);

		TLeave bean = new TLeave();
		bean.setUSERNAME(userName);
		bean.setUSERSID(userId);
		bean.setUNITNAME(unitName);
		bean.setMOBILE(mobile);
		bean.setTLEAVETYPE(leaveType);
		bean.setIMAGE(image);
		bean.setSTARTTIME(startTime);
		bean.setENDTIME(endTime);
		bean.setREMARK(remark);
		bean.setTUNITID(tunitid);
		bean.setFLAGSTATE(flagState);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());
		String nowtimes = format.format(c1.getTime());
		bean.setAPPLICATIONSTIME(nowtimes);
		bean.setFIRSTAPPROVALSTATE("待审批");
		bean.setSECONDAPPROVALSTATE("待审批");
		bean.setTHREEAPPROVALSTATE("待审批");
		bean.setDAYNUM(String.valueOf(day));
		bean.setPHOTO(photo);

		WebUserService.store(bean);

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../success");

		List list = WebUserService.findByExample(bean);
		request.setAttribute("list", list);
		return mnv;
	}

	public ModelAndView leaveCreate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		System.out.println(request.getCharacterEncoding());

		String userName = new String(
				(request.getParameter("userName")).getBytes("iso-8859-1"),
				"utf-8");

		String userId = new String(
				(request.getParameter("userId")).getBytes("iso-8859-1"),
				"utf-8");
		String unitName = new String(
				(request.getParameter("unitName")).getBytes("iso-8859-1"),
				"utf-8");
		String mobile = request.getParameter("mobile");
		String leaveType = new String(
				(request.getParameter("leaveType")).getBytes("iso-8859-1"),
				"utf-8");
		String image = request.getParameter("image");
		String startTime = request.getParameter("datemin");
		String endTime = request.getParameter("datemax");
		String remark = new String(
				(request.getParameter("remark")).getBytes("iso-8859-1"),
				"utf-8");
		String photo = request.getParameter("photo");
		String tunitid = request.getParameter("苏里德苏木");
		String flagState = "审批中";

		Date a1 = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);
		Date b1 = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);
		// 获取相减后天数
		long day = (a1.getTime() - b1.getTime()) / (24 * 60 * 60 * 1000);

		TLeave bean = new TLeave();
		bean.setUSERNAME(userName);
		bean.setUSERSID(userId);
		bean.setUNITNAME(unitName);
		bean.setMOBILE(mobile);
		bean.setTLEAVETYPE(leaveType);
		bean.setIMAGE(image);
		bean.setSTARTTIME(startTime);
		bean.setENDTIME(endTime);
		bean.setREMARK(remark);
		bean.setTUNITID(tunitid);
		bean.setFLAGSTATE(flagState);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());
		String nowtimes = format.format(c1.getTime());
		bean.setAPPLICATIONSTIME(nowtimes);
		bean.setFIRSTAPPROVALSTATE("待审批");
		bean.setSECONDAPPROVALSTATE("待审批");
		bean.setTHREEAPPROVALSTATE("待审批");
		bean.setDAYNUM(String.valueOf(day));
		bean.setPHOTO(photo);

		WebUserService.store(bean);

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../success");

		List list = WebUserService.findByExample(bean);
		request.setAttribute("list", list);
		return mnv;
	}

	public ModelAndView leaveChange(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");

		String sid = request.getParameter("sid");
		String level = request.getParameter("level");
		String kind = request.getParameter("kind");
		String remark = request.getParameter("remark");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());
		String nowtimes = format.format(c1.getTime());
		TLeave bean = new TLeave();
		bean.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(bean);
		bean = (TLeave) list.get(0);

		if (level.equals("1")) {
			bean.setFIRSTAPPROVALSTATE(kind);
			if (remark != null) {
				bean.setFIRSTAPPROVALREMARK(remark);
			}
			bean.setFIRSTAPPROVALDATE(nowtimes);
			bean.setFIRSTAPPROVALPERSON(webUser.getUserName());
		} else if (level.equals("2")) {
			bean.setSECONDAPPROVALSTATE(kind);
			if (remark != null) {
				bean.setSECONDAPPROVALREMARK(remark);
			}
			bean.setSECONDAPPROVALDATE(nowtimes);
			bean.setSECONDAPPROVALPERSON(webUser.getUserName());
		} else {
			bean.setTHREEAPPROVALSTATE(kind);
			if (remark != null) {
				bean.setTHREEAPPROVALREMARK(remark);
			}
			bean.setTHREEAPPROVALDATE(nowtimes);
			bean.setTHREEAPPROVALPERSON(webUser.getUserName());
		}

		WebUserService.store(bean);

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../success");

		List list2 = WebUserService.findByExample(bean);
		request.setAttribute("list", list2);
		return mnv;
	}

	public ModelAndView deleteLeave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");

		String sid = request.getParameter("sid");
		String level = request.getParameter("level");
		String kind = request.getParameter("kind");
		String remark = request.getParameter("remark");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());
		String nowtimes = format.format(c1.getTime());
		TLeave bean = new TLeave();
		bean.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(bean);
		bean = (TLeave) list.get(0);

		bean.setFLAGSTATE("0");

		WebUserService.store(bean);

		return leaveQuery(request, response);
	}

	// unit
	public ModelAndView unitQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUnit bean = new TUnit();
		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		if (webUser.getRightt().equals("超级管理员")) {

		} else if (webUser.getRightt().equals("区管理员")) {
			bean.setRegionId(webUser.getRegionId());
		} else {
			bean.setUnitId(webUser.getUnitId());
		}
		bean.setFlagState("正常");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main/tatarUnitList");
		List list = WebUserService.findByExample(bean);
		request.setAttribute("list", list);
		return mnv;
	}

	public ModelAndView unitMap(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sid3 = request.getParameter("sid3");
		String lnglat = request.getParameter("lnglat");
		TUnit bean = new TUnit();
		bean.setSid(Integer.valueOf(sid3));
		List list = WebUserService.findByExample(bean);
		bean = (TUnit) list.get(0);

		String[] ids = lnglat.split(",");
		bean.setLngId(ids[0]);
		bean.setLatId(ids[1]);

		WebUserService.store(bean);

		return unitQuery(request, response);
	}

	public ModelAndView unitCreate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");

		String unitName = new String(
				(request.getParameter("unitName")).getBytes("iso-8859-1"),
				"utf-8");
		String unitProperties = new String(
				(request.getParameter("unitProperties")).getBytes("iso-8859-1"),
				"utf-8");
		String organizationCode = new String(
				(request.getParameter("organizationCode"))
						.getBytes("iso-8859-1"),
				"utf-8");
		String tel = new String(
				(request.getParameter("tel")).getBytes("iso-8859-1"), "utf-8");
		String remark = new String(
				(request.getParameter("remark")).getBytes("iso-8859-1"),
				"utf-8");
		String linkMan = new String(
				(request.getParameter("linkMan")).getBytes("iso-8859-1"),
				"utf-8");
		String linkPhone = new String(
				(request.getParameter("linkPhone")).getBytes("iso-8859-1"),
				"utf-8");
		String scoreOriginal = request.getParameter("scoreOriginal");
		String state = new String(
				(request.getParameter("state")).getBytes("iso-8859-1"), "utf-8");

		TUnit bean = new TUnit();
		bean.setState(state);
		bean.setFlagState("正常");
		bean.setOrganizationCode(organizationCode);
		bean.setRemark(remark);
		bean.setUnitName(unitName);
		bean.setTel(tel);
		bean.setUnitProperties(unitProperties);
		bean.setLinkMan(linkMan);
		bean.setLinkPhone(linkPhone);
		bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
		bean.setRegionId(webUser.getRegionId());
		bean.setScoreOriginal(scoreOriginal);
		bean.setScoreCurrent(scoreOriginal);
		bean.setRegionName(webUser.getRegionName());

		WebUserService.store(bean);

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main/tatarUnitList");

		List list = WebUserService.findByExample(bean);
		request.setAttribute("list", list);
		return mnv;
	}

	public ModelAndView unitEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");

		String sid = request.getParameter("sid");
		String unitName = new String(
				(request.getParameter("unitName")).getBytes("iso-8859-1"),
				"utf-8");
		String unitProperties = new String(
				(request.getParameter("unitProperties")).getBytes("iso-8859-1"),
				"utf-8");
		String organizationCode = new String(
				(request.getParameter("organizationCode"))
						.getBytes("iso-8859-1"),
				"utf-8");
		String tel = new String(
				(request.getParameter("tel")).getBytes("iso-8859-1"), "utf-8");
		String remark = new String(
				(request.getParameter("remark")).getBytes("iso-8859-1"),
				"utf-8");
		String linkMan = new String(
				(request.getParameter("linkMan")).getBytes("iso-8859-1"),
				"utf-8");
		String linkPhone = new String(
				(request.getParameter("linkPhone")).getBytes("iso-8859-1"),
				"utf-8");
		String scoreOriginal = request.getParameter("scoreOriginal");
		String state = new String(
				(request.getParameter("state")).getBytes("iso-8859-1"), "utf-8");
		System.out.println("unitName00000000000000000" + unitName);
		TUnit bean = new TUnit();
		bean.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(bean);
		bean = (TUnit) list.get(0);

		bean.setUnitName(unitName);
		bean.setOrganizationCode(organizationCode);
		bean.setUnitProperties(unitProperties);
		bean.setTel(tel);
		bean.setRemark(remark);
		bean.setLinkMan(linkMan);
		bean.setLinkPhone(linkPhone);
		bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
		bean.setCreatedBy(webUser.getSid().toString() + "-"
				+ webUser.getUnitName());
		bean.setState(state);
		WebUserService.store(bean);

		return unitQuery(request, response);
	}

	public ModelAndView unitDelete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sid = request.getParameter("sid");
		TUnit bean = new TUnit();
		bean.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(bean);
		bean = (TUnit) list.get(0);
		bean.setFlagState("删除");
		WebUserService.store(bean);

		return unitQuery(request, response);
	}

	// end unit
	// start Department
	public ModelAndView departmentQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TDepartment bean = new TDepartment();
		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		System.out.println(webUser.getDepartmentId());

		if (webUser.getRightt().equals("超级管理员")) {

		} else if (webUser.getRightt().equals("区管理员")) {
			bean.setRegionId(webUser.getRegionId());
		} else if (webUser.getRightt().equals("单位管理员")) {
			bean.setUnitId(webUser.getUnitId());
		} else {
			bean.setSid(Integer.valueOf(webUser.getDepartmentId()));
		}
		bean.setFlagState("正常");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main/tatarDepartmentList");
		List list = WebUserService.findByExample(bean);
		request.setAttribute("list", list);
		return mnv;
	}

	public ModelAndView areaQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TArea bean = new TArea();
		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		System.out.println(webUser.getDepartmentId());

		if (webUser.getRightt().equals("超级管理员")) {

		} else if (webUser.getRightt().equals("区管理员")) {
			bean.setRegionId(webUser.getRegionId());
		} else if (webUser.getRightt().equals("单位管理员")) {
			bean.setUnitId(webUser.getUnitId());
		} else {
			bean.setUnitId(webUser.getUnitId());
		}
		bean.setFlagState("正常");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main/tatarAreaList");
		List list = WebUserService.findByExample(bean);
		request.setAttribute("list", list);
		return mnv;
	}
	public ModelAndView areaInOutQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TArea bean = new TArea();
		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		System.out.println(webUser.getDepartmentId());

		if (webUser.getRightt().equals("超级管理员")) {

		} else if (webUser.getRightt().equals("区管理员")) {
			bean.setRegionId(webUser.getRegionId());
		} else if (webUser.getRightt().equals("单位管理员")) {
			bean.setUnitId(webUser.getUnitId());
		} else {
			bean.setUnitId(webUser.getUnitId());
		}
		bean.setFlagState("正常");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main/tatarAreaLocationList");
		List list = WebUserService.findByExample(bean);
		request.setAttribute("list", list);
		return mnv;
	}
	public ModelAndView areaInOutMap(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String areaId = request.getParameter("areaId");
		TInout bean = new TInout();
		bean.setWEEK(areaId);
		List list = WebUserService.findByExample(bean);
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main/tatarAreaInOutQuery");

		request.setAttribute("list", list);
		return mnv;
	}
	public ModelAndView areaCreate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");

		String areaName = new String(
				(request.getParameter("areaName")).getBytes("iso-8859-1"),
				"utf-8");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		TArea bean = new TArea();
		bean.setFlagState("正常");
		bean.setRegionId(webUser.getRegionId());
		bean.setRegionName(webUser.getRegionName());
		bean.setUnitId(webUser.getUnitId());
		bean.setUnitName(webUser.getUnitName());
		bean.setAreaName(areaName);
		bean.setStartTime(startTime);
		bean.setEndTime(endTime);

		WebUserService.store(bean);

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main/tatarAreaList");

		List list = WebUserService.findByExample(bean);
		request.setAttribute("list", list);
		return mnv;
	}

	public ModelAndView areaEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sid = request.getParameter("sid2");
		System.out.println(sid + "555555");
		String areaName = new String(
				(request.getParameter("areaName")).getBytes("iso-8859-1"),
				"utf-8");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		TArea bean = new TArea();
		bean.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(bean);
		bean = (TArea) list.get(0);

		bean.setAreaName(areaName);
		bean.setStartTime(startTime);
		bean.setEndTime(endTime);

		WebUserService.store(bean);

		return areaQuery(request, response);
	}

	public ModelAndView areaMap(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sid = request.getParameter("sid3");
		String lnglat = request.getParameter("lnglat");
		TArea bean = new TArea();
		bean.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(bean);
		bean = (TArea) list.get(0);

		String[] ids = lnglat.split(",");
		bean.setLngId(ids[0]);
		bean.setLatId(ids[1]);

		WebUserService.store(bean);
		return areaQuery(request, response);
	}

	public ModelAndView areaDelete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sid = request.getParameter("sid");
		TArea bean = new TArea();
		bean.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(bean);
		bean = (TArea) list.get(0);
		bean.setFlagState("失效");
		WebUserService.store(bean);
		
		return areaQuery(request, response);
	}

	public ModelAndView departmentCreate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");

		String departmentName = new String(
				(request.getParameter("departmentName")).getBytes("iso-8859-1"),
				"utf-8");
		String tel = new String(
				(request.getParameter("tel")).getBytes("iso-8859-1"), "utf-8");
		String remark = new String(
				(request.getParameter("remark")).getBytes("iso-8859-1"),
				"utf-8");
		String linkMan = new String(
				(request.getParameter("linkMan")).getBytes("iso-8859-1"),
				"utf-8");
		String linkPhone = new String(
				(request.getParameter("linkPhone")).getBytes("iso-8859-1"),
				"utf-8");
		String scoreOriginal = request.getParameter("scoreOriginal");
		String state = new String(
				(request.getParameter("state")).getBytes("iso-8859-1"), "utf-8");

		TDepartment bean = new TDepartment();
		bean.setState(state);
		bean.setFlagState("正常");
		bean.setUnitId(webUser.getUnitId());
		bean.setRemark(remark);
		bean.setUnitName(webUser.getUnitName());
		bean.setTel(tel);
		bean.setRegionId(webUser.getRegionId());
		bean.setRegionName(webUser.getRegionName());
		bean.setLinkMan(linkMan);
		bean.setLinkPhone(linkPhone);
		bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
		bean.setScoreOriginal(scoreOriginal);
		bean.setScoreCurrent(scoreOriginal);
		bean.setDepartmentName(departmentName);

		WebUserService.store(bean);

		return departmentQuery(request, response);
	}

	public ModelAndView departmentEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");

		String sid = request.getParameter("sid");
		String departmentName = new String(
				(request.getParameter("departmentName")).getBytes("iso-8859-1"),
				"utf-8");
		String tel = new String(
				(request.getParameter("tel")).getBytes("iso-8859-1"), "utf-8");
		String remark = new String(
				(request.getParameter("remark")).getBytes("iso-8859-1"),
				"utf-8");
		String linkMan = new String(
				(request.getParameter("linkMan")).getBytes("iso-8859-1"),
				"utf-8");
		String linkPhone = new String(
				(request.getParameter("linkPhone")).getBytes("iso-8859-1"),
				"utf-8");
		String scoreOriginal = request.getParameter("scoreOriginal");
		String state = new String(
				(request.getParameter("state")).getBytes("iso-8859-1"), "utf-8");

		TDepartment bean = new TDepartment();
		bean.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(bean);
		bean = (TDepartment) list.get(0);

		bean.setDepartmentName(departmentName);

		bean.setTel(tel);
		bean.setRemark(remark);
		bean.setLinkMan(linkMan);
		bean.setLinkPhone(linkPhone);
		bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
		bean.setCreatedBy(webUser.getSid().toString() + "-"
				+ webUser.getUnitName());
		bean.setState(state);
		WebUserService.store(bean);

		return departmentQuery(request, response);
	}

	public ModelAndView departmentDelete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sid = request.getParameter("sid");
		TDepartment bean = new TDepartment();
		bean.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(bean);
		bean = (TDepartment) list.get(0);
		bean.setFlagState("删除");
		WebUserService.store(bean);

		return departmentQuery(request, response);
	}

	// end department
	// start task
	public ModelAndView taskQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TTask bean = new TTask();
		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		/**
		 * if (webUser.getRightt().equals("超级管理员")) {
		 * 
		 * } else if (webUser.getRightt().equals("区管理员")) {
		 * bean.setTaskReader(webUser.getRegionId()); } else if
		 * (webUser.getRightt().equals("单位管理员")) {
		 * bean.setUnitId(webUser.getUnitId()); } else {
		 * bean.setSid(Integer.valueOf(webUser.getDepartmentId())); }
		 **/
		bean.setFlagState("正常");
		bean.setTaskKind("派遣式");
		bean.setUnitId(webUser.getUnitId());
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main/tatarTaskList");
		List list = WebUserService.findByExample(bean);
		request.setAttribute("list", list);
		return mnv;
	}

	public ModelAndView taskCreate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");

		String taskName = new String(
				(request.getParameter("taskName")).getBytes("iso-8859-1"),
				"utf-8");
		String content = new String(
				(request.getParameter("content")).getBytes("iso-8859-1"),
				"utf-8");
		String unitId = new String(
				(request.getParameter("select0")).getBytes("iso-8859-1"),
				"utf-8");
		String department = new String(
				(request.getParameter("service_class1")).getBytes("iso-8859-1"),
				"utf-8");
		String users = new String(
				(request.getParameter("service_class2")).getBytes("iso-8859-1"),
				"utf-8");
		String deadLine = new String(
				(request.getParameter("deadLine")).getBytes("iso-8859-1"),
				"utf-8");
		String filePath = new String(
				(request.getParameter("filePath")).getBytes("iso-8859-1"),
				"utf-8");
		String linkMan = new String(
				(request.getParameter("linkMan")).getBytes("iso-8859-1"),
				"utf-8");
		String linkPhone = new String(
				(request.getParameter("linkPhone")).getBytes("iso-8859-1"),
				"utf-8");
		String score = request.getParameter("score");
		String fileId = SessionInfoTatar.fileId;
	    String fileTemp=System.currentTimeMillis()+"1";
		
	
	
		String readers = "-";
		TTask bean = new TTask();
		if (department.equals("所有科室")) {
			TUsers tempBean = new TUsers();
			tempBean.setUnitId(unitId);
			List list = WebUserService.findByExample(tempBean);
			for (int i = 0; i < list.size(); i++) {
				tempBean = (TUsers) list.get(i);
				readers = readers + tempBean.getSid() + "-";
				
				Template tem=new Template();  
			    tem.setTouser(tempBean.getOpenId());  // oQMBIwaHaXWaREwzoBXScKEfaBoM ogx_NvxGZAEnkDCeqTgKZd1t7NxE	    
			    tem.setTemplateId("cbIo-6bhVciJ1iqHIedNoLOTWdEL5LalcfrFKknyK7M");  
			    tem.setUrl("http://www.tataren.com/hurd_/hurd.do?method=firstMobileTask");  
		   
			    List<TemplateParam> data=new ArrayList<TemplateParam>();  
			    data.add(new TemplateParam("first",content,"#0044BB"));  
			    data.add(new TemplateParam("keyword1",webUser.getUnitName(),"#0044BB"));  
			    data.add(new TemplateParam("keyword2",linkMan,"#0044BB"));  
			    data.add(new TemplateParam("keyword3",linkPhone,"#0044BB"));  
			    tem.setTemplateParamList(data);  
			    String token =WeChatValue.getAccessToken();
			    String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
				requestUrl = requestUrl.replace("ACCESS_TOKEN", token);

				CommonUtil.httpsRequest(requestUrl, "POST",
						tem.toJSON());
						
			}
			bean.setDepartmentName("所有科室");
		} else {
			if (users.equals("所有人员")) {	
				System.out.println("***"+department);
				String departments[] = department.split("-");
				System.out.println("***"+departments[0]);
				TUsers tempBean = new TUsers();
				tempBean.setDepartmentId(departments[0]);
				
				List list = WebUserService.findByExample(tempBean);
				for (int i = 0; i < list.size(); i++) {
					tempBean = (TUsers) list.get(i);
					readers = readers + tempBean.getSid() + "-";
					System.out.println("***"+tempBean.getUserName());
					
					Template tem=new Template();  
				    tem.setTouser(tempBean.getOpenId());  // oQMBIwaHaXWaREwzoBXScKEfaBoM ogx_NvxGZAEnkDCeqTgKZd1t7NxE	    
				    tem.setTemplateId("cbIo-6bhVciJ1iqHIedNoLOTWdEL5LalcfrFKknyK7M");  
				    tem.setUrl("http://www.tataren.com/hurd_/hurd.do?method=firstMobileTask");  
			   
				    List<TemplateParam> data=new ArrayList<TemplateParam>();  
				    data.add(new TemplateParam("first",content,"#0044BB"));  
				    data.add(new TemplateParam("keyword1",webUser.getUnitName(),"#0044BB"));  
				    data.add(new TemplateParam("keyword2",linkMan,"#0044BB"));  
				    data.add(new TemplateParam("keyword3",linkPhone,"#0044BB"));  
				    tem.setTemplateParamList(data);  
				    String token =WeChatValue.getAccessToken();
				    String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
					requestUrl = requestUrl.replace("ACCESS_TOKEN", token);

					CommonUtil.httpsRequest(requestUrl, "POST",
							tem.toJSON());
							
				}

			} else {
				readers = readers + users;
				String userss[] = users.split("-");
				TUsers tempBean = new TUsers();
				tempBean.setSid(Integer.valueOf(userss[0]));
				List list = WebUserService.findByExample(tempBean);
				for (int i = 0; i < list.size(); i++) {
					tempBean = (TUsers) list.get(i);
				}
				Template tem=new Template();  
			    tem.setTouser(tempBean.getOpenId());  // oQMBIwaHaXWaREwzoBXScKEfaBoM ogx_NvxGZAEnkDCeqTgKZd1t7NxE	    
			    tem.setTemplateId("cbIo-6bhVciJ1iqHIedNoLOTWdEL5LalcfrFKknyK7M");  
			    tem.setUrl("http://www.tataren.com/hurd_/hurd.do?method=firstMobileTask");  
		   
			    List<TemplateParam> data=new ArrayList<TemplateParam>();  
			    data.add(new TemplateParam("first",content,"#0044BB"));  
			    data.add(new TemplateParam("keyword1",webUser.getUnitName(),"#0044BB"));  
			    data.add(new TemplateParam("keyword2",linkMan,"#0044BB"));  
			    data.add(new TemplateParam("keyword3",linkPhone,"#0044BB"));  
			    tem.setTemplateParamList(data);  
			    String token =WeChatValue.getAccessToken();
			    String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
				requestUrl = requestUrl.replace("ACCESS_TOKEN", token);

				CommonUtil.httpsRequest(requestUrl, "POST",
						tem.toJSON());
			}
		}
		if(!filePath.equals("")){
			if(fileId.substring(0, 7).equals(fileTemp.substring(0, 7))){
				bean.setFileId(fileId);
			}
		}
		
		System.out.println(fileId);
		System.out.println(filePath);
		System.out.println(fileTemp);
		bean.setTaskName(taskName);
		bean.setUnitId(unitId);
		bean.setRegionId(webUser.getRegionId());
		bean.setContent(content);
		bean.setScore(score);
		bean.setDeadLine(deadLine);
		bean.setReaderIds(readers);
		bean.setLinkMan(linkMan);
		bean.setTel(linkPhone);
		bean.setTaskKind("派遣式");
		bean.setFlagState("正常");
		bean.setState("生效");
		
		bean.setCreatedBy(webUser.getSid().toString());

		WebUserService.store(bean);

		return taskQuery(request, response);
	}

	public ModelAndView taskCreatePage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");

		String select0 = new String(
				(request.getParameter("select0")).getBytes("iso-8859-1"),
				"utf-8");
		TDepartment bean = new TDepartment();
		bean.setUnitId(select0);
		JSONArray jsonn = new JSONArray();
		try {
			List list = WebUserService.findByExample(bean);
			String[] str = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				bean = (TDepartment) list.get(i);
				jsonn.put(i, bean.getSid() + "-" + bean.getDepartmentName());
				System.out.println(bean.getDepartmentName());
			}
			String json = jsonn.toString();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(json.toString());
			System.out.println(json + "555");

		} catch (Exception e) {
			e.printStackTrace();
		}
		// WebUserService.store(bean);

		return null;
	}

	public ModelAndView taskCreatePage2(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");

		String select0 = new String(
				(request.getParameter("service_class1")).getBytes("iso-8859-1"),
				"utf-8");
		TUsers bean = new TUsers();
		String[] value = select0.split("-");
		bean.setDepartmentId(value[0]);
		// PrintWriter out = response.getWriter();
		JSONArray jsonn = new JSONArray();
		try {
			List list = WebUserService.findByExample(bean);
			String[] str = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				bean = (TUsers) list.get(i);
				jsonn.put(i, bean.getSid() + "-" + bean.getUserName());
			}
			String json = jsonn.toString();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		// WebUserService.store(bean);

		return null;
	}

	public ModelAndView taskEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");

		String sid = request.getParameter("sid");
		String taskName = new String(
				(request.getParameter("taskName")).getBytes("iso-8859-1"),
				"utf-8");
		String content = new String(
				(request.getParameter("content")).getBytes("iso-8859-1"),
				"utf-8");
		String taskKind = new String(
				(request.getParameter("taskKind")).getBytes("iso-8859-1"),
				"utf-8");
		String taskReader = new String(
				(request.getParameter("taskReader")).getBytes("iso-8859-1"),
				"utf-8");
		String readerIds = new String(
				(request.getParameter("readerIds")).getBytes("iso-8859-1"),
				"utf-8");
		String deadLine = new String(
				(request.getParameter("deadLine")).getBytes("iso-8859-1"),
				"utf-8");
		String score = request.getParameter("scor");
		String state = new String(
				(request.getParameter("state")).getBytes("iso-8859-1"), "utf-8");

		TTask bean = new TTask();
		bean.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(bean);
		bean = (TTask) list.get(0);

		bean.setTaskKind(taskKind);
		bean.setTaskName(taskName);
		bean.setTaskReader(taskReader);
		bean.setContent(content);
		bean.setScore(score);
		bean.setDeadLine(deadLine);
		bean.setState(state);
		bean.setReaderIds(readerIds);
		bean.setCreatedBy(webUser.getSid().toString());
		WebUserService.store(bean);

		return taskQuery(request, response);
	}

	public ModelAndView taskDelete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sid = request.getParameter("sid");
		TTask bean = new TTask();
		bean.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(bean);
		bean = (TTask) list.get(0);
		bean.setFlagState("删除");
		WebUserService.store(bean);

		return taskQuery(request, response);
	}

	// end task
	
	// start notice
		public ModelAndView noticeQuery(HttpServletRequest request,
				HttpServletResponse response) throws Exception {

			TTask bean = new TTask();
			TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
			/**
			 * if (webUser.getRightt().equals("超级管理员")) {
			 * 
			 * } else if (webUser.getRightt().equals("区管理员")) {
			 * bean.setTaskReader(webUser.getRegionId()); } else if
			 * (webUser.getRightt().equals("单位管理员")) {
			 * bean.setUnitId(webUser.getUnitId()); } else {
			 * bean.setSid(Integer.valueOf(webUser.getDepartmentId())); }
			 **/
			bean.setFlagState("正常");
			bean.setTaskKind("通知式");
			bean.setUnitId(webUser.getUnitId());
			ModelAndView mnv = new ModelAndView();
			mnv.setViewName("../main/tatarNoticeList");
			List list = WebUserService.findByExample(bean);
			request.setAttribute("list", list);
			return mnv;
		}

		public ModelAndView noticeCreate(HttpServletRequest request,
				HttpServletResponse response) throws Exception {

			TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");

			String taskName = new String(
					(request.getParameter("taskName")).getBytes("iso-8859-1"),
					"utf-8");
			String content = new String(
					(request.getParameter("content")).getBytes("iso-8859-1"),
					"utf-8");
			
			String deadLine = new String(
					(request.getParameter("deadLine")).getBytes("iso-8859-1"),
					"utf-8");
		
		
		
			TTask bean = new TTask();
			
			
		
			bean.setTaskName(taskName);
			bean.setUnitId(webUser.getUnitId());
			bean.setRegionId(webUser.getRegionId());
			bean.setContent(content);
			bean.setDeadLine(deadLine);
			bean.setTaskKind("通知式");
			bean.setFlagState("正常");
			bean.setState("生效");
			
			bean.setCreatedBy(webUser.getSid().toString());

			WebUserService.store(bean);
			
			TUsers users = new TUsers();
			users.setUnitId(webUser.getUnitId());
			users.setState("正常");
			List list = WebUserService.findByExample(users);
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				TUsers beanUser = (TUsers) iter.next();
				Template tem=new Template();  
			    tem.setTouser(beanUser.getOpenId());  // oQMBIwaHaXWaREwzoBXScKEfaBoM ogx_NvxGZAEnkDCeqTgKZd1t7NxE	    
			    tem.setTemplateId("tE9xL-eOFe-TOxXMDJmsjYY1cOlN_eHY8ePGCAb30lM");  
			    tem.setUrl("");  
		   
			    List<TemplateParam> data=new ArrayList<TemplateParam>();  
			    data.add(new TemplateParam("first","发布通知","#FF3333"));  
			    data.add(new TemplateParam("keyword1",taskName,"#0044BB"));  
			    data.add(new TemplateParam("keyword2",content,"#0044BB"));  
			    data.add(new TemplateParam("keyword3",deadLine,"#0044BB"));  
			    data.add(new TemplateParam("keyword4","乌兰陶勒盖镇人民政府","#0044BB")); 
			    data.add(new TemplateParam("Remark","","#AAAAAA"));  
			   		    tem.setTemplateParamList(data);  
			    String token =WeChatValue.getAccessToken();
			    String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
				requestUrl = requestUrl.replace("ACCESS_TOKEN", token);

				CommonUtil.httpsRequest(requestUrl, "POST",
						tem.toJSON());		
			}
			
			

			return noticeQuery(request, response);
		}

		
		// end new
	public ModelAndView unitUpdate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sid = request.getParameter("sid");
		String unitName = request.getParameter("unitName");
		String unitProperties = request.getParameter("unitProperties");
		String organizationCode = request.getParameter("organizationCode");
		String address = request.getParameter("address");
		String remark = request.getParameter("remark");
		String linkMan = request.getParameter("linkMan");
		String linkPhone = request.getParameter("linkPhone");

		TUnit tu = new TUnit();
		tu.setSid(Integer.valueOf(sid));
		tu.setAddress(address);
		tu.setOrganizationCode(organizationCode);
		tu.setRemark(remark);
		tu.setUnitName(unitName);
		tu.setUnitProperties(unitProperties);
		tu.setLinkMan(linkMan);
		tu.setLinkPhone(linkPhone);
		tu.setCreateTime(new Timestamp(System.currentTimeMillis()));

		WebUserService.store(tu);

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../success");

		List list = WebUserService.findByExample(tu);
		request.setAttribute("list", list);
		return mnv;
	}

	public ModelAndView memberShow(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sid = request.getParameter("sid");

		TMember bean = new TMember();
		bean.setSid(Integer.valueOf(sid));

		List list = WebUserService.findByExample(bean);
		bean = (TMember) list.get(0);

		ModelAndView mnv = new ModelAndView();
		request.setAttribute("bean", bean);
		mnv.setViewName("../member/member-show");
		return mnv;
	}

	public ModelAndView memberChangePassword(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sid = request.getParameter("sid");
		String pwd = request.getParameter("new-password");

		TUsers bean = new TUsers();
		bean.setSid(Integer.valueOf(sid));

		List list = WebUserService.findByExample(bean);
		bean = (TUsers) list.get(0);
		bean.setPwd(pwd);

		WebUserService.store(bean);

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../success");

		return mnv;
	}

	public ModelAndView memberChangeRole(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sid = request.getParameter("sid");
		String tleval = request.getParameter("tlevel");

		TUsers bean = new TUsers();
		bean.setSid(Integer.valueOf(sid));

		List list = WebUserService.findByExample(bean);
		bean = (TUsers) list.get(0);
		bean.setRightt(tleval);

		WebUserService.store(bean);

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../success");

		return mnv;
	}

	public ModelAndView memberLinkEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sid = request.getParameter("sid");

		TUsers bean = new TUsers();
		bean.setSid(Integer.valueOf(sid));

		List list = WebUserService.findByExample(bean);
		bean = (TUsers) list.get(0);

		ModelAndView mnv = new ModelAndView();
		request.setAttribute("bean", bean);
		mnv.setViewName("../member/member-edit");
		return mnv;
	}

	public ModelAndView memberQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../member/member-list");
		TUsers tm = new TUsers();
		List list = WebUserService.findByExample(tm);
		request.setAttribute("list", list);
		TUnit tu = new TUnit();
		List lst = WebUserService.findByExample(tu);

		String str = "[";
		if (lst != null && lst.size() != 0) {
			for (Iterator iter = lst.iterator(); iter.hasNext();) {
				TUnit bean = (TUnit) iter.next();
				str += "\"" + bean.getUnitName() + "\"" + "  ,";
			}
		}
		str = str.substring(0, str.length() - 1);
		str += "]";

		request.setAttribute("str", str);
		return mnv;
	}

	public ModelAndView memberQueryRole(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../member/member-role");
		TUsers tm = new TUsers();
		List list = WebUserService.findByExample(tm);
		request.setAttribute("list", list);
		TUnit tu = new TUnit();
		List lst = WebUserService.findByExample(tu);

		String str = "[";
		if (lst != null && lst.size() != 0) {
			for (Iterator iter = lst.iterator(); iter.hasNext();) {
				TUnit bean = (TUnit) iter.next();
				str += "\"" + bean.getUnitName() + "\"" + "  ,";
			}
		}
		str = str.substring(0, str.length() - 1);
		str += "]";

		request.setAttribute("str", str);
		return mnv;
	}

	public ModelAndView memberCreate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String autocomplete = request.getParameter("autocomplete");
		String userName = request.getParameter("userName");
		String identification = request.getParameter("identification");
		String birthPlace = request.getParameter("birthPlace");
		String sex = request.getParameter("sex");
		String nation = request.getParameter("nation");
		String establishmentType = request.getParameter("establishmentType");
		String preparationUnit = request.getParameter("preparationUnit");
		String positions = request.getParameter("positions");
		String politicalStatus = request.getParameter("politicalStatus");
		String joinPoliticalTime = request.getParameter("joinPoliticalTime");
		String timeToWork = request.getParameter("timeToWork");
		String timeToWorkArea = request.getParameter("timeToWorkArea");
		String joinWorkWay = request.getParameter("joinWorkWay");
		String oldDepartment = request.getParameter("oldDepartment");
		String firstDegree = request.getParameter("firstDegree");
		String firstMajor = request.getParameter("firstMajor");
		String firstShcool = request.getParameter("firstShcool");
		String finalDegree = request.getParameter("finalDegree");
		String finalMajor = request.getParameter("finalMajor");
		String finalGraduationTime = request
				.getParameter("finalGraduationTime");
		String finalShcool = request.getParameter("finalShcool");
		String photo = request.getParameter("photo");
		String unitId = request.getParameter("unitId");
		String departmentId = request.getParameter("departmentId");
		String department = request.getParameter("department");

		String birthday = identification.substring(6, 14);

		TMember tm = new TMember();
		tm.setUnit(autocomplete);
		tm.setUsername(userName);
		tm.setIdentification(identification);
		tm.setBirthPlace(birthPlace);
		tm.setSex(sex);
		tm.setNation(nation);
		tm.setEstablishmentType(establishmentType);
		tm.setPreparationUnit(preparationUnit);
		tm.setPositions(positions);
		tm.setPoliticalStatus(politicalStatus);
		tm.setTimeToWork(timeToWorkArea);
		tm.setTimeToWorkArea(timeToWorkArea);
		tm.setJoinPoliticalTime(joinPoliticalTime);
		tm.setJoinWorkWay(joinWorkWay);
		tm.setOldDepartment(oldDepartment);
		tm.setFinalDegree(finalDegree);
		tm.setFinalMajor(finalMajor);
		tm.setFinalShcool(finalShcool);
		tm.setFinalGraduationTime(finalGraduationTime);
		tm.setFirstDegree(firstDegree);
		tm.setFirstMajor(firstMajor);
		tm.setFirstShcool(firstShcool);
		tm.setPhoto(photo);
		tm.setTimeToWork(timeToWork);
		tm.setBirthday(birthday);
		tm.setUnitId(unitId);
		tm.setDepartment(department);
		tm.setDepartmentId(departmentId);

		WebUserService.store(tm);

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../success");

		return mnv;
	}

	public ModelAndView memberEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sid = request.getParameter("sid");
		String autocomplete = request.getParameter("autocomplete");
		String userName = request.getParameter("userName");
		String identification = request.getParameter("identification");
		String birthPlace = request.getParameter("birthPlace");
		String sex = request.getParameter("sex");
		String nation = request.getParameter("nation");
		String establishmentType = request.getParameter("establishmentType");
		String preparationUnit = request.getParameter("preparationUnit");
		String positions = request.getParameter("positions");
		String politicalStatus = request.getParameter("politicalStatus");
		String joinPoliticalTime = request.getParameter("joinPoliticalTime");
		String timeToWork = request.getParameter("timeToWork");
		String timeToWorkArea = request.getParameter("timeToWorkArea");
		String joinWorkWay = request.getParameter("joinWorkWay");
		String oldDepartment = request.getParameter("oldDepartment");
		String firstDegree = request.getParameter("firstDegree");
		String firstMajor = request.getParameter("firstMajor");
		String firstShcool = request.getParameter("firstShcool");
		String finalDegree = request.getParameter("finalDegree");
		String finalMajor = request.getParameter("finalMajor");
		String finalGraduationTime = request
				.getParameter("finalGraduationTime");
		String finalShcool = request.getParameter("finalShcool");
		String photo = request.getParameter("photo");

		String birthday = identification.substring(6, 14);

		TMember tm = new TMember();
		tm.setSid(Integer.valueOf(sid));
		tm.setUnit(autocomplete);
		tm.setUsername(userName);
		tm.setIdentification(identification);
		tm.setBirthPlace(birthPlace);
		tm.setSex(sex);
		tm.setNation(nation);
		tm.setEstablishmentType(establishmentType);
		tm.setPreparationUnit(preparationUnit);
		tm.setPositions(positions);
		tm.setPoliticalStatus(politicalStatus);
		tm.setTimeToWork(timeToWorkArea);
		tm.setTimeToWorkArea(timeToWorkArea);
		tm.setJoinPoliticalTime(joinPoliticalTime);
		tm.setJoinWorkWay(joinWorkWay);
		tm.setOldDepartment(oldDepartment);
		tm.setFinalDegree(finalDegree);
		tm.setFinalMajor(finalMajor);
		tm.setFinalShcool(finalShcool);
		tm.setFinalGraduationTime(finalGraduationTime);
		tm.setFirstDegree(firstDegree);
		tm.setFirstMajor(firstMajor);
		tm.setFirstShcool(firstShcool);
		tm.setPhoto(photo);
		tm.setTimeToWork(timeToWork);
		tm.setBirthday(birthday);

		WebUserService.store(tm);

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../success");

		return mnv;
	}

	public ModelAndView memberPhotoAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//String filepath = "D:/app/Workspaces/tatar/WebRoot/main/upload"
		//		+ java.io.File.separator;
		String filepath = "/usr/local/tomcat/project/tatar/upload"
				+ java.io.File.separator;
		String filename = "";
		String type = "";
		ServletInputStream in = request.getInputStream();
		byte[] buf = new byte[4048];
		int len = in.readLine(buf, 0, buf.length);
		String f = new String(buf, 0, len - 1);
		while ((len = in.readLine(buf, 0, buf.length)) != -1) {
			filename = new String(buf, 0, len);
			int j = filename.lastIndexOf("\"");
			int p = filename.lastIndexOf(".");
			// 文件类型
			
			int jj = filename.lastIndexOf(".");
			int pp = filename.lastIndexOf("filename=")+10;
			String fileId = filename.substring(pp,jj);
			type = filename.substring(p, j);
			System.out.println(type+"3333"+filename+"5555"+fileId);
			// 文件名称
			filename = System.currentTimeMillis() + type;

			DataOutputStream fileStream = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream(filepath
							+ filename)));

			len = in.readLine(buf, 0, buf.length);
			len = in.readLine(buf, 0, buf.length);
			while ((len = in.readLine(buf, 0, buf.length)) != -1) {
				String tempf = new String(buf, 0, len - 1);
				if (tempf.equals(f) || tempf.equals(f + "--")) {
					break;
				} else {
					// 写入
					fileStream.write(buf, 0, len);
				}
			}
			fileStream.close();
		}
		PrintWriter out = response.getWriter();
		String result = filename;
		SessionInfoTatar.fileId=filename;
		System.out.println("666666666");
		System.out.println(SessionInfoTatar.fileId);
		//out.print(result);
		out.close();
		in.close();
		return null;
	}

	public ModelAndView updatePassword(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers wu = (TUsers) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../webuser/update-password");
		String sid = request.getParameter("sid");
		String oldpwd = request.getParameter("oldpwd");
		String password = request.getParameter("password");
		WebUser webUser = WebUserService.getWebUser(Integer.valueOf(sid));
		if (!webUser.getPwd().equals(oldpwd)) {
			mnv.setViewName("../webuser/update-password");
			request.setAttribute("error", "旧密码错误!");
			return mnv;
		} else {
			webUser.setPwd(password);
			request.setAttribute("error", "密码修改成功!");
			WebUserService.store(webUser);
			return mnv;
		}

	}

	public ModelAndView queryInOut(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers wu = (TUsers) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main/tatarInOutQuery");
		String dayTime = request.getParameter("dayTime");
		String kindId = request.getParameter("kindId");
		String unitId = wu.getUnitId();
		TInout ti = new TInout();
		List list = WebUserService.getTInoutByKindId(kindId, dayTime,
				wu.getUnitId());
		request.setAttribute("list", list);
		request.setAttribute("dayTime", dayTime);
		request.setAttribute("kindId", kindId);
		return mnv;

	}
	public ModelAndView queryInOutLeave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers wu = (TUsers) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main/tatarLeaveInOutQuery");
		String dayTime = request.getParameter("dayTime");
		String kindId = request.getParameter("kindId");
		String unitId = wu.getUnitId();
		TInout ti = new TInout();
		List list = WebUserService.getTInoutByKindIdLeave(kindId, dayTime,
				wu.getUnitId());
		request.setAttribute("list", list);
		request.setAttribute("dayTime", dayTime);
		request.setAttribute("kindId", kindId);
		return mnv;

	}
	public ModelAndView InOutQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TInout bean = new TInout();
		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");

		if (webUser.getRightt().equals("工作人员")) {
			bean.setUSERSID(webUser.getOpenId());
		} else {
			bean.setUNITID(webUser.getUnitId());
		}

		ModelAndView mnv = new ModelAndView();
		TUnit unitBean = new TUnit();
		unitBean.setSid(Integer.valueOf(webUser.getUnitId()));
		List unitList = WebUserService.findByExample(unitBean);
		unitBean = (TUnit) unitList.get(0);
		mnv.setViewName("../main/tatarInOutDay");
		if (unitBean.getUnitProperties().equals("两签")) {
			mnv.setViewName("../main/tatarInOutDayHalf");
		}

		String startTime = request.getParameter("start");
		if (startTime == null) {
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			/* 日期格式化 yyyy-MM-dd M必须大写 */
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(date);
			bean.setDAYTIME(dateString);

		} else {
			bean.setDAYTIME(startTime);
		}

		List list = WebUserService.findByExample(bean);
		request.setAttribute("list", list);

		return mnv;
	}

	public ModelAndView InOutCollectQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		TInoutCollect bean = new TInoutCollect();
		String readerId = "";
		String kindId = "1";
		String unitKind = "4";
		TUnit unitBean = new TUnit();
		unitBean.setSid(Integer.valueOf(webUser.getUnitId()));
		List unitlist = WebUserService.findByExample(unitBean);
		unitBean = (TUnit) unitlist.get(0);
		if (unitBean.getUnitProperties().equals("两签")) {
			unitKind = "2";
		}

		if (webUser.getRightt().equals("工作人员")) {
			kindId = "2";
			readerId = webUser.getOpenId();
		} else {
			readerId = webUser.getUnitId();
		}
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main/tatarInOutCollect");

		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		List list = WebUserService.getTInoutCollect(startTime, endTime, kindId,
				readerId, unitKind);
		request.setAttribute("list", list);

		return mnv;
	}

	public ModelAndView InOutQuerySingel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String mapId = "[";
		String labelId = "['";

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main/tatarInOutSingleMap");
		TInout bean = new TInout();
		String daytime = request.getParameter("daytime");
		String usersid = request.getParameter("usersid");
		bean.setUSERSID(usersid);
		bean.setDAYTIME(daytime);

		List list = WebUserService.findByExample(bean);
		bean = (TInout) list.get(0);
		if (bean.getFIRSTCHECKSTATE().equals("已签到")) {
			mapId = mapId + bean.getFIRSTCHECKLONGID() + ","
					+ bean.getFIRSTCHECKLATIID() + "],[";
			labelId = labelId + bean.getUSERNAME() + ","
					+ bean.getFIRSTCHECKTIME().substring(11, 19) + "'],['";
		}
		if (bean.getTWOCHECKSTATE().equals("已签退")) {
			mapId = mapId + bean.getTWOCHECKLONGID() + ","
					+ bean.getTWOCHECKLATIID() + "],[";
			labelId = labelId + bean.getUSERNAME() + ","
					+ bean.getTWOCHECKTIME().substring(11, 19) + "'],['";
		}
		if (bean.getTHREECHECKSTATE().equals("已签到")) {
			mapId = mapId + bean.getTHREECHECKLONGID() + ","
					+ bean.getTHREECHECKLATIID() + "],[";
			labelId = labelId + bean.getUSERNAME() + ","
					+ bean.getTHREECHECKTIME().substring(11, 19) + "'],['";
		}
		if (bean.getFOURCHECKSTATE().equals("已签退")) {
			mapId = mapId + bean.getFOURCHECKLONGID() + ","
					+ bean.getFOURCHECKLATIID() + "],[";
			labelId = labelId + bean.getUSERNAME() + ","
					+ bean.getFOURCHECKTIME().substring(11, 19) + "'],['";
		}

		mapId = mapId.substring(0, mapId.length() - 2);
		labelId = labelId.substring(0, labelId.length() - 2);
		System.out.println("))))))))))))))))))))))" + mapId);
		System.out.println("))))))))))))))))))))))" + labelId);
		request.setAttribute("bean", bean);
		request.setAttribute("mapId", mapId);
		request.setAttribute("labelId", labelId);
		request.setAttribute("list", list);

		return mnv;
	}

	public ModelAndView queryInOutCount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers wu = (TUsers) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../leave/inOutCount-list");
		TView tv = new TView();
		String startTime = request.getParameter("startTimeH");
		String endTime = request.getParameter("endTimeH");

		tv.setStartTime(startTime);
		tv.setEndTime(endTime);
		System.out.println(startTime + endTime);
		List list = WebUserService.findByExample(tv);
		request.setAttribute("list", list);

		return mnv;
	}

	public ModelAndView queryInOutSingle(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers wu = (TUsers) request.getSession().getAttribute("webUser");
		String mapId = "[";
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../leave/inOut-show");
		String userId = request.getParameter("userId");
		String startTime = request.getParameter("startTime");
		String[] startTimeStr = startTime.split("-");
		int yearStr = Integer.valueOf(startTimeStr[0]).intValue();
		int monthStr = Integer.valueOf(startTimeStr[1]).intValue();
		int dayStr = Integer.valueOf(startTimeStr[2]).intValue();
		Calendar cal = Calendar.getInstance();
		cal.set(yearStr, monthStr - 1, dayStr);
		List list = new ArrayList();
		for (int j = 0; j < 5; j++) {
			/* 获取当前日历的日期的星期数（1:星期天） */
			int week_index = cal.get(Calendar.DAY_OF_WEEK);
			Date date = cal.getTime();
			/* 日期格式化 yyyy-MM-dd M必须大写 */
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(date);
			/* 将日历日期推后1天 */
			cal.add(cal.DATE, +1);

			List listLeave = WebUserService.findTSign(dateString, userId);
			TSign ts = (TSign) new TSign();
			ts.setWeekName(dateString);
			if (!listLeave.isEmpty()) {
				TLeave tl = (TLeave) listLeave.get(0);
				ts.setWorkState(tl.getTLEAVETYPE() + "(" + tl.getFLAGSTATE()
						+ ")");
			} else {
				ts.setWorkState("工作");
			}

			List listInOut = WebUserService.getTInout(userId, dateString);
			if (!listInOut.isEmpty()) {

				TInout beanIn = (TInout) listInOut.get(0);
				ts.setFirstState(beanIn.getFIRSTCHECKSTATE());
				ts.setFirstAddress(beanIn.getFIRSTCHECKLATIID() + ","
						+ beanIn.getFIRSTCHECKLONGID());
				ts.setTwoState(beanIn.getTWOCHECKSTATE());
				ts.setTwoAddress(beanIn.getTWOCHECKLATIID() + ","
						+ beanIn.getTWOCHECKLONGID());
				ts.setThreeState(beanIn.getTHREECHECKSTATE());
				ts.setThreeAddress(beanIn.getTHREECHECKLATIID() + ","
						+ beanIn.getTHREECHECKLONGID());
				ts.setFourState(beanIn.getFOURCHECKSTATE());
				ts.setFourAddress(beanIn.getFOURCHECKLATIID() + ","
						+ beanIn.getFOURCHECKLONGID());
				if (beanIn.getFIRSTCHECKSTATE().equals("已签到")) {
					mapId = mapId + beanIn.getFIRSTCHECKLONGID() + ","
							+ beanIn.getFIRSTCHECKLATIID() + "],[";
				}
				if (beanIn.getTWOCHECKSTATE().equals("已签退")) {
					mapId = mapId + beanIn.getTWOCHECKLONGID() + ","
							+ beanIn.getTWOCHECKLATIID() + "],[";
				}
				if (beanIn.getTHREECHECKSTATE().equals("已签到")) {
					mapId = mapId + beanIn.getTHREECHECKLONGID() + ","
							+ beanIn.getTHREECHECKLATIID() + "],[";
				}
				if (beanIn.getFOURCHECKSTATE().equals("已签退")) {
					mapId = mapId + beanIn.getFOURCHECKLONGID() + ","
							+ beanIn.getFOURCHECKLATIID() + "],[";
				}
			} else {

				ts.setFirstState("未签到");
				ts.setTwoState("未签到");
				ts.setThreeState("未签到");
				ts.setFourState("未签到");
			}
			list.add(ts);
		}
		mapId = mapId.substring(0, mapId.length() - 2);
		TUsers tm = new TUsers();

		List listTm = WebUserService.getTMember(userId);
		tm = (TUsers) listTm.get(0);
		request.setAttribute("userName", tm.getChatName());
		request.setAttribute("trueName", tm.getUserName());
		request.setAttribute("unitName", tm.getUnitName());
		request.setAttribute("departMent", tm.getDepartmentName());
		request.setAttribute("headUrl", tm.getHeadUrl());
		request.setAttribute("mapId", mapId);
		request.setAttribute("list", list);
		return mnv;

	}

	public ModelAndView rightQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main/tatarRightList");
		TUsers bean = new TUsers();

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");

		if (webUser.getRightt().equals("超级管理员")) {

		} else if (webUser.getRightt().equals("区管理员")) {
			bean.setRegionId(webUser.getRegionId());
		} else if (webUser.getRightt().equals("单位管理员")) {
			bean.setUnitId(webUser.getUnitId());
		} else if (webUser.getRightt().equals("科室管理员")) {
			bean.setDepartmentId(webUser.getDepartmentId());
		} else {
			bean.setSid(webUser.getSid());
		}
		List list = WebUserService.findByExample(bean);
		request.setAttribute("list", list);
		return mnv;
	}

	public ModelAndView rightEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sid = request.getParameter("sid");
		String rightt = new String(
				(request.getParameter("rightt")).getBytes("iso-8859-1"),
				"utf-8");
		String pwd = new String(
				(request.getParameter("pwd")).getBytes("iso-8859-1"), "utf-8");

		String account = request.getParameter("account");
		String leave1 = new String(
				(request.getParameter("leave1")).getBytes("iso-8859-1"),
				"utf-8");
		String leave2 = new String(
				(request.getParameter("leave2")).getBytes("iso-8859-1"),
				"utf-8");
		String leave3 = new String(
				(request.getParameter("leave3")).getBytes("iso-8859-1"),
				"utf-8");

		TUsers bean = new TUsers();
		bean.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(bean);
		bean = (TUsers) list.get(0);

		bean.setRightt(rightt);
		bean.setPwd(pwd);
		bean.setAccount(account);
		bean.setLeave1(leave1);
		bean.setLeave2(leave2);
		bean.setLeave3(leave3);

		WebUserService.store(bean);

		return rightQuery(request, response);
	}

	public ModelAndView examQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Texam bean = new Texam();
		// String username = new String(
		// (request.getParameter("username")).getBytes("iso-8859-1"),
		// "utf-8");
		String identification = new String(
				(request.getParameter("identification")).getBytes("iso-8859-1"),
				"utf-8");

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../exam/exam");
		// bean.setUsername(username);
		bean.setIdentification(identification);
		List list = WebUserService.findByExample(bean);
		if (null == list || list.size() ==0) {
			mnv.setViewName("../exam/5");
			request.setAttribute("error", "没有查询到数据，请核实！");
			
		} else {
			bean = (Texam) list.get(0);
			request.setAttribute("bean", bean);
		}
		return mnv;
	}
}