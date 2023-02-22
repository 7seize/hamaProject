package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;
import java.net.*;


@WebServlet("/orderview")
public class OrderViewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;  
    public OrderViewCtrl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String oiid = request.getParameter("oiid");
		OrderViewSvc orderViewSvc = new OrderViewSvc();
		OrderInfo orderInfo = orderViewSvc.getOrderInfo(oiid);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
