package net.board.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BoardFrontController extends HttpServlet{
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//가상주소 가져오기
		//  http://localhost:8080/FunWeb2/BoardList.bo
		//   /FunWeb2/BoardList.bo
		String RequestURI=request.getRequestURI();
		System.out.println(RequestURI);
		//   /FunWeb2    8
		String contextPath=request.getContextPath();
		System.out.println(contextPath);
		System.out.println(contextPath.length());
		//  /BoardList.bo
		String command=RequestURI.substring(contextPath.length());
		System.out.println(command);
		//주소비교
		ActionForward forward=null;
		Action action=null;
		if(command.equals("/BoardList.bo")){
			//  BoardListAction 
			action=new BoardListAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardListSearch.bo")){
			// BoardListSearchAction
			action=new BoardListSearchAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardWrite.bo")){
			//   ./center/write.jsp  이동
			forward=new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./CarMain.jsp?center=board/write.jsp");
			
		}else if(command.equals("/BoardWriteAction.bo")){//글쓰기 요청
			action=new BoardWriteAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardContent.bo")){
			// BoardContentAction   execute()
			action=new BoardContentAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/boardUpdate.bo")){
			//  boardUpdate   
			action=new boardUpdate();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardUpdateAction.bo")){
			//  BoardUpdateAction
			action=new BoardUpdateAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/boardDelete.bo")){
			forward=new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./CarMain.jsp?center=board/delete.jsp");
		}else if(command.equals("/BoardDeleteAction.bo")){
			action=new BoardDeleteAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/boardReWrite.bo")){
			//  ./center/reWrite.jsp
			forward=new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./CarMain.jsp?center=board/reWrite.jsp");
			
			
			
			
		}else if(command.equals("/BoardReWriteAction.bo")){
			//  BoardReWriteAction
			action=new BoardReWriteAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/Main.bo")){
			// MainAction 
			action=new MainAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//이동
		if(forward!=null){
			if(forward.isRedirect()){//true
				response.sendRedirect(forward.getPath());
			}else{//false
				RequestDispatcher dispatcher=request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
