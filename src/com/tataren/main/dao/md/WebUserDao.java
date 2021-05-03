package com.tataren.main.dao.md;

import java.util.List;
import java.util.Map;

import com.tataren.main.dao.AbstractJdbcDao;
import com.tataren.main.domain.user.TMessage;
import com.tataren.main.domain.user.WebUser;
import com.tataren.main.web.PageBreaker;
import com.tataren.main.web.SortInformation;
import com.tataren.tatar.domain.TArea;
import com.tataren.tatar.domain.TInout;
import com.tataren.tatar.domain.TInoutCollect;
import com.tataren.tatar.domain.TLeave;
import com.tataren.tatar.domain.TMember;

public class WebUserDao extends AbstractJdbcDao {

	public List findAllGmManagerUser() {
		String sql = "select * from SGMUSER where role =1 ";
		return this.query(sql, WebUser.class);
	}

	public List getTInout(String nickname, String daytime) {
		String sql = "select * from TInout where usersid ='" + nickname
				+ "' and daytime='" + daytime + "'";
		return this.query(sql, TInout.class);
	}

	public List getTInoutCollect(String startTime, String endTime,
			String kindId, String readerId, String unitKind) {
		String sql = "";
		if (unitKind.equals("4")) {
			if (kindId.equals("2")) {
				sql = "select 1 sid, t.username , t.unitname, t.openId,to_char(sum(wuqiandao)) wuqian,to_char(sum(chidao)) chidao,to_char(sum(pianli)) pianli  From tinoutcollect t where t.openid='"
						+ readerId
						+ "' and t.daytime>='"
						+ startTime
						+ "' and t.daytime<='"
						+ endTime
						+ "' group by t.username,t.unitname,t.openId";
			} else {
				sql = "select 1 sid, t.username , t.unitname, t.openId,to_char(sum(wuqiandao)) wuqian,to_char(sum(chidao)) chidao,to_char(sum(pianli)) pianli  From tinoutcollect t where t.unitId='"
						+ readerId
						+ "' and t.daytime>='"
						+ startTime
						+ "' and t.daytime<='"
						+ endTime
						+ "' group by t.username,t.unitname,t.openId";
			}
		} else {
			if (kindId.equals("2")) {
				sql = "select 1 sid, t.username , t.unitname, t.openId,to_char(sum(wuqiandao)) wuqian,to_char(sum(chidao)) chidao,to_char(sum(pianli)) pianli  From tinoutcollecthalf t where t.openid='"
						+ readerId
						+ "' and t.daytime>='"
						+ startTime
						+ "' and t.daytime<='"
						+ endTime
						+ "' group by t.username,t.unitname,t.openId";
			} else {
				sql = "select 1 sid, t.username , t.unitname, t.openId,to_char(sum(wuqiandao)) wuqian,to_char(sum(chidao)) chidao,to_char(sum(pianli)) pianli  From tinoutcollecthalf t where t.unitId='"
						+ readerId
						+ "' and t.daytime>='"
						+ startTime
						+ "' and t.daytime<='"
						+ endTime
						+ "' group by t.username,t.unitname,t.openId";
			}
		}
		return this.query(sql, TInoutCollect.class);
	}

