package ctrl;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/product_custom_view")
public class ProductCustomViewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProductCustomViewCtrl() {super();  }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int pmcidx = Integer.parseInt(request.getParameter("pmcidx"));
		
		
		//의례적인 로그인검
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		
		if(loginInfo == null ) { 
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('잘못된 경로로 들어오셨습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();	
		}
		String miid = loginInfo.getMi_id();
		
		//ProductCustom형으로 해당 커스텀마카롱인덱스에 해당하는 정보를 담아온다. 
		ProductCustomSvc productCustomSvc = new ProductCustomSvc();
		ProductCustom pc = productCustomSvc.getCustomInfo(pmcidx);
		
	
		if(pc==null) { //클릭해서 들어온건데 정보가 없으면 
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('잘못된 경로로 들어오셨습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
			
		
		//토핑의 인덱스를 가져온다. null이나 빈문자열이 아니면 인덱스를 저장할 변수에 
		String tpid1=""; String tpid2="";
		//토핑 이름 가져감 
		if(pc.getPmc_tp1()!=null && !pc.getPmc_tp1().equals("")){
			tpid1= pc.getPmc_tp1();
			String tpname1 = productCustomSvc.getToppingName(tpid1);
			request.setAttribute("tpname1", tpname1);
		}
		if(pc.getPmc_tp2()!=null && !pc.getPmc_tp2().equals("")){
			tpid2= pc.getPmc_tp2();
			String tpname2 = productCustomSvc.getToppingName(tpid2);
			request.setAttribute("tpname2", tpname2);
		}
		
		

			request.setAttribute("pc", pc); //담고
			//사실은 리뷰도 담아가야할텐데 어떻게 될 지 몰루??
			
			RequestDispatcher dispatcher 
			= request.getRequestDispatcher("product/product_custom_view.jsp");
			dispatcher.forward(request, response);
			
		
		
	
		
	
	}

}












