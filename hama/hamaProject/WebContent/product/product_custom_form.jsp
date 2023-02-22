<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<%
request.setCharacterEncoding("utf-8");
//토핑 리스트 
ArrayList<ProductTopping> toppingList = 
(ArrayList<ProductTopping>)request.getAttribute("toppingList");
//마카롱리스트
ArrayList<ProductInfo> macList = 
(ArrayList<ProductInfo>)request.getAttribute("macList");
int price=0;//커스텀 마카롱의 가격을 저장할 변수
%>
<script>
//해야할것. 이미지 가져오기
//선택한 맛에 따라 인덱스에 맞는 사진 크게넣어주는 스크립트필요. 그리고 가격 설정 
//document.querySelector('input[name="radioName"]:checked').value;


//마카롱 토핑 select가 2를 넘지 않도록 하는 스크립트 
function count_check(obj){
	var chkBox = document.getElementsByName("topping");
	var toppingcnt =  document.getElementById("toppingcnt");
	var chkCnt = 0; 
	for(var i=0; i<chkBox.length; i++){
		if(chkBox[i].checked){ //checked된 경우
			chkCnt++;
			toppingcnt.value = chkCnt;
		}
	}
	if(chkCnt>2){
		alert("토핑은 2개까지만 선택할 수 있습니다.");
		obj.checked=false;
		return false;
	}
}

function chkVal(frm) {
	var name = frm.name.value;
	var file = frm.file1.value;
	if (name == "") {
		alert("이름을 입력하세요.");
		frm.name.focus();
		return false;
	}
	if (file == "")		frm.isFile.value = "n";
	else				frm.isFile.value = "y";

	return true;
}
</script>


<style>
.container{ width:70%; margin:0 auto; }
.frmCustom{display: flex; justify-content:space-around;}
</style>


<div class="container">
	<h2>[나의 마카롱]&nbsp;&nbsp;&nbsp;</h2>
	
	<form name = "frmCustom" action="product_custom_in" method="post" class="frmCustom" enctype="multipart/form-data" onsubmit="return chkVal(this);">
	<input type="hidden" name="isFile" value="" />
		<img src="/hamaProject/product/pdt_img/mc/mc100.png" width="200" border="0" />
		<div>
			<label id="name">마카롱 이름 : </label>
			<input type="text" name="name" id="name" minlength="1" maxlength="20" />
			<br/><br/>
			<label id="sweet">당도 : </label>
			<input type="number" name="sweet" id="sweet" min="0" max="100" value="50" />
			<br/><br/>
			<label id="vg">비건 옵션</label>
			<input type="radio" name="vg" value="y" >Vegan
			<input type="radio" name="vg" value="n" checked="checked" >non-Vegan
			<br/><br/>
			<label id="pl">필링 양</label>
			<input type="radio" name="pl" value="a" >적게
			<input type="radio" name="pl" value="b" checked="checked">보통
			<input type="radio" name="pl" value="c" >많이
			<br/><br/>
			<label for="taste">마카롱 맛  : </label>
			<select name="taste" id="taste">
			
		<%	
			ProductInfo pi1 = macList.get(0); //커스텀 마카롱  가격을 가져오기 위해 0일때만
			price=pi1.getPi_price();
			
			for(int i=1; i<macList.size() ; i++){  //순서1(커스텀마카롱제외)부터 상품코드마카롱인 리스트 돌리기
				ProductInfo pi = macList.get(i);
				String name=pi.getPi_name();
				
				
			%>
				<option value="<%=pi.getPi_id() %>"><%=name %></option>
			<%} %>	
			</select>
			<br/><br/>
			<label for="topping">토핑 최대 두가지  : </label>		
			<%for(int i=0; i<toppingList.size() ; i++){ 
				ProductTopping pt = toppingList.get(i);
				String name=pt.getPmt_name();
			%> 
			<input type="checkbox" name="topping" value="<%=pt.getPmt_id()%>" onclick ="count_check(this);"/>
			<%=name %>
				<%if(i%3==1){%> <br/> <%} %>	
			<%} %>	
			<input type="hidden" name="toppingcnt" id="toppingcnt" value="0" />
			
			<br/><br/>
			<p>가격:<%=price%>원</p>
			<p>레터링 이미지(파일에 올린 그림대로 레터링 해드려요. png만 가능)</p>
			<input type="file" name="file1" accept="image/png" /> 
			<hr/>
			<input type="submit" value="커스텀 마카롱 등록" />	
		</div>	
			
	</form>
		<input type="button"  value="목록으로" onclick="location.href='ev_tor_list?kind=a';" />
</div>














<%@ include file="../_inc/footer.jsp" %>