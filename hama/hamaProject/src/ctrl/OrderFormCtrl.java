package ctrl;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/order_form")
public class OrderFormCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public OrderFormCtrl() {super();}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		System.out.println("post 성공");

		String kind = request.getParameter("kind");
		

		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");

		if(loginInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인후 이용해 주세요');");
			out.println("location.replace('login_form.jsp?url=cart_view');");
			out.println("</script>");
			out.close();
		}
		String miid = loginInfo.getMi_id();
		

		String select = "select a.pi_id, a.pi_img1, a.pi_name, a.pi_price ";
		String from = " from t_product_info a ";
		String where = " where a.pi_isview = 'y' ";
		
		if(kind.equals("c")) {
			select += " , c.oc_cnt cnt,   c.oc_idx";
			from += ", t_order_cart c ";
			where += " and a.pi_id = c.pi_id " +
			" and c.mi_id = '" + miid+ "' and (" ;
			
			
			String[] arr = request.getParameterValues("chk");

			for(int i=0; i<arr.length; i++) {
				if(i==0) where += " c.oc_idx = '' ";
				else	 where += " or c.oc_idx = " + arr[i];
			}
			where += ") order by a.pi_id " ; 
		} else {
			String piid = request.getParameter("piid");
			int cnt = Integer.parseInt(request.getParameter("cnt")); 
			select += "," + cnt + " cnt ";
			where +=  "  and a.pi_id = '" + piid + "'";
		}

		OrderFormSvc orderFormSvc = new OrderFormSvc();
		ArrayList<OrderCart> pdtList = 
				orderFormSvc.getBuyList(kind, select + from + where);

		ArrayList<MemberAddr> addrList = 
				orderFormSvc.getAddrList(miid);
		System.out.println("select: "+select);
		System.out.println("from:"+from);
		System.out.println("where:"+where);
		
		request.setAttribute("pdtList", pdtList);
		request.setAttribute("addrList", addrList);
		request.setAttribute("where", where);
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("order/order_form.jsp");
		dispatcher.forward(request, response);
		
	}

}

