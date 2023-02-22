package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.*;
import vo.*;
import svc.*;

public class EvTorProcDao {
	// 토너먼트 게시판 관련 쿼리작업(목록, 등록, 삭제)등을 모두 처리하는 클래스
	private static EvTorProcDao evTorProcDao;
	private Connection conn;

	private EvTorProcDao() {
	} // 기본생성자

	public static EvTorProcDao getInstance() {
		if (evTorProcDao == null)
			evTorProcDao = new EvTorProcDao();
		// 이미 생성된 freeProcDao 클래스의 인스턴스가 없으면 새롭게 인스턴스를 생성하라
		return evTorProcDao;
	}

	public void setConnection(Connection conn) {
		// 현 Dao 클래스에서 사용할 커넥션 객체를 받아와서 생성해주는 메소드
		this.conn = conn;
	}

	public int getTorListCount(String where) {
		// 토너먼트 리스트의 검색된 결과의 레코드(게시글) 개수를 리턴하는 메소드 
		// 페이징 버려서 안써요 
		int rcnt = 0;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select count(*) from t_ev_cus_tor " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next())
				rcnt = rs.getInt(1);
			// 컬럼명 없어서 인덱스 번호 그냥 씀
			// select count하는 거여서 사실 검사 안해도 됨.

		} catch (Exception e) {
			System.out.println("EvTorProcDao클래스:getTorListCount()오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}

		return rcnt;
	}

	public ArrayList<EvCusTor> getTorList( String kind, String date,  String orderBy){
		// 토너먼트 목록을 ArrayList로 리턴하는 메소드(토너먼트 목록 보여주기만 할 것)
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<EvCusTor> torList = new ArrayList<EvCusTor>();
		// 그냥 생성해도 됨
		EvCusTor ect = null;
		// torList에 저장할 하나의 게시글 정보를 담을 인스턴스
		// where 절에 kina=a or b일 경우 조건 다르게
		String orderby = orderBy;// 정렬도 다르게
		try {
		
			// torList에서 보여질 목록
			System.out.println(kind);
			System.out.println(orderby);
					
			
			String sql = "select * from t_ev_cus_tor ";
			String datesql = " where ect_isview='y' ";
			
			// kind=a 이번 달에 올라온 글들만 
			if (kind.equals("a")) {
				datesql += " and left(ect_date, 7)='"+date+"' ";
			}
			// kind=b 이번 달이 아닌  글들만 
			if (kind.equals("b")) { //지금 연,월이 맞지 않는 걸로 limit 100, 무조건 인기(득표순)
				datesql += " and left(ect_date, 7)<>'"+date+"'";
				orderby += " limit 0,100 " ;
			}
						
			sql += (datesql+ orderby) ;
			stmt = conn.createStatement();
			System.out.println(sql);

			rs = stmt.executeQuery(sql);
			// 한 개가 아니니까 루프돌리기
			while (rs.next()) {
				ect = new EvCusTor();
				ect.setMi_id(rs.getString("mi_id"));
				ect.setPmc_idx(rs.getInt("pmc_idx"));
				ect.setEct_idx(rs.getInt("ect_idx"));
				ect.setEct_vote(rs.getInt("ect_vote"));
				ect.setEct_img1(rs.getString("ect_img1"));
				ect.setEct_title(rs.getString("ect_title"));
				ect.setEct_date(rs.getString("ect_date"));
				torList.add(ect);
			}

		} catch (Exception e) {
			System.out.println("EvTorProcDao : getTorList()메소드오류 ");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}

		return torList;
	}

	public EvCusTor getEvCusTorInfo(int ectidx) {
		// 지정한 게시글의 정보들을 EvCusTor형 인스턴스로 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		EvCusTor ect = null;

		try {
			String sql = "select * from t_ev_cus_tor where ect_isview = 'y' and ect_idx=" + ectidx;

			stmt = conn.createStatement();
			System.out.println(sql + ":EvTorProcDao :getEvCusTorInfo()");
			rs = stmt.executeQuery(sql);
			// 한 게시글에서 보여줄 항목들
			// 한 개 니까 루프 안돌려도 됨
			if (rs.next()) {
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

		} catch (Exception e) {
			System.out.println("EvTorProcDao클래스:getEvCusTorInfo()오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return ect;
	}									
	public int voteBtn(int ectidx, String miid) { // ##############################
		// 지정한 게시물에 투표하기[좋아요와 유사]를 처리하는 메소드
		// 이미 투표를 했으면 0, 투표가 안되었을면 1을, 정상 처리 되었으면 2를 리턴
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;

		try {
			stmt = conn.createStatement();

			String sql = "select * from t_ev_cus_tor_poll" + " where left(ectp_date , 4) = left(now() , 4) and mi_id ='"+ miid+ "'";
			// mi_id

			System.out.println(sql + ":EvTorProcDao: voteBtn 셀렉트쿼리");
			rs = stmt.executeQuery(sql);

			if (rs.next()) { // 이미 투표를 했으면 result = 0;
				result = 0;
				System.out.println("이미 투표를 한 상태");
				System.out.println("쿼리 실패가 나와 나와야함");
			} else {
				 sql = "select * from t_ev_cus_tor" + " where left(ect_date , 4) = left(now() , 4) and " +
						 " ect_isview = 'y' and ect_idx = '" + ectidx +"'";
				rs = stmt.executeQuery(sql);
				int pmcidx = 0;
				if (rs.next()) {
					result =1;
					pmcidx = rs.getInt("pmc_idx");
				}
				if (result == 1) {
					sql = "update t_ev_cus_tor set ect_vote = ect_vote + 1 ";
					sql += " where ect_idx = " + ectidx;
					
					System.out.println(sql + ":EvTorProcDao: 자기가 원하는 레시피에 투표하는 쿼리");
					result = stmt.executeUpdate(sql);
					// 레시피에 투표하기 업데이트 쿼리
					
					sql = "insert into t_ev_cus_tor_poll  " + " (ect_idx, pmc_idx, mi_id) values ('" + ectidx
							+ "', '" + pmcidx + "', '" + miid + "')";
					System.out.println(sql + ":EvTorProcDao:투표한 기록을 남기는 쿼리 실행 ");

					result += stmt.executeUpdate(sql);
					// 투표하기 실행 추가 쿼리
				}
			}
		} catch (Exception e) {
			System.out.println("EvTorProcDao클래스:voteBtn()오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return result;
	}
	
	public EvCusTorPoll isvote(String miid) { // ##############################
		// 투표했는지 확인하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		EvCusTorPoll ectp = new EvCusTorPoll();

		try {
			stmt = conn.createStatement();
			String sql = "select * from t_ev_cus_tor_poll" + " where left(ectp_date , 4) = left(now() , 4) and mi_id ='"+ miid+ "'";
			// mi_id
			System.out.println(sql + ":투표여부 확인 쿼리");
			rs = stmt.executeQuery(sql);
			if (rs.next()) { // 이미 투표를 했으면 result = 0;
				ectp.setEctp_idx(rs.getInt("ectp_idx"));
				ectp.setEct_idx(rs.getInt("ect_idx"));
				ectp.setPmc_idx(rs.getInt("pmc_idx"));
				ectp.setMi_id(rs.getString("mi_id"));
				ectp.setEctp_date(rs.getString("ectp_date"));
			} else {
				ectp = null;
			}
		} catch (Exception e) {
			System.out.println("EvTorProcDao클래스:IsBtn()오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return ectp;
	}
	public int didTor(String miid, String date) {
		//회원의 아이디와 현재년월('2022-02')을 가져가서 t_ev_cus_tor 테이블에 글이 있는지 
		//여부로 토너먼트에 해당 월에 참여했는지 확인하는 메소드 
		int result = 0;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select count(*) from t_ev_cus_tor where left(ect_date, 7)='"
					+ date + "' and mi_id = '" + miid+ "';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			System.out.println(sql+"didTor");
			if (rs.next())
				result = rs.getInt(1);
			// 컬럼명 없어서 인덱스 번호 그냥 씀
			// select count하는 거여서 사실 검사 안해도 됨.

		} catch (Exception e) {
			System.out.println("EvTorProcDao클래스:didTor()오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}

		return result;
	}
	public ArrayList<ProductCustom> getbuyCustomList(String miid){
		//토너먼트 폼을 위해 회원이 구매하고 올린 적이 없는 커스텀 마카롱 리스트를 가져오는 메소드 
		Statement stmt = null; 
		ResultSet rs = null;
		ArrayList<ProductCustom> customList = new ArrayList<ProductCustom>();
		ProductCustom pc = null;
		
		try {
			String sql = "select a.*, b.pi_id, b.pi_name, b.pi_img1 "
					+ " from t_product_ma_custom a, t_product_info b "
					+ " where a.mi_id = '"
					+ miid
					+ "'and a.pi_id = b.pi_id  and a.pmc_isbuy = 'y' and a.pmc_idx "
					+ " not in(select pmc_idx from t_ev_cus_tor)";
			//아이디로 커스텀마카롱 검사하는데
			//토너먼트를 올린적이 없으며, 구매한 마카롱만  후보로 가져갈 수 있음 
			System.out.println(sql + ": /EvTorProcDao/getbuyCustomList");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);	
			
			while(rs.next()) {
				pc = new ProductCustom();
				
				pc.setPmc_idx(rs.getInt("pmc_idx")); //커스텀 마카롱 인덱스
				pc.setPi_name(rs.getString("pi_name")); //맛이름
				pc.setPi_img1(rs.getString("pi_img1")); //맛이미지
				pc.setPi_id(rs.getString("pi_id")); //맛상품아이디
				pc.setMi_id(miid); //회원 아이디 
				pc.setPmc_name(rs.getString("pmc_name"));
				pc.setPmc_sugar(rs.getInt("pmc_sugar"));
				pc.setPmc_vg(rs.getString("pmc_vg"));
				pc.setPmc_pl(rs.getString("pmc_pl"));
				pc.setPmc_tp1(rs.getString("pmc_tp1"));
				pc.setPmc_tp2(rs.getString("pmc_tp2"));
				pc.setPmc_date(rs.getString("pmc_date"));
				pc.setPmc_price(rs.getInt("pmc_price"));
				pc.setPmc_isview(rs.getString("pmc_isview"));
				pc.setPmc_isbuy(rs.getString("pmc_isbuy"));		
				
				System.out.println("EvTorProcDao의  getbuyCustomList" + (rs.getInt("pmc_idx")));
				
				if(rs.getString("pmc_tp1")!=null && !rs.getString("pmc_tp1").equals("")) {
					String tpname = getToppingName(rs.getString("pmc_tp1"));
					pc.setTpname1(tpname); 
				}
				if(rs.getString("pmc_tp2")!=null && !rs.getString("pmc_tp2").equals("")) {
					String tpname = getToppingName(rs.getString("pmc_tp2"));
					pc.setTpname2(tpname);
				}
					
				customList.add(pc);
			}

		}catch(Exception e){
			System.out.println("EvTorProcDao의  getbuyCustomList메소드에서오류발생");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt);
		}
		return customList;	
	}

	public String getToppingName(String tpid) {		
		//토핑 아이디를 가져와서 토핑 이름을 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		String tpname = "";
		try {
			String sql = "select pmt_name from t_product_ma_topping where "
					+ "pmt_id = '"+ tpid+ "'";
		
			stmt = conn.createStatement();
			System.out.println(sql + "EvTorProcDao:getToppingName");
			rs = stmt.executeQuery(sql); 
			//한 개 니까 루프 안돌려도 됨 
			if(rs.next()) {
				tpname = rs.getString(1);		
			}
		
		}catch(Exception e) {
			System.out.println("EvTorProcDao:getToppingName()오류");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}		
		return tpname;

	}
	public int torInsert(String miid, EvCusTor ect){
		//토너먼트 글을 등록하는 메소드
		int result = 0;
		Statement stmt = null;
		try {
			String sql = "insert into t_ev_cus_tor(pmc_idx, mi_id, ect_img1, ect_title, "
					+ " ect_content ) values ( "
					+ ect.getPmc_idx()
					+ ",'"
					+ miid
					+ "', '"
					+ ect.getEct_img1()
					+ "', '"
					+ ect.getEct_title()
					+ "'  , '"
					+ ect.getEct_content()
					+ "')";
					
			System.out.println(sql + "EvTorProcDao:torInsert");	
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		
		}catch(Exception e) {
			System.out.println("EvTorProcDao:torInsert()오류");
			e.printStackTrace();
		}finally {
			close(stmt); 
		}		
		return result;

	}
	
	public ProductCustom getCustomInfo(int pcidx) {
		//회원이 클릭한 커스텀마카롱의 상세뷰(커스텀마카롱의 모든 정보, 맛, 토핑맛)을 가져오는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		ProductCustom pc = null;
		try {
			String sql = "select a.*, b.pi_name, b.pi_img1 "
					+ " from t_product_ma_custom a, t_product_info b "
					+ " where a.pi_id = b.pi_id and pmc_idx=" + pcidx;
		
			stmt = conn.createStatement();
			System.out.println(sql + "ProductCustomDao:getCustomInfo");
			rs = stmt.executeQuery(sql); 
			//한 개 니까 루프 안돌려도 됨 
			if(rs.next()) {
				pc = new ProductCustom();
				
				pc.setPmc_idx(pcidx); //커스텀 마카롱 인덱스
				pc.setPi_name(rs.getString("pi_name")); //맛이름
				pc.setPi_img1(rs.getString("pi_img1")); //맛이미지
				pc.setPi_id(rs.getString("pi_id")); //맛상품아이디
				pc.setPmc_name(rs.getString("pmc_name")); //사용자설정제목
				pc.setPmc_sugar(rs.getInt("pmc_sugar"));
				pc.setPmc_vg(rs.getString("pmc_vg"));
				pc.setPmc_pl(rs.getString("pmc_pl"));
				pc.setPmc_tp1(rs.getString("pmc_tp1"));
				pc.setPmc_tp2(rs.getString("pmc_tp2"));
				pc.setPmc_date(rs.getString("pmc_date"));
				pc.setPmc_price(rs.getInt("pmc_price"));
				pc.setPmc_isview(rs.getString("pmc_isview"));
				pc.setPmc_isbuy(rs.getString("pmc_isbuy"));		
				pc.setPmc_img(rs.getString("pmc_img"));	
				
				if(rs.getString("pmc_tp1")!=null && !rs.getString("pmc_tp1").equals("")) {
					String tpname = getToppingName(rs.getString("pmc_tp1"));
					pc.setTpname1(tpname); 
				}
				if(rs.getString("pmc_tp2")!=null && !rs.getString("pmc_tp2").equals("")) {
					String tpname = getToppingName(rs.getString("pmc_tp2"));
					pc.setTpname2(tpname);
				}
			}
		
		}catch(Exception e) {
			System.out.println("ProductCustomDao:getCustomInfo()오류");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}		
		return pc;
	}
	
	
	
	
	
	
	
	
	
}