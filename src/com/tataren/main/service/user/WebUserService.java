package com.tataren.main.service.user;

import java.util.List;

import com.tataren.main.domain.user.TMessage;
import com.tataren.main.domain.user.WebUser;
import com.tataren.main.service.BasicServiceInterface;
import com.tataren.main.web.PageBreaker;
import com.tataren.main.web.SortInformation;
import com.tataren.tatar.domain.TUsers;


public interface WebUserService extends BasicServiceInterface {


	
	public TUsers findWebUsersByLogonID(String loginName);
	public WebUser findWebUsersByLogonID2(String loginName);
	public List findWebUsers(WebUser eg, PageBreaker breaker,
			SortInformation sortInfo);
	//public String getAccessToken();
	//public String getJsTicket();
	public String getSignature(String code);
	public String getSignatureArea(String code);
	public List findWebUsers(Integer[] sids);
	public List queryAllData(String a);
	public WebUser getWebUser(Integer sid);

	public void storeWebUser(WebUser bean);
	public void setSessionUser();
	public List findByAccount(String account,String loginname,String username,PageBreaker pageBreaker,
			SortInformation sortInfo);
	public void deleteWebUser(WebUser bean);

	public void deleteWebUsers(Integer[] sids);
	
	public TMessage findIMessagesBySid(Integer sid);
	public List findByTime(String startTime,String endTime);
	public List findTSign(String startTime,String userId);
	public List findByTime(String fromTime,String endTime,String creator);
	public List findByCreator(String creator);
	public List findByMessage(String message);
	public List getTInout(String nickname, String daytime) ;
	public List getTInoutCollect(String startTime, String endTime,String kindId,String readerId,String unitKind) ;
	public List getTInoutByKindId(String kindId, String daytime,String unitId);
	public List getTInoutByKindIdLeave(String kindId, String daytime,String unitId);
	public List getTInoutByKindIdArea(String unitId);
	public List getTMember(String opendId) ;
	public List getTUsers(String opendId) ;
	public List getTLeave(String yearId,String userId,String unitId,String kindId) ;
	


	
	
	
}

