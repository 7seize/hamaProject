package svc;
import static db.JdbcUtil.*; //jdbc Ŭ������ ��� ������� �����Ӱ� ����� �� ���� 
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;


public class LoginSvc {
//�α��ο� �ʿ��� ���̵�� ��ȣ�� �޾� ����Ͻ� ������ ó���ϴ� Ŭ���� 
//Db���� �����۾��� ���� �װ� dao���� �Ѵ�. 
	public AdminInfo getLoginInfo(String aid, String pwd) {
		Connection conn = getConnection();
		LoginDao loginDao = LoginDao.getInstance();
		loginDao.setConnection(conn);
		
		AdminInfo loginInfo =  loginDao.getLoginInfo(aid, pwd);
		close(conn);
		
		return loginInfo;
	}	
}
