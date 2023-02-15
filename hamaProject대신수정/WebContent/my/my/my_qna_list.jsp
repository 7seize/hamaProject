<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<link rel="stylesheet" href="../_inc/css/common.css">
<link rel="stylesheet" href="../_inc/css/header.css">
<%@ include file="./my_menu.jsp" %>

<style>
    .my-qna{
        width: 100%;
    }
    .my-qna div{
        width: 80%;
        margin: 50px auto 0;
    }
    .qna-list{
        border-collapse: collapse;
        width: 100%;
    }
    .qna-list tr{
        height:40px;
        border-bottom: 1px solid black;
    }
    .qna-list td{
        text-align: center;
        font-size : 15px;
    }
    .my-qna div input{
        background-color: antiquewhite;
        border: 1px solid gray;
        margin-top: 20px;
        float: right;
        padding: 5px;
        cursor: pointer;
    }
</style>
<div class="my-qna">
    <h2>1대1 문의</h2>
    <div>
        <table class="qna-list">
            <tr>
                <th width="10%">번호</th>
                <th width="10%">분류</th>
                <th width="*">제목</th>
                <th width="15%">작성일</th>
                <th width="15%">답변상태</th>
            </tr>
            <tr>
                <td>번호</td>
                <td>회원</td>
                <td>문의입니다</td>
                <td>22.03.02</td>
                <td>답변완료</td>
            </tr>
        </table>
        <input type="button" value="문의하기">
    </div>
</div>



</div>

<%@ include file="../_inc/footer.jsp" %>