package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import java.util.*;
import vo.*;
import java.net.*;
import java.time.*;



@WebServlet("/ev_tor_good")// 투표하기 기능을 위한 컨트롤
public class EvTorGoodCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public EvTorGoodCtrl() {  super(); }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//String good = request.getParameter("good");
	//	EvCusTorPoll ecpt = (EvCusTorPoll)request.getAttribute("ecpt");
	//	LocalDate today = LocalDate.now();
		
		int ectidx = Integer.parseInt(request.getParameter("ectidx")); // 토너먼트 글 번호 가져오기
		//int pmcidx = Integer.parseInt(request.getParameter("pmcidx")); //커스텀인덱스 가져와야하고
		System.out.println("여기까지 작동?1");
		
	//	 int year = today.getYear();
	//	 int month = today.getMonthValue();
		
	
		//로그인체크  //로그인 인포만 가져오면 되고 세션에 있는거 뽑아오기 
		HttpSession session = request.getSession();
		MemberInfo mi = (MemberInfo)session.getAttribute("loginInfo");
		String miid = mi.getMi_id();
		
		//프린터만들기
		/*
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		//로그인이 안된 상태이면 튕기기
		if(mi == null) {
			out.println("<script>");
			out.println("alert('잘못된 경로로 들어오셨습니다. 로그인하세요.');");
			out.println("</script>");
			out.close();
		}
		*/
		
		int result = 0;
		EvCusTorPoll ectp = new EvCusTorPoll();
		// 어떤 글에 투표했다랑 날짜만 가져가야 함(로그인은 세션)
		// 검사해야되는게
		// 유저가 이번 달에 투표를 했는지
		// 투표여부 검사 만약 했으면 alert(히스토리 백) result ==1
		//가져갸야 함 :연월 int,  회원아이디, 커스텀 인덱스 //셀렉트 조건 where mi_id -> 레코드개수 
		// 투표 일 ecpt_date (연월) -> 레코드 개수 reuslt =1 return result; 이미한 투표니까 안됨
		/*
		if(request.getParameter("ectpidx") != null) {
			int ectidx = Integer.parseInt(request.getParameter("ectidx"));
			int pmcidx = Integer.parseInt(request.getParameter("pmcidx"));
			ectp.setEctp_idx(ectpidx);
			ectp.setMi_id(mi.getMi_id());
			ectp.setEct_idx(ectidx);
		//	ectp.setEctp_date(ectp_date);
		}
		//EvCusTorPoll형 인스턴스에 담기 
		*/
		// evTorViewSvc에 didvote() 메소드 
		EvTorViewSvc evTorViewSvc = new EvTorViewSvc();
		result = evTorViewSvc.voteBtn(ectidx,miid);
		System.out.println("여기까지 작동?");
		System.out.println(result);
		
		//################################
		/*
		if( result == 0) { // 투표했는지 검사 이미 투표를 했을 경우
			out.println("<script>");
			out.println("alert('이미 투표하셨습니다.');");
			out.println("location.href('history.back()');");
			out.println("</script>");
			out.close();	
		}
		if( result == 0) { // 투표를 안했을 경우
			
			request.setAttribute("ectidx", ectidx); 
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher("event/ev_tor_list.jsp"); // 투표한 내용을 담고 토너먼트 리스트로 보내기
			dispatcher.forward(request, response);
		}
		

		
	
		result = evTorViewSvc.dovote( miid,  ectidx ); // 커스텀 토너먼트 투표 테이블에 insert(토너먼트 글 번호 ) 할것 // 득표수..?
		String sql = "insert into where mi_id =  " + miid ;	// 
		
		
		
		result = evTorViewSvc.updatevote(  ectidx ); //2 득표수 업데이트 커스텀토너먼트 글번호
		 sql = " update t_ev_cus_tor set ect_vote = ect_vote + 1 "; // 득표수 업데이트
		
		result += sql;

		//서비스 만들기 (insert와 update 할 것임) 
		 result = evTorViewSvc.voteBtn(ectp);
		 return result;
		 */
		//################################
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
		//ev_tor_view.jsp의  [voteBtn()] ajax 로 갈 것임  

	}
	
}

