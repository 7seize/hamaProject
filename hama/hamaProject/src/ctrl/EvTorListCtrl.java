package ctrl;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import java.util.*;
import vo.*;
import java.net.*;
import java.time.LocalDate;

// 토너먼트 리스트 목록
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
		
		String kind = request.getParameter("kind"); 
		
		
		String where = " where ect_isview='y' ";   //게시글 보이는지 안보이는지 용도()-둘 다 해당되는 조건

		
		//날짜도 가져가야함(날짜로 a와 b일 때 구분해줄 변수)
		LocalDate today = LocalDate.now();
		int	cYear = today.getYear(); // 현재 연도
		int cMonth = today.getMonthValue();// 현재 월 월
		String month = "";
		if(cMonth<10) {
			month = "0"+Integer.toString(cMonth);
		}else {
			month = Integer.toString(cMonth);
		}
		String date = Integer.toString(cYear)+"-"+month; 
		System.out.println(date+"EvTorListCtrl");//2023-02 이렇게 나옴
		
		
		
		//정렬조건은 득표순(ect_vote), 최근순 (ect_date)
		String orderBy = ""; 
		String o = request.getParameter("o");
		if (o == null || o.equals(""))	o = "a"; 

		switch (o) {
		case "a" :	// 득표수순
			orderBy = " order by ect_vote desc ";	break;
		case "b" :	// 최신순
			orderBy = " order by ect_date desc ";	break;
	}
		

		EvTorListSvc evTorListSvc = new EvTorListSvc();
		//rcnt = evTorListSvc.getTorListCount(where); rcnt버림 페이징버리라고함
		
		//토너먼트 리스트
		ArrayList<EvCusTor> torList = evTorListSvc.getTorList(kind, date, orderBy);

		request.setAttribute("torList", torList);

		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("event/ev_tor_list.jsp");
		dispatcher.forward(request, response);
		
		
	}
}
