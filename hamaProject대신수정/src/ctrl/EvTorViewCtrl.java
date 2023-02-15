package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/ev_tor_view")
public class EvTorViewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public EvTorViewCtrl() {super();}
	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//PageInfo pageInfo	= (PageInfo)request.getAttribute("pageInfo"); 
		//EvCusTorPoll ecpt = (EvCusTorPoll) request.getAttribute("ectp");// ��ʸ�Ʈ ��ǥ �޾ƿ���
		// EvCusTor ect = (EvCusTor) request.getAttribute("ect");//��ʸ�Ʈ ����Ʈ �޾ƿ���
		//String good = request.getParameter("good"); 	// view.jsp�� ajax :��ǥ�ϱ� ��� -> ������ ctrl ����
	//	ArrayList<EvCusTor> torList = (ArrayList<EvCusTor>)request.getAttribute("torList");
			
	
		//System.out.println("ectpidx : " + request.getParameter("ectpidx"));
		System.out.println("ectidx : " + request.getParameter("ectidx"));
		System.out.println("pmcidx : " + request.getParameter("pmcidx"));
		
		
		int ectidx = Integer.parseInt(request.getParameter("ectidx")); // ��ʸ�Ʈ �� ��ȣ
		// 
		// �� ��ȣ�� �˾ƾ� �󼼺���
		//System.out.println("test1");
		int cpage = 1, psize=10, bsize=10, rcnt=0, pcnt=0, spage=0;
		//���� ������ ��ȣ, ������ũ��, ���ũ��, ���ڵ�(�Խñ�)����, ����������, ���������� ����
		//������ ������ 
		
		if(request.getParameter("cpage")!= null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
			//cpage���� ������ �޾Ƽ� int������ ����ȯ ��Ŵ 
			//cpage�� String���� �ް� �ٴҰ����� ������� �ؾ��ϱ� ������ int����ȯ
			//���������1. ���Ȼ�����.2	
		}
		// �α���üũ //�α��� ������ �������� �ǰ� ���ǿ� �ִ°� �̾ƿ���
		HttpSession session = request.getSession();
		MemberInfo mi = (MemberInfo) session.getAttribute("loginInfo");
	
		// �����͸����
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		// �α����� �ȵ� �����̸� ƨ���
		if (mi == null) {
			out.println("<script>");
			out.println("alert('�߸��� ��η� �����̽��ϴ�. �α����ϼ���.');");
			out.println("</script>");
			out.close();
		}
		
		int result = 0;
		EvTorViewSvc evTorViewSvc = new EvTorViewSvc();
	
		EvCusTorPoll ectp = new EvCusTorPoll();
		
		if(request.getParameter("ectpidx") != null) {
			int ectpidx = Integer.parseInt(request.getParameter("ectpidx")); 
			int pmcidx = Integer.parseInt(request.getParameter("pmcidx"));
			
			ectp.setEctp_idx(ectpidx);
			ectp.setEct_idx(ectidx);
			ectp.setMi_id(mi.getMi_id());
			//ectp.setPmc_idx(pmcidx);
		// EvCusTorPoll�� �ν��Ͻ��� ���
			result = evTorViewSvc.voteBtn(ectp);
		}
		
		// ������ �Խù��� ���� �͵���
		EvCusTor ect = new EvCusTor();
		ect = evTorViewSvc.getEvCusTorInfo(ectidx);

		// ������ �ν��Ͻ��� �޾ƿ� ����
		// ����ڰ� ������ �Խñ��� ������� EvCusTor �� �ν��Ͻ��� �޾ƿ�

		if (ect == null) { // �۸�Ͽ��� ���� Ŭ�������� ��������ϴµ� null�ϼ������� ����׷��ٸ�
			// ������ �Խñ��� ������
			response.setContentType("text/html; charset=utf-8");

			out.println("<script>");
			out.println("alert('�α��� �� ����ϽǼ��ֽ��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();

		} else {
			// ������ �Խñ��� ������
			PageInfo pageInfo = new PageInfo();
			pageInfo.setBsize(bsize);
			pageInfo.setCpage(cpage);
			pageInfo.setPcnt(pcnt);
			pageInfo.setPsize(psize);
			pageInfo.setRcnt(rcnt);
			pageInfo.setSpage(spage);

			System.out.println("������� Ȯ��");
			
			request.setAttribute("pageInfo", pageInfo); 
			request.setAttribute("ect", ect);// �Խñۺ����ְ�
			System.out.println();
			// �ڷΰ��������� ������ ��Ͽ�
			
		/*	if(request.getParameter("ectpidx") != null) {
				request.setAttribute("ectp", ectp);
			}
		*/
			RequestDispatcher dispatcher = request.getRequestDispatcher("event/ev_tor_view.jsp");
			dispatcher.forward(request, response);
			
		}	
		
		out.println(result);
		// ev_tor_view ajax�� �̵�
	}	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request ,response );
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request ,response );
	}


}
