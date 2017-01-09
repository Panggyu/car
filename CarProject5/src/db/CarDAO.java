package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.sun.org.apache.regexp.internal.recompile;

//커넥션풀을 이용하여 데이터베이스에 접근하여 데이터를 입력 수정 삭제 검색할수 있는 DAO클래스 선언
public class CarDAO {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	/* DB연결 메소드 */
	public void getCon() {
		try {
			// 1.WAS서버와 연결된 DBApp웹프로젝트의 모든 정보를 가지고 있는 컨텍스트객체 생성
			Context init = new InitialContext();
			// 2.연결된 WAS서버에서 DataSource(커넥션풀) 검색해서 가져오기
			DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/jspbeginner");
			// DataSource(커넥션풀)에서 DB연동객체 (커넥션) 가져오기
			con = ds.getConnection(); // DB연결

		} catch (Exception err) {
			err.printStackTrace();
		}

	}// getCon()메소드 끝
	/* 전체 차량 보기 메소드를 작성 */

	public Vector<CarListBean> getAllCarlist() {
		// 리턴할 Vector객체를 선언
		Vector<CarListBean> v = new Vector<CarListBean>();
		// 하나의 레코드를 저장할 객체 선언
		CarListBean bean = null;

		try {
			// 커넥션 메소드 호출 하여 DB연결객체 하나 얻기
			getCon();// DB연결
			// 쿼리준비 : 전체 차량 레코드 검색
			String sql = "select * from carlist";
			// 쿼리를 실행할수있는 객체 선언
			pstmt = con.prepareStatement(sql);
			// 쿼리 실행후 결과를 리턴
			rs = pstmt.executeQuery();
			// 반복문을 돌면서 빈클래스에 컬럼데이터를 저장
			while (rs.next()) {// Resultset에 레코드가 존재할때까지 반복
				bean = new CarListBean();
				bean.setCarno(rs.getInt(1)); // 차번호 담기
				bean.setCarname(rs.getString(2)); // 차이름 담기
				bean.setCarcompany(rs.getString(3));// 차제조사
				bean.setCarprice(rs.getInt(4));// 차가격 담기
				bean.setCarusepeople(rs.getInt(5));// 차인승 담기
				bean.setCarinfo(rs.getString(6));// 차정보 담기
				bean.setCarimg(rs.getString(7));// 차이미지명 담기
				bean.setCarcategory(rs.getString(8));// 차종류(대형,소형,중형) 담기
				// 다 저장된 빈객체를 백터에 저장
				v.add(bean);
			}
			// DB연결객체 커넥션 풀에 반납
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v; // 백터 리턴
	}// getAllCarlist()메소드 끝

	/* 카테고리별 자동차레코드 데이터 검색메소드 */
	public Vector<CarListBean> getCategoryCarList(String carcategory) {
		// 리턴할 Vector객체를 선언
		Vector<CarListBean> v = new Vector<CarListBean>();
		// 하나의 레코드를 저장할 객체 선언
		CarListBean bean = null;

		try {
			// 커넥션 메소드 호출 하여 DB연결객체 하나 얻기
			getCon();// DB연결
			// 쿼리준비 : 카테고리별 차량 레코드 검색
			String sql = "select * from carlist where carcategory=?";
			// 쿼리를 실행할수있는 객체 선언
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, carcategory);
			// 쿼리 실행후 결과를 리턴
			rs = pstmt.executeQuery();

			// 반복문을 돌면서 빈클래스에 컬럼데이터를 저장
			while (rs.next()) {// Resultset에 레코드가 존재할때까지 반복
				bean = new CarListBean();
				bean.setCarno(rs.getInt(1)); // 차번호 담기
				bean.setCarname(rs.getString(2)); // 차이름 담기
				bean.setCarcompany(rs.getString(3));// 차제조사
				bean.setCarprice(rs.getInt(4));// 차가격 담기
				bean.setCarusepeople(rs.getInt(5));// 차인승 담기
				bean.setCarinfo(rs.getString(6));// 차정보 담기
				bean.setCarimg(rs.getString(7));// 차이미지명 담기
				bean.setCarcategory(rs.getString(8));// 차종류(대형,소형,중형) 담기
				// 다 저장된 빈객체를 백터에 저장
				v.add(bean);
			}
			// DB연결객체 커넥션 풀에 반납
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v; // 백터 리턴
	}// getCategoryCarList메소드 끝

	/* 하나의 자동차 정보를 리턴하는 메소드 */
	public CarListBean getOneCar(int carno) {
		// 리턴할 하나의 레코드를 저장할 객체 선언
		CarListBean bean = null;
		try {
			// 커넥션 메소드 호출 하여 DB연결객체 하나 얻기
			getCon();// DB연결
			// 쿼리준비 : 매개변수로 전달받은 차넘버에 해당하는 차량 레코드 검색
			String sql = "select * from carlist where carno=?";
			// 쿼리를 실행할수있는 객체 선언
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, carno);
			// 쿼리 실행후 결과를 리턴
			rs = pstmt.executeQuery();

			// 반복문을 돌면서 빈클래스에 컬럼데이터를 저장
			while (rs.next()) {// Resultset에 레코드가 존재할때까지 반복
				bean = new CarListBean();
				bean.setCarno(rs.getInt(1)); // 차번호 담기
				bean.setCarname(rs.getString(2)); // 차이름 담기
				bean.setCarcompany(rs.getString(3));// 차제조사
				bean.setCarprice(rs.getInt(4));// 차가격 담기
				bean.setCarusepeople(rs.getInt(5));// 차인승 담기
				bean.setCarinfo(rs.getString(6));// 차정보 담기
				bean.setCarimg(rs.getString(7));// 차이미지명 담기
				bean.setCarcategory(rs.getString(8));// 차종류(대형,소형,중형) 담기
			}
			// DB연결객체 커넥션 풀에 반납
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean; // 빈 리턴
	}// getOneCar메소드끝

	
	// 렌트카 주문데이터를 담고 있는 CarOrderBean객체를 넘겨받아...
	// DB에 저장하는 메소드
	public void insertCarOrder(CarOrderBean cbean) {
		try {
			// 커넥션 메소드 호출 하여 DB연결객체 하나 얻기
			getCon();// DB연결
			//쿼리준비
			String sql = "insert into carorder(carno,carqty,carreserveday,"
			+ "carbegindate,carins,carwifi,carnave,carbabyseat,memberphone,memberpass) "
			+ "values(?,?,?,?,?,?,?,?,?,?)";
			//쿼리를 실행할 객체 선언
			pstmt = con.prepareStatement(sql);
			//?에 값을 대입
			pstmt.setInt(1, cbean.getCarno());
			pstmt.setInt(2, cbean.getCarqty());
			pstmt.setInt(3, cbean.getCarreserveday());
			pstmt.setString(4, cbean.getCarbegindate());
			pstmt.setInt(5, cbean.getCarins());
			pstmt.setInt(6, cbean.getCarwifi());
			pstmt.setInt(7, cbean.getCarnave());
			pstmt.setInt(8, cbean.getCarbabyseat());
			pstmt.setString(9, cbean.getMemberphone());
			pstmt.setString(10, cbean.getMemberpass());
			
			//쿼리를 실행하시오.
			pstmt.executeUpdate();
			
			//디비연결 객체 반납
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//insertCarOrder메소드 끝

	/*예약확인 페이지에서..렌트카예약시 작성한..
	 * 전화번호와 패스워드에 해당하는  예약한 주문정보를 모두 가져오는 메소드  */
	public Vector<CarConfirmBean> getAllCarOrder(String memberphone, String memberpass) {
		//렌트예약 정보를 담고있는 각각의 CarConfirmBean객체를 담기위한 컬렉션 객체 생성
		Vector<CarConfirmBean> v = new Vector<CarConfirmBean>();
		//DB에서 검색한 렌트예약 정보 객체(CarConfirmBean객체)를 저장할 참조변수 선언 
		CarConfirmBean bean = null;
		try {
			//DB연결
			getCon();
			//렌트예약한 날자가  현재날짜보다 크고,
			//렌트예약시 작성한 비회원 전화번호과 패스워드에 해당하는 렌트예약정보를 검색하는데..
			//carorder테이블과 carorder테이블을 natural 조인하여 검색.
			
			//참고! String 타입을 Date타입으로 변경 했다 
			String sql = "select * from carorder natural join carlist where "
					+ "now() < str_to_date(carbegindate , '%Y-%m-%d') and "
					+ "memberphone=? and memberpass=?";
			
			//참고
			//SELECT 문에 *와 같이 별도의 컬럼 순서를 지정하지 않으면...
			//NATURAL JOIN의 기준이 되는 컬럼들이 다른 컬럼보다 먼저 출력된다. 
			//이 때 NATURAL JOIN은 JOIN에 사용된 같은 이름의 컬럼을 중복출력하지 않고 하나로 처리한다. 


			//?를 제외한 select구문을 담은 쿼리실행 객체 반환
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberphone);//? 값 셋팅  : 예약시 작성한 전화번호
			pstmt.setString(2, memberpass);//? 값 셋팅 : 예약시 작성한 비밀번호 
			//select한 렌트예약정보를 담고 있는 ResultSet객체 반환
			rs = pstmt.executeQuery();
			//렌트 예약 정보 하나하나를  CarConfirmBean()객체에 저장
			while(rs.next()){
				bean = new CarConfirmBean(); //dto 생성
				bean.setOrderid(rs.getInt(2));//자동차 렌트(대여)한  주문id 담기
				bean.setCarqty(rs.getInt(3));//렌트 차량 대여한 수량 저장
				bean.setCarreserveday(rs.getInt(4));//대여한 기간 저장
				bean.setCarbegindate(rs.getString(5));//자동차를 렌트(대여)힌 시작날짜 저장.
				bean.setCarins(rs.getInt(6));//렌트시 보험적용 일수 저장
				bean.setCarwifi(rs.getInt(7));//렌트시 무선wifi적용 일수 저장
				bean.setCarnave(rs.getInt(8));//렌트시 네비게이션 적용여부 저장 
				bean.setCarbabyseat(rs.getInt(9));//렌트시 베이비시트적용 일수 저장
				bean.setCarname(rs.getString(12));//렌트예약한 자동차 이름 저장
				bean.setCarprice(rs.getInt(14));//렌트예약한 자동차 가격저장
				bean.setCarimg(rs.getString(17));//렌트예약한 자동차 이미지 저장
				v.add(bean);//백터에 담기
			}
			//자원해제
			con.close();		
			
		} catch (Exception e) {
			System.out.println("getAllCarOrder메소드에서 오류 : " + e);
		}
		
		
		//백터 반환
		return v;
	}
	
	//하나의 주문아이디를 전달받아... 하나의 주문 정보를 리턴하는 메소드
	public CarConfirmBean getOneOrder(int orderid) {
		// 리턴타입 선언
		CarConfirmBean cbean =null;
		try {
			getCon();
			//쿼리준비
			String sql ="select * from carorder where orderid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, orderid);
			//결과를 리턴
			rs=pstmt.executeQuery();
			if(rs.next()){
				cbean = new CarConfirmBean();
				cbean.setOrderid(orderid);//예약 id
				cbean.setCarbegindate(rs.getString(5));//대여 예약(주문)날짜
				cbean.setCarreserveday(rs.getInt(4));//대여기간
				cbean.setCarins(rs.getInt(6));//보험 적용 여부
				cbean.setCarwifi(rs.getInt(7));//wifi 적용 여부
				cbean.setCarnave(rs.getInt(8));//네비 적용 여부
				cbean.setCarbabyseat(rs.getInt(9));//베이비시티 적용 여부 
			}
			con.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return cbean;
	}//getOneOrder메소드 끝


	//예약 수정내용을 전달받아.. 다시예약을 수정하는 메소드	
	public void carOrderUpdate(CarOrderBean bean) {
		try {
			getCon();
		String sql ="update carorder set carbegindate=? , carreserveday=? , carqty=?"
					+ ", carins=? , carwifi=? , carbabyseat=? where orderid=? "
					+ "and memberpass=?";
			//쿼리 실행할 객체 생성
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, bean.getCarbegindate());
			pstmt.setInt(2, bean.getCarreserveday());
			pstmt.setInt(3, bean.getCarqty());
			pstmt.setInt(4, bean.getCarins());
			pstmt.setInt(5, bean.getCarwifi());
			pstmt.setInt(6, bean.getCarbabyseat());
			pstmt.setInt(7, bean.getOrderid());
			pstmt.setString(8, bean.getMemberpass());
			pstmt.executeUpdate();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//하나의 주문 정보를 삭제 하는 메소드
	public int carOrderDelete(int orderid, String memberpass) {
		int result=0;
		try {
			getCon();
			String sql ="delete from carorder where orderid=? and memberpass=?";
			pstmt = con.prepareStatement(sql);
			//?에 값을 대입
			pstmt.setInt(1, orderid);
			pstmt.setString(2, memberpass);
			//쿼리가 실행되지 않았다면 0값이 리턴 실행이 되면 1이 리턴됩니다.
			result = pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return result;
	}

}