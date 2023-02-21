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

	public  ArrayList<OrderInfo> getOrderList(int cpage, int psize, String where, String order){

		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<OrderInfo> orderList  = new ArrayList<OrderInfo>();
		OrderInfo oi = null;

		try {

			String sql = "select * from t_order_info "+
			where + order +
			" limit "+(cpage-1)*psize+","+psize;
			
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); 

			while(rs.next()) {
				ArrayList<OrderDetail> detail  = new ArrayList<OrderDetail>();
				oi = new OrderInfo();
				
			 	oi.setOi_id(rs.getString("oi_id"));
			 	oi.setMi_id(rs.getString("mi_id"));
			 	oi.setOi_name(rs.getString("mi_id"));
			 	oi.setOi_sender(rs.getString("oi_sender"));
			 	oi.setOi_status(rs.getString("oi_status"));
			 	oi.setOi_pay(rs.getInt("oi_pay"));
			 	oi.setOi_date(rs.getString("oi_date"));
			 	
			 	
			 	try {
			 		sql = "select * from t_order_detail "+
						 	"where oi_id = '"+ rs.getString("oi_id") +"'";
				 	Statement stmt2 = conn.createStatement();
				 	ResultSet rs2 = stmt2.executeQuery(sql); 
				 	while(rs2.next()) {
				 		OrderDetail od  = new OrderDetail();
				 		od.setOd_idx(rs2.getInt("od_idx"));
				 		od.setOd_cnt(rs2.getInt("od_cnt"));
				 		od.setOd_name(rs2.getString("od_name"));
				 		od.setOd_price(rs2.getInt("od_price"));
				 		od.setOi_id(rs2.getString("oi_id"));
				 		od.setOd_idx(rs2.getInt("od_idx"));	
				 		detail.add(od);
					}
				} catch (Exception e) {
					System.out.println("OrderProcDao :getOrderList() ����");
					e.printStackTrace();
				}finally {
					//close(rs2); close(stmt2); 
				}
			 	oi.setDetailList(detail);
			 	orderList.add(oi);
			}
		
		}catch(Exception e) {
			System.out.println("OrderProcDao :getOrderList() ����");
			e.printStackTrace();
		}finally {
			
			close(rs); close(stmt); 
			
		}
		
		return orderList;
	}
	public int getListCount(String where) {
		int rcnt=0;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select count(*) from t_order_info " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); 
			
			if(rs.next()) rcnt = rs.getInt(1);
			//�÷��� ��� �ε��� ��ȣ �׳� �� 
			//select count�ϴ� �ſ��� ��� �˻� ���ص� ��.
		
		}catch(Exception e) {
			System.out.println("OrderProcDaoŬ���� getListCount()����");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}

	
		return rcnt;
	}
	public int statUp(String status, String oiid) {
		int result = 0;
		Statement stmt = null; 
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			String sql = "update t_order_info set oi_status = "+
			"'"+status+"' where oi_id = '"+oiid+"'"; 

			System.out.println(sql);
			result = stmt.executeUpdate(sql);		
			
		}catch(Exception e){
			System.out.println("OrderProcDao Class statUp Fall");
			e.printStackTrace();
		}finally {
			close(stmt);
		}
		return result;
	}
}