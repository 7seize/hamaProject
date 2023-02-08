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
		//임시값 #################################
		String miid = "atest";
		//#######################################
		
		ProductSetViewSvc productSetViewSvc = new ProductSetViewSvc();
		
		ArrayList<ProductInfo> piList = productSetViewSvc.getProductInfo();
		
		
		ProductCustom pc = productSetViewSvc.getProductCustom(miid);
		
		//request.setAttribute("pc", pc);
		request.setAttribute("piList", piList);
		
		RequestDispatcher dispatcher = 
				
		request.getRequestDispatcher("product/product_set_view.jsp");
		dispatcher.forward(request, response);
	}

}
