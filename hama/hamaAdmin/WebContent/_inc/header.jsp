<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.time.*" %>
<%@ page import = "vo.*" %>     

<script  src="/hamaAdmin/js/jquery-3.6.1.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/hamaAdmin/css/common.css">
</head>
<%
AdminInfo loginInfo = (AdminInfo)session.getAttribute("loginInfo");
boolean isLogin = false;
if(loginInfo != null) isLogin = true;

session.setMaxInactiveInterval(5*60);


isLogin = true;	//작업동안 로그인안해도#########################################################나중에 지울것!


if(!isLogin) {
	response.setContentType("text/html; charset=utf-8");
	//PrintWriter out = response.getWriter();
	out.println("<script>");
	out.println("alert('로그인 후 이용해 주세요');");
	out.println(" location.replace('login_form.jsp');");
	out.println("</script>");
	out.close();
}

%>
<style>
    header{
        width: 90%;
        margin: 0 auto;
    }
    main{
        width: 90%;
        margin: 0 auto;
        display:flex;
        justify-content: space-between;
        
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
    	color:  rgb(255, 255, 255);
        font-weight: bold;
        font-size: 15px;
        padding: 1%;
        background-color: rgb(100, 100, 100);
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
<script type="text/javascript">
function startTimer(duration, display) {
  let timer = duration, minutes, seconds;
  let interval = setInterval(function () {
    minutes = parseInt(timer / 60, 10)
    seconds = parseInt(timer % 60, 10);

    minutes = minutes < 10 ? "0" + minutes : minutes;
    seconds = seconds < 10 ? "0" + seconds : seconds;

    display.textContent = minutes + ":" + seconds;

    if (--timer < 0) {
      timer = duration;
    }
    if(timer === 0) {
      clearInterval(interval);
      alert("로그아웃 되셨습니다.")
      location.replace('login_form.jsp');
    }
  }, 1000);
}

window.onload = function () {
  var minutes = 5;

  var fiveMinutes = (60 * minutes) - 1,
    display = document.querySelector('#Timer');
  startTimer(fiveMinutes, display);
};

</script>
<body>
<header>
    <section class="admin-header">
        <span>관리자계정</span>
        <h2>HAMARON</h2>
        <div id="link">
            <%if(isLogin){%>
            <p>	
            	<span id="Timer">05:00 </span>
				<a href="/hamaAdmin/login_form.jsp"> 로그아웃</a>&nbsp;&nbsp;
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
                <li><a href="/hamaAdmin/order">&nbsp;주문 내역</a></li>
                <li><a href="/hamaAdmin/orderrefund">&nbsp;환불 문의</a></li>
            </ul>
        </div>
        <div class="menu">
            <p>&nbsp;상품 관리</p>
            <ul>
                <li><a href="/hamaAdmin/product">&nbsp;상품 목록</a></li>
                <li><a href="/hamaAdmin/productcus">&nbsp;커스텀 상품</a></li>
                <li><a href="">&nbsp;상품 재고 관리</a></li>
            </ul>
        </div>
        <div class="menu">
            <p>&nbsp;회원 관리</p>
            <ul>
                <li><a href="/hamaAdmin/member">&nbsp;회원 목록</a></li>
                <li><a href="/hamaAdmin/memberqna">&nbsp;1대1 문의</a></li>
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