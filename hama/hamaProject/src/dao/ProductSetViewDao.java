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
			System.out.println("ProductSetViewDao�겢�옒�뒪 getProductInfo �삤瑜�");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return piList;
	}
	public ArrayList<ProductCustom> getProductCustom(String miid) {
		Statement stmt = null;
		ResultSet rs = null;
		ProductCustom pc = null;
		ArrayList<ProductCustom> pcList = new ArrayList<ProductCustom>();
		
		try {
			String sql = "select * from  t_product_ma_custom " + 
					"where pmc_isview = 'y' and mi_id = '"+miid+"'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				pc = new ProductCustom();
				pc.setPmc_idx(rs.getInt("pmc_idx"));
				pc.setPmc_name(rs.getString("pmc_name"));
				pc.setPmc_sugar(rs.getInt("pmc_sugar"));
				pc.setPmc_vg(rs.getString("pmc_vg"));
				pc.setPmc_pl(rs.getString("pmc_pl"));
				pc.setPi_id(rs.getString("pi_id"));
				pc.setPmc_img(rs.getString("pmc_img"));
				pc.setMi_id(miid);
				
				pc.setPmc_tp1(rs.getString("pmc_tp1"));
				pc.setPmc_tp2(rs.getString("pmc_tp2"));
				
				if(rs.getString("pmc_tp1")!=null && !rs.getString("pmc_tp1").equals("")) {
		               String tpname = getToppingName(rs.getString("pmc_tp1"));
		               pc.setTpname1(tpname); 
		        }
		        if(rs.getString("pmc_tp2")!=null && !rs.getString("pmc_tp2").equals("")) {
		           String tpname = getToppingName(rs.getString("pmc_tp2"));
		           pc.setTpname2(tpname);
		       }
				
				
				
				pcList.add(pc);
			}

		} catch(Exception e) {
			System.out.println("ProductSetViewDao�겢�옒�뒪 getProductCustom �삤瑜�");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return pcList;
	}
	
	
	
	   public String getToppingName(String tpid) {      
		      //�넗�븨 �븘�씠�뵒瑜� 媛��졇���꽌 �넗�븨 �씠由꾩쓣 由ы꽩�븯�뒗 硫붿냼�뱶
		      Statement stmt = null;
		      ResultSet rs = null;
		      String tpname = "";
		      try {
		         String sql = "select pmt_name from t_product_ma_topping where "
		               + "pmt_id = '"+ tpid+ "'";
		      
		         stmt = conn.createStatement();
		         System.out.println(sql + "EvTorProcDao:getToppingName");
		         rs = stmt.executeQuery(sql); 
		         //�븳 媛� �땲源� 猷⑦봽 �븞�룎�젮�룄 �맖 
		         if(rs.next()) {
		            tpname = rs.getString(1);      
		         }
		      
		      }catch(Exception e) {
		         System.out.println("EvTorProcDao:getToppingName()�삤瑜�");
		         e.printStackTrace();
		      }finally {
		         close(rs); close(stmt); 
		      }      
		      return tpname;

		   }
	
}
