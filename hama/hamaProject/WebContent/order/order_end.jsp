<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>   
<link rel="stylesheet" href="/hamaProject/css/order_end.css">
<%
request.setCharacterEncoding("utf-8");
OrderInfo oi  = (OrderInfo)request.getAttribute("orderInfo");
//주문 공통 정보(배송지, 및 결제 관련 정보)를 저장한 인스턴스를 받아옴
ArrayList<OrderDetail> dl = oi.getDetailList();
//주문 내 상품 정보들을 ArrayList<OrderDetail> 로 받아옴
%>
    <div  class="order_end">
        <div>
            <h2>결제 완료</h2>
            <h3>주문 정보</h3>
            <div class="order_info">
                <div>
                    <div class="order_wrap">
                        <div class="order_title">
                            <div>주문번호</div>
                        </div>
                        <div class="order_content">
                            <div><%=oi.getOi_id()%></div>
                        </div>
                    </div>
                    <div class="order_wrap">
                        <div class="order_title">
                            <div>주문일자 </div>
                        </div>
                        <div class="order_content">
                            <div><%=oi.getOi_date()%></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
      
        <h3>배송 정보</h3>
        <div class="order_info">
            <div>
                <div class="order_wrap">
                    <div class="order_title">
                        <div>보내는이</div>
                    </div>
                    <div class="order_content">
                        <div><%=oi.getOi_sender()%></div>
                    </div>
                </div>
                <div class="order_wrap">
                    <div class="order_title">
                        <div>보내는이 연락처 </div>
                    </div>
                    <div class="order_content">
                        <div><%=oi.getOi_sephone()%></div>
                    </div>
                </div>
                <div class="order_wrap">
                    <div class="order_title">
                        <div>받는이</div>
                    </div>
                    <div class="order_content">
                        <div><%=oi.getOi_name()%></div>
                    </div>
                </div>
                <div class="order_wrap">
                    <div class="order_title">
                        <div>받는이 연락처 </div>
                    </div>
                    <div class="order_content">
                        <div><%=oi.getOi_phone()%></div>
                    </div>
                </div>
                <div class="order_wrap">
                    <div class="order_title">
                        <div>배송지</div>
                    </div>
                    <div class="order_content">
                        <div><%=oi.getOi_zip()%></div>
                    </div>
                </div>
                <div class="order_wrap">
                    <div class="order_title">
                        <div></div>
                    </div>
                    <div class="order_content">
                        <div><%=oi.getOi_addr1()%> </div>
                    </div>
                    <div class="order_content">
                        <div><%=oi.getOi_addr2()%></div>
                    </div>
                </div>
                <div class="order_wrap">
                    <div class="order_title">
                        <div>배송 희망일</div>
                    </div>
                    <div class="order_content">
                        <div><%=oi.getOi_redate()%></div>
                    </div>
                </div>
            </div>
        </div>
        <h3>상품정보</h3>
<%
for(int i=0; i<dl.size(); i++){ 
    OrderDetail od = dl.get(i);
    String img = "/hamaProject/product/pdt_img/"+od.getPi_id().substring(0,2)+"/"+ od.getOd_img();
    String lnk = "/hamaProject/product_view?piid=" + od.getPi_id();
%>
        <div class="order_info">
            <div>
                <div class="order_wrap">
                    <div class="order_title">
                        <div>상품명</div>
                    </div>
                    <div class="order_content">
                        <div><%=od.getOd_name() %></div>
                    </div>
                </div>
                <div class="order_wrap">
                    <div class="order_title">
                        <div>수량 </div>
                    </div>
                    <div class="order_content">
                        <div><%=od.getOd_cnt() %> 개</div>
                    </div>
                </div>
                <div class="order_wrap">
                    <div class="order_title">
                        <div>가격 </div>
                    </div>
                    <div class="order_content">
                        <div><%=od.getOd_price() %> 원</div>
                    </div>
                </div>
            </div>
        </div>
<%} %>
        <h3>합계</h3>
        <div class="order_info">
            <div>
                <div class="order_wrap">
                    <div class="order_title">
                        <div>총 금액</div>
                    </div>
                    <div class="order_content">
                        <div><%=oi.getOi_pay() %>원</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
