package svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class EvTorListSvc { 
	public int getTorListCount(String where) {
		//페이징을 위한 메소드호출인데 페이징 버리기로 해서 안씀 
		int rcnt=0;
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
		
		rcnt = evTorProcDao.getTorListCount(where);
		close(conn);
		return rcnt;
	}

	public  ArrayList<EvCusTor>  getTorList( String kind, String date,  String orderBy){
		ArrayList<EvCusTor> torList  = new ArrayList<EvCusTor>();
		
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
		
		torList = evTorProcDao.getTorList(kind, date, orderBy);
		
		close(conn);
		
		return torList;
	}
}
