<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<link rel="stylesheet" href="../_inc/css/common.css">
<link rel="stylesheet" href="../_inc/css/header.css">
<%@ include file="./my_menu.jsp" %>

<style>
	.my-review{
        width: 100%;
    }
    .review-table{
        display: flex;
        width: 80%;
        margin: 30px auto;
    }
    .review-table div{
        border: 1px solid rgb(102, 102, 102);
        text-align: center;
        height: 110px;
        padding: 5px;
    }
    .review-table div input{
        border: none;
        padding: 5px;
        background-color: antiquewhite;
        margin-top: 10px;
        cursor : pointer
    }
    .review-table ul{
        border: 1px solid rgb(102, 102, 102);
        width: 100%;
        border-left: none;
        border-right: none;
        height: 110px;
    }
    .review-table ul li{
        margin: 10px;
    }
</style>

<div class="my-review">
    <h2>나의 리뷰</h2>
    <div class="review-table">
        <div><img src="" alt="상품 이미지"></div>
        <ul>
            <li>마카롱 10구 세트</li>
            <li>옵션</li>
            <li>구매날짜</li>
        </ul>
        <div>
            <input type="button" value="리뷰 작성"><br/>
            <input type="button" value="삭제">
        </div>
    </div>
</div>


</div>

<%@ include file="../_inc/footer.jsp" %>