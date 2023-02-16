package svc;
import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ProductViewSvc {
	public int readUpdate(String piid) {
		int result = 0;
		Connection conn = getConnection();
		ProductDao productDao = ProductDao.getInstance();
		productDao.setConnection(conn);

		result = productDao.readUpdate(piid);
		if (result == 1)	commit(conn);
		else				rollback(conn);
		close(conn);

		return result;
	}

	public ProductInfo getProductInfo(String piid) {
		ProductInfo pi = null;
		Connection conn = getConnection();
		ProductDao productDao = ProductDao.getInstance();
		productDao.setConnection(conn);

		pi = productDao.getProductInfo(piid);
		close(conn);

		return pi;
	}

}
