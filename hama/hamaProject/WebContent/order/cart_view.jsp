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
        <div class="cart_content">
            <div class="check_wrap"><input type="checkbox" name="chk" value ="ocidx" checked="checked"></div>
            <div class="cart_img">
                <div class="cart_img_container">
                    <img src="./img/mc/mc100.png" alt="">
                </div>
            </div>
            <div class="cart_info">커스텀 마카롱</div>
            <div class="cart_price" >6000 원</div>
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
            <div class="cart_point">60 pt</div>
            <div class="cart_amout">6000 원</div>
            <div class="cart_del" onclick="cartDel('ocidx')">삭제</div>
        </div>
        <div class="cart_content">
            <div class="check_wrap"><input type="checkbox" name="chk" value ="ocidx" checked="checked"></div>
            <div class="cart_img">
                <div class="cart_img_container">
                    <img src="./img/ck/ck101.png" alt="">
                </div>
            </div>
            <div class="cart_info">초코 쿠키</div>
            <div class="cart_price">6000 원</div>
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
            <div class="cart_point">60 pt</div>
            <div class="cart_amout">6000 원</div>
            <div class="cart_del">삭제</div>
        </div>
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
</body>
</html>
</body>
</html>