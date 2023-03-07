<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.time.*" %>
<%@ page import = "vo.*" %>     
<%@ include file="../_inc/header.jsp" %>

<%
request.setCharacterEncoding("utf-8");

ArrayList<ProductInfo> productInfo  = (ArrayList<ProductInfo>)request.getAttribute("productInfo");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");

int bsize = pageInfo.getBsize(), cpage = pageInfo.getCpage();
int psize= pageInfo.getPsize(), pcnt= pageInfo.getPcnt();
int spage = pageInfo.getSpage(), rcnt= pageInfo.getRcnt();

boolean isUpOrder = false;
String kindorder = (String)request.getAttribute("kindorder");
if(kindorder != null && !kindorder.equals("")){
	isUpOrder = true ;
}else{
	kindorder ="";
} 

String schtype= pageInfo.getSchtype();
String keyword= pageInfo.getKeyword();
String lnkOrder = "product?";


String schargs = "", args=""; 
if(schtype != null && !schtype.equals("") && 
	keyword != null && !keyword.equals("") ){
	schargs = "&schtype=" + schtype + "&keyword=" + keyword;
}
args = "&cpage=" + cpage +  schargs;
lnkOrder +=schargs;

%>
<style>
.container{width: 90%; margin:0 atuo;}
table{border-collapse: collapse;} 
th, td{ text-align: center;}
h2{margin: 20px 0 10px 0;}
</style>
<link rel="stylesheet" href="/hamaAdmin/css/product_list.css">

<div class="container">

<script>
let sch = "idx"
const schValue = function(val) {
	sch = val.value;
}

const statusVal = function (val) {
	let arr = val.value.split(",");
	let status = arr[0];
	let piid = arr[1];

	
	$.ajax({
		type : "POST",
		url : "/hamaAdmin/product",
		data : {"status" : status, "piid" : piid},
		success : function(chkRs){
			if(chkRs==0){
				alert("상품 변경에 실패했습니다. \n 다시 시도하세요");
				return;
			}
			location.reload(); //새로고침 
		}
	});
}


</script>
	<h2>상품 정보</h2>
	<hr/>
	<form name="frmSch" method="get" style="margin-bottom: 15px" >
		<select name="schtype" onchange="schValue(this)" >
			<option value = "all" <%if(schtype!=null && schtype.equals("all")){%> selected = "selected" <%} %>>전체</option>
			<option value = "id" <%if(schtype!=null && schtype.equals("id")){%> selected = "selected" <%} %>>상품코드</option>
			<option value = "ctr" <%if(schtype!=null && schtype.equals("ctr")){%> selected = "selected" <%} %>>분류</option>
			<option value = "name" <%if(schtype!=null && schtype.equals("name")){%> selected = "selected" <%} %>>상품명</option>
			<option value = "isview" <%if(schtype!=null && schtype.equals("isview")){%> selected = "selected" <%} %>>개시여부</option>
		</select>
		<input type = "search" name = "keyword" placeholder="검색" value="<%=keyword %>" />
		<input type="submit"  value="검색" />
		<button class="add_product"> 상품추가 </button>
	</form>
	<table width="100%" cellpadding="5" align="center">
		<tr class="ta_title" >
			<th>상품 코드</th>
			<th>분류</th>
			<th>상품명</th>
			<th>가격</th>
			<th>할인율</th>
			<th>판매량</th>
			<th> <%if(isUpOrder && kindorder.equals("isview")){%><a href="<%=lnkOrder%>">개시 여부 ⯅</a><%}else{%><a href="<%=lnkOrder%>&kindorder=isview">게시 여부 ⯆</a><%} %></th>
		</tr>
<%
if(productInfo.size()>0){ //게시글 목록이 있으면


for(int i = 0; i < productInfo.size(); i++){ 
	ProductInfo pi = productInfo.get(i);
%>
		<tr onClick="" style="cursor: pointer;" class="chcolor" >

			<td><%=pi.getPi_id() %></td>
			<td><%=pi.getPc_id() %></td>
			<td><%=pi.getPi_name() %></td>
			<td><%=pi.getPi_price() %> 원</td>
			<td><%=pi.getPi_dc() %> %</td>
			<td><%=pi.getPi_sale() %> 개</td>
			<td>
				<select onchange="statusVal(this)" >
			        <option value="Y,<%=pi.getPi_id() %>" <%if(pi.getPi_isview().equals("Y")){%>selected="selected"<%} %> > 개시 </option>
			        <option value="N,<%=pi.getPi_id() %>" <%if(pi.getPi_isview().equals("N")){%>selected="selected"<%} %> > 미개시 </option>
			    </select>
			</td>

		</tr>
<%}
}else{ //게시글 목록이 없으면
	out.print("<tr><td colspan='5' >");
	out.print("검색결과가 없습니다. </td></tr> ");
}
%>
	</table>
<table width="700" cellpadding="5" style="margin: 100px auto; ">
<tr>
<td width="600" style="background: #fff" >
<%
String order ="";
if(kindorder !=null && !kindorder.equals("")){
	order = "&kindorder=" + kindorder;
}
if(rcnt>0){ //게시글이 있으면 - 페이징 영역을 보여줌 
	String lnk = "product?cpage=";
	pcnt = rcnt / psize;
	if(rcnt % psize>0) pcnt++; //전체 페이지 수(마지막 페이지 번호이기도 함)
	
	//앞부분 페이징
	if(cpage == 1){
		//1페이지가 0이면
		out.println("[처음]&nbsp;&nbsp;&nbsp;[이전]&nbsp;&nbsp;&nbsp;");
	}else{
		out.println("<a href='" + lnk + 1 + schargs + order + "'>[처음]</a>&nbsp;&nbsp;&nbsp;");
		out.println("<a href='" + lnk + (cpage-1) + schargs + order + "'>[이전]</a>&nbsp;&nbsp;");
	}

	spage = (cpage-1)/bsize * bsize + 1; //현재 블록에서의 시작 페이지 번호 
	for(int i=1, j=spage; i<=bsize && j<= pcnt; i++, j++){
		//i :  블록에서 보여줄 페이지의 개수만큼 루프를 돌릴 조건으로 사용된느 변수
		//j : 실제 출력할 페이지 번호로 전체 페이지 개수(마지막 페이지) 를 넘지 않도록 사용해야함
		if(cpage == j){ //현재페이지면 강조만
			out.println("&nbsp;<strong>" + j + "</strong>&nbsp;" );
		}else{ //다른페이징이면 링크 걸려서 
			out.print("&nbsp;<a href='" + lnk + j + schargs + order +"'>" );
			out.println(j + "</a>&nbsp;" );
		}	
	}
	
	
	
	
	//뒷부분페이징
	if(cpage == pcnt){
		out.println("&nbsp;&nbsp;[다음]&nbsp;&nbsp;&nbsp;[마지막]");
	}else{
		out.println("&nbsp;&nbsp;<a href='" + lnk + (cpage+1) + schargs +order+ "'>[다음]</a>");
		out.println("&nbsp;&nbsp;&nbsp;<a href='" + lnk + pcnt + schargs +order +"'>[마지막]</a>");
	}

}
%>
</div>
</main>
</body>
<script>

const addProduct = document.querySelector('.add_product')
addProduct.addEventListener('click',()=>{
	event.preventDefault(); // 기본 동작인 폼 전송을 막음
    window.location = '/hamaAdmin/productadd'
})
</script>
</html>