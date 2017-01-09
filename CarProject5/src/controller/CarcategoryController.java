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


/**
 * 카테고리별 차 검색
 */
@WebServlet("/CarcategoryController.do")
public class CarcategoryController extends HttpServlet {
	
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
		
		//CarList.jsp 또는 CarReservation에서 사용자가 선택한 카테고리값을 먼저 읽어드림
		String carcategory = request.getParameter("carcategory");
		
		//데이터 베이스 작업 객체 생성
		CarDAO cdao = new CarDAO();
		//카테고리별 차검색 메소드를 호출하여  검색한 레코드를 담고 있는 백터객체 리턴 받기.
		//단 호출시! 카테고리값 메소드의 매개변수로 전달 ! 
		Vector<CarListBean> v = cdao.getCategoryCarList(carcategory);
		
		//CarMain.jsp로 데이터를 넘기기위해서 request객체에 저장
		request.setAttribute("v", v);
		
		// CarList.jsp페이지로 이동하면서 request영역 전달
		RequestDispatcher dis = 
				request.getRequestDispatcher("CarMain.jsp?center=CarList.jsp");
		// 데이터를 넘겨 주시오
		dis.forward(request, response);				
	}

}