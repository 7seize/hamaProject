<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.time.*" %>
<%@ page import = "vo.*" %>     
<%@ include file="../_inc/header.jsp" %>

<%
request.setCharacterEncoding("utf-8");

ArrayList<MemberQna> memberQna  = (ArrayList<MemberQna>)request.getAttribute("memberQna");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");

int bsize = pageInfo.getBsize(), cpage = pageInfo.getCpage();
int psize= pageInfo.getPsize(), pcnt= pageInfo.getPcnt();
int spage = pageInfo.getSpage(), rcnt= pageInfo.getRcnt();


String kindorder = (String)request.getAttribute("kindorder");
String desc = (String)request.getAttribute("desc");
String schtype= pageInfo.getSchtype();
String keyword= pageInfo.getKeyword();
String lnkOrder = "memberqna?";
String schargs = "", args=""; 

if(kindorder == null) kindorder ="";
if(desc == null) desc = "";
if(schtype != null && !schtype.equals("") && 
	keyword != null && !keyword.equals("") ){
	schargs = "&schtype=" + schtype + "&keyword=" + keyword;
}
args = "&cpage=" + cpage +  schargs;

lnkOrder +=schargs;
%>
<link rel="stylesheet" href="/hamaAdmin/css/member_qna.css">
<div class="container">
	
<script>
let sch = "idx"
	const schValue = function(val) {
		sch = val.value;
	}

	function chkAll(all){
		let chk = document.getElementsByName('chk');
		
		for(let i=0; i < chk.length; i++){
			chk[i].checked = all.checked;
		}
	}

</script>
	<h2>1대1 문의</h2>
	<hr/>
	<form name="frmSch" method="get" style="margin-bottom: 15px" >
		<select name="schtype" onchange="schValue(this)" >
			<option value = "all" <%if(schtype!=null && schtype.equals("all")){%> selected = "selected" <%} %>>전체</option>
			<option value = "idx" <%if(schtype!=null && schtype.equals("idx")){%> selected = "selected" <%} %>>고객 아이디</option>
			<option value = "name" <%if(schtype!=null && schtype.equals("name")){%> selected = "selected" <%} %>>고객 이름</option>
		</select>
		<input type = "search" name = "keyword" placeholder="검색" value="<%=keyword %>" />
		<input type="submit"  value="검색" />
	</form>
	<table width="100%" cellpadding="5" align="center">
		<tr class="ta_title">
			<th><input type="checkbox" name="all" onclick="chkAll(this)" ></th>
			<th>회원 아이디</th>
			<th>회원 이름</th>
			<th>분류</th>
			<th>제목</th>
			<th>질문 일자</th>
			<th>사진 여부</th>
			<th>답변 여부</th>
			<th>상세 정보</th>
		</tr>
<%
if(memberQna.size()>0){ //게시글 목록이 있으면
for(int i = 0; i < memberQna.size(); i++){ 
	MemberQna mq = memberQna.get(i);

%>
		<tr class="chcolor" onclick="" style="cursor: pointer;">
			<td class="unmove" ><input class="unmove" type="checkbox" name="chk" ></td>
			<td><%=mq.getMi_id() %></td>
			<td><%=mq.getMi_name() %></td>
			<%if(mq.getBq_ctgr() !=null){ 
				if(mq.getBq_ctgr().equals("a")){%><td>회원</td><%}else if(mq.getBq_ctgr().equals("b")){ %><td>상품</td><%}else if(mq.getBq_ctgr().equals("c")) {%><td>주문/결제</td><%}else if(mq.getBq_ctgr().equals("d")) {%><td>기타</td><%} %>
			<%} %>
			
			<%if(mq.getBq_title() !=null && mq.getBq_title().length() >= 12 ) {%><td style="text-align: left;"><%=mq.getBq_title().substring(0,10) + "..." %></td><%}else{ %><td style="text-align: left;"><%=mq.getBq_title()%></td><%} %>			
			<td><%=mq.getBq_qdate() %></td>
			<%if((mq.getBq_img1() != null && !mq.getBq_img1().equals("") )||(mq.getBq_img2() != null && !mq.getBq_img2().equals("") )) {%><td>Y</td><%}else{ %><td>N</td><%} %>
			<td><%=mq.getBq_isanswer().toUpperCase() %></td>
			<td><input type="button" value="상세 내역" ></td>
		</tr>
<%}
}else{ //게시글 목록이 없으면
	out.print("<tr><td colspan='9' >");
	out.print("검색결과가 없습니다. </td></tr> ");
}
%>
	</table>
<table width="700" cellpadding="5" style="margin: 100px auto;" class="page_nav" >
<tr>
<td width="600" style="background: #fff">
<%
String order ="";
if(kindorder !=null && !kindorder.equals("")){
	order = "&kindorder=" + kindorder;
	order += "&desc=" + desc;
}
if(rcnt>0){
	String lnk = "memberqna?cpage=";
	pcnt = rcnt / psize;
	if(rcnt % psize>0) pcnt++;
	if(cpage == 1){
		out.println("[처음]&nbsp;&nbsp;&nbsp;[이전]&nbsp;&nbsp;&nbsp;");
	}else{
		out.println("<a href='" + lnk + 1 + schargs + order + "'>[처음]</a>&nbsp;&nbsp;&nbsp;");
		out.println("<a href='" + lnk + (cpage-1) + schargs + order + "'>[이전]</a>&nbsp;&nbsp;");
	}

	spage = (cpage-1)/bsize * bsize 
			+ 1;
	for(int i=1, j=spage; i<=bsize && j<= pcnt; i++, j++){

		if(cpage == j){
			out.println("&nbsp;<strong>" + j + "</strong>&nbsp;" );
		}else{
			out.print("&nbsp;<a href='" + lnk + j + schargs + order +"'>" );
			out.println(j + "</a>&nbsp;" );
		}	
	}

	if(cpage == pcnt){
		out.println("&nbsp;&nbsp;[다음]&nbsp;&nbsp;&nbsp;[마지막]");
	}else{
		out.println("&nbsp;&nbsp;<a href='" + lnk + (cpage+1) + schargs +order+ "'>[다음]</a>");
		out.println("&nbsp;&nbsp;&nbsp;<a href='" + lnk + pcnt + schargs +order +"'>[마지막]</a>");
	}

}
%>
</div>
</main>

</body>
</html>