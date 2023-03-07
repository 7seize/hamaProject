package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;
import java.net.*;


@WebServlet("/productcus")
public class ProductCusCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProductCusCtrl() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0, spage = 0;

		if(request.getParameter("cpage")!= null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}
		String schtype =request.getParameter("schtype");   // �˻� ����
		String keyword =request.getParameter("keyword");   // �˻���
		String where = " ";   
		String kindorder = request.getParameter("kindorder");
		
		if(schtype == null || keyword == null) {
			 schtype = "";
			 keyword = "";
		 }else if(!schtype.equals("") && !keyword.equals("")  ) {
			 URLEncoder.encode(keyword, "UTF-8");
			 
			 if(schtype.equals("id")) {
				 where+=" and (pi_id like '%" + keyword+ "%' )";
			 }else if(schtype.equals("topping")){
				 where+=" and ((pmc_tp1 like '%" + keyword+ "%') or (pmc_tp2 like '%\" + keyword+ \"%') )";
			 }else if(schtype.equals("name")) {
				 where+=" and (pi_name like '%" + keyword+ "%' )";
			 }else if(schtype.equals("isview")) {
				 where+=" and (pmc_isview ='n' )";
			 }
		 }
		
		
		String order ="";
		/*
		if(kindorder == null) kindorder ="";
		if(!kindorder.equals("")) {order = " order by pi_" + kindorder + " desc";} else {
			order = " order by pmc_idx desc";
		} 
		*/
		
		
		
		ProductCusSvc productcusSvc = new ProductCusSvc();
		 rcnt = productcusSvc.getListCount(where);
		
		ArrayList<ProductCustom> productcustom = new ArrayList<ProductCustom>();
		productcustom = productcusSvc.getProductList(cpage,psize,where,order);
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);
		pageInfo.setPcnt(pcnt);
		pageInfo.setPsize(psize);
		pageInfo.setRcnt(rcnt);
		pageInfo.setSpage(spage);
		pageInfo.setSchtype(schtype);
		pageInfo.setKeyword(keyword);

		request.setAttribute("productCustom", productcustom);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("kindorder", kindorder);
		
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("product/product_cus_list.jsp");
		dispatcher.forward(request,response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}