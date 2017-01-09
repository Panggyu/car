package controller;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.CarDAO;
import db.CarListBean;

/*전체 차량보기 버튼을 클릭 했을때.. 클라이언트의 요청을 받는  서블릿 클래스 입니다.*/
@WebServlet("/CarListController.do")
public class CarListController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		requestpro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		requestpro(request, response);
	}

	// doGet,doPost 방식으로 데이터가 넘어오던 모두 requestpro메소드에서 처리
	private void requestpro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/* 전체차량 보기 검색 */
		// 데이터 베이스에 접근하기위한 Model객체 생성(CarDao)
		CarDAO cdao = new CarDAO();
		
		// 실제 데이터 베이스에 접근하여 자동차 정보를 모두 읽어서 백터에 저장
		Vector<CarListBean> v = cdao.getAllCarlist();

		// CarList.jsp화면에 보여질 뷰페이지로 이동시킬때.. request객체에 담아서 보내 줍니다.
		request.setAttribute("v", v); // request객체영역에 담기

		// CarList.jsp페이지로 이동하면서 request영역 전달
		RequestDispatcher dis = 
				request.getRequestDispatcher("CarMain.jsp?center=CarList.jsp");
		// 데이터를 넘겨 주시오
		dis.forward(request, response);

	}

}
