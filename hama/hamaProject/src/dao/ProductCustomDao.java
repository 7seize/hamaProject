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
					+ " where  a.pi_id = b.pi_id and"
					+ " a.mi_id = '"+ miid +"' and " 
					+ " a.pmc_isview = 'y' and b.pi_isview = 'y' order by pmc_date ";
			
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
				pc.setPmc_img(rs.getString("pmc_img"));	
			}
		
		}catch(Exception e) {
			System.out.println("ProductCustomDao:getCustomInfo()오류");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}		
		return pc;
	}
	
	public int countCustom(String miid) {
		//해당 회원이 커스텀 마카롱 몇 개나 만들었는지 수를 가져오는 메소드 
		int cnt = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select count(*) "
					+ " from t_product_ma_custom "
					+ " where mi_id = '"+miid+"' "
					+ "and pmc_isview = 'y'";
			stmt = conn.createStatement();
			System.out.println(sql + "ProductCustomDao:countCustom");
			rs = stmt.executeQuery(sql); 
			//한 개 니까 루프 안돌려도 됨 
			if(rs.next()) {
				cnt = rs.getInt(1);		
			}
		
		}catch(Exception e) {
			System.out.println("ProductCustomDao:countCustom()오류");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}		
		return cnt;
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
	
	public ArrayList<ProductInfo> getMacList(){
		//마카롱(맛) 리스트 가져오는 메소드... 
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<ProductInfo> productList = new ArrayList<ProductInfo>();
		ProductInfo pi = null;

		try {
			String sql = "select pi_id, pi_img1, pi_name, pc_id, " + 
				" pi_price from t_product_info  where pc_id = 'mc' and "
				+ " pi_isview = 'y' ";
				
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				pi = new ProductInfo();
				pi.setPi_id(rs.getString("pi_id"));
				pi.setPi_img1(rs.getString("pi_img1"));
				pi.setPi_name(rs.getString("pi_name"));
				pi.setPi_price(rs.getInt("pi_price"));
				pi.setPc_id(rs.getString("pc_id"));

				productList.add(pi);
			}

		} catch(Exception e) {
			System.out.println("ProductCustomDao 클래스의 getProductList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return productList;
	
	}
	
	
	public ArrayList<ProductTopping> getToppingList() {
		// 토핑리스트 
			Statement stmt = null;
			ResultSet rs = null;
			ArrayList<ProductTopping> toppingList = new ArrayList<ProductTopping>();
			ProductTopping pt = null;

			try {
				String sql = "select * from t_product_ma_topping where pmt_isview = 'y'";
					
				System.out.println(sql);
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					pt = new ProductTopping();
					pt.setPmt_id(rs.getString("pmt_id"));
					pt.setPmt_idx(rs.getInt("pmt_idx"));
					pt.setPmt_name(rs.getString("pmt_name"));
					
					toppingList.add(pt);
				}

			} catch(Exception e) {
				System.out.println("ProductDao 클래스의 getProductList() 메소드 오류");
				e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}
			return toppingList;
		}
	
	
	
	
	public int customDelete(String miid, String pmcidx) {
		//해당 회원이 선택한 커스텀의 인덱스와 아이디를 갖고 가서 삭제(isview를 n으로 업데이트 하는 메소드)
		int result = 0;
		Statement stmt = null;
		try {
			String sql = "update t_product_ma_custom set pmc_isview = 'n' "
					+ " where mi_id = '"+ miid +"' "
					+ " and pmc_idx = '" +  pmcidx +"'";
			System.out.println(sql + "ProductCustomDao:customDelete");	
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			System.out.println(result+"ProductCustomDao:customDelete() result");	
		
		}catch(Exception e) {
			System.out.println("ProductCustomDao:customDelete()오류");
			e.printStackTrace();
		}finally {
			close(stmt); 
		}		
		return result;
	}

	public int customInsert(String miid, ProductCustom pc){
		//커스터 레시피를 등록하는 메소드 
		int result = 0;
		Statement stmt = null;
		try {
			String sql = "insert into t_product_ma_custom(mi_id,   pmc_name,   "
					+ " pmc_sugar,   pmc_vg,   pmc_pl,   pi_id,   pmc_img,  "
					+ " pmc_tp1,   pmc_tp2) values("
					+ "'"+ miid+"',   "
					+ "'"+ pc.getPmc_name()+"',  "
					+ pc.getPmc_sugar()+", "
					+ "'"+ pc.getPmc_vg()+"', "
					+ "'"+ pc.getPmc_pl()+"',"
					+ " '"+ pc.getPi_id()+"' , "
					+ "'"+ pc.getPmc_img()+"',  "
					+ " '"+ pc.getPmc_tp1()+"'   ,"
					+ " '"+ pc.getPmc_tp2()+"');";
					
			System.out.println(sql + "ProductCustomDao:customInsert");	
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		
		}catch(Exception e) {
			System.out.println("ProductCustomDao:customInsert()오류");
			e.printStackTrace();
		}finally {
			close(stmt); 
		}		
		return result;
	}
			
	

	
	
	
	


	
	
	
	

}



















