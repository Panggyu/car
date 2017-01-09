package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.CarDAO;
import db.CarOrderBean;

/*CarOrder.jsp페이지에서 전달한 요청정보를 받는 서블릿 클래스 */
@WebServlet("/CarOrderController.do")
public class CarOrderController extends HttpServlet {
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
		
		//CarOrder.jsp페이지에서 넘어온 데이터를 자바빈클래스에 저장
		CarOrderBean cbean = new CarOrderBean();
		cbean.setCarno(Integer.parseInt(request.getParameter("carno")));
		cbean.setCarqty(Integer.parseInt(request.getParameter("carqty")));
		cbean.setCarreserveday(Integer.parseInt(request.getParameter("carreserveday")));
		cbean.setCarbegindate(request.getParameter("carbegindate"));
		cbean.setCarins(Integer.parseInt(request.getParameter("carins")));
		cbean.setCarwifi(Integer.parseInt(request.getParameter("carwifi")));
		cbean.setCarnave(Integer.parseInt(request.getParameter("carnave")));
		cbean.setCarbabyseat(Integer.parseInt(request.getParameter("carbabyseat")));
		cbean.setMemberphone(request.getParameter("memberphone"));
		cbean.setMemberpass(request.getParameter("memberpass"));
		
		
		//데이터베이스 객체를 생성
		CarDAO cdao = new CarDAO();
		/*먼저 할일!!*/
		//CarDAo클래스에 ...주문 현황을 저장하는 메소드를 만들자. 
		//메소드 이름 : insertCarOrder(CarOrderBean bean)
		
		
		//렌트(대여)주문 현황을 DB에 저장하는 메소드 호출시...  
		//자바빈객체 전달하여 insert하기 <-- DB작업
		cdao.insertCarOrder(cbean);
				
		// CarList.jsp페이지로 이동하기위해....
		// CarListController서블릿으로 이동시.. request영역 전달
		RequestDispatcher dis = 
				request.getRequestDispatcher("CarListController.do");
		dis.forward(request, response);
		
	}

}
