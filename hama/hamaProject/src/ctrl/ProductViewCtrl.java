package ctrl;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.websocket.SendResult;

import svc.*;
import vo.*;

@WebServlet("/product_view")
public class ProductViewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductViewCtrl() {super();}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String piid = request.getParameter("piid"); //상품아이디

		ProductViewSvc productViewSvc = new ProductViewSvc();

		int result = productViewSvc.readUpdate(piid);	// 상품 조회수 증가
		ProductInfo pi = productViewSvc.getProductInfo(piid); //아이디로 상세페이지
		
		if (pi == null) { //검사 null이면 튕긴다. 
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('상품 정보가 없습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		request.setAttribute("pi", pi);
		
	
			//상품 아이디로 구분해서 보내는 서블릿/jsp파일이 다르다
		
			if(piid.equals("mc100")) {
				//커스텀마카롱리스트컨트롤서블릿을 호출
				response.sendRedirect("product_custom_list");
				
				
			}else if(piid.equals("cb101")||piid.equals("cb102")) { //5,10구성마카롱
				//래규님 setctrl로 보냄 
				int temp=5;
				if(piid.equals("cb102")) temp = 10;
				System.out.println(temp + ": productviewctrl");
	
				RequestDispatcher dispatcher = 
						request.getRequestDispatcher("productsetview?boxSize="+temp);
					dispatcher.forward(request, response);
				
			}else { //리스트에서 일반 상품 클릭시 상세 페이지로 이동 
				RequestDispatcher dispatcher = 
						request.getRequestDispatcher("/product/product_view.jsp");
						dispatcher.forward(request, response);
			}
		
	}
			
}


