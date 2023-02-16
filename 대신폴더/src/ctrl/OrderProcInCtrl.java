package ctrl;

import static db.JdbcUtil.commit;
import static db.JdbcUtil.rollback;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

//select할 거 아니라 insert update delete만 할거기 때문에
//트랜잭션 동작할 것. arrayList쓰려고 util import 안해도 됨 select안하니깐..

@WebServlet("/order_proc_in")
public class OrderProcInCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public OrderProcInCtrl() {super();}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		
		//로그인체크
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		//로그인 인포만 가져오면 되고 세션에 있는거 뽑아오기 
		if(loginInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('잘못된 경로로 들어오셨습니다. 로그인하세요.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		String miid = loginInfo.getMi_id(); //로그인한 회원아이디를 miid에 저장 
		String kind = request.getParameter("kind");
		int total = Integer.parseInt(request.getParameter("total"));
		//총금액은 무조건 int
		//ocidx는 kind가 c일경우만 받을거니까 여기서 받을 필요 x

		
		//배송지 및 결제 정보 받아서 작업 
		String orderName = request.getParameter("order_name");
		String orderPhone = request.getParameter("order_phone");
		String rname = request.getParameter("receiveName");
		String phone = request.getParameter("receivePhone");
		String zip = request.getParameter("receiveZip");
		String addr1 = request.getParameter("receiveAdd1");
		String addr2 = request.getParameter("receiveAdd2");
		String receiveMemo = request.getParameter("receiveMemo");
		String receiveDate = request.getParameter("receiveDate");
		String payment = request.getParameter("order_payment");
		
		
		//담아야할 게 많으니까 OrderInfo형인스턴스로 제작해서 가져감 
		OrderInfo oi = new OrderInfo();
		oi.setOi_sender(orderName);
		oi.setOi_sephone(orderPhone);
		oi.setMi_id(miid);
		oi.setOi_name(rname); //수취인이름
		oi.setOi_phone(phone);
		oi.setOi_addr1(addr1);
		oi.setOi_addr2(addr2);
		oi.setOi_zip(zip);
		oi.setOi_payment(payment);
		oi.setOi_pay(total);
		oi.setOi_memo(receiveMemo);
		oi.setOi_date(receiveDate);
		//#####################################################################################
		
		OrderProcInSvc orderProcInSvc = new OrderProcInSvc();
		//서비스로오옹..
		String result = null, temp = "";
		//result가 왜 String???? 받아올게 많아서 숫자로 못받아옴
		//실행결과를 저장할 변수로 주문번호, 적용된 레코드 개수, 적용되어야 할 레코드 개수 
		
		if(kind.equals("c")) { //장바구니를 통한 구매일경우
			//장바구니 idx필요 ocidx를 모아서 하나의 긴 스트링을 ocidxs를제작
			//그걸 tempt안에 넣음
			temp = request.getParameter("ocidxs");
			//장바구니에서 구매할 상품들의 인덱스번호들(쉼표로 구분)
			
		}else { //바로 구매일 경우 
			//상품아이디랑 수량 옵션을 하나의 스트링으로 만들어가서 가져감
			
			
		}
		result = orderProcInSvc.orderInsert(kind, oi, temp);
		String[] arr = result.split(",");
		
		if(arr[1].equals(arr[2])) {
			//정상적으로 구매가 이루어졌으면
			//구매한 내역을 보는 폼으로 이동..
			//url이 변해야하는가 아닌가 변함>redirect 변하지않음>디스패치
			//새로고침하면어떻게되는가?
			//redirect로 이동해야함. proc작업했으면 처리한 걸 또 처리할 수 없으니 보통은 redirect가많음
			response.sendRedirect("order_end?oiid=" + arr[0]);
			
			//근데 주문번호 가져가기 /보안상 문제가 없나? post로 보내주면 될텐데 그렇게 보내려면 
			//url손으로 치는건 다 get방식 form태그 쓰면 post인데 꼼수가...
			//out.println으로 스크립트.document.frm.submit하고 form 사진참고
			//order_proc_in_ctrl_oiidhowto...참고 
			//그렇게하면 주문번호를 숨긴 채로 가져갈 수 있음 장점:주문번호숨김
			//단점 : sendRedirect에 비해 왔다갔다함..그래서 sendRedirect하기로함
			//주문번호를 쿼리스트링이 아닌 post방식으로 보내 숨기는 방법 
			
			
		}	
		else{
			//정상적으로 구매가 이루어지지 않았으면 
			//이상태는 결제는 되었는데 쿼리는 안들어간 상태
			//어드민을 어떻게 만들었는지에 따라 cs팀에서 요청이 들어올 것.
			
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('구매가 정상적으로 처리되지 않았습니다. \\n 고객 센터에 문의하세요. ');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		
	}

}








