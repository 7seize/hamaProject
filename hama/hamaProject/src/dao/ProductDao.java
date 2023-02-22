package dao;
import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import vo.*;


public class ProductDao {
	// 상품 관련된 쿼리 작업(목록, 상세보기)들을 모두 처리하는 클래스
	private static ProductDao productDao;
	private Connection conn;
	private ProductDao() {}
	public static ProductDao getInstance() {
		if (productDao == null)	productDao = new ProductDao();	
		return productDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	
	// product_list.jsp 상품 목록 관련 dao
	public ArrayList<ProductInfo> getProductList(String where, String orderBy) {
		// 검색되는 상품 목록을 지정한 페이지에 맞춰 ArrayList<ProductInfo>형으로 리턴하는 메소드
			Statement stmt = null;
			ResultSet rs = null;
			ArrayList<ProductInfo> productList = new ArrayList<ProductInfo>();
			ProductInfo pi = null;

			try {
				String sql = "select pi_id, pi_img1, pi_name, pc_id, " + 
					" pi_price from t_product_info  where pi_isview = 'y' " + 
					where + " group by pi_id " + orderBy  ;
					
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
				System.out.println("ProductDao 클래스의 getProductList() 메소드 오류");
				e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}
			return productList;
		}
	
	//여기부터는 상품 상세 
	//보려는 상품의 조회수를 1증가시키는 메소드 
	public int readUpdate(String piid) {
		int result = 0;
		Statement stmt = null;
		try {
			String sql = " update t_product_info "
					+ " set pi_read = pi_read + 1  "
					+ " where pi_id = '" + piid+ "' ";
			System.out.println(sql + "ProductDao : readUpdate");
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			//프라이머리키로 같은거 찾는거니까 1외에 나올 수 없음 		
		} catch(Exception e) {
			System.out.println("ProductDao 클래스의 readUpdate() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		return result;
	}
	
	//사용자가 선택한 상품의 정보를 ProductInfo형 인스턴스로 리턴하는 메소드 	
	public ProductInfo getProductInfo(String piid) {
		Statement stmt = null;
		ResultSet rs = null;
		ProductInfo pi = null;
		try {
			String sql = "select * from t_product_info where pi_isview = 'y' "
					+ " and pi_id = '" + piid +"' ";
			
			System.out.println(sql + "ProductDao/getProductInfo");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			//프라이머리키로 같은거 찾는거니까 1외에 나올 수 없음 
			
			if(rs.next()) {
				//데이터가 있을 때 
				pi = new ProductInfo();// 상품 정보를 저장할 인스턴스 생성 
				
				pi.setPi_id(rs.getString("pi_id"));
				pi.setPc_id(rs.getString("pc_id"));
				pi.setPi_name(rs.getString("pi_name"));
				pi.setPi_price(rs.getInt("pi_price"));
				pi.setPi_cost(rs.getInt("pi_cost"));
				pi.setPi_dc(rs.getInt("pi_dc"));
				pi.setPi_img1(rs.getString("pi_img1"));
				pi.setPi_img2(rs.getString("pi_img2"));
				pi.setPi_img3(rs.getString("pi_img3"));
				pi.setPi_desc(rs.getString("pi_desc"));
				pi.setPi_read(rs.getInt("pi_read"));
				pi.setPi_score(rs.getFloat("pi_score"));
				pi.setPi_review(rs.getInt("pi_review"));
				pi.setPi_sale(rs.getInt("pi_sale"));
				pi.setPi_limit(rs.getString("pi_limit"));
				pi.setPi_alg(rs.getString("pi_alg"));
				pi.setPi_kcal(rs.getInt("pi_kcal"));

			}//데이터가 없으면 null이 pi에 들어갈거고 < 그러면 null이면 ctrl에서
			//상품정보 없다고 튕기게 되어있음.
			
			
		} catch(Exception e) {
			System.out.println("ProductDao 클래스의 getProductInfo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs); close(stmt);
		}		
		
		return pi;
	}
	
	
	
	
	
}
