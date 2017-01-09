package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//로그아웃 요청이 들어왔을때... 
//세션값 초기화후~ 로그아웃 메세지창을 띄어주고...
//CarList.jsp페이지로 이동하는일을 하는 Action클래스 
public class MemberLogoutAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberLogoutAction execute()");
		//세션값 초기화
		HttpSession session=request.getSession();
		session.invalidate();
		
		//이동 ./Main.me
//		ActionForward forward=new ActionForward();
//		forward.setRedirect(true);
//		forward.setPath("./Main.bo");
//		return forward;
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println("<script>");
		out.println("alert('로그아웃');");
		out.println("location.href='./Main.me';");
		out.println("</script>");
		out.close();
		return null;
	}
}
