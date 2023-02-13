<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     
<%@ include file="../_inc/header.jsp" %>

<%
request.setCharacterEncoding("utf-8");
ArrayList<OrderCart> pdtList = (ArrayList<OrderCart>)request.getAttribute("pdtList");
ArrayList<MemberAddr> addrList = (ArrayList<MemberAddr>)request.getAttribute("addrList");
MemberAddr ad = addrList.get(0);

if(!isLogin || pdtList.size() == 0 || addrList.size() == 0 ){
	//로그인이 안되어있거나, 구매할 상품 또는 배송지 정보가 하나도 없으면 
	out.println("<script>");
	out.println("alert('잘못된 경로로 들어오셨습니다.');");
	out.println("history.back();");
	out.println("</script>");
	out.close();
}
%>
<link rel="stylesheet" href="/hamaProject/css/order_form.css">

    <form name="frmCart" class="cart_contain" action="order_form" method="post">
        <div class="cart_name">
            <div class="cart_img">이미지</div>
            <div class="cart_info">상품 정보</div>
            <div class="cart_price">판매가</div>
            <div class="cart_num">수량</div>
            <div class="cart_point">적립금</div>
            <div class="cart_amout">합계</div>
        </div>
<%
String ocidxs = ""; //장바구니 인덱스 번호들을 누적 저장할 변수 
int total=0; //총 구매가격의 누적 값을 저장할 변수 
for(int i=0; i<pdtList.size() ; i++){
	OrderCart oc = pdtList.get(i);
%>
        <div class="cart_content">
            <div class="cart_img">
                <div class="cart_img_container">
                    <img src="/hamaProject/product/pdt_img/<%=oc.getPi_id().substring(0,2) %>/<%=oc.getPi_id() %>.png" alt="">
                </div>
            </div>
            <div class="cart_info"><%=oc.getPi_name() %></div>
            <div class="cart_price" ><%=oc.getPi_price() %> 원</div>
            <div class="cart_num"> <%=oc.getOc_cnt() %> </div>
            <div class="cart_point"><%=oc.getPi_price()*0.01 %> pt</div>
            <div class="cart_amout"><%=oc.getPi_price()*oc.getOc_cnt() %> 원</div>
        </div>
<%
total += oc.getPi_price()*oc.getOc_cnt();
} %>
        <div class="cart_total">
            <div>예상 적립 포인트</div>
            <div><%=total*0.01 %> pt</div>
            <div>총 주문 금액</div>
            <div class="total_price"><%=total %> 원</div>
            <div></div>
        </div>
    </form>
    <form action="">
        <h2>주문하시는 분 (보내는 분)</h2>
        <div class="order_info">
            <div>
                <div class="order_title">
                    <label for="order_name">주문하시는 분 </label>
                </div>
                <div class="order_content">
                    <input id="order_name" type="text" value="<%=ad.getMi_id() %>">
                </div>
                <div class="order_title">
                    <label for="order_phone">휴대번호 </label>
                </div>
                <div class="order_content">
                    <input id="order_phone" type="text" value="<%=ad.getMa_phone()%>"  >
                </div>
            </div>
        </div>
        <h2>받으시는 분 (상품을 받으시는 분)</h2>
        <div>
            <input type="checkbox" id="is_receive">
            <label for="is_receive"> 주문자 정보와 동일</label>
        </div>
        <div class="order_info">
            <div>
                <div class="order_title">
                    <label for="receive_name">받으시는 분 이름</label>
                </div>
                <div class="order_content">
                    <input id="receive_name" type="text">
                </div>
                <div class="order_title">
                    <label for="receive_phone">휴대번호 </label>
                </div>
                <div class="order_content">
                    <input id="receive_phone" type="text">
                </div>
                <div class="order_title">
                    <label for="receive_add">주소 </label>
                </div>
                <div class="order_content">
                    <div>
                        <input id="receive_add" type="text">
                        <input id="receive_btn" type="button" value="우편번호 찾기">
                    </div>
                    <div>
                        <input id="receive_add" type="text">
                    </div>
                    <div>
                        <input id="receive_add" type="text">
                    </div>
                </div>
                 <div class="order_title">
                    <label for="receive_memo">요청사항(선택) </label>
                </div>
                <div class="order_content">
                    <input id="receive_memo" type="text">
                </div>
                <div class="order_title">
                    <label for="receive_date">배송 희망일(선택) </label>
                </div>
                <div class="order_content">
                    <input id="receive_date" type="date">
                </div>
            </div>
        </div>
        <h2>결제 선택</h2>
        <div class="order_info">
            <div>
                <div class="order_title">
                    <label for="order_total">총 결제금액 </label>
                </div>
                <div class="order_content">
                    <input id="order_total" type="text" >
                </div>
                <div class="order_title">
                    <label for="order_payment">결제방법 </label>
                </div>
                <div class="order_content">
                    <input name="order_payment" id="card" type="radio" >
                    <label for="card">신용카드 </label>
                    <input name="order_payment" id="bankbook" type="radio" >
                    <label for="bankbook">무통장 입금 </label>
                    <input name="order_payment" id="account" type="radio" >
                    <label for="account">계좌이체 </label>
                </div>
            </div>
        </div>
        <input value="결제하기" type="submit">
    </form>
</body>
</html>