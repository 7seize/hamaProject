package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;


public class EvTorViewSvc {
	public EvCusTor getEvCusTorInfo(int ectidx) {
		EvCusTor ect = null;
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
		
		ect = evTorProcDao.getEvCusTorInfo(ectidx);
	
		close(conn);
		return ect;
	}
	
	// 투표하기 메소드
	public int voteBtn(	int ectidx, String miid) {
		int result = 0;			
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
			
		result = evTorProcDao.voteBtn(ectidx, miid);
		if (result==2) 	commit(conn);
		else			rollback(conn);
		close(conn);
			
		return result;
	}
	
	
	public EvCusTorPoll didvote(String miid) {	//###################투표했는지 확인하는 메소드
		EvCusTorPoll ectp = null;
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
		ectp = evTorProcDao.isvote(miid);
	
		close(conn);
		return ectp;
	}

	public ProductCustom getCustomInfo(int pcidx) {
		ProductCustom pc = null;
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
		pc = evTorProcDao.getCustomInfo(pcidx);
		
		close(conn);
		return pc;
	}
}
