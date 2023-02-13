<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.time.*" %>
<%@ page import = "vo.*" %>

<%
request.setCharacterEncoding("utf-8");
ArrayList<OrderCart> cartList = 
(ArrayList<OrderCart>)request.getAttribute("cartList");

%>
<%@ include file="../_inc/header.jsp" %>
<link rel="stylesheet" href="/hamaProject/css/cart_view.css">
<script defer src="/hamaProject/js/cart_view.js"></script>
<% if(cartList != null){ //카트에 담겨있는 제품이 있을경우
	int amout = 0;
%>
<form name="frmCart" class="cart_contain" action="order_form" method="post">
<input type="hidden" name="chk" value="" />
<input type="hidden" name="kind" value="c" />
        <div class="cart_name">
            <div class="check_wrap"><input type="checkBox" name="all" id="all" onclick="chkAll(this)" checked="checked"></div>
            <div class="cart_img">이미지</div>
            <div class="cart_info">상품 정보</div>
            <div class="cart_price">판매가</div>
            <div class="cart_num">수량</div>
            <div class="cart_point">적립금</div>
            <div class="cart_amout">합계</div>
            <div class="cart_del">주문 관리</div>
        </div>
<%for(int i=0; i< cartList.size(); i++){
	OrderCart oc = cartList.get(i);
	int ocidx = oc.getOc_idx();
	int cnt = oc.getOc_cnt();
%>
        <div class="cart_content">
            <div class="check_wrap"><input type="checkbox" name="chk" onclick="unchkAll(this)" value = <%=ocidx %> checked="checked"></div>
            <div class="cart_img">
                <a href="product_view?piid=<%=oc.getPi_id()%>" class="cart_img_container">
                    <img src="/hamaProject/product/pdt_img/<%=oc.getPi_id().substring(0,2)%>/<%=oc.getPi_id()%>.png" alt="">
                </a>
            </div>
            <a href="product_view?piid=<%=oc.getPi_id()%>" class="cart_info"><%=oc.getPi_name() %></a>
            <div class="cart_price" ><%=oc.getPi_price() %> 원</div>
            <div class="cart_num">
            <select class="cart_num_count" onchange="cartUp(<%=ocidx%>, this.value)" >
            <%for(int j = 1 ; j <100; j++){
            	if(j == cnt){
            		%><option value="<%=j %>" selected ><%=j %></option><%
            	}else{
            		%><option value="<%=j %>" ><%=j %></option><%
            	}
            } %>
            </select>
            </div>
            <div class="cart_point"><%=oc.getPi_price()*cnt*0.01 %> pt</div>
            <div class="cart_amout"><%=oc.getPi_price()*cnt %> 원</div>
            <div class="cart_del" onclick="cartDel(<%=ocidx%>)">삭제</div>
        </div>
<% amout += oc.getPi_price()*cnt;
} %>
        <div class="cart_total">
            <div>총 주문 금액</div>
            <div class="total_price"><%=amout %> 원</div>
            <div></div>
        </div>
        <div class="cart_btn">
            <input type="button" onclick="chkBuy();" value="선택 항목 구매">
            <input type="button" onclick="allBuy();" value="전체 구매">
        </div>
    </form>
<%}else{ %>
<div>장바구니가 비어 있습니다. (css 작업 나중에)</div>
<%} %>
</body>
</html>
</body>
</html>