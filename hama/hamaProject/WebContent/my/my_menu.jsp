<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<style>
    .mypage{
        width: 60%;
        margin: 0 auto;
        display: flex;
        justify-content: flex-start;
    }
    .my-menu{
    	margin-right: 40px;
    }
    h1{
        font-size: 2rem;
        background-color: bisque;
        width: 150px;
        height: 100px;
        line-height: 100px;
        text-align: center;
        border: 1px solid rgb(218, 218, 218);
    }
    nav > div{
        width: 150px;
        border: 1px solid rgb(218, 218, 218);
        line-height: 30px;
    }
    nav > div > p{
        font-weight: bold;
        font-size: 17px;
        margin: 10px;
    }
    nav > div > ul{
        font-size: 13px;
        margin: 10px;
    }
</style>
<div class="mypage">
    <div class="my-menu">
        <h1>
            MY
        </h1>
        <nav>
            <div>
                <p>MY 쇼핑</p>
                <ul>
                    <li><a href="my_buy_list.jsp">구매내역</a></li>
                    <li><a href="my_refund_list.jsp">환불내역</a></li>
                    <li><a href="my_point_view.jsp">포인트내역</a></li>
                </ul>
            </div>
            <div>
                <p>MY 활동</p>
                <ul>
                    <li><a href="my_custom_list.jsp">커스텀 마카롱 관리</a></li>
                    <li><a href="my_review_list.jsp">리뷰 관리</a></li>
                    <li><a href="my_qna_list.jsp">1대1 문의</a></li>
                </ul>
            </div>
            <div>
                <p>MY 정보</p>
                <ul>
                    <li><a href="my_info_view.jsp">개인정보 수정</a></li>
                    <li><a href="my_place_view.jsp">배송지 관리</a></li>
                    <li><a href="my_leave_view.jsp">회원 탈퇴</a></li>
                </ul>
            </div>
        </nav>
    </div>


