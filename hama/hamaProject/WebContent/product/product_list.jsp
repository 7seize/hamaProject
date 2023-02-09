<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<%
//받아오기
request.setCharacterEncoding("utf-8");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
ArrayList<ProductInfo> productList = 
	(ArrayList<ProductInfo>)request.getAttribute("productList");
String pc = request.getParameter("pc"); //대분류 저장하는 변수


// hamaProject/product_view?piid=mc100&pc=mc&o=b 쿼리스트링예시
// piid(상품아이디), pc(대분류), o(정렬순 a=신상품 b=판매순)


//쿼리 스트링으로 각각 상세보기,  정렬용 쿼리스트링을 저장할 변수
String args = "",  oargs = "";
//쿼리스트링 
if (pc != null && !pc.equals("")) args += "&pc=" + pc; 
String lnk = "product_list?" + args;


//정렬용변수 
String o = pageInfo.getO();
//정렬타입
if (o != null && !o.equals(""))		oargs += "&o=" + o;
//상품 상세보기 링크용 쿼리스트링
args += oargs;



%>
<style>
#product{margin:0 auto; width:60%;}
</style>




<div id=product>

<p align="right">
		<select name="o" onchange="location.href='<%=lnk%>&o=' + this.value;">
			<option value="a" <% if (o.equals("a")) { %>selected="selected"<% } %>>신상품 순</option>
			<option value="b" <% if (o.equals("b")) { %>selected="selected"<% } %>>판매 순</option>
		</select>&nbsp;&nbsp;&nbsp;
</p>
<hr/>



<table width="100%" cellpadding="5" cellspacing="0">
<%
	int i=0;	
	for (i = 0 ; i < productList.size() ; i++) {
		ProductInfo pi = productList.get(i);
		lnk = "product_view?piid=" + pi.getPi_id() + args;
		if (i % 4 == 0)	out.println("<tr>");
%>
	<td width="25%" align="center" onmouseover="this.bgColor='#efefef';" onmouseout="this.bgColor='';">
	<a href="<%=lnk %>">
	<img src="/hamaProject/product/pdt_img/<%=pi.getPc_id()%>/<%=pi.getPi_id()%>.png" width="150" height="150" border="0" />
	<br /><%=pi.getPi_name() %></a>
	</td>
<%
		if (i % 4 == 3)	out.println("</tr>");
	}
	
	if (i % 4 > 0) {
		for (int j = 0 ; j < (4 - (i % 4)) ; j++)
			out.println("<td width='25%'></td>");
		out.println("</tr>");
	}
	
%>
	</table>


</div>



<%@ include file="../_inc/footer.jsp" %>