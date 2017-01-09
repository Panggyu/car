package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.CarConfirmBean;
import db.CarDAO;

/*CarReserveResult.jsp페이지에서 예약된 차량의 주문id, 예약된 차량의 이미지 이름을 전달받는 서블릿*/
@WebServlet("/CarConfirmUpdateController.do")
public class CarConfirmUpdateController extends HttpServlet {
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
		/*클라이언트의 요청값 전달받기*/
		//CarReserveResult.jsp페이지에서 렌트예약한 하나의 차량에 대하여.. 수정하기 버튼을 누르면..
		//전달해오는  carimg, orderid 값 받기
		String carimg = request.getParameter("carimg");
		int orderid = Integer.parseInt(request.getParameter("orderid"));
	
		/*데이터베이스 자바빈 작업*/
		CarDAO cdao = new CarDAO();
		//렌트예약한 id를 전달하여..하나의 주문정보를 얻어오는 메소드를 호출
		CarConfirmBean cbean = cdao.getOneOrder(orderid);	
		//차량이미지를 빈클래스에 추가
		cbean.setCarimg(carimg);
		
		/*request영역에 담기*/
		//차량 주문 정보 수정 페이지로 전달 해야함 
		//대여기간,대여일,차량수량, 보험적용,무선wifi,베이비시트, 비밀번호등을 저장하고 있는..
		//CarConfirmBean객체를 request영역에 담기 
		request.setAttribute("cbean", cbean);
		
		/*View로 전달*/
		//CarConfirmUpdate.jsp페이지(차량 주문 정보 수정 페이지)로 이동하면서 request영역 전달
		RequestDispatcher dis = 
				request.getRequestDispatcher("CarMain.jsp?center=CarConfirmUpdate.jsp");
		// 데이터를 넘겨 주시오
		dis.forward(request, response);		
		
	}

}
