<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="../_inc/header.jsp" %>

<link rel="stylesheet" href="../_inc/css/common.css">
<link rel="stylesheet" href="../_inc/css/header.css">


<style>
h2{text-align:center; }
h3{text-align:center; }
.voteImg {  margin: 100px auto; list-style:none; 
padding:0 30px; width:70%; display:flex; text-align:center; }
.voteImg > li{ width:25%; border: 1px solid black;}
.container>b {padding-left:50px;}
.voteSub { padding:50px 50px; display:flex; flex-direction: column ; align-items: flex-start}
#voteTp { width: 200px; }
#submit {margin-left: 90%; }
</style>
<script ></script>
<h2>2월 추가 토핑 투표</h2>
<h3>기간 : 2023.02.01 ~ 2023.02.28 </h3>
<div class="container">
	<ul class="voteImg">
		<li><a><img width="100%" height="100%" src="ev_img/grape.png" /><br />포도</a></li>
		<li><a><img width="100%" height="100%" src="ev_img/shine musket.png" /><br />샤인머스켓</a></li>
		<li><a><img width="100%" height="100%" src="ev_img/nutella.png"/><br />누텔라</a></li>
		<li><a><img width="100%" height="100%" src="ev_img/macadamia.png" /><br />마카다미아</a></li>
	</ul>
	<b>* 원하는 토핑을 선택해주세요. 1위를 한 토핑은 다음달 토핑 선택칸에 추가됩니다!</b>
</div>
<div class="voteSub">
	<input type="text" id="voteTp" value="원하는 토핑을 적어주세요."/><br />
	<b>* 제출하신 토핑은 다음달 투표에 반영됩니다.</b><br />
	<!-- 차트 삽입해야 함-->
</div>
<input type="submit" id="submit" value="투표하기" />

<%@ include file="../_inc/footer.jsp" %>
</body>
</html>