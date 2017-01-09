package net.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;import com.sun.jndi.url.corbaname.corbanameURLContextFactory;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class MainAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MainAction execute()");
		// 디비 객체 생성 BoardDAO  bdao
		BoardDAO bdao=new BoardDAO();
		// int count =  메서드호출  getBoardCount()   count(*)
		int count=bdao.getBoardCount();
		String pageNum="1";
		List<BoardBean> boardList=null;
		//  시작행번호 1번     5개 가져오기
	    // boardList=메서드호출  getBoardList(시작행startRow,몇개pageSize)
		if(count!=0){
			boardList=bdao.getBoardList(1, 5);
		}
		// 저장 count   boardList   pageNum
		request.setAttribute("count", count);
		request.setAttribute("boardList", boardList);
		request.setAttribute("pageNum", pageNum);
		// 이동 ./main/main.jsp
		ActionForward forward=new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./main/main.jsp");
		return forward;
	}
}
