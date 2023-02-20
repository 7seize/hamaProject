package dao;
import static db.JdbcUtil.*;  
import java.util.*;
import java.sql.*;
import javax.*;
import vo.*;


public class LoginDao {
//로그인에 관련된 쿼리 작업을 처리하는 클래스 
	private static LoginDao loginDao;
	private Connection conn;
	private LoginDao() {}
	
	public static LoginDao  getInstance() {
		if (loginDao == null) loginDao = new LoginDao(); 	
		return loginDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	public AdminInfo getLoginInfo(String aid, String pwd) {
	//받아온 아이디와 암호로 로그인 작업을 처리한 후, 관리자 정보 AdminInfo형 인스턴스로 리턴
		Statement stmt = null;
		ResultSet rs = null;
		AdminInfo loginInfo = null;
		
		try {
			String sql = "select * from t_admin_info " +
					" where ai_id = '"+ aid +
					"' and ai_pw = '" + pwd + "'";

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); //쿼리 stmt에 넣음
			
			if(rs.next()){ //로그인성공시 ,rs에 데이터가 있으면 
				loginInfo = new AdminInfo();
				//로그인한 관리자 정보들을 저장할 인스턴스 생성 
				loginInfo.setAi_idx(rs.getInt("ai_idx"));
				loginInfo.setAi_pw(rs.getString("ai_pw"));
				loginInfo.setAi_name(rs.getString("ai_name"));
						
			} //rs가 비었으면 else없이 loginInfo 인스턴스에 null이 있는 상태로 리턴할 것 
			
		}catch(Exception e) {
			System.out.println("LoginDao 클래스 getLoginInfo() 메소드 오류");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}
		return loginInfo;
		//리턴한다
	}
}
