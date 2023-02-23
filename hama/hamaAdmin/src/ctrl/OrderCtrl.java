package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;
import java.net.*;

@WebServlet("/order")
public class OrderCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public OrderCtrl() { super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 15, bsize = 10, rcnt = 0, pcnt = 0, spage = 0;

		if(request.getParameter("cpage")!= null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}
		String schtype =request.getParameter("schtype");
		String keyword =request.getParameter("keyword"); 
		String startdate =request.getParameter("startdate");
		String enddate =request.getParameter("enddate");
		String where = " where 1=1 ";   
		String kindorder = request.getParameter("kindorder");
		String desc =request.getParameter("desc");
		
		if(schtype == null || keyword == null) {
			 schtype = "";
			 keyword = "";
		 }else if(!schtype.equals("") && !keyword.equals("")  ) {
			 URLEncoder.encode(keyword, "UTF-8");
			 
			 if(schtype.equals("idx")) {
				 where+=" and (oi_id like '%" + keyword+ "%' )";
			 }else if(schtype.equals("uid")){
				 where+=" and (mi_id like '%" + keyword+ "%' )";
			 }else if(schtype.equals("name")) {
				 where+=" and (oi_sender like '%" + keyword+ "%' )";
			 }
		 }
		if(startdate == null) {
			startdate = "";
		}else if(!startdate.equals("")){
			where+=" and '"+startdate+"' <= DATE_FORMAT(oi_date, '%Y-%m-%d') ";
		}
		if(enddate == null) {
			enddate = "";
		}else if(!enddate.equals("")){
			where+=" and '"+enddate+"' >= DATE_FORMAT(oi_date, '%Y-%m-%d') ";
		}
		
		String order ="";
		
		if(kindorder == null) kindorder ="";
		if(desc == null){
			desc ="";
		}else if(desc.equals("desc")) {
			desc = "desc";
		}else{
			desc = "asc";
		}
		
		if(!kindorder.equals("")) {
			order = " order by oi_" + kindorder + " ";
			order += desc;
		} else {
			order = " order by oi_date desc ";
		} 
	
		OrderListSvc orderListSvc = new OrderListSvc();
		 rcnt = orderListSvc.getListCount(where);
		
		ArrayList<OrderInfo> orderList  = new ArrayList<OrderInfo>();
		orderList = orderListSvc.getOrderList(cpage,psize,where,order);
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);
		pageInfo.setPcnt(pcnt);
		pageInfo.setPsize(psize);
		pageInfo.setRcnt(rcnt);
		pageInfo.setSpage(spage);
		pageInfo.setSchtype(schtype);
		pageInfo.setKeyword(keyword);
		

		request.setAttribute("orderList", orderList);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("kindorder", kindorder);
		request.setAttribute("desc", desc);
		request.setAttribute("startdate", startdate);
		request.setAttribute("enddate", enddate);
		
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("order/order_list.jsp");
		dispatcher.forward(request,response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String status = request.getParameter("status");
		String oiid = request.getParameter("oiid");
		
		OrderListSvc orderListSvc = new OrderListSvc();
		int result = orderListSvc.statUpdate(status,oiid);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
	}

}
