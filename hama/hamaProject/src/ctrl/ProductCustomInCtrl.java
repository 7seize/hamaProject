package ctrl;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@MultipartConfig(
		fileSizeThreshold=0,
		location="E:/ldh/web/work/hamaProject/WebContent/product/upload"
		// 물리적인 절대경로를 찍어 놓은 것  그러면 업로드한 파일이 여기에 들어가 있다. 경로명 다들 바꿔야함 
)
@WebServlet("/product_custom_in")
public class ProductCustomInCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductCustomInCtrl() {super();  }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");	
		//프린터
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//검사할것. 1.마카롱이름검사. 2.로그인검사  3. 토핑검사. 4. 파일검사

		
		//1. 마카롱이름검사. jsp에서 onsubmit(true) 해서 검사 안해도 됨 
		String name = request.getParameter("name");
		//if(name==null||name.equals("")) {
			//out.println("<script>");
			//out.println("alert('마카롱 이름을 입력해주세요.');");
			//out.println("history.back();");
			//out.println("</script>");
			//out.close();
		//}
		
		//2. 로그인검사
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");	
		if(loginInfo == null) {
			
			out.println("<script>");
			out.println("alert('로그인 이후 사용하실 수 있습니다.');");
			out.println("location.replace('login_form.jsp?url=product_custom_form');");
			out.println("</script>");
			out.close();
		}
		String miid = loginInfo.getMi_id();
		
		
		
		//3. 토핑검사
		int toppingcnt=Integer.parseInt(request.getParameter("toppingcnt"));
		//토핑 선택 수 (토핑 안골랐으면0 / 1 / 2)
		String tp1 = ""; String tp2 = ""; //토핑아이디를  저장할 변수 
		
		if(toppingcnt==1) { //토핑 하나면,
			tp1 = request.getParameter("topping"); 
		}else if(toppingcnt==2) { //토핑 두 개일때는 배열로 받아온다.
			String[] tp =  request.getParameterValues("topping");
			tp1 = tp[0];
			tp2 = tp[1];	
		}
			
			
		//4.파일검사
		String isFile = request.getParameter("isFile");
		String  uploadFileName = ""; //유저가 올린 레터링 이미지 이름을 저장할 변수 
		if(isFile.equals("y")) { //유저가 레터링을 업로드 했을 때만 했으면	
			//유저가 업로드한 레터링 파일 
			Part part = request.getPart("file1");
			//업로드 되는 파일을 Part형 인스턴스에 저장 

			String contentDisposition = part.getHeader("content-disposition");
			//예) form-data; name="file1"; filename="업로드할파일명.확장자"	
			System.out.println("contentDisposition : "+ contentDisposition);
			// 확인용콘솔로그
			uploadFileName = getUploadFileName(contentDisposition);
			//uploadFileName = 파일 이름
			part.write(uploadFileName); //파일저장
		}
		
		
		
		//공통 빌 수가 없는 당도,비건옵션,필링양, 마카롱맛(맛에따른아이디) 변수에 담기
		int sweet =Integer.parseInt(request.getParameter("sweet"));
		String vg =request.getParameter("vg");
		String pl =request.getParameter("pl");
		String taste =request.getParameter("taste");
		
		
		//확인용
		System.out.println( "ProductCustomInCtrl 확인"+
				"/이름=" + name +
				"/맛=" +  taste+ 
				"/당도=" +  sweet+ 
				"/비건옵션=" + vg + 
				"/필링양=" +  pl+ 
				"/토핑1=" + tp1 + 
				"/토핑2=" + tp2 + 
				"/레터링 이미지=" + uploadFileName + 
				"/아이디=" + miid 
		);
		
		
		//담아야할 게 많으니까 ProductCustom형 인스턴스로 제작해서 가져감
		ProductCustom pc =  new ProductCustom();
		pc.setMi_id(miid);
		pc.setPmc_name(name);
		pc.setPmc_sugar(sweet);
		pc.setPmc_vg(vg);
		pc.setPmc_pl(pl);
		pc.setPmc_img(uploadFileName);
		pc.setPmc_tp1(tp1);
		pc.setPmc_tp2(tp2);
		pc.setPi_id(taste);
		
		ProductCustomSvc productCustomSvc = new ProductCustomSvc();	
		int result = 0;
		result = productCustomSvc.customInsert(miid,pc);
	
		//result 결과에 따라 보내는거 달라짐 
		
		if(result!=1) {
			out.println("<script>");
			out.println("alert('커스텀 마카롱이 마카롱이 등록되지 않았습니다. \\n 다시 시도해주세요.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		
		response.sendRedirect("product_custom_list"); // 글번호
		
		
		
	}
	//올린 파일의 이름을 가져다 주는데 필요한 메소드 건들x
	private String getUploadFileName(String contentDisposition){
		
		String uploadFileName = null;
		String[] contentSplitStr = contentDisposition.split(";");
		//세미 콜론을 기준으로 split
		
		int fIdx = contentSplitStr[2].indexOf("\""); //따옴표로 위치를 찾음 
		int sIdx = contentSplitStr[2].lastIndexOf("\""); //마지막따옴표위치를찾음
		
		uploadFileName = contentSplitStr[2].substring(fIdx+1, sIdx);
		return uploadFileName;
		//예) contentSplitStr[2]는 세미콜론으로 잘랐기 때문에 filename="업로드할파일명.확장자" 
		//가 되고 그러면 첫번째 " 와 마지막 "를 찾아서 잘라오는 거가 업로드할파일명.확장자고
	// 결국 업로드할파일명.확장자 = uploadFileName 이 된다 
	}

}
