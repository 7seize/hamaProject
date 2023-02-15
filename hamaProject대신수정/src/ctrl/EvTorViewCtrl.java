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
	//	ArrayList<EvCusTor> torList = (ArrayList<EvCusTor>)request.getAttribute("torList");
			
	
		//System.out.println("ectpidx : " + request.getParameter("ectpidx"));
		System.out.println("ectidx : " + request.getParameter("ectidx"));
		System.out.println("pmcidx : " + request.getParameter("pmcidx"));
		
		
		int ectidx = Integer.parseInt(request.getParameter("ectidx")); // 토너먼트 글 번호
		// 
		// 글 번호를 알아야 상세보기
		//System.out.println("test1");
		int cpage = 1, psize=10, bsize=10, rcnt=0, pcnt=0, spage=0;
		//현재 페이지 번호, 페이지크기, 블록크기, 레코드(게시글)개수, 페이지개수, 시작페이지 등을
		//저장할 변수들 
		
		if(request.getParameter("cpage")!= null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
			//cpage값이 있으면 받아서 int형으로 형변환 시킴 
			//cpage는 String으로 달고 다닐거지만 산술연산 해야하기 때문에 int형변환
			//산술적이유1. 보안상이유.2	
		}
		// 로그인체크 //로그인 인포만 가져오면 되고 세션에 있는거 뽑아오기
		HttpSession session = request.getSession();
		MemberInfo mi = (MemberInfo) session.getAttribute("loginInfo");
	
		// 프린터만들기
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		// 로그인이 안된 상태이면 튕기기
		if (mi == null) {
			out.println("<script>");
			out.println("alert('잘못된 경로로 들어오셨습니다. 로그인하세요.');");
			out.println("</script>");
			out.close();
		}
		
		int result = 0;
		EvTorViewSvc evTorViewSvc = new EvTorViewSvc();
	
		EvCusTorPoll ectp = new EvCusTorPoll();
		
		if(request.getParameter("ectpidx") != null) {
			int ectpidx = Integer.parseInt(request.getParameter("ectpidx")); 
			int pmcidx = Integer.parseInt(request.getParameter("pmcidx"));
			
			ectp.setEctp_idx(ectpidx);
			ectp.setEct_idx(ectidx);
			ectp.setMi_id(mi.getMi_id());
			//ectp.setPmc_idx(pmcidx);
		// EvCusTorPoll형 인스턴스에 담기
			result = evTorViewSvc.voteBtn(ectp);
		}
		
		// 보여줄 게시물에 관한 것들임
		EvCusTor ect = new EvCusTor();
		ect = evTorViewSvc.getEvCusTorInfo(ectidx);

		// 형식의 인스턴스로 받아올 것임
		// 사용자가 선택한 게시글의 내용들을 EvCusTor 형 인스턴스로 받아옴

		if (ect == null) { // 글목록에서 글을 클릭했으니 보여줘야하는데 null일수가없음 만약그런다면
			// 보여줄 게시글이 없으면
			response.setContentType("text/html; charset=utf-8");

			out.println("<script>");
			out.println("alert('로그인 후 사용하실수있습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();

		} else {
			// 보여줄 게시글이 있으면
			PageInfo pageInfo = new PageInfo();
			pageInfo.setBsize(bsize);
			pageInfo.setCpage(cpage);
			pageInfo.setPcnt(pcnt);
			pageInfo.setPsize(psize);
			pageInfo.setRcnt(rcnt);
			pageInfo.setSpage(spage);

			System.out.println("여기까지 확인");
			
			request.setAttribute("pageInfo", pageInfo); 
			request.setAttribute("ect", ect);// 게시글보여주고
			System.out.println();
			// 뒤로가기했을떄 페이지 기록용
			
		/*	if(request.getParameter("ectpidx") != null) {
				request.setAttribute("ectp", ectp);
			}
		*/
			RequestDispatcher dispatcher = request.getRequestDispatcher("event/ev_tor_view.jsp");
			dispatcher.forward(request, response);
			
		}	
		
		out.println(result);
		// ev_tor_view ajax로 이동
	}	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request ,response );
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request ,response );
	}


}
