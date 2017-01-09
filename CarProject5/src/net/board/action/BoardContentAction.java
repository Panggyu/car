package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardContentAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardContentAction execute()");
		// int num  파라미터 가져오기
		int num=Integer.parseInt(request.getParameter("num"));
		// String pageNum 파라미터 가져오기
		String pageNum=request.getParameter("pageNum");
		
		// BoardDAO bdao 객체생성
		BoardDAO bdao=new BoardDAO();
		// 조회수 증가 updateReadcount(num)
		bdao.updateReadcount(num);
		// BoardBean bb=메서드호출 getBoard(num)
		BoardBean bb=bdao.getBoard(num);
		
		//저장 "bb",bb
		request.setAttribute("bb", bb);
		//저장 "pageNum",pageNum
		request.setAttribute("pageNum", pageNum);
		// 이동 ./board/content.jsp
		ActionForward forward=new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./CarMain.jsp?center=board/content.jsp");
		return forward;
	}
}
