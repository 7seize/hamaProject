package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class MemberProcUpSvc {
	
	public int memberProcUp (MemberInfo memberInfo, String update) {
		int result = 0;
		Connection conn = getConnection();
		MemberProcUpDao memberProcUpDao = MemberProcUpDao.getInstance();
		memberProcUpDao.setConnection(conn);
		
		result = memberProcUpDao.memberProcUp(memberInfo, update);
		if(result == 1)		commit(conn);		
		else				rollback(conn);		
		close(conn);
		
		
		return result;
	}
	

}
