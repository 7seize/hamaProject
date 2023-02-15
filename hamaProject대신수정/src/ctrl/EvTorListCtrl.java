package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import java.util.*;
import vo.*;
import java.net.*;

// ��ʸ�Ʈ ����Ʈ ���
@WebServlet("/ev_tor_list")
public class EvTorListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public EvTorListCtrl() {super();}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request , response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		doProcess(request , response);
		}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String kind = request.getParameter("kind"); // a �϶� ��ǥ�Ϸ� ����, b�϶� ������ �����ϱ�
		String where = " where ect_isview='n' ";     

		int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0, spage = 0;
		// ���� ������ ��ȣ, ������ũ��, ���ũ��, ���ڵ�(�Խñ�)����, ����������, ���������� ����
		// ������ ������

		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
			// cpage���� ������ �޾Ƽ� int������ ����ȯ ��Ŵ
			// cpage�� String���� �ް� �ٴҰ����� ������� �ؾ��ϱ� ������ int����ȯ
			// ���������1. ���Ȼ�����.2
		}
		//���������� ��ǥ��(ect_vote), �ֱټ� (ect_date)
		String orderBy = ""; 
		String o = request.getParameter("o");
		if (o == null || o.equals(""))	o = "a"; 

		switch (o) {
		case "a" :	// ��ǥ����
			orderBy = " order by ect_vote desc";	break;
		case "b" :	// ��� ����
			orderBy = " order by ect_date desc";	break;
		}
		

		EvTorListSvc evTorListSvc = new EvTorListSvc();
		rcnt = evTorListSvc.getTorListCount(where);
		// ��ʸ�Ʈ ����Ʈ
		ArrayList<EvCusTor> torList = evTorListSvc.getTorList(kind,  cpage, psize, orderBy);

		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);
		pageInfo.setPcnt(pcnt);
		pageInfo.setPsize(psize);
		pageInfo.setRcnt(rcnt);
		pageInfo.setSpage(spage);
		pageInfo.setO(o);
		// ����¡�� �˻��� �ʿ��� �������� PageInfo�� �ν��Ͻ��� ����
		
		
		request.setAttribute("torList", torList);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("kind", kind);

		
		RequestDispatcher dispatcher = request.getRequestDispatcher("event/ev_tor_list.jsp");
		dispatcher.forward(request, response);
		
		
	}
}
