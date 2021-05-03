package com.tataren.main.service.user;

import java.security.MessageDigest;
import java.util.List;

import org.springframework.dao.IncorrectResultSizeDataAccessException;

import com.tataren.main.dao.md.WebUserDao;
import com.tataren.main.domain.user.TMessage;
import com.tataren.main.domain.user.WebUser;
import com.tataren.main.service.BasicService;
import com.tataren.main.service.SessionInfo;
import com.tataren.main.util.WeChatValue;
import com.tataren.main.web.PageBreaker;
import com.tataren.main.web.SortInformation;
import com.tataren.tatar.domain.TUsers;



public class WebUserServiceImpl extends BasicService implements WebUserService {

	private WebUserDao webUserDao;

	public String getSignature(String code) {

		String jsapi_ticket=WeChatValue.getJsTicket();
		String timestamp="2017171717";
		String noncestr="tataren";
		String url="http://"+SessionInfo.appUrl+"/suld_/suld.do?method=indexInter&code="+code+"&state=STATE";
		String signatur="111";
		String str = "jsapi_ticket=" + jsapi_ticket +
	                "&noncestr=" + noncestr +
	                "&timestamp=" + timestamp +
	                "&url=" + url;
		
		 try {
	            MessageDigest digest = java.security.MessageDigest
	                    .getInstance("SHA-1"); //å¦ææ¯SHAå å¯åªéè¦å°"SHA-1"æ¹æ"SHA"å³å¯
	            digest.update(str.getBytes());
	            byte messageDigest[] = digest.digest();
	            // Create Hex String
	            StringBuffer hexStr = new StringBuffer();
	            // å­èæ°ç»è½¬æ¢ä¸º åå­è¿å¶ æ°
	            for (int i = 0; i < messageDigest.length; i++) {
	                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
	                if (shaHex.length() < 2) {
	                    hexStr.append(0);
	                }
	                hexStr.append(shaHex);
	            }
	            signatur= hexStr.toString();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	
		return signatur;
	}
	public String getSignatureArea(String code) {

		String jsapi_ticket=WeChatValue.getJsTicket();
		String timestamp="2017171717";
		String noncestr="tataren";
		String url="http://"+SessionInfo.appUrl+"/suld_/suld.do?method=indexInterAreaCheck&code="+code+"&state=STATE";
		String signatur="111";
		String str = "jsapi_ticket=" + jsapi_ticket +
	                "&noncestr=" + noncestr +
	                "&timestamp=" + timestamp +
	                "&url=" + url;
		
		 try {
	            MessageDigest digest = java.security.MessageDigest
	                    .getInstance("SHA-1"); //å¦ææ¯SHAå å¯åªéè¦å°"SHA-1"æ¹æ"SHA"å³å¯
	            digest.update(str.getBytes());
	            byte messageDigest[] = digest.digest();
	            // Create Hex String
	            StringBuffer hexStr = new StringBuffer();
	            // å­èæ°ç»è½¬æ¢ä¸º åå­è¿å¶ æ°
	            for (int i = 0; i < messageDigest.length; i++) {
	                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
	                if (shaHex.length() < 2) {
	                    hexStr.append(0);
	                }
	                hexStr.append(shaHex);
	            }
	            signatur= hexStr.toString();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

		return signatur;
	}
	public void deleteWebUsers(Integer[] sids) {
		for (int i = 0; i < sids.length; i++) {
			webUserDao.deleteBean(WebUser.class, sids[i]);
		}
	}

	public void deleteWebUser(WebUser bean) {
		webUserDao.deleteBean(bean.getClass(), bean.getSid());
	}

	public List findWebUsers(Integer[] sids) {
		return webUserDao.findByIds(WebUser.class, sids);
	}

	public List findWebUsers(WebUser eg, PageBreaker breaker,
			SortInformation sortInfo) {
		return webUserDao.findByExample(eg, breaker, sortInfo);
	}

	public WebUser getWebUser(Integer uid) {
		// TODO Auto-generated method stub
		return (WebUser) webUserDao.getBean(WebUser.class, uid);
	}

	public TMessage findIMessagesBySid(Integer sid) {
		return (TMessage) webUserDao.getBean(TMessage.class, sid.intValue());
	}

	public void storeWebUser(WebUser bean) {
		webUserDao.saveOrUpdate(bean);
	}
	public WebUser findWebUsersByLogonID2(String loginName) {
		String sql = "select * from WebUser where state='0' and loginName =?";
		List list = this.query(sql, new Object[] { loginName }, WebUser.class);
		if (list.size() == 0)
			return null;
		else if (list.size() == 1 || list.size() == 2 || list.size() == 3
				|| list.size() == 4)
			return (WebUser) list.get(0);
		else {
			throw new IncorrectResultSizeDataAccessException(
					" Incorrect result size: expected 1, actual ", 1,
					list.size());
		}
	}

	public TUsers findWebUsersByLogonID(String loginName) {
		String sql = "select * from TUsers where state='正常' and loginName =?";
		List list = this.query(sql, new Object[] { loginName }, TUsers.class);
		if (list.size() == 0)
			return null;
		else if (list.size() == 1 || list.size() == 2 || list.size() == 3
				|| list.size() == 4)
			return (TUsers) list.get(0);
		else {
			throw new IncorrectResultSizeDataAccessException(
					" Incorrect result size: expected 1, actual ", 1,
					list.size());
		}
	}

	public List queryAllData(String a) {
		String sql = "select * from CardMind where sfzh =?";
		List list = this.query(sql, new Object[] { a }, List.class);
		return list;
	}

	public List findSgmuserASMap(WebUser bean, PageBreaker pageBreaker,
			SortInformation sortInfo) {

		return webUserDao.findWebusers(bean, pageBreaker, sortInfo);
	}

	public List findByTime(String fromTime, String endTime) {
		return webUserDao.findTMessage(fromTime, endTime);
	}
	public List findTSign(String startTime,String userId) {
		return webUserDao.findTSign(startTime, userId);
	}
	public List getTInout(String nickname, String daytime) {
		return webUserDao.getTInout(nickname, daytime);
	}
	public List getTInoutCollect(String startTime, String endTime,String kindId,String readerId,String unitKind) {
		return webUserDao.getTInoutCollect(startTime, endTime,kindId,readerId,unitKind);
	}
	public List getTInoutByKindId(String kindId, String daytime,String unitId) {
		return webUserDao.getTInoutByKindId(kindId, daytime,unitId);
	}
	public List getTInoutByKindIdLeave(String kindId, String daytime,String unitId) {
		return webUserDao.getTInoutByKindIdLeave(kindId, daytime,unitId);
	}
	public List getTInoutByKindIdArea(String unitId) {
		return webUserDao.getTInoutByKindIdArea(unitId);
	}

	public List getTMember(String opendId) {
		return webUserDao.getTMember(opendId);
	}
	public List getTUsers(String opendId) {
		return webUserDao.getTMember(opendId);
	}
	public List getTLeave(String yearId,String userId,String unitId,String kindId) {
		return webUserDao.getTLeave(yearId,userId,unitId, kindId);
	}
	public List findByTime(String fromTime, String endTime, String creator) {
		return webUserDao.findTMessage(fromTime, endTime, creator);
	}

	public List findByCreator(String creator) {
		return webUserDao.findTMessage(creator);
	}

	public List findByMessage(String message) {
		return webUserDao.findMessage(message);
	}

	public WebUserDao getWebUserDao() {
		return webUserDao;
	}

	public void setSessionUser(){
		
	};
	public void setWebUserDao(WebUserDao sgmUserDao) {
		this.webUserDao = sgmUserDao;
	}

	public List findByAccount(String account, String loginname,
			String username, PageBreaker pageBreaker, SortInformation sortInfo) {

		return webUserDao.findUserAccount(account, loginname, username,
				pageBreaker, sortInfo);
	}
}
