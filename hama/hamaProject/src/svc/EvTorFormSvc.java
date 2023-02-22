package svc;
import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;


public class EvTorFormSvc {
	public int didTor(String miid, String date){
		//회원의 아이디와 현재년월('2022-02')을 가져가서 t_ev_cus_tor 테이블에 글이 있는지 
		//여부로 토너먼트에 해당 월에 참여했는지 확인하는 메소드 
		int result=0;
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
		
		result = evTorProcDao.didTor(miid, date);
		close(conn);
		return result;
	}
	
	public ArrayList<ProductCustom> getbuyCustomList(String miid){
		ArrayList<ProductCustom> customList =   new ArrayList<ProductCustom>();
				
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
		
		customList = evTorProcDao.getbuyCustomList(miid);

		close(conn);
		return customList;	
	}
	public int torInsert(String miid, EvCusTor ect){
		//토너먼트 글 입력 
		int result=0;
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
		
		result = evTorProcDao.torInsert(miid, ect);
	
		if(result == 1) 	commit(conn);
		else				rollback(conn);	
		
		close(conn);
		return result;
	}
	

}
