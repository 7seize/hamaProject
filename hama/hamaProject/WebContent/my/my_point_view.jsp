<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<link rel="stylesheet" href="../_inc/css/common.css">
<link rel="stylesheet" href="../_inc/css/header.css">
<%@ include file="./my_menu.jsp" %>

<style>
    .my-point{
        display: flex;
        width: 100%;
        justify-content: center;
    }
    .point-now{
        border: 1px solid rgb(102, 102, 102);
        width: 50%;
        height: 130px;
        border-right: none;
        padding: 10px;
    }
    .point-now > h2{
        text-align: right;
        font-size: 50px;
        height: 100px;
    }
    .point-history{
        width: 50%;
        
    }
    .point-history > p{
        border: 1px solid rgb(102, 102, 102);
        height: 30px;
        line-height: 30px;
        padding-left: 5px;
    }
    .point-history > ul{
        border: 1px solid rgb(102, 102, 102);
        height: 100px;
        border-top: none;
        line-height: 32px;
        padding: 5px;
    }
    .point-history ul li span{
        float: right;
    }
</style>
<div class="my-point">
    <div class="point-now">
        <p>사용가능 포인트</p>
        <h2>2100</h2>
    </div>
    <div class="point-history">
        <p>포인트 적립 및 사용 내역</p>
        <ul>
            
            <li>2021.03.05</li>
            <li>포인트 사용 <span>-1000</span></li>
            <li>마카롱 10구 세트 <span>잔여포인트 : 2100</span></li>
        </ul>
        <ul>
            <li>2021.03.05</li>
            <li>포인트 사용 <span>-1000</span></li>
            <li>마카롱 10구 세트 <span>잔여포인트 : 2100</span></li>
        </ul>
    </div>
</div>


</div>

<%@ include file="../_inc/footer.jsp" %>