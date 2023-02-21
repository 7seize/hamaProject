<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("utf-8");
String url = request.getParameter("url"); //로그인할 후 이동할 페이지 주소 
if(url == null) url = "index.jsp"; 
%>


<link rel="stylesheet" href="/hamaAdmin/css/login_form.css">
    <div class="login-contain">
        <h1>관리자 로그인</h1>
        <div class="login-form">
            <form name="frmLogin" action="login" method="post">
           	 	<input type="hidden" name="url" value="<%=url %>" />
                <div class="login-form-inner">
                    <div>
                        <p><label for="login_email">아이디</label></p>
                        <input type="text" name="uid" value="admin1" />
                    </div>
                    <div>
                        <p><label for="login_password">비밀번호</label></p>
                        <input type="password" name="pwd" value="1111" />
                    </div>
                </div>
                <div class="login-submit">
                    <input type="submit" value="login" />
                </div>

            </form>
        </div>
    </div>
</body>
</html>