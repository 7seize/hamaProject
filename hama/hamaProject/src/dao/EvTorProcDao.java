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
	
		public  ArrayList<ProductCustom> getCustomList( int cpage, int psize){
			//커스텀 목록을 ArrayList로 리턴하는 메소드
			Statement stmt = null;
			ResultSet rs = null;
			ArrayList<ProductCustom> customList  = new ArrayList<ProductCustom>();
			//그냥 생성해도 됨
			//하나의 레시피들을 저장할 ArrayList<ProductCustom> 
			ProductCustom pmc = null;
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
				String sql = "select a.pmc_idx, a.pmc_img ,b.ect_idx, b.ect_title " + 
						" from t_product_ma_custom a,  t_ev_cus_tor b " +
						" where a.mi_id = b.mi_id and a.pmc_idx = b.pmc_idx and a.pmc_isview='y' and a.pmc_isbuy='y';"; 
				
				stmt = conn.createStatement();
				System.out.println(sql + ":EvTorProcDao : getCustomList()메소드오류 ");
				rs = stmt.executeQuery(sql); 
				//한 개가 아니니까 루프돌리기 
				while(rs.next()) {
					 pmc = new ProductCustom();
					 pmc.setPmc_idx(rs.getInt("pmc_idx"));
					 pmc.setPmc_img(rs.getString("pmc_img"));
					 pmc.setEct_idx(rs.getInt("ect_idx"));
					 pmc.setEct_title(rs.getString("ect_title"));
					 customList.add(pmc);
				}
			
			}catch(Exception e) {
				System.out.println("EvTorProcDao : getCustomList()메소드오류 ");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt); 
			}
			
			
			return customList;
		}
		
		
		public int voteUpdate(int ectidx) {
			//득표수를 증가시키는 메소드
			int result=0;
			Statement stmt = null;		
			try {
				String sql = " update t_ev_cus_tor set ect_vote = ect_vote + 1 "
						+ " where ect_vote = " +  ectidx;
				stmt = conn.createStatement();
				System.out.println(sql + ":EvTorProcDao:voteUpdate ");
				result = stmt.executeUpdate(sql);
			
			}catch(Exception e) {
				System.out.println("FreeProcDao클래스:readUpdate()오류");
				e.printStackTrace();
			}finally {
				 close(stmt); 
			}
			return result;
			
		}
		/*
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
				//한 개 니까 루프 안돌려도 됨 
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
				System.out.println("FreeProcDao클래스:getFreeInfo()오류");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt); 
			}		
			return bf;
		}
*/
}
