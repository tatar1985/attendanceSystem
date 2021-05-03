package com.tataren.main.model;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.tataren.main.domain.user.TAccount;
import com.tataren.main.domain.user.TMessage;
import com.tataren.main.domain.user.WebUser;
import com.tataren.main.security.PasswordEncryptor;
import com.tataren.main.service.SessionInfoTatar;
import com.tataren.main.service.user.WebUserService;
import com.tataren.main.util.RequestUtils;
import com.tataren.main.web.PageBreaker;
import com.tataren.main.web.SortInformation;

public class LoginController extends MultiActionController {

	private WebUserService WebUserService;

	public WebUserService getWebUserService() {
		return WebUserService;
	}

	public void setWebUserService(WebUserService WebUserService) {
		this.WebUserService = WebUserService;
	}

	public ModelAndView webUserRegister(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = (String) request.getSession().getAttribute("userId");
		String email = request.getParameter("Email");
		String pwd = PasswordEncryptor.encryptPassword(RequestUtils
				.getStringParameter(request, "PWD", ""));
		String userName = request.getParameter("userName");
		String sex = request.getParameter("Sex");

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../messageList");
		WebUser webUser = new WebUser();
		webUser.setEmail(email);
		webUser.setPwd(pwd);
		webUser.setLoginName(userName);
		webUser.setRIGHTT(Integer.decode("1"));
		WebUserService.storeWebUser(webUser);
		return queryMessage(request, response);
	}

	public ModelAndView webUserLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tatarId = "1";
		String userID = request.getParameter("userId");
		String tatarID = request.getParameter("tatarId");
		if (tatarID != null) {
			tatarId = tatarID;
		}
		userID = userID == null ? "" : userID.trim();
		// String password =
		// PasswordEncryptor.encryptPassword(RequestUtils.getStringParameter(request,
		// "password", ""));
		String password = RequestUtils.getStringParameter(request, "password",
				"");
		HttpSession session = request.getSession();
		String srand = (String) session.getAttribute("rand");
		String authImg = request.getParameter("authimg");
		String locale = RequestUtils.getStringParameter(request, "locale");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../main");
		String treeName = "";

		if (false) {
			authImg = authImg.toUpperCase();
			if (!(srand.equalsIgnoreCase(authImg))) {
				mnv.addObject("login", "authError");
				mnv.setViewName("../index");
				return mnv;
			}
		}

