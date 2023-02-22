package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ProductSetViewSvc {
	public ArrayList<ProductCustom> getProductCustom(String miid) {
		ArrayList<ProductCustom> pc = null;
		Connection conn = getConnection();
		ProductSetViewDao productSetViewDao = ProductSetViewDao.getInstance();
		productSetViewDao.setConnection(conn);

		pc = productSetViewDao.getProductCustom(miid);
		close(conn);

		return pc;
	}
	//getProductInfo
	public ArrayList<ProductInfo> getProductInfo() {
		ArrayList<ProductInfo> pilist = null;
		Connection conn = getConnection();
		ProductSetViewDao productSetViewDao = ProductSetViewDao.getInstance();
		productSetViewDao.setConnection(conn);

		pilist = productSetViewDao.getProductInfo();
		close(conn);

		return pilist;
	}
}
