package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

//�̷��� �ٲ������.

@WebServlet("/login_form")
public class LoginForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginForm() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		HttpSession session = request.getSession();
		if(session.getAttribute("loginInfo")!= null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인후 이용해주세요');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("login_form.jsp");

		dispatcher.forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
