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
					+"' and pi_id = '"+ oc.getPi_id();


			System.out.println(sql);
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);		
			
			if(result==0) {

				sql = "insert into t_order_cart (mi_id, pi_id, oc_cnt) "
						+ "  values ('"+ oc.getMi_id()+"', '"
						+ oc.getPi_id()+"', '"
						+ oc.getOc_cnt()+"') ";
			
						stmt = conn.createStatement();
						result = stmt.executeUpdate(sql);	
			}	
		}catch(Exception e){
			System.out.println("CartProcDao 클래스 cartInsert 메소드 오류�");
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
			System.out.println("CartProcDao 클래스의 cartDelete메소드에서오류발생");
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
			System.out.println("CartProcDao 클래스의 cartUpdate메소드에서오류발생");
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
			
	
			String sql = " select  a.*, b.pi_name,  b.pi_img1, "
					+ " b.pi_price, b.pi_dc, c.ps_stock"
					+ " from t_order_cart a, t_product_info b, "
					+ " t_product_stock c where a.pi_id = b.pi_id "
					+ " and a.pi_id = c.pi_id and a.ps_idx = c.ps_idx "
					+ " and b.pi_isview = 'y' and a.mi_id = '"+
					miid +"' ";
			
			System.out.println(sql + ": /CartProcDao/getCartList");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);	
			
			while(rs.next()) {
				oc = new OrderCart();
				oc.setOc_idx(rs.getInt("oc_idx"));
				oc.setMi_id(miid);
				oc.setPi_id(rs.getString("pi_id"));
				oc.setPs_idx(rs.getInt("ps_idx"));
				oc.setOc_cnt(rs.getInt("oc_cnt"));
				oc.setPi_name(rs.getString("pi_name"));
				oc.setPi_img1(rs.getString("pi_img1"));
				oc.setPi_price(rs.getInt("pi_price"));
				oc.setPi_dc(rs.getInt("pi_dc"));
				oc.setPs_stock(rs.getInt("ps_stock"));
				oc.setStockList(ppd.getStockList(oc.getPi_id()));
				///mvcSite/src/dao/ProductProcDao.java 메소드 참고
				cartList.add(oc);
			}

		}catch(Exception e){
			System.out.println("CartProcDao 클래스의 getCartList메소드에서오류발생");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt);
		}
		return cartList;	
	}
	
}
