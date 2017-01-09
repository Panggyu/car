<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<center>
	<%-- <자유게시판> 이미지 --%>
	<img alt="" src="img/jayu.jpg" border="0">
	<p>
	<c:if test="${count > 0 }">
		<table width="1000" bgcolor="#88ffff" border="1">
			<tr height="60">
				<td align="right" colspan="5">
					<a href="CarMain.jsp?center=BoardInputForm.jsp">
						<img alt="" src="img/geulwrite.jpg" width="100" height="50" border="0">
					</a>
				</td>
			</tr>
			<tr height="40">
				<th width="100"> 번호 </th>
				<th width="300"> 제목 </th>
				<th width="100"> 작성자 </th>
				<th width="150"> 작성일 </th>
				<th width="100"> 조회수 </th>
			</tr>	
			<c:forEach var="v" items="${v }">
				<tr height="40">
					<td align="center">${number}</td> 			
					<c:set var="number" value="${number-1 }" />
					<td align="left">
					<c:forEach var="j" begin="1" end="${v.re_step }"  >
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</c:forEach>
					<a href="BoardInfoController.do?num=${v.num}">${v.subject } </a></td>
					<td align="center">${v.writer }</td> 
					<td align="center">${v.reg_date }</td> 
					<td align="center">${v.readcount }</td>
				</tr>	 
			</c:forEach>	
		</table>
	</c:if>
	
	<!-- 게시글이 없을 경우   -->
	<c:if test="${count <= 0 }">
	<table width="1000" bgcolor="#88ffff" border="1">
	<tr height="60">
		<td align="right" colspan="5">
		<a href="CarMain.jsp?center=BoardInputForm.jsp">
		<img alt="" src="img/geulwrite.jpg" width="100" height="50" border="0">
		</a></td>
	</tr>
	<tr height="60">
		<td align="center"> 게시글이 없습니다.</td>
	</tr>	
	</table>
	</c:if>
	<p>
	<!-- 페이지 카운터링  -->
	<c:if test="${count >0 }" >
	<c:set var="pageCount" 
		value="${count/pageSize+(count%pageSize == 0 ? 0 :1)}"/>
	<!--시작 (카운터링) 페이지의 숫자를 설정  -->
	<c:set var="startPage" value="${1 }" />
	<c:if test="${currentPage % 10 != 0 }">
	<!--결과를 정수형으로 리턴을 받야야 하기에 fmt태그를 사옹  -->
	<fmt:parseNumber var="result" value="${currentPage/10 }" integerOnly="true" />
	<c:set var="startPage" value="${result*10+1 }" />	
	</c:if>
	<c:if test="${currentPage % 10 == 0 }">
	<!--결과를 정수형으로 리턴을 받야야 하기에 fmt태그를 사옹  -->
	<c:set var="startPage" value="${(result-1)*10+1 }" />	
	</c:if>
	<!-- 화면에 보여질 페이지 처리시작하기위한 변수 선언 -->
	<c:set var="pageBlock" value="${10 }" />
	<c:set var="endPage" value="${startPage+pageBlock-1 }" />
	<c:if test="${endPage > pageCount }">
	<c:set var="endPage" value="${pageCount }" />
	</c:if>
	
	<!-- 이전이라는 링크를 걸어줄지 파악 -->
	<c:if test="${startPage > 10 }">
	<a href="BoardListController.do?pageNum=${startPage-10 }"> [이전] </a>	
	</c:if>
	
	<!-- 페이징 처리 [1] [2] [3] .. -->
	<c:forEach var="i" begin="${startPage }" end="${endPage }">
	<a href="BoardListController.do?pageNum=${i}"> [${i}]</a>	
	</c:forEach>
	
	<!-- 다음이라는 링크를 걸어줄지 파악 -->
	<c:if test="${endPage < pageCount }">
	<a href="BoardListController.do?pageNum=${startPage+10 }"> [다음] </a>	
	</c:if>
	</c:if>	
</center>

</body>
</html>