<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>

<link rel="stylesheet" href="../_inc/css/common.css">
<link rel="stylesheet" href="../_inc/css/header.css">

<style>
    .notice{
        margin: 0 auto;
        width: 70%;
        text-align: center;
    }
    .notice-title{
        margin: 100px 0;
    }
    .notice-list{
        margin: 100px auto 0;
        border-collapse: collapse;
        width: 80%;
    }
    .notice-list tr{
        height:40px;
        border-bottom: 1px solid black;
    }
    .notice-list td{
    	text-align: center;
    	font-size : 15px;
    }
    .notice-search{
        width: 80%;
        text-align: left;
        margin: 20px auto 100px;
    }
</style>
<body>
<div class="notice">
    <div class="notice-title">
        <h2>공지사항</h2>
        <p>
            전해드려야 할 중요한 이야기들과<br/>이벤트 안내가 이루어집니다.
        </p>
    </div>
    <table class="notice-list">
        <tr>
            <th width="10%">번호</th>
            <th width="*">제목</th>
            <th width="15%">작성자</th>
            <th width="15%">조회수</th>
            <th width="10%">등록일</th>
        </tr>
        <tr>
            <td>번호</td>
            <td>공지제목</td>
            <td>관리자</td>
            <td>512</td>
            <td>22.03.02</td>
        </tr>
    </table>
    <div class="notice-search">
        <input type="text" name="keyword" value=""/>
        <input type="submit" value="검색"/>
    </div>
</div>

<%@ include file="../_inc/footer.jsp" %>