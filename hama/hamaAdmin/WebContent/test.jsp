<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="ai.openai.gpt.*"%>
<%@page import="java.util.*"%>

<%
    // 1. 사용자로부터 입력받은 텍스트 가져오기
    String inputText = request.getParameter("inputText");
    
    // 2. ChatGPT 모델을 사용하여 오타 수정하기
    OpenAI api = new OpenAI("<YOUR_API_KEY>"); // YOUR_API_KEY를 자신의 OpenAI API 키로 대체하세요.
    String prompt = inputText;
    String model = "text-davinci-002"; // 사용할 ChatGPT 모델 선택
    String completions = api.complete(prompt, model, 1, null, null, null, null, null, null, null, null, null, null, null, null, null, null).getChoices().get(0).getText();
    String correctedText = completions.substring(prompt.length()); // prompt 부분을 제외한 나머지가 수정된 텍스트입니다.

    // 3. 수정된 텍스트를 사용자에게 보여주기
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>오타 수정 결과</title>
</head>
<body>
	<h1>오타 수정 결과</h1>
	<p>입력한 텍스트: <%= inputText %></p>
	<p>수정된 텍스트: <%= correctedText %></p>
</body>
</html>