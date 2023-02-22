package ctrl;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/ev_tor_form")
public class EvTorFormCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public EvTorFormCtrl() { super(); }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");	// 인코딩
    	
		//토너먼트 폼으로 연결해주는 컨트롤러
		//가져갈 것 폼으로 보내주되,selectbox로 뿌려줄 토너먼트로 올릴 수 있는 마카롱 커스텀 리스트(거기서 1택해서 올리는거)들을 
		//유저 아이디를 통해 가져와서 리스트 형태로 가져와주고 ev_tor_form.jsp로 보내줘야함
		
		//프린터
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		// 로그인검사
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");	
		if(loginInfo == null) {
			
			out.println("<script>");
			out.println("alert('로그인 이후 사용하실 수 있습니다.');");
			out.println("location.replace('login_form.jsp?url=ev_tor_form');");
			out.println("</script>");
			out.close();
		}
		String miid = loginInfo.getMi_id();
		
		
		
		//1. 얘가 폼을 제출한 적이 있는지를 검사
		//날짜 월 가져가서 일치하는 아이디가 있는지 보고
		//아이디가 있으면 안된다 ->이미 토너먼트 등록을 했고,다음달에 제출할 수 있다는걸 알림.
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
			
		EvTorFormSvc evTorFormSvc = new EvTorFormSvc();
		
		int result =  evTorFormSvc.didTor(miid, date);
		
		if(result!=0) { //0이 아니면 레시피 제출했기 때문에 돌려보내기
			out.println("<script>");
			out.println("alert('이번 달 토너먼트에 이미 레시피를 제출하셨습니다.');");
			out.println("location.replace('ev_tor_list?kind=a');");
			out.println("</script>");
			out.close();
		}
		
		
		
		
		//2. 등록하기로 가면, 커스텀마카롱 리스트를 가져가서 아이디를 셀렉트박스로 뿌려주고 폼에서
		//그리고 폼에서 커스텀마카롱아이디에 따른 걸 나오도록(이거 장바구니에서 주소 섹션)

		// 조건 ! 아이디로 검사, pmcidx가 토너먼트에 올라온적이 없어야하며/ 구매기록에 있어야함  join해야하네요?
		// 토핑 이름 가져가는 것도 아마 해야할텐데 잘 모르겠음 
	
		ArrayList<ProductCustom> customList = evTorFormSvc.getbuyCustomList(miid);
		request.setAttribute("customList", customList);
		
		

		if(customList.size()<=0) { //커스텀 리스트가 0보자 작다=구매한 커스텀마카롱리스트가없다<못올림
			out.println("<script>");
			out.println("alert('구매한 커스텀 마카롱만 토너먼트에 등록할 수 있으며, \n 커스텀마카롱 하나 당 한 번만"
					+ " 후보에 등록할 수 있습니다.');");
			out.println("location.replace('ev_tor_list?kind=a');");
			out.println("</script>");
			out.close();
		}
		
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/event/ev_tor_form.jsp");
				dispatcher.forward(request, response);
		
		
		
	}

}
