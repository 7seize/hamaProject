<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<link rel="stylesheet" href="../_inc/css/common.css">
<link rel="stylesheet" href="../_inc/css/header.css">
<%@ include file="./my_menu.jsp" %>

<style>
	.my-refund{
		width: 100%;
	}
    .refund-list{
        border-collapse: collapse;
        margin: 20px;
        margin: 60px auto;
    }
    .refund-list tr{
        border-bottom: 1px solid black;
        text-align: center;
        font-size: 13px;
    }
    .refund-list td{
        padding: 7px 0;
    }
    .refund-list tr td input{
        border: 1px solid gray;
        background-color: white;
        cursor: pointer;
        border-radius: 5%;
    }
</style>
<div class="my-refund">
    <h2>
        환불내역
    </h2>
    <table class="refund-list">
        <tr class="refund-sort">
            <td width="10%">주문번호/<br>주문일자</td>
            <td width="10%"></td>
            <td width="*">제목(상품이름)</td>
            <td width="15%">가격</td>
            <td width="5%">수량</td>
            <td width="10%">관리</td>
        </tr>
        <tr>
            <td>주문번호/<br>주문일자</td>
            <td>상품이미지</td>
            <td>제목(상품이름)</td>
            <td>가격</td>
            <td>수량</td>
            <td>환불완료</td>
        </tr>
    </table>
</div>


</div>

<%@ include file="../_inc/footer.jsp" %>