package svc;
import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;


public class ProductCustomSvc {
	public ArrayList<ProductCustom> getCustomList(String miid){
		ArrayList<ProductCustom> customList = new ArrayList<ProductCustom>();
		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		customList = productCustomDao.getCustomList(miid);

		close(conn);
		return customList;	
	}
	public ProductCustom getCustomInfo(int pcidx) {
		ProductCustom pc= null;
		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		pc = productCustomDao.getCustomInfo(pcidx);
	
		close(conn);
		return pc;
	}
	public String getToppingName(String tpid) {
		String tpname = "";
		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		tpname = productCustomDao.getToppingName(tpid);
		close(conn);
		return tpname;
	}
	
	
	
	

}


