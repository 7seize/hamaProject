package svc;
import static db.JdbcUtil.*; //jdbc 클래스의 모든 멤버들을 자유롭게 사용할 수 있음 
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;


public class LoginSvc {
//로그인에 필요한 아이디와 암호를 받아 비즈니스 로직을 처리하는 클래스 
//Db관련 쿼리작업은 제외 그건 dao에서 한다. 
	public MemberInfo getLoginInfo(String uid, String pwd) {
		Connection conn = getConnection();
		LoginDao loginDao = LoginDao.getInstance();
		loginDao.setConnection(conn);
		
		MemberInfo loginInfo =  loginDao.getLoginInfo(uid, pwd);
		close(conn);
		
		return loginInfo;
	}	
}
