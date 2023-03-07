package dao;

import static db.JdbcUtil.*;  
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.*;
import vo.*;
import svc.*;

public class ProductCustomDao {
	private static ProductCustomDao productCustomDao;
	private Connection conn;
	private ProductCustomDao() {} 
	
	public static ProductCustomDao  getInstance() {
		if (productCustomDao == null) productCustomDao = new ProductCustomDao(); 
		return productCustomDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public  ArrayList<ProductCustom> getProductCusList(int cpage, int psize, String where, String order){

		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<ProductCustom> productList  = new ArrayList<ProductCustom>();
		ProductCustom pc = null;

		try {

			String sql = "SELECT a.*,b.pi_name FROM t_product_ma_custom a, t_product_info b where a.pi_id = b.pi_id "+
			where + order + " limit "+(cpage-1)*psize+","+psize;
			
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); 

			while(rs.next()) {
				pc = new ProductCustom();
				
			 	pc.setPmc_idx(rs.getInt("pmc_idx"));
			 	pc.setMi_id(rs.getString("mi_id"));
			 	pc.setPmc_name(rs.getString("pmc_name"));
			 	pc.setPi_id(rs.getString("pi_name"));
			 	pc.setPmc_img(rs.getString("pmc_img"));
			 	pc.setPmc_tp1(rs.getString("pmc_tp1"));
			 	pc.setPmc_tp2(rs.getString("pmc_tp2"));
			 	pc.setPmc_isview(rs.getString("pmc_isview"));
			 	pc.setPmc_isbuy(rs.getString("pmc_isbuy"));

		
			 	productList.add(pc);
			}
		}catch(Exception e) {
			System.out.println("ProductCustomDao :getProductCusList()");
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
			String sql = "select count(*) from t_product_ma_custom " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); 
			
			if(rs.next()) rcnt = rs.getInt(1);

		
		}catch(Exception e) {
			System.out.println("ProductCustomDao getListCount()");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}

	
		return rcnt;
	}
}
