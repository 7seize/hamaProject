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
</style>



<div class="container">

	<!-- <h2>관리자 메인 페이지</h2> -->
	
	
	<h2>상품목록</h2>
	<p><input type = "button" value = "상품등록"  class="registerbutton"/><p>
	<hr/>
	<select name="schtype">
		<option value = "" <%if(schtype!=null && schtype.equals("a")){%> selected = "selected" <%} %>>상품코드</option>
		<option value = "" <%if(schtype!=null && schtype.equals("p")){%> selected = "selected" <%} %>>상품명</option>
	</select>
	
	<input type = "search" name = "keyword" placeholder="검색" />
	
	<table width="100%" cellpadding="5" align="center">
		<tr>
			<th>상품코드</th>
			<th>분류</th>
			<th>상품명</th>
			<th>가격</th>
			<th>할인가</th>
			<th>게시여부</th>		
		</tr>
		<tr>
			<td>상품코드</td>
			<td>분류</td>
			<td>상품명</td>
			<td>가격</td>
			<td>할인가</td>
			<td>게시여부</td>
		</tr>
	</table>
	







</div>





</main>
</body>
</html>
