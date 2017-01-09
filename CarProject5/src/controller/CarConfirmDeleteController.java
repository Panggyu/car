package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.CarDAO;

/*CarConfrimDelete.jsp화면에서 삭제비밀번호를 입력후..... 
CarConfrimDeleteController로 렌트예약 삭제 작업을 요청 함.*/
@WebServlet("/CarConfirmDeleteController.do")
public class CarConfirmDeleteController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		requestpro(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		requestpro(request,response);
	}
	//doGet,doPost 방식으로 데이터가 넘어오던 모두 requestpro메소드에서 처리
	private void requestpro(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException  {
	
		//사용자로 부터 입력
		int orderid= Integer.parseInt(request.getParameter("orderid"));
		String memberpass = request.getParameter("memberpass");
		
		//데이터 베이스 객체를 생성
		CarDAO cdao = new CarDAO();
		int result = cdao.carOrderDelete(orderid,memberpass);
		
		if(result!=0){//1. 쿼리가 제대로 실행되었다면
			 
			 response.setContentType("text/html;charset=utf-8"); 
             PrintWriter out = response.getWriter(); 
             out.println("<script>"); 
             out.println("alert('렌트카 예약정보를 삭제 하였습니다.');"); 
             out.println("location.href='CarListController.do'"); 
             out.println("</script>"); 
//
//			RequestDispatcher dis = 
//				request.getRequestDispatcher("CarListController.do");
//			dis.forward(request, response);
		}else{//쿼리가 제대로 실행되지 않았다면 = password가 틀림
			
			request.setAttribute("result", result);//result값은 0입니다.
			//실제이동
			RequestDispatcher dis = 
					request.getRequestDispatcher("CarMain.jsp?center=CarConfirmDelete.jsp");
				dis.forward(request, response);
			
		}
			
	}
}