package svc;

import static db.JdbcUtil.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrderListSvc {
	public  ArrayList<OrderInfo>  getOrderList( int cpage, int psize, String where,String order){
		ArrayList<OrderInfo> orderList  = new ArrayList<OrderInfo>();
		Connection conn = getConnection();
		OrderProcDao orderProcDao = OrderProcDao.getInstance();
		orderProcDao.setConnection(conn);
		
		orderList = orderProcDao.getOrderList(cpage, psize, where, order);
		
		close(conn);
		
		return orderList;
	}
	public int getListCount(String where) {
		int rcnt=0;
		Connection conn = getConnection();
		OrderProcDao orderProcDao = OrderProcDao.getInstance();
		orderProcDao.setConnection(conn);
		
		rcnt = orderProcDao.getListCount(where);
		close(conn);
		
		return rcnt;
	}
	public int statUpdate(String status, String oiid) {
		int result=0;

		Connection conn = getConnection();
		OrderProcDao orderProcDao = OrderProcDao.getInstance();
		orderProcDao.setConnection(conn);
		
		result = orderProcDao.statUp(status, oiid);
		if(result == 1) 	commit(conn);
		else				rollback(conn);

		close(conn);
		return result;
	}
}