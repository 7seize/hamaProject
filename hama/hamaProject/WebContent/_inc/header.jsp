<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.time.*" %>
<%@ page import = "vo.*" %>     
<%
MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
boolean isLogin = false;
if(loginInfo != null) isLogin = true;
//로그인 여부를 판단할 변수 isLogin 생성  
%>
<script defer src="/hamaProject/js/header.js"></script>
<script  src="/hamaProject/js/jquery-3.6.1.js"></script>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>하마롱</title>
<link rel="stylesheet" href="/hamaProject/css/header.css">
<link rel="stylesheet" href="../_inc/css/common.css">

</head>

<body>
    <header>
        <div class="login"><!-- 이미지 경로는 절대경로로 하기 -->
        	<a href="/hamaProject/index.jsp"><img id="logo" src="/hamaProject/_inc/img/hamaron_icon.png" alt="Hamaron"></a>
<%if(isLogin){%>

            <p>
				<a href="/hamaProject/logout.jsp">로그아웃</a>&nbsp;&nbsp;
				<a href="member_form_up">정보수정</a>&nbsp;&nbsp;
				<a href="cart_view">장바구니</a>
			</p>
<%}else{%>
 
            <p>
                <a href="/hamaProject/login_form.jsp">로그인</a>  &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="#">회원가입</a>
            </p>
<%}%>
        </div>
        <ul class="menu">
            <div class="all">
                <label id="buger">
                    <span></span>
                    <span></span>
                    <span></span>
                </label>
                <span>전체메뉴</span>
            </div>
            <li class="menu-big">
                <a>선물세트</a>
                <div class="menu-small unactive">
				<ul>
                    <li><a href="product_list?pc=cb">마카롱 선물상자</a></li>
                    <li><a href="product_list?pc=bx">구성 선물상자</a></li>
                </ul>
                </div>
            </li>
            <li class="menu-big">
                <a>메뉴소개</a>
                <div class="menu-small unactive">
                    <ul>
                        <li><a href="product_list">전체메뉴</a></li>
                        <li><a href="product_list?pc=mc">마카롱</a></li>
                        <li><a href="product_list?pc=ck">쿠키</a></li>
                         <li><a href="product_list?pc=jm">잼</a></li>
                        <li><a href="product_list?pc=te">티타임</a></li>
                    </ul>
                </div>
            </li>
            <li class="menu-big">
                <a>이벤트</a>
                <div class="menu-small unactive">
                    <ul>
                        <li><a href="">투표</a></li>
                        <li><a href="">토너먼트</a></li>
                    </ul>
                </div>
            </li>
            <li class="menu-big">
                <a>제작일정</a>
                <div class="menu-small unactive"></div>
            </li>
            <li class="menu-big">
                <a>게시판</a>
                <div class="menu-small unactive">
                    <ul>
                        <li><a href="">공지</a></li>
                        <li><a href="">토너먼트</a></li>
                    </ul>
                </div>
            </li>
            
        </ul>
    
    </header>
   
