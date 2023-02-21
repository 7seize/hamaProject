package svc;

import static db.JdbcUtil.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ProductListSvc {
	public  ArrayList<ProductInfo>  getProductList( int cpage, int psize, String where,String order){
		ArrayList<ProductInfo> productList  = new ArrayList<ProductInfo>();
		Connection conn = getConnection();
		ProductProcDao productProcDao = ProductProcDao.getInstance();
		productProcDao.setConnection(conn);
		
		productList = productProcDao.getProductList(cpage, psize, where, order);
		
		close(conn);
		
		return productList;
	}
	public int getListCount(String where) {
		int rcnt=0;
		Connection conn = getConnection();
		ProductProcDao productProcDao = ProductProcDao.getInstance();
		productProcDao.setConnection(conn);
		
		rcnt = productProcDao.getListCount(where);
		close(conn);
		
		return rcnt;
	}
	public int statUpdate(String status, String oiid) {
		int result=0;

		Connection conn = getConnection();
		OrderProcDao orderProcDao = OrderProcDao.getInstance();
		orderProcDao.setConnection(conn);
		
		result = orderProcDao.statUp(status, oiid);
		if(result == 1) 	commit(conn);
		else				rollback(conn);

		close(conn);
		return result;
	}
}
