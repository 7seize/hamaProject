package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;
import java.net.*;

@WebServlet("/member")
public class MemberCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MemberCtrl() { super();}

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
			 
			 if(schtype.equals("uid")) {
				 where+=" and (mi_id like '%" + keyword+ "%' )";
			 }else if(schtype.equals("name")){
				 where+=" and (mi_name like '%" + keyword+ "%' )";
			 }
		 }
		String order ="";
		if(kindorder == null) kindorder ="";
		if(!kindorder.equals("")) {order = " order by mi_" + kindorder;} else {
			order = " order by mi_joindate desc ";
		} 
	
		MemberListSvc memberListSvc = new MemberListSvc();
		 rcnt = memberListSvc.getListCount(where);
		
		ArrayList<MemberInfo> memberList  = new ArrayList<MemberInfo>();
		memberList = memberListSvc.getMemberList(cpage,psize,where,order);
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);
		pageInfo.setPcnt(pcnt);
		pageInfo.setPsize(psize);
		pageInfo.setRcnt(rcnt);
		pageInfo.setSpage(spage);
		pageInfo.setSchtype(schtype);
		pageInfo.setKeyword(keyword);
		
		System.out.println("test2");
		
		request.setAttribute("memberList", memberList);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("kindorder", kindorder);
		
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("member/member_list.jsp");
		dispatcher.forward(request,response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//ĳ���� ���ڵ�
		String status = request.getParameter("status");
		String oiid = request.getParameter("oiid");
		
		
		System.out.println(status);
		System.out.println(oiid);
		MemberListSvc orderListSvc = new MemberListSvc();
		int result = orderListSvc.statUpdate(status,oiid);
		
		//CartProcUpSvc cartProcUpSvc = new CartProcUpSvc();
		//int result = cartProcUpSvc.cartUpdate(oc);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
	}

}
