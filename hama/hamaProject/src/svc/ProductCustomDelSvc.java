package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;


public class ProductCustomDelSvc {
	public int customDelete(String miid, String pmcidx) {
		//마카롱 커스텀 테이블에서 해당 레시피를 삭제하기
		int result = 0;
		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);

		result = productCustomDao.customDelete(miid, pmcidx);

		if(result == 1) 	commit(conn);
		else				rollback(conn);	

		close(conn);
		return result;
	}
}
