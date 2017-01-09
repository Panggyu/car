<%@page import="java.util.List"%> 
<%@page import="net.board.db.BoardBean"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
 <link href="/CarProject/css/default.css" rel="stylesheet" type="text/css">
<link href="/CarProject/css/subpage.css" rel="stylesheet" type="text/css">
</head>

<%
// request.setAttribute("bb", bb);
// request.setAttribute("pageNum", pageNum);
BoardBean bb=(BoardBean)request.getAttribute("bb");
String pageNum=(String)request.getAttribute("pageNum");
%>
<body>
<!-- 게시판(글내용보기)-->
	<center>
		<!-- 게시판 -->
		<article>
		<h1>Content</h1>
		<table id="notice">
			<tr>
				<td>글번호</td><td><%=bb.getNum() %></td>
				<td>조회수</td><td><%=bb.getReadcount() %></td>
			</tr>
			<tr>
				<td>작성자</td><td><%=bb.getName() %></td>
				<td>작성일</td><td><%=bb.getDate() %></td>
			</tr>
			<tr>
				<td>글제목</td><td colspan="3"><%=bb.getSubject() %></td>
			</tr>
			<tr>
				<td>글내용</td><td colspan="3"><%=bb.getContent() %></td>
			</tr>
		</table>
		<div id="table_search">
		<%
		String id=(String)session.getAttribute("id");
		if(id!=null){
			if(id.equals(bb.getName())){
		%>
		<input type="button" value="글수정" class="btn" 
		 onclick="location.href='./boardUpdate.bo?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>'">
		<input type="button" value="글삭제" class="btn"
		 onclick="location.href='./boardDelete.bo?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>'">		
		<%
			}
		%>
		<input type="button" value="답글쓰기"  class="btn"
		  onclick="location.href='./boardReWrite.bo?num=<%=bb.getNum()%>&re_ref=<%=bb.getRe_ref()%>&re_lev=<%=bb.getRe_lev()%>&re_seq=<%=bb.getRe_seq()%>'">
		<%
		}
		%>
		<input type="button" value="목록보기" class="btn" 
		   onclick="location.href='./BoardList.bo?pageNum=<%=pageNum%>'">
		</div>
		<div class="clear"></div>
		<div id="page_control">
		</div>
		</article>
	</center>
	<!-- 게시판(글내용보기) -->
</body>
</html>