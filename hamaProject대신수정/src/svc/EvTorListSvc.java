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
		int rcnt=0;
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
		
		rcnt = evTorProcDao.getTorListCount(where);
		close(conn);
		
		return rcnt;
	}
	
	
	public  ArrayList<EvCusTor>  getTorList( String kind, int cpage, int psize, String orderBy){
		ArrayList<EvCusTor> torList  = new ArrayList<EvCusTor>();
		
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
		
		torList = evTorProcDao.getTorList( kind, cpage, psize, orderBy);
		
		close(conn);
		
		return torList;
	}
}
