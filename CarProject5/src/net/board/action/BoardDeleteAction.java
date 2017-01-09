package net.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO;

public class BoardDeleteAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardDeleteAction  execute()");
		// int num   String pageNum   String pass 파라미터 가져오기
		String pageNum=request.getParameter("pageNum");
		int num=Integer.parseInt(request.getParameter("num"));
		String pass=request.getParameter("pass");
		// 디비객체 생성 BoardDAO  bdao
		BoardDAO bdao=new BoardDAO();
		// int check =  메서드호출 deleteBoard(num,pass)
		int check=bdao.deleteBoard(num, pass);
		// check==0 "비밀번호틀림"  뒤로이동
		// check==-1  "num없음" 뒤로이동
		//  1    "삭제성공"  이동 ./BoardList.bo?pageNum=
		if(check==0){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호틀림');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}else if(check==-1){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('num없음');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println("<script>");
		out.println("alert('삭제성공');");
		out.println("location.href='./BoardList.bo?pageNum="+pageNum+"';");
		out.println("</script>");
		out.close();
		return null;
	}
}
