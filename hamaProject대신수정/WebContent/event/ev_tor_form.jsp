<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<%
request.setCharacterEncoding("utf-8");
ArrayList<EvCusTor> torList  = 
(ArrayList<EvCusTor>)request.getAttribute("torList");
EvCusTor ect = (EvCusTor)request.getAttribute("ect");

if(!isLogin || torList.size() == 0   ){
	out.println("<script>");
	out.println("alert('잘못된 경로로 들어오셨습니다.');");
	out.println("history.back();");
	out.println("</script>");
	out.close();
}
%>
<script>
function chTor(val){
	var frm = document.frmTor;
	//alert(frm); 받아오는거 맞는지 확인 오브젝트로 나온다.
	var arr = val.split("|");
	var phone = arr[2].split("-");
	frm.oi_name.value = arr[0];
	frm.oi_rname.value = arr[1];
	frm.p1.value = phone[0];
	frm.p2.value = phone[1];
	frm.p3.value = phone[2];	
	frm.oi_zip.value = arr[3];
	frm.oi_addr1.value = arr[4];
	frm.oi_addr2.value = arr[5];
	//그럼 다르게 찍힘 
}
</script>
<style>
form { position:relative; }
.torForm {   margin : 100px auto 30px; width:60%; 
border: 1px solid black ; text-align: left; }
.torForm th {background-color : #FFF8DC; }
.torForm td {border:1px solid black; }
.upLoad { margin: 0 auto 100px; } 
.upLoad { text-align: center; } 
</style>
<form name="frmTor" action="" method="post" >
<!-- 폼태그에 넣어서 submit해서  회원의 레시피 정보 담아서 보냄-->
<table class="torForm">
<tr>
<th width="15%"> 제목 </th><td width="*" colspan="5" >내가 1등임</td>
</tr>
<tr>
<th width="20%"> MY 마카롱 </th><td  colspan="5"><!-- 내가 구매한 마카롱 가져오기 -->
<select>
<option></option>
</select>
</td>
</tr>
<tr>
<th width="10%">맛</th><td width="15%"></td>
<th width="15%">비건 유무</th><td width="5%"></td>
<th width="10%">필링량</th><td width="15%"></td>
</tr>
<tr>
<th width="25%">당도</th><td width="20%"></td>
<th width="15%">추가 토핑</th><td width="5%"></td>
<th width="25%">레터링 이미지</th><td width="20%"></td>
</tr>
<tr>
<th width="15%">내용</th><td colspan="5"></td>
</tr>
<tr>
<th width="15%">첨부 이미지</th>
<td colspan="5">
<input type="text" name="이미지 찾아보기 " readonly="readonly" size="10" />
<input type="button" value="이미지 찾아보기"  ocnclick="" />
</td>
</tr>
</table>
<p class="upLoad" >
<input type="submit" value="업로드" onclick="" />
</p>
</form>
<%@ include file="../_inc/footer.jsp" %>
</body>
</html>