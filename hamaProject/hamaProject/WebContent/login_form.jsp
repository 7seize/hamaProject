<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="_inc/header.jsp" %>
<link rel="stylesheet" href="/hamaProject/css/login_form.css">
    <div class="login-contain">
        <h1>로그인</h1>
        <div class="login-form">
            <form name="frmLogin" action="login" method="post">
                <div class="login-form-inner">
                    <div>
                        <p><label for="login_email">아이디</label></p>
                        <input type="text" name="uid" value="atest" />
                    </div>
                    <div>
                        <p><label for="login_password">비밀번호</label></p>
                        <input type="password" name="pwd" value="1111" />
                    </div>
                </div>
                <div class="login-submit">
                    <input type="submit" value="login" />
                </div>
                <div class="un-login">
                    <a href="">아이디 찾기</a>
                    <span>/</span>
                    <a href="">비밀번호 찾기</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>