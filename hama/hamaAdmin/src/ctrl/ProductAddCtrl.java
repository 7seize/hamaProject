package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;
import java.net.*;

@WebServlet("/productadd")
public class ProductAddCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProductAddCtrl() { super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String getcat = request.getParameter("cat");
		String setpiid ="";
		

		if(getcat!= null) {
			ProductListSvc productListSvc = new ProductListSvc();
			setpiid = productListSvc.piidAssign(getcat);
		}
		
		
		System.out.println("setpiid : " + setpiid);
		
		request.setAttribute("setpiid", setpiid);
		
		String url = "product/product_add.jsp?setpiid=" + URLEncoder.encode(setpiid, "UTF-8");
		response.sendRedirect(url);
		
	}


}