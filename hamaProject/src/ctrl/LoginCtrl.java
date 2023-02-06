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
		//LoginSvc 의 인스턴스 loginSvc 생성 
		MemberInfo loginInfo = loginSvc.getLoginInfo(uid, pwd);
		//ctrl>svc>dao>svc>ctrl 로 이동했다고 느끼지 말고 메소드 호출했다고 생각하기
		
		//프린터 만들기
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		
		if(loginInfo != null) { //로그인 성공시
			//서블릿이어서 세션 직접 만들어야함. 로그인 성공했을 때만 세션 만들기
			out.println("<script>");
			out.println("alert('ddddd');");
	
			out.println("</script>");
			out.close();
		}else {//로그인 실패시

			out.println("<script>");
			out.println("alert('아이디와 암호를 확인하세요');");
	
			out.println("</script>");
			out.close();
		}
	}

}