	public List getTInoutByKindId(String kindId, String daytime , String unitId) {
		String sql = "";
		if (kindId.equals("1")) {
			sql = "select * from TInout where unitId='"+unitId+"' and firstCheckState ='已签到' and daytime='"
					+ daytime + "'";
		} else if (kindId.equals("2")) {
			sql = "select * from TInout where unitId='"+unitId+"' and twoCheckState ='已签退' and daytime='"
					+ daytime + "'";
		} else if (kindId.equals("3")) {
			sql = "select * from TInout where unitId='"+unitId+"' and threeCheckState ='已签到' and daytime='"
					+ daytime + "'";
		} else {
			sql = "select * from TInout where unitId='"+unitId+"' and fourCheckState ='已签退' and daytime='"
					+ daytime + "'";
		}
		System.out.println("dddd" + sql);
		return this.query(sql, TInout.class);
	}
	public List getTLeave(String yearId,String userId,String unitId,String kindId) {
		String sql = "";
		String startTime=yearId+"-01-01";
		String endTime=yearId+"-12-31";
		if (kindId.equals("超级管理员")) {
			sql = "select * from TLeave where leavestate='正常' and startTime>='"+startTime+"' and endTime<='"+endTime+"' order by sid desc";					
		} else if (kindId.equals("区管理员")) {
			sql = "select * from TLeave where leavestate='正常' and startTime>='"+startTime+"' and endTime<='"+endTime+"' order by sid desc";
		} else if (kindId.equals("单位管理员")) {
			sql = "select * from TLeave where leavestate='正常' and tunitId='"+unitId+"' and startTime>='"+startTime+"' and endTime<='"+endTime+"' order by sid desc";
		} else {
			sql = "select * from TLeave where leavestate='正常' and usersid='"+userId+"' and startTime>='"+startTime+"' and endTime<='"+endTime+"' order by sid desc";
		}
		System.out.println("dddd" + sql);
		return this.query(sql, TLeave.class);
	}
	public List getTInoutByKindIdLeave(String kindId, String daytime , String unitId) {
		String sql = "";
		if (kindId.equals("1")) {
			sql = "select * from TInout where unitId='"+unitId+"' and firstCheckState ='已签到' and daytype='公派' and daytime='"
					+ daytime + "'";
		} else if (kindId.equals("2")) {
			sql = "select * from TInout where unitId='"+unitId+"' and twoCheckState ='已签退' and daytype='公派' and daytime='"
					+ daytime + "'";
		} else if (kindId.equals("3")) {
			sql = "select * from TInout where unitId='"+unitId+"' and threeCheckState ='已签到' and daytype='公派' and daytime='"
					+ daytime + "'";
		} else {
			sql = "select * from TInout where unitId='"+unitId+"' and fourCheckState ='已签退' and daytype='公派' and daytime='"
					+ daytime + "'";
		}
		System.out.println("dddd" + sql);
		return this.query(sql, TInout.class);
	}
	public List getTInoutByKindIdArea(String unitId) {
		
		String sql = "select * from TArea where unitId='"+unitId+"' and  to_char(sysdate,'yyyy-MM-dd HH24:mi:ss') <= endtime and  to_char(sysdate,'yyyy-MM-dd HH24:mi:ss') >=starttime";

		return this.query(sql, TArea.class);
	}
	public List getTMember(String opendId) {
		String sql = "select * from TMember where finalMajor ='" + opendId
				+ "' ";
		return this.query(sql, TMember.class);
	}

	public List findTMessage(String fromTime, String endTime) {
		String sql = "select * from TMESSAGE where createDate >=" + fromTime
				+ " and createDate <=" + endTime;
		return this.query(sql, TMessage.class);
	}

	public List findTSign(String startTime, String userId) {

		String sql = "Select * From tleave where leaveState='æ­£å¸¸' and usersId='"
				+ userId
				+ "' and startTime<='"
				+ startTime
				+ "' and endTime>='" + startTime + "'";
		return this.query(sql, TLeave.class);
	}

	public List findTMessage(String creator) {
		String sql = "select * from TMESSAGE where creator like '%" + creator
				+ "%' order by -sid";
		return this.query(sql, TMessage.class);
	}

	public List findMessage(String message) {
		String sql = "select * from TMESSAGE where message like '%" + message
				+ "%' order by -sid";
		return this.query(sql, TMessage.class);
	}

	public List findTMessage(String fromTime, String endTime, String creator) {
		String sql = "select * from TMESSAGE where (createDate >=" + fromTime
				+ " and createDate <=" + endTime + ") and creator like '%"
				+ creator + "%'";
		return this.query(sql, TMessage.class);
	}

	public List findWebusers(WebUser bean, PageBreaker pageBreaker,
			SortInformation sortInfo) {
		String sql = "select s.*,d.groupname from sgmuser s left join dunsgroup d on s.dunsgroupsid=d.sid where 1=1";
		if (bean.getSid() != null && !"".equals(bean.getSid())) {
			sql += " and s.LOGONID like '%" + bean.getSid() + "%'";
		}
		if (bean.getLoginName() != null && !"".equals(bean.getLoginName())) {
			sql += " and s.LOGONNAME like '%" + bean.getLoginName() + "%'";
		}
		if (bean.getDepartMent() != null && !"".equals(bean.getDepartMent())) {
			sql += " and s.DEPARTMENT like '%" + bean.getDepartMent() + "%'";
		}
		return this.query(sql, Map.class, pageBreaker, sortInfo);
	}

	public List findUserAccount(String account, String loginname,
			String username, PageBreaker pageBreaker, SortInformation sortInfo) {
		String sql = "select * from webuser w where w.rightt=2 ";
		if (account != null && !"".equals(account)) {
			sql += " and w.account < " + account + " ";
		}
		if (loginname != null && !"".equals(loginname)) {
			sql += " and w.loginname like '%" + loginname + "%'";
		}
		if (username != null && !"".equals(username)) {
			sql += " and w.username like '%" + username + "%'";
		}
		return this.query(sql, WebUser.class, pageBreaker, sortInfo);
	}

}
