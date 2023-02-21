package svc;

import static db.JdbcUtil.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;


public class MemberListSvc {
	public  ArrayList<MemberInfo>  getMemberList( int cpage, int psize, String where,String order){
		ArrayList<MemberInfo> memberList  = new ArrayList<MemberInfo>();
		Connection conn = getConnection();
		MemberProcDao memberProcDao = MemberProcDao.getInstance();
		memberProcDao.setConnection(conn);
		
		memberList = memberProcDao.getMemberList(cpage, psize, where, order);
		
		close(conn);
		
		return memberList;
	}
	public int getListCount(String where) {
		int rcnt=0;
		Connection conn = getConnection();
		MemberProcDao memberProcDao = MemberProcDao.getInstance();
		memberProcDao.setConnection(conn);
		
		rcnt = memberProcDao.getListCount(where);
		close(conn);
		
		return rcnt;
	}
	public int statUpdate(String status, String oiid) {
		int result=0;

		Connection conn = getConnection();
		MemberProcDao memberProcDao = MemberProcDao.getInstance();
		memberProcDao.setConnection(conn);
		
		result = memberProcDao.statUp(status, oiid);
		if(result == 1) 	commit(conn);
		else				rollback(conn);

		close(conn);
		return result;
	}
}
