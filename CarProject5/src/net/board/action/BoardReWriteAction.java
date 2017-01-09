package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardReWriteAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardReWriteAction execute()");
		//한글처리
		request.setCharacterEncoding("utf-8");
		// BoardBean bb 객체 생성
		BoardBean bb=new BoardBean();
		// bb 멤버변수  reWrite.jsp 파라미터  값 저장
		// ip 주소 저장
		bb.setNum(Integer.parseInt(request.getParameter("num")));
		bb.setRe_ref(Integer.parseInt(request.getParameter("re_ref")));
		bb.setRe_lev(Integer.parseInt(request.getParameter("re_lev")));
		bb.setRe_seq(Integer.parseInt(request.getParameter("re_seq")));
		bb.setName(request.getParameter("name"));
		bb.setPass(request.getParameter("pass"));
		bb.setSubject(request.getParameter("subject"));
		bb.setContent(request.getParameter("content"));
		bb.setIp(request.getRemoteAddr());
		// BoardDAO  bdao
		BoardDAO bdao= new BoardDAO();
		// 메서드호출  reInsertBoard(bb)
		bdao.reInsertBoard(bb);
		// 이동  ./BoardList.bo
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./BoardList.bo");
		return forward;
	}
}
