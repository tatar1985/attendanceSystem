package com.tataren.main.service.user;
     
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tataren.main.service.SessionInfo;
import com.tataren.main.util.WeChatValue;
import com.tataren.tatar.send.SendService;
import com.tataren.tatar.send.Template;
import com.tataren.tatar.send.TemplateParam;

public class StaticMethodService  implements ServletContextListener {
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent event) {
		Timer timer = new Timer();
		Calendar startTime = Calendar.getInstance();
		startTime.set(startTime.get(Calendar.YEAR),
				startTime.get(Calendar.MONTH), startTime.get(Calendar.DATE),
				8, 20, 0);
		Calendar afterTime = Calendar.getInstance();
		afterTime.set(afterTime.get(Calendar.YEAR),
				afterTime.get(Calendar.MONTH), afterTime.get(Calendar.DATE),
				17, 35, 0);
		long timeInterval = 24 * 60 * 60 * 1000;

		TimerTask task = new TimerTask() {
			public void run() {

				String apiurl = "https://api.weixin.qq.com/cgi-bin/token";

				String appid = SessionInfo.appid;
				String secret = SessionInfo.secret;

				String turl = String.format(
						"%s?grant_type=client_credential&appid=%s&secret=%s",
						apiurl, appid, secret);
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(turl);
				JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
				String result = null;

				try {
					HttpResponse res = client.execute(get);
					String responseContent = null; // 响应内容
					HttpEntity entity = res.getEntity();
					responseContent = EntityUtils.toString(entity, "UTF-8");
					JsonObject json = jsonparer.parse(responseContent)
							.getAsJsonObject();
					// 将json字符串转换为json对象
					System.out.println(turl);
					if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						if (json.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
							result = json.get("errcode").getAsString();
							System.out.println(result);
						} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
							result = json.get("access_token").getAsString();
			
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				WeChatValue.setAccessToken(result);

				// 重新提取 ticket 放入内存中
				apiurl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

				// String accessToken = this.getAccessToken();
				String accessToken = result;
				turl = String.format("%s?access_token=%s&type=jsapi", apiurl,
						accessToken);
				client = new DefaultHttpClient();
				get = new HttpGet(turl);
				jsonparer = new JsonParser();// 初始化解析json格式的对象
				result = null;
				try {
					HttpResponse res = client.execute(get);
					String responseContent = null; // 响应内容
					HttpEntity entity = res.getEntity();
					responseContent = EntityUtils.toString(entity, "UTF-8");
					JsonObject json = jsonparer.parse(responseContent)
							.getAsJsonObject();
					// 将json字符串转换为json对象
					System.out.println(turl);
					if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						if (json.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
							result = json.get("ticket").getAsString();
						} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
							System.out.println("errocdoe="
									+ json.get("errcode").getAsString());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				WeChatValue.setJsTicket(result);

			}
			// GET SIGNATURE

		};
		TimerTask taskSendMor = new TimerTask() {
			public void run() {
				boolean flag = true;
				Calendar startDate = Calendar.getInstance();
				Date date = startDate.getTime();
				/* 日期格式化 yyyy-MM-dd M必须大写 */
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = formatter.format(date);
				int hour = startDate.get(Calendar.HOUR_OF_DAY);
				int min = startDate.get(Calendar.MINUTE);
				if (startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
					flag = false;
				}
				if (startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
					flag = false;
				}
				if (hour > 8) {
					flag = false;
				}
				if (flag) {
					Connection con = null;// 创建一个数据库连接
					Statement st = null;// 创建预编译语句对象，一般都是用这个而不用Statement
					ResultSet rs = null;// 创建一个结果集对象
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
						System.out.println("开始尝试连接数据库！");
						String url = "jdbc:oracle:"
								+ "thin:@47.95.148.190:1521:orcl";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
						String user = "seal";// 用户名,系统默认的账户名
						String password = "password";// 你安装时选设置的密码
						con = DriverManager.getConnection(url, user, password);// 获取连接
						System.out.println("连接成功！");
						Date now = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");// 可以方便地修改日期格式
						String nowtimes = dateFormat.format(now);
						st = con.createStatement();
						rs = st.executeQuery("select username,usersid From tinout t where daytime='"+dateString+"' and  daytype='上班' and unitid='12333' and firstcheckstate='未签到'");
						String username;
						String openId;
						Template tem = new Template();

						// oQMBIwaHaXWaREwzoBXScKEfaBoM
						// ogx_NvxGZAEnkDCeqTgKZd1t7NxE

						tem.setTemplateId("JYTMx1XzUftf9E8sb-dOyQo_HnX1qhD191iMxC-gS4o");
						tem.setUrl("http://www.tataren.com/suld_/suld.do?method=firstMobile");

						// String token =
						// "6_NkDvL4D_TvarmhefwccvpKu09PR13YkA8Wbou0zTFT0A4ee8NbB4cqtb8gx8dMmQHfww2QskLTNZ7lMqD3ZqiGZdvzFptbWJ75nt3lioTSDOEh1XgBs7hmRwp9WGDRdB_krIDF8BRNolTO6EVETeAHAMVK";
						String token = WeChatValue.getAccessToken();

						while (rs.next()) {
							username = rs.getString("username");
							openId = rs.getString("usersid");
							System.out.println(username + openId);
							tem.setTouser(rs.getString("usersid"));
							List<TemplateParam> data = new ArrayList<TemplateParam>();
							data.add(new TemplateParam("first", "签到提醒",
									"#FF3333"));
							data.add(new TemplateParam("keyword1", username,
									"#0044BB"));
							data.add(new TemplateParam("keyword2", "上班时间别忘签到",
									"#0044BB"));
							data.add(new TemplateParam("keyword3",
									dateString, "#0044BB"));
							data.add(new TemplateParam("Remark",
									"努力工作是为了生活，努力生活是为了.... ", "#AAAAAA"));
							tem.setTemplateParamList(data);
							SendService ss = new SendService();
							boolean result = ss.sendTemplateMsg(token, tem);
						}

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
							// 注意关闭的顺序，最后使用的最先关闭
							if (rs != null)
								rs.close();
							if (st != null)
								st.close();
							if (con != null)
								con.close();
							System.out.println("数据库连接已关闭！");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

		};
		TimerTask taskSendAft = new TimerTask() {
			public void run() {
				boolean flag = true;
				Calendar startDate = Calendar.getInstance();
				Date date = startDate.getTime();
				/* 日期格式化 yyyy-MM-dd M必须大写 */
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = formatter.format(date);
				int hour = startDate.get(Calendar.HOUR_OF_DAY);
				int min = startDate.get(Calendar.MINUTE);
				if (startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
					flag = false;
				}
				if (startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
					flag = false;
				}
				if (hour > 18) {
					flag = false;
				}
				if (flag) {
					Connection con = null;// 创建一个数据库连接
					Statement st = null;// 创建预编译语句对象，一般都是用这个而不用Statement
					ResultSet rs = null;// 创建一个结果集对象
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
						System.out.println("开始尝试连接数据库！");
						String url = "jdbc:oracle:"
								+ "thin:@47.95.148.190:1521:orcl";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
						String user = "seal";// 用户名,系统默认的账户名
						String password = "password";// 你安装时选设置的密码
						con = DriverManager.getConnection(url, user, password);// 获取连接
						System.out.println("连接成功！");
						Date now = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");// 可以方便地修改日期格式
						String nowtimes = dateFormat.format(now);
						st = con.createStatement();
						rs = st.executeQuery("select username,usersid From tinout t where daytime='"+dateString+"' and  daytype='上班' and unitid='12333' and fourcheckstate='未签到'");
						//rs = st.executeQuery("select username,usersid From tinout t where daytime='2018-04-13' and usersid='ogx_NvxGZAEnkDCeqTgKZd1t7NxE' and  daytype='上班' and unitid='12333' and fourcheckstate='未签到'");
						String username;
						String openId;
						Template tem = new Template();

						tem.setTemplateId("JYTMx1XzUftf9E8sb-dOyQo_HnX1qhD191iMxC-gS4o");
						tem.setUrl("http://www.tataren.com/suld_/suld.do?method=firstMobile");

						String token = WeChatValue.getAccessToken();

						while (rs.next()) {
							username = rs.getString("username");
							openId = rs.getString("usersid");
							System.out.println(username + openId);
							tem.setTouser(rs.getString("usersid"));
							List<TemplateParam> data = new ArrayList<TemplateParam>();
							data.add(new TemplateParam("first", "签退提醒",
									"#FF3333"));
							data.add(new TemplateParam("keyword1", username,
									"#0044BB"));
							data.add(new TemplateParam("keyword2", "上班时间别忘签退",
									"#0044BB"));
							data.add(new TemplateParam("keyword3",
									dateString, "#0044BB"));

							tem.setTemplateParamList(data);
							SendService ss = new SendService();
							boolean result = ss.sendTemplateMsg(token, tem);
						}

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
							// 注意关闭的顺序，最后使用的最先关闭
							if (rs != null)
								rs.close();
							if (st != null)
								st.close();
							if (con != null)
								con.close();
							System.out.println("数据库连接已关闭！");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

		};
		timer.schedule(task, 10000L, 7200000L);
		timer.schedule(taskSendMor, startTime.getTime(), timeInterval);
		timer.schedule(taskSendAft, afterTime.getTime(), timeInterval);

	} // 实现其中的销毁函数

}