		WebUser webUser = (WebUser) WebUserService
				.findWebUsersByLogonID2(userID);
		Integer i = 2;
		if (webUser != null) {
			i = webUser.getRIGHTT();
		}
		if (webUser != null && String.valueOf(i).equals("2")) {
			mnv.setViewName("../main-user");
		}
		if (webUser != null) {

			if (password.equals(webUser.getPwd())) {

				SessionInfoTatar.createInstance(request, webUser,
						"c".equals(locale) ? Locale.SIMPLIFIED_CHINESE
								: Locale.ENGLISH);

				request.getSession().setAttribute("treeName", treeName);
				request.getSession().setAttribute("locale", locale);
				request.getSession().setAttribute("webUser", webUser);
				request.getSession().setAttribute("userId",
						webUser.getLoginName());
				request.setAttribute("userName", webUser.getLoginName());
				if (tatarId.equals("2")) {
					mnv.setViewName("../ende/buyindex");
				}

			} else {
				mnv.addObject("login", "pwdError");
				mnv.setViewName("../index");
			}
		} else {
			mnv.addObject("login", "nouser");
			mnv.setViewName("../index");
		}
		return mnv;
	}

	public ModelAndView createMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		String loginName = wu.getLoginName();
		String userName = wu.getUserName();
		request.setAttribute("userName", userName);
		String message = request.getParameter("message");
		String company = request.getParameter("company");
		String address = request.getParameter("address");
		String mobile = request.getParameter("mobile");
		String state="待审核";
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");//

		TMessage tm = new TMessage();
		tm.setLoginName(loginName);
		tm.setCreator(userName);
		tm.setMessage(message);
		tm.setState(state);
		tm.setCreatedate(new Timestamp(System.currentTimeMillis()));
		tm.setCompany(company);
		tm.setAddress(address);
		tm.setMobile(mobile);

		WebUserService.store(tm);

		return queryMessageByUser(request, response);
	}

	public ModelAndView createUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		String loginName = request.getParameter("loginName");
		WebUser test = WebUserService.findWebUsersByLogonID2(loginName);
		String right = request.getParameter("right");
		if (test != null) {
			String error = "登录名已注册，请修改登录名";
			request.setAttribute("error", error);
			ModelAndView mnv = new ModelAndView();
			if (right.equals("2")) {
				mnv.setViewName("../Template/add-user");
				return mnv;
			} else {
				mnv.setViewName("../Template/add-admin");
				return mnv;
			}
		} else {

			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			String mobile = request.getParameter("mobile");
			String company = request.getParameter("company");
			String address = request.getParameter("address");
			String zip = request.getParameter("zip");
			String email = request.getParameter("email");
			WebUser webUser = new WebUser();
			webUser.setLoginName(loginName);
			webUser.setUserName(userName);
			webUser.setPwd(password);
			webUser.setMobile(mobile);
			webUser.setDepartMent(company);
			webUser.setAddress(address);
			webUser.setEmail(email);
			webUser.setCreateDate(new Timestamp(System.currentTimeMillis()));
			webUser.setRIGHTT(Integer.valueOf(right));
			webUser.setState("0");
			WebUserService.store(webUser);
			if (webUser.getRIGHTT() == 2) {
				return queryUser(request, response);
			} else {
				return queryAdmin(request, response);
			}
		}
	}

	public ModelAndView queryMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/find-form");
		WebUser webUser = new WebUser();
		webUser.setRIGHTT(1);
		List ops = WebUserService.findByExample(webUser);
		request.setAttribute("ops", ops);
		TMessage tm = new TMessage();
		String creator = request.getParameter("creator");
		String operator = request.getParameter("operator");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String state = request.getParameter("state");
		String translate = request.getParameter("translate");
		if (state != "" && state != null) {
			tm.setState(state);
		}
		SortInformation sortInfo = SortInformation.getInstance(request);
		PageBreaker pageBreaker = PageBreaker.getInstance(request);
		List lst = new ArrayList();
		if (creator != "" && creator != null) {
			lst = WebUserService.findByCreator(creator);
		} else if (translate != "" && translate != null) {
			lst = WebUserService.findByMessage(translate);
		} else {
			lst = WebUserService.findByExample(tm, pageBreaker, sortInfo);
		}

		// List lst=WebUserService.query(sql, tm);
		request.setAttribute("lst", lst);

		return mnv;
	}

	public ModelAndView queryExport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/export");
		TMessage tm = new TMessage();

		List lst = new ArrayList();

		lst = WebUserService.findByExample(tm);

		// List lst=WebUserService.query(sql, tm);
		request.setAttribute("lst", lst);

		return mnv;
	}

	public ModelAndView queryMessage2(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/find-form");
		WebUser webUser = new WebUser();
		webUser.setRIGHTT(1);
		List ops = WebUserService.findByExample(webUser);
		request.setAttribute("ops", ops);
		TMessage tm = new TMessage();
		tm.setState("已审核");
		SortInformation sortInfo = SortInformation.getInstance(request);
		PageBreaker pageBreaker = PageBreaker.getInstance(request);

		List lst = WebUserService.findByExample(tm, pageBreaker, sortInfo);

		// List lst=WebUserService.query(sql, tm);
		request.setAttribute("lst", lst);

		return mnv;
	}

	public ModelAndView queryMessage3(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/find-form");
		WebUser webUser = new WebUser();
		webUser.setRIGHTT(1);
		List ops = WebUserService.findByExample(webUser);
		request.setAttribute("ops", ops);
		TMessage tm = new TMessage();
		tm.setState("待审核");
		SortInformation sortInfo = SortInformation.getInstance(request);
		PageBreaker pageBreaker = PageBreaker.getInstance(request);

		List lst = WebUserService.findByExample(tm, pageBreaker, sortInfo);

		// List lst=WebUserService.query(sql, tm);
		request.setAttribute("lst", lst);

		return mnv;
	}

	public ModelAndView queryAdmin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/admin-list");
		SortInformation sortInfo = SortInformation.getInstance(request);
		PageBreaker pageBreaker = PageBreaker.getInstance(request);
		WebUser webUser = new WebUser();
		webUser.setRIGHTT(1);
		List ops = WebUserService.findByExample(webUser, pageBreaker, sortInfo);
		request.setAttribute("ops", ops);
		request.setAttribute("lst", ops);
		return mnv;
	}

	public ModelAndView queryUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/user-list");
		SortInformation sortInfo = SortInformation.getInstance(request);
		PageBreaker pageBreaker = PageBreaker.getInstance(request);
		WebUser webUser = new WebUser();
		webUser.setRIGHTT(2);
		List ops = WebUserService.findByExample(webUser, pageBreaker, sortInfo);
		request.setAttribute("lst", ops);
		return mnv;
	}

	public ModelAndView queryUserAccount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/account-list");
		String account = request.getParameter("account");
		String loginname = request.getParameter("loginname");
		String username = request.getParameter("username");
		SortInformation sortInfo = SortInformation.getInstance(request);
		PageBreaker pageBreaker = PageBreaker.getInstance(request);
		WebUser webUser = new WebUser();
		webUser.setRIGHTT(2);
		if (pageBreaker.getPageSize() < 20) {
			pageBreaker.setPageSize(20);
		}
		List ops = WebUserService.findByAccount(account, loginname, username,
				pageBreaker, sortInfo);
		// List ops=WebUserService.findByExample(webUser,pageBreaker,sortInfo);
		request.setAttribute("lst", ops);
		return mnv;
	}

	public ModelAndView searchChargeHistory(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/charge-list");
		String loginname = request.getParameter("sid");
		TAccount ta = new TAccount();
		ta.setUser_id(loginname);
		SortInformation sortInfo = SortInformation.getInstance(request);
		PageBreaker pageBreaker = PageBreaker.getInstance(request);
		if (pageBreaker.getCurrentPageNum() < 20) {
			pageBreaker.setCurrentPageNum(20);
			pageBreaker.setPageSize(20);
		}

		List ops = WebUserService.findByExample(ta, pageBreaker, sortInfo);
		// List ops=WebUserService.findByExample(webUser,pageBreaker,sortInfo);
		request.setAttribute("lst", ops);
		request.setAttribute("loginname", loginname);
		return mnv;
	}

	public ModelAndView linkCharging(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/update-account");
		String sid = request.getParameter("sid");
		WebUser ops = WebUserService.getWebUser(Integer.valueOf(sid));
		request.setAttribute("webUser", ops);
		return mnv;
	}

	public ModelAndView updateAccount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/account-list");
		String sid = request.getParameter("sid");
		String money = request.getParameter("money");
		WebUser wb = WebUserService.getWebUser(Integer.valueOf(sid));
		TAccount ta = new TAccount();
		ta.setUser_id(wb.getLoginName());
		ta.setUser_name(wb.getUserName());
		ta.setMoney(money);
		ta.setBeaccount(wb.getAccount());
		ta.setChargetime(new Timestamp(System.currentTimeMillis()));
		if (money.equals("100")) {
			ta.setEnaccount(wb.getAccount() + 50);
			wb.setAccount(wb.getAccount() + 50);
		} else {
			ta.setEnaccount(wb.getAccount() + 25);
			wb.setAccount(wb.getAccount() + 25);
		}
		WebUserService.store(wb);
		WebUserService.store(ta);
		SortInformation sortInfo = SortInformation.getInstance(request);
		PageBreaker pageBreaker = PageBreaker.getInstance(request);
		WebUser webUser = new WebUser();
		webUser.setSid(Integer.valueOf(sid));
		List ops = WebUserService.findByExample(webUser, pageBreaker, sortInfo);
		request.setAttribute("lst", ops);
		return mnv;
	}

	public ModelAndView linkAdmin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/update-admin");
		String sid = request.getParameter("sid");

		WebUser webUser = WebUserService.getWebUser(Integer.valueOf(sid));

		// List lst=WebUserService.query(sql, tm);
		request.setAttribute("webUser", webUser);

		return mnv;
	}

	public ModelAndView linkUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/update-user");
		String sid = request.getParameter("sid");

		WebUser webUser = WebUserService.getWebUser(Integer.valueOf(sid));

		// List lst=WebUserService.query(sql, tm);
		request.setAttribute("webUser", webUser);

		return mnv;
	}

	public ModelAndView updateAdmin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/success");
		String sid = request.getParameter("sid");
		WebUser webUser = WebUserService.getWebUser(Integer.valueOf(sid));
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		webUser.setUserName(userName);
		if (!password.equals(""))
			webUser.setPwd(password);
		WebUserService.store(webUser);
		return queryAdmin(request, response);
	}

	public ModelAndView updateUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/success");
		String sid = request.getParameter("sid");
		WebUser webUser = WebUserService.getWebUser(Integer.valueOf(sid));
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String company = request.getParameter("company");
		String address = request.getParameter("address");
		String mobile = request.getParameter("mobile");
		String state1 = request.getParameter("state1");
		webUser.setUserName(userName);
		if (password != null && !password.equals("")) {
			webUser.setPwd(password);
		}
		webUser.setDepartMent(company);
		webUser.setAddress(address);
		webUser.setMobile(mobile);
		webUser.setState(state1);
		WebUserService.store(webUser);
		return queryUser(request, response);
	}

	public ModelAndView updateUserSelf(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/user-self");
		String sid = request.getParameter("sid");
		WebUser webUser = WebUserService.getWebUser(Integer.valueOf(sid));
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String company = request.getParameter("company");
		String address = request.getParameter("address");
		String mobile = request.getParameter("mobile");
		webUser.setUserName(userName);
		if (password != null && !password.equals("")) {
			webUser.setPwd(password);
		}
		webUser.setDepartMent(company);
		webUser.setAddress(address);
		webUser.setMobile(mobile);
		WebUserService.store(webUser);
		return mnv;
	}

	public ModelAndView queryMessage4(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/find-form");
		WebUser webUser = new WebUser();
		webUser.setRIGHTT(1);
		List ops = WebUserService.findByExample(webUser);
		request.setAttribute("ops", ops);
		TMessage tm = new TMessage();
		tm.setOperator(wu.getUserName());
		SortInformation sortInfo = SortInformation.getInstance(request);
		PageBreaker pageBreaker = PageBreaker.getInstance(request);

		List lst = WebUserService.findByExample(tm, pageBreaker, sortInfo);

		// List lst=WebUserService.query(sql, tm);
		request.setAttribute("lst", lst);

		return mnv;
	}

	public ModelAndView queryMessageByUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/user-form");
		TMessage tm = new TMessage();
		tm.setLoginName(wu.getLoginName());
		SortInformation sortInfo = SortInformation.getInstance(request);
		PageBreaker pageBreaker = PageBreaker.getInstance(request);

		List lst = WebUserService.findByExample(tm, pageBreaker, sortInfo);

		// List lst=WebUserService.query(sql, tm);
		request.setAttribute("lst", lst);

		return mnv;
	}

	public ModelAndView updatePassword(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/success");
		String sid = request.getParameter("sid");
		String oldpwd = request.getParameter("oldpwd");
		String password = request.getParameter("password");
		WebUser webUser = WebUserService.getWebUser(Integer.valueOf(sid));
		if (!webUser.getPwd().equals(oldpwd)) {
			mnv.setViewName("../Template/update-password");
			request.setAttribute("error", "旧密码错误");
			return mnv;
		} else {
			webUser.setPwd(password);
			WebUserService.store(webUser);
			return mnv;
		}

	}

	public ModelAndView isRead(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();

		mnv.setViewName("../Template/find-read");
		if (wu.getRIGHTT() == 2) {
			mnv.setViewName("../Template/user-read");
		}
		String sidInt = request.getParameter("sidInt");
		Integer sid = Integer.valueOf(sidInt);
		TMessage tm = new TMessage();
		WebUserService.findByExample(tm);

		tm = WebUserService.findIMessagesBySid(sid);

		// WebUserService.store(sid);
		// PageBreaker breaker=new PageBreaker();
		// SortInformation sortInfo=new SortInformation();

		List lst = WebUserService.findByExample(tm);
		request.setAttribute("tmessage", tm);

		return mnv;
	}

	public ModelAndView updateMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		String operator = wu.getUserName();
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/find-form");
		String sidInt = request.getParameter("sid");
		String mongolia = request.getParameter("mongol");
		Integer sid = Integer.valueOf(sidInt);
		TMessage tm = new TMessage();

		tm = WebUserService.findIMessagesBySid(sid);
		tm.setOperatordate(new Timestamp(System.currentTimeMillis()));
		tm.setOperator(operator);
		tm.setMongolia(mongolia);
		if (tm.getState().equals("待审核")) {
			tm.setState("已审核");
		}

		WebUserService.store(tm);
		// WebUserService.store(sid);
		// PageBreaker breaker=new PageBreaker();
		// SortInformation sortInfo=new SortInformation();

		List lst = WebUserService.findByExample(tm);
		request.setAttribute("tmessage", tm);

		return this.queryMessage(request, response);
	}

	public ModelAndView sendMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUser wu = (WebUser) request.getSession().getAttribute("webUser");
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");//
		String operatorDate = dateFormat.format(new Timestamp(System
				.currentTimeMillis()));
		String operator = wu.getUserName();
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../Template/find-form");
		String sidInt = request.getParameter("sid");
		String mongolia = request.getParameter("mongol");
		Integer sid = Integer.valueOf(sidInt);
		TMessage tm = new TMessage();
		tm = WebUserService.findIMessagesBySid(sid);
		tm.setOperatordate(new Timestamp(System.currentTimeMillis()));
		tm.setOperator(operator);
		tm.setMongolia(mongolia);
		if (!tm.getState().equals("已翻译")) {
			WebUser wb = WebUserService
					.findWebUsersByLogonID2(tm.getLoginName());
			wb.setAccount(wb.getAccount() - 1);
			TAccount ta = new TAccount();
			ta.setBeaccount(wb.getAccount());
			ta.setEnaccount(wb.getAccount() - 1);
			ta.setMoney("-2");
			ta.setUser_id(wb.getLoginName());
			ta.setUser_name(wb.getUserName());
			ta.setChargetime(new Timestamp(System.currentTimeMillis()));
			WebUserService.store(wb);
			WebUserService.store(ta);
			tm.setState("已翻译");
		}

		WebUserService.store(tm);
		// WebUserService.store(sid);
		// PageBreaker breaker=new PageBreaker();
		// SortInformation sortInfo=new SortInformation();

		List lst = WebUserService.findByExample(tm);
		request.setAttribute("tmessage", tm);

		return this.queryMessage(request, response);
	}

	public ModelAndView loginout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SessionInfoTatar sessionInfo = SessionInfoTatar.getSessionInfo(request);

		request.getSession().invalidate();
		response.getWriter().print(
				"<script>top.location.href='" + request.getContextPath()
						+ "/index.jsp'; </script>");

		return null;
	}

	public ModelAndView autocomplete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String[] temp = "align,both,card,dream,error,fail,gson,hello,invaid,job,kill"
				.split(",");
		String name = "[";
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].indexOf(temp[i]) != -1) {
				name += "\"" + temp[i] + "\",";
			}
		}
		if (name.length() > 1)
			name = name.substring(0, name.length() - 1);
		name += "]";

		System.out.println(name);
		System.out.println(temp);

		PrintWriter pw = response.getWriter();
		pw.write(name);
		pw.flush();
		pw.close();

		return null;
	}

}