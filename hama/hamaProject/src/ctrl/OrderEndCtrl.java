package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;


@WebServlet("/order_end")
public class OrderEndCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public OrderEndCtrl() { super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String oiid = request.getParameter("oiid"); //雅뚯눖揆甕곕뜇�깈 
			
		HttpSession session = request.getSession();
		MemberInfo mi = (MemberInfo)session.getAttribute("loginInfo");
		String miid = mi.getMi_id();
		
		OrderEndSvc orderEndSvc = new OrderEndSvc();
		OrderInfo orderInfo = orderEndSvc.getOrderInfo(miid, oiid);
		
		if(mi == null) {
	         response.setContentType("text/html; charset=utf-8");
	         PrintWriter out = response.getWriter();

	         out.println("<script>");
	         out.println("alert('로그인 이후 사용하실 수 있습니다..');");
	         out.println("location.replace('login_form.jsp?url=order_end');");
	         //out.println("location.replace();");
	         out.println("</script>");
	         out.close();
	     
		}else{ 
			
			request.setAttribute("orderInfo", orderInfo);
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher("order/order_end.jsp");
			dispatcher.forward(request, response);
		}
		
		
	}

}
