package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;


@WebServlet("/cart_proc_del")
public class CartProcDelCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;   
    public CartProcDelCtrl() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String ocidx = request.getParameter("ocidx");

		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");

		if(loginInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();

			out.println("<script>");
			out.println("alert('로그인 이후 사용하실 수 있습니다.');");
			//out.println("location.replace();");
			out.println("</script>");
			out.close();
		}
		String miid = loginInfo.getMi_id();
		String where = " where mi_id ='" + miid + "' ";
		if(ocidx.indexOf(',') > 0) { //여러개의 상품을 삭제할 경우
			//and (oc_idx = ? or oc_idx = ? or oc_idx = ? );
			//ocidx 문자열로 111,222,333,444 이렇게 되어있음
			
			String[] arr = ocidx.split(",");
			for(int i=0; i< arr.length; i++) {
				if(i==0) where += " and (oc_idx = " + arr[i];
				else	where += " or oc_idx = " + arr[i];
			}
			where += ")";
			
			
		}else {
			where += " and oc_idx = " + ocidx ;
		}
			
		CartProcDelSvc cartProcDelSvc = new CartProcDelSvc();
		int result = cartProcDelSvc.cartDelete(where);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		System.out.println(result);
		out.println(result);
		//result 출력  ajax로 간다.
	}

}
