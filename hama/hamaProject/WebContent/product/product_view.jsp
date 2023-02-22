<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<%
request.setCharacterEncoding("utf-8");
ProductInfo pi = (ProductInfo)request.getAttribute("pi");
//화면에서 보여줄 상품 정보들을 저장한 ProductInfo 형 인스턴스 pi를 받아옴 

int realPrice = pi.getPi_price(); //수량 변경에 따른 가격 계산을 위한 변수 
String price =  pi.getPi_price() + "원"; //가격 출력을 위한 변수 

if(pi.getPi_dc() > 0) { //할인률이 있으면 
	realPrice = realPrice * (100-pi.getPi_dc()) / 100; 
	price = "<del>" + pi.getPi_price() + "</del>" + "&nbsp;&nbsp;&nbsp;"
	+ realPrice  + "원";
}

String limit = "당일 섭취 권장 식품"; // 유통기한 저장할 변수 
if(pi.getPi_limit() !=null && !pi.getPi_limit().equals("")){
	if(pi.getPi_limit().equals("b")) limit = "제조일로부터 6개월 ";
}

String al = "해당 사항 없음"; // 알러지 저장할 변수 
//상품대분류아이디가 박스거나 커스텀박스면(둘다마카롱인데 우리가 알러지사항안넣음)
if(pi.getPc_id().equals("bx")||pi.getPc_id().equals("cb")) al ="달걀, 우유";
if(pi.getPi_alg() !=null && !pi.getPi_alg().equals("")){
	al = pi.getPi_alg() ;
}
//url용 상품 대분류
String o = "";
String pc ="";
if (request.getParameter("o")!= null && !request.getParameter("o").equals("")){
	o=request.getParameter("o");}
if (request.getParameter("pc")!= null && !request.getParameter("pc").equals("")){
	pc =request.getParameter("pc");}
%>

<style>
   .container{margin: 0 auto;  width: 70%; display:flex; 
      justify-content: space-around; align-items: center; 
     }
   .pdtImg {width:300px;}
   .pdtImg > a > img {width : 100%;}
   .desc {padding: 20px;} 
   #desc {margin-bottom:5%;}
   .desc > p {padding: 3% 0 ;}
   .desc > strong {padding: 3% 0 ;}
   #cnt {width : 20px; text-align:right;}
   .buyBtn{ margin-top:5%; width:500px; height: 30px;}
   #product_name{font-size: 30px; }
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
		
	}else{ //바로 구매일 경우 => 결제폼으로 가기  수량, 상품아이디 등 컨트롤 값을 가져가야
		//폼을 사용해야한다. 액션을 안정했고 여기서 폼을 제출한다.  
		frm.action = "order_form";  //당연히 서블릿...
		frm.submit();
	}
	
<%}else { %> //로그인 안되어있으면
	if(confirm("로그인을 한 뒤 장바구니를 이용하실 수 있습니다.\n로그인 하시겠습니까?")){
		//로그인하겠다고하면
		location.href="login_form?url=/hamaProject/product_view?piid=<%=pi.getPi_id()%>";
	} 
<%}  %>
}






</script>



<div class="container">
	<div class="pdtImg" >
		<img width=300 src="/hamaProject/product/pdt_img/<%=pi.getPc_id()%>/<%=pi.getPi_id()%>.png" alt="img">
	</div>
   <!-- 상품 관련 영역  kind = 바로구매(d : 하나밖에없음)인지, 장바구니(상품여러개일수도+
   구매한 다음 장바구니 비워야한다)인지 -->
   
   <div class="desc"><!-- 제품설명 -->
      <form name="frm" method="post" >
         <input type="hidden" name="kind" value="d" /><br />
         <input type="hidden" name="piid" value="<%=pi.getPi_id()%>" /><br />
         
         <p id="product_name"><%=pi.getPi_name()%></p><br />
         <p><%=pi.getPi_desc() %></p><br />
         <p>영양성분&nbsp;&nbsp;&nbsp;&nbsp;<%=pi.getPi_kcal()%>kcal</p><br />
         <p>알러지&nbsp;&nbsp;&nbsp;&nbsp;<%=al%></p><br />
         <p>유통기한&nbsp;&nbsp;&nbsp;&nbsp;<%=limit %></p><br />
  

         
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
      <input type="button"  value="목록으로" onclick="location.href='product_list?pc=<%=pc%>&o=<%=o%>';" />
   </div>
 
</div>




<%@ include file="../_inc/footer.jsp" %>
