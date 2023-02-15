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

	public static ProductCustomDao getInstance() {
		if (productCustomDao == null)	productCustomDao = new ProductCustomDao();
		return productCustomDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public ArrayList<ProductCustom> getCustomList(String miid){
		//회원이 만든 커스텀 마카롱 리스트를 가져오는 메소드
		Statement stmt = null; 
		ResultSet rs = null;
		ArrayList<ProductCustom> customList = new ArrayList<ProductCustom>();
		ProductCustom pc = null;
		
		try {
			String sql = " select  a.*, b.pi_name , b.pi_img1, b.pi_isview "
					+ " from t_product_ma_custom a, t_product_info b "
					+ " where a.pmc_isview = 'y' and b.pi_isview = 'y' and "
					+ " a.pi_id = b.pi_id and"
					+ " mi_id = '"+miid +"' order by pmc_date ";
			
			System.out.println(sql + ": /ProductCustomDao/getCustomList");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);	
			
			while(rs.next()) {
				pc = new ProductCustom();
				
				pc.setPmc_idx(rs.getInt("pmc_idx")); //커스텀 마카롱 인덱스
				pc.setPi_name(rs.getString("pi_name")); //맛이름
				pc.setPi_img1(rs.getString("pi_img1")); //맛이미지
				pc.setPi_id(rs.getString("pi_id")); //맛상품아이디
				pc.setMi_id(miid); //회원 아이디 
				pc.setPmc_name(rs.getString("pmc_name"));
				pc.setPmc_sugar(rs.getInt("pmc_sugar"));
				pc.setPmc_vg(rs.getString("pmc_vg"));
				pc.setPmc_pl(rs.getString("pmc_pl"));
				pc.setPmc_tp1(rs.getString("pmc_tp1"));
				pc.setPmc_tp2(rs.getString("pmc_tp2"));
				pc.setPmc_date(rs.getString("pmc_date"));
				pc.setPmc_price(rs.getInt("pmc_price"));
				pc.setPmc_isview(rs.getString("pmc_isview"));
				pc.setPmc_isbuy(rs.getString("pmc_isbuy"));		
				customList.add(pc);
			}

		}catch(Exception e){
			System.out.println("ProductCustomDao의  getCustomList메소드에서오류발생");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt);
		}
		return customList;	
	}
	
	
	
	
	
	public ProductCustom getCustomInfo(int pcidx) {
		//회원이 클릭한 커스텀마카롱의 상세뷰(커스텀마카롱의 모든 정보, 맛, 토핑맛)을 가져오는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		ProductCustom pc = null;
		try {
			String sql = "select a.*, b.pi_name, b.pi_img1 "
					+ " from t_product_ma_custom a, t_product_info b "
					+ " where a.pi_id = b.pi_id and a.pmc_isview = 'y' and "
					+ " pmc_isview = 'y' and pmc_idx=" + pcidx;
		
			stmt = conn.createStatement();
			System.out.println(sql + "ProductCustomDao:getCustomInfo");
			rs = stmt.executeQuery(sql); 
			//한 개 니까 루프 안돌려도 됨 
			if(rs.next()) {
				pc = new ProductCustom();
				
				pc.setPmc_idx(pcidx); //커스텀 마카롱 인덱스
				pc.setPi_name(rs.getString("pi_name")); //맛이름
				pc.setPi_img1(rs.getString("pi_img1")); //맛이미지
				pc.setPi_id(rs.getString("pi_id")); //맛상품아이디
				pc.setPmc_name(rs.getString("pmc_name")); //사용자설정제목
				pc.setPmc_sugar(rs.getInt("pmc_sugar"));
				pc.setPmc_vg(rs.getString("pmc_vg"));
				pc.setPmc_pl(rs.getString("pmc_pl"));
				pc.setPmc_tp1(rs.getString("pmc_tp1"));
				pc.setPmc_tp2(rs.getString("pmc_tp2"));
				pc.setPmc_date(rs.getString("pmc_date"));
				pc.setPmc_price(rs.getInt("pmc_price"));
				pc.setPmc_isview(rs.getString("pmc_isview"));
				pc.setPmc_isbuy(rs.getString("pmc_isbuy"));		
				
			}
		
		}catch(Exception e) {
			System.out.println("ProductCustomDao:getCustomInfo()오류");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}		
		return pc;
	}
	
	public String getToppingName(String tpid) {
		
		Statement stmt = null;
		ResultSet rs = null;
		String tpname = "";
		try {
			String sql = "select pmt_name from t_product_ma_topping where "
					+ "pmt_id = '"+ tpid+ "'";
		
			stmt = conn.createStatement();
			System.out.println(sql + "ProductCustomDao:getToppingName");
			rs = stmt.executeQuery(sql); 
			//한 개 니까 루프 안돌려도 됨 
			if(rs.next()) {
				tpname = rs.getString(1);		
			}
		
		}catch(Exception e) {
			System.out.println("ProductCustomDao:getToppingName()오류");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}		
		return tpname;
		
		
	}

	
	
	
	

}



















