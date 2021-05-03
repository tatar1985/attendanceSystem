package com.tataren.main.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tataren.main.service.SessionInfo;
import com.tataren.main.service.user.WebUserService;
import com.tataren.main.util.WeChatValue;
import com.tataren.tatar.domain.TArea;
import com.tataren.tatar.domain.TInout;
import com.tataren.tatar.domain.TLeave;
import com.tataren.tatar.domain.TLeaveAdmin;
import com.tataren.tatar.domain.TTemp;
import com.tataren.tatar.domain.TUnit;
import com.tataren.tatar.domain.TUsers;
import com.tataren.tatar.send.CommonUtil;
import com.tataren.tatar.send.Template;
import com.tataren.tatar.send.TemplateParam;

public class SuldController extends MultiActionController {

	private WebUserService WebUserService;

	public WebUserService getWebUserService() {
		return WebUserService;
	}

	public void setWebUserService(WebUserService WebUserService) {
		this.WebUserService = WebUserService;
	}

	public ModelAndView weChatMobileUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		


	       
	    Map<String, String> requestMap = WeChatValue.xmlToMap(request);//MessageUtil.parseXml(request);  
	  
	         
	    String toUserName = requestMap.get("ToUserName");  
	    String fromUserName = requestMap.get("FromUserName");  
	    String createTime = requestMap.get("CreateTime");  
	    String msgType = requestMap.get("MsgType");  
	    String content = requestMap.get("Content");  
		
		//关注事件
		String event = requestMap.get("Event"); 
		//带参数二维码
		String eventKey = requestMap.get("EventKey");
		
		System.out.println("ToUserName="+toUserName);
		System.out.println("ToUserName="+fromUserName);
		System.out.println("FromUserName="+createTime);
		System.out.println("msgType="+msgType);
		System.out.println("content="+content);


		if (msgType.equals("text")) {  
			
            StringBuffer str = new StringBuffer();  
            str.append("<xml>");  
            str.append("<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>");  
            str.append("<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>");  
            str.append("<CreateTime>" + createTime + "</CreateTime>");  
            str.append("<MsgType><![CDATA[" + "text" + "]]></MsgType>");  
            str.append("<Content><![CDATA[有事情联系15647767139]]></Content>");  
            str.append("</xml>");  
            System.out.println(str.toString());  
            
            response.getWriter().write(str.toString());  
        }else if(msgType.equals("event")){
        	if(event.equals("CLICK")){	
        		if(eventKey.equals("C2002")){
        			
        			
        		}
        		
        	}else if(event.equals("subscribe")){
        		StringBuffer str = new StringBuffer();  
                str.append("<xml>");  
                str.append("<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>");  
                str.append("<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>");  
                str.append("<CreateTime>" + createTime + "</CreateTime>");  
                str.append("<MsgType><![CDATA[" + "text" + "]]></MsgType>");  
                str.append("<Content><![CDATA[欢迎关注乌兰陶勒盖镇干部管理系统]]></Content>");  
                str.append("</xml>");  
                System.out.println(str.toString());  
                
                response.getWriter().write(str.toString());
                if(eventKey!=null){
                	String[] valueMsg = eventKey.split("_");
                	String optId = valueMsg[1];
                	
                	
                	TUsers webUser = new TUsers();
            		webUser.setOpenId(fromUserName);// y
            		List webList = WebUserService.findByExample(webUser);
            		if (webList.isEmpty()) {
            			Date now = new Date();
            			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
            			String nowtimes = dateFormat.format(now);
            			webUser.setCreateDate(nowtimes);
            			webUser.setRightt("工作人员");
            			webUser.setState("正常");
            			webUser.setAccount("100");
            			webUser.setAccountName("新兵");
            			webUser.setRegionId("150626");
            			webUser.setRegionName("乌审旗");
            			webUser.setUnitId("12333");
            			webUser.setUnitName("乌兰陶勒盖镇人民政府");
            			WebUserService.store(webUser);
            		}
                }
        	}else if(event.equals("SCAN")){
        		if(eventKey!=null){
                	
                	System.out.println("888888888888888888888888888888888888"+eventKey);
                	
                	TUsers webUser = new TUsers();
            		webUser.setOpenId(fromUserName);// y
            		List webList = WebUserService.findByExample(webUser);
            		if (!webList.isEmpty()) {
            			webUser = (TUsers)webList.get(0);
            			webUser.setUnitId("12333");
            			webUser.setUnitName("乌兰陶勒盖镇人民政府");
            			WebUserService.store(webUser);
            		}
                }
        	}
        	
        	
        }
		
		System.out.println("tatar");
		
