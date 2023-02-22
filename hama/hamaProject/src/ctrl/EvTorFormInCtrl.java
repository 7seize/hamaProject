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
		location="E:/ldh/web/work/hamaProject/WebContent/event/upload"
		// 물리적인 절대경로를 찍어 놓은 것  그러면 업로드한 파일이 여기에 들어가 있다. 경로명 다들 바꿔야함 
)
@WebServlet("/ev_tor_form_in")
public class EvTorFormInCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public EvTorFormInCtrl() {super();  }
    
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");	
    	
 
    	//받을거 파일유무 파일, 제목, 내용, 커스텀마카롱 인덱스
    	
		//프린터
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		// 로그인검사
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");	
		if(loginInfo == null) {
			out.println("<script>");
			out.println("alert('로그인 이후 사용하실 수 있습니다.');");
			out.println("location.replace('login_form.jsp?url=ev_tor_form_in');");
			out.println("</script>");
			out.close();
		}
		String miid = loginInfo.getMi_id();
		
		

		//제목
		String title = request.getParameter("title");
		if(title != null) title= getStr(title);
		
		//내용
		String content = request.getParameter("content");
		if(content  != null) content = getStr(content);
			
		//커스텀마카롱 인덱스 
		int pmcidx=Integer.parseInt(request.getParameter("pmcidx"));

	
		//파일저장 및 이름 
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
		
		
		//확인용
		System.out.println( "EvTorFormInCtrl 확인"+
					"/title=" + title +
					"/content=" +  content+ 
					"/pmcidx=" +  pmcidx+ 
					"/uploadFileName=" +  uploadFileName
		);
		
			
		EvCusTor ect = new EvCusTor();
		
		ect.setPmc_idx(pmcidx);
		ect.setMi_id(miid);
		ect.setEct_img1(uploadFileName);
		ect.setEct_title(title);
		ect.setEct_content(content);
		
		
		EvTorFormSvc evTorFormSvc = new EvTorFormSvc();
		
		int result =  evTorFormSvc.torInsert(miid, ect);
		
		if(result!=1) {
			out.println("<script>");
			out.println("alert('토너먼트에 참여되지 않았습니다. \\n 다시 시도해주세요.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		response.sendRedirect("ev_tor_list?kind=a"); 

    	
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
  	private String getStr(String str) {
		//사용자가 입력한 문자열에 대한 처리 해서 리턴하는 메소드
		//대신 무조건무조건 null이 들어가면 안됨 null들어가면 nullpointException나옴 
		return str.trim().replace("'", "''").replace("<", "&lt;");
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
