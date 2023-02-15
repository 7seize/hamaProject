package svc;
import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrderEndSvc {
	public OrderInfo getOrderInfo(String miid, String oiid) {
		OrderInfo orderInfo = null;
		//객체일 때null로채움 > 없는거 구별하기 힘들다 객체는...
		//생성해버리는건 ArrayList >없는지있는지 구별가능하기에
		
		Connection conn = getConnection();
		OrderProcDao orderProcDao =  OrderProcDao.getInstance();
		orderProcDao.setConnection(conn);
		
		orderInfo = orderProcDao.getOrderInfo(miid, oiid);
		close(conn);
		
		
		return orderInfo;
	}
}
