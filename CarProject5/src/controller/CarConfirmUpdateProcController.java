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

/*차량 주문 수정 페이지 CarConfirmUpdate.jsp에서...
 * 차량예약주문을 다시 수정한 내용을 전달받아 수정 처리 하는 서블릿*/
@WebServlet("/CarConfirmUpdateProcController.do")
public class CarConfirmUpdateProcController extends HttpServlet {
	
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
		//차량 주문 수정 페이지 CarConfirmUpdate.jsp에서 사용자로부터 넘어온 데이터를 입력
		int orderid = Integer.parseInt(request.getParameter("orderid"));
		int carreserveday = Integer.parseInt(request.getParameter("carreserveday"));
		int carqty = Integer.parseInt(request.getParameter("carqty"));
		int carins = Integer.parseInt(request.getParameter("carins"));
		int carwifi = Integer.parseInt(request.getParameter("carwifi"));
		int carbabyseat = Integer.parseInt(request.getParameter("carbabyseat"));
		String carbegindate = request.getParameter("carbegindate");
		String memberpass = request.getParameter("memberpass");
		
		//carorderbean객체를  이용하여 데이터를 저장한후 이 빈객체를 DAO로 넘겨줌
		CarOrderBean bean = new CarOrderBean();
		bean.setOrderid(orderid);
		bean.setCarreserveday(carreserveday);
		bean.setCarbabyseat(carbabyseat);
		bean.setCarbegindate(carbegindate);
		bean.setCarqty(carqty);
		bean.setCarins(carins);
		bean.setCarwifi(carwifi);
		bean.setMemberpass(memberpass);
		
		CarDAO cdao = new CarDAO();//데이터 베이스 객체 생성
		cdao.carOrderUpdate(bean);//수정메소드 호출시 빈객체 전달하여 수정!
		
		//수정에 성공하면...다시 CarList.jsp 화면으로 이동하자
		RequestDispatcher dis =
			request.getRequestDispatcher("CarListController.do");
		dis.forward(request, response);
		
	
	}
}
