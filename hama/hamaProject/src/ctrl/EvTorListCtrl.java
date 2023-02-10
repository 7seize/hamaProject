package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import java.util.*;
import vo.*;
import java.net.*;

// 토너먼트 리스트 목록
@WebServlet("/ev_tor_list")
public class EvTorListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EvTorListCtrl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String kind = request.getParameter("kind"); // a, b\

		int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0, spage = 0;
		// 현재 페이지 번호, 페이지크기, 블록크기, 레코드(게시글)개수, 페이지개수, 시작페이지 등을
		// 저장할 변수들

		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
			// cpage값이 있으면 받아서 int형으로 형변환 시킴
			// cpage는 String으로 달고 다닐거지만 산술연산 해야하기 때문에 int형변환
			// 산술적이유1. 보안상이유.2
		}

		EvTorListSvc evTorListSvc = new EvTorListSvc();

		// 토너먼트 리스트
		ArrayList<EvCusTor> torList = evTorListSvc.getTorList(kind, cpage, psize);

		PageInfo pageInfo = new PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);
		pageInfo.setPcnt(pcnt);
		pageInfo.setPsize(psize);
		pageInfo.setRcnt(rcnt);
		pageInfo.setSpage(spage);
		// 페이징과 검색에 필요한 정보들을 PageInfo형 인스턴스에 저장

		request.setAttribute("torList", torList);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("kind", kind);

		RequestDispatcher dispatcher = request.getRequestDispatcher("event/ev_tor_list.jsp");
		dispatcher.forward(request, response);
	}
}
