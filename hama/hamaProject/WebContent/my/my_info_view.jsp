<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<link rel="stylesheet" href="../_inc/css/common.css">
<link rel="stylesheet" href="../_inc/css/header.css">
<%@ include file="./my_menu.jsp" %>

<style>
    .my-info{ width: 100%; }
    .info-change{
        margin: 50px auto;
        width: 80%;
        border-collapse: collapse;
        height: 500px;
    }
    .info-change tr{
        border-top: 1px solid rgb(180, 180, 180);
    }
    .info-change tr:nth-of-type(1){
        border-top: 2px solid rgb(180, 180, 180);
    }
    .info-change tr:nth-of-type(5){
        border-bottom: 2px solid rgb(180, 180, 180);
    }
    .info-change tr td:nth-child(1){
        background-color: #CCE5FF;
    }
    .info-change tr td{ padding-left: 5px; }
    #pwd-change div{ margin-bottom: 10px; }
    .my-info > div{
        text-align:center;
        margin-bottom: 50px;
    }
    .my-info > div > input{
        background-color: beige;
        border: 1px solid gray;
        padding: 5px;
        cursor: pointer;
    }
</style>
<div class="my-info">
    <h2>개인정보 수정</h2>
    <table class="info-change">
        <tr>
            <td><label for="mi_id">아이디</label></td>
            <td><span>아이디 가져오기</span></td>
        </tr>
        <tr>
            <td><label for="mi_pw">비밀번호 변경</label></td>
            <td id="pwd-change">
                <div>
                    <label for="">현재 비밀번호 </label>
                    <input type="password" name="mi_pw" id="mi_pw" maxlength="20"/>
                </div>
                <div>
                    <label for="">새 비밀번호 </label>
                    &nbsp;&nbsp;
                    <input type="password" name="mi_pw" id="mi_pw" maxlength="20"/>
                </div>
                <div>
                    <label for="">비밀번호 확인</label>
                    <input type="password" name="mi_pw" id="mi_pw" maxlength="20"/>
                </div>
            </td>
        </tr>
        <tr>
            <td><label for="">이메일</label></td>
            <td>
                <input type="text" name="e1" size="10"/> @
                <input type="text" name="e3" id="e3" size="10"/>
                <select name="e2" id="e2">
                    <option value="">도메인 선택</option>
                </select>
            </td>
        </tr>
        <tr>
            <td><label for="">휴대폰</label></td>
            <td>
                <input type="text" value="010"  size="3" readonly> - 
                <input type="text" name="p2" size="4" maxlength="4" onkeyup="onlyNum(this);"/> - 
                <input type="text" name="p3" size="4" maxlength="4" onkeyup="onlyNum(this);"/>
            </td>
        </tr>
        <tr>
            <td><label for="">주소</label></td>
            <td>배송지 주소 관리는 [배송지 관리]에서 수정, 등록 합니다.</td>
        </tr>
    </table>
    <div><input type="button" value="수정하기"></div>
</div>


</div>

<%@ include file="../_inc/footer.jsp" %>