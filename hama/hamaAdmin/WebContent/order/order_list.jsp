<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.time.*" %>
<%@ page import = "vo.*" %>     
<%@ include file="../_inc/header.jsp" %>

<%
request.setCharacterEncoding("utf-8");

ArrayList<OrderInfo> orderInfo  = (ArrayList<OrderInfo>)request.getAttribute("orderList");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");

int bsize = pageInfo.getBsize(), cpage = pageInfo.getCpage();
int psize= pageInfo.getPsize(), pcnt= pageInfo.getPcnt();
int spage = pageInfo.getSpage(), rcnt= pageInfo.getRcnt();


String kindorder = (String)request.getAttribute("kindorder");
String desc = (String)request.getAttribute("desc");
String startdate = (String)request.getAttribute("startdate");
String enddate = (String)request.getAttribute("enddate");
String schtype= pageInfo.getSchtype();
String keyword= pageInfo.getKeyword();
String lnkOrder = "order?";
String schargs = "", args=""; 

if(kindorder == null) kindorder ="";
if(desc == null) desc = "";
if(schtype != null && !schtype.equals("") && 
	keyword != null && !keyword.equals("") ){
	schargs = "&schtype=" + schtype + "&keyword=" + keyword;
}
args = "&cpage=" + cpage +  schargs;
System.out.println("startdate"+ startdate);

if(startdate == null ) startdate ="";
if(!startdate.equals("")) schargs += "&startdate=" +startdate;
if(enddate == null ) enddate ="";
if(!enddate.equals("")) schargs += "&enddate=" +enddate;
lnkOrder +=schargs;
%>
<link rel="stylesheet" href="/hamaAdmin/css/order_list.css">
<div class="container">

	
<script>
let sch = "idx"
const schValue = function(val) {
	sch = val.value;
}

function chkAll(all){
	let chk = document.getElementsByName('chk');
	
	for(let i=0; i < chk.length; i++){
		chk[i].checked = all.checked;
	}
}

const statusVal = function (val) {
	let arr = val.value.split(",");
	let status = arr[0];
	let oiid = arr[1];
	
	$.ajax({
		type : "POST",
		url : "/hamaAdmin/order",
		data : {"status" : status, "oiid" : oiid},
		success : function(chkRs){
			if(chkRs==0){
				alert("?????? ????????? ??????????????????. \n ?????? ???????????????");
				return;
			}
			location.reload(); //???????????? 
		}
	});
}



</script>
	<h2>?????? ??????</h2>
	<hr/>
	<form name="frmSch" method="get" style="margin-bottom: 15px" >
		<select name="schtype" onchange="schValue(this)" >
			<option value = "all" <%if(schtype!=null && schtype.equals("all")){%> selected = "selected" <%} %>>??????</option>
			<option value = "idx" <%if(schtype!=null && schtype.equals("idx")){%> selected = "selected" <%} %>>?????? ??????</option>
			<option value = "uid" <%if(schtype!=null && schtype.equals("uid")){%> selected = "selected" <%} %>>?????? ?????????</option>
			<option value = "name" <%if(schtype!=null && schtype.equals("name")){%> selected = "selected" <%} %>>?????? ??????</option>
		</select>
		<input type = "search" name = "keyword" placeholder="??????" value="<%=keyword %>" />
		<input type="submit"  value="??????" />
		<div>
			<label for="startdate"> </label>
			<input name="startdate" type="date" value="<%=startdate %>" >
			<label for="enddate"> - </label>
			<input name="enddate" type="date" value="<%=enddate %>" >
		</div>
	</form>
	<table width="100%" cellpadding="5" align="center">
		<tr class="ta_title">
			<th><input type="checkbox" name="all" onclick="chkAll(this)" ></th>
			<th>?????? ??????</th>
			<th>?????? ??????</th>
			<th>?????? ?????????</th>
			<th>?????? ??????</th>
			<th>?????? ??????</th>
			<th>?????? ??????</th>
			<th> <%if(kindorder.equals("status")&&desc.equals("desc")){%><a href="<%=lnkOrder%>&kindorder=status&desc=asc">?????? ?????? ???</a><%}else{%><a href="<%=lnkOrder%>&kindorder=status&desc=desc">?????? ?????? ???</a><%} %></th>
			<th> <%if(kindorder.equals("date")&&desc.equals("desc")){%><a href="<%=lnkOrder%>&kindorder=date&desc=asc">?????? ?????? ???</a><%}else{%><a href="<%=lnkOrder%>&kindorder=date&desc=desc">?????? ?????? ???</a><%} %></th>
			<th>?????? ?????????</th>
		
		</tr>
