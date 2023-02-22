package ctrl;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/productsetview")
public class ProductSetViewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductSetViewCtrl() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//String piid = request.getParameter("piid");
		String boxSize = (String)request.getParameter("boxSize");
		
		ProductSetViewSvc productSetViewSvc = new ProductSetViewSvc();
		
		ArrayList<ProductInfo> piList = productSetViewSvc.getProductInfo();
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		System.out.println(boxSize);
		if(loginInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();

			out.println("<script>");
			out.println("alert('로그인 이후 사용하실 수 있습니다.');");
			out.println("location.replace('login_form.jsp?url=productsetview?boxSize="+boxSize+"');");
			out.println("</script>");
			out.close();
		}
		
		String miid = loginInfo.getMi_id();
		
		
		ArrayList<ProductCustom> pcList = productSetViewSvc.getProductCustom(miid);
		
		request.setAttribute("pcList", pcList);	//而ㅼ뒪�� 留덉뭅濡� 
		request.setAttribute("piList", piList); //�씪諛� 留덉뭅濡�
		request.setAttribute("boxSizeCount", boxSize); //諛뺤뒪 媛쒖닔
		
		RequestDispatcher dispatcher = 
				
		request.getRequestDispatcher("product/product_set_view.jsp");
		dispatcher.forward(request, response);
	}

}
