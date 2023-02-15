package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.*;
import vo.*;
import svc.*;

public class EvTorProcDao {
	// ��ʸ�Ʈ �Խ��� ���� �����۾�(���, ���, ����)���� ��� ó���ϴ� Ŭ����
	private static EvTorProcDao evTorProcDao;
	private Connection conn;

	private EvTorProcDao() {
	} // �⺻������

	public static EvTorProcDao getInstance() {
		if (evTorProcDao == null)
			evTorProcDao = new EvTorProcDao();
		// �̹� ������ freeProcDao Ŭ������ �ν��Ͻ��� ������ ���Ӱ� �ν��Ͻ��� �����϶�
		return evTorProcDao;
	}

	public void setConnection(Connection conn) {
		// �� Dao Ŭ�������� ����� Ŀ�ؼ� ��ü�� �޾ƿͼ� �������ִ� �޼ҵ�
		this.conn = conn;
	}

	public int getTorListCount(String where) {
		// �����Խ����� �˻��� ����� ���ڵ�(�Խñ�) ������ �����ϴ� �޼ҵ�
		int rcnt = 0;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select count(*) from t_ev_cus_tor " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next())
				rcnt = rs.getInt(1);
			// �÷��� ��� �ε��� ��ȣ �׳� ��
			// select count�ϴ� �ſ��� ��� �˻� ���ص� ��.

		} catch (Exception e) {
			System.out.println("EvTorProcDaoŬ����:getTorListCount()����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}

		return rcnt;
	}

	public ArrayList<EvCusTor> getTorList(String kind, int cpage, int psize, String orderBy) {
		// ��ʸ�Ʈ ����� ArrayList�� �����ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<EvCusTor> torList = new ArrayList<EvCusTor>();
		// �׳� �����ص� ��
		EvCusTor ect = null;
		// torList�� ������ �ϳ��� �Խñ� ������ ���� �ν��Ͻ�
		String where = ""; // where ���� kina=a or b�� ��� ���� �ٸ���
		String orderby = orderBy;// ���ĵ� �ٸ���
		try {
			/*
			 * ����Ʈ�� �ƴ� String sql =
			 * "select a.pmc_idx, a.pmc_sugar, a.pmc_vg, a.pmc_pl, a.pi_id, " +
			 * " a.pmc_tp1, a.pmc_tp2, a.pmc_img, b.ect_idx, b.ect_vote, b.ect_title, b.ect_content "
			 * + " from t_product_ma_custom a, t_ev_cus_tor b where a.pmc_idx = b.pmc_idx "
			 * + " and b.ect_isview = 'y' " + "and a.pmc_isview='y' " +
			 * " and a.pmc_isbuy='y';";
			 * 
			 */
			// torList���� ������ ���
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
			// �� ���� �ƴϴϱ� ����������
			while (rs.next()) {
				ect = new EvCusTor();
				ect.setPmc_idx(rs.getInt("pmc_idx"));
				ect.setEct_idx(rs.getInt("ect_idx"));
				ect.setEct_vote(rs.getInt("ect_vote"));// �α������ �ڵ����� �ϱ����� �ʿ�
				ect.setEct_img1(rs.getString("ect_img1"));
				ect.setEct_title(rs.getString("ect_title"));
				ect.setEct_date(rs.getString("ect_date"));
				torList.add(ect);
			}

		} catch (Exception e) {
			System.out.println("EvTorProcDao : getTorList()�޼ҵ���� ");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}

		return torList;
	}

	public EvCusTor getEvCusTorInfo(int ectidx) {
		// ������ �Խñ��� �������� EvCusTor�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		EvCusTor ect = null;

		try {
			String sql = "select * from t_ev_cus_tor where ect_isview = 'y' and ect_idx=" + ectidx;

			stmt = conn.createStatement();
			System.out.println(sql + ":EvTorProcDao :getEvCusTorInfo()");
			rs = stmt.executeQuery(sql);
			// �� �Խñۿ��� ������ �׸��
			// �� �� �ϱ� ���� �ȵ����� ��
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
			System.out.println("EvTorProcDaoŬ����:getEvCusTorInfo()����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return ect;
	}
	public int voteBtn(EvCusTorPoll ectp) {
		// ������ �Խù��� ��ǥ�ϱ�[���ƿ�� ����]�� ó���ϴ� �޼ҵ�
		// �̹� ��ǥ�� ������ 0, ��ǥ�� �ȵǾ����� 1��, ���� ó�� �Ǿ����� 2�� ����
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;

		try {
			stmt = conn.createStatement();

			String sql = "select * from t_ev_cus_tor_poll" + " where left(ectp_date , 4) = left(now() , 4) and mi_id ="
					+ ectp.getMi_id();
			// mi_id

			System.out.println(sql + ":EvTorProcDao: voteBtn ����Ʈ����");
			rs = stmt.executeQuery(sql);

			if (rs.next()) { // �̹� ��ǥ�� ������ result = 0;
				result = 0;
			} else {
				sql = "update t_ev_cus_tor set ect_vote = ect_vote + 1 ";

				if (result == 1) {
					sql += " ect_vote = ect_vote + 1 " + " where ectp_idx = " + ectp.getEctp_idx();
					;

					System.out.println(sql + ":EvTorProcDao: �ڱⰡ ���ϴ� �����ǿ� ��ǥ�ϴ� ����");
					result = stmt.executeUpdate(sql);
					// �����ǿ� ��ǥ�ϱ� ������Ʈ ����

					sql = "insert into t_ev_cus_tor_poll  " + " (ect_idx, pmc_idx, mi_id) values ('" + ectp.getEct_idx()
							+ "', '" + ectp.getPmc_idx() + "', '" + ectp.getMi_id() + "')";
					System.out.println(sql + ":EvTorProcDao:��ǥ�� ����� ����� ���� ���� ");
					result += stmt.executeUpdate(sql);
					// ��ǥ�ϱ� ���� �߰� ����
				}
			}
		} catch (Exception e) {
			System.out.println("EvTorProcDaoŬ����:voteBtn()����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return result;
	}


}