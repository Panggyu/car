package net.member.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberFrontController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doProcess(request, response);
	}

	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		//가상요청 주소 가져오기
		//  /CarProject/MemberJoin.me
		//  /CarProject/MemberJoinAction.me
		//   /CarProject/MemberLogin.me 
		String RequestURI=request.getRequestURI();
		System.out.println(RequestURI);
		
		//  /CarProject 얻기
		//  /CarProject 얻기
		//  /CarProject 얻기
		String contextPath=request.getContextPath();
		
		//	길이 11
		//	길이 11
		//	길이 11
		System.out.println(contextPath.length());
		
		//  /MemberJoin.me 얻기
		// /MemberJoinAction.me
		//  /MemberLogin.me 얻기 
		String command=RequestURI.substring(contextPath.length());
		System.out.println(command);
					
		/*주소 비교*/	
		//페이지 이동 방식 여부 값,이동페이지 경로 값 저장 하여 리턴 해주는 객체를 저장할 참조변수 선언 
		ActionForward forward=null;
	
		//자식 Action 객체들을 담을 인터페이스 타입의 참조변수 선언
		Action action=null;
			
		//Top.jsp에서.. join링크를 누르면 회원가입페이지로 이동하는 요청이 들어 왔을때..
		if(command.equals("/MemberJoin.me")){
		
			//페이지 이동 방식 여부 값,이동페이지 경로 값 저장 하여 리턴 해주는 객체 생성 
			forward=new ActionForward();
			//페이지 이동 방식 여부 값 false로 저장-> RequestDispatcher  forward() 방식
			forward.setRedirect(false);
			//이동할 페이지 경로(회원가입 페이지) 주소값 저장
			forward.setPath("./CarMain.jsp?center=member/join.jsp");
			
		//join.jsp에서...회원가입 처리요청이 들어 왔을떄...	
		}else if(command.equals("/MemberJoinAction.me")){
	
			//회원가입 처리를 위한 Action객체 생성
			action=new MemberJoinAction();
			
			try {
				//josin.jsp에서 입력한 회원가입 내용을 담고 있는
				//request영역을 execute메소드의 매개변수로 전달하여..
				//회원가입 DB 작업후 회원가입에 성공하면..
				// 페이지 이동 방식 여부 값 true와...
				// 이동할페이지 주소 (./member/login.jsp)를 담고 있는..
				//new ActionForward()객체를 리턴 받는다.
				forward=action.execute(request, response);
						
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		//top.jsp에서 ..login링크를 클릭하여 로그인화면으로 이동하라 라는 요청이 들어 왔을때...	
		//또는 회원가입후!.. 로그인화면으로 이동하라 라는 요청이 들어 왔을떄...
		}else if(command.equals("/MemberLogin.me")){ 
			//페이지 이동 방식 여부 값,이동페이지 경로 값 저장 하여 리턴 해주는 객체 생성 
			forward=new ActionForward();
			//페이지 이동 방식 여부 값 false로 저장-> RequestDispatcher  forward() 방식
			forward.setRedirect(false); //주소값 노출 안됨
			//이동할 페이지 경로(로그인 페이지) 주소값 저장
			forward.setPath("./CarMain.jsp?center=member/login.jsp"); 
		
		//login.jsp에서... "Sign in"버튼을 눌렀을때..로그인 처리 요청받기!
		//사용자가 입력한 id와 패스워드를 request영역에 담아오기
		}else if(command.equals("/MemberLoginAction.me")){
			
			//로그인 처리를 위한 Action객체 생성 
			action=new MemberLoginAction();
			
			try {
				//login.jsp에서 사용자가 입력한 id와 패스워드를 담고 있는 request영역을
				//execute메소드의 매개변수로 전달하여.. DB에있는 id와 패스워드 값을 비교한다.
				//DB에 있는 아이디,비밀번호와...
				//login.jsp 화면에서 입력한 아이디,비밀번호가 일치할때...
				//login.jsp 화면에서 입력한 아이디를 세션객체영역에 저장하고...
				//페이지 이동 방식 여부 값 true와...이동할페이지 주소 (./CarMain.jsp)를 담고 있는..
				//new ActionForward()객체를 MemberFrontController로 리턴 
				forward=action.execute(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		//top.jsp에서 ..logout링크를 클릭하여 세션값초기화하고...
		//CarMain.jsp화면으로 이동하라 라는 요청이 들어 왔을때...				
		}else if(command.equals("/MemberLogout.me")){
			
			//로그아웃 처리를 위한 Action객체 생성  
			action=new MemberLogoutAction();
			try {
				//top.jsp에서  로그아웃 요청이 들어왔을때...  
				//세션값 초기화후~ 로그아웃 메세지창을 띄어주고...
				//CarList.jsp페이지로 이동하는일을 하는 execute()메소드 호출함.
				forward=action.execute(request, response); //return null;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}	//"CarMain.jsp 메인 페이지 요청"이 들어 왔을떄...
		else if(command.equals("/Main.me")){
			//페이지 이동 방식 여부 값,이동페이지 경로 값 저장 하여 리턴 해주는 객체 생성
			forward=new ActionForward();
			forward.setRedirect(false); //주소값 노출 x
			forward.setPath("./CarMain.jsp"); //이동할 페이지 저장
		}
		
		//주소 이동
		if(forward!=null){ //new ActionForward()객체가 존재 하고..
			if(forward.isRedirect()){//true -> sendRedirect() 방식일떄..
				//리다이렉트 방식으로 페이지 이동!  페이지 주소 경로 노출 함 
				//join.jsp화면 이동
				//login.jsp화면 이동
				//CarMain.jsp화면 이동시 session영역 전달
				response.sendRedirect(forward.getPath());
				
			}else{//false -> forward() 방식일때...
				
				RequestDispatcher dispatcher=request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}//if 
		
	}//	doProcess 메소드 끝
	
}//서블릿 끝