<%
if(orderInfo.size()>0){ //????????? ????????? ?????????


for(int i = 0; i < orderInfo.size(); i++){ 
	OrderInfo oi = orderInfo.get(i);
	ArrayList<OrderDetail> orderDetailList = oi.getDetailList();
	OrderDetail od = null;
	if(orderDetailList.size()>0){
		od = orderDetailList.get(0);
	}
%>
		<tr class="chcolor" onclick="if(event.target.className != 'unmove') location.href='/hamaAdmin/orderview?oiid=<%=oi.getOi_id() %>'" style="cursor: pointer;">
			<td class="unmove" ><input class="unmove" type="checkbox" name="chk" ></td>
			<td><%=oi.getOi_id() %></td>
<%if(od != null){ %>
			<td style="text-align: left;"><%=od.getOd_name()%><%if(orderDetailList.size()>1){%> ??? <%=orderDetailList.size()%>???<%}  %></td>
<%}else{ %><td>-</td>
<%} %>
			<td><%=oi.getMi_id() %></td>
			<td><%=oi.getOi_sender() %></td>
			<%if(oi.getOi_payment().equals("c")){%><td>????????????</td>
            <%}else if(oi.getOi_payment().equals("b")){%><td>????????? ??????</td>
            <%}else if(oi.getOi_payment().equals("a")){%><td>????????????</td><%} %>
			<td><%=oi.getOi_pay() %></td>
			<td id="sel" class="unmove" >
				<select class="unmove" onchange="statusVal(this)" >
			        <option value="a,<%=oi.getOi_id() %>" <%if(oi.getOi_status().equals("a")){%>selected="selected"<%} %> >?????? ?????????</option>
			        <option value="b,<%=oi.getOi_id() %>" <%if(oi.getOi_status().equals("b")){%>selected="selected"<%} %> >?????????</option>
			        <option value="c,<%=oi.getOi_id() %>" <%if(oi.getOi_status().equals("c")){%>selected="selected"<%} %> >?????? ??????</option>
			    </select>
			</td>
			<td><%=oi.getOi_date() %></td>
			<%if(oi.getOi_date().equals(oi.getOi_redate())){ %>
            	<td> - </td>
            <%}else{%>
            	<td><%=oi.getOi_redate().substring(0,10) %></td>
            <%} %>
		</tr>
<%}
}else{ //????????? ????????? ?????????
	out.print("<tr><td colspan='5' >");
	out.print("??????????????? ????????????. </td></tr> ");
}
%>
	</table>
<table width="700" cellpadding="5" style="margin: 100px auto;" class="page_nav" >
<tr>
<td width="600" style="background: #fff">
<%
String order ="";
if(kindorder !=null && !kindorder.equals("")){
	order = "&kindorder=" + kindorder;
	order += "&desc=" + desc;
}
if(rcnt>0){
	String lnk = "order?cpage=";
	pcnt = rcnt / psize;
	if(rcnt % psize>0) pcnt++;
	if(cpage == 1){
		out.println("[??????]&nbsp;&nbsp;&nbsp;[??????]&nbsp;&nbsp;&nbsp;");
	}else{
		out.println("<a href='" + lnk + 1 + schargs + order + "'>[??????]</a>&nbsp;&nbsp;&nbsp;");
		out.println("<a href='" + lnk + (cpage-1) + schargs + order + "'>[??????]</a>&nbsp;&nbsp;");
	}

	spage = (cpage-1)/bsize * bsize 
			+ 1;
	for(int i=1, j=spage; i<=bsize && j<= pcnt; i++, j++){

		if(cpage == j){
			out.println("&nbsp;<strong>" + j + "</strong>&nbsp;" );
		}else{
			out.print("&nbsp;<a href='" + lnk + j + schargs + order +"'>" );
			out.println(j + "</a>&nbsp;" );
		}	
	}

	if(cpage == pcnt){
		out.println("&nbsp;&nbsp;[??????]&nbsp;&nbsp;&nbsp;[?????????]");
	}else{
		out.println("&nbsp;&nbsp;<a href='" + lnk + (cpage+1) + schargs +order+ "'>[??????]</a>");
		out.println("&nbsp;&nbsp;&nbsp;<a href='" + lnk + pcnt + schargs +order +"'>[?????????]</a>");
	}

}
%>
</div>
</main>

</body>
</html>