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
	
		public  ArrayList<ProductCustom> getCustomList( int cpage, int psize){
			//Ŀ���� ����� ArrayList�� �����ϴ� �޼ҵ�
			Statement stmt = null;
			ResultSet rs = null;
			ArrayList<ProductCustom> customList  = new ArrayList<ProductCustom>();
			//�׳� �����ص� ��
			//�ϳ��� �����ǵ��� ������ ArrayList<ProductCustom> 
			ProductCustom pmc = null;
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
				String sql = "select a.pmc_idx, a.pmc_img ,b.ect_idx, b.ect_title " + 
						" from t_product_ma_custom a,  t_ev_cus_tor b " +
						" where a.mi_id = b.mi_id and a.pmc_idx = b.pmc_idx and a.pmc_isview='y' and a.pmc_isbuy='y';"; 
				
				stmt = conn.createStatement();
				System.out.println(sql + ":EvTorProcDao : getCustomList()�޼ҵ���� ");
				rs = stmt.executeQuery(sql); 
				//�� ���� �ƴϴϱ� ���������� 
				while(rs.next()) {
					 pmc = new ProductCustom();
					 pmc.setPmc_idx(rs.getInt("pmc_idx"));
					 pmc.setPmc_img(rs.getString("pmc_img"));
					 pmc.setEct_idx(rs.getInt("ect_idx"));
					 pmc.setEct_title(rs.getString("ect_title"));
					 customList.add(pmc);
				}
			
			}catch(Exception e) {
				System.out.println("EvTorProcDao : getCustomList()�޼ҵ���� ");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt); 
			}
			
			
			return customList;
		}
		
		
		public int voteUpdate(int ectidx) {
			//��ǥ���� ������Ű�� �޼ҵ�
			int result=0;
			Statement stmt = null;		
			try {
				String sql = " update t_ev_cus_tor set ect_vote = ect_vote + 1 "
						+ " where ect_vote = " +  ectidx;
				stmt = conn.createStatement();
				System.out.println(sql + ":EvTorProcDao:voteUpdate ");
				result = stmt.executeUpdate(sql);
			
			}catch(Exception e) {
				System.out.println("FreeProcDaoŬ����:readUpdate()����");
				e.printStackTrace();
			}finally {
				 close(stmt); 
			}
			return result;
			
		}
		/*
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
				//�� �� �ϱ� ���� �ȵ����� �� 
				if(rs.next()) {
					ect = new EvCusTor();
					ect.setEct_idx(ectidx);
					ect.setBf_ismem(rs.getString("bf_ismem"));
					bf.setBf_writer(rs.getString("bf_writer"));
					bf.setBf_header(rs.getString("bf_header"));
					bf.setBf_title(rs.getString("bf_title"));
					bf.setBf_content(rs.getString("bf_content"));
					bf.setBf_date(rs.getString("bf_date"));
					bf.setBf_read(rs.getInt("bf_read"));
					bf.setBf_ip(rs.getString("bf_ip"));
				}
			
			}catch(Exception e) {
				System.out.println("FreeProcDaoŬ����:getFreeInfo()����");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt); 
			}		
			return bf;
		}
*/
}
