package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/ev_tor_view")
public class EvTorViewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public EvTorViewCtrl() {super();}
	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		//PageInfo pageInfo	= (PageInfo)request.getAttribute("pageInfo"); 
		//EvCusTorPoll ecpt = (EvCusTorPoll) request.getAttribute("ectp");// 토너먼트 투표 받아오기
		// EvCusTor ect = (EvCusTor) request.getAttribute("ect");//토너먼트 리스트 받아오기
		//String good = request.getParameter("good"); 	// view.jsp의 ajax :투표하기 기능 -> 별도의 ctrl 만들어서
		//ArrayList<EvCusTor> torList = (ArrayList<EvCusTor>)request.getAttribute("torList");
			
	
		//System.out.println("ectpidx : " + request.getParameter("ectpidx"));
		
		
		
		
		//할 일  :  글번호와 커스텀 번호를 가져가, 글번호에 해당하는 걸 보여주고, 커스텀인덱스 가져가 세부사항 보여주고
		//뷰로 넘긴다. 
		String kind = request.getParameter("kind");
		int ectidx = Integer.parseInt(request.getParameter("ectidx")); // 토너먼트 글 번호
		int pmcidx= Integer.parseInt(request.getParameter("pmcidx")); // 커스텀 마카롱 인덱스 
		
		
		//단순 뷰여서 로그인 검사 안해도 됨 
		
	
		
		// 보여줄 게시물 (제목, 내용)
		EvTorViewSvc evTorViewSvc = new EvTorViewSvc();
		EvCusTor ect = new EvCusTor();
		ect = evTorViewSvc.getEvCusTorInfo(ectidx);
		
		// 보여줄 게시물 (커스텀 마카롱 정보)
		ProductCustom pc = new ProductCustom();
		pc = evTorViewSvc.getCustomInfo(pmcidx);	

		if (ect == null || pc == null) { // 글목록에서 글을 클릭했으니 보여줘야하는데 null일수가없음 만약그런다면
			// 보여줄 게시글이 없으면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('잘못된 경로로 들어오셨습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();

		} else {

			request.setAttribute("ect", ect);// 게시글보여주고
			request.setAttribute("pc", pc); //커스텀마카롱 보여주고

			
			
			System.out.println(pc.getTpname1());
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("event/ev_tor_view.jsp");
			dispatcher.forward(request, response);
			
		}	
		
		
	}	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request ,response );
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request ,response );
	}


}
