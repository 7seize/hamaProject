package ctrl;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
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
		RequestDispatcher dispatcher = 
		request.getRequestDispatcher("/product/product_view.jsp");
		dispatcher.forward(request, response);
	}
			
}


