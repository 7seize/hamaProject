package svc;
import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrderViewSvc {
	public OrderInfo getOrderInfo(String oiid) {
		OrderInfo orderInfo = null;

		Connection conn = getConnection();
		OrderProcDao orderProcDao =  OrderProcDao.getInstance();
		orderProcDao.setConnection(conn);
		
		orderInfo = orderProcDao.getOrderInfo(oiid);
		close(conn);
		
		
		return orderInfo;
	}
}
