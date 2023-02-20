<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.time.*" %>
<%@ page import = "vo.*" %>     
<%
AdminInfo loginInfo = (AdminInfo)session.getAttribute("loginInfo");
boolean isLogin = false;
if(loginInfo != null) isLogin = true;
//로그인 여부를 판단할 변수 isLogin 생성  
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/common.css">
</head>
<style>
    header{
        width: 90%;
        margin: 0 auto;
    }
    main{
        width: 90%;
        margin: 0 auto;
        display:flex;
    }
    .admin-header{
        width: 98%;
        margin: 30px auto 10px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        font-size: 13px;
    }
    #link a:nth-of-type(1){
        margin-right: 20px;
    }
    nav{
        margin-top: 20px;
        margin-right: 10px;
    }
    .menu{
        width: 150px;
        border: 1px solid rgb(218, 218, 218);
        line-height: 30px;
        align : center;
    }
    .menu > p{
        font-weight: bold;
        font-size: 15px;
        padding: 1%;
        background-color: rgb(241, 241, 241);
        border-bottom: 1px solid rgb(218, 218, 218);
    }
    .menu > ul{
        font-size: 12px;
        padding: 1%;
    }
    .menu li{
        border-bottom: 1px solid rgb(218, 218, 218);
    }
    .menu ul li:nth-last-of-type(1){
        border-bottom: none;
    }
    @font-face {
    font-family: 'GmarketSansMedium';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff');
    font-weight: normal;
    font-style: normal;
	}
	nav,div,a,li,ul,p,span,tr,th,td,h2{font-family:'IM_Hyemin-Bold';}
    
    
    
</style>

<body>
<header>
    <section class="admin-header">
        <span>관리자계정명</span>
        <h2>HAMARON</h2>
        <div id="link">
            <%if(isLogin){%>
            <p>
				<a href="/hamaAdmin/logout.jsp">로그아웃</a>&nbsp;&nbsp;
			</p>
			<%}else{%>
            <p>
                <a href="/hamaAdmin/login_form.jsp">로그인</a>&nbsp;&nbsp;&nbsp;&nbsp;
            </p>
			<%}%>
        </div>
    </section>
    <hr/>
</header>
<main>
    <nav>
        <div class="menu">
            <p>&nbsp;주문 관리</p>
            <ul>
                <li><a href="">&nbsp;주문 내역</a></li>
                <li><a href="">&nbsp;환불 문의</a></li>
                <li><a href="">&nbsp;배송 관리</a></li>
            </ul>
        </div>
        <div class="menu">
            <p>&nbsp;상품 관리</p>
            <ul>
                <li><a href="/product/product_list.jsp">&nbsp;상품 목록</a></li>
                <li><a href="">&nbsp;커스텀 상품</a></li>
                <li><a href="">&nbsp;상품 재고 관리</a></li>
            </ul>
        </div>
        <div class="menu">
            <p>&nbsp;회원 관리</p>
            <ul>
                <li><a href="">&nbsp;회원 목록</a></li>
                <li><a href="">&nbsp;1대1 문의</a></li>
            </ul>
        </div>
        <div class="menu">
            <p>&nbsp;투표 관리</p>
            <ul>
                <li><a href="">&nbsp;투표 후보 관리</a></li>
                <li><a href="">&nbsp;투표 결과 통계</a></li>
            </ul>
        </div>
        <div class="menu">
            <p>&nbsp;게시판 관리</p>
            <ul>
                <li><a href="">&nbsp;공지사항/이벤트 관리</a></li>
                <li><a href="">&nbsp;FAQ 관리</a></li>
                <li><a href="">&nbsp;리뷰 관리</a></li>
            </ul>
        </div>
        <div class="menu">
            <p>&nbsp;통계 관리</p>
            <ul>
                <li><a href="">&nbsp;연/월간 매출 통계</a></li>
                <li><a href="">&nbsp;판매량 통계</a></li>
                <li><a href="">&nbsp;회원 통계</a></li>
            </ul>
        </div>
    </nav>

    
    
    
    
    
<!--
    헤더 include 하는 모든 페이지는   
</main>
</body>
</html> 
    닫아야함
 -->