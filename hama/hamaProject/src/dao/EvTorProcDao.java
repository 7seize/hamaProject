package dao;

import static db.JdbcUtil.*;  
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.*;
import vo.*;
import svc.*;


public class EvTorProcDao {
	//토너먼트 게시판  관련 쿼리작업(목록, 등록, 삭제)등을 모두 처리하는 클래스 
		private static EvTorProcDao evTorProcDao;
		private Connection conn;
		private EvTorProcDao() {} // 기본생성자
		
		public static EvTorProcDao  getInstance() {
			if (evTorProcDao == null) evTorProcDao = new EvTorProcDao(); 
			//이미 생성된 freeProcDao 클래스의 인스턴스가 없으면 새롭게  인스턴스를 생성하라 
			return evTorProcDao;
		}
		public void setConnection(Connection conn) {
		//현 Dao 클래스에서 사용할 커넥션 객체를 받아와서 생성해주는 메소드 
			this.conn = conn;
		}

		public  ArrayList<EvCusTor> getTorList( String kind, int cpage, int psize){
			//커스텀 목록을 ArrayList로 리턴하는 메소드
			Statement stmt = null;
			ResultSet rs = null;
			ArrayList<EvCusTor> torList  = new ArrayList<EvCusTor>();
			//그냥 생성해도 됨
			EvCusTor ect = null;
			//customList에 저장할 하나의 게시글 정보를 담을 인스턴스 
			try {
				/*
				 * 리스트가 아닌 
				 * String sql =  "select a.pmc_idx, a.pmc_sugar, a.pmc_vg, a.pmc_pl, a.pi_id, " + 
									" a.pmc_tp1, a.pmc_tp2, a.pmc_img, b.ect_idx, b.ect_vote, b.ect_title, b.ect_content " + 
									" from t_product_ma_custom a, t_ev_cus_tor b where a.pmc_idx = b.pmc_idx " + 
									 " and b.ect_isview = 'y' " + "and a.pmc_isview='y' " + " and a.pmc_isbuy='y';"; 
							
				*/
				// customList에서 보여질 목록
				String sql = "select a.*, b.* " + 
						" from t_product_ma_custom a,  t_ev_cus_tor b " +
						" where a.mi_id = b.mi_id and a.pmc_idx = b.pmc_idx and a.pmc_isview='y' and a.pmc_isbuy='y';"; 
				
				stmt = conn.createStatement();
				System.out.println(sql + ":EvTorProcDao : getTorList()메소드오류 ");
				rs = stmt.executeQuery(sql); 
				//한 개가 아니니까 루프돌리기 
				while(rs.next()) {
					 ect = new EvCusTor();
					 ect.setPmc_idx(rs.getInt("pmc_idx"));
					 ect.setEct_idx(rs.getInt("ect_idx"));
					 ect.setEct_vote(rs.getInt("ect_vote"));// 인기순으로 자동정렬 하기위해 필요
					 ect.setEct_img1(rs.getString("ect_img1"));
					 ect.setEct_title(rs.getString("ect_title"));
					 ect.setEct_date(rs.getString("ect_date")); 
					 torList.add(ect);
				}
			
			}catch(Exception e) {
				System.out.println("EvTorProcDao : getTorList()메소드오류 ");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt); 
			}
			
			
			return torList;
		}
		
		public EvCusTor getEvCusTorInfo(int ectidx){
			//지정한 게시글의 정보들을 EvCusTor형 인스턴스로 리턴하는 메소드 
			Statement stmt = null;
			ResultSet rs = null;
			
			EvCusTor ect = null;
		
			try {
				String sql = "select * from t_ev_cus_tor where ect_isview = 'n' and ect_idx=" + ectidx;
			
				stmt = conn.createStatement();
				System.out.println(sql + ":EvTorProcDao :getEvCusTorInfo()");
				rs = stmt.executeQuery(sql); 
				// 한 게시글에서 보여줄 항목들 
				//한 개 니까 루프 안돌려도 됨 
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
				System.out.println("EvTorProcDao클래스:getEvCusTorInfo()오류");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt); 
			}		
			return ect;
		}

}
