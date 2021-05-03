package com.tataren.main.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tataren.main.service.SessionInfo;
import com.tataren.main.service.user.WebUserService;
import com.tataren.tatar.domain.TInout;
import com.tataren.tatar.domain.TTask;
import com.tataren.tatar.domain.TTaskChild;
import com.tataren.tatar.domain.TUsers;

public class HurdController extends MultiActionController {

	private WebUserService WebUserService;

	public WebUserService getWebUserService() {
		return WebUserService;
	}

	public void setWebUserService(WebUserService WebUserService) {
		this.WebUserService = WebUserService;
	}


	public ModelAndView firstMobileTask(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String apiurl = "https://open.weixin.qq.com/connect/oauth2/authorize";
		
		String redirect_uri = java.net.URLEncoder.encode(
				"http://"+SessionInfo.appUrl+"/hurd_/hurd.do?method=indexInterTask",
				"UTF-8");
		String appid = SessionInfo.appid;
		String secret = SessionInfo.secret;
		
		String turl = String
				.format("%s?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect",
						apiurl, appid, redirect_uri);
		response.sendRedirect(turl);
		ModelAndView mnv = new ModelAndView();

		return null;
	}

	public ModelAndView indexInterTask(HttpServletRequest request,
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
			TTask taskBean = new TTask();
			
			taskBean.setReaderIds(webUser.getSid().toString());
			System.out.println(webUser.getSid().toString());
			List taskList = WebUserService.findByExample(taskBean);
			request.setAttribute("taskList", taskList);
			request.getSession().setAttribute("webUser", webUser);
			TTaskChild tcbean = new TTaskChild();
			tcbean.setUserid(webUser.getSid().toString());
			tcbean.setState("进行中");
			List tcList = WebUserService.findByExample(tcbean);
			request.setAttribute("tcList", tcList);
			
			tcbean.setState("已提交");
			List tfList = WebUserService.findByExample(tcbean);
			request.setAttribute("tfList", tfList);
			ModelAndView mnv = new ModelAndView();
			mnv.setViewName("../suld/tatarTaskList");
			return mnv;
		} else {
			/**
			 * webUser.setChatName(nickname); webUser.setState("正常");
			 * webUser.setHeadUrl(headimgurl); WebUserService.store(webUser);
			 **/
			webUser.setChatName(nickname);
			request.getSession().setAttribute("webUser", webUser);
			ModelAndView mnv = new ModelAndView();
			mnv.setViewName("../suld/tatarRegister");
			return mnv;
		}
		

	}
	//
	public ModelAndView firstEnterSelf(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String apiurl = "https://open.weixin.qq.com/connect/oauth2/authorize";
		String redirect_uri = java.net.URLEncoder.encode(
				"http://"+SessionInfo.appUrl+"/hurd_/hurd.do?method=linkEnterSelf",
				"UTF-8");
		String appid = SessionInfo.appid;
		String secret = SessionInfo.secret;
		String turl = String
				.format("%s?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect",
						apiurl, appid, redirect_uri);
		response.sendRedirect(turl);
		ModelAndView mnv = new ModelAndView();

		return null;
	}

	public ModelAndView linkEnterSelf(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarSelf");
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
			} else {
				access_token = json.get("access_token").getAsString();
				openid = json.get("openid").getAsString();
				access_token = json.get("access_token").getAsString();
				scope = json.get("scope").getAsString();

			}
		}

		TUsers webUser = new TUsers();
		webUser.setOpenId(openid);
		List webList = WebUserService.findByExample(webUser);
		
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
			} else {
				nickname = bjson.get("nickname").getAsString();
				headimgurl = bjson.get("headimgurl").getAsString();
				openMid = bjson.get("openid").getAsString();
			}
		}
		
		
		if (!webList.isEmpty()) {
			webUser = (TUsers) webList.get(0);
			webUser.setHeadUrl(headimgurl);
			webUser.setLoginName(webUser.getSid().toString());
			webUser.setPwd(String.valueOf(webUser.getSid()+8));
			WebUserService.store(webUser);
		} else {

			

			webUser.setChatName(nickname);
			webUser.setState("新入");
			webUser.setHeadUrl(headimgurl);
			mnv.setViewName("../suld/tatarRegister");
			
		}
		request.getSession().setAttribute("webUser", webUser);
		return mnv;
	}
	
	
	
	public ModelAndView taskFirstDo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		String sid = request.getParameter("sid");
		String kindId = request.getParameter("kindId");
		TTask bean = new TTask();
		if(kindId.equals("1")){
			bean.setSid(Integer.valueOf(sid));
			List list = WebUserService.findByExample(bean);
			bean = (TTask)list.get(0);
			String ids = bean.getReaderIds().replaceAll("-"+webUser.getSid().toString(), "");
			System.out.println("33333"+ids);
			bean.setReaderIds(ids);
			WebUserService.store(bean);
		}else if(kindId.equals("2")){
			bean.setSid(Integer.valueOf(sid));
			List list = WebUserService.findByExample(bean);
			bean = (TTask)list.get(0);
			String ids = bean.getReaderIds().replaceAll("-"+webUser.getSid(), "");
			bean.setReaderIds(ids);
			WebUserService.store(bean);
			TTaskChild beanTask = new TTaskChild();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Calendar c1 = Calendar.getInstance();
			c1.setTime(new Date());
			String nowtimes = format.format(c1.getTime());
			beanTask.setAcceptDate(nowtimes);
			beanTask.setContent(bean.getContent());
			beanTask.setDeadline(bean.getDeadLine());
			beanTask.setScore(bean.getScore());
			beanTask.setTaskname(bean.getTaskName());
			beanTask.setTaskId(bean.getSid().toString());
			
			beanTask.setFlagState("正常");
			beanTask.setState("进行中");
			beanTask.setUserid(webUser.getSid().toString());
			beanTask.setUserName(webUser.getUnitName());
			beanTask.setLinkMan(bean.getLinkMan());
			beanTask.setTel(bean.getTel());
			beanTask.setFileId(bean.getFileId());
			beanTask.setFileName(bean.getFileName());
			WebUserService.store(beanTask);			
		}else if(kindId.equals("3")){
			TTaskChild beanTask = new TTaskChild();
			beanTask.setSid(Integer.valueOf(sid));
			List list = WebUserService.findByExample(beanTask);
			beanTask = (TTaskChild)list.get(0);				
			beanTask.setState("已提交");		
			WebUserService.store(beanTask);			
		}else if(kindId.equals("4")){
			TTaskChild beanTask = new TTaskChild();
			beanTask.setSid(Integer.valueOf(sid));
			List list = WebUserService.findByExample(beanTask);
			beanTask = (TTaskChild)list.get(0);				
			beanTask.setState("已提交");		
			WebUserService.store(beanTask);			
		}
		
		return taskQuery(request,response);
	}
	public ModelAndView taskQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		
		TTask taskBean = new TTask();
		
		taskBean.setReaderIds(webUser.getSid().toString());
		System.out.println(webUser.getSid().toString());
		List list = WebUserService.findByExample(taskBean);
		request.setAttribute("taskList", list);

		TTaskChild tcbean = new TTaskChild();
		tcbean.setUserid(webUser.getSid().toString());
		tcbean.setState("进行中");
		tcbean.setFlagState("正常");
		List tcList = WebUserService.findByExample(tcbean);
		request.setAttribute("tcList", tcList);
		
		tcbean.setState("已提交");
		List tfList = WebUserService.findByExample(tcbean);
		request.setAttribute("tfList", tfList);
		
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarTaskList");
		return mnv;
	}
	public ModelAndView contactQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		
		TUsers bean = new TUsers();
		
		bean.setUnitId(webUser.getUnitId());
		bean.setState("正常");
		List list = WebUserService.findByExample(bean);
		request.setAttribute("list", list);

		
		
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarContactList");
		return mnv;
	}
	public ModelAndView locationQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TUsers webUser = (TUsers) request.getSession().getAttribute("webUser");
		
		TInout bean = new TInout();
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
		String nowtimes = dateFormat.format(now);
		bean.setUNITID(webUser.getUnitId());
		bean.setDAYTIME(nowtimes);
		bean.setFIRSTCHECKSTATE("已签到");
		List list = WebUserService.findByExample(bean);
		
		String mapId="";
		String labelId="";
		if(list!=null){
		
			for(int m=0 ;m < list.size();m++){	
				TInout inBean = (TInout)list.get(m);
				mapId=mapId+"["+inBean.getFIRSTCHECKLONGID()+","+inBean.getFIRSTCHECKLATIID()+"],";
				labelId=labelId+"['"+inBean.getUSERNAME()+","+inBean.getFIRSTCHECKTIME().substring(11, 19)+"'],";
				
			}
			if(!mapId.equals("")){
				mapId=mapId.substring(0, mapId.length()-1);
				labelId=labelId.substring(0, labelId.length()-1);
			}
		}
		
		request.setAttribute("mapId", mapId);
		request.setAttribute("labelId", labelId);

		
		
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("../suld/tatarInOutAllQuery");
		return mnv;
	}
}