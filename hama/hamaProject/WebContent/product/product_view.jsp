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




%>

<style>
   .container{margin: 0 auto;  width: 70%; display:flex; 
      justify-content: space-evenly;  align-items: center; }
   .pdtImg {width:300px;}
   .pdtImg > a > img {width : 100%;}
   .desc {padding: 20px;} 
   #desc {margin-bottom:5%;}
   .desc > p {padding: 3% 0 ;}
   .desc > strong {padding: 3% 0 ;}
   #cnt {width : 20px; text-align:right;}
   .buyBtn{ margin-top:5%; width:500px; height: 30px;}
   #product_name{ }
</style>





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
         <p>영양성분&nbsp;&nbsp;&nbsp;&nbsp; <%=pi.getPi_kcal()%></p><br />
         <p>알러지&nbsp;&nbsp;&nbsp;&nbsp; <%=al%></p><br />
         <p><strong>유통기한 </strong>&nbsp;&nbsp;&nbsp;&nbsp; <%=limit %></p><br />
  

         
         <div class="btn">
            <p>상품명 : <%=price %></p>
            <input type="button" value="-" onclick="setCnt(this.value);"  />
            <input type="text" name="cnt" id="cnt" value="1" readonly ="readonly" />
            <input type="button" value="+" onclick="setCnt(this.value);"  />
         </div>
         <div class="buyBtn">
            <input type="button" value="장바구니 담기" id="buyC" onclick="buy('c');" />
            <input type="button" value="바로구매"  id="buyD"  onclick="buy('d');" />
         </div>
      </form>
      <a href="javascript:history.back();">목록으로</a>
   </div>
 
</div>




<%@ include file="../_inc/footer.jsp" %>
</body>
</html>