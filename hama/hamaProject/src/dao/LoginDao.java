package dao;
import static db.JdbcUtil.*;  
import java.util.*;
import java.sql.*;
import javax.*;
import vo.*;


public class LoginDao {
//�α��ο� ���õ� ���� �۾��� ó���ϴ� Ŭ���� 
	private static LoginDao loginDao;
	private Connection conn;
	private LoginDao() {}
	//�⺻ �����ڸ� private���� �����Ͽ� �ܺο��� �Ժη� �ν��Ͻ��� �������� ���ϰ� ����
	
	public static LoginDao  getInstance() {

		if (loginDao == null) loginDao = new LoginDao(); 

		return loginDao;
	}
	public void setConnection(Connection conn) {

		this.conn = conn;
	}
	public MemberInfo getLoginInfo(String uid, String pwd) {
	//�޾ƿ� ���̵�� ��ȣ�� �α��� �۾��� ó���� ��, ȸ������ MemberInfo�� �ν��Ͻ��� ����
		Statement stmt = null;
		ResultSet rs = null;
		MemberInfo loginInfo = null;
		
		try {
			
			String sql = "select * from t_member_info " +
					" where mi_status <> 'c' and mi_id = '"+ uid +
					"' and mi_pw = '" + pwd + "'";

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); 
			
			if(rs.next()){

				loginInfo = new MemberInfo();
				//�α����� ȸ���� �������� ������ �ν��Ͻ� ���� 
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

			
			}
			
		}catch(Exception e) {
			System.out.println("LoginDao Ŭ���� getLoginInfo() �޼ҵ� ����");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}
		return loginInfo;

	}
}
