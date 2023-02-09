<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="../_inc/header.jsp" %>
<link rel="stylesheet" href="../_inc/css/common.css">
<link rel="stylesheet" href="../_inc/css/header.css">
<%@ include file="./my_menu.jsp" %>

<style>
	.my-buy{
		width: 100%;
	}
    .buy-term{
        text-align: center;
        margin: 10px 0;
    }
    .buy-term > input{
        border: 1px solid gray;
        width: 120px;
        height: 60px;
        margin: 10px;
        background-color: beige;
        font-size: 15px;
        cursor: pointer;
        border-radius: 5%;
    }
    .buy-list{
        border-collapse: collapse;
        margin: 20px;
        margin: 0 auto;
    }
    .buy-list tr{
        border-bottom: 1px solid black;
        text-align: center;
        font-size: 13px;
    }
    .buy-list td{
        padding: 7px 0;
    }
    .buy-list tr td input{
        border: 1px solid gray;
        background-color: white;
        cursor: pointer;
        border-radius: 5%;
    }
</style>
<div class="my-buy">
    <h2>
        구매내역
    </h2>
    <div class="buy-term">
        <input type="submit" value="전체">
        <input type="submit" value="1개월">
        <input type="submit" value="3개월">
        <input type="submit" value="6개월">
        <input type="submit" value="1년">
    </div>
    <table class="buy-list">
        <tr class="buy-sort">
            <td width="10%">주문번호/<br>주문일자</td>
            <td width="10%"></td>
            <td width="*">제목(상품이름)</td>
            <td width="10%">가격</td>
            <td width="5%">수량</td>
            <td width="10%">배송상태</td>
            <td width="10%">관리</td>
        </tr>
        <tr>
            <td>주문번호/<br>주문일자</td>
            <td>상품이미지</td>
            <td>제목(상품이름)</td>
            <td>가격</td>
            <td>수량</td>
            <td>배송상태</td>
            <td><input type="button" value="환불"><br/>
                <input type="button" value="리뷰작성"></td>
        </tr>
    </table>
</div>

</div>


<%@ include file="../_inc/footer.jsp" %>