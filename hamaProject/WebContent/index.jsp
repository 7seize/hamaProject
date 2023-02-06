<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     
<%@ include file="_inc/header.jsp" %>

<link rel="stylesheet" href="_inc/css/common.css">
<link rel="stylesheet" href="_inc/css/header.css">
    
<style>

main{
    margin: 2% auto;
}

main > video{
    width: 100%;
    display: block; 
    margin: 20px auto;
}
</style>

<main>
    <video autoplay muted loop>
        <source src="./img/hamaron_macaron.mp4" type="video/mp4">
    </video>
</main>

<%@ include file="_inc/footer.jsp" %>