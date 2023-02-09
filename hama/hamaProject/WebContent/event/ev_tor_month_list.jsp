<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<%
request.setCharacterEncoding("utf-8");
ArrayList<BbsFree> freeList 
= (ArrayList<BbsFree>)request.getAttribute("freeList");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");

int bsize = pageInfo.getBsize(), cpage = pageInfo.getCpage();
int psize= pageInfo.getPsize(), pcnt= pageInfo.getPcnt();
int spage = pageInfo.getSpage(), rcnt= pageInfo.getRcnt();
String schtype= pageInfo.getSchtype();
String keyword= pageInfo.getKeyword();

String schargs = "", args="";
//검색조건용 쿼리스트링과 그냥 쿼리 스트링
//검색조건용은 있을 수도 없을 수도 있음 
if(schtype != null && !schtype.equals("") && 
keyword != null && !keyword.equals("") ){
	//검색조건과 검색어가 있으면 검색 관련 쿼리스트링 완성
	schargs = "&schtype=" + schtype + "&keyword=" + keyword;
}
args = "&cpage=" + cpage +  schargs;

%>
<style>
#list tr{height : 30px; }
#list th,#list td{padding : 8px 3px;} 
#list th{border-bottom:double black 3px;}
#list td{border-bottom:dotted black 1px;}
</style>


<h2>자유게시판 목록</h2>
<table width="700" cellpadding="0" cellspacing="0" id="list">
<tr>
<th width="10%">번호</th><th width="*">제목</th>
<th width="15%">작성자</th><th width="15%">작성일</th>
<th width="10%">조회</th>
</tr>
<%
if(freeList.size()>0){ //게시글 목록이 있으면
	int num = rcnt - (psize*(cpage-1));
	
	
	for(int i=0; i<freeList.size(); i++){
		BbsFree bf = freeList.get(i); //담기
		String title = bf.getBf_title();
		if(title.length()>30) title = title.substring(0,27) + "...";
		
		title = "<a href='free_view?bfidx=" + bf.getBf_idx() + args + 
				"'>"+title+"</a>";
				//타이틀 제목 누르면 링크 걸려서 가야함 
		if(bf.getBf_reply()>0){
			title+= " [" +  bf.getBf_reply()+ "]";
		}
%>			
		<tr align="center">
			<td><%=num%></td>
			<td align="left">&nbsp;&nbsp;<%=title%></td>
			<td><%=bf.getBf_writer() %></td>
			<td><%=bf.getBf_date() %></td>
			<td><%=bf.getBf_read() %></td>
		</tr>
<%	
	num--; //num 목록 줄어들어야함 
	}
	
}else{ //게시글 목록이 없으면
	out.print("<tr><td colspan='5' align='center' >");
	out.print("검색결과가 없습니다. </td></tr> ");
}
%>
</table> 
<br />


<table width="700" cellpadding="5">
<tr>
<td width="600">
<%
if(rcnt>0){ //게시글이 있으면 - 페이징 영역을 보여줌 
	String lnk = "free_list?cpage=";
	pcnt = rcnt / psize;
	if(rcnt % psize>0) pcnt++; //전체 페이지 수(마지막 페이지 번호이기도 함)
	
	//앞부분 페이징
	if(cpage == 1){
		//1페이지가 0이면
		out.println("[처음]&nbsp;&nbsp;&nbsp;[이전]&nbsp;&nbsp;&nbsp;");
	}else{
		out.println("<a href='" + lnk + 1 + schargs + "'>[처음]</a>&nbsp;&nbsp;&nbsp;");
		out.println("<a href='" + lnk + (cpage-1) + schargs + "'>[이전]</a>&nbsp;&nbsp;");
	}
	
	
	//중간페이징  1.2..3..4..5...11..12..13..14...15..16...
	//페이징은 그냥 복사해서 쓰기 
	spage = (cpage-1)/bsize * bsize + 1; //현재 블록에서의 시작 페이지 번호 
	for(int i=1, j=spage; i<=bsize && j<= pcnt; i++, j++){
		//i :  블록에서 보여줄 페이지의 개수만큼 루프를 돌릴 조건으로 사용된느 변수
		//j : 실제 출력할 페이지 번호로 전체 페이지 개수(마지막 페이지) 를 넘지 않도록 사용해야함
		if(cpage == j){ //현재페이지면 강조만
			out.println("&nbsp;<strong>" + j + "</strong>&nbsp;" );
		}else{ //다른페이징이면 링크 걸려서 
			out.print("&nbsp;<a href='" + lnk + j + schargs +"'>" );
			out.println(j + "</a>&nbsp;" );
		}	
	}
	
	
	
	
	//뒷부분페이징
	if(cpage == pcnt){
		out.println("&nbsp;&nbsp;[다음]&nbsp;&nbsp;&nbsp;[마지막]");
	}else{
		out.println("&nbsp;&nbsp;<a href='" + lnk + (cpage+1) + schargs + "'>[다음]</a>");
		out.println("&nbsp;&nbsp;&nbsp;<a href='" + lnk + pcnt + schargs + "'>[마지막]</a>");
	}

}
%>
</td>
<td width="*">
	<input type="button" value="글등록" />
</td>
</tr>


<tr>
<td colspan="2">
	<form name="frmSch" method="get">
	<fieldset>
		<legend>게시판검색</legend>
		<select name="schtype"> 
			<option value="">검색조건</option>
			<option value="title"  
			<%if(schtype.equals("title")){%>selected="selected"<%} %>>제목</option>
			<option value="content"  
			<%if(schtype.equals("content")){%>selected="selected"<%} %>>내용</option>
			<option value="tc"  
			<%if(schtype.equals("tc")){%>selected="selected"<%} %>>제목+내용</option>
		
		</select>
		<input type="text" name="keyword" value="<%=keyword %>" />
		<input type="submit"  value="검색" />
		<input type="button"  value="전체글" onclick="location.href='free_list';" />
	</fieldset>
	
	</form>
</td>
</tr>





</table> 










</body>
</html>














