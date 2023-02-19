package dao;


import static db.JdbcUtil.*;  
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.*;
import vo.*;
import svc.*;

public class OrderProcDao {
	private static OrderProcDao orderProcDao;
	private Connection conn;
	private OrderProcDao() {} 
	
	public static OrderProcDao  getInstance() {
		if (orderProcDao == null) orderProcDao = new OrderProcDao(); 
		return orderProcDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public  ArrayList<OrderInfo> getOrderList(int cpage, int psize){

		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<OrderInfo> orderList  = new ArrayList<OrderInfo>();
		OrderInfo oi = null;

		try {

			String sql = "select * from t_order_info a , t_order_detail b "+
			"where a.oi_id = b.oi_id" + 
			" order by oi_status , oi_date "+
			"limit "+(cpage-1)*psize+1+","+cpage*psize;
			
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); 

			while(rs.next()) {
				ArrayList<OrderDetail> od  = new ArrayList<OrderDetail>();
				oi = new OrderInfo();
			 	oi.setOi_id(rs.getString("oi_id"));
			 	oi.setMi_id(rs.getString("mi_id"));
			 
			 	oi.setOi_sender(rs.getString("oi_sender"));
			 	oi.setOi_status(rs.getString("oi_status"));
			 	oi.setOi_date(rs.getString("oi_date"));
			 
			 	oi.setDetailList(od.set);
			 orderList.add(oi);
			}
		
		}catch(Exception e) {
			System.out.println("OrderProcDao :getOrderList() 오류");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}
		
		
		return orderList;
}
