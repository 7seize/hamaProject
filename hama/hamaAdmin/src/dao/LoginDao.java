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
	
	public static LoginDao  getInstance() {
		if (loginDao == null) loginDao = new LoginDao(); 	
		return loginDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	public AdminInfo getLoginInfo(String aid, String pwd) {
	//�޾ƿ� ���̵�� ��ȣ�� �α��� �۾��� ó���� ��, ������ ���� AdminInfo�� �ν��Ͻ��� ����
		Statement stmt = null;
		ResultSet rs = null;
		AdminInfo loginInfo = null;
		
		try {
			String sql = "select * from t_admin_info " +
					" where ai_id = '"+ aid +
					"' and ai_pw = '" + pwd + "'";

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); //���� stmt�� ����
			
			if(rs.next()){ //�α��μ����� ,rs�� �����Ͱ� ������ 
				loginInfo = new AdminInfo();
				//�α����� ������ �������� ������ �ν��Ͻ� ���� 
				loginInfo.setAi_idx(rs.getInt("ai_idx"));
				loginInfo.setAi_pw(rs.getString("ai_pw"));
				loginInfo.setAi_name(rs.getString("ai_name"));
						
			} //rs�� ������� else���� loginInfo �ν��Ͻ��� null�� �ִ� ���·� ������ �� 
			
		}catch(Exception e) {
			System.out.println("LoginDao Ŭ���� getLoginInfo() �޼ҵ� ����");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}
		return loginInfo;
		//�����Ѵ�
	}
}
