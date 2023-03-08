<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.time.*" %>
<%@ page import = "vo.*" %>     
<%@ include file="../_inc/header.jsp" %>

<%
request.setCharacterEncoding("utf-8");

ArrayList<OrderInfo> orderInfo  = (ArrayList<OrderInfo>)request.getAttribute("orderList");
ArrayList<OrderRefund> orderRefund  = (ArrayList<OrderRefund>)request.getAttribute("orderRefund");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");

int bsize = pageInfo.getBsize(), cpage = pageInfo.getCpage();
int psize= pageInfo.getPsize(), pcnt= pageInfo.getPcnt();
int spage = pageInfo.getSpage(), rcnt= pageInfo.getRcnt();


String kindorder = (String)request.getAttribute("kindorder");
String desc = (String)request.getAttribute("desc");
String schtype= pageInfo.getSchtype();
String keyword= pageInfo.getKeyword();
String lnkOrder = "orderrefund?";
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
<link rel="stylesheet" href="/hamaAdmin/css/order_refund_list.css">
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
	<h2>환불 문의</h2>
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
	</form>
	<table width="100%" cellpadding="5" align="center">
		<tr class="ta_title">
			<th><input type="checkbox" name="all" onclick="chkAll(this)" ></th>
			<th>주문 번호</th>
			<th>고객 아이디</th>
			<th>고객 연락처</th>
			<th>환불 내역</th>
			<th>환불 사유</th>
			<th>환불 금액</th>
			<th>환불 상태</th>
		</tr>
<%
if(orderInfo.size()>0){ //게시글 목록이 있으면
for(int i = 0; i < orderInfo.size(); i++){ 
	OrderInfo oi = orderInfo.get(i);
	OrderRefund or = orderRefund.get(i);
	ArrayList<OrderDetail> orderDetailList = oi.getDetailList();
	OrderDetail od = null;
	if(orderDetailList.size()>0){
		od = orderDetailList.get(0);
	}
%>
		<tr class="chcolor" onclick="" style="cursor: pointer;">
			<td class="unmove" ><input class="unmove" type="checkbox" name="chk" ></td>
			<td><%=oi.getOi_id() %></td>
			<td><%=oi.getMi_id() %></td>
			<td><%=oi.getOi_phone() %></td>
<%if(od != null){ %>
			<td style="text-align: left;"><%=od.getOd_name()%><%if(orderDetailList.size()>1){%> 외 <%=orderDetailList.size()%>개<%}  %></td>
<%}else{ %><td>-</td>
<%} %>
			
			<%if(or.getOr_reas().equals("a")) {%><td>상품 분실</td>
			<%}else if(or.getOr_reas().equals("b")){ %><td>오배송</td>
			<%}else if(or.getOr_reas().equals("c")){ %><td>상품 누락</td>
			<%}else if(or.getOr_reas().equals("d")){ %><td>상품 파손</td>
			<%}else if(or.getOr_reas().equals("e")){ %><td>단순 변심</td><%} %>
			<td><%=or.getOr_pay() %></td>
			<td id="sel" class="unmove" >
				<select class="unmove" onchange="statusVal(this)" >
			        <option value="a,<%=oi.getOi_id() %>" <%if(or.getOr_status().equals("a")){%>selected="selected"<%} %> >환불 대기</option>
			        <option value="b,<%=oi.getOi_id() %>" <%if(or.getOr_status().equals("b")){%>selected="selected"<%} %> >환불 완료</option>
			        <option value="c,<%=oi.getOi_id() %>" <%if(or.getOr_status().equals("c")){%>selected="selected"<%} %> >환불 취소</option>
			    </select>
			</td>
		</tr>
<%}
}else{ //게시글 목록이 없으면
	out.print("<tr><td colspan='8' >");
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
	String lnk = "orderrefund?cpage=";
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