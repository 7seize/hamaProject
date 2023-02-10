<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<link rel="stylesheet" href="../_inc/css/common.css">
<link rel="stylesheet" href="../_inc/css/header.css">
<%@ include file="./my_menu.jsp" %>

<style>
    .my-place{ width: 100%; }
    .place-list{
        margin: 5px auto 0;
        padding: 10px;
        width: 80%;
        border: 1px solid rgb(103, 103, 103);
        line-height: 30px;
    }
    .place-list:nth-of-type(1){ margin-top: 30px; }
    .place-list > a{
        padding: 2px 10px;
        border: 1px solid rgb(103, 103, 103);
        color: tomato;
    }
    .my-place > a{
        display: block;
        margin: 10px auto;
        width: 80%;
        border: 1px solid rgb(103, 103, 103);
        padding: 10px;
        text-align: center;
        font-weight: bold;
        color: tomato;
    }
    .my-place a:hover{
        background-color: rgb(241, 239, 239);
        color : tomato;
    }
    .arrname{
        font-size: 22px;
        font-weight: bold;
    }
    .arrdf{
        font-size: 13px;
        font-style: italic ;
        color: gray;
    }
</style>
<div class="my-place">
    <h2>배송지 관리</h2>
    <div class="place-list">
        <ul>
            <li class="arrname">받는사람이름</li>
            <li class="arrdf">기본배송지설정</li>
            <li>배송지1, 배송지2</li>
            <li>전화번호</li>
            <li>요청사항</li>
        </ul>
        <a href="">수정</a>
    </div>
    <a href="">+ 배송지 추가</a>
</div>



</div>

<%@ include file="../_inc/footer.jsp" %>