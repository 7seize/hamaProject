package dao;


import static db.JdbcUtil.*;  
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.*;
import vo.*;
import svc.*;


public class ProductProcDao {
	private static ProductProcDao productProcDao;
	private Connection conn;
	private ProductProcDao() {} 
	
	public static ProductProcDao  getInstance() {
		if (productProcDao == null) productProcDao = new ProductProcDao(); 
		return productProcDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public  ArrayList<ProductInfo> getProductList(int cpage, int psize, String where, String order){

		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<ProductInfo> productList  = new ArrayList<ProductInfo>();
		ProductInfo pi = null;

		try {

			String sql = "select * from t_product_info "+
			where + order +
			" limit "+(cpage-1)*psize+","+psize;
			
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); 

			while(rs.next()) {
				pi = new ProductInfo();
				
			 	pi.setPi_id(rs.getString("pi_id"));
			 	pi.setPc_id(rs.getString("pc_id"));
			 	pi.setPi_name(rs.getString("pi_name"));
		
			 	pi.setPi_isview(rs.getString("pi_isview"));
			 	pi.setPi_price(rs.getInt("pi_price"));
			 	pi.setPi_dc(rs.getInt("pi_dc"));
			 	pi.setPi_sale(rs.getInt("pi_sale"));
			 	
			 	productList.add(pi);
			}
		}catch(Exception e) {
			System.out.println("ProductProcDao :getProductList() ����");
			e.printStackTrace();
		}finally {
			
			close(rs); close(stmt); 
			
		}
		
		return productList;
	}
	public int getListCount(String where) {
		int rcnt=0;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select count(*) from t_product_info " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); 
			
			if(rs.next()) rcnt = rs.getInt(1);
			//�÷��� ��� �ε��� ��ȣ �׳� �� 
			//select count�ϴ� �ſ��� ��� �˻� ���ص� ��.
		
		}catch(Exception e) {
			System.out.println("ProductProcDao getListCount()");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}

	
		return rcnt;
	}
	public int statUp(String status, String piid) {
		int result = 0;
		Statement stmt = null; 
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			String sql = "update t_product_info set pi_isview = "+
					"'"+status+"' where pi_id = '"+piid+"'"; 

			System.out.println(sql);
			result = stmt.executeUpdate(sql);		
			
		}catch(Exception e){
			System.out.println("ProductProcDao Class statUp Fail");
			e.printStackTrace();
		}finally {
			close(stmt);
		}
		return result;
	}
	
	public String setPiid(String cat) {
		String setpiid = "";
		Statement stmt = null; 
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			String sql = "select right(pi_id,3)+1 newpiid from t_product_info where pc_id = '"+cat+"' order by pi_id desc limit 1";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); 		
			if(rs.next()) setpiid = cat + rs.getInt("newpiid");
			
		}catch(Exception e){
			System.out.println("OrderProcDao Class setPiid Fail");
			e.printStackTrace();
		}finally {
			close(stmt);
		}
		return setpiid;
	}
	
	
}
