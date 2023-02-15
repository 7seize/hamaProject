<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<link rel="stylesheet" href="../_inc/css/common.css">
<link rel="stylesheet" href="../_inc/css/header.css">
<%@ include file="./my_menu.jsp" %>


<style>
    .my-leave{ width: 100%; }
    .leave-set{
        width: 80%;
        margin: 30px auto;
    }
    
    .leave-table{
        border-collapse: collapse;
        width: 100%;
        margin-bottom: 5px;
        height: 100px;
    }
    .leave-table tr{ border: 1px solid gray; }
    .leave-table tr td input{ height: 25px; }
    
    .leave-table tr td:nth-of-type(1){
        background-color: #CCE5FF;
        border: 1px solid gray;
        text-align: center;
    }
    .leave-text input{ float: right; }
    .leave-text{ font-size: 11px; }
</style>

<form name="frmLeave" action="/hamaProject/member_form_up" method="post" style="width: 100%">
<input type="hidden" name="kind" value="d" />
<div class="my-leave">
    <h2>회원 탈퇴</h2>
    <div class="leave-set">
        <table class="leave-table">
            <tr>
                <td>아이디</td>
                <td>&nbsp;&nbsp;<input type="text"></td>
            </tr>
            <tr>
                <td>비밀번호</td>
                <td>&nbsp;&nbsp;<input type="password"></td>
            </tr>
        </table>
        <div class="leave-text">
            <span>※회원탈퇴를 신청하시면 즉시 탈퇴 처리됩니다.</span>
            <input type="submit" value="탈퇴하기" />
            <p>사라진 포인트와 정보는 복구할 수 없습니다.</p>
        </div>
    </div>
</div>
</form>

</div>

<%@ include file="../_inc/footer.jsp" %>