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
	public  ArrayList<EvCusTor>  getTorList( String kind, int cpage, int psize){
		ArrayList<EvCusTor> torList  = new ArrayList<EvCusTor>();
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
		
		torList = evTorProcDao.getTorList( kind, cpage, psize);
		
		close(conn);
		
		return torList;
	}
}
