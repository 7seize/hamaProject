package svc;
import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;


public class ProductCustomSvc {
	public ArrayList<ProductCustom> getCustomList(String miid){
		//아이디에 해당하는 커스텀 리스트를
		ArrayList<ProductCustom> customList = new ArrayList<ProductCustom>();
		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		customList = productCustomDao.getCustomList(miid);

		close(conn);
		return customList;	
	}
	public ProductCustom getCustomInfo(int pmcidx) {
		//해당 인덱스에 따른 커스텀마카롱 레시피를 가져오는
		ProductCustom pc= null;
		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		pc = productCustomDao.getCustomInfo(pmcidx);
	
		close(conn);
		return pc;
	}
	public String getToppingName(String tpid) {
		//해당 토핑아이디를 갖고 토핑의 이름을 가져오는
		String tpname = "";
		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		tpname = productCustomDao.getToppingName(tpid);
		close(conn);
		return tpname;
	}
	public int countCustom(String miid) {
		//해당 아이디 회원이 만든 마카롱 커스텀 레시피 중에서 게시가능한 것의 수를 가져오는 레코드
		int cnt = 0;
		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		cnt = productCustomDao.countCustom(miid);
		close(conn);
		
		return cnt;
		
	}
	public ArrayList<ProductInfo> getMacList(){
		//상품 리스트를 조건 없이 가져오기
		ArrayList<ProductInfo> macList = new ArrayList<ProductInfo>();
		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		macList = productCustomDao.getMacList();

		close(conn);
		return macList;	
	}
	public ArrayList<ProductTopping> getToppingList(){
		//토핑 리스트 가져오기 
		ArrayList<ProductTopping> toppingList = new ArrayList<ProductTopping>();
		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		toppingList = productCustomDao.getToppingList();

		close(conn);
		return toppingList;	
	}
	
	public int customInsert(String miid, ProductCustom pc){
		//커스터 레시피를 등록	
		int result = 0;

		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		result = productCustomDao.customInsert(miid, pc);
		
		if(result == 1) 	commit(conn);
		else				rollback(conn);	

		close(conn);
		return result;
	}
	
	
	
	
	
	
	

}


