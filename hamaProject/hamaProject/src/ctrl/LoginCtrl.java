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
		//LoginSvc �� �ν��Ͻ� loginSvc ���� 
		MemberInfo loginInfo = loginSvc.getLoginInfo(uid, pwd);
		//ctrl>svc>dao>svc>ctrl �� �̵��ߴٰ� ������ ���� �޼ҵ� ȣ���ߴٰ� �����ϱ�
		
		//������ �����
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		
		if(loginInfo != null) { //�α��� ������
			//�����̾ ���� ���� ��������. �α��� �������� ���� ���� �����
			out.println("<script>");
			out.println("alert('ddddd');");
	
			out.println("</script>");
			out.close();
		}else {//�α��� ���н�

			out.println("<script>");
			out.println("alert('���̵�� ��ȣ�� Ȯ���ϼ���');");
	
			out.println("</script>");
			out.close();
		}
	}

}
