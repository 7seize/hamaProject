<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp"%>
<%
// 받아오기
request.setCharacterEncoding("utf-8");
ArrayList<EvCusTor> torList  = 
(ArrayList<EvCusTor>)request.getAttribute("torList");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");

//EvCusTor ect = null;

EvCusTor ect = (EvCusTor)request.getAttribute("ect");
EvCusTorPoll ectp = (EvCusTorPoll)request.getAttribute("ectp");// 
String kind = request.getParameter("kind");

System.out.println("torList :" +torList);
System.out.println("ect :" + ect);
System.out.println("pageInfo :" +pageInfo);
System.out.println("kind :" + kind);
System.out.println("ectp :" + ectp);

int bsize = pageInfo.getBsize(), cpage = pageInfo.getCpage();
int psize= pageInfo.getPsize(), pcnt= pageInfo.getPcnt();
int spage = pageInfo.getSpage(), rcnt= pageInfo.getRcnt();


String  args="", oargs = "", vargs = "" ;
// 쿼리 스트링
String o = pageInfo.getO(), v = pageInfo.getV();
if (v != null && !v.equals(""))		vargs += "&v=" + v; // 목록형이인지 갤러리형인지
if (o != null && !o.equals("")){	oargs += "&o=" + o ;}else{ o = "";}; // 정렬을 위해 가져옴
if (kind != null && !kind.equals("")) args += "&kind=" + kind; 
String lnk = "ev_tor_list?" + args;
args = "&cpage=" + cpage ;

if( ectp == null)

%>

<style>
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
<%System.out.println("test0:");  %>
<%System.out.println(ectp.getEctp_idx());  %>
<%System.out.println("test000:");  %>
<script>
 function voteBtn( ectidx ){// 투표하기 버튼 // 로그인되어 있을 경우만 정상적으로 동작
<% if (isLogin){%> 
	 var frmTor = document.frm;
	 if(good == "g") msg= "투표";
//로그인되어있을 때 
	 //	int ectidx = request.getParameter("ect.getEct_idx() ");
		 $.ajax({
			 type : "POST",
			 url : "/hamaProject/ev_tor_good", //요청이 보내지는 곳
			 data : {"ectidx" : <%=ect.getEct_idx()%> },
			 success : function(chkRs){
			 if(chkRs == 1){ //투표 실패시
				 alert("이미 투표하셨습니다.");
			 }else if( chkRs == 2){	//성공시 새로고침 페이지
				 location.reload();
			 }else {
				 alert(msg + "에 실패했습니다.\n 다시 시도해보세요.");	
			 }
			} 
});	

	 <%}else { %> //로그인 안되어있으면 
		alert("로그인 후 사용하실 수 있습니다."); 
	<%}  %>
 }//########################################################
</script>
<%System.out.println("test1:");  %>
<!-- 입력된 내용 불러오기 [ev_tor_form.jsp]-->
<form name="frmTor" method="post">
	<table class="viewTable" width="60%" cellpadding="3" cellspacing="0">
		<tr>
			<th width="*" colspan="6" align="center">내가 1등임</th>
		</tr>
		<tr>
			<th width="10%">작성자</th>
			<td width="10%"></td>
			<th width="10%">조회수</th>
			<td width="10%"></td>
			<th width="10%">작성일</th>
			<td width="10%"></td>
		</tr>
		<tr>
			<th width="10">당도</th>
			<td width="10%"></td>
			<th width="15%">추가토핑</th>
			<td width="10%"></td>
			<th width="15%">레터링 이미지</th>
			<td width="15%"></td>
		</tr>
		<tr>
			<td width="*" colspan="6" align="center">
			<textarea name="opinion" cols="150" rows="5" style="resize: none;" readonly="readonly" ></textarea>
			</td>
		</tr>
		<tr>
			<td width="*" colspan="6" align="center">업로드 된 이미지</td>
		</tr>
	</table>

	<br />
	<hr align="center" width="60%" />
	<br />
	

	<p class="btn">
		<input type="submit" value="목록으로가기" id="btnList"
			onclick="location.href='history.back()';" /> <input type="submit"
			value="투표하기" id="btnList" onclick="voteBtn()" />
		<!-- 버튼 클릭 시 alert() -->
	</p>
	<br />
	<hr align="left" width="700" />
	<br />
<%
	String miid ="", lnkTu = "", good ="";
	if(torList !=null && torList.size() > 0){//투표리스트가 있으면
		for(int i = 0; i < torList.size(); i++){
			ect = torList.get(i);
			//리스트에 있는거 인스턴스에 담아야함 	
		
			if( loginInfo != null  && loginInfo.getMi_id().equals("miid")) //회원일 경우
			 miid = miid.substring(0,4) + "***";
			//아이디 4글자 앞에서 자르고 *** 붙이기 
			 	//좋아요 투표 링크
			
			if(isLogin){ //로그인 후면
			
				lnkTu += "javascript:voteBtn('good', " + ect.getEct_idx() + ");";	
				
			}else{ //로그인 전 투표하려면
				lnkTu  = "javascript:alert('로그인 후 사용하실 수 있습니다.');";
			}
		}
	}
	
	
%>

	<br />
	<hr align="center" width="60%" />
	<br />
</form>

<%@ include file="../_inc/footer.jsp"%>
</body>
</html>