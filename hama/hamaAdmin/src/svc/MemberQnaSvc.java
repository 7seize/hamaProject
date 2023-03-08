package svc;

import static db.JdbcUtil.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;


public class MemberQnaSvc {
	public  ArrayList<MemberQna>  getMemberQnaList( int cpage, int psize, String where,String order){
		ArrayList<MemberQna> memberqna  = new ArrayList<MemberQna>();
		Connection conn = getConnection();
		MemberProcDao memberProcDao = MemberProcDao.getInstance();
		memberProcDao.setConnection(conn);
		
		// ########################################################################################여기부터 작업
		memberqna = memberProcDao.getMemberList(cpage, psize, where, order);
		
		close(conn);
		
		return memberqna;
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

}