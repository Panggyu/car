package net.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardUpdateAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardUpdateAction execute()");
		//한글처리
		request.setCharacterEncoding("utf-8");
		//  pageNum 파라미터    
		String pageNum=request.getParameter("pageNum");
		// BoardBean bb 객체 생성
		BoardBean bb=new BoardBean();
		// bb 멤버변수 <- num name pass subject  content 파라미터
		bb.setContent(request.getParameter("content"));
		bb.setName(request.getParameter("name"));
		bb.setPass(request.getParameter("pass"));
		bb.setSubject(request.getParameter("subject"));
		bb.setNum(Integer.parseInt(request.getParameter("num")));
		// BoardDAO bdao 객체 생성
		BoardDAO bdao=new BoardDAO();
		// int check  =메서드호출  updateBoard(bb)
		int check=bdao.updateBoard(bb);
		// check==0  "비밀번호틀림"  뒤로이동
		// check==-1 "num없음" 뒤로이동
		//        1    "수정성공"  이동 ./BoardList.bo?pageNum=
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
		out.println("alert('수정성공');");
		out.println("location.href='./BoardList.bo?pageNum="+pageNum+"';");
		out.println("</script>");
		out.close();
		return null;
	}
}




