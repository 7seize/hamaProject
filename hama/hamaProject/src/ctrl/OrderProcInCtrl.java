package ctrl;

import static db.JdbcUtil.commit;
import static db.JdbcUtil.rollback;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/order_proc_in")
public class OrderProcInCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public OrderProcInCtrl() {super();}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		
		if(loginInfo == null) {
	         response.setContentType("text/html; charset=utf-8");
	         PrintWriter out = response.getWriter();

	         out.println("<script>");
	         out.println("alert('로그인 이후 사용하실 수 있습니다.');");
	         out.println("location.replace('login_form.jsp?url=order_proc_in');");
	         //out.println("location.replace();");
	         out.println("</script>");
	         out.close();
	      }
		
		String miid = loginInfo.getMi_id(); 
		String kind = request.getParameter("kind");
		int total = Integer.parseInt(request.getParameter("total"));

		String orderName = request.getParameter("order_name");
		String orderPhone = request.getParameter("order_phone");
		String rname = request.getParameter("receiveName");
		String phone = request.getParameter("receivePhone");
		String zip = request.getParameter("receiveZip");
		String addr1 = request.getParameter("receiveAdd1");
		String addr2 = request.getParameter("receiveAdd2");
		String receiveMemo = request.getParameter("receiveMemo");
		String receiveDate = request.getParameter("receiveDate");
		String payment = request.getParameter("order_payment");
		String where = request.getParameter("where");


		OrderInfo oi = new OrderInfo();
		oi.setOi_sender(orderName);
		oi.setOi_sephone(orderPhone);
		oi.setMi_id(miid);
		oi.setOi_name(rname); //�뜝�럥�빢占쎈퓛占쎈쳛占쎈데�뜝�럩逾좑옙逾녑뜝占�
		oi.setOi_phone(phone);
		oi.setOi_addr1(addr1);
		oi.setOi_addr2(addr2);
		oi.setOi_zip(zip);
		oi.setOi_payment(payment);
		oi.setOi_pay(total);
		oi.setOi_memo(receiveMemo);
		oi.setOi_date(receiveDate);
		//#####################################################################################
		
		OrderProcInSvc orderProcInSvc = new OrderProcInSvc();
		String result = null, temp = "";
		if(kind.equals("c")) { 
			temp = request.getParameter("ocidxs");
			
		}else { 
			temp = where;
		}
		result = orderProcInSvc.orderInsert(kind, oi, temp);
		String[] arr = result.split(",");
		
		if(arr[1].equals(arr[2])) {
			
			response.sendRedirect("order_end?oiid=" + arr[0]);			
			
		}	
		else{

			
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('占쎈쨨占쎈맍瑗삥뤆�룊�삕 �뜝�럩�젧�뜝�럡留믣뜝�럩�쓤�뜝�럩紐드슖�댙�삕 嶺뚳퐣瑗띰옙遊뷴뜝�럥�뵹嶺뚯쉻�삕 �뜝�럥瑜ュ뜝�럥由��뜝�럥裕멨뜝�럥鍮띶뜝�럥堉�. \\n 占썩뫁伊믦�뚳옙 �뜝�럡�돺�뜝�럡�댉�뜝�럥�뱺 占쎈닱筌뤾쑴踰ε뜝�럥由��뜝�럡�돪�뜝�럩�뭵. ');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		
	}

}








