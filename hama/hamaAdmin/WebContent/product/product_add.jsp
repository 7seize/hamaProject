<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.time.*" %>
<%@ page import = "vo.*" %>     
<%@ include file="../_inc/header.jsp" %>

<link rel="stylesheet" href="/hamaAdmin/css/product_add.css">
<style>
    table{border-collapse: collapse;} 
    h2{margin: 20px 0 10px 0;}
</style>

<%
	String setpiid = request.getParameter("setpiid");
    if(setpiid == null || setpiid.equals("")) {
        setpiid ="-";
    }
%>

<script>
    const catVal = function (val) {
        let cat = val.value;
        let url = "../productadd?cat=" + cat;
        window.location.href = url;
        
    }
</script>

<div>
    <h2>주문 상세 내역</h2>

    <div class="container"  >
        <table>
            <tr>
                <th>상품코드</th>
                <td id="setpiid"><%=setpiid%></td>
                <th>분류</th>
                <td>
                    <select onchange="catVal(this)">
                    	<option value="-">-</option>
                        <option value="ck" <%if(!setpiid.equals("-") &&  setpiid.subSequence(0,2).equals("ck")){ %> selected = "selected" <%} %>>ck</option>
                        <option value="mc" <%if(!setpiid.equals("-") &&  setpiid.subSequence(0,2).equals("mc")){ %> selected = "selected" <%} %>>mc</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>상품명</th>
                <td > - </td>
                <th><label for="isView">개시 여부 </label></th>
                <td>
                	<label>
                		<input name="isView" type="radio" checked="checked" >개시</input>
                	</label>
                	<label>
                		<input  name="isView" type="radio">미개시</input>
                	</label>
                </td>
            </tr>
            <tr>
                <th>가격</th>
                <td >- 원</td>
                <th>할인가</th>
                <td>- %</td>
            </tr>
            <tr>
                <th>이미지 1</th>
                <td >-</td>
                <th>이미지 2</th>
                <td>-</td>
            </tr>
            <tr>
                <th>알러지 유발 재료</th>
                <td >-</td>

            </tr>
            <tr>
                <th>상세 설명</th>
                <td >-</td>

            </tr>
        </table>
    </div>
</div>

<div>
    <button onclick="goBack()">뒤로가기</button>
</div>

</main>
</body>
</html>