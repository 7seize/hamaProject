package svc;
import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class CartProcUpSvc {
	public int cartUpdate(OrderCart oc) {
		int result=0;
		Connection conn = getConnection();
		CartProcDao cartProcDao = CartProcDao.getInstance();
		cartProcDao.setConnection(conn);
	
		result = cartProcDao.cartUpdate(oc);
		if(result == 1) 	commit(conn);
		else				rollback(conn);	
		
		close(conn);
		return result;
	}
}
