<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<%
request.setCharacterEncoding("utf-8");

ArrayList<ProductCustom> customList = 
(ArrayList<ProductCustom>)request.getAttribute("customList");
%>

<script>
function chMac(val){
	var frm = document.frmTor;
	//alert(frm.value); //받아오는거 맞는지 확인 오브젝트로 나온다.
	var arr = val.split("|");
	//alert(arr);
	frm.pmc_name.value = arr[0]; //이름
	frm.pmc_sugar.value = arr[1]; //당도
	frm.pmc_vg.value = arr[2]; //비건
	frm.pmc_pl.value = arr[3]; //필링양
	frm.pi_name.value = arr[4]; //맛
	frm.pmc_tp1.value = arr[5]; //토핑1
	frm.pmc_tp2.value = arr[6]; //토핑2
	frm.pmcidx.value = arr[7]; //
	
	//그럼 다르게 찍힘 
}


function chkVal(frm) {
	var title = frm.title.value;
	var content = frm.content.value;
	var file = frm.file1.value;

	if (title == "") {
		alert("제목을 입력하세요.");
		frm.title.focus();
		return false;
	}
	if (content == "") {
		alert("내용을 입력하세요.");
		frm.content.focus();
		return false;
	}
	
	if (file == "")		frm.isFile.value = "n";
	else				frm.isFile.value = "y";

	return true;
}

</script>

<style>
form { position:relative; }
h2{margin:0 auto; text-align: center;}
table{margin:30 auto;}
.torForm { margin :0 auto 30px; width:60%; 
border: 1px solid black ; text-align: left; }
.torForm th {background-color : #FFF8DC; }
.torForm td {border:1px solid black; }
.upLoad { margin: 0 auto 100px; } 
.upLoad { text-align: center; } 

th{text-align:center;}
</style>


<form name="frmTor" action="ev_tor_form_in" method="post"  onsubmit="return chkVal(this);" enctype="multipart/form-data" >
<!-- 폼태그에 넣어서 submit해서  회원의 레시피 정보 담아서 보냄-->
<h2>★ 나의 마카롱 레시피 ★</h2>
<input type="hidden" name="kind" value="a" />
<input type="hidden" name="pmcidx" value="0" />
<input type="hidden" name="isFile" value="" />


<table class="macdesc" align=center>

<tr>
	<th > 등록할 마카롱 </th>
	<td >
		<select onchange = "chMac(this.value);">
<%
String name = "",  vg = "논비건", pl="보통", img = "레터링 이미지 미업로드", tp1= "토핑 미선택", tp2 = "토핑 미선택", taste =""; 
int sweet = 0; int price=0;
for(int i=0; i < customList.size() ; i++){
	ProductCustom pc = customList.get(i);	
	int pmcidx =  pc.getPmc_idx(); //커스텀 마카롱 리스트에 일련번호	
	//토핑 맛
	if(pc.getPmc_tp1()!=null && !pc.getPmc_tp1().equals("")){
		tp1=pc.getTpname1();
	}
	if(pc.getPmc_tp2()!=null && !pc.getPmc_tp2().equals("")){
		tp2=pc.getTpname2(); 
	}
	//이미지
	if(pc.getPmc_img()!=null && !pc.getPmc_img().equals("")){
		img=pc.getPmc_img(); 
	}	
	if(pc.getPmc_vg().equals("y")) vg = "비건";
	if(pc.getPmc_pl().equals("c")) pl = "많이";
	if(pc.getPmc_pl().equals("a")) pl = "적게";
	name = pc.getPmc_name();
	taste = pc.getPi_name();
	
	String val = "", txt="";
	val = pc.getPmc_name() + "|" 
		+ pc.getPmc_sugar() + "|" 
		+ vg + "|" 	
		+ pl+ "|" 
		+ pc.getPi_name()+ "|" 
		+ tp1+ "|" 
		+ tp2+ "|" 
		+pmcidx;
	out.println("<option value=\"" + val + "\">" + pc.getPmc_name()+ "</option>");	
	}
	
	%>
		</select>
	</td>
</tr>

<tr>
	<th width="15%">마카롱 이름</th>
	<td>
		<input type="text" name ="pmc_name" id="pmc_name" value="<%=name %>" 
		readonly="readonly" />
	</td>
	<th width="15%">마카롱 맛</th>
	<td ><input type="text" name="pi_name" value="<%=taste %>" readonly="readonly" /></td>
</tr>
<tr>
	<th width="15%">토핑</th>
	<td >
		<input type="text" name="pmc_tp1" value="<%=tp1 %>" readonly="readonly" />
	</td >
	<th width="15%">토핑2</th>
	<td >
		<input type="text" name="pmc_tp2" value="<%=tp2%>" readonly="readonly" />
	</td >
</tr>
<tr>
	<th width="15%">당도</th>
	<td >
		<input type="text" name="pmc_sugar" value="<%=sweet%>" readonly="readonly"  />
	</td >
	<th width="15%">비건옵션</th>
	<td >
		<input type="text" name="pmc_vg" value="<%=vg %>" readonly="readonly" />
	</td >
</tr>
<tr>
	<th width="15%">필링양</th>
	<td >
		<input type="text" name="pmc_pl" value="<%=pl%>" readonly="readonly" />
	</td >
</tr>
</table>

<!-- 받아야하는 것 제목, 콘텐츠, file여부, 커스텀마카롱인덱스번호 -->
<table class="torForm" align=center width="500" cellpadding="5">
<tr>
	<th width="15%">글 제목</th>
	<td colspan="3">
		<input type="text" name="title" size="50" value=""/>
	</td>
</tr>
<tr>
<th>글 내용</th>
<td colspan="3">
	<textarea name="content" rows="10" cols="60"></textarea>
</td>
</tr>
<tr>
	<th width="15%">첨부 이미지</th>
	<td>
		<input type="file" name="file1" accept="image/png" /> 
	</td>
</tr>
</table>

<p class="upLoad" >
	<input type="submit" value="레시피 제출"   />
</p>

</form>



<%@ include file="../_inc/footer.jsp" %>
