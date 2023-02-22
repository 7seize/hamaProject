package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/product_custom_list")
public class ProductCustomListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductCustomListCtrl() { super(); }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		//세션 가져와서 로그인 안되어있으면 로그인 하게 한 뒤에 ctrl로 다시 돌아오게 함 
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		if(loginInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 후 사용하실수있습니다.');");
			out.println("location.replace('login_form.jsp?url=product_custom_list');");
			out.println("</script>");
			out.close();	
		}

		String miid = loginInfo.getMi_id(); //세션의 아이디 뽑아와서
	
		
		ProductCustomSvc productCustomSvc = new ProductCustomSvc();
		ArrayList<ProductCustom> customList = productCustomSvc.getCustomList(miid);
		//해당 아이디인 회원이 만들어둔 커스텀 리스트 가져오기 	
		
		request.setAttribute("customList", customList);
		//어레이리스트 customList에 담아서 리퀘스트에 담아 보내준다. jsp뷰로 볼 수 있게 포워드로 보냄. 그러니까 리퀘스트에 담는다.
		//sendRedirect로 보내면 url이 보내감.파일자체가 확 넘어가니까 request를 안가져감
		//sendRedirect는 url에 쿼리스트링으로 담아가는데, 어레이리스트를 쿼리스트링으로 가져갈 수 없으니 디스패처를 이용해서 이동한다. 
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("product/product_custom_list.jsp");
		dispatcher.forward(request,response);
		
		
	}

}
