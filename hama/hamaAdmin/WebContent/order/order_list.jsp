<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.time.*" %>
<%@ page import = "vo.*" %>     
<%@ include file="../_inc/header.jsp" %>

<%
request.setCharacterEncoding("utf-8");

ArrayList<OrderInfo> orderInfo  = (ArrayList<OrderInfo>)request.getAttribute("orderList");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");

int bsize = pageInfo.getBsize(), cpage = pageInfo.getCpage();
int psize= pageInfo.getPsize(), pcnt= pageInfo.getPcnt();
int spage = pageInfo.getSpage(), rcnt= pageInfo.getRcnt();


String kindorder = (String)request.getAttribute("kindorder");
String desc = (String)request.getAttribute("desc");
String startdate = (String)request.getAttribute("startdate");
String enddate = (String)request.getAttribute("enddate");
String schtype= pageInfo.getSchtype();
String keyword= pageInfo.getKeyword();
String lnkOrder = "order?";
String schargs = "", args=""; 

if(kindorder == null) kindorder ="";
if(desc == null) desc = "";
if(schtype != null && !schtype.equals("") && 
	keyword != null && !keyword.equals("") ){
	schargs = "&schtype=" + schtype + "&keyword=" + keyword;
}
args = "&cpage=" + cpage +  schargs;
System.out.println("startdate"+ startdate);

if(startdate == null ) startdate ="";
if(!startdate.equals("")) schargs += "&startdate=" +startdate;
if(enddate == null ) enddate ="";
if(!enddate.equals("")) schargs += "&enddate=" +enddate;
lnkOrder +=schargs;
%>
<link rel="stylesheet" href="/hamaAdmin/css/order_list.css">
<div class="container">

	
<script>
let sch = "idx"
const schValue = function(val) {
	sch = val.value;
}

const statusVal = function (val) {
	let arr = val.value.split(",");
	let status = arr[0];
	let oiid = arr[1];
	
	$.ajax({
		type : "POST",
		url : "/hamaAdmin/order",
		data : {"status" : status, "oiid" : oiid},
		success : function(chkRs){
			if(chkRs==0){
				alert("상태 변경에 실패했습니다. \n 다시 시도하세요");
				return;
			}
			location.reload(); //새로고침 
		}
	});
}
</script>
	<h2>주문 내역</h2>
	<hr/>
	<form name="frmSch" method="get" style="margin-bottom: 15px" >
		<select name="schtype" onchange="schValue(this)" >
			<option value = "all" <%if(schtype!=null && schtype.equals("all")){%> selected = "selected" <%} %>>전체</option>
			<option value = "idx" <%if(schtype!=null && schtype.equals("idx")){%> selected = "selected" <%} %>>주문 번호</option>
			<option value = "uid" <%if(schtype!=null && schtype.equals("uid")){%> selected = "selected" <%} %>>고객 아이디</option>
			<option value = "name" <%if(schtype!=null && schtype.equals("name")){%> selected = "selected" <%} %>>고객 이름</option>
		</select>
		<input type = "search" name = "keyword" placeholder="검색" value="<%=keyword %>" />
		<input type="submit"  value="검색" />
		<div>
			<label for="startdate"> </label>
			<input name="startdate" type="date" value="<%=startdate %>" >
			<label for="enddate"> - </label>
			<input name="enddate" type="date" value="<%=enddate %>" >
		</div>
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
if(orderInfo.size()>0){ //게시글 목록이 있으면


for(int i = 0; i < orderInfo.size(); i++){ 
	OrderInfo oi = orderInfo.get(i);
	ArrayList<OrderDetail> orderDetailList = oi.getDetailList();
	OrderDetail od = null;
	if(orderDetailList.size()>0){
		od = orderDetailList.get(0);
	}
%>
		<tr class="chcolor"  onclick="location.href='/hamaAdmin/orderview?oiid=<%=oi.getOi_id() %>'" style="cursor: pointer;" >
			<td><%=oi.getOi_id() %></td>
<%if(od != null){ %>
			<td style="text-align: left;"><%=od.getOd_name()%><%if(orderDetailList.size()>1){%> 외 <%=orderDetailList.size()%>개<%}  %></td>
<%}else{ %><td>-</td>
<%} %>
			<td><%=oi.getMi_id() %></td>
			<td><%=oi.getOi_sender() %></td>
			<%if(oi.getOi_payment().equals("c")){%><td>신용카드</td>
            <%}else if(oi.getOi_payment().equals("b")){%><td>무통장 입금</td>
            <%}else if(oi.getOi_payment().equals("a")){%><td>계좌이체</td><%} %>
			<td><%=oi.getOi_pay() %></td>
			<td>
				<select onchange="statusVal(this)" >
			        <option value="a,<%=oi.getOi_id() %>" <%if(oi.getOi_status().equals("a")){%>selected="selected"<%} %> >배송 준비중</option>
			        <option value="b,<%=oi.getOi_id() %>" <%if(oi.getOi_status().equals("b")){%>selected="selected"<%} %> >배송중</option>
			        <option value="c,<%=oi.getOi_id() %>" <%if(oi.getOi_status().equals("c")){%>selected="selected"<%} %> >배송 완료</option>
			        <option value="d,<%=oi.getOi_id() %>" <%if(oi.getOi_status().equals("d")){%>selected="selected"<%} %> >환불 요청</option>
			        <option value="e,<%=oi.getOi_id() %>" <%if(oi.getOi_status().equals("e")){%>selected="selected"<%} %> >환불 완료</option>
			    </select>
			</td>
			<td><%=oi.getOi_date() %></td>
			<%if(oi.getOi_date().equals(oi.getOi_redate())){ %>
            	<td> - </td>
            <%}else{%>
            	<td><%=oi.getOi_redate().substring(0,10) %></td>
            <%} %>
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
	String lnk = "order?cpage=";
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

	spage = (cpage-1)/bsize * bsize + 1; //현재 블록에서의 시작 페이지 번호 
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