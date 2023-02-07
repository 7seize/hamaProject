package ctrl;

import java.io.IOException;
import java.io.PrintWriter;

import svc.*;
import vo.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;


@WebServlet("/login")
public class LoginCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginCtrl() {super();}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid").trim().toLowerCase();
		String pwd = request.getParameter("pwd").trim();	
		LoginSvc loginSvc = new LoginSvc();
		MemberInfo loginInfo = loginSvc.getLoginInfo(uid, pwd);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		if(loginInfo != null) {
			HttpSession session = request.getSession();
			session.setAttribute("loginInfo", loginInfo);
			
			out.println("<script>");
			out.println("alert('로그인 성공할때 뜨는 알람');");
			out.println("</script>");
			out.close();
		}else {
			out.println("<script>");
			out.println("alert('존재하지 않는 회원입니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
	}

}