		return null;
		
	
	}

	/** first enter the check in out page **/
	public ModelAndView firstMobile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String apiurl = "https://open.weixin.qq.com/connect/oauth2/authorize";
		String appUrl = SessionInfo.appUrl;
		String redirect_uri = java.net.URLEncoder.encode("http://" + appUrl
				+ "/suld_/suld.do?method=indexInter", "UTF-8");
		String appid = SessionInfo.appid;
		String secret = SessionInfo.secret;

		String turl = String
				.format("%s?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect",
						apiurl, appid, redirect_uri);
		response.sendRedirect(turl);
		ModelAndView mnv = new ModelAndView();

		return null;
	}

	public ModelAndView indexInter(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarIndex");
		String code = request.getParameter("code");

		String appid = SessionInfo.appid;
		String secret = SessionInfo.secret;
		String state = request.getParameter("STATE");
		String url = " https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid
				+ "&secret="
				+ secret
				+ "&code="
				+ code
				+ "&grant_type=authorization_code ";
		String turl = "https://api.weixin.qq.com/sns/oauth2/access_token";
		String redirect_uri = java.net.URLEncoder.encode(url, "UTF-8");
		String tturl = String.format(
				"%s?appid=%s&secret=%s&code=%s&&grant_type=authorization_code",
				turl, appid, secret, code);

		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(tturl);
		JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
		String openid = null;
		String access_token = null;
		String scope = null;

		HttpResponse res = client.execute(get);
		String responseContent = null; // 响应内容
		HttpEntity entity = res.getEntity();
		responseContent = EntityUtils.toString(entity, "UTF-8");
		JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();

		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			if (json.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
				mnv = new ModelAndView();
				mnv.setViewName("../suld/failedToGet");
				return mnv;
			} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
					// result = json.get("ip_list").getAsString();
				openid = json.get("openid").getAsString();
				access_token = json.get("access_token").getAsString();
				scope = json.get("scope").getAsString();

			}
		}

		TUsers webUser = new TUsers();
		webUser.setOpenId(openid);// y
		List webList = WebUserService.findByExample(webUser);
		if (!webList.isEmpty()) {
			webUser = (TUsers)webList.get(0);
			String burl = "https://api.weixin.qq.com/sns/userinfo";
			String bburl = String.format(
					"%s?access_token=%s&openid=%s&&lang=zh_CN", burl,
					access_token, openid);

			HttpClient bclient = new DefaultHttpClient();
			HttpGet bget = new HttpGet(bburl);
			JsonParser bjsonparer = new JsonParser();// 初始化解析json格式的对象
			String bopenid = null;
			String nickname = null;
			String headimgurl = null;
			String openMid = null;

			HttpResponse bres = client.execute(bget);
			String bresponseContent = null; // 响应内容
			HttpEntity bentity = bres.getEntity();
			bresponseContent = EntityUtils.toString(bentity, "UTF-8");
			JsonObject bjson = bjsonparer.parse(bresponseContent)
					.getAsJsonObject();
			if (bres.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (bjson.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
					mnv.setViewName("../suld/failedToGet");
					return mnv;
				} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
						// result = json.get("ip_list").getAsString();
					nickname = bjson.get("nickname").getAsString();
					headimgurl = bjson.get("headimgurl").getAsString();
					openMid = bjson.get("openid").getAsString();
				}
			}

			webUser.setChatName(nickname);
			webUser.setHeadUrl(headimgurl);
			
			
			TUnit unitBean = new TUnit();
			unitBean.setSid(Integer.valueOf(webUser.getUnitId()));
			List unitList = WebUserService.findByExample(unitBean);
			unitBean = (TUnit) unitList.get(0);
			if (unitBean.getUnitProperties().equals("两签")) {
				mnv.setViewName("../suld/tatarIndexHalf");
			}
		} else {

			String burl = "https://api.weixin.qq.com/sns/userinfo";
			String bburl = String.format(
					"%s?access_token=%s&openid=%s&&lang=zh_CN", burl,
					access_token, openid);

			HttpClient bclient = new DefaultHttpClient();
			HttpGet bget = new HttpGet(bburl);
			JsonParser bjsonparer = new JsonParser();// 初始化解析json格式的对象
			String bopenid = null;
			String nickname = null;
			String headimgurl = null;
			String openMid = null;

			HttpResponse bres = client.execute(bget);
			String bresponseContent = null; // 响应内容
			HttpEntity bentity = bres.getEntity();
			bresponseContent = EntityUtils.toString(bentity, "UTF-8");
			JsonObject bjson = bjsonparer.parse(bresponseContent)
					.getAsJsonObject();
			if (bres.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (bjson.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
					mnv.setViewName("../suld/failedToGet");
					return mnv;
				} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
						// result = json.get("ip_list").getAsString();
					nickname = bjson.get("nickname").getAsString();
					headimgurl = bjson.get("headimgurl").getAsString();
					openMid = bjson.get("openid").getAsString();
				}
			}

			webUser.setChatName(nickname);
			webUser.setState("新入");
			webUser.setHeadUrl(headimgurl);

			mnv.setViewName("../suld/tatarRegister");
		    //WebUserService.store(webUser);
			// mnv.setViewName("../suld/tatarEditSelf");
			request.getSession().setAttribute("webUser", webUser);
			return mnv;

		}
		request.getSession().setAttribute("webUser", webUser);

		TInout bean = new TInout();
		bean.setUSERSID(openid);// y

		Date now = new Date();
		// SimpleDateFormat dateFormat = new
		// SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
		String nowtimes = dateFormat.format(now);
		bean.setDAYTIME(nowtimes);
		List list = WebUserService.getTInout(openid, nowtimes);// y
		if (!list.isEmpty()) {
			bean = (TInout) list.get(0);
		} else {
			bean.setUSERNAME(webUser.getUserName() == null ? webUser
					.getChatName() : webUser.getUserName());
			bean.setDAYTYPE("上班");
			bean.setFIRSTCHECKSTATE("未签到");
			bean.setTWOCHECKSTATE("未签到");
			bean.setTHREECHECKSTATE("未签到");
			bean.setFOURCHECKSTATE("未签到");
			bean.setHEADURL(webUser.getHeadUrl());// y
			WebUserService.store(bean);
		}
		//WebUserService.store(webUser);
		String signature = WebUserService.getSignature(code);
		request.getSession().setAttribute("bean", bean);
		request.getSession().setAttribute("signature", signature);
		// request.setAttribute("bean", bean);
		// request.setAttribute("signature", signature);

		return mnv;
	}

	/** end first enter check in out page **/

	/** first enter the add leave page **/
	public ModelAndView mobileLeaveAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String apiurl = "https://open.weixin.qq.com/connect/oauth2/authorize";
		String appUrl = SessionInfo.appUrl;
		String redirect_uri = java.net.URLEncoder.encode("http://" + appUrl
				+ "/suld_/suld.do?method=linkMobileAdd", "UTF-8");
		String appid = SessionInfo.appid;
		String secret = SessionInfo.secret;
		String turl = String
				.format("%s?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect",
						apiurl, appid, redirect_uri);
		response.sendRedirect(turl);
		ModelAndView mnv = new ModelAndView();

		return null;
	}

	public ModelAndView linkMobileAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarLeaveAdd");
		String code = request.getParameter("code");

		String appid = SessionInfo.appid;
		String secret = SessionInfo.secret;
		String state = request.getParameter("STATE");
		String url = " https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid
				+ "&secret="
				+ secret
				+ "&code="
				+ code
				+ "&grant_type=authorization_code ";
		String turl = "https://api.weixin.qq.com/sns/oauth2/access_token";
		String redirect_uri = java.net.URLEncoder.encode(url, "UTF-8");
		String tturl = String.format(
				"%s?appid=%s&secret=%s&code=%s&&grant_type=authorization_code",
				turl, appid, secret, code);

		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(tturl);
		JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
		String openid = null;
		String access_token = null;
		String scope = null;

		HttpResponse res = client.execute(get);
		String responseContent = null; // 响应内容
		HttpEntity entity = res.getEntity();
		responseContent = EntityUtils.toString(entity, "UTF-8");
		JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();

		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			if (json.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
				return null;
			} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
					// result = json.get("ip_list").getAsString();
				access_token = json.get("access_token").getAsString();
				openid = json.get("openid").getAsString();
				access_token = json.get("access_token").getAsString();
				scope = json.get("scope").getAsString();

			}
		}

		TUsers webUser = new TUsers();
		webUser.setOpenId(openid);
		List webList = WebUserService.findByExample(webUser);
		if (!webList.isEmpty()) {
			webUser = (TUsers) webList.get(0);
			TLeaveAdmin tadmin = new TLeaveAdmin();
			tadmin.setUnitId(webUser.getUnitId());
			List tlist = WebUserService.findByExample(tadmin);
			request.getSession().setAttribute("tlist", tlist);
		} else {

			String burl = "https://api.weixin.qq.com/sns/userinfo";
			String bburl = String.format(
					"%s?access_token=%s&openid=%s&&lang=zh_CN", burl,
					access_token, openid);

			HttpClient bclient = new DefaultHttpClient();
			HttpGet bget = new HttpGet(bburl);
			JsonParser bjsonparer = new JsonParser();// 初始化解析json格式的对象
			String bopenid = null;
			String nickname = null;
			String headimgurl = null;
			String openMid = null;

			HttpResponse bres = client.execute(bget);
			String bresponseContent = null; // 响应内容
			HttpEntity bentity = bres.getEntity();
			bresponseContent = EntityUtils.toString(bentity, "UTF-8");
			JsonObject bjson = bjsonparer.parse(bresponseContent)
					.getAsJsonObject();
			if (bres.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (bjson.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
					return null;
				} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
						// result = json.get("ip_list").getAsString();
					nickname = bjson.get("nickname").getAsString();
					headimgurl = bjson.get("headimgurl").getAsString();
					openMid = bjson.get("openid").getAsString();
				}
			}

			webUser.setChatName(nickname);
			webUser.setState("新入");
			webUser.setHeadUrl(headimgurl);
			mnv.setViewName("../suld/tatarRegister");
			// WebUserService.store(webUser);
			// mnv.setViewName("../suld/tatarEditSelf");
		}
		request.getSession().setAttribute("webUser", webUser);

		/**
		 * TMember bean = new TMember(); List list =
		 * WebUserService.getTMember(openMid); if (!list.isEmpty()) { bean =
		 * (TMember) list.get(0); } else { bean.setUsername(nickname);
		 * bean.setLoginName(openMid); bean.setPhoto(headimgurl);
		 * WebUserService.store(bean); }
		 **/
		// String signature = WebUserService.getSignature(code);
		// request.getSession().setAttribute("webUser", webUser);
		// request.getSession().setAttribute("signature", signature);

		return mnv;
	}

	/** end first enter the leave add page **/

	/** again enter the leave add page **/

	public ModelAndView secondEnterLeaveAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TInout tinout = (TInout) request.getSession().getAttribute("bean");
		String opendId = tinout.getUSERSID();
		List list = WebUserService.getTMember(opendId);
		TUsers webUser = new TUsers();
		if (!list.isEmpty()) {
			webUser = (TUsers) list.get(0);
		} else {
			webUser.setUserName(tinout.getUSERNAME());
			webUser.setOpenId(opendId);
			webUser.setHeadUrl(tinout.getHEADURL());
			WebUserService.store(webUser);
		}
		request.getSession().setAttribute("webUser", webUser);
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarLeaveAdd");

		return mnv;
	}

	public ModelAndView getMobileInoutBa(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String latitudeId = request.getParameter("latitudeId");
		String longitudeId = request.getParameter("longitudeId");
		String kindId = request.getParameter("kindId");
		String kindCheck = request.getParameter("kindCheck");
		String opendId = request.getParameter("opendId");
		String signature = request.getParameter("signature");
		TInout bean = new TInout();
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
		String nowtimes = dateFormat.format(now);
		SimpleDateFormat dateFormat2 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");// 可以方便地修改日期格式
		String nowtimes2 = dateFormat2.format(now);

		List list = WebUserService.getTInout(opendId, nowtimes);
		bean = (TInout) list.get(0);
		if (kindId.equals("1")) {
			bean.setFIRSTCHECKLATIID(latitudeId);
			bean.setFIRSTCHECKLONGID(longitudeId);
			bean.setFIRSTCHECKSTATE("已签到");
			bean.setFIRSTCHECKTIME(nowtimes2);
		} else if (kindId.equals("2")) {
			bean.setTWOCHECKLATIID(latitudeId);
			bean.setTWOCHECKLONGID(longitudeId);
			bean.setTWOCHECKSTATE("已签退");
			bean.setTWOCHECKTIME(nowtimes2);

		} else if (kindId.equals("3")) {

			bean.setTHREECHECKLATIID(latitudeId);
			bean.setTHREECHECKLONGID(longitudeId);
			bean.setTHREECHECKSTATE("已签到");
			bean.setTHREECHECKTIME(nowtimes2);
		} else if (kindId.equals("4")) {

			bean.setFOURCHECKLATIID(latitudeId);
			bean.setFOURCHECKLONGID(longitudeId);
			bean.setFOURCHECKSTATE("已签退");
			bean.setFOURCHECKTIME(nowtimes2);
		}
		WebUserService.store(bean);
		request.getSession().setAttribute("bean", bean);
		request.getSession().setAttribute("signature", signature);
		ModelAndView mnv = new ModelAndView();
		if (kindCheck.equals("2")) {
			mnv.setViewName("../suld/tatarIndexHalf");
		} else {
			mnv.setViewName("../suld/tatarIndex");
		}
		return mnv;

	}

	/** second enter the inout query **/
	public ModelAndView mobileInoutQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String opendId = request.getParameter("opendId");

		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, -6);

		String week = "";
		/* 获取一周七天的值 */
		List listInout = new ArrayList();
		for (int j = 0; j < 7; j++) {
			/* 获取当前日历的日期的星期数（1:星期天） */
			int week_index = cal.get(Calendar.DAY_OF_WEEK);
			Date date = cal.getTime();
			/* 日期格式化 yyyy-MM-dd M必须大写 */
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(date);

			/* 将日历日期推后1天 */
			cal.add(cal.DATE, +1);
			String weekId = String.valueOf(week_index);

			if (week_index == 1) {
				week = "周日";
			} else if (week_index == 2) {
				week = "周一";
			} else if (week_index == 3) {
				week = "周二";
			} else if (week_index == 4) {
				week = "周三";
			} else if (week_index == 5) {
				week = "周四";
			} else if (week_index == 6) {
				week = "周五";
			} else if (week_index == 7) {
				week = "周六";
			}

			List listTemp = WebUserService.getTInout(opendId, dateString);
			if (!listTemp.isEmpty()) {

				TInout beanIn = (TInout) listTemp.get(0);
				beanIn.setWEEK(week);
				beanIn.setDAYTIME(dateString.substring(5, 10));
				listInout.add(beanIn);
			} else {

				TInout beanIn = new TInout();
				beanIn.setWEEK(week);
				beanIn.setUSERSID(opendId);
				beanIn.setDAYTIME(dateString.substring(5, 10));
				beanIn.setFIRSTCHECKSTATE("未签到");
				beanIn.setTWOCHECKSTATE("未签退");
				beanIn.setTHREECHECKSTATE("未签到");
				beanIn.setFOURCHECKSTATE("未签退");
				listInout.add(beanIn);
			}
		}
		request.setAttribute("list", listInout);
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarInOutQuery");
		return mnv;

	}

	public ModelAndView firstMobileInoutQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, -6);
		String opendId = request.getParameter("opendId");

		String week = "";
		/* 获取一周七天的值 */
		List list = new ArrayList();
		for (int j = 0; j < 7; j++) {
			/* 获取当前日历的日期的星期数（1:星期天） */
			int week_index = cal.get(Calendar.DAY_OF_WEEK);
			Date date = cal.getTime();
			/* 日期格式化 yyyy-MM-dd M必须大写 */
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(date);

			/* 将日历日期推后1天 */
			cal.add(cal.DATE, +1);
			String weekId = String.valueOf(week_index);

			if (week_index == 1) {
				week = "周日";
			} else if (week_index == 2) {
				week = "周一";
			} else if (week_index == 3) {
				week = "周二";
			} else if (week_index == 4) {
				week = "周三";
			} else if (week_index == 5) {
				week = "周四";
			} else if (week_index == 6) {
				week = "周五";
			} else if (week_index == 7) {
				week = "周六";
			}

			List listTemp = WebUserService.getTInout(opendId, dateString);
			if (!listTemp.isEmpty()) {

				TInout bean = (TInout) listTemp.get(0);
				bean.setWEEK(week);
				bean.setDAYTIME(dateString.substring(5, 10));
				list.add(bean);
			} else {

				TInout bean = new TInout();
				bean.setWEEK(week);
				bean.setUSERSID(opendId);
				bean.setDAYTIME(dateString.substring(5, 10));
				bean.setFIRSTCHECKSTATE("未签到");
				bean.setTWOCHECKSTATE("未签退");
				bean.setTHREECHECKSTATE("未签到");
				bean.setFOURCHECKSTATE("未签退");
				list.add(bean);
			}
		}
		request.setAttribute("list", list);
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarInOutQuery");
		return mnv;
	}

	/** again enter the add leave page **/
	public ModelAndView leaveAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String trueName = new String(
				(request.getParameter("trueName")).getBytes("iso-8859-1"),
				"utf-8");
		String sid = request.getParameter("sid");
		String opendId = request.getParameter("opendId");
		String mobile = request.getParameter("mobile");
		// String unitName =new
		// String((request.getParameter("unitName")).getBytes("iso-8859-1"),"utf-8");
		String leaveKind = new String(
				(request.getParameter("leaveKind")).getBytes("iso-8859-1"),
				"utf-8");
		String adminLeavel1 = new String(
				(request.getParameter("adminLeavel1")).getBytes("iso-8859-1"),
				"utf-8");
		String adminLeavel2 = new String(
				(request.getParameter("adminLeavel2")).getBytes("iso-8859-1"),
				"utf-8");
		String adminLeavel3 = request.getParameter("adminLeavel3");
		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		if (adminLeavel3 != null) {
			adminLeavel3 = new String(
					(request.getParameter("adminLeavel3"))
							.getBytes("iso-8859-1"),
					"utf-8");
		}

		String startTime = new String(
				(request.getParameter("startTime")).getBytes("iso-8859-1"),
				"utf-8");
		String endTime = new String(
				(request.getParameter("endTime")).getBytes("iso-8859-1"),
				"utf-8");
		String dayType = new String(
				(request.getParameter("dayType")).getBytes("iso-8859-1"),
				"utf-8");
		String dayType2 = new String(
				(request.getParameter("dayType2")).getBytes("iso-8859-1"),
				"utf-8");
		String leaveRemark = new String(
				(request.getParameter("leaveRemark")).getBytes("iso-8859-1"),
				"utf-8");
		String headImgUrl = request.getParameter("headImgUrl");
		String department = new String(
				(request.getParameter("department")).getBytes("iso-8859-1"),
				"utf-8");
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");// 可以方便地修改日期格式
		String nowtimes = dateFormat.format(now);

		TLeave bean = new TLeave();
		bean.setUSERNAME(trueName);
		bean.setMOBILE(mobile);
		bean.setTLEAVETYPE(leaveKind);
		bean.setUNITNAME(webUser.getUnitName());
		bean.setFIRSTAPPROVALPERSON(adminLeavel1);
		bean.setFIRSTAPPROVALSTATE("待审批");
		bean.setSECONDAPPROVALPERSON(adminLeavel2);
		bean.setSECONDAPPROVALSTATE("待审批");
		bean.setAPPLICATIONSTIME(nowtimes);
		if (adminLeavel3 == null) {
			bean.setTHREEAPPROVALSTATE("无需审批");
		} else {
			bean.setTHREEAPPROVALSTATE("待审批");
			bean.setTHREEAPPROVALPERSON(adminLeavel3);
		}

		bean.setREMARK(leaveRemark);
		bean.setSTARTTIME(startTime);

		bean.setENDTIME(endTime);
		bean.setDepartment(department);
		bean.setUSERSID(opendId);
		bean.setTUNITID(webUser.getUnitId());

		Date a1 = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);
		Date b1 = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);
		// 获取相减后天数
		double day = (a1.getTime() - b1.getTime()) / (24 * 60 * 60 * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());

		if (startTime.equals(endTime)) {
			if (dayType.equals("全天")) {
				day = day + 1;
				bean.setDAYNUM(String.valueOf(day));
			} else if (dayType.equals("上午") || dayType.equals("下午")) {
				bean.setDAYTYPE(dayType);
				bean.setDAYTYPEID(dayType);
				bean.setDAYNUM("0.5");
			}
		} else {
			if (dayType.equals("全天") && dayType2.equals("全天")) {
				day = day + 1;
				bean.setDAYNUM(String.valueOf(day));
			} else if (dayType.equals("下午") && dayType2.equals("全天")) {
				bean.setDAYTYPE(dayType);
				day = day + 0.5;
				bean.setDAYNUM(String.valueOf(day));
			} else if (dayType.equals("下午") && dayType2.equals("上午")) {
				bean.setDAYTYPE(dayType);
				bean.setDAYTYPEID(dayType2);
				bean.setDAYNUM(String.valueOf(day));
			} else if (dayType.equals("全天") && dayType2.equals("上午")) {
				bean.setDAYTYPEID(dayType2);
				day = day + 0.5;
				bean.setDAYNUM(String.valueOf(day));
			}
		}

		bean.setPHOTO(headImgUrl);
		bean.setFLAGSTATE("审批中");
		bean.setLeaveState("正常");

		WebUserService.store(bean);

		request.setAttribute("bean", bean);
		TLeave tbean = new TLeave();
		tbean.setUSERSID(opendId);
		tbean.setFLAGSTATE("审批中");
		tbean.setLeaveState("正常");
		List proTLeaves = WebUserService.findByExample(tbean);
		tbean.setFLAGSTATE("完成");
		List succTLeaves = WebUserService.findByExample(tbean);
		request.getSession().setAttribute("proTLeaves", proTLeaves);
		request.getSession().setAttribute("succTTLeaves", succTLeaves);
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarLeaveList");
		
		try {

			String[] adminOpenId = adminLeavel1.split("-");

			TUsers tuser = new TUsers();
			tuser.setSid(Integer.valueOf(adminOpenId[0]));

			List list = WebUserService.findByExample(tuser);

			tuser = (TUsers) list.get(0);

			//SendService ser = new SendService();
			String leaveTime = bean.getSTARTTIME() + "到" + bean.getENDTIME()
					+ "  " + bean.getDAYNUM() + "天";
			//ser.sendLeave(bean.getUSERNAME(), bean.getTLEAVETYPE(), leaveTime,
			//		bean.getREMARK(), tuser.getOpenId());
			
			
			Template tem=new Template();  
			   
		    tem.setTouser(tuser.getOpenId());  // oQMBIwaHaXWaREwzoBXScKEfaBoM ogx_NvxGZAEnkDCeqTgKZd1t7NxE	    
		    tem.setTemplateId("6qWB1fhta9lQ1DkVJP44VIMvFO_m8LKKBSq7qqZNgjE");  
		    tem.setUrl("http://www.tataren.com/suld_/suld.do?method=firstMobileLeaveHisAdmin");  
		              
		    List<TemplateParam> data=new ArrayList<TemplateParam>();  
		    data.add(new TemplateParam("first","请假审批提醒","#FF3333"));  
		    data.add(new TemplateParam("keyword1",bean.getUSERNAME(),"#0044BB"));  
		    data.add(new TemplateParam("keyword2",bean.getTLEAVETYPE(),"#0044BB"));  
		    data.add(new TemplateParam("keyword3",leaveTime,"#0044BB"));  
		    data.add(new TemplateParam("keyword4",bean.getREMARK(),"#0044BB")); 
		    data.add(new TemplateParam("Remark","请及时处理此条信息！ ","#AAAAAA"));  
		   		    tem.setTemplateParamList(data);  
		    String token =WeChatValue.getAccessToken();
		    String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
			requestUrl = requestUrl.replace("ACCESS_TOKEN", token);

			CommonUtil.httpsRequest(requestUrl, "POST",
					tem.toJSON());
		  

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mnv;


	}

	public ModelAndView leaveSub(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sid = request.getParameter("sid");
		TLeave oldBean=new TLeave();
		oldBean.setSid(Integer.valueOf(sid));
		List oldList = WebUserService.findByExample(oldBean);
		oldBean = (TLeave) oldList.get(0);
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String dayType = new String(
				(request.getParameter("dayType")).getBytes("iso-8859-1"),
				"utf-8");
		String dayType2 = new String(
				(request.getParameter("dayType2")).getBytes("iso-8859-1"),
				"utf-8");
		String leaveRemark = new String(
				(request.getParameter("leaveRemark")).getBytes("iso-8859-1"),
				"utf-8");
		System.out.println(startTime+endTime+dayType+dayType2+leaveRemark);

		
		
		
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");// 可以方便地修改日期格式
		String nowtimes = dateFormat.format(now);

		TLeave bean = new TLeave();
		bean.setUSERNAME(oldBean.getUSERNAME());
		bean.setMOBILE(oldBean.getMOBILE());
		bean.setTLEAVETYPE(oldBean.getTLEAVETYPE());
		bean.setUNITNAME(oldBean.getUNITNAME());
		bean.setFIRSTAPPROVALPERSON(oldBean.getFIRSTAPPROVALPERSON());
		bean.setFIRSTAPPROVALSTATE("待审批");
		bean.setSECONDAPPROVALPERSON(oldBean.getSECONDAPPROVALPERSON());
		bean.setSECONDAPPROVALSTATE("待审批");
		bean.setAPPLICATIONSTIME(nowtimes);
		if (oldBean.getTHREEAPPROVALPERSON() == null) {
			bean.setTHREEAPPROVALSTATE("无需审批");
		} else {
			bean.setTHREEAPPROVALSTATE("待审批");
			bean.setTHREEAPPROVALPERSON(oldBean.getTHREEAPPROVALPERSON());
		}

		bean.setREMARK(leaveRemark);
		bean.setSTARTTIME(startTime);

		bean.setENDTIME(endTime);
		bean.setDepartment(oldBean.getDepartment());
		bean.setUSERSID(oldBean.getUSERSID());
		bean.setTUNITID(oldBean.getTUNITID());

		Date a1 = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);
		Date b1 = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);
		// 获取相减后天数
		double day = (a1.getTime() - b1.getTime()) / (24 * 60 * 60 * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());

		if (startTime.equals(endTime)) {
			if (dayType.equals("全天")) {
				day = day + 1;
				bean.setDAYNUM("-"+String.valueOf(day));
			} else if (dayType.equals("上午") || dayType.equals("下午")) {
				bean.setDAYTYPE(dayType);
				bean.setDAYTYPEID(dayType);
				bean.setDAYNUM("-0.5");
			}
		} else {
			if (dayType.equals("全天") && dayType2.equals("全天")) {
				day = day + 1;
				bean.setDAYNUM("-"+String.valueOf(day));
			} else if (dayType.equals("下午") && dayType2.equals("全天")) {
				bean.setDAYTYPE(dayType);
				day = day + 0.5;
				bean.setDAYNUM("-"+String.valueOf(day));
			} else if (dayType.equals("下午") && dayType2.equals("上午")) {
				bean.setDAYTYPE(dayType);
				bean.setDAYTYPEID(dayType2);
				bean.setDAYNUM("-"+String.valueOf(day));
			} else if (dayType.equals("全天") && dayType2.equals("上午")) {
				bean.setDAYTYPEID(dayType2);
				day = day + 0.5;
				bean.setDAYNUM("-"+String.valueOf(day));
			}
		}

		bean.setPHOTO(oldBean.getPHOTO());
		bean.setFLAGSTATE("审批中");
		bean.setLeaveState("正常");

		WebUserService.store(bean);

		request.setAttribute("bean", bean);
		TLeave tbean = new TLeave();
		tbean.setUSERSID(oldBean.getUSERSID());
		tbean.setFLAGSTATE("审批中");
		tbean.setLeaveState("正常");
		List proTLeaves = WebUserService.findByExample(tbean);
		tbean.setFLAGSTATE("完成");
		List succTLeaves = WebUserService.findByExample(tbean);
		request.getSession().setAttribute("proTLeaves", proTLeaves);
		request.getSession().setAttribute("succTTLeaves", succTLeaves);
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarLeaveList");
		
		try {

			String[] adminOpenId = oldBean.getFIRSTAPPROVALPERSON().split("-");

			TUsers tuser = new TUsers();
			tuser.setSid(Integer.valueOf(adminOpenId[0]));

			List list = WebUserService.findByExample(tuser);

			tuser = (TUsers) list.get(0);

			//SendService ser = new SendService();
			String leaveTime = bean.getSTARTTIME() + "到" + bean.getENDTIME()
					+ "  " + bean.getDAYNUM() + "天";
			//ser.sendLeave(bean.getUSERNAME(), bean.getTLEAVETYPE(), leaveTime,
			//		bean.getREMARK(), tuser.getOpenId());
			
			
			Template tem=new Template();  
			   
		    tem.setTouser(tuser.getOpenId());  // oQMBIwaHaXWaREwzoBXScKEfaBoM ogx_NvxGZAEnkDCeqTgKZd1t7NxE	    
		    tem.setTemplateId("6qWB1fhta9lQ1DkVJP44VIMvFO_m8LKKBSq7qqZNgjE");  
		    tem.setUrl("http://www.tataren.com/suld_/suld.do?method=firstMobileLeaveHisAdmin");  
		              
		    List<TemplateParam> data=new ArrayList<TemplateParam>();  
		    data.add(new TemplateParam("first","销假审批提醒","#FF3333"));  
		    data.add(new TemplateParam("keyword1",bean.getUSERNAME(),"#0044BB"));  
		    data.add(new TemplateParam("keyword2",bean.getTLEAVETYPE(),"#0044BB"));  
		    data.add(new TemplateParam("keyword3",leaveTime,"#0044BB"));  
		    data.add(new TemplateParam("keyword4",bean.getREMARK(),"#0044BB")); 
		    data.add(new TemplateParam("Remark","请及时处理此条信息！ ","#AAAAAA"));  
		   		    tem.setTemplateParamList(data);  
		    String token =WeChatValue.getAccessToken();
		    String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
			requestUrl = requestUrl.replace("ACCESS_TOKEN", token);

			CommonUtil.httpsRequest(requestUrl, "POST",
					tem.toJSON());
		  

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mnv;


	}

	/** 考勤记录 第一次登陆 **/
	public ModelAndView firstMobileInout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String apiurl = "https://open.weixin.qq.com/connect/oauth2/authorize";
		String appUrl = SessionInfo.appUrl;
		String redirect_uri = java.net.URLEncoder.encode("http://" + appUrl
				+ "/suld_/suld.do?method=indexInterInout", "UTF-8");
		String appid = SessionInfo.appid;
		String secret = SessionInfo.secret;

		String turl = String
				.format("%s?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect",
						apiurl, appid, redirect_uri);
		response.sendRedirect(turl);
		ModelAndView mnv = new ModelAndView();

		return null;
	}

	public ModelAndView indexInterInout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = request.getParameter("code");

		String appid = SessionInfo.appid;
		String secret = SessionInfo.secret;
		String state = request.getParameter("STATE");
		String url = " https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid
				+ "&secret="
				+ secret
				+ "&code="
				+ code
				+ "&grant_type=authorization_code ";
		String turl = "https://api.weixin.qq.com/sns/oauth2/access_token";
		String redirect_uri = java.net.URLEncoder.encode(url, "UTF-8");
		String tturl = String.format(
				"%s?appid=%s&secret=%s&code=%s&&grant_type=authorization_code",
				turl, appid, secret, code);

		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(tturl);
		JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
		String openid = null;
		String access_token = null;
		String scope = null;

		HttpResponse res = client.execute(get);
		String responseContent = null; // 响应内容
		HttpEntity entity = res.getEntity();
		responseContent = EntityUtils.toString(entity, "UTF-8");
		JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();

		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			if (json.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
				ModelAndView mnv = new ModelAndView();
				mnv.setViewName("../suld/failedToGet");
				return mnv;
			} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
					// result = json.get("ip_list").getAsString();
				access_token = json.get("access_token").getAsString();
				openid = json.get("openid").getAsString();
				access_token = json.get("access_token").getAsString();
				scope = json.get("scope").getAsString();

			}
		}

		String burl = "https://api.weixin.qq.com/sns/userinfo";
		String bburl = String.format(
				"%s?access_token=%s&openid=%s&&lang=zh_CN", burl, access_token,
				openid);

		HttpClient bclient = new DefaultHttpClient();
		HttpGet bget = new HttpGet(bburl);
		JsonParser bjsonparer = new JsonParser();// 初始化解析json格式的对象
		String bopenid = null;
		String nickname = null;
		String headimgurl = null;
		String openMid = null;

		HttpResponse bres = client.execute(bget);
		String bresponseContent = null; // 响应内容
		HttpEntity bentity = bres.getEntity();
		bresponseContent = EntityUtils.toString(bentity, "UTF-8");
		JsonObject bjson = bjsonparer.parse(bresponseContent).getAsJsonObject();
		if (bres.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			if (bjson.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
				ModelAndView mnv = new ModelAndView();
				mnv.setViewName("../suld/failedToGet");
				return mnv;
			} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
					// result = json.get("ip_list").getAsString();
				nickname = bjson.get("nickname").getAsString();
				headimgurl = bjson.get("headimgurl").getAsString();
				openMid = bjson.get("openid").getAsString();
			}
		}

		TUsers webUser = new TUsers();
		webUser.setOpenId(openMid);
		List webList = WebUserService.findByExample(webUser);
		if (!webList.isEmpty()) {
			webUser = (TUsers) webList.get(0);
		} else {
			webUser.setChatName(nickname);
			webUser.setState("新人");
			webUser.setHeadUrl(headimgurl);
			WebUserService.store(webUser);
		}
		request.getSession().setAttribute("webUser", webUser);

		TInout bean = new TInout();
		bean.setUSERSID(openMid);

		Date now = new Date();
		// SimpleDateFormat dateFormat = new
		// SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
		String nowtimes = dateFormat.format(now);
		bean.setDAYTIME(nowtimes);
		List list = WebUserService.getTInout(openMid, nowtimes);
		if (!list.isEmpty()) {
			bean = (TInout) list.get(0);
		} else {
			bean.setUSERNAME(nickname);
			bean.setDAYTYPE("正常");
			bean.setFIRSTCHECKSTATE("未签到");
			bean.setTWOCHECKSTATE("未签到");
			bean.setTHREECHECKSTATE("未签到");
			bean.setFOURCHECKSTATE("未签到");
			bean.setHEADURL(headimgurl);
			WebUserService.store(bean);
		}
		String signature = WebUserService.getSignature(code);
		request.getSession().setAttribute("bean", bean);
		request.getSession().setAttribute("signature", signature);

		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, -6);
		String opendId = openMid;

		String week = "";
		/* 获取一周七天的值 */
		List listInout = new ArrayList();
		for (int j = 0; j < 7; j++) {
			/* 获取当前日历的日期的星期数（1:星期天） */
			int week_index = cal.get(Calendar.DAY_OF_WEEK);
			Date date = cal.getTime();
			/* 日期格式化 yyyy-MM-dd M必须大写 */
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(date);

			/* 将日历日期推后1天 */
			cal.add(cal.DATE, +1);
			String weekId = String.valueOf(week_index);

			if (week_index == 1) {
				week = "周日";
			} else if (week_index == 2) {
				week = "周一";
			} else if (week_index == 3) {
				week = "周二";
			} else if (week_index == 4) {
				week = "周三";
			} else if (week_index == 5) {
				week = "周四";
			} else if (week_index == 6) {
				week = "周五";
			} else if (week_index == 7) {
				week = "周六";
			}

			List listTemp = WebUserService.getTInout(opendId, dateString);
			if (!listTemp.isEmpty()) {

				TInout beanIn = (TInout) listTemp.get(0);
				beanIn.setWEEK(week);
				beanIn.setDAYTIME(dateString.substring(5, 10));
				listInout.add(beanIn);
			} else {

				TInout beanIn = new TInout();
				beanIn.setWEEK(week);
				beanIn.setUSERSID(opendId);
				beanIn.setDAYTIME(dateString.substring(5, 10));
				beanIn.setFIRSTCHECKSTATE("未签到");
				beanIn.setTWOCHECKSTATE("未签退");
				beanIn.setTHREECHECKSTATE("未签到");
				beanIn.setFOURCHECKSTATE("未签退");
				listInout.add(beanIn);
			}
		}
		request.setAttribute("list", listInout);
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarInOutQuery");
		return mnv;

	}

	/** end first enter the Inout History page **/

	/** first enter the leave list page **/
	public ModelAndView firstMobileLeaveHis(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String apiurl = "https://open.weixin.qq.com/connect/oauth2/authorize";
		String appUrl = SessionInfo.appUrl;
		String redirect_uri = java.net.URLEncoder.encode("http://" + appUrl
				+ "/suld_/suld.do?method=indexInterLeaveHis", "UTF-8");
		String appid = SessionInfo.appid;
		String secret = SessionInfo.secret;

		String turl = String
				.format("%s?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect",
						apiurl, appid, redirect_uri);
		response.sendRedirect(turl);
		ModelAndView mnv = new ModelAndView();

		return null;
	}

	public ModelAndView indexInterLeaveHis(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = request.getParameter("code");

		String appid = SessionInfo.appid;
		String secret = SessionInfo.secret;
		String state = request.getParameter("STATE");
		String url = " https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid
				+ "&secret="
				+ secret
				+ "&code="
				+ code
				+ "&grant_type=authorization_code ";
		String turl = "https://api.weixin.qq.com/sns/oauth2/access_token";
		String redirect_uri = java.net.URLEncoder.encode(url, "UTF-8");
		String tturl = String.format(
				"%s?appid=%s&secret=%s&code=%s&&grant_type=authorization_code",
				turl, appid, secret, code);

		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(tturl);
		JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
		String openid = null;
		String access_token = null;
		String scope = null;

		HttpResponse res = client.execute(get);
		String responseContent = null; // 响应内容
		HttpEntity entity = res.getEntity();
		responseContent = EntityUtils.toString(entity, "UTF-8");
		JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();

		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			if (json.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
				ModelAndView mnv = new ModelAndView();
				mnv.setViewName("../suld/failedToGet");
				return mnv;
			} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
					// result = json.get("ip_list").getAsString();
				access_token = json.get("access_token").getAsString();
				openid = json.get("openid").getAsString();
				access_token = json.get("access_token").getAsString();
				scope = json.get("scope").getAsString();

			}
		}

		String burl = "https://api.weixin.qq.com/sns/userinfo";
		String bburl = String.format(
				"%s?access_token=%s&openid=%s&&lang=zh_CN", burl, access_token,
				openid);

		HttpClient bclient = new DefaultHttpClient();
		HttpGet bget = new HttpGet(bburl);
		JsonParser bjsonparer = new JsonParser();// 初始化解析json格式的对象
		String bopenid = null;
		String nickname = null;
		String headimgurl = null;
		String openMid = null;

		HttpResponse bres = client.execute(bget);
		String bresponseContent = null; // 响应内容
		HttpEntity bentity = bres.getEntity();
		bresponseContent = EntityUtils.toString(bentity, "UTF-8");
		JsonObject bjson = bjsonparer.parse(bresponseContent).getAsJsonObject();
		if (bres.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			if (bjson.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
				ModelAndView mnv = new ModelAndView();
				mnv.setViewName("../suld/failedToGet");
				return mnv;
			} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
					// result = json.get("ip_list").getAsString();
				nickname = bjson.get("nickname").getAsString();
				headimgurl = bjson.get("headimgurl").getAsString();
				openMid = bjson.get("openid").getAsString();
			}
		}

		TUsers webUser = new TUsers();
		webUser.setOpenId(openMid);
		List webList = WebUserService.findByExample(webUser);
		if (!webList.isEmpty()) {
			webUser = (TUsers) webList.get(0);
		} else {
			webUser.setChatName(nickname);
			webUser.setState("正常");
			webUser.setHeadUrl(headimgurl);
			WebUserService.store(webUser);
		}
		request.getSession().setAttribute("webUser", webUser);

		TInout bean = new TInout();
		bean.setUSERSID(openMid);

		Date now = new Date();
		// SimpleDateFormat dateFormat = new
		// SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
		String nowtimes = dateFormat.format(now);
		bean.setDAYTIME(nowtimes);
		List list = WebUserService.getTInout(openMid, nowtimes);
		if (!list.isEmpty()) {
			bean = (TInout) list.get(0);
		} else {
			bean.setUSERNAME(nickname);
			bean.setDAYTYPE("正常");
			bean.setFIRSTCHECKSTATE("未签到");
			bean.setTWOCHECKSTATE("未签到");
			bean.setTHREECHECKSTATE("未签到");
			bean.setFOURCHECKSTATE("未签到");
			bean.setHEADURL(headimgurl);
			WebUserService.store(bean);
		}
		String signature = WebUserService.getSignature(code);
		request.getSession().setAttribute("bean", bean);
		request.getSession().setAttribute("signature", signature);

		TLeave beanLeave = new TLeave();
		beanLeave.setUSERSID(openMid);
		beanLeave.setLeaveState("正常");
		beanLeave.setFLAGSTATE("审批中");
		List proTLeaves = WebUserService.findByExample(beanLeave);
		beanLeave.setFLAGSTATE("已通过");
		List succTLeaves = WebUserService.findByExample(beanLeave);
		beanLeave.setFLAGSTATE("未通过");
		List failTLeaves = WebUserService.findByExample(beanLeave);
		for (int i = 0; i < failTLeaves.size(); i++) {
			succTLeaves.add(failTLeaves.get(i));
		}

		request.getSession().setAttribute("proTLeaves", proTLeaves);
		request.getSession().setAttribute("succTLeaves", succTLeaves);
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarLeaveList");
		return mnv;

	}

	/** MobileLeaveDetail Query **/
	public ModelAndView moibleLeaveDeatail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sid = request.getParameter("sid");
		TLeave bean = new TLeave();
		bean.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(bean);
		if (!list.isEmpty()) {
			bean = (TLeave) list.get(0);
		}
		request.setAttribute("bean", bean);
		
		TTemp tempBean = new TTemp();
		tempBean.setUserSid(bean.getUSERSID());
		tempBean.setUnitName(bean.getUNITNAME());
		List tempList = WebUserService.findByExample(tempBean);
		if (!tempList.isEmpty()) {
			tempBean = (TTemp) tempList.get(0);
		}
		request.setAttribute("tempBean", tempBean);
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarLeaveDetail");
		return mnv;

	}
	/** MobileLeaveSubLink  **/
	public ModelAndView moibleLeaveSubLink(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sid = request.getParameter("sid");
		TLeave bean = new TLeave();
		bean.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(bean);
		if (!list.isEmpty()) {
			bean = (TLeave) list.get(0);
		}
		request.setAttribute("bean", bean);

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarLeaveSub");
		return mnv;

	}
	/** MobileLeaveDetail QueryAdmin **/
	public ModelAndView moibleLeaveDeatailAdmin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sid = request.getParameter("sid");
		TLeave bean = new TLeave();
		bean.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(bean);
		if (!list.isEmpty()) {
			bean = (TLeave) list.get(0);
		}
		request.setAttribute("bean", bean);
		TTemp tempBean = new TTemp();
		tempBean.setUserSid(bean.getUSERSID());
		tempBean.setUnitName(bean.getUNITNAME());
		List tempList = WebUserService.findByExample(tempBean);
		if (!tempList.isEmpty()) {
			tempBean = (TTemp) tempList.get(0);
		}
		request.setAttribute("tempBean", tempBean);
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarLeaveDetailAdmin");
		return mnv;

	}

	/** first enter the leave list Adminpage **/
	public ModelAndView firstMobileLeaveHisAdmin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String apiurl = "https://open.weixin.qq.com/connect/oauth2/authorize";
		String appUrl = SessionInfo.appUrl;
		String redirect_uri = java.net.URLEncoder.encode("http://" + appUrl
				+ "/suld_/suld.do?method=indexInterLeaveHisAdmin", "UTF-8");
		String appid = SessionInfo.appid;
		String secret = SessionInfo.secret;

		String turl = String
				.format("%s?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect",
						apiurl, appid, redirect_uri);
		response.sendRedirect(turl);
		ModelAndView mnv = new ModelAndView();

		return null;
	}

	public ModelAndView indexInterLeaveHisAdmin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = request.getParameter("code");

		String appid = SessionInfo.appid;
		String secret = SessionInfo.secret;
		String state = request.getParameter("STATE");
		String url = " https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid
				+ "&secret="
				+ secret
				+ "&code="
				+ code
				+ "&grant_type=authorization_code ";
		String turl = "https://api.weixin.qq.com/sns/oauth2/access_token";
		String redirect_uri = java.net.URLEncoder.encode(url, "UTF-8");
		String tturl = String.format(
				"%s?appid=%s&secret=%s&code=%s&&grant_type=authorization_code",
				turl, appid, secret, code);

		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(tturl);
		JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
		String openid = null;
		String access_token = null;
		String scope = null;

		HttpResponse res = client.execute(get);
		String responseContent = null; // 响应内容
		HttpEntity entity = res.getEntity();
		responseContent = EntityUtils.toString(entity, "UTF-8");
		JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();

		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			if (json.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
				ModelAndView mnv = new ModelAndView();
				mnv.setViewName("../suld/failedToGet");
				return mnv;
			} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
					// result = json.get("ip_list").getAsString();
				access_token = json.get("access_token").getAsString();
				openid = json.get("openid").getAsString();
				access_token = json.get("access_token").getAsString();
				scope = json.get("scope").getAsString();

			}
		}

		String burl = "https://api.weixin.qq.com/sns/userinfo";
		String bburl = String.format(
				"%s?access_token=%s&openid=%s&&lang=zh_CN", burl, access_token,
				openid);

		HttpClient bclient = new DefaultHttpClient();
		HttpGet bget = new HttpGet(bburl);
		JsonParser bjsonparer = new JsonParser();// 初始化解析json格式的对象
		String bopenid = null;
		String nickname = null;
		String headimgurl = null;
		String openMid = null;

		HttpResponse bres = client.execute(bget);
		String bresponseContent = null; // 响应内容
		HttpEntity bentity = bres.getEntity();
		bresponseContent = EntityUtils.toString(bentity, "UTF-8");
		JsonObject bjson = bjsonparer.parse(bresponseContent).getAsJsonObject();
		if (bres.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			if (bjson.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
				ModelAndView mnv = new ModelAndView();
				mnv.setViewName("../suld/failedToGet");
				return mnv;
			} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
					// result = json.get("ip_list").getAsString();
				nickname = bjson.get("nickname").getAsString();
				headimgurl = bjson.get("headimgurl").getAsString();
				openMid = bjson.get("openid").getAsString();
			}
		}

		TUsers webUser = new TUsers();
		webUser.setOpenId(openMid);
		List webList = WebUserService.findByExample(webUser);
		if (!webList.isEmpty()) {
			webUser = (TUsers) webList.get(0);
		} else {
			webUser.setChatName(nickname);

			webUser.setHeadUrl(headimgurl);
			webUser.setOpenId(openMid);
			request.getSession().setAttribute("webUser", webUser);
			ModelAndView mnv = new ModelAndView();
			mnv.setViewName("../suld/tatarRegister");
			return mnv;

		}
		request.getSession().setAttribute("webUser", webUser);

		TInout bean = new TInout();
		bean.setUSERSID(openMid);

		Date now = new Date();
		// SimpleDateFormat dateFormat = new
		// SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
		String nowtimes = dateFormat.format(now);
		bean.setDAYTIME(nowtimes);
		List list = WebUserService.getTInout(openMid, nowtimes);
		if (!list.isEmpty()) {
			bean = (TInout) list.get(0);
		} else {
			bean.setUSERNAME(nickname);
			bean.setDAYTYPE("正常");
			bean.setFIRSTCHECKSTATE("未签到");
			bean.setTWOCHECKSTATE("未签到");
			bean.setTHREECHECKSTATE("未签到");
			bean.setFOURCHECKSTATE("未签到");
			bean.setHEADURL(headimgurl);
			WebUserService.store(bean);
		}
		String signature = WebUserService.getSignature(code);
		request.getSession().setAttribute("bean", bean);
		request.getSession().setAttribute("signature", signature);

		TLeave beanLeave = new TLeave();
		beanLeave.setFIRSTAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setLeaveState("正常");
		beanLeave.setFIRSTAPPROVALSTATE("待审批");
		List listAdminPro = WebUserService.findByExample(beanLeave);

		beanLeave = new TLeave();
		beanLeave.setSECONDAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setFIRSTAPPROVALSTATE("已通过");
		beanLeave.setSECONDAPPROVALSTATE("待审批");
		List listAdmin2Pro = WebUserService.findByExample(beanLeave);

		beanLeave = new TLeave();
		beanLeave.setTHREEAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setSECONDAPPROVALSTATE("已通过");
		beanLeave.setTHREEAPPROVALSTATE("待审批");
		List listAdmin3Pro = WebUserService.findByExample(beanLeave);

		for (int i = 0; i < listAdmin2Pro.size(); i++) {
			listAdminPro.add(listAdmin2Pro.get(i));
		}
		for (int i = 0; i < listAdmin3Pro.size(); i++) {
			listAdminPro.add(listAdmin3Pro.get(i));
		}

		beanLeave = new TLeave();
		beanLeave.setFIRSTAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setFIRSTAPPROVALSTATE("已通过");
		List listAdminSucc = WebUserService.findByExample(beanLeave);

		beanLeave = new TLeave();
		beanLeave.setSECONDAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setSECONDAPPROVALSTATE("已通过");
		List listAdmin2Succ = WebUserService.findByExample(beanLeave);

		beanLeave = new TLeave();
		beanLeave.setTHREEAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setTHREEAPPROVALSTATE("已通过");
		List listAdmin3Succ = WebUserService.findByExample(beanLeave);

		/**
		 * Suld goverment's one leader have two rules . Avoid repeat record add
		 * the list.
		 **/
		for (int i = 0; i < listAdmin2Succ.size(); i++) {
			TLeave tempi = (TLeave) listAdmin2Succ.get(i);
			String opendIdI = tempi.getUSERSID();
			boolean flag = true;
			for (int j = 0; j < listAdminSucc.size(); j++) {
				TLeave tempj = (TLeave) listAdminSucc.get(j);
				String opendIdJ = tempj.getUSERSID();
				if (opendIdJ.equals(opendIdI)) {
					flag = false;
					break;
				}
			}
			if (flag) {
				listAdminSucc.add(listAdmin2Succ.get(i));
			}

		}
		/** end **/
		for (int i = 0; i < listAdmin3Succ.size(); i++) {
			listAdminSucc.add(listAdmin3Succ.get(i));
		}

		beanLeave = new TLeave();
		beanLeave.setFIRSTAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setFIRSTAPPROVALSTATE("未通过");
		List listAdminFail = WebUserService.findByExample(beanLeave);

		beanLeave = new TLeave();
		beanLeave.setSECONDAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setSECONDAPPROVALSTATE("未通过");
		List listAdmin2Fail = WebUserService.findByExample(beanLeave);

		beanLeave = new TLeave();
		beanLeave.setTHREEAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setTHREEAPPROVALSTATE("未通过");
		List listAdmin3Fail = WebUserService.findByExample(beanLeave);

		for (int i = 0; i < listAdminFail.size(); i++) {
			listAdminSucc.add(listAdminFail.get(i));
		}
		for (int i = 0; i < listAdmin2Fail.size(); i++) {
			listAdminSucc.add(listAdmin2Fail.get(i));
		}
		for (int i = 0; i < listAdmin3Fail.size(); i++) {
			listAdminSucc.add(listAdmin3Fail.get(i));
		}

		request.getSession().setAttribute("listAdminPro", listAdminPro);
		request.getSession().setAttribute("listAdminSucc", listAdminSucc);
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarLeaveListAdmin");
		return mnv;

	}

	/** 活动签退 **/
	public ModelAndView firstMobileAreaCheck(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String apiurl = "https://open.weixin.qq.com/connect/oauth2/authorize";
		String appUrl = SessionInfo.appUrl;
		String redirect_uri = java.net.URLEncoder.encode("http://" + appUrl
				+ "/suld_/suld.do?method=indexInterAreaCheck", "UTF-8");
		String appid = SessionInfo.appid;
		String secret = SessionInfo.secret;

		String turl = String
				.format("%s?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect",
						apiurl, appid, redirect_uri);
		response.sendRedirect(turl);
		ModelAndView mnv = new ModelAndView();

		return null;
	}

	public ModelAndView indexInterAreaCheck(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = request.getParameter("code");

		String appid = SessionInfo.appid;
		String secret = SessionInfo.secret;
		String state = request.getParameter("STATE");
		String url = " https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid
				+ "&secret="
				+ secret
				+ "&code="
				+ code
				+ "&grant_type=authorization_code ";
		String turl = "https://api.weixin.qq.com/sns/oauth2/access_token";
		String redirect_uri = java.net.URLEncoder.encode(url, "UTF-8");
		String tturl = String.format(
				"%s?appid=%s&secret=%s&code=%s&&grant_type=authorization_code",
				turl, appid, secret, code);

		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(tturl);
		JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
		String openid = null;
		String access_token = null;
		String scope = null;

		HttpResponse res = client.execute(get);
		String responseContent = null; // 响应内容
		HttpEntity entity = res.getEntity();
		responseContent = EntityUtils.toString(entity, "UTF-8");
		JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();

		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			if (json.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
				ModelAndView mnv = new ModelAndView();
				mnv.setViewName("../suld/failedToGet");
				return mnv;
			} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
					// result = json.get("ip_list").getAsString();
				access_token = json.get("access_token").getAsString();
				openid = json.get("openid").getAsString();
				access_token = json.get("access_token").getAsString();
				scope = json.get("scope").getAsString();

			}
		}

		String burl = "https://api.weixin.qq.com/sns/userinfo";
		String bburl = String.format(
				"%s?access_token=%s&openid=%s&&lang=zh_CN", burl, access_token,
				openid);

		HttpClient bclient = new DefaultHttpClient();
		HttpGet bget = new HttpGet(bburl);
		JsonParser bjsonparer = new JsonParser();// 初始化解析json格式的对象
		String bopenid = null;
		String nickname = null;
		String headimgurl = null;
		String openMid = null;

		HttpResponse bres = client.execute(bget);
		String bresponseContent = null; // 响应内容
		HttpEntity bentity = bres.getEntity();
		bresponseContent = EntityUtils.toString(bentity, "UTF-8");
		JsonObject bjson = bjsonparer.parse(bresponseContent).getAsJsonObject();
		if (bres.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			if (bjson.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
				ModelAndView mnv = new ModelAndView();
				mnv.setViewName("../suld/failedToGet");
				return mnv;
			} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
					// result = json.get("ip_list").getAsString();
				nickname = bjson.get("nickname").getAsString();
				headimgurl = bjson.get("headimgurl").getAsString();
				openMid = bjson.get("openid").getAsString();
			}
		}

		TUsers webUser = new TUsers();
		webUser.setOpenId(openMid);
		List webList = WebUserService.findByExample(webUser);
		if (!webList.isEmpty()) {
			webUser = (TUsers) webList.get(0);
			ModelAndView mnv = new ModelAndView();
			mnv.setViewName("../suld/tatarAreaList");

			TArea area = new TArea();
			List list = WebUserService.getTInoutByKindIdArea(webUser
					.getUnitId());
			TInout tinout = new TInout();
			if (null == list || list.size() == 0) {
				// 为空的情况
				request.setAttribute("listAreaNo", list);

			} else {

				area = (TArea) list.get(0);
				tinout.setUSERSID(webUser.getOpenId());
				tinout.setWEEK(area.getSid().toString());
				List listArea = WebUserService.findByExample(tinout);
				if (null == listArea || listArea.size() == 0) {
					request.setAttribute("listAreaNo", list);
					String signature = WebUserService.getSignatureArea(code);
					System.out.println("signature=" + signature);
					request.getSession().setAttribute("signature", signature);
				} else {
					request.setAttribute("listAreaYes", list);
				}
			}

			return mnv;
		} else {
			/**
			 * webUser.setChatName(nickname); webUser.setState("正常");
			 * webUser.setHeadUrl(headimgurl); WebUserService.store(webUser);
			 **/
			ModelAndView mnv = new ModelAndView();
			mnv.setViewName("../suld/tatarRegister");
			return mnv;
		}

	}
	
	public ModelAndView getAreaAddress(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		String latitudeId = request.getParameter("latitudeId");
		String longitudeId = request.getParameter("longitudeId");
		String areaId = request.getParameter("areaId");
		TInout bean = new TInout();
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
		String nowtimes = dateFormat.format(now);
		SimpleDateFormat dateFormat2 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");// 可以方便地修改日期格式
		String nowtimes2 = dateFormat2.format(now);

		bean.setDAYTIME("2000-01-01");
		bean.setDAYTYPE("活动");
		bean.setUSERNAME(webUser.getUserName());
		bean.setUSERSID(webUser.getOpenId());
		bean.setUNITID(webUser.getUnitId());
		bean.setUNITNAME(webUser.getUnitName());
		bean.setWEEK(areaId);
		bean.setFIRSTCHECKLATIID(latitudeId);
		bean.setFIRSTCHECKLONGID(longitudeId);
		bean.setFIRSTCHECKSTATE("已签到");
		bean.setFIRSTCHECKTIME(nowtimes2);

		WebUserService.store(bean);

		return firstMobileAreaCheck(request, response);

	}

	/** second enter the edit self page **/
	public ModelAndView selfEditMobile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sid = request.getParameter("sid");

		String trueName = new String(
				(request.getParameter("trueName")).getBytes("iso-8859-1"),
				"utf-8");
		String mobile = new String(
				(request.getParameter("mobile")).getBytes("iso-8859-1"),
				"utf-8");
		String unitName = new String(
				(request.getParameter("unitName")).getBytes("iso-8859-1"),
				"utf-8");
		String department = new String(
				(request.getParameter("department")).getBytes("iso-8859-1"),
				"utf-8");

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		//TUsers webUser = new TUsers();
		webUser.setSid(Integer.valueOf(sid));
		//List list = WebUserService.findByExample(webUser);
		//webUser = (TUsers) list.get(0);
		webUser.setUserName(trueName);
		webUser.setMobile(mobile);
		webUser.setUnitName(unitName);
		webUser.setDepartmentName(department);
		WebUserService.store(webUser);
		request.getSession().setAttribute("webUser", webUser);
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarSelf");
		return mnv;
	}

	/** second enter the edit remark page **/
	public ModelAndView selfRemarkMobile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sid = request.getParameter("sid");
		String remark = new String(
				(request.getParameter("remark")).getBytes("iso-8859-1"),
				"utf-8");

		TUsers webUser = new TUsers();
		webUser.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(webUser);
		webUser = (TUsers) list.get(0);
		remark = "-" + remark + "-" + webUser.getRemark();
		webUser.setRemark(remark);
		WebUserService.store(webUser);
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarSelf");
		return mnv;
	}

	/** Admin update the leavel **/
	public ModelAndView moibleLeaveSubmitAdmin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sid = request.getParameter("sid");
		String kind = request.getParameter("kind");
		String flag = request.getParameter("flag");
		String remark = request.getParameter("remark");

		TLeave bean = new TLeave();
		bean.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(bean);
		bean = (TLeave) list.get(0);
		if (remark != null) {
			remark = new String(
					(request.getParameter("remark")).getBytes("iso-8859-1"),
					"utf-8");

		}

		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");// 可以方便地修改日期格式
		String nowtimes = dateFormat.format(now);
		if (kind.equals("1")) {
			if (flag.equals("1")) {
				bean.setFIRSTAPPROVALDATE(nowtimes);
				bean.setFIRSTAPPROVALSTATE("已通过");
				bean.setFIRSTAPPROVALREMARK(remark);
				try {

					String[] adminOpenId = bean.getSECONDAPPROVALPERSON().split("-");

					TUsers tuser = new TUsers();
					tuser.setSid(Integer.valueOf(adminOpenId[0]));

					List listTmep = WebUserService.findByExample(tuser);

					tuser = (TUsers) listTmep.get(0);

					//SendService ser = new SendService();
					String leaveTime = bean.getSTARTTIME() + "到" + bean.getENDTIME()
							+ "  " + bean.getDAYNUM() + "天";
					//ser.sendLeave(bean.getUSERNAME(), bean.getTLEAVETYPE(), leaveTime,
					//		bean.getREMARK(), tuser.getOpenId());
					
					
					Template tem=new Template();  
					   
				    tem.setTouser(tuser.getOpenId());  // oQMBIwaHaXWaREwzoBXScKEfaBoM ogx_NvxGZAEnkDCeqTgKZd1t7NxE	    
				    tem.setTemplateId("6qWB1fhta9lQ1DkVJP44VIMvFO_m8LKKBSq7qqZNgjE");  
				    tem.setUrl("http://www.tataren.com/suld_/suld.do?method=firstMobileLeaveHisAdmin");  
				              
				    List<TemplateParam> data=new ArrayList<TemplateParam>();  
				    data.add(new TemplateParam("first","请假审批提醒","#FF3333"));  
				    data.add(new TemplateParam("keyword1",bean.getUSERNAME(),"#0044BB"));  
				    data.add(new TemplateParam("keyword2",bean.getTLEAVETYPE(),"#0044BB"));  
				    data.add(new TemplateParam("keyword3",leaveTime,"#0044BB"));  
				    data.add(new TemplateParam("keyword4",bean.getREMARK(),"#0044BB")); 
				    data.add(new TemplateParam("Remark","请及时处理此条信息！ ","#AAAAAA"));  
				   		    tem.setTemplateParamList(data);  
				    String token =WeChatValue.getAccessToken();
				    String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
					requestUrl = requestUrl.replace("ACCESS_TOKEN", token);

					CommonUtil.httpsRequest(requestUrl, "POST",
							tem.toJSON());
				  

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				bean.setFIRSTAPPROVALDATE(nowtimes);
				bean.setFIRSTAPPROVALSTATE("未通过");
				bean.setFIRSTAPPROVALREMARK(remark);
				bean.setFLAGSTATE("未通过");
				try {
					
					String leaveTime = bean.getSTARTTIME() + "到" + bean.getENDTIME()
							+ "  " + bean.getDAYNUM() + "天";				
					Template tem=new Template();  
					
				    tem.setTouser(bean.getUSERSID());  // oQMBIwaHaXWaREwzoBXScKEfaBoM ogx_NvxGZAEnkDCeqTgKZd1t7NxE	    
				    tem.setTemplateId("wmQ7M8l6sZd1FF2VgyWYhQplkjwMP-rVfiAehgE6f3c");  
				    tem.setUrl("http://www.tataren.com/suld_/suld.do?method=firstMobileLeaveHisAdmin");  
				              
				    List<TemplateParam> data=new ArrayList<TemplateParam>();  
				    data.add(new TemplateParam("first","请假结果通知","#FF3333"));  
				    data.add(new TemplateParam("keyword1",bean.getTHREEAPPROVALPERSON(),"#0044BB"));  
				    data.add(new TemplateParam("keyword2",bean.getFLAGSTATE(),"#0044BB"));  
				    data.add(new TemplateParam("Remark",leaveTime+"       点击详情，进行查看！ ","#AAAAAA"));  
				   		    tem.setTemplateParamList(data);  
				    String token =WeChatValue.getAccessToken();
				    String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
					requestUrl = requestUrl.replace("ACCESS_TOKEN", token);

					CommonUtil.httpsRequest(requestUrl, "POST",
							tem.toJSON());			  
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (kind.equals("2")) {
			if (flag.equals("1")) {
				bean.setSECONDAPPROVALDATE(nowtimes);
				bean.setSECONDAPPROVALSTATE("已通过");
				bean.setSECONDAPPROVALREMARK(remark);
				if (bean.getTHREEAPPROVALSTATE().equals("无需审批")) {
					bean.setFLAGSTATE("已通过");
					// 插入 签到签退表
					Calendar cal = Calendar.getInstance();
					String dateValue[] = bean.getSTARTTIME().split("-");
					cal.set(Integer.valueOf(dateValue[0]),
							Integer.valueOf(dateValue[1]) - 1,
							Integer.valueOf(dateValue[2]));
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd");
					String opendId = bean.getUSERSID();
					for (double j = 0; j < Double.parseDouble(bean.getDAYNUM()); j++) {

						Date date = cal.getTime();
						String dateString = formatter.format(date);
						cal.add(cal.DATE, +1);

						List listTemp = WebUserService.getTInout(opendId,
								dateString);
						if (!listTemp.isEmpty()) {
							TInout beanIn = (TInout) listTemp.get(0);
							beanIn.setDAYTYPE(bean.getTLEAVETYPE());
							WebUserService.store(beanIn);
						} else {
							TInout beanIn = new TInout();
							beanIn.setUSERSID(opendId);
							beanIn.setDAYTIME(dateString);
							beanIn.setDAYTYPE(bean.getTLEAVETYPE());
							beanIn.setUSERNAME(bean.getUSERNAME());
							beanIn.setHEADURL(bean.getPHOTO());
							beanIn.setUNITNAME(bean.getUNITNAME());
							beanIn.setUNITID(bean.getTUNITID());
							beanIn.setFIRSTCHECKSTATE("未签到");
							beanIn.setTWOCHECKSTATE("未签到");
							beanIn.setTHREECHECKSTATE("未签到");
							beanIn.setFOURCHECKSTATE("未签到");
							WebUserService.store(beanIn);
						}
					}
					// 插入签到签退表结束
					try {
						
						String leaveTime = bean.getSTARTTIME() + "到" + bean.getENDTIME()
								+ "  " + bean.getDAYNUM() + "天";				
						Template tem=new Template();  
						
					    tem.setTouser(bean.getUSERSID());  // oQMBIwaHaXWaREwzoBXScKEfaBoM ogx_NvxGZAEnkDCeqTgKZd1t7NxE	    
					    tem.setTemplateId("wmQ7M8l6sZd1FF2VgyWYhQplkjwMP-rVfiAehgE6f3c");  
					    tem.setUrl("http://www.tataren.com/suld_/suld.do?method=firstMobileLeaveHisAdmin");  
					              
					    List<TemplateParam> data=new ArrayList<TemplateParam>();  
					    data.add(new TemplateParam("first","请假结果通知","#FF3333"));  
					    data.add(new TemplateParam("keyword1",bean.getTHREEAPPROVALPERSON(),"#0044BB"));  
					    data.add(new TemplateParam("keyword2",bean.getFLAGSTATE(),"#0044BB"));  
					    data.add(new TemplateParam("Remark",leaveTime+"       点击详情，进行查看！ ","#AAAAAA"));  
					   		    tem.setTemplateParamList(data);  
					    String token =WeChatValue.getAccessToken();
					    String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
						requestUrl = requestUrl.replace("ACCESS_TOKEN", token);

						CommonUtil.httpsRequest(requestUrl, "POST",
								tem.toJSON());			  
					} catch (Exception e) {
						e.printStackTrace();
					}

				}else{
					try {

						String[] adminOpenId = bean.getTHREEAPPROVALPERSON().split("-");

						TUsers tuser = new TUsers();
						tuser.setSid(Integer.valueOf(adminOpenId[0]));

						List listTmep = WebUserService.findByExample(tuser);

						tuser = (TUsers) listTmep.get(0);

						//SendService ser = new SendService();
						String leaveTime = bean.getSTARTTIME() + "到" + bean.getENDTIME()
								+ "  " + bean.getDAYNUM() + "天";
						//ser.sendLeave(bean.getUSERNAME(), bean.getTLEAVETYPE(), leaveTime,
						//		bean.getREMARK(), tuser.getOpenId());
						
						
						Template tem=new Template();  
						
					    tem.setTouser(tuser.getOpenId());  // oQMBIwaHaXWaREwzoBXScKEfaBoM ogx_NvxGZAEnkDCeqTgKZd1t7NxE	    
					    tem.setTemplateId("6qWB1fhta9lQ1DkVJP44VIMvFO_m8LKKBSq7qqZNgjE");  
					    tem.setUrl("http://www.tataren.com/suld_/suld.do?method=firstMobileLeaveHisAdmin");  
					              
					    List<TemplateParam> data=new ArrayList<TemplateParam>();  
					    data.add(new TemplateParam("first","请假审批提醒","#FF3333"));  
					    data.add(new TemplateParam("keyword1",bean.getUSERNAME(),"#0044BB"));  
					    data.add(new TemplateParam("keyword2",bean.getTLEAVETYPE(),"#0044BB"));  
					    data.add(new TemplateParam("keyword3",leaveTime,"#0044BB"));  
					    data.add(new TemplateParam("keyword4",bean.getREMARK(),"#0044BB")); 
					    data.add(new TemplateParam("Remark","请及时处理此条信息！ ","#AAAAAA"));  
					   		    tem.setTemplateParamList(data);  
					    String token =WeChatValue.getAccessToken();
					    String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
						requestUrl = requestUrl.replace("ACCESS_TOKEN", token);

						CommonUtil.httpsRequest(requestUrl, "POST",
								tem.toJSON());
					  

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} else {
				bean.setSECONDAPPROVALDATE(nowtimes);
				bean.setSECONDAPPROVALSTATE("未通过");
				bean.setSECONDAPPROVALREMARK(remark);
				bean.setFLAGSTATE("未通过");
				try {
					
					String leaveTime = bean.getSTARTTIME() + "到" + bean.getENDTIME()
							+ "  " + bean.getDAYNUM() + "天";				
					Template tem=new Template();  
					
				    tem.setTouser(bean.getUSERSID());  // oQMBIwaHaXWaREwzoBXScKEfaBoM ogx_NvxGZAEnkDCeqTgKZd1t7NxE	    
				    tem.setTemplateId("wmQ7M8l6sZd1FF2VgyWYhQplkjwMP-rVfiAehgE6f3c");  
				    tem.setUrl("http://www.tataren.com/suld_/suld.do?method=firstMobileLeaveHisAdmin");  
				              
				    List<TemplateParam> data=new ArrayList<TemplateParam>();  
				    data.add(new TemplateParam("first","请假结果通知","#FF3333"));  
				    data.add(new TemplateParam("keyword1",bean.getTHREEAPPROVALPERSON(),"#0044BB"));  
				    data.add(new TemplateParam("keyword2",bean.getFLAGSTATE(),"#0044BB"));  
				    data.add(new TemplateParam("Remark",leaveTime+"       点击详情，进行查看！ ","#AAAAAA"));  
				   		    tem.setTemplateParamList(data);  
				    String token =WeChatValue.getAccessToken();
				    String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
					requestUrl = requestUrl.replace("ACCESS_TOKEN", token);

					CommonUtil.httpsRequest(requestUrl, "POST",
							tem.toJSON());			  
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			

		} else if (kind.equals("3")) {
			if (flag.equals("1")) {
				bean.setTHREEAPPROVALDATE(nowtimes);
				bean.setTHREEAPPROVALSTATE("已通过");
				bean.setTHREEAPPROVALREMARK(remark);
				bean.setFLAGSTATE("已通过");
				// 插入 签到签退表
				Calendar cal = Calendar.getInstance();
				String dateValue[] = bean.getSTARTTIME().split("-");
				cal.set(Integer.valueOf(dateValue[0]),
						Integer.valueOf(dateValue[1]) - 1,
						Integer.valueOf(dateValue[2]));
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String opendId = bean.getUSERSID();
				for (double j = 0; j < Double.parseDouble(bean.getDAYNUM()); j++) {

					Date date = cal.getTime();
					String dateString = formatter.format(date);
					cal.add(cal.DATE, +1);

					List listTemp = WebUserService.getTInout(opendId,
							dateString);
					if (!listTemp.isEmpty()) {
						TInout beanIn = (TInout) listTemp.get(0);
						beanIn.setDAYTYPE(bean.getTLEAVETYPE());
						WebUserService.store(beanIn);
					} else {
						TInout beanIn = new TInout();
						beanIn.setUSERSID(opendId);
						beanIn.setDAYTIME(dateString);
						beanIn.setDAYTYPE(bean.getTLEAVETYPE());
						beanIn.setUSERNAME(bean.getUSERNAME());
						beanIn.setHEADURL(bean.getPHOTO());
						beanIn.setUNITNAME(bean.getUNITNAME());
						beanIn.setUNITID(bean.getTUNITID());
						beanIn.setFIRSTCHECKSTATE("未签到");
						beanIn.setTWOCHECKSTATE("未签到");
						beanIn.setTHREECHECKSTATE("未签到");
						beanIn.setFOURCHECKSTATE("未签到");
						WebUserService.store(beanIn);

						beanIn.setFIRSTCHECKSTATE("未签到");
						beanIn.setTWOCHECKSTATE("未签到");
						beanIn.setTHREECHECKSTATE("未签到");
						beanIn.setFOURCHECKSTATE("未签到");
						WebUserService.store(beanIn);
					}
				}
				// 插入签到签退表结束

			} else {
				bean.setTHREEAPPROVALDATE(nowtimes);
				bean.setTHREEAPPROVALSTATE("未通过");
				bean.setTHREEAPPROVALREMARK(remark);
				bean.setFLAGSTATE("未通过");
			}
			try {
		
				String leaveTime = bean.getSTARTTIME() + "到" + bean.getENDTIME()
						+ "  " + bean.getDAYNUM() + "天";				
				Template tem=new Template();  
				
			    tem.setTouser(bean.getUSERSID());  // oQMBIwaHaXWaREwzoBXScKEfaBoM ogx_NvxGZAEnkDCeqTgKZd1t7NxE	    
			    tem.setTemplateId("wmQ7M8l6sZd1FF2VgyWYhQplkjwMP-rVfiAehgE6f3c");  
			    tem.setUrl("http://www.tataren.com/suld_/suld.do?method=firstMobileLeaveHisAdmin");  
			              
			    List<TemplateParam> data=new ArrayList<TemplateParam>();  
			    data.add(new TemplateParam("first","请假结果通知","#FF3333"));  
			    data.add(new TemplateParam("keyword1",bean.getTHREEAPPROVALPERSON(),"#0044BB"));  
			    data.add(new TemplateParam("keyword2",bean.getFLAGSTATE(),"#0044BB"));  
			    data.add(new TemplateParam("Remark",leaveTime+"       点击详情，进行查看！ ","#AAAAAA"));  
			   		    tem.setTemplateParamList(data);  
			    String token =WeChatValue.getAccessToken();
			    String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
				requestUrl = requestUrl.replace("ACCESS_TOKEN", token);

				CommonUtil.httpsRequest(requestUrl, "POST",
						tem.toJSON());			  
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		WebUserService.store(bean);

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");

		TLeave beanLeave = new TLeave();
		beanLeave.setFIRSTAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setLeaveState("正常");
		beanLeave.setFIRSTAPPROVALSTATE("待审批");
		List listAdminPro = WebUserService.findByExample(beanLeave);

		beanLeave = new TLeave();
		beanLeave.setSECONDAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setSECONDAPPROVALSTATE("待审批");
		beanLeave.setFIRSTAPPROVALSTATE("已通过");
		List listAdmin2Pro = WebUserService.findByExample(beanLeave);

		beanLeave = new TLeave();
		beanLeave.setTHREEAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setTHREEAPPROVALSTATE("待审批");
		beanLeave.setSECONDAPPROVALSTATE("已通过");
		List listAdmin3Pro = WebUserService.findByExample(beanLeave);

		for (int i = 0; i < listAdmin2Pro.size(); i++) {
			listAdminPro.add(listAdmin2Pro.get(i));
		}
		for (int i = 0; i < listAdmin3Pro.size(); i++) {
			listAdminPro.add(listAdmin3Pro.get(i));
		}

		beanLeave = new TLeave();
		beanLeave.setFIRSTAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setFIRSTAPPROVALSTATE("已通过");
		List listAdminSucc = WebUserService.findByExample(beanLeave);

		beanLeave = new TLeave();
		beanLeave.setSECONDAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setSECONDAPPROVALSTATE("已通过");
		List listAdmin2Succ = WebUserService.findByExample(beanLeave);

		beanLeave = new TLeave();
		beanLeave.setTHREEAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setTHREEAPPROVALSTATE("已通过");
		List listAdmin3Succ = WebUserService.findByExample(beanLeave);

		for (int i = 0; i < listAdmin2Succ.size(); i++) {
			listAdminSucc.add(listAdmin2Succ.get(i));
		}
		for (int i = 0; i < listAdmin3Succ.size(); i++) {
			listAdminSucc.add(listAdmin3Succ.get(i));
		}

		beanLeave = new TLeave();
		beanLeave.setFIRSTAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setFIRSTAPPROVALSTATE("未通过");
		List listAdminFail = WebUserService.findByExample(beanLeave);

		beanLeave = new TLeave();
		beanLeave.setSECONDAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setSECONDAPPROVALSTATE("未通过");
		List listAdmin2Fail = WebUserService.findByExample(beanLeave);

		beanLeave = new TLeave();
		beanLeave.setTHREEAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setTHREEAPPROVALSTATE("未通过");
		List listAdmin3Fail = WebUserService.findByExample(beanLeave);

		for (int i = 0; i < listAdminFail.size(); i++) {
			listAdminSucc.add(listAdminFail.get(i));
		}
		for (int i = 0; i < listAdmin2Fail.size(); i++) {
			listAdminSucc.add(listAdmin2Fail.get(i));
		}
		for (int i = 0; i < listAdmin3Fail.size(); i++) {
			listAdminSucc.add(listAdmin3Fail.get(i));
		}
		request.getSession().setAttribute("listAdminPro", listAdminPro);
		request.getSession().setAttribute("listAdminSucc", listAdminSucc);
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarLeaveListAdmin");
		return mnv;

	}

	/** second enter the admin leave list **/
	public ModelAndView secondLeaveSubmitAdmin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");

		TLeave beanLeave = new TLeave();
		beanLeave.setFIRSTAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setLeaveState("正常");
		beanLeave.setFIRSTAPPROVALSTATE("待审批");
		List listAdminPro = WebUserService.findByExample(beanLeave);

		beanLeave = new TLeave();
		beanLeave.setSECONDAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setFIRSTAPPROVALSTATE("已通过");
		beanLeave.setSECONDAPPROVALSTATE("待审批");
		List listAdmin2Pro = WebUserService.findByExample(beanLeave);

		beanLeave = new TLeave();
		beanLeave.setTHREEAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setSECONDAPPROVALSTATE("已通过");
		beanLeave.setTHREEAPPROVALSTATE("待审批");
		List listAdmin3Pro = WebUserService.findByExample(beanLeave);

		for (int i = 0; i < listAdmin2Pro.size(); i++) {
			listAdminPro.add(listAdmin2Pro.get(i));
		}
		for (int i = 0; i < listAdmin3Pro.size(); i++) {
			listAdminPro.add(listAdmin3Pro.get(i));
		}

		beanLeave = new TLeave();
		beanLeave.setFIRSTAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setFIRSTAPPROVALSTATE("已通过");
		List listAdminSucc = WebUserService.findByExample(beanLeave);

		beanLeave = new TLeave();
		beanLeave.setSECONDAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setSECONDAPPROVALSTATE("已通过");
		List listAdmin2Succ = WebUserService.findByExample(beanLeave);

		beanLeave = new TLeave();
		beanLeave.setTHREEAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setTHREEAPPROVALSTATE("已通过");
		List listAdmin3Succ = WebUserService.findByExample(beanLeave);

		/**
		 * Suld goverment's one leader have two rules . Avoid repeat record add
		 * the list.
		 **/
		for (int i = 0; i < listAdmin2Succ.size(); i++) {
			TLeave tempi = (TLeave) listAdmin2Succ.get(i);
			String opendIdI = tempi.getUSERSID();
			boolean flag = true;
			for (int j = 0; j < listAdminSucc.size(); j++) {
				TLeave tempj = (TLeave) listAdminSucc.get(j);
				String opendIdJ = tempj.getUSERSID();
				if (opendIdJ.equals(opendIdI)) {
					flag = false;
					break;
				}
			}
			if (flag) {
				listAdminSucc.add(listAdmin2Succ.get(i));
			}

		}
		/** end **/
		for (int i = 0; i < listAdmin3Succ.size(); i++) {
			listAdminSucc.add(listAdmin3Succ.get(i));
		}

		beanLeave = new TLeave();
		beanLeave.setFIRSTAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setFIRSTAPPROVALSTATE("未通过");
		List listAdminFail = WebUserService.findByExample(beanLeave);

		beanLeave = new TLeave();
		beanLeave.setSECONDAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setSECONDAPPROVALSTATE("未通过");
		List listAdmin2Fail = WebUserService.findByExample(beanLeave);

		beanLeave = new TLeave();
		beanLeave.setTHREEAPPROVALPERSON(webUser.getSid().toString() + "-"
				+ webUser.getUserName());
		beanLeave.setTHREEAPPROVALSTATE("未通过");
		List listAdmin3Fail = WebUserService.findByExample(beanLeave);

		for (int i = 0; i < listAdminFail.size(); i++) {
			listAdminSucc.add(listAdminFail.get(i));
		}
		for (int i = 0; i < listAdmin2Fail.size(); i++) {
			listAdminSucc.add(listAdmin2Fail.get(i));
		}
		for (int i = 0; i < listAdmin3Fail.size(); i++) {
			listAdminSucc.add(listAdmin3Fail.get(i));
		}


		request.getSession().setAttribute("listAdminPro", listAdminPro);
		request.getSession().setAttribute("listAdminSucc", listAdminSucc);
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarLeaveListAdmin");
		return mnv;

	}

	public ModelAndView secondQueryLeaveList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		TLeave beanLeave = new TLeave();
		beanLeave.setUSERSID(webUser.getOpenId());
		beanLeave.setLeaveState("正常");
		beanLeave.setFLAGSTATE("审批中");
		List proTLeaves = WebUserService.findByExample(beanLeave);
		beanLeave.setFLAGSTATE("已通过");
		List succTLeaves = WebUserService.findByExample(beanLeave);
		beanLeave.setFLAGSTATE("未通过");
		List failTLeaves = WebUserService.findByExample(beanLeave);
		for (int i = 0; i < failTLeaves.size(); i++) {
			succTLeaves.add(failTLeaves.get(i));
		}

		request.getSession().setAttribute("proTLeaves", proTLeaves);
		request.getSession().setAttribute("succTLeaves", succTLeaves);
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarLeaveList");
		return mnv;
	}

	/** del leave by user **/
	public ModelAndView linkDelLeave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sid = request.getParameter("sid");
		TLeave bean = new TLeave();
		bean.setSid(Integer.valueOf(sid));
		List list = WebUserService.findByExample(bean);
		bean = (TLeave) list.get(0);
		bean.setLeaveState("撤销");
		WebUserService.store(bean);
		return secondQueryLeaveList(request, response);

	}

	/** user register by NouserInformation **/
	public ModelAndView tatarRegister(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();
		String code = new String(
				(request.getParameter("code")).getBytes("iso-8859-1"), "utf-8");
		TUsers bean = new TUsers();
		if (code.equals("111111")) {
			bean.setRegionId("88888");
			bean.setRegionName("宇宙区域");
			bean.setUnitId("88888");
			bean.setUnitName("宇宙俱乐部");
			bean.setDepartmentId("88888");
			bean.setDepartmentName("地球俱乐部");
			bean.setChatName(webUser.getChatName());
			bean.setHeadUrl(webUser.getHeadUrl());
			bean.setUserName(webUser.getChatName());
			bean.setOpenId(webUser.getOpenId());
			bean.setAccount("100");
			bean.setState("正常");
			bean.setRightt("工作人员");
			WebUserService.store(bean);
			mnv.setViewName("../suld/tatarSelf");

		} else {
			TUnit beanUnit = new TUnit();
			beanUnit.setSid(Integer.valueOf(code));
			List listUnit = WebUserService.findByExample(beanUnit);
			if (!listUnit.isEmpty()) {
				beanUnit = (TUnit) listUnit.get(0);
				bean.setChatName(webUser.getChatName());
				bean.setUnitId(beanUnit.getSid().toString());
				bean.setUnitName(beanUnit.getUnitName());
				bean.setHeadUrl(webUser.getHeadUrl());
				bean.setOpenId(webUser.getOpenId());
				bean.setAccount("100");
				bean.setRightt("工作人员");
				bean.setState("正常");
				WebUserService.store(bean);
				mnv.setViewName("../suld/tatarSelf");
			} else {
				mnv.setViewName("../suld/tatarRegister");
				request.setAttribute("error", "失效的输入码");
				return mnv;
			}
		}
		List list = WebUserService.findByExample(bean);
		bean = (TUsers) list.get(0);
		request.getSession().setAttribute("webUser", bean);
		return mnv;

	}

	public ModelAndView tatarRegisterChange(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		ModelAndView mnv = new ModelAndView();

		String code = new String(
				(request.getParameter("code")).getBytes("iso-8859-1"), "utf-8");
		TUnit beanUnit = new TUnit();
		beanUnit.setSid(Integer.valueOf(code));
		List listUnit = WebUserService.findByExample(beanUnit);
		if (!listUnit.isEmpty()) {
			beanUnit = (TUnit) listUnit.get(0);
			webUser.setUnitId(code);
			webUser.setUnitName(beanUnit.getUnitName());
			webUser.setDepartmentId("");
			webUser.setDepartmentName("");
			WebUserService.store(webUser);
			request.getSession().setAttribute("webUser", webUser);
			mnv.setViewName("../suld/tatarSelf");
		} else {
			mnv.setViewName("../suld/tatarEditCode");
			request.setAttribute("error", "失效的Code!");
			return mnv;
		}

		return mnv;

	}
	
	
	public ModelAndView tatarGetPic(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		try{  
			
		    HttpClient httpClient = new DefaultHttpClient();
		    
            HttpPost httppost = new HttpPost("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+"accessToken");  
              
            Map<String,Map<String,Integer>> sceneMap = new HashMap<String,Map<String,Integer>>();  
            Map<String,Integer> sceneIdMap = new HashMap<String, Integer>();  
            sceneIdMap.put("scene_id", 123);  
            sceneMap.put("scene", sceneIdMap);  
              
            JSONObject attentionXml = new JSONObject();  
            attentionXml.put("expire_seconds", 1800);  
            attentionXml.put("action_name", "QR_SCENE");  
            attentionXml.put("action_info", sceneMap);  
            System.out.println("atten====="+attentionXml.toString());  
            try {  
  
                StringEntity se = new StringEntity(attentionXml.toString());  
                  
                httppost.setEntity(se);  
  
                System.out.println("executing request" + httppost.getRequestLine());  
  
                HttpResponse responseEntry = httpClient.execute(httppost);  
             
                try {  
                    HttpEntity entity = responseEntry.getEntity();  
  
                    System.out.println("----------------------------------------");  
                    System.out.println(responseEntry.getStatusLine());  
                    if (entity != null) {  
                        System.out.println("Response content length: "  
                                + entity.getContentLength());  
                        JSONObject result = null ;  
                        System.out.println("Response content length: "  
                                + entity.getContentLength());  
                    BufferedReader bufferedReader = new BufferedReader(  
                                new InputStreamReader(entity.getContent()));  
                        String text;  
                        while ((text = bufferedReader.readLine()) != null) {  
                            System.out.println("text======="+text);  
                            result = JSONObject.fromObject(text);  
                        }  
//此url位生成临时二维码url,扫码后可进行公众账号关注  
                        String resultUrl = result.getString("url");  
//                      JSONObject result = new JSONObject();  
                        if(!StringUtils.isEmpty(resultUrl)){  
                            result.put("attentionUrl", resultUrl);  
                            result.put("expireSeconds", result.getString("expire_seconds"));  
                        }else{  
                            result.put("status", "error");  
                            result.put("errcode", result.getString("errcode"));  
                            result.put("errmsg", result.getString("errmsg"));  
                        }  
                        System.out.println(result);
  
                    }  
                    EntityUtils.consume(entity);  
                }  
                finally {  
                    
                }  
            }  
            finally {  
              
            }  
            return null;          
        }catch(Exception e){  
            e.printStackTrace();  
            JSONObject result = new JSONObject();  
            result.put("status","error");  
            result.put("msg",e.getMessage());  
            return null;  
        }  
     
		
		
		
	
	}

}
