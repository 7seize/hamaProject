<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.time.*" %>
<%@ page import = "vo.*" %>     
<%@ include file="../_inc/header.jsp" %>

<%
request.setCharacterEncoding("utf-8");

ArrayList<ProductCustom> productCustom  = (ArrayList<ProductCustom>)request.getAttribute("productCustom");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");

int bsize = pageInfo.getBsize(), cpage = pageInfo.getCpage();
int psize= pageInfo.getPsize(), pcnt= pageInfo.getPcnt();
int spage = pageInfo.getSpage(), rcnt= pageInfo.getRcnt();

String kindorder = (String)request.getAttribute("kindorder");
String desc = (String)request.getAttribute("desc");
String schtype= pageInfo.getSchtype();
String keyword= pageInfo.getKeyword();
String lnkOrder = "productcus?";
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
<link rel="stylesheet" href="/hamaAdmin/css/product_add.css">
<div class="container">
	
<script>

</script>
	<h2>주문 내역</h2>
	<hr/>
	<form name="frmSch" method="get" style="margin-bottom: 15px" >
		<select name="schtype" onchange="schValue(this)" >
			<option value = "all" <%if(schtype!=null && schtype.equals("all")){%> selected = "selected" <%} %>>전체</option>
			<option value = "id" <%if(schtype!=null && schtype.equals("id")){%> selected = "selected" <%} %>>주문 번호</option>
			<option value = "topping" <%if(schtype!=null && schtype.equals("topping")){%> selected = "selected" <%} %>>고객 아이디</option>
			<option value = "name" <%if(schtype!=null && schtype.equals("name")){%> selected = "selected" <%} %>>고객 이름</option>
		</select>
		<input type = "search" name = "keyword" placeholder="검색" value="<%=keyword %>" />
		<input type="submit"  value="검색" />

	</form>
	<table width="100%" cellpadding="5" align="center">
		<tr class="ta_title">
			<th>주문 번호</th>
			<th>주문 내역</th>
			<th>고객 아이디</th>
			<th>고객 이름</th>
			<th>결제 방식</th>
			<th>결제 금액</th>
			<th> <%if(kindorder.equals("status")&&desc.equals("desc")){%><a href="<%=lnkOrder%>&kindorder=status&desc=asc">주문 상태 ⯅</a><%}else{%><a href="<%=lnkOrder%>&kindorder=status&desc=desc">주문 상태 ⯆</a><%} %></th>
			<th> <%if(kindorder.equals("date")&&desc.equals("desc")){%><a href="<%=lnkOrder%>&kindorder=date&desc=asc">접수 시간 ⯅</a><%}else{%><a href="<%=lnkOrder%>&kindorder=date&desc=desc">접수 시간 ⯆</a><%} %></th>
			<th>희망 배송일</th>
		
		</tr>
<%
if(productCustom.size()>0){ //게시글 목록이 있으면


for(int i = 0; i < productCustom.size(); i++){ 
	ProductCustom pc = productCustom.get(i);

%>
		<tr class="chcolor" onclick="" style="cursor: pointer;">
			<td><%=pc.getPmc_idx() %></td>
		</tr>
<%}
}else{ //게시글 목록이 없으면
	out.print("<tr><td colspan='5' >");
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
if(rcnt>0){ //게시글이 있으면 - 페이징 영역을 보여줌 
	String lnk = "productcus?cpage=";
	pcnt = rcnt / psize;
	if(rcnt % psize>0) pcnt++; //전체 페이지 수(마지막 페이지 번호이기도 함)
	
	//앞부분 페이징
	if(cpage == 1){
		//1페이지가 0이면
		out.println("[처음]&nbsp;&nbsp;&nbsp;[이전]&nbsp;&nbsp;&nbsp;");
	}else{
		out.println("<a href='" + lnk + 1 + schargs + order + "'>[처음]</a>&nbsp;&nbsp;&nbsp;");
		out.println("<a href='" + lnk + (cpage-1) + schargs + order + "'>[이전]</a>&nbsp;&nbsp;");
	}

	spage = (cpage-1)/bsize * bsize 
			+ 1; //현재 블록에서의 시작 페이지 번호 
	for(int i=1, j=spage; i<=bsize && j<= pcnt; i++, j++){
		//i :  블록에서 보여줄 페이지의 개수만큼 루프를 돌릴 조건으로 사용된느 변수
		//j : 실제 출력할 페이지 번호로 전체 페이지 개수(마지막 페이지) 를 넘지 않도록 사용해야함
		if(cpage == j){ //현재페이지면 강조만
			out.println("&nbsp;<strong>" + j + "</strong>&nbsp;" );
		}else{ //다른페이징이면 링크 걸려서 
			out.print("&nbsp;<a href='" + lnk + j + schargs + order +"'>" );
			out.println(j + "</a>&nbsp;" );
		}	
	}
	
	
	
	//뒷부분페이징
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