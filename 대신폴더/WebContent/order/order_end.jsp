<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>   
<%
request.setCharacterEncoding("utf-8");
OrderInfo oi  = (OrderInfo)request.getAttribute("orderInfo");
//주문 공통 정보(배송지, 및 결제 관련 정보)를 저장한 인스턴스를 받아옴
ArrayList<OrderDetail> dl = oi.getDetailList();
//주문 내 상품 정보들을 ArrayList<OrderDetail> 로 받아옴
%>
<h2> 결제 완료 창</h2>
<div>
	<div>주문번호</div>
	<div><%=oi.getOi_id()%></div>
</div>
<div>
	<div>주문일자</div>
	<div><%=oi.getOi_date()%></div>
</div>
<div>
	<div>보내는 이</div>
	<div><%=oi.getOi_sender()%></div>
</div>
<div>
	<div>보내는이 연락처</div>
	<div><%=oi.getOi_sephone()%></div>
</div>
<div>
	<div>받는이</div>
	<div><%=oi.getOi_name()%></div>
</div>
<div>
	<div>받는이 전화번호</div>
	<div><%=oi.getOi_phone()%></div>
</div>
<div>
	<div>희망 배송일</div>
	<div><%=oi.getOi_redate()%></div>
</div>
<div>
	<div>결제수단</div>
	<div><%=oi.getOi_payment()%></div>
</div>
<div>
	<div>배송지</div>
	<div><%=oi.getOi_zip()%></div>
	<div><%=oi.getOi_addr1()%></div>
	<div><%=oi.getOi_addr2()%></div>
</div>
<div>
	<div>배송 요청 사항</div>
	<div><%=oi.getOi_memo()%></div>
</div>


<p>상품</p>
<%
for(int i=0; i<dl.size(); i++){ 
	OrderDetail od = dl.get(i);
	String img = "/hamaProject/product/pdt_img/"+od.getPi_id().substring(0,2)+"/"+ od.getOd_img();
	String lnk = "/hamaProject/product_view?piid=" + od.getPi_id();
%>
<div>
	<div>
		<a href="<%=lnk%>"><img src="<%=img %>" width="110" height="90" /></a>
	</div>
	<div>
		<div>상품id</div>
		<div><%=od.getPi_id() %></div>
	</div>
	<div>
		<div>상품 수량</div>
		<div><%=od.getOd_cnt() %> 개</div>
	</div>
	<div>
		<div>상품 가격</div>
		<div><%=od.getOd_price() %>원</div>
	</div>
	<div>
		<div>상품명</div>
		<div><%=od.getOd_name() %></div>
	</div>
</div>

<%} %>


<%
String payment = "카드 결제";
if(oi.getOi_payment().equals("b"))  payment = "휴대폰 결제";
if(oi.getOi_payment().equals("c"))  payment = "무통장 입금";
%>
<%=payment %>:  총 <%=oi.getOi_pay() %>원
</p>


</body>
</html>

