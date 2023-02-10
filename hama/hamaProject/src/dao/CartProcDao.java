package dao;

import static db.JdbcUtil.*;  
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.*;
import vo.*;
import svc.*;

public class CartProcDao {
	private static CartProcDao cartProcDao;
	private Connection conn;
	private CartProcDao() {}
	
	public static CartProcDao  getInstance() {
		if (cartProcDao == null) cartProcDao = new CartProcDao(); 
		return cartProcDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int cartInsert(OrderCart oc) {
		Statement stmt = null; 
		int result = 0;
		try {
			String sql = " update t_order_cart set "
					+ " oc_cnt = oc_cnt + " + oc.getOc_cnt()
					+ " where mi_id = '"+ oc.getMi_id()
					+"' and pi_id = '"+ oc.getPi_id() + "'";


			System.out.println(sql);
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);		
			
			if(result==0) {

				sql = "insert into t_order_cart (mi_id, pi_id, oc_cnt) "
						+ "  values ('"+ oc.getMi_id()+"', '"
						+ oc.getPi_id()+"', '"
						+ oc.getOc_cnt()+"') ";
				
						System.out.println(sql);
						
						stmt = conn.createStatement();
						result = stmt.executeUpdate(sql);	
			}	
		}catch(Exception e){
			System.out.println("CartProcDao Class cartInsert Fall");
			e.printStackTrace();
		}finally {
			close(stmt);
		}
		return result;		
	}
	
	public int cartDelete(String where) {
		int result = 0;
		Statement stmt = null; 
		try {
			String sql = " delete from t_order_cart "+ where;
			System.out.println(sql);
			
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);		
			
		}catch(Exception e){
			System.out.println("CartProcDao Class cartDelete Fall");
			e.printStackTrace();
		}finally {
			close(stmt);
		}
		return result;
	}
	
	public int cartUpdate(OrderCart oc) {
		int result = 0;
		Statement stmt = null; 
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			String sql = "update t_order_cart set ";
			String where = " where mi_id= '" + oc.getMi_id() + "' "; 
			
			sql += " oc_cnt = " + oc.getOc_cnt() + where +
					" and oc_idx = " + oc.getOc_idx();
			System.out.println(sql);
			result = stmt.executeUpdate(sql);		
			
		}catch(Exception e){
			System.out.println("CartProcDao Class cartUpdate Fall");
			e.printStackTrace();
		}finally {
			close(stmt);
		}
		return result;
	}
	
	
	
	
	
	public ArrayList<OrderCart> getCartList(String miid){
		//장바구니에서 보여줄 정보들을 ArrayList로 리턴하는 메소드 
		Statement stmt = null; 
		ResultSet rs = null;
		ArrayList<OrderCart> cartList = new ArrayList<OrderCart>();
		OrderCart oc = null;
		try {
			ProductDao ppd = ProductDao.getInstance();
	
			String sql = " select  a.*, b.pi_name, b.pi_img1, "
					+ " b.pi_price "
					+ " from t_order_cart a, t_product_info b "
					+ " where a.pi_id = b.pi_id "
					+ " and b.pi_isview = 'y' and a.mi_id = '"+
					miid +"' ";
			
			System.out.println(sql + ": 장바구니 + 상품정보 join sql");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);	
			
			while(rs.next()) {
				oc = new OrderCart();
				oc.setOc_idx(rs.getInt("oc_idx"));
				oc.setMi_id(miid);
				oc.setPi_id(rs.getString("pi_id"));
				oc.setOc_cnt(rs.getInt("oc_cnt"));
				oc.setPi_name(rs.getString("pi_name"));
				oc.setPi_img1(rs.getString("pi_img1"));
				oc.setPi_price(rs.getInt("pi_price"));

				cartList.add(oc);
			}

		}catch(Exception e){
			System.out.println("CartProcDao 클래스 getCartList 오류");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt);
		}
		return cartList;	
	}
	
}
