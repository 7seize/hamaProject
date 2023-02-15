package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/cart_proc_up")
public class CartProcUpCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CartProcUpCtrl() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//캐릭터 인코딩
		int ocidx = Integer.parseInt(request.getParameter("ocidx"));
		int cnt = Integer.parseInt(request.getParameter("cnt"));
		//opt : 변경할 옵션 인덱스 번호, cnt : 변경할 수량 
		//셋 다 int고 cnt는 0 넣어줫으니 int로 받아오기 
		
		//vo패키지 import해서 세션 로그인 기록 가져오기 
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		if(loginInfo == null) { // 로그인 안되어있으면 튕기게 
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 이후 사용하실 수 있습니다.');");
			//out.println("location.replace();");
			out.println("</script>");
			out.close();
		}
		String miid = loginInfo.getMi_id();
		
		OrderCart oc = new OrderCart(); 
		oc.setOc_idx(ocidx);
		oc.setMi_id(miid);
		oc.setOc_cnt(cnt);
		
		CartProcUpSvc cartProcUpSvc = new CartProcUpSvc();
		int result = cartProcUpSvc.cartUpdate(oc);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
		//result 출력해주기>그럼 ajax로 간다. ajax로 호출해준거여서 reuslt값만 찍어주기.
	}

}
