<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.time.*" %>
<%@ page import = "vo.*" %>     
<%@ include file="../_inc/header.jsp" %>

<%

%>
<link rel="stylesheet" href="/hamaAdmin/css/order_view.css">
<style>

table{border-collapse: collapse;} 
h2{margin: 20px 0 10px 0;}
</style>
	<div class="container" >
		<table>
	        <tr>
	            <th>주문 일자</th>
	            <td>내용</td>
	            <th>구매코드</th>
	            <td>내용</td>
	        </tr>
	        <tr>
	            <th>아이디</th>
	            <td>내용</td>
	            <th>상태</th>
	            <td>내용</td>
	        </tr>
	        <tr>
	            <th rowspan="2">상품명</th>
	            <td>내용</td>
	            <th rowspan="2">개수</th>
	            <td>내용</td>
	        </tr>
	        <tr>
	            <td>내용</td>
	
	            <td>내용</td>
	        </tr>
	        <tr>
	            <th>총금액</th>
	            <td>내용</td>
	            <th>사용 포인트</th>
	            <td>내용</td>
	        </tr>
	        <tr>
	            <th>결제수단</th>
	            <td>내용</td>
	            <th>배송 희망일</th>
	            <td>내용</td>
	        </tr>
	        <tr>
	            <th>보내는이</th>
	            <td>내용</td>
	            <th>휴대번호</th>
	            <td>내용</td>
	        </tr>
	        <tr>
	            <th>받는이</th>
	            <td>내용</td>
	            <th>받는이 연락처</th>
	            <td>내용</td>
	        </tr>
	        <tr>
	            <th>우편번호</th>
	            <td>내용</td>
	            <th>송장번호</th>
	            <td>내용</td>
	        </tr>
	        <tr>
	            <th>주소</th>
	            <td colspan="3">내용ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ</td>
	        </tr>
	        <tr>
	            <th>주소 상세</th>
	            <td colspan="3">내용ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ</td>
	        </tr>
	        <tr>
	            <th>배송 요청 사항</th>
	            <td colspan="3">내용ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ</td>
	        </tr>
	    </table>
	</div>
	<div>
		<button onclick="location.href='/hamaAdmin/order'" >  뒤로가기  </button>
	</div>
</main>
</body>
</html>