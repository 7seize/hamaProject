package ctrl;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/product_custom_form")
public class ProductCustomFormCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductCustomFormCtrl() { super(); }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		//1. 로그인검사
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");	
		if(loginInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 이후 사용하실 수 있습니다.');");
			out.println("location.replace('login_form.jsp?url=product_custom_form');");
			out.println("</script>");
			out.close();
		}
		String miid = loginInfo.getMi_id();
		
				
		//2.커스텀마카롱(view=y)인거 아이디로 개수검사 10개미만이면 history.back
		ProductCustomSvc productCustomSvc = new ProductCustomSvc();
		int cnt = productCustomSvc.countCustom(miid);
		if(cnt>=10) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('마카롱 레시피는 10개까지만 만들 수 있습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
			
		
		//3-1 토핑리스트가져오기 아이디,이름,인덱스 (+게시여부조건)		
		ArrayList<ProductTopping> toppingList = 
				productCustomSvc.getToppingList();
		request.setAttribute("toppingList", toppingList);
		
		
		//3-2 마카롱을 리스트 형식으로 가져오기	
		ArrayList<ProductInfo> macList = 
				productCustomSvc.getMacList();
		request.setAttribute("macList", macList);
		
		
		//4. 10개 미만이라면 커스텀 마카롱 만드는 폼으로 보내기
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/product/product_custom_form.jsp");
		dispatcher.forward(request, response);


	
	}

}
