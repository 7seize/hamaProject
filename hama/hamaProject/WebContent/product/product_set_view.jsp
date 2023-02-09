<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     
<%@ include file="../_inc/header.jsp" %>
<%
request.setCharacterEncoding("utf-8");

ArrayList<ProductInfo> piList = (ArrayList<ProductInfo>)request.getAttribute("piList");
ArrayList<ProductCustom> pcList = (ArrayList<ProductCustom>)request.getAttribute("piList");
ProductInfo pi = null;
ProductCustom pc = null;
%>
<link rel="stylesheet" href="/hamaProject/css/product_set_view.css">
<script defer src="/hamaProject/js/product_set_view.js"></script>
<%
//임시로 박스갯수 10개 나중에 받아올값임
int boxsize = 10;
//마카롱 갯수
int macc = piList.size();
//커스텀 마카롱 갯수
int maccCur = pcList.size();
%>
<script>
let maccNum = <%=boxsize %>;
</script>
<body>
    <div class="slider-contain">
        <div class="slide slide_wrap">
<%for(int i = 0; i < macc; i++ ){ 
	pi =piList.get(i);
%>
            <div class="slide_item">
                <div class="macc-img" name="<%=pi.getPi_id()%>"><img src="/hamaProject/product/pdt_img/mc/<%=pi.getPi_id()%>.png"></div>
                <div><%=pi.getPi_name() %></div>
                <div class="macc-decs">▲ 상세 정보</div>
                <div class="decs-detail mc-unactive">
                    <div class = "decs-close">▼</div>
                    <div><%=pi.getPi_name() %></div>
                    <div><%=pi.getPi_desc() %></div>
                    <div><%=pi.getPi_alg() %></div>
                </div>
            </div>
<%} %>
<%for(int i = 1; i <= maccCur; i++ ){ 
	pc =pcList.get(i);
%>
            <div class="slide_item">
                <div class="macc-img" name="mc100-<%=pc.getPmc_idx()%>"><img src="/hamaProject/product/pdt_img/mc/mc100.png"></div>
                <div><%=pc.getPmc_name()%></div>
                <div class="macc-decs">▲ 상세 정보</div>
                <div class="decs-detail mc-unactive">
                    <div class = "decs-close">▼</div>
                    <div><%=pc.getPmc_name()%></div>
                    <div>당도 : <%=pc.getPmc_sugar()%>%</div>
<%
String fill = pc.getPmc_pl();
if(fill.equals('a')){
	fill ="적게";
}else if(fill.equals('c')){
	fill = "많이";
}else{
	fill = "보통";
}
%>
                    <div>필링량 : <%=fill %></div>
<%
String topping1= pc.getPmc_tp1();
if(topping1 == null){
%>
					<div>토핑1 : 없음</div>
<%}else{ %>
					<div>토핑1 : <%=topping1 %></div>
<%} %>
<%
String topping2= pc.getPmc_tp1();
if(topping2 == null){
%>
					<div>토핑2 : 없음</div>
<%}else{ %>
					<div>토핑2 : <%=topping2 %></div>
<%} %>
<%
String vg = pc.getPmc_vg();
if(vg == null){
	vg = "아니요";
}else{
	vg = "예";
}
%>
                    <div>비건 여부 : <%=vg%></div>
<%
String im = pc.getPmc_img();
if(im == null){
	im = "아니요";
}else{
	im = "예";
}
%>
                    <div>사진 여부 : <%=im%></div>
                </div>
            </div>
<%} %>
            <div class="slide_prev_button slide_button">◀</div>
            <div class="slide_next_button slide_button">▶</div>
            <ul class="slide_pagination"></ul>
        </div>
    </div>
    <div>
        
        <div class="mcc_box">
            <div><img class="select_macc" src="/hamaProject/product/pdt_img/vmc/mc100_v.png"></div>

        </div>
    </div>
    <form>
        <input id="result" type="button" value="장바구니에 담기">
        <input id="resultBuy" type="button" value="바로 구매">
    </form>   
</body>
</html>
