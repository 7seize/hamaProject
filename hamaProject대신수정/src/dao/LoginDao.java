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
	//기본 생성자를 private으로 선언하여 외부에서 함부로 인스턴스를 생성하지 못하게 막음
	
	public static LoginDao  getInstance() {
	//LoginDao 클래스의 인스턴스를 생성해주는 매소드  
	//이미 인스턴스가 있으면 기존의 인스턴스를 리턴한다.
	//LoginDao 클래스의 인스턴스를 하나만 생성하여 사용하게 하는 싱글톤 방식
		if (loginDao == null) loginDao = new LoginDao(); 
		//이미 생성된 LoginDao 클래스의 인스턴스가 없으면 새롭게  인스턴스를 생성하라 
		return loginDao;
	}
	public void setConnection(Connection conn) {
	//현 Dao 클래스에서 사용할 커넥션 객체를 받아와서 생성해주는 메소드 
		this.conn = conn;
	}
	public MemberInfo getLoginInfo(String uid, String pwd) {
	//받아온 아이디와 암호로 로그인 작업을 처리한 후, 회원정보 MemberInfo형 인스턴스로 리턴
		Statement stmt = null;
		ResultSet rs = null;
		MemberInfo loginInfo = null;
		
		try {
			
			String sql = "select * from t_member_info " +
					" where mi_status <> 'c' and mi_id = '"+ uid +
					"' and mi_pw = '" + pwd + "'";

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); //쿼리 stmt에 넣음
			
			if(rs.next()){ //로그인성공시 ,rs에 데이터가 있으면 

				loginInfo = new MemberInfo();
				//로그인한 회원의 정보들을 저장할 인스턴스 생성 
				loginInfo.setMi_id(rs.getString("mi_id"));
				loginInfo.setMi_pw(rs.getString("mi_pw"));
				loginInfo.setMi_name(rs.getString("mi_name"));
				loginInfo.setMi_memo(rs.getString("mi_memo"));
				loginInfo.setMi_birth(rs.getString("mi_birth"));
				loginInfo.setMi_phone(rs.getString("mi_phone"));
				loginInfo.setMi_email(rs.getString("mi_email"));
				loginInfo.setMi_point(rs.getInt("mi_point"));
				loginInfo.setMi_joindate(rs.getString("mi_joindate"));
				loginInfo.setMi_status(rs.getString("mi_status"));

			
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
