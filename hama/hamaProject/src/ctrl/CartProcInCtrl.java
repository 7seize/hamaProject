package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;


@WebServlet("/cart_proc_in")
public class CartProcInCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CartProcInCtrl() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String boxItem = request.getParameter("arr");
		String custom = request.getParameter("custombox");
		String piid = request.getParameter("piid");
		String boxsize = request.getParameter("boxsize");
		
		System.out.println("박스 보낸 거" + boxItem);
		System.out.println(custom);
		System.out.println(boxsize);
		int cnt = 0;
		if(boxItem != null) {
			cnt = 1;
			if(boxsize.equals("5")) {
				piid = "cb101";
			}else {
				piid = "cb102";
			}

		}else {
			cnt = Integer.parseInt(request.getParameter("cnt"));
		}
		
				
		
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		
		
		
		if(loginInfo == null) {
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
		oc.setMi_id(miid);
		oc.setPi_id(piid);
		oc.setOc_cnt(cnt);
		oc.setOc_box(boxItem);
		oc.setOc_pmc_idx(custom);
		
		
		CartProcInSvc cartProcInSvc = new CartProcInSvc();
		
		int result = cartProcInSvc.cartInsert(oc);
		
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
	}

}
