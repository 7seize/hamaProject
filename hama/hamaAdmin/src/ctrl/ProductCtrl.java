package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;
import java.net.*;

@WebServlet("/product")
public class ProductCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProductCtrl() { super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0, spage = 0;

		if(request.getParameter("cpage")!= null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}
		String schtype =request.getParameter("schtype");   // �˻� ����
		String keyword =request.getParameter("keyword");   // �˻���
		String where = " where 1=1 ";   
		String kindorder = request.getParameter("kindorder");
		
		if(schtype == null || keyword == null) {
			 schtype = "";
			 keyword = "";
		 }else if(!schtype.equals("") && !keyword.equals("")  ) {
			 URLEncoder.encode(keyword, "UTF-8");
			 
			 if(schtype.equals("id")) {
				 where+=" and (pi_id like '%" + keyword+ "%' )";
			 }else if(schtype.equals("ctr")){
				 where+=" and (pc_id like '%" + keyword+ "%' )";
			 }else if(schtype.equals("name")) {
				 where+=" and (pi_name like '%" + keyword+ "%' )";
			 }else if(schtype.equals("isview")) {
				 where+=" and (pi_isview ='n' )";
			 }
		 }
		String order ="";
		if(kindorder == null) kindorder ="";
		if(!kindorder.equals("")) {order = " order by pi_" + kindorder + " desc";} else {
			order = " order by pi_id";
		} 
	
		ProductListSvc ProductListSvc = new ProductListSvc();
		 rcnt = ProductListSvc.getListCount(where);
		
		ArrayList<ProductInfo> productInfo  = new ArrayList<ProductInfo>();
		productInfo = ProductListSvc.getProductList(cpage,psize,where,order);
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);
		pageInfo.setPcnt(pcnt);
		pageInfo.setPsize(psize);
		pageInfo.setRcnt(rcnt);
		pageInfo.setSpage(spage);
		pageInfo.setSchtype(schtype);
		pageInfo.setKeyword(keyword);
		
		
		request.setAttribute("productInfo", productInfo);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("kindorder", kindorder);
		
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("product/product_list.jsp");
		dispatcher.forward(request,response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//ĳ���� ���ڵ�
		String status = request.getParameter("status");
		String piid = request.getParameter("piid");
		
		
		System.out.println(status);

		ProductListSvc productListSvc = new ProductListSvc();
		int result = productListSvc.statUpdate(status,piid);
		
		//CartProcUpSvc cartProcUpSvc = new CartProcUpSvc();
		//int result = cartProcUpSvc.cartUpdate(oc);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
	}

}