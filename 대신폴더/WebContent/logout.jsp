<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
session.invalidate(); //로그아웃 세션 날려버리기
response.sendRedirect("index.jsp"); //로그아웃한 뒤 인덱스 파일로 리다이렉트
%>