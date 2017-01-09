package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardWriteAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardWriteAction execute()");
		//한글처리
		request.setCharacterEncoding("utf-8");
		//자바빈  net.board.db.BoardBean 
		// 객체 생성 BoardBean bb
		BoardBean bb=new BoardBean();
		// bb.set  멤버변수 <= 파라미터 저장 name  pass  subject content
		bb.setName(request.getParameter("name"));
		bb.setPass(request.getParameter("pass"));
		bb.setSubject(request.getParameter("subject"));
		bb.setContent(request.getParameter("content"));
		bb.setIp(request.getRemoteAddr());
		//디비  net.board.db.BoardDAO 
		// 객체 생성 BoardDAO bdao
		BoardDAO bdao= new BoardDAO();
		//  insertBoard(bb)메서드 호출
		bdao.insertBoard(bb);
		// 이동  ./BoardList.bo
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./BoardList.bo");
		return forward;
	}
}
