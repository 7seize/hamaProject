package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;




public class ProductListSvc {
	public ArrayList<ProductInfo> getProductList(String where, String orderBy) {
		ArrayList<ProductInfo> productList = new ArrayList<ProductInfo>();
		Connection conn = getConnection();
		ProductDao productDao = ProductDao.getInstance();
		productDao.setConnection(conn);

		productList = productDao.getProductList(where, orderBy);
		close(conn);

		return productList;
	}
	
	
		
}
