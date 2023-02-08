<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<%
request.setCharacterEncoding("utf-8");
ProductInfo pi = (ProductInfo)request.getAttribute("pi");
//화면에서 보여줄 상품 정보들을 저장한 ProductInfo 형 인스턴스 pi를 받아옴 
ArrayList<ProductOut> stockList = pi.getStockList();
//pi에 들어있는 옵션별 재고량 목록을 받아옴

int realPrice = pi.getPi_price(); //수량 변경에 따른 가격 계산을 위한 변수 
String price =  pi.getPi_price() + "원"; //가격 출력을 위한 변수 
if(pi.getPi_dc() > 0) { //할인률이 있으면 
	realPrice = realPrice * (100-pi.getPi_dc()) / 100; 
	price = "<del>" + pi.getPi_price() + "</del>" + "&nbsp;&nbsp;&nbsp;"
	+ realPrice  + "원";
}

%>
<link rel="stylesheet" href="../_inc/css/common.css">
<link rel="stylesheet" href="../_inc/css/header.css">
<style>
	.container{margin: 100px auto;  width: 70%; display:flex; 
		justify-content: space-evenly;  align-items: center; }
	.pdtImg {width:30%; }
	.pdtImg > a > img {width : 100%;}
	.desc {} 
	#desc {margin-bottom:5%;}
	.desc > p {padding: 3% 0 ;}
	.desc > strong {padding: 3% 0 ;}
	.none {display: none;}
	.btn {border:1px solid gray; }
	#cnt {width : 20px; text-align:right;}
	.buyBtn{ margin-top:5%; width:500px; height: 30px;}
</style>
<script>

function setCnt(chk){// 수량개수
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
	<% if (isLogin){%>
	var cnt = frm.cnt.value; // 우리는 옵션이 없어서 사이즈대신 수량으로 교체
	if(cnt==""){
		alert("수량을 선택하세요");
		return;
	}
	
	if(chk=="c"){ //장바구니 담기일 경우
		var cnt = frm.cnt.value; //개수는 따로 받아와야함
		$.ajax({
			type : "POST",
			url : "/hamaProject/cart_proc_in",
			data : {"piid":"<%= pi.getPi_id()%>", "cnt":cnt},
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
		
	}else{ //바로 구매일 경우 => 결제폼으로 가기 사이즈, 수량, 상품아이디 등 컨트롤 값을 가져가야
		//폼을 사용해야한다. 액션을 안정했고 여기서 폼을 제출한다.  
		frm.action = "order_form";  //당연히 서블릿...
		frm.submit();
	}
	
	
	
	
	<%}else { %>
	location.href= "login_form?url=/hamaProject/product_view?piid=<%=pi.getPi_id()%>";
	<%}  %>
}
</script>
<div class="container">
	<div class="pdtImg">
		<a href=""><img src="/hamaProject/product/pdt_img/mc/mc101.png<%=pi.getPi_img1()%>" alt="Hamaron"></a>
	</div>
	<!-- 상품 관련 영역  kind = 바로구매(d : 하나밖에없음)인지, 장바구니(상품여러개일수도+
	구매한 다음 장바구니 비워야한다)인지 -->
	<div class="desc"><!-- 제품설명 -->
		<form name="frm" method="post" >
			<input type="hidden" name="kind" value="d" />
			<input type="hidden" name="piid" value="<%=pi.getPi_id()%>" />
			<a href="product_list?pc=<%=pi.getPc_id()%>" ><%=pi.getPi_name() %></a>

			<strong>제품이름</strong>
			<p>제품설명</p>
			<input type="button"  id="desc" value="원산지·영양성분 ·알레르기 유발성분 정보  →"  />
			<div><!-- 위 원산지 버튼 누르면 나타나게 , 그전에는 안보이게 -->
				<p class="none">
					제품이름:
					영양성분 : 340kcal
					원산지 : … 
					알러지 : a,b, c, d, e…
				</p>
			</div>
			<strong>유통기한 </strong>
			<p>당일 섭취 권장 식품</p>
			<strong>보관방법 </strong>
			<p>직사광선을 피하고 서늘한 곳 보관</p>
			<div class="btn">
				<p>상품명 : </p> <p>5000원</p>
				<input type="button" value="-" onclick="setCnt(this.value);"  />
				<input type="text" name="cnt" id="cnt" value="1" readonly ="readonly" />
				<input type="button" value="+" onclick="setCnt(this.value);"  />
			</div>
			<div class="buyBtn">
				<input type="button" value="장바구니 담기" id="buyC" onclick="buy('c');" />
				<input type="button" value="바로구매"  id="buyD"  onclick="buy('d');" />
			</div>
		</form>
	</div>
</div>



<%@ include file="../_inc/footer.jsp" %>
</body>
</html>