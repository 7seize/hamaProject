<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<%
request.setCharacterEncoding("utf-8");
ArrayList<ProductCustom> customList = 
(ArrayList<ProductCustom>)request.getAttribute("customList");
//커스텀 마카롱을 보여줌 

%>
<style>
.container{ width:60%; margin:0 auto; }
table{border-collapse: collapse;}
th, td{ text-align: center;}
tr{border-bottom: 1px dotted black;}
</style>
<script>

function getSelectedValues(){
	//체크된 놈들의 ocidx를 가져와야함
	//체크박스들 중 선택된 체크박스들의 값들을 쉼표로 구분하여 문자열로 리턴하는 함수
		var chk = document.frmCustom.chk; //chk 컨트롤 배열에서 선택된 체크박스의 값들을
	//누적저장할 변수
	//만약 하나만 삭제하는거면 하나가 들어와서 배열이 안되어서, 배열 안에 들어가지도 못함..
	//그러니 hidden 객체로 <input type="hidden" name="chk" value="" />
	//로 같은 이름인 chk로 하나 넣어놔서 하나만 선택삭제여도 배열에 들어갈 수 있도록 만든다. 대신 for문 1부터 돌려야함 
		var idxs = "";
		for(var i=1; i<chk.length; i++){ //히든객체로 하나 넣어놨기에 1부터 돌림 히든돌리지않게
			if (chk[i].checked) idxs +=  "," + chk[i].value;
		}
			return idxs.substring(1);
}

function chkDel(){
	//사용자가 선택한 상품들을 삭제시키는 함수
	//체크된 놈들의 pcidx를 가져와야함
		var pcidx = getSelectedValues();
		//선택한 pcidx값들이 쉼표를 기준으로 '1,2,3,4' 문자열로 저장됨
		if(pcidx == "") alert("삭제할 상품을 선택하세요.");
		else			customDel(pcidx);
		
}

function customDel(ocidx){ //////////아직 안만들었다.
	//장바구니 내 특정 상품을 삭제하는 함수
		if(confirm("정말 삭제하시겠습니까?")){
			//cart_proc_del 매핑 CartProcDelCtrl  
			$.ajax({
				type : "POST",
				url : "/mvcSite/cart_proc_del",
				data : {"ocidx" : ocidx},
				success : function(chkRs){
					if(chkRs==0){
						alert("상품 삭제에 실패했습니다. \n 다시 시도하세요");
					}
					location.reload(); //새로고침 
				}
			}); 
	}
}
</script>


<div class="container">
	<h2>나의 마카롱 레시피 </h2>
	<div>
		<form name="frmCustom" >
			<input type="hidden" name="chk" value="" />
			<input type="hidden" name="kind" value="c" />
			<table width="800" cellpadding="5">	
				<tr>
					<th>선택</th>
					<th>번호</th>
					<th>이미지</th>
					<th>이름</th>
					<th>맛</th>
					<th>가격</th>
					<th>삭제</th>
				</tr>
				
			<%
			if(customList.size()>0){ //커스텀 마카롱 리스트가 있을 경우
				int total=0; //사용안함나중에지울것
				for(int i=0; i<customList.size() ; i++){
					ProductCustom pc = customList.get(i);
					
					int pcidx = pc.getPmc_idx(); //커스텀 마카롱 리스트에 일련번호
					String title = pc.getPmc_name();
			%>
			
				<tr>
					<td width=10%">
						<input type="checkbox" name="chk" value ="<%=pcidx %>" checked="checked"/>
					</td>
					<td width="10%"><%= i+1 %></td>
					<td width="10%">
						<img src="/hamaProject/product/pdt_img/mc/<%=pc.getPi_img1()%>" width="40" height="50" border="0" />
					</td>
					<td><a href='product_custom_view?pcidx=<%=pcidx%>&miid=<%=pc.getMi_id()%>'><%=title %></a></td>
					<td><%= pc.getPi_name() %></td>
					<td><%= pc.getPmc_price()%></td>
					<td width="5%">
						<input type="button" value="삭제" onclick="cartDel(<%=pcidx%>);"  />
					</td>
				</tr>
				
				
				
	<%		
				}
	%>
			</table>
	
		<%
			}else{ //커스텀에 없을 경우
				out.println("<tr><td colspan=8 align='center'> 커스텀 마카롱 레시피가 없습니다.</td></tr></table>");
			}
		%>
		
		</form>
	</div>



</div>
















<%@ include file="../_inc/footer.jsp" %>