package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.CarOrderBean;

/*CarOption.jsp(옵션선택)페이지에서  [예약하기]버튼을 클릭 했을때.. 클라이언트의 요청을 받는  서블릿 클래스 입니다.*/
@WebServlet("/CarOptionController.do")
public class CarOptionController extends HttpServlet {
	
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
	    //CarOption.jsp에서 전달한 금액 연산을 위하여 데이터를 일일이 받아줌
		int carqty = Integer.parseInt(request.getParameter("carqty"));//대여수량 받기
		int carprice = Integer.parseInt(request.getParameter("carprice"));//대여금액 받기
		int carreserveday = Integer.parseInt(request.getParameter("carreserveday"));//대여날짜 받기
		
		//보험비 적용여부 받기= 보험적용 1일이면 : 값1 받기
		//보험비 적용여부 받기= 보험미적용 이면  : 값0 받기		
		int carins = Integer.parseInt(request.getParameter("carins"));
		
		//무선wifi 적용여부 받기 =  적용 1일이면 : 값1 받기
		//무선wifi 적용여부 받기 =  미보험미적용 이면 : 값0 받기
		int carwifi = Integer.parseInt(request.getParameter("carwifi"));

		//네비게이션 적용여부 받기 =  적용(무료)이면 : 값1 받기
		//네비게이션 적용여부 받기 =  비적용 이면 : 값0 받기  
		int carnave = Integer.parseInt(request.getParameter("carnave"));       
		
		//베이비시트 적용여부 받기 =  적용 1일이면 : 값1 받기
		//베이비시트 적용여부 받기 =  미보험미적용 이면 : 값0 받기
		int carbabyseat = Integer.parseInt(request.getParameter("carbabyseat"));
		
		
	
		//참고!! 연산후에 결과를  CarOrder.jsp로  데이터를 넘겨줌
		//차량가액 = 수량 * 대여기간 * 차량가격
		int totalreserve = carqty * carreserveday * carprice;
		//옵션금액 = 각종옵션에 대여기간과 수량을 곱해서 리턴
		int totaloption = 
((carins * carreserveday) + (carwifi * carreserveday) + (carbabyseat*carreserveday)) * 10000 * carqty;
		
		//CarOrder.jsp쪽으로  선택했던 데이터들을 모두 넘겨주어야함.
		//그러기에 앞서.... 위의 CarOption.jsp에서 전달한 데이터들을 담을 자바빈클래스(CarOderBean.java)를 만들자.
		//----------> db패키지에  CarOrderBean자바빈 클래스 만들기 
				
		//자바빈클래스 CarOderBean 객체 생성 하여 CarOption.jsp에서 전달한 정보들 저장하기
		CarOrderBean cbean = new CarOrderBean();
		cbean.setCarno(Integer.parseInt(request.getParameter("carno")));//렌트할 차량 번호 받아와서 자바빈에 저장
		cbean.setCarqty(carqty);//렌트할 차량 수량 (대여수량) 받아와서 자바빈에 저장
		cbean.setCarreserveday(carreserveday);//대여기간 받아와서 자바빈에 저장
		cbean.setCarins(carins);//보험비 적용여부값 받아와서 자바빈에 저장
		cbean.setCarnave(carnave);// 네비게이션 적용여부값 받아와서 자바빈에 저장
		cbean.setCarbabyseat(carbabyseat);///베이비시트 적용여부값 받아와서 자바빈에 저장
		cbean.setCarbegindate(request.getParameter("carbegindate"));//자동차 대여한 날짜를 받아와서 자바빈에 저장
		
		//CarOrder.jsp로 데이터를 넘기기 위해 request영역에 저장하기 (탁자위에 올려 놓기)
		request.setAttribute("cbean", cbean);//자바빈객체 저장
		request.setAttribute("totalreserve", totalreserve);//렌트(대여)차량 가액금액 저장
		request.setAttribute("totaloption", totaloption);//렌트(대여)차량 옵션금액 저장
		
		//실제 CarOrder.jsp로 이동시... request영역 전달
		RequestDispatcher dis = 
				request.getRequestDispatcher("CarMain.jsp?center=CarOrder.jsp");
		dis.forward(request, response);			
	}
}
