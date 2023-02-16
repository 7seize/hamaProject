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
      String url = request.getParameter("url").replace('$', '&');
      LoginSvc loginSvc = new LoginSvc();
      //LoginSvc 
      MemberInfo loginInfo = loginSvc.getLoginInfo(uid, pwd);

      

      response.setContentType("text/html; charset=utf-8");
      PrintWriter out = response.getWriter();
      
      
      if(loginInfo != null) {
         
         HttpSession session = request.getSession();
         session.setAttribute("loginInfo", loginInfo );
         
         out.println("<script>");
         out.println("location.replace('" + url + "');");
         out.println("</script>");
         out.close();
      }else {

         out.println("<script>");
         out.println("alert('아이디나 비밀번호가 일치 하지 않습니다.');");
         out.println("history.back();");
         out.println("</script>");
         out.close();
      }
   }

}