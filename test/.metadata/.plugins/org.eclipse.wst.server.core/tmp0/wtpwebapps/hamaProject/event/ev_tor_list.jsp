<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../_inc/header.jsp" %>

<link rel="stylesheet" href="../_inc/css/common.css">
<link rel="stylesheet" href="../_inc/css/header.css">

   
<style>
	h2{ text-align:center; }
	h3{ text-align:center; }
	.container{ margin: 100px auto; width:70%; display:flex; 
	justify-content:space-between; flex-wrap: wrap;  }
	.container > div {width: 30%; text-align:center;
	 border:1px solid black; margin-bottom: 10%;padding-bottom:10px;}
	.container > p {}
	.container > b {}
	.voteIng{}
	.voteIng > img { width: 100%;border:1px solid black;}
	.votePre > img {  width: 100%; border:1px solid black; filter: opacity(0.5) drop-shadow(0 0 0 #ff0000);}
</style>
<h2>2월 토너먼트 진행중</h2>
<h3>기간 : 2023.02.01 ~ 2023.02.28 </h3>

<div class="container">
	<div class="voteIng">
		<img  src="ev_img/macaronTp.png"/>
		<p>2월의 레시피 투표</p>
		<b>2023.02.01 ~ 2023.02.28</b>
	</div>
	<div class="votePre">
		<img src="ev_img/macaronTp.png"/>
		<p>1월의 레시피 투표</p>
		<b>2023.01.01 ~ 2023.01.31</b>
	</div>
	<div class="votePre">
		<img src="ev_img/macaronTp.png"/>
		<p>12월의 레시피 투표</p>
		<b>2022.12.01 ~ 2022.12.31</b>
	</div>
	<div class="votePre">
		<img src="ev_img/macaronTp.png"/>
		<p>11월의 레시피 투표</p>
		<b>2022.11.01 ~ 2022.11.30</b>
	</div>
	<div class="votePre">
		<img src="ev_img/macaronTp.png"/>
		<p>10월의 레시피 투표</p>
		<b>2022.10.01 ~ 2022.10.31</b>
	</div>
	<div class="votePre">
		<img src="ev_img/macaronTp.png"/>
		<p>09월의 레시피 투표</p>
		<b>2022.09.01 ~ 2022.09.30</b>
	</div>
</div>


<%@ include file="../_inc/footer.jsp" %>
</body>
</html>