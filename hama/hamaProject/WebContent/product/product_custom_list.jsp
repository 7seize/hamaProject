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
.container{width: 60%; margin:0 auto; }
table{border-collapse: collapse;} 
th, td{ text-align: center;}
tr{border-bottom: 1px dotted black;}
</style>
<script>


//커스텀 마카롱의 인덱스를 가져가  삭제하는 함수(view=n으로)
function customDel(pmcidx){
	if(confirm("정말 삭제하시겠습니까?")){
		//product_custom_del 매핑 ProductCustomDelCtrl  
		$.ajax({
			type : "POST",
			url : "/hamaProject/product_custom_del",
			data : {"pmcidx" : pmcidx},
			success : function(chkRs){
				console.log('g');
				if(chkRs!=1){
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
			<table width="100%" cellpadding="5">	
				<tr>
					
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
					
					int pmcidx = pc.getPmc_idx(); //커스텀 마카롱 리스트에 일련번호
					String title = pc.getPmc_name();
			%>
			
				<tr>
					
					<td width="10%"><%= i+1 %></td>
					<td width="10%">
						<img src="/hamaProject/product/pdt_img/mc/<%=pc.getPi_img1()%>" width="40" height="50" border="0" />
					</td>
					<td><a href='product_custom_view?pmcidx=<%=pmcidx%>&miid=<%=pc.getMi_id()%>'><%=title %></a></td>
					<td><%= pc.getPi_name() %></td>
					<td><%= pc.getPmc_price()%></td>
					<td width="5%">
						<input type="button" value="삭제" onclick="customDel(<%=pmcidx%>);"  />
						
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
	<input type="button" value="추가하기" onclick="location.href='product_custom_form';"/>


</div>





<%@ include file="../_inc/footer.jsp" %>