<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<%
request.setCharacterEncoding("utf-8");
ProductCustom pc = (ProductCustom)request.getAttribute("pc");

String tpname1 = "토핑 미선택";
String tpname2 = "토핑 미선택";
//토핑아이디가 비어있지않거나, 빈문자열이 아닐 때만, 토핑 이름을 가져온다. 
if(pc.getPmc_tp1()!=null && !pc.getPmc_tp1().equals("")){
	tpname1 = (String)request.getAttribute("tpname1");	
}
if(pc.getPmc_tp2()!=null && !pc.getPmc_tp2().equals("")){
	tpname2 = (String)request.getAttribute("tpname2");
}

String vg = "논비건"; 
if(pc.getPmc_vg().equals("y")) vg = "비건";

String pl = "보통";
if(pc.getPmc_pl().equals("c")) pl = "많이";
if(pc.getPmc_pl().equals("a")) pl = "적게";

String letterimg = "레터링 이미지 미업로드"; //유저가 올린 레터링
if(pc.getPmc_img()!=null && !pc.getPmc_img().equals("")){
	 //레터링이미지 파일 주소(아직 안만들었음 )
	letterimg = "<img src=/hamaProject/product/pdt_img/ltr/"+pc.getPmc_img()+"/>"; 
}
%>
<style>
.container{ width:60%; margin:0 auto; }
.temp{display:flex; justify-content: center; margin:100 auto;}
.desc{margin:0 0 0 20%; border-left:1px solid #f6bc49; padding : 0 0 0 50px;}
:
</style>

<div class="container">
	<h2>[나의 마카롱]&nbsp;&nbsp;&nbsp;<%=pc.getPmc_name() %></h2>
	<div class="temp">
		<img src="/hamaProject/product/pdt_img/mc/<%=pc.getPi_img1()%>" width="200"  border="0" class="img"/>
		
		<div class="desc">
			<p>이름 : <%=pc.getPi_name() %> </p>
			<p>당도  : <%=pc.getPmc_sugar() %>%</p>
			<p>비건 옵션: <%=vg %> </p>
			<p>필링양 : <%=pl%> </p>
			<p>맛  : <%=pc.getPi_name() %>맛</p>
			<p>토핑 : <%=tpname1 %>, <%=tpname2 %></p>
			<p>가격 : <%=pc.getPmc_price() %></p>
			<p><%= letterimg %><p>
		</div>

	</div>
		<p><a align="right" href="javascript:history.back();">[목록으로]</a><p>

</div>


<%@ include file="../_inc/footer.jsp" %>