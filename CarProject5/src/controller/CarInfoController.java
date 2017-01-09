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


/*하나의 차량 이미지를 클릭 했을때.. 클라이언트의 요청을 받는  서블릿 클래스 입니다.*/
@WebServlet("/CarInfoController.do")
public class CarInfoController extends HttpServlet {
	
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
		//CarList.jsp에서 사용자가 선택한 자동차 번호 전달 받기
		int carno = Integer.parseInt(request.getParameter("carno"));
		
		//데이터베이스에 연결하여 하나의 자동차 정보를 읽어 드림
		CarDAO cdao = new CarDAO();
		
		//실제 데이터 베이스에 접근하여 하나의 자동차 정보를 모두 읽어서.... 
		//하나의 컬럼정보를 저장해주는 빈객체 리턴
		//단 호출시! 자동차번호를 메소드의 매개변수로 전달 ! 
		CarListBean bean = cdao.getOneCar(carno);
		
		//리퀘스트 객체를 이용하여 데이터를 저장
		request.setAttribute("bean", bean);
		
		//CarMain.jsp페이지로 이동하면서 request영역 전달
		RequestDispatcher dis =
				request.getRequestDispatcher("CarMain.jsp?center=CarInfo.jsp");
		//전환
		dis.forward(request, response);
				
	}

}

