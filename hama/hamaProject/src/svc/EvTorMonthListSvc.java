package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class EvTorMonthListSvc {

	// 커스텀 리스트 
	public  ArrayList<ProductCustom>  getCustomList( int cpage, int psize){
		 ArrayList<ProductCustom> customList  = new ArrayList<ProductCustom>();
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
		
		customList = evTorProcDao.getCustomList( cpage, psize);
		close(conn);
		
		return customList;
	}

}
