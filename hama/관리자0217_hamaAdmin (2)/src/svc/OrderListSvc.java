package svc;

import static db.JdbcUtil.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrderListSvc {
	public  ArrayList<OrderInfo>  getOrderList( int cpage, int psize){
		ArrayList<OrderInfo> orderList  = new ArrayList<OrderInfo>();
		Connection conn = getConnection();
		OrderProcDao orderProcDao = OrderProcDao.getInstance();
		orderProcDao.setConnection(conn);
		
		orderList = orderProcDao.getOrderList(cpage, psize);
		
		close(conn);
		
		return orderList;
	}
}
