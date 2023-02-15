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
		// 자유게시판의 검색된 결과의 레코드(게시글) 개수를 리턴하는 메소드
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

	public ArrayList<EvCusTor> getTorList(String kind, int cpage, int psize, String orderBy) {
		// 토너먼트 목록을 ArrayList로 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<EvCusTor> torList = new ArrayList<EvCusTor>();
		// 그냥 생성해도 됨
		EvCusTor ect = null;
		// torList에 저장할 하나의 게시글 정보를 담을 인스턴스
		String where = ""; // where 절에 kina=a or b일 경우 조건 다르게
		String orderby = orderBy;// 정렬도 다르게
		try {
			/*
			 * 리스트가 아닌 String sql =
			 * "select a.pmc_idx, a.pmc_sugar, a.pmc_vg, a.pmc_pl, a.pi_id, " +
			 * " a.pmc_tp1, a.pmc_tp2, a.pmc_img, b.ect_idx, b.ect_vote, b.ect_title, b.ect_content "
			 * + " from t_product_ma_custom a, t_ev_cus_tor b where a.pmc_idx = b.pmc_idx "
			 * + " and b.ect_isview = 'y' " + "and a.pmc_isview='y' " +
			 * " and a.pmc_isbuy='y';";
			 * 
			 */
			// torList에서 보여질 목록
			String sql = "select a.*, b.* " + " from t_product_ma_custom a,  t_ev_cus_tor b ";

			// kind=a
			if (kind != null && kind.equals("a")) {
				where = " where a.mi_id = b.mi_id and a.pmc_idx = b.pmc_idx and a.pmc_isview='y' "
						+ " and a.pmc_isbuy='y'";
			} else {// kind != a
				where = " where a.mi_id = b.mi_id and a.pmc_idx = b.pmc_idx and a.pmc_isview='y' "
						+ " and a.pmc_isbuy='n'";
			}

			sql += where + orderby ;

			stmt = conn.createStatement();

			System.out.println(sql);

			rs = stmt.executeQuery(sql);
			// 한 개가 아니니까 루프돌리기
			while (rs.next()) {
				ect = new EvCusTor();
				ect.setPmc_idx(rs.getInt("pmc_idx"));
				ect.setEct_idx(rs.getInt("ect_idx"));
				ect.setEct_vote(rs.getInt("ect_vote"));// 인기순으로 자동정렬 하기위해 필요
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
	public int voteBtn(EvCusTorPoll ectp) {
		// 지정한 게시물에 투표하기[좋아요와 유사]를 처리하는 메소드
		// 이미 투표를 했으면 0, 투표가 안되었을면 1을, 정상 처리 되었으면 2를 리턴
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;

		try {
			stmt = conn.createStatement();

			String sql = "select * from t_ev_cus_tor_poll" + " where left(ectp_date , 4) = left(now() , 4) and mi_id ="
					+ ectp.getMi_id();
			// mi_id

			System.out.println(sql + ":EvTorProcDao: voteBtn 셀렉트쿼리");
			rs = stmt.executeQuery(sql);

			if (rs.next()) { // 이미 투표를 했으면 result = 0;
				result = 0;
			} else {
				sql = "update t_ev_cus_tor set ect_vote = ect_vote + 1 ";

				if (result == 1) {
					sql += " ect_vote = ect_vote + 1 " + " where ectp_idx = " + ectp.getEctp_idx();
					;

					System.out.println(sql + ":EvTorProcDao: 자기가 원하는 레시피에 투표하는 쿼리");
					result = stmt.executeUpdate(sql);
					// 레시피에 투표하기 업데이트 쿼리

					sql = "insert into t_ev_cus_tor_poll  " + " (ect_idx, pmc_idx, mi_id) values ('" + ectp.getEct_idx()
							+ "', '" + ectp.getPmc_idx() + "', '" + ectp.getMi_id() + "')";
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


}