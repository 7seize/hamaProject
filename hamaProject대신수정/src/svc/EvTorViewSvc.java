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
	
	public int voteBtn(	EvCusTorPoll ectp) {
		int result = 0;			
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
			
		result = evTorProcDao.voteBtn(ectp);
		if (result==2) 	commit(conn);
		else			rollback(conn);
		close(conn);
			
		return result;
	}

}
