package svc;

import static db.JdbcUtil.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ProductCusSvc {
	public  ArrayList<ProductCustom>  getProductList( int cpage, int psize, String where,String order){
		ArrayList<ProductCustom> productCusList  = new ArrayList<ProductCustom>();
		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		productCusList = productCustomDao.getProductCusList(cpage, psize, where, order);
		
		close(conn);
		
		return productCusList;
	}
	
	
	
	public int getListCount(String where) {
		int rcnt=0;
		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		rcnt = productCustomDao.getListCount(where);
		close(conn);
		
		return rcnt;
	}
	public int statUpdate(String status, String pmcidx) {
		int result=0;

		Connection conn = getConnection();
		ProductProcDao productProcDao = ProductProcDao.getInstance();
		productProcDao.setConnection(conn);
		
		result = productProcDao.statUp(status, pmcidx);
		if(result == 1) 	commit(conn);
		else				rollback(conn);

		close(conn);
		return result;
	}

}
