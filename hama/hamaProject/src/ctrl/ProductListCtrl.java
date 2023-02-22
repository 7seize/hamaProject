package ctrl;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;



@WebServlet("/product_list")
public class ProductListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductListCtrl() { super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		
		String where = ""; //검색조건 where절 
		String pc = request.getParameter("pc");	// 상품 분류 , 검색조건 없음
		if (pc != null && !pc.equals("")) {
			
			where += " and pc_id = '" + pc + "' ";
		}
		
		//정렬조건은 판매순(pi_sale), 최근순 (pi_date)
		String orderBy = ""; 
		String o = request.getParameter("o");
		if (o == null || o.equals(""))	o = "a"; 

		switch (o) {
		case "a" :	// 등록 역순
			orderBy = " order by pi_date desc";	break;
		case "b" :	// 판매량순
			orderBy = " order by pi_sale desc";	break;
		}
		
		
		ProductListSvc productListSvc = new ProductListSvc();
		ArrayList<ProductInfo> productList = 
				productListSvc.getProductList(where, orderBy); //제품리스트가져감
		
		PageInfo pageInfo = new PageInfo(); //정렬을 위해 o가져감 
		pageInfo.setO(o);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("productList", productList);
		
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/product/product_list.jsp");
				dispatcher.forward(request, response);
	
	
	
	
	
	
	
	
	
	}

}

















