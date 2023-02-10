<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<%
request.setCharacterEncoding("utf-8");
%>


<link rel="stylesheet" href="../_inc/css/common.css">
<link rel="stylesheet" href="../_inc/css/header.css">
<style>
</style>
<style>
.reList{width:700px;}
.reWriter{width:700px; display:flex; padding:5px; 
justify-content: space-between; border:1px solid black;}
.reContent{width:700px; padding:5px;  border:1px solid black; border-top: none;
margin-bottom: 5px;}
</style>




<table width="700" cellpadding="0" cellspacing="0" id="list">
<tr>
<th width="10%">작성자</th><td width="15%"></td>
<th width="10%">득표수</th><td width="10%"></td>
<th width="10%">작성일</th><td width="*%"></td>
</tr>
<tr><th>제목</th><td colspan="5"></td></tr>
<tr><th>내용</th><td colspan="5"></td></tr>

</table> 
<br /><hr align="left" width="700" /><br />
<p style="width:700px" align="center">
	<input type="button" value="목록으로가기" onclick="location.href='ev_tor_month_list';" />
</p>
<br /><hr align="left" width="700" /><br />


<%@ include file="../_inc/footer.jsp" %>
</body>
</html>