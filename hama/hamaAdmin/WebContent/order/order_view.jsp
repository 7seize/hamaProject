<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.time.*" %>
<%@ page import = "vo.*" %>     
<%@ include file="../_inc/header.jsp" %>

<%
OrderInfo oi = (OrderInfo)request.getAttribute("orderInfo");

ArrayList <OrderDetail> od = oi.getDetailList();
%>
<link rel="stylesheet" href="/hamaAdmin/css/order_view.css">
<style>

table{border-collapse: collapse;} 
.main_con{
	width: 100%;
}
.main_con >h2{
	margin: 20px 0 10px 0;
	border-bottom: 1px solid rgb(150,150,150);
}
</style>
<script>
function goBack(){
	window.history.back();
}
</script>
	<div class="main_con">
		<h2>주문 상세 내역</h2>
		<div>
			<button onclick="goBack()" >  뒤로가기  </button>
		</div>
		<div class="container"  >
			<table>
		        <tr>
		            <th>주문 일자</th>
		            <td><%= oi.getOi_date() %></td>
		            <th>구매코드</th>
		            <td><%= oi.getOi_id() %></td>
		        </tr>
		        <tr>
		            <th>아이디</th>
		            <td><%= oi.getMi_id() %></td>
		            <th>상태</th>
		            <%if(oi.getOi_status().equals("a")){%><td>배송 준비중</td>
		            <%}else if(oi.getOi_status().equals("b")){%><td>배송중</td>
		            <%}else if(oi.getOi_status().equals("c")){%><td>배송완료</td>
		            <%}else if(oi.getOi_status().equals("d")){%><td>환불 요청</td>
		            <%}else if(oi.getOi_status().equals("e")){%><td>환불 완료</td><%} %>
	
		        </tr>
		        <%if(od.size() == 0){ %>
			        <tr>
			            <th rowspan="1">상품명</th>
			            <td>-</td>
			            <th rowspan="1">개수</th>
			            <td>-</td>
			        </tr>
		        <%}else{ %>
		        	<tr>
		            <th rowspan="<%=od.size()%>">상품명</th>
		            <td><%=od.get(0).getOd_name() %></td>
		            <th rowspan="<%=od.size()%>">개수</th>
		            <td><%=od.get(0).getOd_cnt() %> 개</td>
		        </tr>
		        <%} 
		        if(od.size()  > 1){
		        	for(int i=1; i < od.size(); i++){
		        %>
				        <tr>
				            <td><%=od.get(i).getOd_name() %></td>
				            <td><%=od.get(i).getOd_cnt() %> 개</td>
				        </tr>
		        	<%} %>
		        <%} %>
	
		        <tr>
		            <th>총금액</th>
		            <td><%=oi.getOi_pay() %> 원</td>
		            <th>사용 포인트</th>
		            <td><%=oi.getOi_upoint() %> point</td>
		        </tr>
		        <tr>
		            <th>결제수단</th>
		            <%if(oi.getOi_payment().equals("c")){%><td>신용카드</td>
		            <%}else if(oi.getOi_payment().equals("b")){%><td>무통장 입금</td>
		            <%}else if(oi.getOi_payment().equals("a")){%><td>계좌이체</td><%} %>
		            <th>배송 희망일</th>
		            <%if(oi.getOi_date().equals(oi.getOi_redate())){ %>
		            	<td> - </td>
		            <%}else{%>
		            	<td><%=oi.getOi_redate().substring(0,10) %></td>
		            <%} %>
		        </tr>
		        <tr>
		            <th>보내는이</th>
		            <td><%=oi.getOi_sender() %></td>
		            <th>휴대번호</th>
		            <td><%=oi.getOi_sephone() %></td>
		        </tr>
		        <tr>
		            <th>받는이</th>
		            <td><%=oi.getOi_name() %></td>
		            <th>받는이 연락처</th>
		            <td><%=oi.getOi_phone() %></td>
		        </tr>
		        <tr>
		            <th>우편번호</th>
		            <td><%=oi.getOi_zip() %></td>
		            <th>송장번호</th>
		            <%if(oi.getOi_invoice() == null || oi.getOi_invoice().equals("")){ %>
		            	<td>-</td>
		            <%}else{ %>
		            	<td><%=oi.getOi_invoice() %></td>
		            <%} %>
		        </tr>
		        <tr>
		            <th>주소</th>
		            <td colspan="3"><%=oi.getOi_addr1() %></td>
		        </tr>
		        <tr>
		            <th>주소 상세</th>
		            <td colspan="3"><%=oi.getOi_addr2() %></td>
		        </tr>
		        <tr>
		            <th>배송 요청 사항</th>
		            <%if(oi.getOi_memo() == null || oi.getOi_memo().equals("")){ %>
		            	<td colspan="3">-</td>
		            <%}else{ %>
		            	<td colspan="3"><%=oi.getOi_memo() %></td>
		            <%} %>
		        </tr>
		    </table>
		</div>
	</div>
</main>
</body>
</html>