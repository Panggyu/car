<%@page import="java.util.List"%>
<%@page import="net.board.db.BoardBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html> 
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <link href="/CarProject/css/default.css" rel="stylesheet" type="text/css">
<link href="/CarProject/css/subpage.css" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js" type="text/javascript"></script>
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/ie7-squish.js" type="text/javascript"></script>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if IE 6]>
 <script src="../script/DD_belatedPNG_0.0.8a.js"></script>
 <script>
   /* EXAMPLE */
   DD_belatedPNG.fix('#wrap');
   DD_belatedPNG.fix('#main_img');   

 </script>
 <![endif]-->
</head>
<%
//세션값 가져오기
String id=(String)session.getAttribute("id");
//세션값이 없으면  ./MemberLogin.me
if(id==null){
	response.sendRedirect("./MemberLogin.me");
}
// request.setAttribute("bb", bb);
// request.setAttribute("pageNum", pageNum);
BoardBean bb=(BoardBean)request.getAttribute("bb");
String pageNum=request.getParameter("pageNum");
%>
<body>

<center>
	<!-- 게시판 -->
	<article>
	<h1>Notice Update</h1>
	<form action="./BoardUpdateAction.bo?pageNum=<%=pageNum %>" method="post">
	<input type="hidden" name="num" value="<%=bb.getNum()%>">
	<table id="notice">
	<tr><td>이름</td>
	<td><input type="text" name="name" value="<%=id %>" readonly></td></tr>
	<tr><td>비밀번호</td><td><input type="text" name="pass"></td></tr>
	<tr><td>제목</td><td><input type="text" name="subject" value="<%=bb.getSubject()%>"></td></tr>
	<tr><td>내용</td>
	<td><textarea rows="13" cols="40" name="content"><%=bb.getContent() %></textarea></td></tr>
	</table>
	<div id="table_search">
	<input type="submit" value="글수정" class="btn">
	<input type="reset" value="다시작성" class="btn">
	<input type="button" value="목록보기" class="btn" 
	   onclick="location.href='./BoardList.bo?pageNum=<%=pageNum%>'">
	</div>
	</form>
	<div class="clear"></div>
	<div id="page_control">
	</div>
	</article>
	<!-- 게시판 -->
</center>
</body>
</html>