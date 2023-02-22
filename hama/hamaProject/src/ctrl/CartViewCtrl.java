package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/cart_view")
public class CartViewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public CartViewCtrl() {super();}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		if(loginInfo == null) {
	         response.setContentType("text/html; charset=utf-8");
	         PrintWriter out = response.getWriter();
	         out.println("<script>");
	         out.println("alert('로그인 이후 사용하실 수 있습니다.');");
	         out.println("location.replace('login_form.jsp?url=cart_view');");
	         //out.println("location.replace();");
	         out.println("</script>");
	         out.close();
	      }
		
		String miid = loginInfo.getMi_id();
		CartViewSvc cartViewSvc= new CartViewSvc();
		ArrayList<OrderCart> cartList = cartViewSvc.getCartList(miid);
		//占쎌삢獄쏅떽�럡占쎈빍占쎈읂占쎌뵠筌욑옙占쎈퓠占쎄퐣 癰귣똻肉т빳占� 占쎌삢獄쏅떽�럡占쎈빍占쎈퓠 占쎈뼖疫뀐옙 占쎄맒占쎈�뱄옙�젟癰귣�諭억옙�뱽 揶쏉옙占쎌죬占쎌궔占쎈뼄.
		
		request.setAttribute("cartList", cartList);

		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("order/cart_view.jsp");
		dispatcher.forward(request,response);
	
		
	}

}
