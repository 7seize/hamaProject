<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp"%>
<%
// 받아오기
request.setCharacterEncoding("utf-8");

PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");


EvCusTor ect = (EvCusTor)request.getAttribute("ect");
ProductCustom pc = (ProductCustom)request.getAttribute("pc");
String kind = "a";
String o = "a";

String  args="", oargs = "" ;
// 쿼리 스트링

if (request.getParameter("o") != null && !request.getParameter("o").equals("")){	
	o = request.getParameter("o");
	} // 정렬을 위해 가져옴
if (request.getParameter("kind") != null && !request.getParameter("kind").equals("")){ 
	kind = request.getParameter("kind");}
	
args += "&kind=" + kind; 
oargs += "&o=" + o ;
String lnk = "pmcidx="+pc.getPmc_idx()+args+oargs;


String writer = ect.getMi_id();
writer = writer.substring(0,3) + "****";

String tpname1 = "";
String tpname2 = "";
//토핑아이디가 비어있지않거나, 빈문자열이 아닐 때만, 토핑 이름을 가져온다. 
if(pc.getPmc_tp1()!=null && !pc.getPmc_tp1().equals("")){
	tpname1 = pc.getTpname1();
}
if(pc.getPmc_tp2()!=null && !pc.getPmc_tp2().equals("")){
	tpname2 = ", "+pc.getTpname2();
}

String vg = "논비건"; 
if(pc.getPmc_vg().equals("y")) vg = "비건";

String pl = "보통";
if(pc.getPmc_pl().equals("c")) pl = "많이";
if(pc.getPmc_pl().equals("a")) pl = "적게";

String img = "noimg.PNG"; 
boolean letterimg = false;
if(ect.getEct_img1()!=null && !ect.getEct_img1().equals("")) {
	//이미지관련처리해야함
	img= ect.getEct_img1();
	letterimg = true;
}
%>

<style>
.tr,th{text-align: center;}
.viewTable {
	border: 1px solid black;
	width: 50%;
	margin: 100px auto;
	text-align: left;
}

.viewTable th {
	border: 1px solid black;
	background-color: #FFF8DC;
}

.viewTable td {
	border: 1px solid black;
}

.btn {
	margin: 0 auto;
	text-align: center;
	width: 700px;
}

#btnList {
	margin: 0 10%;
}
</style>

<script>
 function voteBtn( ectidx ){// 투표하기 버튼 // 로그인되어 있을 경우만 정상적으로 동작
<% if (isLogin){%> 
	 var frmTor = document.frm;
//	 if(good == "g") msg= "투표";
//로그인되어있을 때 
	 //	int ectidx = request.getParameter("ect.getEct_idx() ");
		 $.ajax({
			 type : "POST",
			 url : "/hamaProject/ev_tor_good", //요청이 보내지는 곳
			 data : {"ectidx" : ectidx },
			 success : function(chkRs){
				 console.log(chkRs);
				 if(chkRs == 0){ //투표 실패시
					 alert("이미 투표하셨습니다.");
				 }else if( chkRs == 2){	//성공시 새로고침 페이지
					 location.reload();
					 alert("투표성공.");
				 }else {
					 alert("오류");	
				 }
			},
			  
		});	

 <%}else { %> //로그인 안되어있으면 
		alert("로그인 후 사용하실 수 있습니다."); 
		location.replace('login_form.jsp?url=ev_tor_view?<%=lnk%>');
		
<%}  %>
 }//########################################################
</script>
<!-- 입력된 내용 불러오기 [ev_tor_form.jsp]-->
<form name="frmTor" method="post">
	<table class="viewTable" width="60%" cellpadding="3" cellspacing="0">
		<tr>
			<th width="*" colspan="6" align="center">내가 1등임</th>
		</tr>
		<tr>
			<th width="10%">작성자</th>
			<td width="20%"><%=writer %>님</td>
			<th width="10%">작성일</th>
			<td width="20%"><%=ect.getEct_date().substring(0,10)%></td>
			<th width="10%">득표수</th>
			<td width="20%"><%=ect.getEct_vote()%></td>
		</tr>
		<tr>
			<th width="10">당도</th>
			<td width="20%"><%=pc.getPmc_sugar() %> 퍼센트</td>
			<th width="15%">추가토핑</th>
			<td width="20%"><%=tpname1%><%=tpname2%></td>
			<th width="15%">필링양</th>
			<td width="25%"><%=pl %></td>
		</tr>
		<tr>
			<td width="*" colspan="6" align="center"><%=ect.getEct_content()%></td>
			</td>
		</tr>
		<tr>
			<%if(letterimg) {%>	
			<td width="*" colspan="6" align="center">
				<img src="/hamaProject/event/upload/<%=img%>"  width="150" height="150" border="0" />
			</td>
			<%}%>		
			
		</tr>
	</table>

	<br />
	<hr align="center" width="60%" />
	<br />
	

	<p class="btn">
		<input type="button" value="목록으로가기" id="btnList"
			onclick="history.back();" /> 
			<%if (kind.equals("a")) {%>	
		<input type="button" value="투표하기" id="btnList" onclick="voteBtn(<%=ect.getEct_idx()%>)" />
		<%}%>	
		<!-- 버튼 클릭 시 alert() -->
	</p>
</form>

<%@ include file="../_inc/footer.jsp"%>
</body>
</html>