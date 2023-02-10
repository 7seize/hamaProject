package dao;

import static db.JdbcUtil.*;  
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.*;
import vo.*;
import svc.*;


public class EvTorProcDao {
	//��ʸ�Ʈ �Խ���  ���� �����۾�(���, ���, ����)���� ��� ó���ϴ� Ŭ���� 
		private static EvTorProcDao evTorProcDao;
		private Connection conn;
		private EvTorProcDao() {} // �⺻������
		
		public static EvTorProcDao  getInstance() {
			if (evTorProcDao == null) evTorProcDao = new EvTorProcDao(); 
			//�̹� ������ freeProcDao Ŭ������ �ν��Ͻ��� ������ ���Ӱ�  �ν��Ͻ��� �����϶� 
			return evTorProcDao;
		}
		public void setConnection(Connection conn) {
		//�� Dao Ŭ�������� ����� Ŀ�ؼ� ��ü�� �޾ƿͼ� �������ִ� �޼ҵ� 
			this.conn = conn;
		}

		public  ArrayList<EvCusTor> getTorList( String kind, int cpage, int psize){
			//Ŀ���� ����� ArrayList�� �����ϴ� �޼ҵ�
			Statement stmt = null;
			ResultSet rs = null;
			ArrayList<EvCusTor> torList  = new ArrayList<EvCusTor>();
			//�׳� �����ص� ��
			EvCusTor ect = null;
			//customList�� ������ �ϳ��� �Խñ� ������ ���� �ν��Ͻ� 
			try {
				/*
				 * ����Ʈ�� �ƴ� 
				 * String sql =  "select a.pmc_idx, a.pmc_sugar, a.pmc_vg, a.pmc_pl, a.pi_id, " + 
									" a.pmc_tp1, a.pmc_tp2, a.pmc_img, b.ect_idx, b.ect_vote, b.ect_title, b.ect_content " + 
									" from t_product_ma_custom a, t_ev_cus_tor b where a.pmc_idx = b.pmc_idx " + 
									 " and b.ect_isview = 'y' " + "and a.pmc_isview='y' " + " and a.pmc_isbuy='y';"; 
							
				*/
				// customList���� ������ ���
				String sql = "select a.*, b.* " + 
						" from t_product_ma_custom a,  t_ev_cus_tor b " +
						" where a.mi_id = b.mi_id and a.pmc_idx = b.pmc_idx and a.pmc_isview='y' and a.pmc_isbuy='y';"; 
				
				stmt = conn.createStatement();
				System.out.println(sql + ":EvTorProcDao : getTorList()�޼ҵ���� ");
				rs = stmt.executeQuery(sql); 
				//�� ���� �ƴϴϱ� ���������� 
				while(rs.next()) {
					 ect = new EvCusTor();
					 ect.setPmc_idx(rs.getInt("pmc_idx"));
					 ect.setEct_idx(rs.getInt("ect_idx"));
					 ect.setEct_vote(rs.getInt("ect_vote"));// �α������ �ڵ����� �ϱ����� �ʿ�
					 ect.setEct_img1(rs.getString("ect_img1"));
					 ect.setEct_title(rs.getString("ect_title"));
					 ect.setEct_date(rs.getString("ect_date")); 
					 torList.add(ect);
				}
			
			}catch(Exception e) {
				System.out.println("EvTorProcDao : getTorList()�޼ҵ���� ");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt); 
			}
			
			
			return torList;
		}
		
		public EvCusTor getEvCusTorInfo(int ectidx){
			//������ �Խñ��� �������� EvCusTor�� �ν��Ͻ��� �����ϴ� �޼ҵ� 
			Statement stmt = null;
			ResultSet rs = null;
			
			EvCusTor ect = null;
		
			try {
				String sql = "select * from t_ev_cus_tor where ect_isview = 'n' and ect_idx=" + ectidx;
			
				stmt = conn.createStatement();
				System.out.println(sql + ":EvTorProcDao :getEvCusTorInfo()");
				rs = stmt.executeQuery(sql); 
				// �� �Խñۿ��� ������ �׸�� 
				//�� �� �ϱ� ���� �ȵ����� �� 
				if(rs.next()) {
					ect = new EvCusTor();
					ect.setEct_idx(ectidx);
					ect.setMi_id(rs.getString("mi_id"));
					ect.setPmc_idx(rs.getInt("pmc_idx"));
					ect.setEct_date(rs.getString("ect_date"));
					ect.setEct_vote(rs.getInt("ect_vote"));
					ect.setEct_img1(rs.getString("ect_img1"));
					ect.setEct_title(rs.getString("ect_title"));
					ect.setEct_content(rs.getString("ect_content"));
				}
			
			}catch(Exception e) {
				System.out.println("EvTorProcDaoŬ����:getEvCusTorInfo()����");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt); 
			}		
			return ect;
		}

}
