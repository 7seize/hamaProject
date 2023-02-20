package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

//이렇게 바꿔놔야함.

@WebServlet("/login_form")
public class LoginForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginForm() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		//JSP가 아니므로 세션을 사용하려면 직접 HttpSession 세션의 인스턴스를 생성해야한다.
		HttpSession session = request.getSession();
		//아직안쓸것이기때문에 아직 형변환 안해도 됨. 
		if(session.getAttribute("loginInfo")!= null) {
			//현재 로그인이 되어있는 상태라면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('잘못된 경로로 들어오셨습니다');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("login_form.jsp");
		//로그인 폼으로 보낸다.
		dispatcher.forward(request, response);
		//디스패처로 포워딩..1.url이 안변함. 2.이전파일이갖고있는 request랑 response 자기가 가져감
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
