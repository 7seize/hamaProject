package ctrl;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/order_form")
public class OrderFormCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public OrderFormCtrl() {super();}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		System.out.println("post 성공");
		//바로구매인지 장바구니인지 구분해야함
		//장바구니를 통한 구매(c)인지, 바로 구매(d)인지 여부를 판단할 데이터
		String kind = request.getParameter("kind");
		
		//로그인체크
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		//로그인 인포만 가져오면 되고 세션에 있는거 뽑아오기 
		if(loginInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 이후 사용하실 수 있습니다.');");
			out.println("location.replace('login_form.jsp?url=cart_view');");
			out.println("</script>");
			out.close();
		}
		String miid = loginInfo.getMi_id();
		
		//구매하려는 상품 정보를 위한 쿼리를 만들어서> 뷰로 
		//장바구니  ㅣ장바구니 테이블에서정보를 가져옴
		//직접인 경우 장바구니에서 가져올 게 없다. 
		String select = "select a.pi_id, a.pi_img1, a.pi_name, a.pi_price ";
		String from = " from t_product_info a ";
		String where = " where a.pi_isview = 'y' ";
		
		if(kind.equals("c")) { //장바구니(c)를 통한 구매일 경우
			//장바구니일 때만 회원 아이디가 필요함 
			select += " , c.oc_cnt cnt,   c.oc_idx";
			from += ", t_order_cart c ";
			where += " and a.pi_id = c.pi_id " +
			" and c.mi_id = '" + miid+ "' and (" ;
			
			//###################################################################################작업중
			String[] arr = request.getParameterValues("chk");
			//checkbox에 있는 value들 배열로 받기

			for(int i=0; i<arr.length; i++) {
				if(i==0) where += " c.oc_idx = '' ";
				else	 where += " or c.oc_idx = " + arr[i];
//"select 어쩌고 where c.oc_idx = arr[1] or c.oc_idx = arr[2] or...이렇게 나오게 함 "
//첫번째는 or 안붙이고 다음거는 or 쓴다. 
			}
			where += ") order by a.pi_id " ; //괄호 닫기
			//order by로 아이디순 그다음 상품순 정렬
	
		} else { //바로 구매(d)일 경우 //###################################################################################작업중
			//오더폼에서보여줄 상품정보를 보여주면 됨.
			//어디서가져옴? 바로구매니까 장바구니테이블이아니라 상품테이블에서 직접 가져와야함
			//아이디,수량은 가져올 수 없고 가져와야한다.
			String piid = request.getParameter("piid");
			int cnt = Integer.parseInt(request.getParameter("cnt")); 
			
			//수량,사이즈는 이미 값이 있어서 받아온 상태>계속뒤에붙여서달고다녀야하는데
			//바로구매때문에 매개변수가 더 늘어난 꼴이 됨... 
			//cnt가 product 테이블에 넣고싶으면  alias - cnt 사용하면 됨
			//바로구매는 단 하나니까 ㄱㅊ음 
			
			select += "," + cnt + " cnt ";
			//쿼리 예시 select pi_id, pi_name, 10 cnt from t_product_info;
			//이렇게 나올 것임 
			// 바로구매일 때 alias cnt로 받았으면 장바구니일 때도 cnt로 alias 써야하고 해줘야함
			
			where +=  "  and a.pi_id = '" + piid + "'";
			//바로구매니까 무조건 하나만 가져옴 
			
		
			
		}
		// 바로구매여도 오더카트형을 어레이리스트를 사용함.. 근데 어레이리스트이다?
		//바로구매는 하나인데 어레이리스트?써도되나 
		//하나만 넣어도 됨
		OrderFormSvc orderFormSvc = new OrderFormSvc();
		//하나가 아니라 여러개일 수도 있으니 어레이 리스트로 가져가기 
		ArrayList<OrderCart> pdtList = 
				orderFormSvc.getBuyList(kind, select + from + where);
		//구매할 상품 목록을 ArrayList로 받아옴 
		
		ArrayList<MemberAddr> addrList = 
				orderFormSvc.getAddrList(miid);
		//장바구니에 주소 넣어야하니 MemberAddr형 어레이리스트
		//현재 로그인한 회원의 주소 목록을 ArrayList로 받아옴 
		System.out.println(select);
		System.out.println(from);
		System.out.println(where);
		
		request.setAttribute("pdtList", pdtList);
		request.setAttribute("addrList", addrList);
		//리퀘스트에 구매할 상품목록 어레이리스트 pdt리스트 저장.
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("order/order_form.jsp");
		// 폼으로 보낸다.
		dispatcher.forward(request, response);
		//디스패처로 포워딩..1.url이 안변함. 2.이전파일이갖고있는 request랑 response 자기가 가져감
		
	}

}

