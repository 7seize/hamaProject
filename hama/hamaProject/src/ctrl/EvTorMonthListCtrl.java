package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import java.util.*;
import vo.*;
import java.net.*;

// �ſ� ����Ǵ� ��ʸ�Ʈ �̺�Ʈ ���
@WebServlet("/ev_tor_month_list")
public class EvTorMonthListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EvTorMonthListCtrl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0, spage = 0;
		// ���� ������ ��ȣ, ������ũ��, ���ũ��, ���ڵ�(�Խñ�)����, ����������, ���������� ����
		// ������ ������

		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
			// cpage���� ������ �޾Ƽ� int������ ����ȯ ��Ŵ
			// cpage�� String���� �ް� �ٴҰ����� ������� �ؾ��ϱ� ������ int����ȯ
			// ���������1. ���Ȼ�����.2
		}

		EvTorMonthListSvc evTorMonthListSvc = new EvTorMonthListSvc();

		// ��ī�� Ŀ���� ���
		ArrayList<ProductCustom> customList = evTorMonthListSvc.getCustomList(cpage, psize);
		// �����ؼ� ����ó�� ����ϱ�
		// �ش� �������� cpage, psize�� �˾ƾ� �� �������� �� �� ����
		// ���ȭ�鿡�� ������ �Խñ� ����� ArrayList<BbsFree>������ �޾ƿ�
		// �ʿ��� ��ŭ�� �޾ƿ������� cpage�� psize�� �ʿ���

		PageInfo pageInfo = new PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);
		pageInfo.setPcnt(pcnt);
		pageInfo.setPsize(psize);
		pageInfo.setRcnt(rcnt);
		pageInfo.setSpage(spage);
		// ����¡�� �˻��� �ʿ��� �������� PageInfo�� �ν��Ͻ��� ����

		request.setAttribute("customList", customList);
		request.setAttribute("pageInfo", pageInfo);

		RequestDispatcher dispatcher = request.getRequestDispatcher("bbs/free_list.jsp");
		dispatcher.forward(request, response);
	}
}
