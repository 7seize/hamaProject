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
			out.println("alert('로그인 후 사용하실수있습니다.');");
			out.println("location.replace('login_form.jsp?url=cart_view');");
			out.println("</script>");
			out.close();
			
		}
		
		String miid = loginInfo.getMi_id();
		CartViewSvc cartViewSvc= new CartViewSvc();
		ArrayList<OrderCart> cartList = cartViewSvc.getCartList(miid);
		//장바구니페이지에서 보여줄 장바구니에 담긴 상품정보들을 가져온다.
		
		request.setAttribute("cartList", cartList);
		//어레이리스트 카트 리스트에 담아서 리퀘스트에 담아 보내준다. jsp뷰로 볼 수 있게 
		//포워드로 보냄. 그러니까 리퀘스트에 담는다.
		// sendRedirect로 보내면 url이 보내감.파일자체가 확 넘어가니까 request를 안가져감
		//sendRedirect는 url에 쿼리스트링으로 담아가는데, 어레이리스트를 쿼리스트링으로
		//가져갈 수 없으니 디스패처를 이용해서 이동한다. 
		
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("order/cart_view.jsp");
		dispatcher.forward(request,response);
	
		
	}

}
