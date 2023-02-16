package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import svc.*;


@WebServlet("/ev_tor_form_in")
public class EvTorFormInCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public EvTorFormInCtrl() {super();  }
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	RequestDispatcher dispatcher = 
    		request.getRequestDispatcher("event/ev_tor_form.jsp.jsp");
    	dispatcher.forward(request, response);
    	// ��� ���� ���̹Ƿ� ���� Service�� Dao Ŭ���� ���� �ٷ� View�� �ش��ϴ� ev_tor_view.jsp ���Ϸ� �̵�
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
