<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<center>
	<h1> 차량 주문 정보 삭제 </h1>
	
	<form action="CarConfirmDeleteController.do" method="post">
		<p>
		<table width="400" border="0">
			
			<c:set var="result" value="${requestScope.result}"  />
			
			<c:if test="${result==null }">
				<c:set var="result" value="${1 }"  />
			</c:if>	
			
			<c:if test="${result==0 }">
				<script>
					alert("비밀번호가 틀립니다.");		
				</script>
			</c:if>
		
		<%--먼저 작성 --%>
			<tr align="center">
				<td align="center"> 비밀번호 입력 : 
				<input type="hidden" value="${param.orderid }" name="orderid">
				<input type="password" name="memberpass"> &nbsp;&nbsp;&nbsp;
				<input type="submit" value="삭제 하기"></td>
			</tr>		
		</table>	
	</form>	
</center>	
	
</body>
</html>