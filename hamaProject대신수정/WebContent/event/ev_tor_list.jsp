<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>

<%
// 받아오기
request.setCharacterEncoding("utf-8");

ArrayList<EvCusTor> torList  = 
(ArrayList<EvCusTor>)request.getAttribute("torList");

PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");

//EvCusTor ect = null;
EvCusTorPoll ectp = (EvCusTorPoll)request.getAttribute("ectp");
EvCusTor ect = (EvCusTor)request.getAttribute("ect");
String kind = request.getParameter("kind");

System.out.println("torList :" +torList);
System.out.println("ect :" +ect);
System.out.println("pageInfo :" +pageInfo);
System.out.println("kind :" + kind);

int bsize = pageInfo.getBsize();	//블록크기
int cpage = pageInfo.getCpage();	//현재페이지 정보
int psize= pageInfo.getPsize();		//페이지 크기
int pcnt= pageInfo.getPcnt();		//페이지 개수
int spage = pageInfo.getSpage();	//
int rcnt= pageInfo.getRcnt();		//게시글 게수

String  args ="";	//쿼리스트링(페이지 번호)을 저장할 변수
String oargs = "";
String vargs = "" ;

// 쿼리 스트링
String o = pageInfo.getO();

System.out.println("o :" + o);

if (o != null && !o.equals("")){	oargs += "&o=" + o ;}else{ o = "a";}; // 정렬방식 a일경우 b일 경우	//기본이 득표순으로 설정
if (kind != null && !kind.equals("")) args += "kind=" + kind; 			// kind a일경우 투표하러가기 / b일경우 레시피 구경
String lnk = "ev_tor_list?" + args;		// ev_tor_list? + kind=b
args = "&cpage=" + cpage + oargs ;		//&cpage=1 + &o= + b

//args += oargs;							////oargs???


%>
<style>
.torO {margin:0 auto; width:60%;}
#list {margin: 100px auto;}
#list tr{height : 30px; }
#list th,#list td{padding : 8px 3px;} 
#list th{border-bottom:double black 3px;}
#list td{border-bottom:dotted black 1px;}
</style>


<div class="torO"><!-- 득표순, 등록역순  정렬-->
<p align="right">
		<select name="o" onchange="location.href='<%=lnk%>&o=' + this.value;">
			<option value="a" <% if (o.equals("a")) { %>selected="selected"<% } %>>득표순</option>
			<option value="b" <% if (o.equals("b")) { %>selected="selected"<% } %>>최신순</option>
		</select>
</p>
<hr/>
<form name="frmSch" method="post">
<input type="hidden" name="kind" value="<%=kind %>" />

<table width="100%" cellpadding="5" cellspacing="0" id="list">
<%

	for ( int i  = 0 ; i < torList.size() ; i++) { //루프시작
		ect = torList.get(i);
		lnk = "ev_tor_view?ectidx=" + ect.getEct_idx() + args;
		
		System.out.println("lnk :" + lnk);
		
	//	lnk += "&ectpidx=" + ectp.getEctp_idx();
	//	lnk += "&pmcidx=" + ectp.getPmc_idx();
		if (i % 4 == 0)	out.println("<tr>");
%>
	<td width="25%" align="center" onmouseover="this.bgColor='#efefef';" onmouseout="this.bgColor='';">
		<div class="coustom_img">
			
			<a href="<%=lnk %>" class="coustomImg" onclick="view(<%=ect.getEct_idx()%>);" width="30" />
				<img src="/hamaProject/event/ev_img/macadamia.png"  width="150" height="150" border="0" />
				<br /><%=ect.getEct_title() %>
			</a>
		</div>
	</td>
<%
		if (i % 4 == 3) out.println("</tr>");
	}
	int i = 0;
	if (i % 4 > 0  ) {
		for (int j = 0 ; j < (4 - (i % 4)) ; j++)
			out.println("<td width='25%'></td>");
		out.println("</tr>");
	}
	
%>
</table> 


<table width="700" cellpadding="5">
<tr>
<td width="600">
<%
if(rcnt>0){ //게시글이 있으면 - 페이징 영역을 보여줌 
	//String imgGall = "/hamaProject/product/pdt_img/mc100.png";
	//if (v.equals("g"))	imgGall = "/mvcSite/img/ico_g_on.png";
	lnk = "ev_tor_list?cpage=";
	pcnt = rcnt / psize;
	if(rcnt % psize>0) pcnt++; //전체 페이지 수(마지막 페이지 번호이기도 함)
	
	//앞부분 페이징
	if(cpage == 1){
		//1페이지가 0이면
		out.println("[처음]&nbsp;&nbsp;&nbsp;[이전]&nbsp;&nbsp;&nbsp;");
	}else{
		out.println("<a href='" + lnk + 1  + "'>[처음]</a>&nbsp;&nbsp;&nbsp;");
		out.println("<a href='" + lnk + (cpage-1)  + "'>[이전]</a>&nbsp;&nbsp;");
	}
	
	
	//중간페이징  1.2..3..4..5...11..12..13..14...15..16...
	//페이징은 그냥 복사해서 쓰기 
	spage = (cpage-1)/bsize * bsize + 1; //현재 블록에서의 시작 페이지 번호 
	int j , k = 0;
	for(  j =1, k=spage; j<=bsize && k<= pcnt; j++, k++){
		//j :  블록에서 보여줄 페이지의 개수만큼 루프를 돌릴 조건으로 사용된느 변수
		//k : 실제 출력할 페이지 번호로 전체 페이지 개수(마지막 페이지) 를 넘지 않도록 사용해야함
		if(cpage == k){ //현재페이지면 강조만
			out.println("&nbsp;<strong>" + k + "</strong>&nbsp;" );
		}else{ //다른페이징이면 링크 걸려서 
			out.print("&nbsp;<a href='" + lnk + k  +"'>" );
			out.println(j + "</a>&nbsp;" );
		}	
	}

	//뒷부분페이징
	if(cpage == pcnt){
		out.println("&nbsp;&nbsp;[다음]&nbsp;&nbsp;&nbsp;[마지막]");
	}else{
		out.println("&nbsp;&nbsp;<a href='" + lnk + (cpage+1)  + "'>[다음]</a>");
		out.println("&nbsp;&nbsp;&nbsp;<a href='" + lnk + pcnt  + "'>[마지막]</a>");
	}

}
//System.out.println("test3");
%>
</td>
<td width="*">
	<input type="submit" value="레시피 제출" onclick="ev_tor_form.jsp"/>
</td>
</tr>
</table> 
</form>
<%@ include file="../_inc/footer.jsp" %>
</body>
</html>














