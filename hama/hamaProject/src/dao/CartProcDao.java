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

			if(oc.getPi_id().equals("cb101") || oc.getPi_id().equals("cb102") ) {
				sql =  " update t_order_cart set "
						+ " oc_cnt = oc_cnt + " + oc.getOc_cnt()
						+ " where mi_id = '"+ oc.getMi_id()
						+"' and pi_id = '"+ oc.getPi_id() 
						+"' and oc_box = '"+ oc.getOc_box()  
						+"' and oc_pmc_idx = '"+ oc.getOc_pmc_idx() +"'";
			}else if(oc.getPi_id().equals("mc100")){
				sql =  " update t_order_cart set "
						+ " oc_cnt = oc_cnt + " + oc.getOc_cnt()
						+ " where mi_id = '"+ oc.getMi_id()
						+"' and pi_id = '"+ oc.getPi_id() 
						+"' and oc_pmc_idx = '"+ oc.getOc_pmc_idx() +"'";
			}
			System.out.println(sql);
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);		
			
			if(result==0) {

				sql = "insert into t_order_cart (mi_id, pi_id, oc_cnt) "
						+ "  values ('"+ oc.getMi_id()+"', '"
						+ oc.getPi_id()+"', '"
						+ oc.getOc_cnt()+"') ";
				
				if(oc.getPi_id().equals("cb101") || oc.getPi_id().equals("cb102") || oc.getPi_id().equals("mc100")) {
					sql = "insert into t_order_cart (mi_id, pi_id, oc_cnt, oc_box ,oc_pmc_idx) "
							+ "  values ('"+ oc.getMi_id()+"', '"
							+ oc.getPi_id()+"', '"
							+ oc.getOc_cnt()+"', '" 
							+ oc.getOc_box()+"', '" 
							+ oc.getOc_pmc_idx()+"') ";
				}else if(oc.getPi_id().equals("mc100")){
					sql =  "insert into t_order_cart (mi_id, pi_id, oc_cnt, oc_pmc_idx) "
							+ "  values ('"+ oc.getMi_id()+"', '"
							+ oc.getPi_id()+"', '"
							+ oc.getOc_cnt()+"', '"
							+ oc.getOc_pmc_idx()+"') ";
				}
				
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
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);	
			
			while(rs.next()) {
				oc = new OrderCart();
				oc.setOc_idx(rs.getInt("oc_idx"));
				oc.setMi_id(miid);
				oc.setPi_id(rs.getString("pi_id"));
				oc.setOc_box(rs.getString("oc_box"));
				oc.setOc_pmc_idx(rs.getString("oc_pmc_idx"));
				oc.setOc_cnt(rs.getInt("oc_cnt"));
				oc.setPi_name(rs.getString("pi_name"));
				oc.setPi_img1(rs.getString("pi_img1"));
				oc.setPi_price(rs.getInt("pi_price"));
				
				if(rs.getString("oc_box")!= null && !rs.getString("oc_box").equals("")) {
					//.equals  
					String [] openbox = rs.getString("oc_box").split(",");
					String box2 = "";
					for(int i= 0; i < openbox.length ; i ++) {
						Statement stmt2 = null; 
						ResultSet rs2 = null;
						try{
							sql = "select pi_name from t_product_info where pi_id = '"+openbox[i] +"'" ;
							stmt2 = conn.createStatement();
							rs2 = stmt2.executeQuery(sql);	
							System.out.println(sql);
							if(rs2.next()) {
								box2 += rs2.getString("pi_name");
								box2 += ",";
								System.out.println(box2);
							}
							
						}catch(Exception e){
							System.out.println("CartProcDao 클래스 getCartList2 오류");
							e.printStackTrace();
						}finally {
							close(rs2); close(stmt2);
						}
						
						
					}
					
					oc.setOc_box(box2);
				}
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
