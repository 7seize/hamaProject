package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import vo.*;

public class ProductSetViewDao {
	private static ProductSetViewDao productSetViewDao;
	private Connection conn;
	private ProductSetViewDao() {}

	public static ProductSetViewDao getInstance() {
		if (productSetViewDao == null)	productSetViewDao = new ProductSetViewDao();
		return productSetViewDao;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	public ArrayList<ProductInfo> getProductInfo() {
		Statement stmt = null;
		ResultSet rs = null;
		ProductInfo pi =null;
		ArrayList<ProductInfo> piList = new ArrayList<ProductInfo>();
		
		try {
			String sql = "SELECT pi_id, pi_name, pi_desc, pi_alg FROM t_product_info " + 
					"where pc_id ='mc' and pi_id <>'mc100' and pi_isview = 'y'";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				pi = new ProductInfo();
				pi.setPi_id(rs.getString("pi_id"));
				pi.setPi_name(rs.getString("pi_name"));
				pi.setPi_desc(rs.getString("Pi_desc"));
				pi.setPi_alg(rs.getString("pi_alg"));
				piList.add(pi);
			}

		} catch(Exception e) {
			System.out.println("ProductSetViewDao클래스 getProductInfo 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return piList;
	}
	public ProductCustom getProductCustom(String miid) {
		Statement stmt = null;
		ResultSet rs = null;
		ProductCustom pc = null;
		
		try {
			String sql = "select pmc_idx from  t_product_ma_custom " + 
					"where pmc_isview = 'y' and mi_id = '"+miid+"'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				pc = new ProductCustom();
				pc.setPmc_idx(rs.getInt("pmc_idx"));
				pc.setMi_id(miid);
			}

		} catch(Exception e) {
			System.out.println("ProductSetViewDao클래스 getProductCustom 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return pc;
	}
}
