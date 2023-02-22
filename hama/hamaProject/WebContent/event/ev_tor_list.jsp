<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>

<%
// 받아오기
request.setCharacterEncoding("utf-8");

ArrayList<EvCusTor> torList  = 
(ArrayList<EvCusTor>)request.getAttribute("torList");

EvCusTor ect = (EvCusTor)request.getAttribute("ect");
String kind = request.getParameter("kind");
String o = request.getParameter("o");

LocalDate today = LocalDate.now();
int	cYear = today.getYear(); // 현재 연도
int cMonth = today.getMonthValue();// 현재 월 월



String args = "";	//쿼리스트링(페이지 번호)을 저장할 변수
String oargs = "";


if (o != null && !o.equals("")){	oargs += "&o=" + o ;}else{ o = "a";}; // 정렬방식 a일경우 b일 경우	//기본이 득표순으로 설정
if (kind != null && !kind.equals("")) args += "&kind=" + kind; 			// kind a일경우 투표하러가기 / b일경우 레시피 구경
String lnk = "ev_tor_list?" + args;		// ev_tor_list? + kind=b	
args += oargs;							





%>
<style>
.torO {margin:0 auto; width:60%;}
#list {margin: 100px auto;}
#list tr{height : 30px; }
#list th,#list td{padding : 8px 3px;} 
#list th{border-bottom:double black 3px;}

</style>








<div class="torO"><!-- 득표순, 등록역순  정렬--><!-- 제목 받아오기, 연 월 추출 -->
<% if(  kind.equals("a") ){ %>
	<h2 align="center" margin-top="30px" >★  <%=cYear %>년 <%=cMonth %>월 커스텀 레시피 토너먼트 ★</h2>
	
	<select name="o" onchange="location.href='<%=lnk%>&o=' + this.value;">
		<option value="a" <% if (o.equals("a")) { %>selected="selected"<% } %>>득표순</option>
		<option value="b" <% if (o.equals("b")) { %>selected="selected"<% } %>>최신순</option>
	</select>

<% } else {%><!-- 이번달 제외한 이전까지의 레시피가 보여야 함/무조건 인기순/100개만 -->
	<h2 align="center" margin-top="30px" >★  ~Top100~ 명예의 전당 ★</h2>
<% } %>

<hr/>


<form name="frmSch" method="post">
<input type="hidden" name="kind" value="<%=kind %>" />

<table width="100%" cellpadding="5" cellspacing="0" id="list">
<%
	String img = "noimg.PNG";
	boolean letterimg = false;
	for ( int i  = 0 ; i < torList.size() ; i++) { //루프시작
		ect = torList.get(i);
		lnk = "ev_tor_view?ectidx=" + ect.getEct_idx() + "&pmcidx=" + ect.getPmc_idx() +args;
		if(ect.getEct_img1()!=null && !ect.getEct_img1().equals("")) {
			//이미지관련처리해야함
			letterimg = true;
			img = ect.getEct_img1();
		}
		System.out.println("lnk :" + lnk);
		
	
		if (i % 4 == 0)	out.println("<tr>");
%>
	<td width="25%" align="center" onmouseover="this.bgColor='#efefef';" onmouseout="this.bgColor='';">
		<div class="coustom_img">
			
			<a href="<%=lnk %>" class="coustomImg" onclick="view(<%=ect.getEct_idx()%>);" width="30" />
			<br/>
			<% if(  o.equals("a") ){ //인기순 정렬일 때 %> 
				<p><%=i+1 %>위 (<%=ect.getEct_vote()%>표)</p>
			
			<% }%>		
				<img src="/hamaProject/event/upload/<%=img%>"  width="150" height="150" border="0" />
				<br />
				<%=ect.getEct_title() %>
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

</td>

<% if(  kind.equals("a") ){ //토너먼트 투표 창일 때만  %> 
	<input type="button" value="레시피 제출" onclick="location.href='ev_tor_form';" />		
<% }%>	

<td width="*">
	
</td>
</tr>
</table> 
</form>
<%@ include file="../_inc/footer.jsp" %>
</body>
</html>














