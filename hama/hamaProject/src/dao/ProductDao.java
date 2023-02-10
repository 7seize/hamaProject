package dao;
import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import vo.*;


public class ProductDao {
	// ��ǰ ���õ� ���� �۾�(���, �󼼺���)���� ��� ó���ϴ� Ŭ����
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
	
	

	public ArrayList<ProductInfo> getProductList(String where, String orderBy) {
		// �˻��Ǵ� ��ǰ ����� ������ �������� ���� ArrayList<ProductInfo>������ �����ϴ� �޼ҵ�
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
				System.out.println("ProductDao Ŭ������ getProductList() �޼ҵ� ����");
				e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}
			return productList;
		}
	

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
			//�����̸Ӹ�Ű�� ������ ã�°Ŵϱ� 1�ܿ� ���� �� ���� 		
		} catch(Exception e) {
			System.out.println("ProductDao Ŭ������ readUpdate() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		return result;
	}
	
	//����ڰ� ������ ��ǰ�� ������ ProductInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ� 	
	public ProductInfo getProductInfo(String piid) {
		Statement stmt = null;
		ResultSet rs = null;
		ProductInfo pi = null;
		try {
			String sql = "select * from t_product_info where pi_isview = 'y' "
					+ " and pi_id = '" + piid +"' ";
			
			System.out.println(sql + "getProductInfo");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			//�����̸Ӹ�Ű�� ������ ã�°Ŵϱ� 1�ܿ� ���� �� ���� 
			
			if(rs.next()) {
				//�����Ͱ� ���� �� 
				pi = new ProductInfo();// ��ǰ ������ ������ �ν��Ͻ� ���� 
				
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

			}//�����Ͱ� ������ null�� pi�� ���Ű� < �׷��� null�̸� ctrl����
			//��ǰ���� ���ٰ� ƨ��� �Ǿ�����.
			
			
		} catch(Exception e) {
			System.out.println("ProductDao Ŭ������ getProductInfo() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs); close(stmt);
		}		
		
		return pi;
	
	}
	
	
	
	
	
	
	
	
	

	
}
