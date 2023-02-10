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
<% if(cartList != null){ //카트에 담겨있는 제품이 있을경우 %>
<form name="frmCart" class="cart_contain" action="order_form" method="post">
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
%>
        <div class="cart_content">
            <div class="check_wrap"><input type="checkbox" name="chk" value ="ocidx" checked="checked"></div>
            <div class="cart_img">
                <a href="product_view?piid=<%=oc.getPi_id()%>" class="cart_img_container">
                    <img src="/hamaProject/product/pdt_img/<%=oc.getPi_id().substring(0,2)%>/<%=oc.getPi_id()%>.png" alt="">
                </a>
            </div>
            <a href="product_view?piid=<%=oc.getPi_id()%>" class="cart_info"><%=oc.getPi_name() %></a>
            <div class="cart_price" ><%=oc.getPi_price() %> 원</div>
            <div class="cart_num">
                <select class="cart_num_count">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                </select>
            </div>
            <div class="cart_point"><%=oc.getPi_price()*0.01 %> pt</div>
            <div class="cart_amout">6000 원</div>
            <div class="cart_del" onclick="cartDel('ocidx')">삭제</div>
        </div>
<%} %>
        <div class="cart_total">
            <div>총 주문 금액</div>
            <div class="total_price">12000 원</div>
            <div></div>
        </div>
        <div class="cart_btn">
            <input type="button" value="선택 항목 구매">
            <input type="button" value="전체 구매">
        </div>
    </form>
<%}else{ %>
<div>장바구니가 비어 있습니다. (css 작업 나중에)</div>
<%} %>
</body>
</html>
</body>
</html>