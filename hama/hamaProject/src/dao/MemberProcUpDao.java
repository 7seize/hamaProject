package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import vo.*;

//회원 정보수정에 관련된 쿼리 작업을 처리하는 클래스
public class MemberProcUpDao {
	private static MemberProcUpDao memberProcUpDao;
	private Connection conn;
	private MemberProcUpDao() {}
	
	public static MemberProcUpDao getInstance() {
		if(memberProcUpDao == null)	memberProcUpDao = new MemberProcUpDao();
		
		return memberProcUpDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int memberProcUp(MemberInfo memberInfo, String update) {
	// 사용자가 입력한 데이터들로 회원 정보수정을 시키는 메소드
		Statement stmt = null;
		int result = 0;
		try {
			String sql = "update t_member_info set " +	update +
					" where mi_id = '" + memberInfo.getMi_id() + "' and mi_pw = '"+ memberInfo.getMi_pw() +"' ";
			System.out.println(sql+"memberProcUp");
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
			
		}catch(Exception e) {
			System.out.println("MemberProcUpDao 클래스의 memberProcUp() 메소드 오류");
			e.printStackTrace();
		}finally {
			close(stmt);
		}
		return result;
	}
}
