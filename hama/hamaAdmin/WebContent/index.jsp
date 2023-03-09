<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.time.*" %>
<%@ page import = "vo.*" %>     
<%@ include file="_inc/header.jsp" %>

<%

String schtype="";
String desc = "<img src=/hamaAdmin/product/pdt_img/d.png width='30px' />";
String asc = "<img src=/hamaAdmin/product/pdt_img/u.png width='30px' />";


%>
<style>
.container{width: 90%; margin:0 atuo;}
table{border-collapse: collapse;} 
th, td{ text-align: center;}
.main-contain{
	width: 100%;
	margin: 18 0;
}
.main_centent{
	display: flex;
	width: 800px;
	margin: 0 120px 60px;
}
.main_centent > div{
	width: 50%;
	padding: 0 70px;
}
.main_centent > div >h2{
	text-align: center;
	margin-bottom: 30px;
}
.main_centent > div > ul>li{
	display: flex;
	justify-content: space-between;
}
</style>

<div class="main-contain">
	<div class="main_centent">
		<div class="yesterday_sales" >
			<h2>전일 매출</h2>
			<ul>
				<li>
					<div>매출</div>
					<div>10000원</div>
				</li>
				<li>
					<div>총매출</div>
					<div>10000원</div>
				</li>
			</ul>
		</div>
		<div class="contact_qna" >
			<h2>1 : 1 문의</h2>
			<ul>
				<li>
					<div>미답변 문의</div>
					<div>10건</div>
				</li>
				<li>
					<div>당일 문의</div>
					<div>1건</div>
				</li>
				<li>
					<div>답변 완료</div>
					<div>1건</div>
				</li>
			</ul>
		</div>
	</div>
	<div class="main_centent">
		<div class="today_sales" >
			<h2>당일 상품별 매출</h2>
			<ul>
				<li>
					<div>커스텀 마카롱</div>
					<div>10000원</div>
				</li>
				<li>
					<div>마카롱</div>
					<div>10000원</div>
				</li>
				<li>
					<div>쿠키</div>
					<div>10000원</div>
				</li>
				<li>
					<div>티타임</div>
					<div>10000원</div>
				</li>
				<li>
					<div>세트</div>
					<div>10000원</div>
				</li>
				<li>
					<div>총매출</div>
					<div>10000원</div>
				</li>
			</ul>
		</div>
		<div class="contact_qna" >
			<h2>주문량</h2>
			<ul>
				<li>
					<div>당일 주문건수</div>
					<div>10건</div>
				</li>
				<li>
					<div>전일 주문건수</div>
					<div>1건</div>
				</li>
				<li>
					<div>이번주 주문수</div>
					<div>1건</div>
				</li>
				<li>
					<div>당월 주문수</div>
					<div>1건</div>
				</li>
			</ul>
		</div>
	</div>
</div>


</main>
</body>
</html>
