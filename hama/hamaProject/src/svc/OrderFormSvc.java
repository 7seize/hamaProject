package svc;
import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrderFormSvc {
	public ArrayList<OrderCart> getBuyList(String kind, String sql) {
		ArrayList<OrderCart> pdtList = new ArrayList<OrderCart>();

		Connection conn = getConnection();
		OrderProcDao orderProcDao =  OrderProcDao.getInstance();
		orderProcDao.setConnection(conn);
		
		pdtList = orderProcDao.getBuyList(kind,sql);
		close(conn);
		return pdtList;
	}
	public ArrayList<MemberAddr>  getAddrList(String miid) {
		ArrayList<MemberAddr> addrList = new ArrayList<MemberAddr>();

		Connection conn = getConnection();
		OrderProcDao orderProcDao =  OrderProcDao.getInstance();
		orderProcDao.setConnection(conn);
		
		addrList = orderProcDao.getAddrList(miid);
		close(conn);
		return addrList;
	}


}