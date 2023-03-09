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
		memberqna = memberProcDao.getMemberQnaList(cpage, psize, where, order);
		
		close(conn);
		
		return memberqna;
	}
	public int getQnaListCount(String where) {
		int rcnt=0;
		Connection conn = getConnection();
		MemberProcDao memberProcDao = MemberProcDao.getInstance();
		memberProcDao.setConnection(conn);
		
		rcnt = memberProcDao.getQnaListCount(where);
		close(conn);
		
		return rcnt;
	}

}