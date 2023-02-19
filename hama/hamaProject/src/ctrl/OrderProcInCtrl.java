package ctrl;

import static db.JdbcUtil.commit;
import static db.JdbcUtil.rollback;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/order_proc_in")
public class OrderProcInCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public OrderProcInCtrl() {super();}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		
		if(loginInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�옒紐삳맂 寃쎈줈濡� �뱾�뼱�삤�뀲�뒿�땲�떎. 濡쒓렇�씤�븯�꽭�슂.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		String miid = loginInfo.getMi_id(); 
		String kind = request.getParameter("kind");
		int total = Integer.parseInt(request.getParameter("total"));

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
		String where = request.getParameter("where");


		OrderInfo oi = new OrderInfo();
		oi.setOi_sender(orderName);
		oi.setOi_sephone(orderPhone);
		oi.setMi_id(miid);
		oi.setOi_name(rname); //�닔痍⑥씤�씠由�
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
		String result = null, temp = "";
		System.out.println("sdsddddddddddddddddddddsdsd" + request.getParameter("ocidxs"));
		if(kind.equals("c")) { 
			temp = request.getParameter("ocidxs");
			
		}else { 
			temp = where;
		}
		result = orderProcInSvc.orderInsert(kind, oi, temp);
		String[] arr = result.split(",");
		
		if(arr[1].equals(arr[2])) {
			
			response.sendRedirect("order_end?oiid=" + arr[0]);
			
			//洹쇰뜲 二쇰Ц踰덊샇 媛��졇媛�湲� /蹂댁븞�긽 臾몄젣媛� �뾾�굹? post濡� 蹂대궡二쇰㈃ �맆�뀗�뜲 洹몃젃寃� 蹂대궡�젮硫� 
			//url�넀�쑝濡� 移섎뒗嫄� �떎 get諛⑹떇 form�깭洹� �벐硫� post�씤�뜲 瑗쇱닔媛�...
			//out.println�쑝濡� �뒪�겕由쏀듃.document.frm.submit�븯怨� form �궗吏꾩갭怨�
			//order_proc_in_ctrl_oiidhowto...李멸퀬 
			//洹몃젃寃뚰븯硫� 二쇰Ц踰덊샇瑜� �닲湲� 梨꾨줈 媛��졇媛� �닔 �엳�쓬 �옣�젏:二쇰Ц踰덊샇�닲源�
			//�떒�젏 : sendRedirect�뿉 鍮꾪빐 �솕�떎媛붾떎�븿..洹몃옒�꽌 sendRedirect�븯湲곕줈�븿
			//二쇰Ц踰덊샇瑜� 荑쇰━�뒪�듃留곸씠 �븘�땶 post諛⑹떇�쑝濡� 蹂대궡 �닲湲곕뒗 諛⑸쾿 
			
			
		}	
		else{
			//�젙�긽�쟻�쑝濡� 援щℓ媛� �씠猷⑥뼱吏�吏� �븡�븯�쑝硫� 
			//�씠�긽�깭�뒗 寃곗젣�뒗 �릺�뿀�뒗�뜲 荑쇰━�뒗 �븞�뱾�뼱媛� �긽�깭
			//�뼱�뱶誘쇱쓣 �뼱�뼸寃� 留뚮뱾�뿀�뒗吏��뿉 �뵲�씪 cs���뿉�꽌 �슂泥��씠 �뱾�뼱�삱 寃�.
			
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('援щℓ媛� �젙�긽�쟻�쑝濡� 泥섎━�릺吏� �븡�븯�뒿�땲�떎. \\n 怨좉컼 �꽱�꽣�뿉 臾몄쓽�븯�꽭�슂. ');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		
	}

}








