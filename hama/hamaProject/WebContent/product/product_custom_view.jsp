<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<%
request.setCharacterEncoding("utf-8");
ProductCustom pc = (ProductCustom)request.getAttribute("pc");

String tpname1 = "";
String tpname2 = "";
//토핑아이디가 비어있지않거나, 빈문자열이 아닐 때만, 토핑 이름을 가져온다. 
if(pc.getPmc_tp1()!=null && !pc.getPmc_tp1().equals("")){
	tpname1 = (String)request.getAttribute("tpname1");	
}
if(pc.getPmc_tp2()!=null && !pc.getPmc_tp2().equals("")){
	tpname2 = ", "+(String)request.getAttribute("tpname2");
}

String vg = "논비건"; 
if(pc.getPmc_vg().equals("y")) vg = "비건";

String pl = "보통";
if(pc.getPmc_pl().equals("c")) pl = "많이";
if(pc.getPmc_pl().equals("a")) pl = "적게";

boolean letterimg = false; //유저가 올린 레터링
if(pc.getPmc_img()!=null && !pc.getPmc_img().equals("")){
	letterimg = true;
}


int realPrice = pc.getPmc_price(); //수량 변경에 따른 가격 계산을 위한 변수 
String price =  pc.getPmc_price() + "원"; //가격 출력을 위한 변수 





%>
<style>
.container{ width:60%; margin:0 auto; }
.temp{display:flex; justify-content: center; margin:100 auto;}
.desc{margin:0 0 0 20%; border-left:1px solid #f6bc49; padding : 0 0 0 50px;}
</style>


<script>
function setCnt(chk){ //수량 조절 함수
	var price = <%=realPrice%>;
	var frm = document.frm;
	var cnt = parseInt(frm.cnt.value);
	
	if (chk == "+" && cnt<99 )		  frm.cnt.value =cnt+ 1;
	else if(chk == "-" && cnt > 1)		frm.cnt.value= cnt- 1;	
	
	var obj = document.getElementById("total");
	total.innerHTML = frm.cnt.value * price;
}

function buy(chk){
	//[장바구니 담기] 또는 [바로구매] 버튼 클릭시 작업할 함수
   var frm = document.frm;
<% if (isLogin){%> //로그인되어있을 때 
	if(chk=="c"){ //장바구니 담기일 경우
		var cnt = frm.cnt.value; //개수는 따로 받아와야함
		$.ajax({
			type : "POST",
			url : "/hamaProject/cart_proc_in",		
			data : {"piid":"mc100", "cnt":cnt, "custombox":"<%=pc.getPmc_idx()%>"},
			success : function(chkRs){
				if(chkRs == 0){ //장바구니 담기에 실패했을 경우
					alert("장바구니 담기에 실패했습니다. \n 다시 시도해보세요.");
					 return;
				}else{ //장바구니 담기에 성공했을 경우
					if(confirm("장바구니에 담았습니다. \n 장바구니로 이동하시겠습니까?")){
						//보겠다고하면
						location.href="cart_view";
					}
				}
			}
		}); 
		
	}else{ //바로 구매일 경우 => 결제폼으로 가기  수량, 상품아이디 등 컨트롤 값을 가져가야
		//폼을 사용해야한다. 액션을 안정했고 여기서 폼을 제출한다.  
		frm.action = "order_form";  //당연히 서블릿...
		frm.submit();
	}
	
<%}else { %> //로그인 안되어있으면
	if(confirm("로그인을 한 뒤 장바구니를 이용하실 수 있습니다.\n로그인 하시겠습니까?")){
		//로그인하겠다고하면  
		location.href="login_form?url=/hamaProject/product_custom_view?pmcidx=<%=pc.getPmc_idx()%>";
	} 
<%}  %>
}
</script>







<div class="container">
	<h2>[나의 마카롱]&nbsp;&nbsp;&nbsp;<%=pc.getPmc_name() %></h2>
	<div class="temp">
		<img src="/hamaProject/product/pdt_img/mc/<%=pc.getPi_img1()%>" width="200"  border="0" class="img"/>
		
		<div class="desc">
			<p>이름 : <%=pc.getPi_name() %> </p>
			<p>당도  : <%=pc.getPmc_sugar() %>%</p>
			<p>비건 옵션: <%=vg%> </p>
			<p>필링양 : <%=pl%> </p>
			<p>맛  : <%=pc.getPi_name() %>맛</p>
			<p>토핑 : <%=tpname1 %><%=tpname2 %></p>
			<%if(letterimg) {%>	
			<p>레터링 이미지 : <p>
			<img src="/hamaProject/product/upload/<%=pc.getPmc_img()%>" alt="<%=pc.getPmc_img()%>" width="100" />
			<%}else{%>
			<span>레터링 이미지 미업로드</span>
			<%}%>
	
<!-- 결제관련 -->	
	<form name="frm" method="post" >
        <input type="hidden" name="kind" value="d" />
      	<input type="hidden" name="piid" value="mc100" />
        <input type="hidden" name="custombox" value="<%=pc.getPmc_idx()%>" />  
        <div class="btn">
           <p>구매가격 : <span id="total"><%=realPrice %></span>원</p><br/>
           <input type="button" value="-" onclick="setCnt(this.value);"  />
           <input type="text" name="cnt" id="cnt" value="1" readonly ="readonly" style="width:30px;font-size:1rem;" />
           <input type="button" value="+" onclick="setCnt(this.value);"  />
        </div>
        <div class="buyBtn">
           <input type="button" value="장바구니 담기" id="buyC" onclick="buy('c');" />
           <input type="button" value="바로구매"  id="buyD"  onclick="buy('d');" />
        </div>  
    </form>	
    		
		</div><!-- desc div 끝 -->	
	</div><!-- temp div 끝 -->
	<input type="button"  value="목록으로" onclick="location.href='product_custom_list';" />

</div>


<%@ include file="../_inc/footer.jsp" %>