package dao;

import static db.JdbcUtil.*;  
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.*;
import vo.*;
import svc.*;


public class MemberProcDao {
	private static MemberProcDao memberProcDao;
	private Connection conn;
	private MemberProcDao() {} 
	
	public static MemberProcDao  getInstance() {
		if (memberProcDao == null) memberProcDao = new MemberProcDao(); 
		return memberProcDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public  ArrayList<MemberInfo> getMemberList(int cpage, int psize, String where, String order){

		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<MemberInfo> memberList  = new ArrayList<MemberInfo>();
		MemberInfo mi = null;

		try {

			String sql = "select * from t_member_info "+
			where + order +
			" limit "+(cpage-1)*psize+","+psize;
			
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); 

			while(rs.next()) {

				mi = new MemberInfo();
			 	mi.setMi_id(rs.getString("mi_id"));
			 	mi.setMi_name(rs.getString("mi_name"));
			 	mi.setMi_phone(rs.getString("mi_phone"));
			 	mi.setMi_email(rs.getString("mi_email"));
			 	mi.setMi_joindate(rs.getString("mi_joindate"));
			 	mi.setMi_status(rs.getString("mi_status"));
			 	mi.setMi_point(rs.getInt("mi_point"));
			 	memberList.add(mi);
			 	
				}
		
		}catch(Exception e) {
			System.out.println("MemberProcDao :getMemberList() ����");
			e.printStackTrace();
		}finally {
			
			close(rs); close(stmt); 
			
		}
		
		return memberList;
	}
	public int getListCount(String where) {
		int rcnt=0;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select count(*) from t_member_info " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); 
			
			if(rs.next()) rcnt = rs.getInt(1);
			//�÷��� ��� �ε��� ��ȣ �׳� �� 
			//select count�ϴ� �ſ��� ��� �˻� ���ص� ��.
		
		}catch(Exception e) {
			System.out.println("MemberProcDaoŬ���� getListCount()����");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}

	
		return rcnt;
	}
	public int statUp(String status, String miid) {
		int result = 0;
		Statement stmt = null; 
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			String sql = "update t_member_info set mi_status = "+
			"'"+status+"' where mi_id = '"+miid+"'"; 

			System.out.println(sql);
			result = stmt.executeUpdate(sql);		
			
		}catch(Exception e){
			System.out.println("MemberProcDao Class statUp Fall");
			e.printStackTrace();
		}finally {
			close(stmt);
		}
		return result;
	}
	public  ArrayList<MemberQna> getMemberQnaList(int cpage, int psize, String where, String order){

		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<MemberQna> memberqna  = new ArrayList<MemberQna>();
		MemberQna mq = null;

		try {

			String sql = "select bq_ctgr, bq_title, bq_content, bq_img1, bq_img2, "+
			"bq_qdate, bq_isanswer, bq_ai_idx, bq_answer, bq_adate, b.mi_id, b.mi_name "+
			" from t_bbs_qna a, t_member_info b "+
			where +" and a.mi_id = b.mi_id "+ order +
			" limit "+(cpage-1)*psize+","+psize;
			
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); 

			while(rs.next()) {
				mq = new MemberQna();
				mq.setBq_ctgr(rs.getString("bq_ctgr"));
				mq.setBq_title(rs.getString("bq_title"));
				mq.setBq_content(rs.getString("bq_content"));
				mq.setBq_img1(rs.getString("bq_img1"));
				mq.setBq_img2(rs.getString("bq_img2"));
				mq.setBq_qdate(rs.getString("bq_qdate"));
				mq.setBq_isanswer(rs.getString("bq_isanswer"));
				mq.setMi_id(rs.getString("mi_id"));
				mq.setMi_name(rs.getString("mi_name"));
				
				memberqna.add(mq);
			}
		
		}catch(Exception e) {
			System.out.println("MemberProcDao :getMemberQnaList() fail");
			e.printStackTrace();
		}finally {
			
			close(rs); close(stmt); 
			
		}
		
		return memberqna;
	}
	public int getQnaListCount(String where) {
		int rcnt=0;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select count(*) from t_bbs_qna " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); 
			
			if(rs.next()) rcnt = rs.getInt(1);
			//�÷��� ��� �ε��� ��ȣ �׳� �� 
			//select count�ϴ� �ſ��� ��� �˻� ���ص� ��.
		
		}catch(Exception e) {
			System.out.println("MemberProcDao getQnaListCount() fail");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}

	
		return rcnt;
	}
}
